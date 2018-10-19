<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">
	function dqs_xyjkshClick2() {
		$("#dqs_xyjksh2").toggle(600);
	}
</script>
<div class="wrapper">
	<div class="tab-content">
		<div class="tableList">
			<h3 onclick="dqs_xyjkshClick2()" class="tableTitle">信用借款审核(100W至300W)</h3>
			<div id="dqs_xyjksh2">
				<table id="contentTable" class="table table-bordered table-condensed">
					<thead>
						<tr>
							<th width="15%">审核科目</th>
							<th width="10%">审核对象</th>
							<th width="20%">审核工具</th>
							<th width="15%">材料要求</th>
							<th width="25%">禁入标准</th>
							<th width="5%">替换手段</th>
							<th width="5%">统计数量</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${xyjkshList2}" var="rule" varStatus="i">
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
