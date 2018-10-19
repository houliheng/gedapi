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
	function hkjhClick(){
	$("#tableDataId").toggle(600);
	}
</script>
</head>
<body>
	<sys:message content="${message}" />
	<div class="tableList">
		<h3 onclick="hkjhClick()"  class="tableTitle">还款计划表</h3>
		<div id="tableDataId" style="max-height:200px;overflow:auto;">
			<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered table-condensed table-hover">
				<thead>
					<tr>
						<th>期数</th>
						<th>月还款</th>
						<th>服务费</th>
						<th>账户管理费</th>
					</tr>
				</thead>
					<c:forEach items="${repayPlanList}" var="repayPlan">
						<tr>
							<td id="periodNum" class="title" title="${repayPlan.periodNum}">${repayPlan.periodNum}</td>
							<td id="repayAmount" class="title" title="${repayPlan.repayAmount}">${repayPlan.repayAmount-repayPlan.serviceFee}</td>
							<td id="serviceFee" class="title" title="${repayPlan.serviceFee}">${repayPlan.serviceFee}</td>
							<td id="managementFee" class="title" title="${repayPlan.managementFee}">${repayPlan.managementFee}</td>
						</tr>
					</c:forEach>
			</table>
		</div>
	</div>
</body>
</html>