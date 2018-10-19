<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>其他抵质押物信息管理</title>
<script type="text/javascript">
	$(document).ready(function() {
	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
	function mainBorrowOther() {
		$("#tableDataOtherId").toggle(600);
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="tableList">
			<h3 class="infoListTitle" onclick="mainBorrowOther();" >其他抵质押物数据列表</h3>
			<div id="tableDataOtherId" style="max-height: 300px; overflow: auto;">
				<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
					<thead>
						<tr>
							<th width="5%">序号</th>
							<th width="20%">资产名称</th>
							<th width="10%">数量</th>
							<th width="20%">价值(元)</th>
							<th width="15%">处置金额</th>
							<th width="10%">处置状态</th>
							<th width="20%">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${mortgageOtherInfoList}" var="mortgageOtherInfo" varStatus="i">
							<tr>
								<td id="num" class="title" title="序号">${i.index+1}</td>
								<td id="assetsName" class="title" title="${mortgageOtherInfo.assetsName}">
									${mortgageOtherInfo.assetsName}
									</a>
								</td>
								<td id="countNum" class="title" title="${mortgageOtherInfo.countNum}">${mortgageOtherInfo.countNum}</td>
								<td id="valueNum" class="title" title="${mortgageOtherInfo.valueNum}">${mortgageOtherInfo.valueNum}</td>
								<td id="dealAmount" class="title" title="${mortgageOtherInfo.dealAmount}">${mortgageOtherInfo.dealAmount}</td>
								<td id="dealStatus" class="title" title="${fns:getDictLabel(mortgageOtherInfo.dealStatus, 'DEAL_STATUS', '')}">${fns:getDictLabel(mortgageOtherInfo.dealStatus, 'DEAL_STATUS', '')}</td>
								<td>
									<a href="#" onclick="details('${ctx}/postloan/mortgageOtherInfo/form?readOnly=true&id=${mortgageOtherInfo.id}','其他抵质押物信息');">详情</a>
									<c:if test="${mortgageOtherInfo.dealStatus != '1' && readOnly ne true }">
										<a href="#" onclick="dealTask('${ctx}/postloan/mortgage/toDeal?mortgageFlag=other&mortgageId=${mortgageOtherInfo.id}&dealApplyNo=${mortgageOtherInfo.applyNo}','处置');">处置</a>
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