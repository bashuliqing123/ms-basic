<@ms.html5>
	<@ms.nav title="栏目表管理"></@ms.nav>
	<!--@ms.searchForm name="searchForm" isvalidation=true>
		<@ms.searchFormButton>
			 <@ms.queryButton onclick="search()"/> 
		</@ms.searchFormButton>			
	</@ms.searchForm-->
	<@ms.panel>
		<div id="toolbar">
			<@ms.panelNav>
				<@ms.buttonGroup>
					<@ms.addButton id="addColumnBtn"/>
					<@ms.delButton id="delColumnBtn"/>
				</@ms.buttonGroup>
			</@ms.panelNav>
		</div>
		<table id="columnList" 
			data-show-refresh="true"
			data-show-columns="true"
			data-show-export="true"
			data-method="post" 
			data-side-pagination="server">
		</table>
	</@ms.panel>
	
	<@ms.modal  modalName="delColumn" title="删除栏目" >	
		<@ms.modalBody>删除选中栏目，如果有子栏目也会一并删除
			<@ms.modalButton>
				<!--模态框按钮组-->
				<@ms.button  value="确认删除？"  id="deleteColumnBtn"  />
			</@ms.modalButton>
		</@ms.modalBody>
	</@ms.modal>
</@ms.html5>
<#include "${managerViewPath}/column/shiro-js.ftl"/>