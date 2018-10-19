<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>保证金退还申请管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		document.getElementById("marginAmount").value = outputmoney(${applyMarginRepay.marginAmount});
		document.getElementById("applyMarginAmount").value = outputmoney(${applyMarginRepay.applyMarginAmount});
		document.getElementById("marginAmount1").value = outputmoney(${applyMarginRepay.marginAmount});
		document.getElementById("approMarginAmount").value = outputmoney(${applyMarginRepay.approMarginAmount});
		if ("1" == "${actTaskParam.status}") {
			$("#searchInfo").hide();
		}
		$("#inputForm").validate({
		submitHandler : saveForm,
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
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
		var valueT1 = $("#marginAmount").val().replace(/,/g, "");
		$("#marginAmount").val(valueT1);
		var valueT2 = $("#applyMarginAmount").val().replace(/,/g, "");
		$("#applyMarginAmount").val(valueT2);
		var valueT3 = $("#marginAmount").val().replace(/,/g, "");
		$("#marginAmount1").val(valueT3);
		var valueT4 = $("#approMarginAmount").val().replace(/,/g, "");
		$("#approMarginAmount").val(valueT4);
		var status = $("input[name='marginAmountStatus']:checked").val();
		if (4 == Number(status) || 5 == Number(status)) {
			loading();
			top.$.jBox.tip.mess = null;
			var formJson = $("#inputForm").serializeJson();
			$.post("${ctx}/accounting/applyMarginRepay/save", formJson, function(data) {
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
		} else {
			alertx("请进行确认。");
		}
	}
</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="applyMarginRepay" action="${ctx}/accounting/applyMarginRepay/save" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<input type="hidden" name="contractNo" value="${applyMarginRepay.contractNo}" />
		<input type="hidden" id="applyNo" name="applyNo" value="${actTaskParam.applyNo}" />
		<input type="hidden" id="taskId" name="taskId" value="${actTaskParam.taskId}" />
		<input type="hidden" id="taskDefKey" name="taskDefKey" value="${actTaskParam.taskDefKey}" />
		<input type="hidden" id="procDefId" name="procDefId" value="${actTaskParam.procDefId}" />
		<input type="hidden" id="status" name="status" value="${actTaskParam.status}" />
		<input type="hidden" id="procInstId" name="procInstId" value="${actTaskParam.procInstId}" />
		<input type="hidden" id="flag" name="flag" value="3" />
		<sys:message content="${message}" />
		<div id="detail"></div>
		<div class="searchInfo">
			<h3 class="searchTitle">保证金退还申请</h3>
			<div class="searchCon">
				<table class="fromTable filter">
					<tr>
						<td class="ft_label">保证金：</td>
						<td class="ft_content">
							<form:input path="marginAmount" htmlEscape="false" readonly="true" class="input-medium" />
						</td>
						<td class="ft_label">申请退还保证金：</td>
						<td class="ft_content">
							<form:input path="applyMarginAmount" htmlEscape="false" readonly="true" class="input-medium" />
						</td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td class="ft_label" style="width: 10%;">申请说明：</td>
						<td class="ft_content" colspan="5" style="width: 90%;">
							<form:textarea path="marginApplyDesc" htmlEscape="false" rows="5" readonly="true" cols="3" maxlength="1000" class="area-length" style="width: 90%;" />
						</td>
					</tr>
				</table>
			</div>
		</div>
		<shiro:hasPermission name="accounting:applyMarginRepay:approve">
			<div class="searchInfo">
				<h3 class="searchTitle">保证金退还结算中心审核</h3>
				<div class="searchCon">
					<table class="fromTable filter">
						<tr>
							<td class="ft_label">保证金：</td>
							<td class="ft_content">
								<form:input path="marginAmount" id="marginAmount1" htmlEscape="false" readonly="true" class="input-medium" />
							</td>
							<td class="ft_label">批复退还保证金：</td>
							<td class="ft_content">
								<form:input path="approMarginAmount" htmlEscape="false" readonly="true" class="input-medium" />
							</td>
							<td class="ft_label">到账模式：</td>
							<td class="ft_content">
								<form:input path="marginReceivedMode" value="${fns:getDictLabel(applyMarginRepay.marginReceivedMode, 'MARGIN_RECEIVED_MODE', '')}" htmlEscape="false" readonly="true" class="input-medium" />
							</td>
						</tr>
						<tr>
							<td class="ft_label" style="width: 10%;">批复说明：</td>
							<td class="ft_content" colspan="5" style="width: 90%;">
								<form:textarea path="marginApplyDesc" readonly="true" htmlEscape="false" rows="5" cols="3" maxlength="1000" class="area-length" style="width: 90%;" />
							</td>
						</tr>
					</table>
				</div>
			</div>
		</shiro:hasPermission>
		<shiro:hasPermission name="accounting:applyMarginRepay:deadApprove">
			<div class="searchInfo" id="searchInfo">
				<h3 class="searchTitle">保证金退还分公司确认</h3>
				<div class="searchCon">
					<table class="fromTable filter">
						<tr>
							<td class="ft_label">退还结论：</td>
							<td class="ft_content">
								<input type="radio" name="marginAmountStatus" value="4" class="required">
								<span>
									<font>确认退还</font>
								</span>
								<input type="radio" name="marginAmountStatus" value="5" class="required">
								<span>
									<font>放弃</font>
								</span>
							</td>
						</tr>
						<tr>
							<td colspan="2" style="text-align: right;">
								<input id="btnSubmit" type="submit" class="btn btn-primary" value="提 交" />
							</td>
						</tr>
					</table>
				</div>
			</div>
		</shiro:hasPermission>
	</form:form>
</body>
</html>