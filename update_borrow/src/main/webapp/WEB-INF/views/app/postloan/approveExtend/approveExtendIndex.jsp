<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>合同展期申请</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {

		if ("1" == '${actTaskParam.status}') {
			$("#approveExtendForm").hide();
		}

		$.loadDiv("extendRepayPlanList", "${ctx }/postloan/extendRepayPlan/list", {
			contractNo : '${actTaskParam.applyNo}'
		}, "post");
	});
	function saveApplyExtendForm() {
		top.$.jBox.tip.mess = null;
		var formJson = $("#applyExtendFormId").serializeJson();
		$.post("${ctx}/postloan/applyExtend/save", formJson, function(data) {
			if (data) {
				if (data.status == 1) {
					//保存成功时，将主借人ID值赋给主借人工作信息表单中的隐藏域
					alertx(data.message, function() {
						var id = data.id;
						$("#applyExtendFormId input[id=id]").val(id);
						closeAndReloadPostLoan();
					});
				} else {
					alertx(data.message);
				}

			}
		});
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="wrapper">
			<div id="extendTaskDetail">
				<%@ include file="/WEB-INF/views/app/postloan/applyExtend/extendTaskDetail.jsp"%>
			</div>
			<div id="applyExtendForm">
				<%@ include file="/WEB-INF/views/app/postloan/applyExtend/applyExtendForm.jsp"%>
			</div>
			<div id="extendRepayPlanList"></div>
		</div>
		<div id="approveExtendForm">
			<%@ include file="/WEB-INF/views/app/postloan/approveExtend/approveExtendForm.jsp"%>
		</div>
	</div>
</body>
</html>
