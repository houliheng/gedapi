<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>代偿资金账户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#compensatoryAccount").val('');
			$(".select2-chosen").html("请选择");
			$(".class1").attr("selected", 'selected');
			$("input.input-medium").val("");
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function del() {
			$("#compensatoryAccount").val('');
			$(".select2-chosen").html("请选择");
			$(".class1").attr("selected", 'selected');
			$("input.input-medium").val("");
		}
		function addAccount() {
			var url = "${ctx}/credit/compensatoryAccount/form";
			openJBox("compensatoryAccount-form", url, "新增代偿账户", 800, 400,null);
		}
		function changeStatus(accountStatus,id){
			loading('正在修改，请稍等...');
			$.post("${ctx}/credit/compensatoryAccount/changeStatus", {accountStatus:accountStatus,id:id}, function(data) {
				if (data) {
					closeTip();
					if (data.status == 1) {
						alertx(data.message, function() {
							window.location.reload();
						});
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
				<form:form id="searchForm" modelAttribute="compensatoryAccount" action="${ctx}/credit/compensatoryAccount/list" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="filter">
						<table class="fromTable filter">
							<tr>
								<td class="ft_label">代偿资金账户：</td>
								<td class="ft_content">
									<form:input path="compensatoryAccount" htmlEscape="false" maxlength="32" class="input-medium"/>
								</td>
								<td class="ft_label">状态:</td>
								<td class="ft_content">
									<form:select path="accountStatus" class="input-medium">
										<form:option value="" class="class1" label="请选择" />
										<form:options items="${fns:getDictList('ACCOUNT_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />&nbsp; 
							<input id="btnReset"class="btn btn-primary" type="button" value="重置" onclick="del()" />
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<sys:message content="${message}"/>
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div id="guaranAId" class="ribbon filter">
					<ul class="layout">
						<li class="add">
							<a id="add" href="javascript:void(0);" onclick="addAccount();">
								<span>
									<b></b>
									新增
								</span>
							</a>
						</li>
					</ul>
			</div>
			<div id="tableDataId">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th>序号</th>
							<th>代偿资金账户  </th>
							<th>客户编码</th>
							<th>优先级</th>
							<th>状态</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${page.list}" var="compensatoryAccount"  varStatus="status">
						<tr>
							<td class="title" title="${status.index + 1}">${status.index + 1}</td>
							<td class="title" title="${compensatoryAccount.compensatoryAccount}">${compensatoryAccount.compensatoryAccount}</td>
							<td class="title" title="${compensatoryAccount.custId}">${compensatoryAccount.custId}</td>
							<td class="title">${fns:getDictLabel(compensatoryAccount.priopityLevel, 'PRIOPITY_LEVEL', '')}</td> 
							<td class="title">${fns:getDictLabel(compensatoryAccount.accountStatus, 'ACCOUNT_STATUS', '')}</td> 
							<td>
								<a href="javascript:void(0)" onclick="changeStatus('0','${compensatoryAccount.id}')">启用</a>
								<a href="javascript:void(0)" onclick="changeStatus('1','${compensatoryAccount.id}')">禁止</a>
			    				<%-- <a href="${ctx}/credit/compensatoryAccount/list?accountStatus=0&&id=${compensatoryAccount.id}">启用</a>
			    				<a href="${ctx}/credit/compensatoryAccount/list?accountStatus=1&&id=${compensatoryAccount.id}">禁止</a> --%>
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