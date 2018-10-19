<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<!-- 
  @reqno:H1601220021
  @date-designer:2016年1月28日-gaofeng
  @date-author:2016年1月28日-gaofeng:客户基本信息详细信息
 -->
<head>
<title>客户基本信息管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		//$("#name").focus();
		$("#inputForm").validate({
		submitHandler : function(form) {
			loading('正在提交，请稍等...');
			form.submit();
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			$("#messageBox").text("输入有误，请先更正。");
			if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
				error.appendTo(element.parent().parent());
			} else {
				error.insertAfter(element);
			}
		}
		});

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
		

	});

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
	//所属行业加载
	var treeCategory = [ 'categoryMain', 'categoryLarge', 'categoryMedium', 'categorySmall' ];
	function loadCategory(root, curr) {
		var parentInduCode = $("#" + root).select2("val");
		var emptyFlag = false;
		for (var i = 0; i < treeCategory.length; i++) {
			if (curr == treeCategory[i]) {
				emptyFlag = true;
			}
			if (emptyFlag) {
				$("#" + treeCategory[i]).select2("val", "");
				$("#" + treeCategory[i]).empty();
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
</script>
</head>
<body>
	<div class="wrapper">
		<ul class="nav nav-tabs">
			<li class="active">
				<a href="${ctx}/custinfo/custInfo/form?id=${custInfo.id}">客户详情</a>
			</li>
			<li>
				<a href="${ctx}/credit/RelatedPiece?custId=${custInfo.id}">关联进件</a>
			</li>
		</ul>
	</div>
	<br />
	<br />
	<div class="searchInfo">
		<h3 class="searchTitle">客户详情</h3>
		<div class="searchCon">
			<form:form id="inputForm" modelAttribute="custInfo" action="${ctx}/custinfo/custInfo/save" method="post" class="form-horizontal">
				<form:hidden path="id" />
				<sys:message content="${message}" />
				<table class="fromTable">
					<tr>
						<td class="ft_label">客户名称：</td>
						<td class="ft_content">
							<input type="text" id="custName" name="custName" readonly="readonly" class="input-medium" value="${custInfo.custName}" />
						</td>
						<td class="ft_label">证件类型：</td>
						<td class="ft_content">
							<input type="text" class="input-medium" readonly value="${fns:getDictLabel(custInfo.idType, 'CUSTOMER_P_ID_TYPE', '')}" />
						</td>
						<td class="ft_label">证件号：</td>
						<td class="ft_content">
							<input type="text" id="idNum" name="idNum" class="input-medium card" readonly="readonly" value="${custInfo.idNum}" />
						</td>
					</tr>
					<tr>
						<td class="ft_label">手机号：</td>
						<td class="ft_content">
							<input type="text" id="mobileNum" name="mobileNum" readonly="readonly" class="input-medium mobile" value="${custInfo.mobileNum}" />
						</td>
						<td class="ft_label">民族：</td>
						<td class="ft_content">
							<form:select id="nationNo" path="nationNo" class="input-medium required" cssStyle="width:177px;" readonly="readonly" disabled="true">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('NATION_NO')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
						</td>
					</tr>
					<tr>
						<td class="ft_label">出生日期：</td>
						<td class="ft_content">
							<input id="birthDay" name="birthDay" type="text" maxlength="20" class="input-medium Wdate" disabled="true" value="${custInfo.birthDay}" onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd'});" />
						</td>
						<td class="ft_label">年龄：</td>
						<td class="ft_content">
							<input type="text" id="ageNo" maxlength="2" name="ageNo" readonly="readonly" class="input-medium" value="${custInfo.ageNo}" />
						</td>
						<td class="ft_label">性别：</td>
						<td class="ft_content">
							<form:select id="sexNo" path="sexNo" class="input-medium required" cssStyle="width:177px;" disabled="true">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
						</td>
					</tr>
					<tr>
						<td class="ft_label">婚姻状况：</td>
						<td class="ft_content">
							<input type="text" class="input-medium" readonly value="${fns:getDictLabel(custInfo.wedStatus, 'WED_STATUS', '')}" />
						<td class="ft_label">子女数量：</td>
						<nobr>
							<td class="ft_content">
								(
								<input type="text" id="childrenSon" name="childrenSon" readonly="readonly" class="input-mini" value="${custInfo.childrenSon}" />
								男
								<input type="text" id="childrenGirl" name="childrenGirl" readonly="readonly" class="input-mini" value="${custInfo.childrenGirl}" />
								女)
							</td>
						</nobr>
						<td class="ft_label">供养人数：</td>
						<td class="ft_content">
							<input type="text" id="providesNum" name="providesNum" class="input-medium" readonly="readonly" value="${custInfo.providesNum}" />
						</td>
					</tr>
					<tr>
						<td class="ft_label">每月家庭支出：</td>
						<td class="ft_content">
							<input type="text" id="householdSpendingMonth" name="householdSpendingMonth" readonly="readonly" class="input-mini" value="${custInfo.householdSpendingMonth}" />
						</td>
						<td class="ft_label">最高学历：</td>
						<td class="ft_content">
							<input type="text" class="input-medium" readonly value="${fns:getDictLabel(custInfo.topEducationNo, 'EDUCATION', '')}" />
						</td>
						<td class="ft_label">家庭电话(区号+电话号)：</td>
						<td class="ft_content">
							<input type="text" id="homePhoneNo" name="homePhoneNo" class="input-medium" readonly="readonly" value="${custInfo.homePhoneNo}" />
						</td>
					</tr>
					<tr>
						<td class="ft_label">现住宅地居住时间(年)：</td>
						<td class="ft_content">
							<input id="regInDate" name="regInDate" type="text" maxlength="3" class="input-medium number" value="${custInfo.regInDate}" />
						</td>
						<td class="ft_label">来本市时间(年)：</td>
						<td class="ft_content">
							<input id="cityInDate" name="cityInDate" type="text" maxlength="3" class="input-medium number" value="${custInfo.cityInDate}" />
						</td>
						<td class="ft_label">住宅电话(区号+电话号)：</td>
						<td class="ft_content">
							<input type="text" id="hosePhoneNo" name="hosePhoneNo" class="input-medium" readonly="readonly" value="${custInfo.hosePhoneNo}" />
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
							<span style="width: 15px;display: inline-block;"></span>
							地址明细：
							<input type="text" name="regDetails" id="regDetails" class="input-medium required" readonly="readonly" maxlength="200" value="${custInfo.regDetails}" style="margin: 0px;position: relative;display: inline-block;vertical-align: middle;width:480px" />
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">是否本地户籍：</td>
						<td class="ft_content">
							<form:select id="isLocal" path="isLocal" class="input-mini required" cssStyle="width:177px;" disabled="true">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
						</td>
						</td>
						<td class="ft_label">现居地与户籍地址是否一致：</td>
						<td class="ft_content" colspan="5">
							<form:radiobuttons path="addrResi" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="" disabled="true" />
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
							<span style="width: 15px;display: inline-block;"></span>
							地址明细：
							<input type="text" name="contDetails" id="contDetails" value="${custInfo.contDetails}" class="input-medium required" readonly="readonly" maxlength="200" style="margin: 0px;position: relative;display: inline-block;vertical-align: middle;" />
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">居住状况：</td>
						<td class="ft_content">
							<input type="text" id="livingStatus" name="livingStatus" readonly="readonly" class="input-medium" value="${custInfo.livingStatus}" />
						</td>
						<td class="ft_label">个人年收入：</td>
						<td class="ft_content">
							<input type="text" id="perAnnualIncome" name="perAnnualIncome" readonly="readonly" class="input-medium" value="${custInfo.perAnnualIncome}" />
						</td>
					</tr>
					<tr>
						<td class="ft_label">收入来源：</td>
						<td class="ft_content">
							<input type="text" id="sourceOfIncome" name="sourceOfIncome" readonly="readonly" class="input-medium" value="${custInfo.sourceOfIncome}" />
						</td>
					</tr>
					<tr>
						<td class="ft_label">电子邮箱：</td>
						<td class="ft_content">
							<input type="text" id="emailNo" name="emailNo" readonly="readonly" class="input-medium" value="${custInfo.emailNo}" />
						</td>
						<td class="ft_label">微信号码：</td>
						<td class="ft_content">
							<input type="text" id="wechatNo" name="wechatNo" readonly="readonly" class="input-medium" value="${custInfo.wechatNo}" />
						</td>
					</tr>
					<tr>
						<td class="ft_label">QQ号码：</td>
						<td class="ft_content">
							<input type="text" id="qqNo" name="qqNo" readonly="readonly" class="input-medium" value="${custInfo.qqNo}" />
						</td>
						<td class="ft_label">微博号码：</td>
						<td class="ft_content">
							<input type="text" id="microNo" name="microNo" readonly="readonly" class="input-medium" value="${custInfo.microNo}" />
						</td>
						<td class="ft_label">其他联系方式：</td>
						<td class="ft_content">
							<input type="text" id="otherContactType" name="otherContactType" readonly="readonly" class="input-medium" value="${custInfo.otherContactType}" />
						</td>
					</tr>
					<tr>
						<td class="ft_label">所属行业：</td>
						<td class="ft_content" colspan="5">
							<form:select path="categoryMain" class="input-small required" onchange="loadCategory('categoryMain','categoryLarge')" disabled="true">
								<form:option value="" label="" />
								<form:options items="${categoryMainList}" itemLabel="induName" itemValue="induCode" htmlEscape="false" />
							</form:select>
							门类
							<span class="help-inline">
								<font color="red">*</font>
							</span>
							<form:select path="categoryLarge" class="input-small required" onchange="loadCategory('categoryLarge','categoryMedium')" disabled="true">
								<form:option value="" label="" />
								<form:options items="${categoryLargeList}" itemLabel="induName" itemValue="induCode" htmlEscape="false" />
							</form:select>
							大类
							<span class="help-inline">
								<font color="red">*</font>
							</span>
							<form:select path="categoryMedium" class="input-small required" onchange="loadCategory('categoryMedium','categorySmall')" disabled="true">
								<form:option value="" label="" />
								<form:options items="${categoryMediumList}" itemLabel="induName" itemValue="induCode" htmlEscape="false" />
							</form:select>
							中类
							<span class="help-inline">
								<font color="red">*</font>
							</span>
							<form:select path="categorySmall" class="input-small required" disabled="true">
								<form:option value="" label="" />
								<form:options items="${categorySmallList}" itemLabel="induName" itemValue="induCode" htmlEscape="false" />
							</form:select>
							小类
							<span class="help-inline">
								<font color="red">*</font>
							</span>
						</td>
					</tr>
				</table>
			</form:form>
		</div>
	</div>
</body>
</html>