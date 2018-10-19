<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>外包催收管理</title>
<script type="text/javascript">
	$(document).ready(function() {
		if ('${readOnly}' == "true") {
			$("#outDiv").hide();
		}
	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
</script>
</head>
<body>
	<div class="wrapper">
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 class="tableTitle">外包催收信息列表</h3>
			<div id="outDiv" class="ribbon">
				<ul class="layout">
					<li class="add">
						<a href="#" onclick="add('${ctx}/postloan/debtCollectionOut/form','新增外包催收信息');">
							<span>
								<b></b>
								新增
							</span>
						</a>
					</li>
					<li class="delete">
						<a href="#" onclick="finish();">
							<span>
								<b></b>
								结束催收
							</span>
						</a>
					</li>
				</ul>
			</div>
			<div id="outTableDataId" style="max-height:200px;overflow: auto;" >
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th width="5%">序号</th>
							<th width="15%">处理时间</th>
							<th width="20%">处理人</th>
							<th width="20%">客户回款描述</th>
							<th width="20%">催收详情</th>
							<th width="20%">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="debtCollectionOut">
							<tr>
								<td id="num" class="title" title="序号">${i.index+1}</td>
								<td id="collectionDate" class="title" title="<fmt:formatDate value='${debtCollectionOut.collectionDate}' pattern='yyyy-MM-dd' />">
									<fmt:formatDate value="${debtCollectionOut.collectionDate}" pattern="yyyy-MM-dd" />
								</td>
								<td id="collector" class="title" title="${debtCollectionOut.collector}">${debtCollectionOut.collector}</td>
								<td id="custResult" class="title" title="${debtCollectionOut.custResult}">${debtCollectionOut.custResult}</td>
								<td id="description" class="title" title="${debtCollectionOut.description}">${debtCollectionOut.description}</td>
								<td>
									<a href="javascript:void(0);" onclick="add('${ctx}/postloan/debtCollectionOut/form?readOnly=true&id=${debtCollectionOut.id}','查看上门催收信息');">详情</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<!-- 			<div class="pagination">${page}</div> -->
		</div>
	</div>
</body>
</html>