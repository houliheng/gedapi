<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>借后清收任务分配表管理</title>
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
		<li><a href="${ctx}/postloan/accountCleanAllocate/">借后清收任务分配表列表</a></li>
		<li class="active"><a href="${ctx}/postloan/accountCleanAllocate/form?id=${accountCleanAllocate.id}">借后清收任务分配表<shiro:hasPermission name="postloan:accountCleanAllocate:edit">${not empty accountCleanAllocate.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="postloan:accountCleanAllocate:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="accountCleanAllocate" action="${ctx}/postloan/accountCleanAllocate/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">合同编号：</label>
			<div class="controls">
				<form:input path="contractNo" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">借后经理ID：</label>
			<div class="controls">
				<form:input path="allocateById" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">借后经理姓名：</label>
			<div class="controls">
				<form:input path="allocateBy" htmlEscape="false" maxlength="30" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">借后25日复核专员ID：</label>
			<div class="controls">
				<form:input path="checkedById" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">借后25日复核专员姓名：</label>
			<div class="controls">
				<form:input path="checkedBy" htmlEscape="false" maxlength="30" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">分配状态（1待分配（待办任务）2已分配（已办任务）3保存）：</label>
			<div class="controls">
				<form:input path="allocateType" htmlEscape="false" maxlength="4" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="postloan:accountCleanAllocate:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>