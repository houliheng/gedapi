<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>放款明细管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {

	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
	//重置按钮
	function del() {
		$("#companyId").val('');
		$("#companyName").val('');
		$("input.input-medium").val("");
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="loanDetail" action="${ctx}/accounting/loanDetail/list?queryFlag=button" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<div class="filter">
						<table class="fromTable">
							<tr>
								<td>
									<label>客户名称：</label>
									<form:input path="custName" htmlEscape="false" maxlength="20" class="input-medium" />
								</td>
								<td>
									<label>合同编号：</label>
									<form:input path="contractNo" htmlEscape="false" maxlength="50" class="input-medium" />
								</td>
								<td class="ft_label">所属机构：</td>
								<td class="ft_content">
									<sys:usertreeselect id="company" name="company.id" value="${loanDetail.company.id}" labelName="company.name" labelValue="${loanDetail.company.name}" title="公司" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true" />
								</td>
							</tr>
							<tr>
								<td>
									<label>产品类型：</label>
									<form:input path="approProductTypeName" htmlEscape="false" maxlength="50" class="input-medium" />
								</td>
								<td>
									<label>放款日期：</label>
									<input name="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:150px" value="<fmt:formatDate value="${loanDetail.startTime}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
									至
									<input name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:150px" value="<fmt:formatDate value="${loanDetail.endTime}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />
							&nbsp;
							<input id="btnReset" class="btn btn-primary" type="button" onclick="del()" value="重置" />
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div id="tableDataId">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th width="6%">客户名称</th>
							<th width="9%">合同编号</th>
							<th width="7%">大区</th>
							<th width="6%">区域</th>
							<th width="6%">分公司</th>
							<th width="7%">放款日期</th>
							<th width="8%">合同金额</th>
							<th width="8%">放款金额</th>
							<th width="7%">服务费</th>
							<th width="8%">担保费</th>
							<th width="7%">特殊服务费</th>
							<th width="7%">实收服务费</th>
							<th width="7%">实收担保费</th>
							<th width="7%">实收担保金</th>
							<th width="8%">状态</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="staContractStatus">
							<tr>
								<td class="title" title="${staContractStatus.custName}">${staContractStatus.custName}</td>
								<td class="title" title="${staContractStatus.contractNo}">${staContractStatus.contractNo}</td>
								<td class="title" title="${staContractStatus.orgLevel2.name}">${staContractStatus.orgLevel2.name}</td>
								<td class="title" title="${staContractStatus.orgLevel3.name}">${staContractStatus.orgLevel3.name}</td>
								<td class="title" title="${staContractStatus.orgLevel4.name}">${staContractStatus.orgLevel4.name}</td>
								<td class="title" title="<fmt:formatDate value='${staContractStatus.loanDate}' pattern='yyyy-MM-dd' />">
									<fmt:formatDate value="${staContractStatus.loanDate}" pattern="yyyy-MM-dd" />
								</td>
								<td class="title" title="${staContractStatus.contractAmount}">${staContractStatus.contractAmount}</td>
								<td class="title" title="${staContractStatus.loanAmount}">${staContractStatus.loanAmount}</td>
								<td class="title" title="${staContractStatus.serviceFee}">${staContractStatus.serviceFee}</td>
								<td class="title" title="${staContractStatus.marginAmount}">${staContractStatus.marginAmount}</td>
								<td class="title" title="${staContractStatus.specialServiceFee}">${staContractStatus.specialServiceFee}</td>
								<td class="title" title="${staContractStatus.factServiceFee}">${staContractStatus.factServiceFee}</td>
								<td class="title" title="${staContractStatus.factGuaranteeFee}">${staContractStatus.factGuaranteeFee}</td>
								<td class="title" title="${staContractStatus.factGuaranteeGold}">${staContractStatus.factGuaranteeGold}</td>
								<td class="title" title="${fns:getDictLabel(staContractStatus.loanStatus, 'LOAN_STATUS', '')}">${fns:getDictLabel(staContractStatus.loanStatus, 'LOAN_STATUS', '')}</td>	
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="pagination">${page}</div>
		</div>
	</div>
</body>
</html>