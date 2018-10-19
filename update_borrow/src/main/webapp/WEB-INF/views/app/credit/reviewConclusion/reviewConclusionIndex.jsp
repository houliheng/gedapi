<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%-- <%@ include file="/WEB-INF/views/app/credit/creditAndLine/creditAndLineScript.jsp"%> --%>
<html>
<head>
<title>复议信息</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		//还款计划
		$.loadDiv("repayPlanList", "${ctx }/credit/repayPlan/toRepayPlan", {
			applyNo : "${actTaskParam.applyNo}",
			taskDefKey : "${checkApprove.taskDefKey}"
		}, "post");
		
		 adjustTextareaLength("remark", "preRemark");
	});
</script>
</head>
<body>
	<!-- 申请信息 -->
	<div id="applyInfoForCreditForm">
		<%@ include file="/WEB-INF/views/app/credit/creditViewBook/applyInfoForCredit/applyInfoForCreditForm.jsp"%>
	</div>
	<!-- 批复信息 -->
	<div id="checkApprove" class="searchInfo">
		<h3 class="searchTitle">批复信息</h3>
		<div class="searchCon">
			<table class="fromTable filter">
				<form:form id="checkApproveForm" modelAttribute="checkApprove" action="${ctx}/credit/checkApprove/save" method="post" class="form-horizontal">
					<form:hidden path="id" />
					<sys:message content="${message}" />
					<pre class="pre-style"  id="preRemark" style="width:820px;"></pre>
					<tr>
						<td width="13%" class="ft_label">产品类型：</td>
						<td class="ft_content">
							<form:input path="approProductTypeName" htmlEscape="false" class="input-medium money" readonly="true" />
						</td>
						<td width="13%" class="ft_label">产品名称：</td>
						<td class="ft_content">
							<form:input path="approProductName" htmlEscape="false" class="input-medium money" readonly="true" />
						</td>
						<td class="ft_label">产品期限(月)：</td>
						<td class="ft_content">
							<form:input path="approPeriodValue" htmlEscape="false" class="input-medium money" value="${fns:getDictLabel(checkApprove.approPeriodValue, 'PRODUCT_PERIOD_VALUE', '')}" readonly="true" />
						</td>
					</tr>
					<tr>
						<td class="ft_label">合同金额(元)：</td>
						<td class="ft_content">
							<form:input path="contractAmount" htmlEscape="false" class="input-medium money" readonly="true" />
						</td>
						<td class="ft_label">放款金额(元)：</td>
						<td class="ft_content">
							<form:input path="loanAmount" htmlEscape="false" class="input-medium money" readonly="true" />
						</td>
						<td class="ft_label">利率(%)：</td>
						<td class="ft_content">
							<form:input path="approYearRate" onblur="this.value=rateMoney(this.value);" onkeyup="rateMax(this)" maxlength="5" htmlEscape="false" class="input-medium" readonly="true" />
						</td>
					</tr>
					<tr>
						<td class="ft_label">服务费率(%)：</td>
						<td class="ft_content">
							<form:input path="serviceFeeRate" onblur="this.value=rateMoney(this.value);" onkeyup="rateMax(this)" maxlength="5" htmlEscape="false" class="input-medium" readonly="true" />
						</td>
						<c:if test="${fns:getDictValue('special_Service_Fee_Rate','special_Service_Fee_Rate','special_Service_Fee_Rate') eq '1'}">
							<td class="ft_label">特殊服务费率(%)：</td>
							<td class="ft_content">
								<form:input path="specialServiceFeeRate" onblur="this.value=rateMoney(this.value);" onkeyup="rateMax(this)" maxlength="5" htmlEscape="false" class="input-medium" readonly="true" />
							</td>
						</c:if>
						<td class="ft_label">服务费收取方式：</td>
						<td class="ft_content">
							<input id="serviceFeeType" name="serviceFeeType" type="text" value="${fns:getDictLabel(checkApprove.serviceFeeType,'SERVICE_FEE_TYPE','') }" htmlEscape="false" class="input-medium"
								readonly="true" />
						</td>
					</tr>
					<tr>
						<td class="ft_label">服务费：</td>
						<td class="ft_content">
							<form:input path="serviceFee" htmlEscape="false" class="input-medium money" readonly="true" />
						</td>
						<c:if test="${fns:getDictValue('special_Service_Fee','special_Service_Fee','special_Service_Fee') eq '1'}">
							<td class="ft_label">特殊服务费：</td>
							<td class="ft_content">
								<form:input path="specialServiceFee" htmlEscape="false" class="input-medium money" readonly="true" />
							</td>
						</c:if>
						<td class="ft_label">服务费总计：</td>
						<td class="ft_content">
							<form:input path="allServiceFee" htmlEscape="false" class="input-medium money" readonly="true" />
						</td>
					</tr>
					<tr>
						<td class="ft_label">还款方式：</td>
						<td class="ft_content">
							<input id="approLoanRepayType" name="approLoanRepayType" type="text" value="${loanRepayTypeName}" htmlEscape="false" class="input-medium" readonly="true" />
						</td>
						<td class="ft_label">保证金率(%)：</td>
						<td class="ft_content">
							<form:input path="marginRate" onblur="this.value=rateMoney(this.value);" onkeyup="rateMax(this)" maxlength="5" htmlEscape="false" class="input-medium" readonly="true" />
						</td>
						<td class="ft_label">保证金：</td>
						<td class="ft_content">
							<form:input path="marginAmount" htmlEscape="false" class="input-medium money" readonly="true" />
						</td>
					</tr>
					<tr>
						<td class="ft_label">外访费：</td>
						<td class="ft_content">
							<form:input path="checkFee" htmlEscape="false" class="input-medium money" readonly="true" />
						</td>
						<td class="ft_label">借款模式：</td>
						<td class="ft_content">
							<input id="approLoanRepayType" name="approLoanRepayType" type="text" value="${fns:getDictLabel(checkApprove.loanModel,'LOAN_MODEL','') }" htmlEscape="false" class="input-medium" readonly="true" />
						</td>
						<td class="ft_label">是否加急：</td>
						<td class="ft_content">
							<form:select path="isUrgent" class="input-medium required" disabled="true">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
						</td>
					</tr>
					<tr>
						<td class="ft_label">备注：</td>
						<td class="ft_content" colspan="6">
							<form:textarea path="remark" htmlEscape="false" rows="4" maxlength="1000" class="textarea-style required"  style="width:820px;" placeholder="此项填写为小黄表备注项" onkeyup="adjustTextareaLength('remark','preRemark')"/>
							<font color="red">*</font>
						</td>
					</tr>
			</table>
			</form:form>
		</div>
	</div>
	<!-- 还款计划 -->
	<div id="repayPlanList"></div>
	<!-- 审批结论 -->
	<!-- 待办时，显示复议的审批结论 -->
	<c:if test="${actTaskParam.status == 0 }">
		<div id="reviewConclusionForm">
			<%@ include file="/WEB-INF/views/app/credit/processSuggestionInfo/reviewConclusionForm.jsp"%>
		</div>
	</c:if>
</body>
</html>
