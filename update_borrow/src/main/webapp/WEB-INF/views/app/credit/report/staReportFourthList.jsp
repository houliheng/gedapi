<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>统计报表4管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
	});
	function fromReset() {
		$("#company").select2("val", "");
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="staReportSecond" action="${ctx}/credit/report/fourthList" method="post" class="breadcrumb form-search">
					<div class="filter">
						<table class="searchTable">
							<tr>
								<td class="ft_label">机构：</td>
								<td class="ft_content">
									<form:select path="company" class="input-medium">
										<form:option value="" label="请选择" />
										<form:options items="${list}" itemLabel="orgName" itemValue="orgId" htmlEscape="false" />
									</form:select>
								</td>
								<td></td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />
							<input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="return fromReset();" />
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div id="tableDataId" style="overflow:auto;">
				<table id="contentTable" cellpadding="0" cellspacing="0" border="0" width="100%">
					<thead>
						<tr>
							<th width="5%">排名</th>
							<th>行业名称</th>
							<th>统计数量</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${lists}" var="lists" varStatus="i">
							<tr>
								<td>${i.count}</td>
								<td id="induName" class="title" title="${lists.induName}">${lists.induName}</td>
								<td id="count" class="title" title="${lists.count}">${lists.count}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
