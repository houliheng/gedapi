<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">
	$(document).ready(function() {

		new CitySelect({
		data : data,
		provId : '#regProvince',
		cityId : '#regCity',
		areaId : '#regDistinct',
		isSelect : false
		});
		new CitySelect({
		data : data,
		provId : '#contProvince',
		cityId : '#contCity',
		areaId : '#contDistinct',
		isSelect : false
		});

		if (!checkIsNull('${custInfo.regDistinct}')) {
			setName(data, "reg", '${custInfo.regProvince}', '${custInfo.regCity}', '${custInfo.regDistinct}');
			if (!checkIsNull('${custInfo.contDistinct}')) {
				setName(data, "cont", '${custInfo.contProvince}', '${custInfo.contCity}', '${custInfo.contDistinct}');
			}
		}

		//根据身份证号生成出生日期和年龄
		var idNum = $("#idNum").val();
		ensureAgeMain(idNum);
		adjustTextareaLength("livingStatusDesc", "pre");
		document.getElementById("perAnnualIncome").value = outputmoney("${custInfo.perAnnualIncome}");
		document.getElementById("householdSpendingMonth").value = outputmoney("${custInfo.householdSpendingMonth}");
		//借款人不是已婚，不加载配偶信息
		isLoadCustMateInfo();
		//不存在企业信息，不加载企业信息
		isLoadCompanyInfo();
		$("#custInfoForm").validate({
		submitHandler : saveCustInfo,
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});
		if ('${custInfo.addrResi}' == null || '${custInfo.addrResi}' == '') {
			//现居地与户籍地址是否一致，默认为否
			$("input[id='addrResi2']").attr("checked", "true");
		}

		if ("${custInfo.addrResi}" == "1") {
			$("#contProvince").prop("disabled", true).select2();
			$("#contCity").prop("disabled", true).select2();
			$("#contDistinct").prop("disabled", true).select2();
			$("#contDetails").attr("readOnly", true);
		}
		//初始化身份证终止日期
		if ('${custInfo.personIdEndDate}' == null || '${custInfo.personIdEndDate}' == '') {
			$("input[id='yes']").attr("checked", "true");
			$("#personIdEndDate").hide();
			$("#personIdEndDate").val('');
			$("#cardEndText").hide();
			$("#personIdEndDateRed").hide();
		} else {
			$("input[id='no']").attr("checked", "true");
			$("#personIdEndDate").show();
			$("#cardEndText").show();
			$("#personIdEndDateRed").show();
		}
		
		valueIsRquiredMain(${custInfo.isFixedHouse});
		if('${custInfo.isFixedHouse}' == "1"){
			document.getElementById("mainEstateValuation").value = outputmoney("${custInfo.estateValuation }");
		}
	});

	function loadDataMain() {
		var $addrResi = $("input[name='addrResi']:checked").val();
		if (1 == $addrResi) {
			$("#contProvince").val($("#regProvince").val());
			$("#s2id_contProvince>.select2-choice>.select2-chosen").html($("#s2id_regProvince>.select2-choice>.select2-chosen").html());

			$("#contCity").append($("#regCity").html());
			$("#contCity").val($("#regCity").val());
			$("#s2id_contCity>.select2-choice>.select2-chosen").html($("#s2id_regCity>.select2-choice>.select2-chosen").html());

			$("#contDistinct").append($("#regDistinct").html());
			$("#contDistinct").val($("#regDistinct").val());
			$("#s2id_contDistinct>.select2-choice>.select2-chosen").html($("#s2id_regDistinct>.select2-choice>.select2-chosen").html());

			$("#contDetails").val($("#regDetails").val());
		}
	}

	var treeCategory1 = [ "categoryMain1", "categoryLarge1", "categoryMedium1", "categorySmall1" ];
	var treeCategory2 = [ "categoryMain2", "categoryLarge2", "categoryMedium2", "categorySmall2" ];
	function mainLoadCategory(root, curr) {
		var parentInduCode = $("#" + root).select2("val");
		var emptyFlag = false;
		for (var i = 0; i < treeCategory1.length; i++) {
			if (curr == treeCategory1[i]) {
				emptyFlag = true;
			}
			if (emptyFlag) {
				$("#" + treeCategory1[i]).select2("val", "");
				$("#" + treeCategory1[i]).empty();
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
	function errorHideHelper(id) {
		var $val = $("#" + id).find("option:checked").val()
		if (null != $val && "" != $val) {
			$("label[for='" + id + "']").hide();
		}
	}
	//客户信息select错误信息隐藏
	function hideCustInfoSelectError() {
		errorHideHelper("sex");
		errorHideHelper("webStatus");
		errorHideHelper("education");
	}
	//省市级联错误信息隐藏-这里把所有的省市级联错误都统计进行处理
	function hideCityJiLianError() {
		errorHideHelper("regProvince");
		errorHideHelper("regCity");
		errorHideHelper("regDistinct");

		errorHideHelper("contProvince");
		errorHideHelper("contCity");
		errorHideHelper("contDistinct");
	}

	function finishDistinct() {
		//现居地等于户籍地
		var $addrResi = $("input[name='addrResi']:checked").val();
		if (1 == $addrResi) {//是
			$("#contDistinct").val($("#regDistinct").val());
			$("#s2id_contDistinct>.select2-choice>.select2-chosen").html($("#s2id_regDistinct>.select2-choice>.select2-chosen").html());
			//房屋所在地等于现居地
			var $addrSync = $("input[name='addrSync']:checked").val();
			if (1 == $addrSync) {//是
				$("#hsDistinct").val($("#contDistinct").val());
				$("#s2id_hsDistinct>.select2-choice>.select2-chosen").html($("#s2id_contDistinct>.select2-choice>.select2-chosen").html());
			}
		}
		hideCityJiLianError();
	}

	//不是已婚时，不加载配偶信息
	function isLoadCustMateInfo() {
		if ($("#wedStatus").val() != '${wedStatusYh}') {
			$("#custMateInfo").hide();
		} else {
			$("#custMateInfo").show();
		}
	}

	//不存在企业时，不加载借款人企业
	function isLoadCompanyInfo() {
		if ($("#isHaveCompany").val() == '1') {//存在
			$("#companyInfo").show();
			$("#companyManagerInfoList").show();
			$("#companyRelatedListDiv").show();
		} else {//存在
			$("#companyInfo").hide();
			$("#companyManagerInfoList").hide();
			$("#companyRelatedListDiv").hide();
		}
	}

	//修改 下拉框的“必填信息”提示，在选中后仍无法消失的问题
	function validateMsg(elementId) {
		if ("" != $("#" + elementId).find("option:checked").val()) {
			$("label[for='" + elementId + "']").hide();
		}
		if (elementId == 'isHaveCompany') {
			isLoadCompanyInfo();
		}
	}

	function changeValue() {
		//现居地等于户籍地
		var $addrResi = $("input[name='addrResi']:checked").val();
		if (1 == $addrResi) {
			$("#contProvince").val($("#regProvince").val());
			$("#s2id_contProvince>.select2-choice>.select2-chosen").html($("#s2id_regProvince>.select2-choice>.select2-chosen").html());
			$("#contCity").append($("#regCity").html());
			$("#contCity").val($("#regCity").val());
			$("#s2id_contCity>.select2-choice>.select2-chosen").html($("#s2id_regCity>.select2-choice>.select2-chosen").html());
			$("#contDistinct").append($("#regDistinct").html());
			$("#contDistinct").val($("#regDistinct").val());
			$("#s2id_contDistinct>.select2-choice>.select2-chosen").html($("#s2id_regDistinct>.select2-choice>.select2-chosen").html());
			$("#contDetails").val($("#regDetails").val());
			$("#contProvince").prop("disabled", true).select2();
			$("#contCity").prop("disabled", true).select2();
			$("#contDistinct").prop("disabled", true).select2();
			$("#contDetails").attr("readOnly", true);
			validateMsg('contProvince');
			validateMsg('contCity');
			validateMsg('contDistinct');
			document.getElementById("contDetails").style.backgroundColor = "";
		} else if (0 == $addrResi) {
			if ('${readOnly}' == "false") {
				$("#contProvince").prop("disabled", false).select2();
				$("#contCity").prop("disabled", false).select2();
				$("#contDistinct").prop("disabled", false).select2();
				$("#contDetails").removeAttr("readOnly");
				var $contProvince = $("#s2id_contProvince>.select2-choice>.select2-chosen");
				$contProvince.html("请选择");
				$("#contProvince").val("请选择");
				$("#contCity").empty();
				$("#contCity").append("<option value=''>请选择</option>");
				var $contCity = $("#s2id_contCity>.select2-choice>.select2-chosen");
				$contCity.html("请选择");

				$("#contDistinct").empty();
				$("#contDistinct").append("<option value=''>请选择</option>");
				var $contDistinct = $("#s2id_contDistinct>.select2-choice>.select2-chosen");
				$contDistinct.html("请选择")

				$("#contDetails").val(null);
			}
		}
	}

	function changeContDetails() {
		$("#regDetails").val($("#regDetails").val().replace(/\s/g,""));
		var $addrResi = $("input[name='addrResi']:checked").val();
		if (1 == $addrResi) {
			$("#contDetails").val($("#regDetails").val());
			validateMsg('contDetails');
		}
	}
	//身份证终止日期显示与否
	function changeShowOrHide1() {
		var $cardFlag = $("input[name='cardFlag']:checked").val();
		if ("yes" == $cardFlag) {
			$("input[id='yes']").attr("checked", "true");
			$("#personIdEndDate").hide();
			$("#personIdEndDate").val('');
			$("#cardEndText").hide();
			$("#personIdEndDateRed").hide();
		} else {
			$("input[id='no']").attr("checked", "true");
			$("#personIdEndDate").show();
			$("#cardEndText").show();
			$("#personIdEndDateRed").show();
		}
	}

	function cancalDisabled() {
		$("#contProvince").removeAttr("disabled");
		$("#contCity").removeAttr("disabled");
		$("#contDistinct").removeAttr("disabled");
	}
	function mainBorrow() {
		$("#mainBorrowId").toggle(600);
	}

	function ensureAgeMain(card) {
		$.ajax({
		type : "post",
		data : {
			card : card
		},
		url : "${ctx}/custinfo/custInfo/ensureAge",
		dataType : "json",
		success : function(data) {
			$("#ageNo").val(data.age);
			$("#birthDay").val(data.birth);
		},
		error : function(msg) {
			alertx("未能保存，请查看后台信息");
		}
		});
	}

	function ensureAge() {
		var aDate = new Date();
		var thisYear = aDate.getFullYear();
		var brith = $("#birthDay").val();
		brith = brith.substr(0, 4);
		age = (thisYear - brith);
		$("#ageNo").val(age);
	}
	
	function valueIsRquiredMain(value) {
		if (value == 1){
			$("#mainHouseInformation").show();
			$("#mainEstateValuation").addClass("required");
			$("#mainHouseAddress").addClass("required");
		}else{
			$("#mainHouseInformation").hide();
			$("#mainEstateValuation").removeClass("required");
			$("#mainHouseAddress").removeClass("required");
			$("#mainEstateValuation").val(null);
			$("#mainHouseAddress").val(null);
		}
	}
</script>
<div class="searchInfo">
	<h3 onclick="mainBorrow()" class="searchTitle">借款人信息</h3>
	<div id="mainBorrowId" class="searchCon">
		<form:form id="custInfoForm" modelAttribute="custInfo" action="${ctx}/custinfo/custInfo/save" method="post" class="form-horizontal">
			<form:hidden path="id" />
			<input type="hidden" name="applyNo" value="${actTaskParam.applyNo }" />
			<input type="hidden" name="currApplyNo" value="${actTaskParam.applyNo }" />
			<sys:message content="${message}" />
			<table class="fromTable filter">
				<tr>
					<td class="ft_label">客户名称：</td>
					<td class="ft_content">
						<form:input type="text" path="custName" htmlEscape="false" readonly="true" class="input-medium" />
					</td>
					<td class="ft_label">证件类型：</td>
					<td class="ft_content">
						<form:select path="idType" id="idTypeMain" onchange="validateMsg('idTypeMain');" disabled="true" class="input-medium" cssStyle="width:164px;">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('CUSTOMER_P_ID_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
					</td>
					<td class="ft_label">证件号：</td>
					<td class="ft_content">
						<input type="text" id="idNum" name="idNum" readonly="true" class="input-medium card" value="${custInfo.idNum}" />
					</td>
				</tr>
				<tr>
					<td class="ft_label">身份证起始日期：</td>
					<td class="ft_content">
						<input id="personIdStartDate" name="personIdStartDate" type="text" maxlength="20" class="input-medium Wdate required" value="${custInfo.personIdStartDate}" onclick="WdatePicker({onpicked:dateWhite(this),maxDate:new Date()})" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">身份证是否长期：</td>
					<td class="ft_label" style="text-align: left;">
						<input type="radio" name="cardFlag" id="yes" value="yes" onchange="changeShowOrHide1()" />
						是
						<input type="radio" id="no" name="cardFlag" value="no" onchange="changeShowOrHide1()" />
						否
					</td>
					<td class="ft_label">
						<span id="cardEndText">身份证终止日期：</span>
					</td>
					<td class="ft_content">
						<input id="personIdEndDate" name="personIdEndDate" type="text" maxlength="20" class="input-medium Wdate required" value="${custInfo.personIdEndDate}" onclick="WdatePicker({onpicked:dateWhite(this),minDate:new Date()})" />
						<font id="personIdEndDateRed" color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">手机号：</td>
					<td class="ft_content">
						<input type="text" id="mobileNum" readonly="true" name="mobileNum" class="input-medium required mobile" value="${custInfo.mobileNum}" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">民族：</td>
					<td class="ft_content">
						<form:select path="nationNo" id="nationNoMain" class="input-medium required" cssStyle="width:177px;">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('NATION_NO')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">出生日期：</td>
					<td class="ft_content">
						<input id="birthDay" name="birthDay" type="text" maxlength="20" readonly="readonly" class="input-medium Wdate" value="${custInfo.birthDay}" />
					</td>
					<td class="ft_label">年龄：</td>
					<td class="ft_content">
						<input type="text" id="ageNo" maxlength="2" name="ageNo" readonly="true" class="input-medium" value="${custInfo.ageNo}" />
					</td>
					<td class="ft_label">性别：</td>
					<td class="ft_content">
						<form:select id="sexNoMain" onchange="validateMsg('sexNo');" path="sexNo" class="input-medium required" cssStyle="width:177px;">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">婚姻状况：</td>
					<td class="ft_content">
						<form:select path="wedStatus" onChange="isLoadCustMateInfo();validateMsg('wedStatus');" class="input-medium required" cssStyle="width:177px;">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('WED_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
					<td class="ft_label">子女数量：</td>
					<nobr>
						<td class="ft_content">
							<form:select onchange="validateMsg('childrenSon');" path="childrenSon" class="input-medium required" cssStyle="width:33%">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('CHILDREN_NUM')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							男
							<font color="red">*</font>
							<form:select onchange="validateMsg('childrenGirl');" path="childrenGirl" class="input-medium required" cssStyle="width:33%">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('CHILDREN_NUM')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							女
							<font color="red">*</font>
						</td>
					</nobr>
					<td class="ft_label">供养人数：</td>
					<td class="ft_content">
						<input type="text" id="providesNum" name="providesNum" class="input-medium number" value="${custInfo.providesNum}" />
					</td>
				</tr>
				<tr>
					<td class="ft_label">每月家庭支出(元)：</td>
					<td class="ft_content">
						<input type="text" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" id="householdSpendingMonth" name="householdSpendingMonth" class="input-mini required" value="${custInfo.householdSpendingMonth}" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">最高学历：</td>
					<td class="ft_content">
						<form:select path="topEducationNo" id="topEducationNoMain" class="input-medium required" cssStyle="width:177px;">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('EDUCATION')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
					<td class="ft_label">家庭电话(区号+号码)：</td>
					<td class="ft_content">
						<input type="text" id="homePhoneNo" name="homePhoneNo" class="input-medium phone" value="${custInfo.homePhoneNo}" />
					</td>
				</tr>
				<tr>
					<td class="ft_label">现住宅地居住时间(年)：</td>
					<td class="ft_content">
						<input id="regInDate" name="regInDate" type="text" maxlength="3" class="input-medium number required" value="${custInfo.regInDate}" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">来本市年限(年)：</td>
					<td class="ft_content">
						<input id="cityInDate" name="cityInDate" type="text" maxlength="3" class="input-medium number required" value="${custInfo.cityInDate}" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">住宅电话(区号+号码)：</td>
					<td class="ft_content">
						<input type="text" id="hosePhoneNo" name="hosePhoneNo" class="input-medium phone" value="${custInfo.hosePhoneNo}" />
					</td>
				</tr>
				<tr>
					<td class="ft_label">户口所在地址：</td>
					<td class="ft_content" colspan="5">
						<form:select path="regProvince" class="input-small nuNullCheck" data-code="-1" onchange="loadDataMain()"></form:select>
						&nbsp;省
						<form:select path="regCity" class="input-small nuNullCheck" data-code="-1" onchange="loadDataMain()"></form:select>
						&nbsp;市
						<form:select path="regDistinct" class="input-small nuNullCheck" data-code="-1" onchange="loadDataMain()"></form:select>
						&nbsp;区&nbsp;
						<font color="red">*</font>
						<span style="width: 15px; display: inline-block;"></span>
						地址明细：
						<input type="text" name="regDetails" id="regDetails"  class="input-medium required" maxlength="200" value="${custInfo.regDetails}" style="margin: 0px; position: relative; display: inline-block; vertical-align: middle; width: 285px" onblur="changeContDetails();" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">是否本地户籍：</td>
					<td class="ft_content">
						<form:select path="isLocal" id="isLocalMain" class="input-mini required" cssStyle="width:177px;" onchange="validateMsg('isLocal');">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
					<td class="ft_label" colspan="2" style="text-align: right;">现居地与户籍地址是否一致：</td>
					<td class="ft_label" colspan="2" style="text-align: left;">
						<form:radiobuttons path="addrResi" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="input-small" onchange="changeValue();" />
					</td>
				</tr>
				<tr>
					<td class="ft_label">现居地址：</td>
					<td class="ft_content" colspan="5">
						<form:select class="input-small nuNullCheck" path="contProvince" data-code="-1"></form:select>
						&nbsp;省
						<form:select class="input-small nuNullCheck" path="contCity" data-code="-1"></form:select>
						&nbsp;市
						<form:select class="input-small nuNullCheck" path="contDistinct" data-code="-1"></form:select>
						&nbsp;区&nbsp;
						<font color="red">*</font>
						<span style="width: 15px; display: inline-block;"></span>
						地址明细：
						<input type="text" name="contDetails" id="contDetails" onblur="this.value=reSpaces(this.value);" value="${custInfo.contDetails}" class="input-medium required" maxlength="200" style="margin: 0px; position: relative; vertical-align: middle; width: 285px" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">居住状况：</td>
					<td class="ft_content">
						<form:select path="livingStatus" id="livingStatusMain" class="input-small required" onchange="validateMsg('livingStatusMain');">
							<option value="">请选择</option>
							<form:options items="${fns:getDictList('LIVING_STATUS')}" htmlEscape="false" itemLabel="label" itemValue="value" onchange="validateMsg();" />
						</form:select>
						<font color="red">*</font>
					</td>
					<td class="ft_label">名下是否有房产：</td>
					<td class="ft_content" colspan="3">
						<form:select path="isFixedHouse" id="isFixedHouseMain" class="input-small" onchange="validateMsg('isFixedHouseMain');valueIsRquiredMain(this.value);">
							<option value="">请选择</option>
							<form:options items="${fns:getDictList('IS_HAVE')}" htmlEscape="false" itemLabel="label" itemValue="value" />
						</form:select>
					</td>
				</tr>
				<tr id="mainHouseInformation">
					<td class="ft_label" id="addressLabel">房产地址：</td>
					<td class="ft_content" id="addressContent">
						<form:input path="houseAddress" id="mainHouseAddress"  class="input-xlarge" />
					</td>
					<td class="ft_label" id="estateLabel">房产估值：</td>
					<td class="ft_content" id="estateContent">
						<form:input path="estateValuation" id="mainEstateValuation" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" class="input-medium number" />
					</td>
				</tr>
				<tr>
					<td class="ft_label">居住状况说明：</td>
					<td class="ft_content" colspan="5">
						<pre class="textarea-width pre-style required" id="pre"></pre>
						<form:textarea path="livingStatusDesc" rows="5" class="textarea-width textarea-style required" onkeyup="adjustTextareaLength('livingStatusDesc','pre')" maxlength="300" htmlEscape="false" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">个人年收入(元)：</td>
					<td class="ft_content">
						<form:input path="perAnnualIncome" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" class="input-medium required" />
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
						<input type="text" id="emailNo" name="emailNo" class="input-medium email" value="${custInfo.emailNo}" />
					</td>
					<td class="ft_label">微信号码：</td>
					<td class="ft_content">
						<input type="text" id="wechatNo" maxlength="20" name="wechatNo" class="input-medium" value="${custInfo.wechatNo}" />
					</td>
				</tr>
				<tr>
					<td class="ft_label">QQ号码：</td>
					<td class="ft_content">
						<input type="text" id="qqNo" name="qqNo" class="input-medium qq" value="${custInfo.qqNo}" maxlength="20" />
					</td>
					<td class="ft_label">微博号码：</td>
					<td class="ft_content">
						<input type="text" id="microNo" name="microNo" maxlength="20" class="input-medium" value="${custInfo.microNo}" />
					</td>
					<td class="ft_label">其他联系方式：</td>
					<td class="ft_content">
						<input type="text" id="otherContactType" maxlength="20" name="otherContactType" class="input-medium" value="${custInfo.otherContactType}" />
					</td>
				</tr>
				<tr>
					<td class="ft_label">所属行业：</td>
					<td class="ft_content" colspan="5">
						<form:select id="categoryMain1" path="categoryMain" class="input-medium required" onchange="mainLoadCategory('categoryMain1','categoryLarge1','');validateMsg('categoryMain1');">
							<form:option value="" label="" />
							<form:options items="${categoryMainList}" itemLabel="induName" itemValue="induCode" htmlEscape="false" />
						</form:select>
						门类
						<span class="help-inline"> </span>
						<form:select id="categoryLarge1" path="categoryLarge" class="input-medium required" onchange="mainLoadCategory('categoryLarge1','categoryMedium1','');validateMsg('categoryLarge1');">
							<form:option value="" label="" />
							<form:options items="${categoryLargeList}" itemLabel="induName" itemValue="induCode" htmlEscape="false" />
						</form:select>
						大类
						<span class="help-inline"> </span>
						<form:select id="categoryMedium1" path="categoryMedium" class="input-medium required" onchange="mainLoadCategory('categoryMedium1','categorySmall1','');validateMsg('categoryMedium1');">
							<form:option value="" label="" />
							<form:options items="${categoryMediumList}" itemLabel="induName" itemValue="induCode" htmlEscape="false" />
						</form:select>
						中类
						<span class="help-inline"> </span>
						<form:select id="categorySmall1" path="categorySmall" class="input-medium required" onchange="validateMsg('categorySmall1');">
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
					<td class="ft_label">是否有企业：</td>
					<td class="ft_content"><!-- 默认有企业 在这写死-->
						<input type="hidden" name="isHaveCompany" id="isHaveCompany" value="1">
						<input type="text" value="是" readonly="readonly" class="input-medium" >
					</td>
					<%-- <td class="ft_content">
						<form:select path="isHaveCompany" class="input-medium required" onchange="validateMsg('isHaveCompany');">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td> --%>
				</tr>
				<tr>
					<td class="searchButton" colspan="6" style="text-align: right;">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="return cancalDisabled();" />
						&nbsp;
					</td>
				</tr>
			</table>
		</form:form>
	</div>
</div>