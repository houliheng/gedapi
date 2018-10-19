<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>账务流水管理</title>
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

	//重置按钮
	function del() {
		$("#companyId").val('');
		$("#companyName").val('');
		$("#streamStartTime").val('');
		$("#streamEndTime").val('');
		$(".select2-chosen").html("请选择");
		$(".class1").attr("selected", 'selected');
		$("input.input-medium").val("");
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="accountingStream" action="${ctx}/accounting/accountingStream" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<div class="filter">
						<table class="filter">
							<tr>
								<td class="ft_label">客户名称：</td>
								<td class="ft_content">
									<form:input path="custName" htmlEscape="false" maxlength="20" class="input-medium" />
								</td>
								<td class="ft_label">合同编号：</td>
								<td class="ft_content">
									<form:input path="contractNo" htmlEscape="false" maxlength="50" class="input-medium" />
								</td>
								<td class="ft_label">流水号：</td>
								<td class="ft_content">
									<form:input path="streamNo" htmlEscape="false" maxlength="50" class="input-medium" />
								</td>
							</tr>
							<tr>
								<td class="ft_label">流水类型：</td>
								<td class="ft_content">
									<form:select path="streamType" class="input-medium">
										<form:option value="" class="class1" label="请选择" />
										<form:options items="${fns:getDictList('STREAM_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
								</td>
								<td class="ft_label">所属机构：</td>
								<td class="ft_content">
									<sys:usertreeselect id="company" name="company.id" value="${accountingStream.company.id}" labelName="company.name" labelValue="${accountingStream.company.name}" title="公司" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true" />
								</td>
								<td class="ft_label">流水日期：</td>
								<td class="ft_content">
									<nobr>
										<input id="streamStartTime" name="streamStartTime" type="text" readonly="readonly" maxlength="20" class="input-small Wdate" value="<fmt:formatDate value="${accountingStream.streamStartTime}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
										至
										<input id="streamEndTime" name="streamEndTime" type="text" readonly="readonly" maxlength="20" class="input-small Wdate" value="<fmt:formatDate value="${accountingStream.streamEndTime}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
									</nobr>
								</td>
							</tr>
							<tr>
								<td class="ft_content" colspan="6" style="text-align:right">
									<shiro:hasPermission name="accounting:deductApply:view">
										<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />&nbsp;</shiro:hasPermission>
									<input id="btnReset" class="btn btn-primary" type="button" onclick="del()" value="重置" />
								</td>
							</tr>
						</table>
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
							<th width="8%">客户名称</th>
							<th width="12%">合同编号</th>
							<th width="8%">大区</th>
							<th width="8%">区域</th>
							<th width="10%">分公司</th>
							<th width="14%">流水号</th>
							<th width="10%">流水金额</th>
							<th width="7%">流水类型</th>
							<th width="7%">操作人</th>
							<th width="13%">流水日期</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="accountingStream">
							<tr>
								<td class="title" title="${accountingStream.custName}">${accountingStream.custName}</td>
								<td class="title" title="${accountingStream.contractNo}">${accountingStream.contractNo}</td>
								<td class="title" title="${accountingStream.orgLevel2.name}">${accountingStream.orgLevel2.name}</td>
								<td class="title" title="${accountingStream.orgLevel3.name}">${accountingStream.orgLevel3.name}</td>
								<td class="title" title="${accountingStream.orgLevel4.name}">${accountingStream.orgLevel4.name}</td>
								<td class="title" title="${accountingStream.streamNo}">${accountingStream.streamNo}</td>
								<td class="title" title="${accountingStream.deductAmountResult}">${accountingStream.deductAmountResult}</td>
								<td class="title" title="${fns:getDictLabel(accountingStream.streamType, 'STREAM_TYPE', '')}">${fns:getDictLabel(accountingStream.streamType, 'STREAM_TYPE', '')}</td>
								<td class="title" title="${accountingStream.deductCustName}">${accountingStream.deductCustName}</td>
								<td class="title" title="<fmt:formatDate value='${accountingStream.streamTime}' pattern='yyyy-MM-dd HH:mm:ss' />">
									<fmt:formatDate value="${accountingStream.streamTime}" pattern="yyyy-MM-dd" />
								</td>
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