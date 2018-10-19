<!--
@reqno		:H1508270061
@db-z		 :sys_office : 机构表
@date-designer:20150827-jiangbing
@date-author  :20150827-jiangbing 改修BUG 添加机构后不刷新组织机构树
-->
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>机构管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			
			
			
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			/**
			 * @reqno: H1507230023
			 * @date-designer:20150727-chenshaojia
			 * @db-j : sys_office : parent_id
			 * @date-author:20150727-chenshaojia: 取查询的结果集的第一个值为根节点
			 */
			var data = ${fns:toJson(list)}, rootId = "${not empty list[0].parent.id ? list[0].parent.id : '0'}";
			addRow("#treeTableList", tpl, data, rootId, true);
			var treeTable = $("#treeTable").treeTable({
				expandLevel : 1,
				/**
				 * @reqno: H1507080024
				 * @date-designer:20150706-zhunan
				 * @db-j : sys_office : id,parent_id,name,sort,code,type
				 * @e-out-other : treeTableList - 树表 : 展示区域树的grid
				 * @date-author:20150706-zhunan: 根据点开的“+”号的ID，来找其下级节点内容，找出后进行渲染显示
				 */
				beforeExpand : function ($treeTable, id){
					//判断id是否已经有了孩子节点，如果有了就不再加载，这样就可以起到缓存的作用
					if ($('.' + id, $treeTable).length) {  return; }
					$.ajax({
	 					type: "POST",
 					   	url: "${ctx}/sys/office/listNode?id=",
 					   	data:{"parent.id": id},
 					   	datatype: 'json',
 					   	success: function(result){
 					   		var html = addRow("#treeTableList", tpl, result, id, false);
 					   		$treeTable.addChilds(html);
 					   	}
	 				});
				}
			});
			
			/**
			 * @reqno: H1507080024
			 * @date-designer:20150706-zhunan
			 * @e-out-other : treeTableList - 树表 : 展示菜单树的grid
			 * @date-author:20150706-zhunan: 默认展开第二层
			 */
			 /**
 			 * @reqno: H1508060012
 			 * @date-designer:20150807-zhangxingyu
 			 * @e-out-other : treeTableList - 树表 : 展示菜单树的grid
 			 * @date-author:20150807-zhangxingyu:加上判断条件，当data.length>0是才去展开
 			 * 									
 			 */
			if(data.length > 0){
				treeTable.openNode($("#" + data[0].id));
			}
			/**
			 * 
			 * @reqno: H1507070035
			 * @date-designer:20150715-zhangxingyu
			 * @db-z : sys_office : 机构表
			 * @e-out-other : a : 删除的超链接
			 * @date-author:20150715-zhangxingyu:为删除链接注册单击事件，
			 									   发送ajax异步请求获取此行机构的下级机构
			 									   如果有用户则给出提示：该机构存在下级机构，不允许删除！
			 * 									
			 */
			$("a:contains('删除')").live("click",function(){
				var id = $(this).next().val();
				var a=$(this)[0];
				$.ajax({
	 					type: "POST",
 					    /**
			             * @reqno:H1508280023
			             * @date-designer:20150831-jiangbing
			             * @date-author:20150831-jiangbing:判断要删除的节点是否有子节点
			             */
 					    url: "${ctx}/sys/office/checkNode?id=",
 					   	data:{"parent.id": id},
 					   	datatype: 'json',
 					   	success: function(result){
 					   		if(result!=""){
 					   			return alertx('该机构存在下级机构，不允许删除！');
 					   		}else{
 					   			/**
								 * 
								 * @reqno: H1507070035
								 * @date-designer:20150715-zhangxingyu
								 * @db-z : sys_user : 用户表
								 * @e-out-other : a : 删除的超链接
								 * @date-author:20150715-zhangxingyu:如果没有用户，再发送ajax异步请求获取此行机构下的所有用户
								 									   如果有用户则给出提示该机构下存在用户，不允许删除！
								 									   如果没有，给出是否删除提示，是发送删除请求，否返回false，事件结束！
								 * 									
								 */
 					   			$.ajax({
				 					type: "POST",
			 					   	url: "${ctx}/sys/user/byOfficeIdList",
			 					   	data:{officeId: id},
			 					   	datatype: 'json',
			 					   	success: function(result){
			 					   		if(result==""){
			 					   			return confirmx('是否要删除该机构？',a.href);
			 					   		}
			 					   		else{
			 					   			return alertx('该机构下存在用户，不允许删除！');
			 					   		}
			 					   	}
				 				});
 					   		}
 					   	}
	 				});
	 			return false;
			});
			/* end */

			/**
			 * @reqno:H1508270061
			 * @date-designer:20150827-jiangbing
			 * @date-author:20150827-jiangbing:判断是否添加机构，刷新父页面结构树
			 */
			var addFlag = ${addFlag};//true:新增机构
			if (addFlag) {
				var id = "${office.id}";
				parent.refreshTree(true, id);//调用index画面刷新方法
			}
		});
		
		/**
		 * @reqno: H1507080024
		 * @date-designer:20150713-zhunan
		 * @e-out-other : treeTableList - 树表 : 展示区域树的grid
		 * @date-author:20150713-zhunan: 根据传入的数据data来进行渲染，如果root为true，表示向id=list的组建中插入数据，pid表示其父节点ID，tpl为要被替换的tr模板
		 */
		function addRow(list, tpl, data, pid, root) {
			var html = "";
			for (var i = 0; i < data.length; i++) {
				var row = data[i];
				if ((${fns:jsGetVal('row.parentId')}) == pid) {
					html += Mustache.render(tpl, {
								dict: {
									type: getDictLabel(${fns:toJson(fns:getDictList('sys_office_type'))}, row.type)
								}, pid: (root?0:pid), row: row
							});
				}
			}
			if(root==true){
				$(list).append(html);
			}
			return html;
		}
