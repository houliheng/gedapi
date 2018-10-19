<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>其他抵质押物信息管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#otherForm").validate({
		submitHandler : function(form) {
			saveOtherForm();
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});

		adjustTextareaLength("description", "preOther");
	});

	function saveOtherForm() {
		loading();
		top.$.jBox.tip.mess = null;
		var formJson = $("#otherForm").serializeJson();
		$.post("${ctx}/credit/mortgageOtherInfo/save", formJson, function(data) {
			if (data) {
			closeTip();
				if (data.status == 1) {//保存成功
					alertx(data.message, function() {
						parent.$.loadDiv("other", "${ctx }/credit/mortgageOtherInfo", {
							applyNo : data.data
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
	<form:form id="otherForm" modelAttribute="mortgageOtherInfo" action="${ctx}/credit/mortgageOtherInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<form:hidden path="applyNo" />
		<sys:message content="${message}" />
		<table class="filter" style="width:100%">
			<pre class="input-xxlarge pre-style" style="width:750px" id="preOther"></pre>
			<h3 class="infoListTitle">其他抵质押物信息</h3>
			<tr>
				<td class="ft_label">资产名称：</td>
				<td class="ft_content">
					<form:input path="assetsName" htmlEscape="false" maxlength="50" class="input-medium required" />
					<font color="red">*</font>
				</td>
				<td colspan="2"></td>
			</tr>
			<tr>
				<td class="ft_label">备注：</td>
				<td class="ft_content" colspan="5">
					<form:textarea path="description" htmlEscape="false" rows="4" maxlength="1000" class="input-xxlarge textarea-style required" style="width:750px" onkeyup="adjustTextareaLength('description','preOther')" />
				</td>
			</tr>
			<tr>
				<td class="searchButton" colspan="6">
					<shiro:hasPermission name="credit:mortgageInfo:edit">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />&nbsp;</shiro:hasPermission>
					<input id="btnCancel" class="btn" type="button" value="取 消" onclick="parent.$.jBox.close()" />
				</td>
			</tr>
		</table>
	</form:form>
</body>
</html>