<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>抵押物处置管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {

	});
	function page(n, s) {
		loading();
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
	//重置
	function resetForm() {
		$("#contractNo").val('');
		$("#custName").val('');
		page();//查询
	}

	function toTaskDown(contractNo, periodNum, companyId) {
		var newUrl = "${ctx}/postloan/collateralDisposal/toTaskDown";
		openJBox('toTaskDownBox', newUrl, "任务下发", $(window).width() - 300, $(window).height() - 200, {
		contractNo : contractNo,
		periodNum : periodNum,
		companyId : companyId
		});
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="collateralDisposal" action="${ctx}/postloan/collateralDisposal/list" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<div class="filter">
						<table class="searchTable">
							<tr>
								<td class="ft_label">客户名称：</td>
								<td class="ft_content">
									<form:input path="custName" htmlEscape="false" class="input-medium" />
								</td>
								<td class="ft_label">合同编号：</td>
								<td class="ft_content">
									<form:input path="contractNo" htmlEscape="false" maxlength="32" class="input-medium" />
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />
							&nbsp; &nbsp;
							<input id="btnReset" class="btn btn-primary" type="button" onclick="resetForm();" value="重置" />
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
							<th>客户名称</th>
							<th>联系方式</th>
							<th>合同编号</th>
							<th>合同金额</th>
							<th>期数</th>
							<th>逾期金额</th>
							<th>逾期天数</th>
							<th>大区</th>
							<th>区域</th>
							<th>分公司</th>
							<th>登记人</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="TaskTempEntity">
							<tr>
								<td id="custName" class="title" title="${TaskTempEntity.custName}">${TaskTempEntity.custName}</td>
								<td id="mobileNum" class="title" title="${TaskTempEntity.mobileNum}">${TaskTempEntity.mobileNum}</td>
								<td id="contractNo" class="title" title="${TaskTempEntity.contractNo}">${TaskTempEntity.contractNo}</td>
								<td id="contractAmount" class="title" title="${TaskTempEntity.contractAmount}">${TaskTempEntity.contractAmount}</td>
								<td id="periodNum" class="title" title="${TaskTempEntity.periodNum}">${TaskTempEntity.periodNum}</td>
								<td id="overdueAmount" class="title" title="${TaskTempEntity.overdueAmount}">${TaskTempEntity.overdueAmount}</td>
								<td id="overdueDays" class="title" title="${TaskTempEntity.overdueDays}">${TaskTempEntity.overdueDays}</td>
								<td id="orgLevel2" class="title" title="${TaskTempEntity.orgLevel2.name}">${TaskTempEntity.orgLevel2.name}</td>
								<td id="orgLevel3" class="title" title="${TaskTempEntity.orgLevel3.name}">${TaskTempEntity.orgLevel3.name}</td>
								<td id="orgLevel4" class="title" title="${TaskTempEntity.orgLevel4.name}">${TaskTempEntity.orgLevel4.name}</td>
								<td id="registerName" class="title" title="${TaskTempEntity.registerName}">${TaskTempEntity.registerName}</td>
								<td>
									<a href="#" onclick="toTaskDown('${TaskTempEntity.contractNo}','${TaskTempEntity.periodNum}','${TaskTempEntity.orgLevel2.id}');">任务下发</a>
								</td>
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