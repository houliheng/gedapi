<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">
	function sqxxClick() {
		$("#sqxxId").toggle(600);
	}
</script>
<div class="searchInfo">
	<h3 onclick="sqxxClick()" class="searchTitle">申请信息</h3>
	<div id="sqxxId" class="searchCon">
		<table class="fromTable filter">
			<tr>
				<td class="ft_label">客户名称：</td>
				<td class="ft_content">
					<input type="text" id="custName" class="input-medium" readonly="true" value="${applyInfo.applyRegister.custName}" />
				</td>
				<td class="ft_label">证件类型：</td>
				<td class="ft_content">
					<input type="text" id="idType" class="input-medium" readonly="true" value="${applyInfo.applyRegister.idTypeLabel}" />
				</td>
				<td class="ft_label">证件号码：</td>
				<td class="ft_content">
					<input type="text" id="idNum" class="input-medium card" readonly="true" value="${applyInfo.applyRegister.idNum}" />
				</td>
			</tr>
			<tr>
				<td class="ft_label">企业名称：</td>
				<td class="ft_content">
					<input type="text" id="companyName" class="input-medium" readonly="true" value="${applyInfo.applyRegister.companyName}" />
				</td>
				<td class="ft_label">企业证件类型：</td>
				<td class="ft_content">
					<input type="text" id="comIdType" class="input-medium" readonly="true" value="${applyInfo.applyRegister.comIdTypeLabel}" />
				</td>
				<td class="ft_label">企业证件号：</td>
				<td class="ft_content">
					<input type="text" id="comIdNum" class="input-medium" readonly="true" value="${applyInfo.applyRegister.comIdNum}" />
				</td>
			</tr>
			<tr>
				<td class="ft_label">产品类型：</td>
				<td class="ft_content">
					<input type="text" id="applyProductTypeName" class="input-medium" readonly="true" value="${applyInfo.applyRegister.applyProductTypeLabel}" />
				</td>
				<td class="ft_label">产品名称：</td>
				<td class="ft_content">
					<input type="text" id="applyProductName" class="input-medium" readonly="true" value="${applyInfo.applyRegister.applyProductName}" />
				</td>
				<td class="ft_label">产品期限(月)：</td>
				<td class="ft_content">
					<input type="text" id="applyPeriodValue" class="input-medium" readonly="true" value="${fns:getDictLabel(applyInfo.applyPeriodValue, 'PRODUCT_PERIOD_VALUE', '')}" />
				</td>
			</tr>
			<tr>
				<td class="ft_label">申请利率(%)：</td>
				<td class="ft_content">
					<input type="text" id="applyYearRate" class="input-medium" onblur="this.value=rateMoney(this.value);" onkeyup="rateMax(this)" maxlength="5" readonly="true" value="${applyInfo.applyYearRate}" />
				</td>
				<td class="ft_label">申请服务费率(%)：</td>
				<td class="ft_content">
					<input type="text" id="applyServiceRate" class="input-medium" onblur="this.value=rateMoney(this.value);" onkeyup="rateMax(this)" maxlength="5" readonly="true" value="${applyInfo.applyServiceRate}" />
				</td>
				<td class="ft_label">申请金额(元)：</td>
				<td class="ft_content">
					<input type="text" class="input-medium" name="applyAmount" readonly="true" value="<fmt:formatNumber value="${not empty applyInfo.applyAmount ? applyInfo.applyAmount : applyInfo.applyRegister.applyAmount}" pattern="###,##0.00"/>" />
				</td>
			</tr>
			<tr class="ZGCategory">
				<td width="13%" class="ft_label ZGCategory">产品分类：</td>
				<td class="ft_content ZGCategory">
					<input type="text" id="productCategoryKey" name="productCategoryKey" class="input-medium" readonly="true" value="${productCategoryKey}" />
					<input type="hidden" id="productCategoryValue" name="productCategoryValue" value="${productCategoryValue}">
				</td>
			</tr>
		</table>
	</div>
</div>
