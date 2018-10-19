<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>25日复核管理</title>
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
	</script>
</head>
<body>
	<div class="wrapper">
		<ul class="nav nav-tabs">
			<li class="active"><a href="${ctx}/postloan/checkTwentyFive/">25日复核列表</a></li>
			<shiro:hasPermission name="postloan:checkTwentyFive:edit"><li><a href="${ctx}/postloan/checkTwentyFive/form">25日复核添加</a></li></shiro:hasPermission>
		</ul>
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="checkTwentyFive" action="${ctx}/postloan/checkTwentyFive/" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="filter">
						<ul class="ul-form">
							<li><label>合同编号：</label>
								<form:input path="contractNo" htmlEscape="false" maxlength="50" class="input-medium"/>
							</li>
							<li><label>复核人员：</label>
								<form:input path="checkedBy" htmlEscape="false" maxlength="30" class="input-medium"/>
							</li>
							<li><label>复核方式（1只通过电话2只通过实地走访3两者都实施）：</label>
								<form:select path="checkTwentyFiveType" class="input-medium">
									<form:option value="" label=""/>
									<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
								</form:select>
							</li>
							<li><label>电话号码：</label>
								<form:input path="checkPhone" htmlEscape="false" maxlength="15" class="input-medium"/>
							</li>
							<li><label>走访地址：</label>
								<form:input path="checkAddress" htmlEscape="false" maxlength="300" class="input-medium"/>
							</li>
							<li><label>复核结果（1保存通过2清收3法务4签署保险合同）：</label>
								<form:select path="checkTwentyFiveResult" class="input-medium">
									<form:option value="" label=""/>
									<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
								</form:select>
							</li>
							<li><label>25复核审核结果具体建议：</label>
								<form:input path="checkTwentyFiveAdvice" htmlEscape="false" maxlength="1000" class="input-medium"/>
							</li>
						</ul>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />&nbsp; 
							<input id="btnReset"class="btn btn-primary" type="button" value="重置"/>
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
							<th>合同编号</th>
							<th>复核人员</th>
							<th>复核方式（1只通过电话2只通过实地走访3两者都实施）</th>
							<th>电话号码</th>
							<th>走访地址</th>
							<th>复核结果（1保存通过2清收3法务4签署保险合同）</th>
							<th>25复核审核结果具体建议</th>
							<shiro:hasPermission name="postloan:checkTwentyFive:edit"><th>操作</th></shiro:hasPermission>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${page.list}" var="checkTwentyFive">
						<tr>
							<td id="contractNo" class="title" title="${checkTwentyFive.contractNo}"><a href="${ctx}/postloan/checkTwentyFive/form?id=${checkTwentyFive.id}">
								${checkTwentyFive.contractNo}
							</a></td>
							<td id="checkedBy" class="title" title="${checkTwentyFive.checkedBy}">
								${checkTwentyFive.checkedBy}
							</td>
							<td id="checkTwentyFiveType" class="title" title="${checkTwentyFive.checkTwentyFiveType}">
								${fns:getDictLabel(checkTwentyFive.checkTwentyFiveType, '', '')}
							</td>
							<td id="checkPhone" class="title" title="${checkTwentyFive.checkPhone}">
								${checkTwentyFive.checkPhone}
							</td>
							<td id="checkAddress" class="title" title="${checkTwentyFive.checkAddress}">
								${checkTwentyFive.checkAddress}
							</td>
							<td id="checkTwentyFiveResult" class="title" title="${checkTwentyFive.checkTwentyFiveResult}">
								${fns:getDictLabel(checkTwentyFive.checkTwentyFiveResult, '', '')}
							</td>
							<td id="checkTwentyFiveAdvice" class="title" title="${checkTwentyFive.checkTwentyFiveAdvice}">
								${checkTwentyFive.checkTwentyFiveAdvice}
							</td>
							<shiro:hasPermission name="postloan:checkTwentyFive:edit"><td>
			    				<a href="${ctx}/postloan/checkTwentyFive/form?id=${checkTwentyFive.id}">修改</a>
								<a href="${ctx}/postloan/checkTwentyFive/delete?id=${checkTwentyFive.id}" onclick="return confirmx('确认要删除该25日复核吗？', this.href)">删除</a>
							</td></shiro:hasPermission>
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