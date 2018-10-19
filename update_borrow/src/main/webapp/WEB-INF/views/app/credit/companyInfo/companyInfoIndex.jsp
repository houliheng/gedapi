<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>批量借款企业管理</title>
    <meta name="decorator" content="default" />
    <script type="text/javascript">

        $(document).ready(function() {

            <!--担保信息-->
            $.loadDiv("creCompanyInfoList", "${ctx}/credit/creApplyCompanyRelation/queryCompanyList", {
                applyNo : '${actTaskParam.applyNo}',
                taskDefKey : '${actTaskParam.taskDefKey}'
            }, "post");
        });
    </script>
</head>
<body>
<input type="hidden" id="applyNo" value="${actTaskParam.applyNo}" />
<div   id="creCompanyInfoList"   ></div>

<div style="width: 100%; height: 50px; ">
</div>
<div style="width: 100%; height: 50px; ">
</div>
<div style="width: 100%; height: 50px; ">
</div><div style="width: 100%; height: 50px; ">
</div><div style="width: 100%; height: 50px; ">
</div><div style="width: 100%; height: 50px; ">
</div><div style="width: 100%; height: 50px; ">
</div><div style="width: 100%; height: 50px; ">
</div><div style="width: 100%; height: 50px; ">
</div><div style="width: 100%; height: 50px; ">
</div><div style="width: 100%; height: 50px; ">
</div><div style="width: 100%; height: 50px; ">
</div><div style="width: 100%; height: 50px; ">
</div>
</body>
</body>
</html>
