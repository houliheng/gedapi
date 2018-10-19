<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>个人征信信息管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		if ('${readOnly}' == '0') {
			setPageReadOnly();
		}
		//判断是否有银行卡
		isAllowWhite();
		//$("#name").focus();
		 document.getElementById("allCreditAmount").value = outputmoney("${creditCust.allCreditAmount}");
		document.getElementById("allUsedAmount").value = outputmoney("${creditCust.allUsedAmount}");
		document.getElementById("allBalanceAmount").value = outputmoney("${creditCust.allBalanceAmount}");
		document.getElementById("allOverdueAmount").value = outputmoney("${creditCust.allOverdueAmount}");
		document.getElementById("maxCreditAmount").value = outputmoney("${creditCust.maxCreditAmount}");
		document.getElementById("maxUsedAmount").value = outputmoney("${creditCust.maxUsedAmount}"); 
		document.getElementById("recentSixMonthLines").value = outputmoney("${creditCust.recentSixMonthLines}"); 
		document.getElementById("guaranteeCapitalBal").value = outputmoney("${creditCust.guaranteeCapitalBal}"); 
		$("#creditCustForm").validate({
		submitHandler : saveCreditCust,
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});
		if (($("#roleType").val()) == '1') {
			$("#mortgageHouseNumTd1").show();
			$("#mortgageHouseNumTd2").show();
		} else {
			$("#mortgageHouseNumTd1").hide();
			$("#mortgageHouseNumTd2").hide();

		}
	});
	function addRow(list, idx, tpl, row) {
		$(list).append(Mustache.render(tpl, {
		idx : idx,
		delBtn : true,
		row : row
		}));
		$(list + idx).find("select").each(function() {
			$(this).val($(this).attr("data-value"));
		});
		$(list + idx).find("input[type='checkbox'], input[type='radio']").each(function() {
			var ss = $(this).attr("data-value").split(',');
			for (var i = 0; i < ss.length; i++) {
				if ($(this).val() == ss[i]) {
					$(this).attr("checked", "checked");
				}
			}
		});
	}
	function delRow(obj, prefix) {
		var id = $(prefix + "_id");
		var delFlag = $(prefix + "_delFlag");
		if (id.val() == "") {
			$(obj).parent().parent().remove();
		} else if (delFlag.val() == "0") {
			delFlag.val("1");
			$(obj).html("&divide;").attr("title", "撤销删除");
			$(obj).parent().parent().addClass("error");
		} else if (delFlag.val() == "1") {
			delFlag.val("0");
			$(obj).html("&times;").attr("title", "删除");
			$(obj).parent().parent().removeClass("error");
		}
	}

	//根绝角色类型查询人员姓名List
	function findCustByRoleType() {
		$("#custId").empty();
		$("#custId").append("<option value=''>请选择</option>");
		var $custId = $("#s2id_custId>.select2-choice>.select2-chosen");
		$custId.html("请选择");
		$("#idNum").val(null);
		$.post("${ctx}/credit/creditAndLine/creditLineBank/findCustNameByRoleType", {
		roleType : $("#roleType").val(),
		applyNo : '${creditCust.applyNo}'
		}, function(data) {
			$.each(data, function(i, val) {
				$("#custId").append("<option value='"+val["custId"]+"' label='"+val["custName"]+"'>" + val["custName"] + "</option>");
			});
		});

		//房贷笔数
		if (($("#roleType").val()) == '1') {
			$("#mortgageHouseNumTd1").show();
			$("#mortgageHouseNumTd2").show();
		} else {
			$("#mortgageHouseNumTd1").hide();
			$("#mortgageHouseNumTd2").hide();
		}

	}

	//保存银行卡流水信息信息
	function saveCreditCust() {
		//如果人员类型不为借款人，保存前remove掉房贷笔数
		if (($("#roleType").val()) != '1') {
			$("#mortgageHouseNum").remove();
		}
		fmtBigdecimalFunc();
		var custName = $("#custId").find("option:selected").attr("label");
		$("#custName").val(custName);
		var allCreditCardNum = $("#allCreditCardNum").val();
		if (allCreditCardNum <= 0) {
			$("tr[name='bankInfoTr']").remove();
			$("#overdueCardNum").remove();
			$("#unusedCardNum").remove();
			$("span[name='bankInfoSpan']").remove();
		}
		loading();
		top.$.jBox.tip.mess = null;
		var creditCust = $("#creditCustForm").serializeJson();
		$.post("${ctx}/credit/creditAndLine/creditCust/save", creditCust, function(data) {
			if (data) {
			closeTip();
				if (data.status == 1) {//保存成功
					alertx(data.message, function() {
						parent.$.loadDiv("creditCustDiv", "${ctx }/credit/creditAndLine/creditCust/list", {
							applyNo : '${creditCust.applyNo }'
						}, "post");
						closeJBox();
					});
				} else {
					alertx(data.message);
				}
			}
		});
	}

	function loadInNum() {
		$("#idNum").empty();
		$.post("${ctx}/credit/creditAndLine/creditCust/findidNumByCustId", {
			custId : $("#custId").val()
		}, function(data) {
			if (data) {
				if (data.status == 'success') {
					$("#idNum").val(data.idNum);
					$("#idNum").css("background-color","");
					$("#idType").val(data.idType);
				} else {
					alertx("查询身份信息失败，请联系管理员！");
				}
			}
		});
	}
	//信用卡总数量为0时，不可填写银行卡信息
	function isAllowWhite() {
		var allCreditCardNum = $("#allCreditCardNum").val();
		if (allCreditCardNum <= 0) {
			$("tr[name='bankInfoTr']").hide();
			$("#overdueCardNum").hide();
			$("#unusedCardNum").hide();
			$("span[name='bankInfoSpan']").hide();
		} else {
			$("tr[name='bankInfoTr']").show();
			$("#overdueCardNum").show();
			$("#unusedCardNum").show();
			$("span[name='bankInfoSpan']").show();
		}
	}
	
	function fmtBigdecimalFunc() {
		var allCreditAmount = $("#allCreditAmount").val().replace(/,/g, "");
		$("#allCreditAmount").val(allCreditAmount);
		
		var allUsedAmount = $("#allUsedAmount").val().replace(/,/g, "");
		$("#allUsedAmount").val(allUsedAmount);
		
		var allBalanceAmount = $("#allBalanceAmount").val().replace(/,/g, "");
		$("#allBalanceAmount").val(allBalanceAmount);
		
		var allOverdueAmount = $("#allOverdueAmount").val().replace(/,/g, "");
		$("#allOverdueAmount").val(allOverdueAmount);
		
		var maxCreditAmount = $("#maxCreditAmount").val().replace(/,/g, "");
		$("#maxCreditAmount").val(maxCreditAmount);
		
		var maxUsedAmount = $("#maxUsedAmount").val().replace(/,/g, "");
		$("#maxUsedAmount").val(maxUsedAmount);
		
		var recentSixMonthLines = $("#recentSixMonthLines").val().replace(/,/g, "");
		$("#recentSixMonthLines").val(recentSixMonthLines);
		
		var recentSixMonthMoney = $("#recentSixMonthMoney").val().replace(/,/g, "");
		$("#recentSixMonthMoney").val(recentSixMonthMoney);
		
		var guaranteeCapitalBal = $("#guaranteeCapitalBal").val().replace(/,/g, "");
		$("#guaranteeCapitalBal").val(guaranteeCapitalBal);
	}
	
	function changeRequired(value){
		if(value == '1'){//是白户
			$("input").removeClass("required");
			$("select").removeClass("required");
			$("input").css("background-color","");
			$(".select2-choice").css("background-color","");
			$("#roleType").addClass("required");
			$("#custId").addClass("required");
			$("#idNum").addClass("required");
			$("#isWhite").addClass("required");
		}else{
			$(".input-medium").addClass("required");
			$("#btnSubmit").removeClass("required");
			$("#companyPhoneNo").removeClass("required");
			$("select").addClass("required");
		}
	}
	
