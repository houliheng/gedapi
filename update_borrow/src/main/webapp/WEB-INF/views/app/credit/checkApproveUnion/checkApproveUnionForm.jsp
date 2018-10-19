<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>批复信息授信管理</title>
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
		<li><a href="${ctx}/credit/checkApproveUnion/">批复信息授信列表</a></li>
		<li class="active"><a href="${ctx}/credit/checkApproveUnion/form?id=${checkApproveUnion.id}">批复信息授信<shiro:hasPermission name="credit:checkApproveUnion:edit">${not empty checkApproveUnion.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="credit:checkApproveUnion:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="checkApproveUnion" action="${ctx}/credit/checkApproveUnion/save" method="post" class="form-horizontal">
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
			<label class="control-label">客户编号：</label>
			<div class="controls">
				<form:input path="custId" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">风险定价费率：</label>
			<div class="controls">
				<form:input path="pricedRisk" htmlEscape="false" maxlength="3" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">批贷产品类型：</label>
			<div class="controls">
				<form:input path="approProductTypeCode" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">批贷产品类型名称：</label>
			<div class="controls">
				<form:input path="approProductTypeName" htmlEscape="false" maxlength="30" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">批贷产品ID：</label>
			<div class="controls">
				<form:input path="approProductId" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">批贷产品名称：</label>
			<div class="controls">
				<form:input path="approProductName" htmlEscape="false" maxlength="30" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">合同金额：</label>
			<div class="controls">
				<form:input path="contractAmount" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">批贷金额：</label>
			<div class="controls">
				<form:input path="approAmount" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">放款金额：</label>
			<div class="controls">
				<form:input path="loanAmount" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">利率：</label>
			<div class="controls">
				<form:input path="approYearRate" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">冠E通利率：</label>
			<div class="controls">
				<form:input path="guanetongRate" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务费率：</label>
			<div class="controls">
				<form:input path="serviceFeeRate" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">特殊服务费率：</label>
			<div class="controls">
				<form:input path="specialServiceFeeRate" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务费收取方式：</label>
			<div class="controls">
				<form:input path="serviceFeeType" htmlEscape="false" maxlength="4" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务费：</label>
			<div class="controls">
				<form:input path="serviceFee" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">特殊服务费：</label>
			<div class="controls">
				<form:input path="specialServiceFee" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务费总计：</label>
			<div class="controls">
				<form:input path="allServiceFee" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">批贷期限ID：</label>
			<div class="controls">
				<form:input path="approPeriodId" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">批贷期限值：</label>
			<div class="controls">
				<form:input path="approPeriodValue" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">还款方式（字典类型：LOAN_REPAY_TYPE）：</label>
			<div class="controls">
				<form:input path="approLoanRepayType" htmlEscape="false" maxlength="4" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">保证金率（%）：</label>
			<div class="controls">
				<form:input path="marginRate" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">保证金：</label>
			<div class="controls">
				<form:input path="marginAmount" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">外访费：</label>
			<div class="controls">
				<form:input path="checkFee" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">借款模式：</label>
			<div class="controls">
				<form:input path="loanModel" htmlEscape="false" maxlength="4" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否加急：</label>
			<div class="controls">
				<form:input path="isUrgent" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">建议签订合同类型：</label>
			<div class="controls">
				<form:input path="contractType" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">批复日期：</label>
			<div class="controls">
				<input name="approDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${checkApproveUnion.approDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">流程ID：</label>
			<div class="controls">
				<form:input path="taskDefKey" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">批复信息流程顺序：</label>
			<div class="controls">
				<form:input path="processSequence" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:input path="remark" htmlEscape="false" maxlength="1000" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="credit:checkApproveUnion:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>