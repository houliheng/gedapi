<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>合同信息管理</title>
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
		<div class="tableList">
			<h3 class="tableTitle">关联匹配信息列表</h3>
			<div id="tableDataId">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th>对象类型</th>
							<th>对象名称</th>
							<th>关联进件编号</th>
							<th>关联进件客户名称</th>
							<th>关联对象类型</th>
							<shiro:hasPermission name="credit:contract:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					</thead>
					<tbody>
					<%-- <c:forEach items="${page.list}" var="contract">
						<tr>
							<td id="updateDate" class="title" title="${contract.updateDate}"><a href="${ctx}/credit/contract/form?id=${contract.id}">
								<fmt:formatDate value="${contract.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</a></td>
							<shiro:hasPermission name="credit:contract:edit"><td>
			    				<a href="${ctx}/credit/contract/form?id=${contract.id}">修改</a>
								<a href="${ctx}/credit/contract/delete?id=${contract.id}" onclick="return confirmx('确认要删除该合同信息吗？', this.href)">删除</a>
							</td></shiro:hasPermission>
						</tr>
					</c:forEach>
					</tbody> --%>
				</table>
			</div>
		</div>
	</div>
</body>
</html>