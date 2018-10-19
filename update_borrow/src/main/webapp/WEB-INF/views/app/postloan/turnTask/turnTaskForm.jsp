<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>合同展期申请</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#turnTaskForm").validate({
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
		var formJson = $("#turnTaskForm").serializeJson();
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
		<div id="turnTaskId">
			<div class="searchInfo">
				<h3 class="searchTitle">转办</h3>
				<div class="searchCon">
					<form:form id="turnTaskForm" modelAttribute="debtCollection" action="#" method="post" class="form-horizontal">
						<form:hidden path="id"  />
						<table class="fromTable filter">
							<tr>
								<td class="ft_label">转办方式：</td>
								<td class="ft_content">
									<form:radiobuttons path="newCollectionType" items="${fns:getDictList('CURR_COLLECTION_TYPE_TURN')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required" />
									<font color="red">*</font>
								</td>
							</tr>
							<tr>
								<td class="ft_label">转办意见：</td>
								<td class="ft_content" colspan="5">
									<form:textarea path="suggestion" rows="5" class="textarea-width textarea-style required" maxlength="1000" htmlEscape="false" />
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
				</div>
			</div>
		</div>
	</div>
</body>
</html>
