<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>冠e通担保企业管理</title>
<script type="text/javascript">
	$(document).ready(function() {

	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
	function gqgetComInfoId() {
		$("#gqgetComInfoId").toggle(600);
	}
</script>
</head>
<body>
	<div class="tableList">
		<sys:message content="${message}" />
		<div class="tableDataId" style="max-height: 400px; overflow: auto;">
			<div class="searchInfo">
				<h3 onclick="gqgetComInfoId();" class="tableTitle">担保企业信息列表</h3>
				<div id="gqgetComInfoId">
					<div class="ribbon filter">
						<ul class="layout">
							<li class="add">
								<a id="add" href="javascript:void(0);" onclick="addCom();">
									<span>
										<b></b>
										新增
									</span>
								</a>
							</li>
							<li class="edit">
								<a id="edit" href="javascript:void(0);" onclick="editCom();">
									<span>
										<b></b>
										编辑
									</span>
								</a>
							</li>
							<li class="delete">
								<a id="delete" href="javascript:void(0);" onclick="deleteCom();">
									<span>
										<b></b>
										删除
									</span>
								</a>
							</li>
						</ul>
						<table id="contentTable" class="table table-striped table-bordered table-condensed">
							<thead>
								<tr>
									<th width="5%">
										<input type="checkbox" name="house" id="house">
									</th>
									<th width="20%">与借款公司关系</th>
									<th width="20%">企业介绍</th>
									<th width="20%">经营状况</th>
									<th width="20%">收入状况</th>
									<shiro:hasPermission name="credit:gqgetComInfo:edit">
										<th width="15%">操作</th>
									</shiro:hasPermission>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${page.list}" var="gqgetGuarantorCompany">
									<tr>
										<td>
											<input type="checkbox" value="${gqgetGuarantorCompany.id}" name="comCheck" onclick="selectSingle('comCheck');">
										</td>
										<td id="relaton" class="title" title="${gqgetGuarantorCompany.relaton}">${gqgetGuarantorCompany.relaton}</td>
										<td id="companyDesc" class="title" title="${gqgetGuarantorCompany.companyDesc}">${gqgetGuarantorCompany.companyDesc}</td>
										<td id="operationState" class="title" title="${gqgetGuarantorCompany.operationState}">${gqgetGuarantorCompany.operationState}</td>
										<td id="incomeState" class="title" title="${gqgetGuarantorCompany.incomeState}">${gqgetGuarantorCompany.incomeState}</td>
										<shiro:hasPermission name="credit:gqgetComInfo:edit">
											<td>
												<a href="javascript:void(0);" onclick="queryCom('${gqgetGuarantorCompany.id}')">详情</a>
											</td>
										</shiro:hasPermission>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<!-- 			<div class="pagination">${page}</div> -->
			</div>
		</div>
	</div>
</body>
</html>