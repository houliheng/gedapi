<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>借后清收任务分配表管理</title>
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
	<div class="wrapper">
		<ul class="nav nav-tabs">
			<li class="active"><a href="${ctx}/postloan/accountCleanAllocate/">借后清收任务分配表列表</a></li>
			<shiro:hasPermission name="postloan:accountCleanAllocate:edit"><li><a href="${ctx}/postloan/accountCleanAllocate/form">借后清收任务分配表添加</a></li></shiro:hasPermission>
		</ul>
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="accountCleanAllocate" action="${ctx}/postloan/accountCleanAllocate/" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="filter">
						<ul class="ul-form">
						</ul>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />&nbsp; 
							<input id="btnReset"class="btn btn-primary" type="button" value="重置"/>
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<sys:message content="${message}"/>
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div id="tableDataId">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<shiro:hasPermission name="postloan:accountCleanAllocate:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${page.list}" var="accountCleanAllocate">
						<tr>
							<shiro:hasPermission name="postloan:accountCleanAllocate:edit"><td>
			    				<a href="${ctx}/postloan/accountCleanAllocate/form?id=${accountCleanAllocate.id}">修改</a>
								<a href="${ctx}/postloan/accountCleanAllocate/delete?id=${accountCleanAllocate.id}" onclick="return confirmx('确认要删除该借后清收任务分配表吗？', this.href)">删除</a>
							</td></shiro:hasPermission>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="pagination">${page}</div>
		</div>
	</div>
</body>
</html>