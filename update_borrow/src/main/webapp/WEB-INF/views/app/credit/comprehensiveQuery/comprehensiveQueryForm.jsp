<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>借前外访</title>
	<base target="_self">
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		//初始化最初展示的Tab页面
		$(document).ready(function(){
			showLoanApply(true);
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
				<li class="active">
					<a href="#tab_loanApply" onclick="showLoanApply(true)">借款申请信息</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_custInfo" onclick="showCustInfoTab(true)">客户信息</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_guarantorInfo" onclick="showGuarantorInfoTab(true)">担保信息</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_mortgageCarInfo" onclick="showMortgageCarInfoTab(true)">抵质押物信息</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_checkDoubtful" onclick="showCheckDoubtfulTab(true)">借前外访信息</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_checkFace" onclick="showCheckFaceTab(true)">面审信息</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_faceSign" onclick="showFaceSignTab(true)">面签信息</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_creditViewBook" onclick="showCreditViewBookTab(true)">信审意见书</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_checkApprove" onclick="showCheckApproveTab(true)">批复信息</a>
				</li>
			</ul>
		</div>
		<div class="tab-content">
			<div class="tab-pane" id="tab_loanApply"></div>
			<div class="tab-pane" id="tab_custInfo"></div>
			<div class="tab-pane" id="tab_guarantorInfo"></div>
			<div class="tab-pane" id="tab_mortgageCarInfo"></div>
			<div class="tab-pane" id="tab_checkDoubtful"></div>
			<div class="tab-pane" id="tab_checkFace"></div>
			<div class="tab-pane" id="tab_faceSign"></div>
			<div class="tab-pane" id="tab_creditViewBook"></div>
			<div class="tab-pane" id="tab_checkApprove"></div>
		</div>
	</div>
</body>
</html>