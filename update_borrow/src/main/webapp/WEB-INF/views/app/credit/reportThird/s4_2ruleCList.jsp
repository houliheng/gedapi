<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">
	function dq_2hhjkshClick() {
		$("#dq_2hhjksh").toggle(600);
	}
</script>
<div class="wrapper">
	<div class="tab-content">
		<div class="tableList">
			<h3 onclick="dq_2hhjkshClick()" class="tableTitle">混合贷借款额在抵质押物市值的100%到150%之间的</h3>
			<div id="dq_2hhjksh">
				<table id="contentTable" class="table table-bordered table-condensed">
					<thead>
						<tr>
							<th width="10%">审核科目</th>
							<th width="15%">审核对象</th>
							<th width="15%">审核工具</th>
							<th width="10%">材料要求</th>
							<th width="30%">禁入标准</th>
							<th width="10%">替换手段</th>
							<th width="5%">统计数量</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${dq_2hhjkshList}" var="rule" varStatus="i">
							<tr>
								<td>${rule.reviewedBook}</td>
								<td>${rule.reviewedTarget}</td>
								<td>${rule.reviewedTool}</td>
								<td>${rule.dataRequied}</td>
								<td>${rule.banRule}</td>
								<td>${rule.replaceMeans}</td>
								<td>${rule.bookCount}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
