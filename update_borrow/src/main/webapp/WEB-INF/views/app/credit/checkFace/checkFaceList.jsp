<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>面审信息管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		document.getElementById("fundingGap").value = outputmoney("${checkFace.fundingGap}");
		$("#inputForm").validate({
		submitHandler : function(form) {
			var valueT = $("#fundingGap").val().replace(/,/g, "");
			$("#fundingGap").val(valueT);
			var flag = $("input[name='passFlag']:checked").val();
			if (flag == "black") {
				confirmx("确认加入黑名单吗？", function() {
					$("#btnSubmit").attr("disabled", "disabled");
					$("#rsBtnSubmit").attr("disabled", "disabled");
					loading('正在提交，请稍等...');
					saveForm();
				});
			} else {
				$("#btnSubmit").attr("disabled", "disabled");
				$("#rsBtnSubmit").attr("disabled", "disabled");
				loading('正在提交，请稍等...');
				saveForm();
			}
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});
		//页面初始化后，设置“加黑说明”不可编辑。
		$("#blacklistDiv").hide();

		adjustTextareaLength("loanPurposeDesc", "preLoanPurposeDesc");
		adjustTextareaLength("loanRepayDesc", "preLoanRepayDesc");
		adjustTextareaLength("companyDesc", "preCompanyDesc");
		adjustTextareaLength("fundingDesc", "preFundingDesc");
		adjustTextareaLength("familyDesc", "preFamilyDesc");
		adjustTextareaLength("guaranteeMeasureDesc", "preGuaranteeMeasureDesc");
		adjustTextareaLength("guaranteePersonDesc", "preGuaranteePersonDesc");
		adjustTextareaLength("description", "preDescription");
		adjustTextareaLength("blacklistRemarks", "preBlacklistRemarks");
		adjustTextareaLength("suggestionDesc", "preSuggestionDesc");

	});

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
	
	//验证并提交工作流流程 
	function validateWorkflowSumit(v){
		$("#sta").attr("value", v);
		var applyNo = document.getElementById("applyNo").value;
		$.ajax({
			url : "${ctx}/credit/fhRiskControl/verifyFhCheckNumAjax",
			type: 'POST', 
			cache:false, 
			data : {
				applyNo : applyNo
			},
			async : false,
			dataType : "JSON",
			type : "POST",
			success : function(data) {
				if (data.status == "1") {
					var valueT = $("#fundingGap").val().replace(/,/g, "");
					$("#fundingGap").val(valueT);
					var flag = $("input[name='passFlag']:checked").val();
					if (flag == "black") {
						confirmx("确认加入黑名单吗？", function() {
							$("#btnSubmit").attr("disabled", "disabled");
							$("#rsBtnSubmit").attr("disabled", "disabled");
							loading('正在提交，请稍等...');
							saveForm();
						});
					} else {
						$("#btnSubmit").attr("disabled", "disabled");
						$("#rsBtnSubmit").attr("disabled", "disabled");
						loading('正在提交，请稍等...');
						saveForm();
					}
				} else {
					alertx(data.message);
				}
			}
		});
	}

	function saveForm() {
		top.$.jBox.tip.mess = null;
		var paramJson = $("#inputForm").serializeJson();
		$.post("${ctx}/credit/checkFace/save", paramJson, function(data) {
			if (data) {
				$("#btnSubmit").removeAttr("disabled");
				$("#rsBtnSubmit").removeAttr("disabled");
				if (data.status == 1) {
					alertx(data.message, function() {
						goToPage('${ctx}${actTaskParam.headUrl}', 'checkFaceSkipId');
					});
				} else {
					alertx(data.message);
				}
				closeTip();
			}
		});
	}
