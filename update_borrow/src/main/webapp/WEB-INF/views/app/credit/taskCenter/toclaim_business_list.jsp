<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>待分配任务列表</title>
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
			return true
		}
		if ('${ZBFlag == 1}') {
			$("#searchForm").attr("action", "${ctx}/credit/taskCenter/claimlist/${officeId}");
		} else {
			$("#searchForm").attr("action", "${ctx}/credit/taskCenter/claimlist");
		}
		loading();
		$("#searchForm").submit();
		return false;
	}

	function fromReset() {
		$("#cusName").val('');
		$("#idNum").val('');
		$("#applyNo").val('');
		$("#createStartTime").val('');
		$("#createEndTime").val('');
		page();//查询
	}

	function timeJudge() {
		if ('${ZBFlag == 1}') {
			$("#searchForm").attr("action", "${ctx}/credit/taskCenter/claimlist/${officeId}");
		} else {
			$("#searchForm").attr("action", "${ctx}/credit/taskCenter/claimlist");
		}
		if ($("#createStartTime").val() != '' & $("#createEndTime").val() != '') {
			if ($("#createStartTime").val() > $("#createEndTime").val()) {
				alertx("开始时间应小于结束时间！");
				return;
			} else {
				loading();
				$("#searchForm").submit();
				return;
			}
		}
		loading();
		$("#searchForm").submit();
	}
	//获取复选框的val
	function getCheckBoxVal() {
		var str = "";
		$("input[name=pcheck]:checked").each(function() {
			if ($(this).attr("checked")) {
				str += $(this).val() + ",";
			}
		});
		return str;
	}
	//获取PROCDEF
	function getProcHiddenVal() {
		var str = "";
		$("input[name=pcheck]:checked").each(function() {
			if ($(this).attr("checked")) {
				str = $(this).next("input[name=pHiddenPro]:hidden").val();
			}
		});
		return str;
	}
	//获取EXEC
	function getExecHiddenVal() {
		var str = "";
		$("input[name=pcheck]:checked").each(function() {
			if ($(this).attr("checked")) {
				str = $(this).next("input[name=pHiddenPro]:hidden").next("input[name=pHiddenExe]:hidden").val();
			}
		});
		return str;
	}

	//获取taskId
	function getTaskHiddenVal() {
		var str = "";
		$("input[name=pcheck]:checked").each(function() {
			if ($(this).attr("checked")) {
				str = $(this).next("input[name=pHiddenPro]:hidden").next("input[name=pHiddenExe]:hidden").next("input[name=pHiddenTak]:hidden").val();
			}
		});
		return str;
	}
	//获取Id
	function getIdHiddenVal() {
		var str = "";
		$("input[name=pcheck]:checked").each(function() {
			if ($(this).attr("checked")) {
				str = $(this).next("input[name=pHiddenPro]:hidden").next("input[name=pHiddenExe]:hidden").next("input[name=pHiddenTak]:hidden").next("input[name=pHiddenid]:hidden").val();
			}
		});
		return str;
	}

	//获取applyCode
	function getApplyCodeVal() {
		var str = "";
		$("input[name=pcheck]:checked").each(function() {
			if ($(this).attr("checked")) {
				str = $(this).parent().next().next().next().next().next().text();
			}
		});
		return str;
	}
	var width = $(window).width() - 300;
	var height = $(window).height() - 200;
	//流程轨迹
	function processTrack() {
		var str = getCheckBoxVal();
		if (str != "") {
			if (str.indexOf(",") != str.length - 1) {
				alertx("请不要选择多条对应的流程任务");
			} else {
				var taskDefKey = $("#taskDefKey").val();
				str = str.substring(0, str.length - 1);
				var url = "${ctx}/credit/taskCenter/processTrack?procInstId=" + str + " &taskDefKey= " + taskDefKey;
				openJBox("box_processTrack", url, "流程轨迹", $(document).width() - 100, $(document).height() - 100);
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
				var url = "${ctx}/credit/taskCenter/trace/photo/" + procDefId + "/" + execId;
				openJBox("box_tracePhoto", url, "流程图", $(document).width() - 100, $(document).height() - 100);
			}
		} else {
			alertx("请选择对应的流程任务");
		}
	}

	//任务分配
	function doClaim() {
		var tasklist = $("input[name='pcheck']:checked");
		if (tasklist.length < 1) {
			alertx("没有选择记录，请选择！");
		} else {
			var taskId = $("input[name='pcheck']:checked").attr("taskId");//这里目前只支持选中单个任务
			var taskDefKey = $("input[name='pcheck']:checked").attr("taskDefKey");//这里目前只支持选中单个任务
			var applyNo = $("input[name='pcheck']:checked").attr("applyNo");//这里目前只支持选中单个任务
			var processName = $("input[name='pcheck']:checked").attr("processName");//这里目前只支持选中单个任务
			var execId = $("input[name='pcheck']:checked").attr("execId");//这里目前只支持选中单个任务
			var procDefId = $("input[name='pcheck']:checked").attr("procDefId");//这里目前只支持选中单个任务
			openJBox("box_claim", "${ctx}/credit/taskCenter/loadOrgUser", "任务分配", $(document).width() - 100, $(document).height() - 100, {
			taskId : taskId,
			taskDefKey : taskDefKey,
			applyNo : applyNo,
			execId : execId,
			procDefId : procDefId,
			processName : encodeX(processName)
			});
		}
	}

	//任务打回
	function taskBackFun() {
		var tasklist = $("input[name='pcheck']:checked");
		if (tasklist.length < 1) {
			alertx("没有选择记录，请选择！");
		} else {
			var taskId = $("input[name='pcheck']:checked").attr("taskId");//这里目前只支持选中单个任务
			var procInstId = $("input[name='pcheck']:checked").val();//这里目前只支持选中单个任务
			$.ajax({
			type : "post",
			data : {
			taskId : taskId,
			procInstId : procInstId
			},
			url : "${ctx}/credit/taskCenter/taskBack",
			dataType : "json",
			success : function(data) {
				if (data.status == "1") {
					alertx(data.message, function() {
						var newUrl = '${actTaskParam.headUrl}';
						goToPage(newUrl, 'toclaimBusinessListSkipId');
					});
				} else {
					alertx(data.message);
				}
			},
			error : function(msg) {
				alertx("任务查询失败！无法获取任务地址信息！");
			}
			});
		}
	}

	//选择任务分配人员后，回调该函数
	function selectReturn(user) {
		var taskid = $("input[name='pcheck']:checked").attr("taskId");//这里目前只支持选中单个任务
		$.ajax({
		url : "${ctx}/credit/taskCenter/doAsign?user=" + user.loginName + "&taskId=" + taskid,
		type : "POST",
		success : function(data) {
			if ("success" == data) {
				alertx("任务分配成功！");
				$("#searchForm").submit();
			} else {
				alertx("任务分配失败！");
			}
		}
		});
	}
	//任务详情
	function showSignBusiness(procDefId, execId, taskDefKey, id, title, taskId, procInstId) {
		var url = "${ctx}/credit/taskCenter/loadTaskAddr/" + procDefId + "/" + execId + "/" + taskDefKey + "?taskId=" + taskId;
		$.ajax({
		type : "post",
		url : url,
		dataType : "json",
		success : function(data) {
			if (data.flag == "fail") {
				alertx("任务查询失败！无法获取任务地址信息！");
			}else {
				//url
				var location = data.location;
				var canRedo = data.canRedo;
				//flag表示未办理0已办理1
				var flag = "1";
				var newUrl = "${ctx}" + location + "?procDefId=" + procDefId + "&taskDefKey=" + taskDefKey + "&applyNo=" + id + "&execId=" + execId + "&status=" + flag + "&title=" + title + "&taskId=" + taskId + "&procInstId=" + procInstId + "&canRedo=" + canRedo + "&headUrl=${headUrl}";
				//openJBox("box_" + execId, newUrl, title, width, height);
				goToPage(newUrl, 'toclaimBusinessListSkipId');
			}
		},
		error : function(msg) {
			alertx("任务查询失败！无法获取任务地址信息！");
		}
		});
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="customer" action="" method="post">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<input id="taskDefKey" name="taskDefKey" type="hidden" value="${taskDefKey}" />
					<input id="ZBFlag" name="ZBFlag" type="hidden" value="${ZBFlag}" />
					<div class="filter">
						<table class="searchTable">
							<tr>
								<td class="ft_label">客户名称：</td>
								<td class="ft_content">
									<input id="cusName" name="cusName" type="text" maxlength="50" class="input-medium" value="${customer.cusName }" />
								</td>
								<td class="ft_label">证件号码：</td>
								<td class="ft_content">
									<input id="idNum" name="idNum" type="text" maxlength="50" class="input-medium" value="${customer.idNum }" />
								</td>
							</tr>
							<tr>
								<td class="ft_label">申请编号：</td>
								<td class="ft_content">
									<input id="applyNo" name="applyNo" type="text" maxlength="50" class="input-medium" value="${customer.applyNo }" />
								</td>
								<td class="ft_label">创建时间：</td>
								<td class="ft_content">
									<input id="createStartTime" name="createStartTime" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate" value="<fmt:formatDate value="${customer.createStartTime }" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd',isShowClear:true});" />
									<label>&nbsp;--&nbsp;</label>
									<input id="createEndTime" name="createEndTime" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate" value="<fmt:formatDate value="${customer.createEndTime }" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd',isShowClear:true});" />
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="timeJudge();" />
							&nbsp;
							<input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="return fromReset();" />
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<sys:message content="${message}" />
		<div class="ribbon">
			<ul class="layout">
				<li class="mcp_commit">
					<a href="#" id="processDo" onclick="doClaim()">
						<span>
							<b></b>
							分配
						</span>
					</a>
				</li>
				<li class="mcp_pic">
					<a href="#" id="processPicture" onclick="tracePhoto()">
						<span>
							<b></b>
							流程图
						</span>
					</a>
				</li>
				<li class="mcp_change">
					<a href="#" id="processTrack" onclick="processTrack()">
						<span>
							<b></b>
							流程轨迹
						</span>
					</a>
				</li>
				<c:if test="${ZBFlag == 1}">
					<li id="taskBackFunId" class="mcp_back">
						<a href="#" id="taskBackFun" onclick="taskBackFun()">
							<span>
								<b></b>
								退回
							</span>
						</a>
					</li>
				</c:if>
			</ul>
		</div>
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div id="tableDataId" style="max-height:400px;overflow:auto;">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<th width="20"></th>
						<th width="10%">任务名称</th>
						<th width="10%">客户名称</th>
						<th width="10%">证件类型</th>
						<th width="13%">证件号</th>
						<th width="10%">申请编号</th>
						<th width="10%">申请金额(元)</th>
						<th width="10%">合同金额(元)</th>
						<th>登记门店</th>
						<th width="10%">创建时间</th>
					</tr>
					<c:forEach items="${page.list}" var="process" varStatus="index">
						<c:if test="${0 == index.count%2}">
							<tr class="doubleRow">
						</c:if>
						<c:if test="${1 == index.count%2}">
							<tr>
						</c:if>
						<td width="20px">
							<input type="checkbox" value="${process.PROC_INS_ID}" name="pcheck" onclick="selectSingle('pcheck');" id="c_${index.count}" taskId="${process.ID }" execId="${process.EXECUTIONID }" procDefId="${process.PROCESSDEFINITIONID }" processName="${process.NAME}" applyNo="${process.APPLY_NO }" taskDefKey="${process.TASKDEFINITIONKEY}">
							<input type="hidden" value="${process.PROCESSDEFINITIONID }" name="pHiddenPro">
							<input type="hidden" value="${process.EXECUTIONID }" name="pHiddenExe">
							<input type="hidden" value="${process.TASKDEFINITIONKEY }" name="pHiddenTak">
							<input type="hidden" value="${process.APPLY_NO }" name="pHiddenid">
							<input type="hidden" value="${process.ID }" name="pHiddenid">
						</td>
						<td class="title" title="${process.NAME}">
							<a href="javascript:void(0)" id="c_${index.count}_alink" onclick="showSignBusiness('${process.PROCESSDEFINITIONID }','${process.EXECUTIONID }','${process.TASKDEFINITIONKEY }','${process.APPLY_NO }','${process.NAME}','${process.ID }','${process.PROC_INS_ID}')">${process.NAME}</a>
						</td>
						<a id="toclaimBusinessListSkipId"></a>
						<td style="word-break:break-all;word-wrap:break-word;">${process.CUST_NAME}</td>
						<td class="title">${fns:getDictLabel(process.ID_TYPE, 'CUSTOMER_P_ID_TYPE', '')}</td>
						<td class="title">${process.ID_NUM}</td>
						<td class="title">${process.APPLY_NO}</td>
						<td class="title">
							<fmt:formatNumber value="${process.APPLY_AMOUNT}" pattern="###,##0.00"></fmt:formatNumber>
						</td>
						<td class="title">
							<fmt:formatNumber value="${process.CONTRACT_AMOUNT}" pattern="###,##0.00"></fmt:formatNumber>
						</td>
						<td class="title">${process.ORG_ID}</td>
						<td class="title">
							<fmt:formatDate value="${process.CREATETIME }" type="both" pattern="yyyy-MM-dd HH:mm:ss" />
						</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="pagination">${page}</div>
		</div>
	</div>
</body>
</html>