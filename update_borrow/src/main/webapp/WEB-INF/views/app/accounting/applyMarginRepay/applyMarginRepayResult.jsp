<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>保证金退还审批管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		document.getElementById("marginAmount").value = outputmoney(${applyMarginRepay.marginAmount});
		document.getElementById("applyMarginAmount").value = outputmoney(${applyMarginRepay.applyMarginAmount});
		document.getElementById("marginAmount1").value = outputmoney(${applyMarginRepay.marginAmount});
		var value = ${applyMarginRepay.applyMarginAmount};
		document.getElementById("approMarginAmount").value = outputmoney(value);
		if ("1" == "${actTaskParam.status}") {
			$("#btnSubmit").hide();
		}
		$("#inputForm").validate({
		submitHandler : saveForm,
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});
	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}

	function saveForm() {
		var valueT1 = $("#marginAmount").val().replace(/,/g, "");
		$("#marginAmount").val(valueT1);
		var valueT2 = $("#applyMarginAmount").val().replace(/,/g, "");
		$("#applyMarginAmount").val(valueT2);
		var valueT3 = $("#marginAmount").val().replace(/,/g, "");
		$("#marginAmount1").val(valueT3);
		var valueT4 = $("#approMarginAmount").val().replace(/,/g, "");
		$("#approMarginAmount").val(valueT4);
		var applyAmount = $("#applyMarginAmount").val();
		var approAmount = $("#approMarginAmount").val();
		var reg = /^([1-9][\d]{0,10}|0)(\.[\d]{1,2})?$/;
		if (!reg.test(approAmount)) {
			alertx("请输入正确格式的金额");
		} else if (Number(approAmount) > Number(applyAmount)) {
			alertx("批复金额不能大于申请金额");
		} else {
			loading();
			top.$.jBox.tip.mess = null;
			var formJson = $("#inputForm").serializeJson();
			$.post("${ctx}/accounting/applyMarginRepay/save", formJson, function(data) {
				if (data) {
					closeTip();
					if (data.status == 1) {//保存成功
						alertx(data.message, function() {
							closeAndReloadACC();
						});
					} else {
						alertx(data.message);
					}
				}
			});
		}
	}
