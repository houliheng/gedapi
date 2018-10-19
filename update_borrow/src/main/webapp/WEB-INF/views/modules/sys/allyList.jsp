<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>群组管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/ally/">群组列表</a></li>
		<shiro:hasPermission name="sys:ally:edit"><li><a href="${ctx}/sys/ally/form">添加群组</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="ally" action="${ctx}/sys/ally/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
		<thead>
			<tr><th>群组ID</th><th>群组名称</th><th>群组排序号</th>
				<shiro:hasPermission name="sys:ally:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="ally">
			<tr>
				<td>${ally.id}</td>
				<td>${ally.allyname}</td>
				<td>${ally.ordernum}</td>
				<shiro:hasPermission name="sys:ally:edit"><td>
					<a href="${ctx}/sys/ally/assign?id=${ally.id}">分配</a>
    				<a href="${ctx}/sys/ally/form?id=${ally.id}">修改</a>
					<a href="${ctx}/sys/ally/delete?id=${ally.id}" onclick="return confirmx('确认要删除该组件吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>