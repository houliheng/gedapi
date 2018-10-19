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
		showFinanceLoanTab('${actTaskParam.status == 1}');
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
					<a href="#tab_faceSign" onclick="showFaceSignTab(true)">面签信息</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_financeLoan" onclick="showFinanceLoanTab('${actTaskParam.status == 1}')">财务放款</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_creditReport" onclick="showCreditReportTab('${actTaskParam.status == 1}')">信审报告</a>
				</li>

				<!--批量借款企业-->
				<c:if test="${flag == 100}">

					<li class="line" id="lineId"></li>
					<li class="" id="companyInfo">
						<a href="#tab_companyInfo" onclick="showCompanyInfoTab('${actTaskParam.status == 1}')">批量处理</a>
					</li>

				</c:if>

				<li class="line"></li>
				<li class="">
					<a href="#tab_get" onclick="showGetTab('${actTaskParam.status == 1}')">冠e通</a>
				</li>
			</ul>
		</div>
		<div class="tab-content">
			<div class="tab-pane" id="tab_faceSign"></div>
			<div class="tab-pane" id="tab_financeLoan"></div>
			<div class="tab-pane" id="tab_creditReport"></div>
			<div class="tab-pane" id="tab_get"></div>
			<div class="tab-pane" id="tab_companyInfo"></div>
		</div>
	</div>
</body>
</html>