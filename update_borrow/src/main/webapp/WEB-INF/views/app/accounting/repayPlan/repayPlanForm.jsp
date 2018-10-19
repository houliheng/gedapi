<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>应还款查询管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
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
			<a href="${ctx}/accounting/repayPlan/">应还款查询列表</a>
		</li>
		<li class="active">
			<a href="${ctx}/accounting/repayPlan/form?id=${repayPlan.id}">
				应还款查询
				<shiro:hasPermission name="accounting:repayPlan:edit">${not empty repayPlan.id?'修改':'添加'}</shiro:hasPermission>
				<shiro:lacksPermission name="accounting:repayPlan:edit">查看</shiro:lacksPermission>
			</a>
		</li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="repayPlan" action="${ctx}/accounting/repayPlan/save" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<sys:message content="${message}" />
		<div class="control-group">
			<label class="control-label">大区：</label>
			<div class="controls">
				<form:input path="orgLevel2.id" htmlEscape="false" maxlength="32" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">当期期数：</label>
			<div class="controls">
				<form:input path="periodNum" htmlEscape="false" maxlength="10" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">区域：</label>
			<div class="controls">
				<form:input path="orgLevel3.id" htmlEscape="false" maxlength="32" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">应还款日期：</label>
			<div class="controls">
				<form:input path="deductDate" htmlEscape="false" maxlength="10" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">登记门店：</label>
			<div class="controls">
				<form:input path="orgLevel4.id" htmlEscape="false" maxlength="32" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客户名称：</label>
			<div class="controls">
				<form:input path="custName" htmlEscape="false" maxlength="20" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">还款金额-本息合计：</label>
			<div class="controls">
				<form:input path="repayAmount" htmlEscape="false" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">合同编号：</label>
			<div class="controls">
				<form:input path="contractNo" htmlEscape="false" maxlength="50" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">期限值：</label>
			<div class="controls">
				<form:input path="periodValue" htmlEscape="false" maxlength="4" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">其中利息：</label>
			<div class="controls">
				<form:input path="interestAmount" htmlEscape="false" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">还款金额-服务费：</label>
			<div class="controls">
				<form:input path="serviceFee" htmlEscape="false" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">还款金额-账户管理费：</label>
			<div class="controls">
				<form:input path="mangementFee" htmlEscape="false" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">还款金额-本金：</label>
			<div class="controls">
				<form:input path="capitalAmount" htmlEscape="false" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				<input name="createTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="<fmt:formatDate value="${repayPlan.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" />
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="accounting:repayPlan:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)" />
		</div>
	</form:form>
</body>
</html>