<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>合同展期申请管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		//$("#name").focus();
		$("#inputForm").validate({
		submitHandler : function(form) {
			loading('正在提交，请稍等...');
			form.submit();
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
	<ul class="nav nav-tabs">
		<li>
			<a href="${ctx}/postloan/applyExtend/">合同展期申请列表</a>
		</li>
		<li class="active">
			<a href="${ctx}/postloan/applyExtend/form?id=${applyExtend.id}">
				合同展期申请
				<shiro:hasPermission name="postloan:applyExtend:edit">${not empty applyExtend.id?'修改':'添加'}</shiro:hasPermission>
				<shiro:lacksPermission name="postloan:applyExtend:edit">查看</shiro:lacksPermission>
			</a>
		</li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="applyExtend" action="${ctx}/postloan/applyExtend/save" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<sys:message content="${message}" />
		<div class="control-group">
			<label class="control-label">合同编号：</label>
			<div class="controls">
				<form:input path="contractNo" htmlEscape="false" maxlength="50" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">申请展期金额：</label>
			<div class="controls">
				<form:input path="extendAmount" htmlEscape="false" class="input-xlarge  number" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">展期费率1：</label>
			<div class="controls">
				<form:input path="extendFeeRate1" htmlEscape="false" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">展期费率2：</label>
			<div class="controls">
				<form:input path="extendFeeRate2" htmlEscape="false" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">展期保证金：</label>
			<div class="controls">
				<form:input path="extendMargin" htmlEscape="false" class="input-xlarge  number" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">展期开始日期：</label>
			<div class="controls">
				<input name="extendStartDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="<fmt:formatDate value="${applyExtend.extendStartDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">展期结束日期：</label>
			<div class="controls">
				<input name="extendEndDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="<fmt:formatDate value="${applyExtend.extendEndDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">展期服务费：</label>
			<div class="controls">
				<form:input path="extendServiceFee" htmlEscape="false" class="input-xlarge  number" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">展期期限值：</label>
			<div class="controls">
				<form:select path="extendPeriod" class="input-xlarge ">
					<form:option value="" label="" />
					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">展期利息：</label>
			<div class="controls">
				<form:input path="extendInterest" htmlEscape="false" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">展期费比例：</label>
			<div class="controls">
				<form:input path="extendFeePercent" htmlEscape="false" maxlength="2" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">展期费：</label>
			<div class="controls">
				<form:input path="extendFee" htmlEscape="false" class="input-xlarge  number" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">展期应缴总额：</label>
			<div class="controls">
				<form:input path="extendAllAmount" htmlEscape="false" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">展期理由及风控措施：</label>
			<div class="controls">
				<form:input path="extendRerson" htmlEscape="false" maxlength="1000" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">特别申请事项：</label>
			<div class="controls">
				<form:input path="applySpeciall" htmlEscape="false" maxlength="1000" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">展期申请状态（1保存未申请2提交申请待审批）：</label>
			<div class="controls">
				<form:select path="extendApplyStatus" class="input-xlarge ">
					<form:option value="" label="" />
					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="postloan:applyExtend:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)" />
		</div>
	</form:form>
</body>
</html>