<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>征信企业贷款明细管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {

		document.getElementById("loanAmount").value = outputmoney("${creditCompanyLoan.loanAmount}");
		document.getElementById("balanceAmount").value = outputmoney("${creditCompanyLoan.balanceAmount}");
		document.getElementById("overdueAmount").value = outputmoney("${creditCompanyLoan.overdueAmount}");
		document.getElementById("monthExpireAmount").value = outputmoney("${creditCompanyLoan.monthExpireAmount}");

		if ('${readOnly}' == '0') {
			setPageReadOnly();
		}
		//$("#name").focus();
		$("#creditCompanyLoanForm").validate({
		submitHandler : saveCreditCompanyLoan,
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			$("#messageBox").text("输入有误，请先更正。");
			checkReq(error, element)
		}
		});
	});

	//保存企业贷款明细
	function saveCreditCompanyLoan() {

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
		var creditCompanyLoan = $("#creditCompanyLoanForm").serializeJson();
		creditCompanyLoan.creditCompany = {
			id : '${creditCompanyLoan.creditCompany.id}'
		}
		saveJson("${ctx}/credit/creditAndLine/creditCompany/creditCompanyLoan/save", JSON.stringify(creditCompanyLoan), function(data) {
			if (data) {
				closeTip();
				if (data.status == 1) {//保存成功
					alertx(data.message, function() {
						//刷新JBox
						var creditCompanyId = '${creditCompanyLoan.creditCompany.id }';
						parent.$.loadDiv("creditCompanyLoanDiv", "${ctx }/credit/creditAndLine/creditCompany/creditCompanyLoan/list", {
							creditCompanyId : '${creditCompanyLoan.creditCompany.id }'
						}, "post");
						parent.parent.$.loadDiv("creditCompanyDiv", "${ctx }/credit/creditAndLine/creditCompany/list", {
						applyNo : '${applyNo}',
						readOnly : '${readOnly}'
						}, "post");
						closeJBox("creditCompanyLoanBox");
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
		<h3 class="searchTitle">新增企业贷款信息</h3>
		<div class="searchCon">
			<form:form id="creditCompanyLoanForm" modelAttribute="creditCompanyLoan" action="" method="post" class="form-horizontal">
				<form:hidden path="id" />
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
							<input id="loanDate" name="loanDate" type="text" maxlength="20" class="input-medium Wdate required" value="${creditCompanyLoan.loanDate}" onclick="WdatePicker({onpicked:dateWhite(this),maxDate:new Date()})" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">到期日期：</td>
						<td class="ft_content">
							<input id="expireDate" name="expireDate" type="text" maxlength="20" class="input-medium Wdate required" value="${creditCompanyLoan.expireDate}" onclick="WdatePicker({onpicked:dateWhite(this),minDate:new Date()})" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">币种：</td>
						<td class="ft_content">
							<form:select path="currCd" class="input-medium required">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('CURR_CD') }" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">放款金额(元)：</td>
						<td class="ft_content">
							<form:input onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" path="loanAmount" htmlEscape="false" maxlength="32" class="input-medium required" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">余额(元)：</td>
						<td class="ft_content">
							<form:input onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" path="balanceAmount" htmlEscape="false" maxlength="32" class="input-medium required" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">逾期金额(元)：</td>
						<td class="ft_content">
							<form:input path="overdueAmount" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" htmlEscape="false"  class="input-medium required" />
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">对外担保状态：</td>
						<td class="ft_content">
							<form:select path="guaranteeOutStat" class="input-medium required">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('OUTER_GUARANTEE_STATUS') }" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
						</td>
						<td class="ft_label">月内贷款到期(元)：</td>
						<td class="ft_content">
							<form:input path="monthExpireAmount" htmlEscape="false" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);"  class="input-medium required" />
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td colspan="6">
							<div class="form-actions">
								<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />
								&nbsp;
								<input id="btnCancel" class="btn" type="button" value="返 回" onclick="closeJBox('creditCompanyLoanBox');" />
							</div>
						</td>
					</tr>
				</table>
			</form:form>
		</div>
	</div>
</body>
</html>