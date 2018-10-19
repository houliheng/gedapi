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
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 onclick="mainBorrow0()" class="tableTitle">车辆抵质押物数据列表</h3>
			<div id="mainBorrowCarId">
				<div  class="ribbon">
					<ul class="layout">
						<li class="add">
							<a href="#" onclick="add('${ctx}/credit/mortgageCarInfo/form','新增车辆抵质押物信息');">
								<span>
									<b></b>
									新增
								</span>
							</a>
						</li>
						<li class="edit">
							<a id="edit" href="javascript:void(0)" onclick="edit('type','${ctx}/credit/mortgageCarInfo/form','编辑车辆抵质押物信息');">
								<span>
									<b></b>
									编辑
								</span>
							</a>
						</li>
						<li class="delete">
							<a id="delete" href="#" onclick="del('type','${ctx}/credit/mortgageCarInfo/batchDelete','car','${ctx }/credit/mortgageCarInfo/list')">
								<span>
									<b></b>
									删除
								</span>
							</a>
						</li>
					</ul>
				</div>
				<div id="tableDataId" style="max-height: 300px; overflow: auto;">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th width="20px;">
									<input type="checkbox" onclick="allCheck('all','type');" name="all" id="all">
								</th>
								<th width="20px">序号</th>
								<th>产权属性</th>
								<th>产权人姓名</th>
								<th>抵押质押</th>
								<th>车辆品牌</th>
								<th>购置日期</th>
								<shiro:hasPermission name="credit:mortgageInfo:edit">
									<th>操作</th>
								</shiro:hasPermission>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${mortgageCarInfoList}" var="mortgageCarInfo" varStatus="i">
								<tr>
									<td width="20px">
										<input type="checkbox" value="${mortgageCarInfo.id}" name="type">
									</td>
									<td id="num" class="title" title="序号">${i.index+1}</td>
									<td id="propertyRight" class="title" title="${mortgageCarInfo.propertyRight}">${fns:getDictLabel(mortgageCarInfo.propertyRight, 'PROPERTY_RIGHT', '')}</td>
									<td id="propertyName" class="title" title="${mortgageCarInfo.propertyName}">${mortgageCarInfo.propertyName}</td>
									<td id="mortgageType" class="title" title="${mortgageCarInfo.mortgageType}">${fns:getDictLabel(mortgageCarInfo.mortgageType, 'MORTGAGE_TYPE', '')}</td>
									<td id="vehicleBrand" class="title" title="${mortgageCarInfo.vehicleBrand}">${mortgageCarInfo.vehicleBrand}</td>
									<td id="buyDate" class="title" title="${mortgageCarInfo.buyDate}">
										<fmt:formatDate value="${mortgageCarInfo.buyDate}" pattern="yyyy-MM-dd" />
									</td>
									<shiro:hasPermission name="credit:mortgageInfo:edit">
										<td>
											<a href="#" onclick="details('${ctx}/credit/mortgageCarInfo/form?readOnly=true&id=${mortgageCarInfo.id}','查看车辆抵质押物信息');">详情</a>
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
