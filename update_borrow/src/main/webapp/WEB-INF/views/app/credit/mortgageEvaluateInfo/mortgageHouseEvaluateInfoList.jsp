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
						<a id="edit" href="javascript:void(0)" onclick="edit('typeHouse','${ctx}/credit/mortgageHouseInfo/evaluate?id=','编辑房产抵质押物信息');">
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
							<th width="20px"></th>
							<th width="20px">序号</th>
							<th>产权属性</th>
							<th>产权人名称</th>
							<th>建筑面积</th>
							<th>房龄</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${mortgageHouseInfoList}" var="mortgageHouseInfo" varStatus="i">
							<tr>
								<td width="20px">
									<input type="checkbox" value="${mortgageHouseInfo.id}" onclick="selectSingle('typeHouse')" name="typeHouse">
								</td>
								<td id="num" class="title" title="序号">${i.index+1}</td>
								<td id="propertyRight" class="title" title="${mortgageHouseInfo.propertyRight}">${fns:getDictLabel(mortgageHouseInfo.propertyRight, 'PROPERTY_RIGHT', '')}</td>
								<td id="propertyName" class="title" title="${mortgageHouseInfo.propertyName}">${mortgageHouseInfo.propertyName}</td>
								<td id="buildingArea" class="title" title="${mortgageHouseInfo.buildingArea}">${mortgageHouseInfo.buildingArea}</td>
								<td id="houseYears" class="title" title="${mortgageHouseInfo.houseYears}">${mortgageHouseInfo.houseYears}</td>
								<td><a id="query" href="javascript:void(0)" onclick="query('${mortgageHouseInfo.id}','${ctx}/credit/mortgageHouseInfo/evaluate?id=','房产抵质押物信息');">
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
	</div>
</body>
</html>
