<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">
	$(document).ready(function() {

	});

	function contractBaseInfoToggle() {
		$("#contractBaseInfoId").toggle(600);
	}
</script>
<div class="searchInfo">
	<h3 onclick="contractBaseInfoToggle()" class="searchTitle">25日复核任务信息详情</h3>
	<div id="contractBaseInfoId" class="searchCon">
		<form:form id="contractBaseInfoForm" modelAttribute="contractBaseInfo" action="${ctx}/contractBaseInfo/contractBaseInfo/save" method="post" class="form-horizontal">
			<sys:message content="${message}" />
			<table class="fromTable filter">
				<tr>
					<td class="ft_label">合同编号：</td>
					<td class="ft_content">
						<input type="text" id="contractNo" name="contractNo" readonly="true" class="input-medium" value="${contractBaseInfo.contractNo}" />
					</td>
					<td class="ft_label">借款人姓名：</td>
					<td class="ft_content">
						<input type="text" id="custName" name="custName" readonly="true" class="input-medium" value="${contractBaseInfo.custName}" />
					</td>
					<td class="ft_label">放款日期：</td>
					<td class="ft_content">
						<input type="text" id="loanDate" name="loanDate" readonly="true" class="input-medium" value="${contractBaseInfo.loanDate}" />
					</td>
				</tr>
				<tr>
					<td class="ft_label">批复额度：</td>
					<td class="ft_content">
						<input type="text" id="loanAmount" name="loanAmount" readonly="true" class="input-medium" value="${contractBaseInfo.loanAmount}" />
					</td>
					<td class="ft_label">合同期限：</td>
					<td class="ft_content">
						<input type="text" id="conEndDate" name="conEndDate" readonly="true" class="input-medium" value="${contractBaseInfo.conEndDate}" />
					</td>
					<td class="ft_label">每月还款日：</td>
					<td class="ft_content">
						<input type="text" id="repayDate" name="repayDate" readonly="true" class="input-medium" value="${contractBaseInfo.repayDate}" />
					</td>
				</tr>
				<tr>
					<td class="ft_label">期数：</td>
					<td class="ft_content">
						<input type="text" id="approPeriodValue" name="approPeriodValue" readonly="true" class="input-medium" value="${contractBaseInfo.approPeriodValue}" />
					</td>
					<td class="ft_label">业务分类：</td>
					<td class="ft_content">
						<input type="text" id="approProductTypeName" name="approProductTypeName" readonly="true" class="input-medium" value="${contractBaseInfo.approProductTypeName}" />
					</td>
				</tr>
				<tr>
					<td class="ft_label">所属大区：</td>
					<td class="ft_content">
						<input type="text" id="idNum" name="idNum" readonly="true" class="input-medium" value="${contractBaseInfo.idNum}" />
					</td>
					<td class="ft_label">所属区域：</td>
					<td class="ft_content">
						<input type="text" id="idNum" name="idNum" readonly="true" class="input-medium" value="${contractBaseInfo.idNum}" />
					</td>
					<td class="ft_label">所属分公司：</td>
					<td class="ft_content">
						<input type="text" id="idNum" name="idNum" readonly="true" class="input-medium" value="${contractBaseInfo.idNum}" />
					</td>
				</tr>
				<tr>
					<td class="ft_label">分公司经理：</td>
					<td class="ft_content">
						<input type="text" id="idNum" name="idNum" readonly="true" class="input-medium" value="${contractBaseInfo.idNum}" />
					</td>
					<td class="ft_label">分公司副经理：</td>
					<td class="ft_content">
						<input type="text" id="idNum" name="idNum" readonly="true" class="input-medium" value="${contractBaseInfo.idNum}" />
					</td>
					<td class="ft_label">客户经理：</td>
					<td class="ft_content">
						<input type="text" id="idNum" name="idNum" readonly="true" class="input-medium" value="${contractBaseInfo.idNum}" />
					</td>
				</tr>
				<tr>
					<td class="ft_label">签约见证人：</td>
					<td class="ft_content">
						<input type="text" id="idNum" name="idNum" readonly="true" class="input-medium" value="${contractBaseInfo.idNum}" />
					</td>
					<td class="ft_label">面审风控：</td>
					<td class="ft_content">
						<input type="text" id="idNum" name="idNum" readonly="true" class="input-medium" value="${contractBaseInfo.idNum}" />
					</td>
					<td class="ft_label">电核风控：</td>
					<td class="ft_content">
						<input type="text" id="idNum" name="idNum" readonly="true" class="input-medium" value="${contractBaseInfo.idNum}" />
					</td>
				</tr>
				<tr>
					<td class="ft_label">外访风控：</td>
					<td class="ft_content">
						<input type="text" id="idNum" name="idNum" readonly="true" class="input-medium" value="${contractBaseInfo.idNum}" />
					</td>
					<td class="ft_label">大区风控主管：</td>
					<td class="ft_content">
						<input type="text" id="idNum" name="idNum" readonly="true" class="input-medium" value="${contractBaseInfo.idNum}" />
					</td>
					<td class="ft_label">大区主审风控：</td>
					<td class="ft_content">
						<input type="text" id="idNum" name="idNum" readonly="true" class="input-medium" value="${contractBaseInfo.idNum}" />
					</td>
				</tr>
				<tr>
					<td class="ft_label">区域主审风控：</td>
					<td class="ft_content">
						<input type="text" id="idNum" name="idNum" readonly="true" class="input-medium" value="${contractBaseInfo.idNum}" />
					</td>
					<td class="ft_label">分公司主审风控：</td>
					<td class="ft_content">
						<input type="text" id="idNum" name="idNum" readonly="true" class="input-medium" value="${contractBaseInfo.idNum}" />
					</td>
					<td class="ft_label">抵、质押权人：</td>
					<td class="ft_content">
						<input type="text" id="idNum" name="idNum" readonly="true" class="input-medium" value="${contractBaseInfo.idNum}" />
					</td>
				</tr>
			</table>
		</form:form>
	</div>
</div>