<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>接口日志记录管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {

	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
	function resetData() {
		$("#applyNo").val('');
		$("#interfaceName").val('');
		$("#message").val('');
		$("#sendDate").val('');
		$("#createDate").val('');
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="interfaceInfo" action="${ctx}/credit/interfaceInfo/" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<div class="filter">
						<ul class="ul-form">
							<li>
								<label>申请编号：</label>
								<form:input path="applyNo" htmlEscape="false" maxlength="32" class="input-medium" />
							</li>
							<li>
								<label>接口名称：</label>
								<form:input path="interfaceName" htmlEscape="false" maxlength="100" class="input-medium" />
							</li>
							<li>
								<label>返回信息：</label>
								<form:input path="message" htmlEscape="false" maxlength="1000" class="input-medium" />
							</li>
							<li>
								<label>发送时间：</label>
								<input id="sendDate"  name="sendDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" value="<fmt:formatDate value="${interfaceInfo.sendDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" />
							</li>
							<li>
								<label>结束时间：</label>
								<input id="createDate"  name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" value="<fmt:formatDate value="${interfaceInfo.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" />
							</li>
						</ul>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />
							&nbsp;
							<input id="btnReset" class="btn btn-primary" type="button" onclick="resetData()" value="重置" />
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div id="tableDataId">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th>申请编号</th>
							<th width="15%">接口名称</th>
							<th>返回信息</th>
							<th width="15%">发送时间</th>
							<th width="15%">结束时间</th>
							<shiro:hasPermission name="credit:interfaceInfo:edit">
								<th width="7%">操作</th>
							</shiro:hasPermission>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="interfaceInfo">
							<tr>
								<td id="applyNo" class="title" title="${interfaceInfo.applyNo}">
									<a href="${ctx}/credit/interfaceInfo/form?id=${interfaceInfo.id}"> ${interfaceInfo.applyNo} </a>
								</td>
								<td id="interfaceName" class="title" title="${interfaceInfo.interfaceName}">${interfaceInfo.interfaceName}</td>
								<td id="message" class="title" title="${interfaceInfo.message}">${interfaceInfo.message}</td>
								<td id="sendDate" class="title" title="${interfaceInfo.sendDate}">
									<fmt:formatDate value="${interfaceInfo.sendDate}" pattern="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td id="createDate" class="title" title="${interfaceInfo.createDate}">
									<fmt:formatDate value="${interfaceInfo.createDate}" pattern="yyyy-MM-dd HH:mm:ss" />
								</td>
								<shiro:hasPermission name="credit:interfaceInfo:edit">
									<td>
										<a href="${ctx}/credit/interfaceInfo/delete?id=${interfaceInfo.id}" onclick="return confirmx('确认要删除该接口日志记录吗？', this.href)">删除</a>
									</td>
								</shiro:hasPermission>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="pagination">${page}</div>
		</div>
	</div>
</body>
</html>