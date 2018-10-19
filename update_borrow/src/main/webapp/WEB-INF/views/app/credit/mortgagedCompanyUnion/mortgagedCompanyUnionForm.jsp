<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>担保企业管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		if ('${readOnly}' != "true") {
			$("#btnCancel").hide();
		}
		adjustTextareaLength('relaton', 'preRelaton');
		adjustTextareaLength('companyDesc', 'preCompanyDesc');
		adjustTextareaLength('incomeState', 'preIncomeState');
		adjustTextareaLength('operationState', 'preOperationState');
		$("#gqgetGuarantorCompanyForm").validate({
		submitHandler : saveForm,
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

	function saveForm() {
		loading();
		top.$.jBox.tip.mess = null;
		var formJson = $("#gqgetGuarantorCompanyForm").serializeJson();
		$.post("${ctx}/credit/mortgagedCompanyUnion/save", formJson, function(data) {
			if (data) {
				if (data.status == 1) {//保存成功
					alertx(data.message, function() {
						parent.$.loadDiv("gqgetGuarantorCompanyInfo", "${ctx }/credit/mortgagedCompanyUnion/list", {
							applyNo : $("#applyNo").val(),
							approveId : $("#approveId").val(),
						}, "post");
						closeJBox();
					});
				} else {
					alertx(data.message);
				}
				closeTip();
			}
		});
	}
</script>
</head>
<body>
	<form:form id="gqgetGuarantorCompanyForm" modelAttribute="mortgagedCompanyUnion" action="" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<input type="hidden" id="applyNo" name="applyNo" value="${mortgagedCompanyUnion.applyNo}" />
		<input type="hidden" id="approveId" name="approveId" value="${mortgagedCompanyUnion.approveId}" />
		<sys:message content="${message}" />
		<table class="filter">
			<tr>
				<td class="ft_label">与借款公司关系：</td>
				<td class="ft_content" colspan="5">
					<pre class="input-medium textareaWidth pre-style" id="preRelaton"></pre>
					<form:textarea path="relaton" htmlEscape="false" rows="4" maxlength="1000" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('relaton', 'preRelaton');" />
					<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td class="ft_label">企业介绍：</td>
				<td class="ft_content" colspan="5">
					<pre class="input-medium textareaWidth pre-style" id="preCompanyDesc"></pre>
					<form:textarea path="companyDesc" htmlEscape="false" rows="4" maxlength="1000" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('companyDesc', 'preCompanyDesc');" />
					<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td class="ft_label">经营状况：</td>
				<td class="ft_content" colspan="5">
					<pre class="input-medium textareaWidth pre-style" id="preOperationState"></pre>
					<form:textarea path="operationState" htmlEscape="false" rows="4" maxlength="1000" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('operationState', 'preOperationState');" />
					<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td class="ft_label">收入状况：</td>
				<td class="ft_content" colspan="5">
					<pre class="input-medium textareaWidth pre-style" id="preIncomeState"></pre>
					<form:textarea path="incomeState" htmlEscape="false" rows="4" maxlength="1000" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('incomeState', 'preIncomeState');" />
					<font color="red">*</font>
				</td>
			</tr>
		</table>
		<div class="searchButton">
			<shiro:hasPermission name="credit:gqgetComInfo:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />
				&nbsp;
			<input id="btnReset" class="btn-primary btn " type="button" value="关 闭" onclick="closeJBox();" />
			</shiro:hasPermission>
		</div>
		<div style="float:right">
			<input id="btnCancel" class="btn-primary btn " type="button" value="关 闭" onclick="closeJBox();" />
		</div>
	</form:form>
</body>
</html>