<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>新增批量借款企业</title>
    <meta name="decorator" content="default" />
    <script type="text/javascript">

        $(document).ready(function() {
            <!--借款企业信息-->
            $.loadDiv("piliCompanyList", "${ctx}/credit/creApplyCompanyRelation/selectCompanyByCustId", {
                id : '${custId}',
            }, "post");

            $.loadDiv("guarantorInfoList", "${ctx}/credit/creApplyCompanyRelation/selectCustInfo", {
                custId : '${custId}',
                applyNo : '${applyNo}',
                readOnly :'${readOnly}',
                taskDefKey : '${taskDefKey}'
            }, "post");

            $.loadDiv("guarantorCompanyInfo", "${ctx}/credit/guarantorCompanyInfo/guaranteeList", {
                custId : '${custId}',
                applyNo : '${applyNo}',
                readOnly :'${readOnly}',
                taskDefKey : '${taskDefKey}'
            }, "post");

            <!--担保信息-->
             $.loadDiv("guarantorCompanyList", "${ctx}/credit/creApplyCompanyRelation/selectCompany", {
                custId : '${custId}',
                applyNo : '${applyNo}',
                 readOnly :'${readOnly}',
                 taskDefKey : '${taskDefKey}'
            }, "post") ;
        });
    </script>
</head>
<body>
<input type="hidden" id="applyNo" value="${applyNo}" />

<input type="hidden" id="custId" value="${custId}" />

<div id="piliCompanyList"></div>
<hr class="solid-line" />
  <!--担保公司-->
 <div id="guarantorCompanyList"></div>
<hr class="solid-line" />
<!--担保人-->
<div id="guarantorInfoList"></div>
<hr class="solid-line" />

<!--担保企业-->
 <div id="guarantorCompanyInfo"></div>
<hr class="solid-line" />


</body>
</html>

