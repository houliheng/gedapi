<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>一次网查（企查查）管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		adjustTextareaLength('description','preDescription');
		$(document).ready(function() {
			if('${stockWebCheck.isException}' == "0"){
				var isException = document.getElementById("radio_yess");
				isException.checked = "checked";
			}
			if('${stockWebCheck.isException}' == "1"){
				var isException = document.getElementById("radio_noo");
				isException.checked = "checked";
			}
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					var data = $("#inputForm").serializeJson();
					$.post("${ctx}/credit/stockWebCheck/save", data, function(data) {
						if (data) {
							closeTip();
							if (data.status == 1) {
								alertx(data.message, function() {
									window.location.reload();
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
	<h3 class="searchTitle">一次网查</h3><br/>
	<div>
		<table>
				<tr>
					<td class="ft_label">企业名称：</td>
					<td class="ft_content">
						<input type="text" id="companyName" class="input-medium" readonly="true" value="${applyRegister.companyName}" />
					</td>
					<td class="ft_label">企业证件类型：</td>
					<td class="ft_content">
						<input type="text" id="comIdType" class="input-medium" readonly="true" value="${applyRegister.comIdTypeLabel}" />
					</td>
					<td class="ft_label">企业证件号：</td>
					<td class="ft_content">
						<input type="text" id="comIdNum" class="input-medium" readonly="true" value="${applyRegister.comIdNum}" />
					</td>
				</tr>
			</table>
	</div><br/>
	<div>
		<form:form id="inputForm" modelAttribute="stockWebCheck" action="${ctx}/credit/stockWebCheck/save" method="post" class="form-horizontal">
			<form:hidden path="id"/>
			<form:hidden path="applyNo"/>
			<sys:message content="${message}"/>	
			<table class="fromTable filter">
				<tr>
					<td class="ft_label">
						<a href="http://www.qichacha.com" target="_blank">企查查：</a>
					</td>
					<td class="">
						<input type="radio" name="isException" value="0" id="radio_yess" class="required">
						<label for="radio_yes">正常</label>
						&nbsp;&nbsp;
						<input type="radio" name="isException" value="1" id="radio_noo" class="required">
						<label for="radio_no">异常</label>
						<font color="red">*</font>
						&nbsp;&nbsp;
						<a id="checkPhoneSkipId" target="_parent"></a>
					</td>
				</tr>
				<tr>
					<td class="ft_label">描述：</td>
					<td class="ft_content" style="width: 70%;">
						<pre class="textareaWidth pre-style"  id="preDescription"></pre>
						<form:textarea id="description" path="description" style="overflow-y:visible" htmlEscape="false" rows="4" maxlength="1000" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('description','preDescription'),this.value=this.value.replace(/[, ]/g,'')"/>
						<font color="red">*</font>
					</td>
				</tr>
			</table>
			<div class="form-actions" style="text-align: right;">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
			</div>
		</form:form>
	</div>
</body>
</html>