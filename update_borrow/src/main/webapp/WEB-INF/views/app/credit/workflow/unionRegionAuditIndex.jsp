<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>联合授信</title>
<base target="_self">
<meta name="decorator" content="default" />
<script type="text/javascript">
	//初始化最初展示的Tab页面
	$(document).ready(function() {
		showCheckApproveUnionTab('${actTaskParam.status == 1}');
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
				<!--批量借款企业-->
				<c:if test="${flag == 100}">

					<li class="line" id="lineId"></li>
					<li class="" id="companyInfo">
						<a href="#tab_companyInfo" onclick="showCompanyInfoTab('${actTaskParam.status == 1}')">批量处理</a>
					</li>

				</c:if>

				<li class="line"></li>
				<li class="">
					<a href="#tab_mortgageCarInfo" onclick="showMortgageCarEvaluateInfoTab(true)">抵质押物信息</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_information" onclick="showInformationTab('${actTaskParam.status == 1}')">综合信息</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_association" onclick="showAssociationTab('${actTaskParam.status == 1}')">关联匹配</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_checkFace" onclick="showCheckFaceTab(true)">面审信息</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_creditAndLine" onclick="showCreditAndLineTab(true)">征信及流水</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_financeImport" onclick="showFinanceImportTab(true)">财报导入</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_creditReport" onclick="showCreditReportTab('${actTaskParam.status == 1}')">信审报告</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_creditViewBook" onclick="showCreditViewBookTab(true)">信审意见书</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_checkApprove_union" onclick="showCheckApproveUnionTab('${actTaskParam.status == 1}')">批复信息</a>
				</li>
<!-- 				<li class="line"></li> -->
<!-- 				<li class=""> -->
<!-- 					<a href="#tab_get" onclick="showGetTab('${actTaskParam.status == 1}')">冠e通</a> -->
<!-- 				</li> -->
			</ul>
		</div>
		<div class="tab-content">
			<div class="tab-pane" id="tab_loanApply"></div>
			<div class="tab-pane" id="tab_custInfo"></div>
			<div class="tab-pane" id="tab_guarantorInfo"></div>
			<div class="tab-pane" id="tab_mortgageCarInfo"></div>
			<div class="tab-pane" id="tab_information"></div>
			<div class="tab-pane" id="tab_association"></div>
			<div class="tab-pane" id="tab_checkFace"></div>
			<div class="tab-pane" id="tab_creditAndLine"></div>
			<div class="tab-pane" id="tab_financeImport"></div>
			<div class="tab-pane" id="tab_dss"></div>
			<div class="tab-pane" id="tab_creditReport"></div>
			<div class="tab-pane" id="tab_creditViewBook"></div>
			<div class="tab-pane" id="tab_checkApprove_union"></div>
			<div class="tab-pane" id="tab_companyInfo"></div>
		</div>
	</div>
</body>
</html>