<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>征信企业贷款明细管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$.loadDiv("creditCompanyLoanDiv","${ctx}/credit/creditAndLine/creditCompany/creditCompanyLoan/list",{creditCompanyId:'${creditCompanyId}',applyNo:'${applyNo}'},"post");
	});
</script>
</head>
<body>
<div id="creditCompanyLoanDiv"></div>
</body>
</html>