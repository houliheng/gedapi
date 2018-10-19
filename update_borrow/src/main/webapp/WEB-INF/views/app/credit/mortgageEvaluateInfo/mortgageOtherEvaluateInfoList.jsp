<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
</head>
<body>
	<!-- //其他抵质押物 -->
	<h3 class="infoListTitle">其他抵质押物数据列表</h3>
	<div class="ribbon">
		<ul class="layout">
			<li class="edit">
				<a id="edit" href="javascript:void(0)" onclick="edit('otherType','${ctx}/credit/mortgageOtherInfo/evaluate?id=','编辑其它抵质押物信息');">
					<span>
						<b></b>
						编辑
					</span>
				</a>
			</li>
		</ul>
	</div>
	<div id="tableDataId" style="max-height: 300px; overflow: auto;">
		<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
			<thead>
				<tr>
					<th  width="3%"></th>
					<th  width="17%">序号</th>
					<th  width="20%">资产名称</th>
					<th  width="20%">数量</th>
					<th  width="20%">价值(元)</th>
					<th  width="20%">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${mortgageOtherInfoList}" var="mortgageOtherInfo" varStatus="i">
					<tr>
						<td width="20px">
							<input type="checkbox" value="${mortgageOtherInfo.id}" onclick="selectSingle('otherType')" name="otherType">
						</td>
						<td id="num" class="title" title="序号">${i.index+1}</td>
						<td id="assetsName" class="title" title="${mortgageOtherInfo.assetsName}">
							${mortgageOtherInfo.assetsName}
							</a>
						</td>
						<td id="countNum" class="title" title="${mortgageOtherInfo.countNum}">${mortgageOtherInfo.countNum}</td>
						<td id="valueNum" class="title" title="${mortgageOtherInfo.valueNum}">${mortgageOtherInfo.valueNum}</td>
						<td><a id="query" href="javascript:void(0)" onclick="query('${mortgageOtherInfo.id}','${ctx}/credit/mortgageOtherInfo/evaluate?id=','查看其它抵质押物信息');">
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
</body>
</html>