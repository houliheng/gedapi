<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>外访费返还</title>
	<base target="_self">
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		//初始化最初展示的Tab页面
		$(document).ready(function(){
			showCheckFeeReturnFormTab('${checkFeeId}','${readOnly}');
		});
	</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/app/credit/workflow/include/workflowScript.jsp"%>
	<div class="wrapper">
	    <sys:message content="${message}"/>	
		<div style="margin-top: 5px;">
			<ul class="nav nav-tabs" id="mainTabs">
				<li class="">
					<a href="#tab_applyInfoView" onclick="showApplyInfoViewTab('${applyNo}',true)">借款申请信息</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_checkFeeReturn" onclick="showCheckFeeReturnFormTab('${checkFeeId}','${readOnly}')">外访费返还</a>
				</li>
			</ul>
		</div>
		<div class="tab-content">
			<div class="tab-pane" id="tab_applyInfoView"></div>
			<div class="tab-pane" id="tab_checkFeeReturn"></div>
		</div>
	</div>
</body>
</html>