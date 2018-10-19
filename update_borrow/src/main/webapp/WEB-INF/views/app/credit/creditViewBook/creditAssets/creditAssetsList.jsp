<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>信审意见书资产清单管理</title>
</head>
<body>
	<sys:message content="${message}" />
	<div class="tableList">
		<h3 class="tableTitle">资产清单列表</h3>
		<div class="ribbon" id="buttonDiv">
			<ul class="layout">
				<li class="add">
					<a href="#" onclick="add('${ctx}/credit/creditViewBook/creditAssets/form?applyNo=${creditAssets.applyNo}','新增资产清单列表信息');">
						<span>
							<b></b>
							新增
						</span>
					</a>
				</li>
				<li class="edit">
					<a id="edit" href="javascript:void(0)" onclick="edit('${ctx}/credit/creditViewBook/creditAssets/form','编辑车辆抵质押物信息');">
						<span>
							<b></b>
							编辑
						</span>
					</a>
				</li>
				<li class="delete">
					<a id="delete" href="#" onclick="del('${ctx}/credit/creditViewBook/creditAssets/batchDelete','${ctx }/credit/creditViewBook/creditAssets')">
						<span>
							<b></b>
							删除
						</span>
					</a>
				</li>
			</ul>
		</div>
		<div id="tableDataId">
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th width="20px;">
							<input type="checkbox" onclick="allCheck('assets','assetsType');" name="assets" id="assets">
						</th>
						<th width="20px">序号</th>
						<th>资产名称</th>
						<th>权属人类型</th>
						<th>权属人名称</th>
						<th>市值</th>
						<th>是否已抵押</th>
						<th>是否外访</th>
						<th>详细情况</th>
						<shiro:hasPermission name="credit:creditViewBook:edit">
							<th>操作</th>
						</shiro:hasPermission>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${creditAssetsList}" var="creditAssets" varStatus="i">
						<tr>
							<td width="20px">
								<input type="checkbox" value="${creditAssets.id}" name="assetsType">
							</td>
							<td id="num" class="title" title="序号">${i.index+1}</td>
							<td id="assetsName" class="title" title="${creditAssets.assetsName}">${creditAssets.assetsName}</td>
							<td id="roleType" class="title" title="${creditAssets.roleType}">${fns:getDictLabel(creditAssets.roleType, 'ROLE_TYPE', '')}</td>
							<td id="assetsOwnerName" class="title" title="${creditAssets.assetsOwnerName}">${creditAssets.assetsOwnerName}</td>
							<td id="marketValue" class="title" title="${creditAssets.marketValue}">
								<fmt:formatNumber value="${creditAssets.marketValue}" pattern="###,##0.00"></fmt:formatNumber>
							</td>
							<td id="isMortgage" class="title" title="${creditAssets.isMortgage}">${fns:getDictLabel(creditAssets.isMortgage, 'yes_no', '')}</td>
							<td id="isCheck" class="title" title="${creditAssets.isCheck}">${fns:getDictLabel(creditAssets.isCheck, 'yes_no', '')}</td>
							<td id="detailComment" class="title" title="${creditAssets.detailComment}">${creditAssets.detailComment}</td>
							<shiro:hasPermission name="credit:creditViewBook:edit">
								<td>
									<a href="javascript:void(0);" onclick="add('${ctx}/credit/creditViewBook/creditAssets/form?readOnly=true&id=${creditAssets.id}','查看资产列表信息');">详情</a>
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