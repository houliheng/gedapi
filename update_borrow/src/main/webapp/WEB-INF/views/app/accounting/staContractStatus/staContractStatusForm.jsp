<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>合同还款明细查询管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		if ('${pl}' == "pl") {
			$("[name = 'pl']").hide();
		}

		$("#inputForm").validate({
		submitHandler : function(form) {
			loading();
			form.submit();
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			$("#messageBox").text("输入有误，请先更正。");
			if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
				error.appendTo(element.parent().parent());
			} else {
				error.insertAfter(element);
			}
		}
		});
	});

	function validateIsLock(contractNo, periodNum) {
		loading();
		$.ajax({
		url : "${ctx}/accounting/deductApply/validateIsLock",
		data : {
			contractNo:contractNo,
			periodNum : periodNum
		},
		type : "post",
		async : false,
		dataType : "JSON",
		success : function(data) {
			closeTip();
			if (data.status == 1) {
				var width = $(window).width() - 300;
				width = Math.max(width, 1000);
				var height = $(window).height() - 200;
				//划扣
				var url = "${ctx}/accounting/deductApply/form";
				openJBox("deductApplyBox", url, "划扣", width, height,{
					contractNo:contractNo,
					periodNum:periodNum
				});
			} else {
				alertx(data.message);
			}
		}
		});
	}
	function advanceRepayNew(contractNo){
		loading();
		$.ajax({
		url : "${ctx}/accounting/advanceRepayGet/validateApplyAdvanceRepay",
		type : "post",
		data : {
			contractNo:contractNo
		},
		async : false,
		dataType : "JSON",
		success : function(data) {
			closeTip();
			if (data.status == 1) {
				var width = $(window).width() - 300;
				width = Math.max(width, 1000);
				var height = $(window).height() - 200;
				var url = "${ctx}/accounting/advanceRepayGet/form";
				openJBox("applyAdvanceRepayBox", url, "提前还款(新)", width, height,{
					contractNo:contractNo
				});
			} else {
				alertx(data.message);
			}
		}
		});
	}
	
	function validateApplyAdvanceRepay(contractNo) {
		loading();
		$.ajax({
		url : "${ctx}/accounting/applyAdvanceRepay/validateApplyAdvanceRepay",
		type : "post",
		data : {
			contractNo:contractNo
		},
		async : false,
		dataType : "JSON",
		success : function(data) {
			closeTip();
			if (data.status == 1) {
				var width = $(window).width() - 300;
				width = Math.max(width, 1000);
				var height = $(window).height() - 200;
				//提前还款
				var url = "${ctx}/accounting/applyAdvanceRepay/form";
				openJBox("applyAdvanceRepayBox", url, "提前还款", width, height,{
					contractNo:contractNo
				});
			} else {
				alertx(data.message);
			}
		}
		});
	}
	function applyMargin(contractNo) {
		loading();
		$.ajax({
		url : "${ctx}/accounting/applyMarginRepay/marginRepay",
		type : "post",
		data : {
			contractNo:contractNo
		},
		async : false,
		dataType : "JSON",
		success : function(data) {
			closeTip();
			if (data.status == 1) {
				var width = $(window).width() - 300;
				width = Math.max(width, 1000);
				var height = $(window).height() - 200;
				var url = "${ctx}/accounting/applyMarginRepay/form?contractNo="+contractNo;
				openJBox("applyMarginRepayBox", url, "保证金退还申请", width, height);
			} else {
				alertx(data.message);
			}
		}
		});
	}
	
	function penaltyBack(contractNo,periodNum) {
		loading();
		$.ajax({
		url : "${ctx}/accounting/applyPenaltyFineExempt/dealStaPenaltyFineExempt",
		type : "post",
		data : {
			contractNo:contractNo,
			periodNum:periodNum
		},
		async : false,
		dataType : "JSON",
		success : function(data) {
			closeTip();
			if (data.status == 1) {
				alertx(data.message,function(){
					refreshPage(data.contractNo);
				});
			} else {
				alertx(data.message);
			}
		}
		});
	}

	function penalty(contractNo, periodNum, status) {
		loading();
		$.ajax({
		url : "${ctx}/accounting/applyPenaltyFineExempt/validatePenalty",
		data : {
		contractNo:contractNo,
		status : status,
		periodNum : periodNum
		},
		type : "post",
		async : false,
		dataType : "JSON",
		success : function(data) {
			closeTip();
			if (data.status == 1) {
				var width = $(window).width() - 300;
				width = Math.max(width, 1000);
				var height = $(window).height() - 200;
				var url = "${ctx}/accounting/applyPenaltyFineExempt/form?contractNo="+contractNo+"&periodNum="+periodNum+"&flag="+data.message;
				openJBox("applyPenaltyFineExemptBox", url, "减免", width, height);
			} else {
				alertx(data.message);
			}
		}
		});
	}
	//还款明细（账务查询）
	function deductResultBox(contractNo) {
		loading();
		$.ajax({
		url : "${ctx}/accounting/staRepayPlanStatus/validateIsOrNoApplyPenaltyFineExempt",
		type : "post",
		data : {
			contractNo:contractNo
				},
		async : false,
		dataType : "JSON",
		success : function(data) {
			closeTip();
			if (data.status == 1) {
				var width = $(window).width() - 100;
				width = Math.max(width, 1000);
				var height = $(window).height() - 100;
				var url = "${ctx}/accounting/deductResult/index";
				openJBox("deductResultBox", url, "账务调整", width, height, {
					contractNo : contractNo
				});
			} else {
				alertx(data.message);
			}
		}
		});
	}

	function refreshPage(contractNo) {
		$("#contractNoTmp").val(contractNo);
		var newUrl = "${ctx}/accounting/staContractStatus/form?backUrl=${backUrl}";
		$("#inputForm").attr('action',newUrl);
		$("#inputForm").submit();
	}
	
	
