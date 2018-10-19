<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
<title>还款来源</title>
<meta name="decorator" content="default"/>
<script type="text/javascript">
    $(document).ready(function () {
        $("#repaySourceForm").validate({
            submitHandler: saveForm,
            errorContainer: "#messageBox",
            errorPlacement: function (error, element) {
                checkReq(error, element);
            }
        });
    });

    function hklyClick() {
        $("#hklyId").toggle(600);
    }

    function saveForm() {
        $("#btnSubmit").attr("disabled", "true");
        var formJson = $("#repaySourceForm").serializeJson();
        $.post("${ctx}/credit/gqgetComInfo/saveRepaySource", formJson, function (data) {
            if (data) {
                $("#btnSubmit").removeAttr("disabled");
                alertx(data.message);
            }
        });
    }
</script>
</head>
<body>
<div id="repaySourceDiv" class="searchInfo">
<h3 onclick="hklyClick()" class="searchTitle">借款信息</h3>
<div id="hklyId" class="searchCon">
<form:form id="repaySourceForm" modelAttribute="repaySource" method="post" class="form-horizontal">
    <%@ include file="/WEB-INF/views/app/credit/workflow/include/actTaskParams.jsp" %>
    <table class="fromTable filter">
        <form:hidden path="id" />
        <%@ include file="/WEB-INF/views/app/credit/workflow/include/actTaskParams.jsp" %>
        <tr>
            <td class="ft_label">
                <font style="color: red">*</font>
                还款来源1：
            </td>
            <td class="ft_content">
                <form:textarea path="sourceOfDepayment1" htmlEscape="false" rows="4" maxlength="1000" class="textareaWidth textarea-style required checkNumberTwenty" onkeyup="adjustTextareaLength('sourceOfDepayment1', 'preSourceOfDepayment1');" />
                <br>
                <font style="color: red">最少输入20字，最多可输入1000字</font>
            </td>
        </tr>
        <tr>
            <td class="ft_label">还款来源2：</td>
            <td class="ft_content">
                <form:textarea path="sourceOfDepayment2" htmlEscape="false" rows="4" maxlength="1000" class="textareaWidth textarea-style checkNumberTwenty" onkeyup="adjustTextareaLength('sourceOfDepayment2', 'preSourceOfDepayment2');" />
                <br>
                <font style="color: red">最少输入20字，最多可输入1000字</font>
            </td>
        </tr>
        <tr>
            <td class="ft_label">还款来源3：</td>
            <td class="ft_content">
                <form:textarea path="sourceOfDepayment3" htmlEscape="false" rows="4" maxlength="1000" class="textareaWidth textarea-style checkNumberTwenty" onkeyup="adjustTextareaLength('sourceOfDepayment3', 'preSourceOfDepayment3');" />
                <br>
                <font style="color: red">最少输入20字，最多可输入1000字</font>
            </td>
        </tr>
        <tr>
            <td class="ft_label">还款来源4：</td>
            <td class="ft_content">
                <form:textarea path="sourceOfDepayment4" htmlEscape="false" rows="4" maxlength="1000" class="textareaWidth textarea-style checkNumberTwenty" onkeyup="adjustTextareaLength('sourceOfDepayment4', 'preSourceOfDepayment4');" />
                <br>
                <font style="color: red">最少输入20字，最多可输入1000字</font>
            </td>
        </tr>
    </table>
    <div class="searchButton" id="buttonDiv">
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
    </div>
    </form:form>
    </div>
</div>
</body>
</html>
