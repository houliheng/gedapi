<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>批复信息</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		var productCategoryKey = '${approveProductCategoryKey}';
		if(productCategoryKey==null||productCategoryKey==''){//不是债股结合产品
			$(".ZGCategory").hide();//隐藏不可编辑的分类
		}else{//是债股结合的产品
			$(".ZGCategory").show();
		}
		//---
		var specialServiceFee = $("#specialServiceFee").val();
		if(specialServiceFee==null||specialServiceFee==""||specialServiceFee=="undefined"){
			$("#specialServiceFee").val("0");
		}
		
		var specialServiceFeeRate = $("#specialServiceFeeRate").val();
		if(specialServiceFeeRate==null||specialServiceFeeRate==""||specialServiceFeeRate=="undefined"){
			$("#specialServiceFeeRate").val("0");
		}
		//---
		var showCgFlag='${showCgFlag}';
		if(showCgFlag==1){
			$(".cgisShow").show();
			$.loadDiv("purchaseInfoListDiv", "${ctx }/credit/purchaseInfo/list", {
				applyNo : "${actTaskParam.applyNo}"
			}, "post");
		}else{
			$(".cgisShow").hide();
		}
		setDivReadOnly("checkApprovUnionInddexDiv");
		$.loadDiv("checkApproveUnionListId", "${ctx}/credit/checkApproveUnion/list", {
			applyNo : "${actTaskParam.applyNo}"
		}, "post");
	});

