<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>核销管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		//$("#name").focus();
		$("#inputForm").validate({
		submitHandler : saveForm,
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});
	});

	function saveForm() {
		loading();
		var flag = $("[name = 'approveResult']:checked").val();
		top.$.jBox.tip.mess = null;
		var formJson = $("#inputForm").serializeJson();
		$.post("${ctx}/postloan/checkRemove/approveSave?flag=" + flag, formJson, function(data) {
			if (data) {
				closeTip();
				if (data.status == 1) {
					alertx(data.message, function() {
						closeAndReloadPostLoan();
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
	<form:form id="inputForm" modelAttribute="checkRemove" action="#" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<form:hidden path="ZBorDQ" />
		<form:hidden path="contractNo" />
		<sys:message content="${message}" />
		<table class="fromTable filter">
			<tr>
				<td class="ft_label">区域核销原因：</td>
				<td class="ft_content" colspan="5">
					<textarea rows="5" class="textarea-width textarea-style" disabled="disabled" maxlength="1000" htmlEscape="false">${checkRemove.applySuggestion}</textarea>
				</td>
			</tr>
		</table>
		<c:if test="${checkRemove.ZBorDQ == 'zb'}">
			<table class="fromTable filter">
				<tr>
					<td class="ft_label">大区审核结果：</td>
					<td class="ft_content">
						<input type="radio" checked="checked" disabled="disabled" value="yes" id="radio_yes">
						<label for="radio_yes">通过</label>
						&nbsp;&nbsp;
						<input type="radio" disabled="disabled" value="no" id="radio_no">
						<label for="radio_no">打回</label>
					</td>
				</tr>
				<tr>
					<td class="ft_label">大区审核意见：</td>
					<td class="ft_content" colspan="5">
						<textarea rows="5" class="textarea-width textarea-style" disabled="disabled" maxlength="1000" htmlEscape="false">${checkRemove.approveSuggestionDQ}</textarea>
					</td>
				</tr>
			</table>
		</c:if>
		<table class="fromTable filter">
			<tr>
				<td class="ft_label">审核结果：</td>
				<td class="ft_content">
					<input type="radio" name="approveResult" value="yes" id="radio_yes" class="required">
					<label for="radio_yes">通过</label>
					&nbsp;&nbsp;
					<input type="radio" name="approveResult" value="no" id="radio_no" class="required">
					<label for="radio_no">打回</label>
					<font class="font" color="red">*</font>
				</td>
			</tr>
			<tr>
				<td class="ft_label">审核意见：</td>
				<td class="ft_content" colspan="5">
					<form:textarea path="approveSuggestion" rows="5" class="textarea-width textarea-style required" maxlength="1000" htmlEscape="false" />
					<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td class="searchButton" colspan="6" style="text-align: right;">
					<input id="btnSubmitt" type="submit" class="btn btn-primary" value="保 存" />
					&nbsp;
					<input id="btnButtonn" type="button" class="btn btn-primary" value="关 闭" onclick="closeJBox()" />
				</td>
			</tr>
		</table>
	</form:form>
</body>
</html>