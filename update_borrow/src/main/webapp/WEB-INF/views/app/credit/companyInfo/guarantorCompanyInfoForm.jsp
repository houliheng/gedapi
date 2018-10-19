<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>新增担保公司信息</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		isHideDateElement();
		document.getElementById("annualTurnover").value = outputmoney("${guarantorCompanyInfo.annualTurnover}");
		document.getElementById("operateAreas").value = outputmoney("${guarantorCompanyInfo.operateAreas}");
		document.getElementById("registerCapital").value = outputmoney("${guarantorCompanyInfo.registerCapital}");
		
		$("#guarantorCompanyInfoForm").validate({
		submitHandler : function() {
			saveCompanyInfo();
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {

			checkReq(error, element);

		}
		});
		if ('${readOnly}' == '0') {
			setPageReadOnly();
			$("#isAddrResi1").attr("disabled", true);
			$("#isAddrResi2").attr("disabled", true);
			$("#btnSubmit").hide();
		}

		adjustTextareaLength("stateOfTaxation", "preStateOfTaxation");
		adjustTextareaLength("capitalStructureCom", "preCapitalStructureCom");

		if ('${guarantorCompanyInfo.isAddrResi}' == null || '${guarantorCompanyInfo.isAddrResi}' == '') {
			//现居地与户籍地址是否一致，默认为否
			$("input[id='isAddrResi2']").attr("checked", "true");
		}
		if ("${guarantorCompanyInfo.isAddrResi}" == "1") {
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

		if (!checkIsNull('${guarantorCompanyInfo.regDistinct}')) {
			setName(data, "companyReg", '${guarantorCompanyInfo.regProvince}', '${guarantorCompanyInfo.regCity}', '${guarantorCompanyInfo.regDistinct}');
			if (!checkIsNull('${guarantorCompanyInfo.operateDistinct}')) {
				setName(data, "companyCont", '${guarantorCompanyInfo.operateProvince}', '${guarantorCompanyInfo.operateCity}', '${guarantorCompanyInfo.operateDistinct}');
			}
		}
	

	});

	function loadData() {
		var $addrResi = $("input[name='isAddrResi']:checked").val();
		if (1 == $addrResi) {
			$("#companyContProvince").val($("#companyRegProvince").val());
			$("#s2id_companyContProvince>.select2-choice>.select2-chosen").html($("#s2id_companyRegProvince>.select2-choice>.select2-chosen").html());

			$("#companyContCity").append($("#companyRegCity").html());
			$("#companyContCity").val($("#companyRegCity").val());
			$("#s2id_companyContCity>.select2-choice>.select2-chosen").html($("#s2id_companyRegCity>.select2-choice>.select2-chosen").html());

			$("#companyContDistinct").append($("#companyRegDistinct").html());
			$("#companyContDistinct").val($("#companyRegDistinct").val());
			$("#s2id_companyContDistinct>.select2-choice>.select2-chosen").html($("#s2id_companyRegDistinct>.select2-choice>.select2-chosen").html());

			$("#companyContDetails").val($("#companyRegDetails").val());
		}
	}

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
	function handleComOnlyId() {
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
			} else if (checkIsNull($("#registerNationalTaxNo").val()) && checkIsNull($("#organizationNo").val()) && checkIsNull($("#busiLicRegNo").val())) {

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
	}

	function companyLoadCity(val) {
		if ("reg" == val) {
			$("#companyRegCity").empty();
			$("#companyRegCity").append("<option value=''>请选择</option>");
			var $companyRegCity = $("#s2id_companyRegCity>.select2-choice>.select2-chosen");
			$companyRegCity.html("请选择");
			$("#companyRegDistinct").empty();
			$("#companyRegDistinct").append("<option value=''>请选择</option>");
			var $companyRegDistinct = $("#s2id_companyRegDistinct>.select2-choice>.select2-chosen");
			$companyRegDistinct.html("请选择");
			//现居地等于户籍地
			var $isAddrResi = $("input[name='isAddrResi']:checked").val();
			if (1 == $isAddrResi) {
				$("#companyContProvince").val($("#companyRegProvince").val());
				$("#s2id_companyContProvince>.select2-choice>.select2-chosen").html($("#s2id_companyRegProvince>.select2-choice>.select2-chosen").html());
				$("#companyContCity").val($("#companyRegCity").val());
				$("#s2id_companyContCity>.select2-choice>.select2-chosen").html($("#s2id_companyRegCity>.select2-choice>.select2-chosen").html());
				$("#companyContDistinct").val($("#companyRegDistinct").val());
				$("#s2id_companyContDistinct>.select2-choice>.select2-chosen").html($("#s2id_companyRegDistinct>.select2-choice>.select2-chosen").html());
			}
			$.post("${ctx}/sys/area/treeNode", {
				parentId : $("#companyRegProvince").val()
			}, function(data) {
				$.each(data, function(i, val) {
					$("#companyRegCity").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
				});
				//现居地等于户籍地
				if (1 == $isAddrResi) {
					$("#companyContCity").empty();
					$("#companyContCity").append($("#companyRegCity").html());
				}
			});
		} else {
			$("#companyContCity").empty();
			$("#companyContCity").append("<option value=''>请选择</option>");
			var $companyContCity = $("#s2id_companyContCity>.select2-choice>.select2-chosen");
			$companyContCity.html("请选择");
			$("#companyContDistinct").empty();
			$("#companyContDistinct").append("<option value=''>请选择</option>");
			var $companyContDistinct = $("#s2id_companyContDistinct>.select2-choice>.select2-chosen");
			$companyContDistinct.html("请选择");
			//现居地等于户籍地
			var $isAddrResi = $("input[name='isAddrResi']:checked").val();
			if (1 == $isAddrResi) {
				$("#companyContProvince").val($("#companyRegProvince").val());
				$("#s2id_companyContProvince>.select2-choice>.select2-chosen").html($("#s2id_companyRegProvince>.select2-choice>.select2-chosen").html());
				$("#companyContCity").val($("#companyRegCity").val());
				$("#s2id_companyContCity>.select2-choice>.select2-chosen").html($("#s2id_companyRegCity>.select2-choice>.select2-chosen").html());
				$("#companyContDistinct").val($("#companyRegDistinct").val());
				$("#s2id_companyContDistinct>.select2-choice>.select2-chosen").html($("#s2id_companyRegDistinct>.select2-choice>.select2-chosen").html());
			}
			$.post("${ctx}/sys/area/treeNode", {
				parentId : $("#companyContProvince").val()
			}, function(data) {
				$.each(data, function(i, val) {
					$("#companyContCity").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
				});

				//现居地等于户籍地
				if (1 == $isAddrResi) {
					$("#companyContCity").empty();
					$("#companyContCity").append($("#companyRegCity").html());
				}
			});
		}
	}
	function companyLoadDistinct(val) {
		if ("reg" == val) {
			$("#companyRegDistinct").empty();
			$("#companyRegDistinct").append("<option value=''>请选择</option>");
			var $companyRegDistinct = $("#s2id_regDistinct>.select2-choice>.select2-chosen");
			$companyRegDistinct.html("请选择");
			//现居地等于户籍地
			var $isAddrResi = $("input[name='isAddrResi']:checked").val();
			if (1 == $isAddrResi) {

				$("#companyContCity").val($("#companyRegCity").val());
				$("#s2id_companyContCity>.select2-choice>.select2-chosen").html($("#s2id_companyRegCity>.select2-choice>.select2-chosen").html());

				$("#companyCompanyContCity").empty();
				$("#companyCompanyContCity").append("<option value=''>请选择</option>");
				var $companyCompanyContCity = $("#s2id_companyCompanyContCity>.select2-choice>.select2-chosen");
				$companyCompanyContCity.html("请选择");
			}
			$.post("${ctx}/sys/area/treeNode", {
				parentId : $("#companyRegCity").val()
			}, function(data) {
				$.each(data, function(i, val) {
					$("#companyRegDistinct").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
				});
				//现居地等于户籍地
				if (1 == $isAddrResi) {
					$("#companyContDistinct").empty();
					$("#companyContDistinct").append($("#companyRegDistinct").html());
				}
			});
		} else {
			$("#companyContDistinct").empty();
			$("#companyContDistinct").append("<option value=''>请选择</option>");
			var $companyContDistinct = $("#s2id_companyContDistinct>.select2-choice>.select2-chosen");
			$companyContDistinct.html("请选择");
			if (1 == $isAddrResi) {
				$("#companyContCity").val($("#regCity").val());
				$("#s2id_companyContCity>.select2-choice>.select2-chosen").html($("#s2id_regCity>.select2-choice>.select2-chosen").html());

				$("#companyCompanyContDistinct").empty();
				$("#companyCompanyContDistinct").append("<option value=''>请选择</option>");
				var $companyCompanyContDistinct = $("#s2id_companyContDistinct>.select2-choice>.select2-chosen");
				$companyCompanyContDistinct.html("请选择")
				$("#companyContDetails").val($("#companyRegDetails").val());
			}
			$.post("${ctx}/sys/area/treeNode", {
				parentId : $("#companyContCity").val()
			}, function(data) {
				$.each(data, function(i, val) {
					$("#companyContDistinct").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
				});

				//现居地等于户籍地
				if (1 == $isAddrResi) {
					$("#companyContDistinct").empty();
					$("#companyContDistinct").append($("#companyRegDistinct").html());
				}
			});
		}
	}
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

	//保存担保企业信息
	function saveCompanyInfo() {
		if(($("#companyRegDistinct").val() == "-1") || ($("#operateDistinct").val() == "-1")){
			alertx("请填写现居住地！");
			closeTip();
		}else{
			loading();
			var valueT1 = $("#registerCapital").val().replace(/,/g, "");
			var valueT2 = $("#annualTurnover").val().replace(/,/g, "");
			var valueT3 = $("#operateAreas").val().replace(/,/g, "");
			$("#registerCapital").val(valueT1);
			$("#annualTurnover").val(valueT2);
			$("#operateAreas").val(valueT3);
			top.$.jBox.tip.mess = null;
			var company = $("#guarantorCompanyInfoForm").serializeJson();
			company.operateProvince = $("#companyContProvince").val();
			company.operateCity = $("#companyContCity").val();
			company.operateDistinct = $("#companyContDistinct").val();
			$.post("${ctx}/credit/guarantorCompanyInfo/save?flag=1", company, function(data) {
				if (data) {
					closeTip();
					if (data.status == 1) {
						alertx(data.message, function() {
							parent.$.loadDiv("guarantorCompanyInfo", "${ctx }/credit/guarantorCompanyInfo/list", {
								applyNo : '${guarantorCompanyInfo.currApplyNo}'
							}, "post");
							closeJBox();
						});
					} else {
						alertx(data.message);
					}

				}
			});
		}
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
			document.getElementById("companyContDetails").style.backgroundColor = "";
		} else if (0 == $isAddrResi) {
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

	function changeCompanyContDistinctValue() {
		var $isAddrResi = $("input[name='isAddrResi']:checked").val();
		if (1 == $isAddrResi) {
			$("#companyContDistinct").val($("#companyRegDistinct").val());
			$("#s2id_companyContDistinct>.select2-choice>.select2-chosen").html($("#s2id_companyRegDistinct>.select2-choice>.select2-chosen").html());
			$("#companyContDetails").val($("#companyRegDetails").val());
		}
	}

	function changeCompanyContDetails() {
		$("#companyRegDetails").val($("#companyRegDetails").val().replace(/\s/g,""));
		var $isAddrResi = $("input[name='isAddrResi']:checked").val();
		if (1 == $isAddrResi) {
			$("#companyContDetails").val($("#companyRegDetails").val());
			document.getElementById("companyContDetails").style.backgroundColor = "";
		}
	}
</script>
</head>
<body>
	<div class="searchInfo">
		<h3 class="searchTitle">担保企业信息</h3>
		<div class="searchCon">
			<form:form id="guarantorCompanyInfoForm" modelAttribute="guarantorCompanyInfo" action="" method="post" class="form-horizontal">
				<form:hidden path="id" />
				<input type="hidden" name="currApplyNo" value="${guarantorCompanyInfo.currApplyNo }" />
				<sys:message content="${message}" />
				<table class="fromTable filter">
					<tr>
						<td class="ft_label">工商登记名称：</td>
						<td class="ft_content">
							<form:input path="busiRegName" onblur="this.value=reSpaces(this.value);" htmlEscape="false" maxlength="30" class="input-medium required" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">成立时间：</td>
						<td class="ft_content">
							<input id="foundDate" name="foundDate" readonly="true" type="text" maxlength="20" class="input-medium Wdate required" value="<fmt:formatDate value="${guarantorCompanyInfo.foundDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),maxDate:new Date(),dateFmt:'yyyy-MM-dd'});" />
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
						<td class="ft_label">是否推送外访系统：</td>
						<td class="ft_content">
							<form:select path="svFlag" id="svFlag" class="input-small required" onchange="valueIsRquired(this.value)">
								<option value="">请选择</option>
								<form:options items="${fns:getDictList('SV_FLAG')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">统一社会信用代码：</td>
						<td class="ft_content">
							<form:input path="unSocCreditNo" htmlEscape="false" maxlength="50" class="input-medium" onblur="handleComOnlyId();" onchange="isHideDateElement();" />
						</td>
						<td id="unSocCreditNoTD1" class="ft_label">信用代码有效期：</td>
						<td id="unSocCreditNoTD2" class="ft_content" colspan="2">
							<input id="unSocCreditStartDate" name="unSocCreditStartDate" readonly="true" type="text" maxlength="20" class="input-mini Wdate" value="<fmt:formatDate value="${guarantorCompanyInfo.unSocCreditStartDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),maxDate:new Date()})" />
							至
							<input id="unSocCreditEndDate" name="unSocCreditEndDate" readonly="true" type="text" maxlength="20" class="input-mini Wdate" value="<fmt:formatDate value="${guarantorCompanyInfo.unSocCreditEndDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),minDate:new Date()})" />
						</td>
					</tr>
					<tr>
						<td class="ft_label">组织机构代码：</td>
						<td class="ft_content">
							<form:input path="organizationNo" htmlEscape="false" maxlength="50" class="input-medium" onblur="handleComOnlyId();" onchange="isHideDateElement();" />
						</td>
						<td id="organizationNoTD1" class="ft_label">机构代码有效期：</td>
						<td id="organizationNoTD2" class="ft_content" colspan="2">
							<input id="organizationStartDate" name="organizationStartDate" readonly="true" type="text" maxlength="20" class="input-mini Wdate" value="<fmt:formatDate value="${guarantorCompanyInfo.organizationStartDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),maxDate:new Date()})" />
							至
							<input id="organizationEndDate" name="organizationEndDate" readonly="true" type="text" maxlength="20" class="input-mini Wdate" value="<fmt:formatDate value="${guarantorCompanyInfo.organizationEndDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),minDate:new Date()})" />
						</td>
					</tr>
					<tr>
						<td class="ft_label">营业执照注册号：</td>
						<td class="ft_content">
							<form:input path="busiLicRegNo" htmlEscape="false" maxlength="50" class="input-medium" onblur="handleComOnlyId();" onchange="isHideDateElement();" />
						</td>
						<td id="busiLicRegNoTD1" class="ft_label">营业执照有效期：</td>
						<td id="busiLicRegNoTD2" class="ft_content" colspan="2">
							<input id="busiLicRegStartDate" name="busiLicRegStartDate" readonly="true" type="text" maxlength="20" class="input-mini Wdate" value="<fmt:formatDate value="${guarantorCompanyInfo.busiLicRegStartDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),maxDate:new Date()})" />
							至
							<input id="busiLicRegEndDate" name="busiLicRegEndDate" readonly="true" type="text" maxlength="20" class="input-mini Wdate" value="<fmt:formatDate value="${guarantorCompanyInfo.busiLicRegEndDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),minDate:new Date()})" />
						</td>
					</tr>
					<tr>
						<td class="ft_label">税务登记税号：</td>
						<td class="ft_content">
							<form:input path="registerNationalTaxNo" htmlEscape="false" maxlength="50" class="input-medium" onblur="handleComOnlyId();" />
						</td>
						<%-- <td class="ft_label">税务登记税号有效期：</td>
						<td class="ft_content" colspan="2">
							<input id="registerNatTaxStartDate" name="registerNatTaxStartDate" readonly="true" type="text" maxlength="20" class="input-mini Wdate" value="<fmt:formatDate value="${guarantorCompanyInfo.registerNatTaxStartDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),maxDate:'#F{$dp.$D(\'registerNatTaxEndDate\')}'})" />
							至
							<input id="registerNatTaxEndDate" name="registerNatTaxEndDate" readonly="true" type="text" maxlength="20" class="input-mini Wdate" value="<fmt:formatDate value="${guarantorCompanyInfo.registerNatTaxEndDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),minDate:'#F{$dp.$D(\'registerNatTaxStartDate\')}'})" />
						</td> --%>
					</tr>
					<%-- <tr>
						<td class="ft_label">登记税号（地）：</td>
						<td class="ft_content">
							<form:input path="registerLandTaxNo" htmlEscape="false" maxlength="50" class="input-medium required" />
						</td>
						<td class="ft_label">登记税号(地)有效期：</td>
						<td class="ft_content" colspan="2">
							<input id="registerLandTaxStartDate" name="registerLandTaxStartDate" readonly="true" type="text" maxlength="20" class="input-mini Wdate required" value="<fmt:formatDate value="${guarantorCompanyInfo.registerLandTaxStartDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),maxDate:'#F{$dp.$D(\'registerLandTaxEndDate\')}'})" />
							至
							<input id="registerLandTaxEndDate" name="registerLandTaxEndDate" readonly="true" type="text" maxlength="20" class="input-mini Wdate required" value="<fmt:formatDate value="${guarantorCompanyInfo.registerLandTaxEndDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),minDate:'#F{$dp.$D(\'registerLandTaxStartDate\')}'})" />
						</td>
					</tr> --%>
					<tr>
						<td class="ft_label">特殊经营许可证：</td>
						<td class="ft_content">
							<form:input path="specialOperatePermit" htmlEscape="false" maxlength="32" class="input-medium" onchange="isHideDateElement();" />
						</td>
						<td id="specialOperatePermitTD1" class="ft_label">许可证有效期：</td>
						<td id="specialOperatePermitTD2" class="ft_content" colspan="2">
							<input id="specialOperateStartDate" name="specialOperateStartDate" readonly="true" type="text" maxlength="20" class="input-mini Wdate" value="<fmt:formatDate value="${guarantorCompanyInfo.specialOperateStartDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),maxDate:new Date()})" />
							至
							<input id="specialOperateEndDate" name="specialOperateEndDate" readonly="true" type="text" maxlength="20" class="input-mini Wdate " value="<fmt:formatDate value="${guarantorCompanyInfo.specialOperateEndDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),minDate:new Date()})" />
						</td>
					</tr>
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
						<td class="ft_label">法人代表证件号：</td>
						<td class="ft_content">
							<form:input path="corporationCardIdNo" htmlEscape="false" maxlength="18" class="input-medium card required" />
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">实缴资本：</td>
						<td class="ft_content">
							<form:input path="paidCapital"  htmlEscape="false" class="input-medium required" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">注册资金(元)：</td>
						<td class="ft_content">
							<form:input path="registerCapital" htmlEscape="false" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" class="input-medium number required" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">经营期限：</td>
						<td class="ft_content">
							<form:input path="operatePeriod" htmlEscape="false" maxlength="2" class="input-medium number required" />
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">注册地址：</td>
						<td class="ft_content" colspan="5">
							<form:select path="regProvince" id="companyRegProvince" class="input-small nuNullCheck" data-code="-1" onchange="loadData()"></form:select>
							&nbsp;省
							<form:select path="regCity" id="companyRegCity" class="input-small nuNullCheck" data-code="-1" onchange="loadData()"></form:select>
							&nbsp;市
							<form:select path="regDistinct" id="companyRegDistinct" class="input-small nuNullCheck" data-code="-1" onchange="loadData()"></form:select>
							&nbsp;区&nbsp;
							<span style="width: 15px; display: inline-block;"></span>
							地址明细：
							<input type="text" name="regDetails" id="companyRegDetails" class="input-medium required" maxlength="200" value="${guarantorCompanyInfo.regDetails }" style="margin: 0px; position: relative; display: inline-block; vertical-align: middle; width:180px" onblur="changeCompanyContDetails();" />
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
							<form:select class="input-small nuNullCheck" path="operateProvince" name="operateProvince" id="companyContProvince" data-code="-1"></form:select>
							&nbsp;省
							<form:select class="input-small nuNullCheck" path="operateCity" name="operateCity" id="companyContCity" data-code="-1"></form:select>
							&nbsp;市
							<form:select class="input-small nuNullCheck" path="operateDistinct" name="operateDistinct" id="companyContDistinct" data-code="-1"></form:select>
							&nbsp;区&nbsp;
							<font color="red">*</font>
							<span style="width: 15px; display: inline-block;"></span>
							地址明细：
							<input type="text" name="operateDetails" onblur="this.value=reSpaces(this.value);" id="companyContDetails" class="input-medium required" maxlength="200" value="${guarantorCompanyInfo.operateDetails }" style="margin: 0px; position: relative; display: inline-block; vertical-align: middle; width:180px" />
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
							<form:input path="operateAreas" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" htmlEscape="false" class="input-medium required" />
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
							<form:input path="mainBusiness" htmlEscape="false" maxlength="300" class="input-medium required" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">年营业额(元)：</td>
						<td class="ft_content">
							<form:input path="annualTurnover" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" htmlEscape="false" class="input-medium required" />
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">企业名下是否有房产：</td>
						<td class="ft_content">
							<form:select id="isHaveHouses" path="isHaveHouses" class="input-medium required">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
						</td>
						<td class="ft_label">办公环境使用年限：</td>
						<td class="ft_content">
							<form:input path="currOfficeLifetime" htmlEscape="false" maxlength="4" class="input-medium number required" />
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">所属行业：</td>
						<td class="ft_content" colspan="5">
							<form:select id="categoryMain3" path="categoryMain" class="input-medium required" onchange="companyLoadCategory('categoryMain3','categoryLarge3')">
								<form:option value="" label="" />
								<form:options items="${categoryMainList}" itemLabel="induName" itemValue="induCode" htmlEscape="false" />
							</form:select>
							门类
							<span class="help-inline">
								<font color="red">*</font>
							</span>
							<form:select id="categoryLarge3" path="categoryLarge" class="input-medium required" onchange="companyLoadCategory('categoryLarge3','categoryMedium3')">
								<form:option value="" label="" />
								<form:options items="${categoryLargeList}" itemLabel="induName" itemValue="induCode" htmlEscape="false" />
							</form:select>
							大类
							<span class="help-inline">
								<font color="red">*</font>
							</span>
							<form:select id="categoryMedium3" path="categoryMedium" class="input-medium required" onchange="companyLoadCategory('categoryMedium3','categorySmall3')">
								<form:option value="" label="" />
								<form:options items="${categoryMediumList}" itemLabel="induName" itemValue="induCode" htmlEscape="false" />
							</form:select>
							中类
							<span class="help-inline">
								<font color="red">*</font>
							</span>
							<form:select id="categorySmall3" path="categorySmall" class="input-medium required">
								<form:option value="" label="" />
								<form:options items="${categorySmallList}" itemLabel="induName" itemValue="induCode" htmlEscape="false" />
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
							<pre class="pre-style" style="width:700px;margin-top: 10px;" id="preStateOfTaxation"></pre>
							<form:textarea path="stateOfTaxation" htmlEscape="false" rows="4" cssClass="textarea-style required" maxlength="300" style="width:700px;margin-top: 10px;" onkeyup="adjustTextareaLength('stateOfTaxation','preStateOfTaxation')" />
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">股本结构描述：</td>
						<td class="ft_content" colspan="5">
							<pre class="pre-style" style="width:700px;margin-top: 10px;" id="preCapitalStructureCom"></pre>
							<form:textarea path="capitalStructureCom" htmlEscape="false" rows="4" cssClass="textarea-style required" maxlength="1000" style="width:700px;margin-top: 10px;" onkeyup="adjustTextareaLength('capitalStructureCom','preCapitalStructureCom')" />
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="searchButton" colspan="6" style="text-align: right;">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="return handleComOnlyId();" />
							&nbsp;
							<input id="btnButton" class="btn btn-primary" type="button" value="返回" onclick="closeJBox();" />
							&nbsp;
						</td>
					</tr>
					 
				</table>
			</form:form>
		</div>
	</div>
</body>
</html>