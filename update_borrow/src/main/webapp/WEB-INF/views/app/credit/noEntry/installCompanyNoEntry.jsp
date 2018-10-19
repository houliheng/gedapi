<!-- 
 * @reqno: H1512260022
 * @date-designer:2016年01月13日-lirongchao
 * @date-author:2016年01月13日-lirongchao:到机构禁件设置jsp
 -->
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>机构禁件设置</title>
<meta name="decorator" content="blank" />
<%@include file="/WEB-INF/views/include/treeview.jsp"%>
<script type="text/javascript">
		var typeTree;
		var tree;
		var ids = [];
		// 初始化
		$(document).ready(function(){
			tree = $.fn.zTree.init($("#typeTreeCompanyNoEnty"), setting, zNodes);
			var	treeobj = $.fn.zTree.getZTreeObj("typeTreeCompanyNoEnty");
			var nodes = treeobj.getNodes();
			treeobj.expandNode(nodes[0],true,false,true);
			tree.checkNode(zNodes, true, false);
			tree.setting.check.chkboxType = { "Y" : "", "N" : "" };
			for(var i = 0 ;i < companyNoEntryList.length;i ++){
				var node = tree.getNodeByParam("id", companyNoEntryList[i].id);
				try{tree.checkNode(node, true, false);}catch(e){}
			}
		});
		var setting = {check:{enable:true,nocheckInherit:true},view:{selectedMulti:false},
				data:{simpleData:{enable:true}},callback:{beforeClick:function(id, node){
					tree.checkNode(node, !node.checked, true, true);
					return false;
				}}};
		var zNodes=[
					<c:forEach items="${officeList}" var="office">
					{id:"${office.id}", 
						pId:"${not empty office.parent.id?office.parent.id:0}", 
						name:"${office.name}"},
		            </c:forEach>];		
		var companyNoEntryList=[
					<c:forEach items="${companyNoEntryList}" var="companyNoEntry">
					{id:"${companyNoEntry.office.id}", 
						name:"${companyNoEntry.office.name}"},
		            </c:forEach>];
		//获取所有选中的机构id
		function getCheckedNodes(){
			var nodes = tree.getCheckedNodes(true);
			for(var i=0; i<nodes.length; i++) {
				ids.push(nodes[i].id);
			}
			return ids;
		}
	</script>
</head>
<body>
		<div class="span3" style="border-right:  #A8A8A8;">
			<h5>请选择机构</h5>
			<div id="typeTreeCompanyNoEnty" class="ztree"></div>
		</div>
</body>
</html>
