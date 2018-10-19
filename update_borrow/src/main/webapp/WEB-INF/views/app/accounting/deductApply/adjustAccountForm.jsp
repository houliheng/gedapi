<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>冲正流水管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#inputForm").validate({
		submitHandler : saveForm,
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});
	});
	function saveForm() {
		loading();
		top.$.jBox.tip.mess = null;
		var formJson = $("#inputForm").serializeJson();
		$.post("${ctx}/accounting/deductApply/saveAdjustAccount", formJson, function(data) {
			if (data) {
				closeTip();
				if (data.status == 1) {//保存成功
					alertx(data.message, function() {
						closeAndReloadACC();
					});
				} else {
					alertx(data.message);
				}
			}
		});
	}

	function closeBoxAndDeleteLock() {
		loading();
		var contractNo = document.getElementById("contractNo").value;
		$.ajax({
		url : "${ctx}/accounting/deductApply/deleteLock",
		type:"post",
		data:{
			contractNo:contractNo
		},
		async : false,
		dataType : "JSON",
		success : function(data) {
			closeTip();
			if (data.status == 1) {
				closeJBox();
			} else {
				alertx(data.message);
			}
		}
		});
	}
</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="deductApplyVO" action="#" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<form:hidden path="deductApplyNo" />
		<div class="filter">
			<h3 class="infoListTitle">冲正流水</h3>
			<table class="filter">
				<tr>
					<td class="ft_label">客户名称：</td>
					<td class="ft_content">
						<form:input path="custName" htmlEscape="false" readonly="true" class="input-middle" />
					</td>
					<td class="ft_label">合同号：</td>
					<td class="ft_content">
						<form:input path="contractNo" htmlEscape="false" readonly="true" class="input-middle" />
					</td>
					<td class="ft_label">流水号：</td>
					<td class="ft_content">
						<form:input path="streamNo" htmlEscape="false" readonly="true" class="input-middle" />
					</td>
				</tr>
				<tr>
					<td class="ft_label">划扣金额：</td>
					<td class="ft_content">
						<form:input path="deductAmountResult" htmlEscape="false" readonly="true" class="input-middle" />
					</td>
					<td class="ft_label">划扣日期：</td>
					<td class="ft_content">
						<form:input path="streamTimeStr" htmlEscape="false" readonly="true" class="input-middle" />
					</td>
					<td class="ft_label">划扣人：</td>
					<td class="ft_content">
						<form:input path="deductCustName" htmlEscape="false" readonly="true" class="input-middle" />
						<form:hidden path="deductCustId"   />
					</td>
				</tr>
				<tr>
					<td class="ft_label">冲正原因：</td>
					<td class="ft_content" colspan="5">
						<form:textarea path="description" htmlEscape="false" rows="4" maxlength="1000" class="input-medium required " style="width:91%" />
						<font style="color: red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_content" colspan="6" style="text-align:center">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="冲正" />
						&nbsp;
						<input id="btnCancel" class="btn" type="button" value="关闭" onclick="closeBoxAndDeleteLock();" />
					</td>
				</tr>
			</table>
		</div>
	</form:form>
</body>
</html>