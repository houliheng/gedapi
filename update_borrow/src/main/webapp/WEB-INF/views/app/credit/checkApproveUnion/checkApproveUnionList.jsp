<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>批复信息授信管理</title>
<script type="text/javascript">
	$(document).ready(function() {
		if ($("#taskDefKey").val() == "utask_dqsxqr" || $("#taskDefKey").val() == "utask_zgssxqr" || $("#status").val() == "1") {
			$("a[name='updateRelationName']").hide();
		}
	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}

	function detailUnion(custId, approId) {
		var newUrl = "${ctx}/credit/workflow/unionDetailIndex";
		openJBox("detailUnionBoxId1", newUrl, "详情", $(window).width() - 50, 500, {
		procDefId : $("#procDefId").val(),
		taskDefKey : $("#taskDefKey").val(),
		applyNo : $("#applyNo").val(),
		execId : $("#execId").val(),
		status : $("#status").val(),
		taskId : $("#taskId").val(),
		custId : custId,
		procInstId : $("#procInstId").val(),
		headUrl : $("#headUrl").val(),
		approId : approId
		});
	}

	function updateRelation(applyNo, checkApproveId, custId) {
        console.log("进入客户授信=====》参数：applyNo="+applyNo+",checkApproveId="+checkApproveId+",custId="+custId);
		openJBox("zsdfgh", "${ctx}/credit/checkApproveUnion/updateRelation", "更新授信人员", $(window).width() - 500, $(window).height() - 500, {
		applyNo : applyNo,
		id : checkApproveId
		});

	}

	function updateRelationInfo(applyNo, checkApproveId, custId) {
	    console.log("进入关联人授信");
		if (custId != null && custId != "") {
            console.log("发送关联人授信请求");
			openJBox("zsdfgha", "${ctx}/credit/checkApproveUnion/updateRelationCustInfo", "更新授信人员", $(window).width() - 500, $(window).height() - 500, {
			applyNo : applyNo,
			id : checkApproveId,
			custId : custId
			});
		} else {
			alertx("请先进行客户授信");
		}
	}
</script>
</head>
<body>
	<div class="tableList">
		<h3 class="tableTitle">数据列表</h3>
		<div id="tableDataId">
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>客户名称</th>
						<th>关联人名称</th>
						<th>产品类型</th>
						<th>期数</th>
						<th>批复金额</th>
						<shiro:hasPermission name="credit:checkApproveUnion:edit">
							<th>操作</th>
						</shiro:hasPermission>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="checkApproveUnion">
						<tr>
							<td>${checkApproveUnion.custName}</td>
							<td>${checkApproveUnion.relatedName}</td>
							<td>${fns:getDictLabel(checkApproveUnion.approProductTypeCode, 'PRODUCT_TYPE', '')}</td>
							<td>${checkApproveUnion.approPeriodValue}</td>
							<td>${checkApproveUnion.contractAmount}</td>
							<shiro:hasPermission name="credit:checkApproveUnion:edit">
								<td>
									<a href="#" onclick="detailUnion('${checkApproveUnion.custId}','${checkApproveUnion.id}')">详情</a>
									<c:if test="${checkApproveUnion.roleType != 5 && checkApproveUnion.roleType  != 1}">
										<a name="updateRelationName" href="#" onclick="updateRelation('${checkApproveUnion.applyNo}','${checkApproveUnion.id}','${checkApproveUnion.custId}');">客户授信</a>
									</c:if>
									<a name="updateRelationName" href="#" onclick="updateRelationInfo('${checkApproveUnion.applyNo}','${checkApproveUnion.id}','${checkApproveUnion.custId}');">关联人授信</a>
								</td>
							</shiro:hasPermission>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	</div>
</body>
</html>