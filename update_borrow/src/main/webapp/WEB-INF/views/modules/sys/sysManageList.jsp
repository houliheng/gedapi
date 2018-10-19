]<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>系统管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			if($("#pageNo")[0].value.length>10){
				top.$.jBox.tip('当前页码大小长度不能大于10位！');
				return true;
			}
			else if($("#pageSize")[0].value.length>10){
				top.$.jBox.tip('每页条数大小的长度不能大于10位！');
				return true
			}else{
				$("#searchForm").submit();
		    	return false;
			}
	    }
		
		<%--
		/**
		 * @reqno:H1511160125
		 * @date-designer:20151117-xudong
		 * @db-j : sys_system : sys_system
		 * @e-out-table : 统列系表 : 系统列表
		 * @date-author:20151117-xudong:点击删除按钮时，判断此系统的编号是否在菜单、角色中使用。
 							 2、如果使用，不允许删除，给出“菜单、角色中此系统还在使用，请先删除菜单和角色后，再删除系统”；
		 */ 
	 --%>
		function deleteSys(id) {
			var href="${ctx}/sys/sysManage/delete?id=" + id;
			confirmx('确认要删除该系统吗？', href);
		}
	</script>
</head>
<body>
	<%-- /**
	 * 
	 * @reqno:H1511160121
	 * @date-designer:20151117-xudong
	 * @db-j : sys_system : sys_system
	 * @e-out-table : 统列系表 : 系统列表
	 * @date-author:20151117-xudong:增加系统添加的链接按钮
	 */ --%>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/sysManage/">系统列表</a></li>
		<shiro:hasPermission name="sys:sysManage:edit"><li><a href="${ctx}/sys/sysManage/form">系统添加</a></li></shiro:hasPermission>
	</ul>
	<%-- /**
	 * 
	 * @reqno:H1511160120
	 * @date-designer:20151117-xudong
	 * @e-in-text : searchForm - form:form : 分页查询的表单
	 * @e-in-other : pageNo - input type='hidden' : 隐藏域     用来存放当前的页号
	 * @e-in-other : pageSize - input type='hidden' : 隐藏域     用来存放当每页显示多少条数据
	 * @date-author:20151117-xudong:加一表单，用来存放分页是page的信息
	 */ --%>
	<form:form id="searchForm" modelAttribute="sysManage" action="${ctx}/sys/sysManage/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	</form:form>
	<sys:message content="${message}"/>
	<%-- /**
	 * 
	 * @reqno:H1511160120
	 * @date-designer:20151117-xudong
	 * @db-j : sys_system : sys_system
	 * @e-out-table : 统列系表 : 系统列表
	 * @date-author:20151117-xudong:显示系统列表
	 */ --%>
	 <%--
	 	/**
		* @reqno:H1512180081
		* @date-designer:20151223-sunna
		* @date-author:20151223-sunna:增加英文名称和系统名称的展示
		*/
	 --%>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<tr><th>系统编号</th><th>系统名称</th><th>英文名称</th><th>系统全名</th><th>访问地址</th><th>WebService服务地址</th><th>单点登录地址</th><shiro:hasPermission name="sys:sysManage:edit"><th>操作</th></shiro:hasPermission></tr>
		<c:forEach items="${page.list}" var="sysManage">
			<tr>
				<td id="no">${sysManage.no}</td>
				<td id="name">${sysManage.name}</td>
				<%--
	 				/**
					* @reqno:H1512180081
					* @date-designer:20151223-sunna
					* @date-author:20151223-sunna:增加英文名称和系统名称的展示
					*/
	 			--%>
				<td>${sysManage.sysEnname}</td>
				<td>${sysManage.sysAllname}</td>
				<td>${sysManage.accUrl}</td>
				<td>${sysManage.wbsUrl}</td>
				<td>${sysManage.slnUrl}</td>
				<shiro:hasPermission name="sys:sysManage:edit"><td>
					<%--
					/**
					 * @reqno:H1511160123
					 * @date-designer:20151117-xudong
					 * @db-j : sys_system : sys_system
					 * @e-out-table : 统列系表 : 系统列表
					 * @date-author:20151117-xudong:增加修改链接
					 */ 
					 --%>
					<a href="${ctx}/sys/sysManage/form?id=${sysManage.id}">修改</a>
					<%--
						/**
						 * @reqno:H1511160125
						 * @date-designer:20151117-xudong
						 * @db-j : sys_system : sys_system
						 * @e-out-table : 统列系表 : 系统列表
						 * @date-author:20151117-xudong:增加删除链接
						 */ 
					 --%>
					<a href="#" onclick="deleteSys('${sysManage.id}');">删除</a>
				</td></shiro:hasPermission>	
			</tr>
		</c:forEach>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>