</script>
</head>
<body>
	<div class="filter">
		<form:form id="inputForm" modelAttribute="checkFace" action="${ctx}/credit/checkFace/save" method="post" class="form-horizontal">
			<form:hidden path="id" />
			<input type="hidden" id="applyNo" name="applyNo" value="${actTaskParam.applyNo}" />
			<input type="hidden" id="taskId" name="taskId" value="${actTaskParam.taskId}" />
			<input type="hidden" id="taskDefKey" name="taskDefKey" value="${actTaskParam.taskDefKey}" />
			<input type="hidden" id="procDefId" name="procDefId" value="${actTaskParam.procDefId}" />
			<input type="hidden" id="status" name="status" value="${actTaskParam.status}" />
			<input type="hidden" id="procInstId" name="procInstId" value="${actTaskParam.procInstId}" />
			<input type="hidden" id="headUrl" name="headUrl" value="${actTaskParam.headUrl}" />
			<!-- 跳转用a标签 -->
			<a id="checkFaceSkipId" target="_parent"></a>
			<input type="hidden" id="sta" name="sta" value="" />
			<sys:message content="${message}" />
			<table class="filter">
				<tr>
					<td class="ft_label">资金缺口(元)：</td>
					<td class="ft_content">
						<input name="fundingGap" id="fundingGap" type="text" class="input-medium required" value="${checkFace.fundingGap}" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">预借款期限(月)：</td>
					<td class="ft_content">
						<form:select path="perLoanDate" class="input-medium required">
							<form:option value="" label="请选择"></form:option>
							<form:options items="${fns:getDictList('PER_LOAN_DATE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font style="color: red">*</font>
					</td>
					<td class="ft_label">参与者：</td>
					<td class="ft_content">
						<form:select path="participant" class="input-medium">
							<form:option value="" label="请选择"></form:option>
							<form:options items="${fns:getDictList('apply_participant')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
					</td>
				</tr>
				<tr>
					<td class="ft_label">借款用途：</td>
					<td class="ft_content" colspan="7">
						<pre class="input-medium textareaWidth pre-style" id="preLoanPurposeDesc"></pre>
						<textarea rows="5" cols="300" class="input-medium textareaWidth textarea-style required" maxlength="1000" name="loanPurposeDesc" id="loanPurposeDesc" onkeyup="adjustTextareaLength('loanPurposeDesc','preLoanPurposeDesc')">${checkFace.loanPurposeDesc}</textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">还款来源：</td>
					<td class="ft_content" colspan="5">
						<pre class="input-medium textareaWidth pre-style" id="preLoanRepayDesc"></pre>
						<textarea rows="5" cols="300" class="input-medium textarea-style textareaWidth required" maxlength="1000" name="loanRepayDesc" id="loanRepayDesc" onkeyup="adjustTextareaLength('loanRepayDesc','preLoanRepayDesc')">${checkFace.loanRepayDesc}</textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">企业情况：</td>
					<td class="ft_content" colspan="5">
						<pre class="input-medium textareaWidth pre-style" id="preCompanyDesc"></pre>
						<textarea rows="5" cols="1" class="input-medium textareaWidth textarea-style required" maxlength="1000" placeholder="包括但不限于行业、上下游、目前状况" name="companyDesc" id="companyDesc" onkeyup="adjustTextareaLength('companyDesc','preCompanyDesc')">${checkFace.companyDesc}</textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">资产状况：</td>
					<td class="ft_content" colspan="5">
						<pre class="input-medium textareaWidth pre-style" id="preFundingDesc"></pre>
						<textarea rows="5" cols="300" class="input-medium textarea-style textareaWidth required" maxlength="1000" name="fundingDesc" id="fundingDesc" onkeyup="adjustTextareaLength('fundingDesc','preFundingDesc')">${checkFace.fundingDesc}</textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">家庭情况：</td>
					<td class="ft_content" colspan="5">
						<pre class="input-medium textareaWidth pre-style" id="preFamilyDesc"></pre>
						<textarea rows="5" cols="300" class="input-medium textarea-style textareaWidth required" maxlength="1000" name="familyDesc" id="familyDesc" placeholder="包括但不限于子女状况" onkeyup="adjustTextareaLength('familyDesc','preFamilyDesc')">${checkFace.familyDesc}</textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">担保措施：</td>
					<td class="ft_content" colspan="5">
						<pre class="input-medium textareaWidth pre-style" id="preGuaranteeMeasureDesc"></pre>
						<textarea rows="5" cols="300" class="input-medium textareaWidth textarea-style required" maxlength="1000" name="guaranteeMeasureDesc" id="guaranteeMeasureDesc" placeholder="有无资产抵押，能否提供担保人等" onkeyup="adjustTextareaLength('guaranteeMeasureDesc','preGuaranteeMeasureDesc')">${checkFace.guaranteeMeasureDesc}</textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">担保人情况：</td>
					<td class="ft_content" colspan="5">
						<pre class="input-medium textareaWidth pre-style" id="preGuaranteePersonDesc"></pre>
						<textarea rows="5" cols="300" class="input-medium textarea-style textareaWidth required" maxlength="1000" name="guaranteePersonDesc" id="guaranteePersonDesc" onkeyup="adjustTextareaLength('guaranteePersonDesc','preGuaranteePersonDesc')">${checkFace.guaranteePersonDesc}</textarea>
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">电核网查外访，注意事项：</td>
					<td class="ft_content" colspan="5">
						<pre class="input-medium textareaWidth pre-style" id="preDescription"></pre>
						<textarea rows="5" cols="300" class="input-medium textarea-style textareaWidth required" maxlength="1000" name="description" id="description" onkeyup="adjustTextareaLength('description','preDescription')">${checkFace.description}</textarea>
						<font color="red">*</font>
					</td>
				</tr>
			</table>
			<div id="isHideSuggestionDiv">
				<table>
					<tr>
						<td class="ft_label">面审结论：</td>
						<td class="" colspan="5">
							<input type="radio" name="passFlag" value="yes" id="radio_yes" class="required" onclick="validate('non_display')">
							<label for="radio_yes">通过</label>
							&nbsp;&nbsp;
							<input type="radio" name="passFlag" value="backToSQLR" id="radio_back" class="required" onclick="validate('non_display')">
							<label for="radio_back">打回到申请录入</label>
							&nbsp;&nbsp;
							<input type="radio" name="passFlag" value="no" id="radio_no" class="required" onclick="validate('non_display')">
							<label for="radio_no">拒绝</label>
							&nbsp;&nbsp;
							<input type="radio" name="passFlag" value="black" id="radio_black" class="required" onclick="validate('dispaly')">
							<label for="radio_black">拒绝并加入黑名单</label>
							<font color="red">*</font>
						</td>
					</tr>
					<tr id="blacklistDiv">
						<td class="ft_label">加黑说明：</td>
						<td class="" colspan="5">
							<pre class="input-medium textareaWidth pre-style required" id="preBlacklistRemarks"></pre>
							<textarea rows="5" cols="100" maxlength="1000" name="blacklistRemarks" id="blacklistRemarks" class="input-medium textarea-style textareaWidth required" onkeyup="adjustTextareaLength('blacklistRemarks','preBlacklistRemarks')"></textarea>
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">面审意见：</td>
						<td class="" colspan="5">
							<pre class="input-medium textareaWidth pre-style" id="preSuggestionDesc"></pre>
							<textarea rows="5" cols="100" maxlength="1000" id="suggestionDesc" name="suggestionDesc" class="input-medium textarea-style textareaWidth required" onkeyup="adjustTextareaLength('suggestionDesc','preSuggestionDesc')">${processSuggestionInfo.suggestionDesc}</textarea>
							<font color="red">*</font>
						</td>
					</tr>
				</table>
				<div class="searchButton">
					<shiro:hasPermission name="credit:checkFace:edit">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="validateSubmit(1)" />&nbsp;</shiro:hasPermission>
					<input id="rsBtnSubmit" class="btn btn-primary" type="button" value="提 交" onclick="validateWorkflowSumit(0)" />
					&nbsp;
				</div>
			</div>
		</form:form>
	</div>
</body>
</html>