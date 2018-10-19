<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>菜单管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(list)}, rootId = "${not empty menu.id ? menu.id : '0'}";
			addRow("#treeTableList", tpl, data, rootId, true);
			var treeTable = $("#treeTable").treeTable({
				expandLevel : 1,
				/**
	        	 * @reqno: H1507080023
	        	 * @date-designer:20150706-zhunan
	        	 * @db-j : sys_office : id,parent_id,name,sort,code,type
	        	 * @e-out-other : treeTableList - 树表 : 展示菜单树的grid
	        	 * @date-author:20150706-zhunan: 根据点开的“+”号的ID，来找其下级节点内容，找出后进行渲染显示
	        	 */
				beforeExpand : function ($treeTable, id){
					//判断id是否已经有了孩子节点，如果有了就不再加载，这样就可以起到缓存的作用
		            if ($('.' + id, $treeTable).length) {  return; }
		            $.ajax({
	 					type: "POST",
 					   	url: "${ctx}/sys/menu/listNode?",
 					   	data:{parentId: id},
 					   	datatype: 'json',
 					   	success: function(result){
 					   		var html = addRow("#treeTableList", tpl, result, id, false);
 					   		$treeTable.addChilds(html);
 					   	}	
	 				});
				}
			}).show();
			
			/**
        	 * @reqno: H1507080023
        	 * @date-designer:20150706-zhunan
        	 * @e-out-other : treeTableList - 树表 : 展示菜单树的grid
        	 * @date-author:20150706-zhunan: 默认展开第二层
        	 */
        	 if(data.length > 0){
				treeTable.openNode($("#" + data[0].id));
        	 }
		});
		
		/**
    	 * @reqno: H1507080023
    	 * @date-designer:20150713-zhunan
    	 * @e-out-other : treeTableList - 树表 : 展示菜单树的grid
    	 * @date-author:20150713-zhunan: 根据传入的数据data来进行渲染，如果root为true，表示向id=list的组建中插入数据，pid表示其父节点ID，tpl为要被替换的tr模板
    	 */
		function addRow(list, tpl, data, pid, root) {
			var html = "";
			for (var i = 0; i < data.length; i++) {
				var row = data[i];
// 				if ((${fns:jsGetVal('row.parentId')}) == pid) {
					var href = row.href;
					var permission = row.permission;
					html += Mustache.render(tpl, {
								dict: {
									isShow: getDictLabel(${fns:toJson(fns:getDictList('menu_function'))}, row.isShow)
								}, 
								href: href,
								pid: (root?0:pid), 
								icon: row.icon!=null?row.icon:' hide',
								permission : permission,
								name : row.sysManagesName,
								menu: row,
								haveChildNode : row.haveChildNode
							});
// 				}
			}
// 			alert(html);
			if(root==true){
				$(list).append(html);
			}
			return html;
		}
		
    	function updateSort() {
    		/**
			 * @reqno: 	H1506250037
			 * @date-designer:20150627-zhunan
			 * @e-in-text : sorts - 输入框 : 输入排序号
			 * @date-author:20150627-zhunan: 对要提交的数据进行校验，禁止输入空的数据，当出现空值时候进行提示，并且标注红色
			 */
    		var sorts = $("[id='sorts']");
    		var ids = $("[name='ids']");
    		var idsStr = "";
    		var empute = false;
    		for(var i=0;i<sorts.length;i++){
    			var value = sorts[i].value;
    			var id = ids[i].value;
    			var defaultValue = sorts[i].defaultValue;
    			if(defaultValue != value) {
    				idsStr += (id+",");
    			}
    			if(value==null||value.length==0){
    				$(sorts[i]).css("background-color","#FF2D2D");
    				empute = true;
    				break;
    			}
    		}
    		
    		if(empute==true){
    			alert("请您输入正确数字！");
    			return;
    		}
    		<%--
    		/**
    		 * @reqno:H1511170151
    		 * @date-designer:2015年11月17日-xudong
    		 * @e-out-table : 菜单列表 : 菜单列表 
    		 * @db-j : sys_menu : 菜单表
    		 * @db-j : rl_menu_sys : 系统菜单关联表
    		 * @db-j : sys_system : 系统表
    		 * @date-author:2015年11月17日-xudong:验证用户是否有修改菜单排序的权限
    		 */
    	 --%>
    		if(idsStr == "") {
    			loading('正在提交，请稍等...');
    	    	$("#listForm").attr("action", "${ctx}/sys/menu/updateSort");
    	    	$("#listForm").submit();
    		} else {
    			// 验证权限
    			$.ajax({
    				type: "POST",
    			   	url: "${ctx}/sys/menu/hasMenuPrivilege",
    			   	data:{"menuId": idsStr},
    			   	datatype: 'json',
    			   	success: function(result){
    			   		if(result.isOK == true) {
    			   			loading('正在提交，请稍等...');
    		    	    	$("#listForm").attr("action", "${ctx}/sys/menu/updateSort");
    		    	    	$("#listForm").submit();
    			   		} else {
    			   			top.$.jBox.tip('您没有修改该菜单排序的权限！');
    			   		}
    			   	}
    			});
    		}
    	}
    	
    	<%--
		/**
		 * @reqno:H1511170151
		 * @date-designer:2015年11月17日-xudong
		 * @e-out-table : 菜单列表 : 菜单列表 
		 * @db-j : sys_menu : 菜单表
		 * @db-j : rl_menu_sys : 系统菜单关联表
		 * @db-j : sys_system : 系统表
		 * @date-author:2015年11月17日-xudong:增加导航到修改菜单页的方法，验证用户是否有修改菜单的权限，如果没有，则提示用户
		 */
	 --%>
    	function toEditMenuPage(menuId) {
    		// 验证是否有修改菜单的权限
    		$.ajax({
					type: "POST",
				   	url: "${ctx}/sys/menu/hasMenuPrivilege",
				   	data:{"menuId": menuId},
				   	datatype: 'json',
				   	success: function(result){
				   		if(result.isOK == true) {
				   			var href = '${ctx}/sys/menu/form?id=' + menuId;
				    		window.location.href = href;
				   		} else {
				   			top.$.jBox.tip('您没有修改该菜单的权限！');
				   		}
				   	}
			});
    	}
    	
    	<%--
			/**
			 * @reqno:H1511170151
			 * @date-designer:2015年11月17日-xudong
			 * @e-out-table : 菜单列表 : 菜单列表 
			 * @db-j : sys_menu : 菜单表
			 * @db-j : rl_menu_sys : 系统菜单关联表
			 * @db-j : sys_system : 系统表
			 * @date-author:2015年11月17日-xudong:点击删除菜单的方法，验证用户是否有删除菜单的权限，如果没有，则提示用户
			 */
		 --%>
    	function deleteMenu(menuId) {
    		$.ajax({
				type: "POST",
			   	url: "${ctx}/sys/menu/hasMenuPrivilege",
			   	data:{"menuId": menuId},
			   	datatype: 'json',
			   	success: function(result){
			   		if(result.isOK == true) {
			   			var href = '${ctx}/sys/menu/delete?id=' + menuId;
			   			confirmx('要删除该菜单及所有子菜单项吗？', href);
			   		} else {
			   			top.$.jBox.tip('您没有删除该菜单的权限！');
			   		}
			   	}
			});
    	}
    	
    	<%--
			/**
			 * @reqno:H1511170151
			 * @date-designer:2015年11月17日-xudong
			 * @e-out-table : 菜单列表 : 菜单列表 
			 * @db-j : sys_menu : 菜单表
			 * @db-j : rl_menu_sys : 系统菜单关联表
			 * @db-j : sys_system : 系统表
			 * @date-author:2015年11月17日-xudong:点击增加子菜单的方法，验证用户是否有增加子菜单的权限，如果没有，则提示用户
			 */
		 --%>
    	function addSubMenu(menuId) {
    		$.ajax({
				type: "POST",
			   	url: "${ctx}/sys/menu/hasMenuPrivilege",
			   	data:{"menuId": menuId},
			   	datatype: 'json',
			   	success: function(result){
			   		if(result.isOK == true) {
			   			var href = '${ctx}/sys/menu/form?parent.id=' + menuId;
			   			window.location.href = href;
			   		} else {
			   			top.$.jBox.tip('您没有对该菜单添加子菜单的权限！');
			   		}
			   	}
			});
    	}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/menu/">菜单列表</a></li>
		<shiro:hasPermission name="sys:menu:edit"><li><a href="${ctx}/sys/menu/form">菜单添加</a></li></shiro:hasPermission>
	</ul>
	<sys:message content="${message}"/>
	<form id="listForm" method="post">
		<%--
			/**
			 * @reqno:H1511170151
			 * @date-designer:2015年11月17日-xudong
			 * @e-out-table : 菜单列表 : 菜单列表 
			 * @db-j : sys_menu : 菜单表
			 * @db-j : rl_menu_sys : 系统菜单关联表
			 * @db-j : sys_system : 系统表
			 * @date-author:2015年11月17日-xudong:在菜单列表中，增加所属系统列的展示信息
			 */
		 --%>
		<table id="treeTable" class="table table-striped table-bordered table-condensed hide table-hover">
			<thead><tr><th>名称</th><th>链接</th><th style="text-align:center;">排序</th><th>类型</th><th>权限标识</th><th>所属系统</th><shiro:hasPermission name="sys:menu:edit"><th>操作</th></shiro:hasPermission></tr></thead>
			<tbody id="treeTableList">
			</tbody>
		</table>
		<script type="text/template" id="treeTableTpl">
			<tr id="{{menu.id}}" pId="{{pid}}" hasChild={{haveChildNode}} >
				<td nowrap><i class="icon-{{icon}}"></i>
				<a href="${ctx}/sys/menu/form?id={{menu.id}}&isCheck=1">{{menu.name}}</a></td>
				<td title="{{menu.href}}">{{href}}</td>
				<td style="text-align:center;">
					<shiro:hasPermission name="sys:menu:edit">
						<input type="hidden" name="ids" value="{{menu.id}}"/>
						<input name="sorts" id="sorts" type="text" value="{{menu.sort}}" maxlength="9" style="width:50px;margin:0;padding:0;text-align:center;" 
							onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"">
					</shiro:hasPermission><shiro:lacksPermission name="sys:menu:edit">
						{{menu.sort}}
					</shiro:lacksPermission>
				</td>
				<td>{{dict.isShow}}</td>
				<td title="{{menu.permission}}">{{permission}}</td>
				<td title="{{menu.sysManagesName}}">{{name}}</td>
				<shiro:hasPermission name="sys:menu:edit"><td nowrap>
					<a href="#" onclick="toEditMenuPage('{{menu.id}}');">修改</a>
					<a href="#" onclick="deleteMenu('{{menu.id}}');">删除</a>
					<a href="#" onclick="addSubMenu('{{menu.id}}');">添加下级菜单</a> 
				</td></shiro:hasPermission>
			</tr>
		</script>
		<shiro:hasPermission name="sys:menu:edit"><div class="form-actions pagination-left">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保存排序" onclick="updateSort();"/>
		</div></shiro:hasPermission>
	 </form>
</body>
</html>