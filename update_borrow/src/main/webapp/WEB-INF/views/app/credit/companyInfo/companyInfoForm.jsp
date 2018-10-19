<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">
	$(document).ready(function() {
		isHideDateElement();
		document.getElementById("registerCapital").value = outputmoney("${companyInfo.registerCapital}");
		document.getElementById("annualTurnover").value = outputmoney("${companyInfo.annualTurnover}");
		document.getElementById("operateAreas").value = outputmoney("${companyInfo.operateAreas}");
		$("#companyInfoForm").validate({
		submitHandler : saveCompanyInfo,
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});

		adjustTextareaLength("capitalStructureCom", "preCapitalStructureCom");
		adjustTextareaLength("stateOfTaxation", "preStateOfTaxation");

		if ('${companyInfo.isAddrResi}' == null || '${companyInfo.isAddrResi}' == '') {
			//现居地与户籍地址是否一致，默认为否
			$("input[id='isAddrResi2']").attr("checked", "true");
		}
		if ("${companyInfo.isAddrResi}" == "1") {
			changeCompanyInfoValue();
		}

		new CitySelect({
		data : data,
		provId : '#companyRegProvince',
		cityId : '#companyRegCity',
		areaId : '#companyRegDistinct',
		isSelect : false
		});
		new CitySelect({
		data : data,
		provId : '#companyContProvince',
		cityId : '#companyContCity',
		areaId : '#companyContDistinct',
		isSelect : false
		});

		if (!checkIsNull('${companyInfo.regDistinct}')) {
			setName(data, "companyReg", '${companyInfo.regProvince}', '${companyInfo.regCity}', '${companyInfo.regDistinct}');
			if (!checkIsNull('${companyInfo.operateDistinct}')) {
				setName(data, "companyCont", '${companyInfo.operateProvince}', '${companyInfo.operateCity}', '${companyInfo.operateDistinct}');
			}
		}

	});

	//证件号与有效期的联动设置
	function isHideDateElement() {
		if (checkIsNull($("#unSocCreditNo").val())) {
			$("#unSocCreditNoTD1").hide();
			$("#unSocCreditStartDate").val("");
			$("#unSocCreditNoTD2").hide();
			$("#unSocCreditEndDate").val("");
		} else {
			$("#unSocCreditNoTD1").show();
			$("#unSocCreditNoTD2").show();
		}
		if (checkIsNull($("#organizationNo").val())) {
			$("#organizationNoTD1").hide();
			$("#organizationStartDate").val("");
			$("#organizationNoTD2").hide();
			$("#organizationEndDate").val("");
		} else {
			$("#organizationNoTD1").show();
			$("#organizationNoTD2").show();
		}
		if (checkIsNull($("#busiLicRegNo").val())) {
			$("#busiLicRegNoTD1").hide();
			$("#busiLicRegStartDate").val("");
			$("#busiLicRegNoTD2").hide();
			$("#busiLicRegEndDate").val("");
		} else {
			$("#busiLicRegNoTD1").show();
			$("#busiLicRegNoTD2").show();
		}
		if (checkIsNull($("#specialOperatePermit").val())) {
			$("#specialOperatePermitTD1").hide();
			$("#specialOperateStartDate").val("");
			$("#specialOperatePermitTD2").hide();
			$("#specialOperateEndDate").val("");
		} else {
			$("#specialOperatePermitTD1").show();
			$("#specialOperatePermitTD2").show();
		}
	}

	//处理企业四个证件号必填、不必填关系
	function handleComOnlyId() {}
	/* function handleComOnlyId() {
	    //统一社会信用代码
		$("#unSocCreditNo").addClass("required");
		$("#unSocCreditStartDate").addClass("required");
		$("#unSocCreditEndDate").addClass("required");
		document.getElementById("unSocCreditNo").style.backgroundColor = "pink";
		document.getElementById("unSocCreditStartDate").style.backgroundColor = "pink";
		document.getElementById("unSocCreditEndDate").style.backgroundColor = "pink";

		// 组织机构代码
		$("#organizationNo").addClass("required");
		$("#organizationStartDate").addClass("required");
		$("#organizationEndDate").addClass("required");
		document.getElementById("organizationNo").style.backgroundColor = "pink";
		document.getElementById("organizationStartDate").style.backgroundColor = "pink";
		document.getElementById("organizationEndDate").style.backgroundColor = "pink";

		// 营业执照注册号
		$("#busiLicRegNo").addClass("required");
		$("#busiLicRegStartDate").addClass("required");
		$("#busiLicRegEndDate").addClass("required");
		document.getElementById("busiLicRegNo").style.backgroundColor = "pink";
		document.getElementById("busiLicRegStartDate").style.backgroundColor = "pink";
		document.getElementById("busiLicRegEndDate").style.backgroundColor = "pink";

		// 登记税号
		$("#registerNationalTaxNo").addClass("required");
		document.getElementById("registerNationalTaxNo").style.backgroundColor = "pink";

		var companyType = $("#companyType").val();
		if ("1" == companyType) {//个体工商户：营业执照，登记税号必填
			// 统一社会信用代码
			$("#unSocCreditNo").removeClass("required");
			$("#unSocCreditStartDate").removeClass("required");
			$("#unSocCreditEndDate").removeClass("required");
			document.getElementById("unSocCreditNo").style.backgroundColor = "";
			document.getElementById("unSocCreditStartDate").style.backgroundColor = "";
			document.getElementById("unSocCreditEndDate").style.backgroundColor = "";
			// 组织机构代码
			$("#organizationNo").removeClass("required");
			$("#organizationStartDate").removeClass("required");
			$("#organizationEndDate").removeClass("required");
			document.getElementById("organizationNo").style.backgroundColor = "";
			document.getElementById("organizationStartDate").style.backgroundColor = "";
			document.getElementById("organizationEndDate").style.backgroundColor = "";
			//营业执照注册号
			if (checkIsNull($("#busiLicRegNo").val())) {
				document.getElementById("busiLicRegNo").style.backgroundColor = "pink";
			} else {
				document.getElementById("busiLicRegNo").style.backgroundColor = "";
			}
			if (checkIsNull($("#busiLicRegStartDate").val())) {
				document.getElementById("busiLicRegStartDate").style.backgroundColor = "pink";
			} else {
				document.getElementById("busiLicRegStartDate").style.backgroundColor = "";
			}
			if (checkIsNull($("#busiLicRegEndDate").val())) {
				document.getElementById("busiLicRegEndDate").style.backgroundColor = "pink";
			} else {
				document.getElementById("busiLicRegEndDate").style.backgroundColor = "";
			}
			//登记税号
			if (checkIsNull($("#registerNationalTaxNo").val())) {
				document.getElementById("registerNationalTaxNo").style.backgroundColor = "pink";
			} else {
				document.getElementById("registerNationalTaxNo").style.backgroundColor = "";
			}
		} else {//不是个体工商户
			if (!checkIsNull($("#unSocCreditNo").val())) {//统一社会信用代码不为空
				// 统一社会信用代码
				document.getElementById("unSocCreditNo").style.backgroundColor = "";
				if (checkIsNull($("#unSocCreditStartDate").val())) {
				document.getElementById("unSocCreditStartDate").style.backgroundColor = "pink";
				} else {
					document.getElementById("unSocCreditStartDate").style.backgroundColor = "";
				}
				if (checkIsNull($("#unSocCreditEndDate").val())) {
					document.getElementById("unSocCreditEndDate").style.backgroundColor = "pink";
				} else {
					document.getElementById("unSocCreditEndDate").style.backgroundColor = "";
				}
				// 登记税号
				$("#registerNationalTaxNo").removeClass("required");
				document.getElementById("registerNationalTaxNo").style.backgroundColor = "";
				// 营业执照注册号
				$("#busiLicRegNo").removeClass("required");
				$("#busiLicRegStartDate").removeClass("required");
				$("#busiLicRegEndDate").removeClass("required");
				document.getElementById("busiLicRegNo").style.backgroundColor = "";
				document.getElementById("busiLicRegStartDate").style.backgroundColor = "";
				document.getElementById("busiLicRegEndDate").style.backgroundColor = "";
				// 组织机构代码
				$("#organizationNo").removeClass("required");
				$("#organizationStartDate").removeClass("required");
				$("#organizationEndDate").removeClass("required");
				document.getElementById("organizationNo").style.backgroundColor = "";
				document.getElementById("organizationStartDate").style.backgroundColor = "";
				document.getElementById("organizationEndDate").style.backgroundColor = "";
			} else if (checkIsNull($("#registerNationalTaxNo").val())
					&& checkIsNull($("#organizationNo").val())
					&& checkIsNull($("#busiLicRegNo").val())) {

				$("#unSocCreditNo").addClass("required");
				$("#unSocCreditStartDate").addClass("required");
				$("#unSocCreditEndDate").addClass("required");
				document.getElementById("unSocCreditNo").style.backgroundColor = "pink";
				document.getElementById("unSocCreditStartDate").style.backgroundColor = "pink";
				document.getElementById("unSocCreditEndDate").style.backgroundColor = "pink";

				// 组织机构代码
				$("#organizationNo").addClass("required");
				$("#organizationStartDate").addClass("required");
				$("#organizationEndDate").addClass("required");
				document.getElementById("organizationNo").style.backgroundColor = "pink";
				document.getElementById("organizationStartDate").style.backgroundColor = "pink";
				document.getElementById("organizationEndDate").style.backgroundColor = "pink";

				// 营业执照注册号
				$("#busiLicRegNo").addClass("required");
				$("#busiLicRegStartDate").addClass("required");
				$("#busiLicRegEndDate").addClass("required");
				document.getElementById("busiLicRegNo").style.backgroundColor = "pink";
				document.getElementById("busiLicRegStartDate").style.backgroundColor = "pink";
				document.getElementById("busiLicRegEndDate").style.backgroundColor = "pink";

				// 登记税号
				$("#registerNationalTaxNo").addClass("required");
				document.getElementById("registerNationalTaxNo").style.backgroundColor = "pink";
			} else {
				// 统一社会信用代码
				$("#unSocCreditNo").removeClass("required");
				$("#unSocCreditStartDate").removeClass("required");
				$("#unSocCreditEndDate").removeClass("required");
				document.getElementById("unSocCreditNo").style.backgroundColor = "";
				document.getElementById("unSocCreditStartDate").style.backgroundColor = "";
				document.getElementById("unSocCreditEndDate").style.backgroundColor = "";
				//组织机构代码
				if (checkIsNull($("#organizationNo").val())) {
					document.getElementById("organizationNo").style.backgroundColor = "pink";
				} else {
					document.getElementById("organizationNo").style.backgroundColor = "";
				}
				if (checkIsNull($("#organizationStartDate").val())) {
					document.getElementById("organizationStartDate").style.backgroundColor = "pink";
				} else {
					document.getElementById("organizationStartDate").style.backgroundColor = "";
				}
				if (checkIsNull($("#organizationEndDate").val())) {
					document.getElementById("organizationEndDate").style.backgroundColor = "pink";
				} else {
					document.getElementById("organizationEndDate").style.backgroundColor = "";
				}
				//登记税号
				if (checkIsNull($("#registerNationalTaxNo").val())) {
					document.getElementById("registerNationalTaxNo").style.backgroundColor = "pink";
				} else {
					document.getElementById("registerNationalTaxNo").style.backgroundColor = "";
				}
				//营业执照注册号
				if (checkIsNull($("#busiLicRegNo").val())) {
					document.getElementById("busiLicRegNo").style.backgroundColor = "pink";
				} else {
					document.getElementById("busiLicRegNo").style.backgroundColor = "";
				}
				if (checkIsNull($("#busiLicRegStartDate").val())) {
					document.getElementById("busiLicRegStartDate").style.backgroundColor = "pink";
				} else {
					document.getElementById("busiLicRegStartDate").style.backgroundColor = "";
				}
				if (checkIsNull($("#busiLicRegEndDate").val())) {
					document.getElementById("busiLicRegEndDate").style.backgroundColor = "pink";
				} else {
					document.getElementById("busiLicRegEndDate").style.backgroundColor = "";
				}
			}
		}
	} */

	var treeCategory3 = [ "categoryMain3", "categoryLarge3", "categoryMedium3", "categorySmall3" ];
	function companyLoadCategory(root, curr) {
		var parentInduCode = $("#" + root).select2("val");
		var emptyFlag = false;
		for (var i = 0; i < treeCategory3.length; i++) {
			if (curr == treeCategory3[i]) {
				emptyFlag = true;
			}
			if (emptyFlag) {
				$("#" + treeCategory3[i]).select2("val", "");
				$("#" + treeCategory3[i]).empty();
			}
		}
		if ("" != parentInduCode && parentInduCode.length > 0) {
			$.post("${ctx}/credit/industry/loadIndustry", {
				parentInduCode : parentInduCode
			}, function(data) {
				$("#" + curr).append("<option value=''></option>");
				$.each(data, function(i, val) {
					$("#" + curr).append("<option value='"+val.induCode+"'>" + val.induName + "</option>");
				});
			}, "json");
		}
	}
	function companyInfoClick() {
		$("#companyInfoId").toggle(600);
	}

	function changeCompanyInfoValue() {
		//现居地等于户籍地
		var $isAddrResi = $("input[name='isAddrResi']:checked").val();
		if (1 == $isAddrResi) {
			$("#companyContProvince").val($("#companyRegProvince").val());
			$("#s2id_companyContProvince>.select2-choice>.select2-chosen").html($("#s2id_companyRegProvince>.select2-choice>.select2-chosen").html());
			$("#companyContCity").append($("#companyRegCity").html());
			$("#companyContCity").val($("#companyRegCity").val());
			$("#s2id_companyContCity>.select2-choice>.select2-chosen").html($("#s2id_companyRegCity>.select2-choice>.select2-chosen").html());
			$("#companyContDistinct").append($("#companyRegDistinct").html());
			$("#companyContDistinct").val($("#companyRegDistinct").val());
			$("#s2id_companyContDistinct>.select2-choice>.select2-chosen").html($("#s2id_companyRegDistinct>.select2-choice>.select2-chosen").html());
			$("#companyContDetails").val($("#companyRegDetails").val());
			$("#companyContProvince").prop("disabled", true).select2();
			$("#companyContCity").prop("disabled", true).select2();
			$("#companyContDistinct").prop("disabled", true).select2();
			$("#companyContDetails").attr("readOnly", true);
			// 			validateMsg('companyContProvince');
			// 			validateMsg('companyContCity');
			// 			validateMsg('companyContDistinct');
			document.getElementById("companyContDetails").style.backgroundColor = "";
		} else if (0 == $isAddrResi) {
			if ('${readOnly}' == "false") {
				$("#companyContProvince").prop("disabled", false).select2();
				$("#companyContCity").prop("disabled", false).select2();
				$("#companyContDistinct").prop("disabled", false).select2();
				$("#companyContDetails").removeAttr("readOnly");
				var $companyContProvince = $("#s2id_companyContProvince>.select2-choice>.select2-chosen");
				$companyContProvince.html("请选择");
				$("#companyContProvince").val("请选择");
				$("#companyContCity").empty();
				$("#companyContCity").append("<option value=''>请选择</option>");
				var $companyContCity = $("#s2id_companyContCity>.select2-choice>.select2-chosen");
				$companyContCity.html("请选择");

				$("#companyContDistinct").empty();
				$("#companyContDistinct").append("<option value=''>请选择</option>");
				var $companyContDistinct = $("#s2id_companyContDistinct>.select2-choice>.select2-chosen");
				$companyContDistinct.html("请选择")

				$("#companyContDetails").val(null);
			}
		}
	}


	function changeCompanyContDetails() {
		$("#companyRegDetails").val($("#companyRegDetails").val().replace(/\s/g,""));
		var $isAddrResi = $("form[id='companyInfoForm'] input[name='isAddrResi']:checked").val();
		if (1 == $isAddrResi) {
			$("#companyContDetails").val($("#companyRegDetails").val());
			validateMsg('companyContDetails');
			document.getElementById("companyContDetails").style.backgroundColor = "";
		}
	}

	function loadDataCompany() {
		var $addrResi = $("form[id='companyInfoForm'] input[name='isAddrResi']:checked").val();
		if (1 == $addrResi) {
			$("#companyContProvince").val($("#companyRegProvince").val());
			$("#s2id_companyContProvince>.select2-choice>.select2-chosen").html($("#s2id_companyRegProvince>.select2-choice>.select2-chosen").html());
			$("#companyContCity").append($("#companyRegCity").html());
			$("#companyContCity").val($("#companyRegCity").val());
			$("#s2id_companyContCity>.select2-choice>.select2-chosen").html($("#s2id_companyRegCity>.select2-choice>.select2-chosen").html());

			$("#companyContDistinct").append($("#companyRegDistinct").html());
			$("#companyContDistinct").val($("#companyRegDistinct").val());
			$("#s2id_companyContDistinct>.select2-choice>.select2-chosen").html($("#s2id_companyRegDistinct>.select2-choice>.select2-chosen").html());

			$("#contDetails").val($("#regDetails").val());
		}
	}
