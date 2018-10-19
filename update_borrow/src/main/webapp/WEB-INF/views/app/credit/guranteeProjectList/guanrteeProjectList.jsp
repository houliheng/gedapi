<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>担保公司管理</title>
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
		
		function set() {
			$("#guaranteeCompanyName").val('');
			$(".select2-chosen").html("请选择");
			$(".class1").attr("selected", 'selected');
			$("input.input-medium").val("");
			$("#guranteeId").val("");
		}
		
		function detailGuarantee(contractNo) {
			loading();
	 		var newUrl= "${ctx}/credit/guranteeProjectManage/contractDeatil?contractNo="+contractNo;
			 $("#searchForm").attr('action',newUrl);
		     $("#searchForm").submit();
		}
	</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="guranteeProjectList" action="${ctx}/credit/guranteeProjectManage/" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="filter">
					<table class="fromTable">
							<tr>
								<td>
									<label>公司名：</label>
									<form:input path="borrowName" htmlEscape="false" maxlength="32" class="input-medium"/>
								</td>
								<td>
									<label>借款人手机号：</label>
									<form:input path="mobilePhone" htmlEscape="false" maxlength="32" class="input-medium"/>
								</td>
								<td>
								<label>审批状态：</label>
								<form:select path="applyStatus" class="input-medium">
										<form:option value="" class="class1" label="请选择" />
										<form:options items="${fns:getDictList('APPLY_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
								</form:select>
								</td>
							</tr>
							<tr>
								<td>
									<label>放款状态：</label>
									<form:select path="loanStatus" class="input-medium">
										<form:option value="" class="class1" label="请选择" />
										<form:options items="${fns:getDictList('LOAN_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
								</form:select>	
								</td>
								<td>
									<label>还款状态：</label>
									<form:select path="accountStatus" class="input-medium">
										<form:option value="" class="class1" label="请选择" />
										<form:options items="${fns:getDictList('CONTRACT_STATE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
									
								</td>
								<td>
									<label>担保公司：</label>
									<form:select path="guranteeId" class="input-medium">
									<form:option value="" class="class1" label="请选择" /> 
									<c:forEach items="${creGuaranteeCompanies}" var="creGuaranteeCompany" varStatus="page">
										<form:option value="${creGuaranteeCompany.id}" class="class1" label="${creGuaranteeCompany.guaranteeCompanyName }" /> 
									</c:forEach>
									</form:select>
								<td>
							</tr>
							<%-- <tr>
								<td>
									<label>代偿状态：</label>
									<form:select path="compentStatus" class="input-medium">
									<form:option value="" class="class1" label="请选择" />
									<form:options items="${fns:getDictList('PAYMENT_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
								</td>
							</tr> --%>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="查 询" />&nbsp; 
							<input id="btnReset"class="btn btn-primary" type="button" value="重 置" onclick="set()"/>
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<sys:message content="${message}"/>
		<div class="tableList" id="guranteeCompanyList">
			<h3 class="tableTitle">数据列表</h3>
			<div id="tableDataId" style="max-height:400px;overflow:auto;">
			<table id="contentTable" cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered table-condensed table-hover">
						<thead>
							<tr>
								<th width="3%">序号</th>
								<th width="7%">合同号</th>
								<th width="6%">借款人名称</th>
								<th width="9%">借款金额（元）</th>
								<th width="7%">期限（月）</th>
								<th width="6%">利率（年）</th>
								<th width="5%">当前期数</th>
								<th width="7%">借款状态</th>
								<th width="7%">保证金（元）</th>
								<th width="7%">担保服务费</th>
								<th width="8%">代偿金额（元）</th>
								<th width="8%">待还金额（元）</th>
								<th width="7%">担保公司</th>
								<th width="7%">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${page.list}" var="guranteeProject" varStatus="page">
								<tr>
									<td>${page.index+1 }</td>
									<td class="title" title="${guranteeProject.contractNo}">${guranteeProject.contractNo}</td>
									<td class="title" title="${guranteeProject.borrowName}">${guranteeProject.borrowName}</td>
									<td class="title" title="${guranteeProject.borrowMoney}">${guranteeProject.borrowMoney}</td>
									<td class="title" title="${guranteeProject.periodValue}">${guranteeProject.periodValue}</td>
									<td class="title" title="${guranteeProject.yearRate}">${guranteeProject.yearRate}</td>
									<td class="title" title="${guranteeProject.currentPeriod}">${guranteeProject.currentPeriod}</td>
									<td class="title" title="">
									<c:if test="${guranteeProject.flag eq '1'}">
										${fns:getDictLabel(guranteeProject.accountStatus, 'CONTRACT_STATE', '')}
									</c:if>
									<c:if test="${guranteeProject.flag eq '2'}">
										${fns:getDictLabel(guranteeProject.loanStatus, 'LOAN_STATUS', '')}
									</c:if>
									<c:if test="${guranteeProject.flag eq '3'}">
										${fns:getDictLabel(guranteeProject.applyStatus, 'APPLY_STATUS', '')}
									</c:if>	
									</td>
									<td class="title" title="${guranteeProject.bail}">${guranteeProject.bail}</td>
									<td class="title" title="${guranteeProject.guranteeServicceFee}">${guranteeProject.guranteeServicceFee}</td>
									<td class="title" title="${guranteeProject.compensatoryMoney}">${guranteeProject.compensatoryMoney}</td>
									<td class="title" title="${guranteeProject.stayMoney}">
									${guranteeProject.stayMoney}
									</td>
									<td class="title" title="${guranteeProject.guranteeCompany}">
									${guranteeProject.guranteeCompany}
									</td>
									<td>
										<a href="javascript:void(0)" onclick="detailGuarantee('${guranteeProject.contractNo}');">详情</a>
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