<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>提前还款管理(新)</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		var remainCapitalAmount=new Number("${advanceRepayGet.remainCapitalAmount}");
		$("#remainCapitalAmount").val(outputmoney(remainCapitalAmount));
		var remainServiceFee=new Number("${advanceRepayGet.remainServiceFee}");
		$("#remainServiceFee").val(outputmoney(remainServiceFee));
		var remainInterestAmount=new Number("${advanceRepayGet.remainInterestAmount}");
		$("#remainInterestAmount").val(outputmoney(remainInterestAmount));
		var remainManagementFee=new Number("${advanceRepayGet.remainManagementFee}");
		$("#remainManagementFee").val(outputmoney(remainManagementFee));
		var allAdvanceRepay=new Number("${advanceRepayGet.allAdvanceRepay}");
		$("#allAdvanceRepay").val(outputmoney(allAdvanceRepay));
		var remainAllInterest=new Number("${advanceRepayGet.remainAllInterest}");
		$("#remainAllInterest").val(outputmoney(remainAllInterest));
		$("#inputForm").validate({
		submitHandler : saveForm,
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});
	});
	//利息总额=剩余应还利息+剩余应还管理费
	function interestAll(){
		 var remainManagementFee = $("#remainManagementFee").val().replace(/,/g, "");
	     var remainInterestAmount = $("#remainInterestAmount").val().replace(/,/g, "");
	     var remainAllInterest =new Number(remainManagementFee)+new Number(remainInterestAmount);
		if(remainAllInterest==null||remainAllInterest==""||remainAllInterest=="undefined"){
			$("#remainAllInterest").val(0.00);
		}else{
			$("#remainAllInterest").val(outputmoney(remainAllInterest));
		}
	}
	//提前还款总额=应还本金+利息总额+分期服务费
	function repayAll(){
		 var remainManagementFee = $("#remainManagementFee").val().replace(/,/g, "");
	     var remainInterestAmount = $("#remainInterestAmount").val().replace(/,/g, "");
	     var remainCapitalAmount = $("#remainCapitalAmount").val().replace(/,/g, "");
	     var remainServiceFee = $("#remainServiceFee").val().replace(/,/g, "");
	     var allAdvanceRepay =new Number(remainManagementFee)+new Number(remainInterestAmount)+new Number(remainCapitalAmount)+new Number(remainServiceFee);
	     if(allAdvanceRepay==null||allAdvanceRepay==""||allAdvanceRepay=="undefined"){
	    	 $("#allAdvanceRepay").val(0.00);
	     }else{
	    	 $("#allAdvanceRepay").val(outputmoney(allAdvanceRepay)); 
	     }
	    
	}
	function saveForm() {
		confirmx("提交后不可更改，确认提交！",	saveDate);
	}
	function saveDate(){
		loading('正在提交，请稍等...');
		 var remainCapitalAmount = $("#remainCapitalAmount").val().replace(/,/g, "");
	     $("#remainCapitalAmount").val(remainCapitalAmount);
	     var remainServiceFee = $("#remainServiceFee").val().replace(/,/g, "");
	     $("#remainServiceFee").val(remainServiceFee);
	     var remainInterestAmount = $("#remainInterestAmount").val().replace(/,/g, "");
	     $("#remainInterestAmount").val(remainInterestAmount);
	     var remainManagementFee = $("#remainManagementFee").val().replace(/,/g, "");
	     $("#remainManagementFee").val(remainManagementFee);
	     var allAdvanceRepay = $("#allAdvanceRepay").val().replace(/,/g, "");
	     $("#allAdvanceRepay").val(allAdvanceRepay);
	     var remainAllInterest = $("#remainAllInterest").val().replace(/,/g, "");
	     $("#remainAllInterest").val(remainAllInterest);
		var formJson = $("#inputForm").serializeJson();
		$.post("${ctx}/accounting/advanceRepayGet/save", formJson, function(data) {
			if (data) {
				closeTip();
				if (data.status == 1) {//保存成功
					alertx(data.message, function() {
						closeJBox();
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
	<form:form id="inputForm" modelAttribute="advanceRepayGet" action="${ctx}/accounting/advanceRepayGet/save" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<sys:message content="${message}" />
		<h3 class="infoListTitle">提前还款信息</h3>
		<table class="filter">
			<tr>
				<td class="ft_label">合同编号：</td>
				<td class="ft_content">
					<form:input path="contractNo" readonly="true" htmlEscape="false"  class="input-xlarge required" />
					<font style="color: red">*</font>
				</td>
				<td class="ft_label">应还本金：</td>
				<td class="ft_content">
					<form:input path="remainCapitalAmount" onkeyup="keyPress11(this);" maxlength="16" onblur="repayAll();this.value=outputmoney(this.value);" htmlEscape="false" class="input-xlarge required" />
					<font style="color: red">*</font>
				</td>
			</tr>
			<tr>
				<td class="ft_label">剩余应还利息：</td>
				<td class="ft_content">
					<form:input path="remainInterestAmount" onkeyup="keyPress11(this);" maxlength="16" onblur="interestAll();repayAll();this.value=outputmoney(this.value);" htmlEscape="false" class="input-xlarge required number" />
					<font style="color: red">*</font>
				</td>
				<td class="ft_label">剩余应还管理费：</td>
				<td class="ft_content">
					<form:input path="remainManagementFee" onkeyup="keyPress11(this);" maxlength="16" onblur="interestAll();repayAll();this.value=outputmoney(this.value);" htmlEscape="false" class="input-xlarge required number" />
					<font style="color: red">*</font>
				</td>
			</tr>
			
			<tr>
				<td class="ft_label">利息总额：</td>
				<td class="ft_content">
					<form:input path="remainAllInterest" readonly="true"  htmlEscape="false" maxlength="18" class="input-xlarge required number" />
					<font style="color: red">*</font>
				</td>
				<td class="ft_label">提前还款总额：</td>
				<td class="ft_content">
					<form:input path="allAdvanceRepay" readonly="true"    htmlEscape="false" maxlength="18" class="input-xlarge required number" />
					<font style="color: red">*</font>
				</td>
			</tr>
			<tr>
				<td class="ft_label">还款日：</td>
				<td class="ft_content">
					<input type="text"  id="repayDate" name="repayDate" value="${advanceRepayGet.repayDate}"  class="input-xlarge Wdate required"  onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd'});"/>
					<font color="red">*</font>
				</td>
				<td class="ft_label">分期服务费：</td>
				<td class="ft_content">
					<form:input path="remainServiceFee"  onkeyup="keyPress11(this);" maxlength="16" onblur="repayAll();this.value=outputmoney(this.value);" htmlEscape="false" class="input-xlarge"/>
				</td>
			</tr>
		</table>
		<div class="searchButton">
			<input id="rsBtnSubmit" class="btn btn-primary" type="submit" value="发送" />
			&nbsp;
			<input id="btnCancel" class="btn" type="button" value="关闭" onclick="closeJBox();" />
		</div>
	</form:form>
</body>
</html>