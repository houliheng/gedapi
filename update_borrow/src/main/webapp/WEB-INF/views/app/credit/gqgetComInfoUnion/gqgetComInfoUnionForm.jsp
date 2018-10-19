<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>冠e通录入接口信息管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		/* adjustTextareaLength('intrOfMainborrower', 'preIntrOfMainborrower'); */
		adjustTextareaLength('introductionOfCompany', 'preIntrOfCompany');
		adjustTextareaLength('mixLoanUsage', 'preMixLoanUsage');
		adjustTextareaLength('introductionOfComProduction', 'preIntrOfComProduction');
		adjustTextareaLength('operateActuality', 'preOperateActuality');
		adjustTextareaLength('other', 'preOther');
		adjustTextareaLength('sourceOfPepayment1', 'preSourceOfDepayment1');
		adjustTextareaLength('sourceOfPepayment2', 'preSourceOfDepayment2');
		adjustTextareaLength('sourceOfPepayment3', 'preSourceOfDepayment3');
		adjustTextareaLength('sourceOfPepayment4', 'preSourceOfDepayment4');
		adjustTextareaLength('auditOpintion', 'preAuditOpintion');
		
		adjustTextareaLength('borrowAndMatePunish', 'preBorrowAndMatePunish');
		adjustTextareaLength('borrowInvolveInfo', 'preBorrowInvolveInfo');
		adjustTextareaLength('borrowCrimaAdminPunish', 'preBorrowCrimaAdminPunish');
		adjustTextareaLength('borrowNewLoanBlance', 'preBorrowNewLoanBlance');
		adjustTextareaLength('creditCompany', '1pre2');
		 if('${companyInfo.isAddrResi}' == "1"){
			var isAddrResi = document.getElementById("radio_comYes");
			isAddrResi.checked = "checked";
		}
		if('${companyInfo.isAddrResi}' == "0"){
			var isAddrResi = document.getElementById("radio_comNo");
			isAddrResi.checked = "checked";
		}
		if('${custType}' == "2"){
			$(".personCustType").hide();
			$(".creditCompanyCustType").show();
		}
		$("#inputForm").validate({
		submitHandler : function(form) {
			saveForm();
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});
		$("#warehouse").hide();
		$("#comId").hide();
		if (!checkIsNull('${productType}')) {
			if ('${productType}' == '1') {//信用
			//	$("#intrOfComProductionId").hide();
				$("#equip").hide();
				$("#car").hide();
				$("#house").hide();
				$("#gqgetGuarantorCompanyInfo").hide();
				$("#operateActualityId").hide();
				$("#propertyType").show();
			} else if ('${productType}' == '2') {//抵押
				$("#operateActualityId").hide();
			//	$("#intrOfComProductionId").hide();
				$("#gqgetGuarantorCompanyInfo").hide();
				$("#otherId").hide();
				$("#assetCar").hide();
				$("#assetHouse").hide();
				/* $("#overallRanking").removeClass("required"); */
				$("#isLoan").removeClass("required");
				$("#isOverdue").removeClass("required");
				$("#sourceOfCreditRegistries").removeClass("required");
				var a = document.getElementById("zcxi");
				$(a).html("抵押信息")
				$("#propertyType").hide();
			} else if ('${productType}' == '4') {//混合
				/* $("#mainBorrowerId").hide(); */
				$("#otherId").hide();
				$("#assetCar").hide();
				$("#assetHouse").hide();
				/* $("#overallRanking").removeClass("required"); */
				$("#propertyType").hide();
				var a = document.getElementById("zcxi");
				$(a).html("抵押信息")
			}else if ('${productType}' == '7') {
				$("#operateActualityId").hide();
				$("#house").hide();
				$("#car").hide();
				$("#warehouse").show();
				$("#comId").show();
				$("#equip").hide();
				$("#gqgetGuarantorCompanyInfo").hide();
			}
		}

		$("#otherId").hide();
		$("#assetCar").hide();
		$("#assetHouse").hide();
		if (!checkIsNull('${productType}')) {
			if ('${productType}' == '1' || '${productType}' == '7') {
				if (!checkIsNull('${gqgetComInfo.propertyHouse}') && '${gqgetComInfo.propertyHouse}' == '1') {
					$("#propertyHouseRadio").val("1");
					$("#assetHouse").show();
				}
				if (!checkIsNull('${gqgetComInfo.propertyCar}') && '${gqgetComInfo.propertyCar}' == '1') {
					$("#propertyCarRadio").val("1");
					$("#assetCar").show();
				}
				if (!checkIsNull('${gqgetComInfo.propertyOther}') && '${gqgetComInfo.propertyOther}' == '1') {
					$("#propertyOtherRadio").val("1");
					$("#otherId").show();
				}
			}
		}

		$.loadDiv("gqgetGuarantorInfoList", "${ctx }/credit/guarantorInfo/listUnion", {
			applyNo : '${actTaskParam.applyNo}',
			approId : '${gqgetComInfo.approveId}'
		}, "post");
		$.loadDiv("gqgetGuarantorCompanyInfo", "${ctx }/credit/mortgagedCompanyUnion/list", {
			applyNo : '${actTaskParam.applyNo}',
			approveId : '${gqgetComInfo.approveId}'
		}, "post");
		$.loadDiv("house", "${ctx }/credit/mortgageHouseInfo/list?gqgetFlag=2", {
			applyNo : '${actTaskParam.applyNo}'
		}, "post");
		$.loadDiv("assetHouse", "${ctx }/credit/gqgetAssetHouseUnion/list", {
		applyNo : '${actTaskParam.applyNo}',
		approveId : '${gqgetComInfo.approveId}'
		}, "post");
		$.loadDiv("car", "${ctx }/credit/mortgageCarInfo/list?gqgetFlag=2", {
			applyNo : '${actTaskParam.applyNo}'
		}, "post");
		$.loadDiv("assetCar", "${ctx }/credit/gqgetAssetCarUnion/list", {
		gqgetApplyNo : '${actTaskParam.applyNo}',
		approveId : '${gqgetComInfo.approveId}'
		}, "post");
		$.loadDiv("equip", "${ctx }/credit/gqgetComInfoUnion/mortEquip", {
			applyNo : '${actTaskParam.applyNo}',
			approveId : '${gqgetComInfo.approveId}'
		}, "post");
		$.loadDiv("warehouse", "${ctx }/credit/gqgetComInfoUnion/warehouseList", {
			applyNo : '${actTaskParam.applyNo}',
			approId : '${gqgetComInfo.approveId}'
		}, "post");
	});

	function saveForm() {
		loading();
		top.$.jBox.tip.mess = null;
		var formJson = $("#inputForm").serializeJson();
		$.post("${ctx}/credit/gqgetComInfoUnion/save", formJson, function(data) {
			if (data) {
				closeTip();
				if (data.status == 1) {//保存成功
					alertx(data.message, function() {
					});
				} else {
					alertx(data.message);
				}
			}
		});
	}



	
	
	
	
	function queryAssetCar(id) {
		openJBox('queryAssetCarBox', "${ctx}/credit/gqgetAssetCarUnion/form?readOnly=true", "编辑车辆资产信息", $(window).width() - 400, 300, {
			id : id
		});
	}

	
	function changePropertyHouse(property) {
		if ('1' == property) {//已选中，取消
			$("#propertyHouseRadio").attr("checked", false);
			$("#propertyHouseRadio").val("0");
			$("#propertyHouse").val("0");
			$("#assetHouse").hide();
		} else {
			$("#propertyHouseRadio").val("1");
			$("#assetHouse").show();
			$("#propertyHouse").val("1");
		}
	}
	function changePropertyCar(property) {
		if ("1" == property) {//已选中，取消
			$("#propertyCarRadio").attr("checked", false);
			$("#propertyCarRadio").val("0");
			$("#propertyCar").val("0");
			$("#assetCar").hide();
		} else {
			$("#propertyCarRadio").val("1");
			$("#assetCar").show();
			$("#propertyCar").val("1");
		}
	}
	function changePropertyOther(property) {
		if ("1" == property) {//已选中，取消
			$("#propertyOtherRadio").attr("checked", false);
			$("#propertyOtherRadio").val("0");
			$("#propertyOther").val("0");
			$("#otherId").hide();
		} else {
			$("#propertyOtherRadio").val("1");
			$("#otherId").show();
			$("#propertyOther").val("1");
		}
	}
	function checkProperty() {
		if (!checkIsNull('${productType}')) {
			if ('${productType}' == '1') {
				if (!($("#propertyHouse").val() == '1' || $("#propertyCar").val() == '1' || $("#propertyOther").val() == '1')) {
					alertx("资产信息必填");
					return false;
				}
			}
		}
	}
