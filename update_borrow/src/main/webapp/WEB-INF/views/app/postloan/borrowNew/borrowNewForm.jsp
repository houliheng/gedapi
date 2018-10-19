<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>借新还旧信息管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#borrowNewForm").validate({
		submitHandler : saveBorrowNew,
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});
		//只读处理
		setDivReadOnly("borrowNewDiv");
		var applyFlag = '${applyFlag}';
		if (applyFlag == "DQ") {
			$("input[type='radio']").removeAttr("disabled");
			$("#DQcheckAdvice").removeAttr("readOnly");
		} else if (applyFlag == "apply") {
			$("#applyAdvice").removeAttr("readOnly");
		} else {
			$("input[type='radio']").removeAttr("disabled");
			$("#ZBcheckAdvice").removeAttr("readOnly");
			$("#radio_no").attr("disabled", "disabled");
			$("#radio_yes").attr("disabled", "disabled");
		}
		$("div[class='searchButton']").show();
		$("#btnSubmit").show();
		$("#btnCancel").show();
	});

	function saveBorrowNew() {
		disableButtons("borrowNewDiv");
		loading();
		var applyFlag = '${applyFlag}';
		var url = "${ctx}/postloan/borrowNew/save?applyFlag=" + applyFlag;
		var formJson = $("#borrowNewForm").serializeJson();
		$.post(url, formJson, function(data) {
			if (data) {
				closeTip();
				if (data.status == 1) {
					alertx(data.message, function() {
						enableButtons("borrowNewDiv");
						if (applyFlag == "apply") {
							parent.parent.page();
							parent.closeJBox('checkDailyForm');
							closeJBox('borrowNewForm');
						} else {
							parent.page();
							closeJBox('borrowNewForm');
						}

					});
				} else {
					alertx(data.message, function() {
						enableButtons("borrowNewDiv");
					});
				}
			}
		});
	}
</script>
</head>
<body>
	<div id="borrowNewDiv" class="searchInfo">
		<h3 onclick="manageSuggToggle()" class="searchTitle">借后管理建议</h3>
		<div class="searchCon">
			<form:form id="borrowNewForm" modelAttribute="borrowNew" action="${ctx}/postloan/borrowNew/save" method="post" class="form-horizontal">
				<form:hidden path="id" />
				<form:hidden path="applyById" />
				<form:hidden path="DQcheckedById" />
				<form:hidden path="DQcheckedBy" />
				<form:hidden path="ZBcheckedById" />
				<form:hidden path="ZBcheckedBy" />
				<input type="hidden" name="checkDailyAllocate.id" value="${checkDailyAllocate.id }" />
				<input type="hidden" name="checkDaily.id" value="${checkDailyAllocate.checkDaily.id }" />
				<input type="hidden" name="checkDailyResult" value="${checkDailyAllocate.checkDaily.checkDailyResult }" />
				<input type="hidden" name="checkDailyAdvice" value="${checkDailyAllocate.checkDaily.checkDailyAdvice }" />
				<sys:message content="${message}" />
				<table class="fromTable filter">
					<tr>
						<td class="ft_label">合同编号：</td>
						<td class="ft_content">
							<form:input path="contractNo" readonly="readonly" htmlEscape="false" maxlength="50" class="input-medium " />
							<span class="help-inline">
								<font color="red">*</font>
							</span>
						</td>
						<td class="ft_label">申请人员：</td>
						<td class="ft_content">
							<form:input path="applyBy" readonly="readonly" htmlEscape="false" maxlength="30" class="input-medium " />
							<span class="help-inline">
								<font color="red">*</font>
							</span>
						</td>
						<td class="ft_label">申请时间：</td>
						<td class="ft_content">
							<input name="applyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="<fmt:formatDate value="${borrowNew.applyDate}" pattern="yyyy-MM-dd"/>" />
							<span class="help-inline">
								<font color="red">*</font>
							</span>
						</td>
					</tr>
					<c:if test="${applyFlag eq 'check' }">
						<tr>
							<td class="ft_label">审批人员：</td>
							<td class="ft_content">
								<form:input path="checkedBy" readonly="readonly" htmlEscape="false" maxlength="30" class="input-medium " />
								<span class="help-inline">
									<font color="red">*</font>
								</span>
							</td>
							<td class="ft_label">审批时间：</td>
							<td class="ft_content">
								<input name="checkDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="<fmt:formatDate value="${borrowNew.checkDate}" pattern="yyyy-MM-dd"/>" />
								<span class="help-inline">
									<font color="red">*</font>
								</span>
							</td>
						</tr>
					</c:if>
					<tr>
						<td class="ft_label">申请原因：</td>
						<td class="ft_content" colspan="5">
							<form:textarea path="applyAdvice" htmlEscape="false" rows="4" maxlength="1000" class="input-xxlarge required" />
							<c:if test="${applyFlag eq 'apply' }">
								<form:hidden path="checkResult" />
							</c:if>
							<span class="help-inline">
								<font color="red">*</font>
							</span>
						</td>
					</tr>
					<c:if test="${applyFlag eq 'DQ' }">
						<tr>
							<td class="ft_label">审批结果：</td>
							<td class="ft_content" colspan="5">
								<form:radiobuttons path="checkResult" items="${fns:getDictList('BORROW_NEW_RESULT')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required" />
								<span class="help-inline">
									<font color="red">*</font>
								</span>
							</td>
						</tr>
						<tr>
							<td class="ft_label">审批意见：</td>
							<td class="ft_content" colspan="5">
								<form:textarea path="DQcheckAdvice" htmlEscape="false" rows="4" maxlength="1000" class="input-xxlarge required" />
								<span class="help-inline">
									<font color="red">*</font>
								</span>
							</td>
						</tr>
					</c:if>
					<c:if test="${applyFlag eq 'ZB' }">
						<td class="ft_label">大区审核结果：</td>
						<td class="ft_content">
							<input type="radio" checked="checked" id="radio_yes" value="yes">
							<label for="radio_yes">通过</label>
							<input type="radio" value="no" id="radio_no">
							<label for="radio_no">打回</label>
						</td>
						<tr>
							<td class="ft_label">大区审批意见：</td>
							<td class="ft_content" colspan="5">
								<textarea htmlEscape="false" rows="4" maxlength="1000" class="input-xxlarge required">${borrowNew.DQcheckAdvice }</textarea>
							</td>
						</tr>
						<tr>
							<td class="ft_label">总部审批结果：</td>
							<td class="ft_content" colspan="5">
								<form:radiobuttons path="checkResult" items="${fns:getDictList('BORROW_NEW_RESULT')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required" />
								<span class="help-inline">
									<font color="red">*</font>
								</span>
							</td>
						</tr>
						<tr>
							<td class="ft_label">总部审批意见：</td>
							<td class="ft_content" colspan="5">
								<form:textarea path="ZBcheckAdvice" htmlEscape="false" rows="4" maxlength="1000" class="input-xxlarge required" />
								<span class="help-inline">
									<font color="red">*</font>
								</span>
							</td>
						</tr>
					</c:if>
					<tr>
						<td colspan="6">
							<div class="searchButton">
								<shiro:hasPermission name="postloan:borrowNew:edit">
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