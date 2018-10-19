<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>还款计划管理</title>
<script type="text/javascript">
	$(document).ready(function() {

	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
	
	function hkjhbClick(){
	$("#tableDataId").toggle(600);
	}
</script>
</head>
<body>
	<sys:message content="${message}" />
	<div class="tableList">
		<h3 onclick="hkjhbClick()" class="tableTitle">还款计划表</h3>
		<div  id="tableDataId" >
		<!-- style="max-height:200px;overflow: auto;" -->
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>期数</th>
						<th>还款日期</th>
						<th>月还款</th>
						<th>服务费</th>
						<th>账户管理费</th>
						<th>本息合计</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${repayPlanList}" var="repayPlan">
						<tr>
							<td id="periodNum" class="title" title="${repayPlan.periodNum}">${repayPlan.periodNum}</td>
							<td id="deductDate" class="title" title="${repayPlan.deductDate}">
								<fmt:formatDate value="${repayPlan.deductDate}" pattern="yyyy-MM-dd"/>
							</td>
							<td id="repayAmount" class="title" title="${repayPlan.repayAmount}">
								<fmt:formatNumber value="${repayPlan.repayAmount-repayPlan.serviceFee}" pattern="###,##0.00"/>
							</td>
							<td id="serviceFee" class="title" title="${repayPlan.serviceFee}">
								<fmt:formatNumber value="${repayPlan.serviceFee}" pattern="###,##0.00"/>
							</td>
							<td id="managementFee" class="title" title="${repayPlan.managementFee}">
								<fmt:formatNumber value="${repayPlan.managementFee}" pattern="###,##0.00"/>
							</td>
							<td id="amount" class="title" title="${repayPlan.capitalAmount+repayPlan.interestAmount}">
								<fmt:formatNumber value="${repayPlan.capitalAmount+repayPlan.interestAmount}" pattern="###,##0.00"/>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>