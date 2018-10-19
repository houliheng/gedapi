<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
<title>借款信息</title>
<meta name="decorator" content="default"/>
<script type="text/javascript">
    $(document).ready(function () {
        $("#checkApproveForm").validate({
            submitHandler: saveForm,
            errorContainer: "#messageBox",
            errorPlacement: function (error, element) {
                checkReq(error, element);
            }
        });

        init();

        //还款计划表
        $.loadDiv("repayPlanList", "${ctx }/credit/repayPlan/getRepayPlanByContractNo", {
            applyNo: "${actTaskParam.applyNo}",
            taskDefKey: "under_dqglr"
        }, "post");
    });
</script>
<script type="text/javascript">
    /**
     * 借款信息部分函数
     */
    function jkxxClick() {
        $("#pfxxId").toggle(600);
    }

    function init() {
        $("#riskReserve").val(outputmoney("0"));
        calcReturnServiceFee();
        outputElement();
        calcProductTypeName();
    }

    function saveForm() {
        $("#btnSubmit").attr("disabled", "true");
        loading();
        replaceElement();
        var formJson = $("#checkApproveForm").serializeJson();
        $.post("${ctx}/credit/checkApprove/saveUnderCheckApprove", formJson, function (data) {
            if (data) {
                $("#btnSubmit").removeAttr("disabled");
                closeTip();
                if (data.status == 1) {
                    var id = data.id;
                    $("#checkApproveForm input[id=id]").val(id);
                    alertx(data.message);
                } else {
                    alertx(data.message);
                }
            }
            outputElement();
        });
    }

    function replaceElement() {
        var contractAmountValue = $("#contractAmount").val().replace(/,/g, "");
        $("#contractAmount").val(contractAmountValue);
        var riskReserveValue = $("#riskReserve").val().replace(/,/g, "");
        $("#riskReserve").val(riskReserveValue);
        var guaranteeFeeValue = $("#guaranteeFee").val().replace(/,/g, "");
        $("#guaranteeFee").val(guaranteeFeeValue);
        var serviceFeeValue = $("#serviceFee").val().replace(/,/g, "");
        $("#serviceFee").val(serviceFeeValue);
        var marginAmountValue = $("#marginAmount").val().replace(/,/g, "");
        $("#marginAmount").val(marginAmountValue);
        var returnServiceFeeValue = $("#returnServiceFee").val().replace(/,/g, "");
        $("#returnServiceFee").val(returnServiceFeeValue);
    }

    function outputElement() {
        document.getElementById("contractAmount").value = outputmoney($("#contractAmount").val());
        document.getElementById("riskReserve").value = outputmoney($("#riskReserve").val());
        document.getElementById("guaranteeFee").value = outputmoney($("#guaranteeFee").val());
        document.getElementById("marginAmount").value = outputmoney($("#marginAmount").val());
        document.getElementById("serviceFee").value = outputmoney($("#serviceFee").val());
        document.getElementById("returnServiceFee").value = outputmoney($("#returnServiceFee").val());
    }

    function getApproLoanRepayType() {
        $("#approLoanRepayType").empty();
        $("#approLoanRepayType").append("<option value=''>请选择</option>");
        var $approLoanRepayType = $("#s2id_approLoanRepayType>.select2-choice>.select2-chosen");
        $approLoanRepayType.html("请选择");

        $.post("${ctx}/credit/checkApprove/getApproLoanRepayTypelist", {
            productType: $("#approProductTypeCode").val()
        }, function (data) {
            $.each(data, function (i, val) {
                $("#approLoanRepayType").append("<option value='" + val["loanRepayType"] + "'>" + val["loanRepayDesc"] + "</option>");
            });
        });
    }

    function setPeriodValue() {
        var periodVal = $("#approPeriodId").find("option:selected").text();
        $("#approPeriodValue").val(periodVal);
    }

    function calcProductTypeName() {
        var productName = $("#approProductTypeCode").find("option:selected").text();
        $("#approProductTypeName").val(productName);
    }

    function calcReturnServiceFee() {
        var returnServiceFeeFlag = $("#returnServiceFeeFlag").val();
        var returnServiceFee = $("#returnServiceFee");
        if (returnServiceFeeFlag === '0') {
            returnServiceFee.val(outputmoney("0"));
            returnServiceFee.attr("disabled", "true");
        } else {
            returnServiceFee.removeAttr("disabled");
        }
    }

    function getYearRateInterest() {
        var productType = $("#approProductTypeCode").val();
        var periodValue = $("#approPeriodId").val();
        var loanRepayType = $("#approLoanRepayType").val();
        $.post("${ctx}/credit/checkApprove/getLoanYearRateInterest", {
            productType : productType,
            periodValue : periodValue,
            loanType : loanRepayType
        }, function (data) {
            var rate = gdpMoney(data);
            $("#guanetongRate").val(rate);
        });
    }

    /**
     * 机构信息部分
     */
    function jgxxClick() {
        $("#jgxxId").toggle(600);
    }

    function saveOrgInfo() {
        $("#orgInfoSubmit").attr("disabled", "true");
        var formJson = $("#underOrgInfoForm").serializeJson();
        $.post("${ctx}/credit/underCompanyInfo/saveUnderOrgInfo", formJson, function (data) {
            if (data) {
                $("#orgInfoSubmit").removeAttr("disabled");
                alertx(data.message);
            }
        });
    }

    /**
     * 生成还款计划
     */
    function createRepayPlan() {
        $("#createRepayPlan").attr("disabled", "true");
        $.post("${ctx}/credit/repayPlan/createUnderRepayPlan", {
            applyNo: "${actTaskParam.applyNo}",
            taskDefKey: "under_dqglr"
        }, function (data) {
            if (data) {
                $("#createRepayPlan").removeAttr("disabled");
                if (data.status === 1) {
                    alertx(data.message);
                } else {
                    alertx(data.message);
                }
            }
        });
        $("#repayPlanList").empty();
        $.loadDiv("repayPlanList", "${ctx }/credit/repayPlan/getRepayPlanByContractNo", {
            applyNo: "${actTaskParam.applyNo}",
            taskDefKey: "under_dqglr"
        }, "post");
    }
