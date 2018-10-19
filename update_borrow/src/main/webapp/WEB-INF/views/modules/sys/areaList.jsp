<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>区域管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(list)}, rootId = "0";
			addRow("#treeTableList", tpl, data, rootId, true);
			var treeTable = $("#treeTable").treeTable({
				expandLevel : 1,
				/**
	        	 * @reqno: H1507060002
	        	 * @date-designer:20150706-zhunan
	        	 * @db-j : sys_area : id,parent_id,name,sort,code,type
	        	 * @db-j : SYS_DICT : type,value,LABEL
	        	 * @e-out-other : treeTableList - 树表 : 展示区域树的grid
	        	 * @date-author:20150706-zhunan: 根据点开的“+”号的ID，来找其下级节点内容，找出后进行渲染显示
	        	 */
				beforeExpand : function ($treeTable, id){
					if($("#" + id + "_child").length > 0){
						$("#" + id + "_child").remove();
					}
					//判断id是否已经有了孩子节点，如果有了就不再加载，这样就可以起到缓存的作用
		            if ($('.' + id, $treeTable).length) {  return; }
		            $.ajax({
	 					type: "POST",
 					   	url: "${ctx}/sys/area/treeNode",
 					   	data:{parentId: id},
 					   	datatype: 'json',
 					   	success: function(result){
 					   		var html = addRow("#treeTableList", tpl, result, id, false);
 					   		$treeTable.addChilds(html);
 					   	}	
	 				});
				}
				/*@end*/
			});
			/**
        	 * @reqno: H1507060002
        	 * @date-designer:20150706-zhunan
        	 * @e-out-other : treeTableList - 树表 : 展示区域树的grid
        	 * @date-author:20150706-zhunan: 默认展开第二层
        	 */
        	 if(data.length > 0){
				treeTable.openNode($("#" + data[0].id));
        	 }
		});
		
		/**
    	 * @reqno: H1507060002
    	 * @date-designer:20150706-zhunan
    	 * @e-out-other : treeTableList - 树表 : 展示区域树的grid
    	 * @date-author:20150706-zhunan: 根据传入的数据data来进行渲染，如果root为true，表示向id=list的组建中插入数据，pid表示其父节点ID，tpl为要被替换的tr模板
    	 */
		function addRow(list, tpl, data, pid, root) {
			var html = "";
			for (var i = 0; i < data.length; i++) {
				var row = data[i];
				if ((${fns:jsGetVal('row.parent_id')}) == pid) {
					html += Mustache.render(tpl,{ dict : {
									type : getDictLabel(${fns:toJson(fns:getDictList('sys_area_type'))},row.type)
								},
								pid : row.parent_id,
								row : row
							});
				}
			}
			if(root==true){
				$(list).append(html);
			}
			return html;
			/*@end*/
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/area/">区域列表</a></li>
		<shiro:hasPermission name="sys:area:edit"><li><a href="${ctx}/sys/area/form">区域添加</a></li></shiro:hasPermission>
	</ul>
	<sys:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>区域名称</th><th>区域编码</th><th>区域类型</th><th>备注</th><shiro:hasPermission name="sys:area:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody id="treeTableList"></tbody>
	</table>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}" hasChild={{row.childnum}} arrow=true>
			<td><a href="${ctx}/sys/area/form?id={{row.id}}&isCheck=1">{{row.name}}</a></td>
			<td>{{row.code}}</td>
			<td>{{row.type}}</td>
			<td>{{row.remarks}}</td>
			<shiro:hasPermission name="sys:area:edit"><td>
				<a href="${ctx}/sys/area/form?id={{row.id}}">修改</a>
				<a href="${ctx}/sys/area/delete?id={{row.id}}" onclick="return confirmx('要删除该区域及所有子区域项吗？', this.href)">删除</a>
				<a href="${ctx}/sys/area/form?parent.id={{row.id}}">添加下级区域</a> 
			</td></shiro:hasPermission>
		</tr>
	</script>
</body>
</html>