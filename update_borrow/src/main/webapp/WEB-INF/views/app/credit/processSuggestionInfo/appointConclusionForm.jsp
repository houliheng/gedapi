<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<script type="text/javascript">
	$(document).ready(function() {
		adjustTextareaLength('descSuggestion', 'sugpre');
		$("#appointInfoForm").validate({
		submitHandler : function(form) {	
			var passFlag = $("input[name='passFlag']:checked").val();
			 if(passFlag == "no"){
				confirmx("你确定要结束流程吗？",function(){
					saveAppointConclusion();
				});
			}else if(passFlag=="yes"){
				if($("#actProc").val().indexOf("gqcc_loan_union") == -1){
					$.post("${ctx}/credit/appointInfo/validateGedAccount", {idNum:"${applyInfo.applyRegister.idNum}",legalNum:"${companyMessage.corporationCardIdNo}",custType:"{applyInfo.applyRegister.custType}",socialNo:"${applyInfo.applyRegister.comIdNum}"}, function(data) {
						if (data) {
							if (data.status == 3 || data.status == "3") {
								alertx("<font style='color:red'>"+data.message+"</font>",function(){
									saveAppointConclusion();
								});
							}else if(data.status == "0" || data.status == 0){
								alertx(data.message,function(){
									saveAppointConclusion();
								});
							}else{
								saveAppointConclusion();
							}
						}
					});
				}else{
					saveAppointConclusion();
				}
			}else{
				saveAppointConclusion();
			} 
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});
		
		
		function saveAppointConclusion(){
			loading();
			var param = $("#appointInfoForm").serialize();
			var passFlag = $("input[name='passFlag']:checked").val();
			$.post("${ctx}/credit/appointInfo/saveAppointConclusion", param, function(data) {
				if (data) {
				closeTip();
					if (data.status == '1') {
						$("#rs_msg").html("<div id='messageBox' class='alert alert-success'><button data-dismiss='alert' class='close'>×</button>提交成功！页面即将关闭...</div>");
						goToPage('${ctx}${actTaskParam.headUrl}', 'appointSkipId');
					} else {
						$("#rs_msg").html("<div id='messageBox' class='alert alert-error'><button data-dismiss='alert' class='close'>×</button>" + data.message + "</div>");
					}
				}
			});
		}
	});
  
</script>
<div class="searchInfo">
	<h3 class="searchTitle">预约结论</h3>
	<div id="rs_msg"></div>
	<div class="searchCon">
		<form:form id="appointInfoForm" modelAttribute="processSuggestionInfo" action="${ctx}/credit/appointInfo/saveAppointConclusion" method="post" class="form-horizontal">
			<form:hidden path="id" />
			<input type="hidden" value="${actTaskParam.procDefId}" id="actProc">
			<a id="appointSkipId" target="_parent"></a>
			<%@ include file="/WEB-INF/views/app/credit/workflow/include/actTaskParams.jsp"%>
			<sys:message content="${message}" />
			<table class="fromTable filter">
				<tr>
					<td class="ft_label" style="width: 10%;">预约结果：</td>
					<td class="ft_content" style="width: 90%;">
						<input type="radio" name="passFlag" value="yes" id="radio_yes" class="required" />
						<label for="radio_yes">同意签约</label>
						<input type="radio" name="passFlag" value="no" id="radio_no" class="required" />
						<label for="radio_no">放弃签约</label>
						<input type="radio" name="passFlag" value="back" id="radio_back" class="required">
						<label for="radio_back">打回</label>
					</td>
				</tr>
				<tr>
					<td class="ft_label" style="width: 10%;">预约时间：</td>
					<td class="ft_content" style="width: 90%;">
						<input id="reserverTime" name="reserveTime" readonly="true" type="text" maxlength="20" class="input-medium Wdate required" value="${processSuggestionInfo.reserveTime}" onclick="WdatePicker({onpicked:dateWhite(this),minDate:new Date()})" />
					</td>
				</tr>
				<tr>
					<td class="ft_label" style="width: 10%;">预约意见：</td>
					<td class="ft_content" style="width: 90%;">
						<pre class="textarea-width pre-style required" id="sugpre"></pre>
						<form:textarea path="suggestionDesc" id="descSuggestion" htmlEscape="false" rows="3" maxlength="1000" class="textarea-width textarea-style required" onkeyup="adjustTextareaLength('descSuggestion', 'sugpre')" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: right;">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="提交" />
					</td>
				</tr>
			</table>
		</form:form>
	</div>
</div>