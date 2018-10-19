<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>历史批复信息列表</title>
<meta name="decorator" content="default" />
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
	//查询
	function checkApproveDetails(url,applyNo,taskDefKey) {
		openJBox('details', url+"&applyNo="+applyNo+"&taskDefKey="+taskDefKey, "查看历史批复信息", $(window.document).width() - 30, $(window.document).height() - 100);
	}
</script>
</head>
<body>
	<div class="wrapper">
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 onclick="mainBorrow0()" class="tableTitle">历史批复信息列表</h3>
			<div id="mainBorrowCarId">
				<div id="tableDataId" style="max-height: 300px; overflow: auto;">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th width="20px">序号</th>
								<th>任务名称</th>
								<th>产品类型</th>
								<th>批复额度</th>
								<th>批复期限</th>
								<th>还款方式</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${page}" var="checkApprove" varStatus="i">
								<tr>
									<td id="num" class="title" title="序号">${i.index+1}</td>
									<td id="taskDefKey" class="title" title="${checkApprove.taskDefKey}">${fns:getDictLabel(checkApprove.taskDefKey, 'PROCESS_NAME', '')}</td>
									<td id="approProductTypeName" class="title" title="${checkApprove.approProductTypeName}">${checkApprove.approProductTypeName}</td>
									<td id="loanAmount" class="title" title="${checkApprove.loanAmount}">${checkApprove.loanAmount}</td>
									<td id="approPeriodValue" class="title" title="${checkApprove.approPeriodValue}">${checkApprove.approPeriodValue}</td>
									<td id="approLoanRepayType" class="title" title="${checkApprove.approLoanRepayType}">${checkApprove.approLoanRepayType}</td>
									<td>
										<a href="#" onclick="checkApproveDetails('${ctx}/credit/checkApprove/load?readOnly=viewHistory','${checkApprove.applyNo}','${checkApprove.taskDefKey}');">详情</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div style="float:right">
					<input id="btnCancel" class="btn-primary btn " type="button"
								value="关闭" onclick="closeJBox()" />
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
