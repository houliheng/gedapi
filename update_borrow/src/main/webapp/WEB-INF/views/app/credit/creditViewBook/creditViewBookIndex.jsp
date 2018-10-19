<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<style type="text/css" media="print">
.noprint {
	display: none !important;
}
</style>
<title>信审意见书</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {  
		var productCategoryKey = '${productCategoryKey}';
		if(productCategoryKey==null||productCategoryKey==''){
			$(".ZGCategory").hide();
		}else{
			$(".ZGCategory").show();
		}
		if("${actTaskParam.taskDefKey}" == "utask_fgsfksh"){
			if (!checkIsNull('${checkApprove.id}')) {
				parent.showGetInfoTab();
			}else{
				parent.hideGetInfoTab();
			}
		}
		$("#creditViewBookForm").validate({
		submitHandler : saveForm,
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});
		//外访费收取情况
		$.loadDiv("checkFeeDiv", "${ctx }/credit/checkFee/creditViewBook", {
			applyNo : "${actTaskParam.applyNo}"
		}, "post");
		//资产清单列表
		$.loadDiv("assetDiv", "${ctx }/credit/creditViewBook/creditAssets/list", {
			applyNo : "${actTaskParam.applyNo}"
		}, "post");
		
		adjustTextareaLength("creditAnalysis.categoryDesc","preCategoryDesc");
		
		adjustTextareaLength("creditAnalysisExtend.turnoverProfit","preTurnoverProfit");
		adjustTextareaLength("creditAnalysisExtend.tfBasis","preTfBasis");
		adjustTextareaLength("creditAnalysisExtend.staffNumberDesc","preStaffNumberDesc");
		adjustTextareaLength("creditAnalysisExtend.sndBasis","preSndBasis");
		adjustTextareaLength("creditAnalysisExtend.saleProfitDesc","preSaleProfitDesc");
		adjustTextareaLength("creditAnalysisExtend.spdBasis","preSpdBasis");
		adjustTextareaLength("creditAnalysisExtend.companyDebt","preCompanyDebt");
		adjustTextareaLength("creditAnalysisExtend.cdBasis","preCdBasis");
		adjustTextareaLength("creditAnalysisMostExtends.coreValue","preCoreValue");
		adjustTextareaLength("creditAnalysisMostExtends.coreAdvantage","preCoreAdvantage");
		adjustTextareaLength("creditAnalysisMostExtends.coreGuarantee","preCoreGuarantee");
		adjustTextareaLength("creditAnalysisMostExtends.coreMeasures","preCoreMeasures");
		
		
		adjustTextareaLength("creditAnalysisMostExtend.applicationDetails","preApplicationDetails");
		adjustTextareaLength("creditAnalysisMostExtend.verifyMethod","preVerifyMethod");
		adjustTextareaLength("creditAnalysisMostExtend.verifyBasis","preVerifyBasis");
		adjustTextareaLength("creditAnalysisMostExtend.repaymentSourceBasis","preRepaymentSourceBasis");
		adjustTextareaLength("creditAnalysisMostExtend.secondRepaymentSource","preSecondRepaymentSource");
		adjustTextareaLength("creditAnalysisMostExtend.otherRepaymentSource","preOtherRepaymentSource");
		adjustTextareaLength("creditAnalysisMostExtends.policyRiskAnalysis","prePolicyRiskAnalysis");
		adjustTextareaLength("creditAnalysisMostExtends.operateRiskAnalysis","preOperateRiskAnalysis");
		adjustTextareaLength("creditAnalysisMostExtends.creditRiskAnalysis","preCreditRiskAnalysis");
		adjustTextareaLength("creditAnalysisMostExtends.unexpectedRiskAnalysis","preUnexpectedRiskAnalysis");
		adjustTextareaLength("creditAnalysisMostExtend.guaranteeDetail","preGuaranteeDetail");
		adjustTextareaLength("creditAnalysisMostExtend.guaranteeCorporation","preGuaranteeCorporation");
		
		
		adjustTextareaLength("suggestionBranch","preSuggestionBranch");
		adjustTextareaLength("suggestionArea","preSuggestionArea");
		adjustTextareaLength("suggestionLargeArea","preSuggestionLargeArea");
		adjustTextareaLength("suggestionHead","preSuggestionHead");
	});
 
	
	function saveForm() {
		loading();
		top.$.jBox.tip.mess = null;
		var company = $("#creditViewBookForm").serializeJson();
		$.post("${ctx}/credit/creditViewBook/save", company, function(data) {
			if (data) {
				closeTip();
				if (data.status == 1) {
					alertx(data.message, function() {
					});
				} else {
					alertx(data.message);
				}

			}
		});
	}

	//新增
	function add(url, title) {
		var width = $(top.document).width() - 500;
		width = Math.max(width, 1000);
		var height = $(top.document).height() - 150;
		/* height = Math.max(height,1000);  */
		openJBox('', url, title, width, height);
	}

	//修改
	function edit(urlSingle, title) {
		var width = $(top.document).width() - 300;
		width = Math.max(width, 1000);
		var $checkLine = $("input[name='assetsType']:checked");
		var $len = $checkLine.length;
		if ($len != 1) {
			alertx("请选择一条数据");
		} else {
			var url = urlSingle + "?id=" + $checkLine.val();
			openJBox('', url, title, width, $(top.document).height() - 200);
		}
	}
	//删除
	function del(url, divName, divUrl) {
		var $checkLine = $("input[name='assetsType']:checked");
		if (0 == $checkLine.length) {
			alertx("请选择需要删除的数据！");
		} else {
			confirmx("是否删除?", function() {
				delOper(url, divName, divUrl);
			});
		}
	}

	function delOper(url, divName, divUrl) {
		var $checkLine = $("input[name='assetsType']:checked");
		if (null != $checkLine && $checkLine.length > 0) {
			var ids = "";
			$checkLine.each(function(v) {
				ids += (this.value + ",");
			});
			$.post(url, {
				"ids" : ids
			}, function(data) {
				if ("success" == data) {
					alertx("删除成功！");
					$.loadDiv("assetDiv", "${ctx }/credit/creditViewBook/creditAssets", {
						applyNo : "${actTaskParam.applyNo}"
					}, "post");
				}
			});
		}
	}

	//查看详情
	function details(url, message) {
		openJBox('', url, message, 1000, 500);
	}
	
	function assessPostSelectFun(sel){
		if(sel.value == '1'){
			window.parent.selectStockPerson(sel);
		}else if(sel.value == '2'){
			var isOk;
			var urlsuffix = "?applyNo=${actTaskParam.applyNo}&taskId=${actTaskParam.taskId}&headUrl=${actTaskParam.headUrl}&taskDefKey=${actTaskParam.taskDefKey}&procDefId=${actTaskParam.procDefId}&status=${actTaskParam.status}&procInstId=${actTaskParam.procInstId}";
			confirmx('无本层级股权尽调人员，将由风控人员股权尽调。', function(val){
				isOk = "1";
				$.ajax({
					url : "${ctx}/credit/stockTaskDistribute/changeup" + urlsuffix,
					type : "POST",
					success : function(data) {
						alertx(data.message);
						sel.disabled = "disabled";
					}
				});
			},function(){
				if(isOk != "1"){
					$("#assessPostSelectElementId").select2("val", "0");
				}
			});
		}
	}
