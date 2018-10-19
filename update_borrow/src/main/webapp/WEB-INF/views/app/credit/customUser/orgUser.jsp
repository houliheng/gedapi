<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>人员选择</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}

	function changeup() {
		var selectedUser = $("input[name='id']:checked");
		if (selectedUser.length != 1) {
			alertx("请选择一个任务办理人员!");
		} else {
			var loginName = selectedUser.attr("loginName");
			var id = selectedUser.attr("value");
			$.ajax({
			url : "${ctx}/credit/taskCenter/doAsign?user=" + loginName + "&userId=" + id + "&taskId=${actTaskParam.taskId}&applyNo=${actTaskParam.applyNo}&taskDefKey=${actTaskParam.taskDefKey}&processName=${processName}&execId=${actTaskParam.execId}&procDefId=${actTaskParam.procDefId}",
			type : "POST",
			success : function(data) {
				if ("success" == data) {
					alertx("任务分配成功！", function() {
						parent.page();
						closeJBox();
					});
				} else {
					alertx("任务分配失败！");
				}
			}
			});
		}
	}

	function formReset() {
		$("#loginName").val('');
		$("#name").val('');
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="user" action="${ctx}/credit/taskCenter/loadOrgUser" method="post">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<input type="hidden" id="applyNo" name="applyNo" value="${actTaskParam.applyNo}" />
					<input type="hidden" id="taskId" name="taskId" value="${actTaskParam.taskId}" />
					<input type="hidden" id="taskDefKey" name="taskDefKey" value="${actTaskParam.taskDefKey}" />
					<input type="hidden" id="procDefId" name="procDefId" value="${actTaskParam.procDefId}" />
					<input type="hidden" id="status" name="status" value="${actTaskParam.status}" />
					<input type="hidden" id="procInstId" name="procInstId" value="${actTaskParam.procInstId}" />
					<input type="hidden" id="execId" name="execId" value="${actTaskParam.execId}" />
					<input type="hidden" id="processName" name="processName" value="${processName}" />
					<div class="filter">
						<table class="searchTable">
							<tr>
								<td class="ft_label">登录名：</td>
								<td class="ft_content">
									<form:input path="loginName" />
								</td>
								<td class="ft_label">姓名：</td>
								<td class="ft_content">
									<form:input path="name" />
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />
							&nbsp;
							<input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="return formReset();" />
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<div class="ribbon">
			<ul class="layout">
				<li class="mcp_change">
					<a href="#" onclick="changeup()" id="assign">
						<span>
							<b></b>
							分配
						</span>
					</a>
				</li>
				<li class="mcp_close">
					<a href="#" onclick="closeJBox()" id="btnclose">
						<span>
							<b></b>
							关闭
						</span>
					</a>
				</li>
			</ul>
		</div>
		<div class="tableList">
			<h3 class="infoListTitle">人员列表</h3>
			<div style="height:300px;overflow:auto;">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<th width="30px"></th>
						<th width="10%">序号</th>
						<th width="10%">登录名</th>
						<th width="10%">姓名</th>
						<th width="15%">归属部门</th>
						<th>归属机构</th>
					</tr>
					<c:forEach items="${page.list}" var="user" varStatus="index">
						<c:if test="${0 == index.count%2}">
							<tr class="doubleRow">
						</c:if>
						<c:if test="${1 == index.count%2}">
							<tr>
						</c:if>
						<td width="30px">
							<input type="radio" id="id" name="id" value="${user.id}" loginName="${user.loginName}" />
						</td>
						<td width="10%" id="count">${index.count + (page.pageNo-1)*page.pageSize}</td>
						<td width="10%" id="loginName">${user.loginName}</td>
						<td width="10%" id="name">${user.name}</td>
						<td width="15%" id="officeId">${user.office.name}</td>
						<td id="companyId">${user.company.name}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="pagination">${page}</div>
		</div>
	</div>
</body>
</html>