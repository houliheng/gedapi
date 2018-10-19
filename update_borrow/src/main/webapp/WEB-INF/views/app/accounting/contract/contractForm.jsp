<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>合同信息管理</title>
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
<div class="searchInfo">
	<h3 class="searchTitle">关联匹配详情信息</h3>
	 <div class="searchCon">
	<form:form id="inputForm" modelAttribute="contract" class="form-horizontal">
		<form:hidden path="id" />
		<sys:message content="${message}" />
		
		<table class="fromTable filter">
			<tr>
				<td class="ft_label">客户名称：</td>
				<td class="ft_content">
					<form:input path="custName" htmlEscape="false" maxlength="200" class="input-medium " readonly="true" />
				</td>
				<td class="ft_label">证件类型：</td>
				<td class="ft_content">
					<form:select path="idType" class="input-medium " disabled="true">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('CUSTOMER_P_ID_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
				</td>
				<td class="ft_label">证件号：</td>
				<td class="ft_content">
					<form:input path="idNum" htmlEscape="false" maxlength="200" class="input-medium card" readonly="true" />
				</td>
			</tr>
			<tr>
				<td class="ft_label">产品类型：</td>
				<td class="ft_content">
					<form:select path="approProductTypeId" class="input-medium " disabled="true">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('PRODUCT_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
				</td>
				<td class="ft_label">产品名称：</td>
				<td class="ft_content">
					<form:input path="approProductTypeName" htmlEscape="false" maxlength="30" class="input-medium " readonly="true" />
				</td>
			</tr>
			<tr>
				<td class="ft_label">合同编号：</td>
				<td class="ft_content">
					<form:input path="contractNo" htmlEscape="false" maxlength="50" class="input-medium " readonly="true" />
				</td>
				<td class="ft_label">合同金额(元)：</td>
				<td class="ft_content">
					<form:input path="contractAmount" htmlEscape="false" class="input-medium " readonly="true" />
				</td>
				<td class="ft_label">放款金额(元)：</td>
				<td class="ft_content">
					<form:input path="loanAmount" htmlEscape="false" class="input-medium " readonly="true" />
				</td>
			</tr>
			<tr>
				<td class="ft_label">服务费率(%)：</td>
				<td class="ft_content">
					<form:input path="serviceFeeRate" onblur="this.value=rateMoney(this.value);" onkeyup="rateMax(this)" htmlEscape="false" class="input-medium " readonly="true" />
				</td>
				<td class="ft_label">出借人收益率(%)：</td>
				<td class="ft_content">
					<form:input path="serviceFeeType" onblur="this.value=rateMoney(this.value);" onkeyup="rateMax(this)" htmlEscape="false" class="input-medium " readonly="true" />
				</td>
				<td class="ft_label">期限：</td>
				<td class="ft_content">
					<form:input path="approPeriodValue" htmlEscape="false" maxlength="4" class="input-medium " readonly="true" />
				</td>
			</tr>
			<tr>
				<td class="ft_label">服务费：</td>
				<td class="ft_content">
					<form:input path="serviceFee" htmlEscape="false" class="input-medium " readonly="true" />
				</td>
				<td class="ft_label">服务费收取方式：</td>
				<td class="ft_content">
					<form:select path="serviceFeeType" class="input-medium " disabled="true">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('SERVICE_FEE_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
				</td>
			</tr>
			<tr>
				<td class="ft_label">还款方式：</td>
				<td class="ft_content">
					<form:select path="serviceFeeType" class="input-medium " disabled="true">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('LOAN_REPAY_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
				</td>
				<td class="ft_label">保证金率（%）：</td>
				<td class="ft_content">
					<form:input path="marginRate" onblur="this.value=rateMoney(this.value);" onkeyup="rateMax(this)" htmlEscape="false" class="input-medium " readonly="true" />
				</td>
				<td class="ft_label">保证金：</td>
				<td class="ft_content">
					<form:input path="marginAmount" htmlEscape="false" class="input-medium " readonly="true" />
				</td>
			</tr>
			<tr>
				<td class="ft_label">贷款模式：</td>
				<td class="ft_content">
					<form:select path="loanModel" class="input-medium " disabled="true">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('LOAN_MODEL')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
				</td>
				<!-- 需要从ACC_STA_CONTRACT_STATUS表中来查 -->
				<td class="ft_label">逾期期数：</td>
				<td class="ft_content">
					<form:input path="staContractStatus.taTimes" htmlEscape="false" class="input-medium " readonly="true" />
				</td>
				<td class="ft_label">当前逾期金额(元)：</td>
				<td class="ft_content">
					<form:input path="staContractStatus.currOverdueAmount" htmlEscape="false" class="input-medium " readonly="true" />
				</td>
			</tr>
			<tr>
				<td class="ft_label">还款状态：</td>
				<td class="ft_content">
					<form:select path="staContractStatus.repayContractStatus" class="input-medium " disabled="true">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('REPAY_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
				</td>
				<td class="ft_label">登记员：</td>
				<td class="ft_content">
					<form:input path="operateName" htmlEscape="false" maxlength="20" class="input-medium " readonly="true" />
				</td>
				<td class="ft_label">登记门店：</td>
				<td class="ft_content">
					<form:input path="operateOrgName" htmlEscape="false" maxlength="300" class="input-medium " readonly="true" />
				</td>
			</tr>
		</table>
		<div class="searchButton">
			<input type="button" class="btn btn-primary" value="返回" onclick="history.go(-1)" />
		</div>
	</form:form>
	</div>
	</div>
	<h3 class="infoListTitle">还款明细列表</h3>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="20px">序号</th>
				<th>期数</th>
				<th>状态</th>
				<th>还款日期</th>
				<th>应还金额(元)</th>
				<th>服务费</th>
				<th>账户管理费</th>
				<th>利息</th>
				<th>本金</th>
				<th>违约金</th>
				<th>罚息</th>
				<th>逾期天数</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${repayPlanList}" var="repayPlan" varStatus="i">
				<tr>
					<td id="num" class="title" title="序号">${i.index+1}</td>
					<td id="periodNum" class="title" title="${repayPlan.periodNum}">${repayPlan.periodNum}</td>
					<td id="repayPeriodStatus" class="title" title="${repayPlan.staRepayPlanStatus.repayPeriodStatus}">${fns:getDictLabel(repayPlan.staRepayPlanStatus.repayPeriodStatus, 'PERIOD_STATE', '')}</td>
					<td id="deductDate" class="title" title="${repayPlan.deductDate}">${repayPlan.deductDate}</td>
					<td id="repayAmount" class="title" title="${repayPlan.repayAmount}">${repayPlan.repayAmount}</td>
					<td id="factServiceFee" class="title" title="${repayPlan.staRepayPlanStatus.factServiceFee}">${repayPlan.staRepayPlanStatus.factServiceFee}</td>
					<td id="factMangementFee" class="title" title="${repayPlan.staRepayPlanStatus.factMangementFee}">${repayPlan.staRepayPlanStatus.factMangementFee}</td>
					<td id="factCapitalAmount" class="title" title="${repayPlan.staRepayPlanStatus.factCapitalAmount}">${repayPlan.staRepayPlanStatus.factCapitalAmount}</td>
					<td id="factInterestAmount" class="title" title="${repayPlan.staRepayPlanStatus.factInterestAmount}">${repayPlan.staRepayPlanStatus.factInterestAmount}</td>
					<td id="factPenaltyAmount" class="title" title="${repayPlan.staRepayPlanStatus.factPenaltyAmount}">${repayPlan.staRepayPlanStatus.factPenaltyAmount}</td>
					<td id="factFineAmount" class="title" title="${repayPlan.staRepayPlanStatus.factFineAmount}">${repayPlan.staRepayPlanStatus.factFineAmount}</td>
					<td id="overdueDays" class="title" title="${repayPlan.staOverdueStatus.overdueDays}">${repayPlan.staOverdueStatus.overdueDays}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>