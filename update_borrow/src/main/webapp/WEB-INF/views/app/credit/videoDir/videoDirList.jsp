<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>影像目录管理管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(list)}, rootId = "${not empty list[0].parent.id ? list[0].parent.id : '0'}";
			addRow("#treeTableList", tpl, data, rootId, true);

			var treeTable = $("#treeTable").treeTable({
				expandLevel : 1,
				beforeExpand : function ($treeTable, id){
					//判断id是否已经有了孩子节点，如果有了就不再加载，这样就可以起到缓存的作用
					if ($('.' + id, $treeTable).length) {  return; }
					$.ajax({
	 					type: "POST",
 					   	url: "${ctx}/credit/videoDir/listNode?id=",
 					   	data:{
 					   		"parent.id": id,
 					   		"type" : '${videoDir.type}'
 					   		},
 					   	datatype: 'json',
 					   	success: function(result){
 					   		var html = addRow("#treeTableList", tpl, result, id, false);
 					   		$treeTable.addChilds(html);
 					   	}
	 				});
				}
			});

			if(data.length > 0){
				treeTable.openNode($("#" + data[0].id));
			}

			$("a:contains('删除')").live("click",function(){
				var id = $(this).next().val();
				var a=$(this)[0];
				$.ajax({
	 					type: "POST",
 					    url: "${ctx}/credit/videoDir/checkNode?id=",
 					   	data:{"parent.id": id},
 					   	datatype: 'json',
 					   	success: function(result){
 					   		if(result!=""){
 					   			return alertx('该目录存在下级目录，不允许删除！');
 					   		}else{
			 					return confirmx('是否要删除该目录？',a.href);
			 				}
			 			}
				 	});
	 			return false;
			});
			/* end */

			var addFlag = '${addFlag}';//true:新增目录
			if (addFlag) {
				var id = '${VideoDir.id}';
				parent.refreshTree(true, id);//调用index画面刷新方法
			}
		});

		function addRow(list, tpl, data, pid, root) {
			var html = "";
			for (var i = 0; i < data.length; i++) {
				var row = data[i];
				if ((${fns:jsGetVal('row.parentId')}) == pid) {
					html += Mustache.render(tpl, {
								dict: {
									type: row.type
								}, pid: (root?0:pid), row: row
					});
				}
			}
			if(root==true){
				$(list).append(html);
			}
			return html;
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
	<li class="active"><a href="${ctx}/credit/videoDir/list?id=&parent.id=0">目录列表</a></li>
		<shiro:hasPermission name="credit:videoDir:edit"><li><a href="${ctx}/credit/videoDir/form?parent.id=${videoDir.id}&type=${videoDir.type}">目录添加</a></li></shiro:hasPermission>
	</ul>
	<sys:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed table-hover">
		<thead><tr><th>目录名称</th><th>对应路径</th><th>创建时间</th>
		<shiro:hasPermission name="credit:videoDir:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody id="treeTableList"></tbody>
	</table>

	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}" hasChild={{row.haveChildNode}}>
			<td><a controller='true'>{{row.name}}</a></td>

			<td>{{row.fileDir}}</td>
			<td>{{row.createDate}}</td>

			<shiro:hasPermission name="credit:videoDir:edit"><td>
				<a href="${ctx}/credit/videoDir/form?id={{row.id}}">修改</a>
				<a href="${ctx}/credit/videoDir/delete?id={{row.id}}">删除</a>
				<input type="hidden" value={{row.id}}>
				<a href="${ctx}/credit/videoDir/form?parent.id={{row.id}}&type={{row.type}}">添加下级目录</a>
			</td></shiro:hasPermission>
		</tr>
	</script>
</body>
</html>