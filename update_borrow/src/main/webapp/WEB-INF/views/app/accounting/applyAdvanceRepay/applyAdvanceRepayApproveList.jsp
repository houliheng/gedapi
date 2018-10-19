<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>提前还款管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#inputForm").validate({
		submitHandler : saveForm,
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});
	});

	function calculateAllDeductAmountApprove() {
		var approveDeductFee = $("#approveDeductFee").val();
		var approveRemainDeductAmount = $("#approveRemainDeductAmount").val();
		if (checkIsNull(approveDeductFee) && !(checkIsNull(approveRemainDeductAmount))) {
			var approveAllDeductAmount = Number(approveRemainDeductAmount).toFixed(2);
		} else if (checkIsNull(approveRemainDeductAmount) && !(checkIsNull(approveDeductFee))) {
			var approveAllDeductAmount = Number(approveDeductFee).toFixed(2);
		} else if (checkIsNull(approveRemainDeductAmount) && checkIsNull(approveDeductFee)) {
			var approveAllDeductAmount = 0;
		} else {
			var approveAllDeductAmount = (Number(approveRemainDeductAmount) + Number(approveDeductFee)).toFixed(2);
		}
		$("#approveAllDeductAmount").val(approveAllDeductAmount);
	}

	function saveForm() {
		loading();
		top.$.jBox.tip.mess = null;
		var formJson = $("#inputForm").serializeJson();
		$.post("${ctx}/accounting/applyAdvanceRepay/saveSuggetion", formJson, function(data) {
			if (data) {
				closeTip();
				if (data.status == 1) {//保存成功
					alertx(data.message, function() {
						closeAndReloadACC();
					});
				} else {
					alertx(data.message);
				}
			}
		});
	}
</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="applyAdvanceRepay" action="" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<input type="hidden" name="applyNo" value="${actTaskParam.applyNo}" />
		<input type="hidden" name="taskId" value="${actTaskParam.taskId}" />
		<input type="hidden" name="taskDefKey" value="${actTaskParam.taskDefKey}" />
		<input type="hidden" name="procDefId" value="${actTaskParam.procDefId}" />
		<input type="hidden" name="status" value="${actTaskParam.status}" />
		<input type="hidden" name="procInstId" value="${actTaskParam.procInstId}" />
		<input type="hidden" name="contractNo" value="${applyAdvanceRepay.contractNo}" />
		<input type="hidden" name="periodNum" value="${applyAdvanceRepay.periodNum}" />
		<input type="hidden" name="saveFlag" />
		<sys:message content="${message}" />
		<div id="applyId">
			<h3 class="infoListTitle">提前还款申请信息</h3>
			<table class="fromTable filter">
				<tr>
					<td class="ft_label">提前还款费用收取方式：</td>
					<td class="ft_content">
						<form:select path="advanceDeductType" class="input-xlarge required" disabled="true" style="margin-bottom:6px">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('BREAK_REPAY_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<form:hidden path="advanceDeductTypeName" />
					</td>
				</tr>
				<tr>
					<td class="ft_label">提前还款费用：</td>
					<td class="ft_content">
						<form:input path="advanceDeductFee" htmlEscape="false" readonly="true" class="input-xlarge required" />
					</td>
					<td class="ft_label">申请减免金额：</td>
					<td class="ft_content">
						<form:input path="advanceDeductFeeExempt" htmlEscape="false" readonly="true" class="input-xlarge required" />
					</td>
				</tr>
				<tr>
					<td class="ft_label">剩余应还总额：</td>
					<td class="ft_content">
						<form:input path="remainDeductAmount" htmlEscape="false" readonly="true" value="${remainRepayAmount}" class="input-xlarge required" />
					</td>
					<td class="ft_label">申请减免金额：</td>
					<td class="ft_content">
						<form:input path="remainDeductAmountExempt" htmlEscape="false" readonly="true" class="input-xlarge required" />
					</td>
				</tr>
				<tr>
					<td class="ft_label">全额应还总金额：</td>
					<td class="ft_content">
						<form:input path="allDeductAmount" htmlEscape="false" readonly="true" class="input-xlarge required" />
					</td>
					<td class="ft_label">申请全额应还总额：</td>
					<td class="ft_content">
						<form:input path="allDeductAmountApply" htmlEscape="false" readonly="true" class="input-xlarge required" />
					</td>
				</tr>
				<tr>
					<td class="ft_label">申请说明：</td>
					<td class="ft_content" colspan="3" style="width: 91%;">
						<form:textarea path="advanceDescription" htmlEscape="false" readonly="true" rows="5" cols="3" maxlength="1000" class="area-length" style="width: 90%;" />
					</td>
				</tr>
			</table>
		</div>
		<div id="approveId">
			<h3 class="infoListTitle">提前还款批复信息</h3>
			<table class="fromTable filter">
				<tr>
					<td class="ft_label">审批提前还款费用：</td>
					<td class="ft_content">
						<form:input path="approveDeductFee" htmlEscape="false" onblur="calculateAllDeductAmountApprove();" class="input-xlarge number required" />
						<font style="color: red">*</font>
					</td>
					<td class="ft_label">审批剩余应还总额：</td>
					<td class="ft_content">
						<form:input path="approveRemainDeductAmount" htmlEscape="false" onblur="calculateAllDeductAmountApprove();" class="input-xlarge number required" />
						<font style="color: red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">审批全额应还总额：</td>
					<td class="ft_content">
						<form:input path="approveAllDeductAmount" htmlEscape="false" readonly="true" class="input-xlarge number required" />
						<font style="color: red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">审批意见：</td>
					<td class="ft_content">
						<input type="radio" name="passFlag" value="yes" class="required">
						<span>
							<font>通过</font>
						</span>
						<input type="radio" name="passFlag" value="no" class="required">
						<span>
							<font>拒绝</font>
						</span>
						<font style="color: red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">批复说明：</td>
					<td class="ft_content" colspan="3" style="width: 91%;">
						<form:textarea path="approveDescription" htmlEscape="false" rows="5" cols="3" maxlength="1000" class="area-length required" style="width: 90%;" />
						<font style="color: red">*</font>
					</td>
				</tr>
			</table>
		</div>
		<div class="searchButton">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="提交" />
			&nbsp;
			<input id="btnCancel" class="btn btn-primary" type="button" value="返回" onclick="closeJBox();" />
		</div>
	</form:form>
</body>
</html>