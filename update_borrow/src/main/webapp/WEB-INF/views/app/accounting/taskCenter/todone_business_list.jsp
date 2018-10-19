<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="acc" tagdir="/WEB-INF/tags/accounting"%>
<html>
<head>
<title>已办任务列表</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	/**
	@reqno: H1512250091
	@date-designer:20151226-lirongchao
	@date-author:20151226-lirongchao:分页功能不可用：修改每页显示条数回车后，不进行分页
									修正分页功能，要求能够正常进行分页、页码跳转，且功能
									与系统中其他界面的分页功能保持一致；
	 */
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
		$("#custName").val('');
		$("#proDefKey").val('');
		$("#dataScope").val('');
		$("#doneStartTime").val('');
		$("#doneEndTime").val('');
		page();//查询
	}
	function timeJudge() {
		if ($("#doneStartTime").val() != '' & $("#doneEndTime").val() != '') {
			if ($("#doneStartTime").val() > $("#doneEndTime").val()) {
				alertx("开始时间应小于结束时间！");
				return;
			} else {
				$("#searchForm").submit();
				return;
			}
		}
		$("#searchForm").submit();
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
	/**
	 * @reqno:H1509230062
	 * @date-designer:20151016-huangxuecheng
	 * @date-author:20151016-huangxuecheng:循环获取taskId
	 */
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
				str = $(this).next("input[name=pHiddenPro]:hidden").next("input[name=pHiddenExe]:hidden").next("input[name=pHiddenTak]:hidden").next("input[name=pHiddenAPP]:hidden").next("input[name=pHiddenid]:hidden").val();
			}
		});
		return str;
	}
	/**
	 * @reqno:H1509230062
	 * @date-designer:20151016-huangxuecheng
	 * @date-author:20151016-huangxuecheng:循环获取applyCode
	 */
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
	var width = $(top.document).width() - 300;
	width = Math.max(width, 1000);
	var height = $(top.document).height() - 200;
	//流程轨迹
	function processTrack() {
		var str = getCheckBoxVal();
		if (str != "") {
			if (str.indexOf(",") != str.length - 1) {
				alertx("请不要选择多条对应的流程任务");
			} else {
				var taskDefKey = $("#taskDefKey").val();
				str = str.substring(0, str.length - 1);
				var url = "${ctx}/accounting/taskCenter/processTrack?procInstId=" + str + " &taskDefKey= " + taskDefKey;
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
				var url = "${ctx}/accounting/taskCenter/trace/photo/" + procDefId + "/" + execId;
				openJBox("box_" + execId, url, "流程图", width, height);
			}
		} else {
			alertx("请选择对应的流程任务");
		}
	}
	//办理按钮
	function showBusiness() {
		var str = getCheckBoxVal();
		if (str != "") {
			if (str.indexOf(",") != str.length - 1) {
				alertx("请不要选择多条对应的流程任务");
			} else {
				$("input[name=pcheck]:checkbox").each(function() {
					if ($(this).attr("checked")) {
						var checkboxId = $(this).attr("id");
						$("#" + checkboxId + "_alink").click();
					}
				});
			}
		} else {
			alertx("请选择对应的流程任务");
		}
	}
	/**
	 * @reqno:H1512260020
	 * @date-designer:2016年1月11日-lihao02
	 * @date-author:2016年1月11日-lihao02:在各已办任务详情页面增加“撤回”按钮，
	 * 先判断当前流程是否能撤回，能撤回则在页面显示“撤回”按钮，否则页面无“撤回”按钮；
	 * 点击“撤回”按钮，撤回流程，并返回页面提示。
	 * 此参数用于页面判断是否显示“撤回”按钮。
	 */
	function showSignBusiness(procDefId, execId, taskDefKey, id, title, taskId, procInstId, periodNum) {
		var url = "${ctx}/accounting/taskCenter/loadTaskAddr/" + procDefId + "/" + execId + "/" + taskDefKey + "?taskId=" + taskId;
		$.ajax({
		type : "post",
		url : url,
		dataType : "json",
		success : function(data) {
			if (data.flag == "fail") {
				alertx("任务查询失败！无法获取任务地址信息！");
			} else {
				//url
				var location = data.location;
				var canRedo = data.canRedo;
				//flag表示未办理0已办理1
				var flag = "1";
				var newUrl = "${ctx}" + location + "?procDefId=" + procDefId + "&taskDefKey=" + taskDefKey + "&applyNo=" + id + "&execId=" + execId + "&status=" + flag + "&title=" + title + "&taskId=" + taskId + "&procInstId=" + procInstId + "&canRedo=" + canRedo + "&periodNum=" + periodNum;
				openJBox("box_" + execId, newUrl, title, width, height);
			}
		},
		error : function(msg) {
			alertx("任务查询失败！无法获取任务地址信息！");
		}
		});
	}
	//页签增加
	function addTabPage002(title, url, applyNo, taskId, taskDefKey, procDefId, status) {
		url = url + '?applyNo=' + applyNo + '&taskId=' + taskId + '&taskDefKey=' + taskDefKey + '&procDefId=' + procDefId + '&status=' + status;
		addTabPage(title, url);
	}
	//页面数据列表高度设置
	$(document).ready(function() {
		if ($("#taskDefKey").val() != "") {
			var action = "${ctx}/accounting/taskCenter/toDoneList/" + $("#taskDefKey").val();
			$("#searchForm").attr("action", action);
		}

		var tds = $(".title");
		$.each(tds, function() {
			$(this).attr("title", $.trim($(this).text()));
		});
	});
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="taskVO" action="${ctx}/accounting/taskCenter/${submitPath }" method="post">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<input id="taskDefKey" name="taskDefKey" type="hidden" value="${taskDefKey}" />
					<div class="filter">
						<table class="searchTable">
							<c:if test="${noProcessName ne 'true' }">
								<tr>
									<td class="ft_label">流程名称：</td>
									<td class="ft_content">
										<form:select path="proDefKey" class="input-medium">
											<form:option value="" label="" />
											<form:options items="${processList}" itemLabel="name" itemValue="id" htmlEscape="false" />
										</form:select>
									</td>
									<td class="ft_label">客户名称：</td>
									<td class="ft_content">
										<form:input path="custName" maxlength="50" class="input-medium" />
									</td>
								</tr>
								<tr>
									<td class="ft_label">数据范围：</td>
									<td class="ft_content">
										<acc:officeselect url="/sys/customOffice/treeData" id="office" name="office.id" value="${taskVO.office.id}" labelName="office.name" labelValue="${taskVO.office.name}" orgLevel="office.levelnumString" title="机构" allowClear="true" cssClass="input-medium"></acc:officeselect>
									</td>
									<td class="ft_label">完成时间：</td>
									<td class="ft_content" style="width: 240px;">
										<input id="createStartTime" name="createStartTime" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate" value="<fmt:formatDate value="${taskVO.createStartTime }" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});" />
										<label>&nbsp;--&nbsp;</label>
										<input id="createEndTime" name="createEndTime" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate" value="<fmt:formatDate value="${taskVO.createEndTime }" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});" />
									</td>
								</tr>
							</c:if>
							<c:if test="${noProcessName eq 'true' }">
								<tr>
									<td class="ft_label">客户名称：</td>
									<td class="ft_content">
										<form:input path="custName" maxlength="50" class="input-medium" />
									</td>
									<td class="ft_label">数据范围：</td>
									<td class="ft_content">
										<acc:officeselect url="/sys/customOffice/treeData" id="office" name="office.id" value="${taskVO.office.id}" labelName="office.name" labelValue="${taskVO.office.name}" orgLevel="office.levelnumString" title="机构" allowClear="true" cssClass="input-medium"></acc:officeselect>
									</td>
									<td class="ft_label">完成时间：</td>
									<td class="ft_content" style="width: 240px;">
										<input id="createStartTime" name="createStartTime" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate" value="<fmt:formatDate value="${taskVO.createStartTime }" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});" />
										<label>&nbsp;--&nbsp;</label>
										<input id="createEndTime" name="createEndTime" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate" value="<fmt:formatDate value="${taskVO.createEndTime }" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});" />
									</td>
								</tr>
							</c:if>
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
				<li class="mcp_info">
					<a href="#" id="processDo" onclick="showBusiness()">
						<span>
							<b></b>
							详情
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
			</ul>
		</div>
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div id="tableDataId" style="max-height:400px;overflow:auto;">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<th width="2%"></th>
						<th width="10%">任务名称</th>
						<c:if test="${noProcessName ne 'true' }">
							<th width="10%">流程名称</th>
						</c:if>
						<th width="10%">合同编号</th>
						<th width="10%">客户名称</th>
						<th width="10%">登记门店</th>
						<th width="10%">区域</th>
						<th width="13%">大区</th>
						<th width="17%">完成时间</th>
					</tr>
					<c:forEach items="${page.list}" var="process" varStatus="index">
						<tr>
							<c:if test="${0 == index.count%2}">
								<tr class="doubleRow">
							</c:if>
							<c:if test="${1 == index.count%2}">
								<tr>
							</c:if>
							<td>
								<input type="checkbox" value="${process.PROC_INS_ID}" name="pcheck" onclick="selectSingle()" id="c_${index.count}">
								<input type="hidden" value="${process.PROCESSDEFINITIONID }" name="pHiddenPro">
								<input type="hidden" value="${process.EXECUTIONID }" name="pHiddenExe">
								<input type="hidden" value="${process.TASKDEFINITIONKEY }" name="pHiddenTak">
								<input type="hidden" value="${process.APPLY_ID }" name="pHiddenAPP">
								<input type="hidden" value="${process.ID }" name="pHiddenid">
								<input type="hidden" value="${process.PERIOD_NUM }" name="pHiddenPeriodNum">
							</td>
							<td class="title" title="${process.NAME}">
								<a href="javascript:void(0)" id="c_${index.count}_alink" onclick="showSignBusiness('${process.PROCESSDEFINITIONID }','${process.EXECUTIONID }','${process.TASKDEFINITIONKEY }','${process.CONTRACT_NO }','${process.NAME}','${process.ID }','${process.PROC_INS_ID}','${process.PERIOD_NUM}')">${process.NAME}</a>
							</td>
							<c:if test="${noProcessName ne 'true' }">
								<td class="title" title="${process.PROCESSNAME}">${process.PROCESSNAME}</td>
							</c:if>
							<td class="title" title="${process.CONTRACT_NO}">${process.CONTRACT_NO}</td>
							<td class="title" title="${process.CUST_NAME}">${process.CUST_NAME}</td>
							<td class="title" title="${process.REGIST_ORG_NAME}">${process.REGIST_ORG_NAME}</td>
							<td class="title" title="${process.AREA_NAME}">${process.AREA_NAME}</td>
							<td class="title" title="${process.LARGE_AREA_NAME}">${process.LARGE_AREA_NAME}</td>
							<td class="title" title="">
								<fmt:formatDate value="${process.CREATE_DATE}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />
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
