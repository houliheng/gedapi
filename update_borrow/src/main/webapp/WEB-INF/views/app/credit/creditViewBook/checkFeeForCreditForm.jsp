<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>信审意见书外访费收取情况管理</title>
<script type="text/javascript">
	$(document).ready(function() {
		adjustTextareaLength("description", "pre");
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

	function wffsqClick() {
		$("#wfdsqId").toggle(600);
	}
</script>
</head>
<body>
	<div class="searchInfo">
		<h3 onclick="wffsqClick()" class="searchTitle">外访费收取情况</h3>
		<div id="wfdsqId" class="searchCon">
			<form:form id="inputForm" modelAttribute="checkFee" action="${ctx}/credit/creditViewBook/checkFeeForCredit/save" method="post" class="form-horizontal">
				<form:hidden path="id" />
				<sys:message content="${message}" />
				<table class="fromTable filter">
					<tr>
						<td class="ft_label">收取时间：</td>
						<td class="ft_content">
							<input id="checkDate" name="checkDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " disabled="true" value="<fmt:formatDate value="${checkFee.checkDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd',maxDate:new Date(),isShowClear:false});" />
						</td>
						<td class="ft_label">收取人：</td>
						<td class="ft_content">
							<form:input path="checkUserName" htmlEscape="false" maxlength="300" class="input-medium " readonly="true" />
						</td>
						<td class="ft_label">金额(元)：</td>
						<td class="ft_content">
							<form:input path="checkFee" htmlEscape="false" maxlength="300" class="input-medium " readonly="true" />
						</td>
					</tr>
					<tr>
						<td class="ft_label">是否收取外访费：</td>
						<td class="ft_content">
							<form:radiobuttons path="isCollect" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="" disabled="true" />
						</td>
					</tr>
					<tr>
						<td class="ft_label">备注：</td>
						<td class="ft_content" colspan="5">
							<pre class="input-xxlarge pre-style" id="pre"></pre>
							<form:textarea path="description" htmlEscape="false" maxlength="1000" class="input-xxlarge textarea-style " onkeyup="adjustTextareaLength('description','pre')" readonly="true" />
						</td>
					</tr>
				</table>
			</form:form>
		</div>
	</div>
</body>
</html>