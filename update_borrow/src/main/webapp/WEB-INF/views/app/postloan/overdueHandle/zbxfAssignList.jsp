<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>总部下发列表</title>
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
	//总部选择人，启动流程
	function zbxfAssign(userName,loginName){
		confirmx("您是否确定选择【" + userName + "】下发任务?", function() {
			loading();
			top.$.jBox.tip.mess = null;
			$.ajax({
				type : "post",
				url : "${ctx}/postloan/overdueHandle/zbxfAssign?contractNo=${contractNo}&periodNum=${periodNum}" + "&loginName=" + loginName,
				dataType : "json",
				success : function(data) {
					closeTip();
					alertx(data.message, function() {
						parent.page();
						closeJBox();
					});
				},
				error : function(msg) {
					alertx("操作失败，请查看后台信息");
				}
			});
		});
	}
	//大区选择人提交流程
	function dqxfAssign(userName,loginName){
		var taskId = $("#taskId").val();
		var procInstId = $("#procInstId").val();
		confirmx("您是否确定选择【" + userName + "】下发任务?", function() {
			loading();
			top.$.jBox.tip.mess = null;
			$.ajax({
				type : "post",
				url : "${ctx}/postloan/overdueHandle/dqxfAssign?contractNo=${contractNo}&periodNum=${periodNum}" + "&loginName=" + loginName+ "&taskId=" + taskId + "&procInstId=" + procInstId,
				dataType : "json",
				success : function(data) {
					closeTip();
					alertx(data.message, function() {
						parent.page();
						closeJBox();
					});
				},
				error : function(msg) {
					alertx("操作失败，请查看后台信息");
				}
			});
		});
	}
	function reset() {
		$("#userName").val('');
		$("#loginName").val('');
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="zbxfAssignSearchForm" modelAttribute="plUser" action="${ctx}/postloan/overdueHandle/zbxfAssignList?contractNo=${contractNo}&periodNum=${periodNum}" method="post">
					<input id="taskId" name="taskId" type="hidden" value="${actTaskParam.taskId}" />
					<input id="procInstId" name="procInstId" type="hidden" value="${actTaskParam.procInstId}" />
					<div class="filter">
						<table class="searchTable">
							<tr>
								<td class="ft_label" >登录名：</td>
								<td class="ft_content" >
									<input id="loginName" name="loginName" type="text" maxlength="50" class="input-medium" value="${plUser.loginName }" />
								</td>
								<td class="ft_label" >人员姓名：</td>
								<td class="ft_content" >
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
								<th width="10%">序号</th>
								<th width="20%">逾期处理人员</th>
								<th width="30%">所属机构</th>
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
									<td class="title" title="${plUser.companyName }">${plUser.companyName }</td>
									<c:if test="${actTaskParam.taskId eq null || actTaskParam.taskId eq ''}">
										<td><a href="#" onclick="zbxfAssign('${plUser.userName}','${plUser.loginName}')">任务下发</a></td>
									</c:if>
									<c:if test="${actTaskParam.taskId ne null && actTaskParam.taskId ne ''}">
										<td><a href="#" onclick="dqxfAssign('${plUser.userName}','${plUser.loginName}')">任务下发</a></td>
									</c:if>
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