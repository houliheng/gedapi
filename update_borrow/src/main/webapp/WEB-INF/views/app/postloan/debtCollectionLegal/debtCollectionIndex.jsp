<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>法务催收管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$.loadDiv("debtCollectionLegalDiv", "${ctx }/postloan/debtCollectionLegal/toDebtCollectionLegal", {
			debtId : '${debtCollection.id}'
		}, "post");
	});

	//新增
	function add(url, title) {
		var width = $(window).width() - 100;
		var height = $(window).height() - 50;
		openJBox('debtCollectionLegalForm', url, title, width, height, {
		contractNo : '${debtCollection.contractNo}',
		debtId : '${debtCollection.id}'
		});
	}

	function finish() {
		confirmx("确定结束法务催收？", function() {
			$.post("${ctx}/postloan/debtCollectionLegal/finishDebtCollectionLegal", {
			contractNo : '${debtCollection.contractNo}',
			debtId : '${debtCollection.id}'
			}, function(data) {
				if (data) {
					if (data.status == 1) {
						alertx(data.message, function() {
							closeAndReloadPostLoan();
						});
					} else {
						alertx(data.message);
					}
				}
			});
		});
	}
</script>
</head>
<body>
	<div id="contractFormDiv">
		<%@ include file="/WEB-INF/views/app/postloan/debtColletion/debtCollectionContractForm.jsp"%>
	</div>
	<div id="debtCollectionLegalDiv"></div>
	<div id="collectionPaymentInfoDiv">
		<%@ include file="/WEB-INF/views/app/postloan/collectionPaymentInfo/collectionPaymentInfoList.jsp"%>
	</div>
</body>
</html>