</script>
<div class="searchInfo">
	<h3 onclick="companyInfoClick()" class="searchTitle">借款人企业信息</h3>
	<div id="companyInfoId" class="searchCon">
		<form:form id="companyInfoForm" modelAttribute="companyInfo" action="${ctx}/credit/companyInfo/save" method="post" class="form-horizontal">
			<form:hidden path="id" />
			<form:hidden path="currApplyNo" />
			<sys:message content="${message}" />
			<table class="fromTable filter">
				<tr>
					<td class="ft_label">工商登记名称：</td>
					<td class="ft_content" colspan="3">
						<form:input path="busiRegName" maxlength="30" onblur="this.value=reSpaces(this.value);"  style="width:80%;" class="input-medium required" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">成立时间：</td>
					<td class="ft_content">
						<input id="foundDate" name="foundDate" type="text" readonly="true" maxlength="20" class="input-medium Wdate required" value='<fmt:formatDate value="${companyInfo.foundDate}" pattern="yyyy-MM-dd"/>' onclick="WdatePicker({onpicked:dateWhite(this),maxDate:new Date(),dateFmt:'yyyy-MM-dd'});" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">企业类型：</td>
					<td class="ft_content">
						<form:select id="companyType" path="companyType" class="input-medium required" onchange="handleComOnlyId();">
							<form:option value="" td="" />
							<form:options items="${fns:getDictList('COMPANY_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">统一社会信用代码：</td>
					<td class="ft_content">
						<input id="unSocCreditNo" name="unSocCreditNo" type="text" maxlength="50" class="input-medium required" value="${companyInfo.unSocCreditNo}" onblur="handleComOnlyId();" onchange="isHideDateElement();" />
						<font color="red">*</font>
					</td>
					<td id="unSocCreditNoTD1" class="ft_label">信用代码有效期：</td>
					<td id="unSocCreditNoTD2" class="ft_content" colspan="2">
						<input id="unSocCreditStartDate" name="unSocCreditStartDate" readonly="true" type="text" maxlength="20" class="input-mini Wdate required" value="<fmt:formatDate value="${companyInfo.unSocCreditStartDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),maxDate:new Date()})" />
						<font color="red">*</font>
						至
						<input id="unSocCreditEndDate" name="unSocCreditEndDate" readonly="true" type="text" maxlength="20" class="input-mini Wdate required" value='<fmt:formatDate value="${companyInfo.unSocCreditEndDate}" pattern="yyyy-MM-dd"/>' onclick="WdatePicker({onpicked:dateWhite(this),minDate:new Date()})" />
						<font color="red">*</font>
					</td>
				</tr>
				<%-- <tr>
					<td class="ft_label">组织机构代码：</td>
					<td class="ft_content">
						<input id="organizationNo" name="organizationNo" type="text" maxlength="50" class="input-medium " value="${companyInfo.organizationNo}" onblur="handleComOnlyId();" onchange="isHideDateElement();" />
					</td>
					<td id="organizationNoTD1" class="ft_label">机构代码有效期：</td>
					<td id="organizationNoTD2" class="ft_content" colspan="2">
						<input id="organizationStartDate" name="organizationStartDate" readonly="true" type="text" maxlength="20" class="input-mini Wdate" value='<fmt:formatDate value="${companyInfo.organizationStartDate}" pattern="yyyy-MM-dd"/>' onclick="WdatePicker({onpicked:dateWhite(this),maxDate:new Date()})" />
						至
						<input id="organizationEndDate" name="organizationEndDate" readonly="true" type="text" maxlength="20" class="input-mini Wdate" value='<fmt:formatDate value="${companyInfo.organizationEndDate}" pattern="yyyy-MM-dd"/>' onclick="WdatePicker({onpicked:dateWhite(this),minDate:new Date()})" />
					</td>
				</tr>
				<tr>
					<td class="ft_label">营业执照注册号：</td>
					<td class="ft_content">
						<form:input path="busiLicRegNo" htmlEscape="false" maxlength="50" class="input-medium" onblur="handleComOnlyId();" onchange="isHideDateElement();" />
					</td>
					<td id="busiLicRegNoTD1" class="ft_label">营业执照有效期：</td>
					<td id="busiLicRegNoTD2" class="ft_content" colspan="2">
						<input id="busiLicRegStartDate" name="busiLicRegStartDate" readonly="true" type="text" maxlength="20" class="input-mini Wdate" value="<fmt:formatDate value="${companyInfo.busiLicRegStartDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),maxDate:new Date()})" />
						至
						<input id="busiLicRegEndDate" name="busiLicRegEndDate" type="text" readonly="true" maxlength="20" class="input-mini Wdate" value="<fmt:formatDate value="${companyInfo.busiLicRegEndDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),minDate:new Date()})" />
					</td>
				</tr>
				<tr>
					<td class="ft_label">登记税号：</td>
					<td class="ft_content">
						<form:input path="registerNationalTaxNo" htmlEscape="false" maxlength="50" class="input-medium" onblur="handleComOnlyId();" />
					</td>
				</tr>
				<tr>
					<td class="ft_label">特殊经营许可证：</td>
					<td class="ft_content">
						<form:input path="specialOperatePermit" htmlEscape="false" maxlength="32" class="input-medium" onchange="isHideDateElement();" />
					</td>
					<td id="specialOperatePermitTD1" class="ft_label">许可证有效期：</td>
					<td id="specialOperatePermitTD2" class="ft_content" colspan="2">
						<input id="specialOperateStartDate" name="specialOperateStartDate" readonly="true" type="text" maxlength="20" class="input-mini Wdate" value="<fmt:formatDate value="${companyInfo.specialOperateStartDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),maxDate:new Date()})" />
						至
						<input id="specialOperateEndDate" name="specialOperateEndDate" readonly="true" type="text" maxlength="20" class="input-mini Wdate" value="<fmt:formatDate value="${companyInfo.specialOperateEndDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),minDate:new Date()})" />
					</td>
				</tr> --%>
				<tr>
					<td class="ft_label">企业用地：</td>
					<td class="ft_content">
						<form:select id="companyLandUse" path="companyLandUse" class="input-medium required">
							<form:option value="" td="" />
							<form:options items="${fns:getDictList('LAND_USE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
					<td class="ft_label">企业性质：</td>
					<td class="ft_content">
						<form:select id="companyProperty" path="companyProperty" class="input-medium required">
							<form:option value="" td="" />
							<form:options items="${fns:getDictList('COMPANY_PROPERTY')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
					<td class="ft_label">注册资金(元)：</td>
					<td class="ft_content">
						<form:input id="registerCapital" path="registerCapital" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" htmlEscape="false" class="input-medium number required" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">法人代表名称：</td>
					<td class="ft_content">
						<form:input path="corporationName" htmlEscape="false" maxlength="200" class="input-medium required" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">法人代表手机号：</td>
					<td class="ft_content">
						<form:input path="corporationMobile" htmlEscape="false" maxlength="11" class="input-medium mobile required" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">法人代表身份证号：</td>
					<td class="ft_content">
						<form:input path="corporationCardIdNo" htmlEscape="false" maxlength="18" class="input-medium card required" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">实缴资本：</td>
					<td class="ft_content">
						<form:input id="paidCapital" path="paidCapital"  htmlEscape="false" class="input-medium  required" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">经营期限(年)：</td>
					<td class="ft_content">
						<form:input path="operatePeriod" htmlEscape="false" maxlength="2" class="input-medium number required" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">是否有财报：</td>
					<td class="ft_content">
						<form:select path="isEarnings" class="input-medium required">
							<form:option value="" td="" />
							<form:options items="${fns:getDictList('IS_HAVE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">注册地址：</td>
					<td class="ft_content" colspan="5">
						<form:select path="regProvince" id="companyRegProvince" class="input-small nuNullCheck" data-code="-1" onchange="loadDataCompany()" />
						&nbsp;省
						<form:select path="regCity" id="companyRegCity" class="input-small nuNullCheck" data-code="-1" onchange="loadDataCompany()" />
						&nbsp;市
						<form:select path="regDistinct" id="companyRegDistinct" class="input-small nuNullCheck" data-code="-1" onchange="loadDataCompany()" />
						&nbsp;区&nbsp;
						<font color="red">*</font>
						<span style="width: 15px; display: inline-block;"></span>
						地址明细：
						<input type="text" name="regDetails" id="companyRegDetails" class="input-medium required" maxlength="200" value="${companyInfo.regDetails }" style="margin: 0px; position: relative; display: inline-block; vertical-align: middle; width:265px" onblur="changeCompanyContDetails();" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label" style="text-align: right;">经营地址与注册地址是否一致：</td>
					<td class="ft_content" colspan="2" style="text-align: left;">
						<form:radiobuttons path="isAddrResi" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="input-small" onchange="changeCompanyInfoValue();" />
					</td>
				</tr>
				<tr>
					<td class="ft_label">经营地址：</td>
					<td class="ft_content" colspan="5">
						<form:select path="operateProvince" id="companyContProvince" class="input-small nuNullCheck" data-code="-1" />
						&nbsp;省
						<form:select path="operateCity" id="companyContCity" class="input-small nuNullCheck" data-code="-1" />
						&nbsp;市
						<form:select path="operateDistinct" id="companyContDistinct" class="input-small nuNullCheck" data-code="-1" />
						&nbsp;区&nbsp;
						<font color="red">*</font>
						<span style="width: 15px; display: inline-block;"></span>
						地址明细：
						<input type="text" name="operateDetails" id="companyContDetails" onblur="this.value=reSpaces(this.value);"  class="input-medium required" maxlength="200" value="${companyInfo.operateDetails }" style="margin: 0px; position: relative; display: inline-block; vertical-align: middle; width:265px" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">经营区域：</td>
					<td class="ft_content">
						<form:input path="operateArea" htmlEscape="false" maxlength="100" class="input-medium required" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">企业状况：</td>
					<td class="ft_content">
						<form:select id="companyStatus" path="companyStatus" class="input-medium required">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('COMPANY_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
					<td class="ft_label">员工人数：</td>
					<td class="ft_content">
						<form:input path="currStaffNum" htmlEscape="false" maxlength="8" class="input-medium number required" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">经营面积(㎡)：</td>
					<td class="ft_content">
						<form:input path="operateAreas" htmlEscape="false" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" class="input-medium number required" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">企业网址：</td>
					<td class="ft_content">
						<form:input path="companySite" htmlEscape="false" maxlength="100" class="input-medium" />
					</td>
					<td class="ft_label">主营业务：</td>
					<td class="ft_content">
						<form:input path="mainBusiness" htmlEscape="false" maxlength="100" class="input-medium required" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">年营业额(元)：</td>
					<td class="ft_content">
						<form:input onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" id="annualTurnover" path="annualTurnover" htmlEscape="false" class="input-medium required" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<%-- <td class="ft_label">借款到期日期：</td>
					<td class="ft_content">
						<input id="landEndDate" name="landEndDate" type="text" maxlength="20" class="input-medium Wdate required"  value="<fmt:formatDate value='${companyInfo.landEndDate}' pattern='yyyy-MM-dd'/>" onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd'});" />
					</td> --%>
					<td class="ft_label">企业名下是否有房产：</td>
					<td class="ft_content">
						<form:select id="isHaveHouses" path="isHaveHouses" class="input-medium required">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
					<td class="ft_label">办公环境使用年限(年)：</td>
					<td class="ft_content">
						<form:input path="currOfficeLifetime" htmlEscape="false" maxlength="4" class="input-medium number required" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">所属行业：</td>
					<td class="ft_content" colspan="5">
						<form:select id="categoryMain3" path="categoryMain" class="input-medium required" onchange="companyLoadCategory('categoryMain3','categoryLarge3','');validateMsg('categoryMain3');">
							<form:option value="" label="" />
							<form:options items="${categoryMainList3}" itemLabel="induName" itemValue="induCode" htmlEscape="false" />
						</form:select>
						门类
						<font color="red">*</font>
						<span class="help-inline"> </span>
						<form:select id="categoryLarge3" path="categoryLarge" class="input-medium required" onchange="companyLoadCategory('categoryLarge3','categoryMedium3','');validateMsg('categoryLarge3');">
							<form:option value="" label="" />
							<form:options items="${categoryLargeList3}" itemLabel="induName" itemValue="induCode" htmlEscape="false" />
						</form:select>
						大类
						<font color="red">*</font>
						<span class="help-inline"> </span>
						<form:select id="categoryMedium3" path="categoryMedium" class="input-medium required" onchange="companyLoadCategory('categoryMedium3','categorySmall3','');validateMsg('categoryMedium3');">
							<form:option value="" label="" />
							<form:options items="${categoryMediumList3}" itemLabel="induName" itemValue="induCode" htmlEscape="false" />
						</form:select>
						中类
						<font color="red">*</font>
						<span class="help-inline"> </span>
						<form:select id="categorySmall3" path="categorySmall" class="input-medium required" onchange="validateMsg('categorySmall3');">
							<form:option value="" label="" />
							<form:options items="${categorySmallList3}" itemLabel="induName" itemValue="induCode" htmlEscape="false" />
						</form:select>
						小类
						<span class="help-inline">
							<font color="red">*</font>
						</span>
					</td>
				</tr>
				<tr>
					<td class="ft_label">纳税情况：</td>
					<td class="ft_content" colspan="5">
						<pre class="textarea-width pre-style" style="margin-top: 10px;" id="preStateOfTaxation"></pre>
						<form:textarea path="stateOfTaxation" htmlEscape="false" class="textarea-width textarea-style required" style="margin-top: 10px;" rows="4" maxlength="300" onkeyup="adjustTextareaLength('stateOfTaxation','preStateOfTaxation')" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">股本结构描述：</td>
					<td class="ft_content" colspan="5">
						<pre class="textarea-width pre-style" style="margin-top: 10px;" id="preCapitalStructureCom"></pre>
						<form:textarea path="capitalStructureCom" htmlEscape="false" rows="4" class="textarea-style textarea-width required" style="margin-top: 10px;" maxlength="1000" onkeyup="adjustTextareaLength('capitalStructureCom','preCapitalStructureCom')" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="searchButton" colspan="6" style="text-align: right;">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="return handleComOnlyId();" />
						&nbsp;
					</td>
				</tr>
				<tr style="display:none">
					<td class="ft_label" style="text-align: right;">是否上传工单：</td>
					<td class="ft_label" colspan="2" style="text-align: left;">
						<form:select path="svFlag" class="input-medium required">
							<form:option value="1" label="是" />
					    </form:select>
						<font color="red">*</font>
					</td>
				</tr>
			</table>
		</form:form>
	</div>
</div>