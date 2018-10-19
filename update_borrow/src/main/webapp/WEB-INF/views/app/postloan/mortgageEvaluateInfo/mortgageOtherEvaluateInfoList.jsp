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
</script>
</head>
<body>
	<!-- //其他抵质押物 -->
	<h3 class="infoListTitle">其他抵质押物数据列表</h3>
	<div class="ribbon">
		<ul class="layout">
			<li class="edit">
				<a id="add" href="javascript:void(0)" onclick="addMortgage('${applyNo}','${ctx}/postloan/mortgageOtherInfo/form','新增其他抵质押物');">
					<span>
						<b></b>
						追加
					</span>
				</a>
				
			</li>
		</ul>
	</div>
	<div id="tableDataId" style="max-height: 300px; overflow: auto;">
		<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
			<thead>
				<tr>
					
					<th  width="6%">序号</th>
					<th  width="20%">资产名称</th>
					<th  width="20%">数量</th>
					<th  width="20%">价值(元)</th>
					<th>是否追加的数据</th> 
					<th  width="20%">操作</th>
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
						 <td id="isPushData" class="title" title="${fns:getDictLabel(mortgageOtherInfo.isPushData, 'yes_no', '')}">${fns:getDictLabel(mortgageOtherInfo.isPushData, 'yes_no', '')}</td> 
						<td>
						<a id="query" href="javascript:void(0)" onclick="query('${mortgageOtherInfo.applyNo}','${mortgageOtherInfo.id}','${ctx}/postloan/mortgageOtherInfo/evaluate','其他抵质押物信息');">
					<span>
						<b></b>
						检查
					</span>
						</a>
						<c:if test="${1==mortgageOtherInfo.isPushData}">
						<a id="editEvaluate" href="javascript:void(0)" onclick="editEvaluate('${mortgageOtherInfo.applyNo}','${mortgageOtherInfo.id}','${ctx}/postloan/mortgageOtherInfo/form?carId=','其他抵质押物信息');">
					<span>
						<b></b>
						修改
					</span>
						</a>
						</c:if>
						<c:if test="${1==mortgageOtherInfo.isPushData}">
						<a id="delete" href="javascript:void(0)" onclick="deleteInfo('mortgageOtherEvaluate','${mortgageOtherInfo.applyNo}','${mortgageOtherInfo.id}','${ctx}/postloan/mortgageOtherInfo/delete','${ctx}/postloan/mortgageOtherInfo/toOtherEvaluateIndex');">
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
</body>
</html>