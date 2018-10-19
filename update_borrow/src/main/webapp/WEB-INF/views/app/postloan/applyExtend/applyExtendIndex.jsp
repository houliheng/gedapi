<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>合同展期申请</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$.loadDiv("extendRepayPlanList", "${ctx }/postloan/extendRepayPlan/list", {
			contractNo : '${contractNo}'
		}, "post");
	});
	function saveApplyExtendForm() {
		var period = $("#extendPeriod").val();
		if (Number(period) == 0) {
			alertx("请填写正确的展期期限");
		} else {
			loading();
			top.$.jBox.tip.mess = null;
			var formJson = $("#applyExtendFormId").serializeJson();
			$.post("${ctx}/postloan/applyExtend/save", formJson, function(data) {
				if (data) {
				closeTip();
					if (data.status == 1) {
						alertx(data.message, function() {
							var id = data.id;
							$("#applyExtendFormId input[id=id]").val(id);
							if (data.flag == "submit") {
								closeAndReloadPostLoan();
							} else {
								$.loadDiv("extendRepayPlanList", "${ctx }/postloan/extendRepayPlan/list", {
									contractNo : data.contractNo
								}, "post");
							}
						});
					} else {
						alertx(data.message);
					}

				}
			});
		}
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div id="extendTaskDetail">
			<%@ include file="/WEB-INF/views/app/postloan/applyExtend/extendTaskDetail.jsp"%>
		</div>
		<div id="applyExtendForm">
			<%@ include file="/WEB-INF/views/app/postloan/applyExtend/applyExtendForm.jsp"%>
		</div>
		<div id="extendRepayPlanList"></div>
	</div>
</body>
</html>
