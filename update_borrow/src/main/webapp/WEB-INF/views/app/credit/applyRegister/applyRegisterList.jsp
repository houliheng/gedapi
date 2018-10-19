<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>个人客户登记管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {

	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		loading();
		$("#searchForm").submit();
		return false;
	}
	//新增
	function add() {
		$.post("${ctx}/credit/applyRegister/isAllowRegiste", null, function(data) {
			if (data) {
				if (data == 'true') {
					var url = "${ctx}/credit/applyRegister/form";
					openJBox("applyRegister-form", url, "新增个人客户信息", 1000, 500);
				} else {
					alertx(data);
				}
			} else {
				alertx("禁件校验失败，请联系管理员！");
			}
		});
	}

	//修改
	function edit(applyRegisterId) {
		if (applyRegisterId != null && "" != applyRegisterId) {
			var url = "${ctx}/credit/applyRegister/form?id=" + applyRegisterId;
			openJBox("applyRegister-form", url, "修改个人客户信息", 1000, 500);
		} else {
			var $checkLine = $("input[name='ids']:checked");
			var $len = $checkLine.length;
			if ($len != 1) {//需要勾选一条信息进行修改
				alertx("请选择一条个人客户信息");
			} else {
				var url = "${ctx}/credit/applyRegister/form?id=" + $checkLine.val();
				openJBox("applyRegister-form", url, "修改个人客户信息", 1000, 400);
			}
		}
	}
	//删除
	function del() {
		$.post("${ctx}/credit/applyRegister/isAllowRegiste", null, function(data) {
			if (data) {
				if (data == 'true') {
					var $checkLine = $("input[name='ids']:checked");
					//console.log(JSON.stringify($checkLine));
					var $len = $checkLine.length;
					if ($len < 1) {
						alertx("请选择要删除的个人客户登记信息");
					} else {
						var checkedValue = getCheckedValue("ids");
						var url = "${ctx}/credit/applyRegister/delete?ids=" + checkedValue;
						confirmx('确认要删除勾选的个人客户登记信息吗？', url);
					}
				} else {
					alertx(data);
				}
			} else {
				alertx("禁件校验失败，请联系管理员！");
			}
		});
	}
	//单个删除
	function singleDel(id,status) {
		if(status == '1'){
			$.post("${ctx}/credit/applyRegister/isAllowRegiste", null, function(data) {
				if (data) {
					if (data == 'true') {
						var url = "${ctx}/credit/applyRegister/delete?id=" + id;
						confirmx('确认要删除该个人客户登记吗？', url);
					} else {
						alertx(data);
					}
				} else {
					alertx("禁件校验失败，请联系管理员！");
				}
			});
		}else{
			var statusDescribe = getDictLabel(${fns:toJson(fns:getDictList('APPLY_STATUS'))}, status);
			alertx("'"+statusDescribe+"'状态下登记客户不可删除！");
		}
	}

	//重置
	function resetForm() {
		$("#applyStatus").select2("val", "");
		$("#idType").select2("val", "");
		$("#idNum").val('');
		$("#custName").val('');
	}

	//提交
	function submitForm() {
		loading();
		$("#searchForm").submit();
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="applyRegister" action="${ctx}/credit/applyRegister/list" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<div class="filter">
						<table class="searchTable">
							<tr>
								<td class="ft_label">客户名称：</td>
								<td class="ft_content">
									<form:input path="custName" class="input-medium" />
								</td>
								<td class="ft_label">状态：</td>
								<td class="ft_content">
									<form:select path="applyStatus" class="input-medium">
										<form:option value="" label="" />
										<form:options items="${fns:getDictList('APPLY_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
								</td>
							</tr>
							<tr>
								<td class="ft_label">证件类型：</td>
								<td class="ft_content">
									<form:select path="idType" class="input-medium">
										<form:option value="" label="" />
										<form:options items="${fns:getDictList('CUSTOMER_P_ID_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
								</td>
								<td class="ft_label">证件号：</td>
								<td class="ft_content" style="width: 240px;">
									<form:input path="idNum" htmlEscape="false" maxlength="18" class="input-medium" />
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="submitForm();" />
							&nbsp;
							<input id="btnReset" class="btn btn-primary" type="button" onclick="resetForm();" value="重置" />
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 class="tableTitle">列表信息</h3>
			<div class="ribbon">
				<ul class="layout">
					<shiro:hasPermission name="credit:applyRegister:edit">
						<li class="add">
							<a id="add" href="javascript:void(0);" onclick="add();">
								<span>
									<b></b>
									新增
								</span>
							</a>
						</li>
					</shiro:hasPermission>
					<li class="edit">
						<a id="edit" href="javascript:void(0);" onclick="edit();">
							<span>
								<b></b>
								修改
							</span>
						</a>
					</li>
					<li class="delete">
						<a id="delete" href="javascript:void(0);" onclick="del();">
							<span>
								<b></b>
								删除
							</span>
						</a>
					</li>
				</ul>
			</div>
			<div id="tableDataId" style="max-height: 400px; overflow: auto;">
				<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
					<thead>
						<tr>
							<th style="width: 3%;">
								<input id="allCheck" name="allCheck" type="checkbox" onclick="allCheck();" />
							</th>
							<th style="width: 3%;">序号</th>
							<th style="width: 5%;">客户名称</th>
							<th style="width: 10%;">申请编号</th>
							<th style="width: 10%;">证件类型</th>
							<th style="width: 10%;">证件号码</th>
							<th style="width: 10%;">移动电话</th>
							<th style="width: 10%;">渠道说明</th>
							<th style="width: 8%;">客户来源</th>
							<th style="width: 8%;">登记日期</th>
							<th style="width: 8%;">客户申请状态</th>
							<shiro:hasPermission name="credit:applyRegister:edit">
								<th style="width: 8%;">操作</th>
							</shiro:hasPermission>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="applyRegister" varStatus="applyRegisterList">
							<tr>
								<td>
									<input id="ids" name="ids" type="checkbox" value="${applyRegister.id }" />
								</td>
								<td>${applyRegisterList.count }</td>
								<td id="custName" class="title" title="${applyRegister.custName}">${applyRegister.custName}</td>
								<td id="applyNo" class="title" title="${applyRegister.applyNo}">${applyRegister.applyNo}</td>
								<td id="idType" class="title" title="${fns:getDictLabel(applyRegister.idType, 'CUSTOMER_P_ID_TYPE', '')}">${fns:getDictLabel(applyRegister.idType, 'CUSTOMER_P_ID_TYPE', '')}</td>
								<td id="idNum" class="title" title="${applyRegister.idNum}">${applyRegister.idNum}</td>
								<td id="mobileNum" class="title" title="${applyRegister.mobileNum}">${applyRegister.mobileNum}</td>
								<td id="channelOtherDesc" class="title" title="${applyRegister.channelOtherDesc}">${applyRegister.channelOtherDesc}</td>
								<td id="channelSourceType" class="title" title="${fns:getDictLabel(applyRegister.channelSourceType, 'CHANNEL_SOURCE_TYPE', '')}">${fns:getDictLabel(applyRegister.channelSourceType, 'CHANNEL_SOURCE_TYPE', '')}</td>
								<td id="registerDate" class="title" title="<fmt:formatDate value='${applyRegister.registerDate}' pattern='yyyy-MM-dd'/>">
									<fmt:formatDate value='${applyRegister.registerDate}' pattern='yyyy-MM-dd' />
								</td>
								<td id="applyStatus" class="title" title="${fns:getDictLabel(applyRegister.applyStatus, 'APPLY_STATUS', '')}">${fns:getDictLabel(applyRegister.applyStatus, 'APPLY_STATUS', '')}</td>
								<shiro:hasPermission name="credit:applyRegister:edit">
									<td>
										<a href="javascript:void(0);" onclick="edit('${applyRegister.id}');">修改</a>
										<a href="javascript:void(0);" onclick="singleDel('${applyRegister.id}','${applyRegister.applyStatus}');">删除</a>
									</td>
								</shiro:hasPermission>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="pagination">${page}</div>
		</div>
	</div>
</body>
</html>