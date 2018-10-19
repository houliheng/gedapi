<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>合同还款明细查询管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#inputForm").validate({
		submitHandler : function(form) {
			loading('正在提交，请稍等...');
			form.submit();
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			$("#messageBox").text("输入有误，请先更正。");
			if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
				error.appendTo(element.parent().parent());
			} else {
				error.insertAfter(element);
			}
		}
		});
	});
</script>
</head>
<body>
	<h3 class="infoListTitle">还款明细列表</h3>
	<div id="tableDataId">
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>期数</th>
					<th>状态</th>
					<th>还款日期</th>
					<th>应还金额</th>
					<th>服务费</th>
					<th>账户管理费</th>
					<th>利息</th>
					<th>本金</th>
					<th>违约金</th>
					<th>罚息</th>
					<th>实还服务费</th>
					<th>实还账户管理费</th>
					<th>实还利息</th>
					<th>实还本金</th>
					<th>实还违约金</th>
					<th>实还罚息</th>
					<th>违约金减免</th>
					<th>罚息减免</th>
					<th>当期本息结清日期</th>
					<th>逾期本金</th>
					<th>逾期利息</th>
					<th>逾期天数</th>
					<th>当期总结清日期</th>
					<shiro:hasPermission name="accounting:staContractStatus:edit">
						<th>操作</th>
					</shiro:hasPermission>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${pageCar.list}" var="mortgageCarInfo">
					<tr>
						<td id="propertyRight" class="title" title="${mortgageCarInfo.periodNum}">
							<a href="${ctx}/credit/mortgageCarInfo/form?id=${mortgageCarInfo.id}"> ${mortgageCarInfo.propertyRight} </a>
						</td>
						<td id="repayPeriodStatus" class="title" title="${mortgageCarInfo.repayPeriodStatus}">${mortgageCarInfo.repayPeriodStatus}</td>
						<td id="deducDate" class="title" title="${mortgageCarInfo.deducDate}">
							<fmt:formatDate value="${mortgageCarInfo.deducDate}" pattern="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td id="repayAmount" class="title" title="${mortgageCarInfo.repayAmount}">${mortgageCarInfo.repayAmount}</td>
						<td id="serviceFee" class="title" title="${mortgageCarInfo.serviceFee}">${mortgageCarInfo.serviceFee}</td>
						<td id="mangementFee" class="title" title="${mortgageCarInfo.mangementFee}">${mortgageCarInfo.mangementFee}</td>
						<td id="interestAmount" class="title" title="${mortgageCarInfo.interestAmount}">${mortgageCarInfo.interestAmount}</td>
						<td id="capitalAmount" class="title" title="${mortgageCarInfo.capitalAmount}">${mortgageCarInfo.capitalAmount}</td>
						<td id="penaltyAmount" class="title" title="${mortgageCarInfo.penaltyAmount}">${mortgageCarInfo.penaltyAmount}</td>
						<td id="fineAmount" class="title" title="${mortgageCarInfo.fineAmount}">${mortgageCarInfo.fineAmount}</td>
						<td id="factServiceFee" class="title" title="${mortgageCarInfo."factServiceFee"}">${mortgageCarInfo."factServiceFee"}</td>
						<td id="factMangementFee " class="title" title="${mortgageCarInfo."factMangementFee"}">${mortgageCarInfo."factMangementFee"}</td>
						<td id="factInterestAmount" class="title" title="${mortgageCarInfo."factInterestAmount"}">${mortgageCarInfo."factInterestAmount"}</td>
						<td id="factOverdueCapitalAmount" class="title" title="${mortgageCarInfo."factOverdueCapitalAmount"}">${mortgageCarInfo."factOverdueCapitalAmount"}</td>
						<td id="factPenaltyAmount " class="title" title="${mortgageCarInfo."factPenaltyAmount"}">${mortgageCarInfo."factPenaltyAmount"}</td>
						<td id="factFineAmount" class="title" title="${mortgageCarInfo."factFineAmount"}">${mortgageCarInfo."factFineAmount"}</td>
						<td id="penaltyExemptAmount" class="title" title="${mortgageCarInfo."penaltyExemptAmount"}">${mortgageCarInfo."penaltyExemptAmount"}</td>
						<td id="fineExemptAmount" class="title" title="${mortgageCarInfo."fineExemptAmount"}">${mortgageCarInfo."fineExemptAmount"}</td>
						<td id="" class="title" title="${mortgageCarInfo.""}">${mortgageCarInfo.""}</td>
						<td id="oweCapitalAmount" class="title" title="${mortgageCarInfo."oweCapitalAmount"}">${mortgageCarInfo."oweCapitalAmount"}</td>
						<td id="oweInterestAmount" class="title" title="${mortgageCarInfo."oweInterestAmount"}">${mortgageCarInfo."oweInterestAmount"}</td>
						<td id="overdueDays" class="title" title="${mortgageCarInfo."overdueDays"}">${mortgageCarInfo."overdueDays"}</td>
						<td id="" class="title" title="${mortgageCarInfo.""}">${mortgageCarInfo.""}</td>
						<shiro:hasPermission name="credit:accStaContractStatus:edit">
							<td>
								<a href="${ctx}/credit/checkWeb/form?id=${checkWeb.id}">划扣</a>
							</td>
							<td>
								<a href="${ctx}/credit/checkWeb/form?id=${checkWeb.id}">提前还款</a>
							</td>
							<td>
								<a href="${ctx}/credit/checkWeb/form?id=${checkWeb.id}">减免</a>
							</td>
							<td>
								<a href="${ctx}/credit/checkWeb/form?id=${checkWeb.id}">账务调整</a>
							</td>
							<td>
								<a href="${ctx}/credit/checkWeb/form?id=${checkWeb.id}">退还保证金</a>
							</td>
						</shiro:hasPermission>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>