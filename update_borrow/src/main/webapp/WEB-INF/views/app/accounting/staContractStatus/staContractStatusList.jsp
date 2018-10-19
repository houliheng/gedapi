<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>合同还款明细查询管理</title>
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
		$(".select2-chosen").html("请选择");
		$(".class1").attr("selected", 'selected');
		$("input.input-medium").val("");
	}

	function deleteContractLock(contractNo) {
		loading();
		$.ajax({
		url : "${ctx}/accounting/staContractStatus/deleteContractLock",
		type : "post",
		data : {
			contractNo : contractNo
		},
		dataType : "json",
		success : function(data) {
			closeTip();
			alertx(data.message, function() {
				closeJBox();
			});
		},
		error : function(msg) {
			closeTip();
			alertx("未能完成操作，请查看后台信息");
		}
		});
	}
	function queryContractDetail(contractNo) {
		loading();
		$("#contractNoTmp").val(contractNo);
 		var newUrl= "${ctx}/accounting/staContractStatus/form?backUrl=${ctx}/accounting/staContractStatus/list";
		 $("#searchForm").attr('action',newUrl);
	     $("#searchForm").submit();
	}
	
/* 	function saveDeductResultBatch(contractNo) {
		var flag = "";
		top.$.jBox.warning("是否属于中间人代扣补录的流水？", '系统提示', function(v, h, f) {
			if (v != 'cancel') {
				if (v == 'yes') {
					var flag = "1";
				}
				var url = "${ctx}/accounting/deductResult/formBatch";
				openJBox("saveDeductResultBox", url, "批量补录流水", 800, 350,{contractNo:contractNo,flag:flag});
			}
		})
	} */
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="staContractStatus" action="${ctx}/accounting/staContractStatus?queryFlag=button" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<input type="hidden" id="contractNoTmp" name="contractNoTmp" />
					<div class="filter">
						<table class="fromTable">
							<tr>
								<td>
									<label>客户名称：</label>
									<form:input path="custName" htmlEscape="false" maxlength="20" class="input-medium" />
								</td>
								<td>
									<label>合同编号：</label>
									<form:input path="contractNo" htmlEscape="false" maxlength="50" class="input-medium" />
								</td>
								<td class="ft_label">所属机构：</td>
								<td class="ft_content">
									<sys:usertreeselect id="company" name="company.id" value="${staContractStatus.company.id}" labelName="company.name" labelValue="${staContractStatus.company.name}" title="公司" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true" />
								</td>
							</tr>
							<tr>
								<td>
									<label>还款状态：</label>
									<form:select path="repayContractStatus" class="input-medium">
										<form:option value="" class="class1" label="请选择" />
										<form:options items="${fns:getDictList('CONTRACT_STATE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
								</td>
								<td>
									<label>放款日期：</label>
									<input name="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:150px" value="<fmt:formatDate value="${staContractStatus.startTime}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
									至
									<input name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" style="width:150px" value="<fmt:formatDate value="${staContractStatus.endTime}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
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
			<h3 class="tableTitle">数据列表</h3>
			<div id="tableDataId">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th width="6%">客户名称</th>
							<th width="8%">授信企业名称</th>
							<th width="10%">合同编号</th>
							<th width="7%">放款日期</th>
							<th width="8%">合同金额</th>
							<th width="5%">期限</th>
							<th width="8%">还款状态</th>
							<th width="8%">累计逾期期数</th>
							<th width="8%">当前逾期金额</th>
							<th width="8%">大区</th>
							<th width="6%">区域</th>
							<th width="10%">分公司</th>
							<shiro:hasPermission name="accounting:staContractStatus:edit">
								<th width="8%">操作</th>
							</shiro:hasPermission>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="staContractStatus">
							<tr>
								<td class="title" title="${staContractStatus.custName}">${staContractStatus.custName}</td>
								<td class="title" title="${staContractStatus.companyName}">${staContractStatus.companyName}</td>
								<td class="title" title="${staContractStatus.contractNo}">${staContractStatus.contractNo}</td>
								<td class="title" title="<fmt:formatDate value='${staContractStatus.loanDate}' pattern='yyyy-MM-dd' />">
									<fmt:formatDate value="${staContractStatus.loanDate}" pattern="yyyy-MM-dd" />
								</td>
								<td class="title" title="${staContractStatus.contractAmount}">${staContractStatus.contractAmount}</td>
								<td class="title" title="${staContractStatus.periodValue}">${staContractStatus.periodValue}</td>
								<td class="title" title="${fns:getDictLabel(staContractStatus.repayContractStatus, 'CONTRACT_STATE', '')}">${fns:getDictLabel(staContractStatus.repayContractStatus, 'CONTRACT_STATE', '')}</td>
								<td class="title" title="${staContractStatus.taTimes}">${staContractStatus.taTimes}</td>
								<td class="title" title="${staContractStatus.currOverdueAmount}">${staContractStatus.currOverdueAmount}</td>
								<td class="title" title="${staContractStatus.orgLevel2.name}">${staContractStatus.orgLevel2.name}</td>
								<td class="title" title="${staContractStatus.orgLevel3.name}">${staContractStatus.orgLevel3.name}</td>
								<td class="title" title="${staContractStatus.orgLevel4.name}">${staContractStatus.orgLevel4.name}</td>
								<shiro:hasPermission name="accounting:staContractStatus:edit">
									<td>
										<a href="#" onclick="queryContractDetail('${staContractStatus.contractNo}')">详情</a>
										<shiro:hasRole name="admin">
										&nbsp;
											<a href="#" onclick="deleteContractLock('${staContractStatus.contractNo}');">解锁</a>
										</shiro:hasRole>
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