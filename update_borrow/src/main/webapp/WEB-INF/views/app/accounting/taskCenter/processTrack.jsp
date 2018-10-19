<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>流程轨迹列表</title>
<meta name="decorator" content="default" />
</head>
<body>
	<div class="wrapper">
		<div class="tableList">
			<h3 class="tableTitle">流程轨迹列表</h3>
			<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
				<tr>
					<th width="10%">任务名称</th>
					<th width="10%">执行人</th>
					<th width="10%">开始时间</th>
					<th width="10%">结束时间</th>
					<th width="10%">状态</th>
					<th width="20%">提交意见</th>
					<th>任务历时</th>
				</tr>
				<c:forEach items="${proList}" var="proList" varStatus="index">
				<tr>
					<td class="title" title="${proList.TASKNAME}">${proList.TASKNAME}</td>
					<td class="title" title="${proList.USERNAME}">${proList.USERNAME}</td>
					<td><fmt:formatDate value="${proList.STARTTIME}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td><fmt:formatDate value="${proList.ENDTIME}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td class="title" title="${proList.STATUS}">${proList.STATUS}</td>
					<td class="title" title="${proList.NCOMMENT}">${proList.NCOMMENT}</td>
					<td >${proList.TIMESPAN}</td>
				</tr>
				</c:forEach>
			</table>
		</div>
		<div class="searchButton">
			<input id="btnReset" class="btn btn-primary" type="button" value="关闭" onclick="closeJBox();" />
		</div>
	</div>
</body>
</html>
