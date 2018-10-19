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
			url : "${ctx}/postloan/collateralDisposal/taskDown?loginName=" + loginName + "&taskId=${actTaskParam.taskId}&procInstId=${actTaskParam.procInstId}&applyNo=${actTaskParam.applyNo}&taskDefKey=${actTaskParam.taskDefKey}&processName=${processName}&execId=${actTaskParam.execId}&procDefId=${actTaskParam.procDefId}&contractNo=${collateralDisposal.contractNo}&periodNum=${collateralDisposal.periodNum}",
			type : "POST",
			success : function(data) {
				if (data.status == "1") {
					alertx("任务下发成功！", function() {
						parent.page();
						closeJBox();
					});
				} else {
					alertx("任务下发失败！");
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
		<div class="ribbon">
			<ul class="layout">
				<li class="mcp_change">
					<a href="#" onclick="changeup()" id="assign">
						<span>
							<b></b>
							下发
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
			<div>
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<th width="30px"></th>
						<th width="10%">序号</th>
						<th width="10%">登录名</th>
						<th width="10%">姓名</th>
						<th>归属机构</th>
					</tr>
					<c:forEach items="${userList}" var="user" varStatus="index">
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
						<td id="companyName">${user.companyName}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="pagination">${page}</div>
		</div>
	</div>
</body>
</html>