</script>
</head>
<body>
	<div id="tableDataId">
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>期数</th>
					<th>状态</th>
					<th>还款日期</th>
					<th>应还金额</th>
					<th>服务费</th>
					<th>账户管理费</th>
					<th>利息</th>
					<th>本金</th>
					<th>违约金</th>
					<th>罚息</th>
					<th>实还服务费</th>
					<th>实还账户管理费</th>
					<th>实还利息</th>
					<th>实还本金</th>
					<th>实还违约金</th>
					<th>实还罚息</th>
					<th>违约金减免</th>
					<th>罚息减免</th>
					<th>当期本息结清日期</th>
					<th>逾期本金</th>
					<th>逾期利息</th>
					<th>逾期天数</th>
					<th>当期总结清日期</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${deductResultTemps}" var="result">
					<tr>
						<td id="propertyRight" class="title" title="${result.periodNum}">${result.periodNum}</td>
						<td id="repayPeriodStatus" class="title" title="${result.repayPeriodStatus}">${fns:getDictLabel(result.repayPeriodStatus, 'PERIOD_STATE', '')}</td>
						<td id="deducDate" class="title" title="${result.deductDate}">${result.deductDate}</td>
						<td id="repayAmount" class="title" title="${result.repayAmount}">${result.repayAmount}</td>
						<td id="serviceFee" class="title" title="${result.serviceFee}">${result.serviceFee}</td>
						<td id="mangementFee" class="title" title="${result.managementFee}">${result.managementFee}</td>
						<td id="interestAmount" class="title" title="${result.interestAmount}">${result.interestAmount}</td>
						<td id="capitalAmount" class="title" title="${result.capitalAmount}">${result.capitalAmount}</td>
						<td id="penaltyAmount" class="title" title="${result.penaltyAmount}">${result.penaltyAmount}</td>
						<td id="fineAmount" class="title" title="${result.fineAmount}">${result.fineAmount}</td>
						<td id="factServiceFee" class="title" title="${result.factServiceFee}">${result.factServiceFee}</td>
						<td id="factManagementFee " class="title" title="${result.factManagementFee}">${result.factManagementFee}</td>
						<td id="factInterestAmount" class="title" title="${result.factInterestAmount}">${result.factInterestAmount}</td>
						<td id="factOverdueCapitalAmount" class="title" title="${result.factOverdueCapialAmount}">${result.factOverdueCapialAmount}</td>
						<td id="factPenaltyAmount " class="title" title="${result.factPenaltyAmount}">${result.factPenaltyAmount}</td>
						<td id="factFineAmount" class="title" title="${result.factFineAmount}">${result.factFineAmount}</td>
						<td id="penaltyExemptAmount" class="title" title="${result.penaltyExemptAmount}">${result.penaltyExemptAmount}</td>
						<td id="fineExemptAmount" class="title" title="${result.fineExemptAmount}">${result.fineExemptAmount}</td>
						<td id="capitalInterestRepayDate" class="title" title="${result.capitalInterestRepayDate}">${result.capitalInterestRepayDate}</td>
						<td id="oweCapitalAmount" class="title" title="${result.oweCapitalAmount}">${result.oweCapitalAmount}</td>
						<td id="oweInterestAmount" class="title" title="${result.oweInterestAmount}">${result.oweInterestAmount}</td>
						<td id="overdueDays" class="title" title="${result.overdueDays}">${result.overdueDays}</td>
						<td id="allRepayDate" class="title" title="${result.allRepayDate}">${result.allRepayDate}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<form:form id="inputForm" modelAttribute="applyMarginRepay" action="#" method="post" class="form-horizontal">
		<sys:message content="${message}" />
		<form:hidden path="id" />
		<input type="hidden" name="contractNo" value="${applyMarginRepay.contractNo}" />
		<input type="hidden" name="applyNo" value="${actTaskParam.applyNo}" />
		<input type="hidden" name="taskId" value="${actTaskParam.taskId}" />
		<input type="hidden" name="taskDefKey" value="${actTaskParam.taskDefKey}" />
		<input type="hidden" name="procDefId" value="${actTaskParam.procDefId}" />
		<input type="hidden" name="status" value="${actTaskParam.status}" />
		<input type="hidden" name="procInstId" value="${actTaskParam.procInstId}" />
		<input type="hidden" name="flag" value="2" />
		<div class="searchInfo" id="">
			<h3 class="searchTitle">保证金退还申请</h3>
			<div class="searchCon">
				<table class="fromTable filter">
					<tr>
						<td class="ft_label">保证金：</td>
						<td class="ft_content">
							<form:input path="marginAmount" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" htmlEscape="false" readonly="true" class="input-medium" />
						</td>
						<td class="ft_label">申请退还保证金：</td>
						<td class="ft_content">
							<form:input path="applyMarginAmount" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" htmlEscape="false" class="input-medium" readonly="true" />
						</td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td class="ft_label" style="width: 10%;">申请说明：</td>
						<td class="ft_content" colspan="5" style="width: 90%;">
							<form:textarea path="marginApplyDesc" htmlEscape="false" rows="5" cols="3" maxlength="1000" class="area-length" style="width: 90%;" readonly="true" />
						</td>
					</tr>
				</table>
			</div>
		</div>
		<shiro:hasPermission name="accounting:applyMarginRepay:approve">
			<div class="searchInfo">
				<h3 class="searchTitle">保证金退还结算中心审核</h3>
				<div class="searchCon">
					<table class="fromTable filter">
						<tr>
							<td class="ft_label">保证金：</td>
							<td class="ft_content">
								<form:input path="marginAmount" id="marginAmount1" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" htmlEscape="false" class="input-medium" readonly="true" />
							</td>
							<td class="ft_label">批复退还保证金：</td>
							<td class="ft_content">
								<form:input path="approMarginAmount" id="approMarginAmount" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" htmlEscape="false" onclick="this.value = ''" readonly="${readonly}" class="input-medium required" />
								<font style="color: red">*</font>
							</td>
							<td class="ft_label">到账模式：</td>
							<td class="ft_content">
								<form:select path="marginReceivedMode" disabled="${readonly}" class="input-medium required">
									<form:option value="" label="" />
									<form:options items="${fns:getDictList('MARGIN_RECEIVED_MODE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
								</form:select>
								<font style="color: red">*</font>
							</td>
						</tr>
						<tr>
							<td class="ft_label" style="width: 10%;">批复说明：</td>
							<td class="ft_content" colspan="5" style="width: 90%;">
								<form:textarea path="marginApproDesc" readonly="${readonly}" htmlEscape="false" rows="5" cols="3" maxlength="1000" class="area-length required " style="width: 90%;" />
								<font style="color: red">*</font>
							</td>
						</tr>
						<tr>
							<td colspan="6" style="text-align: right;">
								<input id="btnSubmit" class="btn btn-primary" type="submit" value="提 交" />
								&nbsp;
							</td>
						</tr>
					</table>
				</div>
			</div>
		</shiro:hasPermission>
	</form:form>
</body>
</html>