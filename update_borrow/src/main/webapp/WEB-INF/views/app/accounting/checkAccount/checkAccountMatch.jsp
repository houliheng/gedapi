<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>查账申请信息</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
    $(document).ready(function () {
        $("#resultForm").validate({
            submitHandler : function (form) {
                var str = getCheckBoxVal();
                var radioVal = $("input[name='passFlag']:checked").val();
                var message = ("true" === radioVal) ? "流程提交后，各信息将不能再做修改，确认要提交吗？" : "确信要废弃本申请吗？";
                if (str !== "" || "false" === radioVal) {
                    confirmx(message, function() {
                        var param = $("#resultForm").serializeJson();
                        $.post("${ctx}/accounting/checkAccountVerify/submit", {
                            passFlag : radioVal,
                            suggestion : $("#suggestion").val(),
                            ids : str,
                            applyId : $("#id").val()
                        }, function(data) {
                            if(data){
                                closeTip();
                                if (data.status === '1') {
                                    $("#rs_msg").html("<div id='messageBox' class='alert alert-success'><button data-dismiss='alert' class='close'>×</button>提交成功！页面即将关闭...</div>");
                                    $("#checkAccountBackId").click();
                                } else {
                                    $("#rs_msg").html("<div id='messageBox' class='alert alert-error'><button data-dismiss='alert' class='close'>×</button>" + data.message + "</div>");
                                }
                            }
                        });
                    });
                } else {
                    alertx("请选择对应的银行流水");
                }
            }
        });
    });

    function checkAccountClick() {
        $("#checkAccountId").toggle(600);
    }

    function checkAccountApplyClick() {
        $("#checkAccountApplyId").toggle(600);
    }

    function bankFlowClick() {
        $("#bankFlowId").toggle(600);
    }

    function checkAccountResultClick() {
        $("#checkAccountResultId").toggle(600);
    }

    function selectAll() {
        if ($("[name=rowAll]:checkbox").attr("checked") === "checked") {
            $("[name=rowCheck]:checkbox").attr("checked", true);
        } else {
            $("[name=rowCheck]:checkbox").attr("checked", false);
        }
    }

    function getCheckBoxVal() {
        var str = "";
        $("input[name=rowCheck]:checkbox").each(function() {
            if ($(this).attr("checked")) {
                str += $(this).val() + ",";
            }
        });
        return str;
    }

    // function selectSingle() {
    //     $("[name=rowCheck]:checkbox").each(function () {
    //         $(this).click(function() {
    //             if ($(this).attr("checked") !== "checked") {
    //             }
    //         });
    //         if (rowAllFlag) {
    //             $("[name=rowAll]:checkbox").attr("checked", true);
    //         } else {
    //             $("[name=rowAll]:checkbox").removeAttr("checked");
    //         }
    //     });
    //     console.log(rowAllFlag);
    // }
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
            <form:input type="hidden" path="id"/>
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
                               value="<fmt:formatDate value="${checkAccountApply.tradeDate}" pattern="yyyy-MM-dd HH:mm" />"
                        readonly="readonly"/>
                    </td>
                    <td class="ft_label">查账金额</td>
                    <td class="ft_content">
                        <form:input path="checkAmount" class="input-medium required" maxlength="10"
                        onblur="this.value=outputmoney(this.value);" onkeyup="keyPress11(this);"
                        readonly="true"/>
                    </td>
                </tr>
                <tr>
                    <td class="ft_label">实际打款户名</td>
                    <td class="ft_content">
                        <form:input path="outAccountName" class="input-medium required" maxlength="30"
                        readonly="true"/>
                    </td>
                    <td class="ft_label">打款银行</td>
                    <td class="ft_content">
                        <form:input path="outAccountBankName" class="input-medium required" maxlength="30"
                        readonly="true"/>
                    </td>
                    <td class="ft_label">打款银行卡号</td>
                    <td class="ft_content">
                        <form:input path="outAccountNumber" class="input-medium required" maxlength="6"
                        readonly="true"/>
                    </td>
                </tr>
            </table>
        </form:form>
    </div>
