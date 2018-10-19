<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>数据处理成功管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: saveForm,
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
		
		function saveForm() {
			loading();
			top.$.jBox.tip.mess = null;
			var formJson = $("#inputForm").serializeJson();
			$.post("${ctx}/accounting/cleanContract/save", formJson, function(data) {
				if (data) {
					closeTip();
					if (data.status == 1) {//保存成功
						alertx(data.message, function() {
							closeAndReloadACC();
						});
					} else {
						alertx(data.message);
					}
				}
			});
		}
		
		function backPage(){
			var newUrl= "${ctx}/accounting/cleanContract/list?contratNo="+$("#contractNo").val();
			 $("#inputForm").attr('action',newUrl);
		     $("#inputForm").submit();
		}
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="cleanContract" action="${ctx}/accounting/cleanContract/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="dataDt"/>
		<form:hidden path="orgLevel2"/>
		<form:hidden path="orgLevel3"/>
		<form:hidden path="orgLevel4"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">合同编号：</label>
			<div class="controls">
				<form:input path="contractNo" htmlEscape="false" readonly="true" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">期数：</label>
			<div class="controls">
				<form:input path="periodNum" htmlEscape="false"  readonly="true" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">每期还款状态：</label>
			<div class="controls">
				<form:select path="repayPeriodStatus" class="input-medium required">
					<form:option value="" class="class1" label="请选择" />
					<form:options items="${fns:getDictList('PERIOD_STATE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">本息结清日期：</label>
			<div class="controls">
				<input name="capitalInterestRepayDate" type="text"  maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${cleanContract.capitalInterestRepayDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">总结清日期：</label>
			<div class="controls">
				<input name="allRepayDate" type="text"  maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${cleanContract.allRepayDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">还款金额：</label>
			<div class="controls">
				<form:input path="factRepayAmount" htmlEscape="false" class="input-xlarge required "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实还-服务费：</label>
			<div class="controls">
				<form:input path="factServiceFee" htmlEscape="false" class="input-xlarge required "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实还-账户管理费：</label>
			<div class="controls">
				<form:input path="factManagementFee" htmlEscape="false" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实还-本期本金：</label>
			<div class="controls">
				<form:input path="factCapitalAmount" htmlEscape="false" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实还-本期利息：</label>
			<div class="controls">
				<form:input path="factInterestAmount" htmlEscape="false" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实还-违约金：</label>
			<div class="controls">
				<form:input path="factPenaltyAmount" htmlEscape="false" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实还-罚息：</label>
			<div class="controls">
				<form:input path="factFineAmount" htmlEscape="false" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实还-营业外收入：</label>
			<div class="controls">
				<form:input path="factAddAmount" htmlEscape="false" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实还-提前还款费用：</label>
			<div class="controls">
				<form:input path="factBreakAmount" htmlEscape="false" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">退回金额：</label>
			<div class="controls">
				<form:input path="backAmount" htmlEscape="false" class="input-xlarge required"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="accounting:cleanContract:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="closeJBox()"/>
		</div>
	</form:form>
</body>
</html>