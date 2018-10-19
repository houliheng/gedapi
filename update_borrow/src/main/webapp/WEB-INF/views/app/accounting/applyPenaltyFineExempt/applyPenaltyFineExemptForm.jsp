<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>违约金罚息减免管理</title>
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

	function saveForm() {
		var partPenalty = $("#penaltyExemptAmount").val();
		var allPenalty = "${penaltyExemptAmount}";
		var partFine = $("#fineExemptAmount").val();
		var allFine = "${fineExemptAmount}";
		var reg = /^([1-9][\d]{0,10}|0)(\.[\d]{1,2})?$/;
		if (!reg.test(partPenalty) || !reg.test(partFine)) {
			alertx("请输入正确格式的金额");
		} else if (Number(partPenalty) > Number(allPenalty) || Number(partFine) > Number(allFine)) {
			alertx("申请金额过大，请修改。");
		} else {
			loading();
			top.$.jBox.tip.mess = null;
			var formJson = $("#inputForm").serializeJson();
			$.post("${ctx}/accounting/applyPenaltyFineExempt/save", formJson, function(data) {
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
	}
</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="applyPenaltyFineExempt" action="${ctx}/accounting/applyPenaltyFineExempt/save" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<form:hidden path="contractNo" />
		<sys:message content="${message}" />
		<div class="filter">
			<h3 class="infoListTitle">减免结论</h3>
			<table class="fromTable">
				<tr>
					<td class="ft_label" style="width:175px">申请意见：</td>
					<td class="" colspan="5">
						<form:textarea path="description" htmlEscape="false" rows="4" maxlength="1000" class="input-medium required " style="width:91%" />
						<font style="color: red">*</font>
					</td>
				</tr>
			</table>
		</div>
		<div class="filter">
			<h3 class="infoListTitle">减免设置</h3>
			<table class="fromTable">
				<tr>
					<td class="ft_label">期数：</td>
					<td class="ft_content">
						<form:input path="periodNum" htmlEscape="false" maxlength="10" readonly="true" class="input-medium" />
					</td>
				</tr>
				<tr>
					<td class="ft_label">可减免违约金：</td>
					<td class="ft_content">
						<form:input path="" htmlEscape="false" maxlength="10" class="input-medium" readonly="true" value="${penaltyExemptAmount}" />
					</td>
					<td class="ft_label">申请违约金减免：</td>
					<td class="ft_content">
						<form:input path="penaltyExemptAmount" htmlEscape="false" onclick="this.value = ''" maxlength="10" class="input-medium money required" />
						<font style="color: red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">可减免罚息：</td>
					<td class="ft_content">
						<form:input path="" htmlEscape="false" maxlength="10" class="input-medium" readonly="true" value="${fineExemptAmount}" />
					</td>
					<td class="ft_label">申请罚息减免：</td>
					<td class="ft_content">
						<form:input path="fineExemptAmount" htmlEscape="false" onclick="this.value = ''" maxlength="10" class="input-medium money required" />
						<font style="color: red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_content" colspan="6" style="text-align:right">
						<shiro:hasPermission name="accounting:applyPenaltyFineExempt:edit">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="保存" />&nbsp;
					</shiro:hasPermission>
						<input id="btnReset" class="btn btn-primary" type="button" value="关闭" onclick="closeJBox();" />
					</td>
				</tr>
			</table>
		</div>
	</form:form>
</body>
</html>