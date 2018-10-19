<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>还款计划授信管理</title>
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
		<li><a href="${ctx}/credit/repayPlanUnion/">还款计划授信列表</a></li>
		<li class="active"><a href="${ctx}/credit/repayPlanUnion/form?id=${repayPlanUnion.id}">还款计划授信<shiro:hasPermission name="credit:repayPlanUnion:edit">${not empty repayPlanUnion.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="credit:repayPlanUnion:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="repayPlanUnion" action="${ctx}/credit/repayPlanUnion/save" method="post" class="form-horizontal">
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
			<label class="control-label">批复ID：</label>
			<div class="controls">
				<form:input path="approveId" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">合同编号：</label>
			<div class="controls">
				<form:input path="contractNo" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">借据ID：</label>
			<div class="controls">
				<form:input path="duebillId" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">期数：</label>
			<div class="controls">
				<form:input path="periodNum" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">计划扣款日期：</label>
			<div class="controls">
				<input name="deductDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${repayPlanUnion.deductDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">还款金额-合计：</label>
			<div class="controls">
				<form:input path="repayAmount" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">还款金额-服务费：</label>
			<div class="controls">
				<form:input path="serviceFee" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">标的还款金额（本息差额）-账户管理费：</label>
			<div class="controls">
				<form:input path="managementFee" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">标的还款金额-本金：</label>
			<div class="controls">
				<form:input path="capitalAmount" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">标的还款金额-利息：</label>
			<div class="controls">
				<form:input path="interestAmount" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">合同还款金额-本金：</label>
			<div class="controls">
				<form:input path="bidCapitalAmount" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">合同还款金额-利息：</label>
			<div class="controls">
				<form:input path="bidInterestAmount" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">合同还款金额-本息合计：</label>
			<div class="controls">
				<form:input path="bidRepayAmount" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">差额还款金额-本金：</label>
			<div class="controls">
				<form:input path="difCapitalAmount" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">差额还款金额-利息：</label>
			<div class="controls">
				<form:input path="difInterestAmount" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">逾期违约金：</label>
			<div class="controls">
				<form:input path="overduePenalty" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">流程ID：</label>
			<div class="controls">
				<form:input path="taskDefKey" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="credit:repayPlanUnion:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>