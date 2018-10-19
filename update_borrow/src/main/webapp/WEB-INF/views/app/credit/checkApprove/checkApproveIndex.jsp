<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>批复信息</title>
	<meta name="decorator" content="default" />
	<script type="text/javascript">
        $(document).ready(function() {
            var productCategoryKey = '${productCategoryKey}';
            if(productCategoryKey==null||productCategoryKey==''){//不是债股结合产品
                $(".ZGCategory").hide();//隐藏不可编辑的分类
            }else{//是债股结合的产品
                $(".ZGCategory").show();
            }

            var showCgFlag='${showCgFlag}';
            if(showCgFlag==1){
                $(".cgisShow").show();
                $.loadDiv("purchaseInfoListDiv", "${ctx }/credit/purchaseInfo/list", {
                    applyNo : '${actTaskParam.applyNo}'
                }, "post");
            }else{
                $(".cgisShow").hide();
            }
            getPricedRisk('${actTaskParam.applyNo}');
            if ("${actTaskParam.taskDefKey}" == "utask_fgsjlsh" || "${actTaskParam.taskDefKey}" == "utask_qyjlsh" || "${actTaskParam.taskDefKey}" == "utask_dqfkjlsh" || "${actTaskParam.taskDefKey}" == "utask_zgsjlsh" || "${actTaskParam.taskDefKey}" == "utask_zgszjlsh" || "${actTaskParam.taskDefKey}" == "utask_fgsfy") {
                setDivReadOnly("checkApprovInfoDiv");
                removeOnkeyup();
                if ("${actTaskParam.taskDefKey}" != "utask_fgsjlsh") {
                    $("div[id='checkApprovInfoDiv'] div[class='searchButton']").show();
                    $("div[id='checkApprovInfoDiv'] input[class='btn btn-primary']").show();
                    $("div[id='checkApprovInfoDiv'] input[id='btnSubmit']").hide();
                }
            }

            if ("${actTaskParam.taskDefKey}" == "utask_fgsfksh") {
                $("div[id='buttonDiv'] input[id='viewApproveHistory']").hide();
            }

            if ("${readOnly}" == "viewHistory") {
                setDivReadOnly("checkApprovInfoDiv");
                removeOnkeyup();
            }
            if ("${readOnly}" == "true") {
                removeOnkeyup();
            }
            document.getElementById("contractAmount").value = outputmoney("${checkApprove.contractAmount}");
            if(document.getElementById("qualityServiceMarginAmount") != null){
                document.getElementById("qualityServiceMarginAmount").value = outputmoney("${checkApprove.qualityServiceMarginAmount}");
            }
            document.getElementById("loanAmount").value = outputmoney("${checkApprove.loanAmount}");
            document.getElementById("serviceFee").value = outputmoney("${checkApprove.serviceFee}")
            if (document.getElementById("specialServiceFee") != 'undefined' && document.getElementById("specialServiceFee") != null && document.getElementById("specialServiceFee") != "") {
                document.getElementById("specialServiceFee").value = outputmoney("${checkApprove.specialServiceFee}")
            }
            document.getElementById("allServiceFee").value = outputmoney("${checkApprove.allServiceFee}");
            document.getElementById("marginAmount").value = outputmoney("${checkApprove.marginAmount}");
            document.getElementById("checkFee").value = outputmoney("${checkApprove.checkFee}");


            $("#checkApproveForm").validate({
                submitHandler : saveForm,
                errorContainer : "#messageBox",
                errorPlacement : function(error, element) {
                    checkReq(error, element);
                }
            });
            //还款计划
            $.loadDiv("repayPlanDiv", "${ctx }/credit/repayPlan/toRepayPlan", {
                applyNo : "${actTaskParam.applyNo}",
                taskDefKey : "${checkApprove.taskDefKey}"
            }, "post");

            //加载页面后 调整视图 计算放款金额，保证金，服务费
            monthlySpreadStatus();

            calculateAmount();

            loadProductPeriod();

            adjustTextareaLength("remark", "preRemark");
        });

        function saveForm() {
            $("#btnSubmit").attr("disabled", "true");
            loading();
            var valueT1 = $("#contractAmount").val().replace(/,/g, "");
            var valueT2 = $("#loanAmount").val().replace(/,/g, "");
            var valueT3 = $("#serviceFee").val().replace(/,/g, "");
            if ($("#specialServiceFee").val() != 'undefined' && $("#specialServiceFee").val() != null && $("#specialServiceFee").val() != "") {
                var valueT4 = $("#specialServiceFee").val().replace(/,/g, "");
                $("#specialServiceFee").val(valueT4);
            }
            var valueT5 = $("#allServiceFee").val().replace(/,/g, "");
            var valueT6 = $("#marginAmount").val().replace(/,/g, "");
            var valueT7 = $("#checkFee").val().replace(/,/g, "");
            $("#contractAmount").val(valueT1);
            $("#loanAmount").val(valueT2);
            $("#serviceFee").val(valueT3);
            $("#allServiceFee").val(valueT5);
            $("#marginAmount").val(valueT6);
            $("#checkFee").val(valueT7);
            $("#interestMonthlySpread").val($("#interestMonthlySpread").val().replace(/,/g, ""));
            $("#interestRateDiff").val($("#interestRateDiff").val().replace(/,/g, ""));
            if($("#realityServiceFee").val() != null){
                $("#realityServiceFee").val($("#realityServiceFee").val().replace(/,/g, ""));
            }
            if($("#qualityServiceMarginAmount").val() != null ){
                var valueT8 = $("#qualityServiceMarginAmount").val().replace(/,/g, "");
                $("#qualityServiceMarginAmount").val(valueT8);
            }
            var valueT9 = $("#topShopBackMoney").val().replace(/,/g, "");
            var valueT11 = $("#mediacyServiceFee").val().replace(/,/g, "");
            $("#topShopBackMoney").val(valueT9);
            $("#mediacyServiceFee").val(valueT11);
            var formJson = $("#checkApproveForm").serializeJson();
            $.post("${ctx}/credit/checkApprove/save", formJson, function(data) {
                if (data) {
                    $("#btnSubmit").removeAttr("disabled");
                    closeTip();
                    if (data.status == 1) {
                        var id = data.id;
                        $("#guanetongRate").val(data.guanetongRate);
                        $("#checkApproveForm input[id=id]").val(id);
                        alertx(data.message);
                        $.loadDiv("repayPlanDiv", "${ctx }/credit/repayPlan/toRepayPlan", {
                            applyNo : "${actTaskParam.applyNo}",
                            taskDefKey : "${actTaskParam.taskDefKey}"
                        }, "post");
                        if(document.getElementById("qualityServiceMarginAmount") != null){
                            document.getElementById("qualityServiceMarginAmount").value = outputmoney($("#qualityServiceMarginAmount").val());
                        }
                        document.getElementById("contractAmount").value = outputmoney($("#contractAmount").val());
                        document.getElementById("loanAmount").value = outputmoney($("#loanAmount").val());
                        document.getElementById("serviceFee").value = outputmoney($("#serviceFee").val());
                        if (document.getElementById("specialServiceFee") != 'undefined' && document.getElementById("specialServiceFee") != null && document.getElementById("specialServiceFee") != "") {
                            document.getElementById("specialServiceFee").value = outputmoney($("#specialServiceFee").val());
                        }
                        document.getElementById("allServiceFee").value = outputmoney($("#allServiceFee").val());
                        document.getElementById("marginAmount").value = outputmoney($("#marginAmount").val());
                        document.getElementById("checkFee").value = outputmoney($("#checkFee").val());
                        document.getElementById("interestRateDiff").value = outputmoney($("#interestRateDiff").val());
                        var approProductTypeCode = $("#approProductTypeCode").val();
                        $("#isHideSuggestionDiv input[id=checkApproveProductType]").val(approProductTypeCode);
                        if ("${actTaskParam.taskDefKey}" == "utask_fgsfksh") {
                            parent.showGetInfoTab();
                        }
                    } else {
                        alertx(data.message);
                    }
                }
            });
        }

        function setPeriodValue() {
            var periodVal = $("#approPeriodId").find("option:selected").text();
            $("#approPeriodValue").val(periodVal);
        }
        function pfxxClick() {
            $("#pfxxId").toggle(600);
        }

        //放款金额=合同金额-服务费-保证金-特殊服务费
        function calculateAmount() {
            //先计算保证金，保证金=合同金额*保证金比例
            var contractAmount = $("#contractAmount").val().replace(/,/g, "");// 合同金额
            var zgjhTypeCode = '${zgjhTypeCode}';//债股结合类型
            var productTypeCode = $("#approProductTypeCode").val();
            var marginRate = $("#marginRate").val();// 保证金率
            var marginAmount = outputmoney(contractAmount * marginRate * 0.01);
            $("#marginAmount").val(marginAmount);// 保证金

            //先计算质量服务保证金，质量服务保证金=合同金额*质量服务担保费率
            if($("#qualityServiceMarginRate").val()  != null){
                var qualityServiceMarginRate = $("#qualityServiceMarginRate").val();//质量服务担保费率
                //质量服务保证金
                var qualityServiceMarginAmount = outputmoney(contractAmount * qualityServiceMarginRate * 0.01);
                $("#qualityServiceMarginAmount").val(qualityServiceMarginAmount);
            }

            //服务费，服务费=合同金额*服务费率*期限
            var serviceFeeRate = $("#serviceFeeRate").val();
            var approPeriodId = $("#approPeriodId").find("option:selected").text();
            var serviceFee = outputmoney(contractAmount * serviceFeeRate * 0.01 * approPeriodId);
			if(serviceFee == ""){
                $("#serviceFee").val("0.00");
            }else{
                $("#serviceFee").val(serviceFee);
            }

            //特殊服务费 ， 特殊服务费 = 合同金额*特殊服务费率
            var specialServiceFeeRate = $("#specialServiceFeeRate").val();
            var specialServiceFee = outputmoney(contractAmount * specialServiceFeeRate * 0.01);
            if(specialServiceFee==""||specialServiceFee==null||specialServiceFee=='undefined'){
                specialServiceFee="0";
            }
            $("#specialServiceFee").val(specialServiceFee);
            //服务费合计，服务费合计 = 服务费 + 特殊服务费
            var serviceFee = $("#serviceFee").val().replace(/,/g, "");
            var allServiceFee;
            if ($("#specialServiceFee").val()) {
                var specialServiceFee = $("#specialServiceFee").val().replace(/,/g, "");
                allServiceFee = outputmoney((serviceFee - 0) + (specialServiceFee - 0));
            } else {
                allServiceFee = outputmoney((serviceFee - 0));
            }

            if(allServiceFee == ''){
                $("#allServiceFee").val("0.00");
            }else{
                $("#allServiceFee").val(allServiceFee);
            }
            //放款金额，放款金额=合同金额-服务费-保证金-特殊服务费
            var marginAmountTrue = marginAmount.replace(/,/g, "");
            var serviceFee = $("#serviceFee").val().replace(/,/g, "");
            if($("#qualityServiceMarginRate").val()  != null){
                var qualityServiceMarginAmountTrue = qualityServiceMarginAmount.replace(/,/g, "");
            }
            var serviceType = document.getElementById('serviceFeeType').value;

            if(zgjhTypeCode == productTypeCode){
                loanAmount = outputmoney(contractAmount - marginAmountTrue);
                var addFundPeriod = $("#addFundPeriod").val();
                if (addFundPeriod != null && addFundPeriod != '') {
                    var realityServiceFee = outputmoney(loanAmount.replace(/,/g, "") * serviceFeeRate * addFundPeriod * 0.01);

					if(realityServiceFee == ""){
                        $("#realityServiceFee").val('0.00');
                    }else{
                        $("#realityServiceFee").val(realityServiceFee);
                    }
                    $("#allServiceFee").val(outputmoney((realityServiceFee.replace(/,/g, "") - 0) + (specialServiceFee.replace(/,/g, "") - 0)));
                }
            }else{
                if (serviceType == 1) {
                    if ($("#specialServiceFee").val()) {
                        var specialServiceFee = $("#specialServiceFee").val().replace(/,/g, "");
                        if($("#qualityServiceMarginRate").val()  != null){
                            var loanAmount = outputmoney(contractAmount - serviceFee - marginAmountTrue - specialServiceFee - qualityServiceMarginAmountTrue);
                        }else{
                            var loanAmount = outputmoney(contractAmount - serviceFee - marginAmountTrue - specialServiceFee);
                        }
                    } else {
                        if($("#qualityServiceMarginRate").val() != null){
                            var loanAmount = outputmoney(contractAmount - serviceFee - marginAmountTrue - qualityServiceMarginAmountTrue);
                        }else{
                            var loanAmount = outputmoney(contractAmount - serviceFee - marginAmountTrue);
                        }
                    }
                } else {
                    if($("#qualityServiceMarginRate").val()  != null){
                        var loanAmount = outputmoney(contractAmount - marginAmountTrue - qualityServiceMarginAmountTrue);
                    }else{
                        var loanAmount = outputmoney(contractAmount - marginAmountTrue);
                    }
                }
            }
            $("#loanAmount").val(loanAmount);

            if(marginRate>=30){
                //计算月息差 = 合同金额*保证金率*批复月利率
                if(contractAmount!=null&&marginRate!=null&&contractAmount!=''&&marginRate!=''){
                    var approYearRate = $("#approYearRate").val();
                    if(approYearRate!=null&&approYearRate!=''){
                        var interestRateDiffValue = contractAmount * marginRate *approYearRate*0.01*0.01;
                        var interestRateDiff;
                        if(interestRateDiffValue == "0"){
                            interestRateDiff = "0.00";
                        }else{
                            interestRateDiff = outputmoney(interestRateDiffValue);
                        }
                        if("NaN.undefined" == interestRateDiff){
                            $("#interestRateDiff").val("");
                        }else{
                            $("#interestRateDiff").val(interestRateDiff);
                        }
                    }
                }
                var contractAmountTemp=$("#contractAmount").val().replace(/,/g, "");
                var marginAmountTemp=$("#marginAmount").val().replace(/,/g, "");
                var approPeriodIdTemp=$("#approPeriodId").val().replace(/,/g, "");
                //上游返利总额= 上游供应商月返利*（合同金额—保证金）*期限
                if(contractAmountTemp!=null&&marginAmountTemp!=null&&approPeriodIdTemp!=null&&contractAmountTemp!=''&&marginAmountTemp!=''&&approPeriodIdTemp!=''){
                    var topShopBackRate = $("#topShopBackRate").val();
                    if(topShopBackRate!=null&&topShopBackRate!=''){
                        contractAmountTemp=parseFloat(contractAmountTemp);
                        marginAmountTemp=parseFloat(marginAmountTemp);
                        var topShopBackMoneyTemp=parseFloat(contractAmountTemp-marginAmountTemp);
                        var topShopBackMoney=outputmoney(topShopBackRate*topShopBackMoneyTemp*approPeriodId*0.01);
                        $("#topShopBackMoney").val(topShopBackMoney);
                    }
                }
                //实际月利率  = （1-保证金率）/100
                if(contractAmount!=null&&marginRate!=null&&contractAmount!=null&&contractAmount!=''&&marginRate!=''&&contractAmount!=''){
                    var topShopMonthRate = outputmoney((100-marginRate)/100);
                    $("#topShopMonthRate").val(topShopMonthRate);
                }
                $("#interestRateDiff").css('background-color','#FFFFFF');
                $("#topShopBackMoney").css('background-color','#FFFFFF');
                $("#topShopMonthRate").css('background-color','#FFFFFF');
            }else{
                $("#interestRateDiff").val("0");
                $("#topShopBackMoney").val("0");
                $("#topShopMonthRate").val("0");
                $("#interestRateDiff").css('background-color','#EEEEEE');
                $("#topShopBackMoney").css('background-color','#EEEEEE');
                $("#topShopMonthRate").css('background-color','#EEEEEE');
            }

            /**
             * 保证金月息差计算
             * 债股结合 ：
             * 保证金月息差 = 保证金 * 0.8%
             * 采购贷 保证金率 >= 30 ：
             * 保证金月息差 = 保证金 * 批复月利率
             *
             * 让利后月利率计算
             * 债股结合：
             * 利息月息差 = （0.8 - 让利后月利率）* 放款金额
             **/
            var interestRateDiff = "";
            var cgdTypeCode = '${cgdTypeCode}';
            if (zgjhTypeCode === productTypeCode) {
                //interestRateDiff = outputmoney(marginAmountTrue * 0.008);
				//更改为债股结合 : 保证金月系差 = 保证金 * 批复月利率
                interestRateDiff = outputmoney(marginAmountTrue * $("#approYearRate").val() * 0.01);

                var discountInterestRate = $("#discountInterestRate").val();
                if (discountInterestRate < 0.8) {
                    var interestMonthlySpread = (0.8 - discountInterestRate) * 0.01 * loanAmount.replace(/,/g, "");
                    $("#interestMonthlySpread").val(outputmoney(interestMonthlySpread));
                } else {
                    $("#interestMonthlySpread").val("0.00");
                }
            } else if (cgdTypeCode === productTypeCode) {
                if (marginRate >= 30) {
                    interestRateDiff = outputmoney(marginAmountTrue * $("#approYearRate").val() * 0.01);
                } else {
                    $("#interestRateDiff").val("0.00");
                    $("#interestRateDiff").attr("disable", true);
                }
            }
            $("#interestRateDiff").val(interestRateDiff);
        }

        function viewHistory() {
            var url = "${ctx}/credit/checkApprove/checkApproveHistoryList";
            openJBox("checkFeeFormBox", url, "查看历史批复信息", $(window).width() - 80, $(window).height() - 500, {
                applyNo : '${actTaskParam.applyNo}',
                todoOrDoneFlag : '${actTaskParam.status}',
                taskDefKey : '${actTaskParam.taskDefKey}'
            });
        }

        //选择产品类型后，自动加载可用产品列表数据
        function loadProductList() {
            var approProductTypeCode = $("#approProductTypeCode").val();
            $("#approProductTypeName").val($("#approProductTypeCode").find("option:selected").text());
            $("#s2id_approProductId>.select2-choice>.select2-chosen").html("");

            $("#approProductId").empty();
            if (null != approProductTypeCode && "" != approProductTypeCode) {
                $.post("${ctx}/credit/product/coProductList", {
                    "productType" : approProductTypeCode,
                    "procDefKey" : '${procDefKey}',
                    "orgId" : '${orgId}'
                }, function(data) {
                    var opstr = "<option value=''></option>";
                    $.each(data, function(i, val) {
                        opstr += "<option value='"+val.id+"'>" + val.productName + "</option>";
                    });
                    $("#approProductId").append(opstr);
                });
            }
        }
        //选择产品后，补填产品信息
        function loadProductPeriod() {
            $("#approProductName").val($("#approProductId").find("option:selected").text());
        }

        function getApproLoanRepayType() {
            $("#approLoanRepayType").empty();
            $("#approLoanRepayType").append("<option value=''>请选择</option>");
            var $approLoanRepayType = $("#s2id_approLoanRepayType>.select2-choice>.select2-chosen");
            $approLoanRepayType.html("请选择");

            $.post("${ctx}/credit/checkApprove/getApproLoanRepayTypelist", {
                productType : $("#approProductTypeCode").val()
            }, function(data) {
                $.each(data, function(i, val) {
                    $("#approLoanRepayType").append("<option value='"+val["loanRepayType"]+"'>" + val["loanRepayDesc"] + "</option>");
                });
            });
        }

        function removeOnkeyup() {
            $("div[id='checkApprovInfoDiv'] input[id='contractAmount']").removeAttr("onkeyup");
            $("div[id='checkApprovInfoDiv'] input[id='loanAmount']").removeAttr("onkeyup");
            $("div[id='checkApprovInfoDiv'] input[id='serviceFee']").removeAttr("onkeyup");
            $("div[id='checkApprovInfoDiv'] input[id='marginAmount']").removeAttr("onkeyup");
            $("div[id='checkApprovInfoDiv'] input[id='specialServiceFee']").removeAttr("onkeyup");
            $("div[id='checkApprovInfoDiv'] input[id='allServiceFee']").removeAttr("onkeyup");
            //采购贷
            $("div[id='checkApprovInfoDiv'] input[id='interestRateDiff']").removeAttr("onkeyup");
        }

        function getPricedRisk(applyNo) {
            $.ajax({
                url : "${ctx}/credit/checkApprove/getPricedRisk",
                data : {
                    applyNo : applyNo
                },
                async : false,
                dataType : "json",
                success : function(data) {
                    $("#pricedRisk").val(data.pricedRisk);
                }
            });
        }

        //整数验证
        function intVerify(num) {
            var reg = /^((?!0)\d{1,2}|100)$/;
            if (!num.match(reg)) {
                return false;
            } else {
                return true;
            }
        }

        /**
         * 月息差显示判断
         */
        function monthlySpreadStatus() {
            var productTypeCode = $("#approProductTypeCode").val();
            if ("1" === '${flowCode}') {
                if ('${zgjhTypeCode}' === productTypeCode) {
                    $("#monthlySpreadBlock").show();
                    $("#interestRateLabel").show();
                    $("#interestRateContent").show();
                    $("#interestLabel").show();
                    $("#interestContent").show();
                } else if ('${cgdTypeCode}' === productTypeCode) {
                    $("#monthlySpreadBlock").show();
                    $("#interestRateLabel").hide();
                    $("#interestRateContent").hide();
                    $("#interestLabel").hide();
                    $("#interestContent").hide();
                } else {
                    $("#monthlySpreadBlock").hide();
                    $("#interestRateLabel").hide();
                    $("#interestRateContent").hide();
                    $("#interestLabel").hide();
                    $("#interestContent").hide();
                }
            }
        }

	</script>
