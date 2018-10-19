<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>房产抵质押物管理</title>
<!-- 	<meta name="decorator" content="default"/> -->
<script type="text/javascript">
	$(document).ready(function() {
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
				<div class="ribbon">
					<ul class="layout">
						<li class="add">
							<a href="#" onclick="add('${ctx}/credit/mortgageHouseInfo/form','新增房产抵质押物信息');">
								<span>
									<b></b>
									新增
								</span>
							</a>
						</li>
						<li class="edit">
							<a id="edit" href="javascript:void(0)" onclick="edit('typeHouse','${ctx}/credit/mortgageHouseInfo/form','编辑房产抵质押物信息');">
								<span>
									<b></b>
									编辑
								</span>
							</a>
						</li>
						<li class="delete">
							<a id="delete" href="#" onclick="del('typeHouse','${ctx}/credit/mortgageHouseInfo/batchDelete','house','${ctx }/credit/mortgageHouseInfo')">
								<span>
									<b></b>
									删除
								</span>
							</a>
						</li>
					</ul>
				</div>
				<div id="tableDataId" style="max-height:300px;overflow:auto;">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th width="20px;">
									<input type="checkbox" onclick="allCheck('house','typeHouse');" name="house" id="house">
								</th>
								<th width="20px">序号</th>
								<th>产权属性</th>
								<th>产权人名称</th>
								<th>建筑面积</th>
								<shiro:hasPermission name="credit:mortgageInfo:edit">
									<th>操作</th>
								</shiro:hasPermission>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${mortgageHouseInfoList}" var="mortgageHouseInfo" varStatus="i">
								<tr>
									<td width="20px">
										<input type="checkbox" value="${mortgageHouseInfo.id}" name="typeHouse">
									</td>
									<td id="num" class="title" title="序号">${i.index+1}</td>
									<td id="propertyRight" class="title" title="${mortgageHouseInfo.propertyRight}">
										${fns:getDictLabel(mortgageHouseInfo.propertyRight, 'PROPERTY_RIGHT', '')}
										</a>
									</td>
									<td id="propertyName" class="title" title="${mortgageHouseInfo.propertyName}">${mortgageHouseInfo.propertyName}</td>
									<td id="buildingArea" class="title" title="${mortgageHouseInfo.buildingArea}">${mortgageHouseInfo.buildingArea}</td>
									<shiro:hasPermission name="credit:mortgageInfo:edit">
										<td>
											<a href="#" onclick="details('${ctx}/credit/mortgageHouseInfo/form?readOnly=true&id=${mortgageHouseInfo.id}','查看房产抵质押物信息');">详情</a>
										</td>
									</shiro:hasPermission>
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