// 		function addRow(list, tpl, data, pid, root){
// 			for (var i=0; i<data.length; i++){
// 				var row = data[i];
// 				if ((${fns:jsGetVal('row.parentId')}) == pid){
// 					$(list).append(Mustache.render(tpl, {
// 						dict: {
// 							type: getDictLabel(${fns:toJson(fns:getDictList('sys_office_type'))}, row.type)
// 						}, pid: (root?0:pid), row: row
// 					}));
// 					addRow(list, tpl, data, row.id);
// 				}
// 			}
// 		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
	<!-- 	 /**
	 * 
	 * @reqno:H1505280043
	 * @date-designer:20150601-zhangxingyu
	 * 
	 * 
	 * @date-author:20150601-zhangxingyu:原来是点过添加下级之后就再点机构列表，就会只查询点击的那个机构的下级机构 ，去掉参数，再点机构列表就会查询所有
	 */ -->
		<li class="active"><a href="${ctx}/sys/office/list?id=&parent.id=0">机构列表</a></li>
		<shiro:hasPermission name="sys:office:edit"><li><a href="${ctx}/sys/office/form?parent.id=${office.id}">机构添加</a></li></shiro:hasPermission>
	</ul>
	<sys:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed table-hover">
		<thead><tr><th>机构名称</th><th>归属区域</th><th>机构编码</th><th>机构类型</th>
<!-- 		<th>备注</th> -->
		<shiro:hasPermission name="sys:office:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody id="treeTableList"></tbody>
	</table>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}" hasChild={{row.haveChildNode}}>
			<!--/**
					 * 
					 * @reqno: 	H1506290069
					 * @date-designer:20150630-zhangxingyu
					 * 
					 * @date-author:20150630-zhangxingyu:加一个请求参数isCheck=1用来标记该请求是查看
					 */ -->
			<td><a href="${ctx}/sys/office/form?id={{row.id}}&isCheck=1">{{row.name}}</a></td>
			<td>{{row.area.name}}</td>
			<td>{{row.code}}</td>
			<td>{{dict.type}}</td>
			<!--<td>{{row.remarks}}</td>-->
			<shiro:hasPermission name="sys:office:edit"><td>
				<a href="${ctx}/sys/office/form?id={{row.id}}">修改</a>
			<!--/**
				 * @reqno: H1507070035
				 * @date-designer:20150715-zhangxingyu
				 * @e-out-other : input type='hidden' : 隐藏域
				 * @date-author:20150715-zhangxingyu:去掉原链接上的单击事件
				 * 									新加一input type="hidden"标签，用来放当前行的office.id
				 */ -->
				<a href="${ctx}/sys/office/delete?id={{row.id}}">删除</a>
				<input type="hidden" value={{row.id}}>
				<a href="${ctx}/sys/office/form?parent.id={{row.id}}">添加下级机构</a> 
			</td></shiro:hasPermission>
		</tr>
	</script>
</body>
</html>