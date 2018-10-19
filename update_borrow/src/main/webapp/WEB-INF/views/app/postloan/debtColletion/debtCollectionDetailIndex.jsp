<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>催收详情</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$.loadDiv("debtCollectionPhoneDiv", "${ctx }/postloan/debtCollectionPhone/list", {
		contractNo : '${contractNo}',
		readOnly : "true"
		}, "post");
		$.loadDiv("debtCollectionFaceDiv", "${ctx }/postloan/debtCollectionFace/list", {
		contractNo : '${contractNo}',
		readOnly : "true"
		}, "post");
		$.loadDiv("debtCollectionOutDiv", "${ctx }/postloan/debtCollectionOut/list", {
		contractNo : '${contractNo}',
		readOnly : "true"
		}, "post");
		$.loadDiv("debtCollectionLegalDiv", "${ctx }/postloan/debtCollectionLegal/toDebtCollectionLegal", {
		contractNo : '${contractNo}',
		readOnly : "true"
		}, "post");
		$.loadDiv("debtCollectionTurnTaskDiv", "${ctx }/postloan/doneTurnTask/detailList", {
		contractNo : '${contractNo}',
		readOnly : "true"
		}, "post");
	});

	//新增
	function add(url, title) {
		var width = $(window).width() - 100;
		var height = $(window).height() - 100;
		openJBox('debtCollectionPhoneForm', url, title, width, height, {
			contractNo : '${contractNo}'
		});
	}
</script>
</head>
<body>
	<div id="contractFormDiv">
		<%@ include file="/WEB-INF/views/app/postloan/debtColletion/debtCollectionContractForm.jsp"%>
	</div>
	<div id="debtCollectionPhoneDiv"></div>
	<div id="debtCollectionFaceDiv"></div>
	<div id="debtCollectionOutDiv"></div>
	<div id="debtCollectionLegalDiv"></div>
	<div id="collectionPaymentInfoDiv">
		<%@ include file="/WEB-INF/views/app/postloan/collectionPaymentInfo/collectionPaymentInfoList.jsp"%>
	</div>
	<div id="debtCollectionTurnTaskDiv"></div>
</body>
</html>