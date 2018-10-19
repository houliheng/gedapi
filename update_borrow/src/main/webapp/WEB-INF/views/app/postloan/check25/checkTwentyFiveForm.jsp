<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>25日复核管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {

		if (${flag} == '2' || ${flag} == '11') {
			setDivReadOnly("checkTwentyFiveDiv");
		}

		oneOfTwoRequired();

		if (${checkedType} == '6') {
			$("#accountClean").hide();
		}

		if (!checkIsNull('${checkTwentyFiveInfoVO.checkTwentyFive.checkTwentyFiveResult}')) {
			var value = '${checkTwentyFiveInfoVO.checkTwentyFive.checkTwentyFiveResult}';
			var checkBoxes = $("input[name='checkTwentyFive.checkTwentyFiveResult']");
			getCheckBoxesValue(value, checkBoxes);
		}

		if (!checkIsNull('${checkTwentyFiveInfoVO.checkTwentyFive.checkTwentyFiveType}')) {
			var value = '${checkTwentyFiveInfoVO.checkTwentyFive.checkTwentyFiveType}';
			var checkBoxes = $("input[name='checkTwentyFive.checkTwentyFiveType']");
			getCheckBoxesValue(value, checkBoxes);
		}
	});

	//清收
	function accountCleanAppro() {
		//当选择清收时，隐藏保存按钮
		loading();
		$("#btnSubmit").hide();
		oneOfTwoRequired();
		if ($("#inputForm").valid() == true) {
			var url = "${ctx}/postloan/accountCleanApprove/form?status=apply";
			openJBox("accountCleanApprove-box", url, "清收申请", $(window).width() - 100, $(window).height() - 200, {
				contractNo : '${contractNo }'
			});
		}
		closeTip();
	}

	//法务
	function legal() {
		//当选择法务催收时，隐藏保存按钮
		$("#btnSubmit").hide();
		oneOfTwoRequired();
		var checkResult = getValue();
		var checkType = getValueType();
		if ($("#inputForm").valid() == true) {
			confirmx("您确定要进行法务催收吗？", function() {
				loading();
				top.$.jBox.tip.mess = null;
				var formJson = $("#inputForm").serializeJson();
				formJson["checkTwentyFive.checkTwentyFiveResult"] = checkResult;
				formJson["checkTwentyFive.checkTwentyFiveType"] = checkType;
				$.post("${ctx}/postloan/debtCollection/save", formJson, function(data) {
					if (data) {
						closeTip();
						if (data.status == 1) {//保存成功
							alertx(data.message, function() {
								parent.page();
								closeJBox();
							});
						} else {
							alertx(data.message);
						}
					}
				});
			});
		}
	}

	//签署保证合同
	function signGuarContract() {
		//当选择签署保证合同时，隐藏保存按钮
		$("#btnSubmit").hide();
		oneOfTwoRequired();
		var checkResult = getValue();
		var checkType = getValueType();
		if ($("#inputForm").valid() == true) {
			confirmx("您确定签署保证合同吗？", function() {
				loading();
				top.$.jBox.tip.mess = null;
				var formJson = $("#inputForm").serializeJson();
				formJson["checkTwentyFive.checkTwentyFiveResult"] = checkResult;
				formJson["checkTwentyFive.checkTwentyFiveType"] = checkType;
				$.post("${ctx}/postloan/checkTwentyFiveInfo/save", formJson, function(data) {
					if (data) {
						closeTip();
						if (data.status == 1) {//保存成功
							var url = "${ctx}/postloan/checkTwentyFive/signGuarContract";
							var title = "签署保证合同";
							var width = $(window).width() - 100;
							width = Math.max(width, 800);
							var height = $(window).height() - 100;
							height = Math.min(height, 600);
							openGuarantyContractJBox("signGuarContract-box", url, title, width, height, {
							contractNo : '${contractNo}',
							flag : '${flag}'
							});
						} else {
							alertx(data.message);
						}
					}
				});

			});
		}
	}

	function saveCheckTwentyFiveForm() {
		oneOfTwoRequired();
		$("#inputForm").validate({
		submitHandler : function() {
			loading();
			$("#btnSubmit").attr("disabled", true);
			var checkResult = getValue();
			var checkType = getValueType();
			top.$.jBox.tip.mess = null;
			var formJson = $("#inputForm").serializeJson();
			formJson["checkTwentyFive.checkTwentyFiveResult"] = checkResult;
			formJson["checkTwentyFive.checkTwentyFiveType"] = checkType;
			$.post("${ctx}/postloan/checkTwentyFiveInfo/save", formJson, function(data) {
				if (data) {
					closeTip();
					if (data.status == 1) {//保存成功
						alertx(data.message, function() {
							parent.page();
							closeJBox();
						});
					} else {
						alertx(data.message);
						$("#btnSubmit").removeAttr("disabled");
					}
				}
			});

		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			$("#messageBox").text("输入误，请先更正。");
			checkReq(error, element)
		}
		});

		$("#inputForm").submit();
	}

	function oneOfTwoRequired() {
		$("#checkTwentyFivePhoneType").addClass("required");
		$("#checkTwentyFiveAddressType").addClass("required");
		$("input[name='checkTwentyFive.checkTwentyFiveType']:checked").each(function() {
			if ($(this).val() == "2") {
				$("#checkTwentyFivePhoneType").removeClass("required");
			} else if ($(this).val() == "1") {
				$("#checkTwentyFiveAddressType").removeClass("required");
			}
		});
	}

	function saveCheckTwentyFiveInputForm() {
		loading();
		var checkResult = getValue();
		var checkType = getValueType();
		top.$.jBox.tip.mess = null;
		var formJson = $("#inputForm").serializeJson();
		formJson["checkTwentyFive.checkTwentyFiveResult"] = checkResult;
		formJson["checkTwentyFive.checkTwentyFiveType"] = checkType;
		$.post("${ctx}/postloan/checkTwentyFiveInfo/save?procedure=accountClean", formJson, function(data) {
			if (data) {
				closeTip();
				if (data.status == 1) {//保存成功
					alertx(data.message, function() {
						parent.page();
						closeJBox();
					});
				} else {
					alertx(data.message);
				}
			}
		});
	}

	function getValue() {
		var checkResult = null;
		var checkBoxChecked = $("input[id^='checkTwentyFive.checkTwentyFiveResult']:checked");
		$.each(checkBoxChecked, function(j, checkBox) {
			var checkValue = $(checkBox).val();
			if (checkResult != null) {
				checkResult = checkResult + ',' + checkValue;
			} else {
				checkResult = checkValue;
			}
		});
		return checkResult;
	}
	
	function getValueType() {
		var checkResult = null;
		var checkBoxChecked = $("input[name='checkTwentyFive.checkTwentyFiveType']:checked");
		$.each(checkBoxChecked, function(j, checkBox) {
			var checkValue = $(checkBox).val();
			if (checkResult != null) {
				checkResult = checkResult + ',' + checkValue;
			} else {
				checkResult = checkValue;
			}
		});
		return checkResult;
	}

	//加载复选框
	function checkTwentyFiveResultCheckBoxes() {
		var riskPointStr = '${checkTwentyFiveInfoVO.checkTwentyFive.checkTwentyFiveResult}';
		var riskPointArray = riskPointStr.split(",");
		var checkBoxAll = $("input[name='checkTwentyFive.checkTwentyFiveResult']");
		for (var i = 0; i < riskPointArray.length; i++) {
			$.each(checkBoxAll, function(j, checkBox) {
				var checkValue = $(checkBox).val();
				if (riskPointArray[i] == checkValue) {
					$(checkBox).attr("checked", true);
				}
			});
		}
	}

	function changeProperty(value, id) {
		if (value == "0") {
			$("#" + id + "Remark").attr("required", true);
		} else {
			$("#" + id + "Remark").removeAttr("required");
			if (document.getElementById(id + "Remark").style.background = "pink") {
				document.getElementById(id + "Remark").style.background = "";
			}
		}
	}

	function toSaveCheck(flag) {
		if (flag == "indeed") {
			var url = "${ctx}/postloan/checkIndeed/index";
			var title = "实地外访";
			var id = "saveIndeedIndexBox";
		} else {
			var url = "${ctx}/postloan/plCheckPhone/index";
			var title = "电话外访";
			var id = "savePhoneIndexBox";
		}
		openJBox(id, url, title, $(window).width() - 50, $(window).height() - 50, {
		allocateId : '${allocateId}',
		contractNo : '${contractNo}',
		readOnlyFlag : '${flag}'
		});
	}

	function showCheckIndeedButton() {
		$("input[name='checkTwentyFive.checkTwentyFiveType']:checked").each(function() {
			if ($(this).val() == "2") {
				toSaveCheck("indeed");
			}
		});
	}
	function showCheckPhoneButton() {
		$("input[name='checkTwentyFive.checkTwentyFiveType']:checked").each(function() {
			if ($(this).val() == "1") {
				toSaveCheck("phone");
			}
		});
	}
