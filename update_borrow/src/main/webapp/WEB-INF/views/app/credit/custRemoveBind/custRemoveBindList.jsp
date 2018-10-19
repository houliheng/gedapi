<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>客户解绑定管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {

	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
	function formSubmit() {
		$("#searchForm").submit();
	}
	// 	重置
	function fromReset() {
		$("#custName").val('');
		$("#isBind").select2("val", "");
		$("#idType").select2("val", "");
		$("#idNum").val('');
		$("#userName").val('');
		$("#companyId").val("");
		$("#companyName").val("");
	}

	function getIsBindVal() {
		var str = "";
		$("input[name=type]:checkbox").each(function() {
			if ($(this).attr("checked")) {
				str += this.parentElement.parentElement.cells[6].title;
			}
		});
		return str;
	}

	function bindUser(id) {
		var idStr;
		if (id == null) {
			idStr = getCheckedValue();
		} else {
			idStr = id;
		}
		var strIsBind = getIsBindVal();
		if (strIsBind.indexOf("已绑定") != -1) {
			alertx("请选择未绑定状态的数据");
			return;
		} else if (idStr != null && idStr != "") {
			var url = "${ctx}/credit/custRemoveBind/bindUser?id=" + idStr + "&userType=${userType}";
			openJBox("bindUserBox", url, "绑定", 1100, 620);
		}
	}

	// 	function bindUserSingle(id) {
	// 		var url = "${ctx}/credit/custRemoveBind/bindUser?id=" + id + "&userType=${userType}";
	// 		openJBox("bindUser-box", url, "绑定", 1100, 620);
	// 		window.showModalDialog(url, window, "dialogWidth:1100px;dialogHeight:620px;status:no;help:no;resizable:yes;location:no;");
	// 	}

	function bindOffAll() {
		var idStr = getCheckedValue();
		var strIsBind = getIsBindVal();
		if (strIsBind.indexOf("未绑定") != -1) {
			alertx("请选择已绑定状态的数据");
			return;
		} else if (idStr != null && idStr != "") {
			var url = "${ctx}/credit/custRemoveBind/bindOff?id=" + idStr + "&userType=${userType}";
			return confirmx('是否确定解绑定?', url);
		}
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="custRemoveBind" action="${ctx}/credit/custRemoveBind/?userType=${userType}" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<div class="filter">
						<table class="searchTable">
							<ul class="ul-form">
								<tr>
									<td class="ft_label">客户名称：</td>
									<td class="ft_content">
										<input id="custName" name="custName" type="text" maxlength="50" class="input-medium" value="${custRemoveBind.custName}" />
									</td>
									<c:if test="${'manage' == userType}">
										<td class="ft_label">状态：</td>
										<td class="ft_content">
											<form:select id="isBind" name="isBind" path="isBind" class="input-medium" value="${custRemoveBind.isBind}">
												<form:option value="" label="  --全部--" />
												<form:options items="${fns:getDictList('BIND_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
											</form:select>
										</td>
								</tr>
								<tr>
									</c:if>
									<c:if test="${'common' == userType}">
								</tr>
								<tr>
									</c:if>
									<td class="ft_label">证件类型：</td>
									<td class="ft_content">
										<form:select id="idType" name="idType" path="idType" class="input-medium" value="${custRemoveBind.idType}">
											<form:option value="" label="  --全部--" />
											<form:options items="${fns:getDictList('CUSTOMER_P_ID_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
										</form:select>
									</td>
									<td class="ft_label">证件号码：</td>
									<td class="ft_content">
										<input id="idNum" name="idNum" type="text" maxlength="50" class="input-medium" value="${custRemoveBind.idNum}" />
									</td>
								</tr>
								<tr>
									<c:if test="${'manage' == userType}">
										<td class="ft_label">客户经理：</td>
										<td class="ft_content">
											<input id="userName" name="user.name" type="text" maxlength="50" class="input-medium" value="${custRemoveBind.user.name}" />
										</td>
									</c:if>
									<c:if test="${'manage' == userType}">
										<td class="ft_label">所属机构：</td>
										<td class="ft_content">
											<sys:usertreeselect id="company" name="company.id" value="${custRemoveBind.company.id}" labelName="company.name" labelValue="${custRemoveBind.company.name}" title="公司" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true" />
										</td>
									</c:if>
								</tr>
							</ul>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="formSubmit();" />
							<input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="return fromReset();" />
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<div class="ribbon">
			<ul class="layout">
				<c:if test="${'manage' == userType}">
					<li class="mcp_commit">
						<a href="#" id="bind" onclick="bindUser(null);">
							<span>
								<b></b>
								绑定
							</span>
						</a>
					</li>
				</c:if>
				<li class="mcp_change">
					<a href="#" id="bindOff" onclick="bindOffAll();">
						<span>
							<b></b>
							解绑
						</span>
					</a>
				</li>
			</ul>
		</div>
		<sys:message content="${message}" type="${type}" />
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div id="tableDataId" style="max-height:400px;overflow:auto;">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<th width="20px;">
							<input type="checkbox" onclick="allCheck();" name="all" id="all">
						</th>
						<th width="15%">客户名称</th>
						<th width="15%">证件类型</th>
						<th width="15%">证件号</th>
						<th width="15%">客户经理</th>
						<th width="15%">所属机构</th>
						<th width="15%">状态</th>
						<shiro:hasPermission name="credit:custRemoveBind:edit">
							<th width="5%">操作</th>
						</shiro:hasPermission>
					</tr>
					<c:forEach items="${page.list}" var="custRemoveBind" varStatus="index">
						<c:if test="${0 == index.count%2}">
							<tr class="doubleRow">
						</c:if>
						<c:if test="${1 == index.count%2}">
							<tr>
						</c:if>
						<td width="20px">
							<input type="checkbox" value="${custRemoveBind.id}" name="type">
						</td>
						<td class="title" title="${custRemoveBind.custName}">${custRemoveBind.custName}</td>
						<td class="title" title="${fns:getDictLabel(custRemoveBind.idType, 'CUSTOMER_P_ID_TYPE', '')}">${fns:getDictLabel(custRemoveBind.idType, 'CUSTOMER_P_ID_TYPE', '')}</td>
						<td class="title" title="${custRemoveBind.idNum}">${custRemoveBind.idNum}</td>
						<td class="title" title="${custRemoveBind.user.name}">${custRemoveBind.user.name}</td>
						<td class="title" title="${custRemoveBind.company.name}">${custRemoveBind.company.name}</td>
						<td class="title" title="${fns:getDictLabel(custRemoveBind.isBind, 'BIND_STATUS', '')}">${fns:getDictLabel(custRemoveBind.isBind, 'BIND_STATUS', '')}</td>
						<td>
							<c:if test="${'0' == custRemoveBind.isBind}">
								<a href="#" onclick="bindUser('${custRemoveBind.id}');" title="绑定">绑定</a>
							</c:if>
							<c:if test="${'1' == custRemoveBind.isBind}">
								<a href="${ctx}/credit/custRemoveBind/bindOff?id=${custRemoveBind.id}&userType=${userType}" title="解绑" onclick="return confirmx('确定要解绑吗？', this.href)">解绑</a>
							</c:if>
						</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		<div class="pagination">${page}</div>
	</div>
</body>
</html>