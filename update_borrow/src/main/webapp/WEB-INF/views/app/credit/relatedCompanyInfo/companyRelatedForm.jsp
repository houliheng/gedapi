<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>关联企业管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#companyRelatedForm").validate({
		submitHandler : function(form) {
			loading();
			var param = $("#companyRelatedForm").serialize();
			$.post("${ctx}/credit/companyRelated/save", param, function(data) {
				if (data) {
					closeTip();
					if (data.status == '1') {
						alertx(data.message, function() {
							parent.$.loadDiv('companyRelatedListDiv', '${ctx}/credit/companyRelated/list', {
							companyId : $("#companyId").val(),
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
			$("#messageBox").text("输入有误，请先更正。");
			if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
				error.appendTo(element.parent().parent());
			} else {
				error.insertAfter(element);
			}
		}
		});
	});
</script>
</head>
<body>
	<div class="searchInfo">
		<h3 class="searchTitle">借款人企业关联企业信息</h3>
		<br />
		<div class="searchCon">
			<form:form id="companyRelatedForm" modelAttribute="companyRelated" action="${ctx}/credit/companyRelated/save" method="post" class="form-horizontal">
				<form:hidden path="id" />
				<form:hidden path="companyId" />
				<sys:message content="${message}" />
				<div class="control-group">
					<label class="control-label">工商登记名称：</label>
					<div class="controls">
						<form:input path="relatedCompanyName" htmlEscape="false" maxlength="300" class="input-xlarge required" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">组织机构代码：</label>
					<div class="controls">
						<form:input path="relatedCompanyOrg" htmlEscape="false" maxlength="50" class="input-xlarge required" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">与借款人企业关系：</label>
					<div class="controls">
						<form:input path="relatedCompanyType" htmlEscape="false" maxlength="50" class="input-xlarge required" />
					</div>
				</div>
				<div class="searchButton text-left">
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