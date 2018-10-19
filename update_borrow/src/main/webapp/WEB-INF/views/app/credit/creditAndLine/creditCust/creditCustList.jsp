<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>个人征信信息管理</title>
<script type="text/javascript">
	$(document).ready(function() {
		if ('${readOnly}' == '0') {
			setPageReadOnly();
		}
	});
	//新增个人征信信息
	function addCreditCust() {
		var url = "${ctx}/credit/creditAndLine/creditCust/form";
		openJBox("creditCust-form", url, "新增个人征信信息", $(window).width()-100, 600, {
		applyNo : '${applyNo }',
		readOnly : '${readOnly}'
		});
	}

	//删除个人征信信息
	function deleteCreditCust() {
		var $checkLine = $("input[name='creditCustIds']:checked");
		var $len = $checkLine.length;
		if ($len < 1) {
			alertx("请选择要删除的个人征信信息");
		} else {
			var checkedValue = getCheckedValue("creditCustIds");
			var url = "${ctx}/credit/creditAndLine/creditCust/delete?ids=" + checkedValue;
			confirmx('确认要删除勾选的个人征信信息吗？', function() {
				saveJson(url, null, function(data) {
					if (data) {
						if (data.status == 1) {
							alertx(data.message, function() {
								$.loadDiv("creditCustDiv", "${ctx }/credit/creditAndLine/creditCust/list", {
									applyNo : '${applyNo }'
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

	//详情
	function detailCreditCust(creditCustId) {
		var url = "${ctx}/credit/creditAndLine/creditCust/creditCustLoan/list";
		openJBox("creditCust-detail", url, "个人贷款明细列表", $(window).width()-100, 600, {
		creditCustId : creditCustId,
		readOnly : '${readOnly}',
		applyNo : '${applyNo}'
		});
	}
	
	
	//个人征信详情
	function creditCustDetail(creditCustId) {
		var url = "${ctx}/credit/creditAndLine/creditCust/form?creditCustId=" + creditCustId + "&&readOnly=0";
		openJBox("companyInfo-detai", url, "个人征信详情", $(window).width()-100, 600, null);
	}
	
	//编辑
	function editCreditCust() {
		var $checkLine = $("input[name='creditCustIds']:checked");
		var $len = $checkLine.length;
		if ($len != 1) {//需要勾选一条信息进行修改
			alertx("请选择一条个人征信信息！");
		} else {
			var url = "${ctx}/credit/creditAndLine/creditCust/form?creditCustId=" + $checkLine.val() + "&&readOnly=1";
			openJBox("companyInfo-detai", url, "编辑个人征信信息", $(window).width()-100, 600, null);
		}

	}
</script>
</head>
<body>
	<div class="wrapper">
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 class="tableTitle">个人征信列表</h3>
			<div class="ribbon filter">
				<ul class="layout">
					<li class="add">
						<a id="add" href="#" onclick="addCreditCust();">
							<span>
								<b></b>
								新增
							</span>
						</a>
					</li>
					<li class="edit">
						<a id="edit" href="#" onclick="editCreditCust();">
							<span>
								<b></b>
								编辑
							</span>
						</a>
					</li>
					<li class="delete">
						<a id="delete" href="#" onclick="deleteCreditCust();">
							<span>
								<b></b>
								删除
							</span>
						</a>
					</li>
				</ul>
				<div id="tableDataId">
					<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover" style="table-layout: fixed;">
						<thead>
							<tr>
								<th width="2%">
									<input type="checkbox" id="creditCustCheckAll" name="creditCustCheckAll" onclick="allCheck('creditCustCheckAll','creditCustIds')" />
								</th>
								<th width="3%">序号</th>
								<th width="5%">人员类型</th>
								<th width="4%">姓名</th>
								<th width="8%">打印日期</th>
								<th width="10%">本人年内查询次数</th>
								<th width="8%">信用卡总数量</th>
								<th width="10%">信用卡总授信额度(元)</th>
								<th width="10%">信用卡总使用额度(元)</th>
								<th width="11%">信用卡当前逾期金额(元)</th>
								<th width="6%">贷款总额度(元)</th>
								<th width="6%">贷款总余额</th>
								<th width="7%">贷款逾期总额</th>
								<th width="9%">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${creditCustList}" var="creditCust" varStatus="creditCustList">
								<tr>
									<td>
										<input id="creditCustIds" type="checkbox" name="creditCustIds" value="${creditCust.id}" />
									</td>
									<td>${creditCustList.count }</td>
									<!-- 人员类型 -->
									<td id="roleType" class="title" title="${fns:getDictLabel(creditCust.roleType, 'ROLE_TYPE', '')}">${fns:getDictLabel(creditCust.roleType, 'ROLE_TYPE', '')}</td>
									<!-- 姓名 -->
									<td id="custName" class="title" title="${creditCust.custName}">${creditCust.custName}</td>
									<!-- 打印日期 -->
									<td id="printingDate" class="title" title="<fmt:formatDate value='${creditCust.printingDate}' pattern='yyyy-MM-dd' />">
										<fmt:formatDate value="${creditCust.printingDate}" pattern="yyyy-MM-dd" />
									</td>
									<!-- 本人年内查询次数 -->
									<td id="queryNumYear" class="title" title="${creditCust.queryNumYear}">${creditCust.queryNumYear}</td>
									<!-- 信用卡总数量 -->
									<td id="allCreditCardNum" class="title" title="${creditCust.allCreditCardNum}">${creditCust.allCreditCardNum}</td>
									<!-- 信用卡总授信额度(元) -->
									<td id="allCreditAmount" class="title" title="${creditCust.allCreditAmount}">${creditCust.allCreditAmount}</td>
									<!-- 信用卡总使用额度(元) -->
									<td id="allUsedAmount" class="title" title="${creditCust.allUsedAmount}">${creditCust.allUsedAmount}</td>
									<!-- 信用卡当前逾期金额(元) -->
									<td id="allOverdueAmount" class="title" title="${creditCust.allOverdueAmount}">${creditCust.allOverdueAmount}</td>
									<!-- 贷款总额度(元) -->
									<td id="sumLoanAmount" class="title" title="${creditCust.sumLoanAmount}">
										<c:if test="${empty creditCust.sumLoanAmount}">0</c:if>
										<c:if test="${not empty creditCust.sumLoanAmount}">${creditCust.sumLoanAmount}</c:if>
									</td>
									<!-- 贷款总余额 -->
									<td id="sumBalanceAmount" class="title" title="${creditCust.sumBalanceAmount}">
										<c:if test="${empty creditCust.sumBalanceAmount}">0</c:if>
										<c:if test="${not empty creditCust.sumBalanceAmount}">${creditCust.sumBalanceAmount}</c:if>
									</td>
									<!-- 贷款逾期总额 -->
									<td id="sumOverdueAmount" class="title" title="${creditCust.sumOverdueAmount}">
										<c:if test="${empty creditCust.sumOverdueAmount}">0</c:if>
										<c:if test="${not empty creditCust.sumOverdueAmount}">${creditCust.sumOverdueAmount}</c:if>
									</td>
									<!-- 操作-->
									<td>
										<a href="#" onclick="detailCreditCust('${creditCust.id}')">贷款明细</a>
										<a href="#" onclick="creditCustDetail('${creditCust.id}')">详情</a>
									</td>
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