</script>
<style type="text/css">
.remarkMessage {
	width: 500px;
}
</style>
</head>
<body>
	<c:if test="${flag eq '1'}">
		<%@ include file="/WEB-INF/views/app/postloan/imageUploadAndView/workflowButtons.jsp"%>
	</c:if>
	<div id="checkTwentyFiveDiv" class="wrapper">
		<sys:message content="${message}" />
		<form:form id="inputForm" modelAttribute="checkTwentyFiveInfoVO" action="" method="post" class="form-horizontal">
			<input type="hidden" id="contractNo" name="contractNo" value="${contractNo}" />
			<input type="hidden" id="allocateId" name="allocateId" value="${allocateId}" />
			<input type="hidden" id="custName" name="custName" value="${custName}" />
			<input type="hidden" id="contractAmount" name="contractAmount" value="${contractAmount}" />
			<input type="hidden" id="currCollectionType" name="currCollectionType" value="${currCollectionType}" />
			<input type="hidden" id="currCollectionFrom" name="currCollectionFrom" value="${currCollectionFrom}" />
			<input type="hidden" id="currCollectionStatus" name="currCollectionStatus" value="${currCollectionStatus}" />
			<c:if test="${checkedType eq '6'}">
				<div id='messageBox' class='alert alert-error'>
					<font>请注意：该任务为清收打回任务!</font>
				</div>
			</c:if>
			<div class="filter">
				<h3 class="searchTitle">项目审查</h3>
				<table class="fromable">
					<tr>
						<td class="ft_label">&nbsp;&nbsp;复核方式：</td>
						<td>
							<input type="checkbox" name="checkTwentyFive.checkTwentyFiveType" id="checkTwentyFivePhoneType" class="no-margin" value="1" onclick="oneOfTwoRequired();">
							电话
							<input type="button" id="phoneButton" class=" btn btn-primary" type="button" value="电访详情" onclick="showCheckPhoneButton();" />
							&nbsp;&nbsp;
							<input type="checkbox" name="checkTwentyFive.checkTwentyFiveType" id="checkTwentyFiveAddressType" class="no-margin" value="2" onclick="oneOfTwoRequired();">
							实地
							<input type="button" id="indeedButton" class=" btn btn-primary" type="button" value="实访详情" onclick="showCheckIndeedButton();" />
						</td>
					</tr>
				</table>
				<h3 class="searchTitle">主借人</h3>
				<table class="fromTable">
					<tr>
						<td class="ft_label colspan=2">基本资料：</td>
						<td>
							<form:radiobuttons path="mainBorrower.checkTwentyFiveInfo.checkBase" id="mainBorrowerCheckBase" itemLabel="label" itemValue="value" htmlEscape="false" items="${fns:getDictList('yes_no')}" class="required" onclick="changeProperty(this.value,'mainBorrowerCheckBase');" />
						</td>
						<td class="ft_label colspan=2">基本资料备注：</td>
						<td colspan="3">
							<form:input path="mainBorrower.checkTwentyFiveInfo.checkBaseRemark" class="remarkMessage" id="mainBorrowerCheckBaseRemark" htmlEscape="false" />
						</td>
					</tr>
					<tr>
						<td class="ft_label checkTwentyFive">网查情况：</td>
						<td>
							<form:radiobuttons path="mainBorrower.checkTwentyFiveInfo.checkWeb" id="mainBorrowerCheckWeb" itemLabel="label" itemValue="value" htmlEscape="false" items="${fns:getDictList('yes_no')}" class="required" onclick="changeProperty(this.value,'mainBorrowerCheckWeb');" />
						</td>
						<td class="ft_label checkTwentyFive">网查情况备注：</td>
						<td colspan="3">
							<form:input path="mainBorrower.checkTwentyFiveInfo.checkWebRemark" class="remarkMessage" id="mainBorrowerCheckWebRemark" htmlEscape="false" />
						</td>
					</tr>
					<tr>
						<td class="ft_label colspan=2">家庭情况：</td>
						<td>
							<form:radiobuttons path="mainBorrower.checkTwentyFiveInfo.checkFamily" id="mainBorrowerCheckFamily" itemLabel="label" itemValue="value" htmlEscape="false" items="${fns:getDictList('yes_no')}" class="required" onclick="changeProperty(this.value,'mainBorrowerCheckFamily');" />
						</td>
						<td class="ft_label checkTwentyFive">家庭情况备注：</td>
						<td colspan="3">
							<form:input path="mainBorrower.checkTwentyFiveInfo.checkFamilyRemark" class="remarkMessage" id="mainBorrowerCheckFamilyRemark" htmlEscape="false" />
						</td>
					</tr>
					<tr>
						<td class="ft_label checkTwentyFive">财务状况：</td>
						<td>
							<form:radiobuttons path="mainBorrower.checkTwentyFiveInfo.checkFinancial" id="mainBorrowerCheckFinancial" itemLabel="label" itemValue="value" htmlEscape="false" items="${fns:getDictList('yes_no')}" class="required" onclick="changeProperty(this.value,'mainBorrowerCheckFinancial');" />
						</td>
						<td class="ft_label checkTwentyFive">财务状况备注：</td>
						<td colspan="3">
							<form:input path="mainBorrower.checkTwentyFiveInfo.checkFinancialRemark" class="remarkMessage" id="mainBorrowerCheckFinancialRemark" htmlEscape="false" />
						</td>
					</tr>
					<tr>
						<td class="ft_label checkTwentyFive">隐性负债：</td>
						<td>
							<form:radiobuttons path="mainBorrower.checkTwentyFiveInfo.checkDebt" id="mainBorrowerCheckDebt" itemLabel="label" itemValue="value" htmlEscape="false" items="${fns:getDictList('yes_no')}" class="required" onclick="changeProperty(this.value,'mainBorrowerCheckDebt');" />
						</td>
						<td class="ft_label checkTwentyFive">隐性负债备注：</td>
						<td colspan="3">
							<form:input path="mainBorrower.checkTwentyFiveInfo.checkDebtRemark" class="remarkMessage" id="mainBorrowerCheckDebtRemark" htmlEscape="false" />
						</td>
					</tr>
				</table>
				<h3 class="searchTitle">担保人</h3>
				<table class="fromTable">
					<tr>
						<td class="ft_label colspan=2">基本资料：</td>
						<td>
							<form:radiobuttons path="guarantor.checkTwentyFiveInfo.checkBase" id="guarantorCheckBase" onclick="changeProperty(this.value,'guarantorCheckBase');" itemLabel="label" itemValue="value" htmlEscape="false" items="${fns:getDictList('yes_no')}" class="required" />
						</td>
						<td class="ft_label colspan=2">基本资料备注：</td>
						<td colspan="3">
							<form:input path="guarantor.checkTwentyFiveInfo.checkBaseRemark" class="remarkMessage" id="guarantorCheckBaseRemark" htmlEscape="false" />
						</td>
					</tr>
					<tr>
						<td class="ft_label checkTwentyFive">网查情况：</td>
						<td>
							<form:radiobuttons path="guarantor.checkTwentyFiveInfo.checkWeb" id="guarantorCheckWeb" onclick="changeProperty(this.value,'guarantorCheckWeb');" itemLabel="label" itemValue="value" htmlEscape="false" items="${fns:getDictList('yes_no')}" class="required" />
						</td>
						<td class="ft_label checkTwentyFive">网查情况备注：</td>
						<td colspan="3">
							<form:input path="guarantor.checkTwentyFiveInfo.checkWebRemark" class="remarkMessage" id="guarantorCheckWebRemark" htmlEscape="false" />
						</td>
					</tr>
					<tr>
						<td class="ft_label colspan=2">家庭情况：</td>
						<td>
							<form:radiobuttons path="guarantor.checkTwentyFiveInfo.checkFamily" id="guarantorCheckFamily" onclick="changeProperty(this.value,'guarantorCheckFamily');" itemLabel="label" itemValue="value" htmlEscape="false" items="${fns:getDictList('yes_no')}" class="required" />
						</td>
						<td class="ft_label colspan=2">家庭情况备注：</td>
						<td colspan="3">
							<form:input path="guarantor.checkTwentyFiveInfo.checkFamilyRemark" class="remarkMessage" id="guarantorCheckFamilyRemark" htmlEscape="false" />
						</td>
					</tr>
					<tr>
						<td class="ft_label checkTwentyFive">财务状况：</td>
						<td>
							<form:radiobuttons path="guarantor.checkTwentyFiveInfo.checkFinancial" id="guarantorCheckFinancial" onclick="changeProperty(this.value,'guarantorCheckFinancial');" itemLabel="label" itemValue="value" htmlEscape="false" items="${fns:getDictList('yes_no')}" class="required" />
						</td>
						<td class="ft_label checkTwentyFive">财务状况备注：</td>
						<td colspan="3">
							<form:input path="guarantor.checkTwentyFiveInfo.checkFinancialRemark" class="remarkMessage" id="guarantorCheckFinancialRemark" htmlEscape="false" />
						</td>
					</tr>
				</table>
				<h3 class="searchTitle">主借企业</h3>
				<table class="fromTable">
					<tr>
						<td class="ft_label colspan=2">基本资料：</td>
						<td>
							<form:radiobuttons path="mainBorrowerCompany.checkTwentyFiveInfo.checkBase" id="mainBorrowerCompanyCheckBase" onclick="changeProperty(this.value,'mainBorrowerCompanyCheckBase');" itemLabel="label" itemValue="value" htmlEscape="false" items="${fns:getDictList('yes_no')}" class="required" />
						</td>
						<td class="ft_label colspan=2">基本资料备注：</td>
						<td colspan="3">
							<form:input path="mainBorrowerCompany.checkTwentyFiveInfo.checkBaseRemark" class="remarkMessage" id="mainBorrowerCompanyCheckBaseRemark" htmlEscape="false" />
						</td>
					</tr>
					<tr>
						<td class="ft_label checkTwentyFive">网查情况：</td>
						<td>
							<form:radiobuttons path="mainBorrowerCompany.checkTwentyFiveInfo.checkWeb" id="mainBorrowerCompanyCheckWeb" onclick="changeProperty(this.value,'mainBorrowerCompanyCheckWeb');" itemLabel="label" itemValue="value" htmlEscape="false" items="${fns:getDictList('yes_no')}" class="required" />
						</td>
						<td class="ft_label checkTwentyFive">网查情况备注：</td>
						<td colspan="3">
							<form:input path="mainBorrowerCompany.checkTwentyFiveInfo.checkWebRemark" class="remarkMessage" id="mainBorrowerCompanyCheckWebRemark" htmlEscape="false" />
						</td>
					</tr>
					<tr>
						<td class="ft_label colspan=2">借款用途：</td>
						<td>
							<form:radiobuttons path="mainBorrowerCompany.checkTwentyFiveInfo.checkLoanPurpost" id="mainBorrowerCompanyCheckLoanPurpost" onclick="changeProperty(this.value,'mainBorrowerCompanyCheckLoanPurpost');" itemLabel="label" itemValue="value" htmlEscape="false" items="${fns:getDictList('yes_no')}" class="required" />
						</td>
						<td class="ft_label colspan=2">借款用途备注：</td>
						<td colspan="3">
							<form:input path="mainBorrowerCompany.checkTwentyFiveInfo.checkLoanPurpostRemark" class="remarkMessage" id="mainBorrowerCompanyCheckLoanPurpostRemark" htmlEscape="false" />
						</td>
					</tr>
					<tr>
						<td class="ft_label checkTwentyFive">银行流水：</td>
						<td>
							<form:radiobuttons path="mainBorrowerCompany.checkTwentyFiveInfo.checkBank" id="mainBorrowerCompanyCheckBank" onclick="changeProperty(this.value,'mainBorrowerCompanyCheckBank');" itemLabel="label" itemValue="value" htmlEscape="false" items="${fns:getDictList('yes_no')}" class="required" />
						</td>
						<td class="ft_label checkTwentyFive">银行流水备注：</td>
						<td colspan="3">
							<form:input path="mainBorrowerCompany.checkTwentyFiveInfo.checkBankRemark" class="remarkMessage" id="mainBorrowerCompanyCheckBankRemark" htmlEscape="false" />
						</td>
					</tr>
					<tr>
						<td class="ft_label colspan=2">征信报告：</td>
						<td>
							<form:radiobuttons path="mainBorrowerCompany.checkTwentyFiveInfo.checkCredit" id="mainBorrowerCompanyCheckCredit" onclick="changeProperty(this.value,'mainBorrowerCompanyCheckCredit');" itemLabel="label" itemValue="value" htmlEscape="false" items="${fns:getDictList('yes_no')}" class="required" />
						</td>
						<td class="ft_label colspan=2">征信报告备注：</td>
						<td colspan="3">
							<form:input path="mainBorrowerCompany.checkTwentyFiveInfo.checkCreditRemark" class="remarkMessage" id="mainBorrowerCompanyCheckCreditRemark" htmlEscape="false" />
						</td>
					</tr>
					<tr>
						<td class="ft_label checkTwentyFive">资产情况：</td>
						<td>
							<form:radiobuttons path="mainBorrowerCompany.checkTwentyFiveInfo.checkProperty" id="mainBorrowerCompanyCheckProperty" onclick="changeProperty(this.value,'mainBorrowerCompanyCheckProperty');" itemLabel="label" itemValue="value" htmlEscape="false" items="${fns:getDictList('yes_no')}" class="required" />
						</td>
						<td class="ft_label checkTwentyFive">资产情况备注：</td>
						<td colspan="3">
							<form:input path="mainBorrowerCompany.checkTwentyFiveInfo.checkPropertyRemark" class="remarkMessage" id="mainBorrowerCompanyCheckPropertyRemark" htmlEscape="false" />
						</td>
					</tr>
					<tr>
						<td class="ft_label colspan=2">抵质押品：</td>
						<td>
							<form:radiobuttons path="mainBorrowerCompany.checkTwentyFiveInfo.checkMortgage" id="mainBorrowerCompanyCheckMortgage" onclick="changeProperty(this.value,'mainBorrowerCompanyCheckMortgage');" itemLabel="label" itemValue="value" htmlEscape="false" items="${fns:getDictList('yes_no')}" cssClass="required" />
						</td>
						<td class="ft_label colspan=2">抵质押品备注：</td>
						<td colspan="3">
							<form:input path="mainBorrowerCompany.checkTwentyFiveInfo.checkMortgageRemark" class="remarkMessage" id="mainBorrowerCompanyCheckMortgageRemark" htmlEscape="false" />
						</td>
					</tr>
					<tr>
						<td class="ft_label checkTwentyFive">经营情况：</td>
						<td>
							<form:radiobuttons path="mainBorrowerCompany.checkTwentyFiveInfo.checkOperate" id="mainBorrowerCompanyCheckOperate" onclick="changeProperty(this.value,'mainBorrowerCompanyCheckOperate');" itemLabel="label" itemValue="value" htmlEscape="false" items="${fns:getDictList('yes_no')}" cssClass="required" />
						</td>
						<td class="ft_label checkTwentyFive">经营情况备注：</td>
						<td colspan="3">
							<form:input path="mainBorrowerCompany.checkTwentyFiveInfo.checkOperateRemark" class="remarkMessage" id="mainBorrowerCompanyCheckOperateRemark" htmlEscape="false" />
						</td>
					</tr>
				</table>
				<h3 class="searchTitle">担保企业</h3>
				<table class="fromTable">
					<tr>
						<td class="ft_label colspan=2">基本资料：</td>
						<td>
							<form:radiobuttons path="guarantorCompany.checkTwentyFiveInfo.checkBase" id="guarantorCompanyCheckBase" onclick="changeProperty(this.value,'guarantorCompanyCheckBase');" itemLabel="label" itemValue="value" htmlEscape="false" items="${fns:getDictList('yes_no')}" class="required" />
						</td>
						<td class="ft_label colspan=2">基本资料备注：</td>
						<td colspan="3">
							<form:input path="guarantorCompany.checkTwentyFiveInfo.checkBaseRemark" class="remarkMessage" id="guarantorCompanyCheckBaseRemark" htmlEscape="false" />
						</td>
					</tr>
					<tr>
						<td class="ft_label checkTwentyFive">网查情况：</td>
						<td>
							<form:radiobuttons path="guarantorCompany.checkTwentyFiveInfo.checkWeb" id="guarantorCompanyCheckWeb" onclick="changeProperty(this.value,'guarantorCompanyCheckWeb');" itemLabel="label" itemValue="value" htmlEscape="false" items="${fns:getDictList('yes_no')}" class="required" />
						</td>
						<td class="ft_label checkTwentyFive">网查情况备注：</td>
						<td colspan="3">
							<form:input path="guarantorCompany.checkTwentyFiveInfo.checkWebRemark" class="remarkMessage" id="guarantorCompanyCheckWebRemark" htmlEscape="false" />
						</td>
					</tr>
					<tr>
						<td class="ft_label colspan=2">借款用途：</td>
						<td>
							<form:radiobuttons path="guarantorCompany.checkTwentyFiveInfo.checkLoanPurpost" id="guarantorCompanyCheckLoanPurpost" onclick="changeProperty(this.value,'guarantorCompanyCheckLoanPurpost');" itemLabel="label" itemValue="value" htmlEscape="false" items="${fns:getDictList('yes_no')}" class="required" />
						</td>
						<td class="ft_label colspan=2">借款用途备注：</td>
						<td colspan="3">
							<form:input path="guarantorCompany.checkTwentyFiveInfo.checkLoanPurpostRemark" class="remarkMessage" id="guarantorCompanyCheckLoanPurpostRemark" htmlEscape="false" />
						</td>
					</tr>
					<tr>
						<td class="ft_label checkTwentyFive">银行流水：</td>
						<td>
							<form:radiobuttons path="guarantorCompany.checkTwentyFiveInfo.checkBank" id="guarantorCompanyCheckBank" onclick="changeProperty(this.value,'guarantorCompanyCheckBank');" itemLabel="label" itemValue="value" htmlEscape="false" items="${fns:getDictList('yes_no')}" class="required" />
						</td>
						<td class="ft_label checkTwentyFive">银行流水备注：</td>
						<td colspan="3">
							<form:input path="guarantorCompany.checkTwentyFiveInfo.checkBankRemark" class="remarkMessage" id="guarantorCompanyCheckBankRemark" htmlEscape="false" />
						</td>
					</tr>
					<tr>
						<td class="ft_label colspan=2">征信报告：</td>
						<td>
							<form:radiobuttons path="guarantorCompany.checkTwentyFiveInfo.checkCredit" id="guarantorCompanyCheckCredit" onclick="changeProperty(this.value,'guarantorCompanyCheckCredit');" itemLabel="label" itemValue="value" htmlEscape="false" items="${fns:getDictList('yes_no')}" class="required" />
						</td>
						<td class="ft_label colspan=2">征信报告备注：</td>
						<td colspan="3">
							<form:input path="guarantorCompany.checkTwentyFiveInfo.checkCreditRemark" class="remarkMessage" id="guarantorCompanyCheckCreditRemark" htmlEscape="false" />
						</td>
					</tr>
					<tr>
						<td class="ft_label checkTwentyFive">资产情况：</td>
						<td>
							<form:radiobuttons path="guarantorCompany.checkTwentyFiveInfo.checkProperty" id="guarantorCompanyCheckProperty" onclick="changeProperty(this.value,'guarantorCompanyCheckProperty');" itemLabel="label" itemValue="value" htmlEscape="false" items="${fns:getDictList('yes_no')}" class="required" />
						</td>
						<td class="ft_label checkTwentyFive">资产情况备注：</td>
						<td colspan="3">
							<form:input path="guarantorCompany.checkTwentyFiveInfo.checkPropertyRemark" class="remarkMessage" id="guarantorCompanyCheckPropertyRemark" htmlEscape="false" />
						</td>
					</tr>
					<tr>
						<td class="ft_label colspan=2">抵质押品：</td>
						<td>
							<form:radiobuttons path="guarantorCompany.checkTwentyFiveInfo.checkMortgage" id="guarantorCompanyCheckMortgage" onclick="changeProperty(this.value,'guarantorCompanyCheckMortgage');" itemLabel="label" itemValue="value" htmlEscape="false" items="${fns:getDictList('yes_no')}" class="required" />
						</td>
						<td class="ft_label colspan=2">抵质押品备注：</td>
						<td colspan="3">
							<form:input path="guarantorCompany.checkTwentyFiveInfo.checkMortgageRemark" class="remarkMessage" id="guarantorCompanyCheckMortgageRemark" htmlEscape="false" />
						</td>
					</tr>
					<tr>
						<td class="ft_label checkTwentyFive">经营情况：</td>
						<td>
							<form:radiobuttons path="guarantorCompany.checkTwentyFiveInfo.checkOperate" id="guarantorCompanyCheckOperate" onclick="changeProperty(this.value,'guarantorCompanyCheckOperate');" itemLabel="label" itemValue="value" htmlEscape="false" items="${fns:getDictList('yes_no')}" class="required" />
						</td>
						<td class="ft_label checkTwentyFive">经营情况备注：</td>
						<td colspan="3">
							<form:input path="guarantorCompany.checkTwentyFiveInfo.checkOperateRemark" class="remarkMessage" id="guarantorCompanyCheckOperateRemark" htmlEscape="false" />
						</td>
					</tr>
				</table>
				<h3 class="searchTitle">合规性</h3>
				<table class="fromTable">
					<tr>
						<td class="ft_label colspan=2">建档情况：</td>
						<td>
							<form:radiobuttons path="compliance.checkTwentyFiveInfo.checkArchive" id="complianceCheckArchive" onclick="changeProperty(this.value,'complianceCheckArchive');" itemLabel="label" itemValue="value" htmlEscape="false" items="${fns:getDictList('yes_no')}" class="required" />
						</td>
						<td class="ft_label colspan=2">建档情况备注：</td>
						<td colspan="3">
							<form:input path="compliance.checkTwentyFiveInfo.checkArchiveRemark" class="remarkMessage" id="complianceCheckArchiveRemark" htmlEscape="false" />
						</td>
					</tr>
					<tr>
						<td class="ft_label checkTwentyFive">流程合规性：</td>
						<td>
							<form:radiobuttons path="compliance.checkTwentyFiveInfo.checkProc" id="complianceCheckProc" onclick="changeProperty(this.value,'complianceCheckProc');" itemLabel="label" itemValue="value" htmlEscape="false" items="${fns:getDictList('yes_no')}" class="required" />
						</td>
						<td class="ft_label checkTwentyFive">流程合规性备注：</td>
						<td colspan="3">
							<form:input path="compliance.checkTwentyFiveInfo.checkProcRemark" class="remarkMessage" id="complianceCheckProcRemark" htmlEscape="false" />
						</td>
					</tr>
					<tr>
						<td class="ft_label checkTwentyFive">信访咨询费：</td>
						<td>
							<form:radiobuttons path="compliance.checkTwentyFiveInfo.checkGetFee" id="complianceCheckGetFee" onclick="changeProperty(this.value,'complianceCheckGetFee');" itemLabel="label" itemValue="value" htmlEscape="false" items="${fns:getDictList('yes_no')}" class="required" />
						</td>
						<td class="ft_label checkTwentyFive">信访咨询费备注：</td>
						<td colspan="3">
							<form:input path="compliance.checkTwentyFiveInfo.checkGetFeeRemark" class="remarkMessage" id="complianceCheckGetFeeRemark" htmlEscape="false" />
						</td>
					</tr>
					<tr>
						<td class="ft_label checkTwentyFive">实地外访：</td>
						<td>
							<form:radiobuttons path="compliance.checkTwentyFiveInfo.checkGetAddress" id="complianceCheckGetAddress" onclick="changeProperty(this.value,'complianceCheckGetAddress');" itemLabel="label" itemValue="value" htmlEscape="false" items="${fns:getDictList('yes_no')}" class="required" />
						</td>
						<td class="ft_label checkTwentyFive">实地外访备注：</td>
						<td colspan="3">
							<form:input path="compliance.checkTwentyFiveInfo.checkGetAddressRemark" class="remarkMessage" id="complianceCheckGetAddressRemark" htmlEscape="false" />
						</td>
					</tr>
					<tr>
						<td class="ft_label checkTwentyFive">签约条件执行情况：</td>
						<td>
							<form:radiobuttons path="compliance.checkTwentyFiveInfo.checkSign" id="complianceCheckSign" onclick="changeProperty(this.value,'complianceCheckSign');" itemLabel="label" itemValue="value" htmlEscape="false" items="${fns:getDictList('yes_no')}" class="required" />
						</td>
						<td class="ft_label checkTwentyFive">签约条件执行情况备注：</td>
						<td colspan="3">
							<form:input path="compliance.checkTwentyFiveInfo.checkSignRemark" class="remarkMessage" id="complianceCheckSignRemark" htmlEscape="false" />
						</td>
					</tr>
				</table>
				<h3 class="searchTitle">借后管理建议</h3>
				<table class="fromtable" style="width:100%">
					<tr>
						<td class="ft_label colspan=2">审核意见：</td>
						<td>
							<form:checkboxes path="checkTwentyFive.checkTwentyFiveResult" itemLabel="label" itemValue="value" htmlEscape="false" items="${fns:getDictList('CHECK_RESULT')}" class="required"></form:checkboxes>
						</td>
					</tr>
					<tr>
						<td class="ft_label colspan=2">具体建议：</td>
						<td>
							<form:textarea path="checkTwentyFive.checkTwentyFiveAdvice" rows="4" class="textareaWidth required" />
						</td>
					</tr>
					<tr rowspan="2"></tr>
				</table>
				<div class="searchButton" style="text-align:center">
					<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="saveCheckTwentyFiveForm();" />
					&nbsp;
					<input id="accountClean" class="btn btn-primary" type="button" value="清收" onclick="accountCleanAppro();" />
					&nbsp;
					<input id="btnSubmit3" class="btn btn-primary" type="button" value="法务" onclick="legal();" />
					&nbsp;
					<input id="signContract" class="btn btn-primary" style="width:100px" type="button" value="签署保证合同" onclick="signGuarContract()" />
					&nbsp;
				</div>
		</form:form>
	</div>
	</div>
</body>
</html>