<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%--<jsp:useBean id="command" class="com.spring.mvc.Student" scope="request"></jsp:useBean>--%>
<%@ page import="com.resoft.common.utils.Constants"%>
<html>
<head>
<title>新增借款</title>
<meta name="decorator" content="default" />
<%--<c:if test="${true != readOnly}">--%>
	<script>
        $(document).ready(function() {

            <%--if (!checkIsNull('${underCustInfo.accountDistinct}')) {--%>
                <%--setName(data, "reg", '${underCustInfo.accountProvince}', '${underCustInfo.accountCity}', '${underCustInfo.accountDistinct}');--%>

            <%--}--%>

            getAccountInfo("+${underCompanyInfo.unSocCreditNo} + ");
//            alert(111);
            getCustorInfo("+${underCompanyInfo.corporationCardIdNo}+ ");
            //校验企业信息
            $("#underCompanyInfoForm").validate({
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
            //校验客户信息
            $("#underCustInfoForm").validate({
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

            /***************************************************初始化法人地址开始*************************************************************/
            new CitySelect({
                data : data,
                provId : '#operateProvince',
                cityId : '#operateCity',
                areaId : '#operateDistinct',
                isSelect : false
            });


            if (!checkIsNull('${underCompanyInfo.operateDistinct}')) {
                setName(data, "operate", '${underCompanyInfo.operateProvince}', '${underCompanyInfo.operateCity}', '${underCompanyInfo.operateDistinct}');
            }

            function workLoadCity() {
                $("#operateCity").empty();
                $("#operateCity").append("<option value=''>请选择</option>");
                var $operateCity = $("#s2id_operateCity>.select2-choice>.select2-chosen");
                $operateCity.html("请选择");
                $("#operateDistinct").empty();
                $("#operateDistinct").append("<option value=''>请选择</option>");
                var $operateDistinct = $("#s2id_operateDistinct>.select2-choice>.select2-chosen");
                $operateDistinct.html("请选择");
                $.post("${ctx}/sys/area/treeNode", {
                    parentId : $("#operateProvince").val()
                }, function(data) {
                    $.each(data, function(i, val) {
                        $("#operateCity").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
                    });
                });
            }
            function workLoadDistinct() {
                $("#operateDistinct").empty();
                $("#operateDistinct").append("<option value=''>请选择</option>");
                var $operateDistinct = $("#s2id_operateDistinct>.select2-choice>.select2-chosen");
                $operateDistinct.html("请选择");

                $.post("${ctx}/sys/area/treeNode", {
                    parentId : $("#operateCity").val()
                }, function(data) {
                    $.each(data, function(i, val) {
                        $("#operateDistinct").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
                    });
                });
            }
            /***************************************************初始化化法人地址结束*************************************************************/
            /***************************************************初始化注册地址开始*************************************************************/
            new CitySelect({
                data : data,
                provId : '#registerProvince',
                cityId : '#registerCity',
                areaId : '#registerDistinct',
                isSelect : false
            });

            if (!checkIsNull('${underCompanyInfo.registerDistinct}')) {
                setName(data, "register", '${underCompanyInfo.registerProvince}', '${underCompanyInfo.registerCity}', '${underCompanyInfo.registerDistinct}');
            }

            function workLoadCity() {
                $("#registerCity").empty();
                $("#registerCity").append("<option value=''>请选择</option>");
                var $registerCity = $("#s2id_registerCity>.select2-choice>.select2-chosen");
                $registerCity.html("请选择");
                $("#registerDistinct").empty();
                $("#registerDistinct").append("<option value=''>请选择</option>");
                var $registerDistinct = $("#s2id_registerDistinct>.select2-choice>.select2-chosen");
                $registerDistinct.html("请选择");
                $.post("${ctx}/sys/area/treeNode", {
                    parentId : $("#registerProvince").val()
                }, function(data) {
                    $.each(data, function(i, val) {
                        $("#registerCity").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
                    });
                });
            }
            function workLoadDistinct() {
                $("#registerDistinct").empty();
                $("#registerDistinct").append("<option value=''>请选择</option>");
                var $registerDistinct = $("#s2id_registerDistinct>.select2-choice>.select2-chosen");
                $registerDistinct.html("请选择");

                $.post("${ctx}/sys/area/treeNode", {
                    parentId : $("#registerCity").val()
                }, function(data) {
                    $.each(data, function(i, val) {
                        $("#registerDistinct").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
                    });
                });
            }
            /***************************************************初始化化注册地址结束*************************************************************/
            /***************************************************初始化办公地址开始*************************************************************/

            new CitySelect({
                data : data,
                provId : '#officeProvince',
                cityId : '#officeCity',
                areaId : '#officeDistinct',
                isSelect : false
            });

            if (!checkIsNull('${underCompanyInfo.officeDistinct}')) {
                setName(data, "office", '${underCompanyInfo.officeProvince}', '${underCompanyInfo.officeCity}', '${underCompanyInfo.officeDistinct}');
            }

            function workLoadCity() {
                $("#officeCity").empty();
                $("#officeCity").append("<option value=''>请选择</option>");
                var $officeCity = $("#s2id_officeCity>.select2-choice>.select2-chosen");
                $officeCity.html("请选择");
                $("#officeDistinct").empty();
                $("#officeDistinct").append("<option value=''>请选择</option>");
                var $officeDistinct = $("#s2id_officeDistinct>.select2-choice>.select2-chosen");
                $officeDistinct.html("请选择");
                $.post("${ctx}/sys/area/treeNode", {
                    parentId : $("#officeProvince").val()
                }, function(data) {
                    $.each(data, function(i, val) {
                        $("#officeCity").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
                    });
                });
            }
            function workLoadDistinct() {
                $("#officeDistinct").empty();
                $("#officeDistinct").append("<option value=''>请选择</option>");
                var $officeDistinct = $("#s2id_officeDistinct>.select2-choice>.select2-chosen");
                $officeDistinct.html("请选择");

                $.post("${ctx}/sys/area/treeNode", {
                    parentId : $("#officeCity").val()
                }, function(data) {
                    $.each(data, function(i, val) {
                        $("#officeDistinct").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
                    });
                });
            }
            /***************************************************初始化化办公地址结束*************************************************************/
            /***************************************************初始化开户地址开始*************************************************************/
            new CitySelect({
                data : data,
                provId : '#accountProvince',
                cityId : '#accountCity',
                areaId : '#accountDistinct',
                isSelect : false
            });

            if (!checkIsNull('${underCustInfo.accountProvince}')) {
                setName(data, "account", '${underCustInfo.accountProvince}', '${underCustInfo.accountCity}', '${underCustInfo.accountDistinct}');
            }


            function workLoadCity() {
                $("#accountCity").empty();
                $("#accountCity").append("<option value=''>请选择</option>");
                var $accountCity = $("#s2id_accountCity>.select2-choice>.select2-chosen");
                $accountCity.html("请选择");
                $("#accountDistinct").empty();
                $("#accountDistinct").append("<option value=''>请选择</option>");
                var $accountDistinct = $("#s2id_accountDistinct>.select2-choice>.select2-chosen");
                $accountDistinct.html("请选择");
                $.post("${ctx}/sys/area/treeNode", {
                    parentId : $("#accountProvince").val()
                }, function(data) {
                    $.each(data, function(i, val) {
                        $("#accountCity").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
                    });
                });
            }
            function workLoadDistinct() {
                $("#accountDistinct").empty();
                $("#accountDistinct").append("<option value=''>请选择</option>");
                var $accountDistinct = $("#s2id_accountDistinct>.select2-choice>.select2-chosen");
                $accountDistinct.html("请选择");

                $.post("${ctx}/sys/area/treeNode", {
                    parentId : $("#accountCity").val()
                }, function(data) {
                    $.each(data, function(i, val) {
                        $("#accountDistinct").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
                    });
                });
            }

            /***************************************************初始化开户地址结束*************************************************************/

        });

        /***************************************************初始化行业信息开始*************************************************************/
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
        /***************************************************初始化行业信息结束*************************************************************/

        /***************************************************加载企业账户信息开始*************************************************************/
        function getAccountInfo() {
            var unSocCreditNoval = $("#unSocCreditNo").val();
            if ("" != unSocCreditNoval && unSocCreditNoval.length > 0){
                $.post("${ctx}/credit/underCompanyInfo/getAccountCompanyInfo", {
                    socialCreditNo : unSocCreditNoval
                }, function(data) {
//                    alert(data.status);
                    if (data.status == 0 ){
//                        $("#custoeTable").hide();
                        $("#companyTable").hide();
                        return;
                    }

                    $("#c_status").html("已开户");
                    $("#c_companyName").html(data.accountCompany.companyName);
//                    $("#c_companyCardType").html(data.accountCompany.companyCardType);
                    $("#c_companyCardType").html("身份证");
                    $("#c_socialCreditCode").html(data.accountCompany.socialCreditCode);
                    $("#c_legalName").html(data.accountCompany.legalName);
                    $("#c_legalCardNumber").html(data.accountCompany.legalCardNumber);
                    $("#c_legalMobile").html(data.accountCompany.legalMobile);
                    $("#c_companyBankOfDepositValue").html(data.accountCompany.companyBankOfDepositValue);
                    $("#c_companyAccount").html(data.accountCompany.companyName);
                    $("#c_companyBankBranchName").html(data.accountCompany.companyBankBranchName);
                    $("#c_provinceCode_cityCode_areaCode").html(data.accountCompany.companyName);
                    $("#c_companyContact").html(data.accountCompany.companyContact);
                    $("#c_contactMobile").html(data.accountCompany.contactMobile);
//                    $("#custoeTable").hide();
                    $("#companyTable").show();
                }, "json");
            }
        }

        /***************************************************加载个人账户信息结束*************************************************************/
        function getCustorInfo() {
//            var idCardNum = $("#corporationCardIdNo").val();
            var idCardNum = $("#idNum").val();
            if ("" != idCardNum && idCardNum.length > 0){
                $.post("${ctx}/credit/underCompanyInfo/getAccountInfo", {
                    idCardNum : idCardNum
                }, function(data) {

                    if (data.status == 0 ){
                        $("#custoeTable").hide();
//                        $("#companyTable").hide();
                        return;
                    }

                    $("#custoeTable").show();
//                    $("#companyTable").hide();

//                    $("#p_status").html(data.account.status);
                    $("#p_status").html("已开户");
                    $("#p_legalPerName").html(data.account.legalPerName);
                    $("#p_legalPerNum").html(data.account.legalPerNum);
                    $("#p_legalPerPhone").html(data.account.legalPerPhone);
//                    $("#p_companyBankOfDeposit").html(data.account.companyBankOfDeposit);
                    $("#p_companyBankOfDeposit").html(data.bankName);
                    $("#p_companyAccount").html(data.account.companyAccount);
                    $("#p_provinceName_cityName").html(data.account.companyBankBranchName );

                }, "json");
            }
        }

        /***************************************************加载个人账户信息结束*************************************************************/
	</script>

</head>

<body>
	<sys:message content="${message}" />
	<%--企业借款信息--%>
	<div class="">
		<form:form id="underCompanyInfoForm" modelAttribute="underCompanyInfo" action="${ctx}/credit/underCompanyInfo/saveCompanyInfo" method="post" class="form-horizontal">
		<div class="infoList">
			<h3 onclick="" class="infoListTitle">企业信息</h3>
			<div class="infoListCon">
				<div id="khdjxx" class="filter">
					<%--<input type="hidden" name="id" value="${underCompanyInfo.applyNo}" />--%>
					<%@ include file="/WEB-INF/views/app/credit/workflow/include/actTaskParams.jsp"%>
					<table class="fromTable">
						<tr>

							<td class="ft_label"><font style="color: red">*</font>企业名称：</td>
							<td class="ft_content">
								<input type="text"  maxlength="50"  id="busiRegName" name="busiRegName" class="input-medium  required"  value="${underCompanyInfo.busiRegName}" />
							</td>
							<td class="ft_label"><font style="color: red">*</font>统一社会信用代码：</td>
							<td class="ft_content">
								<input type="text"  maxlength="50" id="unSocCreditNo" name="unSocCreditNo" class="input-medium  required"  onblur="getAccountInfo();" value="${underCompanyInfo.unSocCreditNo}" />
							</td>
							<td class="ft_label"><font style="color: red">*</font>营业执照号：</td>
							<td class="ft_content">
								<input type="text" maxlength="100"  id="busiLicRegNo" name="busiLicRegNo" class="input-medium required"   value="${underCompanyInfo.busiLicRegNo}" />
							</td>
						</tr>
						<tr>
							<td class="ft_label"><font style="color: red">*</font>注册资本：</td>
							<td class="ft_content">
								<input type="text" maxlength="30"  id="registerCapital" name="registerCapital" class="input-medium required"   value="${underCompanyInfo.registerCapital}" />
							</td>
							<td class="ft_label"><font style="color: red">*</font>实缴资本：</td>
							<td class="ft_content">
								<input type="text"  maxlength="30" id="paidInCapital" name="paidInCapital" class="input-medium required"   value="${underCompanyInfo.paidInCapital}" />
							</td>
							<td class="ft_label"><font style="color: red">*</font>成立时间：</td>
							<td class="ft_content">
								<%--<input type="text" id="companyStartDate" name="companyStartDate" class="input-medium required"   value="${underCompanyInfo.companyStartDate}" />--%>
								<input id="companyStartDate" maxlength="20"  name="companyStartDate" type="text" maxlength="20" class="input-medium Wdate required" value="<fmt:formatDate value="${underCompanyInfo.companyStartDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd'});" />

							</td>
						</tr>
						<tr>
							<td class="ft_label"><font style="color: red">*</font>法定代表人名称：</td>
							<td class="ft_content">
								<input type="text" id="corporationName" maxlength="30"  name="corporationName" class="input-medium required"   value="${underCompanyInfo.corporationName}" />
							</td>
							<td class="ft_label"><font style="color: red">*</font>法定代表人身份证号：</td>
							<td class="ft_content">
								<input type="text" id="corporationCardIdNo"  maxlength="50" name="corporationCardIdNo"   class="input-medium card required"   value="${underCompanyInfo.corporationCardIdNo}" />
							</td>
							<td class="ft_label"><font style="color: red">*</font>经营区域：</td>
							<td class="ft_content">
								<input type="text" id="regDistinct" name="regDistinct" maxlength="100"  class="input-medium required"   value="${underCompanyInfo.regDistinct}" />
							</td>
						</tr>

						<tr>
							<td class="ft_label"><font style="color: red">*</font>法定代表人地址：</td>
							<td class="ft_content" colspan="5">
								<form:select path="operateProvince" class="input-small nuNullCheck" data-code="-1" ></form:select>
								&nbsp;省
								<form:select path="operateCity" class="input-small nuNullCheck" data-code="-1" ></form:select>
								&nbsp;市
								<form:select path="operateDistinct" class="input-small nuNullCheck" data-code="-1" ></form:select>
								&nbsp;区&nbsp;
								<span style="width: 15px; display: inline-block;"></span>
								地址明细：
								<input type="text" maxlength="100"  name="operateDetails" id="operateDetails" onblur="this.value=reSpaces(this.value);" value="${underCompanyInfo.operateDetails}" class="input-medium  required" maxlength="300" style="margin: 0px; position: relative; vertical-align: middle;width:325px" />
								<%--<font color="red">*</font>--%>
							</td>
						</tr>
						<tr>
							<td class="ft_label"><font style="color: red">*</font>注册地址：</td>
							<td class="ft_content" colspan="5">
								<form:select path="registerProvince" class="input-small nuNullCheck" data-code="-1" ></form:select>
								&nbsp;省
								<form:select path="registerCity" class="input-small nuNullCheck" data-code="-1" ></form:select>
								&nbsp;市
								<form:select path="registerDistinct" class="input-small nuNullCheck" data-code="-1" ></form:select>
								&nbsp;区&nbsp;
								<span style="width: 15px; display: inline-block;"></span>
								地址明细：
								<input type="text" maxlength="100"  name="registerDetails" id="registerDetails" onblur="this.value=reSpaces(this.value);" value="${underCompanyInfo.registerDetails}" class="input-medium required" maxlength="300" style="margin: 0px; position: relative; vertical-align: middle;width:325px" />
								<%--<font color="red">*</font>--%>
							</td>
						</tr>
						<tr>
							<td class="ft_label"><font style="color: red">*</font>办公地址：</td>
							<td class="ft_content" colspan="5">
								<form:select path="officeProvince" class="input-small nuNullCheck" data-code="-1" ></form:select>
								&nbsp;省
								<form:select path="officeCity" class="input-small nuNullCheck" data-code="-1" ></form:select>
								&nbsp;市
								<form:select path="officeDistinct" class="input-small nuNullCheck" data-code="-1" ></form:select>
								&nbsp;区&nbsp;
								<span style="width: 15px; display: inline-block;"></span>
								地址明细：
								<input type="text" name="officeDetails" maxlength="100"  id="officeDetails" onblur="this.value=reSpaces(this.value);" value="${underCompanyInfo.officeDetails}" class="input-medium  required" maxlength="300" style="margin: 0px; position: relative; vertical-align: middle;width:325px" />
								<%--<font color="red">*</font>--%>
							</td>
						</tr>

						<tr>
							<td class="ft_label"><font style="color: red">*</font>所属行业：</td>
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
							<%--<font color="red">*</font>--%>
						</span>
							</td>
						</tr>

						<tr>
							<td class="ft_label"><font style="color: red">*</font>企业信息：</td>
							<td class="" colspan="2">
								<textarea rows="4" cols="100" maxlength="500" id="companyInfo" name="companyInfo" class="input-xxlarge textarea-style required"  onkeyup="adjustTextareaLength('suggestion','pre')">${underCompanyInfo.companyInfo}</textarea>
								<%--<font style="color: red">*</font>--%>
							</td>
							<td class="ft_label"><font style="color: red">*</font>企业产品介绍：</td>
							<td class="" colspan="2">
								<textarea rows="4" cols="100" maxlength="100" id="companyProductInfo" name="companyProductInfo" class="input-xxlarge textarea-style required"  onkeyup="adjustTextareaLength('suggestion','pre')">${underCompanyInfo.companyProductInfo}</textarea>
								<%--<font style="color: red">*</font>--%>
							</td>
						</tr>
						<tr>
							<td class="ft_label"><font style="color: red">*</font>股东信息：</td>
							<td class="" colspan="2">
								<textarea rows="4" cols="100" maxlength="100" id="stockInfo" name="stockInfo" class="input-xxlarge textarea-style required"  onkeyup="adjustTextareaLength('suggestion','pre')">${underCompanyInfo.stockInfo}</textarea>
								<%--<font style="color: red">*</font>--%>
							</td>
							<td class="ft_label"><font style="color: red">*</font>企业法人征信信息：</td>
							<td class="" colspan="2">
								<textarea rows="4" cols="100" maxlength="100" id="creditCorporation" name="creditCorporation" class="input-xxlarge textarea-style required"  onkeyup="adjustTextareaLength('suggestion','pre')">${underCompanyInfo.creditCorporation}</textarea>
								<%--<font style="color: red">*</font>--%>
							</td>
						</tr>
						<tr>
							<td class="ft_label"><font style="color: red">*</font>其他平台借款情况：</td>
							<td class="" colspan="2">
								<textarea rows="4" cols="100" maxlength="100" id="otherLoanplatInfo" name="otherLoanplatInfo" class="input-xxlarge textarea-style required"  onkeyup="adjustTextareaLength('suggestion','pre')">${underCompanyInfo.otherLoanplatInfo}</textarea>
								<%--<font style="color: red">*</font>--%>
							</td>
						</tr>
						<tr>
							<td class="ft_label">
								<font style="color:red">*</font>
								企业银行贷款情况：
							</td>
							<td colspan="5">
								<table class="table table-striped table-bordered table-condensed">
									<thead>
									<tr>
										<th>是否有贷款记录</th>
										<th>共几笔贷款记录</th>
										<th>当前是否有逾期</th>
										<th>企业征信记录等级</th>
									</tr>
									</thead>
									<tbody>
									<tr>
										<td>
											<form:select path="isLoan" class="input-medium required" cssStyle="width:177px;">
												<form:option value="" label="请选择" />
												<form:options items="${fns:getDictList('IS_HAVE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
											</form:select>
										</td>
										<td>
											<form:input type="hidden" path="id" id="bankLoanId" htmlEscape="false" readonly="true" class="input-medium" />
											<form:input type="text" path="loanRecode" htmlEscape="false" maxlength="3" class="input-medium required" />
										</td>
										<td>
											<form:select path="isOverDue" class="input-medium required" cssStyle="width:177px;">
												<form:option value="" label="请选择" />
												<form:options items="${fns:getDictList('IS_HAVE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
											</form:select>
										</td>
										<td>
											<form:select path="sourceOfCreregist" class="input-medium required" cssStyle="width:177px;">
												<form:option value="" label="请选择" />
												<form:options items="${fns:getDictList('CREDIT_REGISTRIES_LEVEL')}" itemLabel="label" itemValue="value" htmlEscape="false" />
											</form:select>
										</td>
									</tr>
									</tbody>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="searchButton">
				<input id="btnSubmit1" class="btn btn-primary" type="submit" value="保 存" />
			</div>
		</div>
		</form:form>





		<%--客户信息--%>
		<form:form id="underCustInfoForm" modelAttribute="underCustInfo" action="${ctx}/credit/underCompanyInfo/saveCustInfo" method="post">
			<div class="infoList">
				<h3 onclick="jksqxxClick()" class="infoListTitle">借款人基本信息</h3>
				<%@ include file="/WEB-INF/views/app/credit/workflow/include/actTaskParams.jsp"%>
				<%--<input type="hidden" name="id" value="${underCustInfo.applyNo}" />--%>
				<div id="jksqxx" class="infoListCon">
					<div class="filter">
						<table class="fromTable">
							<tr>
								<td class="ft_label"><font style="color: red">*</font>借款人姓名：</td>
								<td class="ft_content">
									<input type="text"  maxlength="30" id="custName" name="custName" class="input-medium required"   value="${underCustInfo.custName}" />
									<%--<input type="hidden" id="applyNo" name="applyNo" value="${underCustInfo.applyNo }"/>--%>
								</td>
								<td class="ft_label"><font style="color: red">*</font>借款人身份证：</td>
								<td class="ft_content">
									<input type="text"  maxlength="18" id="idNum" name="idNum" onblur="getCustorInfo();" class="input-medium  required card"  value="${underCustInfo.idNum}" />
								</td>
								<td class="ft_label"><font style="color: red">*</font>借款人手机号：</td>
								<td class="ft_content">
									<input type="text" maxlength="11"  NAME="mobileNum" id="mobileNum" class="input-medium  required"   value="${underCustInfo.mobileNum}"  />
								</td>

								</td>
							</tr>
							<tr>
								<td class="ft_label">借款人性别：</td>
								<td class="ft_content">
									<%--<input type="text" class="input-medium"   value="${underCustInfo.sexNo}" />--%>
									<%--<input type="hidden" id="sexNo" name="sexNo" value="${underCustInfo.sexNo }"/>--%>
										<form:select path="sexNo" class="input-medium required">
											<form:option value="" label="" />
											<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false" />
										</form:select>
								</td>
								<td class="ft_label">借款人生日：</td>
								<td class="ft_content">
									<%--<input type="text" id="birthDay" class="input-medium"   value="${underCustInfo.birthDay}" />--%>
									<%--<input id="birthDay" name="birthDay" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" value="${underCustInfo.birthDay}" />--%>
									<input id="birthDay" name="birthDay" type="text" maxlength="20" class="input-medium Wdate required" value="<fmt:formatDate value="${underCustInfo.birthDay}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd'});" />
								</td>
								<td class="ft_label">婚姻状况：</td>
								<td class="ft_content">
									<%--<input type="text" id="wedStatus" name="wedStatus" class="input-medium"  readonly value="${fns:getDictLabel(underCustInfo.wedStatus, 'WED_STATUS', '')}"    />--%>
									<form:select id="wedStatus" path="wedStatus" class="input-medium required" cssStyle="width:177px;">
										<form:option value="" label="" />
										<form:options items="${fns:getDictList('WED_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
								</td>

								</td>
							</tr>
							<tr>
								<td class="ft_label">学历：</td>
								<td class="ft_content">
									<%--<input type="text" class="input-medium"   value="${underCustInfo.topEducationNo}" />--%>
									<%--<input type="hidden" id="topEducationNo" name="topEducationNo" value="${underCustInfo.topEducationNo }"/>--%>
									<%--<input type="text" class="input-medium" readonly value="${fns:getDictLabel(custInfo.topEducationNo, 'EDUCATION', '')}" />--%>
										<form:select path="topEducationNo" id="topEducationNoMain" class="input-medium required" cssStyle="width:177px;">
											<form:option value="" label="" />
											<form:options items="${fns:getDictList('EDUCATION')}" itemLabel="label" itemValue="value" htmlEscape="false" />
										</form:select>
										<%--<font color="red">*</font>--%>
								</td>
								<td class="ft_label">平台逾期次数：</td>
								<td class="ft_content">
									<input type="text" maxlength="20"  id="platOverdueCount" name="platOverdueCount" class="input-medium"   value="${underCustInfo.platOverdueCount}" />
								</td>
								<td class="ft_label">平台逾期总金额(元)：</td>
								<td class="ft_content">
									<input type="text" maxlength="20"  id="platOverdueMoney" name="platOverdueMoney" class="input-medium"   value="${underCustInfo.platOverdueMoney}"  />
								</td>
								<%--<font style="color: red">*</font>--%>
								</td>
							</tr>

							<tr>
								<td class="ft_label"><font style="color: red">*</font>开户地址：</td>
								<td class="ft_content" colspan="5">
									<form:select path="accountProvince" class="input-small nuNullCheck" data-code="-1" >
									</form:select>
									&nbsp;省
									<form:select path="accountCity" class="input-small nuNullCheck" data-code="-1" >
									</form:select>
									&nbsp;市
									<form:select path="accountDistinct" class="input-small nuNullCheck" data-code="-1" >
									</form:select>
									&nbsp;区&nbsp;
									<span style="width: 15px; display: inline-block;"></span>
									地址明细：
									<input type="text" name="accountDetails" id="accountDetails" onblur="this.value=reSpaces(this.value);" value="${underCustInfo.accountDetails}" class="input-medium  required" maxlength="100" style="margin: 0px; position: relative; vertical-align: middle;width:325px" />
									<%--<font color="red">*</font>--%>


									<%--<form:select path="applyProductTypeCode" cssClass="input-medium">--%>
										<%--<form:option value="" label="" />--%>
										<%--<form:options items="${fns:getDictList('PRODUCT_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />--%>
									<%--</form:select>--%>
								</td>
							</tr>
							<tr>
								<td class="ft_label">其他平台网贷借款情况：</td>
								<td class="" colspan="2">
									<textarea rows="4" cols="100" maxlength="100" id="otherWebloanInfo" name="otherWebloanInfo" class="input-xxlarge textarea-style "  onkeyup="adjustTextareaLength('suggestion','pre')">${underCustInfo.otherWebloanInfo}</textarea>
									<%--<font style="color: red">*</font>--%>
								</td>
								<td class="ft_label">行政处罚等重大情况：</td>
								<td class="" colspan="2">
									<textarea rows="4" cols="100" maxlength="100" id="executivePenaltyInfo" name="executivePenaltyInfo" class="input-xxlarge textarea-style "  onkeyup="adjustTextareaLength('suggestion','pre')">${underCustInfo.executivePenaltyInfo}</textarea>
									<%--<font style="color: red">*</font>--%>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div class="searchButton">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />
			</div>
		</form:form>




		<%--账户信息--%>
		<form:form id="applyInfoForm" modelAttribute="applyInfo" action="${ctx}/credit/applyInfo/save" method="post">
			<div class="infoList">
				<h3 onclick="jksqxxClick()" class="infoListTitle">账户信息</h3>
				<%@ include file="/WEB-INF/views/app/credit/workflow/include/actTaskParams.jsp"%>
				<input type="hidden" name="id" value="${applyInfo.id}" />
				<div id="jksqxx" class="infoListCon">
					<div class="filter">
						<table id="companyTable" class="fromTable" style="display: none">
							<%--企业户的开户信息--%>
							<tr>
								<td class="ft_label">开户状态：</td>
								<td class="ft_content">
									<a id="c_status" name="" style=" text-decoration: none; color: black">      </a>
								</td>

							</tr>
							<tr>
								<td class="ft_label">企业名称：</td>
								<td class="ft_content">
									<a id="c_companyName" name="" style=" text-decoration: none; color: black">      </a>
								</td>
								<td class="ft_label">企业证件类型：</td>
								<td class="ft_content">
									<a id="c_companyCardType" name="" style=" text-decoration: none; color: black">   社会统一信用代码   </a>
								</td>
								<td class="ft_label">企业统一社会信用代码：</td>
								<td class="ft_content">
									<a id="c_socialCreditCode" name="" style=" text-decoration: none; color: black">      </a>
								</td>
							</tr>
							<tr>
								<td class="ft_label">法人姓名：</td>
								<td class="ft_content">
									<a id="c_legalName" name="" style=" text-decoration: none; color: black">      </a>
								</td>
								<td class="ft_label">法人身份证号码：</td>
								<td class="ft_content">
									<a id="c_legalCardNumber" name="" style=" text-decoration: none; color: black">      </a>
								</td>
								<td class="ft_label">法人手机号：</td>
								<td class="ft_content">
									<a id="c_legalMobile" name="" style=" text-decoration: none; color: black">      </a>
								</td>
							</tr>
							<tr>
								<td class="ft_label">企业开户银行：</td>
								<td class="ft_content">
									<a id="c_companyBankOfDepositValue" name="" style=" text-decoration: none; color: black">      </a>
								</td>
								<td class="ft_label">企业对公账号：</td>
								<td class="ft_content">
									<a id="c_companyAccount" name="" style=" text-decoration: none; color: black">      </a>
								</td>
								<td class="ft_label">开户支行名称：</td>
								<td class="ft_content">
									<a id="c_companyBankBranchName" name="" style=" text-decoration: none; color: black">      </a>
								</td>
							</tr>
							<tr>
								<td class="ft_label">开户支行所在地区：</td>
								<td class="ft_content">
									<a id="c_provinceCode_cityCode_areaCode" name="" style=" text-decoration: none; color: black">      </a>
								</td>
								<td class="ft_label">企业联系人：</td>
								<td class="ft_content">
									<a id="c_companyContact" name="" style=" text-decoration: none; color: black">      </a>
								</td>
								<td class="ft_label">联系人手机号：</td>
								<td class="ft_content">
									<a id="c_contactMobile" name="" style=" text-decoration: none; color: black">      </a>
								</td>
							</tr>

						<table id="custoeTable" class="fromTable" style="display: none">

								<%--个人户的开户信息--%>
							<tr>
								<td class="ft_label">开户状态：</td>
								<td class="ft_content">
									<a id="p_status" name="" style=" text-decoration: none; color: black">      </a>
								</td>

							</tr>
							<tr>
								<td class="ft_label">开户人姓名：</td>
								<td class="ft_content">
									<a id="p_legalPerName" name="" style=" text-decoration: none; color: black">      </a>
								</td>
								<td class="ft_label">收款人身份证：</td>
								<td class="ft_content">
									<a id="p_legalPerNum" name="" style=" text-decoration: none; color: black">      </a>
								</td>
								<td class="ft_label">移动电话：</td>
								<td class="ft_content">
									<a id="p_legalPerPhone" name="" style=" text-decoration: none; color: black">      </a>
								</td>
							</tr>
							<tr>
								<td class="ft_label">收款银行：</td>
								<td class="ft_content">
									<a id="p_companyBankOfDeposit" name="" style=" text-decoration: none; color: black">      </a>
								</td>
									<%--<td class="ft_label">收款账户名称：</td>--%>
									<%--<td class="ft_content">--%>
									<%--<a id="p_" name="" style=" text-decoration: none; color: black">      </a>--%>
									<%--</td>--%>
								<td class="ft_label">收款银行卡号：</td>
								<td class="ft_content">
									<a id="p_companyAccount" name="" style=" text-decoration: none; color: black">      </a>
								</td>
							</tr>
							<tr>
								<td class="ft_label">收款银行地址：</td>
								<td class="ft_content">
									<a id="p_provinceName_cityName" name="" style=" text-decoration: none; color: black">      </a>
								</td>
							</tr>
						</table>
						</table>
					</div>
				</div>
			</div>
		</form:form>

	</div>
</body>
<script>
</script>
</html>