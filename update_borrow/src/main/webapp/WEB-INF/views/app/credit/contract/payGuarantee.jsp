<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>确认缴费表单</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			if("${contract.factGuaranteeFee}"==0){
				$("#factGuaranteeFee").val("0.00");
			}else{
				var factGuaranteeFee=new Number("${contract.factGuaranteeFee}");
				$("#factGuaranteeFee").val(outputmoney(factGuaranteeFee));
			}
			
			if("${contract.factGuaranteeGold}"==0){
				$("#factGuaranteeGold").val("0.00");
			}else{
				var factGuaranteeGold=new Number("${contract.factGuaranteeGold}");
				$("#factGuaranteeGold").val(outputmoney(factGuaranteeGold));
			} 	
			if("${contract.approProductTypeId}" != "6"){
				$(".serviceFee").hide();
			} 
			if("${contract.factServiceFee}"==0){
				$("#factServiceFee").val("0.00");
			}else{
				var factServiceFee=new Number("${contract.factServiceFee}");
				$("#factServiceFee").val(outputmoney(factServiceFee));
			} 
			$("#inputPayForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					 var factGuaranteeFee = $("#factGuaranteeFee").val().replace(/,/g, "");
				     $("#factGuaranteeFee").val(factGuaranteeFee);
				     var factGuaranteeGold = $("#factGuaranteeGold").val().replace(/,/g, "");
				     $("#factGuaranteeGold").val(factGuaranteeGold);
				     var factServiceFee = $("#factServiceFee").val().replace(/,/g, "");
				     $("#factServiceFee").val(factServiceFee);
					var param = $("#inputPayForm").serialize();
					$.post("${ctx}/credit/applyLoanStatus/pushGedLoan", param, function(data) {
						if (data) {
							closeTip();
							if (data.status == '1') {
								alertx(data.message, function() {
									parent.location.reload();
									closeJBox();
								});
							} else {
								alertx(data.message);
							}
						}
					});
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
	</script>
</head>
<body>
	<form:form id="inputPayForm" modelAttribute="contract" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input name="applyNo" id="applyNo" value="${contract.applyNo }" type="hidden">
		<input name="contractNo" id="contractNo" value="${contract.contractNo }" type="hidden">
		<input name="approProductTypeId"  value="${contract.approProductTypeId}" type="hidden">
		<sys:message content="${message}"/>
		<input name="flag" id="flag" value="${flag}" type="hidden">
		<sys:message content="${message}"/>
		<table class="fromTable filter">
			<tr>
				<td class="ft_label">实收担保费：</td>
				<td class="ft_content">
					<input type="text" id="factGuaranteeFee"  onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" name="factGuaranteeFee" value="${contract.factGuaranteeFee}" maxlength="18" htmlEscape="false" class=" input-medium required" />
					<font color="red">*</font>
				</td>
			</tr>	
			<tr>	
				<td class="ft_label">实收担保金：</td>
				<td class="ft_content">
					<input type="text" id="factGuaranteeGold"  onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" name="factGuaranteeGold" value="${contract.factGuaranteeGold}"  maxlength="18" htmlEscape="false" class=" input-medium required" />
					<font color="red">*</font>
				</td>
			</tr>
			<tr class="serviceFee">	
				<td class="ft_label">实收服务费：</td>
				<td class="ft_content">
					<input type="text" id="factServiceFee"  onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" name="factServiceFee" value="${contract.factServiceFee}"  maxlength="18" htmlEscape="false" class=" input-medium required" />
					<font color="red">*</font>
				</td>
			</tr>
		</table>
		<div class="searchButton" id="buttonDiv">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="确认" />
		</div>
	</form:form>
</body>
</html>