<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">
	function dq_fcdyjkshClick() {
		$("#dq_fcdyjksh").toggle(600);
	}
</script>
<div class="wrapper">
	<div class="tab-content">
		<div class="tableList">
			<h3 onclick="dq_fcdyjkshClick()" class="tableTitle">房产抵押借款审核</h3>
			<div id="dq_fcdyjksh">
				<table id="contentTable" class="table table-bordered table-condensed">
					<thead>
						<tr>
							<th width="10%">审核科目</th>
							<th width="10%">审核对象</th>
							<th width="15%">审核工具</th>
							<th width="10%">材料要求</th>
							<th width="45%">禁入标准</th>
							<th width="5%">替换手段</th>
							<th width="5%">统计数量</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${dq_fcdyjkshList}" var="rule" varStatus="i">
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
