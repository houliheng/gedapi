<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>法务催收管理</title>
<script type="text/javascript">
	$(document).ready(function() {
		if ('${readOnly}' == "true") {
			$("#legalDiv").hide();
		}
		//$("#name").focus();
		$("#inputForm").validate({
		submitHandler : function(form) {
			saveForm();
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			$("#messageBox").text("输入有误，请先更正。");
			if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
				error.appendTo(element.parent().parent());
			} else {
				error.insertAfter(element);
			}
		}
		});
	});
</script>
</head>
<body>
	<div class="wrapper">
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 class="searchTitle">法务催收信息列表</h3>
			<div id="legalDiv" class="ribbon">
				<ul class="layout">
					<li class="add">
						<a href="#" onclick="add('${ctx}/postloan/debtCollectionLegal/form','新增法务催收信息');">
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
			<div id="legalTableDataId" style="max-height:200px;overflow: auto;">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th width="5%">序号</th>
							<th width="10%">立案时间</th>
							<th width="10%">立案类型</th>
							<th width="10%">立案人</th>
							<th width="10%">立案法院</th>
							<th width="10%">处理状态</th>
							<th width="10%">处理详情</th>
							<th width="19%">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${debtCollectionLegalList}" var="debtCollectionLegal" varStatus="i">
							<tr>
								<td id="num" class="title" title="序号">${i.index+1}</td>
								<td id="collectionDate" class="title" title="${debtCollectionLegal.collectionDate}">
									<fmt:formatDate value="${debtCollectionLegal.collectionDate}" pattern="yyyy-MM-dd" />
								</td>
								<td id="legalType" class="title" title="${debtCollectionLegal.legalType}">${debtCollectionLegal.legalType}</td>
								<td id="collectorName" class="title" title="${debtCollectionLegal.collectorName}">${debtCollectionLegal.collectorName}</td>
								<td id="legalOrg" class="title" title="${debtCollectionLegal.legalOrg}">${debtCollectionLegal.legalOrg}</td>
								<td id="legalStatus" class="title" title="${debtCollectionLegal.legalStatus}">${debtCollectionLegal.legalStatus}</td>
								<td id="description" class="title" title="${debtCollectionLegal.description}">${debtCollectionLegal.description}</td>
								<td>
									<a href="javascript:void(0);" onclick="add('${ctx}/postloan/debtCollectionLegal/form?readOnly=true&id=${debtCollectionLegal.id}','查看法务催收信息');">详情</a>
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