<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<form:form id="contractInputForm" modelAttribute="contract" action="${ctx}/credit/contract/save" method="post" class="form-horizontal">
	<form:hidden path="id" id="contractId" />
	<sys:message content="${message}" />
	<div class="searchInfo">
		<h3 class="searchTitle">合同信息</h3>
		<div class="searchCon">
			<table class="fromTable filter">
				<tr>
					<td class="ft_label">客户名称：</td>
					<td class="ft_content">
						<form:input path="custName" readonly="true" htmlEscape="false" maxlength="300" class="input-medium" />
					</td>
					<td class="ft_label">证件类型：</td>
					<td class="ft_content">
						<form:input path="idType" value="${fns:getDictLabel(contract.idType, 'CUSTOMER_P_ID_TYPE', '')}" readonly="true" htmlEscape="false" maxlength="300" class="input-medium" />
					</td>
					<td class="ft_label">证件号码：</td>
					<td class="ft_content">
						<form:input path="idNum" htmlEscape="false" readonly="true" maxlength="300" class="input-medium " />
					</td>
				</tr>
				<tr>
					<td class="ft_label">产品类型：</td>
					<td class="ft_content">
						<form:input path="approProductTypeName" value="${fns:getDictLabel(contract.approProductTypeName,'PRODUCT_TYPE', '')}" readonly="true" htmlEscape="false" maxlength="300" class="input-medium" />
					</td>
					<td class="ft_label">合同编号：</td>
					<td class="ft_content">
						<form:input path="contractNo" readonly="true" htmlEscape="false" maxlength="300" class="input-medium " />
					</td>
					<td class="ft_label">合同金额：</td>
					<td class="ft_content">
						<form:input path="contractAmount" readonly="true" htmlEscape="false" maxlength="300" class="input-medium " />
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="searchInfo">
		<h3 class="searchTitle">借款人银行卡信息</h3>
		<div class="searchCon">
			<table class="fromTable filter">
				<tr>
					<td class="ft_label">还款银行卡号：</td>
					<td class="ft_content">
						<form:input path="repBankcardNo" readonly="true" htmlEscape="false" class="input-medium" />
					</td>
					<td class="ft_label">还款账户名称：</td>
					<td class="ft_content">
						<form:input path="repBankcardName" readonly="true" htmlEscape="false" class="input-medium" />
					</td>
					<td class="ft_label">还款移动电话：</td>
					<td class="ft_content">
						<form:input path="repBankMobile" readonly="true" htmlEscape="false" class="input-medium" />
					</td>
				</tr>
				<tr>
					<td class="ft_label">还款收款银行：</td>
					<td class="ft_content">
						<form:input path="repBank" value="${fns:getDictLabel(contract.repBank, 'BANKS', '')}" readonly="true" htmlEscape="false" class="input-medium" />
					</td>
					<td class="ft_label">还款开户行名称：</td>
					<td class="ft_content">
						<form:input path="repBankName" htmlEscape="false" readonly="true" class="input-medium" />
					</td>
					<td class="ft_label">还款人身份证号：</td>
					<td class="ft_content">
						<form:input path="repBankIdNum" htmlEscape="false" readonly="true" class="input-medium" />
					</td>
				</tr>
				<tr>
					<td class="ft_label">还款银行地址：</td>
					<td class="ft_content" colspan="5">
						<form:select path="repBankProvince" class="input-medium" disabled="true">
							<form:options items="${provinceMap1}" htmlEscape="false" itemLabel="addressValue" itemValue="addressId" />
						</form:select>
						<span>
							<font>省</font>
						</span>
						<form:select path="repBankCity" class="input-medium" disabled="true">
							<form:options items="${cityMap1}" htmlEscape="false" itemLabel="addressValue" itemValue="addressId" />
						</form:select>
						<span>
							<font>市</font>
						</span>
						<form:select path="repBankDistinct" class="input-medium" disabled="true">
							<form:options items="${distinctMap1}" htmlEscape="false" itemLabel="addressValue" itemValue="addressId" />
						</form:select>
						<span>
							<font>区</font>
						</span>
						<form:input path="repBankDetail" htmlEscape="false" readonly="true" class="input-medium" />
					</td>
				</tr>
			</table>
		</div>
	</div>
</form:form>