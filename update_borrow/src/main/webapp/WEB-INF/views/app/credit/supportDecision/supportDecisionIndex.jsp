<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>客户基本信息管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$.loadDiv("experianDecisionAnalysis", "${ctx }/credit/experianDecisionAnalysis/list", {applyNo:'${applyNo}'}, "post");
		$.loadDiv("themisRatingScoreDiv", "${ctx }/credit/supportDecision/themisRatingScore", {applyNo:'${applyNo}'}, "post");
		$.loadDiv("themisReviewDiv", "${ctx }/credit/supportDecision/reviewIndex", {applyNo:'${applyNo}'}, "post");
	});
</script>
</head>
<body>
	<div id="experianDecisionAnalysis"></div>
	<div id="themisRatingScoreDiv"></div>
	<div id="themisReviewDiv"></div>
	<div id=""></div>
	<div id=""></div>
</body>
</html>
