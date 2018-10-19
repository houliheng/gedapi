<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>担保人信息</title>
</head>
<script type="text/javascript">
	function guaranA() {
		$("#guaranAId").toggle(600);
	}
</script>
<body>
	<div class="tableList">
		<sys:message content="${message}" />
		<div id="tableDataId" style="max-height: 400px; overflow: auto;">
			<div class="searchInfo">
				<h3 onclick="guaranA()" class="searchTitle">担保人信息列表</h3>
				<div id="guaranAId" class="ribbon filter">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th width="3%">
									<input type="checkbox" id="checkAllGuarantor" name="checkAllGuarantor" onclick="allCheck('checkAllGuarantor','guarantorIds');" />
								</th>
								<th width="3%">序号</th>
								<th width="10%">姓名</th>
								<th width="10%">性别</th>
								<th width="15%">证件号</th>
								<th width="10%">婚姻状况</th>
								<th width="10%">单位名称</th>
								<th width="10%">与借款人关系</th>
								<th width="15%">房产地址</th>
								<th width="14%">房产估值</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${guarantorRelationList}" var="guarantorRelation" varStatus="guarantorRelationList">
								<tr>
									<td>
										<input type="checkbox" name="guarantorIds" id="guarantorIds" value="${guarantorRelation.custInfo.id }" />
									</td>
									<td>${guarantorRelationList.index+1 }</td>
									<td>${guarantorRelation.custInfo.custName}</td>
									<td>${fns:getDictLabel(guarantorRelation.custInfo.sexNo, 'sex', '')}</td>
									<td>${guarantorRelation.custInfo.idNum}</td>
									<td>${fns:getDictLabel(guarantorRelation.custInfo.wedStatus, 'WED_STATUS', '')}</td>
									<td>${guarantorWorkInfoList[guarantorRelationList.index].companyName }</td>
									<td>${fns:getDictLabel(guarantorRelation.relationForApply, 'CONTACT_RELATIONS', '')}</td>
									<td>${guarantorRelation.custInfo.houseAddress}</td>
									<td>${guarantorRelation.custInfo.estateValuation}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