</script>
</head>
<body>
	<!-- 申请信息 -->
	<div id="applyInfo">
		<%@ include file="/WEB-INF/views/app/credit/creditViewBook/applyInfoForCredit/applyInfoForCreditForm.jsp"%>
	</div>
	<%-- <!-- 综合意见 -->
	<div style="border:1px solid #DDDDDD">
		<div id="ComprehensiveOpinionDiv" style="margin-left: 2px;margin-right: 2px">
			<%@ include file="/WEB-INF/views/app/credit/creditViewBook/compositeOpinion/compositeOpinionForm.jsp"%>
			<!-- 外访费收取情况 -->
		</div>
		<div id="checkFeeDiv" style="margin-left: 2px;margin-right: 2px"></div>
	</div> --%>
	<!-- 资产清单列表 -->
	<div id="assetDiv"></div>
	<div class="searchInfo">
		
		<form:form id="creditViewBookForm" modelAttribute="creditViewBook" action="${ctx}/credit/creditViewBook/save" method="post" class="form-horizontal">
			<form:hidden path="id" />
			<%@ include file="/WEB-INF/views/app/credit/workflow/include/actTaskParams.jsp"%>
			<sys:message content="${otherInfoMessage}" />
			<div style="border:1px solid #DDDDDD">
			<!-- 综合意见 -->
				<div>
					<div id="ComprehensiveOpinionDiv" style="margin-left: 2px;margin-right: 2px;margin-top: 2px">
						<%@ include file="/WEB-INF/views/app/credit/creditViewBook/compositeOpinion/compositeOpinionForm.jsp"%>
						<!-- 外访费收取情况 -->
					</div>
					<div id="checkFeeDiv" style="margin-left: 2px;margin-right: 2px"></div>
				</div>
			
			
			<h3 class="searchTitle">其他信息</h3>
			<div class="searchCon" style="margin-left: 2px;margin-right: 2px">
			
			
			
			
			
			
			
				<table id="otherInfoTable" class="fromTable filter">
					<tr>
						<td class="ft_label">借款人夫妻资产占借款金额比例：</td>
						<td class="ft_content">
							<form:select path="creditOtherInfo.coupleAssetsOfLoan" id="coupleAssetsOfLoan" class="input-medium required">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('COUPLE_ASSETS_OF_LOAN')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
						</td>
						<td class="ft_label">担保人资产占借款金额比例：</td>
						<td class="ft_content">
							<form:select path="creditOtherInfo.guaranteeAssetsOfLoan" id="guaranteeAssetsOfLoan" class="input-medium required">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('GUARANTEE_ASSETS_OF_LOAN')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
						</td>
						<td class="ft_label">最近一年股权变更：</td>
						<td class="ft_content">
							<form:select path="creditOtherInfo.lastYearStockChange" id="lastYearStockChange" class="input-medium required">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('LAST_YEAR_STOCK_CHANGE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">缴税情况：</td>
						<td class="ft_content">
							<form:select path="creditOtherInfo.payTaxStatus" id="payTaxStatus" class="input-medium required">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('PAY_TAX_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
						</td>
						<td class="ft_label">物业费缴纳情况：</td>
						<td class="ft_content">
							<form:select path="creditOtherInfo.managementFeeStatus" id="managementFeeStatus" class="input-medium required">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('MANAGEMENT_FEE_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
						</td>
						<td class="ft_label">电费缴纳情况：</td>
						<td class="ft_content">
							<form:select path="creditOtherInfo.powerFeeStatus" id="powerFeeStatus" class="input-medium required">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('POWER_FEE_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">借款企业中借款人占股(%)：</td>
						<td class="ft_content">
							<form:input path="creditOtherInfo.mainManOfStock" id="mainManOfStock" htmlEscape="false" onblur="this.value=gdpMoney(this.value);" onkeyup="gdpMax(this)" maxlength="6" class="input-medium required" />
							<span class="help-inline">
								<font color="red">*</font>
							</span>
						</td>
						<td class="ft_label">申请人在用款企业的出资年限：</td>
						<td class="ft_content">
							<form:input path="creditOtherInfo.capitalContributionPeriod" onblur="this.value=capMoney(this.value);" onkeyup="gdpMax(this)" maxlength="4" id="capitalContributionPeriod" htmlEscape="false" class="input-medium number required" />
							<span class="help-inline">
								<font color="red">*</font>
							</span>
						</td>
						<td class="ft_label">借款到期日期：</td>
						<td class="ft_content">
							<input id="landEndDate" name="creditOtherInfo.landEndDate" type="text" maxlength="20" class="input-medium Wdate required" value="<fmt:formatDate value='${creditViewBook.creditOtherInfo.landEndDate}' pattern='yyyy-MM'/>" onclick="WdatePicker({onpicked:dateWhite(this),minDate:new Date(),dateFmt:'yyyy-MM'});" />
							<span class="help-inline">
								<font color="red">*</font>
							</span>
						</td>
					</tr>
				</table>
			</div>
			<div style="margin-left: 2px;margin-right: 2px;">
					<table class="fromTable filter">
						<tr>
							<td class="ft_label" style="width: 10%;"></td>
							<td class="ft_content" style="width: 90%;">
								<pre class="textareaWidth pre-style"  id="preCategoryDesc"></pre>
								
								
								<pre class="textareaWidth pre-style"  id="preTurnoverProfit"></pre>
								<pre class="textareaWidth pre-style"  id="preTfBasis"></pre>
								<pre class="textareaWidth pre-style"  id="preStaffNumberDesc"></pre>
								<pre class="textareaWidth pre-style"  id="preSndBasis"></pre>
								<pre class="textareaWidth pre-style"  id="preSaleProfitDesc"></pre>
								<pre class="textareaWidth pre-style"  id="preSpdBasis"></pre>
								<pre class="textareaWidth pre-style"  id="preCompanyDebt"></pre>
								<pre class="textareaWidth pre-style"  id="preCdBasis"></pre>
								<pre class="textareaWidth pre-style"  id="preCoreValue"></pre>
								<pre class="textareaWidth pre-style"  id="preCoreAdvantage"></pre>
								<pre class="textareaWidth pre-style"  id="preCoreGuarantee"></pre>
								<pre class="textareaWidth pre-style"  id="preCoreMeasures"></pre>
								
								
								<pre class="textareaWidth pre-style"  id="preApplicationDetails"></pre>
								<pre class="textareaWidth pre-style"  id="preVerifyMethod"></pre>
								<pre class="textareaWidth pre-style"  id="preVerifyBasis"></pre>
								<pre class="textareaWidth pre-style"  id="preRepaymentSourceBasis"></pre>
								<pre class="textareaWidth pre-style"  id="preSecondRepaymentSource"></pre>
								<pre class="textareaWidth pre-style"  id="preOtherRepaymentSource"></pre>
								<pre class="textareaWidth pre-style"  id="prePolicyRiskAnalysis"></pre>
								<pre class="textareaWidth pre-style"  id="preOperateRiskAnalysis"></pre>
								<pre class="textareaWidth pre-style"  id="preCreditRiskAnalysis"></pre>
								<pre class="textareaWidth pre-style"  id="preUnexpectedRiskAnalysis"></pre>
								<pre class="textareaWidth pre-style"  id="preGuaranteeDetail"></pre>
								<pre class="textareaWidth pre-style"  id="preGuaranteeCorporation"></pre>
								
								<pre class="textareaWidth pre-style"  id="preSuggestionBranch"></pre>
								<pre class="textareaWidth pre-style"  id="preSuggestionArea"></pre>
								<pre class="textareaWidth pre-style"  id="preSuggestionLargeArea"></pre>
							    <pre class="textareaWidth pre-style"  id="preSuggestionHead"></pre>
							</td>
						</tr>
						<tr>
							<td class="ft_label" style="width: 10%;">行业状况分析：</td>
							<td class="ft_content" style="width: 90%;">
								<form:textarea id="categoryDesc" path="creditAnalysis.categoryDesc"  htmlEscape="false" rows="4" maxlength="1000" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('creditAnalysis.categoryDesc','preCategoryDesc'),this.value=this.value.replace(/[, ]/g,'')"/>
								<font color="red">*</font>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<h3 class="searchTitle">借款企业经营情况</h3>
			<div style="border:1px solid #DDDDDD">	
			<div class="searchCon">
				<div style="margin-left: 2px;margin-right: 2px">
					<table class="fromTable filter">	
						<tr>
							<td class="ft_label" style="width: 8%;">近三年营业额及利润：</td>
							<td class="ft_content" style="width: 42%;">
								<form:textarea id="turnoverProfit" path="creditAnalysisExtend.turnoverProfit" style="overflow-y:visible"  htmlEscape="false" rows="2" minlength="30" maxlength="500" class="input-xxlarge textarea-style required" onkeyup="adjustTextareaLength('creditAnalysisExtend.turnoverProfit','preTurnoverProfit'),this.value=this.value.replace(/[, ]/g,'')"/>
								<font style="color: red">*最少输入30字，最多可输入500字</font>
							</td>
							<td class="ft_label" style="width: 8%;">核实方法及依据：</td>
							<td class="ft_content" style="width: 42%;">
								<form:textarea id="tfBasis" path="creditAnalysisExtend.tfBasis" style="overflow-y:visible" htmlEscape="false" rows="2" minlength="30" maxlength="500" class="input-xxlarge textarea-style required" onkeyup="adjustTextareaLength('creditAnalysisExtend.tfBasis','preTfBasis'),this.value=this.value.replace(/[, ]/g,'')"/>
								<font style="color: red">*最少输入30字，最多可输入500字</font>
							</td>
						</tr>
						<tr>
							<td class="ft_label" style="width: 8%;">员工数量及高管情况：</td>
							<td class="ft_content" style="width: 42%;">
								<form:textarea id="staffNumberDesc" path="creditAnalysisExtend.staffNumberDesc" style="overflow-y:visible" htmlEscape="false" rows="2" minlength="30" maxlength="500" class="input-xxlarge textarea-style required" onkeyup="adjustTextareaLength('creditAnalysisExtend.staffNumberDesc','preStaffNumberDesc'),this.value=this.value.replace(/[, ]/g,'')"/>
								<font style="color: red">*最少输入30字，最多可输入500字</font>
							</td>
							<td class="ft_label" style="width: 8%;">核实方法及依据：</td>
							<td class="ft_content" style="width: 42%;">
								<form:textarea id="sndBasis" path="creditAnalysisExtend.sndBasis" style="overflow-y:visible" htmlEscape="false" rows="2" minlength="30" maxlength="500" class="input-xxlarge textarea-style required" onkeyup="adjustTextareaLength('creditAnalysisExtend.sndBasis','preSndBasis'),this.value=this.value.replace(/[, ]/g,'')"/>
								<font style="color: red">*最少输入30字，最多可输入500字</font>
							</td>
						</tr>
						<tr>
							<td class="ft_label" style="width: 8%;">销售收入构成及稳定性：</td>
							<td class="ft_content" style="width: 42%;">
								<form:textarea id="saleProfitDesc" path="creditAnalysisExtend.saleProfitDesc" style="overflow-y:visible" htmlEscape="false" rows="2" minlength="30" maxlength="500" class="input-xxlarge textarea-style required" onkeyup="adjustTextareaLength('creditAnalysisExtend.saleProfitDesc','preSaleProfitDesc'),this.value=this.value.replace(/[, ]/g,'')"/>
								<font style="color: red">*最少输入30字，最多可输入500字</font>
							</td>
							<td class="ft_label" style="width: 8%;">核实方法及依据：</td>
							<td class="ft_content" style="width: 42%;">
								<form:textarea id="spdBasis" path="creditAnalysisExtend.spdBasis" style="overflow-y:visible" htmlEscape="false" rows="2" minlength="30" maxlength="500" class="input-xxlarge textarea-style required" onkeyup="adjustTextareaLength('creditAnalysisExtend.spdBasis','preSpdBasis'),this.value=this.value.replace(/[, ]/g,'')"/>
								<font style="color: red">*最少输入30字，最多可输入500字</font>
							</td>
						</tr>
						<tr>
							<td class="ft_label" style="width: 8%;">企业负债情况及分析：</td>
							<td class="ft_content" style="width: 42%;">
								<form:textarea id="companyDebt" path="creditAnalysisExtend.companyDebt" style="overflow-y:visible" htmlEscape="false" rows="2" minlength="30" maxlength="500" class="input-xxlarge textarea-style required" onkeyup="adjustTextareaLength('creditAnalysisExtend.companyDebt','preCompanyDebt'),this.value=this.value.replace(/[, ]/g,'')"/>
								<font style="color: red">*最少输入30字，最多可输入500字</font>
							</td>
							<td class="ft_label" style="width: 8%;">核实方法及依据：</td>
							<td class="ft_content" style="width: 42%;">
								<form:textarea id="cdBasis" path="creditAnalysisExtend.cdBasis" style="overflow-y:visible" htmlEscape="false" rows="2" minlength="30" maxlength="500" class="input-xxlarge textarea-style required" onkeyup="adjustTextareaLength('creditAnalysisExtend.cdBasis','preCdBasis'),this.value=this.value.replace(/[, ]/g,'')"/>
								<font style="color: red">*最少输入30字，最多可输入500字</font>
							</td>
						</tr>
					</table>
				</div>	
				<div style="margin-left: 2px;margin-right: 2px">	
					<h3 class="searchTitle">核心分析</h3>	
					<table class="fromTable filter">
						<tr>
							<td class="ft_label" style="width: 15%;">企业核心价值分析：</td>
							<td class="ft_content" style="width: 85%;">
								<form:textarea id="coreValue" path="creditAnalysisMostExtends.coreValue" style="overflow-y:visible" htmlEscape="false" rows="2" minlength="30" maxlength="500" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('creditAnalysisMostExtends.coreValue','preCoreValue'),this.value=this.value.replace(/[, ]/g,'')"/>
								<br>
								<font style="color: red">*最少输入30字，最多可输入500字</font>
							</td>
						</tr>
						<tr>
							<td class="ft_label" style="width: 15%;">企业核心优势：</td>
							<td class="ft_content" style="width: 85%;">
								<form:textarea id="coreAdvantage" path="creditAnalysisMostExtends.coreAdvantage" style="overflow-y:visible" htmlEscape="false" rows="2" minlength="30" maxlength="500" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('creditAnalysisMostExtends.coreAdvantage','preCoreAdvantage'),this.value=this.value.replace(/[, ]/g,'')"/>
								<br>
								<font style="color: red">*最少输入30字，最多可输入500字</font>
							</td>
						</tr>
						<tr>
							<td class="ft_label" style="width: 15%;">核心担保人介绍：</td>
							<td class="ft_content" style="width: 85%;">
								<form:textarea id="coreGuarantee" path="creditAnalysisMostExtends.coreGuarantee" style="overflow-y:visible" htmlEscape="false" rows="2" minlength="30" maxlength="500" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('creditAnalysisMostExtends.coreGuarantee','preCoreGuarantee'),this.value=this.value.replace(/[, ]/g,'')"/>
								<br>
								<font style="color: red">*最少输入30字，最多可输入500字</font>
							</td>
						</tr>
						<tr>
							<td class="ft_label" style="width: 15%;">核心担保措施：</td>
							<td class="ft_content" style="width: 85%;">
								<form:textarea id="coreMeasures" path="creditAnalysisMostExtends.coreMeasures" style="overflow-y:visible" htmlEscape="false" rows="2" minlength="30" maxlength="500" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('creditAnalysisMostExtends.coreMeasures','preCoreMeasures'),this.value=this.value.replace(/[, ]/g,'')"/>
								<br>
								<font style="color: red">*最少输入30字，最多可输入500字</font>
							</td>
						</tr>
					</table>
				</div>
				<div style="margin-left: 2px;margin-right: 2px">
					<h3  class="searchTitle">借款用途</h3>
					<table class="fromTable filter">
						<tr>
							<td class="ft_label" style="width: 15%;">用途明细：</td>
							<td class="ft_content" style="width: 85%;">
								<form:textarea id="applicationDetails" path="creditAnalysisMostExtend.applicationDetails" style="overflow-y:visible" htmlEscape="false" rows="2" minlength="30" maxlength="500" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('creditAnalysisMostExtend.applicationDetails','preApplicationDetails'),this.value=this.value.replace(/[, ]/g,'')"/>
								<br>
								<font style="color: red">*最少输入30字，最多可输入500字</font>
							</td>
						</tr>
						<tr>
							<td class="ft_label" style="width: 15%;">核实用途采用的方式方法：</td>
							<td class="ft_content" style="width: 85%;">
								<form:textarea id="verifyMethod" path="creditAnalysisMostExtend.verifyMethod" style="overflow-y:visible" htmlEscape="false" rows="2" minlength="30" maxlength="500" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('creditAnalysisMostExtend.verifyMethod','preVerifyMethod'),this.value=this.value.replace(/[, ]/g,'')"/>
								<br>
								<font style="color: red">*最少输入30字，最多可输入500字</font>
							</td>
						</tr>
						<tr>
							<td class="ft_label" style="width: 15%;">核实用途采用的依据：</td>
							<td class="ft_content" style="width: 85%;">
								<form:textarea id="verifyBasis" path="creditAnalysisMostExtend.verifyBasis" style="overflow-y:visible" htmlEscape="false" rows="2" minlength="30" maxlength="500" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('creditAnalysisMostExtend.verifyBasis','preVerifyBasis'),this.value=this.value.replace(/[, ]/g,'')"/>
								<br>
								<font style="color: red">*最少输入30字，最多可输入500字</font>
							</td>
						</tr>
					</table>
				</div> 
				<div style="margin-left: 2px;margin-right: 2px">
					<h3 class="searchTitle">还款来源</h3>
					<table class="fromTable filter">
						<tr>
							<td class="ft_label" style="width: 15%;">经营性还款来源分析及依据：</td>
							<td class="ft_content" style="width: 85%;">
								<form:textarea id="repaymentSourceBasis" path="creditAnalysisMostExtend.repaymentSourceBasis" style="overflow-y:visible" htmlEscape="false" rows="2" minlength="30" maxlength="500" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('creditAnalysisMostExtend.repaymentSourceBasis','preRepaymentSourceBasis'),this.value=this.value.replace(/[, ]/g,'')"/>
								<br>
								<font style="color: red">*最少输入30字，最多可输入500字</font>
							</td>
						</tr>
						<tr>
							<td class="ft_label" style="width: 15%;">第二还款来源分析及依据：</td>
							<td class="ft_content" style="width: 85%;">
								<form:textarea id="secondRepaymentSource" path="creditAnalysisMostExtend.secondRepaymentSource" style="overflow-y:visible" htmlEscape="false" rows="2" minlength="30" maxlength="500" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('creditAnalysisMostExtend.secondRepaymentSource','preSecondRepaymentSource'),this.value=this.value.replace(/[, ]/g,'')"/>
								<br>
								<font style="color: red">*最少输入30字，最多可输入500字</font>
							</td>
						</tr>
						<tr>
							<td class="ft_label" style="width: 15%;">其他还款来源：</td>
							<td class="ft_content" style="width: 85%;">
								<form:textarea id="otherRepaymentSource" path="creditAnalysisMostExtend.otherRepaymentSource" style="overflow-y:visible" htmlEscape="false" rows="2"  maxlength="200" class="textareaWidth textarea-style" onkeyup="adjustTextareaLength('creditAnalysisMostExtend.otherRepaymentSource','preOtherRepaymentSource'),this.value=this.value.replace(/[, ]/g,'')"/>
							</td>
						</tr>
					</table>
				</div>	
				<div style="margin-left: 2px;margin-right: 2px">
					<h3 class="searchTitle">借款风险</h3>
					<table class="fromTable filter">
						<tr>
							<td class="ft_label" style="width: 15%;">政策性风险分析：</td>
							<td class="ft_content" style="width: 85%;">
								<form:textarea id="policyRiskAnalysis" path="creditAnalysisMostExtends.policyRiskAnalysis" style="overflow-y:visible" htmlEscape="false" rows="2" minlength="30" maxlength="500" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('creditAnalysisMostExtends.policyRiskAnalysis','prePolicyRiskAnalysis'),this.value=this.value.replace(/[, ]/g,'')"/>
								<br>
								<font style="color: red">*最少输入30字，最多可输入500字</font>
							</td>
						</tr>
						<tr>
							<td class="ft_label" style="width: 15%;">经营性风险分析：</td>
							<td class="ft_content" style="width: 85%;">
								<form:textarea id="operateRiskAnalysis" path="creditAnalysisMostExtends.operateRiskAnalysis" style="overflow-y:visible"  htmlEscape="false" rows="2" minlength="30" maxlength="500" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('creditAnalysisMostExtends.operateRiskAnalysis','preOperateRiskAnalysis'),this.value=this.value.replace(/[, ]/g,'')"/>
								<br>
								<font style="color: red">*最少输入30字，最多可输入500字</font>
							</td>
						</tr>
						<tr>
							<td class="ft_label" style="width: 15%;">信用风险分析：</td>
							<td class="ft_content" style="width: 85%;">
								<form:textarea id="creditRiskAnalysis" path="creditAnalysisMostExtends.creditRiskAnalysis" style="overflow-y:visible" htmlEscape="false" rows="2" minlength="30" maxlength="500" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('creditAnalysisMostExtends.creditRiskAnalysis','preCreditRiskAnalysis'),this.value=this.value.replace(/[, ]/g,'')"/>
								<br>
								<font style="color: red">*最少输入30字，最多可输入500字</font>
							</td>
						</tr>
						<tr>
							<td class="ft_label" style="width: 15%;">突发事件等风险分析：</td>
							<td class="ft_content" style="width: 85%;">
								<form:textarea id="unexpectedRiskAnalysis" path="creditAnalysisMostExtends.unexpectedRiskAnalysis" style="overflow-y:visible"  htmlEscape="false" rows="2" minlength="30" maxlength="500" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('creditAnalysisMostExtends.unexpectedRiskAnalysis','preUnexpectedRiskAnalysis'),this.value=this.value.replace(/[, ]/g,'')"/>
								<br>
								<font style="color: red">*最少输入30字，最多可输入500字</font>
							</td>
						</tr>
					</table>
				</div>
				<div style="margin-left: 2px;margin-right: 2px;margin-bottom: 2px">
					<h3  class="searchTitle">担保情况</h3>
					<table class="fromTable filter">
						<tr>
							<td class="ft_label" style="width: 15%;">担保人情况简述：</td>
							<td class="ft_content" style="width: 85%;">
								<form:textarea id="guaranteeDetail" path="creditAnalysisMostExtend.guaranteeDetail" style="overflow-y:visible" htmlEscape="false" rows="2" minlength="30" maxlength="800" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('creditAnalysisMostExtend.guaranteeDetail','preGuaranteeDetail'),this.value=this.value.replace(/[, ]/g,'')"/>
								<br>
								<font style="color: red">*最少输入30字，最多可输入800字</font>
							</td>
						</tr>
						
						<tr>
							<td class="ft_label" style="width: 15%;">担保企业情况简述：</td>
							<td class="ft_content" style="width: 85%;">
								<form:textarea id="guaranteeCorporation" path="creditAnalysisMostExtend.guaranteeCorporation" style="overflow-y:visible" htmlEscape="false" rows="2" minlength="30" maxlength="800" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('creditAnalysisMostExtend.guaranteeCorporation','preGuaranteeCorporation'),this.value=this.value.replace(/[, ]/g,'')"/>
								<br>
								<font style="color: red">*最少输入30字，最多可输入800字</font>
							</td>
						</tr>
					</table>
				</div>
			</div>
			</div>
			<h3 class="searchTitle">审批信息</h3>
			<div class="searchCon">
				<table class="fromTable filter">
					<tr>
						<td class="ft_label" style="width: 15%;">分公司综合意见：</td>
						<td class="ft_content" style="width: 85%;">
							<form:textarea path="suggestionBranch" style="overflow-y:visible" htmlEscape="false" rows="4" maxlength="2000" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('suggestionBranch','preSuggestionBranch')"/>
							<font color="red">*</font>
						</td>
					</tr>
					<tr id="suggestionAreaTr">
						<td class="ft_label" style="width: 15%;">区域综合意见：</td>
						<td class="ft_content" style="width: 85%;">
							<form:textarea path="suggestionArea" id="suggestionArea" style="overflow-y:visible" htmlEscape="false" rows="4" maxlength="2000" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('suggestionArea','preSuggestionArea')"/>
							<font color="red">*</font>
						</td>
					</tr>
					<tr id="suggestionLargeAreaTr">
						<td class="ft_label" style="width: 15%;">大区综合意见：</td>
						<td class="ft_content" style="width: 85%;">
							<form:textarea path="suggestionLargeArea" id="suggestionLargeArea" style="overflow-y:visible" htmlEscape="false" rows="4" maxlength="2000" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('suggestionLargeArea','preSuggestionLargeArea')"/>
							<font color="red">*</font>
						</td>
					</tr>
					<tr id="suggestionHeadTr">
						<td class="ft_label" style="width: 15%;">总公司综合意见：</td>
						<td class="ft_content" style="width: 85%;">
							<form:textarea path="suggestionHead" id="suggestionHead" style="overflow-y:visible" htmlEscape="false" rows="4" maxlength="2000" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('suggestionHead','preSuggestionHead')"/>
							<font color="red">*</font>
							
						</td>
					</tr>
				</table>
			</div>
			<div class="form-actions" style="text-align: right;">
				<c:if test="${showStatus == '1'}">
					本层级估值岗选择：
					<select id="assessPostSelectElementId" class="input-medium" style="width: 120px;" onchange="assessPostSelectFun(this)" <c:if test="${isExistStock != '0' && isExistStock !=null}">disabled="disabled"</c:if> >
						<option value="0" <c:if test="${isExistStock == '0'|| isExistStock ==null}">selected="selected"</c:if>>请选择</option>
						<option value="1" <c:if test="${isExistStock == '1'}">selected="selected"</c:if>>有估值岗人员</option>
						<option value="2" <c:if test="${isExistStock == '2'}">selected="selected"</c:if>>无估值岗人员</option>
					</select>&nbsp;&nbsp;&nbsp;&nbsp;
				</c:if>
				<input id="btnSubmit" class="btn btn-primary noprint" type="submit" value="保 存" />
				&nbsp;
				<input id="btnPrint" class="btn btn-primary noprint" type="button" value="打印本页面" onclick="window.print()" />
			</div>
		</form:form>
	</div>
	<!-- 分公司风控审核:移除区域、大区、总公司综合意见，将分公司综合意见设置为可以编辑 -->
	<c:if test="${taskDefKeyFlag == 'utask_fgsfksh'}">
		<script type="text/javascript">
			var isGZ='${controGZ}';
			if(isGZ=='1'){
				$("#suggestionAreaTr").hide();
				$("#suggestionLargeAreaTr").hide();
				$("#suggestionHeadTr").hide();
				disableBaseElement();
				disableWdate();
				disableSelect2();
				hideSuggDiv();
				hideButtons();
				$("font").hide();
			}else{
				$(document).ready(function() {
					$("#suggestionAreaTr").hide();
					$("#suggestionLargeAreaTr").hide();
					$("#suggestionHeadTr").hide();
				});
			}
		</script>
	</c:if>
	<!-- 分公司经理审核:移除区域、大区、总公司综合意见 -->
	<c:if test="${taskDefKeyFlag == 'utask_fgsjlsh'}">
		<script type="text/javascript">
			$(document).ready(function() {
				$("#suggestionAreaTr").hide();
				$("#suggestionLargeAreaTr").hide();
				$("#suggestionHeadTr").hide();
				//文本框、大文本框、单选框、复选框只读处理
				disableBaseElement();
				//时间插件只读处理
				disableWdate();
				//下拉框只读处理
				disableSelect2();
				//新增、修改、删除、保存按钮只读处理
				hideButtons();
				//审批意见只读处理
				hideSuggDiv();
				//由于页面的特殊性，所以这里直接将所有的fongt节点删除
				$("font").hide();
			});
		</script>
	</c:if>
	<!-- 区域风控专员审核:移除大区、总公司综合意见，将区域综合意见设置为可以编辑 -->
	<c:if test="${taskDefKeyFlag == 'utask_qyfksh'}">
		<script type="text/javascript">
			var isGZ='${controGZ}';
			if(isGZ=='1'){
				$("#suggestionLargeAreaTr").hide();
				$("#suggestionHeadTr").hide();
				disableBaseElement();
				disableWdate();
				disableSelect2();
				hideSuggDiv();
				hideButtons();
				$("font").hide();
			}else{
				$(document).ready(function() {
					$("#suggestionLargeAreaTr").hide();
					$("#suggestionHeadTr").hide();
					//文本框、大文本框、单选框、复选框只读处理
					disableBaseElement();
					//时间插件只读处理
					disableWdate();
					//下拉框只读处理
					disableSelect2();
					//新增、修改、删除、保存按钮只读处理
					hideButtons();
					//审批意见只读处理
					hideSuggDiv();
					//由于页面的特殊性，所以这里直接将所有的fongt节点删除
					$("font").hide();
					if ('${actTaskParam.status}' == 0) {//待办
						//放开区域综合意见填写权限
						$("#suggestionArea").removeAttr("readOnly");
						//显示保存按钮
						$("#btnSubmit").show();
						//放开本层级估值岗选择
						if ('${isExistStock}' == '0') {
							$("#assessPostSelectElementId").prop("disabled",false).select2();
						}
					}
				});
			}
			
		</script>
	</c:if>
	<!-- 区域风控经理审核:移除大区、总公司综合意见 -->
	<c:if test="${taskDefKeyFlag == 'utask_qyjlsh'}">
		<script type="text/javascript">
			$(document).ready(function() {
				$("#suggestionLargeAreaTr").hide();
				$("#suggestionHeadTr").hide();
				//文本框、大文本框、单选框、复选框只读处理
				disableBaseElement();
				//时间插件只读处理
				disableWdate();
				//下拉框只读处理
				disableSelect2();
				//新增、修改、删除、保存按钮只读处理
				hideButtons();
				//审批意见只读处理
				hideSuggDiv();
				//由于页面的特殊性，所以这里直接将所有的fongt节点删除
				$("font").hide();
			});
		</script>
	</c:if>
	<!-- 大区风控专员审核:移除总公司综合意见，将大区综合意见设置为可以编辑-->
	<c:if test="${taskDefKeyFlag == 'utask_dqfkzysh'}">
		<script type="text/javascript">
			var isGZ='${controGZ}';
			if(isGZ=='1'){
				$("#suggestionHeadTr").hide();
				disableBaseElement();
				disableWdate();
				disableSelect2();
				hideSuggDiv();
				hideButtons();
				$("font").hide();
			}else{
				$(document).ready(function() {
					$("#suggestionHeadTr").hide();
					//文本框、大文本框、单选框、复选框只读处理
					disableBaseElement();
					//时间插件只读处理
					disableWdate();
					//下拉框只读处理
					disableSelect2();
					//新增、修改、删除、保存按钮只读处理
					hideButtons();
					//审批意见只读处理
					hideSuggDiv();
					//由于页面的特殊性，所以这里直接将所有的fongt节点删除
					$("font").hide();
					if ('${actTaskParam.status}' == 0) {//待办
						//放开区域综合意见填写权限
						$("#suggestionLargeArea").removeAttr("readOnly");
						//显示保存按钮
						$("#btnSubmit").show();
						//放开本层级估值岗选择
						if ('${isExistStock}' == '0') {
							$("#assessPostSelectElementId").prop("disabled",false).select2();
						}
					}
				});
			}
		</script>
	</c:if>
	<!-- 大区风控经理审核:移除总公司综合意见-->
	<c:if test="${taskDefKeyFlag == 'utask_dqfkjlsh'}">
		<script type="text/javascript">
			$(document).ready(function() {
				$("#suggestionHeadTr").hide();
				//文本框、大文本框、单选框、复选框只读处理
				disableBaseElement();
				//时间插件只读处理
				disableWdate();
				//下拉框只读处理
				disableSelect2();
				//新增、修改、删除、保存按钮只读处理
				hideButtons();
				//审批意见只读处理
				hideSuggDiv();
				//由于页面的特殊性，所以这里直接将所有的fongt节点删除
				$("font").hide();
			});
		</script>
	</c:if>
	<!-- 分公司复议 -->
	<c:if test="${taskDefKeyFlag == 'utask_fgsfy'}">
		<script type="text/javascript">
			$(document).ready(function() {
				$("#suggestionHeadTr").hide();
				//文本框、大文本框、单选框、复选框只读处理
				disableBaseElement();
				//时间插件只读处理
				disableWdate();
				//下拉框只读处理
				disableSelect2();
				//新增、修改、删除、保存按钮只读处理
				hideButtons();
				//审批意见只读处理
				hideSuggDiv();
				//由于页面的特殊性，所以这里直接将所有的fongt节点删除
				$("font").hide();
			});
		</script>
	</c:if>
	<!-- 总公司风控专员审核:移除总公司综合意见，将大区综合意见设置为可以编辑-->
	<c:if test="${taskDefKeyFlag == 'utask_zgsfksh'}">
		<script type="text/javascript">
			var isGZ='${controGZ}';
			if(isGZ=='1'){
				disableBaseElement();
				disableWdate();
				disableSelect2();
				hideSuggDiv();
				hideButtons();
				$("font").hide();
			}else{
				$(document).ready(function() {
					//文本框、大文本框、单选框、复选框只读处理
					disableBaseElement();
					//时间插件只读处理
					disableWdate();
					//下拉框只读处理
					disableSelect2();
					//新增、修改、删除、保存按钮只读处理
					hideButtons();
					//审批意见只读处理
					hideSuggDiv();
					//由于页面的特殊性，所以这里直接将所有的fongt节点删除
					$("font").hide();
					if ('${actTaskParam.status}' == 0) {//待办
						//放开区域综合意见填写权限
						$("#suggestionHead").removeAttr("readOnly");
						//显示保存按钮
						$("#btnSubmit").show();
						//放开本层级估值岗选择
						if ('${isExistStock}' == '0') {
							$("#assessPostSelectElementId").prop("disabled",false).select2();
						}
					}
				});
			}
		</script>
	</c:if>
	<!-- 总公司经理审核:移除保存按钮-->
	<c:if test="${taskDefKeyFlag == 'utask_zgsjlsh'}">
		<script type="text/javascript">
			$(document).ready(function() {
				//文本框、大文本框、单选框、复选框只读处理
				disableBaseElement();
				//时间插件只读处理
				disableWdate();
				//下拉框只读处理
				disableSelect2();
				//新增、修改、删除、保存按钮只读处理
				hideButtons();
				//审批意见只读处理
				hideSuggDiv();
				//由于页面的特殊性，所以这里直接将所有的fongt节点删除
				$("font").hide();
			});
		</script>
	</c:if>
	<!-- 总公司总经理审核:移除保存按钮-->
	<c:if test="${taskDefKeyFlag == 'utask_zgszjlsh'}">
		<script type="text/javascript">
			$(document).ready(function() {
				//文本框、大文本框、单选框、复选框只读处理
				disableBaseElement();
				//时间插件只读处理
				disableWdate();
				//下拉框只读处理
				disableSelect2();
				//新增、修改、删除、保存按钮只读处理
				hideButtons();
				//审批意见只读处理
				hideSuggDiv();
				//由于页面的特殊性，所以这里直接将所有的fongt节点删除
				$("font").hide();
			});
		</script>
	</c:if>
</body>
</html>