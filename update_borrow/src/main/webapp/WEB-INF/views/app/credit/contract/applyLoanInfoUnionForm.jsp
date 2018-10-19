<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>合同信息管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		//---
		var specialServiceFee = $("#specialServiceFee").val();
		if(specialServiceFee==null||specialServiceFee==""||specialServiceFee=="undefined"){
			$("#specialServiceFee").val("0");
		}
		var specialServiceFeeRate = $("#specialServiceFeeRate").val();
		if(specialServiceFeeRate==null||specialServiceFeeRate==""||specialServiceFeeRate=="undefined"){
			$("#specialServiceFeeRate").val("0");
		}
		//---
		if ("${contract.contractNo}" == null || "${contract.contractNo}" == "") {
			$("#contractTmp").hide();
		}
		//document.getElementById("applyAmount").value = outputmoney("${applyInfo.applyAmount}");
		document.getElementById("contractAmount").value = outputmoney("${contract.contractAmount}");
		document.getElementById("loanAmount").value = outputmoney("${contract.loanAmount}");
		document.getElementById("marginAmount").value = outputmoney("${contract.marginAmount}");
		document.getElementById("checkFee").value = outputmoney("${contract.checkFee}");
		document.getElementById("serviceFee").value = outputmoney("${contract.serviceFee}");
		if (document.getElementById("specialServiceFee")) {
			document.getElementById("specialServiceFee").value = outputmoney("${contract.specialServiceFee}");
		}
		document.getElementById("allServiceFee").value = outputmoney("${contract.allServiceFee}");

		document.getElementById("factLoanAmount").value = outputmoney("${contract.loanAmount}");

		if (${contract.serviceFeeType} == 1) {
			document.getElementById("receiveAmount").value = outputmoney("${contract.receiveAmount}");
		} else {
			document.getElementById("receiveAmount").value = outputmoney("${contract.marginAmount}");
		}
		$("#openAccountStatus").prop("disabled", true).select2();
		getInfo();
		adjustTextareaLength('approResult', 'approResultPre');
		adjustTextareaLength('description', 'descriptionPre');
		var ORG_PLATFORM = $("#contractOrgPlatform").val();
		$("#operateOtherDesc").attr("readOnly", "readOnly");
		if (ORG_PLATFORM == "10040099") {
			$("#operateOtherDesc").removeAttr("readOnly");
		}
		var isAccord = $("input[name='isAccord']:checked").val();
		if (isAccord == '1') {
			setDivReadOnly("repBankCardInfoDiv");
		}
		//$("#name").focus();
		$("#contractForm").validate({
		submitHandler : function(form) {
			fmtBigdecimalFunc();
			saveForm();
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});

		checkboxSelect();

		//信用标不显示抵押权人
		isLoadMortgagedPerson();
		adjustTextareaLength("remark", "preRemark");

		new CitySelect({
		data : data,
		provId : '#recBankProvince',
		cityId : '#recBankCity',
		areaId : '#recBankDistinct',
		isSelect : false
		});
		new CitySelect({
		data : data,
		provId : '#repBankProvince',
		cityId : '#repBankCity',
		areaId : '#repBankDistinct',
		isSelect : false
		});

		new CitySelect({
		data : data,
		provId : '#contractProvince',
		cityId : '#contractCity',
		areaId : '#contractDistinct',
		isSelect : false
		});
		if (!checkIsNull('${contract.contractDistinct}')) {
			setName(data, "contract", '${contract.contractProvince}', '${contract.contractCity}', '${contract.contractDistinct}');
		}
		if (!checkIsNull('${contract.recBankDistinct}')) {
			setName(data, "recBank", '${contract.recBankProvince}', '${contract.recBankCity}', '${contract.recBankDistinct}');
			if (!checkIsNull('${contract.repBankDistinct}')) {
				setName(data, "repBank", '${contract.repBankProvince}', '${contract.repBankCity}', '${contract.repBankDistinct}');
			}
		}

	});

	function loadData() {
		var $isAccord = $("input[name='isAccord']:checked").val();
		if (1 == $isAccord) {
			$("#repBankProvince").val($("#recBankProvince").val());
			$("#s2id_repBankProvince>.select2-choice>.select2-chosen").html($("#s2id_recBankProvince>.select2-choice>.select2-chosen").html());

			$("#repBankCity").append($("#recBankCity").html());
			$("#repBankCity").val($("#recBankCity").val());
			$("#s2id_repBankCity>.select2-choice>.select2-chosen").html($("#s2id_recBankCity>.select2-choice>.select2-chosen").html());

			$("#repBankDistinct").append($("#recBankDistinct").html());
			$("#repBankDistinct").val($("#recBankDistinct").val());
			$("#s2id_repBankDistinct>.select2-choice>.select2-chosen").html($("#s2id_recBankDistinct>.select2-choice>.select2-chosen").html());
			$("#repBankDetail").val($("#recBankDetail").val());
		}
	}

	// 去掉数字格式化后的逗号
	function fmtBigdecimalFunc() {
		//申请金额(元)
		/* var applyAmount = $("#applyAmount").val().replace(/,/g, "");
		$("#applyAmount").val(applyAmount); */

		//合同金额(元)
		var contractAmount = $("#contractAmount").val().replace(/,/g, "");
		$("#contractAmount").val(contractAmount);

		//放款金额(元)
		var loanAmount = $("#loanAmount").val().replace(/,/g, "");
		$("#loanAmount").val(loanAmount);

		//保证金
		var marginAmount = $("#marginAmount").val().replace(/,/g, "");
		$("#marginAmount").val(marginAmount);

		//外访费
		var checkFee = $("#checkFee").val().replace(/,/g, "");
		$("#checkFee").val(checkFee);

		//服务费
		var serviceFee = $("#serviceFee").val().replace(/,/g, "");
		$("#serviceFee").val(serviceFee);

		//特殊服务费
		if (!checkIsNull($("#specialServiceFee").val())) {
			var specialServiceFee = $("#specialServiceFee").val().replace(/,/g, "");
			$("#specialServiceFee").val(specialServiceFee);
		}

		//服务费总计
		var allServiceFee = $("#allServiceFee").val().replace(/,/g, "");
		$("#allServiceFee").val(allServiceFee);

		//实际放款金额(元)、转到银行卡金额(元)
		var factLoanAmount = $("#factLoanAmount").val().replace(/,/g, "");
		$("#factLoanAmount").val(factLoanAmount);

		//收到现金金额(元)
		var receiveAmount = $("#receiveAmount").val().replace(/,/g, "");
		$("#receiveAmount").val(receiveAmount);

	}

	function recBankLoadCity() {
		$("#recBankCity").empty();
		$("#recBankCity").append("<option value=''>请选择</option>");
		var $recBankCity = $("#s2id_recBankCity>.select2-choice>.select2-chosen");
		$recBankCity.html("请选择");
		$.post("${ctx}/sys/area/treeNode", {
			parentId : $("#recBankProvince").val()
		}, function(data) {
			$.each(data, function(i, val) {
				$("#recBankCity").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
			});
			recBankLoadDistinct();
		});
	}

	function recBankLoadDistinct() {
		$("#recBankDistinct").empty();
		$("#recBankDistinct").append("<option value=''>请选择</option>");
		var $recBankDistinct = $("#s2id_recBankDistinct>.select2-choice>.select2-chosen");
		$recBankDistinct.html("请选择");

		$.post("${ctx}/sys/area/treeNode", {
			parentId : $("#recBankCity").val()
		}, function(data) {
			$.each(data, function(i, val) {
				$("#recBankDistinct").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
			});
		});
	}

	function repBankLoadCity() {
		$("#repBankCity").empty();
		$("#repBankCity").append("<option value=''>请选择</option>");
		var $repBankCity = $("#s2id_repBankCity>.select2-choice>.select2-chosen");
		$repBankCity.html("请选择");
		$.post("${ctx}/sys/area/treeNode", {
			parentId : $("#repBankProvince").val()
		}, function(data) {
			$.each(data, function(i, val) {
				$("#repBankCity").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
			});
			repBankLoadDistinct();
		});
	}

	function repBankLoadDistinct() {
		$("#repBankDistinct").empty();
		$("#repBankDistinct").append("<option value=''>请选择</option>");
		var $repBankDistinct = $("#s2id_repBankDistinct>.select2-choice>.select2-chosen");
		$repBankDistinct.html("请选择");

		$.post("${ctx}/sys/area/treeNode", {
			parentId : $("#repBankCity").val()
		}, function(data) {
			$.each(data, function(i, val) {
				$("#repBankDistinct").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
			});
		});
	}

	function contractLoadCity() {
		$("#contractCity").empty();
		$("#contractCity").append("<option value=''>请选择</option>");
		var $contractCity = $("#s2id_contractCity>.select2-choice>.select2-chosen");
		$contractCity.html("请选择");
		$.post("${ctx}/sys/area/treeNode", {
			parentId : $("#contractProvince").val()
		}, function(data) {
			$.each(data, function(i, val) {
				$("#contractCity").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
			});
			contractLoadDistinct();
		});
	}

	function contractLoadDistinct() {
		$("#contractDistinct").empty();
		$("#contractDistinct").append("<option value=''>请选择</option>");
		var $contractDistinct = $("#s2id_contractDistinct>.select2-choice>.select2-chosen");
		$contractDistinct.html("请选择");

		$.post("${ctx}/sys/area/treeNode", {
			parentId : $("#contractCity").val()
		}, function(data) {
			$.each(data, function(i, val) {
				$("#contractDistinct").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
			});
		});
	}

	//根据抵押权人的id获取其他信息
	function getInfo() {

		var mortgageeValue = $("#mortgageeId").val();
		if (mortgageeValue == "") {
			$("#mortgageeName").val("");
			$("#mortgageeIdNum").val("");
			$("#mortgageeMobileNum").val("");
			$("#bankcardNo").val("");
			$("#bankNo").val("");
			$("#bankDetailName").val("");
			$("#contProvinceName").val("");
			$("#contCityName").val("");
			$("#contDistinctName").val("");
			$("#contProvince").val("");
			$("#contCity").val("");
			$("#contDistinct").val("");
			$("#contDetails").val("");
			$("#mortgageeName").val("");
			$("#mortgageeCapTerNo").val("");
		} else {
			$.post("${ctx}/credit/mortgagedperson/mortgagedPersonSelected", {
				"id" : $("#mortgageeId").val()
			}, function(data) {
				$("#mortgageeName").attr("value", data.mortgageeName);
				$("#mortgageeIdNum").attr("value", data.idNum);
				$("#mortgageeMobileNum").attr("value", data.mobileNum);
				$("#bankcardNo").attr("value", data.bankcardNo);
				$("#bankNo").attr("value", data.bankNo);
				$("#bankDetailName").attr("value", data.bankDetailName);
				$("#contProvinceName").attr("value", data.contProvince);
				$("#contCityName").attr("value", data.contCity);
				$("#contDistinctName").attr("value", data.contDistinct);
				$("#contProvince").attr("value", data.contProvinceCode);
				$("#contCity").attr("value", data.contCityCode);
				$("#contDistinct").attr("value", data.contDistinctCode);
				$("#contDetails").attr("value", data.contDetails);
				$("#mortgageeName").attr("value", data.mortgageeName);
				$("#mortgageeCapTerNo").attr("value", data.capitalTerraceNo);

			});
		}
	}

	//根据值让checkbox选中
	function checkboxSelect() {
		var checkboxValues = '${contract.signContracts}';
		var checkboxValuesArray = new Array();
		checkboxValuesArray = checkboxValues.split(",");
		for (var i = 0; i < checkboxValuesArray.length; i++) {
			check(checkboxValuesArray[i]);
		}
	}
	function check(name) {
		$(":checkbox").each(function() {
			if ($(this).val() == name) {
				$(this).attr("checked", true);
			}

		})
	}

	function checkForm() {
		//校验收款银行卡号是否一致
		if ($("#recBankcardNo").val() != $("#recBankcardNoConfirm").val()) {
			alertx("收款银行卡号不一致！");
			return false;
		}
		//校验还款银行卡号是否一致
		if ($("#repBankcardNo").val() != $("#repBankcardNoConfirm").val()) {
			alertx("还款银行卡号不一致！");
			return false;
		}

		return true;

	}

	function isLoadMortgagedPerson() {
		if ('${contract.approProductTypeCode}' == '1' || '${contract.approProductTypeName}' == '信用') {
			$("#mortgageInfoDiv").hide();
		} else {
			$("#mortgageInfoDiv").show();
		}
	}
