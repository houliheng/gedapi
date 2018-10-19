<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">

	function extendTask() {
		$("#extendTaskId").toggle(600);
	}
</script>
<div class="searchInfo">
	<h3 onclick="extendTask()" class="searchTitle">展期任务详情</h3>
	<div id="extendTaskId" class="searchCon">
		<table class="fromTable filter">
			<tr>
				<td class="ft_label">所属分公司：</td>
				<td class="ft_content">
					<input type="text" id="operateOrgName" name="operateOrgName" value="${plContract.operateOrgName }" htmlEscape="false" readonly="true" class="input-medium" />
				</td>
				<td class="ft_label">客户名称：</td>
				<td class="ft_content">
					<input type="text" id="custName" name="custName" htmlEscape="false" value="${plContract.custName }" readonly="true" class="input-medium" />
				</td>
				<td class="ft_label">合同编号：</td>
				<td class="ft_content">
					<input type="text" id="contractNo" name="contractNo" readonly="true" class="input-medium" value="${plContract.contractNo }" />
				</td>
			</tr>
			<tr>
				<td class="ft_label">原合同金额：</td>
				<td class="ft_content">
					<input id="contractAmount" name="contractAmount" type="text" readonly="true" class="input-medium" value="${plContract.contractAmount }" />
				</td>
				<td class="ft_label">原合同起始日期：</td>
				<td class="ft_content">
					<input id="conStartDate" name="conStartDate" type="text" readonly="true" class="input-medium" value="<fmt:formatDate value='${plContract.conStartDate }' pattern='yyyy-MM-dd'/>" />
				</td>
				<td class="ft_label">原合同到期日期：</td>
				<td class="ft_content">
					<input type="text" id="conEndDate" readonly="true" name="conEndDate" class="input-medium" value="<fmt:formatDate value='${plContract.conEndDate }' pattern='yyyy-MM-dd'/>" />
				</td>
			</tr>
			<tr>
				<td class="ft_label">原合同期数：</td>
				<td class="ft_content">
					<input type="text" id="approPeriodValue" readonly="true" name="approPeriodValue" class="input-medium" value="${plContract.approPeriodValue }" />
				</td>
			</tr>
		</table>
	</div>
</div>