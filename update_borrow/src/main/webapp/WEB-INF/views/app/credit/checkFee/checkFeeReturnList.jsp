<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>外访费返还</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		resetTip();
		$("#checkFeeVOForm").validate({
			submitHandler : function(form) {
				loading();
				form.submit();
			}
		});
	});
	//新增外访费返还信息
	function addCheckFee() {
		var url = "${ctx}/credit/checkFee/checkFeeReturn";
		openJBox("checkFeeFormBox", url, "新增外访费返还信息", 800, 400, {
			applyNo : '${applyNo }'
		});
	}

	//编辑外访费返还信息
	function editCheckFee(id, applyNo) {
		var url = "${ctx}/credit/workflow/checkFeeReturnForm?checkFeeId=" + id + "&&readOnly=0" + "&applyNo=" + applyNo;
		openJBox('box', url, "外访费返还信息详情", 900, 500, null);
	}

	//重置
	function resetField() {
		$("#custName").val("");
		$("#contractNo").val("");
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="checkFeeVOForm" modelAttribute="checkFeeVO" action="${ctx }/credit/checkFee/checkFeeReturnList" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<div class="filter">
						<ul class="ul-form">
							<li>
								<label>客户名称：</label>
								<form:input path="custName" htmlEscape="false" maxlength="200" class="input-medium" />
							</li>
							<li>
								<label>合同编号：</label>
								<form:input path="contractNo" htmlEscape="false" maxlength="50" class="input-medium" />
							</li>
						</ul>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />
							&nbsp;
							<input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="resetField();" />
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div id="tableDataId">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th width="5%">序号</th>
							<th width="10%">客户名称</th>
							<th width="10%">申请编号</th>
							<th width="10%">合同编号</th>
							<th width="10%">合同金额(元)</th>
							<th width="10%">缴纳外访费金额(元)</th>
							<th width="10%">已退还金额(元)</th>
							<th width="19%">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="checkFeeVO" varStatus="i">
							<tr>
								<td id="num" class="title" title="序号">${i.index+1}</td>
								<td id="custName" class="title" title="${checkFeeVO.custName}">${checkFeeVO.custName}</td>
								<td id="applyNo" class="title" title="${checkFeeVO.applyNo}">${checkFeeVO.applyNo}</td>
								<td id="contractNo" class="title" title="${checkFeeVO.contractNo}">${checkFeeVO.contractNo}</td>
								<td id="contractAmount" class="title" title="${checkFeeVO.contractAmount}">
									<fmt:formatNumber value="${checkFeeVO.contractAmount }" pattern="###,##0.00"></fmt:formatNumber>
								</td>
								<!-- 需从外访费登记表中读取数据 -->
								<td id="checkFee" class="title" title="${checkFeeVO.checkFee}">
									<fmt:formatNumber value="${checkFeeVO.checkFee }" pattern="###,##0.00"></fmt:formatNumber>
								</td>
								<td id="returnCheckFee" class="title" title="${checkFeeVO.returnCheckFee}">
									<fmt:formatNumber value="${checkFeeVO.returnCheckFee }" pattern="###,##0.00"></fmt:formatNumber>
								</td>
								<c:if test="${checkFeeVO.returnCheckFee != checkFeeVO.checkFee }">
									<td>
										<a href="#" onclick="editCheckFee('${checkFeeVO.id}','${checkFeeVO.applyNo}');">返还</a>
									</td>
								</c:if>
								<c:if test="${checkFeeVO.returnCheckFee == checkFeeVO.checkFee }">
									<td>
										<a href="#">返还</a>
									</td>
								</c:if>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="pagination">${page}</div>
		</div>
	</div>
</body>
</html>