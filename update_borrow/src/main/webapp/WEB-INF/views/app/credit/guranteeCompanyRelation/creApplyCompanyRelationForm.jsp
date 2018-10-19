<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>批量借款企业关系表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
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
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/credit/creApplyCompanyRelation/">批量借款企业关系表列表</a></li>
		<li class="active"><a href="${ctx}/credit/creApplyCompanyRelation/form?id=${creApplyCompanyRelation.id}">批量借款企业关系表<shiro:hasPermission name="credit:creApplyCompanyRelation:edit">${not empty creApplyCompanyRelation.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="credit:creApplyCompanyRelation:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="creApplyCompanyRelation" action="${ctx}/credit/creApplyCompanyRelation/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">申请编号：</label>
			<div class="controls">
				<form:input path="applyNo" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">主借款企业主键,批量借款企业主键：</label>
			<div class="controls">
				<form:input path="companyId" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">担保公司，担保人，担保企业主键：</label>
			<div class="controls">
				<form:input path="custId" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">1.担保人，2担保人企业，3担保公司：</label>
			<div class="controls">
				<form:input path="roleType" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">确认标记：</label>
			<div class="controls">
				<form:input path="notarizeFlag" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="credit:creApplyCompanyRelation:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>