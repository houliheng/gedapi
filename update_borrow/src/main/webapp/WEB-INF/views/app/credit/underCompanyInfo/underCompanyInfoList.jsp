<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>线下借款-企业信息管理</title>
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
			<li class="active"><a href="${ctx}/credit/underCompanyInfo/">线下借款-企业信息列表</a></li>
			<shiro:hasPermission name="credit:underCompanyInfo:edit"><li><a href="${ctx}/credit/underCompanyInfo/form">线下借款-企业信息添加</a></li></shiro:hasPermission>
		</ul>
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="underCompanyInfo" action="${ctx}/credit/underCompanyInfo/" method="post" class="breadcrumb form-search">
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
							<th>更新时间</th>
							<shiro:hasPermission name="credit:underCompanyInfo:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${page.list}" var="underCompanyInfo">
						<tr>
							<td id="updateDate" class="title" title="${underCompanyInfo.updateDate}"><a href="${ctx}/credit/underCompanyInfo/form?id=${underCompanyInfo.id}">
								<fmt:formatDate value="${underCompanyInfo.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</a></td>
							<shiro:hasPermission name="credit:underCompanyInfo:edit"><td>
			    				<a href="${ctx}/credit/underCompanyInfo/form?id=${underCompanyInfo.id}">修改</a>
								<a href="${ctx}/credit/underCompanyInfo/delete?id=${underCompanyInfo.id}" onclick="return confirmx('确认要删除该线下借款-企业信息吗？', this.href)">删除</a>
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