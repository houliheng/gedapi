<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>电话催收管理</title>
<script type="text/javascript">
	$(document).ready(function() {
		if ('${readOnly}' == "true") {
			$("#phoneDiv").hide();
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
			<h3 class="tableTitle">电话催收信息列表</h3>
			<div id="phoneDiv" class="ribbon">
				<ul class="layout">
					<li class="add">
						<a href="#" onclick="add('${ctx}/postloan/debtCollectionPhone/form','新增电话催收信息');">
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
			<div id="PhoneTableDataId" style="max-height:200px;overflow: auto;">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th width="5%">序号</th>
							<th width="15%">催收时间</th>
							<th width="15%">催收人</th>
							<th width="15%">电话号码</th>
							<th width="15%">催收对象</th>
							<th width="15%">催收详情</th>
							<th width="15%">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="debtCollectionPhone" varStatus="i">
							<tr>
								<td id="num" class="title" title="序号">${i.index+1}</td>
								<td id="collectionDate" class="title" title="<fmt:formatDate value='${debtCollectionPhone.collectionDate}' pattern='yyyy-MM-dd' />">
									<fmt:formatDate value="${debtCollectionPhone.collectionDate}" pattern="yyyy-MM-dd" />
								</td>
								<td id="collector" class="title" title="${debtCollectionPhone.collectorName}">${debtCollectionPhone.collectorName}</td>
								<td id="callNum" class="title" title="${debtCollectionPhone.callNum}">${debtCollectionPhone.callNum}</td>
								<td id="custName" class="title" title="${debtCollectionPhone.custName}">${debtCollectionPhone.custName}</td>
								<td id="description" class="title" title="${debtCollectionPhone.description}">${debtCollectionPhone.description}</td>
								<td>
									<a href="javascript:void(0);" onclick="add('${ctx}/postloan/debtCollectionPhone/form?readOnly=true&id=${debtCollectionPhone.id}','查看电话催收信息');">详情</a>
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