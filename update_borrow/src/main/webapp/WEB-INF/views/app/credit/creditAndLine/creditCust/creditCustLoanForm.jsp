<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>贷款明细管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		//$("#name").focus();
		document.getElementById("loanAmount").value = outputmoney("${creditCustLoan.loanAmount}");
		document.getElementById("balanceAmount").value = outputmoney("${creditCustLoan.balanceAmount}");
		document.getElementById("overdueAmount").value = outputmoney("${not empty creditCustLoan.overdueAmount ? creditCustLoan.overdueAmount : applyRegister.overdueAmount}");
		document.getElementById("monthExpireAmount").value = outputmoney("${creditCustLoan.monthExpireAmount}");
		$("#creditCustLoanForm").validate({
		submitHandler : saveCreditCustLoan,
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			$("#messageBox").text("输入有误，请先更正。");
			checkReq(error, element);
		}
		});
	});

	//保存银行卡流水信息信息
	function saveCreditCustLoan() {

		var valueT1 = $("#loanAmount").val().replace(/,/g, "");
		var valueT2 = $("#balanceAmount").val().replace(/,/g, "");
		var valueT3 = $("#overdueAmount").val().replace(/,/g, "");
		var valueT4 = $("#monthExpireAmount").val().replace(/,/g, "");
		$("#loanAmount").val(valueT1);
		$("#balanceAmount").val(valueT2);
		$("#overdueAmount").val(valueT3);
		$("#monthExpireAmount").val(valueT4);
		loading();
		top.$.jBox.tip.mess = null;
		var creditCustLoan = $("#creditCustLoanForm").serializeJson();
		creditCustLoan.creditCust = {
			id : '${creditCustLoan.creditCust.id}'
		}
		saveJson("${ctx}/credit/creditAndLine/creditCust/creditCustLoan/save", JSON.stringify(creditCustLoan), function(data) {
			if (data) {
				closeTip();
				if (data.status == 1) {//保存成功
					alertx(data.message, function() {
						//刷新JBox
						var creditCustId = '${creditCustLoan.creditCust.id }';
						//parent.$.loadDiv("creditCustLoanDiv","${ctx }/credit/creditAndLine/creditCust/creditCustLoan/list",{creditCustId:'${creditCustLoan.creditCust.id }'},"post");
						parent.location.href = "${ctx }/credit/creditAndLine/creditCust/creditCustLoan/list?creditCustId=" + '${creditCustLoan.creditCust.id }' + "&applyNo=" + '${applyNo}' + "&readOnly=" + '${readOnly}';
						parent.parent.$.loadDiv("creditCustDiv", "${ctx }/credit/creditAndLine/creditCust/list", {
						applyNo : '${applyNo}',
						readOnly : '${readOnly}'
						}, "post");
						closeJBox("creditCustLoanBox");
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
	<div class="searchInfo">
		<h3 class="searchTitle">个人贷款信息</h3>
		<div class="searchCon">
			<form:form id="creditCustLoanForm" modelAttribute="creditCustLoan" action="#" method="post" class="form-horizontal">
				<form:hidden path="id" />
				<form:hidden path="creditCust.id" />
				<sys:message content="${message}" />
				<table class="fromTable filter">
					<tr>
						<td class="ft_label">发贷行：</td>
						<td class="ft_content">
							<form:input path="bankName" htmlEscape="false" maxlength="20" class="input-medium required" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">贷款类型：</td>
						<td class="ft_content">
							<form:select path="loanKind" class="input-medium required">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('KIND_LOAN') }" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
						</td>
						<td class="ft_label">贷款状态：</td>
						<td class="ft_content">
							<form:select path="loanStatus" class="input-medium required">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('OUTER_GUARANTEE_STATUS') }" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">放款日期：</td>
						<td class="ft_content">
							<input id="loanDate" name="loanDate" type="text" maxlength="20" class="input-medium Wdate required" value="${creditCustLoan.loanDate}" onclick="WdatePicker({onpicked:dateWhite(this),maxDate:new Date()})" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">到期日期：</td>
						<td class="ft_content">
							<input id="expireDate" name="expireDate" type="text" maxlength="20" class="input-medium Wdate required" value="${creditCustLoan.expireDate}" onclick="WdatePicker({onpicked:dateWhite(this),minDate:new Date()})" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">担保方式：</td>
						<td class="ft_content">
							<form:select path="guaranteeType" class="input-medium required">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('GUARANTEE_TYPE') }" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">放款金额(元)：</td>
						<td class="ft_content">
							<form:input path="loanAmount" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" htmlEscape="false" maxlength="32" class="input-medium  required" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">余额(元)：</td>
						<td class="ft_content">
							<form:input onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" path="balanceAmount" htmlEscape="false" maxlength="32" class=" input-medium required" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">逾期金额(元)：</td>
						<td class="ft_content">
							<form:input onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" path="overdueAmount" htmlEscape="false" maxlength="32" class="input-medium  required" />
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">月内贷款到期(元)：</td>
						<td class="ft_content">
							<form:input path="monthExpireAmount" htmlEscape="false" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" class="input-medium required" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">贷款性质：</td>
						<td class="ft_content">
							<form:select path="loanNature" class="input-medium required">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('LOAN_NATURE') }" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td colspan="6">
							<div class="form-actions">
								<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />
								&nbsp;
								<input id="btnCancel" class="btn" type="button" value="返 回" onclick="closeJBox();" />
							</div>
						</td>
					</tr>
				</table>
			</form:form>
</body>
</html>