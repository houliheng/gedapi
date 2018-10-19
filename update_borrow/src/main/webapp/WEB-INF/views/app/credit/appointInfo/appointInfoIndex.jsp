<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>合同预约管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$.loadDiv("registerGED", "${ctx }/credit/conclusion/isRegisterGED", {
			applyNo : '${actTaskParam.applyNo}'
		}, "post");
		var productCategoryKey = '${productCategoryKey}';
		if(productCategoryKey==null||productCategoryKey==''){//不是债股结合产品
			$(".ZGCategory").hide();//隐藏不可编辑的分类
		}else{//是债股结合的产品
			$(".ZGCategory").show();
			$(".classInterestRate").hide();
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
				applyNo : '${applyInfo.applyRegister.applyNo}',
				taskDefKey: '${actTaskParam.taskDefKey}'
			}, "post");
		}else{
			$(".cgisShow").hide();
		}
		adjustTextareaLength('suggestionDesc','suggestionDescPre');
		$("div[id='applyInfoForCreditDiv'] input").attr("readOnly", "readOnly");
		$("div[id='applyInfoForCreditDiv'] textarea").attr("readOnly", "readOnly");
		$("div[id='applyInfoForCreditDiv'] select").prop("disabled", true).select2();

		$("div[id='approveInfoDiv'] input").attr("readOnly", "readOnly");
		$("div[id='approveInfoDiv'] textarea").attr("readOnly", "readOnly");
		$("div[id='approveInfoDiv'] select").prop("disabled", true).select2();
		$("#approveSaveBtn").hide();
		
		

		if (!checkIsNull('${checkApprove.contractType}')) {
			checkContractType();
		}
		
		adjustTextareaLength("remark", "preRemark");
	});

	//加载复选框
	function checkContractType() {
		var contractTypeStr = '${checkApprove.contractType}';
		var contractTypeArray = contractTypeStr.split(",");
		var checkBoxAll = $("input[name='contractType']");
		for (var i = 0; i < contractTypeArray.length; i++) {
			$.each(checkBoxAll, function(j, checkBox) {
				var checkValue = $(checkBox).val();
				if (contractTypeArray[i] == checkValue) {
					$(checkBox).attr("checked", true);
				}
			});
		}
	}
