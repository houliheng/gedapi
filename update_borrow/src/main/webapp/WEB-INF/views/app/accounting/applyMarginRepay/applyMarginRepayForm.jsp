<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>保证金退还申请管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#inputForm").validate({
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
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
	function saveForm() {
		var partAmount = $("#applyMarginAmount").val();
		var allAmount = "${contract.marginAmount}";
		var reg = /^([1-9][\d]{0,10}|0)(\.[\d]{1,2})?$/;
		if (!reg.test(partAmount)) {
			alertx("请输入正确格式的金额");
		} else if (Number(partAmount) > Number(allAmount)) {
			alertx("申请金额过大，请修改。");
		} else {
			loading();
			top.$.jBox.tip.mess = null;
			var formJson = $("#inputForm").serializeJson();
			$.post("${ctx}/accounting/applyMarginRepay/save", formJson, function(data) {
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
	<form:form id="inputForm" modelAttribute="applyMarginRepay" action="${ctx}/accounting/applyMarginRepay/save" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<input type="hidden" name="contractNo" value="${contract.contractNo}" />
		<input type="hidden" name="orgLevel2.id" value="${contract.orgLevel2.id}" />
		<input type="hidden" name="orgLevel2.name" value="${contract.orgLevel2.name}" />
		<input type="hidden" name="orgLevel3.id" value="${contract.orgLevel3.id}" />
		<input type="hidden" name="orgLevel3.name" value="${contract.orgLevel3.name}" />
		<input type="hidden" name="orgLevel4.id" value="${contract.orgLevel4.id}" />
		<input type="hidden" name="orgLevel4.name" value="${contract.orgLevel4.name}" />
		<input type="hidden" name="operateId" value="${contract.operateId}" />
		<input type="hidden" name="operateOrgId" value="${contract.operateOrgId }" />
		<input type="hidden" name="flag" value="1" />
		<sys:message content="${message}" />
		<div id="detail"></div>
		<div class="searchInfo" id="">
			<h3 class="searchTitle">保证金退还申请</h3>
			<div class="searchCon">
				<table class="fromTable filter">
					<tr>
						<td class="ft_label">保证金：</td>
						<td class="ft_content">
							<form:input path="marginAmount" htmlEscape="false" readonly="true" class="input-medium" value="${contract.marginAmount}" />
						</td>
						<td class="ft_label">申请退还保证金：</td>
						<td class="ft_content">
							<form:input path="applyMarginAmount" htmlEscape="false" class=" money input-medium required" value="${contract.marginAmount}" onclick="this.value = ''" />
							<font style="color: red">*</font>
						</td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td class="ft_label" style="width: 10%;">申请说明：</td>
						<td class="ft_content" colspan="5" style="width: 90%;">
							<form:textarea path="marginApplyDesc" htmlEscape="false" rows="5" cols="3" maxlength="1000" class="area-length required" style="width: 90%;" />
							<font style="color: red">*</font>
						</td>
					</tr>
					<tr>
						<td colspan="6" style="text-align: right;">
							<shiro:hasPermission name="accounting:applyMarginRepay:apply">
								<input id="btnSubmit" class="btn btn-primary" type="submit" value="提 交" />&nbsp;
								</shiro:hasPermission>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</form:form>
</body>
</html>