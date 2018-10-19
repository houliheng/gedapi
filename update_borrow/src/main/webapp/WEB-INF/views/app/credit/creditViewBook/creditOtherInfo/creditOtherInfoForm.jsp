<%@ page contentType="text/html;charset=UTF-8"%>
<div class="searchInfo">
	<h3 class="searchTitle">其他信息</h3>
	<div class="searchCon">
		<form:form id="inputForm" modelAttribute="creditOtherInfo" action="${ctx}/credit/creditotherinfo/creditOtherInfo/save" method="post" class="form-horizontal">
			<form:hidden path="id" />
			<sys:message content="${message}" />
			<table class="fromTable filter">
				<tr>
					<td class="ft_label">借款人夫妻资产占借款金额比例：</td>
					<td class="ft_content">
						<form:select path="coupleAssetsOfLoan" id="coupleAssetsOfLoan" class="input-medium required">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('COUPLE_ASSETS_OF_LOAN')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
					<td class="ft_label">担保人资产占借款金额比例：</td>
					<td class="ft_content">
						<form:select path="guaranteeAssetsOfLoan" id="guaranteeAssetsOfLoan" class="input-medium required">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('GUARANTEE_ASSETS_OF_LOAN')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
					<td class="ft_label">最近一年股权变更：</td>
					<td class="ft_content">
						<form:select path="lastYearStockChange" id="lastYearStockChange" class="input-medium required">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('LAST_YEAR_STOCK_CHANGE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">缴税情况：</td>
					<td class="ft_content">
						<form:select path="payTaxStatus" id="payTaxStatus" class="input-medium required">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('PAY_TAX_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
					<td class="ft_label">物业费缴纳情况：</td>
					<td class="ft_content">
						<form:select path="managementFeeStatus" id="managementFeeStatus" class="input-medium required">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('MANAGEMENT_FEE_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
					<td class="ft_label">电费缴纳情况：</td>
					<td class="ft_content">
						<form:select path="powerFeeStatus" id="powerFeeStatus" class="input-medium required">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('POWER_FEE_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">借款企业中借款人占股(%)：</td>
					<td class="ft_content">
						<form:input path="mainManOfStock" htmlEscape="false" onblur="this.value=gdpMoney(this.value);" onkeyup="gdpMax(this)" maxlength="6" class="input-medium required" />
						<span class="help-inline">
						</span>
					</td>
				</tr>
			</table>
		</form:form>
	</div>
</div>