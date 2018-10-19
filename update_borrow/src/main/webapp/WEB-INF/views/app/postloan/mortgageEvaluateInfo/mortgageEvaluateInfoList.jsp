<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>抵(质)押物管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {

	});
	
	//分页
	function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		if($("#pageNo")[0].value.length>10){
			top.$.jBox.tip('当前页码大小长度不能大于10位！');
			return true;
		}
		if($("#pageSize")[0].value.length>10){
			top.$.jBox.tip('每页条数大小的长度不能大于10位！');
			return true;
		}
		$("#checkDailySearchForm").submit();
	    return false;
	}
	
	//重置
	function mortgageReset(){
		$("#contractNo").val("");
		$("#custName").val("");
		$("#approProductTypeId").select2("val", "");
	}
	 	
	function details(urlSingle, title,contractNo,flag,contractAmount,applyNo) {
		var width = $(window).width() - 200;
		var height = $(window).height() - 200;
		var url = "${ctx}/postloan/mortgageEvaluateInfo/toDetailsPage?applyNo="+applyNo;
		openJBox('', url, title, width, height);
	}
/* 
       function showDetail(contractNo,infoId){
        var width = $(document).width() - 100;
	   	var height = $(document).height()-100;
		openJBox("tableDataCheck","${ctx}/postloan/mortgageEvaluateInfo/editInfo?contractNo="+contractNo+"&infoId="+infoId,"抵（质）押物检查",width,height);
	} */
</script>
</head>
<body>
	<div id="checkDailyContractListDiv" class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="checkDailySearchForm" modelAttribute="plContract" action="${ctx}/postloan/mortgageEvaluateInfo/list" method="post">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<div class="filter">
						<table class="searchTable">
							<tr>
								<td class="ft_label">合同编号：</td>
								<td class="ft_content">
									<input id="contractNo" name="contractNo" type="text" maxlength="50" class="input-medium" value="${plContract.contractNo }" />
								</td>
								<td class="ft_label">借款人姓名：</td>
								<td class="ft_content">
									<input id="custName" name="custName" type="text" maxlength="50" class="input-medium" value="${plContract.custName }" />
								</td>
								<td class="ft_label">业务分类：</td>
								<td class="ft_content">
									<form:select path="approProductTypeId" class="input-medium">
										<form:option value="" label="--全部--" />
										<form:options items="${fns:getDictList('PRODUCT_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />
							&nbsp;
							<input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="mortgageReset();" />
						</div>
					</div>
				</form:form>
			</div>
			<sys:message content="${message}" />
			<div class="tableList">
				<h3 class="tableTitle">数据列表</h3>
				<div class="ribbon">
				
				</div>
				<div id="tableDataId"  overflow: auto;">
					<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
						<thead>
							<tr>
								<th width="3%">序号</th>
								<th width="9%">合同编号</th>
								<th width="9%">放款日期</th>
								<th width="9%">借款人姓名</th>
								<th width="9%">批复额度</th>
								<th width="9%">合同期限</th>
								<th width="9%">每月还款日</th>
								<th width="9%">业务分类</th>
								<th width="9%">期数</th>
								<th width="9%">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${page.list }" var="plContract" varStatus="pl">
								<tr>
									
									<td class="title" title="${plContract.count }">${pl.count }</td>
									<td class="title" title="${plContract.contractNo }">${plContract.contractNo }</td>
									<td class="title" title="${plContract.loanDate }">${plContract.loanDate }</td>
									<td class="title" title="${plContract.custName }">${plContract.custName }</td>
									<td class="title" title="${plContract.loanAmount }">${plContract.loanAmount }</td>
									<td class="title" title="${plContract.conEndDate }">${plContract.conEndDate }</td>
									<td class="title" title="${plContract.repayDate }">${plContract.repayDate }</td>
									<td class="title" title="${plContract.approProductTypeName }">${plContract.approProductTypeName }</td>
									<td class="title" title="${plContract.approPeriodValue }">${plContract.approPeriodValue }</td>
										<td>
					<a href="javascript:void(0);" onclick="details('${ctx}/postloan/mortgageEvaluateInfo/toDetailsPage','抵质押物管理','${plContract.contractNo}','','','${plContract.applyNo}');">详情</a>
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