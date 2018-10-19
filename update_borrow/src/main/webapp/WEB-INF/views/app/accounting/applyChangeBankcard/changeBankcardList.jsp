<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>银行卡信息管理</title>
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
		$("#idNum").val("");
		$("input.input-medium").val("");
	}

	function validateData(contractNo) {
		$.ajax({
		url : "${ctx}/accounting/applyChangeBankcardIndex/validate",
		data : {
			contractNo : contractNo
		},
		async : false,
		dataType : "json",
		success : function(data) {
			if (data.status == 1) {
				window.location.href = "${ctx}/accounting/applyChangeBankcardIndex/load?contractNo=" + contractNo;
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
				<form:form id="searchForm" modelAttribute="contract" action="${ctx}/accounting/applyChangeBankcardIndex" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<div class="filter">
						<ul class="ul-form">
							<li>
								<label>证件号：</label>
								<form:input path="idNum" class="input-medium " htmlEscape="false" />
							</li>
							<li>
								<label>所属机构：</label>
								<sys:usertreeselect id="company" name="company.id" value="${contract.company.name}" labelName="company.name" labelValue="${contract.company.name}" title="公司" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true" />
							</li>
						</ul>
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
			<div id="tableDataId"  style="width:100%; overflow-x:auto;overflow-y:hidden" >
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th>客户名称</th>
							<th>证件类型</th>
							<th>证件号</th>
							<th>合同编号</th>
							<th>合同金额</th>
							<th>登记门店</th>
							<shiro:hasPermission name="accounting:contract:edit">
								<th>操作</th>
							</shiro:hasPermission>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="contract">
							<tr>
								<td class="title" title="${contract.custName}">${contract.custName}</td>
								<td class="title" title="${contract.idType}">${fns:getDictLabel(contract.idType, 'CUSTOMER_P_ID_TYPE', '')}</td>
								<td class="title" title="${contract.idNum}">${contract.idNum}</td>
								<td class="title" title="${contract.contractNo}">${contract.contractNo}</td>
								<td class="title" title="${contract.contractAmount}">${contract.contractAmount}</td>
								<td class="title" title="${contract.operateOrgName}">${contract.operateOrgName}</td>
								<td>
									<a href="javaScript:void(0)" onclick="validateData('${contract.contractNo}')">银行卡变更</a>
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