<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>外访费返还</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	$(document).ready(function() {
		$.loadDiv("checkFeeReturnListDiv","${ctx }/credit/checkFee/checkFeeReturnList",null,"post");
	});
	</script>
</head>
<body>
<div id="checkFeeReturnListDiv"></div>
</body>
</html>