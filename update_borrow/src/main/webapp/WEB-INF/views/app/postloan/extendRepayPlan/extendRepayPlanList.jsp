<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript">
	function queryExtendRepayPlan(id) {
		openJBox('qyeryExtendRepayPlanBox', "${ctx}/postloan/extendRepayPlan/form", "查看还款计划", $(window).width() - 200, $(window).height() - 100, {
		id : id,
		contractNo : '${contractNo}',
		readonly : "readonly"
		});
	}
	function extendRepayPlan() {
		$("#extendRepayPlanId").toggle(600);
	}
</script>
</head>
<body>
	<div class="tableList">
		<h3 onclick="extendRepayPlan()" class="tableTitle">展期还款计划</h3>
		<div id="extendRepayPlanId" class="ribbon filter">
			<div id="tableDataId">
				<table id="contentTable" width="100%" class="table table-striped table-bordered table-condensed table-hover">
					<thead>
						<tr>
							<th width="11%">期数</th>
							<th width="11%">应还日期</th>
							<th width="11%">服务费</th>
							<th width="11%">账户管理费</th>
							<th width="11%">利息</th>
							<th width="11%">本金</th>
							<th width="11%">违约金</th>
							<th width="11%">当期应还总计</th>
							<th width="12%">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="extendRepayPlan">
							<tr>
								<td>${extendRepayPlan.extendPeriodNum}</td>
								<td>
									<fmt:formatDate value='${extendRepayPlan.extendRepayDate}' pattern='yyyy-MM-dd' />
								</td>
								<td>${extendRepayPlan.extendServiceFee}</td>
								<td>${extendRepayPlan.extendManagementFee}</td>
								<td>${extendRepayPlan.extendInterest}</td>
								<td>${extendRepayPlan.extendCapital}</td>
								<td>${extendRepayPlan.extendPenalty}</td>
								<td>${extendRepayPlan.extendRepayAll}</td>
								<td>
									<a href="#" onclick="queryExtendRepayPlan('${extendRepayPlan.id}')">详情</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>