</script>
</head>
<body>
	<!-- 申请信息 -->
	<div id="applyInfoForCreditDiv">
		<%@ include file="/WEB-INF/views/app/credit/creditViewBook/applyInfoForCredit/applyInfoForCreditForm.jsp"%>
	</div>
	<!-- 批复信息 -->
	<div id="approveInfoDiv" class="searchInfo">
		<h3 class="searchTitle">批复信息</h3>
		<div class="searchCon">
			<table class="fromTable filter">
				<form:form id="checkApproveForm" modelAttribute="checkApprove" action="${ctx}/credit/checkApprove/save" method="post" class="form-horizontal">
					<form:hidden path="id" />
					<sys:message content="${message}" />
					<pre class="pre-style"  id="preRemark" style="width:820px;"></pre>
					<tr>
						<td width="13%" class="ft_label">产品类型：</td>
						<td class="ft_content">
							<form:input path="approProductTypeName" maxlength="18" htmlEscape="false" class="money input-medium required" />
							<font color="red">*</font>
						</td>
						<td width="13%" class="ft_label">产品名称：</td>
						<td class="ft_content">
							<form:input path="approProductName" maxlength="18" htmlEscape="false" class="money input-medium required" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">产品期限(月)：</td>
						<td class="ft_content">
							<form:input path="approPeriodValue" maxlength="18" value="${fns:getDictLabel(checkApprove.approPeriodValue, 'PRODUCT_PERIOD_VALUE', '')}" htmlEscape="false" class="money input-medium required" />
							<font style="color: red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">合同金额(元)：</td>
						<td class="ft_content">
							<form:input path="contractAmount" maxlength="18" htmlEscape="false" class="money input-medium required" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">放款金额(元)：</td>
						<td class="ft_content">
							<form:input path="loanAmount" maxlength="18" htmlEscape="false" class="money input-medium required" />
							<font color="red">*</font>
						</td>
						<td></td>
						<td></td>
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
						<%--<td class="ft_label classInterestRate">月息差（元）：</td>--%>
						<%--<td class="ft_content classInterestRate">--%>
							<%--<form:input path="interestRateDiff" maxlength="18" htmlEscape="false" class="input-medium required" />--%>
							<%--<font color="red">*</font>--%>
						<%--</td>--%>
					</tr>
					<tr>
						<td class="ft_label">批复月利率(%)：</td>
						<td class="ft_content">
							<form:input path="approYearRate" onblur="this.value=rateMoney(this.value);" onkeyup="rateMax(this)" maxlength="5" htmlEscape="false" class="money input-medium required" />
							<font color="red">*</font>
						</td>
						<c:if test="${flowCode == '1'}">
							<td class="ft_label">让利后月利率(%)：</td>
							<td class="ft_content">
								<form:input path="discountInterestRate" htmlEscape="false" cssClass="money input-medium" maxlength="5" readonly="true"/>
							</td>
						</c:if>
						<td class="ft_label">冠E通年利率(%)：</td>
						<td class="ft_content">
							<input type="text" id="guanetongRate" name="guanetongRate"  maxlength="5" htmlEscape="false" class="input-medium" value="${interest}"/>
							<font color="red">*</font>
						</td>
						<td width="13%" class="ft_label ZGCategory">产品分类：</td>
						<td class="ft_content ZGCategory">
							<input type="text" id="productCategoryKey" name="productCategoryKey" class="input-medium" readonly="true" value="${checkProductCategoryKey}" />
						</td>
					</tr>
					<tr>
						<td class="ft_label">服务费率(%)：</td>
						<td class="ft_content">
							<form:input path="serviceFeeRate" onblur="this.value=rateMoney(this.value);" onkeyup="rateMax(this)" htmlEscape="false" maxlength="5" class="input-medium required money" />
							<font color="red">*</font>
						</td>
						<%-- <c:if test="${fns:getDictValue('special_Service_Fee_Rate','special_Service_Fee_Rate','special_Service_Fee_Rate') eq '1'}"> --%>
							<td class="ft_label">特殊服务费率(%)：</td>
							<td class="ft_content">
								<form:input path="specialServiceFeeRate" onblur="this.value=rateMoney(this.value);" onkeyup="rateMax(this)" htmlEscape="false" maxlength="5" class="input-medium required money" />
								<font color="red">*</font>
							</td>
						<%-- </c:if> --%>
						<td class="ft_label">服务费收取方式：</td>
						<td class="ft_content">
							<form:select path="serviceFeeType" class="input-medium required">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('SERVICE_FEE_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">服务费：</td>
						<td class="ft_content">
							<form:input path="serviceFee" htmlEscape="false" maxlength="18" class="input-medium required money" />
							<font color="red">*</font>
						</td>
						<%-- <c:if test="${fns:getDictValue('special_Service_Fee','special_Service_Fee','special_Service_Fee') eq '1'}"> --%>
							<td class="ft_label">特殊服务费：</td>
							<td class="ft_content">
								<form:input path="specialServiceFee" htmlEscape="false" maxlength="18" class="input-medium required money" />
								<font color="red">*</font>
							</td>
						<%-- </c:if> --%>
						<td class="ft_label">服务费总计：</td>
						<td class="ft_content">
							<form:input path="allServiceFee" htmlEscape="false" maxlength="18" class="input-medium required money" />
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">还款方式：</td>
						<td class="ft_content">
							<form:select path="approLoanRepayType" class="input-medium required">
								<form:option value="" label="" />
								<form:options items="${loanRepayTypeList}" itemLabel="loanRepayDesc" itemValue="loanRepayType" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
						</td>
						<td class="ft_label">保证金率(%)：</td>
						<td class="ft_content">
							<form:input path="marginRate" htmlEscape="false" onblur="this.value=rateMoney(this.value);" onkeyup="rateMax(this)" maxlength="5" class="input-medium required money" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">保证金(元)：</td>
						<td class="ft_content">
							<form:input path="marginAmount" htmlEscape="false" maxlength="18" class="input-medium required money" />
							<font color="red">*</font>
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
					<c:if test="${checkApprove.qualityServiceMarginRate != null }">
					<tr>
						<td class="ft_label">质量服务保证金率(%)：</td>
						<td class="ft_content">
							<form:input path="qualityServiceMarginRate" htmlEscape="false" class="input-medium  required" onblur="this.value=gdpMoney(this.value);calculateAmount();" onkeyup="gdpMax(this)" maxlength="5" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">质量服务保证金：</td>
						<td class="ft_content">
							<form:input onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" path="qualityServiceMarginAmount" maxlength="18" htmlEscape="false" class="input-medium" readOnly="true" />
							<font color="red">*</font>
						</td>
					</tr>
					</c:if>
					<tr>
						<td class="ft_label">外访费：</td>
						<td class="ft_content">
							<form:input path="checkFee" htmlEscape="false" maxlegth="18" class="input-medium required money" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">借款模式：</td>
						<td class="ft_content">
							<form:select path="loanModel" class="input-medium required">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('LOAN_MODEL')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
						</td>
						<td class="ft_label">是否加急：</td>
						<td class="ft_content">
							<form:select path="isUrgent" class="input-medium required">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
						</td>
					</tr>
					
					
					<tr class="cgisShow">
						<td class="ft_label">上游供应商名称：</td>
						<td class="ft_content" colspan="4">
							<input type="text" name="topShopName" id="topShopName"  class="input-medium required" maxlength="60" value="${checkApprove.topShopName }" style="margin: 0px; position: relative; display: inline-block; vertical-align: middle; width: 540px"  />
							<font color="red">*</font>
						</td>
					</tr>
					<tr class="cgisShow">
						<td class="ft_label">下游采购商名称：</td>
						<td class="ft_content" colspan="4">
							<input type="text" name="downShopName" id="downShopName"  class="input-medium required" maxlength="60" value="${checkApprove.downShopName }" style="margin: 0px; position: relative; display: inline-block; vertical-align: middle; width: 540px"  />
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
					
					
					<tr>
						<td class="ft_label">备注：</td>
						<td class="ft_content" colspan="6">
							<form:textarea path="remark" htmlEscape="false" rows="4" maxlength="1500" class="textarea-style required"  style="width:820px;" placeholder="此项填写为小黄表备注项" onkeyup="adjustTextareaLength('remark','preRemark')"/>
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">总公司批复意见：</td>
						<td class="ft_content" colspan="5">
						    <pre class="area-xxlarge pre-style"  style="width:820px;" id="suggestionDescPre"></pre>
							<textarea id="suggestionDesc" name="suggestionDesc" title="${topComApproveSugg }" rows="4" cols="5" class="area-xxlarge  textarea-style" style="width:820px;" maxlength="1000" readonly="readonly">${topComApproveSugg }</textarea>
						</td>
					</tr>			
				</form:form>
			</table>
		</div>
	</div>
	<!-- 注册冠易贷 -->
	<div id="registerGED"></div>
	<!-- 预约意见 -->
	<div id="appointConclusionForm">
		<%@ include file="/WEB-INF/views/app/credit/processSuggestionInfo/appointConclusionForm.jsp"%>
	</div>
</body>
</html>