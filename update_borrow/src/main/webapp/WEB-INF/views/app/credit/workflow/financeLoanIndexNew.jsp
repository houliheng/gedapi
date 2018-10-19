<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>财务放款</title>
<base target="_self">
<meta name="decorator" content="default" />
<script type="text/javascript">
	//初始化最初展示的Tab页面
	$(document).ready(function() {
		showFinanceLoanTabNew('${actTaskParam.status == 1}');
	});
</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/app/credit/workflow/include/workflowScript.jsp"%>
	<div class="wrapper">
		<%@ include file="/WEB-INF/views/app/credit/workflow/include/workflowButtons.jsp"%>
		<sys:message content="${message}" />
		<div style="margin-top: 5px;">
			<ul class="nav nav-tabs" id="mainTabs">
				
				<li class="">
					<a href="#tab_financeLoan" onclick="showFinanceLoanTabNew('${actTaskParam.status == 1}')">财务放款</a>
				</li>
			</ul>
		</div>
		<div class="tab-content">
			<div class="tab-pane" id="tab_faceSign"></div>
			<div class="tab-pane" id="tab_financeLoan"></div>
			<div class="tab-pane" id="tab_creditReport"></div>
			<div class="tab-pane" id="tab_get"></div>
		</div>
	</div>
</body>
</html>