</head>
<body>
<c:if test="${readOnly ne 'viewHistory'}">
	<!-- 申请信息 -->
	<div id="applyInfoDiv">
		<%@ include file="/WEB-INF/views/app/credit/creditViewBook/applyInfoForCredit/applyInfoForCreditForm.jsp"%>
	</div>
</c:if>
<!-- 批复信息 -->
<div id="checkApprovInfoDiv" class="searchInfo">
	<h3 onclick="pfxxClick()" class="searchTitle">批复信息</h3>
	<div id="pfxxId" class="searchCon">
		<form:form id="checkApproveForm" modelAttribute="checkApprove" action="${ctx}/credit/checkApprove/save" method="post" class="form-horizontal">
			<form:hidden path="id" />
			<input type="hidden" id="taskDefKey" value="${actTaskParam.taskDefKey}" />
			<%@ include file="/WEB-INF/views/app/credit/workflow/include/actTaskParams.jsp"%>
			<sys:message content="${message}" />
			<table class="fromTable filter">
				<pre class="pre-style" id="preRemark" style="width:820px;"></pre>
				<tr>
					<!-- 非债股结合产品，正常分类来 -->
					<c:if test="${isShowCategoryKey=='0'}">
						<td width="13%" class="ft_label ">产品类型：</td>
						<td class="ft_content ">
							<form:select path="approProductTypeCode" class="input-medium required" onchange="loadProductList();getApproLoanRepayType();monthlySpreadStatus();" cssStyle="width:164px;">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('PRODUCT_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<form:hidden path="approProductTypeName" />
							<font color="red">*</font>
						</td>
						<td width="13%" class="ft_label ">产品名称：</td>
						<td class="ft_content ">
							<form:select id="approProductId" path="approProductId" class="input-medium required" cssStyle="width:164px;" onchange="loadProductPeriod();">
								<form:option value=""></form:option>
								<form:options items="${productList }" htmlEscape="false" itemLabel="productName" itemValue="id" />
							</form:select>
							<font color="red">*</font>
							<input type="hidden" id="approProductName" name="approProductName" />
						</td>
					</c:if>


					<c:if test="${isShowCategoryKey=='1'}">
						<!-- 债股结合产品，并且不在在大区风控经理和总部风控经理节点时，此时不可以修改 -->
						<td width="13%" class="ft_label">产品类型：</td>
						<td class="ft_content">
							<form:input path="approProductTypeName" readonly="true" htmlEscape="false" class="input-medium" />
							<font color="red">*</font>
							<form:hidden path="approProductTypeCode" />
						</td>
						<td width="13%" class="ft_label">产品名称：</td>
						<td class="ft_content">
							<input type="text"  name="approProductName" readonly="true"  class="input-medium" value="${checkApprove.approProductName }"/>
								<%-- <form:input path="approProductName" readonly="true" htmlEscape="false" class="input-medium" />
                                --%><font color="red">*</font>
							<form:hidden path="approProductId" />
						</td>
					</c:if>


					<td class="ft_label">产品期限(月)：</td>
					<td class="ft_content">
						<form:select path="approPeriodId" class="input-medium  required" onchange="setPeriodValue();calculateAmount();">
							<form:option value="" label=""></form:option>
							<form:options items="${fns:getDictList('PRODUCT_PERIOD_VALUE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<form:hidden path="approPeriodValue" />
						<font style="color: red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">合同金额(元)：</td>
					<td class="ft_content">
						<form:input onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);calculateAmount();getPricedRisk('${actTaskParam.applyNo}');" path="contractAmount" maxlength="18" htmlEscape="false" class=" input-medium required" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">放款金额(元)：</td>
					<td class="ft_content">
						<form:input onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" path="loanAmount" maxlength="18" htmlEscape="false" class=" input-medium" readOnly="true" />
						<font color="red">*</font>
					</td>
					<c:if test="${actTaskParam.taskDefKey == 'utask_zgsfksh' || actTaskParam.taskDefKey == 'utask_zgsjlsh' || actTaskParam.taskDefKey == 'utask_zgszjlsh'}">
						<td class="ft_label">风险定价费率(%)：</td>
						<td class="ft_content">
							<form:input path="pricedRisk" htmlEscape="false" class=" input-medium required" readOnly="true" />
						</td>
					</c:if>
				</tr>
				<c:if test="${isShowCategoryKey=='1'}">
					<tr>
						<td class="ft_label">增资期限（月）：</td>
						<td class="ft_content">
							<form:input onkeyup="keyPress11(this);" name="addFundPeriod" onblur="this.value=intVerify(value)?value:'';calculateAmount();getPricedRisk('${actTaskParam.applyNo}');" path="addFundPeriod" maxlength="18" htmlEscape="false" class=" input-medium required" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">实际服务费（元）：</td>
						<td class="ft_content">
							<form:input path="realityServiceFee" name="realityServiceFee" onblur="calculateAmount();getPricedRisk('${actTaskParam.applyNo}');" maxlength="18" htmlEscape="false" class=" input-medium required" readOnly="true" />
							<font color="red">*</font>
						</td>
						<!-- <td class="ft_label">月息差（元）：</td> -->
						<%--<td class="ft_content">--%>
							<%--<form:hidden path="interestRateDiff"    maxlength="18" htmlEscape="false" class="input-medium required" />--%>
							<%--<!-- <font color="red">*</font> -->--%>
						<%--</td>--%>
					</tr>
				</c:if>
				<tr>
					<td class="ft_label">批复月利率(%)：</td>
					<td class="ft_content">
						<form:input path="approYearRate" htmlEscape="false" onblur="this.value=rateMoney(this.value);calculateAmount();" onkeyup="rateMax(this)" class=" money input-medium required" maxlength="5" />
						<font color="red">*</font>
					</td>
					<td class="ft_label" id="interestRateLabel" hidden="hidden">让利后月利率(%)：</td>
					<td class="ft_content" id="interestRateContent" hidden="hidden">
						<form:input path="discountInterestRate" htmlEscape="false" onblur="this.value=gdpMoney(this.value);calculateAmount();" onkeyup="gdpMax(this)" cssClass="money input-medium required" maxlength="5"/>
						<font color="red">*</font>
					</td>
					<td class="ft_label">冠E通年利率(%)：</td>
					<td class="ft_content">
						<input type="text" id="guanetongRate" name="guanetongRate" maxlength="7" htmlEscape="false" class="input-medium" readOnly="true" value="${interest}" />
						<font color="red">*</font>
					</td>
					<!-- 不可修改 -->
					<c:if test="${editCategory=='0'}">
						<td width="13%" class="ft_label">产品分类：</td>
						<td class="ft_content">
							<input type="text" id="productCategoryKey" name="productCategoryKey" class="input-medium" readonly="true" value="${approveProductCategoryKey}" />
							<form:hidden path="productCategory" />
								<%-- <input type="hidden" id="productCategoryValue" name="productCategoryValue" value="${productCategoryValue}">
	 --%>					</td>
					</c:if>
					<!-- 可转换 -->

					<c:if test="${editCategory=='1'}">
						<td width="13%" class="ft_label">产品分类：</td>
						<td class="ft_content">
							<form:select id="productCategory" path="productCategory" name="productCategory" class="input-medium required" cssStyle="width:164px;">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('product_category')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
						</td>
					</c:if>
				</tr>
				<tr>
					<td class="ft_label">服务费率(%)：</td>
					<td class="ft_content">
						<form:input path="serviceFeeRate" htmlEscape="false" class="input-medium required money" onblur="this.value=rateMoney(this.value);calculateAmount();" onkeyup="rateMax(this)" maxlength="5" />
						<font color="red">*</font>
					</td>
						<%-- <c:if test="${fns:getDictValue('special_Service_Fee_Rate','special_Service_Fee_Rate','special_Service_Fee_Rate') eq '1'}"> --%>
					<td class="ft_label">特殊服务费率(%)：</td>
					<td class="ft_content">
						<form:input path="specialServiceFeeRate" htmlEscape="false" class="input-medium money required " onblur="this.value=rateMoney(this.value);calculateAmount();" onkeyup="rateMax(this)" maxlength="5" />
						<font color="red">*</font>
					</td>
						<%-- </c:if> --%>
					<td class="ft_label">服务费收取方式：</td>
					<td class="ft_content">
						<form:select path="serviceFeeType" onchange="calculateAmount()" class="input-medium required ">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('SERVICE_FEE_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">服务费：</td>
					<td class="ft_content">
						<form:input onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);calculateAmount();" path="serviceFee" htmlEscape="false" class="input-medium" readOnly="true" maxlength="18" />
						<font color="red">*</font>
					</td>
						<%-- <c:if test="${fns:getDictValue('special_Service_Fee_Rate','special_Service_Fee_Rate','special_Service_Fee_Rate') eq '1'}"> --%>
					<td id="specialServiceFeeLabel" class="ft_label">特殊服务费：</td>
					<td id="specialServiceFeeContent" class="ft_content">
						<form:input path="specialServiceFee" readonly="true" htmlEscape="false" maxlength="300" class="input-medium " />
					</td>
						<%-- </c:if> --%>
					<td class="ft_label">服务费总计：</td>
					<td class="ft_content">
						<form:input onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);calculateAmount();" path="allServiceFee" class="input-medium" readOnly="true" maxlength="18" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">还款方式：</td>
					<td class="ft_content">
						<form:select path="approLoanRepayType" class="input-medium  required ">
							<form:option value="" label="" />
							<form:options items="${loanRepayTypeList}" itemLabel="loanRepayDesc" itemValue="loanRepayType" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
					<td class="ft_label">保证金率(%)：</td>
					<td class="ft_content">
						<form:input path="marginRate" htmlEscape="false" class="input-medium  required"  maxlength="30"
									onblur="this.value=gdpMoney(this.value);calculateAmount();"
									onkeyup="gdpMax(this)"/>
						<font color="red">*</font>
					</td>
					<td class="ft_label">保证金(元)：</td>
					<td class="ft_content">
						<form:input path="marginAmount" htmlEscape="false" class="input-medium" maxlength="18" readOnly="true"
									onblur="this.value=outputmoney(this.value);" onkeyup="keyPress11(this);" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr id="monthlySpreadBlock" hidden="hidden">
					<td class="ft_label">保证金月息差(元)：</td>
					<td class="ft_content">
						<form:input path="interestRateDiff" htmlEscape="false" class="input-medium" maxlength="18"  readOnly="true"
									onblur="this.value=outputmoney(this.value);calculateAmount();" onkeyup="keyPress11(this)"/>
					</td>
					<td class="ft_label" id="interestLabel" hidden="hidden">利息月息差(元)：</td>
					<td class="ft_content" id="interestContent" hidden="hidden">
						<form:input path="interestMonthlySpread" htmlEscape="false" class="input-medium" maxlength="18"  readOnly="true"
									onblur="this.value=outputmoney(this.value);calculateAmount();" onkeyup="keyPress11(this)"/>
					</td>
				</tr>
				<c:if test="${checkApprove.qualityServiceMarginRate != null }">
					<tr>
						<td class="ft_label">质量服务担保费率(%)：</td>
						<td class="ft_content">
							<form:input path="qualityServiceMarginRate" htmlEscape="false" class="input-medium  required" onblur="this.value=gdpMoney(this.value);calculateAmount();" onkeyup="gdpMax(this)" maxlength="5" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">质量服务保证金：</td>
						<td class="ft_content">
							<form:input onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" path="qualityServiceMarginAmount" maxlength="18" htmlEscape="false" class="input-medium" readOnly="true" />
							<font color="red">*</font>
						</td>
					</tr>
				</c:if>
				<tr>
					<td class="ft_label">外访费：</td>
					<td class="ft_content">
						<form:input onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" path="checkFee" maxlength="18" htmlEscape="false" readonly="true" class="input-medium" />
					</td>

				</tr>

				<tr>

					<td class="ft_label">借款模式：</td>
					<td class="ft_content">
						<form:select path="loanModel" class="input-medium  required ">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('LOAN_MODEL')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
					<td class="ft_label">是否加急：</td>
					<td class="ft_content">
						<form:select path="isUrgent" class="input-medium  required ">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
				</tr>

				<tr class="cgisShow">
					<td class="ft_label">上游供应商名称：</td>
					<td class="ft_content" colspan="4">
						<input type="text" name="topShopName" id="topShopName"  class="input-medium required" maxlength="60" value="${checkApprove.topShopName }" style="margin: 0px; position: relative; display: inline-block; vertical-align: middle; width: 540px"  />
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="cgisShow">
					<td class="ft_label">下游采购商名称：</td>
					<td class="ft_content" colspan="4">
						<input type="text" name="downShopName" id="downShopName"  class="input-medium required" maxlength="60" value="${checkApprove.downShopName }" style="margin: 0px; position: relative; display: inline-block; vertical-align: middle; width: 540px"  />
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="cgisShow">
					<td class="ft_label">上游供应商返利(%)：</td>
					<td class="ft_content">
						<form:input path="topShopBackRate" htmlEscape="false" class="input-medium  required" onblur="this.value=gdpMoney(this.value);calculateAmount();" onkeyup="gdpMax(this)" maxlength="5" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">上游返利总额(元)：</td>
					<td class="ft_content">
						<form:input path="topShopBackMoney" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);"  maxlength="18" htmlEscape="false" class="input-medium required" readOnly="true"/>
						<font color="red">*</font>
					</td>
					<td class="ft_label">实际月利率(%)：</td>
					<td class="ft_content">
						<form:input path="topShopMonthRate" htmlEscape="false" class="input-medium  required" onblur="this.value=gdpMoney(this.value);" onkeyup="gdpMax(this)" maxlength="5" readOnly="true"/>
						<font color="red">*</font>
					</td>
				</tr>
				<tr class="cgisShow">
					<td class="ft_label">居间服务费(元)：</td>
					<td class="ft_content">
						<form:input path="mediacyServiceFee" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);calculateAmount();"  maxlength="18" htmlEscape="false" class="input-medium required"  />
						<font color="red">*</font>
					</td>
					<%--<c:if test="${isShowCategoryKey=='0'}">--%>
						<%--<td class="ft_label">月息差(元)：</td>--%>
						<%--<td class="ft_content">--%>
							<%--<form:input path="interestRateDiff" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);calculateAmount();"  maxlength="18" htmlEscape="false" class=" input-medium required" readOnly="true" />--%>
							<%--<font color="red">*</font>--%>
						<%--</td>--%>
					<%--</c:if>--%>
				</tr>

				<tr class="cgisShow">
					<td class="ft_label">采购商品信息：</td>
				</tr>
				<tr class="cgisShow">
					<td colspan="6">
						<!-- 显示采购信息 -->
						<div id="purchaseInfoListDiv"></div>
					</td>
				</tr>
				<tr>
					<td class="ft_label">备注：</td>
					<td class="ft_content" colspan="6">
						<form:textarea path="remark" htmlEscape="false" rows="4" maxlength="1500" class="textarea-style required" style="width:820px;" placeholder="此项填写为小黄表备注项" onkeyup="adjustTextareaLength('remark','preRemark')" />
						<font color="red">*</font>
					</td>
				</tr>
			</table>
			<c:if test="${actTaskParam.status eq 0}">
				<shiro:hasPermission name="credit:checkApprove:edit">
					<div class="searchButton" id="buttonDiv">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />
						<input id="viewApproveHistory" class="btn btn-primary" style="width:15%" type="button" onclick="viewHistory()" value="查看历史批复信息" />
					</div>
				</shiro:hasPermission>
			</c:if>
			<c:if test="${actTaskParam.status eq 1}">
				<div style="float:right">
					<input id="viewApproveHistoryDone" class="btn btn-primary " style="font-size:12px;width:150px" type="button" onclick="viewHistory()" value="查看历史批复信息" />
				</div>
			</c:if>
		</form:form>
	</div>
</div>
<!-- 还款计划 -->
<div id="repayPlanDiv"></div>
<c:if test="${readOnly ne 'viewHistory'}">
	<!-- 审批结论 -->
	<div id="isHideSuggestionDiv">
		<%@ include file="/WEB-INF/views/app/credit/processSuggestionInfo/checkApproveConclusionForm.jsp"%>
	</div>
</c:if>
</body>
</html>