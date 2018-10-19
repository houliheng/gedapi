<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>账务清收审批管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        
		//重置
		function resetForm() {
			$("#contractNo").val("");
			$("#checkResult").val("");
		    $("#s2id_checkResult>.select2-choice>.select2-chosen").html('--全部--');
			$("#hqCheckResult").val("");
		    $("#s2id_hqCheckResult>.select2-choice>.select2-chosen").html('--全部--');
		}
		//清收审核
		function check(id, status) {
			var url = "${ctx}/postloan/accountCleanApprove/form?id=" + id + "&status=" + status;
			var title = "清收审核";
			var width = $(window).width() - 100;
			width = Math.max(width, 1000);
			var height = $(window).height() - 200;
			height = Math.min(height, 600);
			openJBox("accountCleanApproveForm", url, title, width, height);
		}
	</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="accountCleanApprove" action="${ctx}/postloan/accountCleanApprove/" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<input id="status" name="status" type="hidden" value="${status}"/>
					<div class="filter">
						<ul class="ul-form">
						    <li>
								<label>合同编号：</label>
								<form:input path="contractNo" htmlEscape="false" maxlength="50" class="input-medium" />
							</li>
							<c:if test="${status eq 'apply'}">
								<li><label>审核状态：</label> - <form:select path="checkResult" id="checkResult" class="input-medium">
									<form:option value="" label="--全部--" />
									<form:options items="${fns:getDictList('BORROW_NEW_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
								</form:select> 
								</li>
							</c:if>
							<c:if test="${status eq 'hqApply'}">
								<li><label>审核状态：</label> - <form:select path="hqCheckResult" id="hqCheckResult" class="input-medium">
									<form:option value="" label="--全部--" />
									<form:options items="${fns:getDictList('BORROW_NEW_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
								</form:select> 
								</li>
							</c:if>
							</ul>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />&nbsp; 
							<input id="btnReset"class="btn btn-primary" type="button" value="重置" onclick="resetForm()"/>
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<sys:message content="${message}"/>
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div id="tableDataId">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
					<tr>
							<th width="2%">序号</th>
							<th width="5%">合同编号</th>
							<th width="5%">申请人员</th>
							<th width="5%">申请原因</th>
							<th width="5%">申请时间</th>
							<th width="7%">大区审核人员</th>
							<th width="7%">大区审批意见</th>
							<th width="7%">大区审批时间</th>
							<th width="7%">大区审批结果</th>
							<c:if test="${status eq 'hasHqChecked'}">
							<th width="7%">总部审核人员</th>
							<th width="7%">总部审批意见</th>
							<th width="7%">总部审批时间</th>
							</c:if>
							<c:if test="${status eq 'hqCheck' || status eq 'hasHqChecked'}">
							<th width="7%">总部审批结果</th>
							</c:if>
							<c:if test="${status eq 'check' || status eq 'hqCheck'}">
									<th width="3%">操作</th>
							</c:if>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${page.list}" var="accountCleanApprove" varStatus="accountCleanApproveList">
							<tr>
								<td class="title" title="${accountCleanApproveList.count}">${accountCleanApproveList.count }</td>
								<td id="contractNo" class="title" title="${accountCleanApprove.contractNo}">${accountCleanApprove.contractNo}</td>
								<td id="applyBy" class="title" title="${accountCleanApprove.applyBy}">${accountCleanApprove.applyBy}</td>
								<td id="applyAdvice" class="title" title="${accountCleanApprove.applyAdvice}">${accountCleanApprove.applyAdvice}</td>
								<td id="applyDate" class="title" title="${accountCleanApprove.applyDate}">
									<fmt:formatDate value="${accountCleanApprove.applyDate}" pattern="yyyy-MM-dd" />
								</td>
								<td id="checkedBy" class="title" title="${accountCleanApprove.checkedBy}">${accountCleanApprove.checkedBy}</td>
								<td id="checkAdvice" class="title" title="${accountCleanApprove.checkAdvice}">${accountCleanApprove.checkAdvice}</td>
								<td id="checkDate" class="title" title="${accountCleanApprove.checkDate}">
									<fmt:formatDate value="${accountCleanApprove.checkDate}" pattern="yyyy-MM-dd" />
								</td>
								<td id="checkResult" class="title" title="${fns:getDictLabel(accountCleanApprove.checkResult, 'BORROW_NEW_STATUS', '')}">${fns:getDictLabel(accountCleanApprove.checkResult, 'BORROW_NEW_STATUS', '')}</td>
								<c:if test="${status eq 'hasHqChecked'}">
								<td id="hqCheckedBy" class="title" title="${accountCleanApprove.hqCheckedBy}">${accountCleanApprove.hqCheckedBy}</td>
								<td id="hqCheckAdvice" class="title" title="${accountCleanApprove.hqCheckAdvice}">${accountCleanApprove.hqCheckAdvice}</td>
								<td id="hqCheckDate" class="title" title="${accountCleanApprove.hqCheckDate}">
									<fmt:formatDate value="${accountCleanApprove.hqCheckDate}" pattern="yyyy-MM-dd" />
								</td>
								</c:if>
								<c:if test="${status eq 'hqCheck' || status eq 'hasHqChecked'}">
								<td id="hqCheckResult" class="title" title="${fns:getDictLabel(accountCleanApprove.hqCheckResult, 'BORROW_NEW_STATUS', '')}">${fns:getDictLabel(accountCleanApprove.hqCheckResult, 'BORROW_NEW_STATUS', '')}</td>
								</c:if>
								<c:if test="${status eq 'check' || status eq 'hqCheck' }">
										<td>
											<a href="#" onclick="check('${accountCleanApprove.id}','${status}');">审核</a>
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