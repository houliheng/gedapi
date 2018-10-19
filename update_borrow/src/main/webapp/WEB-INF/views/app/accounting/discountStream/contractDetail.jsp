<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>合同贴息详情</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
$(document).ready(function() {
	if('${queryError}' != ''){
		alertx('${queryError}');
	}
});


	
</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="contractDetailVo" action="${ctx}/credit/guranteeProjectManage/contractDeatil" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<input type="hidden" id="contractNo" name="contractNo" value="${contractDetailVo.contractNo}" />
		<sys:message content="${message}" />
		<!-- <h3 class="infoListTitle">合同信息</h3> -->
		<table class="filter">
			<tr>
				<td class="ft_label">合同编号：</td>
				<td class="ft_content">${contractDetailVo.contractNo }</td>
				<td class="ft_label">企业名称：</td>
				<td class="ft_content">${contractDetailVo.custName }</td>
				<td class="ft_label">应贴息总金额(元)：</td>
				<td class="ft_content">${contractDetailVo.discountFee }</td>
			<tr>
				<td class="ft_label">已贴息总金额(元)：</td>
				<td class="ft_content">${contractDetailVo.factDiscountFee }</td>
				<td class="ft_label">客户待还总金额(元)：</td>
				<td class="ft_content">${contractDetailVo.stayMoney }</td>
				<td class="ft_label">客户实还总金额(元)：</td>
				<td class="ft_content">${contractDetailVo.factMoney }</td>
			</tr>
			<tr>
				<td class="ft_label">当期应还本息和(元)：</td>
				<td class="ft_content">
				${contractDetailVo.repayMoney}
				</td>
				<td class="ft_label">借款状态：</td>
				<td class="ft_content">${fns:getDictLabel(contractDetailVo.borrowLoanStatus, 'CONTRACT_STATE', '')}</td>
			</tr>
		</table>
	</form:form>
	<h3 class="infoListTitle">还款明细列表</h3>
	<div id="tableDataId" style="width:100%; overflow-x:auto;overflow-y:hidden">
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>贴息状态</th>
					<th>期数</th>
					<th>应还日期</th>
					<th>当期应还总金额(元)</th>
					<th>当期应还本息(元)</th>
					<th>客户待还金额(元)</th>
					<th>客户实还金额(元)</th>
					<th>应贴息金额(元)</th>
					<th>已贴息金额(元)</th>
					<th>贴息日期</th>
					<th>期供状态</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${periodDiscountDetails}" var="periodDiscountDetail">
					<tr>
						<td class="title" title="${periodDiscountDetail.discountStatus}">${periodDiscountDetail.discountStatus}</td>
						<td class="title" title="${periodDiscountDetail.periodNum}">${periodDiscountDetail.periodNum}</td>
						<td class="title" title="${periodDiscountDetail.deductDate}">${periodDiscountDetail.deductDate}</td>
						<td class="title" title="${periodDiscountDetail.totalMoney}">${periodDiscountDetail.totalMoney}</td>
						<td class="title" title="${periodDiscountDetail.capitalInte}">${periodDiscountDetail.capitalInte}</td>
						<td class="title" title="${periodDiscountDetail.stayMoney}">${periodDiscountDetail.stayMoney}</td>
						<td class="title" title="${periodDiscountDetail.factMoney}">${periodDiscountDetail.factMoney}</td>
						<td class="title" title="${periodDiscountDetail.discountFee}">${periodDiscountDetail.discountFee}</td>
						<td class="title" title="${periodDiscountDetail.factDiscountFee}">${periodDiscountDetail.factDiscountFee}</td>
						<td class="title" title="<fmt:formatDate value="${periodDiscountDetail.discountDate}" pattern="yyyy-MM-dd"/>"><fmt:formatDate value="${periodDiscountDetail.discountDate}" pattern="yyyy-MM-dd"/></td>
						<td class="title" title="${fns:getDictLabel(periodDiscountDetail.periodStatus, 'PERIOD_STATE', '')}">${fns:getDictLabel(periodDiscountDetail.periodStatus, 'PERIOD_STATE', '')}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>