<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>线下录入主页</title>
    <base target="_self">
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        //初始化最初展示的Tab页面
        $(document).ready(function () {
            showUnderCompanyTab('${actTaskParam.status == 1}');
            console.log()
        });
    </script>
</head>
<body>
<%@ include file="/WEB-INF/views/app/credit/workflow/include/workflowScript.jsp" %>
<div class="wrapper">
    <%@ include file="/WEB-INF/views/app/credit/workflow/include/workflowButtons.jsp" %>
    <sys:message content="${message}"/>
    <div style="margin-top: 5px;">
        <ul class="nav nav-tabs" id="mainTabs">
            <li class="">
                <a href="#tab_underCompanyInfo" onclick="showUnderCompanyTab('${actTaskParam.status == 2}')">客户信息</a>
            </li>
            <li class="line"></li>
            <li class="">
                <a href="#tab_loanInfo" onclick="showLoanInfoTab('${actTaskParam.status == 1}')">借款信息</a>
            </li>
            <li class="line"></li>
            <li class="">
                <a href="#tab_underMortgageCarInfo"
                   onclick="showUnderMortgageCarEvaluateInfoTab('${actTaskParam.status == 2}')">资产信息</a>
            </li>
            <li class="line"></li>
            <li class="">
                <a href="#tab_underGuarantorInfo" onclick="showUnderGuarantorInfoTab('${actTaskParam.status == 1}')">担保信息</a>
            </li>
            <li class="line"></li>
            <li class="">
            <a href="#tab_repaySource" onclick="showRepaySourceTab('${actTaskParam.status == 1}')">还款来源</a>
            </li>
            <li class="line"></li>
            <li class="">
                <a href="#tab_underConclusion" onclick="showUnderConclusionTab('${actTaskParam.status == 1}')">录入结论</a>
            </li>
        </ul>
    </div>
    <div class="tab-content">
        <div class="tab-pane" id="tab_underCompanyInfo"></div>
        <div class="tab-pane" id="tab_loanInfo"></div>
        <div class="tab-pane" id="tab_underMortgageCarInfo"></div>
        <div class="tab-pane" id="tab_underGuarantorInfo"></div>
        <div class="tab-pane" id="tab_repaySource"></div>
        <div class="tab-pane" id="tab_underConclusion"></div>
    </div>
</body>
</html>