</script>
<script type="text/javascript">
	$(document).ready(function() {
		//还款计划表
		$.loadDiv("repayPlanList", "${ctx }/credit/repayPlanUnion/getRepayPlanByParam", {
		applyNo : "${actTaskParam.applyNo}",
		taskDefKey : "utask_htmq",
		approId : "${contract.approId}"
		}, "post");
	});
</script>
<!-- 合同面签阶段 -->
<c:if test="${taskDefKeyFlag == 'utask_htmq'}">
	<script type="text/javascript">
		$(document).ready(function() {
			$("#legalCheckSuggestionDiv").hide();
			$("#contractCheckSuggestionDiv").hide();
		});
	</script>
</c:if>
<!-- 法务审核阶段 -->
<c:if test="${taskDefKeyFlag == 'utask_fwsh'}">
	<script type="text/javascript">
		$(document).ready(function() {
			$("#contractSignSuggestionDiv").hide();
			$("#contractCheckSuggestionDiv").hide();
			setDivReadOnly("readOnlyDiv");

		});
	</script>
</c:if>
<!-- 合同审核阶段 -->
<c:if test="${taskDefKeyFlag == 'utask_htsh'}">
	<script type="text/javascript">
		$(document).ready(function() {
			$("#contractSignSuggestionDiv").hide();
			$("#legalCheckSuggestionDiv").hide();
			setDivReadOnly("readOnlyDiv");
		});
	</script>
