<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>合同信息管理</title>
    <meta name="decorator" content="default" />
    <script type="text/javascript">
        $(document).ready(function() {
            var productCategoryKey = '${productCategoryKey}';
            if(productCategoryKey==null||productCategoryKey==''){//不是债股结合产品
                $(".ZGCategory").hide();//隐藏不可编辑的分类
            }else{//是债股结合的产品
                $(".ZGCategory").show();
                $(".classInterestRate").hide();
            }
            var showCgFlag='${showCgFlag}';
            if(showCgFlag==1){
                $(".cgisShow").show();
                $.loadDiv("purchaseInfoListDiv", "${ctx }/credit/purchaseInfo/list", {
                    applyNo : '${actTaskParam.applyNo}',
                    taskDefKey : '${actTaskParam.taskDefKey}'
                }, "post");
            }else{
                $(".cgisShow").hide();
            }

            if('${oldMessage}' == "1"){
                $("td[name='showMessageName']").show();
                isOrNoShow("yes");
            }
            if('${oldTextFlag}' == "1"){
                $("#oldTextTrId").show();
            }
            isOrNoMiddleMan("${contract.isOrNoMIddleMan}");
            if ("${contract.contractNo}" == null || "${contract.contractNo}" == "") {
                $("#contractTmp").hide();
            }
            document.getElementById("applyAmount").value = outputmoney("${applyInfo.applyAmount}");
            if(document.getElementById("qualityServiceMarginAmount") != null){
                document.getElementById("qualityServiceMarginAmount").value = outputmoney("${checkApprove.qualityServiceMarginAmount}");
            }
            document.getElementById("contractAmount").value = outputmoney("${checkApprove.contractAmount}");
            document.getElementById("loanAmount").value = outputmoney("${checkApprove.loanAmount}");
            document.getElementById("marginAmount").value = outputmoney("${checkApprove.marginAmount}");
            document.getElementById("checkFee").value = outputmoney("${checkApprove.checkFee}");
            document.getElementById("serviceFee").value = outputmoney("${checkApprove.serviceFee}");
            if (document.getElementById("specialServiceFee")) {
                document.getElementById("specialServiceFee").value = outputmoney("${checkApprove.specialServiceFee}");
            }
            document.getElementById("allServiceFee").value = outputmoney("${checkApprove.allServiceFee}");

            document.getElementById("factLoanAmount").value = outputmoney("${checkApprove.loanAmount}");

            if ('${checkApprove.serviceFeeType}' == "1") {
                if('${contract.qualityServiceMarginAmount}' == null || '${checkApprove.qualityServiceMarginAmount}' == "" ){
                    document.getElementById("receiveAmount").value = outputmoney("${checkApprove.marginAmount + checkApprove.allServiceFee}");
                }else{
                    document.getElementById("receiveAmount").value = outputmoney("${checkApprove.marginAmount + checkApprove.allServiceFee+ checkApprove.qualityServiceMarginAmount}");
                }
            } else {
                if('${checkApprove.qualityServiceMarginAmount}' == null || '${checkApprove.qualityServiceMarginAmount}' == "" ){
                    document.getElementById("receiveAmount").value = outputmoney("${checkApprove.marginAmount }");
                }else{
                    document.getElementById("receiveAmount").value = outputmoney("${checkApprove.marginAmount + checkApprove.qualityServiceMarginAmount}");
                }
            }
            $("#openAccountStatus").prop("disabled", true).select2();
            getInfo();
            adjustTextareaLength('approResult', 'approResultPre');
            adjustTextareaLength('description', 'descriptionPre');
            adjustTextareaLength('signSuggestion', 'signSuggestionPre');
            adjustTextareaLength('suggestionDesc', 'suggestionDescPre');
            adjustTextareaLength('suggestionDesc1', 'suggestionDesc1Pre');
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

            $("#inputForm2").validate({
                submitHandler : function(form) {
                    // 			var contractId = $("form[id='contractForm'] input[id='id']").val();
                    if ($("form[id='inputForm2'] input[name='passFlag']:checked").val() == 'yes' && $("#submitForm2Flag").val() == "") {
                        alertx("请先保存合同信息！");
                    }else if($("form[id='inputForm2'] input[name='passFlag']:checked").val() == "yes"){
                        var message = fuc();
                        var comAccount = comAccpunt();
                        message=message.substring(0,message.length-1);
                        comAccount=comAccount.substring(0,comAccount.length-1);
                        /* if($("#comAccount").val() == null || $("#comAccount").val() == ''){
                            alertx("<font style='color:red'>提交失败，此客户未开立资金账户 ，请通知客户尽快开立资金账户</font>");
                        } else if(message != ''){
                         alertx("<font style='color:red'>提交失败，收款人信息："+message+"与开户信息不一致</font>");
                         }else */ if($("#gedunSocCreditNo").val() != $("#gesocialCreditCode").val()){
                            alertx("<font style='color:red'>提交失败，此客户未填写企业开户信息 ，请通知客户尽快完善开户信息</font>");
                        }else if(comAccount != ''){
                            alertx("<font style='color:red'>提交失败，企业开户信息："+comAccount+"与企业信息不一致</font>");
                        }else{
                            $.post("${ctx}/credit/contract/checkIsConfirm?applyNo="+'${actTaskParam.applyNo}',null,
                                function(data) {
                                    if (data) {
                                        if (data.status == '1') {
                                            loading('正在提交，请稍等...');
                                            form.submit();
                                        } else {
                                            alertx(data.message);
                                        }
                                    }
                                });
                        }

                    } else {
                        loading('正在提交，请稍等...');
                        form.submit();
                        /*$.post("${ctx}/credit/contract/checkIsConfirm?applyNo="+'${actTaskParam.applyNo}',null,
                            function(data) {
                                if (data) {
                                    if (data.status == '1') {
                                        loading('正在提交，请稍等...');
                                        form.submit();
                                    } else {
                                        alertx(data.message);
                                    }
                                }
                            });*/
                    }

                },
                errorContainer : "#messageBox",
                errorPlacement : function(error, element) {
                    checkReq(error, element);
                }
            });

            var fuc=function(){
                var message = "";
                if($("#comAccount").val() != $("#recBankcardNo").val()){
                    message = message+"银行卡号、";
                }

                if($("#legalPerName").val() != $("#s2id_recBankcardName a span:first-child").text()){
                    message = message + "法人姓名、";
                }
                if($("#legalPerNum").val() != $("#recBankIdNum").val()){
                    message = message + "法人身份证号码、";
                }
                if($("#legalPerPhone").val() != $("#recBankMobile").val()){
                    message = message +"法人电话号码、";
                }
                if($("#companyBankOfDeposit").val() != $("#s2id_recBank a span:first-child").text()){

                    message =message + "开户行、";
                }
                if($("#companyBankBranchName").val() != $("#recBankName").val()){

                    message = message +"开户支行名称、";
                }
                if($("#provinceName").val() != $("#s2id_recBankProvince a span:first-child").text()){
                    if(!($("#provinceName").val() == "北京市" && $("#s2id_recBankProvince a span:first-child").text() == "北京")){
                        message =message + "开户省、";
                    }

                }
                if($("#cityName").val() != $("#s2id_recBankCity a span:first-child").text()){
                    if(!($("#cityName").val() == "市辖区" && $("#s2id_recBankCity a span:first-child").text() == "北京市")){
                        message =message + "开户市、";
                    }


                }
                return message;
            }

            var comAccpunt = function(){
                var com = "";

                if($("#gedbusiRegName").val() != $("#gecompanyName").val()){
                    com =com + "企业名称、";
                }
                if($("#gedunSocCreditNo").val() != $("#gesocialCreditCode").val()){
                    com =com + "企业统一社会信用代码、";
                }
                if($("#gedcorporationName").val() != $("#gelegalName").val()){
                    com =com + "企业法人名称、";
                }
                if($("#gedcorporationCardIdNo").val() != $("#gelegalCardNumber").val()){
                    com =com + "企业法人身份证号码、";
                }
                if($("#gedcorporationMobile").val() != $("#gelegalMobile").val()){
                    com =com + "企业法人联系方式、";
                }
                return com;
            }

            $("#inputForm3").validate({
                submitHandler : function(form) {
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer : "#messageBox",
                errorPlacement : function(error, element) {
                    checkReq(error, element);
                }
            });

            $("#inputForm4").validate({
                submitHandler : function(form) {
                    var passFlag = $("input[name='passFlag']:checked").val();
                    if(passFlag == "yes" && '${oldMessage}' == "1"){
                        confirmx("此进件涉及合同号变更，请再次确认合同号是否正确", function() {//校验签约
                        	$.post("${ctx}/credit/contract/checkIsSign?applyNo="+'${actTaskParam.applyNo}',null,
                                    function(data) {
                                        if (data) {
                                            if (data.status == '1') {
                                                loading('正在提交，请稍等...');
                                                form.submit();
                                            } else {
                                                alertx(data.message);
                                            }
                                        }
                              });
                        });
                    }else{
                    	if(passFlag == "yes"){//校验签约
                    		$.post("${ctx}/credit/contract/checkIsSign?applyNo="+'${actTaskParam.applyNo}',null,
                                    function(data) {
                                        if (data) {
                                            if (data.status == '1') {
                                                loading('正在提交，请稍等...');
                                                form.submit();
                                            } else {
                                                alertx(data.message);
                                            }
                                        }
                              });
                    	}else{
                    		loading('正在提交，请稍等...');
                            form.submit(); 
                    	}
                    }
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

            /* new CitySelect({
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
             }); */

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
            //getCustData();
            loadBankCardInfo();
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
        });

        function isOrNoShow(value){
            if(value == "yes"){
                $("#showMessageTrId").show();
            }else{
                $("#showMessageTrId").hide();
            }
        }

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
            var applyAmount = $("#applyAmount").val().replace(/,/g, "");
            $("#applyAmount").val(applyAmount);

            //合同金额(元)
            var contractAmount = $("#contractAmount").val().replace(/,/g, "");
            $("#contractAmount").val(contractAmount);

            //质量服务保证金(元)
            if($("#qualityServiceMarginAmount").val() != null){
                var qualityServiceMarginAmount = $("#qualityServiceMarginAmount").val().replace(/,/g, "");
                $("#qualityServiceMarginAmount").val(qualityServiceMarginAmount);
            }
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

        function isOrNoMiddleMan(value) {
            if ("1" == value) {
                $("#middlemanInfoDiv").show();
                getMiddleInfo();
            } else {
                $("#middlemanInfoDiv").hide();
                $("#middlemanId").select2("val", "");
                $("#middlemanName").val("");
                $("#middlemanIdNum").val("");
                $("#middlemanMobileNum").val("");
                $("#middlemanBankcardNo").val("");
                $("#middlemanBankNo").val("");
                $("#middlemanBankDetailName").val("");
                $("#middlemanContDistinctName").val("");
                $("#middlemanContDistinct").val("");
                $("#middlemanCapTerNo").val("");
            }
        }

        //根据中间人的id获取其他信息
        function getMiddleInfo() {
            var middlemanValue = $("#middlemanId").val();
            if (middlemanValue == "") {
                $("#middlemanName").val("");
                $("#middlemanIdNum").val("");
                $("#middlemanMobileNum").val("");
                $("#middlemanBankcardNo").val("");
                $("#middlemanBankNo").val("");
                $("#middlemanBankDetailName").val("");
                $("#middlemanContDistinctName").val("");
                $("#middlemanContDistinct").val("");
                $("#middlemanCapTerNo").val("");
            } else {
                $.post("${ctx}/credit/mortgagedperson/middlePersonSelected", {
                    "id" : middlemanValue
                }, function(data) {
                    $("#middlemanName").attr("value", data.mortgageeName);
                    $("#middlemanIdNum").attr("value", data.idNum);
                    $("#middlemanMobileNum").attr("value", data.mobileNum);
                    $("#middlemanBankcardNo").attr("value", data.bankcardNo);
                    $("#middlemanBankNo").attr("value", data.bankNo);
                    $("#middlemanBankDetailName").attr("value", data.bankDetailName);
                    $("#middlemanContDistinctName").attr("value", data.contDistinct);
                    $("#middlemanContDistinct").attr("value", data.contDistinctCode);
                    $("#middlemanCapTerNo").attr("value", data.capitalTerraceNo);
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
            /* if ($("#recBankcardNo").val() != $("#recBankcardNoConfirm").val()) {
             alertx("收款银行卡号不一致！");
             return false;
             }
             //校验还款银行卡号是否一致
             if ($("#repBankcardNo").val() != $("#repBankcardNoConfirm").val()) {
             alertx("还款银行卡号不一致！");
             return false;
             } */

            return true;

        }

        function isLoadMortgagedPerson() {
            if ('${checkApprove.approProductTypeCode}' == '1' || '${checkApprove.approProductTypeName}' == '信用') {
                $("#mortgageInfoDiv").hide();
            } else {
                $("#mortgageInfoDiv").show();
            }
        }
    </script>
    <script type="text/javascript">
        $(document).ready(function() {
            //还款计划表
            $.loadDiv("repayPlanList", "${ctx }/credit/repayPlan/getRepayPlanByContractNo", {
                applyNo : "${actTaskParam.applyNo}",
                taskDefKey : "utask_htmq"
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
        function gedqygrClick(){
            $("#gedqygrId").toggle(600);
        }
        //根据客户id查询手机号和证件号
        function getCustData() {
            var custId = $("#recBankcardName").val();
            if (custId == "" || custId == null) {
                $("#recBankMobile").val("");
                $("#recBankIdNum").val("");
                $("#repBankIdNum").val("");
                $("#repBankMobile").val("");
            } else {
                $.ajax({
                    url : "${ctx}/credit/contract/getCustData",
                    type : "post",
                    data : {
                        custId : custId
                    },
                    dataType : "json",
                    success : function(data) {
                        if (data.status == "1") {
                            $("#recBankMobile").val(data.phone);
                            $("#recBankIdNum").val(data.idNum);
                            $("#repBankIdNum").val($("#recBankIdNum").val());
                            $("#repBankMobile").val($("#recBankMobile").val());
                        } else {
                            alertx(data.message);
                        }
                    },
                    error : function(msg) {
                        alertx("未能完成操作，请查看后台信息");
                    }
                });
            }
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
                //$("#repBankcardName").val($("#recBankcardName").val());
                //$("#repBankcardName").val($("#s2id_recBankcardName>.select2-choice>.select2-chosen").html());
                $("#repBankName").val($("#recBankName").val());
                //$("#repBankMobile").val($("#recBankMobile").val());
                //$("#repBankIdNum").val($("#recBankIdNum").val());
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
            var url = "${ctx}/credit/contractTemplate/printSelect?contractNo=" + contractNo + "&applyNo=" + '${applyInfo.applyNo}';
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
            var middlemanValue = $("#middlemanId").val();
            var flag = $("#isOrNoMIddleMan").val();
            if (flag == "1" && (checkIsNull(middlemanValue) || middlemanValue == "")) {
                alertx("请选择中间人");
            } else {
                loading();
                top.$.jBox.tip.mess = null;
                var formJson = $("#contractForm").serializeJson();
                $.post("${ctx}/credit/contract/save", formJson, function(data) {
                    if (data) {
                        closeTip();
                        if (data.status == 1) {//保存成功
                            var contractId = data.id;
                            $("form[id='contractForm'] input[id='id']").val(contractId);
                            alertx(data.message, function() {
                                $("#submitForm2Flag").val("true");
                                $("#contractTmp").show();
                                $("#contractNo").val(data.contractNo);
                                $("#conStartDate").attr("value", data.conStartDate);
                                $("#conEndDate").attr("value", data.conEndDate);
                                $.loadDiv("repayPlanList", "${ctx }/credit/repayPlan/getRepayPlanByContractNo", {
                                    applyNo : data.applyNo,
                                    taskDefKey : "utask_htmq"
                                }, "post");
                            });
                        } else {
                            alertx(data.message);
                        }
                    }
                });
            }
        }
    </script>
    <!-- 显示冠E贷账户信息提示 -->
    <%-- <c:if test="${empty companyAmount}">
        <script type="text/javascript">
        $("#companyAccountMessage").val("13213");
            alertx("<font style='color:red'>你好</font>");
        </script>
    </c:if> --%>
</head>
<body>
<div id="readOnlyDiv">
    <form:form id="contractForm" modelAttribute="contract" action="${ctx}/credit/contract/save" method="post" class="form-horizontal">
        <a id="contractSignInfoSkipId" target="_parent"></a>
        <form:hidden path="applyNo" value="${applyInfo.applyNo}" />
        <input type="hidden" id="taskId" name="taskId" value="${actTaskParam.taskId}" />
        <input type="hidden" id="submitForm2Flag" name="submitForm2Flag" value="" />
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
        <input type="hidden" id="approAmount" name="approAmount" value="${checkApprove.approAmount}" />
        <c:if test="${returnFlag eq 'true' && readOnly eq 'false'}">
            <div id='messageBox' class='alert alert-error'>
                <font>请注意：该任务为被打回任务!</font>
            </div>
        </c:if>
        <form:hidden path="id" />
        <div class="searchInfo">
            <h3 onclick="contractClick()" class="searchTitle">申请信息</h3>
            <div id="contractId" class="searchCon">
                <table class="fromTable filter">
                    <tr>
                        <td class="ft_label">客户名称：</td>
                        <td class="ft_content">
                            <input type="text" id="custName" name="custName" class="input-medium" readonly="true" value="${applyInfo.applyRegister.custName}" />
                            <input type="hidden" id="custId" name="custId" class="input-medium" readonly="true" value="${applyInfo.applyRelation.custId}" />
                        </td>
                        <td class="ft_label">证件类型：</td>
                        <td class="ft_content">
                            <input type="text" class="input-medium" readonly="true" value="${applyInfo.applyRegister.idTypeLabel}" />
                            <input type="hidden" id="idType" name="idType" class="input-medium" readonly="true" value="${applyInfo.applyRegister.idType}" />
                        </td>
                        <td class="ft_label">证件号码：</td>
                        <td class="ft_content">
                            <input type="text" id="idNum" name="idNum" class="input-medium" readonly="true" value="${applyInfo.applyRegister.idNum}" />
                        </td>
                    </tr>
                    <tr>
                        <td class="ft_label">企业名称：</td>
                        <td class="ft_content">
                            <input type="text" id="companyName" class="input-medium" readonly="true" value="${applyInfo.applyRegister.companyName}" />
                        </td>
                        <td class="ft_label">企业证件类型：</td>
                        <td class="ft_content">
                            <input type="text" id="comIdType" class="input-medium" readonly="true" value="${applyInfo.applyRegister.comIdTypeLabel}" />
                        </td>
                        <td class="ft_label">企业证件号：</td>
                        <td class="ft_content">
                            <input type="text" id="comIdNum" class="input-medium" readonly="true" value="${applyInfo.applyRegister.comIdNum}" />
                        </td>
                    </tr>
                    <tr>
                        <td class="ft_label">产品类型：</td>
                        <td class="ft_content">
                            <input type="text" id="applyProductTypeName" name="applyProductTypeName" class="input-medium" readonly="true" value="${applyInfo.applyRegister.applyProductTypeLabel}" />
                            <input type="hidden" id="applyProductTypeCode" name="applyProductTypeCode" class="input-medium" readonly="true" value="${applyInfo.applyRegister.applyProductTypeCode}" />
                        </td>
                        <td class="ft_label">产品名称：</td>
                        <td class="ft_content">
                            <input type="text" id="applyProductName" name="applyProductName" class="input-medium" readonly="true" value="${applyInfo.applyRegister.applyProductName}" />
                            <input type="hidden" id="applyProductId" name="applyProductId" class="input-medium" readonly="true" value="${applyInfo.applyRegister.applyProductId}" />
                        </td>
                        <td class="ft_label">产品期限(月)：</td>
                        <td class="ft_content">
                            <input type="text" id="applyPeriodValue" name="applyPeriodValue" class="input-medium" readonly="true" value="${applyInfo.applyPeriodValue}" />
                        </td>
                    </tr>
                    <tr>
                        <td class="ft_label">申请利率(%)：</td>
                        <td class="ft_content">
                            <input type="text" id="applyYearRate" name="applyYearRate" onblur="this.value=rateMoney(this.value);" onkeyup="rateMax(this)" maxlength="5" class="input-medium number" readonly="true" value="${applyInfo.applyYearRate}" />
                            <span class="help-inline"> </span>
                        </td>
                        <td class="ft_label">申请服务费率(%)：</td>
                        <td class="ft_content">
                            <input type="text" id="applyServiceFeeRate" name="applyServiceFeeRate" onblur="this.value=rateMoney(this.value);" onkeyup="rateMax(this)" maxlength="5" class="input-medium number" readonly="true" value="${applyInfo.applyServiceRate}" />
                            <span class="help-inline"> </span>
                        </td>
                        <td class="ft_label">申请金额(元)：</td>
                        <td class="ft_content">
                            <input type="text" class="input-medium" id="applyAmount" name="applyAmount" readonly="true" value="${not empty applyInfo.applyAmount ? applyInfo.applyAmount : applyRegister.applyAmount}" />
                        </td>
                    </tr>
                    <tr>
                        <td width="13%" class="ft_label ZGCategory">产品分类：</td>
                        <td class="ft_content ZGCategory">
                            <input type="text" id="productCategoryKey" name="productCategoryKey" class="input-medium" readonly="true" value="${productCategoryKey}" />
                        </td>
                    </tr>
                </table>
            </div>
        </div>
        <div id="approveInfoDiv" class="searchInfo">
            <h3 onclick="pfxxClick()" class="searchTitle">批复信息</h3>
            <div id="pfxxId" class="searchCon">
                <table class="fromTable filter">
                    <tr>
                        <input type="hidden" id="approId" name="approId" value="${checkApprove.id}" />
                        <td class="ft_label">产品类型：</td>
                        <td class="ft_content">
                            <input type="text" id="approProductTypeName" name="approProductTypeName" class="input-medium" readonly="true" value="${checkApprove.approProductTypeName}" />
                            <input type="hidden" id="approProductTypeCode" name="approProductTypeCode" class="input-medium" readonly="true" value="${checkApprove.approProductTypeCode}" />
                        </td>
                        <td class="ft_label">产品名称：</td>
                        <td class="ft_content">
                            <input type="text" id="approProductName" name="approProductName" class="input-medium" readonly="true" value="${checkApprove.approProductName}" />
                            <input type="hidden" id="approProductId" name="approProductId" class="input-medium" readonly="true" value="${checkApprove.approProductId}" />
                        </td>
                        <td width="13%" class="ft_label ZGCategory">产品分类：</td>
                        <td class="ft_content ZGCategory">
                            <input type="text" id="approveProductCategoryKey" name="approveProductCategoryKey" class="input-medium" readonly="true" value="${approveProductCategoryKey}" />
                        </td>
                    </tr>
                    <tr>
                        <td class="ft_label">合同金额(元)：</td>
                        <td class="ft_content">
                            <input type="text" id="contractAmount" name="contractAmount" class="input-medium" readonly="true" value="${checkApprove.contractAmount}" />
                        </td>
                        <td class="ft_label">放款金额(元)：</td>
                        <td class="ft_content">
                            <input type="text" id="loanAmount" name="loanAmount" class="input-medium" readonly="true" value="${checkApprove.loanAmount}" />
                        </td>
                        <td class="ft_label">期限：</td>
                        <td class="ft_content">
                            <input type="text" id="approPeriodValue" name="approPeriodValue" class="input-medium" readonly="true" value="${checkApprove.approPeriodValue}" />
                        </td>
                    </tr>
                    <tr class="ZGCategory">
                        <td class="ft_label">增资期限（月）：</td>
                        <td class="ft_content">
                            <input type="text" name="addFundPeriod" value="${checkApprove.addFundPeriod}" maxlength="18" htmlEscape="false" class="input-medium" />
                            <font color="red">*</font>
                        </td>
                        <td class="ft_label">实际服务费（元）：</td>
                        <td class="ft_content">
                            <input type="text" value="${checkApprove.realityServiceFee}" maxlength="18" htmlEscape="false" class="input-medium" readOnly="true" />
                            <font color="red">*</font>
                        </td>
                        <%--<td class="ft_label classInterestRate">月息差（元）：</td>--%>
                        <%--<td class="ft_content classInterestRate">--%>
                            <%--<input type="text" name="interestRateDiff" value="${checkApprove.interestRateDiff }" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);calculateAmount();"  maxlength="18" htmlEscape="false" class=" input-medium required" readOnly="true" />--%>
                            <%--<font color="red">*</font>--%>
                        <%--</td>--%>
                    </tr>
                    <tr>
                        <td class="ft_label">批复月利率(%)：</td>
                        <td class="ft_content">
                            <input type="text" id="approYearRate" onblur="this.value=rateMoney(this.value);" onkeyup="rateMax(this)" maxlength="5" name="approYearRate" class="input-medium" readonly="true" value="${checkApprove.approYearRate}" />
                            <span class="help-inline"> </span>
                        </td>
                        <c:if test="${flowCode == '1'}">
                            <td class="ft_label">让利后月利率(%)：</td>
                            <td class="ft_content">
                                <input type="text" id="discountInterestRate" name="discountInterestRate" maxlength="5" readonly="readonly" value="${checkApprove.discountInterestRate}" onblur="this.value=rateMoney(this.value);" onkeyup="rateMax(this)" />
                            </td>
                        </c:if>
                    </tr>
                    <tr>
                        <td class="ft_label">服务费率(%)：</td>
                        <td class="ft_content">
                            <input type="text" id="serviceFeeRate" onblur="this.value=rateMoney(this.value);" onkeyup="rateMax(this)" maxlength="5" name="serviceFeeRate" class="input-medium" readonly="true" value="${checkApprove.serviceFeeRate}" />
                            <span class="help-inline"> </span>
                        </td>
                            <%-- <c:if test="${fns:getDictValue('special_Service_Fee_Rate','special_Service_Fee_Rate','special_Service_Fee_Rate') eq '1'}"> --%>
                        <td class="ft_label">特殊服务费率(%)：</td>
                        <td class="ft_content">
                            <input type="text" id="specialServiceFeeRate" onblur="this.value=rateMoney(this.value);" onkeyup="rateMax(this)" maxlength="5" name="specialServiceFeeRate" class="input-medium" readonly="true" value="${checkApprove.specialServiceFeeRate}" />
                            <span class="help-inline"> </span>
                        </td>
                            <%-- </c:if> --%>
                        <td class="ft_label">服务费收取方式：</td>
                        <td class="ft_content">
                            <input type="text" class="input-medium" readonly="true" value="${checkApprove.serviceFeeTypeLabel}" />
                            <input type="hidden" id="serviceFeeType" name="serviceFeeType" value="${checkApprove.serviceFeeType}" />
                        </td>
                    </tr>
                    <tr>
                        <td class="ft_label">服务费：</td>
                        <td class="ft_content">
                            <input type="text" id="serviceFee" name="serviceFee" class="input-medium" readonly="true" value="${checkApprove.serviceFee}" />
                        </td>
                            <%-- <c:if test="${fns:getDictValue('special_Service_Fee','special_Service_Fee','special_Service_Fee') eq '1'}"> --%>
                        <td class="ft_label">特殊服务费：</td>
                        <td class="ft_content">
                            <input type="text" id="specialServiceFee" name="specialServiceFee" class="input-medium" readonly="true" value="${checkApprove.specialServiceFee}" />
                        </td>
                            <%-- </c:if> --%>
                        <td class="ft_label">服务费总计：</td>
                        <td class="ft_content">
                            <input type="text" id="allServiceFee" name="allServiceFee" class="input-medium" readonly="true" value="${checkApprove.allServiceFee}" />
                        </td>
                    </tr>
                    <tr>
                        <td class="ft_label">还款方式：</td>
                        <td class="ft_content">
                            <input type="text" class="input-medium" readonly="true" value="${approLoanRepayTypeName}" />
                            <input type="hidden" id="approLoanRepayType" name="approLoanRepayType" value="${checkApprove.approLoanRepayType}" />
                        </td>
                        <td class="ft_label">保证金率（%）：</td>
                        <td class="ft_content">
                            <input type="text" id="marginRate" onblur="this.value=rateMoney(this.value);" onkeyup="rateMax(this)" maxlength="5" name="marginRate" class="input-medium" readonly="true" value="${checkApprove.marginRate}" />
                        </td>
                        <td class="ft_label">保证金（元）：</td>
                        <td class="ft_content">
                            <input type="text" id="marginAmount" name="marginAmount" class="input-medium" readonly="true" value="${checkApprove.marginAmount}" />
                        </td>
                    </tr>
                    <c:if test="${flowCode == '2' || flowCode == '1'}">
                        <tr>
                            <td class="ft_label">保证金月息差(元)：</td>
                            <td class="ft_content">
                                <input type="text" id="interestRateDiff" name="interestRateDiff" class="input-medium" readonly="readonly" value="${checkApprove.interestRateDiff}" />
                            </td>
                            <c:if test="${flowCode == '1'}">
                                <td class="ft_label">利息月息差(元)：</td>
                                <td class="ft_content">
                                    <input type="text" id="interestMonthlySpread" name="interestMonthlySpread" class="input-medium" readonly="readonly" value="${checkApprove.interestMonthlySpread}" />
                                </td>
                            </c:if>
                        </tr>
                    </c:if>
                    <c:if test="${checkApprove.qualityServiceMarginRate != null }">
                        <tr>
                            <td class="ft_label">质量服务保证金率(%)：</td>
                            <td class="ft_content">
                                <input type="text" name="qualityServiceMarginRate" htmlEscape="false" class="input-medium " value="${checkApprove.qualityServiceMarginRate}" />
                            </td>
                            <td class="ft_label">质量服务保证金：</td>
                            <td class="ft_content">
                                <input type="text" id="qualityServiceMarginAmount" name="qualityServiceMarginAmount" maxlength="18" value="${checkApprove.qualityServiceMarginAmount}" class="input-medium" readOnly="true" />
                            </td>
                        </tr>
                    </c:if>
                    <tr>
                        <td class="ft_label">外访费：</td>
                        <td class="ft_content">
                            <input type="text" id="checkFee" name="checkFee" class="input-medium" readonly="true" value="${checkApprove.checkFee}" />
                        </td>
                        <td class="ft_label">贷款模式：</td>
                        <td class="ft_content">
                            <input type="text" class="input-medium" readonly="true" value="${checkApprove.loanModelLabel}" />
                            <input type="hidden" id="loanModel" name="loanModel" value="${checkApprove.loanModel}" />
                        </td>
                        <td name="showMessageName" style="display: none;" class="ft_label">是否显示变更信息：</td>
                        <td name="showMessageName"  style="display: none;" class="ft_content">
                            <input type="radio" name="showFlag" value="yes" id="radio_yes" checked="checked"  onclick="isOrNoShow(this.value)">
                            <label for="radio_yes">是</label>
                            <input type="radio" name="showFlag" value="no" id="radio_no"  onclick="isOrNoShow(this.value)">
                            <label for="radio_no">否</label>
                        </td>
                    </tr>


                    <tr class="cgisShow">
                        <td class="ft_label">上游供应商名称：</td>
                        <td class="ft_content" colspan="4">
                            <input type="text" name="topShopName" id="topShopName" readOnly="true" class="input-medium required" maxlength="60" value="${checkApprove.topShopName }" style="margin: 0px; position: relative; display: inline-block; vertical-align: middle; width: 540px"  />
                            <font color="red">*</font>
                        </td>
                    </tr>
                    <tr class="cgisShow">
                        <td class="ft_label">下游采购商名称：</td>
                        <td class="ft_content" colspan="4">
                            <input type="text" name="downShopName" id="downShopName" readOnly="true"  class="input-medium required" maxlength="60" value="${checkApprove.downShopName }" style="margin: 0px; position: relative; display: inline-block; vertical-align: middle; width: 540px"  />
                            <font color="red">*</font>
                        </td>
                    </tr>
                    <tr class="cgisShow">
                        <td class="ft_label">上游供应商返利(%)：</td>
                        <td class="ft_content">
                            <input type="text" id="topShopBackRate" readOnly="true" name="topShopBackRate" value="${checkApprove.topShopBackRate }" htmlEscape="false" class="input-medium  required" onblur="this.value=gdpMoney(this.value);calculateAmount();" onkeyup="gdpMax(this)" maxlength="5" />
                            <font color="red">*</font>
                        </td>
                        <td class="ft_label">上游返利总额(元)：</td>
                        <td class="ft_content">
                            <input type="text" id="topShopBackMoney" name="topShopBackMoney" value="${checkApprove.topShopBackMoney }" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);"  maxlength="18" htmlEscape="false" class="input-medium required" readOnly="true"/>
                            <font color="red">*</font>
                        </td>
                        <td class="ft_label">实际月利率(%)：</td>
                        <td class="ft_content">
                            <input type="text" id="topShopMonthRate" name="topShopMonthRate" value="${checkApprove.topShopMonthRate }" htmlEscape="false" class="input-medium  required" onblur="this.value=gdpMoney(this.value);" onkeyup="gdpMax(this)" maxlength="5" readOnly="true"/>
                            <font color="red">*</font>
                        </td>
                    </tr>
                    <tr class="cgisShow">
                        <td class="ft_label">居间服务费(元)：</td>
                        <td class="ft_content">
                            <input type="text" id="mediacyServiceFee" name="mediacyServiceFee" readOnly="true" value="${checkApprove.mediacyServiceFee }" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);calculateAmount();"  maxlength="18" htmlEscape="false" class="input-medium required"  />
                            <font color="red">*</font>
                        </td>
                        <%--<td class="ft_label">月息差(元)：</td>--%>
                        <%--<td class="ft_content">--%>
                            <%--<input type="text" id="interestRateDiff" name="interestRateDiff" value="${checkApprove.interestRateDiff }" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);calculateAmount();"  maxlength="18" htmlEscape="false" class=" input-medium required" readOnly="true" />--%>
                            <%--<font color="red">*</font>--%>
                        <%--</td>--%>
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

                    <tr id = "showMessageTrId" style="display: none;"  >
                        <td class="ft_label">变更信息：</td>
                        <td class="ft_content" colspan="6">
                            <table border = '1px' width = '745px'>
                                <c:forEach items="${messages}" var="contract">
                                    <tr><td style="border-style:none;">${contract}</td></tr>
                                </c:forEach>
                            </table>
                        </td></tr>
                    <tr>
                        <td class="ft_label">备注：</td>
                        <td class="ft_content" colspan="6">
                            <pre class="textarea-width pre-style" id="preRemark"></pre>
                            <textarea id="remark" name="remark" htmlEscape="false" rows="6" readonly="readonly" maxlength="1500" class="textarea-width textarea-style" placeholder="此项填写为小黄表备注项">${checkApprove.remark}</textarea>
                        </td>
                    </tr>
                    <tr id="oldTextTrId" style="display: none;" >
                        <td class="ft_label">原备注：</td>
                        <td class="ft_content" colspan="6">
                            <pre class="textarea-width pre-style" id="preOldText"></pre>
                            <textarea id="oldText"  htmlEscape="false" rows="6" maxlength="1500" class="textarea-width textarea-style">${oldText}</textarea>
                        </td>
                    </tr>
                    <tr>
                        <td class="ft_label">总公司批复意见：</td>
                        <td class="ft_content" colspan="6">
                            <pre class="textarea-width required pre-style" id="approResultPre"></pre>
                            <textarea id="approResult" name="approResult" maxlength="1000" class="textarea-width textarea-style required" rows="6" readonly="true">${topComApproveSugg}</textarea>
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
                            <input onkeyup="keyPress11(this);" readonly="readonly" onblur="this.value=outputmoney(this.value);" id="factLoanAmount" name="factLoanAmount" type="text" value="${checkApprove.loanAmount}" htmlEscape="false" maxlength="300" class="input-medium number" />
                        </td>
                        <td class="ft_label">收到现金金额(元)：</td>
                        <td class="ft_content">
                            <!-- 服务费提前收取时，收到现金金额=保证金+服务费总计；否则，收到现金金额=保证金 -->
                            <c:if test="${checkApprove.serviceFeeType eq '1'}">
                                <c:if test="${checkApprove.qualityServiceMarginAmount == null }" >
                                    <input onkeyup="keyPress11(this);" readonly="readonly" onblur="this.value=outputmoney(this.value);" id="receiveAmount" name="receiveAmount" type="text" value="${checkApprove.marginAmount + checkApprove.allServiceFee}" htmlEscape="false" maxlength="300" class="input-medium number" />
                                </c:if>
                                <c:if test="${checkApprove.qualityServiceMarginAmount != null }" >
                                    <input onkeyup="keyPress11(this);" readonly="readonly" onblur="this.value=outputmoney(this.value);" id="receiveAmount" name="receiveAmount" type="text" value="${checkApprove.marginAmount + checkApprove.allServiceFee+ checkApprove.qualityServiceMarginAmount}" htmlEscape="false" maxlength="300" class="input-medium number" />
                                </c:if>
                            </c:if>
                            <c:if test="${checkApprove.serviceFeeType ne '1'}">
                                <c:if test="${checkApprove.qualityServiceMarginAmount == null }" >
                                    <input onkeyup="keyPress11(this);" readonly="readonly" onblur="this.value=outputmoney(this.value);" id="receiveAmount" name="receiveAmount" type="text" value="${checkApprove.marginAmount}" htmlEscape="false" maxlength="300" class="input-medium number" />
                                </c:if>
                                <c:if test="${checkApprove.qualityServiceMarginAmount != null }" >
                                    <input onkeyup="keyPress11(this);" readonly="readonly" onblur="this.value=outputmoney(this.value);" id="receiveAmount" name="receiveAmount" type="text" value="${checkApprove.marginAmount + checkApprove.qualityServiceMarginAmount}" htmlEscape="false" maxlength="300" class="input-medium number" />
                                </c:if>
                            </c:if>
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
                        <td class="ft_content" colspan="3">
                            <form:input path="operateOtherDesc" htmlEscape="false" maxlength="300" class="input-xxlarge " />
                        </td>
                        <td name="tempHide" class="ft_label">是否有中间人：</td>
                        <td name="tempHide" class="ft_content">
                            <form:radiobuttons class="required" items="${fns:getDictList('yes_no') }" path="isOrNoMIddleMan" itemLabel="label" itemValue="value" htmlEscape="false" onchange="isOrNoMiddleMan(this.value);" />
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
        <div id="middlemanInfoDiv" class="searchInfo">
            <h3 onclick="dyqrxxClick()" class="searchTitle">中间人信息</h3>
            <div id="dyqrxxId" class="searchCon">
                <table class="fromTable filter">
                    <tr>
                        <td class="ft_label">中间人：</td>
                        <td>
                            <form:hidden path="middlemanCapTerNo" />
                            <form:select path="middlemanId" class="input-medium required" onchange="getMiddleInfo();">
                                <form:option value="">请选择</form:option>
                                <form:options items="${middlePersonList}" itemLabel="mortgageeName" itemValue="id" htmlEscape="false" />
                            </form:select>
                        </td>
                        <input type="hidden" id="middlemanName" name="middlemanName" />
                        <td class="ft_label">身份证：</td>
                        <td class="ft_content">
                            <input type="text" id="middlemanIdNum" name="middlemanIdNum" class="input-medium" readonly="true" value="${contract.middlemanIdNum}" />
                        </td>
                        <td class="ft_label">移动电话：</td>
                        <td class="ft_content">
                            <input type="text" id="middlemanMobileNum" name="middlemanMobileNum" class="input-medium mobile" readonly="true" value="${contract.middlemanMobileNum}" />
                        </td>
                    </tr>
                    <tr>
                        <td class="ft_label">银行卡号：</td>
                        <td class="ft_content">
                            <input type="text" id="middlemanBankcardNo" name="middlemanBankcardNo" class="input-medium" readonly="true" value="${contract.middlemanBankcardNo}" />
                        </td>
                        <td class="ft_label">开户行：</td>
                        <td class="ft_content">
                            <input type="text" id="middlemanBankNo" name="middlemanBankNo" class="input-medium" readonly="true" value="${contract.middlemanBankNo}" />
                        </td>
                        <td class="ft_label">开户行名称：</td>
                        <td class="ft_content">
                            <input type="text" id="middlemanBankDetailName" name="middlemanBankDetailName" class="input-medium" readonly="true" value="${contract.middlemanBankDetailName}" />
                        </td>
                    </tr>
                    <tr>
                        <td class="ft_label">开户行地址：</td>
                        <td class="ft_content" colspan="5">
                            <input type="text" id="middlemanContDistinctName" class="input-medium" readonly="true" value="${contract.middlemanContDistinct}" />
                            <input type="hidden" id="middlemanContDistinct" name="middlemanContDistinct" />
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
                                <form:input readonly="true" path="recBankcardNo" htmlEscape="false" maxlength="19" class="input-medium number required" onkeyup="loadBankCardInfo();" />
                            </td>
                                <%--<td class="ft_label">收款银行卡号确认：</td>
                                <!-- 非持久层字段-->
                                 <td class="ft_content">
                                    <input type="text" id="recBankcardNoConfirm" maxlength="19" class="input-medium number required" value="${contract.recBankcardNo}" onkeyup="loadBankCardInfo();" />
                                </td> --%>
                        </tr>
                        <tr>
                            <td class="ft_label">收款账户名称：</td>
                            <td class="ft_content">
                                <form:input path="recBankcardName"   htmlEscape="false" readonly="true" class="input-medium required" />
                                    <%--<form:input path="recBankcardName" htmlEscape="false" readOnly="readOnly" maxlength="20" class="input-medium" onkeyup="loadBankCardInfo();" /> --%>
                                    <%-- <form:select path="recBankcardName" class="input-medium required" disabled="true">
                                        <form:option value="">请选择</form:option>
                                        <form:options items="${applyRelations}" itemLabel="custName" itemValue="custId" htmlEscape="false" />
                                    </form:select> --%>
                                <form:hidden path="recBankcardId"/>
                            </td>
                            <td class="ft_label">移动电话：</td>
                            <td class="ft_content">
                                <form:input  path="recBankMobile" htmlEscape="false" readonly="true" maxlength="11" class="input-medium" />
                            </td>
                            <td class="ft_label">收款人身份证号：</td>
                            <td class="ft_content">
                                <form:input path="recBankIdNum"  htmlEscape="false" readonly="true" maxlength="18" class="input-medium" />
                            </td>
                        </tr>
                        <tr>
                            <td class="ft_label">收款银行：</td>
                            <td class="ft_content">
                                <form:input path="recBank" value="${fns:getDictLabel(contract.recBank, 'BANKS', '')}" htmlEscape="false" readonly="true" class="input-medium" />
                                    <%-- <form:select path="recBank" class="input-medium required" onchange="loadBankCardInfo();">
                                        <form:option value="" label="" />
                                        <form:options items="${fns:getDictList('BANKS') }" itemLabel="label" itemValue="value" htmlEscape="false" />
                                    </form:select> --%>
                            </td>
                            <td class="ft_label">收款开户行名称：</td>
                            <td class="ft_content">
                                <form:input readonly="true" path="recBankName" htmlEscape="false" maxlength="50" class="input-medium" onkeyup="loadBankCardInfo();" />
                            </td>
                        </tr>
                        <tr>
                            <td class="ft_label">收款银行地址：</td>
                            <td class="ft_content" colspan="5">
                                    <%-- <form:select path="recBankProvince" class="input-small nuNullCheck" data-code="-1" onchange="loadData()"></form:select>
                                    &nbsp;省
                                    <form:select path="recBankCity" class="input-small nuNullCheck" data-code="-1" onchange="loadData()"></form:select>
                                    &nbsp;市
                                    <form:select path="recBankDistinct" class="input-small nuNullCheck" data-code="-1" onchange="loadData()"></form:select>
                                    &nbsp;区&nbsp; --%>
                                <form:input readonly="true" path="recBankDetail" htmlEscape="false" class="input-medium" onblur="loadData()" />
                            </td>
                        </tr>
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
                                <%-- <td class="ft_label">还款银行卡号确认：</td>
                                <!-- 非持久层字段-->
                                <td class="ft_content">
                                    <input type="text" id=repBankcardNoConfirm class="input-medium  " value="${contract.repBankcardNo}" />
                                </td> --%>
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
                                <form:input path="repBank" htmlEscape="false" class="input-medium " />
                                    <%-- <form:select path="repBank" class="input-medium ">
                                        <form:option value="" label="请选择" />
                                        <form:options items="${fns:getDictList('BANKS') }" itemLabel="label" itemValue="value" htmlEscape="false" />
                                    </form:select> --%>
                            </td>
                            <td class="ft_label">还款开户行名称：</td>
                            <td class="ft_content">
                                <form:input path="repBankName" htmlEscape="false" class="input-medium " />
                            </td>
                        </tr>
                        <tr>
                            <td class="ft_label">还款银行地址：</td>
                            <td class="ft_content" colspan="5">
                                    <%-- <form:select path="repBankProvince" class="input-small nuNullCheck" data-code="-1"></form:select>
                                    &nbsp;省
                                    <form:select path="repBankCity" class="input-small nuNullCheck" data-code="-1"></form:select>
                                    &nbsp;市
                                    <form:select path="repBankDistinct" class="input-small nuNullCheck" data-code="-1"></form:select>
                                    &nbsp;区&nbsp; --%>
                                <form:input path="repBankDetail" htmlEscape="false" class="input-medium " />
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        <div id="custBankInfoDiv" class="searchInfo">
                <%-- <h3 onclick="gedqygrClick()" class="searchTitle">借款人开户信息</h3>
                <div id="gedqygrId" class="searchCon">
                        <div id="gedAccount">
                            <h3>个人开户信息</h3>
                            <table class="fromTable filter">
                                <tr>
                                    <td class="ft_label">姓名：</td>
                                    <td class="ft_content">
                                        <input id="legalPerName" value="${gedAccount.legalPerName}" type="text" readonly="true"  class="input-medium" />
                                    </td>
                                    <td class="ft_label">身份证号码：</td>
                                    <td class="ft_content">
                                        <input id="legalPerNum" value="${gedAccount.legalPerNum}" type="text" readonly="true"  class="input-medium" />
                                    </td>
                                    <!-- 非持久层字段-->
                                        <input type="hidden" id=companyBankBranchName class="input-medium" readonly="true" value="${gedAccount.companyBankBranchName}" />
                                        <input type="hidden" id=legalPerNum class="input-medium" readonly="true" value="${gedAccount.legalPerNum}" />
                                        <input type="hidden" id=legalPerName class="input-medium" readonly="true" value="${gedAccount.legalPerName}" />
                                        <input type="hidden" id=legalPerPhone class="input-medium" readonly="true" value="${gedAccount.legalPerPhone}" />
                                    <td class="ft_label">手机号码：</td>
                                    <td class="ft_content">
                                        <input id="legalPerPhone" value="${gedAccount.legalPerPhone}" type="text" readonly="true"  class="input-medium" />
                                    </td>
                                </tr>
                                <tr>
                                    <td class="ft_label">开户行：</td>
                                    <td class="ft_content">
                                        <input id="companyBankOfDeposit" value="${fns:getDictLabel(gedAccount.companyBankOfDeposit, 'BANKS', '')}" type="text" readonly="true"  class="input-medium" />
                                    </td>
                                    <td class="ft_label">银行账户：</td>
                                    <td class="ft_content">
                                        <input type="text" id=comAccount class="input-medium" readonly="true" value="${gedAccount.companyAccount}" />
                                    </td>
                                    <td class="ft_label">开户行支行名称：</td>
                                    <!-- 非持久层字段-->
                                    <td class="ft_content">
                                        <input type="text" id=companyBankBranchName class="input-medium" readonly="true" value="${gedAccount.companyBankBranchName}" />
                                    </td>

                                </tr>
                                <tr>
                                    <td class="ft_label">开户省：</td>
                                    <td class="ft_content">
                                        <input type="text" value="${gedAccount.provinceName}" readonly="true"  id="provinceName" class="input-medium  " />
                                    </td>
                                    <td class="ft_label">开户市：</td>
                                    <!-- 非持久层字段-->
                                    <td class="ft_content">
                                        <input type="text" id="cityName" readonly="true" class="input-medium  " value="${gedAccount.cityName}" />
                                    </td>
                                </tr>
                            </table>
                        </div> --%>
                <%--<div id="companyAccount">
                    <input type="hidden" value= "${companyInfo.unSocCreditNo}" id="gedunSocCreditNo">
                    <input type="hidden" value= "${companyInfo.corporationName}" id="gedcorporationName">
                    <input type="hidden" value= "${companyInfo.corporationCardIdNo}" id="gedcorporationCardIdNo">
                    <input type="hidden" value= "${companyInfo.corporationMobile}" id="gedcorporationMobile">
                    <input type="hidden" value= "${companyInfo.busiRegName}" id="gedbusiRegName">
                    <h3>企业户信息</h3>
                    <table class="fromTable filter">
                        <tr>
                            <td class="ft_label">企业名称：</td>
                            <td class="ft_content">
                                <input id="gecompanyName" value="${companyAccount.companyName}" type="text" readonly="true"  class="input-medium" />
                            </td>
                            <td class="ft_label">企业证件类型：</td>
                            <td class="ft_content">
                                <input type="text" class="input-medium" readonly="true" value="${companyAccount.companyCardType eq 1 ? '社会统一信用代码':''}" />
                            </td>
                            <td class="ft_label">企业统一社会信用代码：</td>
                            <!-- 非持久层字段-->
                            <td class="ft_content">
                                <input type="text" id="gesocialCreditCode" class="input-medium" readonly="true" value="${companyAccount.socialCreditCode}" />
                            </td>
                        </tr>
                        <tr>

                            <td class="ft_label">法人代表名称：</td>
                            <td class="ft_content">
                                <input id="gelegalName" value="${companyAccount.legalName}" type="text" readonly="true"  class="input-medium" />
                            </td>
                            <td class="ft_label">法人代表身份证号码：</td>
                            <td class="ft_content">
                                <input id="gelegalCardNumber" value="${companyAccount.legalCardNumber}" type="text" readonly="true"  class="input-medium" />
                            </td>
                            <td class="ft_label">法人代表手机号码：</td>
                            <td class="ft_content">
                                <input type="text" value="${companyAccount.legalMobile}" readonly="true"  id="gelegalMobile" class="input-medium  " />
                            </td>
                        </tr>
                        <tr>

                            <td class="ft_label">企业开户银行：</td>
                            <td class="ft_content">
                                <input id="companyBankOfDepositValue" value="${companyAccount.companyBankOfDepositValue}" type="text" readonly="true"  class="input-medium" />
                            </td>
                            <td class="ft_label">企业对公账户：</td>
                            <td class="ft_content">
                                <input id="companyAccount" value="${companyAccount.companyAccount}" type="text" readonly="true"  class="input-medium" />
                            </td>
                            <td class="ft_label">开户支行名称：</td>
                            <td class="ft_content">
                                <input type="text" value="${companyAccount.companyBankBranchName}" readonly="true"  id="companyBankBranchName" class="input-medium  " />
                            </td>
                        </tr>
                        <tr>
                            <td class="ft_label">开户支行所在地区：</td>
                            <td class="ft_content" colspan="4">
                                <input type="text" value="${companyAccount.provinceCode}" readonly="true"  id="gedProvinceCode" class="input-medium" />&nbsp;省
                                <input type="text" value="${companyAccount.cityCode}" readonly="true"  id="gedCityCode" class="input-medium"/>&nbsp;市
                                <input type="text" value="${companyAccount.areaCode}" readonly="true"  id="gedAreaCode" class="input-medium"/>&nbsp;区
                            </td>
                        </tr>
                    </table>
                </div>--%>
            </div>
        </div>
        <div id="showAccountMessage" class="searchInfo">

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
<!-- 面签结论 -->
<div id="isHideSuggestionDiv">
    <script type="text/javascript">
        function setContractNo() {
            var contractNo = $("#contractNo").val();
            $("#contractNoHidden").val(contractNo);
        }
    </script>
    <div id="contractSignSuggestionDiv">
        <form:form id="inputForm2" modelAttribute="contractSignSuggestion" action="${ctx}/credit/contract/saveSuggestion" method="post" class="form-horizontal">
            <form:hidden path="id" />
            <sys:message content="${message}" />
            <div class="searchInfo">
                <h3 onclick="mqjlClick()" class="searchTitle">面签结论</h3>
                <input type="hidden" id="applyNo" name="applyNo" value="${actTaskParam.applyNo}" />
                <input type="hidden" id="taskId" name="taskId" value="${actTaskParam.taskId}" />
                <input type="hidden" id="taskDefKey" name="taskDefKey" value="${actTaskParam.taskDefKey}" />
                <input type="hidden" id="procDefId" name="procDefId" value="${actTaskParam.procDefId}" />
                <input type="hidden" id="status" name="status" value="${actTaskParam.status}" />
                <input type="hidden" id="procInstId" name="procInstId" value="${actTaskParam.procInstId}" />
                <input type="hidden" id="headUrl" name="headUrl" value="${actTaskParam.headUrl}" />
                <input type="hidden" id="contractNoHidden" name="contractNo" />
                <input type="hidden" value ="" id="socialCreditNo">
                <div id="mqjlId" class="infoListCon">
                    <div class="searchCon">
                        <table class="fromTable filter">
                            <tr>
                                <td class="ft_label" style="width: 10%;">面签结论：</td>
                                <td class="ft_content" style="width: 90%;">
                                    <input type="radio" name="passFlag" value="yes" id="sign_yes" class="required">
                                    <label for="radio_yes">通过</label>
                                    &nbsp;&nbsp;
                                    <input type="radio" name="passFlag" value="no" id="sign_no" class="required">
                                    <label for="radio_no">打回</label>
                                </td>
                            </tr>
                            <tr>
                                <td class="ft_label" style="width: 10%;">面签意见：</td>
                                <td class="ft_content" style="width: 90%;">
                                    <pre class="textarea-width required pre-style" id="signSuggestionPre"></pre>
                                    <form:textarea path="suggestionDesc" id="signSuggestion" class="textarea-width textarea-style required" rows="5" htmlEscape="false" maxlength="1000" onkeyup="adjustTextareaLength('signSuggestion','signSuggestionPre')" />
                                </td>
                            </tr>
                            <tr>
                                <td colspan="6" style="text-align: right;">
                                    <input id="btnSubmitSign" class="btn btn-primary" type="submit" value="提 交" onclick="setContractNo()" />
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </form:form>
    </div>
    <!-- 法务审核 -->
    <div id="legalCheckSuggestionDiv">
        <form:form id="inputForm3" modelAttribute="legalCheckSuggestion" action="${ctx}/credit/contract/saveSuggestion" method="post" class="form-horizontal">
            <form:hidden path="id" />
            <sys:message content="${message}" />
            <div class="searchInfo">
                <h3 class="searchTitle">法务审核结论</h3>
                <input type="hidden" id="applyNo" name="applyNo" value="${actTaskParam.applyNo}" />
                <input type="hidden" id="taskId" name="taskId" value="${actTaskParam.taskId}" />
                <input type="hidden" id="taskDefKey" name="taskDefKey" value="${actTaskParam.taskDefKey}" />
                <input type="hidden" id="procDefId" name="procDefId" value="${actTaskParam.procDefId}" />
                <input type="hidden" id="status" name="status" value="${actTaskParam.status}" />
                <input type="hidden" id="procInstId" name="procInstId" value="${actTaskParam.procInstId}" />
                <input type="hidden" id="headUrl" name="headUrl" value="${actTaskParam.headUrl}" />
                <input type="hidden" name="contractNo" value="${contract.contractNo}" />
                <div class="searchCon">
                    <table class="fromTable filter">
                        <tr>
                            <td class="ft_label" style="width: 10%;">审核结论：</td>
                            <td class="ft_content" style="width: 90%;">
                                <input type="radio" name="passFlag" value="yes" id="legal_yes" class="required">
                                <label for="radio_yes">通过</label>
                                &nbsp;&nbsp;
                                <input type="radio" name="passFlag" value="no" id="legal_no" class="required">
                                <label for="radio_no">打回</label>
                            </td>
                        </tr>
                        <tr>
                            <td class="ft_label" style="width: 10%;">审核意见：</td>
                            <td class="ft_content" style="width: 90%;">
                                <pre class="textarea-width required pre-style" id="suggestionDescPre"></pre>
                                <form:textarea path="suggestionDesc" class="textarea-width textarea-style required" onkeyup="adjustTextareaLength('suggestionDesc','suggestionDescPre')" rows="5" htmlEscape="false" maxlength="1000" />
                            </td>
                        </tr>
                        <tr>
                            <td colspan="6" style="text-align: right;">
                                <input id="btnSubmitLegal" class="btn btn-primary" type="submit" value="提 交" />
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </form:form>
    </div>
    <!-- 合同审核 -->
    <div id="contractCheckSuggestionDiv">
        <form:form id="inputForm4" modelAttribute="contractCheckSuggestion" action="${ctx}/credit/contract/saveSuggestion" method="post" class="form-horizontal">
            <form:hidden path="id" />
            <sys:message content="${message}" />
            <div class="searchInfo">
                <h3 class="searchTitle">合同审核结论</h3>
                <input type="hidden" id="applyNo" name="applyNo" value="${actTaskParam.applyNo}" />
                <input type="hidden" id="taskId" name="taskId" value="${actTaskParam.taskId}" />
                <input type="hidden" id="taskDefKey" name="taskDefKey" value="${actTaskParam.taskDefKey}" />
                <input type="hidden" id="procDefId" name="procDefId" value="${actTaskParam.procDefId}" />
                <input type="hidden" id="status" name="status" value="${actTaskParam.status}" />
                <input type="hidden" id="procInstId" name="procInstId" value="${actTaskParam.procInstId}" />
                <input type="hidden" id="headUrl" name="headUrl" value="${actTaskParam.headUrl}" />
                <input type="hidden" name="approProductTypeNameCode" value="${checkApprove.approProductTypeCode}" />
                <input type="hidden" name="contractNo" value="${contract.contractNo}" />
                <div class="searchCon">
                    <table class="fromTable filter">
                        <tr>
                            <td class="ft_label" style="width: 10%;">审核结论：</td>
                            <td class="ft_content" style="width: 90%;">
                                <input type="radio" name="passFlag" value="yes" id="radio_yes" class="required">
                                <label for="radio_yes">通过</label>
                                &nbsp;&nbsp;
                                <input type="radio" name="passFlag" value="no" id="radio_no" class="required">
                                <label for="radio_no">打回</label>
                            </td>
                        </tr>
                        <tr>
                            <td class="ft_label" style="width: 10%;">审核意见：</td>
                            <td class="ft_content" style="width: 90%;">
                                <pre class="textarea-width required pre-style" id="suggestionDesc1Pre"></pre>
                                <form:textarea path="suggestionDesc" id="suggestionDesc1" rows="5" htmlEscape="false" onkeyup="adjustTextareaLength('suggestionDesc1','suggestionDesc1Pre')" maxlength="1000" class="textarea-width textarea-style required" />
                            </td>
                        </tr>
                        <tr>
                            <td colspan="6" style="text-align: right;">
                                <input id="btnSubmit" class="btn btn-primary" type="submit" value="提 交" />
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </form:form>
    </div>
</div>
<c:if test="${not empty checkFlag}">
    <script type="text/javascript">
        alertx('操作成功', function() {
            goToPage('${ctx}${actTaskParam.headUrl}', 'contractSignInfoSkipId');
        });
    </script>
</c:if>
</body>
</html>