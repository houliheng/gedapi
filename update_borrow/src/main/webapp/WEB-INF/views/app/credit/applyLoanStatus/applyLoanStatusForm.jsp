<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>财务放款管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		var productCategoryKey = '${productCategoryKey}';
		if(productCategoryKey==null||productCategoryKey==''){//不是债股结合产品
			$(".ZGCategory").hide();//隐藏不可编辑的分类
		}else{//是债股结合的产品
			$(".ZGCategory").show();
			$(".classInterestRate").hide();
		}
		//--------------------------
		var showButtonPush = '${showButtonPush}';
		if(showButtonPush=='0'){
			$("#btnLoan").attr("disabled", true);
		}
		//--------------------------------------
		var showCgFlag='${showCgFlag}';
		if(showCgFlag==1){
			$(".cgisShow").show();
			$.loadDiv("purchaseInfoListDiv", "${ctx }/credit/purchaseInfo/list", {
				applyNo : '${actTaskParam.applyNo}',
				taskDefKey : '${actTaskParam.taskDefKey}'
			}, "post");
		}else{
			$(".cgisShow").hide();
		}

		if ('${applyLoanStatus.loanStatus}' != '20') {
			$("#btnPush").attr("disabled", true);
		}

		adjustTextareaLength("approResult", "approResultPre");
		adjustTextareaLength("suggestionDesc", "suggestionDescPre");
		document.getElementById("loanAmount").value = outputmoney("${contract.loanAmount}");
		document.getElementById("contractAmount").value = outputmoney("${contract.contractAmount}");
		document.getElementById("serviceFee").value = outputmoney("${contract.serviceFee}");
		if (!checkIsNull(document.getElementById("specialServiceFee").value)) {
			document.getElementById("specialServiceFee").value = outputmoney("${contract.specialServiceFee}");
		}
		document.getElementById("allServiceFee").value = outputmoney("${contract.allServiceFee}");
		document.getElementById("marginAmount").value = outputmoney("${contract.marginAmount}");
		document.getElementById("checkFee").value = outputmoney("${contract.checkFee}");
		/* if ("${fns:getDictValue('special_Service_Fee','special_Service_Fee','special_Service_Fee')}" == "1") {
			$("#specialServiceFeeLabel").show();
			$("#specialServiceFeeContent").show();
		} else {
			$("#specialServiceFeeLabel").hide();
			$("#specialServiceFeeContent").hide();
		} */
		//页面初始化后，设置“加黑说明”不可编辑。
		$("#blacklistDiv").hide();
		//$("#name").focus();
		$("#processSuggestionInfoForm").validate({
		submitHandler : function() {
			var passFlag = $("input[name='passFlag']:checked").val();
			$.ajax({
			url : "${ctx}/credit/applyLoanStatus/validateLoanStatus",
			type : "post",
			data : {
			passFlag : passFlag,
			productTypeCode : '${contract.approProductTypeCode}',
			applyNo : '${actTaskParam.applyNo}'
			},
			dataType : "json",
			success : function(data) {
				if (data.status == "1") {
					saveSuggestion();
				} else {
					alertx(data.message);
				}
			},
			error : function(msg) {
				alertx("未能完成操作，请查看后台信息");
			}
			});
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});
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
	});

	function validate(show) {
		//当选择"拒绝并加入黑名单"之外的选项时，"加黑说明"设置为不可编辑。
		if (show == 'non_display') {
			$("#blacklistDiv").hide();
			$("#btnSubmit").show();
		} else {
			//当选择“拒绝并加入黑名单”后，将“加黑说明”设置为可编辑
			$("#blacklistDiv").show();
			$("#btnSubmit").hide();
		}
	}

	function validateSubmit(v) {
		$("#sta").attr("value", v);
	}

	//刷新放款状态
	function refreshLoanStatus() {
		top.$.jBox.tip.mess = null;
		var applyNo = '${processSuggestionInfo.applyNo}';
		$.post("${ctx}/credit/applyLoanStatus/refreshLoanStatus", {
			applyNo : applyNo
		}, function(data) {
			if (data) {
				if (data.status == 1) {
					var loanStatus = data.loanStatus;
					$("#s2id_loanStatus>.select2-choice>.select2-chosen").html(loanStatus);
					if (data.loanStatusCode != "20") {
						$("#btnPush").attr("disabled", true);
					} else {
						$("#btnPush").removeAttr("disabled");
					}
				} else {
					alertx(data.message);
				}
			}
		});
	}

	//保存放款意见
	function saveSuggestion() {
		var flag = $("input[name='passFlag']:checked").val();
		if (flag == "black") {
			confirmx("确认加入黑名单吗？", function() {
				loading('正在提交，请稍等...');
				save();
			});
		} else {
			loading('正在提交，请稍等...');
			save();
		}
	}
	function save() {
		var loanStatus = document.getElementById("loanStatus").value;
		top.$.jBox.tip.mess = null;
		var paramJson = $("#processSuggestionInfoForm").serializeJson();
		$.post("${ctx}/credit/applyLoanStatus/saveLoanSuggestion?loanStatus=" + loanStatus, paramJson, function(data) {
			if (data) {
				if (data.status == 1) {
					alertx(data.suggestionMessage, function() {
						goToPage('${ctx}${actTaskParam.headUrl}', 'applyLoanStatusSkipId');
					});
				} else {
					alertx(data.suggestionMessage);
				}
				closeTip();
			}
		});
	}

	//冠E通发标接口
	function targetPush() {
		loading("正在推送，请稍等");
		top.$.jBox.tip.mess = null;
		var formJson = $("#applyLoanStatusForm").serializeJson();
		$.post("${ctx}/credit/applyLoanStatus/save", formJson, function(data) {
			if (data.status == 1) {//保存成功
				refreshLoanStatus();
				$.ajax({
				type : "post",
				data : {
					applyNo : '${actTaskParam.applyNo}'
				},
				dataType : "json",
				url : "${ctx}/outinterface/callInterface/issuingTenderData",
				success : function(msg) {
					closeTip();
					refreshLoanStatus();
					if (msg.status == "0") {
						alertx(msg.message);
					} else {
						alertx(data.message);
						$("#btnPush").attr("disabled", true);
					}
				},
				error : function(msg) {
					closeTip();
					alertx("未能保存，请查看后台信息");
				}
				});
			} else {
				closeTip();
				alertx(data.message);
			}
		});
	}
	
