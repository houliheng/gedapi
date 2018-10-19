<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>接口日志记录管理</title>
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
			adjustTextareaLength('message', 'preMessage');
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/credit/interfaceInfo/">接口日志记录列表</a></li>
		<li class="active"><a href="${ctx}/credit/interfaceInfo/form?id=${interfaceInfo.id}">接口日志记录<shiro:hasPermission name="credit:interfaceInfo:edit">${not empty interfaceInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="credit:interfaceInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="interfaceInfo" action="${ctx}/credit/interfaceInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<pre class="textareaWidth pre-style" id="preMessage"></pre>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">申请编号：</label>
			<div class="controls">
				<form:input path="applyNo" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">接口名称：</label>
			<div class="controls">
				<form:input path="interfaceName" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">返回信息：</label>
			<div class="controls">
				<form:textarea path="message" htmlEscape="false" maxlength="5000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">传输信息：</label>
			<div class="controls">
				<form:textarea path="json" htmlEscape="false" maxlength="50000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发送时间：</label>
			<div class="controls">
				<input name="sendDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${interfaceInfo.sendDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>