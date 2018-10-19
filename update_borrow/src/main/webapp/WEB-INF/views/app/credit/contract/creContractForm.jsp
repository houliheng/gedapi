<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>合同信息管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		//$("#name").focus();
		$("#inputForm").validate({
		submitHandler : function(form) {
			loading('正在提交，请稍等...');
			form.submit();
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			$("#messageBox").text("输入有误，请先更正。");
			if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
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
	<form:form id="inputForm" modelAttribute="contract" class="form-horizontal">
		<form:hidden path="id" />
		<sys:message content="${message}" />
		<table class="filter">
			<tr>
				<td class="ft_label">客户名称：</td>
				<td class="ft_content">
					<form:input path="custName" htmlEscape="false" maxlength="200" class="input-xlarge " readonly="true"/>
				</td>
				<td class="ft_label">证件类型：</td>
				<td class="ft_content">
					<form:select path="idType" class="input-xlarge " disabled="true">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('CUSTOMER_P_ID_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
				</td>
				<td class="ft_label">证件号：</td>
				<td class="ft_content">
					<form:input path="idNum" htmlEscape="false" maxlength="200" class="input-xlarge card" readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="ft_label">产品类型：</td>
				<td class="ft_content">
					<form:select path="approProductTypeCode" class="input-xlarge " disabled="true">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('PRODUCT_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
				</td>
				
				
				<td class="ft_label">产品名称：</td>
				<td class="ft_content">
					<form:input path="approProductTypeName" htmlEscape="false" maxlength="30" class="input-xlarge " readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="ft_label">合同编号：</td>
				<td class="ft_content">
					<form:input path="contractNo" htmlEscape="false" maxlength="50" class="input-xlarge " readonly="true"/>
				</td>
				<td class="ft_label">合同金额(元)：</td>
				<td class="ft_content">
					<form:input path="contractAmount" htmlEscape="false" class="input-xlarge " readonly="true"/>
				</td>
				<td class="ft_label">放款金额(元)：</td>
				<td class="ft_content">
					<form:input path="loanAmount" htmlEscape="false" class="input-xlarge " readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="ft_label">服务费率(%)：</td>
				<td class="ft_content">
					<form:input path="serviceFeeRate" onblur="this.value=rateMoney(this.value);" onkeyup="rateMax(this)" maxlength="5" htmlEscape="false" class="input-xlarge " readonly="true" />
				</td>
				
				
				<td class="ft_label">出借人收益率(%)：</td>
				<td class="ft_content">
					<form:input path="serviceFeeType" onblur="this.value=rateMoney(this.value);" onkeyup="rateMax(this)" maxlength="5" htmlEscape="false" class="input-xlarge " readonly="true"/>
				</td>
				<td class="ft_label">期限：</td>
				<td class="ft_content">
					<form:input path="approPeriodValue" htmlEscape="false" maxlength="4" class="input-xlarge " readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="ft_label">服务费：</td>
				<td class="ft_content">
					<form:input path="serviceFee" htmlEscape="false" class="input-xlarge " readonly="true"/>
				</td>
				<td class="ft_label">服务费收取方式：</td>
				<td class="ft_content">
					<form:select path="serviceFeeType" class="input-xlarge " disabled="true">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('SERVICE_FEE_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
				</td>
			</tr>
			<tr>
				<td class="ft_label">还款方式：</td>
				<td class="ft_content">
					<form:select path="serviceFeeType" class="input-xlarge " disabled="true">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('LOAN_REPAY_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
				</td>
				<td class="ft_label">保证金率（%）：</td>
				<td class="ft_content">
					<form:input path="marginRate" onblur="this.value=rateMoney(this.value);" onkeyup="rateMax(this)" maxlength="5" htmlEscape="false" class="input-xlarge " readonly="true"/>
				</td>
				<td class="ft_label">保证金：</td>
				<td class="ft_content">
					<form:input path="marginAmount" htmlEscape="false" class="input-xlarge " readonly="true"/>
				</td>
			</tr>
			<tr>
				<td class="ft_label">贷款模式：</td>
				<td class="ft_content">
					<form:select path="loanModel" class="input-xlarge " disabled="true">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('LOAN_MODEL')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
				</td>
				<!-- 需要从ACC_STA_CONTRACT_STATUS表中来查 -->
				<td class="ft_label">逾期期数：</td>
				<td class="ft_content"></td>
				<td class="ft_label">当前逾期金额(元)：</td>
				<td class="ft_content"></td>
			</tr>
			<tr>
				<td class="ft_label">还款状态：</td>
				<td class="ft_content"></td>
				<td class="ft_label">登记员：</td>
				<td class="ft_content">
					<form:input path="operateName" htmlEscape="false" maxlength="20" class="input-xlarge " readonly="true"/>
				</td>
				<td class="ft_label">登记门店：</td>
				<td class="ft_content">
					<form:input path="operateOrgName" htmlEscape="false" maxlength="300" class="input-xlarge " readonly="true" />
				</td>
			</tr>
		</table>
	</form:form>
	<h3 class="infoListTitle">还款明细列表</h3>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>期数</th>
				<th>状态</th>
				<th>还款日期</th>
				<th>应还金额(元)</th>
				<th>服务费</th>
				<th>账户管理费</th>
				<th>利息</th>
				<th>本金</th>
				<th>违约金</th>
				<th>罚息</th>
				<th>逾期天数</th>
			</tr>
		</thead>
		<%-- <tbody>
			<c:forEach items="${page.list}" var="contract">
				<tr>
					<td id="updateDate" class="title" title="${contract.updateDate}"><a
						href="${ctx}/credit/contract/form?id=${contract.id}"> <fmt:formatDate
								value="${contract.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" />
					</a></td>
					<shiro:hasPermission name="credit:contract:edit">
						<td><a href="${ctx}/credit/contract/form?id=${contract.id}">修改</a>
							<a href="${ctx}/credit/contract/delete?id=${contract.id}"
							onclick="return confirmx('确认要删除该合同信息吗？', this.href)">删除</a></td>
					</shiro:hasPermission>
				</tr>
			</c:forEach>
		</tbody> --%>
	</table>
</body>
</html>