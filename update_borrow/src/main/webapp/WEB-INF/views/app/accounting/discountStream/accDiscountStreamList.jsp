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
		$(".input-medium").val('');
		$(".select2-chosen").html("请选择");
		$(".class1").attr("selected", 'selected');
		$("#discountDate").val('');
	}

	function dicountPeriodNum(contractNo,periodNum) {
		var width = $(document).width() - 200;
		var newUrl= "${ctx}/accounting/accDiscount/discountConfirm?contractNo="+contractNo+"&periodNum="+periodNum;
		openJBox("discount-form", newUrl, "贴息确认", width, 500);
	}
	

</script>
<style type="text/css">
	.table th{
		text-align: center;
	}
	.tableList table td{
		text-align: center;
	}
</style>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="accDiscountStream" action="${ctx}/accounting/accDiscountStream?queryFlag=button" method="post" class="breadcrumb form-search">
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
									<label>贴息账户：</label>
									<form:input path="fromDiscountAccount" htmlEscape="false" maxlength="50" class="input-medium" />
								</td>
							</tr>
							<tr>
								<td>
									<label>期供状态：</label>
									<form:select path="periodNumStatus" class="input-medium">
										<form:option value="" class="class1" label="请选择" />
										<form:options items="${fns:getDictList('PERIOD_STATE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
								</td>
								<td>
									<label>贴息日期：</label>
									<input id="discountDate" name="discountDate" type="text"  maxlength="20" class="input-mini Wdate" value="${discountDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});" />
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
			<h3 class="tableTitle">数据列表</h3>
			<div id="tableDataId">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th width="5%">流水号</th>
							<th width="9%">合同编号</th>
							<th width="4%">期数</th>
							<th width="7%">贴息日期</th>
							<th width="6%">应贴息金额</th>
							<th width="8%">已贴息金额</th>
							<th width="8%">贴息账户</th>
							<th width="10%">贴息入账账户类型 </th>
							<th width="7%">贴息入账账户</th>
							<th width="6%">操作人</th>
							<th width="6%">大区</th>
							<th width="6%">区域</th>
							<th width="8%">分公司</th>
							<th width="6%">贴息状态</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="dicountStream" varStatus="page">
							<tr>
								<td class="title" title="${dicountStream.seqNo}">${dicountStream.seqNo}</td>
								<td class="title" title="${dicountStream.contractNo}">${dicountStream.contractNo}</td>
								<td class="title" title="${dicountStream.periodNum}">${dicountStream.periodNum}</td>
								<td class="title" title="<fmt:formatDate value="${dicountStream.discountDate}" pattern="yyyy-MM-dd"/>"><fmt:formatDate value="${dicountStream.discountDate}" pattern="yyyy-MM-dd"/></td>
								<td class="title" title="${dicountStream.discountFee}">${dicountStream.discountFee}</td>
								<td class="title" title="${dicountStream.factDiscountFee}">${dicountStream.factDiscountFee}</td>
								<td class="title" title="${dicountStream.fromDiscountAccount}">${dicountStream.fromDiscountAccount}</td>
								<td class="title" title="${fns:getDictLabel(dicountStream.discountType, 'DIACOUNT_TYPE', '')}">${fns:getDictLabel(dicountStream.discountType, 'DIACOUNT_TYPE', '')}</td>
								<td class="title" title="${dicountStream.toAccount}">${dicountStream.toAccount}</td>
								<td class="title" title="${dicountStream.custName}">${dicountStream.custName}</td>
								<td class="title" title="${dicountStream.orgLevel2.name}">${dicountStream.orgLevel2.name}</td>
								<td class="title" title="${dicountStream.orgLevel3.name}">${dicountStream.orgLevel3.name}</td>
								<td class="title" title="${dicountStream.orgLevel4.name}">${dicountStream.orgLevel4.name}</td>
								<td class="title" title="${fns:getDictLabel(dicountStream.discountStatus, 'DIACOUNT_STATUS', '')}">${fns:getDictLabel(dicountStream.discountStatus, 'DIACOUNT_STATUS', '')}</td>
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