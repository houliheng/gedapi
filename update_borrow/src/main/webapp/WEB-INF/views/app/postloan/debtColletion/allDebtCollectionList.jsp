<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>待催收管理</title>
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
	function resetForm() {
		$("#custName").val("");
		$("#contractNo").val("");
		$("#riskLelve").select2("val", "");
		$("#currCollectionType").select2("val", "");
	}

	//查询
	function details(contractNo) {
		var url = "${ctx}/postloan/debtCollection/toDetailsPageIndex?contractNo=" + contractNo;
		var width = $(window).width() - 100
		var height = $(window).height() - 100;
		openJBox("detailsBox", url, "客户详情", width, height);
	}
	//催收详情
	function debtDetails(contractNo, debtId) {
		var url = "${ctx}/postloan/debtCollection/todebtDetailsPage?contractNo=" + contractNo + "&debtId=" + debtId;
		var width = $(window).width() - 50
		var height = $(window).height() - 50;
		openJBox("debtDetailsBox", url, "催收详情", width, height);
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="debtCollection" action="${ctx}/postloan/debtCollection/allDebtCollectionList" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<div class="filter">
						<table class="fromTable">
							<tr>
								<td class="ft_label">客户名称：</td>
								<td class="ft_content">
									<form:input path="custName" htmlEscape="false" class="input-medium" />
								</td>
								<td class="ft_label">合同编号：</td>
								<td class="ft_content">
									<form:input path="contractNo" htmlEscape="false" class="input-medium" />
								</td>
							</tr>
							<tr>
								<td class="ft_label">风险级别：</td>
								<td class="ft_content">
									<form:select path="riskLelve" class="input-medium">
										<form:option value="" label="请选择" />
										<form:options items="${fns:getDictList('RISK_LEVEL')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
								</td>
								<td class="ft_label">当前催收方式：</td>
								<td class="ft_content">
									<form:select path="currCollectionType" class="input-medium">
										<form:option value="" label="请选择" />
										<form:options items="${fns:getDictList('CURR_COLLECTION_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />
							&nbsp;
							<input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="resetForm()" />
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
							<th width="3%">序号</th>
							<th width="10%">客户名称</th>
							<th width="5%">风险级别</th>
							<th width="5%">催收次数</th>
							<th width="15%">合同编号</th>
							<th width="10%">合同金额(元)</th>
							<th width="10%">累计逾期期数</th>
							<th width="10%">当前逾期金额(元)</th>
							<th width="10%">当前催收方式</th>
							<th width="10%">当前状态</th>
							<shiro:hasPermission name="postloan:debtCollection:edit">
								<th width="12%">操作</th>
							</shiro:hasPermission>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" varStatus="debtCollectionList" var="debtCollection">
							<tr>
								<td class="title" title="序号">${debtCollectionList.count}</td>
								<td class="title" title="${debtCollection.custName}">${debtCollection.custName}</td>
								<td class="title" title="${fns:getDictLabel(debtCollection.riskLelve, 'RISK_LEVEL', '')}">${fns:getDictLabel(debtCollection.riskLelve, 'RISK_LEVEL', '')}</td>
								<td class="title" title="${debtCollection.collectionTimes}">${debtCollection.collectionTimes}</td>
								<td class="title" title="${debtCollection.contractNo}">${debtCollection.contractNo}</td>
								<td class="title" title="${debtCollection.contractAmount}">${debtCollection.contractAmount}</td>
								<td class="title" title="${debtCollection.taOverdueTimes}">${debtCollection.taOverdueTimes}</td>
								<td class="title" title="${debtCollection.currOverdueAmount}">${debtCollection.currOverdueAmount}</td>
								<td class="title" title="${fns:getDictLabel(debtCollection.currCollectionType, 'CURR_COLLECTION_TYPE', '')}">${fns:getDictLabel(debtCollection.currCollectionType, 'CURR_COLLECTION_TYPE', '')}</td>
								<td class="title" title="${fns:getDictLabel(debtCollection.currCollectionStatus, 'CURR_COLLECTION_STATUS', '')}">${fns:getDictLabel(debtCollection.currCollectionStatus, 'CURR_COLLECTION_STATUS', '')}</td>
								<shiro:hasPermission name="postloan:debtCollection:edit">
									<td>
										<a href="#" onclick="details('${debtCollection.contractNo}')">客户详情</a>
										&nbsp;
										<a href="#" onclick="debtDetails('${debtCollection.contractNo}','${debtCollection.id}')">催收详情</a>
									</td>
								</shiro:hasPermission>
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