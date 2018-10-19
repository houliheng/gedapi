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
</script>
</head>
<body>
	<div class="wrapper">
		<sys:message content="${message}" />
		<!-- //车辆抵质押物 -->
		<h3 class="infoListTitle">车辆抵质押物数据列表</h3>
		<div class="ribbon">
			<ul class="layout">
				<li class="edit">
					<a id="edit" href="javascript:void(0)" onclick="edit('type','${ctx}/credit/mortgageCarEvaluateInfo/evaluate?carId=','车辆抵质押物信息');">
						<span>
							<b></b>
							编辑
						</span>
					</a>
				</li>
			</ul>
		</div>
		<div id="tableDataId" style="max-height: 300px; overflow: auto;">
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th></th>
						<th width="20px">序号</th>
						<th>产权属性</th>
						<th>产权人姓名</th>
						<th>车牌照号</th>
						<th>抵押质押</th>
						<th>车辆品牌</th>
						<th>购置日期</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${mortgageCarInfoList}" var="mortgageCarInfo" varStatus="i">
						<tr>
							<td width="20px">
								<input type="checkbox" value="${mortgageCarInfo.id}" onclick="selectSingle('type')" name="type">
							</td>
							<td id="num" class="title" title="序号">${i.index+1}</td>
							<td id="propertyRight" class="title" title="${mortgageCarInfo.propertyRight}">
								${fns:getDictLabel(mortgageCarInfo.propertyRight, 'PROPERTY_RIGHT', '')}
								</a>
							</td>
							<td id="propertyName" class="title" title="${mortgageCarInfo.propertyName}">${mortgageCarInfo.propertyName}</td>
							<td id="vehicleNo" class="title" title="${mortgageCarInfo.vehicleNo}">${mortgageCarInfo.vehicleNo}</td>
							<td id="mortgageType" class="title" title="${mortgageCarInfo.mortgageType}">${fns:getDictLabel(mortgageCarInfo.mortgageType, 'MORTGAGE_TYPE', '')}</td>
							<td id="vehicleBrand" class="title" title="${mortgageCarInfo.vehicleBrand}">${mortgageCarInfo.vehicleBrand}</td>
							<td id="buyDate" class="title" title="${mortgageCarInfo.buyDate}">
								<fmt:formatDate value="${mortgageCarInfo.buyDate}" pattern="yyyy-MM-dd" />
							</td>
							<td><a id="query" href="javascript:void(0)" onclick="query('${mortgageCarInfo.id}','${ctx}/credit/mortgageCarEvaluateInfo/evaluate?carId=','车辆抵质押物信息');">
						<span>
							<b></b>
							详情
						</span>
					</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>
