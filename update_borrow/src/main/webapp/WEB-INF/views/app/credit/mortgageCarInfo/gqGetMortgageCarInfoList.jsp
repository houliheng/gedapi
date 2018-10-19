<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>车辆抵质押物信息管理</title>
<script type="text/javascript">
	$(document).ready(function() {
		if('${gqgetFlag}' == "1"){
			$("#carUlId").hide();
		}
	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
	function mainBorrow0() {
		$("#mainBorrowCarId").toggle(600);
	}
</script>
</head>
<body>
	<div class="wrapper">
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 onclick="mainBorrow0()" class="tableTitle">车辆抵质押物数据列表</h3>
			<div id="mainBorrowCarId">
				<div id="tableDataId" style="max-height: 300px; overflow: auto;">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th width="20px;">
									<input type="checkbox" onclick="allCheck('all','type');" name="all" id="all">
								</th>
								<th width="20px">序号</th>
								<th>车辆型号</th>
								<th>使用年限</th>
								<th>估值</th>
								<th>市值</th>
								
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${mortCarList}" var="mortgageCarInfo" varStatus="i">
								<tr>
									<td width="20px">
										<input type="checkbox" value="${mortgageCarInfo.id}" name="type">
									</td>
									<td id="num" class="title" title="序号">${i.index+1}</td>
									<td id="vehicleNo" class="title" title="${mortgageCarInfo.vehicleNo}">${mortgageCarInfo.vehicleNo}</td>
									<td id="usedYears" class="title" title="${mortgageCarInfo.usedYears}">${mortgageCarInfo.usedYears}</td>
									<td id="carEvaluatePrice" class="title" title="${mortgageCarInfo.carEvaluatePrice}">${mortgageCarInfo.carEvaluatePrice}</td>
									<td id="marketPrice1" class="title" title="${mortgageCarInfo.marketPrice1}">${mortgageCarInfo.marketPrice1}</td>
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
