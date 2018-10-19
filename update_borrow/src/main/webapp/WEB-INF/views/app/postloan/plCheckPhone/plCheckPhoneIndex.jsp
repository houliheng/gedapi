<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>电话外访管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$.loadDiv("checkPhoneDiv", "${ctx}/postloan/plCheckPhone/list", {
		allocateId : '${allocateId}',
		contractNo : '${contractNo}',
		readOnlyFlag : '${readOnlyFlag}'
		}, "post");
	});
</script>
</head>
<body>
	<div id="checkPhoneDiv"></div>
</body>
</html>