<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>两人外访</title>
	<base target="_self">
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		//初始化最初展示的Tab页面
		$(document).ready(function(){
			if(!(('${showStatus}')=='1')){
				$("#tab_stockWebCheck").hide();
				$("#stockWebCheck").hide();
				$("#stockLine").hide();
			}
			showCheckCoupleDoubtfulTab('${actTaskParam.status == 1}');
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
					<a href="#tab_mortgageCarInfo" onclick="showMortgageCarInfoTab(true)">抵质押物信息</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_checkDoubtful" onclick="showCheckDoubtfulTab(true)">借前外访信息</a>
				</li>
				<li class="line" id="stockLine"></li>
				<li class="" id="stockWebCheck">
					<a href="#tab_stockWebCheck" onclick="showStockWebCheck(true)">一次网查</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_checkCoupleDoubtful" onclick="showCheckCoupleDoubtfulTab('${actTaskParam.status == 1}')">外访信息</a>
				</li>
			</ul>
		</div>
		<div class="tab-content">
			<div class="tab-pane" id="tab_loanApply"></div>
			<div class="tab-pane" id="tab_custInfo"></div>
			<div class="tab-pane" id="tab_guarantorInfo"></div>
			<div class="tab-pane" id="tab_mortgageCarInfo"></div>
			<div class="tab-pane" id="tab_checkDoubtful"></div>
			<div class="tab-pane" id="tab_stockWebCheck"></div>
			<div class="tab-pane" id="tab_checkCoupleDoubtful"></div>
			<div class="tab-pane" id="tab_companyInfo"></div>
		</div>
	</div>
</body>
</html>