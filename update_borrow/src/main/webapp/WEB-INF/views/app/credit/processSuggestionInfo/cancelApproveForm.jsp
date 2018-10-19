<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>取消审批</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		//$("#name").focus();
		$("#inputForm").validate({
		submitHandler : function(form) {
			loading('正在提交，请稍等...');
			form.submit();
		},
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
	
	function saveSuggestion(){
		top.$.jBox.tip.mess = null;
		var formJson = $("#processSuggestionInfoForm").serializeJson();
		saveJson("${ctx}/credit/processSuggestionInfo/saveAppointConclusion", JSON.stringify(removeDotProperty(formJson)), function(data) {
			if (data) {
				alertx(data.message);
			}
		});
	}
</script>
</head>
<body>
	<div class="searchInfo">
		<h3 class="searchTitle">审批结论</h3>
		<div class="searchCon">
			<form:form id="processSuggestionInfoForm" modelAttribute="processSuggestionInfo" action="${ctx}/credit/processSuggestionInfo/save" method="post" class="form-horizontal">
				<form:hidden path="id" />
				<form:hidden path="applyNo" />
				<form:hidden path="taskDefKey" />
				<sys:message content="${message}" />
				<table class="fromTable filter">
					<tr>
						<td class="ft_label" style="width: 10%;">结论：</td>
						<td class="ft_content" style="width: 90%;">
							<input type="radio" name="conclusion" />
							<font>同意结束申请</font>
							<input type="radio" name="conclusion" />
							<font>打回重约</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label" style="width: 10%;">意见：</td>
						<td class="ft_content" style="width: 90%;">
							<form:textarea path="suggestionDesc" htmlEscape="false" rows="4" maxlength="1000" class="area-length" />
						</td>
					</tr>
					<tr>
						<td colspan="2" style="text-align: right;">
							<shiro:hasPermission name="credit:processSuggestionInfo:cancelApprove:edit">
								<input id="btnSubmit" class="btn btn-primary" type="button" value="提 交" onclick="saveSuggestion();"/>&nbsp;</shiro:hasPermission>
						</td>
					</tr>
				</table>
			</form:form>
		</div>
	</div>
	<!-- </body> -->
	<!-- </html> -->