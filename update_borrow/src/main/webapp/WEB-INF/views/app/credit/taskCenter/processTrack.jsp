<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>流程轨迹列表</title>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/jquery-jbox/2.3/jquery-1.4.2.min.js"></script>
 <link type="text/css" rel="stylesheet" href="${ctxStatic}/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css"/>

<script type="text/javascript">	
		function showFullMsg(hitaskid){
		var url = "${ctx}/credit/checkDoubtful/showFullMsg?hitaskid="+ hitaskid;
		var width = $(window).width()-100;
	var height = $(window).height()-50;
		// window.location.href=url;
		//	 openJBox("box_"+hitaskid, url, "流程轨迹",1000,800);
		//	openJBox("box_", url, "查看", width, height);
		 window.showModalDialog(url, null, "dialogWidth:800px;dialogHeight:500px;status:yes;help:yes;resizable:yes;");
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="tableList">
			<h3 class="tableTitle">流程轨迹列表</h3>
			<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
				<tr>
					<th width="9%">任务名称</th>
					<shiro:hasRole name="admin">
						<th width="8%">执行人</th>
					</shiro:hasRole>
					<th width="13%">开始时间</th>
					<th width="13%">结束时间</th>
					<th width="13%">状态</th>
					<th width="20%">提交意见</th>
					<th>任务历时</th>
				</tr>
				<c:forEach items="${proList}" var="proList" varStatus="index">
				<tr>
					<td class="title" title="${proList.TASKNAME}">${proList.TASKNAME}</td>
					<shiro:hasRole name="admin">
						<td class="title" title="${proList.USERNAME}">${proList.USERNAME}</td>
					</shiro:hasRole>
					<td><fmt:formatDate value="${proList.STARTTIME}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td><fmt:formatDate value="${proList.ENDTIME}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td class="title" title="${proList.STATUS}">${proList.STATUS}</td>
				<%-- 	<c:choose>
						<c:when test="${proList.NCOMMENT}">
						</c:when>
						<c:otherwise>
						</c:otherwise>
					</c:choose> --%>
					<%--  <td class="title" title="${proList.NCOMMENT}">${proList.NCOMMENT}</td>  --%>
				 	<td class="title" title="${proList.NCOMMENT}">
								<a href="#" onclick="showFullMsg('${proList.HITASKID}')">${proList.NCOMMENT}</a>
					</td> 
					<a id="todoBusinessListSkipId" href="javascript:void(0)"></a>
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
