<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>合同查询</title>
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
		$("#applyNo").val('');
	}
	
	function updateContractNoForm(toUpdateId){
		var width = $(window).width() - 300;
		width = Math.max(width, 1000);
		var height = $(window).height() - 200;
		var url = "${ctx}/credit/contract/udpateContractNoForm?id=" + toUpdateId;
		openJBox("udpateContractNoFormBox", url, "合同号更改", width, height);
	}
	
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="contract" action="${ctx}/credit/contract/queryContractNoList" method="post" class="breadcrumb form-search">
						<table>
							<tr>
								<td class="ft_label">申请编号：</td>
								<td class="ft_content">
									<form:input path="applyNo" htmlEscape="false" maxlength="50" class="input-xlarge" />
								</td>
								<td class="ft_content">
										<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />&nbsp;
									<input id="btnReset" class="btn btn-primary" type="button" onclick="del()" value="重置" />
								</td>
							</tr>
						</table>
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
							<th width="8%">申请单号</th>
							<th width="12%">合同编号</th>
							<th width="8%">客户姓名</th>
							<th width="5%">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${contractList}" var="contract">
							<tr>
								<td class="title" title="${contract.applyNo}">${contract.applyNo}</td>
								<td class="title" title="${contract.contractNo}">${contract.contractNo}</td>
								<td class="title" title="${contract.custName}">${contract.custName}</td>
								<td>
									<a href="javaScript:void(0)" onclick="updateContractNoForm('${contract.id}');">修改合同号</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>