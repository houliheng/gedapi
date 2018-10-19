<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>电话催收管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		showCallReceiver('${debtCollectionPhone.callResult}');
		if (!checkIsNull('${debtCollectionPhone.riskAbnormal}')) {
			checkPhoneRiskAbnormal();
		}
		//$("#name").focus();
		$("#inputForm").validate({
		submitHandler : saveForm,
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});
	});

	function showCallReceiver(value) {
		if (value == "1") {
			$("[name = 'callReceiverLabel']").show();
		} else {
			$("[name = 'callReceiverLabel']").hide();
		}
	}

	function checkPhoneRiskAbnormal() {
		var riskPointStr = '${debtCollectionPhone.riskAbnormal}';
		var riskPointArray = riskPointStr.split(",");
		var checkBoxAll = $("input[name='riskAbnormal']");
		for (var i = 0; i < riskPointArray.length; i++) {
			$.each(checkBoxAll, function(j, checkBox) {
				var checkValue = $(checkBox).val();
				if (riskPointArray[i] == checkValue) {
					$(checkBox).attr("checked", true);
				}
			});
		}
	}

	function saveForm() {
		loading();
		var riskAbnormals = getCheckedValue("riskAbnormal");
		top.$.jBox.tip.mess = null;
		var formJson = $("#inputForm").serializeJson();
		formJson["riskAbnormal"] = riskAbnormals;
		$.post("${ctx}/postloan/debtCollectionPhone/save", formJson, function(data) {
			if (data) {
				closeTip();
				if (data.status == 1) {
					alertx(data.message, function() {
						parent.$.loadDiv("debtCollectionPhoneDiv", "${ctx }/postloan/debtCollectionPhone/list", {
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
	<form:form id="inputForm" modelAttribute="debtCollectionPhone" action="#" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<form:hidden path="contractNo" />
		<form:hidden path="custName" />
		<form:hidden path="debtId" />
		<sys:message content="${message}" />
		<table class="fromTable filter">
			<tr>
				<td class="ft_label">催收时间：</td>
				<td class="ft_content">
					<input id="collectionDate" name="collectionDate" type="text" maxlength="20" class="input-medium Wdate required" value="<fmt:formatDate value="${debtCollectionPhone.collectionDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd',maxDate:new Date(),isShowClear:false});" />
					<font color="red">*</font>
				</td>
				<td class="ft_label">风险级别：</td>
				<td class="ft_content">
					<form:radiobuttons path="riskLelve" items="${fns:getDictList('RISK_LEVEL')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required" />
					<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td class="ft_label">电话来源：</td>
				<td class="ft_content">
					<form:select path="callSource" class="input-medium">
						<form:option value="" label="请选择" />
						<form:options items="${fns:getDictList('CALL_SOURCE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
				</td>
				<td class="ft_label">电话类型：</td>
				<td class="ft_content">
					<form:select path="callType" class="input-medium">
						<form:option value="" label="请选择" />
						<form:options items="${fns:getDictList('CALL_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
				</td>
				<td class="ft_label">电话号码：</td>
				<td class="ft_content">
					<form:input path="callNum" htmlEscape="false" maxlength="20" class="input-medium phone required" />
					<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td class="ft_label">拨打对象：</td>
				<td class="ft_content">
					<form:select path="callTarget" class="input-medium required">
						<form:option value="" label="请选择" />
						<form:options items="${fns:getDictList('LISTENER_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
					<font color="red">*</font>
				</td>
				<td class="ft_label">拨打情况：</td>
				<td class="ft_content">
					<form:select path="callResult" class="input-medium required" onchange="showCallReceiver(this.value)">
						<form:option value="" label="请选择" />
						<form:options items="${fns:getDictList('DIAL_RESC')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
					<font color="red">*</font>
				</td>
				<td name="callReceiverLabel" class="ft_label">接听者身份：</td>
				<td name="callReceiverLabel" class="ft_content">
					<form:select path="callReceiver" class="input-medium required">
						<form:option value="" label="请选择" />
						<form:options items="${fns:getDictList('LISTENER_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
					<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td class="ft_label">异常风险点：</td>
				<td class="ft_content" colspan="5">
					<form:checkboxes path="riskAbnormal" class="required" itemLabel="label" itemValue="value" htmlEscape="false" items="${fns:getDictList('CONTRACT_RISK_POINT')}" />
					<font color="red">*</font>
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