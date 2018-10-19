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

	function addWarehouse() {
		openJBox('addWarehouseBox', "${ctx}/credit/gqgetComInfo/warehouseForm", "新增库存货物信息", 1000, 350, {
			applyNo : $("#applyNo").val()
		});

	}

	function editWarehouse() {
		var id = getCheckedIds('warehouseCheck');
		if (id.length != 1) {
			alertx("请选择一条数据");
		} else {
			openJBox('editWarehouseBox', "${ctx}/credit/gqgetComInfo/warehouseForm", "编辑库存货物信息", 1000, 350, {
			id : id,
			applyNo : $("#applyNo").val()
			});
		}
	}
	function deleteWarehouse() {
		var ids = getCheckedIds('warehouseCheck');
		if (0 == ids.length) {
			alertx("请选择需要删除的数据！");
		} else {
			confirmx("是否删除该货物?", function() {
				$.post("${ctx}/credit/gqgetComInfo/warehouseDelete?id=" + ids, null, function(data) {
					if (data.status == '1') {
						alertx(data.message, function() {
							$.loadDiv('warehouse', '${ctx}/credit/gqgetComInfo/warehouseList', {
								applyNo : $("#applyNo").val()
							}, 'post');
						});
					} else {
						alertx(data.message);
					}
				});
			});
		}
	}
	function guaranC() {
		$("#warehouseTable").toggle(600);
	}
</script>
</head>
<body>
	<div class="wrapper">
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 onclick="guaranC()" class="tableTitle">库存货物信息</h3>
			<div id="warehouseTable" class="searchCon">
				<div class="ribbon">
					<ul class="layout">
						<li class="add">
							<a href="#" onclick="addWarehouse()">
								<span>
									<b></b>
									新增
								</span>
							</a>
						</li>
						<li class="edit">
							<a href="#" onclick="editWarehouse();">
								<span>
									<b></b>
									编辑
								</span>
							</a>
						</li>
						<li class="delete">
							<a href="#" onclick="deleteWarehouse();">
								<span>
									<b></b>
									删除
								</span>
							</a>
						</li>
					</ul>
				</div>
				<div id="tableDataId" style="max-height:300px;overflow:auto;">
					<table id="fromTable filter" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th width="4%">
									<input type="checkbox" name="house" id="house">
								</th>
								<th width="3%">序号</th>
								<th>类型</th>
								<th>描述</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${warehouseGoodsList}" var="warehouseGoods" varStatus="i">
								<tr>
									<td>
										<input type="checkbox" value="${warehouseGoods.id}" name="warehouseCheck" onclick="selectSingle('warehouseCheck')">
									</td>
									<td id="num" class="title" title="序号">${i.index+1}</td>
									<td id="wareType" class="title" title="${fns:getDictLabel(warehouseGoods.wareType, 'WARE_TYPE', '')}">${fns:getDictLabel(warehouseGoods.wareType, 'WARE_TYPE', '')}</td>
									<td id="description" class="title" title="${warehouseGoods.description}">${warehouseGoods.description}</td>
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
