<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>借新还旧信息管理</title>
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

	//借新还旧
	function check(id) {
		var url = "${ctx}/postloan/borrowNew/form?id=" + id + "&applyFlag=${borrowNew.ZBOrDQ}";
		var title = "借新还旧审核";
		var width = $(window).width() - 100;
		width = Math.max(width, 1000);
		var height = $(window).height() - 200;
		height = Math.min(height, 600);
		openJBox("borrowNewCheckForm", url, title, width, height);
	}

	//重置
	function resetBorrow() {
		$("#contractNo").val("");
		$("#checkResult").select2("val", "");
		$("#s2id_checkResult>.select2-choice>.select2-chosen").html("");
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="borrowNew" action="${ctx}/postloan/borrowNew/${list}" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<div class="filter">
						<table class="searchTable">
							<tr>
								<td class="ft_label">合同编号：</td>
								<td class="ft_content">
									<input id="contractNo" name="contractNo" type="text" maxlength="50" class="input-medium" value="${borrowNew.contractNo }" />
								</td>
								<td class="ft_label">审核结果：</td>
								<td class="ft_content">
									<form:select path="checkResult" class="input-medium">
										<form:option value="" label="" />
										<form:options items="${fns:getDictList('BORROW_NEW_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />
							&nbsp;
							<input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="resetBorrow();" />
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div id="tableDataId" style="max-height: 400px; overflow: auto;">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th width="3%">序号</th>
							<th width="15%">合同编号</th>
							<th width="6%">申请人员</th>
							<th width="10%">申请原因</th>
							<th width="7%">申请时间</th>
							<th width="8%">大区审核人员</th>
							<th width="7%">大区审批意见</th>
							<th width="8%">大区审批时间</th>
							<th width="7%">总部审核人员</th>
							<th width="8%">总部审批意见</th>
							<th width="8%">总部审批时间</th>
							<th width="7%">审批结果</th>
							<c:if test="${noCheck ne 'true' }">
								<shiro:hasPermission name="postloan:borrowNew:edit">
									<th width="6%">操作</th>
								</shiro:hasPermission>
							</c:if>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="borrowNew" varStatus="borrowNewList">
							<tr>
								<td class="title" title="${borrowNewList.count}">${borrowNewList.count }</td>
								<td id="contractNo" class="title" title="${borrowNew.contractNo}">${borrowNew.contractNo}</td>
								<td id="applyBy" class="title" title="${borrowNew.applyBy}">${borrowNew.applyBy}</td>
								<td id="applyAdvice" class="title" title="${borrowNew.applyAdvice}">${borrowNew.applyAdvice}</td>
								<td id="applyDate" class="title" title="<fmt:formatDate value="${borrowNew.applyDate}" pattern="yyyy-MM-dd" />">
									<fmt:formatDate value="${borrowNew.applyDate}" pattern="yyyy-MM-dd" />
								</td>
								<td id="DQcheckedBy" class="title" title="${borrowNew.DQcheckedBy}">${borrowNew.DQcheckedBy}</td>
								<td id="DQcheckAdvice" class="title" title="${borrowNew.DQcheckAdvice}">${borrowNew.DQcheckAdvice}</td>
								<td id="DQcheckDate" class="title" title="<fmt:formatDate value="${borrowNew.DQcheckDate}" pattern="yyyy-MM-dd" />">
									<fmt:formatDate value="${borrowNew.DQcheckDate}" pattern="yyyy-MM-dd" />
								</td>
								<td id="ZBcheckedBy" class="title" title="${borrowNew.ZBcheckedBy}">${borrowNew.ZBcheckedBy}</td>
								<td id="ZBcheckAdvice" class="title" title="${borrowNew.ZBcheckAdvice}">${borrowNew.ZBcheckAdvice}</td>
								<td id="ZBcheckDate" class="title" title="<fmt:formatDate value="${borrowNew.ZBcheckDate}" pattern="yyyy-MM-dd" />">
									<fmt:formatDate value="${borrowNew.ZBcheckDate}" pattern="yyyy-MM-dd" />
								</td>
								<td id="checkResult" class="title" title="${fns:getDictLabel(borrowNew.checkResult, 'BORROW_NEW_STATUS', '')}">${fns:getDictLabel(borrowNew.checkResult, 'BORROW_NEW_STATUS', '')}</td>
								<c:if test="${noCheck ne 'true' }">
									<shiro:hasPermission name="postloan:borrowNew:edit">
										<td>
											<a href="#" onclick="check('${borrowNew.id}');">审核</a>
										</td>
									</shiro:hasPermission>
								</c:if>
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