</script>
</head>
<body>
<div id="checkApprovInfoDiv" class="searchInfo">
    <h3 onclick="jkxxClick()" class="searchTitle">借款信息</h3>
    <div id="jkxxId" class="searchCon">
        <form:form id="checkApproveForm" modelAttribute="underCheckApprove"
               action="${ctx}/credit/checkApprove/saveUnderCheckApprove" method="post" class="form-horizontal">
            <form:hidden path="id"/>
            <input type="hidden" id="taskDefKey" value="${actTaskParam.taskDefKey}"/>
            <%@ include file="/WEB-INF/views/app/credit/workflow/include/actTaskParams.jsp" %>
            <sys:message content="${message}"/>
            <table class="fromTable filter">
                <tr>
                    <td class="ft_label">借款主体类型</td>
                    <td class="ft_content">
                        <form:select path="loanMainBodyType" class="input-medium" cssStyle="width:164px;">
                            <form:option value="1">个人</form:option>
                            <form:option value="2">企业</form:option>
                        </form:select>
                    </td>
                    <td class="ft_label">
                        <span style="color:#FF0000">*</span>
                        贷款类型
                    </td>
                    <td class="ft_content">
                        <form:select path="approProductTypeCode" class="input-medium required" cssStyle="width:164px;"
                                     onchange="getApproLoanRepayType();calcProductTypeName();">
                            <form:option value="1">信用借款</form:option>
                            <form:option value="7">采购贷</form:option>
                        </form:select>
                        <form:hidden path="approProductTypeName"/>
                    </td>
                    <td class="ft_label">
                        <span style="color:#FF0000">*</span>
                        线下合同编号
                    </td>
                    <td class="ft_content">
                        <form:input path="underContractNo" class="input-medium required"/>
                    </td>
                </tr>
                <tr>
                    <td class="ft_label">
                        <span style="color:#FF0000">*</span>
                        合同金额(元)
                    </td>
                    <td class="ft_content">
                        <form:input path="contractAmount" class="input-medium required"
                                    onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);"/>
                    </td>
                    <td class="ft_label">
                        <span style="color:#FF0000">*</span>
                        合同年利率(%)
                    </td>
                    <td class="ft_content">
                        <form:input path="contractYearRate" class="input-medium required"
                                    onkeyup="gdpMax(this)" onblur="this.value=gdpMoney(this.value);"/>
                    </td>
                    <td class="ft_label">
                        <span style="color:#FF0000">*</span>
                        合同期数
                    </td>
                    <td class="ft_content">
                        <form:select path="approPeriodId" class="input-medium  required"
                                     onchange="setPeriodValue();getYearRateInterest()">
                            <form:option value="" label=""></form:option>
                            <form:options items="${fns:getDictList('PRODUCT_PERIOD_VALUE')}" itemLabel="label"
                                          itemValue="value" htmlEscape="false"/>
                        </form:select>
                        <form:hidden path="approPeriodValue"/>
                    </td>
                </tr>
                <tr>
                    <td class="ft_label">
                        <span style="color:#FF0000">*</span>
                        合同还款方式
                    </td>
                    <td class="ft_content">
                        <form:select path="approLoanRepayType" class="input-medium required" onchange="getYearRateInterest()">
                            <form:option value="" label=""/>
                            <form:options items="${loanRepayTypeList}" itemLabel="loanRepayDesc"
                                          itemValue="loanRepayType" htmlEscape="false"/>
                        </form:select>
                    </td>
                    <td class="ft_label">
                        <span style="color:#FF0000">*</span>
                        标的年利率(%)
                    </td>
                    <td class="ft_content">
                        <form:input path="guanetongRate" class="input-medium required"
                                    onkeyup="gdpMax(this)" onblur="this.value=gdpMoney(this.value);"/>
                    </td>
                </tr>
                <tr>
                    <td class="ft_label">
                        标的还款方式
                    </td>
                    <td class="ft_content">
                        <form:select path="subjectRepayType" class="input-medium required" cssStyle="width:164px;">
                            <form:option value="1">等额等息</form:option>
                            <form:option value="2">先息后本</form:option>
                            <form:option value="7">还本付息</form:option>
                            <form:option value="8">一次性还本付息</form:option>
                        </form:select>
                    </td>
                    <td class="ft_label">
                        <span style="color:#FF0000">*</span>
                        风险备用金(元)
                    </td>
                    <td class="ft_content">
                        <form:input path="riskReserve" class="input-medium required"
                                    onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);"/>
                    </td>
                </tr>
                <tr>
                    <td class="ft_label">
                        <span style="color:#FF0000">*</span>
                        服务费总费率(%)
                    </td>
                    <td class="ft_content">
                        <form:input path="serviceFeeRate" class="input-medium required"
                                    onkeyup="rateMax(this)" onblur="this.value=rateMoney(this.value);"/>
                    </td>
                    <td class="ft_label">
                        <span style="color:#FF0000">*</span>
                        保证金费率(%)
                    </td>
                    <td class="ft_content">
                        <form:input path="marginRate" class="input-medium required"
                                    onkeyup="gdpMax(this)" onblur="this.value=gdpMoney(this.value);"/>
                    </td>
                    <td class="ft_label">
                        <span style="color:#FF0000">*</span>
                        应收担保费(元)
                    </td>
                    <td class="ft_content">
                        <form:input path="guaranteeFee" class="input-medium required"
                                    onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);"/>
                    </td>
                </tr>
                <tr>
                    <td class="ft_label">
                        <span style="color:#FF0000">*</span>
                        应收服务费(元)
                    </td>
                    <td class="ft_content">
                        <form:input path="serviceFee" class="input-medium required"
                                    onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);"/>
                    </td>
                    <td class="ft_label">
                        <span style="color:#FF0000">*</span>
                        应收保证金(元)
                    </td>
                    <td class="ft_content">
                        <form:input path="marginAmount" class="input-medium required"
                                    onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);"/>
                    </td>
                    <td class="ft_label">
                        <span style="color:#FF0000">*</span>
                        是否要返还服务费
                    </td>
                    <td class="ft_content">
                        <form:select path="returnServiceFeeFlag" class="input-medium required"
                                     onchange="calcReturnServiceFee();" cssStyle="width:164px;">
                            <form:option value="0">否</form:option>
                            <form:option value="1">是</form:option>
                        </form:select>
                    </td>
                </tr>
                <tr>
                    <td class="ft_label">
                        <span style="color:#FF0000">*</span>
                        返还服务费金额(元)
                    </td>
                    <td class="ft_content">
                        <form:input path="returnServiceFee" class="input-medium required"
                                    onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);"/>
                    </td>
                    <td class="ft_label">
                        <span style="color:#FF0000">*</span>
                        放款方式
                    </td>
                    <td class="ft_content">
                        <form:select path="serviceFeeType" class="input-medium required" cssStyle="width:164px;">
                            <form:option value="1">放款时一次性收取</form:option>
                            <form:option value="2">分期收取</form:option>
                        </form:select>
                    </td>
                    <td class="ft_label">
                        <span style="color:#FF0000">*</span>
                        签约日期
                    </td>
                    <td class="ft_content">
                        <input id="signDate" name="signDate" class="input-medium required Wdate" type="text" maxlength="20"
                               onclick="WdatePicker({onpicked:dateWhite(this),maxDate:new Date()})"
                               value="<fmt:formatDate value="${underCheckApprove.signDate}" pattern="yyyy-MM-dd" />"/>
                    </td>
                </tr>
                <tr>
                    <td class="ft_label">
                        <span style="color:#FF0000">*</span>
                        借款用途
                    </td>
                    <td class="ft_content">
                        <form:select path="loanUsefulnessCode" class="input-medium required">
                            <form:option value=""></form:option>
                            <form:options items="${fns:getDictList('LOAN_PURPOST')}" itemLabel="label" itemValue="value" htmlEscape="false" />
                        </form:select>
                    </td>
                    <td class="ft_content" colspan="5">
                        <form:textarea path="loanUsefulness" rows="4" maxlength="1500" class="textarea-style required"
                                       style="width:420px;"/>
                    </td>
                </tr>
            </table>
            <div class="searchButton" id="buttonDiv">
                <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
            </div>
        </form:form>
    </div>
