<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>客户基本信息管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {

		$.loadDiv("contactCustInfo", "${ctx }/credit/contactInfo/list", {
		relationId : '${zjrRelation.id}',
		applyNo : '${actTaskParam.applyNo}'
		}, "post");
		$.loadDiv("companyManagerInfoList", "${ctx }/credit/companyManagerInfo/list", {
		companyId : '${companyInfo.id}',
		applyNo : '${actTaskParam.applyNo}'
		}, "post");
		$.loadDiv("companyRelatedListDiv", "${ctx }/credit/companyRelated/list", {
		companyId : '${companyInfo.id}',
		applyNo : '${actTaskParam.applyNo}'
		}, "post");
		/* $.loadDiv("coCustInfo", "${ctx }/credit/coCustInfo/list", {
			applyNo : '${actTaskParam.applyNo}'
		}, "post"); */
	});
	var width = $(window).width() - 300;
	var height = $(window).height() - 150;
	//保存借款人信息

	function saveCustInfo() {
	if($("#contDistinct").val() == "-1"){
			alertx("请填写现居地址！");
		}else{
		loading();
		var valueT1 = $("#perAnnualIncome").val().replace(/,/g, "");
		var valueT2 = $("#householdSpendingMonth").val().replace(/,/g, "");
		var valueT3 = $("#mainEstateValuation").val().replace(/,/g, "");
		$("#perAnnualIncome").val(valueT1);
		$("#householdSpendingMonth").val(valueT2);
		$("#mainEstateValuation").val(valueT3);
		top.$.jBox.tip.mess = null;
		var formJson = $("#custInfoForm").serializeJson();
		$.post("${ctx}/custinfo/custInfo/save", formJson, function(data) {
			if (data) {
				closeTip();
				if (data.status == 1) {
					//保存成功时，将借款人ID值赋给借款人工作信息表单中的隐藏域
					var custId = data.custIdForCustWork;
					var id = data.id;
					document.getElementById("perAnnualIncome").value = outputmoney(valueT1);
					document.getElementById("householdSpendingMonth").value = outputmoney(valueT2);

					$("#custIdForCustWork").val(custId);
					$("#custIdForCustMate").val(custId);
					$("#custInfoForm input[id=id]").val(id);
					var relationId = data.relationId;
					$("#relationId").val(relationId);
				}
				if (!checkIsNull(data.saveMessage)) {
					alertx(data.saveMessage);
				} else {
					alertx(data.message);
				}

			}
		});
		}
	}

	//保存借款人工作信息
	function saveCustWorkInfo() {
		loading();
		var valueT1 = $("#monthIncomeId").val().replace(/,/g, "");
		var valueT2 = $("#otherMonthIncomeId").val().replace(/,/g, "");
		$("#monthIncomeId").val(valueT1);
		$("#otherMonthIncomeId").val(valueT2);
		top.$.jBox.tip.mess = null;
		var work = $("#custWorkInfoForm").serializeJson();
		/* var custIdForWork = $("#custIdForCustWork").val();
		//将序列化后的json中加入custInfo对象，设置custInfo对象的id为custIdForCustWork隐藏域中的值
		work.custInfo = {
			id : custIdForWork
		}; */
		$.post("${ctx}/credit/custWorkInfo/save", work, function(data) {
			if (data) {
				closeTip();
				if (data.status == 1) {
					var id = data.id;
					$("#custWorkInfoForm input[id=custWorkInfoId]").val(id);
					document.getElementById("monthIncomeId").value = outputmoney(valueT1);
					document.getElementById("otherMonthIncomeId").value = outputmoney(valueT2);
					alertx(data.message);
				} else {
					alertx(data.message);
				}
			}
		});
	}

	//保存借款人配偶信息
	function saveCustMateInfo() {
		var valueT1 = $("#matePerAnnualIncome").val().replace(/,/g, "");
		$("#matePerAnnualIncome").val(valueT1);
		var valueT2 = $("#mateEstateValuation").val().replace(/,/g, "");
		$("#mateEstateValuation").val(valueT2);
		if (!checkIsNull($("#custIdForCustMate").val())) {
			loading();
			top.$.jBox.tip.mess = null;
			var mate = $("#custMateInfoForm").serializeJson();
			mate.currApplyNo = '${actTaskParam.applyNo}';
			if ('${custInfo.isHaveAssure}' == "0") {
				mate.mateToGuarantor = "0";
			}
			var custIdForMate = $("#custIdForCustMate").val();
			//将序列化后的json中加入custInfo对象，设置custInfo对象的id为custIdForCustWork隐藏域中的值
			mate.custInfo = {
				id : custIdForMate
			};
			saveJson("${ctx}/custinfo/custInfo/saveMate", JSON.stringify(mate), function(data) {
				if (data) {
					closeTip();
					if (data.status == 1) {
						var id = data.id;
						$("#custMateInfoForm input[id=mateId]").val(id);
						document.getElementById("matePerAnnualIncome").value = outputmoney(valueT1);
						alertx(data.message);
					} else {
						alertx(data.message);
					}
				}
			});
		} else {
			alertx("请先保存借款人信息！");
		}
	}

	//保存借款人企业信息
	function saveCompanyInfo() {
		loading();
		var valueT1 = $("#registerCapital").val().replace(/,/g, "");
		var valueT4 = $("#paidCapital").val().replace(/,/g, "");
		var valueT2 = $("#annualTurnover").val().replace(/,/g, "");
		var valueT3 = $("#operateAreas").val().replace(/,/g, "");
		$("#registerCapital").val(valueT1);
		$("#paidCapital").val(valueT4);
		$("#annualTurnover").val(valueT2);
		$("#operateAreas").val(valueT3);
		top.$.jBox.tip.mess = null;
		var company = $("#companyInfoForm").serializeJson();
		company.operateProvince = $("#companyContProvince").val();
		company.operateCity = $("#companyContCity").val();
		company.operateDistinct = $("#companyContDistinct").val();
		company.currApplyNo = '${actTaskParam.applyNo }';
		$.post("${ctx}/credit/companyInfo/save", company, function(data) {
			if (data) {
				closeTip();
				if (data.status == 1) {
					//保存成功时，将借款人企业ID值赋给表单中的隐藏域
					var companyInfoId = data.companyInfoId;
					var id = data.id;
					$("#companyId").val(companyInfoId);
					$("#companyInfoForm input[id=id]").val(id);
					document.getElementById("registerCapital").value = outputmoney(valueT1);
					document.getElementById("paidCapital").value = outputmoney(valueT4);
					document.getElementById("annualTurnover").value = outputmoney(valueT2);
					alertx(data.message);
				} else {
					alertx(data.message);
				}

			}
		});
	}
</script>
</head>
<body>
	<div>
		<input type="hidden" id="applyNoForCoCust" value="${actTaskParam.applyNo }" />
	</div>
	<div id="mainCustInfo">
		<%@ include file="/WEB-INF/views/app/credit/custinfo/mainBorrowerInfo.jsp"%>
	</div>
	<div id="custWorkInfo">
		<%@ include file="/WEB-INF/views/app/credit/custWorkInfo/custWorkInfoForm.jsp"%>
	</div>
	<div id="custMateInfo">
		<%@ include file="/WEB-INF/views/app/credit/custinfo/custMateInfoList.jsp"%>
	</div>
	<!-- <div id="coCustInfo"></div> -->
	<input type="hidden" id="relationId" value="${zjrRelation.id}" />
	<div id="contactCustInfo"></div>
	<!-- <div id="companyInfo" hidden="hidden"> -->
	<div id="companyInfo"><!-- 原来是隐藏的，现在改为显示 -->
		<%@ include file="/WEB-INF/views/app/credit/companyInfo/companyInfoForm.jsp"%>
	</div>
	<input type="hidden" id="companyId" value="${companyInfo.id}" />
	<div id="companyManagerInfoList"></div>
	<div id="companyRelatedListDiv"></div>
</body>
</html>
