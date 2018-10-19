<%@ page contentType="text/html;charset=UTF-8"%>
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
<div class="wrapper">
	<sys:message content="${message}" />
	<div class="tableList">
		<h3 class="tableTitle">借前外访信息列表</h3>
		<div id="tableDataId" style="max-height:400px;overflow:auto;">
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
					<c:forEach items="${checkDoubtfullList}" var="checkDoubtful" varStatus="index">
						<c:if test="${0 == index.count%2}">
							<tr class="doubleRow">
						</c:if>
						<c:if test="${1 == index.count%2}">
							<tr>
						</c:if>
						<td id="num" class="title" title="序号">${index.index+1}</td>
						<td id="checkDate" class="title" title="${checkDoubtful.checkDate}">
							<fmt:formatDate value="${checkDoubtful.checkDate}" pattern="yyyy-MM-dd" />
						</td>
						<td id="checkUserName" class="title" title="${checkDoubtful.checkUserName}">${checkDoubtful.checkUserName}</td>
						<td id="checkAddress" class="title" title="${checkDoubtful.checkAddress}">${checkDoubtful.checkAddress}</td>
						<td id="riskPoint" class="title" title="${fns:getDictLabels(checkDoubtful.riskPoint, 'CUST_RISK_POINT', '')}">${fns:getDictLabels(checkDoubtful.riskPoint, 'CUST_RISK_POINT', '')}</td>
						<td id="description" class="title" title="${checkDoubtful.description}">${checkDoubtful.description}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>
