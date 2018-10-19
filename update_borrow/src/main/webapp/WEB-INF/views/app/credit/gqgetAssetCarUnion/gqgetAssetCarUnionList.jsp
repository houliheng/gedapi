<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>冠e通资产车辆信息管理</title>
<script type="text/javascript">
	$(document).ready(function() {

	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
	function addAssetCar() {
		openJBox('addAssetCarBox', "${ctx}/credit/gqgetAssetCarUnion/form", "新增车辆资产信息", $(window).width() - 400, 250, {
		gqgetApplyNo : '${gqgetAssetCarUnion.gqgetApplyNo}',
		approveId : '${gqgetAssetCarUnion.approveId}'
		});

	}
	function editAssetCar() {
		var id = getCheckedIds('carCheck');
		if (id.length < 1) {
			alertx("请选择一条数据");
		} else {
			openJBox('editAssetCarBox', "${ctx}/credit/gqgetAssetCarUnion/form", "编辑车辆资产信息", $(window).width() - 400, 250, {
				id : id[0]
			});
		}
	}
	function deleteAssetCar() {
		var ids = getCheckedIds('carCheck');
		if (1 > ids.length) {
			alertx("请选择需要删除的数据！");
		} else {
			confirmx("是否删除该资产?", function() {
				$.post("${ctx}/credit/gqgetAssetCarUnion/delete", {
					id : ids[0]
				}, function(data) {
					if (data.status == '1') {
						alertx(data.message, function() {
							$.loadDiv("assetCar", "${ctx }/credit/gqgetAssetCarUnion/list", {
							gqgetApplyNo : '${gqgetAssetCarUnion.gqgetApplyNo}',
							approveId : '${gqgetAssetCarUnion.approveId}'
							}, "post");
						});
					} else {
						alertx(data.message);
					}
				});
			});
		}
	}
	function gqgetAssetCarUnionDiv() {
		$("#gqgetAssetCarUnionId").toggle(600);
	}
</script>
</head>
<body>
	<div class="tableList">
		<sys:message content="${message}" />
		<div class="tableDataId" style="max-height: 400px; overflow: auto;">
			<div class="searchInfo">
				<h3 class="tableTitle" onclick="gqgetAssetCarUnionDiv();">车产信息</h3>
				<div id="gqgetAssetCarUnionId" class="ribbon filter">
					<ul class="layout">
						<li class="add">
							<a id="add" href="javascript:void(0);" onclick="addAssetCar();">
								<span>
									<b></b>
									新增
								</span>
							</a>
						</li>
						<li class="edit">
							<a id="edit" href="javascript:void(0);" onclick="editAssetCar();">
								<span>
									<b></b>
									编辑
								</span>
							</a>
						</li>
						<li class="delete">
							<a id="delete" href="javascript:void(0);" onclick="deleteAssetCar();">
								<span>
									<b></b>
									删除
								</span>
							</a>
						</li>
					</ul>
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th width="5%">
									<input type="checkbox">
								</th>
								<th width="20%">车辆型号</th>
								<th width="20%">使用年限</th>
								<th width="20%">估值</th>
								<th width="20%">市值</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${page.list}" var="gqgetAssetCarUnion">
								<tr>
									<td>
										<input type="checkbox" value="${gqgetAssetCarUnion.id}" name="carCheck" onclick="selectSingle('carCheck');">
									</td>
									<td id="gqgetVehicleNo" class="title" title="${gqgetAssetCarUnion.gqgetVehicleNo}">${gqgetAssetCarUnion.gqgetVehicleNo}</td>
									<td id="gqgetUsedYears" class="title" title="${gqgetAssetCarUnion.gqgetUsedYears}">${gqgetAssetCarUnion.gqgetUsedYears}</td>
									<td id="gqgetCarEvaluatePrice" class="title" title="${gqgetAssetCarUnion.gqgetCarEvaluatePrice}">${gqgetAssetCarUnion.gqgetCarEvaluatePrice}</td>
									<td id="gqgetMarketPrice" class="title" title="${gqgetAssetCarUnion.gqgetMarketPrice}">${gqgetAssetCarUnion.gqgetMarketPrice}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>