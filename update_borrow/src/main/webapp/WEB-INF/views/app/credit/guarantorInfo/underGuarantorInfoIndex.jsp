<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
<title>担保信息</title>
<meta name="decorator" content="default"/>
<script type="text/javascript">
    $(document).ready(function () {
        $.loadDiv("guarantorInfoList", "${ctx}/credit/guarantorInfo/list", {
            applyNo : '${actTaskParam.applyNo}',
            taskDefKey : '${actTaskParam.taskDefKey}'
        }, "post");

        $.loadDiv("guarantorCompanyList", "${ctx}/credit/creGuaranteeCompany/ApplyGuananteeList", {
            applyNo: '${actTaskParam.applyNo}',
            taskDefKey: '${actTaskParam.taskDefKey}'
        }, "post");
    });
</script>
</head>
<body>
<div style="height: 1000px">
<input type="hidden" id="applyNo" value="${actTaskParam.applyNo}"/>
<div id="guarantorInfoList"></div>
<hr class="solid-line"/>
<div id="guarantorCompanyList"></div>
</div>
</body>
</html>
