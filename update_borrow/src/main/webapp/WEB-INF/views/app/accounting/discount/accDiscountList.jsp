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
		$(".select2-chosen").html("请选择");
		$(".class1").attr("selected", 'selected');
	}

	function dicountPeriodNum(contractNo,periodNum) {
		$.post("${ctx}/accounting/accDiscountStream/validate",{contractNo:contractNo,periodNum:periodNum},function(data){
			if(data){
				closeTip();
				if(data.status == '1'){
					var width = $(document).width() - 200;
					var newUrl= "${ctx}/accounting/accDiscount/discountConfirm?contractNo="+contractNo+"&periodNum="+periodNum;
					openJBox("discount-form", newUrl, "贴息确认", width, 500);
				}else{
				alertx(data.message);
				}
			}
		});
		
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
				<form:form id="searchForm" modelAttribute="discountVo" action="${ctx}/accounting/accDiscount?queryFlag=button" method="post" class="breadcrumb form-search">
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
									<label>期供状态：</label>
									<form:select path="periodStatus" class="input-medium">
										<form:option value="" class="class1" label="请选择" />
										<form:options items="${fns:getDictList('PERIOD_STATE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
								</td>
							</tr>
							<tr>
								<td>
									<label>企业名称：</label>
									<form:input path="custName" htmlEscape="false" maxlength="20" class="input-medium" />
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
			<h3 class="tableTitle">数据列表&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			贴息总额(元):${discountCount}
			</h3>
			<div id="tableDataId">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th width="5%">操作</th>
							<th width="9%">合同编号</th>
							<th width="6%">企业名称</th>
							<th width="7%">产品名称</th>
							<th width="5%">期数</th>
							<th width="8%">应还日期</th>
							<th width="8%">当期应还本息</th>
							<th width="8%">客户待还金额</th>
							<th width="7%">客户实还金额</th>
							<th width="7%">应贴息金额</th>
							<th width="6%">期供状态</th>
							<th width="6%">大区</th>
							<th width="6%">区域</th>
							<th width="8%">分公司</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="discountVo" varStatus="page">
							<tr>
								<td><a href="#" onclick="dicountPeriodNum('${discountVo.contractNo}','${discountVo.periodNum}')">贴息</a></td>
								<td class="title" title="${discountVo.contractNo}">${discountVo.contractNo}</td>
								<td class="title" title="${discountVo.custName}">${discountVo.custName}</td>
								<td class="title" title="${discountVo.approProductName}">${discountVo.approProductName}</td>
								<td class="title" title="${discountVo.periodNum}">${discountVo.periodNum}</td>
								<td class="title" title="${discountVo.deductDate}">${discountVo.deductDate}</td>
								<td class="title" title="${discountVo.repayAmount}">${discountVo.repayAmount}</td>
								<td class="title" title="${discountVo.stayAmount}">${discountVo.stayAmount}</td>
								<td class="title" title="${discountVo.factAmount}">${discountVo.factAmount}</td>
								<td class="title" title="${discountVo.discountFee}">${discountVo.discountFee}</td>
								<td class="title" title="${fns:getDictLabel(discountVo.periodStatus, 'PERIOD_STATE', '')}">${fns:getDictLabel(discountVo.periodStatus, 'PERIOD_STATE', '')}</td>
								<td class="title" title="${discountVo.orgLevel2.name}">${discountVo.orgLevel2.name}</td>
								<td class="title" title="${discountVo.orgLevel3.name}">${discountVo.orgLevel3.name}</td>
								<td class="title" title="${discountVo.orgLevel4.name}">${discountVo.orgLevel4.name}</td>
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