</script>
</head>
<body>
	<!-- 个人征信信息 -->
	<!-- BEGIN -->
	<form:form id="creditCustForm" modelAttribute="creditCust" action="" method="post" class="form-horizontal">
		<div class="searchInfo">
			<h3 class="searchTitle">个人征信信息</h3>
			<div class="searchCon">
				<form:hidden path="id" />
				<form:hidden path="applyNo" />
				<form:hidden path="custName" />
				<form:hidden path="idType" />
				<sys:message content="${message}" />
				<table class="fromTable filter">
					<tr>
						<td class="ft_label">人员类型：</td>
						<td class="ft_content">
							<form:select id="roleType" path="roleType" class="input-medium required" cssStyle="width:177px;" onchange="findCustByRoleType();">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('ROLE_TYPE_P') }" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
						</td>
						<td class="ft_label">姓名：</td>
						<td class="ft_content">
							<select id="custId" name="custId" class="input-medium required" style="width: 177px;" onchange="loadInNum();">
								<option value="" label="" />
								<c:forEach items="${custNameMap }" var="cust">
									<c:if test="${cust.custId eq creditCust.custId }">
										<option value="${cust.custId }" label="${cust.custName}" selected="selected">${cust.custName}</option>
									</c:if>
									<c:if test="${cust.custId ne creditCust.custId }">
										<option value="${cust.custId }" label="${cust.custName}">${cust.custName}</option>
									</c:if>
								</c:forEach>
							</select>
						</td>
						<td class="ft_label">证件号：</td>
						<td class="ft_content">
							<input type="text" id="idNum" name="idNum" maxlength="18" class="input-medium required card" value="${creditCust.idNum }" />
						</td>
					</tr>
					<tr>
						<td class="ft_label">是否白户：</td>
						<td class="ft_content">
							<form:select id="isWhite" path="isWhite" maxlength="4" class="input-medium required" cssStyle="width:177px;" onchange="changeRequired(this.value);">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('yes_no') }" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
						</td>
						<td></td><td></td>
					</tr>
					<tr>
						<td class="ft_label">本人年内查询次数：</td>
						<td class="ft_content">
							<input type="text" id="queryNumYear" name="queryNumYear" maxlength="3" class="input-medium number required" value="${creditCust.queryNumYear }" />
						</td>
						<td class="ft_label">本人季内查询次数：</td>
						<td class="ft_content">
							<input type="text" id="queryNumSeason" name="queryNumSeason" maxlength="3"  class="input-medium number required" value="${creditCust.queryNumSeason }" />
						</td>
						<td class="ft_label">本人月内查询次数：</td>
						<td class="ft_content">
							<input type="text" id="queryNumMonth" name="queryNumMonth" maxlength="3"  class="input-medium number required" value="${creditCust.queryNumMonth }" />
						</td>
					</tr>
					<tr>
						<td class="ft_label">查询时间与报告时间间隔：</td>
						<td class="ft_content">
							<form:select id="queryReportDateMinus" path="queryReportDateMinus" class="input-medium required" cssStyle="width:177px;">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('QUERY_REPORT_DATE_MINUS') }" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
						</td>
						<td class="ft_label">征信信用历史：</td>
						<td class="ft_content">
							<form:select id="creditMonths" path="creditMonths" class="input-medium required" cssStyle="width:177px;">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('CREDIT_MONTHS') }" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
						</td>
						<td id="mortgageHouseNumTd1" class="ft_label">房贷笔数：</td>
						<td id="mortgageHouseNumTd2" class="ft_content">
							<input type="text" id="mortgageHouseNum" name="mortgageHouseNum"  maxlength="3" class="input-medium number required" value="${creditCust.mortgageHouseNum}" />
						</td>
					</tr>
					<tr>
						<td class="ft_label">征信人手机号码：</td>
						<td class="ft_content">
							<input type="text" id="mobileNo" name="mobileNo" maxlength="11" class="input-medium mobile" value="${creditCust.mobileNo }" />
						</td>
						<td class="ft_label">征信人单位号码：</td>
						<td class="ft_content">
							<input type="text" id="companyPhoneNo" maxlength="15" name="companyPhoneNo" class=" simplePhone input-medium" value="${creditCust.companyPhoneNo }" />
						</td>
						<td class="ft_label">打印日期：</td>
						<td class="ft_content">
							<input id="printingDate" name="printingDate" type="text" maxlength="20" class="input-medium Wdate " value="<fmt:formatDate value="${creditCust.printingDate }" pattern="yyyy-MM-dd"/>"
								onclick="WdatePicker({onpicked:dateWhite(this),maxDate:new Date(),dateFmt:'yyyy-MM-dd'});" />
						</td>
					</tr>
				</table>
			</div>
		</div>
		<!-- END -->
		<!-- 个人信用卡信息 -->
		<!-- BEGIN -->
		<div id="creditCustBankCardInfoDiv" class="searchInfo">
			<h3 class="searchTitle">个人信用卡信息</h3>
			<div class="searchCon">
				<table class="fromTable filter">
					<tr name="bankInfoTr">
						<td class="ft_label">发卡法人代表机构数：</td>
						<td class="ft_content">
							<input type="text" id="bankLegalNum" name="bankLegalNum" maxlength="3" class="input-medium number required" value="${creditCust.bankLegalNum}" />
						</td>
						<td class="ft_label">
							<span name="bankInfoSpan">贷记卡逾期月份数：</span>
						</td>
						<td class="ft_content">
							<input type="text" id="debitCardsLimit" name="debitCardsLimit" maxlength="3" class="input-medium number required" value="${creditCust.debitCardsLimit}" />
						</td>
						<td class="ft_label" name="bankInfoTd">
							<span name="bankInfoSpan">首张贷记卡发卡月份：</span>
						</td>
						<td class="ft_content" >
							<input id="firstDebitCardsMonth" name="firstDebitCardsMonth" type="text" maxlength="20" class="input-medium Wdate " value="<fmt:formatDate value="${creditCust.firstDebitCardsMonth }" pattern="yyyy-MM"/>"
								onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy-MM'});" />
						</td>
					</tr>
					<tr>
						<td class="ft_label">总数量：</td>
						<td class="ft_content">
							<input type="text" id="allCreditCardNum" name="allCreditCardNum" maxlength="3" class="input-medium number required" value="${creditCust.allCreditCardNum}" onchange="isAllowWhite();" />
						</td>
						<td class="ft_label">
							<span name="bankInfoSpan">逾期卡数：</span>
						</td>
						<td class="ft_content">
							<input type="text" id="overdueCardNum" name="overdueCardNum" maxlength="3" class="input-medium number required" value="${creditCust.overdueCardNum}" />
						</td>
						<td class="ft_label" name="bankInfoTd">
							<span name="bankInfoSpan">未使用卡数量（不含美元账户）：</span>
						</td>
						<td class="ft_content" name="bankInfoTd">
							<input type="text" id="unusedCardNum" name="unusedCardNum" maxlength="3" class="input-medium number required" value="${creditCust.unusedCardNum}" />
						</td>
					</tr>
					<tr name="bankInfoTr">
						<td class="ft_label">异常卡数量：</td>
						<td class="ft_content">
							<input type="text" id="abnormalCardNum" name="abnormalCardNum" maxlength="3" class="input-medium number required" value="${creditCust.abnormalCardNum}" />
						</td>
						<td class="ft_label">总授信额度(元)：</td>
						<td class="ft_content">
							<input type="text" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" id="allCreditAmount" name="allCreditAmount" maxlength="18" class="input-medium required" value="${creditCust.allCreditAmount}" />
						</td>
						<td class="ft_label">总使用额度(元)：</td>
						<td class="ft_content">
							<input type="text" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" id="allUsedAmount" name="allUsedAmount" maxlength="18" class="input-medium  required" value="${creditCust.allUsedAmount}" />
						</td>
					</tr>
					<tr name="bankInfoTr">
						<td class="ft_label">总余额(元)：</td>
						<td class="ft_content">
							<input type="text" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" id="allBalanceAmount" maxlength="18" name="allBalanceAmount" class="input-medium  required" value="${creditCust.allBalanceAmount}" />
						</td>
						<td class="ft_label">逾期总额(元)：</td>
						<td class="ft_content">
							<input type="text" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" id="allOverdueAmount" maxlength="18" name="allOverdueAmount" class="input-medium required" value="${creditCust.allOverdueAmount}" />
						</td>
						<td class="ft_label">信用卡使用占比：</td>
						<td class="ft_content">
							<form:select id="usedCreditCardRate" path="usedCreditCardRate" maxlength="4" class="input-medium required" cssStyle="width:177px;">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('USED_CREDIT_CARD_RATE') }" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
					</tr>
					<tr name="bankInfoTr">
						<td class="ft_label">最高授信额度(元)：</td>
						<td class="ft_content">
							<input type="text" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" id="maxCreditAmount" name="maxCreditAmount" maxlength="18" class=" input-medium required" value="${creditCust.maxCreditAmount}" />
						</td>
						<td class="ft_label">最高使用额度(元)：</td>
						<td class="ft_content">
							<input type="text" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" id="maxUsedAmount" name="maxUsedAmount" maxlength="18" class="input-medium required" value="${creditCust.maxUsedAmount}" />
						</td>
						<td></td>
						<td></td>
					</tr>
					<tr name="bankInfoTr">
						<td class="ft_label">连续逾期次数：</td>
						<td class="ft_content">
							<input type="text" id="continueOverdueNum" name="continueOverdueNum" maxlength="3" class="input-medium number required" value="${creditCust.continueOverdueNum}" />
						</td>
						<td class="ft_label">累计逾期次数：</td>
						<td class="ft_content">
							<input type="text" id="allOverdueNum" name="allOverdueNum" maxlength="3" class="input-medium number required" value="${creditCust.allOverdueNum}" />
						</td>
						<td class="ft_label">信用卡状态：</td>
						<td class="ft_content">
							<form:select id="creditCardStatus" path="creditCardStatus" class="input-medium required" cssStyle="width:177px;">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('CREDIT_CARD_STATUS') }" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
						</td>
					</tr>
					<tr name="bankInfoTr">
						<td class="ft_label">最近6个月平均使用额度：</td>
						<td class="ft_content">
							<input type="text" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" id="recentSixMonthLines" name="recentSixMonthLines" maxlength="18" class=" input-medium required" value="${creditCust.recentSixMonthLines}" />
						</td>
						<td class="ft_label">
							<span name="bankInfoSpan">贷记卡最长逾期月数：</span>
						</td>
						<td class="ft_content">
							<input type="text" id="debitLongLimit" name="debitLongLimit" maxlength="3" class="input-medium number required" value="${creditCust.debitLongLimit}" />
						</td>
						<td></td>
						<td></td>
					</tr>
				</table>
			</div>
		</div>
		<div id="creditCustLoanInfoDiv" class="searchInfo">
		<div class="searchInfo">
			<h3 class="searchTitle">个人贷款信息</h3>
			<div class="searchCon">
				<table class="fromTable filter">
					<tr>
						<td class="ft_label">个人住房贷款笔数：</td>
						<td class="ft_content">
							<input type="text" id="personHouseLoadNum" name="personHouseLoadNum" maxlength="3" class="input-medium number required" value="${creditCust.personHouseLoadNum}" />
						</td>
						<td class="ft_label">
							<span>个人商用（包括商住两用）房贷款笔数：</span>
						</td>
						<td class="ft_content">
							<input type="text" id="personCommerHouseLoan" name="personCommerHouseLoan" maxlength="3" class="input-medium number required" value="${creditCust.personCommerHouseLoan}" />
						</td>
						<td class="ft_label" name="bankInfoTd">
							<span>其他贷款笔数：</span>
						</td>
						<td class="ft_content" >
							<input type="text" id="otherLoanNum" name="otherLoanNum" maxlength="3" class="input-medium number required" value="${creditCust.otherLoanNum}" />
						</td>
					</tr>
					<tr>
						<td class="ft_label">贷款法人代表机构数：</td>
						<td class="ft_content">
							<input type="text" id="loanLegalOrg" name="loanLegalOrg" maxlength="3" class="input-medium number required" value="${creditCust.loanLegalOrg}" />
						</td>
						<td class="ft_label">
							<span>首笔贷款发放月份：</span>
						</td>
						<td class="ft_content">
							<input id="firstLoanMonth" name="firstLoanMonth" type="text" maxlength="20" class="input-medium Wdate " value="<fmt:formatDate value="${creditCust.firstLoanMonth }" pattern="yyyy-MM"/>"
								onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy-MM'});" />
						</td>
						<td class="ft_label" name="bankInfoTd">
							<span>贷款逾期月份数：</span>
						</td>
						<td class="ft_content" >
							<input type="text" id="loanLimitMonth" name="loanLimitMonth" maxlength="3" class="input-medium number required" value="${creditCust.loanLimitMonth}" />
						</td>
					</tr>
					<tr>
						<td class="ft_label">贷款最长逾期月份数：</td>
						<td class="ft_content">
							<input type="text" id="loanLongLimitMonth" name="loanLongLimitMonth" maxlength="3" class="input-medium number required" value="${creditCust.loanLongLimitMonth}" />
						</td>
						<td class="ft_label">
							<span>最近6个月平均还款金额：</span>
						</td>
						<td class="ft_content">
							<input type="text" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" id="recentSixMonthMoney" maxlength="18" name="recentSixMonthMoney" class="input-medium  required" value="${creditCust.recentSixMonthMoney}" />
						</td>
						<td class="ft_label" name="bankInfoTd">
							<span>担保笔数：</span>
						</td>
						<td class="ft_content" >
							<input type="text" id="guaranteeNum" name="guaranteeNum" maxlength="3" class="input-medium number required" value="${creditCust.guaranteeNum}" />
						</td>
					</tr>
					<tr>
						<td class="ft_label">担保本金余额：</td>
						<td class="ft_content">
							<input type="text" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" id="guaranteeCapitalBal" maxlength="18" name="guaranteeCapitalBal" class="input-medium  required" value="${creditCust.guaranteeCapitalBal}" />
						</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td colspan="6">
							<div class="form-actions">
								<c:if test="${readOnly ne '0' }">
								<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />&nbsp;
								</c:if>
								<input id="btnCancel" class="btn" type="button" value="返 回" onclick="closeJBox();" />
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	</form:form>
</body>
</html>