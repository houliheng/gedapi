<%@ page contentType="text/html;charset=UTF-8"%>
<%-- <%@ include file="/WEB-INF/views/include/taglib.jsp"%> --%>
<!-- <html> -->
<!-- <head> -->
<!-- <title>征信与流水分析管理</title> -->
<!-- <meta name="decorator" content="default" /> -->
<!--<script type="text/javascript">
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
</script>-->
<!-- </head> -->
<!-- <body> -->
<div class="searchInfo">
	<h3 class="searchTitle">分析信息</h3>
	<div class="searchCon">
		<form:form id="inputForm" modelAttribute="creditAnalysis" action="${ctx}/credit/creditAnalysis/save" method="post" class="form-horizontal">
			<form:hidden path="id" />
			<sys:message content="${message}" />
			<table class="fromTable filter">
				<tr>
					<td class="ft_label" style="width: 10%;">借款用途说明：</td>
					<td class="ft_content" style="width: 90%;">
						<form:textarea path="loanPurposeDesc" htmlEscape="false" rows="4" maxlength="1000" class="area-length required" />
					</td>
				</tr>
				<tr>
					<td class="ft_label" style="width: 10%;">经营情况分析：</td>
					<td class="ft_content" style="width: 90%;">
						<form:textarea path="businessDesc" htmlEscape="false" rows="4" maxlength="1000" class="area-length required" />
					</td>
				</tr>
				<tr>
					<td class="ft_label" style="width: 10%;">行业状况分析：</td>
					<td class="ft_content" style="width: 90%;">
						<form:textarea path="categoryDesc" htmlEscape="false" rows="4" maxlength="1000" class="area-length required" />
					</td>
				</tr>
				<tr>
					<td class="ft_label" style="width: 10%;">借款企业分析：</td>
					<td class="ft_content" style="width: 90%;">
						<form:textarea path="companyDesc" htmlEscape="false" rows="4" maxlength="1000" class="area-length required" />
					</td>
				</tr>
				<tr>
					<td class="ft_label" style="width: 10%;">还款来源分析：</td>
					<td class="ft_content" style="width: 90%;">
						<form:textarea path="loanRepayDesc" htmlEscape="false" rows="4" maxlength="1000" class="area-length required" />
					</td>
				</tr>
				<tr>
					<td class="ft_label" style="width: 10%;">担保情况分析：</td>
					<td class="ft_content" style="width: 90%;">
						<form:textarea path="guaranteeDesc" htmlEscape="false" rows="4" maxlength="1000" class="area-length required" />
					</td>
				</tr>
				<tr>
					<td class="ft_label" style="width: 10%;">风险点：</td>
					<td class="ft_content" style="width: 90%;">
						<form:textarea path="riskPoint" htmlEscape="false" rows="4" maxlength="1000" class="area-length required" />
					</td>
				</tr>
				<tr>
					<td class="ft_label" style="width: 10%;">优势：</td>
					<td class="ft_content" style="width: 90%;">
						<form:textarea path="edgeComment" htmlEscape="false" rows="4" maxlength="1000" class="area-length required" />
					</td>
				</tr>
			</table>
		</form:form>
	</div>
</div>
<!-- </body> -->
<!-- </html> -->