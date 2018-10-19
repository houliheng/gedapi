<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">
	$(document).ready(function() {
		$("#blacklistDiv").hide();
		adjustTextareaLength('blacklistRemarks', 'pre');
		adjustTextareaLength('suggestionDesc', 'pre1');
		$("#processSuggestionInfoForm").validate({
		submitHandler : function(form) {
			var flag = $("input[name='passFlag']:checked").val();
			var sta = $("#sta").val();
			if (flag == "black") {
				confirmx("确认加入黑名单吗？", function() {
					loading('正在提交，请稍等...');
					form.submit();
				});
			} else if((flag == "no") && (sta == 0 || sta=="0")){
				confirmx("确认要结束该流程吗？", function() {
					loading('正在提交，请稍等...');
					form.submit();
				});
			}else if((flag == "finish") && (sta == 0 || sta=="0")){
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
	function validate(show) {
		//当选择"拒绝并加入黑名单"之外的选项时，"加黑说明"设置为不可编辑。
		if (show == 'non_display') {
			$("#blacklistDiv").hide();
			$("#btnSave").show();
		} else {
			//当选择“拒绝并加入黑名单”后，将“加黑说明”设置为可编辑
			$("#blacklistDiv").show();
			$("#btnSave").hide();
		}
		$("input[name='passFlag']:checked").val();
	}

	function validateSubmit(sta) {
		$("#sta").attr("value", sta);
		var passFlag = $("input[name='passFlag']:checked").val();
		if ('${actTaskParam.taskDefKey}' != "utask_fgssx" && '${actTaskParam.taskDefKey}' != "utask_zgssxqr" && '${actTaskParam.taskDefKey}' != "utask_dqsxqr") {
			if (sta == 0 && (passFlag == 'yes' || (passFlag == 'no' && '${actTaskParam.taskDefKey}' == 'utask_dqfkzysh'))) {//提交时校验数据完整性
				$.post("${ctx}/credit/checkApprove/validateMandatoryField", {
				passFlag : passFlag,
				applyNo : '${actTaskParam.applyNo}',
				taskDefKey : '${actTaskParam.taskDefKey}',
				approProductTypeCode : $("#checkApproveProductType").val()
				}, function(data) {
					if (data) {
						if (data.status == "1") {//检验成功
							$("#processSuggestionInfoForm").submit();
						} else {
							alertx(data.message);
						}
					}
				});
			} else {
				$("#processSuggestionInfoForm").submit();
			}
		} else if('${actTaskParam.taskDefKey}' == "utask_fgssx"){
			if (sta == 0 && passFlag == 'yes') {
				$.ajax({
				url : "${ctx}/credit/checkApproveUnion/validateSubmit",
				data : {
					applyNo : '${actTaskParam.applyNo}'
				},
				type : "post",
				async : false,
				dataType : "JSON",
				success : function(data) {
					if (data.status == "1") {
						$("#btnValue").val("1");
						$("#processSuggestionInfoForm").submit();
					} else {
						alertx(data.message);
					}
				}
				});
			} else {
				$("#btnValue").val("1");
				$("#processSuggestionInfoForm").submit();
			}
		}else{
			/*if (sta == 0 && passFlag == 'yes') {
				$.ajax({
				url : "${ctx}/credit/checkApproveUnion/validateSubmit",
				data : {
					applyNo : '${actTaskParam.applyNo}'
				},
				type : "post",
				async : false,
				dataType : "JSON",
				success : function(data) {
					if (data.status == "1") {
						$("#btnValue").val("1");
						$("#processSuggestionInfoForm").submit();
					} else {
						alertx(data.message);
					}
				}
				});
			} else {
				$("#btnValue").val("1");
				$("#processSuggestionInfoForm").submit();
			}*/
            $("#btnValue").val("1");
            $("#processSuggestionInfoForm").submit();
		}
	}
</script>
<div class="searchInfo">
	<h3 class="searchTitle">审批结论</h3>
	<div class="searchCon filter">
		<form:form id="processSuggestionInfoForm" modelAttribute="processSuggestionInfo" action="${ctx}/credit/checkApprove/saveApproveSuggestion" method="post" class="form-horizontal">
			<form:hidden path="id" />
			<input type="hidden" id="checkApproveAmount" name="checkApproveAmount" value="${checkApprove.contractAmount}" />
			<input type="hidden" id="checkApproveProductType" name="checkApproveProductType" value="${checkApprove.approProductTypeCode}" />
			<%@ include file="/WEB-INF/views/app/credit/workflow/include/actTaskParams.jsp"%>
			<input type="hidden" id="sta" name="sta" value="" />
			<input type="hidden" id="deleteDataFlag" name="deleteDataFlag" value="" />
			
			<sys:message content="${approveMessage}" />
			<table class="fromTable">
				<tr>
					<td class="ft_label" style="width: 10%;">审批结论：</td>
					<td class="ft_content" style="width: 70%;">
						<input type="radio" name="passFlag" value="yes" id="radio_yes" class="required" onclick="validate('non_display')">
						<label for="radio_yes">通过</label>
						&nbsp;&nbsp;
						<c:if test="${actTaskParam.taskDefKey != 'utask_fgsfksh'}">
							<input type="radio" name="passFlag" value="back" id="radio_back" class="required" onclick="validate('non_display')">
							<label for="radio_back">打回</label>
						&nbsp;&nbsp;
						</c:if>
						<c:if test="${actTaskParam.taskDefKey != 'utask_zgssxqr' && actTaskParam.taskDefKey != 'utask_dqsxqr'}">
							<input type="radio" name="passFlag" value="backToSQLR" id="radio_backToSQLR" class="required" onclick="validate('non_display')">
							<label for="radio_backToSQLR">打回到申请录入</label>
						&nbsp;&nbsp;
						</c:if>
						<c:if test="${actTaskParam.taskDefKey != 'utask_qyfksh' && actTaskParam.taskDefKey != 'utask_qyjlsh' && actTaskParam.taskDefKey != 'utask_dqfkzysh' && actTaskParam.taskDefKey != 'utask_dqfkjlsh'&&actTaskParam.taskDefKey != 'utask_fgsfksh'&& actTaskParam.taskDefKey != 'utask_fgsjlsh'}">
							<input type="radio" name="passFlag" value="no" id="radio_no" class="required" onclick="validate('non_display')">
							<label for="radio_no">拒绝</label>
							&nbsp;&nbsp;
							<input type="radio" name="passFlag" value="black" id="radio_black" class="required" onclick="validate('dispaly')">
							<label for="radio_black">拒绝并加入黑名单</label>
							<font color="red">*</font>
						</c:if>	
						<c:if test="${actTaskParam.taskDefKey eq 'utask_zgsjlsh'}">
						&nbsp;&nbsp;
						<input type="radio" name="passFlag" value="finish" id="radio_finish" class="required" onclick="validate('non_display')">
						<label for="radio_finish">同意并结束流程</label>
						</c:if>
					</td>
				</tr>
				<tr id="blacklistDiv">
					<td class="ft_label" style="width: 10%;">加黑说明：</td>
					<td class="ft_content" style="width: 70%;">
						<pre class="textarea-width pre-style required" id="pre"></pre>
						<textarea name="blacklistRemarks" id="blacklistRemarks" htmlEscape="false" rows="4" maxlength="1000" class="textarea-width textarea-style required" onkeyup="adjustTextareaLength('blacklistRemarks','pre')">${blacklistRemarks }</textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label" style="width: 10%;">审批意见：</td>
					<td class="ft_content" style="width: 70%;">
						<pre class="textarea-width pre-style required" id="pre1"></pre>
						<form:textarea path="suggestionDesc" htmlEscape="false" rows="4" maxlength="1000" class="textarea-width textarea-style required" onkeyup="adjustTextareaLength('suggestionDesc','pre1')" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td style="text-align: right;" colspan="2">
						<input id="btnSave" class="btn btn-primary" type="button" value="保 存" btnValue="1" onclick="validateSubmit(1)" />
						&nbsp;
						<input id="btnSubmit" class="btn btn-primary" type="button" value="提 交" btnValue="0" onclick="validateSubmit(0)" />
						&nbsp;
						<a id="checkApproveSkipId" target="_parent"></a>
					</td>
				</tr>
			</table>
		</form:form>
	</div>
</div>
<c:if test="${not empty close}">
	<script type="text/javascript">
		goToPage('${ctx}${actTaskParam.headUrl}', 'checkApproveSkipId');
	</script>
</c:if>