<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>合同还款明细查询管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	


	
</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="guranteeProjectList" action="${ctx}/credit/guranteeProjectManage/contractDeatil" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<input type="hidden" id="contractNoTmp" name="contractNoTmp"  />
		<sys:message content="${message}" />
		<h3 class="infoListTitle">合同信息</h3>
		<table class="filter">
			<tr>
				<td class="ft_label">借款人名称：</td>
				<td class="ft_content">${guranteeProjectList.borrowName }</td>
			<tr>
				<td class="ft_label">借款金额(元)：</td>
				<td class="ft_content">${guranteeProjectList.borrowMoney }</td>
				<td class="ft_label">期限(月)：</td>
				<td class="ft_content">${guranteeProjectList.periodValue }</td>
				<td class="ft_label">利率(年化)：</td>
				<td class="ft_content">${guranteeProjectList.yearRate }</td>
			</tr>
			<tr>
				<td class="ft_label">还款方式：</td>
				<td class="ft_content">
				${fns:getDictLabel(guranteeProjectList.approveLoanRepayType, 'LOAN_REPAY_TYPE', '')}
				</td>
				<td class="ft_label">当前期数：</td>
				<td class="ft_content">${guranteeProjectList.currentPeriod }</td>
				<td class="ft_label">借款状态：</td>
				<td class="ft_content">
					<c:if test="${guranteeProjectList.flag eq '1'}">
						${fns:getDictLabel(guranteeProjectList.accountStatus, 'CONTRACT_STATE', '')}
					</c:if>
					<c:if test="${guranteeProjectList.flag eq '2'}">
						${fns:getDictLabel(guranteeProjectList.loanStatus, 'LOAN_STATUS', '')}
					</c:if>
					<c:if test="${guranteeProjectList.flag eq '3'}">
						${fns:getDictLabel(guranteeProjectList.applyStatus, 'APPLY_STATUS', '')}
					</c:if>	
				</td>
			</tr>
			<tr>
				<td class="ft_label">担保金(元)：</td>
				<td class="ft_content">${guranteeProjectList.guranteeGold}
				${fns:getDictLabel(guranteeProjectList.guranteeGoldFlag, 'FLAG', '')}
				</td>
				<td class="ft_label">担保服务费(元)：</td>
				<td class="ft_content">${guranteeProjectList.guranteeServicceFee }
				${fns:getDictLabel(guranteeProjectList.guranteeServicceFeeFlag, 'FLAG', '')}
				</td>
			</tr>
		</table>
	</form:form>
	<h3 class="infoListTitle">还款明细列表</h3>
	<div id="tableDataId" style="width:100%; overflow-x:auto;overflow-y:hidden">
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>期数</th>
					<th>待还金额</th>
					<th>应还本息</th>
					<th>代偿金额</th>
					<th>实还金额</th>
					<th>应还罚息</th>
					<th>应还违约金</th>
					<th>还款状态</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${repayPalnDetails}" var="repayDetail">
					<tr>
						<td class="title" title="${repayDetail.periodNum}">${repayDetail.periodNum}</td>
						<td class="title" title="${repayDetail.repayMoney}">${repayDetail.repayMoney}</td>
						<td class="title" title="${repayDetail.principal}">${repayDetail.principal}</td>
						<td class="title" title="${repayDetail.compenMoney}">${repayDetail.compenMoney}</td>
						<td class="title" title="${repayDetail.factRepayMoney}">${repayDetail.factRepayMoney}</td>
						<td class="title" title="${repayDetail.defaultInterest}">${repayDetail.defaultInterest}</td>
						<c:if test="${repayDetail.defaultInterest != null && repayDetail.defaultInterest != ''}">
							<td class="title" title="${repayDetail.penalty}">${repayDetail.penalty}</td>
						</c:if>
						<c:if test="${repayDetail.defaultInterest == null || repayDetail.defaultInterest == ''}">
							<td></td>
						</c:if>
						<td class="title" title="${fns:getDictLabel(repayDetail.repayStatus, 'PERIOD_STATE', '')}">${fns:getDictLabel(repayDetail.repayStatus, 'PERIOD_STATE', '')}</td>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>