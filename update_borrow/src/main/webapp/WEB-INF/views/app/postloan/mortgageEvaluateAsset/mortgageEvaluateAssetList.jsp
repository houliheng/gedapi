<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>资产评估管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
   
        //新增资产
     function showAsset(applyNo){
        var width = $(window).width() - 100;
	   	var height = $(window).height()-100;
		openJBox("AddAssetSelect","${ctx}/postloan/mortgageEvaluateAsset/addAsset","新增资产信息",1200,400,{applyNo:applyNo});
	}
		//重置
	function resetAsset() {
		$("#contractNo").val('');
		$("#custName").val(''); 
	}
	</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="mortgageEvaluateAsset" action="${ctx}/postloan/mortgageEvaluateAsset/list" method="post" >
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="filter">
						<table class="searchTable">
							 <tr>
								<td class="ft_label">合同编号：</td>
								<td class="ft_content"><input id="contractNo" name="contractNo" type="text" maxlength="50" class="input-medium" value="${mortgageEvaluateAsset.contractNo}" /></td>
								<td class="ft_label">借款人姓名：</td>
								<td class="ft_content"><input id="custName" name="custName" type="text" maxlength="50" class="input-medium"   value="${mortgageEvaluateAsset.custName}"/></td>
							</tr> 
						</table>
						<div class="searchButton">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />
							&nbsp;
							<input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="resetAsset();" />
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<sys:message content="${message}"/>
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div id="tableDataId">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
							<tr>
						<th width="3%">序号</th>
								<th width="10%">合同编号</th>
								<th width="10%">放款日期</th>
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
					<c:forEach items="${page.list}" var="mortgageEvaluateAsset" varStatus="i">
						<tr>
							<td class="title" title="${i.index+1}">
								${i.index+1}
							</td>
						 <td class="title" title="${mortgageEvaluateAsset.contractNo}">${mortgageEvaluateAsset.contractNo}</td>
						 		<td class="title" title="${mortgageEvaluateAsset.loanDate }">${mortgageEvaluateAsset.loanDate }</td>
									<td class="title" title="${mortgageEvaluateAsset.custName }">${mortgageEvaluateAsset.custName }</td>
									<td class="title" title="${mortgageEvaluateAsset.loanAmount }">${mortgageEvaluateAsset.loanAmount }</td>
									<td class="title" title="${mortgageEvaluateAsset.conEndDate }">${mortgageEvaluateAsset.conEndDate }</td>
									<td class="title" title="${mortgageEvaluateAsset.repayDate }">${mortgageEvaluateAsset.repayDate }</td>
									<td class="title" title="${mortgageEvaluateAsset.approProductTypeName }">${mortgageEvaluateAsset.approProductTypeName }</td>
									<td class="title" title="${mortgageEvaluateAsset.approPeriodValue }">${mortgageEvaluateAsset.approPeriodValue }</td>
						 	<td>	
						 	<a href="javascript:void(0)" onclick="showAsset('${mortgageEvaluateAsset.applyNo}')">资产详情</a>
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