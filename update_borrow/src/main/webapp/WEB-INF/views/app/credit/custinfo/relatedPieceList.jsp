<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>关联进件</title>
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
</script>
</head>
<body>
	<div class="wrapper">
		<ul class="nav nav-tabs">
			<li>
				<a href="${ctx}/custinfo/custInfo/form?id=${custId}">客户详情</a>
			</li>
			<li class="active">
				<a href="${ctx}/credit/RelatedPiece?custId=${custId}">关联进件</a>
			</li>
		</ul>
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 class="tableTitle">关联进件信息列表</h3>
			<div id="tableDataId">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th>关联关系</th>
							<th>客户名称</th>
							<th>证件类型</th>
							<th>证件号码</th>
							<th>合同金额(元)</th>
							<th>登记日期</th>
							<th>登记门店</th>
							<th>状态</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="relatedPiece">
							<tr>
								<td id="roleType" class="title" title="${relatedPiece.roleType}">${fns:getDictLabel(relatedPiece.roleType, 'ROLE_TYPE', '')}</td>
								<td id="custName" class="title" title="${relatedPiece.custName}">${relatedPiece.custName}</td>
								<td id="idType" class="title" title="${relatedPiece.idType}">${relatedPiece.idType}</td>
								<td id="idNum" class="title" title="${relatedPiece.idNum}">${relatedPiece.idNum}</td>
								<td id="contractAmount" class="title" title="${relatedPiece.contractAmount}">${relatedPiece.contractAmount}</td>
								<td id="registerDate" class="title" title="${relatedPiece.registerDate}">${relatedPiece.registerDate}</td>
								<td id="orgId" class="title" title="${relatedPiece.orgId}">${relatedPiece.orgId}</td>
								<td id="applyStatus" class="title" title="${relatedPiece.applyStatus}">${fns:getDictLabel(relatedPiece.applyStatus, 'APPLY_STATUS', '')}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<%-- <div class="pagination">${page}</div> --%>
		</div>
	</div>
</body>
</html>