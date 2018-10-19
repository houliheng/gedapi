<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>还款明细跳转页</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$.loadDiv("deductResultId", "${ctx}/accounting/deductResult/list",{contractNo:'${contractNo}'}, "post");
	});
</script>
</head>
<body>
	<div id="deductResultId"></div>
</body>
</html>