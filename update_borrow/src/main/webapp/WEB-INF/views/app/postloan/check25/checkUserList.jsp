<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>任务下发</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
	});

	//重置
	function resetField() {
		$("#checkedBy").val("");
	}

	function taskDown(checkUserId, checkUserName) {
		loading();
		confirmx("您是否选择该人员下发任务", function() {
			$.post("${ctx}/postloan/checkTwentyFiveAllocate/saveList", {
			checkedById : checkUserId,
			checkedBy : checkUserName,
			contractNos : '${contractNos}',
			dateFlag : '${dateFlag}'
			}, function(data) {
				if (data) {
				closeTip();
					if (data.status == 1) {//保存成功
						alertx(data.message, function() {
							parent.location.href = "${ctx}/postloan/checkTwentyFive/list?flag=3&dateFlag=" + data.dateFlag;
							closeJBox();
							//closeAndReload();
						});
					} else {
						alertx(data.message);
					}
				}
			});
		});
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="taskDownForm" modelAttribute="checkTwentyFiveAllocate" action="${ctx}/postloan/checkTwentyFiveAllocate/taskDown" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<input id="contractNos" name="contractNos" type="hidden" value="${contractNos}" />
					<div class="filter">
						<ul class="ul-form">
							<li>
								<label style="width:100px">复核人员姓名：</label>
								<form:input path="checkedBy" htmlEscape="false" maxlength="30" class="input-medium" />
							</li>
						</ul>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />
							&nbsp;
							<input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="resetField();" />
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div id="tableDataId">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th width="3%">序号</th>
							<th width="10%">借后复核人员姓名</th>
							<th width="10%">所属机构</th>
							<th width="10%">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="page" varStatus="i">
							<tr>
								<td id="num" class="title" title="序号">${i.index+1}</td>
								<td id="checkUserName" class="title" title="${page.checkUserName}">${page.checkUserName}</td>
								<td id="orgName" class="title" title="${page.orgName}">${page.orgName}</td>
								<td>
									<a href="javascript:void(0);" onclick="taskDown('${page.checkUserId}','${page.checkUserName}');">任务下发</a>
								</td>
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