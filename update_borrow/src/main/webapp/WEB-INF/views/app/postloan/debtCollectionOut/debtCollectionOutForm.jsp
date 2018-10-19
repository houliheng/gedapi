<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>外包催收管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		//$("#name").focus();
		$("#inputForm").validate({
		submitHandler : saveForm,
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});
	});

	function saveForm() {
		top.$.jBox.tip.mess = null;
		var formJson = $("#inputForm").serializeJson();
		$.post("${ctx}/postloan/debtCollectionOut/save", formJson, function(data) {
			if (data) {
				if (data.status == 1) {
					alertx(data.message, function() {
						parent.$.loadDiv("debtCollectionOutDiv", "${ctx }/postloan/debtCollectionOut/list", {
							debtId : data.debtId
						}, "post");
						closeJBox();
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
	<form:form id="inputForm" modelAttribute="debtCollectionOut" action="#" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<form:hidden path="contractNo" />
		<form:hidden path="debtId" />
		<sys:message content="${message}" />
		<table class="fromTable filter">
			<tr>
				<td class="ft_label">处理时间：</td>
				<td class="ft_content">
					<input id="collectionDate" name="collectionDate" type="text" maxlength="20" class="input-medium Wdate required" value="<fmt:formatDate value="${debtCollectionOut.collectionDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd',maxDate:new Date(),isShowClear:false});" />
					<font class="font" color="red">*</font>
				</td>
				<td class="ft_label">处理人：</td>
				<td class="ft_content">
					<form:input path="collector" htmlEscape="false" maxlength="32" class="input-medium required" />
					<font class="font" color="red">*</font>
				</td>
			</tr>
			<tr>
				<td class="ft_label">催收详情：</td>
				<td class="ft_content" colspan="5">
					<form:textarea path="description" rows="5" class="textarea-width textarea-style required" maxlength="1000" htmlEscape="false" />
					<font class="font" color="red">*</font>
				</td>
			</tr>
			<tr>
				<td class="ft_label">客户回款描述：</td>
				<td class="ft_content" colspan="5">
					<form:textarea path="custResult" rows="5" class="textarea-width textarea-style required" maxlength="1000" htmlEscape="false" />
					<font class="font" color="red">*</font>
				</td>
			</tr>
			<tr>
				<td class="searchButton" colspan="6" style="text-align: right;">
					<input id="btnSubmit" type="submit" class="btn btn-primary" value="保 存" />
					&nbsp;
					<input id="btnCancel" class="btn btn-primary " type="button" value="返回" onclick="closeJBox()" />
				</td>
			</tr>
		</table>
	</form:form>
</body>
</html>