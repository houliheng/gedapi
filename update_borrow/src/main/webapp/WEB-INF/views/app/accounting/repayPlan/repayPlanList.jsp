<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>应还款查询管理</title>
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
	function validateIsLock(contractNo, periodNum) {
		$.ajax({
		url : "${ctx}/accounting/deductApply/validateIsLock",
		type:"post",
		data : {
		contractNo:contractNo,
		periodNum : periodNum
		},
		async : false,
		dataType : "json",
		success : function(data) {
			if (data.status == 1) {
				var width = $(top.document).width() - 300;
				width = Math.max(width, 1000);
				var height = $(top.document).height() - 200;
				var url = "${ctx}/accounting/deductApply/form";
				openJBox("deductApplyBox", url, "划扣", width, height,{
					contractNo:contractNo,
					periodNum:periodNum
				});
			} else {
				alertx(data.message);
			}
		}
		});
	}

	//重置按钮
	function del() {
		$("#companyId").val('');
		$("#companyName").val('');
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
			<div class="filter">
				<form:form id="searchForm" modelAttribute="accRepayPlan" action="${ctx}/accounting/repayPlan/list?queryFlag=button" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
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
							<td class="ft_label">所属机构：</td>
							<td class="ft_content">
								<sys:usertreeselect id="company" name="company.id" value="${accRepayPlan.company.id}" labelName="company.name" labelValue="${accRepayPlan.company.name}" title="公司" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true" />
							</td>
						</tr>
						<tr>
							<td class="ft_label">期状态：</td>
							<td class="ft_content">
								<form:select path="repayPeriodStatus" class="input-medium">
									<form:option value="" class="class1" label="请选择" />
									<form:options items="${fns:getDictList('PERIOD_STATE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
								</form:select>
							</td>
							<td class="ft_label">还款日期：</td>
							<td class="ft_content" colspan="2">
								<nobr>
									<input name="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:150px" value="<fmt:formatDate value="${repayPlan.startTime}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
									至
									<input name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:150px" value="<fmt:formatDate value="${repayPlan.endTime}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
								</nobr>
							</td>
						</tr>
						<tr>
							<td class="ft_content" colspan="6" style="text-align:right">
								<shiro:hasPermission name="accounting:repayPlan:view">
									<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />&nbsp;</shiro:hasPermission>
								<input id="btnReset" class="btn btn-primary" type="button" onclick="del()" value="重置" />
							</td>
						</tr>
					</table>
				</form:form>
			</div>
		</div>
		<sys:message content="${message}" />
		<div class="tableList" style="overflow: auto;">
			<h3 class="tableTitle">数据列表&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			 <c:if test="${not empty accRepayPlan}">
				 应还本息总额(元)&nbsp;:  ${accRepayPlan.couponAmount}  &nbsp;&nbsp;
				 应还账户管理费总额(元)&nbsp;:  ${accRepayPlan.mangementFeeAmount}
			 </c:if>
			</h3>
			<div id="tableDataId" style="width:100%; overflow-x:auto;overflow-y:hidden" >
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th width="5%">客户名称</th>
							<th width="9%">合同编号</th>
							<th width="6%">大区</th>
							<th width="6%">区域</th>
							<th width="6%">分公司</th>
							<th width="8%">合同金额</th>
							<th width="5%">期限</th>
							<th width="5%">当期期数</th>
							<th width="8%">应还款日期</th>
							<th width="8%">应还款本金</th>
							<th width="8%">应还款利息</th>
							<th width="8%">应还款账户管理费</th>
							<th width="8%">应还款服务费</th>
							<th width="5%">应还款总计</th>
							<%--<shiro:hasPermission name="accounting:deductApply:edit">
								<th width="5%">操作</th>
							</shiro:hasPermission>--%>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="repayPlan">
							<tr>
								<td class="title" title="${repayPlan.custName}">${repayPlan.custName}</td>
								<td class="title" title="${repayPlan.contractNo}">${repayPlan.contractNo}</td>
								<td class="title" title="${repayPlan.orgLevel2.name}">${repayPlan.orgLevel2.name}</td>
								<td class="title" title="${repayPlan.orgLevel3.name}">${repayPlan.orgLevel3.name}</td>
								<td class="title" title="${repayPlan.orgLevel4.name}">${repayPlan.orgLevel4.name}</td>
								<td class="title" title="${repayPlan.contractAmount}">${repayPlan.contractAmount}</td>
								<td class="title" title="${repayPlan.periodValue}">${repayPlan.periodValue}</td>
								<td class="title" title="${repayPlan.periodNum}">${repayPlan.periodNum}</td>
								<td class="title" title="${repayPlan.deductDate}">${repayPlan.deductDate}</td>
								<td class="title" title="${repayPlan.capitalAmount}">${repayPlan.capitalAmount}</td>
								<td class="title" title="${repayPlan.interestAmount}">${repayPlan.interestAmount}</td>
								<td class="title" title="${repayPlan.mangementFee}">${repayPlan.mangementFee}</td>
								<td class="title" title="${repayPlan.serviceFee}">${repayPlan.serviceFee}</td>
								<td class="title" title="${repayPlan.repayAmount}">${repayPlan.repayAmount}</td>
								<%--<shiro:hasPermission name="accounting:deductApply:edit">
									<td>
										<a href="javaScript:void(0)" onclick="validateIsLock('${repayPlan.contractNo}','${repayPlan.periodNum}')">划扣</a>
									</td>
								</shiro:hasPermission>--%>
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