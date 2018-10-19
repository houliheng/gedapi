<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>财务放款管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
// 		$.loadDiv("applyLoanStatusDiv","${ctx}/credit/applyLoanStatus/form",{applyNo:'${actTaskParam.applyNo}',taskDefKey:${actTaskParam.taskDefKey}},"post");
	});
</script>
</head>
<body>
	<div id="applyLoanStatusDiv">
		<%@ include file="/WEB-INF/views/app/credit/applyLoanStatus/applyLoanStatusForm.jsp"%>
	</div>
</body>
</html>