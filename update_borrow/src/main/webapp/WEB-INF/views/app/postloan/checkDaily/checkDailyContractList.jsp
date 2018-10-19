<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>日常检查待分配列表</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {

	});

	//分页
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		if ($("#pageNo")[0].value.length > 10) {
			top.$.jBox.tip('当前页码大小长度不能大于10位！');
			return true;
		}
		if ($("#pageSize")[0].value.length > 10) {
			top.$.jBox.tip('每页条数大小的长度不能大于10位！');
			return true;
		}
		$("#checkDailySearchForm").submit();
		return false;
	}

	//重置
	function checkDailyReset() {
		$("#contractNo").val("");
		$("#custName").val("");
		$("#approProductTypeId").select2("val", "");
	}

	//详情
	function detail(contractNo) {
		var url = "${ctx}/postloan/checkDaily/form?checkFlag=false&myPath=${myPath}&contractNo=" + contractNo;
		var title = "日常检查详情";
		var width = $(top.document).width() - 300;
		width = Math.max(width, 1000);
		var height = $(top.document).height() - 200;
		openJBox("checkDailyInfoIndex", url, title, width, height);
	}

	//检查
	function check(contractNo) {
		var url = "${ctx}/postloan/checkDaily/form?checkFlag=true&contractNo=" + contractNo;
		var title = "日常检查";
		var width = $(top.document).width() - 300;
		width = Math.max(width, 1000);
		var height = $(top.document).height() - 200;
		openJBox("checkDailyForm", url, title, width, height);
	}

	//任务下发
	function toIssue() {
		var contractNo = "";
		var contractNoStr = getCheckedValue("singleContractNo");
		if (!checkIsNull(contractNoStr)) {
			var contractArray = contractNoStr.split(",");
			if (contractArray != null && contractArray.length == 1) {
				contractNo = contractArray[0];
				var url = "${ctx}/postloan/checkDaily/toIssue?contractNo=" + contractNo;
				var title = "任务下发";
				var width = $(top.document).width() - 300;
				width = Math.max(width, 1000);
				var height = $(top.document).height() - 200;
				openJBox("checkDailyOperatorList", url, title, width, height);
			} else {
				alertx("请选择一个要下发的任务！");
			}
		}
	}
</script>
</head>
<body>
	<div id="checkDailyContractListDiv" class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="checkDailySearchForm" modelAttribute="plContract" action="${ctx}/postloan/checkDaily/${myPath }" method="post">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<div class="filter">
						<table class="searchTable">
							<tr>
								<td class="ft_label">合同编号：</td>
								<td class="ft_content">
									<input id="contractNo" name="contractNo" type="text" maxlength="50" class="input-medium" value="${plContract.contractNo }" />
								</td>
								<td class="ft_label">借款人姓名：</td>
								<td class="ft_content">
									<input id="custName" name="custName" type="text" maxlength="50" class="input-medium" value="${plContract.custName }" />
								</td>
								<td class="ft_label">业务分类：</td>
								<td class="ft_content">
									<form:select path="approProductTypeId" class="input-medium">
										<form:option value="" label="--全部--" />
										<form:options items="${fns:getDictList('PRODUCT_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />
							&nbsp;
							<input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="checkDailyReset();" />
						</div>
					</div>
				</form:form>
			</div>
			<sys:message content="${message}" />
			<div class="tableList">
				<h3 class="tableTitle">数据列表</h3>
				<div class="ribbon">
					<c:if test="${toAllocate eq 'true' }">
						<ul class="layout">
							<li class="add">
								<a id="issue" href="javascript:void(0);" onclick="toIssue();">
									<span>
										<b></b>
										任务下发
									</span>
								</a>
							</li>
						</ul>
					</c:if>
				</div>
				<div id="tableDataId" style="max-height: 400px; overflow: auto;">
					<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
						<thead>
							<tr>
								<th width="2%"></th>
								<th width="2%">序号</th>
								<th width="18%">合同编号</th>
								<th width="6%">借款人姓名</th>
								<th width="6%">业务分类</th>
								<th width="8%">批复额度</th>
								<th width="2%">期数</th>
								<th width="8%">放款日期</th>
								<th width="6%">每月还款日</th>
								<th width="8%">合同期限</th>
								<c:if test="${toAllocate eq 'true' }">
									<th width="6%">日常检查次数</th>
									<th width="8%">上次日常检查时间</th>
								</c:if>
								<shiro:hasPermission name="postloan:checkDaily:edit">
									<th width="6%">操作</th>
								</shiro:hasPermission>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${page.list }" var="plContract" varStatus="pl">
								<tr>
									<td class="title" title="${plContract.contractNo }">
										<input type="checkbox" id="singleContractNo" name="singleContractNo" onclick="selectSingle('singleContractNo');" value="${plContract.contractNo }" />
									</td>
									<td class="title" title="${plContract.count }">${pl.count }</td>
									<td class="title" title="${plContract.contractNo }">${plContract.contractNo }</td>
									<td class="title" title="${plContract.custName }">${plContract.custName }</td>
									<td class="title" title="${plContract.approProductTypeName }">${plContract.approProductTypeName }</td>
									<td class="title" title="${plContract.loanAmount }">${plContract.loanAmount }</td>
									<td class="title" title="${plContract.approPeriodValue }">${plContract.approPeriodValue }</td>
									<td class="title" title="${plContract.loanDate }">${plContract.loanDate }</td>
									<td class="title" title="${plContract.repayDate }">${plContract.repayDate }</td>
									<td class="title" title="${plContract.conEndDate }">${plContract.conEndDate }</td>
									<c:if test="${toAllocate eq 'true' }">
										<td class="title" title="${plContract.checkDailyTimes }">${plContract.checkDailyTimes }</td>
										<td class="title" title="<fmt:formatDate value="${plContract.lastCheckedDate}" pattern="yyyy-MM-dd"/>">
											<fmt:formatDate value="${plContract.lastCheckedDate}" pattern="yyyy-MM-dd" />
										</td>
									</c:if>
									<shiro:hasPermission name="postloan:checkDaily:edit">
										<td>
											<a href="#" onclick="detail('${plContract.contractNo }')">详情</a>
											<c:if test="${toCheck eq 'true' }">
												<a href="#" onclick="check('${plContract.contractNo }')">检查</a>
											</c:if>
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
	</div>
</body>
</html>