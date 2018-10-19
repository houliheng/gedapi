<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>企业征信信息管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		//$("#name").focus();	

		document.getElementById("guaranteeOutAmount").value = outputmoney("${creditCompany.guaranteeOutAmount}");
		document.getElementById("totalPaymentOneYearAmount").value = outputmoney("${creditCompany.totalPaymentOneYearAmount}");
		document.getElementById("sixMonthCompanyIncomeAvg").value = outputmoney("${creditCompany.sixMonthCompanyIncomeAvg}");
		$("#creditCompanyForm").validate({
		submitHandler : saveCreditCompany,
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			$("#messageBox").text("输入有误，请先更正。");
			checkReq(error, element)
		}
		});
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
		$("#companyId").empty();
		$("#companyId").append("<option value=''>请选择</option>");
		var $companyId = $("#s2id_companyId>.select2-choice>.select2-chosen");
		$companyId.html("请选择");
// 		$("#idNum").val(null);
		$.post("${ctx}/credit/creditAndLine/creditLineBank/findCustNameByRoleType", {
		roleType : $("#roleType").val(),
		applyNo : '${creditCompany.applyNo}'
		}, function(data) {
			$.each(data, function(i, val) {
				$("#companyId").append("<option value='"+val["custId"]+"' label='"+val["custName"]+"'>" + val["custName"] + "</option>");
			});
		});
	}
	
	//根绝企业名称（id）查询证件号
	function findCustByCompanyName() {
		var custId = $("#companyId").find("option:selected").attr("value");
		$.post("${ctx}/credit/creditAndLine/creditLineBank/findCustByCompanyName", {
		custId : custId
		}, function(data) {
			$("#idNum").val(data);
		});
	}

	
	//保存银行卡流水信息信息
	function saveCreditCompany() {
		var valueT = $("#guaranteeOutAmount").val().replace(/,/g, "");
		$("#guaranteeOutAmount").val(valueT);
		var totalPaymentOneYearAmountValue = $("#totalPaymentOneYearAmount").val().replace(/,/g, "");
		$("#totalPaymentOneYearAmount").val(totalPaymentOneYearAmountValue);
		var sixMonthCompanyIncomeAvgValue = $("#sixMonthCompanyIncomeAvg").val().replace(/,/g, "");
		$("#sixMonthCompanyIncomeAvg").val(sixMonthCompanyIncomeAvgValue);
		loading();
		top.$.jBox.tip.mess = null;
		var companyName = $("#companyId").find("option:selected").attr("label");
		if(checkIsNull(companyName)){
			companyName = '${creditCompany.companyName}';
		}
		$("#companyName").val(companyName);
		var creditCompany = $("#creditCompanyForm").serializeJson();
		saveJson("${ctx}/credit/creditAndLine/creditCompany/save", JSON.stringify(creditCompany), function(data) {
			if (data) {
				closeTip();
				if (data.status == 1) {//保存成功
					alertx(data.message, function() {
						parent.$.loadDiv("creditCompanyDiv", "${ctx }/credit/creditAndLine/creditCompany/list", {
							applyNo : '${creditCompany.applyNo }'
						}, "post");
						closeJBox();
					});
				} else {
					alertx(data.message);
				}
			}
		});

	}
</script>
</head>
<body>
	<div class="searchInfo">
		<h3 class="searchTitle">企业征信信息</h3>
		<div class="searchCon">
			<form:form id="creditCompanyForm" modelAttribute="creditCompany" action="" method="post" class="form-horizontal">
				<form:hidden path="id" />
				<form:hidden path="applyNo" />
				<form:hidden path="companyName" />
				<sys:message content="${message}" />
				<table class="fromTable filter">
					<tr>
						<td class="ft_label">企业类型：</td>
						<td class="ft_content">
							<form:select id="roleType" path="roleType" onchange="findCustByRoleType();" class="input-medium required" cssStyle="width:177px;">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('ROLE_TYPE_C') }" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
						</td>
						<td class="ft_label">企业名称：</td>
						<td class="ft_content">
							<form:select id="companyId" path="companyId" class="input-medium required" cssStyle="width:177px;">
								<option value="">请选择</option>
								<form:options items="${companyNameMap}" itemLabel="companyName" itemValue="companyId" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
						</td>
						<%-- <td class="ft_label">机构信用代码：</td>
						<td class="ft_content">
							<input type="text" id="idNum" name="idNum" class="input-medium" readonly="readonly" value="${creditCompany.idNum}" />
						</td> --%>
						<td class="ft_label">单位电话：</td>
						<td class="ft_content">
							<input type="text" id="companyPhone" name="companyPhone" class="input-medium phone required" value="${creditCompany.companyPhone}" />
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">征信信用历史月数：</td>
						<td class="ft_content">
							<form:select path="creditMonths" class="input-medium required" cssStyle="width:177px;">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('CREDIT_MONTHS') }" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
						</td>
						<td class="ft_label">对外担保总额(元)：</td>
						<td class="ft_content">
							<input type="text" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" id="guaranteeOutAmount" name="guaranteeOutAmount" class="input-medium required" value="${creditCompany.guaranteeOutAmount}" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">打印时间：</td>
						<td class="ft_content">
							<input in="printingTime" readonly="true" name="printingTime" type="text" maxlength="20" class="input-medium Wdate required" value="${creditCompany.printingTime}" onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd',maxDate:new Date()});" />
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">一年内已还贷款总额(元)：</td>
						<td class="ft_content">
							<input type="text" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" id="totalPaymentOneYearAmount" name="totalPaymentOneYearAmount" class="input-medium required" value="${creditCompany.totalPaymentOneYearAmount}" />
							<font color="red">*</font>
						</td>
						
						<td class="ft_label">是否为大型企业的上下游：</td>
						<td class="ft_content">
							<form:select path="isCompanyUpDown" class="input-medium required" cssStyle="width:177px;">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('yes_no') }" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
						</td>
						<td class="ft_label">企业近六个月月均营业收入(元)：</td>
						<td class="ft_content">
							<input type="text" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" id="sixMonthCompanyIncomeAvg" name="sixMonthCompanyIncomeAvg" class="input-medium required" value="${creditCompany.sixMonthCompanyIncomeAvg}" />
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">首笔信贷交易记录年份：</td>
						<td class="ft_content">
							<input type="text"  id="firstRecordLoanYear" name="firstRecordLoanYear" value="${creditCompany.firstRecordLoanYear}"  class="input-medium Wdate required" value="${creditCompany.printingTime}" onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy',maxDate:new Date()});"/>
							<font color="red">*</font>
						</td>
						<td class="ft_label">办理过信贷业务的机构数量：</td>
						<td class="ft_content">
							<input type="text"  id="operaterOriganNum" onkeyup="value=value.replace(/[^\d]/g,'')" maxlength="9" name="operaterOriganNum" class="input-medium numand0 required" value="${creditCompany.operaterOriganNum}" />
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td colspan="6">
							<div class="form-actions">
									<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />&nbsp;
								<input id="btnCancel" class="btn" type="button" value="返 回" onclick="closeJBox()" />
							</div>
						</td>
					</tr>
				</table>
			</form:form>
		</div>
	</div>
</body>
</html>