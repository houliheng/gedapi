<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>账务清收管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			document.getElementById("cleanAmount").value=outputmoney("${accountClean.cleanAmount}");
			document.getElementById("cleanReturnAmount").value=outputmoney("${accountClean.cleanReturnAmount}");
			$("#inputForm").validate({
				submitHandler: function(form){
					saveForm();
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
		
		function saveForm() {
			loading();
			var valueT1=$("#cleanAmount").val().replace(/,/g,"");		
			$("#cleanAmount").val(valueT1);
			var valueT2=$("#cleanReturnAmount").val().replace(/,/g,"");		
			$("#cleanReturnAmount").val(valueT2);
			top.$.jBox.tip.mess = null;
			var formJson = $("#inputForm").serializeJson();
			$.post("${ctx}/postloan/accountClean/save", formJson, function(data) {
				if (data) {
				closeTip();
					if (data.status == 1) {//保存成功
						alertx(data.message, function() {
						    if(data.data == '7'){
    							parent.parent.page();
    							parent.closeJBox();
						    }else if(data.data == '8'){
						       parent.location.href="${ctx}/postloan/accountClean/list?contractNo=" + '${accountClean.contractNo}';
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
	<form:form id="inputForm" modelAttribute="accountClean" action="${ctx}/postloan/accountClean/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" id="flag" name="flag" value="${flag}"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">合同编号：</label>
			<div class="controls">
				<form:input path="contractNo" htmlEscape="false" maxlength="50" class="input-xlarge required" readOnly="readonly"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">清收金额：</label>
			<div class="controls">
				<form:input path="cleanAmount"  onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" htmlEscape="false" class="input-xlarge  number required"/>
			    <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">清收回款金额：</label>
			<div class="controls">
				<form:input path="cleanReturnAmount" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" htmlEscape="false" class="input-xlarge required"/>
			    <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="postloan:accountClean:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="closeJBox();"/>
		</div>
	</form:form>
</body>
</html>