</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/app/credit/workflow/include/actTaskParams.jsp"%>
	<div id="checkApprovUnionInddexDiv" class="searchInfo">
		<h3 class="searchTitle">批复信息</h3>
		<div class="searchCon">
			<form:form id="checkApproveForm" modelAttribute="checkApproveTotal" action="" method="post" class="form-horizontal">
				<form:hidden path="id" />
				<table class="fromTable filter">
					<tr>
						<td width="13%" class="ft_label">产品类型：</td>
						<td class="ft_content">
							<form:select path="approProductTypeCode" class="input-medium" disabled="disabled" cssStyle="width:164px;">
								<form:option value="" label="请选择" />
								<form:options items="${fns:getDictList('PRODUCT_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<form:hidden path="approProductTypeName" />
						</td>
						<td width="13%" class="ft_label">产品名称：</td>
						<td class="ft_content">
							<form:select id="approProductId" path="approProductId" class="input-medium" disabled="disabled" cssStyle="width:164px;">
								<form:option value=""></form:option>
								<form:options items="${productList}" htmlEscape="false" itemLabel="productName" itemValue="id" />
							</form:select>
							<input type="hidden" id="approProductName" name="approProductName" />
						</td>
						<td class="ft_label">产品期限(月)：</td>
						<td class="ft_content">
							<form:select path="approPeriodId" class="input-medium" disabled="disabled">
								<form:option value="" label=""></form:option>
								<form:options items="${fns:getDictList('PRODUCT_PERIOD_VALUE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<form:hidden path="approPeriodValue" />
						</td>
					</tr>
					<tr>
						<td class="ft_label">合同金额(元)：</td>
						<td class="ft_content">
							<form:input  readonly="true" path="contractAmount" maxlength="18" htmlEscape="false" class=" input-medium" />
						</td>
						<td class="ft_label">放款金额(元)：</td>
						<td class="ft_content">
							<form:input  readonly="true"  path="loanAmount" maxlength="18" htmlEscape="false" class=" input-medium" readOnly="true" />
						</td>
						<td class="ft_label">风险定价费率(%)：</td>
						<td class="ft_content">
							<form:input path="pricedRisk" htmlEscape="false" class=" input-medium" readOnly="true" />
						</td>
					</tr>
					<tr class="ZGCategory">
						<td class="ft_label">增资期限（月）：</td>
						<td class="ft_content">
							<form:input onkeyup="keyPress11(this);" name="addFundPeriod" path="addFundPeriod" maxlength="18" htmlEscape="false" class=" input-medium required" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">实际服务费（元）：</td>
						<td class="ft_content">
							<form:input path="realityServiceFee" name="realityServiceFee"  maxlength="18" htmlEscape="false" class=" input-medium required" readOnly="true" />
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">批复月利率(%)：</td>
						<td class="ft_content">
							<form:input path="approYearRate" htmlEscape="false" readonly="true"  class=" money input-medium" maxlength="5" />
						</td>
						<c:if test="${flowCode == '1'}">
						<td class="ft_label">让利后月利率(%)：</td>
						<td class="ft_content">
							<form:input path="discountInterestRate" htmlEscape="false" cssClass="money input-medium" maxlength="5" readonly="true"/>
						</td>
						</c:if>
						<td class="ft_label">冠E通年利率(%)：</td>
						<td class="ft_content">
							<input type="text" id="guanetongRate" name="guanetongRate" maxlength="7" htmlEscape="false" class="input-medium" readOnly="true" value="${interest}" />
						</td>
						<td width="13%" class="ft_label ZGCategory">产品分类：</td>
						<td class="ft_content ZGCategory">
							<input type="text" id="productCategoryKey" name="productCategoryKey" class="input-medium" readonly="true" value="${approveProductCategoryKey}" />
						</td>
					</tr>
					<tr>
						<td class="ft_label">服务费率(%)：</td>
						<td class="ft_content">
							<form:input path="serviceFeeRate" readonly="true" htmlEscape="false" class="input-medium money"  maxlength="5" />
						</td>
						<%-- <c:if test="${fns:getDictValue('special_Service_Fee_Rate','special_Service_Fee_Rate','special_Service_Fee_Rate') eq '1'}"> --%>
							<td class="ft_label">特殊服务费率(%)：</td>
							<td class="ft_content">
								<form:input path="specialServiceFeeRate" readonly="true" htmlEscape="false" class="input-medium money "  maxlength="5" />
							</td>
						<%-- </c:if> --%>
						<td class="ft_label">服务费收取方式：</td>
						<td class="ft_content">
							<form:select path="serviceFeeType" onchange="calculateAmount()" class="input-medium" disabled="disabled" >
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('SERVICE_FEE_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
						</td>
					</tr>
					<tr>
						<td class="ft_label">服务费：</td>
						<td class="ft_content">
							<form:input  path="serviceFee" htmlEscape="false" class="input-medium" readOnly="true" maxlength="18" />
						</td>
						<%-- <c:if test="${fns:getDictValue('special_Service_Fee_Rate','special_Service_Fee_Rate','special_Service_Fee_Rate') eq '1'}"> --%>
							<td id="specialServiceFeeLabel" class="ft_label">特殊服务费：</td>
							<td id="specialServiceFeeContent" class="ft_content">
								<form:input path="specialServiceFee" readonly="true" htmlEscape="false" maxlength="300" class="input-medium " />
							</td>
						<%-- </c:if> --%>
						<td class="ft_label">服务费总计：</td>
						<td class="ft_content">
							<form:input onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);calculateAmount();" path="allServiceFee" class="input-medium" readOnly="true" maxlength="18" />
						</td>
					</tr>
					<tr>
						<td class="ft_label">还款方式：</td>
						<td class="ft_content">
							<form:select path="approLoanRepayType" class="input-medium" disabled="disabled" >
								<form:option value="" label="" />
								<form:options items="${loanRepayTypeList}" itemLabel="loanRepayDesc" itemValue="loanRepayType" htmlEscape="false" />
							</form:select>
						</td>
						<td class="ft_label">保证金率(%)：</td>
						<td class="ft_content">
							<form:input path="marginRate" htmlEscape="false" class="input-medium" readonly="true"  maxlength="5" />
						</td>
						<td class="ft_label">保证金(元)：</td>
						<td class="ft_content">
							<form:input  path="marginAmount" maxlength="30" htmlEscape="false" class="input-medium" readOnly="true" />
						</td>
					</tr>
					<c:if test="${flowCode == '2' || flowCode == '1'}">
					<tr>
						<td class="ft_label">保证金月息差(元)：</td>
						<td class="ft_content">
							<form:input path="interestRateDiff" htmlEscape="false" class="input-medium" maxlength="18"  readOnly="true" />
						</td>
						<c:if test="${flowCode == '1'}">
						<td class="ft_label">利息月息差(元)：</td>
						<td class="ft_content">
							<form:input path="interestMonthlySpread" htmlEscape="false" class="input-medium" maxlength="18"  readOnly="true" />
						</td>
						</c:if>
					</tr>
					</c:if>
					<c:if test="${checkApproveTotal.qualityServiceMarginRate != null }">
					<tr>
						<td class="ft_label">质量服务保证金率(%)：</td>
						<td class="ft_content">
							<form:input path="qualityServiceMarginRate" htmlEscape="false" class="input-medium" onblur="this.value=gdpMoney(this.value);calculateAmount();" readOnly="true"  onkeyup="gdpMax(this)" maxlength="5" />
						</td>
						<td class="ft_label">质量服务保证金：</td>
						<td class="ft_content">
							<form:input onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" path="qualityServiceMarginAmount" maxlength="18" htmlEscape="false" class="input-medium" readOnly="true" />
						</td>
					</tr>
					</c:if>
					<tr>
						<td class="ft_label">外访费：</td>
						<td class="ft_content">
							<form:input readonly="true"  path="checkFee" maxlength="18" htmlEscape="false" class="input-medium " />
						</td>
						<td class="ft_label">借款模式：</td>
						<td class="ft_content">
							<form:select path="loanModel" class="input-medium" disabled="disabled">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('LOAN_MODEL')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
						</td>
						<td class="ft_label">是否加急：</td>
						<td class="ft_content">
							<form:select path="isUrgent" class="input-medium" disabled="disabled" >
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
						</td>
					</tr>
					


					<tr class="cgisShow">
						<td class="ft_label">上游供应商名称：</td>
						<td class="ft_content" colspan="4">
							<input type="text" name="topShopName" id="topShopName"  class="input-medium required" maxlength="60" value="${checkApproveTotal.topShopName }" style="margin: 0px; position: relative; display: inline-block; vertical-align: middle; width: 540px"  />
							<font color="red">*</font>
						</td>
					</tr>
					<tr class="cgisShow">
						<td class="ft_label">下游采购商名称：</td>
						<td class="ft_content" colspan="4">
							<input type="text" name="downShopName" id="downShopName"  class="input-medium required" maxlength="60" value="${checkApproveTotal.downShopName }" style="margin: 0px; position: relative; display: inline-block; vertical-align: middle; width: 540px"  />
							<font color="red">*</font>
						</td>
					</tr>
					 <tr class="cgisShow">
						<td class="ft_label">上游供应商返利(%)：</td>
						<td class="ft_content">
							<form:input path="topShopBackRate" htmlEscape="false" class="input-medium  required" onblur="this.value=gdpMoney(this.value);calculateAmount();" onkeyup="gdpMax(this)" maxlength="5" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">上游返利总额(元)：</td>
						<td class="ft_content">
							<form:input path="topShopBackMoney" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);"  maxlength="18" htmlEscape="false" class="input-medium required" readOnly="true"/>
							<font color="red">*</font>
						</td>
						<td class="ft_label">实际月利率(%)：</td>
						<td class="ft_content">
							<form:input path="topShopMonthRate" htmlEscape="false" class="input-medium  required" onblur="this.value=gdpMoney(this.value);" onkeyup="gdpMax(this)" maxlength="5" readOnly="true"/>
							<font color="red">*</font>
						</td>
					</tr>
					<tr class="cgisShow">
						<td class="ft_label">居间服务费(元)：</td>
						<td class="ft_content">
							<form:input path="mediacyServiceFee" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);calculateAmount();"  maxlength="18" htmlEscape="false" class="input-medium required"  />
							<font color="red">*</font>
						</td>
						<%--<td class="ft_label">月息差(元)：</td>--%>
						<%--<td class="ft_content">--%>
							<%--<form:input path="interestRateDiff" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);calculateAmount();"  maxlength="18" htmlEscape="false" class=" input-medium required" readOnly="true" />--%>
							<%--<font color="red">*</font>--%>
						<%--</td>--%>
					</tr> 
					<tr class="cgisShow">
						<td class="ft_label">采购商品信息：</td>
					</tr>
					<tr class="cgisShow">
						<td colspan="6">
						<!-- 显示采购信息 -->
							<div id="purchaseInfoListDiv"></div>
						</td>
					</tr>
				</table>
			</form:form>
		</div>
	</div>
	<div id="checkApproveUnionListId"></div>
	<div id="isHideSuggestionDiv">
		<%@ include file="/WEB-INF/views/app/credit/processSuggestionInfo/checkApproveConclusionForm.jsp"%>
	</div>
</body>
</html>
