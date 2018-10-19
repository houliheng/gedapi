]<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>角色管理</title>
<meta name="decorator" content="default" />
<%@include file="/WEB-INF/views/include/treeview.jsp"%>
<script type="text/javascript">
	/**
	 * 
	 * @reqno:H1507080067
	 * @date-designer:20150715-zhangxingyu
	 * @db-z : sys_role : 角色表
	 * @e-in-other : div : 分页的控件
	 * @e-in-text : searchForm - form:form : 分页查询的表单
	 * @date-author:20150715-zhangxingyu:分页查询表单的验证，以及提交
	 */
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		if ($("#pageNo")[0].value.length > 10) {
			top.$.jBox.tip('当前页码大小长度不能大于10位！');
			return true;
		} else if ($("#pageSize")[0].value.length > 10) {
			top.$.jBox.tip('每页条数大小的长度不能大于10位！');
			return true
		} else {
			$("#searchForm").submit();
			return false;
		}
	}
<%--
		/**
		 * @reqno:H1511170153
		 * @date-designer:2015年11月17日-xudong
		 * @e-out-table : 角色列表 : 角色列表
		 * @db-j : sys_role : 角色表
		 * @db-j : rl_menu_sys : 系统菜单关联表
		 * @db-j : sys_system : 系统表
		 * @date-author:2015年11月17日-xudong:增加导航到修改角色页的方法，验证用户是否有修改角色的权限，如果没有，则提示用户
		 */
	 --%>
	function editRole(roleId) {
		// 验证是否有修改角色的权限
		$.ajax({
		type : "POST",
		url : "${ctx}/sys/role/hasRolePrivilege",
		data : {
			"roleId" : roleId
		},
		datatype : 'json',
		success : function(result) {
			if (result.isOK == true) {
				var href = '${ctx}/sys/role/form?id=' + roleId;
				window.location.href = href;
			} else {
				top.$.jBox.tip('您没有修改该角色的权限！');
			}
		}
		});
	}
<%--
			/**
			 * @reqno:H1511170153
			 * @date-designer:2015年11月17日-xudong
			 * @e-out-table : 角色列表 : 角色列表
			 * @db-j : sys_role : 角色表
			 * @db-j : rl_menu_sys : 系统菜单关联表
			 * @db-j : sys_system : 系统表
			 * @date-author:2015年11月17日-xudong:点击删除角色的方法，验证用户是否有删除角色的权限，如果没有，则提示用户
			 */
		 --%>
	function deleteRole(roleId) {
		$.ajax({
		type : "POST",
		url : "${ctx}/sys/role/hasRolePrivilege",
		data : {
			"roleId" : roleId
		},
		datatype : 'json',
		success : function(result) {
			if (result.isOK == true) {
				var href = '${ctx}/sys/role/delete?id=' + roleId;
				confirmx('确认要删除该角色吗？', href);
			} else {
				top.$.jBox.tip('您没有删除该角色的权限！');
			}
		}
		});
	}
<%--
			/**
			 * @reqno:H1511170153
			 * @date-designer:2015年11月17日-xudong
			 * @e-out-table : 角色列表 : 角色列表 
			 * @db-j : sys_role : 角色表
			 * @db-j : rl_menu_sys : 系统菜单关联表
			 * @db-j : sys_system : 系统表
			 * @date-author:2015年11月17日-xudong:当即分配按钮，检查用户是否有权限进行操作，如果没有，则提示用户
			 */
		 --%>
	function assignRole(roleId) {
		$.ajax({
		type : "POST",
		url : "${ctx}/sys/role/hasRolePrivilege",
		data : {
			"roleId" : roleId
		},
		datatype : 'json',
		success : function(result) {
			if (result.isOK == true) {
				var href = '${ctx}/sys/role/assign?id=' + roleId;
				window.location.href = href;
			} else {
				top.$.jBox.tip('您没有分配该角色的权限！');
			}
		}
		});
	}
