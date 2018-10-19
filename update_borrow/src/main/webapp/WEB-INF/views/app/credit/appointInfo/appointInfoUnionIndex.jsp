<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>合同预约管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
	});
	//面签
	function sign(approId,custId){
		var url = "${ctx}/credit/appointInfo/loadUnionForm?approId="+approId+"&custId="+custId;
		var urlsuffix = "&applyNo=${actTaskParam.applyNo}&headUrl=${actTaskParam.headUrl}&taskId=${actTaskParam.taskId}&taskDefKey=${actTaskParam.taskDefKey}&procDefId=${actTaskParam.procDefId}&status=${actTaskParam.status}&procInstId=${actTaskParam.procInstId}&oper=apply";
		url = url+urlsuffix;
		var width = $(window).width() - 50;
		var height = $(window).height()-50;
		//alert(width+"and"+height);
		openJBox("signUnion-form", url, "详情", width, height);
	}
</script>
</head>
<body>
	<!-- 批复信息 -->
	<div class="tableList">
		<h3 onclick="lhsxClick()" class="tableTitle">面签信息列表</h3>
		<div  id="tableDataId" >
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>客户姓名</th>
						<th>产品类型</th>
						<th>期数</th>
						<th>批复金额</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${checkApproveUnionList}" var="checkApproveUnion" varStatus="unionStatus">
						<tr>
							<td id="num" class="title" >${checkApproveUnion.custName}</td>
							<td id="num" class="title" >${checkApproveUnion.approProductTypeName}</td>
							<td id="num" class="title" >${checkApproveUnion.approPeriodValue}</td>
							<td id="num" class="title" ><fmt:formatNumber value="${checkApproveUnion.contractAmount}" pattern="###,##0.00"/></td>
							<td><a href="javascript:void(0);" onclick="sign('${checkApproveUnion.id}','${checkApproveUnion.custId}');">详情</a></td>
							<%-- <td id="deductDate" class="title" title="${repayPlan.deductDate}">
								<fmt:formatDate value="${repayPlan.deductDate}" pattern="yyyy-MM-dd"/>
							</td>--%>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<!-- 预约意见 -->
	<div id="appointConclusionForm">
		<%@ include file="/WEB-INF/views/app/credit/processSuggestionInfo/appointConclusionForm.jsp"%>
	</div>
</body>
</html>