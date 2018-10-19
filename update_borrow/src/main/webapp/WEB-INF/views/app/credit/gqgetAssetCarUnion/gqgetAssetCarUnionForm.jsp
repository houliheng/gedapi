<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>冠e通资产车辆信息管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		document.getElementById("gqgetMarketPrice").value = outputmoney("${gqgetAssetCar.gqgetMarketPrice}");
		document.getElementById("gqgetCarEvaluatePrice").value = outputmoney("${gqgetAssetCar.gqgetCarEvaluatePrice}");
		$("#gqgetAssetCarForm").validate({
		submitHandler : saveForm,
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});
	});
	function saveForm() {
		var value1 = $("#gqgetMarketPrice").val().replace(/,/g, "");
		var value2 = $("#gqgetCarEvaluatePrice").val().replace(/,/g, "");
		$("#gqgetMarketPrice").val(value1);
		$("#gqgetCarEvaluatePrice").val(value2);
		loading();
		top.$.jBox.tip.mess = null;
		var formJson = $("#gqgetAssetCarForm").serializeJson();
		$.post("${ctx}/credit/gqgetAssetCarUnion/save", formJson, function(data) {
			if (data) {
				if (data.status == 1) {//保存成功
					alertx(data.message, function() {
						parent.$.loadDiv("assetCar", "${ctx }/credit/gqgetAssetCarUnion/list", {
						gqgetApplyNo : data.applyNo,
						approveId : data.approveId
						}, "post");
						closeJBox();
					});
				} else {
					alertx(data.message);
				}
				closeTip();
			}
		});
	}
</script>
</head>
<body>
	<form:form id="gqgetAssetCarForm" modelAttribute="gqgetAssetCarUnion" action="" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<form:hidden path="gqgetApplyNo" />
		<form:hidden path="approveId" />
		<sys:message content="${message}" />
		<table class="filter">
			<tr>
				<td class="ft_label">车牌型号：</td>
				<td class="ft_content">
					<form:input path="gqgetVehicleNo" htmlEscape="false" maxlength="10" class="input-xlarge  required" />
					<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td class="ft_label">使用年限：</td>
				<td class="ft_content">
					<form:input path="gqgetUsedYears" htmlEscape="false" maxlength="3" class="input-xlarge number required" />
					<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td class="ft_label">估值：</td>
				<td class="ft_content">
					<form:input path="gqgetCarEvaluatePrice" onkeyup="keyPress11(this);" maxlength="18" onblur="this.value=outputmoney(this.value);" htmlEscape="false" class="input-xlarge number required" />
					<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td class="ft_label">市值：</td>
				<td class="ft_content">
					<form:input path="gqgetMarketPrice" onkeyup="keyPress11(this);" maxlength="18" onblur="this.value=outputmoney(this.value);" htmlEscape="false" class="input-xlarge number required" />
					<font color="red">*</font>
				</td>
			</tr>
		</table>
		<div class="searchButton">
			<shiro:hasPermission name="credit:gqgetComInfoUnion:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />
				&nbsp;
			<input id="btnReset" class="btn-primary btn " type="button" value="关 闭" onclick="closeJBox();" />
			</shiro:hasPermission>
		</div>
	</form:form>
</body>
</html>