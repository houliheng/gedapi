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
	function details(urlSingle, title, allocateId, contractNo, flag, contractAmount, applyNo, custName, readOnly, checkedType) {
		if (!checkIsNull(custName)) {
			custName = encodeX(custName);
		}
		var height = $(window).height() - 100;
		var url = urlSingle + "?contractNo=" + contractNo + "&flag=" + flag + "&contractAmount=" + contractAmount + "&applyNo=" + applyNo + "&custName=" + custName + "&readOnly=" + readOnly + "&checkedType=" + checkedType + "&allocateId=" + allocateId;
		openJBox('', url, title, $(window).width() - 100, height);
	}

	//影像上传和影响查阅
	function uploadImage(urlSingle, title, allocateId, contractNo, flag, contractAmount, applyNo, custName, readOnly, checkedType, status) {
		if (!checkIsNull(custName)) {
			custName = encodeX(custName);
		}
		var url = urlSingle + "?contractNo=" + contractNo + "&flag=" + flag + "&contractAmount=" + contractAmount + "&applyNo=" + applyNo + "&custName=" + custName + "&readOnly=" + readOnly + "&checkedType=" + checkedType + "&allocateId=" + allocateId;
		if (status == "upload") {
			window.open(url, applyNo, "width= 500px" + ",height=250px");
		} else {
			window.open(url, applyNo);
		}
	}

	//待复核页面，在走签署保证合同后，关闭窗口时需要刷新父页面
	function toCheckDetails(urlSingle, title, allocateId, contractNo, flag, contractAmount, applyNo, custName, readOnly, checkedType) {
		if (!checkIsNull(custName)) {
			custName = encodeX(custName);
		}
		var height = $(window).height() - 100;
		var url = urlSingle + "?contractNo=" + contractNo + "&flag=" + flag + "&contractAmount=" + contractAmount + "&applyNo=" + applyNo + "&custName=" + custName + "&readOnly=" + readOnly + "&checkedType=" + checkedType + "&allocateId=" + allocateId;
		location.href = url;
		//openToCheckJBox('checkTwentyFiveBox', url, title, $(window.document).width() - 100, height);
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
			contractNos : contractNos,
			dateFlag : '${dateFlag}'
			});
		} else {
			alertx("请选择至少一条数据");
		}
	}

	function checkedAgain(contractNo) {
		top.$.jBox.confirm("确定要进行重新复核吗？", '系统提示', function(v, h, f) {
			if (v == 'ok') {
				$.ajax({
				type : "post",
				url : "${ctx}/postloan/checkTwentyFiveAllocate/checkedAgain?contractNo=" + contractNo,
				dataType : "json",
				success : function(data) {
					alertx(data.message);
				},
				error : function(msg) {
					alertx("操作失败，请查看后台信息");
				}
				});
			}
		})

	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="check25Form" modelAttribute="plContract" action="${ctx}/postloan/checkTwentyFive/list" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<input id="flag" name="flag" type="hidden" value="${flag}" />
					<input id="dateFlag" name="dateFlag" type="hidden" value="${dateFlag}" />
					<div class="filter">
						<ul class="ul-form">
							<li>
								<label>合同编号：</label>
								<form:input path="contractNo" htmlEscape="false" maxlength="50" class="input-medium" />
							</li>
							<li>
								<label>借款人姓名：</label>
								<form:input path="custName" htmlEscape="false" maxlength="200" class="input-medium" />
							</li>
							<li>
								<label>业务分类：</label>
								<form:select path="approProductTypeId" id="approProductTypeId" class="input-medium">
									<form:option value="" label="--全部--" />
									<form:options items="${fns:getDictList('PRODUCT_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
								</form:select>
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
				<div id="taskDownDiv" class="ribbon">
					<shiro:hasPermission name="postloan:checkTwentyFiveAllocate:edit">
						<ul class="layout">
							<li class="add">
								<a id="taskDown" href="javascript:void(0);" onclick="taskDown();">
									<span>
										<b></b>
										任务下发
									</span>
								</a>
							</li>
						</ul>
					</shiro:hasPermission>
				</div>
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th width="4%">
								<input type="checkbox" onclick="allCheck('all','type');" name="all" id="all">
							</th>
							<th width="4%">序号</th>
							<th>合同编号</th>
							<th>放款日期</th>
							<th>借款人姓名</th>
							<th>批复额度</th>
							<th>合同结束日期</th>
							<th>每月还款日</th>
							<th>业务分类</th>
							<th>期数</th>
							<c:if test="${flag eq 8}">
								<th>状态</th>
							</c:if>
							<c:if test="${flag eq 2 || flag eq 11}">
								<th>复核结果</th>
							</c:if>
							<c:if test="${flag eq 11}">
								<th>核查人</th>
							</c:if>
							<c:if test="${flag ne 10}">
								<th width="10%">操作</th>
							</c:if>
							<c:if test="${flag eq 10}">
								<th>分配人员姓名</th>
							</c:if>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="page" varStatus="i">
							<tr>
								<td width="4%">
									<input type="checkbox" value="${page.contractNo}" name="type">
								</td>
								<td id="num" class="title" title="序号">${i.index+1}</td>
								<td id="contractNo" class="title" title="${page.contractNo}">${page.contractNo}</td>
								<td id="loanDate" class="title" title="${page.loanDate}">
									<fmt:formatDate value="${page.loanDate}" pattern="yyyy-MM-dd" />
								</td>
								<td id="custName" class="title" title="${page.custName}">${page.custName}</td>
								<td id="loanAmount" class="title" title="${page.loanAmount}">
									<fmt:formatNumber value="${page.loanAmount}" pattern="###,##0.00"></fmt:formatNumber>
								</td>
								<td id="conEndDate" class="title" title="${page.conEndDate}">
									<fmt:formatDate value="${page.conEndDate}" pattern="yyyy-MM-dd" />
								</td>
								<td id="loanDate" class="title" title="${page.loanDate}">
									<fmt:formatDate value="${page.loanDate}" pattern="yyyy-MM-dd" />
								</td>
								<td id="approProductTypeId" class="title" title="${page.approProductTypeId}">${fns:getDictLabel(page.approProductTypeId, 'PRODUCT_TYPE', '')}</td>
								<td id="approPeriodValue" class="title" title="${page.approPeriodValue}">${page.approPeriodValue}</td>
								<c:if test="${flag eq '8'}">
									<td id="allocateType" class="title" title="${page.allocateType}">${fns:getDictLabel(page.allocateType, 'ALLOCATE_TYPE', '')}</td>
								</c:if>
								<c:if test="${flag eq '2' || flag eq '11'}">
									<td id=checkedType class="title" title="${page.checkedType}">${fns:getDictLabel(page.checkedType, 'CHECK_TWENTY_FIVE_PROC', '')}</td>
								</c:if>
								<c:if test="${flag eq '11'}">
									<td id=checkedBy class="title" title="${page.checkedBy}">${page.checkedBy}</td>
								</c:if>
								<c:if test="${flag == '1'}">
									<td>
										<a href="javascript:void(0);" onclick="details('${ctx}/postloan/checkTwentyFive/toDetailsPage','25日复核详情','','${page.contractNo}','','','${page.applyNo}');">详情</a>
										<a href="javascript:void(0);" onclick="toCheckDetails('${ctx}/postloan/checkTwentyFive/check','核查','${page.allocateId}','${page.contractNo}','${flag}','${page.contractAmount}','${page.applyNo}','${page.custName}','','${page.checkedType}');">核查</a>
									</td>
								</c:if>
								<c:if test="${flag == '2' || flag == '11'}">
									<td>
										<a href="javascript:void(0);" onclick="details('${ctx}/postloan/checkTwentyFive/toDetailsPage','25日复核详情','','${page.contractNo}','','','${page.applyNo}');">详情</a>
										<a href="javascript:void(0);" onclick="details('${ctx}/postloan/checkTwentyFive/check','核查','${page.allocateId}','${page.contractNo}','${flag}','${page.contractAmount}','','${page.custName}','','${page.checkedType}');">核查</a>
										<c:if test="${flag == '11'}">
											<a href="javascript:void(0);" onclick="checkedAgain('${page.contractNo}');">重新复核</a>
										</c:if>
									</td>
								</c:if>
								<c:if test="${flag == '3' || flag == '4'}">
									<td>
										<a href="javascript:void(0);" onclick="details('${ctx}/postloan/checkTwentyFive/toDetailsPage','25日复核详情','','${page.contractNo}','','','${page.applyNo}');">详情</a>
									</td>
								</c:if>
								<c:if test="${flag == '5'}">
									<td>
										<a href="javascript:void(0);" onclick="details('${ctx}/postloan/checkTwentyFive/toDetailsPage','25日复核详情','','${page.contractNo}','','','${page.applyNo}');">详情</a>
										<a href="javascript:void(0);" onclick="details('${ctx}/postloan/checkTwentyFive/signGuarContract','打印合同模板','','${page.contractNo}');">打印模板</a>
										<a href="javascript:void(0);" onclick="uploadImage('${ctx}/postloan/uploadFile/toUploadPage','影像上传','','${page.contractNo}','','','${page.applyNo}','','','','upload');">影像上传</a>
										<a href="javascript:void(0);" onclick="uploadImage('${ctx}/postloan/showImage/form','影像查阅','','','','','${page.applyNo}','','','','query');">影像查阅</a>
									</td>
								</c:if>
								<c:if test="${flag == '7' || flag == '8'}">
									<td>
										<a href="javascript:void(0);" onclick="details('${ctx}/postloan/accountClean/list','清收','','${page.contractNo}','${flag}','','','','','${page.allocateType}');">清收</a>
									</td>
								</c:if>
								<c:if test="${flag == '9'}">
									<td>
										<a href="javascript:void(0);" onclick="details('${ctx}/postloan/accountClean/accountClean','分配','','${page.contractNo}');">分配</a>
									</td>
								</c:if>
								<c:if test="${flag eq '10'}">
									<td id="checkedBy" class="title" title="${page.checkedBy}">${page.checkedBy}</td>
								</c:if>
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