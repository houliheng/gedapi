<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>加黑详情</title>
<meta name="decorator" content="default" />
<base target="_self"/>
<script type="text/javascript">
function page(n,s){
	$("#pageNo").val(n);
	$("#pageSize").val(s);
	$("#searchForm").submit();
	return false;
}
</script>
</head>
<body>
	<!-- 
	 * @reqno: H1512210032
	 * @date-designer:2015年12月24日-lirongchao
	 * @e-out-other	: name -操作人 : 操作人
	 * @e-out-other	: createDate -操作时间 : 操作时间
	 * @e-out-other	: listStatus -操作类型 : 操作类型
	 * @e-out-other	: remarks -设置说明 : 设置说明
	 * @e-ctrl : close -关闭 : 关闭
	 * @date-author:2015年12月24日-lirongchao: 	1.点击“个人黑名单管理”页面列表中的“详情”链接，弹出窗体，窗体名称“加黑详情”；
												2.“加黑详情”页面布局：上下布局，依次为：工具栏、加黑详情列表；
												3. 工具栏:关闭按钮，点击按钮，关闭当前窗体；
												4.加黑详情列表数据项：操作人、操作时间、操作类型（加黑、刷白）、设置说明；
												   列表排序：操作时间升序；
												   表格分页显示；
												   鼠标放到【操作说明】列时，以tip显示具体内容，以避免内容过多无法查看；
	 -->
	<div class="wrapper">
		<form:form id="searchForm" modelAttribute="blacklist" action="${ctx}/credit/blacklist/details" method="post">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
			<input id="blacklistId" name="blacklistId" type="hidden" value="${blacklistDetail.blacklistId}" />
		</form:form>	
		<div class="ribbon">
			<ul class="layout">
				<li class="mcp_close"><a href="#" id="close" onclick="closeJBox();"><span><b></b>关闭</span></a></li>
			</ul>
		</div>
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 class="tableTitle">加黑详情列表</h3>
			<div id="tableDataId" style="max-height:400px;overflow:auto;">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<th width="10%">操作人</th>
						<th width="15%">操作时间</th>
						<th width="10%">操作类型</th>
						<th >设置说明</th>
					</tr>
					<c:forEach items="${page.list}" var="black" varStatus="index">
						<c:if test="${0 == index.count%2}">
							<tr class="doubleRow">
						</c:if>
						<c:if test="${1 == index.count%2}">
							<tr>
						</c:if>
						<td id = "name" >${black.createBy.name}</td>
						<td id = "createDate"><fmt:formatDate value="${black.createDate }" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td id = "listStatus"> <c:if test="${black.listStatus eq '1'}">加黑</c:if><c:if test="${black.listStatus eq '2'}">刷白</c:if></td>
						<td id = "remarks" class="title" title="${black.remarks}">${black.remarks}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="pagination">${page}</div>
		</div>
	</div>
</body>
</html>
