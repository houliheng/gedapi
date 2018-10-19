<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>日常检查待分配列表</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {

	});

	function issue(checkedById, checkedBy) {
		confirmx("您是否确定选择【" + checkedBy + "】下发任务?", function() {
			loading();
			var contractNo = '${contractNo}';
			var url = "${ctx}/postloan/checkDaily/issue";
			$.post(url, {
				checkedById : checkedById,
				checkedBy : checkedBy,
				contractNo : contractNo
			}, function(data) {
				if (data) {
					closeTip();
					if (data.status == 1) {
						alertx(data.message, function() {
							parent.page();
							closeJBox();
						});
					} else {
						alertx(data.message);
					}
				}
			});
		});
	}

	function reset() {
		$("#userName").val('');
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="checkDailySearchForm" modelAttribute="plUser" action="${ctx}/postloan/checkDaily/toIssue?contractNo=${contractNo }" method="post">
					<div class="filter">
						<table class="searchTable">
							<tr>
								<td class="ft_label" style="width: 20%;">检查人员名称：</td>
								<td class="ft_content" style="width: 70%;">
									<input id="userName" name="userName" type="text" maxlength="50" class="input-medium" value="${plUser.userName }" />
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />
							&nbsp;
							<input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="return reset();" />
						</div>
					</div>
				</form:form>
			</div>
			<sys:message content="${message}" />
			<div class="tableList">
				<h3 class="tableTitle">数据列表</h3>
				<div id="tableDataId" style="max-height: 400px; overflow: auto;">
					<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
						<thead>
							<tr>
								<th width="20%">序号</th>
								<th width="20%">借后检查人员</th>
								<th width="20%">所属机构</th>
								<shiro:hasPermission name="postloan:checkDaily:edit">
									<th width="20%">操作</th>
								</shiro:hasPermission>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${page.list }" var="plUser" varStatus="pl">
								<tr>
									<td class="title" title="${plUser.count }">${pl.count }</td>
									<td class="title" title="${plUser.userName }">${plUser.userName }</td>
									<td class="title" title="${plUser.orgName }">${plUser.orgName }</td>
									<shiro:hasPermission name="postloan:checkDaily:edit">
										<c:if test="${borrowNewRepayOldFlag ne 'true' }">
											<td>
												<a href="#" onclick="issue('${plUser.userId }','${plUser.userName }')">任务下发</a>
											</td>
										</c:if>
										<c:if test="${borrowNewRepayOldFlag eq 'true' }">
											<td>
												<a href="#" onclick="issue('${plUser.userId }')">任务指定</a>
											</td>
										</c:if>
									</shiro:hasPermission>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>