</script>
<c:if test="${true == readOnly}">
	<!-- 查看详细信息时生效 -->
	<script type="text/javascript">
		$(document).ready(function() {
			$("input").attr("readOnly", "readOnly");
			$("textarea").attr("readOnly", "readOnly");
			disableSelect2();
			$("div[class='searchButton']").remove();
			$("font").remove();//由于页面的特殊性，所以这里直接将所有的fongt节点删除
		});
	</script>
</c:if>
</head>
<body>
	<form:form id="inputForm" modelAttribute="gqgetComInfo" action="${ctx}/credit/gqgetComInfoUnion/save" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<form:hidden path="applyNo" value="${gqgetComInfo.applyNo}" />
		<form:hidden path="productType" value="${productType}" />
		<sys:message content="${message}" />
		<!-- <pre class="textareaWidth pre-style" id="preIntrOfMainborrower"></pre> -->
		<pre class="textareaWidth pre-style" id="preIntrOfCompany"></pre>
		<pre class="textareaWidth pre-style" id="preOperateActuality"></pre>
		<pre class="textareaWidth pre-style" id="preMixLoanUsage"></pre>
		<pre class="textareaWidth pre-style" id="preIntrOfComProduction"></pre>
		<pre class="textareaWidth pre-style" id="preOther"></pre>
		<pre class="textareaWidth pre-style" id="preSourceOfDepayment1"></pre>
		<pre class="textareaWidth pre-style" id="preSourceOfDepayment2"></pre>
		<pre class="textareaWidth pre-style" id="preSourceOfDepayment3"></pre>
		<pre class="textareaWidth pre-style" id="preSourceOfDepayment4"></pre>
		<pre class="textareaWidth pre-style" id="preAuditOpintion"></pre>
		
		<pre class="textareaWidth pre-style" id="preBorrowAndMatePunish"></pre>
		<pre class="textareaWidth pre-style" id="preBorrowInvolveInfo"></pre>
		<pre class="textareaWidth pre-style" id="preBorrowCrimaAdminPunish"></pre>
		<pre class="textareaWidth pre-style" id="preBorrowNewLoanBlance"></pre>
		<pre class="textareaWidth pre-style" id="1pre2"></pre>
		<!-- 信用借款 -->
		<div class="searchInfo" id="creditDiv">
			<h3 class="searchTitle">新增标的信审信息</h3>
			<div class="searchCon">
				<table class="fromTable filter">
					<%-- <tr>
						<td class="ft_label">
							<font style="color: red">*</font>
							综合评级：
						</td>
						<td class="ft_content">
							<form:select path="overallRanking" class="input-medium required" cssStyle="width:177px;">
								<form:option value="" label="请选择" />
								<form:options items="${fns:getDictList('STAR_LEVEL')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							&nbsp;颗星
						</td>
					</tr> --%>
					<%-- <tr id="mainBorrowerId">
						<td class="ft_label">
							<font style="color: red">*</font>
							借款人介绍：
						</td>
						<td class="ft_content">
							<form:textarea path="intrOfMainborrower" htmlEscape="false" rows="4" maxlength="1000" class="textareaWidth textarea-style required checkNumberForty" onkeyup="adjustTextareaLength('intrOfMainborrower', 'preIntrOfMainborrower');" />
							<br>
							<font style="color: red">最少输入40字，最多可输入1000字</font>
						</td>
					</tr> --%>
					<tr>
						<td class="ft_label">
							<font style="color: red">*</font>
							企业信息：
						</td>
						<td class="ft_content" colspan="5">
							<form:textarea path="introductionOfCompany" htmlEscape="false" rows="4" maxlength="1000" class="textareaWidth textarea-style required checkNumberForty" onkeyup="adjustTextareaLength('introductionOfCompany', 'preIntrOfCompany');" />
							<br>
							<font style="color: red">最少输入40字，最多可输入1000字</font>
						</td>
					</tr>
					<tr id="operateActualityId">
						<td class="ft_label">
							<font style="color: red">*</font>
							经营状况：
						</td>
						<td class="ft_content" colspan="5">
							<form:textarea path="operateActuality" htmlEscape="false" rows="4" maxlength="1000" class="textareaWidth textarea-style required checkNumberForty" onkeyup="adjustTextareaLength('operateActuality', 'preOperateActuality');" />
							<br>
							<font style="color: red">最少输入40字，最多可输入1000字</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">
							<font style="color: red">*</font>
							借款用途：
						</td>
						<td class="ft_content" colspan="5">
							<form:textarea path="mixLoanUsage" htmlEscape="false" rows="4" maxlength="1000" class="textareaWidth textarea-style required checkNumberTwenty" onkeyup="adjustTextareaLength('mixLoanUsage', 'preMixLoanUsage');" />
							<br>
							<font style="color: red">最少输入20字，可输入1000字</font>
						</td>
					</tr>
					<tr id="intrOfComProductionId">
						<td class="ft_label">
							<font style="color: red">*</font>
							企业产品介绍：
						</td>
						<td class="ft_content" colspan="5">
							<form:textarea path="introductionOfComProduction" htmlEscape="false" rows="4" maxlength="1000" class="textareaWidth textarea-style required checkNumberForty" onkeyup="adjustTextareaLength('introductionOfComProduction', 'preIntrOfComProduction');" />
							<br>
							<font style="color: red">最少输入40字，最多可输入1000字</font>
						</td>
					</tr>
					<tr class="personCustType">
						<td class="ft_label">
							<font style="color: red">*</font>
							借款人及配偶受行政处罚情况：
						</td>
						<td class="ft_content" colspan="5">
							<form:textarea path="borrowAndMatePunish" htmlEscape="false" rows="4" maxlength="30" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('borrowAndMatePunish', 'preBorrowAndMatePunish');" />
							<br>
							<!-- <font style="color: red">最少输入40字，最多可输入1000字</font> -->
						</td>
					</tr>
					<tr class="personCustType">
						<td class="ft_label">
							<font style="color: red">*</font>
							借款人涉诉情况：
						</td>
						<td class="ft_content" colspan="5">
							<form:textarea path="borrowInvolveInfo" htmlEscape="false" rows="4" maxlength="30" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('borrowInvolveInfo', 'preBorrowInvolveInfo');" />
							<br>
							<!-- <font style="color: red">最少输入40字，最多可输入1000字</font> -->
						</td>
					</tr>
					<tr class="personCustType">
						<td class="ft_label">
							<font style="color: red">*</font>
							借款人受刑事、行政处罚情况：
						</td>
						<td class="ft_content" colspan="5">
							<form:textarea path="borrowCrimaAdminPunish" htmlEscape="false" rows="4" maxlength="30" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('borrowCrimaAdminPunish', 'preBorrowCrimaAdminPunish');" />
							<br>
							<!-- <font style="color: red">最少输入40字，最多可输入1000字</font> -->
						</td>
					</tr>
					<tr>
						<td class="ft_label">
							<font style="color: red">*</font>
							借款人在其他网贷平台的贷款情况：
						</td>
						<td class="ft_content" colspan="5">
							<form:textarea path="borrowNewLoanBlance" htmlEscape="false" rows="4" maxlength="1000" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('borrowNewLoanBlance', 'preBorrowNewLoanBlance');" />
							<br>
							<!-- <font style="color: red">最少输入40字，最多可输入1000字</font> -->
						</td>
					</tr>
					<c:if test="${actTaskParam.taskDefKey !='utask_zgsjlsh' || actTaskParam.taskDefKey != 'utask_zgsfksh' || actTaskParam.taskDefKey != 'utask_fgsfy' ||actTaskParam.taskDefKey != 'utask_dqfkjlsh' || actTaskParam.taskDefKey != 'utask_dqfkzysh' || actTaskParam.taskDefKey != 'utask_qyjlsh' || actTaskParam.taskDefKey != 'utask_qyfksh' || actTaskParam.taskDefKey != 'utask_fgsjlsh' || actTaskParam.taskDefKey != 'utask_fgsfksh'}">
						<tr class="creditCompanyCustType" style="display:none;">
							<td class="ft_label" style="width: 15%;">
								法定代表人信用信息：
							</td>
							<td class="ft_content" style="width: 85%;">
								<form:textarea path="creditCompany" htmlEscape="false" rows="4" maxlength="1000" class="textareaWidth textarea-style required" onkeyup="adjustTextareaLength('creditCompany','1pre2');" placeholder="本栏内容需脱敏处理，禁止录入企业和自然人全称"/>
								<font color="red">*</font>
								<br />
							</td>
						</tr>
					</c:if>
					<tr>
					<tr>
						<td class="ft_label">
							<font style="color:red">*</font>
							企业银行贷款情况：
						</td>
						<td colspan="5">
							<table class="table table-striped table-bordered table-condensed">
								<thead>
									<tr>
										<th>是否有贷款记录</th>
										<th>共几笔贷款记录</th>
										<th>当前是否有逾期</th>
										<th>企业征信记录等级</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>
											<form:select path="isLoan" class="input-medium required" cssStyle="width:177px;">
												<form:option value="" label="请选择" />
												<form:options items="${fns:getDictList('IS_HAVE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
											</form:select>
										</td>
										<td>
											<form:input type="text" path="loanRecode" htmlEscape="false" maxlength="3" class="input-medium required" />
										</td>
										<td>
											<form:select path="isOverdue" class="input-medium required" cssStyle="width:177px;">
												<form:option value="" label="请选择" />
												<form:options items="${fns:getDictList('IS_HAVE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
											</form:select>
										</td>
										<td>
											<form:select path="sourceOfCreditRegistries" class="input-medium required" cssStyle="width:177px;">
												<form:option value="" label="请选择" />
												<form:options items="${fns:getDictList('CREDIT_REGISTRIES_LEVEL')}" itemLabel="label" itemValue="value" htmlEscape="false" />
											</form:select>
										</td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
					<tr>
						<%-- <td class="ft_label">借款人在其他网贷平台借款余额(元)：</td>
						<td class="ft_content">
							<form:input  id="borrowNewLoanBlance"  path="borrowNewLoanBlance" maxlength="18" htmlEscape="false" class=" input-medium required" />
							<font color="red">*</font>
						</td> --%>
						<td class="ft_label personCustType">在平台逾期次数：</td>
						<td class="ft_content personCustType">
							<form:input  id="platformOverdueTime"  path="platformOverdueTime" maxlength="18" htmlEscape="false" class=" input-medium number required" />
							<font color="red">*</font>
						</td>
						<td class="ft_label personCustType">在平台逾期金额：</td>
						<td class="ft_content personCustType">
							<form:input  id="platformOverdueMoney"  path="platformOverdueMoney" maxlength="18" htmlEscape="false" class=" input-medium required" />
							<font color="red">*</font>
						</td>
					</tr>
				</table>
			</div>
			<div id="comId">
				<h3 class="searchTitle">公司资料</h3>
				<table class="fromTable filter">
					<tr>
						<td class="ft_label">企业全称或简称：</td>
						<td class="ft_content">
							<input type="text" id="busiRegName" name="busiRegName" class="input-medium" readonly="true" value="${companyInfo.busiRegName}" />
						</td>
						<td class="ft_label">统一社会信用代码：</td>
						<td class="ft_content">
							<input type="text" id="busiRegName" name="busiRegName" class="input-medium" readonly="true" value="${companyInfo.busiRegName}" />
						</td>
						<td class="ft_label">法定代表人名称：</td>
						<td class="ft_content">
							<input type="text" id="corporationName" name="corporationName" class="input-medium" readonly="true" value="${companyInfo.corporationName}" />
						</td>
					</tr>
					
					<tr>
						<td class="ft_label">成立时间：</td>
						<td class="ft_content">
							<input type="text" id="foundDate" name="foundDate" class="input-medium" readonly="true" value="<fmt:formatDate value='${companyInfo.foundDate}' pattern='yyyy-MM-dd'/>" />
						</td>
						<td class="ft_label">注册资本：</td>
						<td class="ft_content">
							<input type="text" id="registerCapital" name="registerCapital" class="input-medium" readonly="true" value="${companyInfo.registerCapital}" />
						</td>
						<td class="ft_label">实缴资本：</td>
						<td class="ft_content">
							<input type="text" id="paidCapital" name="paidCapital" class="input-medium" readonly="true" value="${companyInfo.paidCapital}" />
						</td>
					</tr>
					<tr>
						<td class="ft_label">主体性质：</td>
						<td class="ft_content">
						<input type="text" id="custType" class="input-medium" readonly="true" value="${fns:getDictLabel(custType,'CUST_TYPE','')}" />
						</td>
					</tr>
					<tr>
						<td class="ft_label">注册地址：</td>
						<td class="ft_content" colspan="5">
							<input type="text" id="regProvince" class="input-medium" readonly="true" value="${companyInfo.regProvince}" />
							<span>
								<font>省</font>
							</span>
							<input type="text" id="regCity" class="input-medium" readonly="true" value="${companyInfo.regCity}" />
							<span>
								<font>市</font>
							</span>
							<input type="text" id="regDistinct" class="input-medium" readonly="true" value="${companyInfo.regDistinct}" />
							<span>
								<font>区</font>
							</span>
							<input type="text" id="regDetails" name="regDetails" class="input-medium" readonly="true" value="${companyInfo.regDetails}" />
						</td>
					</tr>
					<tr>
					<td class="ft_label" style="text-align: right;">经营地址与注册地址是否一致：</td>
					<td class="ft_content" colspan="2">
						<input type="radio" name="isAddrResi"  disabled value="1" id="radio_comYes"  class="input-medium">
						<label for="radio_yes">是</label>
						<input type="radio" name="isAddrResi"  disabled value="0" id="radio_comNo"  class="input-medium">
						<label for="radio_no">否</label>
					</td>
				</tr>
				<tr>
					<td class="ft_label">经营地址：</td>
					<td class="ft_content" colspan="5">
							<input type="text" id="operateProvince" class="input-medium" readonly="true" value="${companyInfo.operateProvince}" />
							<span>
								<font>省</font>
							</span>
							<input type="text" id="operateCity" class="input-medium" readonly="true" value="${companyInfo.operateCity}" />
							<span>
								<font>市</font>
							</span>
							<input type="text" id="operateDistinct" class="input-medium" readonly="true" value="${companyInfo.operateDistinct}" />
							<span>
								<font>区</font>
							</span>
							<input type="text" id="operateDetails" name="operateDetails" class="input-medium" readonly="true" value="${companyInfo.operateDetails}" />
						</td>
				</tr>
				<tr>
					<td class="ft_label">
					股东及占股比例：
					</td>
					<td class="ft_content" colspan="5">
					<textarea rows="5" class="textareaWidth textarea-style"  disabled="true">${companyInfo.capitalStructureCom}</textarea>
					</td>
				</tr>
				</table>
			</div>
			<div class="searchInfo" id="creditDiv">
				<h3 id="zcxi" class="searchTitle">资产信息</h3>
				<h3 class="tableTitle" id="propertyType">
					<input type="radio" name="propertyHouseRadio" value="0" id="propertyHouseRadio" onclick="changePropertyHouse(this.value);" <c:if test="${gqgetComInfo.propertyHouse==1}">checked</c:if>>
					<label for="propertyHouseRadio">有房</label>
					<input type="hidden" id="propertyHouse" name="propertyHouse" class="input-medium" value="${gqgetComInfo.propertyHouse}" />
					<input type="radio" name="propertyCarRadio" value="0" id="propertyCarRadio" onclick="changePropertyCar(this.value);" <c:if test="${gqgetComInfo.propertyCar==1}">checked</c:if>>
					<label for="propertyCarRadio">有车</label>
					<input type="hidden" id="propertyCar" name="propertyCar" class="input-medium" value="${gqgetComInfo.propertyCar}" />
					<input type="radio" name="propertyOtherRadio" value="0" id="propertyOtherRadio" onclick="changePropertyOther(this.value);" <c:if test="${gqgetComInfo.propertyOther==1}">checked</c:if>>
					<label for="propertyOtherRadio">其他</label>
					<input type="hidden" id="propertyOther" name="propertyOther" class="input-medium" value="${gqgetComInfo.propertyOther}" />
					<font style="color: red">*</font>
				</h3>
				<div class="searchCon">
					<div id="house"></div>
					<div id="assetHouse"></div>
					<div id="car"></div>
					<div id="assetCar"></div>
					<div id="otherId">
						<h3 class="searchTitle">其他资产</h3>
						<table class="fromTable filter">
							<tr>
								<td class="ft_label">
									<font style="color: red">*</font>
									其他：
								</td>
								<td class="ft_content">
									<form:textarea path="other" htmlEscape="false" rows="4" maxlength="1000" class="textareaWidth textarea-style required checkNumberTwenty" onkeyup="adjustTextareaLength('other', 'preOther');" />
									<br>
									<font style="color: red">最少输入20字，最多可输入1000字</font>
								</td>
							</tr>
						</table>
					</div>
					<div id="equip"></div>
					<div id="warehouse"></div>
				</div>
			</div>
			<div id="gqgetGuarantorInfoList"></div>
			<div id="gqgetGuarantorCompanyInfo"></div>
			<div class="searchInfo" id="creditDiv">
				<h3 class="searchTitle">还款来源</h3>
				<div class="searchCon">
					<table class="fromTable filter">
						<tr>
							<td class="ft_label">
								<font style="color: red">*</font>
								还款来源1：
							</td>
							<td class="ft_content">
								<form:textarea path="sourceOfPepayment1" htmlEscape="false" rows="4" maxlength="1000" class="textareaWidth textarea-style required checkNumberTwenty" onkeyup="adjustTextareaLength('sourceOfPepayment1', 'preSourceOfDepayment1');" />
								<br>
								<font style="color: red">最少输入20字，最多可输入1000字</font>
							</td>
						</tr>
						<tr>
							<td class="ft_label">还款来源2：</td>
							<td class="ft_content">
								<form:textarea path="sourceOfPepayment2" htmlEscape="false" rows="4" maxlength="1000" class="textareaWidth textarea-style checkNumberTwenty" onkeyup="adjustTextareaLength('sourceOfPepayment2', 'preSourceOfDepayment2');" />
								<br>
								<font style="color: red">最少输入20字，最多可输入1000字</font>
							</td>
						</tr>
						<tr>
							<td class="ft_label">还款来源3：</td>
							<td class="ft_content">
								<form:textarea path="sourceOfPepayment3" htmlEscape="false" rows="4" maxlength="1000" class="textareaWidth textarea-style checkNumberTwenty" onkeyup="adjustTextareaLength('sourceOfPepayment3', 'preSourceOfDepayment3');" />
								<br>
								<font style="color: red">最少输入20字，最多可输入1000字</font>
							</td>
						</tr>
						<tr>
							<td class="ft_label">还款来源4：</td>
							<td class="ft_content">
								<form:textarea path="sourceOfPepayment4" htmlEscape="false" rows="4" maxlength="1000" class="textareaWidth textarea-style checkNumberTwenty" onkeyup="adjustTextareaLength('sourceOfPepayment4', 'preSourceOfDepayment4');" />
								<br>
								<font style="color: red">最少输入20字，最多可输入1000字</font>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="searchInfo" id="creditDiv">
				<h3 class="searchTitle">风险控制</h3>
				<div class="searchCon">
					<table class="fromTable filter">
						<tr>
							<td class="ft_label">
								<!-- 20161110字段改为非必填 -->
								审核意见：
							</td>
							<td class="ft_content">
								<form:textarea path="auditOpintion" htmlEscape="false" rows="4" maxlength="1000" class="textareaWidth textarea-style" onkeyup="adjustTextareaLength('auditOpintion', 'preAuditOpintion');" />
								<br>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<div class="searchButton">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="return checkProperty();" />
		</div>
	</form:form>
</body>
</html>