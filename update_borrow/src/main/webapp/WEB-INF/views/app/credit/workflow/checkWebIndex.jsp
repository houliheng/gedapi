<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>申请录入</title>
<base target="_self">
<meta name="decorator" content="default" />
<script type="text/javascript">
		//初始化最初展示的Tab页面
		$(document).ready(function(){
			if(!(('${showStatus}')=='1')){
				$("#tab_stockWebCheck").hide();
				$("#stockWebCheck").hide();
				$("#stockLine").hide();
			}
			showLoanApply('${actTaskParam.status == 1}');
		});
		function hideGuarantorInfoTab(){
			$("#guarantorInfo").hide();
			$("#lineId").hide();
		}
		function showPartGuarantorInfoTab(){
			$("#guarantorInfo").show();
			$("#lineId").show();
		}
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
					<a href="#tab_loanApply" onclick="showLoanApply('${actTaskParam.status == 1}')">借款申请信息</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_custInfo" onclick="showCustInfoTab('${actTaskParam.status == 1}')">客户信息</a>
				</li>
				<li class="line" id="lineId"></li>
				<li class="" id="guarantorInfo">
					<a href="#tab_guarantorInfo" onclick="showGuarantorInfoTab('${actTaskParam.status == 1}')">担保信息</a>
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
					<a href="#tab_mortgageCarInfo" onclick="showMortgageCarInfoTab('${actTaskParam.status == 1}')">抵质押物信息</a>
				</li>
				<li class="line" id="stockLine"></li>
				<li class="" id="stockWebCheck">
					<a href="#tab_stockWebCheck" onclick="showStockWebCheck('${actTaskParam.status == 1}')">一次网查</a>
				</li>
				<!-- 待办时，才显示录入结论标签 -->
				<c:if test="${actTaskParam.status == 0 }">
					<li class="line"></li>
					<li class="">
						<a href="#tab_conclusion" onclick="showConclusionTab('${actTaskParam.status == 1}')">录入结论</a>
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
			<div class="tab-pane" id="tab_conclusion"></div>
			<div class="tab-pane" id="tab_companyInfo"></div>
		</div>
	</div>
</body>
</html>