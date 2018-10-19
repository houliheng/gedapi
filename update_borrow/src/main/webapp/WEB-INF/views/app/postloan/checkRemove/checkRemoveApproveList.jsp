<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>核销</title>
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

	function checkRemoveForm(id, contractNo) {
		var url = "${ctx}/postloan/checkRemove/approveForm";
		var title = "核销";
		var width = $(window).width() - 100;
		var height = $(window).height() - 100;
		openJBox("chenckRemoveJBox", url, title, width, height, {
		id : id,
		contractNo : contractNo,
		ZBorDQ : '${ZBorDQ}'
		});
	}

	function resetForm() {
		$("#contractNo").val("");
		$("#custName").val("");
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="checkRemove" action="${ctx}/postloan/checkRemove/${approveList}" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<input id="ZBorDQ" name="ZBorDQ" type="hidden" value="${ZBorDQ}" />
					<div class="filter">
						<table class="searchTable">
							<tr>
								<td class="ft_label">合同编号：</td>
								<td class="ft_content">
									<input id="contractNo" name="contractNo" type="text" maxlength="50" class="input-medium" value="${checkRemove.contractNo}" />
								</td>
								<td class="ft_label">借款人姓名：</td>
								<td class="ft_content">
									<input id="custName" name="custName" type="text" maxlength="50" class="input-medium" value="${checkRemove.custName}" />
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
							<th width="10">序号</th>
							<th width="15%">合同编号</th>
							<th width="10%">借款人姓名</th>
							<th width="10%">合同金额</th>
							<th width="10%">逾期金额</th>
							<th width="10%">合同起始日期</th>
							<th width="10%">合同到期日期</th>
							<th width="15%">核销状态</th>
							<th width="10%">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="checkRemove" varStatus="i">
							<tr>
								<td class="title" title="${i.index+1}">${i.index+1}</td>
								<td class="title" title="${checkRemove.contractNo}">${checkRemove.contractNo}</td>
								<td class="title" title="${checkRemove.custName}">${checkRemove.custName}</td>
								<td class="title" title="${checkRemove.contractAmount}">${checkRemove.contractAmount}</td>
								<td class="title" title="${checkRemove.currOverdueAmount}">${checkRemove.currOverdueAmount}</td>
								<td class="title" title="<fmt:formatDate value='${checkRemove.conStartDate}' pattern='yyyy-MM-dd' />">
									<fmt:formatDate value='${checkRemove.conStartDate}' pattern='yyyy-MM-dd' />
								</td>
								<td class="title" title="<fmt:formatDate value='${checkRemove.conStartDate}' pattern='yyyy-MM-dd' />">
									<fmt:formatDate value='${checkRemove.conStartDate}' pattern='yyyy-MM-dd' />
								</td>
								<td class="title" title="${fns:getDictLabel(checkRemove.checkRemoveStatus, 'CHECK_REMOVE_STATUS', '')}">${fns:getDictLabel(checkRemove.checkRemoveStatus, 'CHECK_REMOVE_STATUS', '')}</td>
								<td>
									<a href="#" onclick="checkRemoveForm('${checkRemove.id}','${checkRemove.contractNo}');">审核</a>
								</td>
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