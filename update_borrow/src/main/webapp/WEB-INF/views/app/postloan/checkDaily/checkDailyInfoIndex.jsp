<%@ page contentType="text/html;charset=UTF-8"%>
<%@ 	include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>日常检查详情</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		setDivReadOnly("checkDailyContractBaseInfoForm");
		setDivReadOnly("checkTwentyFiveInfo");
		var checkFlag = '${checkFlag}';
		var myPath = '${myPath}';
		if (myPath == "contractList") {
			$("#checkDailyForm").hide();
		} else if (myPath == "contractDoneList") {
			if ('${status}' != null && '${status}' != "") {
				$("#checkDailyForm").show();
				setDivReadOnly("checkDailyForm");
			} else {
				$("#checkDailyForm").hide();
			}
		} else if (myPath == "toCheckList") {
			if (checkFlag != 'true') {
				$("#checkDailyForm").hide();
			} else {
				$("#checkDailyForm").show();
			}
		} else if (myPath == "doneCheckList") {
			$("#checkDailyForm").show();
			setDivReadOnly("checkDailyForm");
		} else {
			$("#checkDailyForm").hide();
		}

	});
</script>
</head>
<body>
	<!-- 25日复核信息详情（合同基本信息） -->
	<div id="checkDailyContractBaseInfoForm">
		<%@ include file="/WEB-INF/views/app/postloan/checkDaily/checkDailyContractBaseInfoForm.jsp"%>
	</div>
	<!-- 25复核信息 -->
	<div id="checkTwentyFiveInfo">
		<%@ include file="/WEB-INF/views/app/postloan/checkDaily/checkTwentyFiveInfo.jsp"%>
	</div>
	<!-- 日常检查 -->
	<div id="checkDailyForm">
		<%@ include file="/WEB-INF/views/app/postloan/checkDaily/checkDailyForm.jsp"%>
	</div>
</body>
</html>
