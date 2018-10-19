<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>上门催收管理</title>
<script type="text/javascript">
	$(document).ready(function() {
		if ('${readOnly}' == "true") {
			$("#faceDiv").hide();
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
			<h3 class="tableTitle">上门催收信息列表</h3>
			<div id="faceDiv" class="ribbon">
				<ul class="layout">
					<li class="add">
						<a href="#" onclick="add('${ctx}/postloan/debtCollectionFace/form','新增上门催收信息');">
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
			<div id="faceTableDataId" style="max-height:200px;overflow: auto;">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th width="5%">序号</th>
							<th width="15%">上门时间</th>
							<th width="20%">上门人</th>
							<th width="20%">催收对象</th>
							<th width="20%">催收详情</th>
							<th width="15%">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="debtCollectionFace" varStatus="i">
							<tr>
								<td id="num" class="title" title="序号">${i.index+1}</td>
								<td id="collectionDate" class="title" title="<fmt:formatDate value='${debtCollectionFace.collectionDate}' pattern='yyyy-MM-dd' />">
									<fmt:formatDate value="${debtCollectionFace.collectionDate}" pattern="yyyy-MM-dd" />
								</td>
								<td id="collector" class="title" title="${debtCollectionFace.collector}">${debtCollectionFace.collector}</td>
								<td id="custName" class="title" title="${debtCollectionFace.custName}">${debtCollectionFace.custName}</td>
								<td id="description" class="title" title="${debtCollectionFace.description}">${debtCollectionFace.description}</td>
								<td>
									<a href="javascript:void(0);" onclick="add('${ctx}/postloan/debtCollectionFace/form?readOnly=true&id=${debtCollectionFace.id}','查看上门催收信息');">详情</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>