<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>转办人员选择</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	function changeup() {
		var che = $("[id='id']:checked");
		if (che.length != 1) {
			alertx("请选择一个转办人员!");
		} else {
			var sysuserid = che[0].value;
			var urlsuffix = "?applyNo=${actTaskParam.applyNo}&taskId=${actTaskParam.taskId}&taskDefKey=${actTaskParam.taskDefKey}&procDefId=${actTaskParam.procDefId}&status=${actTaskParam.status}&procInstId=${actTaskParam.procInstId}&sysuserid=" + sysuserid;
			$.ajax({
			url : "${ctx}/credit/taskCenter/changeup" + urlsuffix,
			type : "POST",
			success : function(data) {
				if (data.status == "1") {
					alertx(data.message, function() {
					parent.goToPage('${ctx}${actTaskParam.headUrl}','headUrlId');
						closeJBox(true);
					});
				} else {
					alertx(data.message);
				}
			}
			});
		}
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="ribbon">
			<ul class="layout">
				<li class="mcp_change">
					<a href="#" onclick="changeup()" id="changeup">
						<span>
							<b></b>
							转办
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
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<th width="30px;"></th>
					<th width="50px;">序号</th>
					<th width="100px;">登录名</th>
					<th width="100px;">姓名</th>
					<th width="300px;">归属部门</th>
					<th>归属机构</th>
				</tr>
			</table>
			<div style="height:300px;overflow:auto;">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<c:forEach items="${list}" var="appoint" varStatus="index">
						<c:if test="${0 == index.count%2}">
							<tr class="doubleRow">
						</c:if>
						<c:if test="${1 == index.count%2}">
							<tr>
						</c:if>
						<td width="30px;">
							<input type="radio" id="id" name="id" value="${appoint.id}" />
						</td>
						<td width="50px;" id="count">${index.count}</td>
						<td width="100px;" id="loginName">${appoint.loginName}</td>
						<td width="100px;" id="name">${appoint.name}</td>
						<td width="300px;" id="officeId">${appoint.office.name}</td>
						<td id="companyId">${appoint.company.name}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
</body>
</html>