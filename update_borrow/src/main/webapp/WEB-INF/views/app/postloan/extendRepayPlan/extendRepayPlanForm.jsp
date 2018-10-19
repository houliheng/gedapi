<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>合同展期还款计划管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		if ('${readonly}' == "true") {
			$("input").attr("readonly", "readonly");
			$("form:input").attr("readonly", "readonly");
			$("#btnSubmit").hide();
		}
		//$("#name").focus();
		$("#inputForm").validate({
		submitHandler : function(form) {
			loading();
			var param = $("#inputForm").serialize();
			$.post("${ctx}/postloan/extendRepayPlan/save", param, function(data) {
			closeTip();
				if (data.status == '1') {
					alertx(data.message, function() {
						parent.$.loadDiv("extendRepayPlanList", "${ctx }/postloan/extendRepayPlan/list", {
							contractNo : data.contractNo
						}, "post");
						parent.$("#extendPeriod").val(data.periodNum);
						closeJBox();
					});
				} else {
					alertx(data.message);
				}
			});
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});
	});
</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="extendRepayPlan" action="${ctx}/postloan/extendRepayPlan/save" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<form:hidden path="contractNo" />
		<sys:message content="${message}" />
		<table class="fromTable filter">
			<tr>
				<td class="ft_label">期数：</td>
				<td class="ft_content">
					<form:input type="text" path="extendPeriodNum" maxlength="10" htmlEscape="false" class="input-medium number required" />
					<font color="red">*</font>
				</td>
				<td class="ft_label">应还日期：</td>
				<td class="ft_content">
					<input id="extendRepayDate" name="extendRepayDate" readonly="true" type="text" maxlength="20" class="input-medium Wdate required" value="<fmt:formatDate value='${extendRepayPlan.extendRepayDate }' pattern='yyyy-MM-dd'/>" onclick="WdatePicker({onpicked:dateWhite(this),minDate:new Date()})" />
					<font color="red">*</font>
				</td>
				<td class="ft_label">服务费：</td>
				<td class="ft_content">
					<form:input type="text" path="extendServiceFee" maxlength="15" htmlEscape="false" class="input-medium number required" />
					<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td class="ft_label">账户管理费：</td>
				<td class="ft_content">
					<form:input type="text" path="extendManagementFee" maxlength="15" htmlEscape="false" class="input-medium number required" />
					<font color="red">*</font>
				</td>
				<td class="ft_label">利息：</td>
				<td class="ft_content">
					<form:input type="text" path="extendInterest" maxlength="15" htmlEscape="false" class="input-medium number required" />
					<font color="red">*</font>
				</td>
				<td class="ft_label">本金：</td>
				<td class="ft_content">
					<form:input type="text" path="extendCapital" maxlength="15" htmlEscape="false" class="input-medium number required" />
					<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td class="ft_label">违约金：</td>
				<td class="ft_content">
					<form:input type="text" path="extendPenalty" maxlength="15" class="input-medium number required" />
					<font color="red">*</font>
				</td>
				<td class="ft_label">当期应还总计：</td>
				<td class="ft_content">
					<form:input path="extendRepayAll" type="text" maxlength="15" class="input-medium number required" />
					<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td class="searchButton" colspan="6" style="text-align: right;">
					<input id="btnSubmit" type="submit" class="btn btn-primary" value="保 存" />
					&nbsp;
					<input id="btnClose" type="button" class="btn btn-primary" value="关 闭" onclick="closeJBox()" />
				</td>
			</tr>
		</table>
	</form:form>
</body>
</html>