</c:if>
<script type="text/javascript">
	function contractClick() {
		$("#contractId").toggle(600);
	}
	function pfxxClick() {
		$("#pfxxId").toggle(600);
	}
	function htqdxxClick() {
		$("#htqdxxId").toggle(600);
	}
	function dyqrxxClick() {
		$("#dyqrxxId").toggle(600);
	}
	function jkryhkxxClick() {
		$("#jkryhkxxId").toggle(600);
	}
	function mqjlClick() {
		$("#mqjlId").toggle(600);
	}

	//还款账户同收款账户
	function loadBankCardInfo() {
		var flag = $("input[name='isAccord']:checked").val();
		if (checkIsNull(flag)) {
			flag = 0;
		}
		if (flag == 1) {//是 将收款账户信息复制到还款账户 并设置不可编辑
			$("#repBank").val($("#recBank").val());
			$("#repeiveAmount").val($("#receiveAmount").val());
			$("#repBankcardNo").val($("#recBankcardNo").val());
			$("#repBankcardName").val($("#recBankcardName").val());
			$("#repBankName").val($("#recBankName").val());
			$("#repBankMobile").val($("#recBankMobile").val());
			$("#repBankIdNum").val($("#recBankIdNum").val());
			$("#repBankDetail").val($("#recBankDetail").val());
			$("#repBankcardNoConfirm").val($("#recBankcardNoConfirm").val());
			setDivReadOnly("repBankCardInfoDiv");
			document.getElementById("repBankcardNo").style.backgroundColor = "";
			document.getElementById("repBankcardName").style.backgroundColor = "";
			document.getElementById("repBankMobile").style.backgroundColor = "";
			document.getElementById("repBankIdNum").style.backgroundColor = "";
			document.getElementById("repBankDetail").style.backgroundColor = "";
			document.getElementById("repBankName").style.backgroundColor = "";
		} else {//否 将还款账户信息清空
			removeDivReadOnly("repBankCardInfoDiv");
			// 	所有数据清空
			clearDivVal("repBankCardInfoDiv");
			// 	下拉框处理
			$("#repBankProvince").val("");
			$("#repBankCity").val("");
			$("#repBankDistinct").val("");
			$("#repBank").val("");
			$("#s2id_repBankProvince>.select2-choice>.select2-chosen").html("请选择");
			$("#s2id_repBankCity>.select2-choice>.select2-chosen").html("请选择");
			$("#s2id_repBankDistinct>.select2-choice>.select2-chosen").html("请选择");
			$("#s2id_repBank>.select2-choice>.select2-chosen").html("请选择");
		}

		// 	省市级联错误信息隐藏 - 这里把所有的省市级联错误都统计进行处理
		function hideCityJiLianError() {
			errorHideHelper("repBankProvince");
			errorHideHelper("repBankCity");
			errorHideHelper("repBankDistinct");
			errorHideHelper("recBankProvince");
			errorHideHelper("recBankCity");
			errorHideHelper("recBankDistinct");
		}
	}

	function errorHideHelper(id) {
		var $val = $("#" + id).find("option:checked").val()
		if (null != $val && "" != $val) {
			$("label[for='" + id + "']").hide();
		}
	}
	//打印
	function printSelect() {
		var width = $(document).width() - 50;
		var height = $(document).height() - 50;
		var contractNo = $("#contractNo").val();
		var url = "${ctx}/credit/contractTemplate/printSelect?contractNo=" + contractNo + "&applyNo=" + '${contract.applyNo}';
		openJBox('', url, "打印", width, 800);
	}

	function platRead() {
		var PLATFORM = $("#contractOrgPlatform").val();
		$("#operateOtherDesc").attr("readOnly", "readOnly");
		if (PLATFORM == "10040099") {
			$("#operateOtherDesc").removeAttr("readOnly");
			var valueOth = "";
			$("#operateOtherDesc").val(valueOth);
		} else {
			var valueOth = "";
			$("#operateOtherDesc").val(valueOth);
		}
	}

	function saveForm() {
		loading();
		top.$.jBox.tip.mess = null;
		var formJson = $("#contractForm").serializeJson();
		$.post("${ctx}/credit/contractUnion/save", formJson, function(data) {
			if (data) {
				closeTip();
				if (data.status == 1) {//保存成功
					var contractId = data.id;
					$("form[id='contractForm'] input[id='id']").val(contractId);
					alertx(data.message, function() {

						$("#contractTmp").show();
						$("#contractNo").val(data.contractNo);
						$("#conStartDate").attr("value", data.conStartDate);
						$("#conEndDate").attr("value", data.conEndDate);
						$.loadDiv("repayPlanList", "${ctx }/credit/repayPlanUnion/getRepayPlanByParam", {
						applyNo : data.applyNo,
						taskDefKey : "utask_htmq",
						approId : "${contract.approId}"
						}, "post");
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
	<div id="readOnlyDiv">
		<form:form id="contractForm" modelAttribute="contract" action="" method="post" class="form-horizontal">
			<!-- <a id="contractSignInfoSkipId" target="_parent"></a> -->
			<form:hidden path="applyNo" value="${contract.applyNo}" />
			<input type="hidden" id="taskId" name="taskId" value="${actTaskParam.taskId}" />
			<input type="hidden" id="taskDefKey" name="taskDefKey" value="${actTaskParam.taskDefKey}" />
			<input type="hidden" id="procDefId" name="procDefId" value="${actTaskParam.procDefId}" />
			<input type="hidden" id="status" name="status" value="${actTaskParam.status}" />
			<input type="hidden" id="procInstId" name="procInstId" value="${actTaskParam.procInstId}" />
			<input type="hidden" id="headUrl" name="headUrl" value="${actTaskParam.headUrl}" />
			<input type="hidden" id="orgId" name="orgId" value="${applyInfo.applyRegister.company.id}" />
			<input type="hidden" id="operateOrgId" name="operateOrgId" value="${applyInfo.applyRegister.company.id}" />
			<input type="hidden" id="operateId" name="operateId" value="${applyInfo.applyRegister.createBy.id}" />
			<input type="hidden" id="operateName" name="operateName" value="${applyInfo.applyRegister.registerName}" />
			<input type="hidden" id="operateOrgName" name="operateOrgName" value="${applyInfo.operateOrgName}" />
			<input type="hidden" id="applyLoanRepayType" name="applyLoanRepayType" value="${applyInfo.loanRepayType}" />
			<input type="hidden" id="approAmount" name="approAmount" value="${contract.approAmount}" />
			<c:if test="${returnFlag eq 'true' && readOnly eq 'false'}">
				<div id='messageBox' class='alert alert-error'>
					<font>请注意：该任务为被打回任务!</font>
				</div>
			</c:if>
			<sys:message content="${message}" />
			<form:hidden path="id" />
			<div class="searchInfo">
				<h3 onclick="contractClick()" class="searchTitle">申请信息</h3>
				<div id="contractId" class="searchCon">
					<table class="fromTable filter">
						<tr>
							<td class="ft_label">申请人：</td>
							<td class="ft_content">
								<input type="text" id="custName" name="custName" class="input-medium" readonly="true" value="${contract.custName}" />
								<input type="hidden" id="custId" name="custId" class="input-medium" readonly="true" value="${contract.custId}" />
							</td>
							<td class="ft_label">证件类型：</td>
							<td class="ft_content">
								<%-- <input type="text" class="input-medium" readonly="true" value="${applyInfo.applyRegister.idTypeLabel}" /> --%>
								<input type="text" class="input-medium" readonly="true" value="${fns:getDictLabel(contract.idType, 'CUSTOMER_P_ID_TYPE', '')}" />
								<input type="hidden" id="idType" name="idType" class="input-medium" readonly="true" value="${contract.idType}" />
							</td>
							<td class="ft_label">证件号码：</td>
							<td class="ft_content">
								<input type="text" id="idNum" name="idNum" class="input-medium" readonly="true" value="${contract.idNum}" />
							</td>
						</tr>
						<tr>
							<td class="ft_label">产品类型：</td>
							<td class="ft_content">
								<input type="text" id="applyProductTypeName" name="applyProductTypeName" class="input-medium" readonly="true" value="${fns:getDictLabel(contract.applyProductTypeCode, 'PRODUCT_TYPE', '')}" />
								<input type="hidden" id="applyProductTypeCode" name="applyProductTypeCode" class="input-medium" readonly="true" value="${contract.applyProductTypeCode}" />
							</td>
							<td class="ft_label">产品名称：</td>
							<td class="ft_content">
								<input type="text" id="applyProductName" name="applyProductName" class="input-medium" readonly="true" value="${contract.applyProductName}" />
								<input type="hidden" id="applyProductId" name="applyProductId" class="input-medium" readonly="true" value="${contract.applyProductId}" />
							</td>
							<td class="ft_label">产品期限(月)：</td>
							<td class="ft_content">
								<input type="text" id="applyPeriodValue" name="applyPeriodValue" class="input-medium" readonly="true" value="${contract.applyPeriodValue}" />
							</td>
						</tr>
						<tr>
							<td class="ft_label">申请利率(%)：</td>
							<td class="ft_content">
								<input type="text" id="applyYearRate" name="applyYearRate" onblur="this.value=rateMoney(this.value);" onkeyup="rateMax(this)" maxlength="5" class="input-medium number" readonly="true" value="${contract.applyYearRate}" />
								<span class="help-inline"> </span>
							</td>
							<td class="ft_label">申请服务费率(%)：</td>
							<td class="ft_content">
								<input type="text" id="applyServiceFeeRate" name="applyServiceFeeRate" onblur="this.value=rateMoney(this.value);" onkeyup="rateMax(this)" maxlength="5" class="input-medium number" readonly="true" value="${contract.applyServiceFeeRate}" />
								<span class="help-inline"> </span>
							</td>
							<%-- <td class="ft_label">申请金额(元)：</td>
							<td class="ft_content">
								<input type="text" class="input-medium" id="applyAmount" name="applyAmount" readonly="true" value="${not empty applyInfo.applyAmount ? applyInfo.applyAmount : applyRegister.applyAmount}" />
							</td> --%>
						</tr>
					</table>
				</div>
			</div>
			<div id="approveInfoDiv" class="searchInfo">
				<h3 onclick="pfxxClick()" class="searchTitle">批复信息</h3>
				<div id="pfxxId" class="searchCon">
					<table class="fromTable filter">
						<tr>
							<input type="hidden" id="approId" name="approId" value="${contract.approId}" />
							<td class="ft_label">产品类型：</td>
							<td class="ft_content">
								<input type="text" id="approProductTypeName" name="approProductTypeName" class="input-medium" readonly="true" value="${contract.approProductTypeName}" />
								<input type="hidden" id="approProductTypeCode" name="approProductTypeCode" class="input-medium" readonly="true" value="${contract.approProductTypeCode}" />
							</td>
							<td class="ft_label">产品名称：</td>
							<td class="ft_content">
								<input type="text" id="approProductName" name="approProductName" class="input-medium" readonly="true" value="${contract.approProductName}" />
								<input type="hidden" id="approProductId" name="approProductId" class="input-medium" readonly="true" value="${contract.approProductId}" />
							</td>
						</tr>
						<tr>
							<td class="ft_label">合同金额(元)：</td>
							<td class="ft_content">
								<input type="text" id="contractAmount" name="contractAmount" class="input-medium" readonly="true" value="${contract.contractAmount}" />
							</td>
							<td class="ft_label">放款金额(元)：</td>
							<td class="ft_content">
								<input type="text" id="loanAmount" name="loanAmount" class="input-medium" readonly="true" value="${contract.loanAmount}" />
							</td>
							<td class="ft_label">期限：</td>
							<td class="ft_content">
								<input type="text" id="approPeriodValue" name="approPeriodValue" class="input-medium" readonly="true" value="${contract.approPeriodValue}" />
							</td>
						</tr>
						<tr>
							<td class="ft_label">批复月利率(%)：</td>
							<td class="ft_content">
								<input type="text" id="approYearRate" onblur="this.value=rateMoney(this.value);" onkeyup="rateMax(this)" maxlength="5" name="approYearRate" class="input-medium" readonly="true" value="${contract.approYearRate}" />
								<span class="help-inline"> </span>
							</td>
						</tr>
						<tr>
							<td class="ft_label">服务费率(%)：</td>
							<td class="ft_content">
								<input type="text" id="serviceFeeRate" onblur="this.value=rateMoney(this.value);" onkeyup="rateMax(this)" maxlength="5" name="serviceFeeRate" class="input-medium" readonly="true" value="${contract.serviceFeeRate}" />
								<span class="help-inline"> </span>
							</td>
							<%-- <c:if test="${fns:getDictValue('special_Service_Fee_Rate','special_Service_Fee_Rate','special_Service_Fee_Rate') eq '1'}"> --%>
								<td class="ft_label">特殊服务费率(%)：</td>
								<td class="ft_content">
									<input type="text" id="specialServiceFeeRate" onblur="this.value=rateMoney(this.value);" onkeyup="rateMax(this)" maxlength="5" name="specialServiceFeeRate" class="input-medium" readonly="true" value="${contract.specialServiceFeeRate}" />
									<span class="help-inline"> </span>
								</td>
							<%-- </c:if> --%>
							<td class="ft_label">服务费收取方式：</td>
							<td class="ft_content">
								<input type="text" class="input-medium" readonly="true" value="${fns:getDictLabel(contract.serviceFeeType, 'SERVICE_FEE_TYPE', '')}" />
								<input type="hidden" id="serviceFeeType" name="serviceFeeType" value="${contract.serviceFeeType}" />
							</td>
						</tr>
						<tr>
							<td class="ft_label">服务费：</td>
							<td class="ft_content">
								<input type="text" id="serviceFee" name="serviceFee" class="input-medium" readonly="true" value="${contract.serviceFee}" />
							</td>
							<%-- <c:if test="${fns:getDictValue('special_Service_Fee','special_Service_Fee','special_Service_Fee') eq '1'}"> --%>
								<td class="ft_label">特殊服务费：</td>
								<td class="ft_content">
									<input type="text" id="specialServiceFee" name="specialServiceFee" class="input-medium" readonly="true" value="${contract.specialServiceFee}" />
								</td>
							<%-- </c:if> --%>
							<td class="ft_label">服务费总计：</td>
							<td class="ft_content">
								<input type="text" id="allServiceFee" name="allServiceFee" class="input-medium" readonly="true" value="${contract.allServiceFee}" />
							</td>
						</tr>
						<tr>
							<td class="ft_label">还款方式：</td>
							<td class="ft_content">
								<input type="text" class="input-medium" readonly="true" value="${approLoanRepayTypeName}" />
								<input type="hidden" id="approLoanRepayType" name="approLoanRepayType" value="${contract.approLoanRepayType}" />
							</td>
							<td class="ft_label">保证金率（%）：</td>
							<td class="ft_content">
								<input type="text" id="marginRate" onblur="this.value=rateMoney(this.value);" onkeyup="rateMax(this)" maxlength="5" name="marginRate" class="input-medium" readonly="true" value="${contract.marginRate}" />
							</td>
							<td class="ft_label">保证金：</td>
							<td class="ft_content">
								<input type="text" id="marginAmount" name="marginAmount" class="input-medium" readonly="true" value="${contract.marginAmount}" />
							</td>
						</tr>
						<tr>
							<td class="ft_label">外访费：</td>
							<td class="ft_content">
								<input type="text" id="checkFee" name="checkFee" class="input-medium" readonly="true" value="${contract.checkFee}" />
							</td>
							<td class="ft_label">贷款模式：</td>
							<td class="ft_content">
								<input type="text" class="input-medium" readonly="true" value="${fns:getDictLabel(contract.loanModel, 'LOAN_MODEL', '')}" />
								<input type="hidden" id="loanModel" name="loanModel" value="${contract.loanModel}" />
							</td>
						</tr>
						<tr>
							<td class="ft_label">备注：</td>
							<td class="ft_content" colspan="6">
								<pre class="textarea-width pre-style" id="preRemark"></pre>
								<textarea id="remark" name="remark" htmlEscape="false" rows="6" maxlength="1000" class="textarea-width textarea-style" placeholder="此项填写为小黄表备注项">${contract.remark}</textarea>
							</td>
						</tr>
						<tr>
							<td class="ft_label">总公司批复意见：</td>
							<td class="ft_content" colspan="6">
								<pre class="textarea-width required pre-style" id="approResultPre"></pre>
								<textarea id="approResult" name="approResult" maxlength="1000" class="textarea-width textarea-style required" rows="6" readonly="true">${contract.approResult}</textarea>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div id="signInfoDiv" class="searchInfo">
				<h3 onclick="htqdxxClick()" class="searchTitle">合同签订信息</h3>
				<div id="htqdxxId" class="searchCon">
					<table class="fromTable filter">
						<tr>
							<td class="ft_label">转到银行卡金额(元)：</td>
							<td class="ft_content">
								<input onkeyup="keyPress11(this);" readonly="readonly" onblur="this.value=outputmoney(this.value);" id="factLoanAmount" name="factLoanAmount" type="text" value="${contract.loanAmount}" htmlEscape="false" maxlength="300" class="input-medium number" />
							</td>
							<td class="ft_label">收到现金金额(元)：</td>
							<td class="ft_content">
								<!-- 服务费提前收取时，收到现金金额=保证金+服务费总计；否则，收到现金金额=保证金 -->
								<input onkeyup="keyPress11(this);" readonly="readonly" onblur="this.value=outputmoney(this.value);" id="receiveAmount" name="receiveAmount" type="text" value="${contract.receiveAmount}" htmlEscape="false" maxlength="300" class="input-medium number" />
							</td>
							<td class="ft_label">主合同：</td>
							<td class="ft_content">
								<form:select path="mianContract" class="input-medium required">
									<form:option value="" label="" />
									<form:options items="${fns:getDictList('MIAN_CONTRACT') }" itemLabel="label" itemValue="value" htmlEscape="false" />
								</form:select>
							</td>
						</tr>
						<tr id="contractTmp">
							<td class="ft_label">合同编号：</td>
							<td class="ft_content">
								<form:input path="contractNo" htmlEscape="false" maxlength="300" readOnly="true" class="input-medium required" />
							</td>
							<td class="ft_label">合同起始日期：</td>
							<td class="ft_content">
								<input id="conStartDate" name="conStartDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" value="<fmt:formatDate value="${contract.conStartDate}" pattern="yyyy-MM-dd"/>" />
							</td>
							<td class="ft_label">合同结束日期：</td>
							<td class="ft_content">
								<input id="conEndDate" name="conEndDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="<fmt:formatDate value="${contract.conEndDate}" pattern="yyyy-MM-dd"/>" />
							</td>
						</tr>
						<tr>
							<td class="ft_label">合同预约日期：</td>
							<td class="ft_content">
								<input id="reserveTime" name="reserveTime" type="text" readOnly="readOnly" maxlength="20" class="input-medium Wdate " value="${reserveTime}" />
							</td>
							<td class="ft_label">合同签订日期：</td>
							<td class="ft_content">
								<input id="occurDate" name="occurDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required " value="<fmt:formatDate value="${contract.occurDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd',minDate:new Date(),isShowClear:false});" />
							</td>
							<td class="ft_label">借款平台：</td>
							<td class="ft_content">
								<form:select path="contractOrgPlatform" onchange="platRead()" class="input-medium required">
									<form:option value="" label="" />
									<form:options items="${fns:getDictList('ORG_PLATFORM')}" itemLabel="label" itemValue="value" htmlEscape="false" />
								</form:select>
							</td>
						</tr>
						<tr>
							<td class="ft_label">其他归属说明：</td>
							<td class="ft_content" colspan="5">
								<form:input path="operateOtherDesc" htmlEscape="false" maxlength="300" class="input-xxlarge " />
							</td>
						</tr>
						<tr>
							<td class="ft_label">合同签署地：</td>
							<td class="ft_content">
								<form:select path="contractProvince" class="input-medium nuNullCheck" data-code="-1"></form:select>
								&nbsp;省
							</td>
							<td class="ft_content">
								<form:select path="contractCity" class="input-medium nuNullCheck" data-code="-1"></form:select>
								&nbsp;市
							</td>
							<td class="ft_content">
								<form:select path="contractDistinct" class="input-medium nuNullCheck" data-code="-1"></form:select>
								&nbsp;区&nbsp;
							</td>
						</tr>
						<tr>
							<td class="ft_label">补充合同说明：</td>
							<td class="ft_content" colspan="5">
								<pre class="textarea-width required pre-style" id="descriptionPre"></pre>
								<form:textarea path="description" id="description" rows="5" onkeyup="adjustTextareaLength('description','descriptionPre')" htmlEscape="false" maxlength="1000" class="textarea-width textarea-style required" />
							</td>
						</tr>
					</table>
					<div class="searchButton">
						<c:if test="${actTaskParam.taskDefKey eq 'utask_htmq' }">
							<input id="btnCancel" class="btn btn-primary" type="button" value="打印" onclick="printSelect()" />
						</c:if>
					</div>
				</div>
			</div>
			<div id="mortgageInfoDiv" class="searchInfo">
				<h3 onclick="dyqrxxClick()" class="searchTitle">抵押权人信息</h3>
				<div id="dyqrxxId" class="searchCon">
					<table class="fromTable filter">
						<tr>
							<td class="ft_label">抵押权人：</td>
							<td>
								<form:hidden path="mortgageeCapTerNo" />
								<form:select path="mortgageeId" class="input-medium required" onchange="getInfo();">
									<form:option value="">请选择</form:option>
									<form:options items="${mortgagedPersonList}" itemLabel="mortgageeName" itemValue="id" htmlEscape="false" />
								</form:select>
							</td>
							<input type="hidden" id="mortgageeName" name="mortgageeName" />
							<td class="ft_label">身份证：</td>
							<td class="ft_content">
								<input type="text" id="mortgageeIdNum" name="mortgageeIdNum" class="input-medium" readonly="true" value="${contract.idNum}" />
							</td>
							<td class="ft_label">移动电话：</td>
							<td class="ft_content">
								<input type="text" id="mortgageeMobileNum" name="mortgageeMobileNum" class="input-medium mobile" readonly="true" value="${contract.mortgageeMobileNum}" />
							</td>
						</tr>
						<tr>
							<td class="ft_label">银行卡号：</td>
							<td class="ft_content">
								<input type="text" id="bankcardNo" name="bankcardNo" class="input-medium" readonly="true" value="${contract.bankcardNo}" />
							</td>
							<td class="ft_label">开户行：</td>
							<td class="ft_content">
								<input type="text" id="bankNo" name="bankNo" class="input-medium" readonly="true" value="${contract.bankNo}" />
							</td>
							<td class="ft_label">开户行名称：</td>
							<td class="ft_content">
								<input type="text" id="bankDetailName" name="bankDetailName" class="input-medium" readonly="true" value="${contract.bankDetailName}" />
							</td>
						</tr>
						<tr>
							<td class="ft_label">开户行地址：</td>
							<td class="ft_content" colspan="5">
								<input type="text" id="contProvinceName" class="input-medium" readonly="true" value="${contract.contProvince}" />
								<input type="hidden" id="contProvince" name="contProvince" />
								<span>
									<font>省</font>
								</span>
								<input type="text" id="contCityName" class="input-medium" readonly="true" value="${contract.contCity}" />
								<input type="hidden" id="contCity" name="contCity" />
								<span>
									<font>市</font>
								</span>
								<input type="text" id="contDistinctName" class="input-medium" readonly="true" value="${contract.contDistinct}" />
								<input type="hidden" id="contDistinct" name="contDistinct" />
								<span>
									<font>区</font>
								</span>
								<input type="text" id="contDetails" name="contDetails" class="input-medium" readonly="true" value="${contract.contDetails}" />
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div id="custBankInfoDiv" class="searchInfo">
				<h3 onclick="jkryhkxxClick()" class="searchTitle">借款人银行卡信息</h3>
				<div id="jkryhkxxId" class="searchCon">
					<div id="recBankCardInfoDiv">
						<table class="fromTable filter">
							<tr>
								<td class="ft_label">收款银行卡号：</td>
								<td class="ft_content">
									<form:input path="recBankcardNo" htmlEscape="false" maxlength="19" class="input-medium number required" onkeyup="loadBankCardInfo();" />
								</td>
								<td class="ft_label">收款银行卡号确认：</td>
								<!-- 非持久层字段-->
								<td class="ft_content">
									<input type="text" id="recBankcardNoConfirm" maxlength="19" class="input-medium number required" value="${contract.recBankcardNo}" onkeyup="loadBankCardInfo();" />
								</td>
								</td>
							</tr>
							<tr>
								<td class="ft_label">收款账户名称：</td>
								<td class="ft_content">
									<form:input path="recBankcardName" htmlEscape="false" readOnly="readOnly" maxlength="20" class="input-medium" onkeyup="loadBankCardInfo();" />
								</td>
								<td class="ft_label">移动电话：</td>
								<td class="ft_content">
									<form:input path="recBankMobile" htmlEscape="false" maxlength="11" class="input-medium required mobile" onkeyup="loadBankCardInfo();" />
								</td>
								<td class="ft_label">收款人身份证号：</td>
								<td class="ft_content">
									<form:input path="recBankIdNum" htmlEscape="false" maxlength="18" class="input-medium required card" onkeyup="loadBankCardInfo();" />
								</td>
							</tr>
							<tr>
								<td class="ft_label">收款银行：</td>
								<td class="ft_content">
									<form:select path="recBank" class="input-medium required" onchange="loadBankCardInfo();">
										<form:option value="" label="" />
										<form:options items="${fns:getDictList('BANKS') }" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
								</td>
								<td class="ft_label">收款开户行名称：</td>
								<td class="ft_content">
									<form:input path="recBankName" htmlEscape="false" maxlength="50" class="input-medium required" onkeyup="loadBankCardInfo();" />
								</td>
							</tr>
							<tr>
								<td class="ft_label">收款银行地址：</td>
								<td class="ft_content" colspan="5">
									<form:select path="recBankProvince" class="input-small nuNullCheck" data-code="-1" onchange="loadData()"></form:select>
									&nbsp;省
									<form:select path="recBankCity" class="input-small nuNullCheck" data-code="-1" onchange="loadData()"></form:select>
									&nbsp;市
									<form:select path="recBankDistinct" class="input-small nuNullCheck" data-code="-1" onchange="loadData()"></form:select>
									&nbsp;区&nbsp;
									<form:input path="recBankDetail" htmlEscape="false" class="input-medium required" onblur="loadData()" />
								</td>
							</tr>
							<!-- 							<tr> -->
							<!-- 								<td class="ft_label">资金平台账号：</td> -->
							<!-- 								<td class="ft_content"> -->
							<!-- 									<form:input path="capitalTerraceNo" htmlEscape="false" class="input-medium " readonly="true" /> -->
							<!-- 								</td> -->
							<!-- 								<td class="ft_label">开户状态：</td> -->
							<!-- 								<td class="ft_content"> -->
							<!-- 									<input id="openAccountStatusLabel" name="openAccountStatusLabel" type="text" class="input-medium " readonly="readonly" value="${openAccountStatusLabel}" /> -->
							<!-- 								</td> -->
							<!-- 							</tr> -->
							<tr>
								<td class="ft_label">还款同收款账户：</td>
								<td class="ft_content">
									<%--  <form:radiobuttons class="required" items="${fns:getDictList('yes_no') }" path="isAccord" itemLabel="label" itemValue="value" htmlEscape="false" onchange="loadBankCardInfo();" /> --%>
									<input type="radio" name="isAccord" value="1" id="isAccord" class="required" checked="true">
									<label for="radio_yes">是</label>
								</td>
							</tr>
						</table>
					</div>
					<div id="repBankCardInfoDiv">
						<table class="fromTable filter">
							<tr>
								<td class="ft_label">还款银行卡号：</td>
								<td class="ft_content">
									<form:input path="repBankcardNo" htmlEscape="false" class="input-medium  " />
								</td>
								<td class="ft_label">还款银行卡号确认：</td>
								<!-- 非持久层字段-->
								<td class="ft_content">
									<input type="text" id=repBankcardNoConfirm class="input-medium  " value="${contract.repBankcardNo}" />
								</td>
							</tr>
							<tr>
								<td class="ft_label">还款账户名称：</td>
								<td class="ft_content">
									<form:input path="repBankcardName" htmlEscape="false" class="input-medium " />
								</td>
								<td class="ft_label">还款移动电话：</td>
								<td class="ft_content">
									<form:input path="repBankMobile" htmlEscape="false" class="input-medium  " />
								</td>
								<td class="ft_label">还款人身份证号：</td>
								<td class="ft_content">
									<form:input path="repBankIdNum" htmlEscape="false" class="input-medium " />
								</td>
							</tr>
							<tr>
								<td class="ft_label">还款银行：</td>
								<td class="ft_content">
									<form:select path="repBank" class="input-medium ">
										<form:option value="" label="请选择" />
										<form:options items="${fns:getDictList('BANKS') }" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
								</td>
								<td class="ft_label">还款开户行名称：</td>
								<td class="ft_content">
									<form:input path="repBankName" htmlEscape="false" class="input-medium " />
								</td>
							</tr>
							<tr>
								<td class="ft_label">还款银行地址：</td>
								<td class="ft_content" colspan="5">
									<form:select path="repBankProvince" class="input-small nuNullCheck" data-code="-1"></form:select>
									&nbsp;省
									<form:select path="repBankCity" class="input-small nuNullCheck" data-code="-1"></form:select>
									&nbsp;市
									<form:select path="repBankDistinct" class="input-small nuNullCheck" data-code="-1"></form:select>
									&nbsp;区&nbsp;
									<form:input path="repBankDetail" htmlEscape="false" class="input-medium " />
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div class="searchButton">
				<c:if test="${actTaskParam.taskDefKey eq 'utask_htmq' }">
					<input id="rsBtnSubmit" class="btn btn-primary" type="submit" style="width: 130px;" onclick=" return checkForm();" value="保存并生成还款计划" />&nbsp;
				</c:if>
			</div>
		</form:form>
		<!-- 还款计划表 -->
		<div id="repayPlanList"></div>
	</div>	
</body>
</html>