<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>代偿资金账户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					loading();
					var param = $("#inputForm").serialize();
					$.post("${ctx}/credit/compensatoryAccount/save", param, function(data) {
						if (data) {
							closeTip();
							if (data.status == '1') {
								alertx(data.message, function() {
									parent.location.reload();
									closeJBox();
								});
							} else {
								alertx(data.message);
							}
						}
					});
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="compensatoryAccount" action="${ctx}/credit/compensatoryAccount/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="fromTable filter">
			<tr>
				<td class="ft_label">代偿账户：</td>
				<td class="ft_content">
					<form:input path="compensatoryAccount" maxlength="18" htmlEscape="false" class=" input-medium required" />
					<font color="red">*</font>
				</td>
				<td class="ft_label">客户编码：</td>
				<td class="ft_content">
					<form:input path="custId" maxlength="18" htmlEscape="false" class=" input-medium required" />
					<font color="red">*</font>
				</td>
			</tr>	
			<tr>	
				<td class="ft_label">优先级：</td>
				<td class="ft_content">
					<form:select id="priopityLevel" path="priopityLevel" name="priopityLevel" class="input-medium required">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('PRIOPITY_LEVEL')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
					<font color="red">*</font>
				</td>
			</tr>
		</table>
		<div class="searchButton" id="buttonDiv">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />
		</div>
	</form:form>
</body>
</html>