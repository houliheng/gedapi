<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>关联匹配信息管理</title>
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
		function detail(applyNo,contractNo){
			var url ="${ctx}/credit/applyRelation/relationalMatchInfo?applyNo=" + applyNo + "&contractNo=" + contractNo;
			var width = Math.max($(document).width() - 50, 800);
			var height = Math.min($(document).height() - 50, 500);
			openJBox("detailBox", url, "详情", width, height);
		}
	</script>
</head>
<body>
	<div class="wrapper">
		<sys:message content="${message}"/>
		<div class="tableList">
			<h3 class="tableTitle">关联匹配数据列表</h3>
			<div class="ribbon">
		</div>
			<div id="tableDataId">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
						    <th width="20px">序号</th>
							<th>对象类型</th>
							<th>对象名称</th>
							<th>关联进件编号</th>
							<th>关联进件客户名称</th>
							<th>关联对象类型</th>
							<th>详情</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${applyRelationList}" var="applyRelation" varStatus="i">
						<tr>
						    <td id="num" class="title" title="序号">
								${i.index+1}
							</td>
							<td id="roleType" class="title" title="${applyRelation.roleType}">
							    ${fns:getDictLabel(applyRelation.roleType, 'ROLE_TYPE', '')}
							</td>
							<td id="custName" class="title" title="${applyRelation.custName}">
								${applyRelation.custName}
							</td>
							<td id="applyNo" class="title" title="${applyRelation.applyNo}">
								${applyRelation.matchApplyNo}
							</td>
							<td id="matchCustName" class="title" title="${applyRelation.matchCustName}">
								${applyRelation.matchCustName}
							</td>
							<td id="matchRoleType" class="title" title="${applyRelation.matchRoleType}">
							  ${fns:getDictLabel(applyRelation.matchRoleType, 'ROLE_TYPE', '')}
							</td>
							<td>
							<%-- <a href="${ctx}/credit/contract/contractInfo?contractNo=${applyRelation.applyLoanStatus.contractNo}">操作</a> --%>
							<a href="javascript:void(0)" onclick="detail('${applyRelation.matchApplyNo}','${applyRelation.applyLoanStatus.contractNo}');">操作</a>
							</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div style="height:200px; overflow: auto;">
</body>
</html>
