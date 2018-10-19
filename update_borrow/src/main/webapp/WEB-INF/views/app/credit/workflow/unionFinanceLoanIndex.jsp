<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>联合授信</title>
	<base target="_self">
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		//初始化最初展示的Tab页面
		$(document).ready(function(){
			showCheckApproveTab('${actTaskParam.status == 1}');
		});
	</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/app/credit/workflow/include/workflowScript.jsp"%>
	<div class="wrapper">
		<%@ include file="/WEB-INF/views/app/credit/workflow/include/workflowButtons.jsp"%>
	    <sys:message content="${message}"/>	
		<div style="margin-top: 5px;">
			<ul class="nav nav-tabs" id="mainTabs">
				<li class="">
					<a href="#tab_checkApprove" onclick="showCheckApproveTab('${actTaskParam.status == 1}')">批复信息</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_get" onclick="showGetTab('${actTaskParam.status == 1}')">冠e通</a>
				</li>
			</ul>
		</div>
		<div class="tab-content">
			<div class="tab-pane" id="tab_checkApprove"></div>
			<div class="tab-pane" id="tab_get"></div>
		</div>
	</div>
</body>
</html>