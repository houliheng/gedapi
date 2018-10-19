<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>区域风控专员审核</title>
<base target="_self">
<meta name="decorator" content="default" />
<script type="text/javascript">
	//初始化最初展示的Tab页面
	$(document).ready(function() {
		showValueStationTab("${isDone == '1'}","${stockInfoId}");//${isDone == 1}
	});
</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/app/credit/workflow/include/workflowScript.jsp"%>
	<div class="wrapper">
		<c:if test="${PLFlag ne 'disappear'}">
			<%@ include file="/WEB-INF/views/app/credit/workflow/include/workflowButtons.jsp"%>
		</c:if>
		<sys:message content="${message}" />
		<a id="beforeStockInfoSkipId" target="_parent" ></a>
		<div style="margin-top: 5px;">
			<ul class="nav nav-tabs" id="mainTabs">
				<li class="">
					<a href="#tab_loanApply" onclick="showLoanApply('true')">借款申请信息</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_custInfo" onclick="showCustInfoTab('true')">客户信息</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_guarantorInfo" onclick="showGuarantorInfoTab('true')">担保信息</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_mortgageCarInfo" onclick="showMortgageCarEvaluateInfoTab('true')">抵质押物信息</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_association" onclick="showAssociationTab('true')">关联匹配</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_checkFace" onclick="showCheckFaceTab('true')">面审信息</a>
				</li>
				<li class="line" id="stockLine"></li>
				<li class="" id="stockWebCheck">
					<a href="#tab_stockWebCheck" onclick="showStockWebCheck(true)">一次网查</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_creditAndLine" onclick="showCreditAndLineTab('true')">征信及流水</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_financeImport" onclick="showFinanceImportTab('true')">财报导入</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_creditReport" onclick="showCreditReportTab('true')">信审报告</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_creditViewBook" onclick="showCreditViewBookTab('true','1')">信审意见书</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_checkApprove" onclick="showCheckApproveTab('true')">批复信息</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_get" onclick="showGetTab('true')">冠e通</a>
				</li>
				<li class="line"></li>
				<li class="" id="getRevieved">
					<a href="#tab_revieved" onclick="showRevieved('true')">黑名单规则</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_fhRiskControl" onclick="showFhRiskControlTab('true')">法海风控</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_checkCoupleDoubtful" onclick="showCheckCoupleDoubtfulTab('true')">外访信息</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_checkFee" onclick="showCheckFeeTab('true')">外访费登记</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_checkWeb" onclick="showCheckWebTab('true')">网查</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_checkPhone" onclick="showCheckPhoneTab('true')">电话核查</a>
				</li>
				<li class="line"></li>
				<li class=""><!-- true不可编辑 -->
					<a href="#tab_valueStation" onclick="showValueStationTab('${isDone == '1'}')">股权尽调</a>
				</li>
				<li class="line"></li>
				<li class="" id="tab_markNormLiId">
					<a href="#tab_markNormDiv" onclick="showMarkNormTab('true')">加减分项</a>
				</li>
			</ul>
		</div>
		<div class="tab-content">
			<div class="tab-pane" id="tab_loanApply"></div>
			<div class="tab-pane" id="tab_custInfo"></div>
			<div class="tab-pane" id="tab_guarantorInfo"></div>
			<div class="tab-pane" id="tab_mortgageCarInfo"></div>
			<div class="tab-pane" id="tab_association"></div>
			<div class="tab-pane" id="tab_checkFace"></div>
			<div class="tab-pane" id="tab_stockWebCheck"></div>
			<div class="tab-pane" id="tab_creditAndLine"></div>
			<div class="tab-pane" id="tab_financeImport"></div>
			<div class="tab-pane" id="tab_creditReport"></div>
			<div class="tab-pane" id="tab_creditViewBook"></div>
			<div class="tab-pane" id="tab_checkApprove"></div>
			<div class="tab-pane" id="tab_get"></div>
			<div class="tab-pane" id="tab_revieved"></div>
			<div class="tab-pane" id="tab_fhRiskControl"></div>
			<div class="tab_pane" id="tab_ViewImage"></div>
			<div class="tab-pane" id="tab_checkWeb"></div>
			<div class="tab-pane" id="tab_checkPhone"></div>
			<div class="tab-pane" id="tab_checkCoupleDoubtful"></div>
			<div class="tab-pane" id="tab_checkFee"></div>
			<div class="tab-pane" id="tab_valueStation"></div>
			<div class="tab-pane" id="tab_markNormDiv"></div>
		</div>
	</div>
</body>
</html>