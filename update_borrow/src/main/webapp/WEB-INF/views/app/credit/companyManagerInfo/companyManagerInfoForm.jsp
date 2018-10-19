<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>企业高管管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#manangerForm").validate({
		submitHandler : function(form) {
			loading();
			var param = $("#manangerForm").serialize();
			$.post("${ctx}/credit/companyManagerInfo/save", param, function(data) {
				if (data) {
					closeTip();
					if (data.status == '1') {
						alertx(data.message, function() {
							parent.$.loadDiv('companyManagerInfoList', '${ctx}/credit/companyManagerInfo/list', {
							companyId : '${companyManagerInfo.companyId}',
							applyNo : '${applyNo}'
							}, 'post');
							closeJBox();
						});
					} else {
						alertx(data.message);
					}
				}
			});
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});
	});
</script>
</head>
<body>
	<div class="searchInfo">
		<h3 class="searchTitle">借款人企业高管信息</h3>
		<br>
		<div class="searchCon">
			<form:form id="manangerForm" modelAttribute="companyManagerInfo" action="${ctx}/credit/companyManagerInfo/save" method="post" class="form-horizontal">
				<form:hidden path="id" />
				<form:hidden path="companyId" value="${companyId}" />
				<sys:message content="${message}" />
				<div class="control-group">
					<label class="control-label">姓名：</label>
					<div class="controls">
						<form:input path="managerName" htmlEscape="false" maxlength="20" class="input-xlarge required" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">职务：</label>
					<div class="controls">
						<form:select path="postName" class="input-xlarge required">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('POST_LEVEL')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">联系方式：</label>
					<div class="controls">
						<form:input path="mobileNum" htmlEscape="false" maxlength="11" class="input-xlarge mobile required" />
					</div>
				</div>
				<div class="searchButton">
					<shiro:hasPermission name="credit:custinfo:edit">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />&nbsp;
			</shiro:hasPermission>
					<input id="btnCancel" class="btn btn-primary" type="button" value="关闭" onclick="closeJBox()" />
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>