</div>
<hr class="solid-line"/>
<div id="orgInfo">
    <h3 onclick="jgxxClick()" class="searchTitle">机构信息</h3>
    <div id="jgxxId" class="searchCon">
        <form:form id="underOrgInfoForm" modelAttribute="underOrgInfo" method="post" class="form-horizontal">
            <form:hidden path="id"/>
            <table class="fromTable filter">
                <tr>
                    <td class="ft_label">
                        <span style="color:#FF0000">*</span>
                        分公司名称
                    </td>
                    <td class="ft_content">
                        <form:input path="gqCompanyName" class="input-medium required"/>
                    </td>
                    <td class="ft_label">
                        <span style="color:#FF0000">*</span>
                        所属大区
                    </td>
                    <td class="ft_content">
                        <form:input path="gqAreaName" class="input-medium required"/>
                    </td>
                    <td class="ft_label">
                        <span style="color:#FF0000">*</span>
                        债权所属公司
                    </td>
                    <td class="ft_content">
                        <form:select path="loanBeCompany" class="input-medium" cssStyle="width:164px;">
                            <form:option value="1">北京</form:option>
                            <form:option value="2">上海</form:option>
                            <form:option value="3">天津</form:option>
                        </form:select>
                    </td>
                </tr>
            </table>
            <div class="searchButton">
                <input id="orgInfoSubmit" class="btn btn-primary" type="submit" value="保 存"
                    onclick="saveOrgInfo()"/>
            </div>
        </form:form>
    </div>
</div>
<hr class="solid-line"/>
<div id="repayPlanList"></div>
<c:if test="${actTaskParam.taskDefKey == 'under_dqglr'}">
    <input id="createRepayPlan" class="btn btn-primary" style="width: 120px" type="submit" value="生成还款计划"
        onclick="createRepayPlan();"/>
</c:if>
</body>
</html>