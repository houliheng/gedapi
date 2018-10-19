<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>房产抵质押物管理</title>
<!-- 	<meta name="decorator" content="default"/> -->
<script type="text/javascript">
	$(document).ready(function() {
		if ('${gqgetFlag}' == "1") {
			$("#houseUlId").hide();
		}
	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
	function mainBorrow1() {
		$("#mainBorrowHouseId").toggle(600);
	}
</script>
</head>
<body>
	<div class="wrapper">
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 onclick="mainBorrow1()" class="tableTitle">房产抵质押物数据列表</h3>
			<div id="mainBorrowHouseId">
				
				<div id="tableDataId" style="max-height:300px;overflow:auto;">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th width="20px;">
									<input type="checkbox" onclick="allCheck('house','typeHouse');" name="house" id="house">
								</th>
								<th width="20px">序号</th>
								<th>地址</th>
								<th>面积</th>
								<th>估值</th>
								<th>市值</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${mortgageHouseInfoList}" var="mortgageHouseInfo" varStatus="i">
								<tr>
									<td width="20px">
										<input type="checkbox" value="${mortgageHouseInfo.id}" name="typeHouse">
									</td>
									<td id="num" class="title" title="序号">${i.index+1}</td>
									<td id="contDetails" class="title" title="${mortgageHouseInfo.contDetails}">${mortgageHouseInfo.contDetails}</td>
									<td id="buildingArea" class="title" title="${mortgageHouseInfo.buildingArea}">${mortgageHouseInfo.buildingArea}</td>
									<td id="evaluatePrice" class="title" title="${mortgageHouseInfo.evaluatePrice}">${mortgageHouseInfo.evaluatePrice}</td>
									<td id="marketPrice" class="title" title="${mortgageHouseInfo.marketPrice}">${mortgageHouseInfo.marketPrice}</td>
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
