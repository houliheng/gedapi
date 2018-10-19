<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>电话催收管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$.loadDiv("debtCollectionPhoneDiv", "${ctx }/postloan/debtCollectionPhone/list", {
			debtId : '${debtCollection.id}'
		}, "post");
	});

	//新增
	function add(url, title) {
		var width = $(window).width() - 50;
		var height = $(window).height() - 50;
		openJBox('debtCollectionPhoneForm', url, title, width, height, {
		contractNo : '${debtCollection.contractNo}',
		debtId : '${debtCollection.id}',
		custName : '${plContract.custName}'
		});
	}

	function finish() {
		confirmx("确定结束电话催收？", function() {
			$.post("${ctx}/postloan/debtCollectionPhone/finishDebtCollectionPhone", {
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
	<div id="debtCollectionPhoneDiv"></div>
</body>
</html>