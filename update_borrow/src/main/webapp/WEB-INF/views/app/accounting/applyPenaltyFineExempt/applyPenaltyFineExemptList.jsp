<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>违约金罚息减免管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		if ("1" == "${actTaskParam.status}") {
			$("#resultForm").hide();
		}
		$("#resultForm").validate({
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
		return false;
	}
	function saveForm() {
		top.$.jBox.tip.mess = null;
		var flag = $("input[name='passFlag']:checked").val();
		var formJson = $("#resultForm").serializeJson();
		saveJson("${ctx}/accounting/applyPenaltyFineExempt/saveResult?passFlag=" + flag + "&taskId=${actTaskParam.taskId}&procInstId=${actTaskParam.procInstId}&flag=${flag}", JSON.stringify(formJson), function(data) {
			if (data) {
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
</script>
</head>
<body>
	<div class="filter" style="width:100%; overflow-x:auto;overflow-y:hidden">
		<h3 class="infoListTitle">还款明细列表</h3>
		<table class="table table-bordered table-condensed">
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
					<c:if test="${result.periodNum == applyPenaltyFineExempt.periodNum}">
						<tr style="background-color: yellow;">
					</c:if>
					<c:if test="${result.periodNum != applyPenaltyFineExempt.periodNum}">
						<tr>
					</c:if>
					<td class="title" title="${result.periodNum}">${result.periodNum}</td>
					<td class="title" title="${result.repayPeriodStatus}">${fns:getDictLabel(result.repayPeriodStatus, 'PERIOD_STATE', '')}</td>
					<td class="title" title="${result.deductDate}">${result.deductDate}</td>
					<td class="title" title="${result.repayAmount}">${result.repayAmount}</td>
					<td class="title" title="${result.serviceFee}">${result.serviceFee}</td>
					<td class="title" title="${result.managementFee}">${result.managementFee}</td>
					<td class="title" title="${result.interestAmount}">${result.interestAmount}</td>
					<td class="title" title="${result.capitalAmount}">${result.capitalAmount}</td>
					<td class="title" title="${result.penaltyAmount}">${result.penaltyAmount}</td>
					<td class="title" title="${result.fineAmount}">${result.fineAmount}</td>
					<td class="title" title="${result.factServiceFee}">${result.factServiceFee}</td>
					<td class="title" title="${result.factManagementFee}">${result.factManagementFee}</td>
					<td class="title" title="${result.factInterestAmount}">${result.factInterestAmount}</td>
					<td class="title" title="${result.factOverdueCapialAmount}">${result.factOverdueCapialAmount}</td>
					<td class="title" title="${result.factPenaltyAmount}">${result.factPenaltyAmount}</td>
					<td class="title" title="${result.factFineAmount}">${result.factFineAmount}</td>
					<td class="title" title="${result.penaltyExemptAmount}">${result.penaltyExemptAmount}</td>
					<td class="title" title="${result.fineExemptAmount}">${result.fineExemptAmount}</td>
					<td class="title" title="${result.capitalInterestRepayDate}">${result.capitalInterestRepayDate}</td>
					<td class="title" title="${result.oweCapitalAmount}">${result.oweCapitalAmount}</td>
					<td class="title" title="${result.oweInterestAmount}">${result.oweInterestAmount}</td>
					<td class="title" title="${result.overdueDays}">${result.overdueDays}</td>
					<td class="title" title="${result.allRepayDate}">${result.allRepayDate}</td>
					</tr>
				</c:forEach>
				<tr>
					<td style="text-align: right;" colspan="3">违约金申请额：</td>
					<td colspan="2">${applyPenaltyFineExempt.penaltyExemptAmount}</td>
					<td style="text-align: right;" colspan="3">罚息申请额：</td>
					<td colspan="15">${applyPenaltyFineExempt.fineExemptAmount}</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="infoList">
		<form:form id="resultForm" action="#" method="post">
			<input type="hidden" name="id" value="${applyPenaltyFineExempt.id}" />
			<input type="hidden" name="contractNo" value="${applyPenaltyFineExempt.contractNo}" />
			<input type="hidden" name="periodNum" value="${applyPenaltyFineExempt.periodNum}" />
			<input type="hidden" name="fineExemptAmount" value="${applyPenaltyFineExempt.fineExemptAmount}" />
			<input type="hidden" name="penaltyExemptAmount" value="${applyPenaltyFineExempt.penaltyExemptAmount}" />
			<div class="filter">
				<h3 class="infoListTitle">减免申请</h3>
				<div class="infoListCon">
					<div class="filter">
						<table class="fromTable">
							<tr>
								<td class="ft_label">违约金申请额：</td>
								<td class="ft_content">
									<input type="text" htmlEscape="false" maxlength="10" class="input-medium" readonly="true" value="${applyPenaltyFineExempt.penaltyExemptAmount}" />
								</td>
								<td class="ft_label">罚息申请额：</td>
								<td class="ft_content">
									<input type="text" class="input-medium" htmlEscape="false" readonly="true" value="${applyPenaltyFineExempt.fineExemptAmount}" maxlength="10" />
								</td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td class="ft_label">申请原因：</td>
								<td class="" colspan="5">
									<textarea rows="5" cols="100" maxlength="1000" id="description" disabled="disabled" class="input-xxlarge">${applyPenaltyFineExempt.description}</textarea>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div class="filter">
				<h3 class="infoListTitle">审批结论</h3>
				<div class="infoListCon">
					<div class="filter">
						<table class="fromTable">
							<tr>
								<td class="ft_label">审批结论：</td>
								<td class="" colspan="5">
									<input type="radio" name="passFlag" value="yes" id="radio_yes" class="required">
									<label for="radio_yes">通过</label>
									&nbsp;&nbsp;
									<input type="radio" name="passFlag" value="no" id="radio_no" class="required">
									<label for="radio_no">拒绝</label>
									<font color="red"></font>
								</td>
							</tr>
							<tr>
								<td class="ft_label">审批意见：</td>
								<td class="" colspan="5">
									<textarea rows="5" cols="100" maxlength="1000" id="description" name="description" class="input-xxlarge required"></textarea>
									<font color="red"></font>
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="rsBtnSubmit" class="btn btn-primary" type="submit" value="提 交" />
						</div>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</body>
</html>