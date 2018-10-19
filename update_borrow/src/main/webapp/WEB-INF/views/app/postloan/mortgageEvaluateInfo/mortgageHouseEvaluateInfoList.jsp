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
</script>
</head>
<body>
	<!-- //房产抵质押物 -->
	<div class="wrapper">
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 class="tableTitle">房产抵质押物数据列表</h3>
			<div class="ribbon">
				<ul class="layout">
					<li class="edit">
						<a id="add" href="javascript:void(0)" onclick="addMortgage('${applyNo}','${ctx}/postloan/mortgageHouseInfo/form','新增房产抵质押物');">
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
							<th>产权人名称</th>
							<th>建筑面积</th>
							<th>房龄</th>
							<th>是否追加的数据</th> 
							<th>操作</th>
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
									 <td id="isPushData" class="title" title="${fns:getDictLabel(mortgageHouseInfo.isPushData, 'yes_no', '')}">${fns:getDictLabel(mortgageHouseInfo.isPushData, 'yes_no', '')}</td> 	
								<td><a id="query" href="javascript:void(0)" onclick="query('${mortgageHouseInfo.applyNo}','${mortgageHouseInfo.id}','${ctx}/postloan/mortgageHouseInfo/evaluate','房屋抵质押物信息');">
							<span>
								<b></b>
								检查
							</span>
						</a>
					<c:if test="${1==mortgageHouseInfo.isPushData}">
						<a id="editEvaluate" href="javascript:void(0)" onclick="editEvaluate('${mortgageHouseInfo.applyNo}','${mortgageHouseInfo.id}','${ctx}/postloan/mortgageHouseInfo/editEvaluate?carId=','房屋抵质押物信息');">
					<span>
						<b></b>
						修改
					</span>
						</a>
						</c:if>
						<c:if test="${1==mortgageHouseInfo.isPushData}">
						<a id="delete" href="javascript:void(0)" onclick="deleteInfo('mortgageHouseEvaluate','${mortgageHouseInfo.applyNo}','${mortgageHouseInfo.id}','${ctx}/postloan/mortgageHouseInfo/delete','${ctx}/postloan/mortgageHouseInfo/toHouseEvaluateIndex');">
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
	</div>
</body>
</html>
