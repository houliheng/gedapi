<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>合同展期申请</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#riskLelveForm").validate({
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
		var formJson = $("#riskLelveForm").serializeJson();
		$.post("${ctx}/postloan/debtCollection/applySave?flag=${flag}", formJson, function(data) {
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
	<div class="wrapper">
		<div id="RiskLelveId">
			<div class="searchInfo">
				<h3 class="searchTitle">设置风险级别</h3>
				<div class="searchCon">
					<form:form id="riskLelveForm" modelAttribute="debtCollection" action="#" method="post" class="form-horizontal">
						<form:hidden path="id" />
						<table class="fromTable filter">
							<tr>
								<td class="ft_label">风险级别：</td>
								<td class="ft_content">
									<form:radiobuttons path="riskLelve" items="${fns:getDictList('RISK_LEVEL')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required" />
									<font color="red">*</font>
								</td>
							</tr>
							<tr>
								<td class="searchButton" colspan="6" style="text-align: right;">
									<input id="bbtnSubmit" type="submit" class="btn btn-primary" value="保 存" />
									&nbsp;
									<input id="bbtnButton" type="button" class="btn btn-primary" value="关 闭" onclick="closeJBox()" />
								</td>
							</tr>
						</table>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
