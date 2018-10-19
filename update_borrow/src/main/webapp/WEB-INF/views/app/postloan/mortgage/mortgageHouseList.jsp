<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>房产抵质押物信息管理</title>
<script type="text/javascript">
	$(document).ready(function() {
	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
	function mainBorrowHouse() {
		$("#tableDataHouseId").toggle(600);
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="tableList">
			<h3 class="tableTitle" onclick="mainBorrowHouse()" >房产抵质押物数据列表</h3>
			<div id="tableDataHouseId" style="max-height: 300px; overflow: auto;">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th width="5%">序号</th>
							<th width="10%">产权属性</th>
							<th width="15%">产权人名称</th>
							<th width="15%">建筑面积</th>
							<th width="15%">房龄</th>
							<th width="10%">处置金额</th>
							<th width="10%">处置状态</th>
							<th width="20%">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${mortgageHouseInfoList}" var="mortgageHouseInfo" varStatus="i">
							<tr>
								<td id="num" class="title" title="序号">${i.index+1}</td>
								<td id="propertyRight" class="title" title="${mortgageHouseInfo.propertyRight}">${fns:getDictLabel(mortgageHouseInfo.propertyRight, 'PROPERTY_RIGHT', '')}</td>
								<td id="propertyName" class="title" title="${mortgageHouseInfo.propertyName}">${mortgageHouseInfo.propertyName}</td>
								<td id="buildingArea" class="title" title="${mortgageHouseInfo.buildingArea}">${mortgageHouseInfo.buildingArea}</td>
								<td id="houseYears" class="title" title="${mortgageHouseInfo.houseYears}">${mortgageHouseInfo.houseYears}</td>
								<td id="dealAmount" class="title" title="${mortgageHouseInfo.dealAmount}">${mortgageHouseInfo.dealAmount}</td>
								<td id="dealStatus" class="title" title="${fns:getDictLabel(mortgageHouseInfo.dealStatus, 'DEAL_STATUS', '')}">${fns:getDictLabel(mortgageHouseInfo.dealStatus, 'DEAL_STATUS', '')}</td>
								<td>
									<a href="#" onclick="details('${ctx}/postloan/mortgageHouseInfo/form?readOnly=true&id=${mortgageHouseInfo.id}','查看产品抵质押物信息');">详情</a>
									<c:if test="${mortgageHouseInfo.dealStatus != '1' && readOnly ne true}">
										<a href="#" onclick="dealTask('${ctx}/postloan/mortgage/toDeal?mortgageFlag=house&mortgageId=${mortgageHouseInfo.id}&dealApplyNo=${mortgageHouseInfo.applyNo}','处置');">处置</a>
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
