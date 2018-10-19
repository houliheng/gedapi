<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>合同带催收统计管理</title>
<script type="text/javascript">
	$(document).ready(function() {
	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
</script>
</head>
<body>
	<div class="wrapper">
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 class="tableTitle">转办明细列表</h3>
			<div id="turnTableDataId" style="max-height:200px;overflow: auto;">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th width="5%">序号</th>
							<th width="20%">转办人</th>
							<th width="20%">转办时间</th>
							<th width="20%">原环节</th>
							<th width="20%">转办环节</th>
							<th width="15%">状态</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${turnTaskList}" var="turnTask" varStatus="turnTaskList">
							<tr>
								<td class="title" title="序号">${turnTaskList.count}</td>
								<td class="title" title="${turnTask.turnPerson}">${turnTask.turnPerson}</td>
								<td class="title" title="${turnTask.turnDate}">${turnTask.turnDate}</td>
								<c:if test="${turnTask.turnBefore == '1' }">
									<td class="title" title="${fns:getDictLabel('2', 'CURR_COLLECTION_TYPE', '')}">${fns:getDictLabel('2', 'CURR_COLLECTION_TYPE', '')}</td>
								</c:if>
								<c:if test="${turnTask.turnBefore != '1' }">
									<td class="title" title="${fns:getDictLabel(turnTask.turnBefore, 'CURR_COLLECTION_TYPE', '')}">${fns:getDictLabel(turnTask.turnBefore, 'CURR_COLLECTION_TYPE', '')}</td>
								</c:if>
								<td class="title" title="${fns:getDictLabel(turnTask.turnAfter, 'CURR_COLLECTION_TYPE', '')}">${fns:getDictLabel(turnTask.turnAfter, 'CURR_COLLECTION_TYPE', '')}</td>
								<td class="title" title="${fns:getDictLabel(turnTask.turnStatus, 'TURN_STATUS', '')}">${fns:getDictLabel(turnTask.turnStatus, 'TURN_STATUS', '')}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>