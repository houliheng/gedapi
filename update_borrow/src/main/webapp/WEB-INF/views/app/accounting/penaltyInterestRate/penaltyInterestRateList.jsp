<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>罚息利率管理</title>
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

	function resetForm() {
		$("#startDate").val('');
		$("#endDate").val('');
	}
</script>
</head>
<body>
	<div class="wrapper">
		<ul class="nav nav-tabs">
			<li class="active">
				<a href="${ctx}/accounting/penaltyInterestRate/">罚息利率列表</a>
			</li>
			<shiro:hasPermission name="accounting:penaltyInterestRate:edit">
				<li>
					<a href="${ctx}/accounting/penaltyInterestRate/form">罚息利率添加</a>
				</li>
			</shiro:hasPermission>
		</ul>
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="penaltyInterestRate" action="${ctx}/accounting/penaltyInterestRate/" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<div class="filter">
						<ul class="ul-form">
							<li>
								<label>开始时间：</label>
								<input name="startDate" id="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" value="<fmt:formatDate value="${penaltyInterestRate.startDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" />
							</li>
							<li>
								<label>结束时间：</label>
								<input name="endDate" id="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" value="<fmt:formatDate value="${penaltyInterestRate.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" />
							</li>
						</ul>
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
							<th>参数名称</th>
							<th>参数值</th>
							<th>备注</th>
							<th>开始时间</th>
							<th>结束时间</th>
							<shiro:hasPermission name="accounting:penaltyInterestRate:edit">
								<th>操作</th>
							</shiro:hasPermission>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="penaltyInterestRate">
							<tr>
								<td id="paramName" class="title" title="${penaltyInterestRate.paramName}">
									<a href="${ctx}/accounting/penaltyInterestRate/form?readOnly=true&id=${penaltyInterestRate.id}"> ${penaltyInterestRate.paramName} </a>
								</td>
								<td id="paramValue" class="title" title="${penaltyInterestRate.paramValue}">${penaltyInterestRate.paramValue}</td>
								<td id="description" class="title" title="${penaltyInterestRate.description}">${penaltyInterestRate.description}</td>
								<td id="startDate" class="title" title="<fmt:formatDate value='${penaltyInterestRate.startDate}' pattern='yyyy-MM-dd HH:mm:ss'/>">
									<fmt:formatDate value="${penaltyInterestRate.startDate}" pattern="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td id="endDate" class="title" title="<fmt:formatDate value='${penaltyInterestRate.endDate}' pattern='yyyy-MM-dd HH:mm:ss'/>">
									<fmt:formatDate value="${penaltyInterestRate.endDate}" pattern="yyyy-MM-dd HH:mm:ss" />
								</td>
								<shiro:hasPermission name="accounting:penaltyInterestRate:edit">
									<td>
										<a href="${ctx}/accounting/penaltyInterestRate/form?readOnly=false&id=${penaltyInterestRate.id}">修改</a>
										<a href="${ctx}/accounting/penaltyInterestRate/delete?id=${penaltyInterestRate.id}" onclick="return confirmx('确认要删除该罚息利率吗？', this.href)">删除</a>
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