</div>
<div id="bankFlowDiv" class="tableList">
    <h3 class="tableTitle" onclick="bankFlowClick();">数据列表</h3>
    <div id="bankFlowId" style="max-height:400px;overflow:auto;">
        <table cellpadding="0" cellspacing="0" border="0"
               class="table table-striped table-bordered table-condensed table-hover">
            <thead>
            <tr>
                <th width="4%">
                    <input type="hidden" onclick="selectAll()" name="rowAll">
                </th>
                <th width="4%">序号</th>
                <th width="6%">交易日期</th>
                <th width="8%">收入金额（元）</th>
                <th width="8%">未入账金额（元）</th>
                <th width="8%">已入账金额（元）</th>
                <th width="12%">对方户名</th>
                <th width="16%">对方账号</th>
                <th width="12%">交易银行</th>
                <th width="16%">摘要</th>
                <th width="6%">状态</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${bankFlowList}" var="flow" varStatus="bankFlowList">
                <tr>
                    <td >
                        <input type="checkbox" value="${flow.id}" name="rowCheck"
                               <%--onclick="selectSingle()"--%>
                               id="r_${bankFlowList.count}">
                    </td>
                    <td class="title" title="${bankFlowList.count}">${bankFlowList.count}</td>
                    <td class="title" title="<fmt:formatDate value="${flow.tradeDate}" pattern="yyyy-MM-dd" />">
                        <fmt:formatDate value="${flow.tradeDate}" pattern="yyyy-MM-dd" />
                    </td>
                    <td class="title" title="${flow.tradeAmount}">${flow.tradeAmount}</td>
                    <td class="title" title="${flow.unAccountAmount}">${flow.unAccountAmount}</td>
                    <td class="title" title="${flow.enterAccountAmount}">${flow.enterAccountAmount}</td>
                    <td class="title" title="${flow.userName}">${flow.userName}</td>
                    <td class="title" title="${flow.accountNumber}">${flow.accountNumber}</td>
                    <td class="title" title="${flow.branchBankName}">${flow.branchBankName}</td>
                    <td class="title" title="${flow.remark}">${flow.remark}</td>
                    <td class="title" title="${flow.status}">
                        <c:if test="${flow.status==0}">
                            未入账
                        </c:if>
                        <c:if test="${flow.status==1}">
                            已入账
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<div id="checkAccountResultDiv" class="searchInfo">
    <h3 onclick="checkAccountResultClick()" class="searchTitle">查账结果</h3>
    <div id="rs_msg"></div>
    <%--<input id="btnBack" class="btn btn-primary" type="button" onclick="window.location.href='${ctx}/accounting/checkAccount/list'" value="返回">--%>
    <a id="checkAccountBackId" onclick="window.location.href='${ctx}/accounting/checkAccountVerify/list'" ></a>
    <form id="resultForm" method="post">
    <div id="checkAccountResultId" class="searchCon">
        <div class="filter">
            <table class="fromTable">
                <tr>
                    <td class="ft_label">
                        <font style="color: red">*</font>
                        查账结果：
                    </td>
                    <td class="" colspan="5">
                        <input type="radio" name="passFlag" value="true" id="radio_yes" class="required">
                        <label for="radio_yes">通过</label>
                        &nbsp;&nbsp;
                        <input type="radio" name="passFlag" value="false" id="radio_no" class="required">
                        <label for="radio_no">拒绝</label>
                    </td>
                </tr>
                <tr>
                    <td class="ft_label">备注：</td>
                    <td class="" colspan="5">
                        <textarea rows="4" cols="100" maxlength="1000" id="suggestion" name="suggestion"
                          class="input-xxlarge textarea-style"
                          onkeyup="adjustTextareaLength('suggestion','pre')"></textarea>
                        <font style="color: red">*</font>
                    </td>
                </tr>
            </table>
            <div class="searchButton">
                <input id="rsBtnSubmit" class="btn btn-primary" type="submit" value="提 交" />
            </div>
        </div>
    </div>
    </form>
</div>
</body>
</html>
