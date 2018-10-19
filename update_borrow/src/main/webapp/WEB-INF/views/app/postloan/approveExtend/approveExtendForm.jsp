<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">
	$(document).ready(function() {

		$(".font").show();
		$("#approveBtnSubmit").show();
		$("#radio_yes").removeAttr("disabled");
		if ('${actTaskParam.taskDefKey}' != "utask_qysp") {
			$("#radio_back").removeAttr("disabled");
		}
		$("#radio_refuse").removeAttr("disabled");
		$("#suggestion").removeAttr("readonly");
		$("#approveExtendFormId").validate({
		submitHandler : saveForm,
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});
	});
	function approveExtend() {
		$("#approveExtendId").toggle(600);
	}

	function saveForm() {
		top.$.jBox.tip.mess = null;
		var formJson = $("#approveExtendFormId").serializeJson();
		$.post("${ctx}/postloan/approveExtend/save", formJson, function(data) {
			if (data) {
				if (data.status == 1) {//保存成功
					alertx(data.message, function() {
						closeAndReloadPostLoan();
					});
				} else {
					alertx(data.message);
				}
			}
		});
	}
</script>
<div class="searchInfo">
	<h3 onclick="approveExtend()" class="searchTitle">展期审批</h3>
	<div id="approveExtendId" class="searchCon">
		<form:form id="approveExtendFormId" modelAttribute="ApproveExtend" action="${ctx}/postloan/approveExtend/save" method="post" class="form-horizontal">
			<input type="hidden" id="contractNo" name="contractNo" value="${contractNo}" />
			<input type="hidden" id="extendId" name="extendId" value="${actTaskParam.extendId}" />
			<input type="hidden" id="applyNo" name="applyNo" value="${actTaskParam.applyNo}" />
			<input type="hidden" id="taskId" name="taskId" value="${actTaskParam.taskId}" />
			<input type="hidden" id="taskDefKey" name="taskDefKey" value="${actTaskParam.taskDefKey}" />
			<input type="hidden" id="procDefId" name="procDefId" value="${actTaskParam.procDefId}" />
			<input type="hidden" id="status" name="status" value="${actTaskParam.status}" />
			<input type="hidden" id="procInstId" name="procInstId" value="${actTaskParam.procInstId}" />
			<sys:message content="${message}" />
			<table class="fromTable filter">
				<tr>
					<td class="ft_label">审批结果：</td>
					<td class="ft_content">
						<input type="radio" name="approveResult" value="yes" id="radio_yes" class="required">
						<label for="radio_yes">通过</label>
						&nbsp;&nbsp;
						<input type="radio" name="approveResult" value="back" id="radio_back" class="required">
						<label for="radio_back">打回</label>
						&nbsp;&nbsp;
						<input type="radio" name="approveResult" value="refuse" id="radio_refuse" class="required">
						<label for="radio_refuse">拒绝</label>
						<font class="font" color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">审批意见：</td>
					<td class="ft_content" colspan="5">
						<textarea id="suggestion" name="suggestion" rows="5" class="textarea-width textarea-style required" maxlength="1000" htmlEscape="false"></textarea>
						<font class="font" color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="searchButton" colspan="6" style="text-align: right;">
						<input id="approveBtnSubmit" type="submit" class="btn btn-primary" value="提 交" />
					</td>
				</tr>
			</table>
		</form:form>
	</div>
</div>