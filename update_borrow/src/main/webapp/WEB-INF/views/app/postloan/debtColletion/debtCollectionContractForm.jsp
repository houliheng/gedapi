<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
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
		
	</script>
<div class="wrapper">
	<sys:message content="${message}" />
	<div class="filter">
		<h3 class="searchTitle">合同信息</h3>
		<table class="fromtable">
			<tr>
				<td class="ft_label" style="width:200px">客户姓名：</td>
				<td class="ft_content"><input type="text" id="custName" htmlEscape="false" maxlength="20" class="input-medium" style="margin-bottom: 3px" value="${plContract.custName}" /></td>
				<td class="ft_label">产品类型：</td>
				<td class="ft_content"><input type="hidden" id="approProductId" htmlEscape="false" maxlength="32" class="input-medium" style="margin-bottom: 3px" readonly="true"
					value="${plContract.approProductId}" /> <input type="text" id="approProductName" htmlEscape="false" maxlength="30" class="input-medium" style="margin-bottom: 3px" readonly="true"
					value="${plContract.approProductName}" /></td>
			</tr>
			<tr>
				<td class="ft_label">期限：</td>
				<td class="ft_content"><input type="text" id="approPeriodValue" htmlEscape="false" maxlength="4" class="input-medium" style="margin-bottom: 3px" readonly="true"
					value="${plContract.approPeriodValue}" /></td>
				<td class="ft_label">合同编号：</td>
				<td class="ft_content"><input type="text" id="contractNo" htmlEscape="false" maxlength="50" class="input-medium" style="margin-bottom: 3px" readonly="true" value="${plContract.contractNo}" />
				</td>
				<td class="ft_label">合同金额：</td>
				<td class="ft_content"><input type="text" id="contractAmount" htmlEscape="false" maxlength="18" class="input-medium" style="margin-bottom: 3px" readonly="true"
					value="${plContract.contractAmount}" /></td>
			</tr>
			<tr>
				<td class="ft_label">累计逾期期数：</td>
				<td class="ft_content"><input type="text" id="taOverdueTimes" htmlEscape="false" maxlength="2" class="input-medium" style="margin-bottom: 3px" readonly="true"
					value="${debtCollection.taOverdueTimes}" /></td>
				</td>
				<td class="ft_label">当前逾期金额：</td>
				<td class="ft_content"><input type="text" id="currOverdueAmount" htmlEscape="false" maxlength="18" class="input-medium" style="margin-bottom: 3px" readonly="true"
					value="${debtCollection.currOverdueAmount}" /></td>
				</td>
			</tr>
		</table>
	</div>
</div>
