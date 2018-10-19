<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分公司经理审核</title>
	<base target="_self">
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		//初始化最初展示的Tab页面
		$(document).ready(function(){
			if(!(('${showStatus}')=='1')){
				$("#tab_stockWebCheck").hide();
				$("#stockWebCheck").hide();
				$("#stockLine").hide();
				$(".stockInfo").hide();
			}
			showCreditViewBookTab('${actTaskParam.status == 1}');
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
			<!-- 	<li class="">
					<a href="#tab_information" onclick="showInformationTab(true)">综合信息</a>
				</li> -->
				<li class="line" id="stockLine"></li>
				<li class="" id="stockWebCheck">
					<a href="#tab_stockWebCheck" onclick="showStockWebCheck(true)">一次网查</a>
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
					<a href="#tab_creditViewBook" onclick="showCreditViewBookTab('${actTaskParam.status == 1}')">信审意见书</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_checkApprove" onclick="showCheckApproveTab('${actTaskParam.status == 1}')">批复信息</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_get" onclick="showGetTab('${actTaskParam.status == 1}')">冠e通</a>
				</li>
				<li class="line"></li>
				<li class="" id="getRevieved">
					<a href="#tab_revieved" onclick="showRevieved('${actTaskParam.status == 1}')">黑名单规则</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_fhRiskControl" onclick="showFhRiskControlTab('${actTaskParam.status == 1}')">法海风控</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_checkCoupleDoubtful" onclick="showCheckCoupleDoubtfulTab(true)">外访信息</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_checkFee" onclick="showCheckFeeTab(true)">外访费登记</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_checkWeb" onclick="showCheckWebTab(true)">网查</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_checkPhone" onclick="showCheckPhoneTab(true)">电话核查</a>
				</li>
				<li class="line stockInfo"></li>
				<li class="stockInfo"><!-- true不可编辑 -->
					<a href="#tab_valueStation" onclick="showValueStationTab(true)">股权尽调</a>
				</li>
				<c:if test="${showStatus == '1'}">
					<li class="line"></li>
					<li class="" id="tab_markNormLiId">
						<a href="#tab_markNormDiv" onclick="showMarkNormTab('true')">加减分项</a>
					</li>
				</c:if>
			</ul>
		</div>
		<div class="tab-content">
			<div class="tab-pane" id="tab_loanApply"></div>
			<div class="tab-pane" id="tab_custInfo"></div>
			<div class="tab-pane" id="tab_guarantorInfo"></div>
			<div class="tab-pane" id="tab_mortgageCarInfo"></div>
			<div class="tab-pane" id="tab_stockWebCheck"></div>
	<!-- 		<div class="tab-pane" id="tab_information"></div> -->
			<div class="tab-pane" id="tab_checkFace"></div>
			<div class="tab-pane" id="tab_creditAndLine"></div>
			<div class="tab-pane" id="tab_financeImport"></div>
			<div class="tab-pane" id="tab_creditReport"></div>
			<div class="tab-pane" id="tab_creditViewBook"></div>
			<div class="tab-pane" id="tab_checkApprove"></div>
			<div class="tab-pane" id="tab_get"></div>
			<div class="tab-pane" id="tab_revieved"></div>
			<div class="tab-pane" id="tab_fhRiskControl"></div>
			<div class="tab-pane" id="tab_checkWeb"></div>
			<div class="tab-pane" id="tab_checkPhone"></div>
			<div class="tab-pane" id="tab_checkCoupleDoubtful"></div>
			<div class="tab-pane" id="tab_checkFee"></div>
			<div class="tab-pane" id="tab_valueStation" class="stockInfo"></div>
			<div class="tab-pane" id="tab_markNormDiv"></div>
			<div class="tab-pane" id="tab_companyInfo"></div>
		</div>
	</div>
</body>
</html>