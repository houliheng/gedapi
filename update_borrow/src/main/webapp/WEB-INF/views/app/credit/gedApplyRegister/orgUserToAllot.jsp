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
			var companyId = selectedUser.attr("value");
			var userId = selectedUser.attr("userId");
			$.ajax({
			url : "${ctx}/credit/gedApplyRegister/allot?loginName=" + loginName + "&userId=" + userId + "&companyId=" + companyId + "&id=${gedApplyRegister.id}",
			type : "POST",
			success : function(data) {
				if (data.status == "1") {
					alertx(data.message, function() {
						parent.page();
						closeJBox();
					});
				} else {
					alertx(data.message);
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
				<form:form id="searchForm" modelAttribute="user" action="${ctx}/credit/gedApplyRegister/toAllot" method="post">
					<input type="hidden" id="id" name="id" value="${gedApplyRegister.id}" />
					<div class="filter">
						<table class="searchTable">
							<tr>
								<td class="ft_label">姓名：</td>
								<td class="ft_content">
									<form:input path="name" />
								</td>
								<td class="ft_content">
									<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />
									&nbsp;
									<input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="return formReset();" />
								</td>
							</tr>
						</table>
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
						<th width="5%"></th>
						<th width="40%">姓名</th>
						<th width="40%">归属机构</th>
					</tr>
					<c:forEach items="${list}" var="user" varStatus="index">
						<tr>
							<td>
								<input type="radio" id="id" name="id" value="${user.companyId}" loginName="${user.loginName}" userId="${user.userId}" />
							</td>
							<td id="name">${user.userName}</td>
							<td id="companyId">${user.companyName}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
</body>
</html>