//向冠E贷发送放款	
/* function targetLoan(applyNo,contractNo){
	confirmx("请确认担保费和保证金线下已缴纳",function(){
		loading("正在推送，请稍等");
		top.$.jBox.tip.mess = null;
		$.ajax({
			type : "post",
			data : {
				applyNo : applyNo,
				contractNo:contractNo
			},
			dataType : "json",
			url : "${ctx}/credit/applyLoanStatus/pushGedLoan",
			success : function(msg) {
				closeTip();
				if (msg.status == "1") {
					alertx(msg.message);
					$("#btnLoan").attr("disabled", true);
				} else {
					alertx(msg.message);
				}
			},
			error : function(msg) {
				closeTip();
				alertx("查询异常，请查看后台信息");
			}
			});
		
	})
	
	
} */
function targetLoan(applyNo,contractNo){
	var url = "${ctx}/credit/applyLoanStatus/sureGuaranteeForm?applyNo="+applyNo+"&contractNo="+contractNo;
	openJBox("applyLoanStatus-form", url, "确认缴费", 800, 400,null);
}
</script>
</head>
<body>
	<form id="emptyForm" action="#"></form>
	<form:form id="contractForm" modelAttribute="contract" action="${ctx}/credit/contract/save" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<sys:message content="${approveMessage}" />
		<div class="searchInfo">
			<h3 class="searchTitle">批复信息</h3>
			<div class="searchCon">
				<table class="fromTable filter">
					<tr>
						<td class="ft_label">产品类型：</td>
						<td class="ft_content">
							<form:select path="approProductTypeName" class="input-medium " disabled="true">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('PRODUCT_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
						</td>
						<td class="ft_label">产品名称：</td>
						<td class="ft_content">
							<input type="text" name="approProductName" id="approProductName" value="${contract.approProductName}" readonly="true" class="input-medium " />
						</td>
					</tr>
					<tr>
						<td class="ft_label">放款金额(元)：</td>
						<td class="ft_content">
							<form:input path="loanAmount" readonly="true" htmlEscape="false" maxlength="300" class="input-medium " />
						</td>
						<td class="ft_label">合同金额(元)：</td>
						<td class="ft_content">
							<form:input path="contractAmount" readonly="true" htmlEscape="false" maxlength="300" class="input-medium " />
						</td>
							<td class="ft_label">期限：</td> 
							<td class="ft_content">
								<input type="text" id="approPeriodValue" name="approPeriodValue" class="input-medium" readonly="true" value="${contract.approPeriodValue}" />
							</td>
					</tr>
					<tr>
						<td class="ft_label">利率(%)：</td>
						<td class="ft_content">
							<form:input path="approYearRate" onblur="this.value=rateMoney(this.value);" onkeyup="rateMax(this)" readonly="true" htmlEscape="false" maxlength="5" class="input-medium " />
							<span class="help-inline"> </span>
						</td>
						<td width="13%" class="ft_label ZGCategory">产品分类：</td>
						<td class="ft_content ZGCategory">
							<input type="text" id="productCategoryKey" name="productCategoryKey" class="input-medium" readonly="true" value="${productCategoryKey}" />
						</td>
					</tr>
					<tr>
						<td class="ft_label">服务费率(%)：</td>
						<td class="ft_content">
							<form:input path="serviceFeeRate" onblur="this.value=rateMoney(this.value);" onkeyup="rateMax(this)" readonly="true" htmlEscape="false" maxlength="5" class="input-medium " />
							<span class="help-inline"> </span>
						</td>
						<%-- <c:if test="${fns:getDictValue('special_Service_Fee_Rate','special_Service_Fee_Rate','special_Service_Fee_Rate') eq '1'}"> --%>
							<td class="ft_label">特殊服务费率(%)：</td>
							<td class="ft_content">
								<form:input path="specialServiceFeeRate" onblur="this.value=rateMoney(this.value);" onkeyup="rateMax(this)" readonly="true" htmlEscape="false" maxlength="5" class="input-medium " />
								<span class="help-inline"> </span>
							</td>
						<%-- </c:if> --%>
						<td class="ft_label">服务费收取方式：</td>
						<td class="ft_content">
							<form:select path="serviceFeeType" disabled="true" class="input-medium ">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('SERVICE_FEE_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
						</td>
					</tr>
					<tr>
						<td class="ft_label">服务费：</td>
						<td class="ft_content">
							<form:input path="serviceFee" readonly="true" htmlEscape="false" maxlength="300" class="input-medium " /> 
						</td>
						<td id="specialServiceFeeLabel" class="ft_label">特殊服务费：</td>
						<td id="specialServiceFeeContent" class="ft_content">
							<form:input path="specialServiceFee" readonly="true" htmlEscape="false" maxlength="300" class="input-medium " />
						</td>
						<td class="ft_label">服务费总计：</td>
						<td class="ft_content">
							<form:input path="allServiceFee" readonly="true" htmlEscape="false" maxlength="300" class="input-medium " />
						</td>
					</tr>
					<tr>
						<td class="ft_label">还款方式：</td>
						<td class="ft_content">
							<form:select path="approLoanRepayType" disabled="true" class="input-medium ">
								<form:option value="" label="" />
								<form:options items="${loanRepayTypeList}" itemLabel="loanRepayDesc" itemValue="loanRepayType" htmlEscape="false" />
							</form:select>
						</td>
						<td class="ft_label">保证金率（%）：</td>
						<td class="ft_content">
							<form:input path="marginRate" onblur="this.value=rateMoney(this.value);" onkeyup="rateMax(this)" readonly="true" htmlEscape="false" maxlength="5" class="input-medium " />
						</td>
						<td class="ft_label">保证金（元）：</td>
						<td class="ft_content">
							<form:input path="marginAmount" readonly="true" htmlEscape="false" maxlength="300" class="input-medium " />
						</td>
					</tr>
					<c:if test="${contract.qualityServiceMarginRate != null }">
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
							<form:input path="checkFee" readonly="true" htmlEscape="false" maxlength="300" class="input-medium " />
						</td>
						<td class="ft_label">贷款模式：</td>
						<td class="ft_content">
							<form:select path="loanModel" disabled="true" class="input-medium ">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('LOAN_MODEL')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
						</td>
					</tr>
					
					
				
					<tr class="cgisShow">
						<td class="ft_label">上游供应商名称：</td>
						<td class="ft_content" colspan="4">
							<input type="text" name="topShopName" id="topShopName"  class="input-medium required" maxlength="60" value="${CGapproves.topShopName }" style="margin: 0px; position: relative; display: inline-block; vertical-align: middle; width: 540px"  />
							<font color="red">*</font>
						</td>
					</tr>
					<tr class="cgisShow">
						<td class="ft_label">下游采购商名称：</td>
						<td class="ft_content" colspan="4">
							<input type="text" name="downShopName" id="downShopName"  class="input-medium required" maxlength="60" value="${CGapproves.downShopName }" style="margin: 0px; position: relative; display: inline-block; vertical-align: middle; width: 540px"  />
							<font color="red">*</font>
						</td>
					</tr>
					 <tr class="cgisShow">
						<td class="ft_label">上游供应商返利(%)：</td>
						<td class="ft_content">
							<input type="text" id="topShopBackRate" name="topShopBackRate" value="${CGapproves.topShopBackRate }" htmlEscape="false" class="input-medium  required" onblur="this.value=gdpMoney(this.value);calculateAmount();" onkeyup="gdpMax(this)" maxlength="5" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">上游返利总额(元)：</td>
						<td class="ft_content">
							<input type="text" id="topShopBackMoney" name="topShopBackMoney" value="${CGapproves.topShopBackMoney }" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);"  maxlength="18" htmlEscape="false" class="input-medium required" readOnly="true"/>
							<font color="red">*</font>
						</td>
						<td class="ft_label">实际月利率(%)：</td>
						<td class="ft_content">
							<input type="text" id="topShopMonthRate" name="topShopMonthRate" value="${CGapproves.topShopMonthRate }" htmlEscape="false" class="input-medium  required" onblur="this.value=gdpMoney(this.value);" onkeyup="gdpMax(this)" maxlength="5" readOnly="true"/>
							<font color="red">*</font>
						</td>
					</tr>
					<tr class="cgisShow">
						<td class="ft_label">居间服务费(元)：</td>
						<td class="ft_content">
							<input type="text" id="mediacyServiceFee" name="mediacyServiceFee" value="${CGapproves.mediacyServiceFee }" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);calculateAmount();"  maxlength="18" htmlEscape="false" class="input-medium required"  />
							<font color="red">*</font>
						</td>
						<%--<td class="ft_label classInterestRate">月息差(元)：</td>--%>
						<%--<td class="ft_content classInterestRate">--%>
							<%--<input type="text" id="interestRateDiff" name="interestRateDiff" value="${CGapproves.interestRateDiff }" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);calculateAmount();"  maxlength="18" htmlEscape="false" class=" input-medium required" readOnly="true" />--%>
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
						<td class="ft_label">总公司批复意见：</td>
						<td class="ft_content" colspan="5">
							<pre class="area-xxlarge pre-style" id="approResultPre" hidden="hidden"></pre>
							<form:textarea path="approResult" rows="5" readonly="true" value="${contrat.approResult}" htmlEscape="false" maxlength="1000" class="area-xxlarge textarea-style " />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</form:form>
	<form:form id="applyLoanStatusForm" modelAttribute="applyLoanStatus" action="#" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<form:hidden path="contractNo" />
		<input type="hidden" id="applyNo" name="applyNo" value="${actTaskParam.applyNo}" />
		<sys:message content="${loanMessage}" />
		<div class="searchInfo">
			<h3 class="searchTitle">财务放款</h3>
			<div class="searchCon">
				<table class="fromTable filter">
					<tr>
						<td class="ft_label">放款状态：</td>
						<td class="ft_content" style="width:30%">
							<form:select path="loanStatus" class="input-medium " disabled="true">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('LOAN_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<a href="#" onclick="refreshLoanStatus()">刷新</a>
						</td>
						<td></td>
						<td>
							<input id="btnLoan" class="btn btn-primary" type="button" onclick="targetLoan('${actTaskParam.applyNo}','${applyLoanStatus.contractNo}')" value="确认已缴费" />
						</td>
					</tr>
					<tr>
						<td colspan="4" style="text-align: right;">
							<input id="btnPush" class="btn btn-primary" type="button" onclick="targetPush()" value="标的推送" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</form:form>
	<!-- 放款结论 -->
	<div id="contractCheckSugg">
		<form:form id="processSuggestionInfoForm" modelAttribute="processSuggestionInfo" action="${ctx}/credit/applyLoanStatus/saveLoanSuggestion?applyLoanStatusId=${applyLoanStatus.id }" method="post" class="form-horizontal">
			<form:hidden path="id" />
			<a id="applyLoanStatusSkipId" target="_parent"></a>
			<%@ include file="/WEB-INF/views/app/credit/workflow/include/actTaskParams.jsp"%>
			<input type="hidden" name="sta" id="sta" value="submit" />
			<div class="searchInfo">
				<h3 class="searchTitle">放款结论</h3>
				<div class="searchCon filter">
					<sys:message content="${suggestionMessage}" />
					<table class="fromTable">
						<tr>
							<td class="ft_label" style="width: 10%;">放款结论：</td>
							<td class="ft_content" style="width: 90%;">
								<input type="radio" name="passFlag" value="success" id="radio_success" class="required" onclick="validate('non_display')">
								<label for="radio_yes">放款成功结束流程</label>
								&nbsp;&nbsp;
								<input type="radio" name="passFlag" value="flow" id="radio_flow" class="required" onclick="validate('non_display')">
								<label for="radio_yes">流标结束流程</label>
								<font color="red">*</font>
								&nbsp;&nbsp;
							</td>
						</tr>
						<tr>
							<td class="ft_label" style="width: 10%;">放款意见：</td>
							<td class="ft_content" style="width: 90%;">
								<pre class="area-xxlarge pre-style required" id="suggestionDescPre"></pre>
								<form:textarea path="suggestionDesc" rows="5" htmlEscape="false" maxlength="1000" class="area-xxlarge textarea-style required " onkeyup="adjustTextareaLength('suggestionDesc','suggestionDescPre')" />
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td colspan="6" style="text-align: right;">
								<input id="btnSubmit" class="btn btn-primary" type="submit" value="提 交" />
							</td>
						</tr>
					</table>
				</div>
			</div>
		</form:form>
	</div>
</body>
</html>