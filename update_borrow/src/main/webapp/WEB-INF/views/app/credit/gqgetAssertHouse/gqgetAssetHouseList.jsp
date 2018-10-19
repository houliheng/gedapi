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
		$("#assertHouseTable").toggle(600);
	}

	function addAssertHouse() {
		openJBox('addAssertHouseBox', "${ctx}/credit/gqgetAssetHouse/form", "新增房产信息", 700, 350, {
			applyNo : $("#applyNo").val()
		});
	}

	function editAssertHouse() {
		var id = getCheckedIds('assertHouseCheck');
		if (id.length != 1) {
			alertx("请选择一条数据");
		} else {
			openJBox('editAssertHouserBox', "${ctx}/credit/gqgetAssetHouse/form", "编辑抵押设备信息", 700, 350, {
			id : id,
			applyNo : $("#applyNo").val()
			});
		}
	}

	function deleteAssertHouse() {
		var ids = getCheckedIds('assertHouseCheck');
		if (0 == ids.length) {
			alertx("请选择需要删除的数据！");
		} else {
			confirmx("是否删除该房产?", function() {
				$.post("${ctx}/credit/gqgetAssetHouse/delete?id=" + ids, null, function(data) {
					console.log(data);
					if (data.status == '1') {
						alertx(data.message, function() {
							$.loadDiv('assetHouse', '${ctx}/credit/gqgetAssetHouse', {
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
</script>
</head>
<body>
	<div class="tableList">
		<sys:message content="${message}" />
		<div class="searchInfo">
		<h3 onclick="guaranD()" class="tableTitle">房屋资产</h3>
		<div id="assertHouseTable" class="ribbon filter">
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
								<input type="checkbox" onclick="allCheck('equip','assertHouseCheck');" name="house" id="house">
							</th>
							<th width="20px">序号</th>
							<th>地址</th>
							<th>面积</th>
							<th>估值</th>
							<th>市值</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="gqgetAssetHouse">
							<tr>
								<td width="20px">
									<input type="checkbox" value="${gqgetAssetHouse.id}" name="assertHouseCheck">
								</td>
								<td id="num" class="title" title="序号">${i.index+1}</td>
								<td id="contDetails" class="title" title="型号">${gqgetAssetHouse.contDetails}</td>
								<td id="buildingArea" class="title" title="购买价格">${gqgetAssetHouse.buildingArea}</td>
								<td id="evaluatePrice" class="title" title="估值">${gqgetAssetHouse.evaluatePrice}</td>
								<td id="marketPrice" class="title" title="市值">${gqgetAssetHouse.marketPrice}</td>
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