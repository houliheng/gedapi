<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>企业征信信息管理</title>
<script type="text/javascript">
	$(document).ready(function() {
		if ('${readOnly}' == '0') {
			setPageReadOnly();
		}
	});
	//新增企业征信信息信息
	function addCreditCompany() {
		var url = "${ctx}/credit/creditAndLine/creditCompany/form";
		openJBox("creditCompanyBox", url, "新增企业征信信息",  $(window).width()-100, 300, {
			applyNo : '${applyNo }'
		});
	}

	//删除企业征信信息信息
	function deleteCreditCompany() {
		var $checkLine = $("input[name='creditCompanyIds']:checked");
		var $len = $checkLine.length;
		if ($len < 1) {
			alertx("请选择要删除的企业征信信息");
		} else {
			var checkedValue = getCheckedValue("creditCompanyIds");
			var url = "${ctx}/credit/creditAndLine/creditCompany/delete?ids=" + checkedValue;
			confirmx('确认要删除勾选的企业征信信息吗？', function() {
				saveJson(url, null, function(data) {
					if (data) {
						if (data.status == 1) {
							alertx(data.message, function() {
								$.loadDiv("creditCompanyDiv", "${ctx }/credit/creditAndLine/creditCompany/list", {
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
	function detailCreditCompany(creditCompanyId) {
		var url = "${ctx}/credit/creditAndLine/creditCompany/creditCompanyLoan/load";
		openJBox("creditCompany-detail", url, "企业贷款明细列表",  $(window).width()-100, 600, {
			creditCompanyId : creditCompanyId, readOnly:'${readOnly}',applyNo:'${applyNo}'
		});
	}

	//编辑
	function editCreditCompany() {
		var $checkLine = $("input[name='creditCompanyIds']:checked");
		var $len = $checkLine.length;
		if ($len != 1) {//需要勾选一条信息进行修改
			alertx("请选择一条企业征信信息！");
		} else {
			var url = "${ctx}/credit/creditAndLine/creditCompany/form?creditCompanyId=" + $checkLine.val() + "&&readOnly=1";
			openJBox("companyInfo-detai", url, "编辑企业征信信息",  $(window).width()-100, 300, null);
		}

	}
</script>
</head>
<body>
	<div class="wrapper">
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 class="tableTitle">企业征信列表</h3>
			<div class="ribbon filter">
				<ul class="layout">
					<li class="add">
						<a id="add" href="#" onclick="addCreditCompany();">
							<span>
								<b></b>
								新增
							</span>
						</a>
					</li>
					<li class="edit">
						<a id="edit" href="#" onclick="editCreditCompany();">
							<span>
								<b></b>
								编辑
							</span>
						</a>
					</li>
					<li class="delete">
						<a id="delete" href="#" onclick="deleteCreditCompany();">
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
						         <input id="allCheckCreditCompany" name="allCheckCreditCompany" type="checkbox" onclick="allCheck('allCheckCreditCompany','creditCompanyIds')">
						        </th>
						        <th width="3%" style="text-align:center;"><p>序</p><p>号</p></th>
						        <th width="10%" style="text-align:center;">企业类型</th>
						        <th width="10%" style="text-align:center;">企业名称</th>
						        <th width="10%" style="text-align:center;">打印时间</th>
						        <th width="14%" style="text-align:center;">证件号码</th>
						        <th width="14%" style="text-align:center;"><p>首笔信贷交易</p><p>记录年份</p></th>
						        <th width="14%" style="text-align:center;" scope="col"><p>办理过信贷业务</p><p>的机构数量</p></th> 
						        <th width="10%" style="text-align:center;">贷款总额<p>(元)</p></th>
						        <th width="10%" style="text-align:center;">贷款总余额<p>(元)</p></th>
						        <th width="10%" style="text-align:center;">贷款逾期总额<p>(元)</p></th>
						        <th width="10%" style="text-align:center;">对外担保总额<p>(元)</p></th>
						        <th width="10%" style="text-align:center;">操作</th>
						      </tr>
						</thead>
						<tbody>
							<c:forEach items="${creditCompanyList }" var="creditCompany" varStatus="creditCompanyList">
								<tr>
									<td>
										<input id="creditCompanyIds" name="creditCompanyIds" type="checkbox" value="${creditCompany.id }">
									</td>
									<td>${creditCompanyList.count }</td>
									<!-- 企业类型 -->
									<td id="roleType" class="title" title="${fns:getDictLabel(creditCompany.roleType, 'ROLE_TYPE', '')}">${fns:getDictLabel(creditCompany.roleType, 'ROLE_TYPE', '')}</td>
									<!-- 企业名称 -->
									<td id="companyName" class="title" title="${creditCompany.companyName}">${creditCompany.companyName}</td>
									<!-- 打印时间 -->
									<td id="printingTime" class="title" title="${creditCompany.printingTime}">${creditCompany.printingTime}</td>
									<!-- 证件号码 -->
									<td id="idNum" class="title" title="${creditCompany.idNum}">${creditCompany.idNum}</td>
									<!-- 首笔信贷交易记录年份 -->
									<td id="idNum" class="title" title="${creditCompany.firstRecordLoanYear}">${creditCompany.firstRecordLoanYear}</td>
									<!-- 办理过信贷业务的机构数量 -->
									<td id="idNum" class="title" title="${creditCompany.operaterOriganNum}">${creditCompany.operaterOriganNum}</td>
									<!-- 贷款总额 -->
									<td id="sumLoanAmount" class="title" title="${creditCompany.sumLoanAmount}">
										<c:if test="${empty creditCompany.sumLoanAmount}">0</c:if>
										<c:if test="${not empty creditCompany.sumLoanAmount}">${creditCompany.sumLoanAmount}</c:if>
									</td>
									<!-- 贷款总余额 -->
									<td id="sumBalanceAmount" class="title" title="${creditCompany.sumBalanceAmount}">
										<c:if test="${empty creditCompany.sumBalanceAmount}">0</c:if>
										<c:if test="${not empty creditCompany.sumBalanceAmount}">${creditCompany.sumBalanceAmount}</c:if>
									</td>
									<!-- 贷款逾期总额 -->
									<td id="sumOverdueAmount" class="title" title="${creditCompany.sumOverdueAmount}">
										<c:if test="${empty creditCompany.sumOverdueAmount}">0</c:if>
										<c:if test="${not empty creditCompany.sumOverdueAmount}">${creditCompany.sumOverdueAmount}</c:if>
									</td>
									<!-- 对外担保总额 -->
									<td id="guaranteeOutAmount" class="title" title="${creditCompany.guaranteeOutAmount}">${creditCompany.guaranteeOutAmount}</td>
									<!-- 操作-->
									<td>
										<a href="#" onclick="detailCreditCompany('${creditCompany.id}');">贷款明细</a>
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