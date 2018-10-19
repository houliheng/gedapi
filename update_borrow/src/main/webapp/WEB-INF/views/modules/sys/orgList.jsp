<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<title>机构码管理</title>
<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		function submitForm() {
			loading();
			$("#searchForm").submit();
		}
		
		function resetValue(){
			$("#name").val("");
			$("#id").val("");
			window.location.href="${ctx}/sy/code/list";
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sy/code/list">码值查询</a></li>
		<li><a href="${ctx}/sy/code/creForm">码值新增</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="code" action="${ctx}/sy/code/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
		<li><label style="padding-top: 10px;">机构编码：</label><form:input path="id" id="id" htmlEscape="false" maxlength="50" value="${id}" class="input-medium"/></li> 
			<li><label style="padding-top: 10px;">机构名称：</label><form:input path="name" id="name" htmlEscape="false" maxlength="50" value="${name}" class="input-medium"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="submitForm()"/>
			<li class="btns"><input id="reset" class="btn btn-primary" type="button" value="重置" onclick="resetValue()"/>
			<!-- <li class="clearfix"></li> -->
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
		<thead>
			<tr><!-- <th>序号</th> --><th>机构编码</th><th>单店简称</th><th>对应码值</th><!-- <th>操作</th> -->
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="code" varStatus="status">
			<tr>
				<%-- <td>${(page.pageNo-1)*30+ status.index + 1}</td> --%>
				<td>${code.id}</td>
				<td>${code.name}</td> 
				<td>${code.orgValue}</td>
			<%-- 	<td><a href="${ctx}/sy/code/creForm?id=${code.id}">修改</a></td>  --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>