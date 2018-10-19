<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>还款划扣管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		/**	初始化 */
		$("#queryBankDataId").hide();
		$("[name='deductTypeEQ1']").hide();
		$("[name='deductTypeNE1']").hide();

		$("#inputForm").validate({
		submitHandler : saveForm,
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});
	});
	function saveForm() {
		var AllDeduct = $("#allDeductAmount").val();
		var partDeduct = $("#deductAmount").val();
		var reg = /^([1-9][\d]{0,10}|0)(\.[\d]{1,2})?$/;
		if (!reg.test(partDeduct)) {
			alertx("请输入正确格式的金额");
		} else if (Number(partDeduct) > Number(AllDeduct)) {
			alertx("申请金额过大，请修改。");
		} else {
			var bankData = $("input[name='num']:checked").val();
			if (checkIsNull(bankData)) {
				alertx("请选择一条银行卡信息");
			} else {
				loading();
				top.$.jBox.tip.mess = null;
				var formJson = $("#inputForm").serializeJson();
				formJson['custName'] = bankData;
				formJson['bankcardNo'] = $("input[name='num']:checked").attr("bankcardNo");
				formJson['custId'] = $("input[name='num']:checked").attr("custId");
				formJson['bank'] = $("input[name='num']:checked").attr("bank");
				$.post("${ctx}/accounting/deductApply/save", formJson, function(data) {
					if (data) {
						closeTip();
						if (data.status == 1) {//保存成功
							alertx(data.message, function() {
								closeJBox();
							});
						} else {
							alertx(data.message);
						}
					}
				});
			}
		}
	}
	function queryBankData(idNum, mobileNum) {
		if (!(checkIsNull(idNum)) || !(checkIsNull(mobileNum))) {
			$("#queryBankDataId").show();
			$.loadDiv("queryBankDataId", "${ctx}/accounting/deductApply/queryBankData", {
			idNum : idNum,
			mobileNum : mobileNum
			}, "post");
		}
	}

	function queryMobileNumAndIdNum(typeValue) {
		if ("" == typeValue || null == typeValue) {
			$("[name='deductTypeEQ1']").hide();
			$("[name='deductTypeNE1']").hide();
			$("#deductTypeEQ1MobileNum").val('');
			$("#deductTypeNE1MobileNum").val('');
			$("#queryBankDataId").hide();
		} else if ("10180001" == typeValue) {
			var contractNo = $("#contractNo").val();
			$.ajax({
			url : "${ctx}/accounting/deductApply/queryMobileNumAndIdNum",
			type : "post",
			data:{
				contractNo:contractNo
			},
			async : false,
			dataType : "JSON",
			success : function(data) {
				if (data.status == 1) {
					$("#deductTypeEQ1MobileNum").val(data.mobileNum);
					$("#capitalTerraceNo").val(data.mainCustId);
					$("[name='deductTypeEQ1']").show();
					$("[name='deductTypeNE1']").hide();
					$("#deductTypeNE1MobileNum").val('');
					queryBankData(data.idNum, null);
				} else {
					alertx(data.message);
				}
			}
			});
		} else {
			var contractNo = $("#contractNo").val();
			$.ajax({
			url : "${ctx}/accounting/deductApply/queryMobileNumAndIdNum",
			type : "post",
			data:{
				contractNo:contractNo
			},
			async : false,
			dataType : "JSON",
			success : function(data) {
				if (data.status == 1) {
					$("#capitalTerraceNo").val(data.mainCustId);
					$("[name='deductTypeEQ1']").hide();
					$("#deductTypeEQ1MobileNum").val('');
					$("[name='deductTypeNE1']").show();
					$("#queryBankDataId").hide();
				} else {
					alertx(data.message);
				}
			}
			});
		}

	}
</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="deductApply" action="#" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<input type="hidden" name="dataDt" value="${deductApply.dataDt}" />
		<input type="hidden" name="deductCustId" value="${deductApply.deductCustId}" />
		<input type="hidden" id="contractNo" name="contractNo" value="${deductApply.contractNo}" />
		<input type="hidden" id="capitalTerraceNo" name="capitalTerraceNo" value="" />
		<input type="hidden" name="periodNum" value="${deductApply.periodNum}" />
		<sys:message content="${message}" />
		<h3 class="infoListTitle">还款划扣</h3>
		<div style="text-align: center;">
			<font color="red">
				<b>为确保合规，提前代扣需获得借款人同意!</b>
			</font>
		</div>
		<table class="fromTable filter">
			<tr>
				<td class="ft_label">待还金额：</td>
				<td class="ft_content">
					<form:input path="allDeductAmount" htmlEscape="false" readonly="true" class="input-medium " value="${deductApply.deductAmount == null ? 0:deductApply.deductAmount}" />
				</td>
				<td class="ft_label">划扣金额：</td>
				<td class="ft_content">
					<form:input path="deductAmount" htmlEscape="false" onclick="this.value = ''" class="input-medium required " />
					<font style="color: red">*</font>
				</td>
			</tr>
			<tr>
				<td class="ft_label">划扣类型:</td>
				<td class="ft_content">
					<form:select path="deductType" class="input-medium required" onchange="queryMobileNumAndIdNum(this.value)">
						<form:option value="" label="请选择"></form:option>
						<form:options items="${fns:getDictList('DEDUCT_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
					<font style="color: red">*</font>
				</td>
				<td name="deductTypeEQ1" class="ft_label">手机号：</td>
				<td name="deductTypeEQ1" class="ft_content">
					<form:input path="mobileNum" id="deductTypeEQ1MobileNum" htmlEscape="false" readonly="true" class="input-medium" />
					<font style="color: red">*</font>
				</td>
				<td name="deductTypeNE1" class="ft_label">手机号：</td>
				<td name="deductTypeNE1" class="ft_content">
					<form:input path="mobileNum" id="deductTypeNE1MobileNum" htmlEscape="false" onblur="queryBankData(null,this.value);" class="input-medium number required" />
					<font style="color: red">*</font>
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<div id="queryBankDataId"></div>
				</td>
			</tr>
			<tr>
				<td class="ft_content" colspan="4" style="text-align:center">
					<shiro:hasPermission name="accounting:deductApply:edit">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="划扣" />&nbsp;</shiro:hasPermission>
					<input id="btnCancel" class="btn" type="button" value="关闭" onclick="closeJBox();" />
				</td>
			</tr>
		</table>
	</form:form>
</body>
</html>