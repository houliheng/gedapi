<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>电话催收管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$.loadDiv("debtCollectionOutDiv", "${ctx }/postloan/debtCollectionOut/list", {
			debtId : '${debtCollection.id}'
		}, "post");
	});

	//新增
	function add(url, title) {
		var width = $(window).width() - 50;
		var height = $(window).height() - 50;
		openJBox('debtCollectionOutForm', url, title, width, height, {
		contractNo : '${debtCollection.contractNo}',
		debtId : '${debtCollection.id}'
		});
	}

	function finish() {
		confirmx("确定结束外包催收？", function() {
			$.post("${ctx}/postloan/debtCollectionOut/finishDebtCollectionOut", {
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
	<div id="debtCollectionOutDiv"></div>
	<div id="collectionPaymentInfoDiv">
		<%@ include file="/WEB-INF/views/app/postloan/collectionPaymentInfo/collectionPaymentInfoList.jsp"%>
	</div>
</body>
</html>