<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>借新还旧信息管理</title>
<meta name="decorator" content="default" />
</head>
<body>
	<script type="text/javascript">
		$(document).ready(function() {
			setDivReadOnly("specialCaseId");
		});

		function formSubmit() {
			var url = "${ctx}/postloan/checkDaily/specialCaseSave";
			var formJson = $("#specialCaseForm").serializeJson();
			$.post(url, formJson, function(data) {
				if (data) {
					if (data.status == 1) {
						alertx(data.message, function() {
							enableButtons("specialCaseDiv");
							parent.parent.page();
							closeJBox();
						});
					} else {
						alertx(data.message, function() {
							enableButtons("specialCaseDiv");
						});
					}
				}
			});
		}
	</script>
	<div id="specialCaseDiv" class="searchInfo">
		<h3 class="searchTitle">特殊情况处理</h3>
		<div id="specialCaseId" class="searchCon">
			<form:form id="specialCaseForm" modelAttribute="checkDailyAllocate" action="" method="post" class="form-horizontal">
				<sys:message content="${message}" />
				<form:hidden path="id" />
				<form:hidden path="contractNo" />
				<form:hidden path="checkDaily.id" />
				<form:hidden path="checkDaily.contractNo" />
				<form:hidden path="checkDaily.checkDailyResult" />
				<table class="fromTable filter">
					<tr>
						<td class="ft_label" style="width: 8%;">处理意见：</td>
						<td class="ft_content" style="width: 92%;"><form:textarea path="checkDaily.checkDailyAdvice" cssClass="area-xxlarge" readonly="readonly" /></td>
					</tr>
				</table>
			</form:form>
		</div>
		<div class="searchButton">
			<input id="btnSave" class="btn btn-primary" type="button" value="提 交" onclick="formSubmit();" /> &nbsp; <input id="btnClose" class="btn btn-primary" type="button" value="关 闭" onclick="closeJBox();" /> &nbsp;
		</div>

	</div>
</body>
</html>