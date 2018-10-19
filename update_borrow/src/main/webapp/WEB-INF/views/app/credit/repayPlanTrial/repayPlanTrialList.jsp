<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<title></title>
  	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
	</script>
  </head> 
  <body>
  	<div class="searchInfo">
		<h3 class="searchTitle">合同信息</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="repayPlanTrial" action="${ctx}/credit/repayPlanTrial/" method="post" class="breadcrumb form-search">
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<div class="filter">
					<table class="searchTable">
						<tr>
							<td class="ft_label">标的金额：</td>
							<td class="ft_content">
								<form:input path="amountValue" class="input-medium" htmlEscape="false"/>
							</td>
						</tr>
						<tr>	
							<td class="ft_label">产品类型：</td>		
							<td class="ft_content">
								<%-- <select id="productTypeCode" class="input-medium" >
								<c:forEach items="${fns:getDictList('PRODUCT_TYPE')}" var='product'>
								<option value='${period.value}' label='${period.label}'>${period.label}</option>
								</c:forEach>
								</select>  --%>
								<form:select path="productTypeCode" class="input-medium">
									<form:option value="" label=""/>
									<form:options items="${fns:getDictList('PRODUCT_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
								</form:select>
							</td>
							<td class="ft_label">期数：</td>
							<td class="ft_content">
								<form:select path="periodValue" class="input-medium">
									<form:option value="" label=""/>
									<form:options items="${fns:getDictList('PRODUCT_PERIOD_VALUE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
								</form:select>
							</td>
						</tr>
						<tr>
							<td class="ft_label">还款方式：</td>
							<td class="ft_content">
							    <form:select path="loanRepayType" class="input-medium">
							    	<form:option value="" label=""/>
							    	<form:options items="${fns:getDictList('LOAN_REPAY_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
							    </form:select>
							</td>
							<td class="ft_label">合同月利率：</td>
							<td class="ft_content">
								<form:input path="contractRate" class="input-medium" htmlEscape="false"/>
							</td>
						</tr>
					</table>
					<div class="searchButton">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="计算"/>
					</div>
				</div>
				</form:form>
			</div>
		<h3 class="tableTitle">数据列表</h3>
		<div id="tableDataId" style="max-height: 400px; overflow: auto;">
  		<table id="contentTable" class="table table-striped table-bordered table-condensed">
  			<thead>
  				<tr>
  					<th>期数</th>
  					<th>预计还款本金</th>
  					<th>预计还款利息</th>
  					<th>预计本息</th>
  					<th>预计差额本金</th>
  					<th>预计差额利息</th>
  					<th>预计本息差额</th>
  					<th>预计合同还款本金</th>
  					<th>预计合同还款利息</th>
  					<th>预计合同还款本息</th>
  				</tr>
  			</thead>
  			<tbody>
  			<c:forEach items="${exportRepayPlanList}" var="exportRepayPlan" varStatus="exportRepayPlanList">
  				<tr>
  					<td class="title" title="${exportRepayPlan.period}">${exportRepayPlan.period}</td>
  					<td class="title" title="${exportRepayPlan.monthBaseAmount}">${exportRepayPlan.monthBaseAmount}</td>
  					<td class="title" title="${exportRepayPlan.monthIrrAmount}">${exportRepayPlan.monthIrrAmount}</td>
  					<td class="title" title="${exportRepayPlan.monthSumAmount}">${exportRepayPlan.monthSumAmount}</td>
  					<td class="title" title="${exportRepayPlan.ceMonthBaseAmount}">${exportRepayPlan.ceMonthBaseAmount}</td>
  					<td class="title" title="${exportRepayPlan.ceMonthIrrAmount}">${exportRepayPlan.ceMonthIrrAmount}</td>
  					<td class="title" title="${exportRepayPlan.ceMonthSumAmount}">${exportRepayPlan.ceMonthSumAmount}</td>
  					<td class="title" title="${exportRepayPlan.htMonthBaseAmount}">${exportRepayPlan.htMonthBaseAmount}</td>
  					<td class="title" title="${exportRepayPlan.htMonthIrrAmount}">${exportRepayPlan.htMonthIrrAmount}</td>
  					<td class="title" title="${exportRepayPlan.htMonthSumAmount}">${exportRepayPlan.htMonthSumAmount}</td>
  				</tr>
  			</c:forEach>
  			<%-- <c:forEach items="${repayPlanList}" var="repayPlan" varStatus="repayPlanList">
  				<tr>
  					<td class="title">${repayPlanList.count }</td>
  					<td class="title" title="${repayPlan.bidCapitalAmount}">${repayPlan.bidCapitalAmount}</td>
  					<td class="title" title="${repayPlan.bidInterestAmount}">${repayPlan.bidInterestAmount}</td>
  					<td class="title" title="${repayPlan.bidRepayAmount}">${repayPlan.bidRepayAmount}</td>
  					<td class="title" title="${repayPlan.difCapitalAmount}">${repayPlan.difCapitalAmount}</td>
  					<td class="title" title="${repayPlan.difInterestAmount}">${repayPlan.difInterestAmount}</td>
  					<td class="title" title="${repayPlan.managementFee}">${repayPlan.managementFee}</td>
  					<td class="title" title="${repayPlan.capitalAmount}">${repayPlan.capitalAmount}</td>
  					<td class="title" title="${repayPlan.interestAmount}">${repayPlan.interestAmount}</td>
  					<td class="title" title="${repayPlan.repayAmount}">${repayPlan.repayAmount}</td>
  				</tr>
  			</c:forEach> --%>
  			</tbody>
  		</table>
  		</div>
	</div>
  </body>
</html>
