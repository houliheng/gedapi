<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>外访信息管理</title>
<script type="text/javascript">
	$(document).ready(function() {

	});

	function add(allocateId, contractNo) {
		var url = "${ctx}/postloan/checkIndeed/form";
		openJBox("checkIndeedBox", url, "", $(window).width() - 50, $(window).height() - 50, {
		allocateId : allocateId,
		contractNo : contractNo
		});
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div id="indeedDiv" class="ribbon">
			<ul class="layout">
				<li class="add">
					<a href="#" onclick="add('${checkIndeed.allocateId}','${checkIndeed.contractNo}');">
						<span>
							<b></b>
							新增
						</span>
					</a>
				</li>
			</ul>
		</div>
		<div class="tableList">
			<h3 class="tableTitle">实地外访信息列表</h3>
			<div id="tableDataId">
				<table id="contentTable" cellpadding="0" cellspacing="0" border="0" width="100%">
					<thead>
						<tr>
							<th width="30px">序号</th>
							<th>外访日期</th>
							<th>外访人</th>
							<th>外访地点</th>
							<th>异常风险点</th>
							<th>外访详情</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="checkIndeed" varStatus="index">
							<c:if test="${0 == index.count%2}">
								<tr class="doubleRow">
							</c:if>
							<c:if test="${1 == index.count%2}">
								<tr>
							</c:if>
							<td id="num" class="title" title="序号">${index.index+1}</td>
							<td id="checkDate" class="title" title="${checkIndeed.checkDate}">
								<fmt:formatDate value="${checkIndeed.checkDate}" pattern="yyyy-MM-dd" />
							</td>
							<td id="checkUserName" class="title" title="${checkIndeed.checkUserName}">${checkIndeed.checkUserName}</td>
							<td id="checkAddress" class="title" title="${checkIndeed.checkAddress}">${checkIndeed.checkAddress}</td>
							<td id="riskPoint" class="title" title="${fns:getDictLabels(checkIndeed.riskPoint, 'CUST_RISK_POINT', '')}">${fns:getDictLabels(checkIndeed.riskPoint, 'CUST_RISK_POINT', '')}</td>
							<td id="description" class="title" title="${checkIndeed.description}">${checkIndeed.description}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>