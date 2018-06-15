/**
The MIT License (MIT) * Copyright (c) 2016 铭飞科技(mingsoft.net)

 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mingsoft.basic.biz.impl;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.basic.biz.IColumnBiz;
import com.mingsoft.basic.dao.IColumnDao;
import com.mingsoft.basic.entity.ColumnEntity;
import com.mingsoft.parser.IParserRegexConstant;
import com.mingsoft.util.StringUtil;

import net.mingsoft.basic.util.BasicUtil;
import net.mingsoft.basic.util.SpringUtil;

/**
 * 
 * 
 * <p>
 * <b>铭飞CMS-铭飞内容管理系统</b>
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2014 - 2015
 * </p>
 * 
 * <p>
 * Company:景德镇铭飞科技有限公司
 * </p>
 * 
 * @author 刘继平
 * 
 * @version 300-001-001
 * 
 *          <p>
 *          版权所有 铭飞科技
 *          </p>
 * 
 *          <p>
 *          Comments:栏目业务层实现类，继承CategoryBizImpl，实现接口IColumnBiz
 *          </p>
 * 
 *          <p>
 *          Create Date:2014-7-14
 *          </p>
 *
 *          <p>
 *          Modification history:
 *          </p>
 */
@Service("columnBiz")
public class ColumnBizImpl extends CategoryBizImpl implements IColumnBiz {

	/**
	 * 栏目持久化层注入
	 */
	private IColumnDao columnDao;

	/**
	 * 获取 columnDao
	 * 
	 * @return columnDao
	 */
	public IColumnDao getColumnDao() {
		return columnDao;
	}

	/**
	 * 设置 columnDao
	 * 
	 * @param columnDao
	 */
	@Autowired
	public void setColumnDao(IColumnDao columnDao) {
		this.columnDao = columnDao;
	}

	@Override
	protected IBaseDao getDao() {
		return columnDao;
	}

	/**
	 * 根据站点ID查询该站点下的栏目集合
	 * 
	 * @param columnWebsiteId
	 *            站点Id
	 * @return 栏目集合
	 */
	public List<ColumnEntity> queryColumnListByWebsiteId(int columnWebsiteId) {
		return columnDao.queryColumnListByWebsiteId(columnWebsiteId);
	}

	public List<ColumnEntity> queryChild(int categoryCategoryId, int columnWebsiteId, Integer modelId, Integer size) {
		return columnDao.queryColumnByCategoryIdAndWebsiteIdAndModelId(categoryCategoryId, columnWebsiteId, modelId,
				size);
	}

	public List<ColumnEntity> queryAll(int appId, int modelId) {
		return columnDao.queryByAppIdAndModelId(appId, modelId);
	}

	public List<ColumnEntity> queryAll(int modelId) {
		return columnDao.queryByAppIdAndModelId(BasicUtil.getAppId(),modelId);
	}

	/**
	 * 通过栏目的站点ID查询该站点下的栏目的父栏目Id为categoryCategoryId子栏目
	 * 通过递归查询将父栏目ID为categoryCategoryId的子栏目集合和他对应在同一节点树的父级栏目的集合全部查询装入List中
	 * 
	 * @param categoryCategoryId
	 *            栏目ID
	 * @param list
	 *            栏目集合
	 * @param columnWebsiteId
	 *            站点ID
	 */
	private void queryExpansionColumnListByWebsiteId(int categoryCategoryId, List<ColumnEntity> list,
			int columnWebsiteId) {
		List<ColumnEntity> queryChildList = new ArrayList<ColumnEntity>();
		queryChildList = columnDao.queryColumnByCategoryIdAndWebsiteIdAndModelId(categoryCategoryId, columnWebsiteId,
				null, null);
		for (int i = 0; i < queryChildList.size(); i++) {
			list.add(queryChildList.get(i));
		}

		if (categoryCategoryId != 0) {
			ColumnEntity columnEntity = (ColumnEntity) (columnDao.getEntity(categoryCategoryId));
			queryExpansionColumnListByWebsiteId(columnEntity.getCategoryCategoryId(), list, columnWebsiteId);
		}
	}

	/**
	 * 通过栏目ID查询该栏目同级栏目
	 * 
	 * @param columnId
	 *            栏目ID
	 * @return 同级栏目集合
	 */
	public List<ColumnEntity> querySibling(int columnId, Integer size) {
		ColumnEntity columnEntity = (ColumnEntity) columnDao.getEntity(columnId);
		List<ColumnEntity> list = new ArrayList<ColumnEntity>();
		if (columnEntity != null) {

			list = columnDao.queryColumnByCategoryIdAndWebsiteIdAndModelId(columnEntity.getCategoryCategoryId(),
					columnEntity.getAppId(), null, size);
		}
		return list;
	}

	/**
	 * 通过栏目ID查询顶级栏目的同级栏目
	 * 
	 * @param columnId
	 *            栏目ID
	 * @return 顶级同级栏目集合
	 */
	public List<ColumnEntity> queryTopSiblingListByColumnId(int columnId, Integer size) {
		ColumnEntity columnEntity = (ColumnEntity) columnDao.getEntity(columnId);
		List<ColumnEntity> list = null;
		if (columnEntity != null) {
			list = querySibling(columnEntity.getCategoryCategoryId(), size);
		}
		return list;
	}

