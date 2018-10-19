<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>查账申请信息</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
    $(document).ready(function () {
        $("#checkAccountApplyForm").validate({
            submitHandler : saveForm,
            errorContainer : "#messageBox",
            errorPlacement : function(error, element) {
                checkReq(error, element);
            }
        });
    });

    function checkAccountClick() {
        $("#checkAccountId").toggle(600);
    }

    function checkAccountApplyClick() {
        $("#checkAccountApplyId").toggle(600);
    }

    function saveApply() {
        saveInit();
        var formJson = $("#checkAccountApplyForm").serializeJson();
        $.post("${ctx}/accounting/checkAccount/saveApply", formJson, function (data) {
            if (data) {
                saveUnlock();
                alertx(data.message);
            }
        });
    }

    function saveForm() {
        saveInit();
        var formJson = $("#checkAccountApplyForm").serializeJson();
        $.post("${ctx}/accounting/checkAccount/submitApply",
            formJson, function (data) {
                if (data) {
                    saveUnlock();
                    alertx(data.message);
                    $("#btnBack").click();
                }
            });
    }

    function saveInit() {
        $("#btnSubmit").attr("disabled", true);
        $("#btnBack").attr("disabled", true);
        $("#btnSave").attr("disabled", true);
        var checkAmount = $("#checkAmount").val().replace(/,/g, "");
        $("#checkAmount").val(checkAmount);
        document.getElementById("contractNumber").value = $("#contractNo").val();
    }

    function saveUnlock() {
        $("#btnSubmit").removeAttr("disabled");
        $("#btnBack").removeAttr("disabled");
        $("#btnSave").removeAttr("disabled");
        document.getElementById("checkAmount").value = outputmoney($("#checkAmount").val());
    }

    function queryBankCode() {
        $.post("${ctx}/accounting/checkAccount/queryBankCode", {
            bankName : $("#outAccountBankName").val()
        }, function (data) {
            if (data.status === "1") {
                $("#outAccountBankCode").val(data.message);
            } else {
                alertx(data.message);
            }
        });
    }
</script>
</head>
<body>
<div id="checkAccountDiv" class="searchInfo">
    <h3 onclick="checkAccountClick()" class="searchTitle">合同信息</h3>
    <div id="checkAccountId" class="searchCon">
        <form:form id="checkAccountForm" method="post" modelAttribute="checkAccount" class="form-horizontal">
            <table class="fromTable filter">
                <tr>
                    <td class="ft_label">借款人名称</td>
                    <td class="ft_content">
                        <form:input path="borrowName" class="input-medium" readonly="true" />
                    </td>
                    <td class="ft_label">合同编号</td>
                    <td class="ft_content">
                        <form:input path="contractNo" class="input-medium" readonly="true" />
                    </td>
                    <td class="ft_label">合同金额（元）</td>
                    <td class="ft_content">
                        <form:input path="contractAmount" class="input-medium" readonly="true" />
                    </td>
                </tr>
                <tr>
                    <td class="ft_label">借款人类型</td>
                    <td class="ft_content">
                        <form:input path="loanType" class="input-medium" readonly="true" />
                    </td>
                    <td class="ft_label">产品名称</td>
                    <td class="ft_content">
                        <form:input path="productName" class="input-medium" readonly="true" />
                    </td>
                    <td class="ft_label">还款状态</td>
                    <td class="ft_content">
                        <form:input path="repayStatus" class="input-medium" readonly="true" />
                    </td>
                </tr>
                <tr>
                    <td class="ft_label">借款人手机号</td>
                    <td class="ft_content">
                        <form:input path="borrowMobile" class="input-medium" readonly="true" />
                    </td>
                    <td class="ft_label">企业名称</td>
                    <td class="ft_content">
                        <form:input path="customName" class="input-medium" readonly="true" />
                    </td>
                </tr>
            </table>
        </form:form>
    </div>
</div>
<div id="checkAccountApplyDiv" class="searchInfo">
    <h3 onclick="checkAccountApplyClick()" class="searchTitle">申请查账信息</h3>
    <div id="checkAccountApplyId" class="searchCon">
        <form:form id="checkAccountApplyForm" method="post" modelAttribute="checkAccountApply"  class="form-horizontal">
            <form:hidden path="id" />
            <form:hidden path="contractNumber" />
            <table class="fromTable filter">
                <tr>
                    <td class="ft_label">借款人名称</td>
                    <td class="ft_content">
                        <form:input path="loanName" class="input-medium required" readonly="true"/>
                    </td>
                    <td class="ft_label">交易日期</td>
                    <td class="ft_content">
                        <input id="tradeDate" name="tradeDate" class="input-medium required Wdate" type="text" maxlength="20"
                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false})"
                               value="<fmt:formatDate value="${checkAccountApply.tradeDate}" pattern="yyyy-MM-dd HH:mm" />"/>
                    </td>
                    <td class="ft_label">查账金额</td>
                    <td class="ft_content">
                        <form:input path="checkAmount" class="input-medium required" maxlength="10"
                        onblur="this.value=outputmoney(this.value);" onkeyup="keyPress11(this);"/>
                    </td>
                </tr>
                <tr>
                    <td class="ft_label">实际打款户名</td>
                    <td class="ft_content">
                        <form:input path="outAccountName" class="input-medium required" maxlength="30" />
                    </td>
                    <td class="ft_label">打款银行</td>
                    <td class="ft_content">
                        <form:hidden path="outAccountBankCode" />
                        <form:input path="outAccountBankName" class="input-medium required" onchange="queryBankCode()" maxlength="30" />
                    </td>
                    <td class="ft_label">打款银行卡号</td>
                    <td class="ft_content">
                        <form:input path="outAccountNumber" class="input-medium required" maxlength="6"
                            onkeyup="this.value=this.value.replace(/[^\d]/g,'')"/>
                    </td>
                </tr>
            </table>
            <div class="searchButton" style="float: right">
                <input id="btnBack" class="btn btn-primary" type="button" onclick="window.location.href='${ctx}/accounting/checkAccount/list'" value="返回">
                &nbsp;
                <input id="btnSave" class="btn btn-primary" type="button" onclick="saveApply()" value="保存" />
                &nbsp;
                <input id="btnSubmit" class="btn btn-primary" type="submit" value="提交" />
            </div>
        </form:form>
    </div>
</div>
</body>
</html>
