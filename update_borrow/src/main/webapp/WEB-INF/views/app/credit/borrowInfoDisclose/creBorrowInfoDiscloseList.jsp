<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>借后信息披露管理</title>
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
	</script>
</head>
<body>
	<div class="wrapper">
		<ul class="nav nav-tabs">
			<li class="active"><a href="${ctx}/credit/creBorrowInfoDisclose/">借后信息披露列表</a></li>
			<shiro:hasPermission name="credit:creBorrowInfoDisclose:edit"><li><a href="${ctx}/credit/creBorrowInfoDisclose/form">借后信息披露添加</a></li></shiro:hasPermission>
		</ul>
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="creBorrowInfoDisclose" action="${ctx}/credit/creBorrowInfoDisclose/" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="filter">
						<ul class="ul-form">
							<li><label>合同编号：</label>
								<form:input path="contractNo" htmlEscape="false" maxlength="50" class="input-medium"/>
							</li>
							<li><label>客户名称：</label>
								<form:input path="custName" htmlEscape="false" maxlength="20" class="input-medium"/>
							</li>
							<li><label>放款日期：</label>
								<input name="beginLoanDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
									value="<fmt:formatDate value="${creBorrowInfoDisclose.beginLoanDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
								<input name="endLoanDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
									value="<fmt:formatDate value="${creBorrowInfoDisclose.endLoanDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
							</li>
							<li><label>首次披露时间：</label>
								<input name="beginFirstDiscloDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
									value="<fmt:formatDate value="${creBorrowInfoDisclose.beginFirstDiscloDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
								<input name="endFirstDiscloDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
									value="<fmt:formatDate value="${creBorrowInfoDisclose.endFirstDiscloDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
							</li>
						</ul>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />&nbsp; 
							<input id="btnReset"class="btn btn-primary" type="button" value="重置"/>
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
							<th>合同编号</th>
							<th>客户名称</th>
							<th>放款日期</th>
							<th>合同金额</th>
							<th>期限</th>
							<th>大区</th>
							<th>区域</th>
							<th>分公司</th>
							<th>首次披露时间</th>
							<th>披露次数</th>
							<th>是否已推送冠E通（0未推送，1已推送）</th>
							<th>更新时间</th>
							<shiro:hasPermission name="credit:creBorrowInfoDisclose:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${page.list}" var="creBorrowInfoDisclose">
						<tr>
							<td id="contractNo" class="title" title="${creBorrowInfoDisclose.contractNo}"><a href="${ctx}/credit/creBorrowInfoDisclose/form?id=${creBorrowInfoDisclose.id}">
								${creBorrowInfoDisclose.contractNo}
							</a></td>
							<td id="custName" class="title" title="${creBorrowInfoDisclose.custName}">
								${creBorrowInfoDisclose.custName}
							</td>
							<td id="loanDate" class="title" title="${creBorrowInfoDisclose.loanDate}">
								<fmt:formatDate value="${creBorrowInfoDisclose.loanDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td id="contractAmount" class="title" title="${creBorrowInfoDisclose.contractAmount}">
								${creBorrowInfoDisclose.contractAmount}
							</td>
							<td id="periodValue" class="title" title="${creBorrowInfoDisclose.periodValue}">
								${creBorrowInfoDisclose.periodValue}
							</td>
							<td id="orgLevel2" class="title" title="${creBorrowInfoDisclose.orgLevel2}">
								${creBorrowInfoDisclose.orgLevel2}
							</td>
							<td id="orgLevel3" class="title" title="${creBorrowInfoDisclose.orgLevel3}">
								${creBorrowInfoDisclose.orgLevel3}
							</td>
							<td id="orgLevel4" class="title" title="${creBorrowInfoDisclose.orgLevel4}">
								${creBorrowInfoDisclose.orgLevel4}
							</td>
							<td id="firstDiscloDate" class="title" title="${creBorrowInfoDisclose.firstDiscloDate}">
								<fmt:formatDate value="${creBorrowInfoDisclose.firstDiscloDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td id="discloFrequency" class="title" title="${creBorrowInfoDisclose.discloFrequency}">
								${creBorrowInfoDisclose.discloFrequency}
							</td>
							<td id="push" class="title" title="${creBorrowInfoDisclose.push}">
								${creBorrowInfoDisclose.push}
							</td>
							<td id="updateDate" class="title" title="${creBorrowInfoDisclose.updateDate}">
								<fmt:formatDate value="${creBorrowInfoDisclose.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<shiro:hasPermission name="credit:creBorrowInfoDisclose:edit"><td>
			    				<a href="${ctx}/credit/creBorrowInfoDisclose/form?id=${creBorrowInfoDisclose.id}">修改</a>
								<a href="${ctx}/credit/creBorrowInfoDisclose/delete?id=${creBorrowInfoDisclose.id}" onclick="return confirmx('确认要删除该借后信息披露吗？', this.href)">删除</a>
							</td></shiro:hasPermission>
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