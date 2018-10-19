<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>合同带催收统计管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {

	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}

	function allotOperator(id) {
		top.$.jBox.confirm("您是否选择该人员分配任务？", '系统提示', function(v, h, f) {
			if (v == 'ok') {
				loading();
				$.ajax({
				url : "${ctx}/postloan/debtCollection/allotOperator",
				data : {
				id : '${debtId}',
				checkedOperatorId : id
				},
				async : false,
				dataType : "json",
				success : function(data) {
					closeTip();
					if (data.status == "1") {
						alertx(data.message, function() {
							closeAndReloadPostLoan();
						});
					} else {
						alertx(data.message);
					}
				}
				});
			}
		})

	}

	//重置按钮
	function resetForm() {
		$("#loginName").val("");
		$("#operatorName").val("");
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="debtCollectionOperator" action="${ctx}/postloan/debtCollection/form" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<input id="debtId" name="debtId" type="hidden" value="${debtId}" />
					<div class="filter">
						<table class="fromTable">
							<tr>
								<td class="ft_label">用户登陆名：</td>
								<td class="ft_content">
									<form:input path="loginName" htmlEscape="false" class="input-medium" />
								</td>
								<td class="ft_label">用户姓名：</td>
								<td class="ft_content">
									<form:input path="operatorName" htmlEscape="false" class="input-medium" />
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />
							&nbsp;
							<input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="resetForm()" />
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div id="tableDataId">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th width="3">序号</th>
							<th width="17">登录名</th>
							<th width="20">姓名</th>
							<th width="20">归属部门</th>
							<th width="20">归属机构</th>
							<shiro:hasPermission name="postloan:debtCollection:edit">
								<th width="20">操作</th>
							</shiro:hasPermission>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="debt" varStatus="debtList">
							<tr>
								<td class="title" title="序号">${debtList.count}</td>
								<td class="title" title="${debt.loginName}">${debt.loginName}</td>
								<td class="title" title="${debt.operatorName}">${debt.operatorName}</td>
								<td class="title" title="${debt.operatorOffice}">${debt.operatorOffice}</td>
								<td class="title" title="${debt.operatorCompany}">${debt.operatorCompany}</td>
								<shiro:hasPermission name="postloan:debtCollection:edit">
									<td>
										<a href="#" onclick="allotOperator('${debt.id}');">分配</a>
									</td>
								</shiro:hasPermission>
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