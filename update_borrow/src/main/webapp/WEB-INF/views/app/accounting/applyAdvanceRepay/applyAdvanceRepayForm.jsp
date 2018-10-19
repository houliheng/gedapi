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

	function getRepayAmount() {
		var remain = $("#remainDeductAmount").val();
		var type = $("#advanceDeductType").val();
		$.ajax({
		url : "${ctx}/accounting/applyAdvanceRepay/getRepayAmount",
		type:"POST",
		data : {
		contractNo:'${applyAdvanceRepay.contractNo}',
		advanceDeductType : type,
		periodNum : '${applyAdvanceRepay.periodNum}',
		remain : remain
		},
		async : false,
		dataType : "json",
		success : function(data) {
			$("#advanceDeductFee").empty();
			$("#allDeductAmount").empty();
			$("#advanceDeductFee").val(data.num);
			$("#allDeductAmount").val(data.sum);
		}
		});
	}
	function setBreakRepayTypeName() {
		var advanceDeductTypeName = $("#advanceDeductType").find("option:selected").text();
		$("#advanceDeductTypeName").val(advanceDeductTypeName);
	}
	function saveForm() {
		loading();
		top.$.jBox.tip.mess = null;
		var formJson = $("#inputForm").serializeJson();
		$.post("${ctx}/accounting/applyAdvanceRepay/save", formJson, function(data) {
			if (data) {
				closeTip();
				if (data.status == 1) {//保存成功
					alertx(data.message, function() {
						closeJBox();
					});
				} else {
					alertx(data.message);
				}
			}
		});
	}
	function calculateAllDeductAmountApply() {
		var remainDeductAmountExempt = $("#remainDeductAmountExempt").val();
		var advanceDeductFeeExempt = $("#advanceDeductFeeExempt").val();
		var allDeductAmount = $("#allDeductAmount").val();
		if (checkIsNull(remainDeductAmountExempt) && !(checkIsNull(advanceDeductFeeExempt))) {
			var allDeductAmountApply = (Number(allDeductAmount) - Number(advanceDeductFeeExempt)).toFixed(2);
		} else if (checkIsNull(advanceDeductFeeExempt) && !(checkIsNull(remainDeductAmountExempt))) {
			var allDeductAmountApply = (Number(allDeductAmount) - Number(remainDeductAmountExempt)).toFixed(2);
		} else if (checkIsNull(remainDeductAmountExempt) && checkIsNull(advanceDeductFeeExempt)) {
			var allDeductAmountApply = allDeductAmount;
		} else {
			var allDeductAmountApply = (Number(allDeductAmount) - Number(remainDeductAmountExempt) - Number(advanceDeductFeeExempt)).toFixed(2);
		}
		$("#allDeductAmountApply").val(allDeductAmountApply);
	}
</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="applyAdvanceRepay" action="${ctx}/accounting/applyAdvanceRepay/save" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<input type="hidden" name="contractNo" value="${applyAdvanceRepay.contractNo}" />
		<input type="hidden" name="periodNum" value="${applyAdvanceRepay.periodNum}" />
		<sys:message content="${message}" />
		<h3 class="infoListTitle">提前还款信息</h3>
		<table class="filter">
			<tr>
				<td class="ft_label">提前还款费用收取方式：</td>
				<td class="ft_content">
					<form:select path="advanceDeductType" class="input-xlarge required" onchange="setBreakRepayTypeName();getRepayAmount()" style="margin-bottom:6px">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('BREAK_REPAY_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
					<font style="color: red">*</font>
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
					<form:input path="advanceDeductFeeExempt" htmlEscape="false" class="input-xlarge required" onblur="calculateAllDeductAmountApply();" />
					<font style="color: red">*</font>
				</td>
			</tr>
			<tr>
				<td class="ft_label">剩余应还总额：</td>
				<td class="ft_content">
					<form:input path="remainDeductAmount" htmlEscape="false" readonly="true" value="${remainRepayAmount}" class="input-xlarge required" />
				</td>
				<td class="ft_label">申请减免金额：</td>
				<td class="ft_content">
					<form:input path="remainDeductAmountExempt" htmlEscape="false" class="input-xlarge required" onblur="calculateAllDeductAmountApply();" />
					<font style="color: red">*</font>
				</td>
			</tr>
			<tr>
				<td class="ft_label">全额应还总金额：</td>
				<td class="ft_content">
					<form:input path="allDeductAmount" htmlEscape="false" readonly="true" class="input-xlarge required" />
				</td>
				<td class="ft_label">申请全额应还总金额：</td>
				<td class="ft_content">
					<form:input path="allDeductAmountApply" htmlEscape="false" readonly="true" class="input-xlarge required" />
					<font style="color: red">*</font>
				</td>
			</tr>
			<tr>
				<td class="ft_label">申请说明：</td>
				<td class="ft_content" colspan="3" style="width: 91%;">
					<form:textarea path="advanceDescription" htmlEscape="false" rows="5" cols="3" maxlength="1000" class="area-length required" style="width: 90%;" />
					<font style="color: red">*</font>
				</td>
			</tr>
		</table>
		<div class="searchButton">
			<input id="rsBtnSubmit" class="btn btn-primary" type="submit" value="划扣" />
			&nbsp;
			<input id="btnCancel" class="btn" type="button" value="关闭" onclick="closeJBox();" />
		</div>
	</form:form>
</body>
</html>