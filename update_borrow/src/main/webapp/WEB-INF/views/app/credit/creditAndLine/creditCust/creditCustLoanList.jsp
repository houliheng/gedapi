<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>征信个人贷款明细管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		if ('${readOnly}' == '0') {
			setPageReadOnly();
		}
	});
	//新增个人贷款信息
	function addCreditCustLoan() {
		var url = "${ctx}/credit/creditAndLine/creditCust/creditCustLoan/form";
		openJBox("creditCustLoanBox", url, "新增个人贷款信息", $(window).width()-100, 300, {
			creditCustId : '${creditCustId }',
			applyNo : '${applyNo}',
			readOnly : '${readOnly}'
		});
	}

	//删除个人贷款信息
	function deleteCreditCustLoan() {
		var $checkLine = $("input[name='creditCustLoanIds']:checked");
		var $len = $checkLine.length;
		if ($len < 1) {
			alertx("请选择要删除的个人贷款信息");
		} else {
			var checkedValue = getCheckedValue("creditCustLoanIds");
			var url = "${ctx}/credit/creditAndLine/creditCust/creditCustLoan/delete?ids=" + checkedValue;
			confirmx('确认要删除勾选的个人贷款信息吗？', function() {
				saveJson(url, null, function(data) {
					if (data) {
						if (data.status == 1) {
							alertx(data.message, function() {
								var creditCustId = '${creditCustId }';
								parent.$.loadDiv("creditCustDiv","${ctx }/credit/creditAndLine/creditCust/list",{applyNo:'${applyNo}', readOnly:'${readOnly}'},"post");
								window.location.href = "${ctx}/credit/creditAndLine/creditCust/creditCustLoan/list?creditCustId=" + creditCustId;
							  
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
	function editCreditCustLoan() {
		var $checkLine = $("input[name='creditCustLoanIds']:checked");
		var $len = $checkLine.length;
		if ($len != 1) {//需要勾选一条信息进行修改
			alertx("请选择一条个人贷款信息！");
		} else {
			var url = "${ctx}/credit/creditAndLine/creditCust/creditCustLoan/form?creditCustLoanId=" + $checkLine.val() + "&&readOnly=1"+"&applyNo="+'${applyNo}';
			openJBox("creditCustLoan-detai", url, "编辑个人贷款", $(window).width()-100, 300, null);
		}

	}
</script>
</head>
<body>
	<div id="creditCustLoanDiv">
		<div class="wrapper">
			<sys:message content="${message}" />
			<div class="tableList">
				<h3 class="tableTitle">个人贷款信息列表</h3>
				<div class="ribbon filter">
					<ul class="layout">
						<li class="add">
							<a id="add" href="#" onclick="addCreditCustLoan();">
								<span>
									<b></b>
									新增
								</span>
							</a>
						</li>
						<li class="edit">
							<a id="edit" href="#" onclick="editCreditCustLoan();">
								<span>
									<b></b>
									编辑
								</span>
							</a>
						</li>
						<li class="delete">
							<a id="delete" href="#" onclick="deleteCreditCustLoan();">
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
									<th width="3%">
										<input type="checkbox" name="allCheckCreditCustLoan" id="allCheckCreditCustLoan" onclick="allCheck('allCheckCreditCustLoan','creditCustLoanIds');" />
									</th>
									<th width="3%">序号</th>
									<th width="8%">发贷行</th>
									<th width="8%">贷款类型</th>
									<th width="8%">贷款状态</th>
									<th width="8%">放款日期</th>
									<th width="8%">到期日期</th>
									<th width="8%">担保方式</th>
									<th width="8%">放款金额(元)</th>
									<th width="8%">余额(元)</th>
									<th width="8%">逾期金额(元)</th>
									<th width="8%">月内贷款到期(元)</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${creditCustLoanList}" var="creditCustLoan" varStatus="creditCustLoanList">
									<tr>
										<td>
											<input type="checkbox" id="creditCustLoanIds" name="creditCustLoanIds" value="${creditCustLoan.id }" />
										</td>
										<td>${creditCustLoanList.count}</td>
										<td id="bankName" class="title" title="${creditCustLoan.bankName}">${creditCustLoan.bankName}</td>
										<td id="loanKind" class="title" title="${fns:getDictLabel(creditCustLoan.loanKind, 'KIND_LOAN', '')}">${fns:getDictLabel(creditCustLoan.loanKind, 'KIND_LOAN', '')}</td>
										<td id="loanStatus" class="title" title="${fns:getDictLabel(creditCustLoan.loanStatus, 'OUTER_GUARANTEE_STATUS', '')}">${fns:getDictLabel(creditCustLoan.loanStatus, 'OUTER_GUARANTEE_STATUS', '')}</td>
										<td id="loanDate" class="title" title="${creditCustLoan.loanDate}">${creditCustLoan.loanDate}</td>
										<td id="expireDate" class="title" title="${creditCustLoan.expireDate}">${creditCustLoan.expireDate}</td>
										<td id="guaranteeType" class="title" title="${fns:getDictLabel(creditCustLoan.guaranteeType, 'GUARANTEE_TYPE', '')}">${fns:getDictLabel(creditCustLoan.guaranteeType, 'GUARANTEE_TYPE', '')}</td>
										<td id="loanAmount" class="title" title="${creditCustLoan.loanAmount}">${creditCustLoan.loanAmount}</td>
										<td id="balanceAmount" class="title" title="${creditCustLoan.balanceAmount}">${creditCustLoan.balanceAmount}</td>
										<td id="overdueAmount" class="title" title="${creditCustLoan.overdueAmount}">${creditCustLoan.overdueAmount}</td>
										<td id="monthExpireAmount" class="title" title="${creditCustLoan.monthExpireAmount}">${creditCustLoan.monthExpireAmount}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>