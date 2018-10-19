<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
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
	function resetSearch() {
		$("#productTypeCode").select2("val", "");
		$("#productTypeCode").val("");
		$("#contractAmount").val("");
	}
</script>
</head>
<body>
	<div class="wrapper">
		<ul class="nav nav-tabs">
			<li class="active">
				<a href="${ctx}/credit/generalManagerAudit/">条件列表</a>
			</li>
			<shiro:hasPermission name="credit:generalManagerAudit:edit">
				<li>
					<a href="${ctx}/credit/generalManagerAudit/form">条件添加</a>
				</li>
			</shiro:hasPermission>
		</ul>
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="generalManagerAudit" action="${ctx}/credit/generalManagerAudit/" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<div class="filter">
						<ul class="ul-form">
							<li>
								<label>产品类型：</label>
								<form:select path="productTypeCode" class="input-medium">
									<form:option value="" label="请选择" />
									<form:options items="${fns:getDictList('PRODUCT_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
								</form:select>
							</li >
							<li>
							</li>
							<li>
								<label style="display:inline-block;width:110px">合同金额(元)：</label>
								<form:input path="contractAmount" htmlEscape="false" onkeyup="keyPressNumber(this)" class="input-medium number" />
							</li>
						</ul>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />
							&nbsp;
							<input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="resetSearch()" />
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div id="tableDataId">
				<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
					<thead>
						<tr>
							<th>姓名</th>
							<th>所属机构</th>
							<th>产品类型</th>
							<th>合同金额(元)</th>
							<shiro:hasPermission name="credit:generalManagerAudit:edit">
								<th>操作</th>
							</shiro:hasPermission>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="generalManagerAudit">
							<tr>
								<td class="title" title="${generalManagerAudit.user.name}">${generalManagerAudit.user.name}</td>
								<td class="title" title="${generalManagerAudit.office.name}">${generalManagerAudit.office.name}</td>
								<td class="title" title="${generalManagerAudit.productTypeCode}">${fns:getDictLabel(generalManagerAudit.productTypeCode, 'PRODUCT_TYPE', '')}</td>
								<td class="title" title="${generalManagerAudit.contractAmount}">${generalManagerAudit.contractAmount}</td>
								<shiro:hasPermission name="credit:generalManagerAudit:edit">
									<td>
										<a href="${ctx}/credit/generalManagerAudit/form?id=${generalManagerAudit.id}">修改</a>
										<a href="${ctx}/credit/generalManagerAudit/delete?id=${generalManagerAudit.id}" onclick="return confirmx('确认要删除该条件吗？', this.href)">删除</a>
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