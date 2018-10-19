<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>电话外访管理</title>
<script type="text/javascript">
	$(document).ready(function() {
	});
	function add(allocateId, contractNo) {
		var url = "${ctx}/postloan/checkPhone/form";
		openJBox("checkIndeedBox", url, "", $(window).width() - 50, $(window).height() - 50, {
		allocateId : allocateId,
		contractNo : contractNo
		});
	}

	//查询
	function queryContact(urlSingle, title, custId, custName, roleType, applyNo, mobileNum) {
		custName = encodeX(custName);
		roleType = encodeX(roleType);
		var width = $(window).width() - 100
		var height = $(window).height() - 100;
		var url = urlSingle + "&roleType=" + roleType + "&custId=" + custId + "&custName=" + custName + "&applyNo=" + applyNo + "&mobileNum=" + mobileNum+"&contractNo=${plCheckPhone.contractNo}&allocateId=${plCheckPhone.allocateId}";
		openJBox("savePhoneBox", url, title, width, height);
	}

	//新增
	function addContact(urlSingle, title, custId, custName, applyNo, mobileNum) {
		custName = encodeX(custName);
		var width = $(window).width() - 100;
		var height = $(window).height() - 100;
		var url = urlSingle + "&custId=" + custId + "&custName=" + custName + "&applyNo=" + applyNo + "&mobileNum=" + mobileNum+"&contractNo=${plCheckPhone.contractNo}&allocateId=${plCheckPhone.allocateId}";
		openJBox('', url, title, width, height);
	}
</script>
</head>
<body>
	<div class="wrapper">
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 class="tableTitle">待核查列表</h3>
			<div id="tableDataId" style="max-height: 300px; overflow: auto;">
				<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
					<thead>
						<tr>
							<th width="20px">序号</th>
							<th>核查对象</th>
							<th>姓名</th>
							<th>移动电话</th>
							<th>核查次数</th>
							<c:if test="${readOnly ne 'true' }">
								<th>操作</th>
							</c:if>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${applyRelationList}" var="applyRelation" varStatus="i">
							<tr>
								<td id="num" class="title" title="${i.index+1}">${i.index+1}</td>
								<td id="roleType" class="title" title="${applyRelation.roleType}">${fns:getDictLabel(applyRelation.roleType, 'ROLE_TYPE', '')}
								<td id="custName" class="title" title="${applyRelation.custName}">${applyRelation.custName}</td>
								<td id="mobileNum" class="title" title="${applyRelation.custInfo.mobileNum}">${applyRelation.custInfo.mobileNum}</td>
								<td id="visitCount" class="title" title="${applyRelation.visitCount}">${applyRelation.visitCount}</td>
								<c:if test="${readOnly ne 'true' }">
									<td>
										<a id="add" href="javascript:void(0);" onclick="queryContact('${ctx}/postloan/plCheckPhone/form?','新增电话核查信息','${applyRelation.custId}','${applyRelation.custName}','${applyRelation.roleType}','${applyRelation.applyNo}','${applyRelation.custInfo.mobileNum}');">核查</a>
									</td>
								</c:if>
							</tr>
						</c:forEach>
						<c:forEach items="${contactInfoList}" var="contactInfo" varStatus="i">
							<tr>
								<td id="num" class="title" title="${(fn:length(applyRelationList))+i.index+1}">${(fn:length(applyRelationList))+i.index+1}</td>
								<td id="roleType" class="title" title="联系人">联系人</td>
								<td id="custName" class="title" title="${contactInfo.contactName}">${contactInfo.contactName}</td>
								<td id="mobileNum" class="title" title="${contactInfo.contactMobile}">${contactInfo.contactMobile}</td>
								<td id="visitCount" class="title" title="${contactInfo.visitCount}">${contactInfo.visitCount}</td>
								<c:if test="${readOnly ne 'true' }">
									<td>
										<a id="add" href="javascript:void(0);" onclick="addContact('${ctx}/postloan/plCheckPhone/formContact?','新增电话核查信息','${contactInfo.id}','${contactInfo.contactName}','${applyNo}','${contactInfo.contactMobile}');">核查</a>
									</td>
								</c:if>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<div class="tableList">
			<h3 class="tableTitle">电话核查信息列表</h3>
			<div id="tableDataId" style="max-height: 300px; overflow: auto;">
				<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
					<thead>
						<tr>
							<th width="20px">序号</th>
							<th>拨打时间</th>
							<th>拨打人</th>
							<th>核查对象</th>
							<th>姓名</th>
							<th>移动电话</th>
							<th>异常风险点</th>
							<th>电核详情</th>
							<shiro:hasPermission name="credit:checkPhone:edit">
								<th>操作</th>
							</shiro:hasPermission>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${checkPhoneList}" var="checkPhone" varStatus="i">
							<tr>
								<td id="num" class="title" title="序号">${i.index+1}</td>
								<td id="dialTime" class="title" title="${checkPhone.dialTime}">
									<fmt:formatDate value="${checkPhone.dialTime}" pattern="yyyy-MM-dd" />
								</td>
								<td id="checkUserName" class="title" title="${checkPhone.checkUserName}">${checkPhone.checkUserName}</td>
								<td id="roleType" class="title" title="${checkPhone.roleType}">${fns:getDictLabel(checkPhone.roleType, 'ROLE_TYPE', '')}</td>
								<td id="custName" class="title" title="${checkPhone.custName}">${checkPhone.custName}</td>
								<td id="mobileNum" class="title" title="${checkPhone.mobileNum}">${checkPhone.mobileNum}</td>
								<td id="riskPoint" class="title" title="${fns:getDictLabels(checkPhone.riskPoint, 'CUST_RISK_POINT', '')}">${fns:getDictLabels(checkPhone.riskPoint, 'CUST_RISK_POINT', '')}</td>
								<td id="description" class="title" title="${checkPhone.description}">${checkPhone.description}</td>
								<shiro:hasPermission name="credit:checkPhone:edit">
									<td>
										<a href="javascript:void(0);" onclick="queryContact('${ctx}/postloan/plCheckPhone/form?readOnly=true&id=${checkPhone.id}','查看电话核查信息','${checkPhone.custId}','${checkPhone.custName}','${checkPhone.roleType}','${checkPhone.applyNo}','${checkPhone.mobileNum}');">详情</a>
									</td>
								</shiro:hasPermission>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
</body>
</html>