<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>条件管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
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
		<li><a href="${ctx}/credit/generalManagerAudit/">条件列表</a></li>
		<li class="active"><a href="${ctx}/credit/generalManagerAudit/form?id=${generalManagerAudit.id}">条件<shiro:hasPermission name="credit:generalManagerAudit:edit">${not empty generalManagerAudit.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="credit:generalManagerAudit:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="generalManagerAudit" action="${ctx}/credit/generalManagerAudit/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<sys:treeselect id="user" name="user.id" value="${generalManagerAudit.user.id}" labelName="user.name" labelValue="${generalManagerAudit.user.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="required" allowClear="true" notAllowSelectParent="true" dataMsgRequired="必填信息"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品类型：</label>
			<div class="controls">
				<form:select path="productTypeCode" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('PRODUCT_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">比较符：</label>
			<div class="controls">
				<form:select path="operatorCode" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('OPERATOR')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">合同金额(元)：</label>
			<div class="controls">
				<form:input path="contractAmount" htmlEscape="false" class="input-xlarge required" maxlength="16"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="credit:generalManagerAudit:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="location='${ctx}/credit/generalManagerAudit'"/>
		</div>
	</form:form>
</body>
</html>