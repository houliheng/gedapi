<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>征信企业贷款明细管理</title>
<script type="text/javascript">
	$(document).ready(function() {
		if ('${readOnly}' == '0') {
			setPageReadOnly();
		}

	});
	//新增企业贷款信息
	function addCreditCompanyLoan() {
		var url = "${ctx}/credit/creditAndLine/creditCompany/creditCompanyLoan/form";
		openJBox("creditCompanyLoanBox", url, "新增企业贷款信息", 900, 300, {
			creditCompanyId : '${creditCompanyId }',
			applyNo: '${applyNo}'
		});
	}

	//删除企业贷款信息
	function deleteCreditCompanyLoan() {
		var $checkLine = $("input[name='creditCompanyLoanIds']:checked");
		var $len = $checkLine.length;
		if ($len < 1) {
			alertx("请选择要删除的企业贷款信息");
		} else {
			var checkedValue = getCheckedValue("creditCompanyLoanIds");
			var url = "${ctx}/credit/creditAndLine/creditCompany/creditCompanyLoan/delete?ids=" + checkedValue;
			confirmx('确认要删除勾选的企业贷款信息吗？', function() {
				saveJson(url, null, function(data) {
					if (data) {
						if (data.status == 1) {
							alertx(data.message, function() {
								var creditCompanyId = '${creditCompanyId}';
								parent.$.loadDiv("creditCompanyDiv","${ctx }/credit/creditAndLine/creditCompany/list",{applyNo:'${applyNo}', readOnly:'${readOnly}'},"post");
								$.loadDiv("creditCompanyLoanDiv", "${ctx }/credit/creditAndLine/creditCompany/creditCompanyLoan/list", {
									creditCompanyId : creditCompanyId
								}, "post");
							});
						} else {
							alertx(data.message);
						}

					}
				});
			});
		}
	}

	//编辑
	function editCreditCompanyLoan() {
		var $checkLine = $("input[name='creditCompanyLoanIds']:checked");
		var $len = $checkLine.length;
		if ($len != 1) {//需要勾选一条信息进行修改
			alertx("请选择一条企业贷款信息！");
		} else {
			var url = "${ctx}/credit/creditAndLine/creditCompany/creditCompanyLoan/form?creditCompanyLoanId=" + $checkLine.val() + "&&readOnly=1"+"&applyNo="+'${applyNo}';
			openJBox("creditCompanyLoanBox", url, "编辑企业贷款", 900, 300, null);
		}

	}

</script>
</head>
<body>
	<div class="wrapper">
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 class="tableTitle">企业贷款信息列表</h3>
			<div class="ribbon filter">
				<ul class="layout">
					<li class="add">
						<a id="add" href="#" onclick="addCreditCompanyLoan();">
							<span>
								<b></b>
								新增
							</span>
						</a>
					</li>
					<li class="edit">
						<a id="edit" href="#" onclick="editCreditCompanyLoan();">
							<span>
								<b></b>
								编辑
							</span>
						</a>
					</li>
					<li class="delete">
						<a id="delete" href="#" onclick="deleteCreditCompanyLoan();">
							<span>
								<b></b>
								删除
							</span>
						</a>
					</li>
				</ul>
				<div id="tableDataId">
					<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
						<thead>
							<tr>
								<th width="5%">
									<input type="checkbox" name="allCheckCreditCompanyLoan" id="allCheckCreditCompanyLoan" onchange="allCheck('allCheckCreditCompanyLoan','creditCompanyLoanIds');" />
								</th>
								<th width="5%">序号</th>
								<th width="9%">发贷行</th>
								<th width="9%">贷款类型</th>
								<th width="9%">放款金额(元)</th>
								<th width="9%">余额(元)</th>
								<th width="9%">逾期金额(元)</th>
								<th width="9%">月内贷款到期</th>
								<th width="9%">贷款状态</th>
								<th width="9%">对外担保状态</th>
								<th width="9%">放款日期</th>
								<th width="9%">到期日期</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${creditCompanyLoanList}" var="creditCompanyLoan" varStatus="creditCompanyLoanList">
								<tr>
									<td>
										<input type="checkbox" id="creditCompanyLoanIds" name="creditCompanyLoanIds" value="${creditCompanyLoan.id }" />
									</td>
									<td>${creditCompanyLoanList.count }</td>
									<!-- 发贷行 -->
									<td id="bankName" class="title" title="${creditCompanyLoan.bankName}">${creditCompanyLoan.bankName}</td>
									<!-- 贷款类型 -->
									<td id="loanKind" class="title" title="${fns:getDictLabel(creditCompanyLoan.loanKind, 'KIND_LOAN', '')}">${fns:getDictLabel(creditCompanyLoan.loanKind, 'KIND_LOAN', '')}</td>
									<!-- 放款金额(元) -->
									<td id="loanAmount" class="title" title="${creditCompanyLoan.loanAmount}">${creditCompanyLoan.loanAmount}</td>
									<!-- 余额 -->
									<td id="balanceAmount" class="title" title="${creditCompanyLoan.balanceAmount}">${creditCompanyLoan.balanceAmount}</td>
									<!-- 逾期金额(元) -->
									<td id="overdueAmount" class="title" title="${creditCompanyLoan.overdueAmount}">${creditCompanyLoan.overdueAmount}</td>
									<!-- 月内贷款到期 -->
									<td id="monthExpireAmount" class="title" title="${creditCompanyLoan.monthExpireAmount}">${creditCompanyLoan.monthExpireAmount}</td>
									<!-- 贷款状态 -->
									<td id="loanStatus" class="title" title="${fns:getDictLabel(creditCompanyLoan.loanStatus, 'OUTER_GUARANTEE_STATUS', '')}">${fns:getDictLabel(creditCompanyLoan.loanStatus, 'OUTER_GUARANTEE_STATUS', '')}</td>
									<!-- 对外担保状态 -->
									<td id="guaranteeOutStat" class="title" title="${fns:getDictLabel(creditCompanyLoan.guaranteeOutStat, 'OUTER_GUARANTEE_STATUS', '')}">${fns:getDictLabel(creditCompanyLoan.guaranteeOutStat, 'OUTER_GUARANTEE_STATUS', '')}</td>
									<!-- 放款日期 -->
									<td id="loanDate" class="title" title="${creditCompanyLoan.loanDate}">${creditCompanyLoan.loanDate}</td>
									<!-- 到期日期 -->
									<td id="expireDate" class="title" title="${creditCompanyLoan.expireDate}">${creditCompanyLoan.expireDate}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>