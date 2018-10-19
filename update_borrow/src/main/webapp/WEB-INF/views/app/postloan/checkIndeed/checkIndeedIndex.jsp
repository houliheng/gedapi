<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>实地外访管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$.loadDiv("checkIndeedDiv", "${ctx}/postloan/checkIndeed/list", {
		allocateId : '${allocateId}',
		contractNo : '${contractNo}'
		}, "post");

	});
</script>
</head>
<body>
	<div id="checkIndeedDiv"></div>
</body>
</html>