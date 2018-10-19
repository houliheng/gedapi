<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>担保人信息管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">

	$(document).ready(function() {

		$.loadDiv("guarantorInfoList", "${ctx}/credit/guarantorInfo/list", {
			applyNo : '${actTaskParam.applyNo}',
			taskDefKey : '${actTaskParam.taskDefKey}'
		}, "post");

		$.loadDiv("guarantorCompanyInfo", "${ctx}/credit/guarantorCompanyInfo/list", {
			applyNo : '${actTaskParam.applyNo}',
			taskDefKey : '${actTaskParam.taskDefKey}'
		}, "post");

		$.loadDiv("guarantorCompanyManagerListDiv", "${ctx}/credit/guarantorCompanyManagerInfo/list", {
		applyNo : '${actTaskParam.applyNo}',
		companyId : ''
		}, "post");

		$.loadDiv("guarantorCompanyRelatedListDiv", "${ctx}/credit/guarantorCompanyRelated/list", {
			applyNo : '${actTaskParam.applyNo}'
		}, "post");

		<!--担保信息-->
        $.loadDiv("guarantorCompanyList", "${ctx}/credit/creGuaranteeCompany/ApplyGuananteeList", {
            applyNo : '${actTaskParam.applyNo}',
            taskDefKey : '${actTaskParam.taskDefKey}'
        }, "post");
    });
</script>
</head>
<body>
	<input type="hidden" id="applyNo" value="${actTaskParam.applyNo}" />
	<div id="guarantorInfoList"></div>
	<hr class="solid-line" />
	<div id="guarantorCompanyList"></div>
	<hr class="solid-line" />
	<div id="guarantorCompanyInfo"></div>
	<hr class="solid-line" />
	<div id="guarantorCompanyManagerListDiv"></div>
	<hr class="solid-line" />
	<div id="guarantorCompanyRelatedListDiv"></div>
</body>
</html>
