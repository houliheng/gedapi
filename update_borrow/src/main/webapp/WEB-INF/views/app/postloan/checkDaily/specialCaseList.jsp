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
	
	//查询
	function details(urlSingle, title, contractNo, flag, contractAmount, applyNo) {
		var height = $(window).height() - 100;
		var url = urlSingle + "?contractNo=" + contractNo + "&flag=" + flag + "&contractAmount=" + contractAmount + "&applyNo=" + applyNo;
		openJBox('', url, title, $(window.document).width() - 100, height);
	}

	//重置
	function specialCseReset() {
		$("#contractNo").val("");
		$("#custName").val("");
		$("#approProductTypeId").select2("val", "");
	}

	//详情
	function check(contractNo) {
		var url = "${ctx}/postloan/checkDaily/specialCaseForm?contractNo=" + contractNo;
		var title = "特殊情况处理";
		var width = $(top.document).width() - 300;
		width = Math.max(width, 1000);
		var height = $(top.document).height() - 200;
		openJBox("checkDailyInfoIndex", url, title, width, height);
	}
</script>
</head>
<body>
	<div id="checkDailyContractListDiv" class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="checkDailySearchForm" modelAttribute="plContract" action="${ctx}/postloan/checkDaily/specialCaseList" method="post">
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
							<input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="specialCseReset();" />
						</div>
					</div>
				</form:form>
			</div>
			<sys:message content="${message}" />
			<div class="tableList">
				<h3 class="tableTitle">数据列表</h3>
				<div id="tableDataId" style="max-height: 400px; overflow: auto;">
					<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
						<thead>
							<tr>
								<th width="2%">序号</th>
								<th width="18%">合同编号</th>
								<th width="9%">借款人姓名</th>
								<th width="9%">业务分类</th>
								<th width="9%">批复额度</th>
								<th width="5%">期数</th>
								<th width="9%">放款日期</th>
								<th width="9%">每月还款日</th>
								<th width="9%">合同期限</th>
								<shiro:hasPermission name="postloan:checkDaily:edit">
									<th width="9%">操作</th>
								</shiro:hasPermission>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${page.list }" var="plContract" varStatus="pl">
								<tr>
									<td class="title" title="${plContract.count }">${pl.count }</td>
									<td class="title" title="${plContract.contractNo }">${plContract.contractNo }</td>
									<td class="title" title="${plContract.custName }">${plContract.custName }</td>
									<td class="title" title="${plContract.approProductTypeName }">${plContract.approProductTypeName }</td>
									<td class="title" title="${plContract.loanAmount }">${plContract.loanAmount }</td>
									<td class="title" title="${plContract.approPeriodValue }">${plContract.approPeriodValue }</td>
									<td class="title" title="<fmt:formatDate value="${plContract.loanDate }" pattern="yyyy-MM-dd"/>"><fmt:formatDate value="${plContract.loanDate }" pattern="yyyy-MM-dd"/></td>
									<td class="title" title="${plContract.repayDate }">${plContract.repayDate }</td>
									<td class="title" title="<fmt:formatDate value="${plContract.conEndDate }" pattern="yyyy-MM-dd"/>"><fmt:formatDate value="${plContract.conEndDate }" pattern="yyyy-MM-dd"/></td>
									<shiro:hasPermission name="postloan:checkDaily:edit">
										<td>
											<a href="javascript:void(0);" onclick="details('${ctx}/postloan/uploadFile/toUploadPage','影像上传','${plContract.contractNo}','','','${plContract.applyNo}');">影像上传</a>
											<a href="javascript:void(0);" onclick="details('${ctx}/postloan/showImage/form','影像查阅','','','','${plContract.applyNo}');">影像查阅</a>
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