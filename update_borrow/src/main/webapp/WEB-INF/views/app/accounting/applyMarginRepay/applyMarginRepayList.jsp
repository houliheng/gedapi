<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>保证金退还申请管理</title>
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
		$(".select2-chosen").html("请选择");
		$(".class1").attr("selected", 'selected');
		$("input.input-medium").val("");
	}

	function applyMargin(contractNo) {
		$.ajax({
		url : "${ctx}/accounting/applyMarginRepay/marginRepay",
		data : {
			contractNo : contractNo
		},
		async : false,
		dataType : "JSON",
		success : function(data) {
			if (data.status == 1) {
				var width = $(top.document).width() - 300;
				width = Math.max(width, 1000);
				var height = $(top.document).height() - 200;
				var url = "${ctx}/accounting/applyMarginRepay/form?contractNo=" + contractNo;
				openJBox("applyMarginRepayBox", url, "保证金退还申请", width, height);
			} else {
				alertx(data.message);
			}
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
				<form:form id="searchForm" modelAttribute="applyMarginRepayVO" action="${ctx}/accounting/applyMarginRepay/list?queryFlag=button" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<div class="filter">
						<table class="fromTable">
							<tr>
								<td class="ft_label">客户名称：</td>
								<td class="ft_content">
									<form:input path="custName" htmlEscape="false" class="input-medium" />
								</td>
								<td class="ft_label">合同编号：</td>
								<td class="ft_content">
									<form:input path="contractNo" htmlEscape="false" class="input-medium" />
								</td>
								<td class="ft_label">所属机构：</td>
								<td class="ft_content">
									<sys:usertreeselect id="company" name="company.id" value="${applyMarginRepayVO.company.id}" labelName="company.name" labelValue="${applyMarginRepayVO.company.name}" title="公司" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true" />
								</td>
							</tr>
							<tr>
								<td class="ft_label">退还状态：</td>
								<td class="ft_content">
									<form:select path="marginAmountStatus" class="input-medium ">
										<form:option value="" class="class1" label="请选择" />
										<form:options items="${fns:getDictList('MARGIN_AMOUNT_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
								</td>
								<td class="ft_label">退款日期：</td>
								<td class="ft_content">
									<nobr>
										<input id="startTime" name="startTime" type="text" readonly="readonly" maxlength="20" class="input-small Wdate" value="<fmt:formatDate value="${applyMarginRepayVO.startTime}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
										至
										<input id="endTime" name="endTime" type="text" readonly="readonly" maxlength="20" class="input-small Wdate" value="<fmt:formatDate value="${applyMarginRepayVO.endTime}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
									</nobr>
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />
							&nbsp;
							<input id="btnReset" class="btn btn-primary" type="button" onclick="del()" value="重置" />
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 class="tableTitle">列表信息</h3>
			<div id="tableDataId" style="width:100%; overflow-x:auto;overflow-y:hidden">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th width="8%">客户名称</th>
							<th width="10%">合同编号</th>
							<th width="8%">合同金额</th>
							<th width="8%">大区</th>
							<th width="8%">区域</th>
							<th width="8%">分公司</th>
							<th width="8%">结清日期</th>
							<th width="9%">缴纳保证金金额</th>
							<th width="9%">保证金退还状态</th>
							<th width="8%">已退还金额</th>
							<th width="8%">退款日期</th>
							<shiro:hasPermission name="accounting:applyMarginRepay:edit">
								<th width="8%">操作</th>
							</shiro:hasPermission>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="applyMarginRepay">
							<tr>
								<td class="title" title="${applyMarginRepay.custName}">${applyMarginRepay.custName}</td>
								<td class="title" title="${applyMarginRepay.contractNo}">${applyMarginRepay.contractNo}</td>
								<td class="title" title="${applyMarginRepay.contractAmount}">${applyMarginRepay.contractAmount}</td>
								<td class="title" title="${applyMarginRepay.orgLevel2.name}">${applyMarginRepay.orgLevel2.name}</td>
								<td class="title" title="${applyMarginRepay.orgLevel3.name}">${applyMarginRepay.orgLevel3.name}</td>
								<td class="title" title="${applyMarginRepay.orgLevel4.name}">${applyMarginRepay.orgLevel4.name}</td>
								<td class="title" title="${applyMarginRepay.settleDate}">${applyMarginRepay.settleDate}</td>
								<td class="title" title="${applyMarginRepay.marginAmount}">${applyMarginRepay.marginAmount}</td>
								<td class="title" title="${fns:getDictLabel(applyMarginRepay.marginAmountStatus, 'MARGIN_AMOUNT_STATUS', '')}">${fns:getDictLabel(applyMarginRepay.marginAmountStatus, 'MARGIN_AMOUNT_STATUS', '')}</td>
								<td class="title" title="${applyMarginRepay.approMarginAmount}">${applyMarginRepay.approMarginAmount}</td>
								<td class="title" title="<fmt:formatDate value='${applyMarginRepay.marginAmountTime}' pattern='yyyy-MM-dd' />">
									<fmt:formatDate value='${applyMarginRepay.marginAmountTime}' pattern='yyyy-MM-dd' />
								</td>
								<shiro:hasPermission name="accounting:applyMarginRepay:edit">
									<td>
										<a href="javaScript:void(0)" onclick="applyMargin('${applyMarginRepay.contractNo}')">退还申请</a>
									</td>
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