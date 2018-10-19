<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>违约金罚息减免管理</title>
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
		$("#startTime").val('');
		$("#endTime").val('');
		$("#penaltyStartTime").val('');
		$("#penaltyEndTime").val('');
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
				<form:form id="searchForm" modelAttribute="applyPenaltyFineExemptVO" action="${ctx}/accounting/applyPenaltyFineExempt/penaltySearch?queryFlag=button" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<div class=" filter">
						<table class="fromTable">
							<tr>
								<td class="ft_label">客户名称：</td>
								<td class="ft_content">
									<form:input path="custName" htmlEscape="false" maxlength="20" class="input-medium" />
								</td>
								<td class="ft_label">合同编号：</td>
								<td class="ft_content">
									<form:input path="contractNo" htmlEscape="false" maxlength="50" class="input-medium" />
								</td>
								<td class="ft_label">还款日期：</td>
								<td class="ft_content">
									<nobr>
										<input id="startTime" name="startTime" type="text" readonly="readonly" maxlength="20" class="input-small Wdate" style="width:150px" value="<fmt:formatDate value="${applyPenaltyFineExemptVO.startTime}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
										至
										<input id="endTime" name="endTime" type="text" readonly="readonly" maxlength="20" class="input-small Wdate" style="width:150px" value="<fmt:formatDate value="${applyPenaltyFineExemptVO.endTime}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
									</nobr>
								</td>
							</tr>
							<tr>
								<td class="ft_label">当前状态：</td>
								<td class="ft_content">
									<form:select path="repayPeriodStatus" class="input-medium">
										<form:option value="" class="class1" label="请选择" />
										<form:options items="${fns:getDictList('PERIOD_STATE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
								</td>
								<td class="ft_label">所属机构：</td>
								<td class="ft_content">
									<sys:usertreeselect id="company" name="company.id" value="${applyPenaltyFineExemptVO.company.id}" labelName="company.name" labelValue="${applyPenaltyFineExemptVO.company.name}" title="公司" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true" />
								</td>
								<td class="ft_label">减免日期：</td>
								<td class="ft_content">
									<nobr>
										<input id="penaltyStartTime" name="penaltyStartTime" type="text" readonly="readonly" maxlength="20" class="input-small Wdate" style="width:150px" value="<fmt:formatDate value="${applyPenaltyFineExemptVO.penaltyStartTime}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
										至
										<input id="penaltyEndTime" name="penaltyEndTime" type="text" readonly="readonly" maxlength="20" class="input-small Wdate" style="width:150px" value="<fmt:formatDate value="${applyPenaltyFineExemptVO.penaltyEndTime}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
									</nobr>
								</td>
							</tr>
							<tr>
								<td class="ft_content" colspan="6" style="text-align:right">
									<shiro:hasPermission name="accounting:applyPenaltyFineExempt:view">
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
			<div id="tableDataId" style="width:100%; overflow-x:auto;overflow-y:hidden">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th width="5%">客户名称</th>
							<th width="6%">合同编号</th>
							<th width="6%">合同金额</th>
							<th width="6%">大区</th>
							<th width="6%">区域</th>
							<th width="6%">分公司</th>
							<th width="6%">合同期限</th>
							<th width="6%">当期期数</th>
							<th width="6%">还款日期</th>
							<th width="6%">当前状态</th>
							<th width="6%">逾期天数</th>
							<th width="7%">当期应还违约金</th>
							<th width="6%">当期应还罚息</th>
							<th width="6%">实收违约金</th>
							<th width="5%">实收罚息</th>
							<th width="6%">减免违约金</th>
							<th width="5%">减免罚息</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="applyPenaltyFineExemptVO">
							<tr>
								<td class="title" title="${applyPenaltyFineExemptVO.custName}">${applyPenaltyFineExemptVO.custName}</td>
								<td class="title" title="${applyPenaltyFineExemptVO.contractNo}">${applyPenaltyFineExemptVO.contractNo}</td>
								<td class="title" title="${applyPenaltyFineExemptVO.contractAmount}">${applyPenaltyFineExemptVO.contractAmount}</td>
								<td class="title" title="${applyPenaltyFineExemptVO.orgLevel2.name}">${applyPenaltyFineExemptVO.orgLevel2.name}</td>
								<td class="title" title="${applyPenaltyFineExemptVO.orgLevel3.name}">${applyPenaltyFineExemptVO.orgLevel3.name}</td>
								<td class="title" title="${applyPenaltyFineExemptVO.orgLevel4.name}">${applyPenaltyFineExemptVO.orgLevel4.name}</td>
								<td class="title" title="${applyPenaltyFineExemptVO.approPeriodValue}">${applyPenaltyFineExemptVO.approPeriodValue}</td>
								<td class="title" title="${applyPenaltyFineExemptVO.periodNum}">${applyPenaltyFineExemptVO.periodNum}</td>
								<td class="title" title="${applyPenaltyFineExemptVO.deductDate}" style="word-break:break-all;word-wrap:break-word;">${applyPenaltyFineExemptVO.deductDate}</td>
								<td class="title" title="${applyPenaltyFineExemptVO.repayPeriodStatus}">${fns:getDictLabel(applyPenaltyFineExemptVO.repayPeriodStatus, 'PERIOD_STATE', '')}</td>
								<td class="title" title="${applyPenaltyFineExemptVO.overdueDays}">${applyPenaltyFineExemptVO.overdueDays}</td>
								<td class="title" title="${applyPenaltyFineExemptVO.penaltyAmount}">${applyPenaltyFineExemptVO.penaltyAmount}</td>
								<td class="title" title="${applyPenaltyFineExemptVO.fineAmount}">${applyPenaltyFineExemptVO.fineAmount }</td>
								<td class="title" title="${applyPenaltyFineExemptVO.factPenaltyAmount}">${applyPenaltyFineExemptVO.factPenaltyAmount}</td>
								<td class="title" title="${applyPenaltyFineExemptVO.factFineAmount}">${applyPenaltyFineExemptVO.factFineAmount}</td>
								<td class="title" title="${applyPenaltyFineExemptVO.penaltyExemptAmount}">${applyPenaltyFineExemptVO.penaltyExemptAmount}</td>
								<td class="title" title="${applyPenaltyFineExemptVO.fineExemptAmount}">${applyPenaltyFineExemptVO.fineExemptAmount}</td>
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