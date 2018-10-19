<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>股权分配人员选择</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	function timeJudge(){
		var companyCode = $("#companyCodeId").val();
		var companyName = $("#companyNameId").val();
		var loginName = $("#loginNameId").val();
		var userName = $("#userNameId").val();
		if(companyCode.trim()=='' 
				&& companyName.trim() == ''
				&& loginName.trim() == ''
				&& userName.trim() == ''){
			alert('请填写检索条件。');
		}else{
			$("#searchForm").submit();
		}
	}

	function changeup() {
		var stockTaskReceiveId='${stockTaskReceiveId}';
		var stockType='${stockType}';
		if(stockType=='1'){
			var che = $("[id='id']:checked");
			if (che.length != 1) {
				alertx("请选择一个尽调人员!");
			} else {
				confirmx("确认后，原数据将清空，确认转办？", function() {
					var sysuserid = che[0].value;
					var urlsuffix = "?stockTaskReceiveId=${stockTaskReceiveId}&stockType=${stockType}&applyNo=${actTaskParam.applyNo}&taskId=${actTaskParam.taskId}&taskDefKey=${actTaskParam.taskDefKey}&procDefId=${actTaskParam.procDefId}&status=${actTaskParam.status}&procInstId=${actTaskParam.procInstId}&sysuserid=" + sysuserid;
					$.ajax({
					url : "${ctx}/credit/stockTaskDistribute/changeup" + urlsuffix,
					type : "POST",
					success : function(data) {
						if (data.status == "1") {
							alertx(data.message, function() {
								window.parent.goToList();	
								closeJBox(true);
							});
						} else {
							alertx(data.message);
						}
					}
					});
				});
			}
		}else{
			var che = $("[id='id']:checked");
			if (che.length != 1) {
				alertx("请选择一个尽调人员!");
			} else {
				var sysuserid = che[0].value;
				var urlsuffix = "?applyNo=${actTaskParam.applyNo}&taskId=${actTaskParam.taskId}&taskDefKey=${actTaskParam.taskDefKey}&procDefId=${actTaskParam.procDefId}&status=${actTaskParam.status}&procInstId=${actTaskParam.procInstId}&sysuserid=" + sysuserid;
				confirmx("请确认所选人员为本层级尽调人员，分配后不可更改！", function() {
					$.ajax({
						url : "${ctx}/credit/stockTaskDistribute/changeup" + urlsuffix,
						type : "POST",
						success : function(data) {
							if (data.status == "1") {
								alertx(data.message, function() {
									window.parent.disabledAssessPostSelect();	
									closeJBox(true);
								});
							} else {
								alertx(data.message);
							}
						}
					});
				});
			}
		}
	}
	
	//重置
	function fromReset() {
		$("#companyCodeId").val('');
		$("#companyNameId").val('');
		$("#loginNameId").val("");
		$("#userNameId").val("");
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="customer" action="${ctx}/credit/stockTaskDistribute/loadOrgUser" method="post">
				<input type="hidden" name="taskDefKey" value="${actTaskParam.taskDefKey}" />
				<input type="hidden" name="applyNo" value="${actTaskParam.applyNo}" />
				<input type="hidden" name="headUrl" value="${actTaskParam.headUrl}" />
				<input type="hidden" name="taskId" value="${actTaskParam.taskId}" />
				<input type="hidden" name="procDefId" value="${actTaskParam.procDefId}" />
				<input type="hidden" name="procInstId" value="${actTaskParam.procInstId}" />
				<input type="hidden" name="status" value="${actTaskParam.status}" />
				<input type="hidden" id="stockType" name="stockType" value="${stockType}">
				<input type="hidden" id="stockTaskReceiveId" name="stockTaskReceiveId" value="${stockTaskReceiveId}">
				
				<div class="filter">
					<table class="searchTable">
						<tr>
							<td class="ft_label">机构编码：</td>
							<td class="ft_content">
								<input id="companyCodeId" name="companyCode" value="${queryParams.companyCode}" type="text" maxlength="50" class="input-medium" />
							</td>
							<td class="ft_label">机构名称：</td>
							<td class="ft_content">
								<input id="companyNameId" name="companyName" value="${queryParams.companyName}" type="text" maxlength="50" class="input-medium" />
							</td>
						</tr>
						<tr>
							<td class="ft_label">登录名：</td>
							<td class="ft_content">
								<input id="loginNameId" name="loginName" value="${queryParams.loginName}" type="text" maxlength="50" class="input-medium" />
							</td>
							<td class="ft_label">人员姓名：</td>
							<td class="ft_content">
								<input id="userNameId" name="userName" value="${queryParams.userName}" type="text" maxlength="50" class="input-medium" />
							</td>
						</tr>
					</table>
					<div class="searchButton">
						<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="timeJudge();" />
						&nbsp;
						<input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="return fromReset();" />
					</div>
				</div>
				</form:form>
			</div>
		</div>
		<div class="ribbon">
			<ul class="layout">
				<li class="mcp_change">
					<a href="#" onclick="changeup()" id="changeup">
						<span>
							<b></b>
							选择
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
					<th width="25px;">序号</th>
					<th width="100px;">登录名</th>
					<th width="100px;">姓名</th>
					<th width="180px;">归属部门</th>
					<th width="120px;">机构编码</th>
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
						<td width="25px;" id="count">${index.count}</td>
						<td width="100px;" id="loginName">${appoint.loginName}</td>
						<td width="100px;" id="name">${appoint.name}</td>
						<td width="180px;" id="officeId">${appoint.office.name}</td>
						<td width="120px;" id="officeId">${appoint.company.code}</td>
						<td >${appoint.company.name}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
</body>
</html>