<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>合同还款明细查询管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {

	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}

	//重置按钮
	function del() {     
		$("#contractNo").val('');
		$("#custName").val('');
		$("#approProductName").val("");
	}

	function queryContractDetail(contractNo) {
		var width = $(document).width() - 200;
		var newUrl= "${ctx}/accounting/accDiscount/discountLeading";
		openJBox("discount-form", newUrl, "批量贴息导入", width, 500, {
			contractNo : contractNo
		});
	}
	
	function contractDetail(contractNo) {
	//	alertx(contractNo);
		loading();
 		var newUrl= "${ctx}/accounting/accDiscountStream/contractDiscountDertail?contractNo="+contractNo;
		 $("#searchForm").attr('action',newUrl);
	     $("#searchForm").submit();
	}
	
	function batchImport() {
		var width = $(document).width() - 200;
		var newUrl= "${ctx}/accounting/accDiscount/discountLeading";
		openJBox("discount-form", newUrl, "批量贴息导入", width, 500, {
			contractNo : ""
		});
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="discount" action="${ctx}/accounting/discount?queryFlag=button" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<div class="filter">
						<table class="fromTable">
							<tr>
								<td>
									<label>合同编号：</label>
									<form:input path="contractNo" htmlEscape="false" maxlength="50" class="input-medium" />
								</td>
								<td>
									<label>企业名称：</label>
									<form:input path="custName" htmlEscape="false" maxlength="20" class="input-medium" />
								</td>
							</tr>
							<tr>
								<td>
									<label>产品名称：</label>
									<form:input path="approProductName" htmlEscape="false" maxlength="20" class="input-medium" />
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />
							&nbsp;
							<input id="btnReset" class="btn btn-primary" type="button" onclick="del()" value="重置" />
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 class="tableTitle">数据列表
			<a href="#" onclick="batchImport()" style="margin-left:900px">批量贴息计划导入</a>
			</h3>
			<div id="tableDataId">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th width="3%">序号</th>
							<th width="8%">合同编号</th>
							<th width="6%">企业名称</th>
							<th width="7%">产品名称</th>
							<th width="8%">合同金额</th>
							<th width="5%">期限</th>
							<th width="8%">应贴息总金额</th>
							<th width="8%">已贴息总金额</th>
							<th width="8%">未贴息总金额</th>
							<th width="8%">大区</th>
							<th width="6%">区域</th>
							<th width="8%">分公司</th>
							<th width="5%">导入状态</th>
							<th width="8%">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="discount" varStatus="page">
							<tr>
								<td>${page.index+1}</td>
								<td class="title" title="${discount.contractNo}">
								<a href="#" onclick="contractDetail('${discount.contractNo}')">${discount.contractNo}</a>
								</td>
								<td class="title" title="${discount.custName}">${discount.custName}</td>
								<td class="title" title="${discount.approProductName}">${discount.approProductName}</td>
								<td class="title" title="${discount.contractAmount}">${discount.contractAmount}</td>
								<td class="title" title="${discount.approPeriodValue}">${discount.approPeriodValue}</td>
								<td class="title" title="${discount.discountFee}">${discount.discountFee}</td>
								<td class="title" title="${discount.factDiscountFee}">${discount.factDiscountFee}</td>
								<td class="title" title="${discount.noDiscountFee}">${discount.noDiscountFee}</td>
								<td class="title" title="${discount.orgLevel2.name}">${discount.orgLevel2.name}</td>
								<td class="title" title="${discount.orgLevel3.name}">${discount.orgLevel3.name}</td>
								<td class="title" title="${discount.orgLevel4.name}">${discount.orgLevel4.name}</td>
								<td class="title" title="${discount.importStatus}">
								<c:if test="${discount.importStatus == '0'}">
								已导入
								</c:if>
								<c:if test="${discount.importStatus == '1'}">
								未导入
								</c:if>
								</td>
								<td>
									<%--<c:if test="${discount.contractStatus == '0800' or discount.contractStatus== '0600'}">
										<a href="#" onclick="queryContractDetail('${discount.contractNo}')">贴息计划导入</a>
									</c:if>
									<c:if test="${discount.contractStatus != '0800' && discount.contractStatus != '0600'}">
										贴息计划导入
									</c:if>
									<c:if test="${discount.discountSave == '1'}">
										<a></a>
									</c:if>--%>
                                    <c:choose>
										<c:when test="${discount.discountSave == '1'}">
											贴息计划导入
										</c:when>
                                        <c:when test="${(discount.contractStatus == '0800' or discount.contractStatus== '0600') && discount.underFlag != '1'}">
											<a href="#" onclick="queryContractDetail('${discount.contractNo}')">贴息计划导入</a>
										</c:when>

										<c:when test="${discount.contractStatus != '0800' && discount.contractStatus != '0600'}">
											贴息计划导入
										</c:when>
										<c:when test="${discount.underFlag == '1'}">
											贴息计划导入
										</c:when>
											
									</c:choose>

								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="pagination">${page}</div>
		</div>
	</div>
</body>
</html>