<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
<title>银行卡变更申请管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
	});
</script>
</head>
<body>
	<div id="contractInfo">
		<%@ include file="/WEB-INF/views/app/accounting/contract/accContractForm.jsp"%>
	</div>
	<div id="applyChangeBankcardInfo">
		<%@ include file="/WEB-INF/views/app/accounting/applyChangeBankcard/applyChangeBankcardForm.jsp"%>
	</div>
	<div id="applyChangeBankcardList">
		<%@ include file="/WEB-INF/views/app/accounting/applyChangeBankcard/applyChangeBankcardList.jsp"%>
	</div>
</body>
</html>