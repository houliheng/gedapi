<!-- 
 * @reqno: H1512260024
 * @date-designer:2016年01月13日-lirongchao
 * @date-author:2016年01月13日-lirongchao:人员禁件设置jsp,分左，中，右三个部分，
 										左边为机构树节点，中间为机构数查询结果，右边为已选人员列表
 -->
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>人员禁件设置</title>
<meta name="decorator" content="blank" />
<%@include file="/WEB-INF/views/include/treeview.jsp"%>
<script src="${ctxStatic}/jqGrid/4.6/i18n/grid.locale-cn.js" type="text/javascript"></script>
<script src="${ctxStatic}/jqGrid/4.6/js/jquery.jqGrid.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/jqGrid/4.6/css/ui.jqgrid.css">
<script type="text/javascript">
		var typeTree;
		var userGrid;
		var selectedGrid;
		var pre_ids;
		var ids;
		// 初始化
		$(document).ready(function(){
			typeTree = $.fn.zTree.init($("#typeTreeUserNoEnty"), setting, officeNodes);
			userGrid = $("#userTable").jqGrid({
				datatype: "local",
				colNames:['ID','登录名','姓名'],
				colModel:[
					{name:'id',sortable:false, hidden: true},
					{name:'loginName',  width: 120, sortable:false},
					{name:'name', width: 120,  sortable:false}
				],
				rowNum:-1,
				height:$(window).height()*0.9,
				multiselect: true,
				gridview: true,
				autoencode: true,
				gridComplete : function() {
					if(ids){
						$.each(ids, function(i, rowid){
							$("#userTable").jqGrid('setSelection',rowid);
						});
					}
				},
				onSelectRow: selectRow,
				onSelectAll: selectAll
			});
			selectedGrid = $("#selectedTable").jqGrid({
				url:"",
				datatype: "json",
				colNames:['ID','登录名','姓名','操作'],
				colModel:[
					{name:'id', sortable:false, hidden: true},
					{name:'loginName', width: 120, sortable:false},
					{name:'name', width: 120, sortable:false},
					{name:'operate', width:50, align:"center", sortable:false, formatter:function(cellvalue, options, rowObject){
						return '<a title="删除" href="javascript:delRow(\''+ rowObject.id +'\')"><img style="height:14px;" src="${ctxStatic}/images/close_hover.png"></a>';
					}}
				],
				rowNum:-1,
				height:$(window).height()*0.9,
				gridview: true,
				autoencode: true,
				gridComplete : function() {
					ids = selectedGrid.getDataIDs();
				}
			});
			addUserNoEntryList();
			var	treeobj = $.fn.zTree.getZTreeObj("typeTreeUserNoEnty");
			var nodes = treeobj.getNodes();
			treeobj.expandNode(nodes[0],true,false,true);
		});
		var setting = {view: {selectedMulti:false,nameIsHTML:true,showTitle:false,dblClickExpand:false},
				data: {simpleData: {enable: true,idKey : "id",pIdKey : "pId"}},	callback: {onClick: treeOnClick}};
		var officeNodes=[
	            <c:forEach items="${officeList}" var="office">
	            {id:"${office.id}",
	             pId:"${not empty office.parent?office.parent.id:0}", 
	             name:"${office.name}",
	             type:"office"},
	            </c:forEach>];
		var selectedNodes =[
		    		        <c:forEach items="${userNoEntryList}" var="userNoEntry">
		    		        {id:"${userNoEntry.user.id}",
		    		         pId:"0",
		    		         name:"${userNoEntry.user.name}",
		    		         loginName:"${userNoEntry.user.loginName}"},
		    		        </c:forEach>];
		//点击选择项回调
		function treeOnClick(event, treeId, treeNode, clickFlag){
			$("#userTable").jqGrid("setGridParam",{
				url: "${ctx}/credit/userNoEntry/users",
				postData:{officeId:treeNode.id},
				datatype: "json",
				mytype: "POST"
			}).trigger("reloadGrid");
		}
		//加载添加禁件的用户
		function addUserNoEntryList() {
			for(var i = 0; i < selectedNodes.length; i++){
				var rowData = new Object();
				rowData.id = selectedNodes[i].id;
				rowData.pId = "0";
				rowData.name = selectedNodes[i].name;
				rowData.loginName = selectedNodes[i].loginName;
				selectedGrid.addRowData(selectedNodes[i].id,rowData);
			};		
		}
		function reloadTree(type){
			if(type == 'office'){
				$.fn.zTree.init($("#typeTreeUserNoEnty"), setting, officeNodes);
			}else{
				$.getJSON("${ctx}/theme/pageInfo/types?type="+type,function(data){
					$.fn.zTree.init($("#typeTreeUserNoEnty"), setting, data);
				});
			}
		}
		function selectRow(rowid, status){
			var rowData = userGrid.getRowData(rowid);
			if(status){
				if($.inArray(String(rowid), ids)<0)
					selectedGrid.addRowData(rowid,rowData);
			}else{
				selectedGrid.delRowData(rowid);
			}
		}
		function selectAll(rowids, status){
			$.each(rowids, function(i, rowid){
				var rowData = userGrid.getRowData(rowid);
				if(status){
					if($.inArray(String(rowid), ids)<0)
						selectedGrid.addRowData(rowid,rowData);
				}else{
					selectedGrid.delRowData(rowid);
				};
			});
		}
		function delRow(rowid){
			selectedGrid.delRowData(rowid);
			userGrid.setSelection(rowid);
		}
		function clearAssign(){
			var submit = function (v, h, f) {
			    if (v == 'ok'){
			    	if(ids == undefined){
			    		ids = '' ;
			    	}
			    	$.each(ids, function(i, rowid){
						delRow(rowid);
					});
			    	top.$.jBox.tip("已选人员清除成功！", 'info');
			    } else if (v == 'cancel'){
			    	top.$.jBox.tip("取消清除操作！", 'info');
			    }
			    return true;
			};
			tips="确定清除页面【已选人员列表】下的已选人员？";
			top.$.jBox.confirm(tips, "清除确认", submit);
		}
	</script>
</head>
<body>
	<div id="assignRole" class="row-fluid span12">
		<div class="span3" style="border-right: 1px solid #A8A8A8;">
			<h5>请选择机构</h5>
			<div id="typeTreeUserNoEnty" class="ztree"></div>
		</div>
		<div id="dai" class="span4">
			<h5>机构人员列表</h5>
			<table id="userTable" class="table-hover table-condensed"></table>
		</div>
		<div id="selected" class="span4" style="padding-left: 16px; border-left: 1px solid #A8A8A8;">
			<h5>已选人员列表</h5>
			<table id="selectedTable" class="table-hover table-condensed"></table>
		</div>
	</div>
</body>
</html>
