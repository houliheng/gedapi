<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>外访信息管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		//$("#name").focus();
		$("#checkIndeedForm").validate({
		submitHandler : saveCheckIndeed,
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

	function saveCheckIndeed() {
		var riskPointStr = getCheckedValue("riskPoint");
		top.$.jBox.tip.mess = null;
		var formJson = $("#checkIndeedForm").serializeJson();
		formJson["riskPoint"] = riskPointStr;
		$.post("${ctx}/postloan/checkIndeed/save", formJson, function(data) {
			if (data) {
				if (data.status == 1) {//保存成功
					alertx(data.message, function() {
						parent.$.loadDiv("checkIndeedDiv", "${ctx}/postloan/checkIndeed/list", {
						allocateId : data.allocateId,
						contractNo : data.contractNo
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
	<div class="filter">
		<h3 class="infoListTitle">新增外访信息</h3>
		<form:form id="checkIndeedForm" modelAttribute="checkIndeed" action="" method="post" class="form-horizontal">
			<form:hidden path="id" />
			<form:hidden path="contractNo" />
			<form:hidden path="allocateId" />
			<sys:message content="${message}" />
			<table class="fromTable" width="100%">
				<tr>
					<td class="" style="text-align: right; width:10% ">外访日期：</td>
					<td class="" width="40%">
						<input name="checkDate" id="checkDate" type="text" maxlength="20" class="required input-medium Wdate" value="${checkDoubtful.checkDate }" onclick="WdatePicker({maxDate:new Date()})" />
						<font color="red">*</font>
					</td>
					<td class="" style="text-align: right; width: 10% ">外访人：</td>
					<td class="" width="40%">
						<input name="checkUserName" id="checkUserName" type="text" maxlength="100" class="input-medium required" value="${checkDoubtful.checkUserName }" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="" style="text-align: right; width: 10% ">外访地点：</td>
					<td colspan="3" class="">
						<input name="checkAddress" id="checkAddress" style="width: 75%" type="text" maxlength="300" class="required" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="" style="text-align: right; width: 10% ">异常风险点：</td>
					<td class="" colspan="3">
						<form:checkboxes path="riskPoint" class="required" itemLabel="label" itemValue="value" htmlEscape="false" items="${fns:getDictList('CUST_RISK_POINT')}" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="" style="text-align: right; width: 10% ">外访详情：</td>
					<td class="" colspan="5">
						<pre class="input-medium pre-style" style="width:825px" id="preDescription"></pre>
						<textarea rows="5" cols="300" class="input-medium textarea-style required" style="width:825px" maxlength="1000" name="description" id="description" onkeyup="adjustTextareaLength('description','preDescription')"></textarea>
						<font color="red">*</font>
					</td>
				</tr>
			</table>
			<div class="searchButton">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />
				&nbsp;
			</div>
		</form:form>
	</div>
</body>
</html>