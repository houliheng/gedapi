<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>还款划扣管理</title>
<meta name="decorator" content="default" />
<style type="text/css">
.tableList table {
	table-layout: auto;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		if ('${chooseFlag}' == "fail") {
			$("#edit").show();
		} else {
			$("#edit").hide();
		}
	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
	//重置按钮
	function del() {
		$("#companyId").val('');
		$("#companyName").val('');
		$("#startTime").val('');
		$("#endTime").val('');
		$(".select2-chosen").html("请选择");
		$(".class1").attr("selected", 'selected');
		$("input.input-medium").val("");
	}

	//重新入账
	function recordAgain() {
		var $checkLine = $("input[name='type']:checked");
		if (null != $checkLine && $checkLine.length == 1) {
			loading();
			var deductApplyNo = $("input[name='type']:checked").attr("deductApplyNo");
			$.ajax({
			url : "${ctx}/accounting/deductApply/recordAgain",
			type : "post",
			data : {
				deductApplyNo : deductApplyNo
			},
			dataType : "json",
			success : function(data) {
				closeTip();
				alertx(data.message, function() {
					chooseDataFail();
				});
			},
			error : function(msg) {
				closeTip();
				alertx("未能完成操作，请查看后台信息");
			}
			});
		} else {
			alertx("请选择一条数据");
		}
	}

	function chooseDataFail() {
		$("#chooseFlag").val("fail");
		$("#searchForm").submit();
	}

	function chooseDataAll() {
		$("#chooseFlag").val("");
		$("#searchForm").submit();
	}

	function chooseDataSuccess() {
		$("#chooseFlag").val("success");
		$("#searchForm").submit();
	}

	//重新匹配
	function matchAgain() {
		var $checkLine = $("input[name='type']:checked");
		if (null != $checkLine && $checkLine.length == 1) {
			var strikeFlag = $("input[name='type']:checked").attr("strikeFlag");
			if (checkIsNull(strikeFlag)) {
				var contractNo = $("input[name='type']:checked").val();
				var streamNo = $("input[name='type']:checked").attr("streamNo");
				var deductApplyNo = $("input[name='type']:checked").attr("deductApplyNo");
				var streamTimeStr = $("input[name='type']:checked").attr("streamTimeStr");
				$.ajax({
				url : "${ctx}/accounting/deductApply/validateIdLock",
				type:"post",
				data:{
					contractNo:contractNo
				},
				async : false,
				dataType : "JSON",
				success : function(data) {
					if (data.status == 1) {
						var width = $(window).width() - 100;
						width = Math.max(width, 1000);
						var height = $(window).height() - 50;
						openJBox("qwe", "${ctx}/accounting/deductApply/detailIndex", "重新匹配", width, height, {
						contractNo : contractNo,
						streamNo : streamNo,
						deductApplyNo : deductApplyNo,
						streamTimeStr : streamTimeStr
						});
					} else {
						alertx(data.message);
					}
				}
				});
			} else {
				alertx("请选择未冲正或未被冲正的数据");
			}
		} else {
			alertx("请选择一条数据");
		}
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="deductApplyVO" action="${ctx}/accounting/deductApply/detailList?queryFlag=button" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="chooseFlag" name="chooseFlag" type="hidden" value="${chooseFlag}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<div class="filter">
						<table class="filter">
							<tr>
								<td class="ft_label">客户名称：</td>
								<td class="ft_content">
									<form:input path="custName" htmlEscape="false" maxlength="20" class="input-medium" />
								</td>
								<td class="ft_label">合同编号：</td>
								<td class="ft_content">
									<form:input path="contractNo" htmlEscape="false" maxlength="50" class="input-medium" />
								</td>
								<td class="ft_label">所属机构：</td>
								<td class="ft_content">
									<sys:usertreeselect id="company" name="company.id" value="${deductApplyVO.company.id}" labelName="company.name" labelValue="${deductApplyVO.company.name}" title="公司" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true" />
								</td>
							</tr>
							<tr>
								<td class="ft_label">划扣人：</td>
								<td class="ft_content">
									<form:input path="deductCustId" htmlEscape="false" maxlength="20" class="input-medium" />
								</td>
								<td class="ft_label">流水号：</td>
								<td class="ft_content">
									<form:input path="streamNo" htmlEscape="false" maxlength="50" class="input-medium" />
								</td>
								<td class="ft_label">入账时间：</td>
								<td class="ft_content">
									<nobr>
										<input id="startTime" name="startTime" type="text" readonly="readonly" maxlength="20" class="input-small Wdate" value="<fmt:formatDate value="${deductApplyVO.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" style="width:150px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" />
										至
										<input id="endTime" name="endTime" type="text" readonly="readonly" maxlength="20" class="input-small Wdate" style="width:150px" value="<fmt:formatDate value="${deductApplyVO.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" />
									</nobr>
								</td>
							</tr>
							<tr>
								<td class="ft_content" colspan="6" style="text-align:right">
									<shiro:hasPermission name="accounting:deductApply:view">
										<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="chooseDataAll();" />&nbsp;</shiro:hasPermission>
									<input id="btnReset" class="btn btn-primary" type="button" onclick="del()" value="重置" />
								</td>
							</tr>
						</table>
					</div>
				</form:form>
			</div>
		</div>
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div class="ribbon">
				<ul class="layout">
					<li class="">
						<a id="add" href="javascript:void(0);" onclick="chooseDataFail();">
							<span>未入账查询</span>
						</a>
					</li>
					<li class="">
						<a id="add" href="javascript:void(0);" onclick="chooseDataSuccess();">
							<span>已入账查询</span>
						</a>
					</li>
					<li class="">
						<a id="edit" href="javascript:void(0);" onclick="recordAgain();">
							<span> 重新入账 </span>
						</a>
					</li>
					<shiro:hasPermission name="accounting:matchAgainId">
						<li class="">
							<a id="matchAgainId" href="javascript:void(0);" onclick="matchAgain();">
								<span> 重新匹配 </span>
							</a>
						</li>
					</shiro:hasPermission>
				</ul>
			</div>
			<div id="tableDataId" style="width:100%; overflow-x:auto;overflow-y:hidden">
				<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed">
					<thead>
						<tr>
							<th width="4%">
								<input type="checkbox">
							</th>
							<th>流水号</th>
							<th>入账时间</th>
							<th>入账金额</th>
							<th>客户名称</th>
							<th>合同编号</th>
							<th>大区</th>
							<th>区域</th>
							<th>分公司</th>
							<th>入账期数</th>
							<th>入账本金</th>
							<th>入账利息</th>
							<th>入账账户管理费</th>
							<th>入账服务费</th>
							<th>罚息</th>
							<th>违约金</th>
							<th>营业外收入</th>
							<th>提前还款费用</th>
							<th>退回</th>
							<th>操作时间</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="deductApplyVO">
							<tr>
								<td>
									<input name="type" type="checkbox" strikeFlag="${deductApplyVO.strikeFlag}" deductApplyNo="${deductApplyVO.deductApplyNo}" streamTimeStr="${deductApplyVO.streamTimeStr}" streamNo="${deductApplyVO.streamNo}" value="${deductApplyVO.contractNo}" onclick="selectSingle('type')">
								</td>
								<td id="streamNo" class="title" title="${deductApplyVO.streamNo}">${deductApplyVO.streamNo}</td>
								<td id="streamTime" class="title" title="<fmt:formatDate value='${deductApplyVO.streamTime}' pattern='yyyy-MM-dd' />">
									<fmt:formatDate value="${deductApplyVO.streamTime}" pattern="yyyy-MM-dd" />
								</td>
								<td id="repayAllAmount" class="title" title="${deductApplyVO.repayAllAmount}">${deductApplyVO.repayAllAmount}</td>
								<td id="custName" class="title" title="${deductApplyVO.custName}">${deductApplyVO.custName}</td>
								<td id="contractNo" class="title" title="${deductApplyVO.contractNo}">${deductApplyVO.contractNo}</td>
								<td id="orgLevel2" class="title" title="${deductApplyVO.orgLevel2.name}">${deductApplyVO.orgLevel2.name}</td>
								<td id="orgLevel3" class="title" title="${deductApplyVO.orgLevel3.name}">${deductApplyVO.orgLevel3.name}</td>
								<td id="orgLevel4" class="title" title="${deductApplyVO.orgLevel4.name}">${deductApplyVO.orgLevel4.name}</td>
								<td id="periodNum" class="title" title="${deductApplyVO.periodNum}">${deductApplyVO.periodNum}</td>
								<td id="repayCapitalAmount" class="title" title="${deductApplyVO.repayCapitalAmount}">${deductApplyVO.repayCapitalAmount}</td>
								<td id="repayInterestAmount" class="title" title="${deductApplyVO.repayInterestAmount}">${deductApplyVO.repayInterestAmount}</td>
								<td id="repayManagementFee" class="title" title="${deductApplyVO.repayManagementFee}">${deductApplyVO.repayManagementFee}</td>
								<td id="repayServiceFee" class="title" title="${deductApplyVO.repayServiceFee}">${deductApplyVO.repayServiceFee}</td>
								<td id="repayFineAmount" class="title" title="${deductApplyVO.repayFineAmount}">${deductApplyVO.repayFineAmount}</td>
								<td id="repayPenaltyAmount" class="title" title="${deductApplyVO.repayPenaltyAmount}">${deductApplyVO.repayPenaltyAmount}</td>
								<td id="repayAddAmount" class="title" title="${deductApplyVO.repayAddAmount}">${deductApplyVO.repayAddAmount}</td>
								<td id="repayBreakAmount" class="title" style="text-align: center;" title="${deductApplyVO.repayBreakAmount}">${deductApplyVO.repayBreakAmount}</td>
								<td id="backAmount" class="title" title="${deductApplyVO.backAmount}">${deductApplyVO.backAmount}</td>
								<td id="operateTime" class="title" title="<fmt:formatDate value='${deductApplyVO.operateTime}' pattern='yyyy-MM-dd' />">
									<fmt:formatDate value="${deductApplyVO.operateTime}" pattern="yyyy-MM-dd" />
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