<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">
	$(document).ready(function() {
		$("#blacklistDiv").hide();
		adjustTextareaLength('blacklistRemarks', 'pre');
		adjustTextareaLength('suggestionDesc', 'pre1');
		$("#processSuggestionInfoForm").validate({
		submitHandler : function(form) {
			var flag = $("input[name='passFlag']:checked").val();
			var sta  = $("#sta").val();
			if (flag == "black") {
				confirmx("确认加入黑名单吗？", function() {
					loading('正在提交，请稍等...');
					form.submit();
				});
			} else if(flag == "no" && (sta == 0 || sta == "0")){
				confirmx("确认要结束该流程吗？", function() {
					loading('正在提交，请稍等...');
					form.submit();
				});
			}else {
				loading('正在提交，请稍等...');
				form.submit();
			}
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});
	});

	//保存、提交
	function saveSuggestion(flag) {
		top.$.jBox.tip.mess = null;
		var formJson = $("#processSuggestionInfoForm").serializeJson();
		$.post("${ctx}/credit/processSuggestionInfo/saveReviewConclusion?blacklistRemarks=" + $("#blacklistRemarks").val(), formJson, function(data) {
			if (data) {
				alertx(data.message);
			}
		});
	}

	function validate(show) {
		//当选择"拒绝并加入黑名单"之外的选项时，"加黑说明"设置为不可编辑。
		if (show == 'non_display') {
			$("#blacklistDiv").hide();
			$("#btnSubmit").show();
		} else {
			//当选择“拒绝并加入黑名单”后，将“加黑说明”设置为可编辑
			$("#blacklistDiv").show();
			$("#btnSubmit").hide();
		}
		$("input[name='passFlag']:checked").val();
	}

	function validateSubmit(v) {
		$("#sta").attr("value", v);
	}
</script>
<div class="searchInfo">
	<h3 class="searchTitle">复议结论</h3>
	<div class="searchCon filter">
		<form:form id="processSuggestionInfoForm" modelAttribute="processSuggestionInfo" action="${ctx}/credit/reviewConclusionIndex/saveReviewConclusion" method="post" class="form-horizontal">
			<form:hidden path="id" />
			<%@ include file="/WEB-INF/views/app/credit/workflow/include/actTaskParams.jsp"%>
			<input type="hidden" id="sta" name="sta" value="" />
			<input type="hidden" id="readOnly" name="readOnly" value="${readOnly }" />
			<sys:message content="${reviewMessage}" />
			<table class="fromTable">
				<tr>
					<td class="ft_label" style="width: 10%;">审批结论：</td>
					<td class="ft_content" style="width: 30%;">
						<input type="radio" name="passFlag" value="yes" id="radio_yes" class="required" onclick="validate('non_display')">
						<label for="radio_yes">通过</label>
						&nbsp;&nbsp;
						<input type="radio" name="passFlag" value="no" id="radio_no" class="required" onclick="validate('non_display')">
						<label for="radio_no">拒绝</label>
						&nbsp;&nbsp;
						<input type="radio" name="passFlag" value="black" id="radio_black" class="required" onclick="validate('dispaly')">
						<label for="radio_black">拒绝并加入黑名单</label>
					</td>
				</tr>
				<tr id="blacklistDiv">
					<td class="ft_label" style="width: 10%;">加黑说明：</td>
					<td class="ft_content" style="width: 90%;">
						<pre class="textarea-width pre-style required" id="pre"></pre>
						<textarea name="blacklistRemarks" id="blacklistRemarks" htmlEscape="false" rows="4" maxlength="1000" class="textarea-width textarea-style required" onkeyup="adjustTextareaLength('blacklistRemarks','pre')">${blacklistRemarks }</textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label" style="width: 10%;">审批意见：</td>
					<td class="ft_content" style="width: 90%;">
						<pre class="textarea-width pre-style required" id="pre1"></pre>
						<form:textarea path="suggestionDesc" htmlEscape="false" rows="4" maxlength="1000" class="textarea-width textarea-style required" onkeyup="adjustTextareaLength('suggestionDesc','pre1')" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td style="text-align: right;" colspan="2">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="validateSubmit(1)" />
						&nbsp;
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="提 交" onclick="validateSubmit(0)" />
						&nbsp;
						<a id="reviewCounclusionSkipId" target="_parent" ></a>
					</td>
				</tr>
			</table>
		</form:form>
	</div>
</div>
<c:if test="${not empty close}">
	<script type="text/javascript">
		goToPage('${ctx}${actTaskParam.headUrl}','reviewCounclusionSkipId');
	</script>
</c:if>
