<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>转办人员选择</title>
	<meta name="decorator" content="default"/>
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
					if(data.status=="1"){
						alertx(data.message,function(){
							parent.page();
							closeJBox(true);
						});
					}else{
						alertx(data.message);
					}
				}
				});
			}
		}
		
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/credit/taskCenter/adminChange");
			if($("#pageNo")[0].value.length>10){
				top.$.jBox.tip('当前页码大小长度不能大于10位！');
				return true;
			}
			else if($("#pageSize")[0].value.length>10){
				top.$.jBox.tip('每页条数大小的长度不能大于10位！');
				return true
			}else{
				$("#searchForm").submit();
		    	return false;
			}
	    }
	</script>
</head>
<body>
	<div class="wrapper">
		<div class="ribbon">
			<ul class="layout">
				<li class="mcp_change"><a href="#" onclick="changeup()" id="changeup"><span><b></b>转办</span></a></li>
				<li class="mcp_close"><a href="#" onclick="closeJBox()" id="btnclose"><span><b></b>关闭</span></a></li>
			</ul>
		</div>
		<form:form id="searchForm" modelAttribute="user" action="${ctx}/credit/taskCenter/adminChange" method="post" class="breadcrumb form-search ">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
			<input id="applyNo" name="applyNo" type="hidden" value="${actTaskParam.applyNo}" />
			<input id="taskId" name="taskId" type="hidden" value="${actTaskParam.taskId}" />
			<input id="taskDefKey" name="taskDefKey" type="hidden" value="${actTaskParam.taskDefKey}" />
			<input id="procDefId" name="procDefId" type="hidden" value="${actTaskParam.procDefId}" />
			<input id="status" name="status" type="hidden" value="${actTaskParam.status}" />
			<input id="procInstId" name="procInstId" type="hidden" value="${actTaskParam.procInstId}" />
			<input id="searchFlag" name="searchFlag" type="hidden" value="1" />
			<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();" />
			<ul class="ul-form">
				<li><label>归属机构：</label> 
				<sys:usertreeselect id="company" name="company.id" value="${user.company.id}" labelName="company.name" labelValue="${user.company.name}" title="机构"
						url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true" /></li>
				<input id="oldCompanyId" type="hidden" value="${user.company.id}" />
				<li><label>登录名：</label> <form:input path="loginName" htmlEscape="false" maxlength="50" class="input-medium" /></li>
				<li class="clearfix"></li>
				<li><label>姓&nbsp;&nbsp;&nbsp;名：</label> 
				<form:input path="name" htmlEscape="false" maxlength="50" class="input-medium" /></li>
				<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();" />
				<li class="clearfix"></li>
			</ul>
		</form:form>
		<div class="tableList">
			<h3 class="infoListTitle">人员列表</h3>
			<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
				<thead>
					<tr>
						<th width="30px;"></th>
						<th width="50px;">序号</th>
						<th>归属机构</th>
						<th>归属部门</th>
						<th class="sort-column login_name">登录名</th>
						<th class="sort-column name">姓名</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="user" varStatus="index">
						<c:if test="${0 == index.count%2}">
							<tr class="doubleRow">
						</c:if>
						<c:if test="${1 == index.count%2}">
							<tr>
						</c:if>
						<td width="30px;"><input type="radio" id="id" name="id" value="${user.id}" /></td>
						<td width="50px;" id="count">${index.count + (page.pageNo-1)*page.pageSize}</td>
						<td>${user.company.name}</td>
						<td>${user.office.name}</td>
						<td>${user.loginName}</td>
						<td>${user.name}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="pagination">${page}</div>
		</div>
	</div>
</body>
</html>