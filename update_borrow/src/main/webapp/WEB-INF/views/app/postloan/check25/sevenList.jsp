<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>25日复核</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		if ('${flag}' != '3') {
			$("#taskDownDiv").remove();
		}
	});

	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#check25Form").submit();
		return false;
	}

	//重置
	function resetField() {
		$("#custName").val("");
		$("#contractNo").val("");
		$("#approProductTypeId").val("");
		$("#s2id_approProductTypeId>.select2-choice>.select2-chosen").html('--全部--');
	}

	//查询
	function details(urlSingle, title, contractNo, flag, contractAmount, applyNo, custName, readOnly, checkedType) {
		if (!checkIsNull(custName)) {
			custName = encodeX(custName);
		}
		var height = $(window).height() - 100;
		var url = urlSingle + "?contractNo=" + contractNo + "&flag=" + flag + "&contractAmount=" + contractAmount + "&applyNo=" + applyNo + "&custName=" + custName + "&readOnly=" + readOnly + "&checkedType=" + checkedType;
		openJBox('', url, title, $(window).width() - 100, height);
	}

	//影像上传和影响查阅
	function uploadImage(urlSingle, title, contractNo, flag, contractAmount, applyNo, custName, readOnly, checkedType, status) {
		if (!checkIsNull(custName)) {
			custName = encodeX(custName);
		}
		var url = urlSingle + "?contractNo=" + contractNo + "&flag=" + flag + "&contractAmount=" + contractAmount + "&applyNo=" + applyNo + "&custName=" + custName + "&readOnly=" + readOnly + "&checkedType=" + checkedType;
		if (status == "upload") {
			window.open(url, applyNo, "width= 500px" + ",height=250px");
		} else {
			window.open(url, applyNo);
		}
	}

	//待复核页面，在走签署保证合同后，关闭窗口时需要刷新父页面
	function toCheckDetails(urlSingle, title, contractNo, flag, contractAmount, applyNo, custName, readOnly, checkedType) {
		if (!checkIsNull(custName)) {
			custName = encodeX(custName);
		}
		var height = $(window).height() - 100;
		var url = urlSingle + "?contractNo=" + contractNo + "&flag=" + flag + "&contractAmount=" + contractAmount + "&applyNo=" + applyNo + "&custName=" + custName + "&readOnly=" + readOnly + "&checkedType=" + checkedType;
		openToCheckJBox('checkTwentyFiveBox', url, title, $(window).width() - 100, height);
	}

	//任务下发
	function taskDown() {
		var width = $(window).width() - 100;
		var height = $(window).height() - 50;
		var url = "${ctx}/postloan/checkTwentyFiveAllocate/taskDown";
		var $checkLine = $("input[name='type']:checked");
		if (null != $checkLine && $checkLine.length > 0) {
			var contractNos = "";
			$checkLine.each(function(v) {
				contractNos += (this.value + ",");
			});
			openJBox('', url, "任务下发", width, height, {
				contractNos : contractNos
			});
		} else {
			alertx("请选择至少一条数据");
		}
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="check25Form" modelAttribute="plContract" action="${ctx}/postloan/checkTwentyFive/list731" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<input id="flag" name="flag" type="hidden" value="${flag}" />
					<div class="filter">
						<ul class="ul-form">
							<li>
								<label>借款人姓名：</label>
								<form:input path="custName" htmlEscape="false" maxlength="200" class="input-medium" />
							</li>
							<li>
								<label>联系方式：</label>
								<form:input path="approPeriodId" htmlEscape="false" maxlength="50" class="input-medium" />
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
							<th style="width: 10%">客户民称</th>
							<th style="width: 20%">批复额度</th>
							<th style="width: 20%">期限</th>
							<th style="width: 20%">联系方式</th>
							<th style="width: 10%">还款日期</th>
							<th style="width: 10%">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="page" varStatus="i">
								<tr>
										<td id="custName" class="title" title="${page.custName}">${page.custName}</td>
							<td id="loanAmount" class="title" title="${page.loanAmount}">
							<fmt:formatNumber value="${page.loanAmount}" pattern="###,##0.00"></fmt:formatNumber>
							</td>	
								<td id="approPeriodValue" class="title" title="${page.approPeriodValue}">${page.approPeriodValue}</td>
							<td id="loanAmount" class="title" title="${page.loanAmount}">
							${page.mobileNum}
							</td>	
						
						<td id="custName" class="title" title="${page.deductDate}">${page.deductDate}</td>
							<td>
							<a href="javascript:void(0);" onclick="details('${ctx}/postloan/checkTwentyFive/toDetailsPage','731复核详情','${page.contractNo}','','','${page.applyNo}');">详情</a>
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