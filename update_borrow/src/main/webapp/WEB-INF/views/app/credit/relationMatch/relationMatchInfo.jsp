<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>详情</title>
	<base target="_self">
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		//初始化最初展示的Tab页面
		$(document).ready(function(){
			showLoanApply(true);
		});
		//抵质押物信息
		function showRepayDetail(readOnly) {
			showTab('tab_repayDetail', '${ctx}/credit/contract/contractInfo?contractNo=${contractNo}');
		}
	</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/app/credit/workflow/include/workflowScript.jsp"%>
	<div class="wrapper">
		<%-- <%@ include file="/WEB-INF/views/app/credit/workflow/include/workflowButtons.jsp"%> --%>
	    <sys:message content="${message}"/>	
		<div style="margin-top: 5px;">
			<ul class="nav nav-tabs" id="mainTabs">
				<li class="">
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
					<a href="#tab_mortgageCarInfo" onclick="showMortgageCarEvaluateInfoTab(true)">抵质押物信息</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_repayDetail" onclick="showRepayDetail(true)">还款明细</a>
				</li>
			</ul>
		</div>
		<div class="tab-content">
			<div class="tab-pane" id="tab_loanApply"></div>
			<div class="tab-pane" id="tab_custInfo"></div>
			<div class="tab-pane" id="tab_guarantorInfo"></div>
			<div class="tab-pane" id="tab_mortgageCarInfo"></div>
			<div class="tab-pane" id="tab_repayDetail"></div>
		</div>
	</div>
</body>
</html>