<%--
		/**
		 * @reqno:H1601050069
		 * @date-designer:2016年1月12日-sunna
		 * @date-author:2016年1月12日-sunna:点击报表选择按钮，检查用户是否有权限进行操作，如果没有，则提示用户
		 */
	 --%>
	function reportSelect(roleId) {
		$.ajax({
		type : "POST",
		url : "${ctx}/sys/role/hasRolePrivilege",
		data : {
			"roleId" : roleId
		},
		datatype : 'json',
		success : function(result) {
			if (result.isOK == true) {
				var href = '${ctx}/sys/role/report?id=' + roleId;
				window.location.href = href;
			} else {
				top.$.jBox.tip('您没有分配该角色的权限！');
			}
		}
		});
	}
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active">
			<a href="${ctx}/sys/role/">角色列表</a>
		</li>
		<shiro:hasPermission name="sys:role:edit">
			<li>
				<a href="${ctx}/sys/role/form?">角色添加</a>
			</li>
		</shiro:hasPermission>
	</ul>
	<!-- /**
	 * 
	 * @reqno:H1507080067
	 * @date-designer:20150715-zhangxingyu
	 * @e-in-text : searchForm - form:form : 分页查询的表单
	 * @e-in-other : pageNo - input type='hidden' : 隐藏域     用来存放当前的页号
	 * @e-in-other : pageSize - input type='hidden' : 隐藏域     用来存放当每页显示多少条数据
	 * @date-author:20150715-zhangxingyu:加一表单，用来存放分页是page的信息
	 */ -->
	<form:form id="searchForm" modelAttribute="dict" action="${ctx}/sys/role/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
	</form:form>
	<sys:message content="${message}" />
	<%--
		/**
			 * @reqno: H1511170153
			 * @date-designer:20151118-xudong
			 * @db-j : sys_role : 角色表
			 * @db-j : rl_role_sys : 角色系统关联表
			 * @e-out-table : 角色列表 : 角色列表
			 * @date-author:20151118-xudong:增加所属系统的展示列
			 */
	 --%>
	<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
		<tr>
			<th>角色名称</th>
			<th>英文名称</th>
			<th>归属机构</th>
			<th>数据范围</th>
			<th>所属系统</th>
			<shiro:hasPermission name="sys:role:edit">
				<th>操作</th>
			</shiro:hasPermission>
		</tr>
		<c:forEach items="${page.list}" var="role">
			<tr>
				<!-- 	/**
	 * 
	 * @reqno: 	H1506150058
	 * @date-designer:20150616-zhangxingyu
	 * 
	 * @date-author:20150616-zhangxingyu:加一个请求参数isCheck=1用来标记该请求是查看
	 */ -->
				<td>
					<a href="form?id=${role.id}&isCheck=1">${role.name}</a>
				</td>
				<%-- <td><a href="form?id=${role.id}">${role.enname}</a></td> --%>
				<!--
				    @reqno:H1507080066
				    @date-designer:20150710-laiwenchao
				    
				    @date-author:20150710-laiwenchao:将第二列的<a>标签去掉，即去掉英文名称的链接。
				    -->
				<td>${role.enname}</td>
				<!-- @end -->
				<td>${role.office.name}</td>
				<td>${fns:getDictLabel(role.dataScope, 'sys_data_scope', '无')}</td>
				<td>${role.sysManagesName }</td>
				<shiro:hasPermission name="sys:role:edit">
					<td>
						<!--
				    * @reqno: H1508110058
				    * @date-designer:20150813-chenshaojia
				    * @db-z : sys_role
				    * @date-author:20150813-chenshaojia:去掉仅限超级管理员能够修改角色的限制条件
				    -->
						<a href="#" onclick="editRole('${role.id}')">修改</a>
						<a href="#" onclick="deleteRole('${role.id}')">删除</a>
						<a href="#" onclick="assignRole('${role.id}')">人员分配</a>
						<!--
					/**
		 			 * 
		 			 * @reqno:H1512210067
		 			 * @date-designer:2015年12月16日-zhangzhida
		 			 * @e-ctrl : menuButton - 功能权限: 功能权限
		 			 * @date-author:2015年12月16日-zhangzhida:在角色信息列表界面增加功能权限控制按钮.点击按钮弹出功能信息.提供增删改操作
		 			 */
					  -->
						<a href="#" id="menuButton" onclick="showMenuTree('${role.id}')">功能权限</a>
						<!--
					/**
		 			 * 
		 			 * @reqno:H1512210069
		 			 * @date-designer:2015年12月16日-zhangzhida
					 * @e-ctrl : officeButton - 数据权限 : 数据权限
		 			 * @date-author:2015年12月16日-zhangzhida:在角色信息列表界面增加数据权限控制按钮.点击按钮弹出功能信息.提供增删改操作
		 			 */
					  -->
						<a href="#" id="officeButton" onclick="showOfficeTree('${role.id}')">数据权限</a>
						<!--
					/**
		 			 * 
		 			 * @reqno:H1601050069
		 			 * @date-designer:2016年1月12日-sunna
					 * @e-ctrl : reportButton - 报表选择: 报表选择
		 			 * @date-author:2016年1月12日-sunna:在角色信息列表界面增加报表选择按钮.点击按钮弹出功能信息.提供报表选择操作
		 			 */
					  -->
						<a href="#" id="reportButton" onclick="reportSelect('${role.id}')">报表选择</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
	</table>
	<!-- /**
	 * 
	 * @reqno:H1507080067
	 * @date-designer:20150715-zhangxingyu
	 * @e-in-other : div : 分页的控件
	 * @date-author:20150715-zhangxingyu:添加分页控件的div
	 */ -->
	<div class="pagination">${page}</div>
	<!--
	/**
		 * 
		 * @reqno:H1512210067
		 * @date-designer:2015年12月16日-zhangzhida
		 * @e-tree : roleTree - 功能权限树: 功能权限树
		 * @e-out-other : roleDialog - 功能权限树弹出框: 功能权限树弹出框
		 * @e-in-text : menuIds - 菜单编号 : 菜单编号
		 * @e-ctrl : submit - 确定 : 确定
		 * @e-ctrl : button - 返回 : 返回
		 * @db-z : sys_menu : 菜单信息表
		 * @date-author:2015年12月16日-zhangzhida:功能权限树所在的弹出框.当点击功能权限按钮时弹出信息.弹出该窗体
		 */
	  -->
	<div class="modal fade" id="roleDialog" tabindex="-1" role="dialog" aria-labelledby="123" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<form action="${ctx}/sys/role/save" id="roleForm" method="post" modelAttribute="role">
					<div class="modal-header">
						<h4 class="modal-title" id="roleTitle">权限设置</h4>
					</div>
					<div class="modal-body">
						<label class="control-label">权限设置:</label>
						<div class="controls">
							<div id="roleTree" class="ztree" style="margin-top:3px;float:left;"></div>
							<input type="hidden" id="id" name="id">
							<input type="hidden" id="menuIds" name="menuIds">
							<!--
								/**
									 * 
									 * @reqno:H1512210069
									 * @date-designer:2015年12月16日-zhangzhida
									 * @e-tree : roleTree - 数据权限数: 数据权限数
									 * @e-out-other : roleDialog - 数据权限树弹出框: 数据权限树弹出框
									 * @e-in-text : officeIds - 机构编号 : 机构编号
									 * @e-ctrl : submit - 确定 : 确定
									 * @e-ctrl : button - 返回 : 返回
									 * @db-z : sys_office : 机构信息表
									 * @date-author:2015年12月16日-zhangzhida: 数据权限树所在的弹出框.当点击数据权限按钮时.弹出该窗体
									 */
								  -->
							<input type="hidden" id="officeIds" name="officeIds">
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary">确定</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">返回</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		/**
		 * @reqno:H1512210067
		 * @date-designer:2015年12月16日-zhangzhida
		 * @date-author:2015年12月16日-zhangzhida:页面初始加载时,为roleForm增加提交验证方法.
		 										当提交时.获取功能权限树的已选的所有功能节点信息.遍历节点将信息.将功能编号存入数组.提交保存数据
		 */
		$(document).ready(function() {
			$("#roleForm").validate({
				submitHandler : function(form) {
					var ids = [], nodes = selectRoleTree.getCheckedNodes(true);
					for (var i = 0; i < nodes.length; i++) {
						ids.push(nodes[i].id);
					}
					if (selectRoleType == "menu") {
						$("#menuIds").val(ids);
					} else {
						/**
						 * @reqno:H1512210069
						 * @date-designer:2015年12月16日-zhangzhida
						 * @date-author:2015年12月16日-zhangzhida:页面初始加载时,为roleForm增加提交验证方法.
																当提交时.获取数据权限树的已选的所有功能节点信息.遍历节点将信息.将数据编号存入数组.提交保存数据
						 */
						$("#officeIds").val(ids);
					}
					$("#id").val(selectRoleId);
					loading('正在提交，请稍等...');
					form.submit();
				}
			});
		});
		/**
		 * @reqno:H1512210067
		 * @date-designer:2015年12月16日-zhangzhida
		 * @date-author:2015年12月16日-zhangzhida:初始化功能权限树和数据权限树的配置
		 */
		var setting = {
		check : {
		enable : true,
		nocheckInherit : true
		},
		view : {
			selectedMulti : false
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			beforeClick : function(id, node) {
				tree.checkNode(node, !node.checked, true, true);
				return false;
			}
		}
		};
		var selectRoleId;
		var selectRoleTree;
		var selectRoleType;

		/**
		 * 
		 * @reqno:H1512210067
		 * @date-designer:2015年12月16日-zhangzhida
		 * @db-j : sys_menu : 菜单信息表
		 * @date-author:2015年12月16日-zhangzhida:当点击功能权限时,获取参数角色编号.销毁roleTree
		 										根据角色编号,获取所有的功能权限信息.重新构建功能权限树
		 */
		function showMenuTree(roleId) {
			selectRoleId = roleId;
			selectRoleType = "menu";
			$.fn.zTree.destroy($("#roleTree"));
			$.ajax({
			type : "POST",
			url : "${ctx}/sys/role/menuTree",
			data : {
				"id" : roleId
			},
			datatype : 'json',
			success : function(result) {
				var treeList = result.menuList;
				var role = result.role;
				var roleTree = $.fn.zTree.init($("#roleTree"), setting, treeList);
				selectRoleTree = roleTree;
				roleTree.setting.check.chkboxType = {
				"Y" : "s",
				"N" : "s"
				};
				/**
				 * 
				 * @reqno:H1512210067
				 * @date-designer:2015年12月16日-zhangzhida
				 * @date-author:2015年12月16日-zhangzhida:根据角色已包含的功能权限信息.设置树节点选中.并展开所有树节点
				 */
				var ids = role.menuIds.split(",");
				for (var i = 0; i < ids.length; i++) {
					var node = roleTree.getNodeByParam("id", ids[i]);
					try {
						roleTree.checkNode(node, true, false);
					} catch (e) {
					}
				}
				//只是展开第一层
				var rootNode = roleTree.getNodeByParam("id", "1");
				roleTree.expandNode(rootNode, true);
				//全部展开
				//roleTree.expandAll(true);
				$("#officeIds").val(result.otherList);
			}
			});
			/**
			 * 
			 * @reqno:H1512210067
			 * @date-designer:2015年12月16日-zhangzhida
			 * @date-author:2015年12月16日-zhangzhida:设置弹出窗体的标题为"功能权限设置".并弹出窗体
			 */
			$("#roleTitle").html("功能权限设置");
			$("#roleDialog").modal("show");
		}

		/**
		 * 
		 * @reqno:H1512210069
		 * @date-designer:2015年12月16日-zhangzhida
		 * @db-j : sys_office : 机构信息表
		 * @date-author:2015年12月16日-zhangzhida:当点击数据权限时,获取参数角色编号.销毁roleTree
												根据角色编号,获取所有的数据权限信息.重新构建数据权限树
		 */
		function showOfficeTree(roleId) {
			selectRoleId = roleId;
			selectRoleType = "office";
			$.fn.zTree.destroy($("#roleTree"));
			$.ajax({
			type : "POST",
			url : "${ctx}/sys/role/officeTree",
			data : {
				"id" : roleId
			},
			datatype : 'json',
			success : function(result) {
				var treeList = result.officeList;
				var role = result.role;
				var roleTree = $.fn.zTree.init($("#roleTree"), setting, treeList);
				selectRoleTree = roleTree;
				roleTree.setting.check.chkboxType = {
				"Y" : "ps",
				"N" : "s"
				};
				/**
				 * 
				 * @reqno:H1512210069
				 * @date-designer:2015年12月16日-zhangzhida
				 * @date-author:2015年12月16日-zhangzhida:根据角色已包含的数据权限信息.设置树节点选中.并展开所有树节点
				 */
				var ids = role.officeIds.split(",");
				for (var i = 0; i < ids.length; i++) {
					var node = roleTree.getNodeByParam("id", ids[i]);
					try {
						roleTree.checkNode(node, true, false);
					} catch (e) {
					}
				}
				roleTree.expandAll(true);
				$("#menuIds").val(result.otherList);
			}
			});
			/**
			 * 
			 * @reqno:H1512210069
			 * @date-designer:2015年12月16日-zhangzhida
			 * @date-author:2015年12月16日-zhangzhida:设置弹出窗体的标题为"数据权限设置".并弹出窗体
			 */
			$("#roleTitle").html("数据权限设置");
			$("#roleDialog").modal("show");
		}
	</script>
</body>
</html>