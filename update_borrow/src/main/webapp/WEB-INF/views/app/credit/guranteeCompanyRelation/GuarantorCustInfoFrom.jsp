<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>新增担保人信息</title>
    <meta name="decorator" content="default" />
    <%@ include file="/WEB-INF/views/include/cityselectJS.jsp"%>
    <script type="text/javascript">
        $(document).ready(function() {
            //房产初始化
            /* if ('${guarantorWorkInfo.custInfo.isFixedHouse}' == 1) {
			alert('有');
		} */
            valueIsRquired(${guarantorWorkInfo.custInfo.isFixedHouse});

            document.getElementById("estateValuation").value = outputmoney("${guarantorWorkInfo.custInfo.estateValuation}");
            document.getElementById("perAnnualIncome1").value = outputmoney("${guarantorWorkInfo.custInfo.perAnnualIncome}");
            if ('${readOnly}' == '0') {
                setPageReadOnly();
            }
            //身份证初始化
            if ('${guarantorWorkInfo.custInfo.personIdEndDate}' == null || '${guarantorWorkInfo.custInfo.personIdEndDate}' == '') {
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
            $("#guarantorInfoForm").validate({
                submitHandler : validateIdNum,
                errorContainer : "#messageBox",
                errorPlacement : function(error, element) {
                    checkReq(error, element);
                }
            });
            adjustTextareaLength("livingStatusDesc", "pre");

            adjustTextareaLength("remarks", "preRemarks");

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

            if (!checkIsNull('${guarantorWorkInfo.custInfo.regDistinct}')) {
                setName(data, "reg", '${guarantorWorkInfo.custInfo.regProvince}', '${guarantorWorkInfo.custInfo.regCity}', '${guarantorWorkInfo.custInfo.regDistinct}');
                if (!checkIsNull('${guarantorWorkInfo.custInfo.contDistinct}')) {
                    setName(data, "cont", '${guarantorWorkInfo.custInfo.contProvince}', '${guarantorWorkInfo.custInfo.contCity}', '${guarantorWorkInfo.custInfo.contDistinct}');
                }
            }

            new CitySelect({
                data : data,
                provId : '#companyProvince',
                cityId : '#companyCity',
                areaId : '#companyDistinct',
                isSelect : false
            });

        });

        function validateIdNum() {
            loading();
            var id1 = $("#id").val();
            var idNum = $("#idNum").val();
            var mobileNum = $("#mobileNum").val();
            $.ajax({
                type : "post",
                data : {
                    id : id1,
                    mobileNum : mobileNum,
                    idNum : idNum,
                    applyNo : '${applyNo}'
                },
                url : "${ctx}/custinfo/custInfo/validateIdNum",
                dataType : "json",
                success : function(data) {
                    if (data.status == "1") {
                        saveGuarantorInfo();
                    } else {
                        closeTip();
                        alertx(data.message);
                    }
                }
            });
        }

        //身份证终止日期显示与否
        function changeShowOrHide() {
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
        function loadCity(val) {
            //重置市、县下拉框
            if ("reg" == val) {
                $("#regCity").empty();
                $("#regCity").append("<option value=''>请选择</option>");
                var $regCity = $("#s2id_regCity>.select2-choice>.select2-chosen");
                $regCity.html("请选择");
                $("#regDistinct").empty();
                $("#regDistinct").append("<option value=''>请选择</option>");
                var $regDistinct = $("#s2id_regDistinct>.select2-choice>.select2-chosen");
                $regDistinct.html("请选择");
                $.post("${ctx}/sys/area/treeNode", {
                    parentId : $("#regProvince").val()
                }, function(data) {
                    $.each(data, function(i, val) {
                        $("#regCity").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
                    });
                });
            } else {
                $("#contCity").empty();
                $("#contCity").append("<option value=''>请选择</option>");
                var $contCity = $("#s2id_contCity>.select2-choice>.select2-chosen");
                $contCity.html("请选择");
                $("#contDistinct").empty();
                $("#contDistinct").append("<option value=''>请选择</option>");
                var $contDistinct = $("#s2id_contDistinct>.select2-choice>.select2-chosen");
                $contDistinct.html("请选择");
                $.post("${ctx}/sys/area/treeNode", {
                    parentId : $("#contProvince").val()
                }, function(data) {
                    $.each(data, function(i, val) {
                        $("#contCity").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
                    });
                });
            }
        }

        function loadDistinct(val) {
            if ("reg" == val) {
                $("#regDistinct").empty();
                $("#regDistinct").append("<option value=''>请选择</option>");
                var $regDistinct = $("#s2id_regDistinct>.select2-choice>.select2-chosen");
                $regDistinct.html("请选择");

                $.post("${ctx}/sys/area/treeNode", {
                    parentId : $("#regCity").val()
                }, function(data) {
                    $.each(data, function(i, val) {
                        $("#regDistinct").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
                    });
                });
            } else {
                $("#contDistinct").empty();
                $("#contDistinct").append("<option value=''>请选择</option>");
                var $contDistinct = $("#s2id_contDistinct>.select2-choice>.select2-chosen");
                $contDistinct.html("请选择");

                $.post("${ctx}/sys/area/treeNode", {
                    parentId : $("#contCity").val()
                }, function(data) {
                    $.each(data, function(i, val) {
                        $("#contDistinct").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
                    });
                });
            }
        }

        function errorHideHelper(id) {
            var $val = $("#" + id).find("option:checked").val()
            if (null != $val && "" != $val) {
                $("label[for='" + id + "']").hide();
            }
        }
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


        //保存担保人信息
        function saveGuarantorInfo() {
            if($("#contDistinct").val() == "-1"){
                alertx("请填写现居住地！");
                closeTip();
            }else{
                var valueT1 = $("#perAnnualIncome1").val().replace(/,/g, "");
                var valueT2 = $("#estateValuation").val().replace(/,/g, "");
                $("#perAnnualIncome1").val(valueT1);
                $("#estateValuation").val(valueT2);
                top.$.jBox.tip.mess = null;
                var work = $("#guarantorInfoForm").serializeJson();
                $.post("${ctx}/credit/creApplyCompanyRelation/saveCustInfo", work, function(data) {
                    if (data) {
                        closeTip();
                        if (data.status == 1) {//保存成功
                            alertx(data.message, function() {

                                parent.$.loadDiv("guarantorInfoList", "${ctx}/credit/creApplyCompanyRelation/selectCustInfo", {
                                    custId : '${piliId}',
                                    applyNo : '${applyNo}'
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

        var treeCategory1 = [ "categoryMain1", "categoryLarge1", "categoryMedium1", "categorySmall1" ];
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

        //json转为form表单
        var JSONToform = function(form, oJson) {
            var max = form.elements.length;
            var i = 0;
            var chkRadioName = ",";
            var e, ename, eValue = "";
            for (i = 0; i < max; i++) {
                e = form.elements[i];
                ename = e.name;
                ename = ename.substring(9);
                switch (e.type) {
                    case 'checkbox':
                    case 'radio':
                        handchkradio(form, ename, oJson[ename]);
                        break;
                    case 'hidden':
                    case 'text':
                    case 'select-one':
                    case 'textarea':
                        if (oJson[ename])
                            e.value = oJson[ename];
                        break;
                    default:
                }
            }
        }

        var handchkradio = function (form, ename, strValue) {
            strValue = ","+strValue+",";
            var  max = form.elements.length;
            var i=0;
            var oE;
            var strTemp="";
            for (i=0; i < max; i++) {
                oE = form.elements[i];
                if (oE.name != ename)
                    continue;   // 元素名称不同，就跳过
                oE.checked = false;
                if ( strValue.indexOf(","+escape(oE.value)+",") >=0 )
                    oE.checked = true;
            }
        }

        function ensureAge(card) {
            if(card.length<15)
                return;
            $.ajax({
                type : "post",
                data : {
                    idNum : card
                },
                url : "${ctx}/custinfo/custInfo/attachInfo",
                dataType : "json",
                success : function(msg) {
                    if(msg.custInfo.categoryLarge){
                        setIndustry(msg.categoryLargeList, msg.categoryMediumList, msg.categorySmallList);
                    }
                    JSONToform(document.getElementById("guarantorInfoForm"), msg.custInfo);
                    if(msg.custInfo.custName){
                        setName(data, "reg", msg.custInfo.regProvince, msg.custInfo.regCity, msg.custInfo.regDistinct);
                        setName(data, "cont", msg.custInfo.contProvince, msg.custInfo.contCity, msg.custInfo.contDistinct);
                    }
                    $("select").select2();

                },
                error : function(msg) {
                    alertx("未能保存，请查看后台信息");
                }
            });
        }

        function setIndustry(largeList,mediumList,smallList){
            $("#categoryLarge1").html("");
            $("#categoryMedium1").html("");
            $("#categorySmall1").html("");

            if(largeList != null){
                for (var m = 0; m < largeList.length; m++) {
                    $("#categoryLarge1").append("<option value='" + largeList[m].induCode + "' data-name='" + largeList[m].induName + "'>" + largeList[m].induName + "</option>");
                }
            }
            if(mediumList != null){
                for (var m = 0; m < mediumList.length; m++) {
                    $("#categoryMedium1").append("<option value='" + mediumList[m].induCode + "' data-name='" + mediumList[m].induName + "'>" + mediumList[m].induName + "</option>");
                }
            }

            if(smallList != null){
                for (var m = 0; m < smallList.length; m++) {
                    $("#categorySmall1").append("<option value='" + smallList[m].induCode + "' data-name='" + smallList[m].induName + "'>" + smallList[m].induName + "</option>");
                }
            }
        }

        function valueIsRquired(value) {
            /* if (value == '1') {
                $("#estateValuation").addClass("required");
            } else {
                if (document.getElementById("estateValuation").style.backgroundColor = "pink") {
                    document.getElementById("estateValuation").style.backgroundColor = "";
                }
                $("#estateValuation").removeClass("required");
            } */
            if (value == 1){
                $("#estateLabel").show();
                $("#addressLabel").show();
                $("#addressContent").show();
                $("#estateValuation").addClass("required");
                $("#houseAddress").addClass("required");
            }else{
                $("#estateLabel").hide();
                $("#addressLabel").hide();
                $("#addressContent").hide();
                $("#estateValuation").removeClass("required");
                $("#houseAddress").removeClass("required");
                $("#estateValuation").val("");
                $("#houseAddress").val("")
            }
        }


    </script>
</head>
<body>
<div class="searchInfo">
    <h3 class="searchTitle">担保人信息</h3>
    <div class="searchCon">
        <form:form id="guarantorInfoForm" modelAttribute="guarantorWorkInfo" action="${ctx}/credit/guarantorInfo/save" method="post" class="form-horizontal">
        <form:hidden path="custInfo.id" id="id" />
        <input type="hidden" name="custInfo.currApplyNo" value="${applyNo}" />

        <sys:message content="${message}" />
        <table class="fromTable filter">

            <tr>
                <td class="ft_label">姓名：</td>
                <td class="ft_content">
                    <form:input type="text" maxlength="20" onblur="this.value=reSpaces(this.value);" path="custInfo.custName" htmlEscape="false" class="input-medium required" />
                    <font color="red">*</font>
                </td>
                <td class="ft_label">性别：</td>
                <td class="ft_content">
                    <form:select id="sexNo" path="custInfo.sexNo" class="input-medium required" cssStyle="width:177px;">
                        <form:option value="" label="" />
                        <form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false" />
                    </form:select>
                    <font color="red">*</font>
                </td>
                <td class="ft_label">移动电话：</td>
                <td class="ft_content">
                    <input type="text" maxlength="11" id="mobileNum" name="custInfo.mobileNum" class="input-medium required mobile" value="${guarantorInfo.mobileNum}" />
                    <font color="red">*</font>
                </td>
            </tr>
            <tr>
                <td class="ft_label">证件类型：</td>
                <td class="ft_content">
                    <form:select id="idType" path="custInfo.idType" class="input-medium required" cssStyle="width:177px;">
                        <form:option value="" label="" />
                        <form:options items="${fns:getDictList('CUSTOMER_P_ID_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
                    </form:select>
                    <font color="red">*</font>
                </td>
                <td class="ft_label">证件号：</td>
                <td class="ft_content">
                    <input type="text" id="idNum" maxlength="18" name="custInfo.idNum" onblur="ensureAge(this.value)" class="input-medium required card" value="${guarantorInfo.idNum}" />
                    <input type="hidden" id="piliId" name="piliId"  value="${piliId}" />
                    <input type="hidden" id="applyNo" name="applyNo"  value="${applyNo}" />

                    <!--这是批量借款id-->
                    <font color="red">*</font>
                </td>
                <td class="ft_label">民族：</td>
                <td class="ft_content">
                    <form:select path="custInfo.nationNo" id="nationNoMain" class="input-medium required" cssStyle="width:177px;">
                        <form:option value="" label="" />
                        <form:options items="${fns:getDictList('NATION_NO')}" itemLabel="label" itemValue="value" htmlEscape="false" />
                    </form:select>
                    <font color="red">*</font>
                </td>
            </tr>
            <tr>
                <td class="ft_label">出生日期：</td>
                <td class="ft_content">
                    <input id="birthDay" name="custInfo.birthDay" type="text" maxlength="20" readonly="readonly" class="input-medium Wdate" value="${guarantorInfo.birthDay}" />
                    <font color="red">*</font>
                </td>
                <td class="ft_label">年龄：</td>
                <td class="ft_content">
                    <input type="text" maxlength="2" id="ageNo" name="custInfo.ageNo" readonly="true" class="input-medium " value="${guarantorInfo.ageNo}" />
                    <font color="red">*</font>
                </td>
            </tr>
            <tr>
                <td class="ft_label">身份证起始日期：</td>
                <td class="ft_content">
                    <input id="personIdStartDate" name="custInfo.personIdStartDate" type="text" maxlength="20" class="input-medium Wdate required" value="${guarantorInfo.personIdStartDate}" onclick="WdatePicker({onpicked:dateWhite(this),maxDate:new Date()})" />
                    <font color="red">*</font>
                </td>
                <td class="ft_label">身份证是否长期：</td>
                <td class="ft_label" style="text-align: left;">
                    <input type="radio" name="cardFlag" id="yes" value="yes" onchange="changeShowOrHide()" />
                    是
                    <input type="radio" id="no" name="cardFlag" value="no" onchange="changeShowOrHide()" />
                    否
                </td>
                <td class="ft_label">
                    <span id="cardEndText">身份证终止日期：</span>
                </td>
                <td class="ft_content">
                    <input id="personIdEndDate" name="custInfo.personIdEndDate" type="text" maxlength="20" class="input-medium Wdate required" value="${guarantorInfo.personIdEndDate}" onclick="WdatePicker({onpicked:dateWhite(this),minDate:new Date()})" />
                    <font id="personIdEndDateRed" color="red">*</font>
                </td>
            </tr>
            <tr>
                <td class="ft_label">最高学历：</td>
                <td class="ft_content">
                    <form:select id="topEducationNo" path="custInfo.topEducationNo" class="input-medium required" cssStyle="width:177px;">
                        <form:option value="" label="" />
                        <form:options items="${fns:getDictList('EDUCATION')}" itemLabel="label" itemValue="value" htmlEscape="false" />
                    </form:select>
                    <font color="red">*</font>
                </td>
                <td class="ft_label">婚姻状况：</td>
                <td class="ft_content">
                    <form:select id="wedStatus" path="custInfo.wedStatus" class="input-medium required" cssStyle="width:177px;">
                        <form:option value="" label="" />
                        <form:options items="${fns:getDictList('WED_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
                    </form:select>
                    <font color="red">*</font>
                </td>
                <td class="ft_label">子女数量：</td>
                <nobr>
                    <td class="ft_content">
                        <form:select id="childrenSon" path="custInfo.childrenSon" class="input-medium required" cssStyle="width:33%">
                            <form:option value="" label="" />
                            <form:options items="${fns:getDictList('CHILDREN_NUM')}" itemLabel="label" itemValue="value" htmlEscape="false" />
                        </form:select>
                        男
                        <font color="red">*</font>
                        <form:select id="childrenGirl" path="custInfo.childrenGirl" class="input-medium required" cssStyle="width:33%">
                            <form:option value="" label="" />
                            <form:options items="${fns:getDictList('CHILDREN_NUM')}" itemLabel="label" itemValue="value" htmlEscape="false" />
                        </form:select>
                        女
                        <font color="red">*</font>
                    </td>
                </nobr>
            </tr>
            <tr>
                <td class="ft_label">户籍地址：</td>
                <td class="ft_content" colspan="5">
                    <form:select path="custInfo.regProvince" id="regProvince" class="input-small nuNullCheck" data-code="-1"></form:select>
                    &nbsp;省
                    <form:select path="custInfo.regCity" id="regCity" class="input-small nuNullCheck" data-code="-1"></form:select>
                    &nbsp;市
                    <form:select path="custInfo.regDistinct" id="regDistinct" class="input-small nuNullCheck" data-code="-1"></form:select>
                    &nbsp;区&nbsp;
                    <font color="red">*</font>
                    <span style="width: 15px; display: inline-block;"></span>
                    地址明细：
                    <input type="text" name="custInfo.regDetails" id="regDetails" onblur="this.value=reSpaces(this.value);" class="input-medium required" maxlength="300" value="${guarantorInfo.regDetails}" style="margin: 0px; position: relative; display: inline-block; vertical-align: middle;" />
                    <font color="red">*</font>
                </td>
            </tr>
            <tr>
                <td class="ft_label">现居住地：</td>
                <td class="ft_content" colspan="5">
                    <form:select class="input-small nuNullCheck" id="contProvince" path="custInfo.contProvince" data-code="-1"></form:select>
                    &nbsp;省
                    <form:select class="input-small nuNullCheck" id="contCity" path="custInfo.contCity" data-code="-1"></form:select>
                    &nbsp;市
                    <form:select class="input-small nuNullCheck" id="contDistinct" path="custInfo.contDistinct" data-code="-1"></form:select>
                    &nbsp;区&nbsp;
                    <font color="red">*</font>
                    <span style="width: 15px; display: inline-block;"></span>
                    地址明细：
                    <input type="text" name="custInfo.contDetails" onblur="this.value=reSpaces(this.value);" id="contDetails" value="${guarantorInfo.contDetails}" class="input-medium required" maxlength="1000" style="margin: 0px; position: relative; vertical-align: middle;" />
                    <font color="red">*</font>
                </td>
            </tr>
            <tr>
                <td class="ft_label">是否本地户籍：</td>
                <td class="ft_content">
                    <form:select path="custInfo.isLocal" id="isLocalMain" class="input-mini required" cssStyle="width:177px;">
                        <form:option value="" label="" />
                        <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
                    </form:select>
                    <font color="red">*</font>
                </td>
                <td class="ft_label">现住宅地居住时间(年)：</td>
                <td class="ft_content">
                    <input id="regInDate" name="custInfo.regInDate" type="text" maxlength="3" class="input-medium number required" value="${guarantorInfo.regInDate}" />
                    <font color="red">*</font>
                </td>
                <td class="ft_label">来本市年限(年)：</td>
                <td class="ft_content">
                    <input id="cityInDate" name="custInfo.cityInDate" type="text" maxlength="3" class="input-medium number required" value="${guarantorInfo.cityInDate}" />
                    <font color="red">*</font>
                </td>
            </tr>
            <tr>
                <td class="ft_label">与借款人关系：</td>
                <td class="ft_content">
                    <form:select path="custInfo.relationForApply" id="relationForApply" class="input-small required">
                        <option value="">请选择</option>
                        <form:options items="${fns:getDictList('CONTACT_RELATIONS_CON')}" itemLabel="label" itemValue="value" htmlEscape="false" />
                    </form:select>
                    <font color="red">*</font>
                </td>
                <td class="ft_label">居住状况：</td>
                <td class="ft_content">
                    <form:select path="custInfo.livingStatus" id="livingStatus" class="input-small required">
                        <option value="">请选择</option>
                        <form:options items="${fns:getDictList('LIVING_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
                    </form:select>
                    <font color="red">*</font>
                </td>
            </tr>
            <tr>
                <td class="ft_label">名下是否有房产：</td>
                <td class="ft_content">
                    <form:select path="custInfo.isFixedHouse" id="isFixedHouse" class="input-small required" onchange="valueIsRquired(this.value)">
                        <option value="">请选择</option>
                        <form:options items="${fns:getDictList('IS_HAVE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
                    </form:select>
                    <font color="red">*</font>
                </td>
                <td class="ft_label" id="addressLabel">房产地址：</td>
                <td class="ft_content" id="addressContent" colspan="3">
                    <form:input path="custInfo.houseAddress" id="houseAddress" class="input-xlarge" />
                    <font color="red">*</font>
                </td>
            </tr>
            <tr>
                <td class="ft_label">是否推送外访系统：</td>
                <td class="ft_content">

                    <form:select path="custInfo.svFlag" id="svFlag" class="input-small required" >

                        <option value="">请选择</option>
                        <form:options items="${fns:getDictList('SV_FLAG')}" itemLabel="label" itemValue="value" htmlEscape="false" />
                    </form:select>
                    <font color="red">*</font>
                </td>

            </tr>
            <tr id="estateLabel">
                <td class="ft_label">房产估值：</td>
                <td class="ft_content">
                    <form:input path="custInfo.estateValuation" id="estateValuation" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" class="input-medium number" />
                    <font color="red">*</font>
                </td>
            </tr>
            <tr>
                <td class="ft_label">居住状况说明：</td>
                <td class="ft_content" colspan="5">
                    <form:textarea path="custInfo.livingStatusDesc" id="livingStatusDesc" maxlength="300" rows="5" class="textarea-style required" style="width:710px" htmlEscape="false" onkeyup="adjustTextareaLength('livingStatusDesc','pre')" />
                    <pre class="pre-style required" style="width:710px" id="pre"></pre>
                    <pre class="pre-style required" style="width:710px" id="preRemarks"></pre>
                    <font color="red">*</font>
                </td>
            </tr>
            <tr>
                <td class="ft_label">个人年收入：</td>
                <td class="ft_content">
                    <form:input path="custInfo.perAnnualIncome" id="perAnnualIncome1" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" class="input-medium number required" />
                    <font color="red">*</font>
                </td>
                <td class="ft_label">收入来源：</td>
                <td class="ft_content" colspan="3">
                    <form:input path="custInfo.sourceOfIncome" maxlength="100" class="input-large required" style="width:80%;" />
                    <font color="red">*</font>
                </td>
            </tr>
            <tr>
                <td class="ft_label">紧急联系人：</td>
                <td class="ft_content">
                    <form:input path="custInfo.energentName" maxlength="20" class="input-medium" />
                </td>
                <td class="ft_label">紧急联系电话：</td>
                <td class="ft_content">
                    <form:input path="custInfo.energentMobileNum" maxlength="11" class="input-medium mobile" />
                </td>
                <td class="ft_label">是否有企业：</td>
                <td class="ft_content">
                    <form:select path="custInfo.isHaveCompany" id="isHaveCompany" class="input-medium required">
                        <form:option value="" label="" />
                        <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
                    </form:select>
                    <font color="red">*</font>
                </td>
            </tr>
            <tr>
                <td class="ft_label">所属行业：</td>
                <td class="ft_content" colspan="5">
                    <form:select id="categoryMain1" path="custInfo.categoryMain" class="input-small" onchange="mainLoadCategory('categoryMain1','categoryLarge1','');validateMsg('categoryMain1');">
                        <form:option value="" label="" />
                        <form:options items="${categoryMainList}" itemLabel="induName" itemValue="induCode" htmlEscape="false" />
                    </form:select>
                    门类
                    <span class="help-inline"> </span>
                    <form:select id="categoryLarge1" path="custInfo.categoryLarge" class="input-small" onchange="mainLoadCategory('categoryLarge1','categoryMedium1','');validateMsg('categoryLarge1');">
                        <form:option value="" label="" />
                        <form:options items="${categoryLargeList}" itemLabel="induName" itemValue="induCode" htmlEscape="false" />
                    </form:select>
                    大类
                    <span class="help-inline"> </span>
                    <form:select id="categoryMedium1" path="custInfo.categoryMedium" class="input-small" onchange="mainLoadCategory('categoryMedium1','categorySmall1','');validateMsg('categoryMedium1');">
                        <form:option value="" label="" />
                        <form:options items="${categoryMediumList}" itemLabel="induName" itemValue="induCode" htmlEscape="false" />
                    </form:select>
                    中类
                    <span class="help-inline"> </span>
                    <form:select id="categorySmall1" path="custInfo.categorySmall" class="input-small" onchange="validateMsg('categorySmall1');">
                        <form:option value="" label="" />
                        <form:options items="${categorySmallList}" itemLabel="induName" itemValue="induCode" htmlEscape="false" />
                    </form:select>
                    小类
                </td>
            </tr>
            <tr>
                <td class="ft_label">备注：</td>
                <td class="ft_content" colspan="5">
                    <form:textarea path="custInfo.remarks" id="remarks" rows="5" maxlength="1000" class="textarea-style" htmlEscape="false" style="width:710px" onkeyup="adjustTextareaLength('remarks','preRemarks')" />
                </td>
            </tr>
        </table>
        <h3 class="searchTitle">担保人工作信息</h3>
        <div class="searchCon">
            <form:hidden path="id" />
            <input type="hidden" name="currApplyNo" value="${applyNo}" />
            <input type="hidden" name="custId" value="${custId}" />
            <sys:message content="${message}" />
            <table class="fromTable filter">
                <tr>
                    <td class="ft_label">单位名称：</td>
                    <td class="ft_content">
                        <form:input path="companyName" onblur="this.value=reSpaces(this.value);" htmlEscape="false" maxlength="50" class="input-medium required" />
                        <font color="red">*</font>
                    </td>
                    <td class="ft_label">单位性质：</td>
                    <td class="ft_content">
                        <form:select path="comNature" class="input-medium">
                            <form:option value="" label="" />
                            <form:options items="${fns:getDictList('COMPANY_PROPERTY')}" itemLabel="label" itemValue="value" htmlEscape="false" />
                        </form:select>
                    </td>
                </tr>
                <tr>
                    <td class="ft_label">单位地址：</td>
                    <td class="ft_content" colspan="5">
                        <form:select path="companyProvince" class="input-small" data-code="-1"></form:select>
                        &nbsp;省
                        <form:select path="companyCity" class="input-small" data-code="-1"></form:select>
                        &nbsp;市
                        <form:select path="companyDistinct" class="input-small" data-code="-1"></form:select>
                        &nbsp;区&nbsp;
                        <span style="width: 15px; display: inline-block;"></span>
                        地址明细：
                        <input type="text" name="companyDetails" id="companyDetails" value="${guarantorWorkInfo.companyDetails}" class="input-medium" maxlength="200" style="margin: 0px; position: relative; vertical-align: middle;" />
                    </td>
                </tr>
                <tr>
                    <td class="ft_label">单位电话：</td>
                    <td class="ft_content">
                        <form:input path="comPhoneAr" htmlEscape="false" maxlength="4" class="input-mini number" />
                        （区号）
                    </td>
                    <td class="ft_content">
                        <form:input path="comPhoneSb" htmlEscape="false" style="width:65%" maxlength="8" class="input-mini number" />
                        （总机）
                    </td>
                    <td class="ft_content">
                        <form:input path="comPhoneEx" htmlEscape="false" style="width:65%" maxlength="8" class="input-mini number" />
                        （分机）
                    </td>
                </tr>
                <tr>
                    <td class="ft_label">职位类别：</td>
                    <td class="ft_content">
                        <form:select path="postType" class="input-medium">
                            <form:option value="" label="" />
                            <form:options items="${fns:getDictList('POST_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
                        </form:select>
                    </td>
                    <td class="ft_label">职位级别：</td>
                    <td class="ft_content">
                        <form:select path="postLevel" class="input-medium">
                            <form:option value="" label="" />
                            <form:options items="${fns:getDictList('POST_LEVEL')}" itemLabel="label" itemValue="value" htmlEscape="false" />
                        </form:select>
                    </td>
                    <td class="ft_label">职位名称：</td>
                    <td class="ft_content">
                        <form:input path="postName" htmlEscape="false" maxlength="30" class="input-medium" />
                    </td>
                </tr>
                <tr>
                    <td class="ft_label">本单位入职年限(年)：</td>
                    <td class="ft_content">
                        <input id="workInDate" name="workInDate" type="text" maxlength="3" class="input-medium number" value="${guarantorWorkInfo.workInDate}" />
                    </td>
                </tr>
                <tr>
                    <td class="searchButton" colspan="6" style="text-align: right;">
                        <c:if test="${readOnly ne '0' }">
                            <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />&nbsp;
                        </c:if>
                        <input id="btnCancel" class="btn btn-primary" type="button" value="返 回" onclick="closeJBox();" />
                        &nbsp;
                    </td>
                </tr>
            </table>
            </form:form>
        </div>
    </div>
</div>
</body>
</html>