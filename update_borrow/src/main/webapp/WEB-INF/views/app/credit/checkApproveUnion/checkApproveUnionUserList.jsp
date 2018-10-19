<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>授信人员变更</title>
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
	function saveFreeStaff() {
		var selectedUser = $("input[name='freeStaff']:checked");
		if (selectedUser.length != 1) {
			alertx("请选择一个欲绑定人员!");
		} else {
			loading();
			var checkApproveUnionId = selectedUser.attr("checkApproveUnionId");
			var custName = selectedUser.attr("custName");
			var id = selectedUser.attr("value");
			$.ajax({
				url : "${ctx}/credit/checkApproveUnion/saveRelationRelated",
				data : {
				id : checkApproveUnionId,
				newCustId : id,
				newCustName:custName
				},
				type : "POST",
				success : function(data) {
					closeTip();
					if (data.status == "1") {
						alertx(data.message, function() {
							parent.$.loadDiv("checkApproveUnionListId", "${ctx}/credit/checkApproveUnion/list", {
								applyNo : "${checkApproveUnion.applyNo}"
							}, "post");
							closeJBox();
						});
					} else {
						alertx(data.message);
					}
				}
			});
		}
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="ribbon">
			<ul class="layout">
				<li class="mcp_change">
					<a href="#" onclick="saveFreeStaff()" id="assign">
						<span>
							<b></b>
							保存
						</span>
					</a>
				</li>
			</ul>
		</div>
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div id="tableDataId">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th width="5%"></th>
							<th>客户名称</th>
							<th>角色类型</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${checkApproveUnions}" var="union">
							<tr>
								<td>
									<input type="checkbox" checkApproveUnionId="${checkApproveUnion.id}" custName="${union.relatedName}" name="freeStaff" value="${union.relatedId}" onclick="selectSingle('freeStaff')" />
								</td>
								<td>${union.relatedName}</td>
								<td>${fns:getDictLabel(union.roleType, 'ROLE_TYPE', '')}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>