</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="staContractStatus" action="${ctx}/accounting/staContractStatus/save" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<input type="hidden" id="contractNoTmp" name="contractNoTmp"  />
		<sys:message content="${message}" />
		<h3 class="infoListTitle">合同信息</h3>
		<table class="filter">
			<tr>
				<td class="ft_label">客户名称：</td>
				<td class="ft_content">${contract.custName}</td>
				<td class="ft_label">产品类型：</td>
				<td class="ft_content">${contract.approProductTypeName}</td>
				<td class="ft_label">产品名称：</td>
				<td class="ft_content">${contract.approProductName}</td>
			<tr>
				<td class="ft_label">合同编号：</td>
				<td class="ft_content">${contract.contractNo}</td>
				<td class="ft_label">合同金额：</td>
				<td class="ft_content">${contract.contractAmount}</td>
				<td class="ft_label">放款金额：</td>
				<td class="ft_content">${contract.loanAmount}</td>
			</tr>
			<tr>
				<td class="ft_label">期限：</td>
				<td class="ft_content">${contract.approPeriodValue}</td>
				<td class="ft_label">利率：</td>
				<td class="ft_content">${contract.approYearRate}%</td>
				<td class="ft_label">借款模式：</td>
				<td class="ft_content">${fns:getDictLabel(contract.loanModel, 'LOAN_MODEL', '')}</td>
			</tr>
			<tr>
				<td class="ft_label">服务费收取方式：</td>
				<td class="ft_content">${fns:getDictLabel(contract.serviceFeeType, 'SERVICE_FEE_TYPE', '')}</td>
				<td class="ft_label">服务费：</td>
				<td class="ft_content">
				<c:if test="${contract.approProductTypeId == '6'}">
				${factServiceFee}
				</c:if>
				<c:if test="${contract.approProductTypeId != '6'}">
				${contract.serviceFee}
				</c:if>
				</td>
				<td class="ft_label">特殊服务费：</td>
				<td class="ft_content">${contract.specialServiceFee}</td>
			</tr>
			<tr>
				<td class="ft_label">保证金：</td>
				<td class="ft_content">${contract.marginAmount}</td>
				<td class="ft_label">逾期期数：</td>
				<td class="ft_content">${staContractStatus.taTimes}</td>
				<td class="ft_label">当前逾期金额：</td>
				<td class="ft_content">${staContractStatus.currOverdueAmount}</td>
			</tr>
			<tr>
				<td class="ft_label">还款方式：</td>
				<td class="ft_content">${fns:getDictLabel(contract.approLoanRepayType, 'LOAN_REPAY_TYPE', '')}</td>
				<td class="ft_label">登记员：</td>
				<td class="ft_content">${contract.operateName}</td>
				<td class="ft_label">登记门店：</td>
				<td class="ft_content">${contract.operateOrgName}</td>
			</tr>
		</table>
	</form:form>
	<h3 class="infoListTitle">还款明细列表</h3>
	<div class="ribbon">
		<ul class="layout" name="pl">
			<li>
				<a href="javaScript:void(0)" onclick="validateApplyAdvanceRepay('${contract.contractNo}')">
					<span>提前还款</span>
				</a>
			</li>
			<li>
				<a href="javaScript:void(0)" onclick="deductResultBox('${contract.contractNo}')">
					<span>账务调整 </span>
				</a>
			</li>
			<li>
				<a href="javaScript:void(0)" onclick="applyMargin('${contract.contractNo}')">
					<span> 退还保证金 </span>
				</a>
			</li>
			<li>
				<a href="javaScript:void(0)" onclick="href='${backUrl}'">
					<span> 返 回 </span>
				</a>
			</li>
			<li>
				<a href="javaScript:void(0)" onclick="refreshPage('${contract.contractNo}');">
					<span> 刷 新</span>
				</a>
			</li>
			<li>
				<a href="javaScript:void(0)" onclick="advanceRepayNew('${contract.contractNo}');">
					<span>提前还款(新)</span>
				</a>
			</li>
		</ul>
	</div>
	<div id="tableDataId" style="width:100%; overflow-x:auto;overflow-y:hidden">
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>期数</th>
					<th>状态</th>
					<th>还款日期</th>
					<th>应还金额</th>
					<th style="color:#B2DFEE">服务费</th>
					<th style="color:#B2DFEE">账户管理费</th>
					<th style="color:#B2DFEE">利息</th>
					<th style="color:#B2DFEE">本金</th>
					<th style="color:#B2DFEE">违约金</th>
					<th style="color:#B2DFEE">罚息</th>
					<th style="color:#9F79EE">实还服务费</th>
					<th style="color:#9F79EE">实还账户管理费</th>
					<th style="color:#9F79EE">实还利息</th>
					<th style="color:#9F79EE">实还本金</th>
					<th style="color:#9F79EE">实还违约金</th>
					<th style="color:#9F79EE">实还罚息</th>
					<th style="color:#9F79EE">实还营业外收入额</th>
					<th style="color:#9F79EE">提前还款费用</th>
					<th style="color:#9F79EE">退回</th>
					<th>违约金减免</th>
					<th>罚息减免</th>
					<th>当期本息结清日期</th>
					<th>逾期本金</th>
					<th>逾期利息</th>
					<th>逾期天数</th>
					<th>当期总结清日期</th>
					<shiro:hasPermission name="accounting:staContractStatus:edit">
						<th name="pl">操作</th>
					</shiro:hasPermission>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${deductResultTemps}" var="result">
					<tr>
						<td class="title" title="${result.periodNum}">${result.periodNum}</td>
						<td class="title" title="${fns:getDictLabel(result.repayPeriodStatus, 'PERIOD_STATE', '')}">${fns:getDictLabel(result.repayPeriodStatus, 'PERIOD_STATE', '')}</td>
						<td class="title" title="${result.deductDate}">${result.deductDate}</td>
						<td class="title" title="${result.repayAmount}">${result.repayAmount}</td>
						<td class="title" title="${result.serviceFee}">${result.serviceFee}</td>
						<td class="title" title="${result.managementFee}">${result.managementFee}</td>
						<td class="title" title="${result.interestAmount}">${result.interestAmount}</td>
						<td class="title" title="${result.capitalAmount}">${result.capitalAmount}</td>
						<c:if test="${result.fineAmount != null && result.fineAmount != ''}">
							<td class="title" title="${result.penaltyAmount}">${result.penaltyAmount}</td>
						</c:if>
						<c:if test="${result.fineAmount == null || result.fineAmount == ''}">
							<td></td>
						</c:if>
						<td class="title" title="${result.fineAmount}">${result.fineAmount}</td>
						<td class="title" title="${result.factServiceFee}">${result.factServiceFee}</td>
						<td class="title" title="${result.factManagementFee}">${result.factManagementFee}</td>
						<td class="title" title="${result.factInterestAmount}">${result.factInterestAmount}</td>
						<td class="title" title="${result.factOverdueCapialAmount}">${result.factOverdueCapialAmount}</td>
						<td class="title" title="${result.factPenaltyAmount}">${result.factPenaltyAmount}</td>
						<td class="title" title="${result.factFineAmount}">${result.factFineAmount}</td>
						<td class="title" title="${result.factAddAmount}">${result.factAddAmount}</td>
						<td class="title" style="text-align: center;" title="${result.factBreakAmount}">${result.factBreakAmount}</td>
						<td class="title" title="${result.backAmount}">${result.backAmount}</td>
						<td class="title" title="${result.penaltyExemptAmount}">${result.penaltyExemptAmount}</td>
						<td class="title" title="${result.fineExemptAmount}">${result.fineExemptAmount}</td>
						<td class="title" title="${result.capitalInterestRepayDate}">${result.capitalInterestRepayDate}</td>
						<td class="title" title="${result.oweCapitalAmount}">${result.oweCapitalAmount}</td>
						<td class="title" title="${result.oweInterestAmount}">${result.oweInterestAmount}</td>
						<td class="title" title="${result.overdueDays}">${result.overdueDays}</td>
						<td class="title" title="${result.allRepayDate}">${result.allRepayDate}</td>
						<td name="pl" style="white-space: nowrap;">
							<a href="javaScript:void(0)" onclick="validateIsLock('${contract.contractNo}','${result.periodNum}')">划扣</a>
							<a href="javaScript:void(0)" onclick="penalty('${contract.contractNo}','${result.periodNum}','${result.repayPeriodStatus}')">减免</a>
				<%-- 			<shiro:hasRole name="admin"> --%>
							<c:if test="${fns:getUser().loginName eq '00027956' && result.penaltyExemptAmount != null && result.fineExemptAmount != null}">
							<a href="javaScript:void(0)" onclick="penaltyBack('${contract.contractNo}','${result.periodNum}')">减免退回</a>
							</c:if>
					<%-- 		</shiro:hasRole> --%>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>