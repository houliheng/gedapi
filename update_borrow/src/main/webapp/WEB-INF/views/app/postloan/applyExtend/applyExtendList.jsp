<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>合同展期申请管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		if ('${flag}' == "1") {
			$("[name = 'done']").show();
			$("[name = 'todo']").hide();
		} else {
			$("[name = 'done']").hide();
			$("[name = 'todo']").show();
		}

	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}

	//重置按钮
	function resetForm() {
		$("#companyId").val('');
		$("#companyName").val('');
		$("#custName").val("");
		$("#contractNo").val("");
		$("#extendApplyStatus").select2("val", "");
	}

	//申请展期
	function applyExtendForm(contractNo, id) {
		var url = "${ctx}/postloan/applyExtend/index?contractNo=" + contractNo + "&id=" + id;
		var title = "展期申请";
		var width = $(window).width() - 200;
		//width = Math.max(width, 1000);
		var height = $(window).height() - 100;
		openJBox("applyExtendFormJBox", url, title, width, height);
	}
	function validateApplyExtendForm(contractNo, id) {
		loading();
		$.ajax({
		type : "post",
		data : {
			contractNo : contractNo
		},
		url : "${ctx}/postloan/applyExtend/validateApplyExtendForm",
		dataType : "json",
		success : function(data) {
			closeTip();
			if (data.status == "1") {
				applyExtendForm(contractNo, id);
			} else {
				alertx(data.message);
			}
		},
		error : function(msg) {
			alertx("系统出现问题，请查看后台信息");
		}
		});
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="applyExtend" action="${ctx}/postloan/applyExtend/list/${flag}" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<div class="filter">
						<table class="fromTable">
							<tr>
								<td class="ft_label">所属机构：</td>
								<td class="ft_content">
									<sys:usertreeselect id="company" name="company.id" value="${applyExtend.company.id}" labelName="company.name" labelValue="${applyExtend.company.name}" title="公司" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true" />
								</td>
								<td class="ft_label">借款人姓名：</td>
								<td class="ft_content">
									<form:input path="custName" htmlEscape="false" class="input-medium" />
								</td>
							</tr>
							<tr>
								<td class="ft_label">合同编号：</td>
								<td class="ft_content">
									<form:input path="contractNo" htmlEscape="false" class="input-medium" />
								</td>
								<td name="done" class="ft_label">展期状态：</td>
								<td name="done" class="ft_content">
									<form:select path="extendApplyStatus" class="input-medium">
										<form:option value="" label="请选择" />
										<form:options items="${fns:getDictList('EXTEND_APPLY_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />
							&nbsp;
							<input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="resetForm()" />
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div id="tableDataId">
				<table id="contentTable" width="100%" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th width="5%">序号</th>
							<th width="10%">所属分公司</th>
							<th width="10%">借款人姓名</th>
							<th width="15%">合同编号</th>
							<th width="10%">原合同金额(元)</th>
							<th width="10%">原合同起始日期</th>
							<th width="10%">原合同到期日期</th>
							<th width="10%">原合同期数</th>
							<th width="10%">展期期数</th>
							<shiro:hasPermission name="postloan:applyExtend:edit">
								<th name="todo" width="10%">操作</th>
								<th name="done" width="10%">展期状态</th>
							</shiro:hasPermission>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="applyExtend" varStatus="applyExtendList">
							<tr>
								<td class="title" title="序号">${applyExtendList.count}</td>
								<td class="title" title="${applyExtend.operateOrgName}">${applyExtend.operateOrgName }</td>
								<td class="title" title="${applyExtend.custName}">${applyExtend.custName }</td>
								<td class="title" title="${applyExtend.contractNo}">${applyExtend.contractNo }</td>
								<td class="title" title="${applyExtend.contractAmount}">${applyExtend.contractAmount }</td>
								<td class="title" title="<fmt:formatDate value='${applyExtend.conStartDate }' pattern='yyyy-MM-dd' />">
									<fmt:formatDate value='${applyExtend.conStartDate }' pattern='yyyy-MM-dd' />
								</td>
								<td class="title" title="<fmt:formatDate value='${applyExtend.conEndDate }' pattern='yyyy-MM-dd' />">
									<fmt:formatDate value='${applyExtend.conEndDate }' pattern='yyyy-MM-dd' />
								</td>
								<td class="title" title="${applyExtend.approPeriodValue}">${applyExtend.approPeriodValue }</td>
								<td class="title" title="${applyExtend.extendPeriod}">${applyExtend.extendPeriod }</td>
								<shiro:hasPermission name="postloan:applyExtend:edit">
									<td name="todo">
										<a href="#" onclick="validateApplyExtendForm('${applyExtend.contractNo}','${applyExtend.id}');">展期申请</a>
									</td>
									<td name="done" class="title" title="${fns:getDictLabel(applyExtend.extendApplyStatus, 'EXTEND_APPLY_STATUS', '')}">${fns:getDictLabel(applyExtend.extendApplyStatus, 'EXTEND_APPLY_STATUS', '')}</td>
								</shiro:hasPermission>
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