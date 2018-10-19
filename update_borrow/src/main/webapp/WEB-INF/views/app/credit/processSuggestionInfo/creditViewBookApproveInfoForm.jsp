<%@ page contentType="text/html;charset=UTF-8"%>
<%-- <%@ include file="/WEB-INF/views/include/taglib.jsp"%> --%>
<!-- <html>
<head>
<title>流程意见管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(
			function() {
				//$("#name").focus();
				$("#inputForm")
						.validate(
								{
									submitHandler : function(form) {
										loading('正在提交，请稍等...');
										form.submit();
									},
									errorContainer : "#messageBox",
									errorPlacement : function(error, element) {
										$("#messageBox").text("输入有误，请先更正。");
										if (element.is(":checkbox")
												|| element.is(":radio")
												|| element.parent().is(
														".input-append")) {
											error.appendTo(element.parent()
													.parent());
										} else {
											error.insertAfter(element);
										}
									}
								});
			});
</script>
</head>
<body>
 -->
	<div class="searchInfo">
		<h3 class="searchTitle">审批信息</h3>
		<div class="searchCon">
			<form:form id="inputForm" modelAttribute="processSuggestionInfo"
				action="${ctx}/credit/processSuggestionInfo/save" method="post"
				class="form-horizontal">
				<form:hidden path="id" />
				<sys:message content="${message}" />
				<table class="fromTable filter">
					<tr>
						<td class="ft_label" style="width: 10%;">分公司综合意见：</td>
						<td class="ft_content" style="width: 90%;">
							<form:textarea path="suggestionDesc" htmlEscape="false" rows="4" maxlength="1000" class="area-length" />
							<font color="red">*</font>	
						</td>
					</tr>
					<tr>
						<td class="ft_label" style="width: 10%;">区域综合意见：</td>
						<td class="ft_content" style="width: 90%;">
							<form:textarea path="suggestionDesc" htmlEscape="false" rows="4" maxlength="1000" class="area-length" />
							<font color="red">*</font>	
						</td>
					</tr>
					<tr>
						<td class="ft_label" style="width: 10%;">大区综合意见：</td>
						<td class="ft_content" style="width: 90%;">
							<form:textarea path="suggestionDesc" htmlEscape="false" rows="4" maxlength="1000" class="area-length" />
							<font color="red">*</font>	
						</td>
					</tr>
					<tr>
						<td class="ft_label" style="width: 10%;">总公司综合意见：</td>
						<td class="ft_content" style="width: 90%;">
							<form:textarea path="suggestionDesc" htmlEscape="false" rows="4" maxlength="1000" class="area-length" />
							<font color="red">*</font>	
						</td>
					</tr>
				</table>
			</form:form>
		</div>
	</div>
<!-- </body> -->
<!-- </html> -->