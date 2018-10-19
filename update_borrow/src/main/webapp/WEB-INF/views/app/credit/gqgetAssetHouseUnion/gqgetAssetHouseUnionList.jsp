<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript">
	$(document).ready(function() {

	});

	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}

	function guaranD() {
		$("#assertHouseUnionTable").toggle(600);
	}

	function addAssertHouse() {
		openJBox('addAssertHouseBox', "${ctx}/credit/gqgetAssetHouseUnion/form", "新增房产信息", 700, 350, {
		applyNo : $("#applyNo").val(),
		approveId : '${gqgetAssetHouseUnion.approveId}'
		});
	}

	function editAssertHouse() {
		var id = getCheckedIds('assertHouseUnionCheck');
		if (id.length != 1) {
			alertx("请选择一条数据");
		} else {
			openJBox('editAssertHouserBox', "${ctx}/credit/gqgetAssetHouseUnion/form", "编辑抵押设备信息", 700, 350, {
			id : id,
			applyNo : $("#applyNo").val(),
			approveId : '${gqgetAssetHouseUnion.approveId}'
			});
		}
	}

	function deleteAssertHouse() {
		var ids = getCheckedIds('assertHouseUnionCheck');
		if (0 == ids.length) {
			alertx("请选择需要删除的数据！");
		} else {
			confirmx("是否删除该房产?", function() {
				$.post("${ctx}/credit/gqgetAssetHouseUnion/delete?id=" + ids, null, function(data) {
					if (data.status == '1') {
						alertx(data.message, function() {
							$.loadDiv('assetHouse', '${ctx}/credit/gqgetAssetHouseUnion/list', {
							applyNo : '${gqgetAssetHouseUnion.applyNo}',
							approveId : '${gqgetAssetHouseUnion.approveId}'
							}, 'post');
						});
					} else {
						alertx(data.message);
					}
				});
			});
		}
	}
</script>
</head>
<body>
	<div class="wrapper">
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 onclick="guaranD()" class="tableTitle">房屋资产</h3>
			<div id="asserthouseUnionTable" class="searchCon">
				<div class="ribbon">
					<ul class="layout">
						<li class="add">
							<a href="#" onclick="addAssertHouse();">
								<span>
									<b></b>
									新增
								</span>
							</a>
						</li>
						<li class="edit">
							<a href="#" onclick="editAssertHouse();">
								<span>
									<b></b>
									编辑
								</span>
							</a>
						</li>
						<li class="delete">
							<a href="#" onclick="deleteAssertHouse();">
								<span>
									<b></b>
									删除
								</span>
							</a>
						</li>
					</ul>
				</div>
				<div id="tableDataId">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th width="20px;">
									<input type="checkbox">
								</th>
								<th width="20px">序号</th>
								<th>地址</th>
								<th>面积</th>
								<th>估值</th>
								<th>市值</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${page.list}" var="gqgetAssetHouseUnion">
								<tr>
									<td width="20px">
										<input type="checkbox" value="${gqgetAssetHouseUnion.id}" onclick="selectSingle('assertHouseUnionCheck');" name="assertHouseUnionCheck">
									</td>
									<td id="num" class="title" title="序号">${i.index+1}</td>
									<td id="contDetails" class="title" title="型号">${gqgetAssetHouseUnion.contDetails}</td>
									<td id="buildingArea" class="title" title="购买价格">${gqgetAssetHouseUnion.buildingArea}</td>
									<td id="evaluatePrice" class="title" title="估值">${gqgetAssetHouseUnion.evaluatePrice}</td>
									<td id="marketPrice" class="title" title="市值">${gqgetAssetHouseUnion.marketPrice}</td>
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