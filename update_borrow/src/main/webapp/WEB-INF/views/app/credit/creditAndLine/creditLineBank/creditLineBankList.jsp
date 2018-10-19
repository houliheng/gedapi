<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>流水信息管理</title>
	<script type="text/javascript">
	$(document).ready(function() {
		if('${readOnly}' == '0'){
			setPageReadOnly();
		}
	});
		//新增银行卡流水信息
		function addCreditLineBank() {
			var url = "${ctx}/credit/creditAndLine/creditLineBank/form";
			openJBox("creditLineBank-form", url, "新增银行卡流水信息",$(window).width()-100, 300, {
				applyNo : '${applyNo }'
			});
		}

		//删除银行卡流水信息
		function deleteCreditLineBank() {
			var $checkLine = $("input[name='creditLineBankIds']:checked");
			var $len = $checkLine.length;
			if ($len < 1) {
				alertx("请选择要删除的银行卡流水信息");
			} else {
				var checkedValue = getCheckedValue("creditLineBankIds");
				var url = "${ctx}/credit/creditAndLine/creditLineBank/delete?ids=" + checkedValue;
				confirmx('确认要删除勾选的银行卡流水信息吗？', function() {
					saveJson(url, null, function(data) {
						if (data) {
							if (data.status == 1) {
								alertx(data.message, function() {
									$.loadDiv("creditLineBankDiv","${ctx }/credit/creditAndLine/creditLineBank/list",{applyNo:'${applyNo }'},"post");

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
		function detailCreditLineBank(creditLineBankId) {
			var url = "${ctx}/credit/creditAndLine/creditLineBank/creditLineBankDetail/list";
			openJBox("creditLineBank-detail", url, "流水明细列表", $(window).width()-100, 600, {creditLineBankId : creditLineBankId, readOnly:'${readOnly}',applyNo:'${applyNo}'});
		}

		//编辑
		function editCreditLineBank() {
			var $checkLine = $("input[name='creditLineBankIds']:checked");
			var $len = $checkLine.length;
			if ($len != 1) {//需要勾选一条信息进行修改
				alertx("请选择一条银行卡流水信息！");
			} else {
				var url = "${ctx}/credit/creditAndLine/creditLineBank/form?creditLineBankId="
						+ $checkLine.val() + "&&readOnly=1";
				openJBox("companyInfo-detai", url, "编辑银行卡流水信息", $(window).width()-100, 300, null);
			}

		}
		
		
		
	</script>
</head>
<body>
	<div class="wrapper">
		<sys:message content="${message}"/>
		<div class="tableList">
			<h3 class="tableTitle">流水信息列表</h3>
			<div class="ribbon filter">
		    	<ul class="layout">
		    		<li class="add"><a id="add" href="javascript:void(0)" onclick="addCreditLineBank();"><span><b></b>新增</span></a></li>
		        	<li class="edit"><a id="edit" href="javascript:void(0)" onclick="editCreditLineBank();" ><span><b></b>编辑</span></a></li>
		        	<li class="delete"><a id="delete" href="javascript:void(0)" onclick="deleteCreditLineBank();"><span><b></b>删除</span></a></li>
		        </ul>
			<div id="tableDataId">
				<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
					<thead>
						<tr>
							<th width="3%"><input id="checkAllCreditLineBank" name="checkAllCreditLineBank" type="checkbox" onclick="allCheck('checkAllCreditLineBank','creditLineBankIds')"></th>
							<th width="3%">序号</th>
							<th width="8%">户名</th>
							<th width="10%">开户行</th>
							<th width="15%">银行卡号</th>
							<th width="7%">打印日期</th>
							<th width="10%">余额(元)</th>
							<th width="10%">进项总额(元)</th>
							<th width="8%">出项总额(元)</th>
							<th width="10%">月均有效流水</th>
							<th width="15%">平均期末余额</th>
							<th width="10%">操作</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${creditLineBankList}" var="creditLineBank" varStatus="creditLineBankList">
						<tr>
							<td><input id="creditLineBankIds" type="checkbox" name="creditLineBankIds" value="${creditLineBank.id}"></td>
							<td>${creditLineBankList.index+1 }</td>
							<!-- 户名 -->
							<td id="custName" class="title" title="${creditLineBank.custName}">
								${creditLineBank.custName}
							</td>
							<!-- 开户行 -->
							<td id="bankName" class="title" title="${creditLineBank.bankName}">
								${creditLineBank.bankName}
							</td>
							<!-- 银行卡号 -->
							<td id="bankcardNo" class="title" title="${creditLineBank.bankcardNo}">
								${creditLineBank.bankcardNo}
							</td>
							<!-- 打印日期 -->
							<td id="printingDate" class="title" title="${creditLineBank.printingDate}">
								${creditLineBank.printingDate}
							</td>
							<!-- 余额 -->
							<td id="balanceAmount" class="title" title="${creditLineBank.balanceAmount}">
								<fmt:formatNumber value="${creditLineBank.balanceAmount}" pattern="###,##0.00"></fmt:formatNumber>
							</td>
							<!-- 进项总额 -->
							<td id="sumIncomeAmount" class="title" title="${creditLineBank.sumIncomeAmount}">
								<c:if test="${empty creditLineBank.sumIncomeAmount  }">0</c:if>
								<c:if test="${not empty creditLineBank.sumIncomeAmount  }">${creditLineBank.sumIncomeAmount }</c:if>
							</td>
							<!-- 出项总额 -->
							<td id="sumExpenseAmount" class="title" title="${creditLineBank.sumExpenseAmount}">
								<c:if test="${empty creditLineBank.sumExpenseAmount }">0</c:if>
								<c:if test="${not empty creditLineBank.sumExpenseAmount }">${creditLineBank.sumExpenseAmount }</c:if>
								
							</td>
							<!-- 月均有效流水 -->
							<td id="avgCurreValidLineAmount" class="title" title="${creditLineBank.avgCurreValidLineAmount }">
								<c:if test="${empty creditLineBank.avgCurreValidLineAmount }">0</c:if>
								<c:if test="${not empty creditLineBank.avgCurreValidLineAmount }">${creditLineBank.avgCurreValidLineAmount }</c:if>
							</td>
							<!-- 平均期末余额-->
							<td id="averageCycleEndAmount" class="title" title="${creditLineBank.averageCycleEndAmount}">
								<fmt:formatNumber value="${creditLineBank.averageCycleEndAmount}" pattern="###,##0.00"></fmt:formatNumber>
							</td>
							<!-- 操作 -->
							<td>
			    				<a href="#" onclick="detailCreditLineBank('${creditLineBank.id}')">流水明细</a>
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