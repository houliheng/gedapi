<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>待办任务列表</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
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
		$("#searchForm").submit();
		return false;
	}
	//复选框选中
	function selectAll() {
		if ($('[name=all]:checkbox').attr("checked") == "checked") {
			$('[name=pcheck]:checkbox').attr("checked", true);
		} else {
			$('[name=pcheck]:checkbox').attr("checked", false);
		}
	}
	//单选框
	function selectSingle() {
		$("[name=pcheck]:checkbox").each(function() {
			$(this).click(function() {
				if ($(this).attr("checked")) {
					$("[name=pcheck]:checkbox").removeAttr("checked");
					$(this).attr("checked", "checked");
				}
			});
		});
	}
	function fromReset() {
		$("#contractNo").val('');
		$("#custName").val('');
		page();//查询
	}
	function timeJudge() {
		if ($("#createStartTime").val() != '' & $("#createEndTime").val() != '') {
			if ($("#createStartTime").val() > $("#createEndTime").val()) {
				alertx("开始时间应小于结束时间！");
			} else {
				$("#searchForm").submit();
			}
		} else {
			$("#searchForm").submit();
		}
	}
	//获取复选框的val
	function getCheckBoxVal() {
		var str = "";
		$("input[name=pcheck]:checkbox").each(function() {
			if ($(this).attr("checked")) {
				str += $(this).val() + ",";
			}
		});
		return str;
	}
	//获取PROCDEF
	function getProcHiddenVal() {
		var str = "";
		$("input[name=pcheck]:checkbox").each(function() {
			if ($(this).attr("checked")) {
				str = $(this).next("input[name=pHiddenPro]:hidden").val();
			}
		});
		return str;
	}
	//获取EXEC
	function getExecHiddenVal() {
		var str = "";
		$("input[name=pcheck]:checkbox").each(function() {
			if ($(this).attr("checked")) {
				str = $(this).next("input[name=pHiddenPro]:hidden").next("input[name=pHiddenExe]:hidden").val();
			}
		});
		return str;
	}
	//获取taskId
	function getTaskHiddenVal() {
		var str = "";
		$("input[name=pcheck]:checkbox").each(function() {
			if ($(this).attr("checked")) {
				str = $(this).next("input[name=pHiddenPro]:hidden").next("input[name=pHiddenExe]:hidden").next("input[name=pHiddenTak]:hidden").val();
			}
		});
		return str;
	}
	//获取Id
	function getIdHiddenVal() {
		var str = "";
		$("input[name=pcheck]:checkbox").each(function() {
			if ($(this).attr("checked")) {
				str = $(this).next("input[name=pHiddenPro]:hidden").next("input[name=pHiddenExe]:hidden").next("input[name=pHiddenTak]:hidden").next("input[name=pHiddenid]:hidden").val();
			}
		});
		return str;
	}
	//获取applyCode
	function getApplyCodeVal() {
		var str = "";
		$("input[name=pcheck]:checkbox").each(function() {
			if ($(this).attr("checked")) {
				str = $(this).parent().next().next().next().next().next().text();
			}
		});
		return str;
	}
	var width = 1000;
	/* width = Math.max(width, 1000); */
	var height = 500;
	//流程轨迹
	function processTrack() {
		var str = getCheckBoxVal();
		if (str != "") {
			if (str.indexOf(",") != str.length - 1) {
				alertx("请不要选择多条对应的流程任务");
			} else {
				var taskDefKey = $("#taskDefKey").val();
				str = str.substring(0, str.length - 1);
				var url = "${ctx}/postloan/taskCenter/processTrack?procInstId=" + str + " &taskDefKey= " + taskDefKey;
				openJBox("box_" + taskDefKey, url, "流程轨迹", width, height);
			}
		} else {
			alertx("请选择对应的流程任务");
		}
	}
	//流程图
	function tracePhoto() {
		var str = getCheckBoxVal();
		if (str != "") {
			if (str.indexOf(",") != str.length - 1) {
				alertx("请不要选择多条对应的流程任务");
			} else {
				str = str.substring(0, str.length - 1);
				var procDefId = getProcHiddenVal();
				var execId = getExecHiddenVal();
				var url = "${ctx}/postloan/taskCenter/trace/photo/" + procDefId + "/" + execId;
				openJBox("box_" + execId, url, "流程图", width, height);
			}
		} else {
			alertx("请选择对应的流程任务");
		}
	}
	//办理按钮
	function doBusiness() {
		var str = getCheckBoxVal();
		if (str != "") {
			if (str.indexOf(",") != str.length - 1) {
				alertx("请不要选择多条对应的办理任务");
			} else {
				$("input[name=pcheck]:checkbox").each(function() {
					if ($(this).attr("checked")) {
						//str = $(this).next("input[name=pHiddenPro]:hidden").next("input[name=pHiddenExe]:hidden").next("input[name=pHiddenTak]:hidden").val();
						var checkboxId = $(this).attr("id");
						$("#" + checkboxId + "_alink").click();
					}
				});

			}
		} else {
			alertx("请选择对应的流程任务");
		}
	}

	$(document).ready(function() {
		if ($("#taskDefKey").val() != "") {
			var action = "${ctx}/postloan/taskCenter/list/" + $("#taskDefKey").val();
			$("#searchForm").attr("action", action);
		}
	});

	//任务下发
	function doSignBusiness(procDefId, execId, taskDefKey, contractNo, title, taskId, procInstId, applyNo, periodNum, companyId) {
		var newUrl = "${ctx}/postloan/collateralDisposal/toTaskDown";
		openJBox('toTaskDownBox', newUrl, "任务下发", $(window).width() - 300, $(window).height() - 200, {
		procDefId : procDefId,
		execId : execId,
		taskDefKey : taskDefKey,
		title : title,
		taskId : taskId,
		procInstId : procInstId,
		applyNo : applyNo,
		contractNo : contractNo,
		periodNum : periodNum,
		companyId : companyId
		});
	}

	//详情
	function toDetailsPage(applyNo) {
		var newUrl = "${ctx}/postloan/collateralDisposal/toDetailsPage";
		openJBox('toDetailsPageBox', newUrl, "详情", $(window).width() - 100, $(window).height() - 100, {
			applyNo : applyNo
		});
	}

	//处置
	function toMortgagePage(procDefId, execId, taskDefKey, contractNo, title, taskId, procInstId, applyNo) {
		var newUrl = "${ctx}/postloan/collateralDisposal/mortgage";
		openJBox('toMortgagePageBox', newUrl, "处置", $(window).width() - 100, $(window).height() - 100, {
		procDefId : procDefId,
		taskDefKey : taskDefKey,
		contractNo : contractNo,
		execId : execId,
		status : "0",
		title : title,
		taskId : taskId,
		procInstId : procInstId,
		applyNo : applyNo
		});
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="taskVO" action="${ctx}/postloan/taskCenter/${submitPath }" method="post">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<input id="taskDefKey" name="taskDefKey" type="hidden" value="${taskDefKey}" />
					<div class="filter">
						<table class="searchTable">
							<tr>
								<td class="ft_label">客户名称：</td>
								<td class="ft_content">
									<form:input path="custName" htmlEscape="false" class="input-medium" />
								</td>
								<td class="ft_label">合同编号：</td>
								<td class="ft_content">
									<form:input path="contractNo" htmlEscape="false" maxlength="32" class="input-medium" />
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="timeJudge();" />
							<input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="return fromReset();" />
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<sys:message content="${message}" />
		<div class="ribbon">
			<ul class="layout">
				<li class="mcp_pic">
					<a href="javascript:void(0)" id="processPicture" onclick="tracePhoto()">
						<span>
							<b></b>
							流程图
						</span>
					</a>
				</li>
				<li class="mcp_change">
					<a href="javascript:void(0)" id="processTrack" onclick="processTrack()">
						<span>
							<b></b>
							流程轨迹
						</span>
					</a>
				</li>
			</ul>
		</div>
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div id="tableDataId" style="max-height: 400px; overflow: auto;">
				<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered table-condensed table-hover">
					<thead>
						<tr>
							<th width="2%"></th>
							<th width="8%">任务名称</th>
							<th width="7%">客户名称</th>
							<th width="8%">联系方式</th>
							<th width="7%">合同编号</th>
							<th width="8%">合同金额</th>
							<th width="4%">期数</th>
							<th width="8%">逾期金额</th>
							<th width="8%">逾期天数</th>
							<th width="5%">大区</th>
							<th width="8%">区域</th>
							<th width="8%">分公司</th>
							<th width="7%">登记人</th>
							<th width="12%">操作</th>
						</tr>
					</thead>
					<c:forEach items="${page.list}" var="process" varStatus="index">
						<c:if test="${0 == index.count%2}">
							<tr class="doubleRow">
						</c:if>
						<c:if test="${1 == index.count%2}">
							<tr>
						</c:if>
						<td width="20px">
							<input type="checkbox" value="${process.PROC_INS_ID}" name="pcheck" onclick="selectSingle()" id="c_${index.count}">
							<input type="hidden" value="${process.PROCESSDEFINITIONID }" name="pHiddenPro">
							<input type="hidden" value="${process.EXECUTIONID }" name="pHiddenExe">
							<input type="hidden" value="${process.TASKDEFINITIONKEY }" name="pHiddenTak">
							<input type="hidden" value="${process.contractNo }" name="pHiddenid">
							<input type="hidden" value="${process.ID }" name="pHiddenid">
							<input type="hidden" value="${process.periodNum }" name="pHiddenPeriodNum">
						</td>
						<td class="title" title="${process.NAME}">${process.NAME}</td>
						<td id="custName" class="title" title="${process.custName}">${process.custName}</td>
						<td id="mobileNum" class="title" title="${process.mobileNum}">${process.mobileNum}</td>
						<td id="contractNo" class="title" title="${process.contractNo}">${process.contractNo}</td>
						<td id="contractAmount" class="title" title="${process.contractAmount}">${process.contractAmount}</td>
						<td id="periodNum" class="title" title="${process.periodNum}">${process.periodNum}</td>
						<td id="overdueAmount" class="title" title="${process.overdueAmount}">${process.overdueAmount}</td>
						<td id="overdueDays" class="title" title="${process.overdueDays}">${process.overdueDays}</td>
						<td id="orgLevel2" class="title" title="${process.orgLevel2.name}">${process.orgLevel2.name}</td>
						<td id="orgLevel3" class="title" title="${process.orgLevel3.name}">${process.orgLevel3.name}</td>
						<td id="orgLevel4" class="title" title="${process.orgLevel4.name}">${process.orgLevel4.name}</td>
						<td id="registerName" class="title" title="${process.registerName}">${process.registerName}</td>
						<c:if test="${taskDefKey == 'utask_dqrwxf'}">
							<td>
								<a href="javascript:void(0)" onclick="doSignBusiness('${process.PROCESSDEFINITIONID }','${process.EXECUTIONID }','${process.TASKDEFINITIONKEY }','${process.contractNo }','${process.NAME}','${process.ID }','${process.PROC_INS_ID}','${process.applyNo}','${process.periodNum}','${process.orgLevel3.id}')">任务下发</a>
							</td>
						</c:if>
						<c:if test="${taskDefKey != 'utask_dqrwxf'}">
							<td>
								<a href="javascript:void(0)" onclick="toDetailsPage('${process.applyNo}')">详情</a>
								<a href="javascript:void(0);" onclick="toMortgagePage('${process.PROCESSDEFINITIONID }','${process.EXECUTIONID }','${process.TASKDEFINITIONKEY }','${process.contractNo }','${process.NAME}','${process.ID }','${process.PROC_INS_ID}','${process.applyNo}');">处置</a>
							</td>
						</c:if>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="pagination">${page}</div>
		</div>
	</div>
</body>
</html>