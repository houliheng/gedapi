<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>车辆抵质押物信息管理</title>
<script type="text/javascript">
	$(document).ready(function() {
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
		<div class="tableList">
			<h3 onclick="mainBorrow0()" class="tableTitle">车辆抵质押物数据列表</h3>
			<div id="mainBorrowCarId">
				<div id="tableDataId" style="max-height: 300px; overflow: auto;">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th width="5%">序号</th>
								<th width="10%">产权属性</th>
								<th width="15%">产权人姓名</th>
								<th width="10%">抵押质押</th>
								<th width="10%">车辆品牌</th>
								<th width="10%">购置日期</th>
								<th width="10%">处置金额</th>
								<th width="10%">处置状态</th>
								<th width="20%">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${mortgageCarInfoList}" var="mortgageCarInfo" varStatus="i">
								<tr>
									<td id="num" class="title" title="序号">${i.index+1}</td>
									<td id="propertyRight" class="title" title="${fns:getDictLabel(mortgageCarInfo.propertyRight, 'PROPERTY_RIGHT', '')}">${fns:getDictLabel(mortgageCarInfo.propertyRight, 'PROPERTY_RIGHT', '')}</td>
									<td id="propertyName" class="title" title="${mortgageCarInfo.propertyName}">${mortgageCarInfo.propertyName}</td>
									<td id="mortgageType" class="title" title="${fns:getDictLabel(mortgageCarInfo.propertyRight, 'PROPERTY_RIGHT', '')}">${fns:getDictLabel(mortgageCarInfo.mortgageType, 'MORTGAGE_TYPE', '')}</td>
									<td id="vehicleBrand" class="title" title="${mortgageCarInfo.vehicleBrand}">${mortgageCarInfo.vehicleBrand}</td>
									<td id="buyDate" class="title" title="${mortgageCarInfo.buyDate}">
										<fmt:formatDate value="${mortgageCarInfo.buyDate}" pattern="yyyy-MM-dd" />
									</td>
									<td id="dealAmount" class="title" title="${mortgageCarInfo.dealAmount}">${mortgageCarInfo.dealAmount}</td>
									<td id="dealStatus" class="title" title="${fns:getDictLabel(mortgageCarInfo.dealStatus, 'DEAL_STATUS', '')}">${fns:getDictLabel(mortgageCarInfo.dealStatus, 'DEAL_STATUS', '')}</td>
									<td>
										<a href="#" onclick="details('${ctx}/postloan/mortgageCarEvaluateInfo/detail?readOnly=true&id=${mortgageCarInfo.id}','查看车辆抵质押物信息');">详情</a>
										<c:if test="${mortgageCarInfo.dealStatus != '1' && readOnly ne true }" >
											<a href="#" onclick="dealTask('${ctx}/postloan/mortgage/toDeal?mortgageFlag=car&mortgageId=${mortgageCarInfo.id}&dealApplyNo=${mortgageCarInfo.applyNo}','处置');">处置</a>
										</c:if>
									</td>
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
