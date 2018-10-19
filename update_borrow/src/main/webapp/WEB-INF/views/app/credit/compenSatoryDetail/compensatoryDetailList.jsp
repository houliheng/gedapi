<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>代偿详情表管理</title>
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
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="compensatoryDetail" action="${ctx}/credit/compensatoryDetail/" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="filter">
						<table class="fromTable filter">
								<tr>
									<td class="ft_label">合同编号：</td>
									<td class="ft_content">
										<input type="text" id="contractNo" name="contractNo"  maxlength="18" htmlEscape="false" class=" input-medium" />
									</td>
									<td class="ft_label">期数：</td>
									<td class="ft_content">
										<input type="text"  id="periodNum" name="periodNum"  maxlength="18" htmlEscape="false" class=" input-medium" />
									</td>
								</tr>
								<tr>
									<td class="ft_label">代偿状态：</td>
									<td class="ft_content">
										<select name="compensatoryStatus" id="compensatoryStatus" class="input-medium" style="width:176px;">
											<option></option>
											<option value="0">代偿成功</option>
											<option value="1">代偿失败</option>
										</select>
									</td>
									<td class="ft_label">代偿账户：</td>
									<td class="ft_content">
										<input type="text" id="compensatoryAccount"   name="compensatoryAccount" maxlength="18" htmlEscape="false" class=" input-medium " />
									</td>
								</tr>
							</table>
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
							<th>序号</th>
							<th>流水号</th>
							<th>合同编号</th>
							<th>期数</th>
							<th>已经代偿金额</th>
							<th>代偿账户</th>
							<th>代偿时间</th>
							<th>状态</th>
							<th>失败原因</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${page.list}" var="compensatoryDetail" varStatus="status">
						<tr>
							<td class="title" title="${status.index + 1}">
								${status.index + 1}
							</td>
							<td class="title" title="${compensatoryDetail.serialNum}">
								${compensatoryDetail.serialNum}
							</td>
							<td class="title" title="${compensatoryDetail.contractNo}">
								${compensatoryDetail.contractNo}
							</td>
							<td class="title" title="${compensatoryDetail.periodNum}">
								${compensatoryDetail.periodNum}
							</td>
							<td class="title" title="${compensatoryDetail.compensatoryAmount}">
								${compensatoryDetail.compensatoryAmount}
							</td>
							<td class="title" title="${compensatoryDetail.compensatoryAccount}">
								${compensatoryDetail.compensatoryAccount}
							</td>
							<td class="title" title="${compensatoryDetail.updateDate}">
								<fmt:formatDate value="${compensatoryDetail.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td class="title" title="${compensatoryDetail.compensatoryStatus}">
								${compensatoryDetail.compensatoryStatus}
							</td>
							<td class="title" title="${compensatoryDetail.failReason}">
								${compensatoryDetail.failReason}
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