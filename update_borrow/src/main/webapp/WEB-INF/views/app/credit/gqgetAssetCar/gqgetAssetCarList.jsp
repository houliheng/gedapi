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
	function gqgetAssetCarDiv() {
		$("#gqgetAssetCarId").toggle(600);
	}
</script>
</head>
<body>
	<div class="tableList">
		<sys:message content="${message}" />
		<div class="tableDataId" style="max-height: 400px; overflow: auto;">
			<div class="searchInfo">
				<h3 class="tableTitle" onclick="gqgetAssetCarDiv();" >车产信息</h3>
				<div id="gqgetAssetCarId" class="ribbon filter">
				<div class="ribbon">
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
					</div>
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
							<c:forEach items="${page.list}" var="gqgetAssetCar">
								<tr>
									<td>
										<input type="checkbox" value="${gqgetAssetCar.id}" name="carCheck" onclick="selectSingle('carCheck');">
									</td>
									<td id="gqgetVehicleNo" class="title" title="${gqgetAssetCar.gqgetVehicleNo}">${gqgetAssetCar.gqgetVehicleNo}</td>
									<td id="gqgetUsedYears" class="title" title="${gqgetAssetCar.gqgetUsedYears}">${gqgetAssetCar.gqgetUsedYears}</td>
									<td id="gqgetCarEvaluatePrice" class="title" title="${gqgetAssetCar.gqgetCarEvaluatePrice}">${gqgetAssetCar.gqgetCarEvaluatePrice}</td>
									<td id="gqgetMarketPrice" class="title" title="${gqgetAssetCar.gqgetMarketPrice}">${gqgetAssetCar.gqgetMarketPrice}</td>
									
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