<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>创建冠易贷账号</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		/* $.ajax({
		type : "post",
		data : {
			applyNo : '${actTaskParam.applyNo}'
		},
		url : "${ctx}/credit/guarantorInfoIndex/validateGuarantor",
		dataType : "json",
		success : function(data) {
			if (data.status == "1") {
				$.loadDiv("guarantorInfoList", "${ctx }/credit/guarantorInfo/list", {
					applyNo : '${actTaskParam.applyNo}'
				}, "post");
				$.loadDiv("guarantorCompanyInfo", "${ctx }/credit/guarantorCompanyInfo/list", {
					applyNo : '${actTaskParam.applyNo}'
				}, "post");
				$.loadDiv("guarantorCompanyManagerListDiv", "${ctx}/credit/guarantorCompanyManagerInfo/list", {
				applyNo : '${actTaskParam.applyNo}',
				companyId : ''
				}, "post");
				$.loadDiv("guarantorCompanyRelatedListDiv", "${ctx}/credit/guarantorCompanyRelated/list", {
					applyNo : '${actTaskParam.applyNo}'
				}, "post");
			} else {
				alertx(data.message);
			}
		},
		error : function(msg) {
			alertx("未能保存，请查看后台信息");
		}
		}); */
		$.loadDiv("guarantorInfoMainList", "${ctx }/credit/createGedAccount/mainList", {
			applyNo : '${actTaskParam.applyNo}'
		}, "post");
		$.loadDiv("guarantorInfoIndex", "${ctx }/credit/createGedAccount/guarantorInfo", {
			applyNo : '${actTaskParam.applyNo}'
		}, "post");
		$.loadDiv("guarantorCompanyIndex", "${ctx}/credit/createGedAccount/guarantorCompany", {
		applyNo : '${actTaskParam.applyNo}',
		companyId : ''
		}, "post");
	});
</script>
</head>
<body>
	<input type="hidden" id="applyNo" value="${actTaskParam.applyNo }" />
	<div id="guarantorInfoMainList"></div>
	<hr class="solid-line" />
	<div id="guarantorInfoIndex"></div>
	<hr class="solid-line" />
	<div id="guarantorCompanyIndex"></div>
</body>
</html>
