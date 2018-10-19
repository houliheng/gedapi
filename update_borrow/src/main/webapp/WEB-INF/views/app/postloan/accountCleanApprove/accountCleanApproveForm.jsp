<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>账务清收审批管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#accountCleanApproveForm").validate({
		submitHandler : function(form) {
			saveForm();
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

		if ('${status}' == 'apply') {
			$("#applyAdvice").removeAttr("readOnly");
		}

	});

	//清收
	function saveForm() {
		loading();
		var status = '${status}';
		top.$.jBox.tip.mess = null;
		var formJson = $("#accountCleanApproveForm").serializeJson();
		$.post("${ctx}/postloan/accountCleanApprove/save?status=" + status, formJson, function(data) {
			if (data) {
				closeTip();
				if (data.status == 1) {//保存成功
					alertx(data.message, function() {
						if (data.data == 'apply') {
							if (data.legalFlag == "legalToClean") {
								parent.page();
								closeJBox();
							} else {
								parent.saveCheckTwentyFiveInputForm();
							}
						} else {
							parent.page();
							closeJBox();
						}
					});
				} else {
					alertx(data.message);
				}
			}
		});
	}
</script>
</head>
<body>
	<div id="accountCleanApproveDiv" class="searchInfo">
		<h3 onclick="manageSuggToggle()" class="searchTitle">清收</h3>
		<div class="searchCon">
			<form:form id="accountCleanApproveForm" modelAttribute="accountCleanApprove" action="" method="post" class="form-horizontal">
				<form:hidden path="id" />
				<form:hidden path="applyById" />
				<form:hidden path="checkedById" />
				<form:hidden path="hqCheckedById" />
				<form:hidden path="checkResult" />
				<sys:message content="${message}" />
				<table class="fromTable filter">
					<tr>
						<td class="ft_label">合同编号：</td>
						<td class="ft_content">
							<form:input path="contractNo" readOnly="readOnly" htmlEscape="false" maxlength="50" class="input-medium " />
							<span class="help-inline">
								<font color="red">*</font>
							</span>
						</td>
						<td class="ft_label">申请人员：</td>
						<td class="ft_content">
							<form:input path="applyBy" readOnly="readOnly" htmlEscape="false" maxlength="30" class="input-medium " />
							<span class="help-inline">
								<font color="red">*</font>
							</span>
						</td>
					</tr>
					<tr>
						<td class="ft_label">申请原因：</td>
						<td class="ft_content" colspan="5">
							<form:textarea path="applyAdvice" htmlEscape="false" rows="4" maxlength="1000" class="input-xxlarge required" readOnly="readOnly" />
							<span class="help-inline">
								<font color="red">*</font>
							</span>
						</td>
					</tr>
					<c:if test="${status eq 'check'}">
						<tr>
							<td class="ft_label">大区审批人员：</td>
							<td class="ft_content">
								<form:input path="checkedBy" readOnly="readOnly" htmlEscape="false" maxlength="30" class="input-medium " />
								<span class="help-inline">
									<font color="red">*</font>
								</span>
							</td>
						</tr>
						<tr>
							<td class="ft_label">大区审批结果：</td>
							<td class="ft_content" colspan="5">
								<form:radiobuttons path="checkResult" id="checkResult" items="${fns:getDictList('BORROW_NEW_RESULT')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required" />
								<span class="help-inline">
									<font color="red">*</font>
								</span>
							</td>
						</tr>
						<tr>
							<td class="ft_label">大区审批意见：</td>
							<td class="ft_content" colspan="5">
								<form:textarea path="checkAdvice" htmlEscape="false" rows="4" maxlength="1000" class="input-xxlarge required" />
								<span class="help-inline">
									<font color="red">*</font>
								</span>
							</td>
						</tr>
					</c:if>
					<c:if test="${status eq 'hqCheck' }">
						<tr>
							<td class="ft_label">大区审批人员：</td>
							<td class="ft_content">
								<form:input path="checkedBy" readOnly="readOnly" htmlEscape="false" maxlength="30" class="input-medium " />
								<span class="help-inline">
									<font color="red">*</font>
								</span>
							</td>
						</tr>
						<tr>
							<td class="ft_label">大区审批结果：</td>
							<td class="ft_content" colspan="5">
								<form:radiobuttons path="checkResult" id="checkResult" items="${fns:getDictList('BORROW_NEW_RESULT')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required" disabled="true" />
								<span class="help-inline">
									<font color="red">*</font>
								</span>
							</td>
						</tr>
						<tr>
							<td class="ft_label">大区审批意见：</td>
							<td class="ft_content" colspan="5">
								<form:textarea path="checkAdvice" htmlEscape="false" rows="4" maxlength="1000" class="input-xxlarge required" readOnly="true" />
								<span class="help-inline">
									<font color="red">*</font>
								</span>
							</td>
						</tr>
					</c:if>
					<c:if test="${status eq 'hqCheck' }">
						<tr>
							<td class="ft_label">总部审批人员：</td>
							<td class="ft_content">
								<form:input path="hqCheckedBy" readOnly="readOnly" htmlEscape="false" maxlength="30" class="input-medium " />
								<span class="help-inline">
									<font color="red">*</font>
								</span>
							</td>
						</tr>
						<tr>
							<td class="ft_label">总部审批结果：</td>
							<td class="ft_content" colspan="5">
								<form:radiobuttons path="hqCheckResult" items="${fns:getDictList('BORROW_NEW_RESULT')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required" />
								<span class="help-inline">
									<font color="red">*</font>
								</span>
							</td>
						</tr>
						<tr>
							<td class="ft_label">总部审批意见：</td>
							<td class="ft_content" colspan="5">
								<form:textarea path="hqCheckAdvice" htmlEscape="false" rows="4" maxlength="1000" class="input-xxlarge required" />
								<span class="help-inline">
									<font color="red">*</font>
								</span>
							</td>
						</tr>
					</c:if>
					<tr>
						<td colspan="6">
							<div class="searchButton">
								<shiro:hasPermission name="postloan:accountCleanApprove:edit">
									<input id="btnSubmit" class="btn btn-primary" type="submit" value="提 交" />&nbsp;</shiro:hasPermission>
								<input id="btnCancel" class="btn" type="button" value="返 回" onclick="closeJBox();" />
							</div>
						</td>
					</tr>
				</table>
			</form:form>
		</div>
	</div>
</body>
</html>