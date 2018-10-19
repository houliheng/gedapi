<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">
	$(document).ready(function() {
		valueIsRquiredMate(${custMateInfo.isFixedHouse});
		if ('${custMateInfo.isFixedHouse}' == "1") {
			document.getElementById("mateEstateValuation").value = outputmoney("${custMateInfo.estateValuation }");
		}
		if ('${custInfo.isHaveAssure}' == "0") {
			$("#mateToGuarantorLabelId").hide();
		}
		adjustTextareaLength("livingStatusDescMate", "preMate");
		document.getElementById("matePerAnnualIncome").value = outputmoney("${custMateInfo.perAnnualIncome }");
		$("#custMateInfoForm").validate({
		submitHandler : validateIdNum,
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			$("#messageBox").text("输入有误，请先更正。");
			checkReq(error, element)
		}
		});
		if ('${custMateInfo.addrResi}' == null || '${custMateInfo.addrResi}' == '') {
			//现居地与户籍地址是否一致，默认为否
			$("input[id='addrResi4']").attr("checked", "true");
		}
		if ('${custMateInfo.addrResi}' == '1') {
			changeMateValue();
		}
		//初始化身份证终止日期
		if ('${custMateInfo.personIdEndDate}' == null || '${custMateInfo.personIdEndDate}' == '') {
			$("input[id='yesMate']").attr("checked", "true");
			$("#personIdEndDateMate").hide();
			$("#cardEndTextMate").hide();
			$("#personIdEndDateMateRed").hide();
		} else {
			$("input[id='noMate']").attr("checked", "true");
			$("#personIdEndDateMate").show();
			$("#cardEndTextMate").show();
			$("#personIdEndDateMateRed").show();
		}

		new CitySelect({
		data : data,
		provId : '#mateRegProvince',
		cityId : '#mateRegCity',
		areaId : '#mateRegDistinct',
		isSelect : false
		});
		new CitySelect({
		data : data,
		provId : '#mateContProvince',
		cityId : '#mateContCity',
		areaId : '#mateContDistinct',
		isSelect : false
		});

		if (!checkIsNull('${custMateInfo.regDistinct}')) {
			setName(data, "mateReg", '${custMateInfo.regProvince}', '${custMateInfo.regCity}', '${custMateInfo.regDistinct}');
			if (!checkIsNull('${custMateInfo.contDistinct}')) {
				setName(data, "mateCont", '${custMateInfo.contProvince}', '${custMateInfo.contCity}', '${custMateInfo.contDistinct}');
			}
		}

	});

	function loadDataMate() {
		//console.info(document.getElementById("mateContProvince").options.length);
		var $addrResi = $("form[id='custMateInfoForm'] input[name='addrResi']:checked").val();
		if (1 == $addrResi) {
			$("#mateContProvince").val($("#mateRegProvince").val());
			$("#s2id_mateContProvince>.select2-choice>.select2-chosen").html($("#s2id_mateRegProvince>.select2-choice>.select2-chosen").html());

			$("#mateContCity").append($("#mateRegCity").html());
			$("#mateContCity").val($("#mateRegCity").val());
			$("#s2id_mateContCity>.select2-choice>.select2-chosen").html($("#s2id_mateRegCity>.select2-choice>.select2-chosen").html());

			$("#mateContDistinct").append($("#mateRegDistinct").html());
			$("#mateContDistinct").val($("#mateRegDistinct").val());
			$("#s2id_mateContDistinct>.select2-choice>.select2-chosen").html($("#s2id_mateRegDistinct>.select2-choice>.select2-chosen").html());

			$("#mateContDetails").val($("#mateRegDetails").val());
		}
	}

	function validateIdNum() {
		var id1 = $("#mateId").val();
		var idNum = $("#idNumMate").val();
		var mobileNum = $("#mobileNumMate").val();
		$.ajax({
		type : "post",
		data : {
		idNum : idNum,
		id : id1,
		mobileNum : mobileNum,
		applyNo : '${actTaskParam.applyNo}'
		},
		url : "${ctx}/custinfo/custInfo/validateIdNum",
		dataType : "json",
		success : function(data) {
			if (data.status == "1") {
				saveCustMateInfo();
			} else {
				alertx(data.message);
			}
		}
		});
	}
	//身份证终止日期显示与否
	function changeShowOrHide2() {
		var $cardFlagMate = $("input[name='cardFlagMate']:checked").val();
		if ("yes" == $cardFlagMate) {
			$("input[id='yesMate']").attr("checked", "true");
			$("#personIdEndDateMate").hide();
			$("#personIdEndDateMate").val('');
			$("#cardEndTextMate").hide();
			$("#personIdEndDateMateRed").hide();
		} else {
			$("input[id='noMate']").attr("checked", "true");
			$("#personIdEndDateMate").show();
			$("#cardEndTextMate").show();
			$("#personIdEndDateMateRed").show();
		}
	}

	var treeCategory2 = [ "categoryMain2", "categoryLarge2", "categoryMedium2", "categorySmall2" ];
	function mateLoadCategory(root, curr) {
		var parentInduCode = $("#" + root).select2("val");
		var emptyFlag = false;
		for (var i = 0; i < treeCategory2.length; i++) {
			if (curr == treeCategory2[i]) {
				emptyFlag = true;
			}
			if (emptyFlag) {
				$("#" + treeCategory2[i]).select2("val", "");
				$("#" + treeCategory2[i]).empty();
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

	function changeMateContDistinctValue() {
		var $addrResi = $("form[id='custMateInfoForm'] input[name='addrResi']:checked").val();
		if (1 == $addrResi) {
			$("#mateContDistinct").val($("#mateRegDistinct").val());
			$("#s2id_mateContDistinct>.select2-choice>.select2-chosen").html($("#s2id_mateRegDistinct>.select2-choice>.select2-chosen").html());
			validateMateMsg('mateContDistinct');
		}
	}

	function changeMateContDetails() {
		$("#mateRegDetails").val($("#mateRegDetails").val().replace(/\s/g, ""));
		var $addrResi = $("form[id='custMateInfoForm'] input[name='addrResi']:checked").val();
		if (1 == $addrResi) {
			$("#mateContDetails").val($("#mateRegDetails").val());
			validateMateMsg('mateContDetails');
		}
	}

	function changeMateValue() {
		//现居地等于户籍地
		var $addrResi = $("form[id='custMateInfoForm'] input[name='addrResi']:checked").val();
		if (1 == $addrResi) {
			$("#mateContProvince").val($("#mateRegProvince").val());
			$("#s2id_mateContProvince>.select2-choice>.select2-chosen").html($("#s2id_mateRegProvince>.select2-choice>.select2-chosen").html());
			$("#mateContCity").append($("#mateRegCity").html());
			$("#mateContCity").val($("#mateRegCity").val());
			$("#s2id_mateContCity>.select2-choice>.select2-chosen").html($("#s2id_mateRegCity>.select2-choice>.select2-chosen").html());
			$("#mateContDistinct").append($("#mateRegDistinct").html());
			$("#mateContDistinct").val($("#mateRegDistinct").val());
			$("#s2id_mateContDistinct>.select2-choice>.select2-chosen").html($("#s2id_mateRegDistinct>.select2-choice>.select2-chosen").html());
			$("#mateContDetails").val($("#mateRegDetails").val());
			$("#mateContProvince").prop("disabled", true).select2();
			$("#mateContCity").prop("disabled", true).select2();
			$("#mateContDistinct").prop("disabled", true).select2();
			$("#mateContDetails").attr("readOnly", true);
			validateMateMsg('mateContProvince');
			validateMateMsg('mateContCity');
			validateMateMsg('mateContDistinct');
			document.getElementById("mateContDetails").style.backgroundColor = "";
		} else if (0 == $addrResi) {
			if ('${readOnly}' == "false") {
				$("#mateContProvince").prop("disabled", false).select2();
				$("#mateContCity").prop("disabled", false).select2();
				$("#mateContDistinct").prop("disabled", false).select2();
				$("#mateContDetails").removeAttr("readOnly");

				var $mateContProvince = $("#s2id_mateContProvince>.select2-choice>.select2-chosen");
				$mateContProvince.html("请选择");
				$("#mateContProvince").val("");
				$("#mateContCity").empty();
				$("#mateContCity").append("<option value=''>请选择</option>");
				var $mateContCity = $("#s2id_mateContCity>.select2-choice>.select2-chosen");
				$mateContCity.html("请选择");

				$("#mateContDistinct").empty();
				$("#mateContDistinct").append("<option value=''>请选择</option>");
				var $mateContDistinct = $("#s2id_mateContDistinct>.select2-choice>.select2-chosen");
				$mateContDistinct.html("请选择")

				$("#mateContDetails").val(null);
			}
		}
	}

	function cancalMateDisabled() {
		$("#mateContProvince").removeAttr("disabled");
		$("#mateContCity").removeAttr("disabled");
		$("#mateContDistinct").removeAttr("disabled");
	}

	//修改 下拉框的“必填信息”提示，在选中后仍无法消失的问题
	function validateMateMsg(elementId) {
		if ("" != $("#" + elementId).find("option:checked").val()) {
			$("label[for='" + elementId + "']").hide();
		}
	}

	function ensureAgeMate(card) {
		if (card.length < 15)
			return;
		$.ajax({
		type : "post",
		data : {
			idNum : card
		},
		url : "${ctx}/custinfo/custInfo/attachInfo",
		dataType : "json",
		success : function(msg) {
			if (msg.custInfo.categoryLarge) {
				setIndustry(msg.categoryLargeList, msg.categoryMediumList, msg.categorySmallList);
			}
			JSONToform(document.getElementById("custMateInfoForm"), msg.custInfo);
			if (msg.custInfo.custName) {
				changeMateValue();
				$("#mateContDetails").val(msg.custInfo.contDetails);
				setName(data, "mateReg", msg.custInfo.regProvince, msg.custInfo.regCity, msg.custInfo.regDistinct);
				setName(data, "mateCont", msg.custInfo.contProvince, msg.custInfo.contCity, msg.custInfo.contDistinct);
			}
			$("select").select2();
		},
		error : function(msg) {
			alertx("未能保存，请查看后台信息");
		}
		});
	}

	function setIndustry(largeList, mediumList, smallList) {
		$("#categoryLarge2").html("");
		$("#categoryMedium2").html("");
		$("#categorySmall2").html("");
		if (largeList != null) {
			for (var m = 0; m < largeList.length; m++) {
				$("#categoryLarge2").append("<option value='" + largeList[m].induCode + "' data-name='" + largeList[m].induName + "'>" + largeList[m].induName + "</option>");
			}
		}

		if (mediumList != null) {
			for (var m = 0; m < mediumList.length; m++) {
				$("#categoryMedium2").append("<option value='" + mediumList[m].induCode + "' data-name='" + mediumList[m].induName + "'>" + mediumList[m].induName + "</option>");
			}
		}
		if (smallList != null) {
			for (var m = 0; m < smallList.length; m++) {
				$("#categorySmall2").append("<option value='" + smallList[m].induCode + "' data-name='" + smallList[m].induName + "'>" + smallList[m].induName + "</option>");
			}
		}
	}

	function valueIsRquiredMate(value) {
		if (value == 1) {
			$("#mateHouseInformation").show();
			$("#mateEstateValuation").addClass("required");
			$("#mateHouseAddress").addClass("required");
		} else {
			$("#mateHouseInformation").hide();
			$("#mateEstateValuation").removeClass("required");
			$("#mateHouseAddress").removeClass("required");
			$("#mateEstateValuation").val('');
			$("#mateHouseAddress").val('');
		}
	}
</script>
<div class="searchInfo">
	<h3 onclick="$('#mateFormId').toggle(600);" class="searchTitle">借款人配偶信息</h3>
	<div id="mateFormId" class="searchCon">
		<form:form id="custMateInfoForm" modelAttribute="custMateInfo" action="${ctx}/custinfo/custInfo/saveAll" method="post" class="form-horizontal">
			<form:hidden path="id" id="mateId" />
			<form:hidden path="currApplyNo" />
			<input type="hidden" name="custIdForCustMate" id="custIdForCustMate" value="${custInfo.id }" />
			<sys:message content="${message}" />
			<table class="fromTable filter">
				<tr>
					<td class="ft_label">姓名：</td>
					<td class="ft_content">
						<input type="text" id="mateCustName" onblur="this.value=reSpaces(this.value);" name="custName" class="input-medium required" value="${custMateInfo.custName}" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">证件类型：</td>
					<td class="ft_content">
						<form:select id="idType" path="idType" class="input-medium required" cssStyle="width:177px;" onchange="validateMateMsg('idType')">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('CUSTOMER_P_ID_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
					<td class="ft_label">证件号：</td>
					<td class="ft_content">
						<input type="text" id="idNumMate" name="idNum" onblur="ensureAgeMate(this.value)" class="input-medium card required" value="${custMateInfo.idNum}" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">身份证起始日期：</td>
					<td class="ft_content">
						<input id="personIdStartDateMate" name="personIdStartDate" type="text" maxlength="20" class="input-medium Wdate required" value="${custMateInfo.personIdStartDate}" onclick="WdatePicker({onpicked:dateWhite(this),maxDate:new Date()})" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">身份证是否长期：</td>
					<td class="ft_label" style="text-align: left;">
						<input type="radio" name="cardFlagMate" id="yesMate" value="yes" onchange="changeShowOrHide2()" />
						是
						<input type="radio" id="noMate" name="cardFlagMate" value="no" onchange="changeShowOrHide2()" />
						否
					</td>
					<td class="ft_label">
						<span id="cardEndTextMate">身份证终止日期：</span>
					</td>
					<td class="ft_content">
						<input id="personIdEndDateMate" name="personIdEndDate" type="text" maxlength="20" class="input-medium Wdate required" value="${custMateInfo.personIdEndDate}" onclick="WdatePicker({onpicked:dateWhite(this),minDate:new Date()})" />
						<font id="personIdEndDateMateRed" color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">手机号：</td>
					<td class="ft_content">
						<input type="text" id="mobileNumMate" name="mobileNum" class="input-medium mobile required" value="${custMateInfo.mobileNum}" maxlength="11" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">民族：</td>
					<td class="ft_content">
						<form:select id="nationNo" path="nationNo" class="input-medium required" cssStyle="width:177px;" onchange="validateMateMsg('nationNo')">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('NATION_NO')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">出生日期：</td>
					<td class="ft_content">
						<input id="birthDayMate" name="birthDay" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" value="${custMateInfo.birthDay}" />
					</td>
					<td class="ft_label">年龄：</td>
					<td class="ft_content">
						<input type="text" id="ageNoMate" name="ageNo" readonly="true" maxlength="2" class="input-medium" value="${custMateInfo.ageNo}" />
					</td>
					<td class="ft_label">性别：</td>
					<td class="ft_content">
						<form:select id="sexNo" path="sexNo" class="input-medium required" cssStyle="width:177px;" onchange="validateMateMsg('sexNo')">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">最高学历：</td>
					<td class="ft_content">
						<form:select id="topEducationNo" path="topEducationNo" class="input-medium required" cssStyle="width:177px;" onchange="validateMateMsg('topEducationNo')">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('EDUCATION')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
					<td class="ft_label">来本市年限(年)：</td>
					<td class="ft_content">
						<input id="cityInDate" name="cityInDate" type="text" maxlength="3" class="input-medium number required" value="${custMateInfo.cityInDate}" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">户口所在地址：</td>
					<td class="ft_content" colspan="5">
						<form:select path="regProvince" id="mateRegProvince" class="input-small nuNullCheck" data-code="-1" onchange="loadDataMate()"></form:select>
						&nbsp;省
						<form:select path="regCity" id="mateRegCity" class="input-small nuNullCheck" data-code="-1" onchange="loadDataMate()"></form:select>
						&nbsp;市
						<form:select path="regDistinct" id="mateRegDistinct" class="input-small nuNullCheck" data-code="-1" onchange="loadDataMate()"></form:select>
						&nbsp;区&nbsp;
						<font color="red">*</font>
						<span style="width: 15px; display: inline-block;"></span>
						地址明细：
						<input type="text" name="regDetails" id="mateRegDetails" class="input-medium required" maxlength="200" value="${custMateInfo.regDetails}" style="margin: 0px; position: relative; display: inline-block; vertical-align: middle;width:270px" onblur="changeMateContDetails();" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">是否本地户籍：</td>
					<td class="ft_content">
						<form:select id="isLocal" path="isLocal" class="input-mini required" cssStyle="width:177px;" onchange="validateMateMsg('isLocal')">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
					<td class="ft_label" colspan="2" style="text-align: right;">现居地与户籍地址是否一致：</td>
					<td class="ft_label" colspan="2" style="text-align: left;">
						<form:radiobuttons path="addrResi" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="input-small" onchange="changeMateValue();" />
					</td>
				</tr>
				<tr>
					<td class="ft_label">现居地址：</td>
					<td class="ft_content" colspan="5">
						<form:select class="input-small nuNullCheck" id="mateContProvince" path="contProvince" data-code="-1"></form:select>
						&nbsp;省
						<form:select class="input-small nuNullCheck" id="mateContCity" path="contCity" data-code="-1"></form:select>
						&nbsp;市
						<form:select class="input-small nuNullCheck" id="mateContDistinct" path="contDistinct" data-code="-1"></form:select>
						&nbsp;区&nbsp;
						<font color="red">*</font>
						<span style="width: 15px; display: inline-block;"></span>
						地址明细：
						<input type="text" name="contDetails" id="mateContDetails" onblur="this.value=reSpaces(this.value);" value="${custMateInfo.contDetails}" class="input-medium required" maxlength="200" style="margin: 0px; position: relative; vertical-align: middle;width:270px" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">居住状况：</td>
					<td class="ft_content">
						<form:select path="livingStatus" id="livingStatus" class="input-small required" onchange="validateMateMsg('livingStatus')">
							<option value="">请选择</option>
							<form:options items="${fns:getDictList('LIVING_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
					<td class="ft_label">名下是否有房产：</td>
					<td class="ft_content">
						<form:select path="isFixedHouse" id="isFixedHouse" class="input-small required" onchange="validateMateMsg('isFixedHouse');valueIsRquiredMate(this.value)">
							<option value="">请选择</option>
							<form:options items="${fns:getDictList('IS_HAVE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
					<td class="ft_label">是否有企业：</td>
					<td class="ft_content">
						<form:select path="isHaveCompany" id="isHaveCompanyMate" class="input-medium required" onchange="validateMateMsg('isHaveCompanyMate');">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
				</tr>
				<tr id="mateHouseInformation">
					<td class="ft_label" id="addressLabel">房产地址：</td>
					<td class="ft_content" id="addressContent">
						<form:input path="houseAddress" id="mateHouseAddress" onblur="this.value=reSpaces(this.value);" class="input-xlarge" />
					</td>
					<td class="ft_label" id="estateLabel">房产估值：</td>
					<td class="ft_content" id="estateContent">
						<form:input path="estateValuation" id="mateEstateValuation" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" class="input-medium number" />
					</td>
				</tr>
				<tr>
					<td class="ft_label">居住状况说明：</td>
					<td class="ft_content" colspan="5">
						<pre class="textarea-width pre-style required" id="preMate"></pre>
						<form:textarea path="livingStatusDesc" id="livingStatusDescMate" maxlength="300" rows="5" class="textarea-width textarea-style required" onkeyup="adjustTextareaLength('livingStatusDescMate','preMate')" htmlEscape="false" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">个人年收入(元)：</td>
					<td class="ft_content">
						<form:input id="matePerAnnualIncome" path="perAnnualIncome" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" class="input-medium required" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">收入来源：</td>
					<td class="ft_content" colspan="3">
						<form:input path="sourceOfIncome" maxlength="100" class="input-large required" style="width:80%;" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">电子邮箱：</td>
					<td class="ft_content">
						<input type="text" id="emailNo" name="emailNo" class="input-medium email" value="${custMateInfo.emailNo}" />
					</td>
					<td class="ft_label">微信号码：</td>
					<td class="ft_content">
						<input type="text" id="wechatNo" name="wechatNo" class="input-medium" value="${custMateInfo.wechatNo}" />
					</td>
				</tr>
				<tr>
					<td class="ft_label">QQ号码：</td>
					<td class="ft_content">
						<input type="text" id="qqNo" name="qqNo" class="input-medium qq" value="${custMateInfo.qqNo}" maxlength="20" />
					</td>
					<td class="ft_label">微博号码：</td>
					<td class="ft_content">
						<input type="text" id="microNo" name="microNo" class="input-medium" value="${custMateInfo.microNo}" maxlength="20" />
					</td>
					<td class="ft_label">其他联系方式：</td>
					<td class="ft_content">
						<input type="text" id="otherContactType" name="otherContactType" class="input-medium" value="${custMateInfo.otherContactType}" maxlength="100" />
					</td>
				</tr>
				<tr>
					<td class="ft_label">所属行业：</td>
					<td class="ft_content" colspan="5">
						<form:select id="categoryMain2" path="categoryMain" class="input-medium " onchange="mateLoadCategory('categoryMain2','categoryLarge2');validateMateMsg('categoryMain2')">
							<form:option value="" label="" />
							<form:options items="${categoryMainList2}" itemLabel="induName" itemValue="induCode" htmlEscape="false" />
						</form:select>
						门类
						<span class="help-inline"> </span>
						<form:select id="categoryLarge2" path="categoryLarge" class="input-medium " onchange="mateLoadCategory('categoryLarge2','categoryMedium2');validateMateMsg('categoryLarge2')">
							<form:option value="" label="" />
							<form:options items="${categoryLargeList2}" itemLabel="induName" itemValue="induCode" htmlEscape="false" />
						</form:select>
						大类
						<span class="help-inline"> </span>
						<form:select id="categoryMedium2" path="categoryMedium" class="input-medium " onchange="mateLoadCategory('categoryMedium2','categorySmall2');validateMateMsg('categoryMedium2')">
							<form:option value="" label="" />
							<form:options items="${categoryMediumList2}" itemLabel="induName" itemValue="induCode" htmlEscape="false" />
						</form:select>
						中类
						<span class="help-inline"> </span>
						<form:select id="categorySmall2" path="categorySmall" class="input-medium " onchange="validateMateMsg('categorySmall2')">
							<form:option value="" label="" />
							<form:options items="${categorySmallList2}" itemLabel="induName" itemValue="induCode" htmlEscape="false" />
						</form:select>
						小类
						<span class="help-inline"> </span>
					</td>
				</tr>
				<tr id="mateToGuarantorLabelId">
					<td class="ft_label" style="text-align: right;">是否为担保人：</td>
					<td class="ft_label" colspan="2" style="text-align: left;">
						<form:radiobuttons path="mateToGuarantor" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="input-small required" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">是否推送外访系统：</td>
						<td class="ft_content">
							<form:select path="svFlag" id="svFlag" class="input-small required" >
								<option value="">请选择</option>
								<form:options items="${fns:getDictList('SV_FLAG')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="searchButton" colspan="6" style="text-align: right;">
						<input class="btn btn-primary" type="submit" onclick="return cancalMateDisabled();" value="保 存" />
						&nbsp;
					</td>
				</tr>
			</table>
		</form:form>
	</div>
</div>