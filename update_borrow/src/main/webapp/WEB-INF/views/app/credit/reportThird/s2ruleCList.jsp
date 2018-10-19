<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">
	$(function() {

	});


	function tsbstkshClick() {
		$("#tsbstksh").toggle(600);
	}
</script>
<div class="wrapper">
	<div class="tab-content">
		<div class="tableList">
			<h3 onclick="tsbstkshClick()" class="tableTitle">特殊必诉条款审核</h3>
			<div id="tsbstksh">
				<table id="contentTable" class="table table-bordered table-condensed">
					<thead>
						<tr>
							<th width="45%">审核科目</th>
							<th width="25%">审核工具</th>
							<th width="25%">材料要求</th>
							<th width="5%">统计数量</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${tsbstkshList}" var="rule" varStatus="i">
							<tr>
								<td>${rule.reviewedBook}</td>
								<td>${rule.reviewedTool}</td>
								<td>${rule.dataRequied}</td>
								<td>${rule.bookCount}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
