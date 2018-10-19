<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>借前外访管理</title>
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
		<li><a href="${ctx}/credit/checkDoubtful/">借前外访列表</a></li>
		<li class="active"><a href="${ctx}/credit/checkDoubtful/form?id=${checkDoubtful.id}">借前外访<shiro:hasPermission name="credit:checkDoubtful:edit">${not empty checkDoubtful.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="credit:checkDoubtful:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="checkDoubtful" action="${ctx}/credit/checkDoubtful/save" method="post" class="form-horizontal">
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
			<label class="control-label">外访日期：</label>
			<div class="controls">
				<input id="checkDate" name="checkDate" type="text" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${checkDoubtful.checkDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:new Date(),isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">检查人ID：</label>
			<div class="controls">
				<form:input path="checkUserId" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				<font color="red">*</font>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">外访人：</label>
			<div class="controls">
				<form:input path="checkUserName" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
				<font color="red">*</font>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">异常风险点：</label>
			<div class="controls">
				<form:input path="riskPoint" htmlEscape="false" maxlength="300" class="input-xlarge required"/>
				<font color="red">*</font>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">外访地点：</label>
			<div class="controls">
				<form:input path="checkAddress" htmlEscape="false" maxlength="300" class="input-xlarge required"/>
				<font color="red">*</font>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">外访详情：</label>
			<div class="controls">
				<form:input path="description" htmlEscape="false" maxlength="1000" class="input-xlarge required"/>
				<font color="red">*</font>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">序号（5位）：</label>
			<div class="controls">
				<form:input path="serialNumber" htmlEscape="false" maxlength="5" class="input-xlarge required"/>
				<font color="red">*</font>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="credit:checkDoubtful:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		<c:if test="${not empty close}">
			<script type="text/javascript">
				closeAndReload();
			</script>
		</c:if>
	</form:form>
</body>
</html>