	/**
	 * 根据栏目Id查询栏目的子栏目集
	 * 
	 * @param columnId
	 *            栏目ID
	 * @return 子栏目集合
	 */
	public List<ColumnEntity> queryChildListByColumnId(int columnId, Integer size) {
		ColumnEntity columnEntity = (ColumnEntity) columnDao.getEntity(columnId);
		List<ColumnEntity> list = null;
		if (columnEntity != null) {
			list = columnDao.queryColumnByCategoryIdAndWebsiteIdAndModelId(columnEntity.getCategoryId(),
					columnEntity.getAppId(), null, size);
		}
		return list;
	}

	/**
	 * 根据栏目ID查询其子栏目ID集合
	 * 
	 * @param categoryId
	 *            栏目ID
	 * @return 子栏目ID集合
	 */
	public int[] queryChildIdsByColumnId(int categoryId, int appId) {
		List<Integer> ids = columnDao.queryColumnChildIdList(categoryId, appId);
		int[] ret = new int[ids.size()];
		for (int i = 0; i < ret.length; i++)
			ret[i] = ids.get(i).intValue();
		return ret;
	}


	/**
	 * 用递归通过栏目ID查询栏目的父级栏目,将查询结果装入List集合中
	 * 
	 * @param columnId
	 *            栏目ID
	 * @param list
	 *            父级栏目集合
	 */
	private void queryColumnParent(ColumnEntity column, List<ColumnEntity> list) {
		if (column.getCategoryCategoryId() != 0) {
			ColumnEntity columnEntity = (ColumnEntity) columnDao.getEntity(column.getCategoryCategoryId());
			list.add(columnEntity);
			queryColumnParent(columnEntity, list);
		}
	}

	/**
	 * 通过栏目ID查询栏目对应节点路径上的父级栏目集合
	 * 
	 * @param columnId
	 *            栏目ID
	 * @return 栏目及其父级栏目集合
	 */
	public List<ColumnEntity> queryParentColumnByColumnId(int columnId) {
		List<ColumnEntity> list = null;
		ColumnEntity columnEntity = (ColumnEntity) columnDao.getEntity(columnId);
		if (columnEntity != null) {
			list = new ArrayList<ColumnEntity>();
			// 递归的查询所有父节点
			queryColumnParent(columnEntity, list);
		}
		return list;
	}

	/**
	 * 根据站点Id查询该站点下的栏目的父栏目Id为categoryCategoryId的子栏目集合数目统计
	 * 
	 * @param categoryCategoryId
	 *            父栏目ID
	 * @param columnWebsiteId
	 *            站点ID
	 * @return 子栏目统计数目
	 */
	public int queryColumnChildListCountByWebsiteId(int categoryCategoryId, int columnWebsiteId) {
		return columnDao.queryColumnChildListCountByWebsiteId(categoryCategoryId, columnWebsiteId);
	}
	
	/**
	 * 组织栏目链接地址
	 * @param request
	 * @param column 栏目实体
	 */
	public static void columnPath(ColumnEntity column) {
		IColumnBiz columnBiz=  SpringUtil.getBean(IColumnBiz.class);
		String columnPath = "";
		String file = BasicUtil.getRealPath("")+IParserRegexConstant.HTML_SAVE_PATH+File.separator+ column.getAppId();
		String delFile = "";
		//修改栏目路径时，删除已存在的文件夹
		column = (ColumnEntity) columnBiz.getEntity(column.getCategoryId());
		delFile = file + column.getColumnPath();
		if(!StringUtil.isBlank(delFile)){
			File delFileName = new File(delFile);
			delFileName.delete();
		}
		//若为顶级栏目，则路径为：/+栏目ID
		if(column.getCategoryCategoryId() == 0){
			column.setColumnPath(File.separator+column.getCategoryId());
			file = file + File.separator + column.getCategoryId();
		} else {
			List<ColumnEntity> list = columnBiz.queryParentColumnByColumnId(column.getCategoryId());
			if(!StringUtil.isBlank(list)){
				String temp = "";
				for(int i = list.size()-1; i>=0; i--){
					ColumnEntity entity = list.get(i);
					columnPath = columnPath + File.separator + entity.getCategoryId();
					temp = temp + File.separator + entity.getCategoryId();
				}
				column.setColumnPath(columnPath + File.separator + column.getCategoryId());
				file = file + temp + File.separator + column.getCategoryId();
			}
		}
		columnBiz.updateEntity(column);
		//生成文件夹
		File fileName = new File(file);
        fileName.mkdir();
	}

	@Override
	public void save(ColumnEntity column, int modelCode,int CategoryManagerId) {
		column.setCategoryAppId(BasicUtil.getAppId());
		column.setAppId(BasicUtil.getAppId());
		column.setCategoryManagerId(CategoryManagerId);
		column.setCategoryDateTime(new Timestamp(System.currentTimeMillis()));
		column.setCategoryModelId(modelCode);
		if(column.getColumnType()==ColumnEntity.ColumnTypeEnum.COLUMN_TYPE_COVER.toInt()){
			column.setColumnListUrl(null);
		}
		super.saveCategory(column);
		columnPath(column);
		
	}

	@Override
	public void delete(List<ColumnEntity> columns) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(ColumnEntity column, int modelCode) {
		// TODO Auto-generated method stub
		
	}

}