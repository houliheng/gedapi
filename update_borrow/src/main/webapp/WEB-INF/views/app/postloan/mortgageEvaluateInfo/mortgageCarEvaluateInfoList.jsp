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
					<a id="addCar" href="javascript:void(0)" onclick="addMortgage('${applyNo}','${ctx}/postloan/mortgageCarEvaluateInfo/form','新增车辆抵质押物');">
						<span>
							<b></b>
							追加
						</span>
					</a>
				</li>
			</ul>
		</div>
		<div id="tableDataId" style="max-height: 300px; overflow: auto;">
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						
						<th width="20px">序号</th>
						<th>产权属性</th>
						<th>产权人姓名</th>
						<th>车牌照号</th>
						<th>抵押质押</th>
						<th>车辆品牌</th>
						<th>购置日期</th>
					 	<th>是否追加的数据</th> 
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${mortgageCarInfoList}" var="mortgageCarInfo" varStatus="i">
						<tr>
							
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
					 	 <td id="isPushData" class="title" title="${fns:getDictLabel(mortgageCarInfo.isPushData, 'yes_no', '')}">${fns:getDictLabel(mortgageCarInfo.isPushData, 'yes_no', '')}</td> 
							<td><a id="query" href="javascript:void(0)" onclick="query('${applyNo}','${mortgageCarInfo.id}','${ctx}/postloan/mortgageCarEvaluateInfo/evaluate','车辆抵质押物信息');">
						<span>
							<b></b>
							检查
						</span>
					</a>
					<c:if test="${1==mortgageCarInfo.isPushData}">
						<a id="editEvaluate" href="javascript:void(0)" onclick="editEvaluate('${mortgageCarInfo.applyNo}','${mortgageCarInfo.id}','${ctx}/postloan/mortgageCarEvaluateInfo/editEvaluate?carId=','车辆抵质押物信息');">
					<span>
						<b></b>
						修改
					</span>
						</a>
						</c:if>
						<c:if test="${1==mortgageCarInfo.isPushData}">
						<a id="delete" href="javascript:void(0)" onclick="deleteInfo('mortgageCarEvaluate','${mortgageCarInfo.applyNo}','${mortgageCarInfo.id}','${ctx}/postloan/mortgageCarEvaluateInfo/delete','${ctx}/postloan/mortgageCarEvaluateInfo/toCarEvaluateIndex');">
					<span>
						<b></b>
						删除
					</span>
						</a>
						</c:if>
					</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>
