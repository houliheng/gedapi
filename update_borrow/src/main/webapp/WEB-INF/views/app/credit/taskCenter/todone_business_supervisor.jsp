<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>办结流程监控</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		closeTip();
	});
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
		loading();
		$("#searchForm").submit();
		return false;
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
	//重置
	function fromReset() {
		$("#cusName").val('');
		$("#idNum").val('');
		$("#applyNo").val('');
		$("#createStartTime").val('');
		$("#createEndTime").val('');
	}
	//查询（时间条件）
	function timeJudge() {
		loading();
		$("#searchForm").submit();
	}
	//获取checkBox的值
	function getCheckBoxVal() {
		var str = "";
		$("input[name=pcheck]:checkbox").each(function() {
			if ($(this).attr("checked")) {
				str += $(this).val() + ",";
			}
		});
		return str;
	}
	//获取procInstId
	function getProcHiddenVal() {
		var str = "";
		$("input[name=pcheck]:checkbox").each(function() {
			if ($(this).attr("checked")) {
				str = $(this).val();
			}
		});
		return str;
	}
	var width = $(window).width() - 100;
	var height = $(window).height() - 50;
	//流程轨迹
	function processTrack() {
		var str = getCheckBoxVal();
		if (str != "") {
			if (str.indexOf(",") != str.length - 1) {
				alertx("请不要选择多条对应的流程信息");
			} else {
				var proId = getProcHiddenVal();
				var url = "${ctx}/credit/taskCenter/processTrack?procInstId=" + proId;
				openJBox("box_" + proId, url, "流程轨迹", width, height);
			}
		} else {
			alertx("请选择对应的流程信息");
		}
	}
	//流程图
	function tracPhoto() {
		var str = getCheckBoxVal();
		if (str != "") {
			if (str.indexOf(",") != str.length - 1) {
				alertx("请不要选择多条对应的流程信息");
			} else {
				var proId = getProcHiddenVal();
				var url = "${ctx}/credit/taskCenter/trace/photo/customer?procInstId=" + proId;
				openJBox("boxp_" + proId, url, "流程图", width, height);
			}
		} else {
			alertx("请选择对应的流程信息");
		}
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
				str = $(this).parent().next().next().next().next().text();
			}
		});
		return str;
	}

	//获取PROCDEF
	function getProcDefHiddenVal() {
		var str = "";
		$("input[name=pcheck]:checkbox").each(function() {
			if ($(this).attr("checked")) {
				str = $(this).next("input[name=pHiddenPro]:hidden").val();
			}
		});
		return str;
	}

	//详细
	function detail() {
		var $checkLine = $("input[name='pcheck']:checked");
		var $len = $checkLine.length;
		if ($len != 1) {//需要勾选一条信息进行修改
			alertx("请选择一条综合查询信息");
		} else {
			var str = $checkLine.val();
			var procInstId = getProcHiddenVal();
			var procDefId = getProcDefHiddenVal();
			var taskDefKey = getTaskHiddenVal();
			var applyNo = getApplyCodeVal();
			var execId = getExecHiddenVal();
			var id = getIdHiddenVal();
			var url2 = "${ctx}/credit/taskCenter/doDetail?procDefId=" + procDefId + "&taskDefKey=" + taskDefKey;
			$.ajax({
			type : "post",
			url : url2,
			dataType : "json",
			success : function(data2) {
				if (data2.flag == "fail") {
					alertx("您选择的数据不属于查看范围，请确认！");
					page();//查询
				} else {
					//url
					var location = data2.location;
					//flag表示未办理0已办理1
					var flag = "1";
					var title = "流程详细信息";
					var newUrl = "${ctx}" + location + "?procDefId=" + procDefId + "&taskDefKey=" + taskDefKey + "&applyNo=" + applyNo + "&execId=" + execId + "&status=" + flag + "&title=" + title + "&taskId=" + id + "&procInstId=" + procInstId;
					var windowWidth = window.document.body.offsetWidth - 200;
					windowWidth = Math.max(windowWidth, 800);
					var windowHeight = window.document.body.offsetHeight - 200;
					windowHeight = Math.max(windowHeight, 600);
					openJBox("box_" + execId, newUrl, title, windowWidth, windowHeight);
				}
			},
			error : function(msg) {
				alertx("未能查看，请查看后台信息");
			}
			});
		}
	}

	//详情功能
	function showInfoBtn() {
		var str = getCheckBoxVal();
		if (str != "") {
			if (str.indexOf(",") != str.length - 1) {
				alertx("请不要选择多条对应的流程信息");
			} else {
				$("input[name=pcheck]:checkbox").each(function() {
					if ($(this).attr("checked")) {
						var checkboxId = $(this).attr("id");
						$("#" + checkboxId + "_alink").click();
					}
				});
			}
		} else {
			alertx("请选择对应的流程信息");
		}
	}
	function showInfo(procInstId, applyNo, title) {
		var url = "${ctx}/credit/taskCenter/infoView?status=1&headUrl=${headUrl}&procInstId=" + procInstId + "&applyNo=" + applyNo + "&title=" + title;
		//openJBox("box_"+applyNo, url, title, width, height);
		goToPage(url, 'todoneBusinessSupervisorSkipId');
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="customer" action="${ctx}/credit/taskCenter/todoneSupervisor" method="post">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<input id="taskDefKey" name="taskDefKey" type="hidden" value="${taskDefKey}" />
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
		<div class="ribbon">
			<ul class="layout">
				<li class="mcp_pic">
					<a href="#" id="processPicture" onclick="tracPhoto()">
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
				<li class="mcp_info">
					<a href="#" id="processInfoView" onclick="detail()">
						<span>
							<b></b>
							详情
						</span>
					</a>
				</li>
			</ul>
		</div>
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div id="tableDataId" style="max-height: 400px; overflow: auto;">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<th width="5%"></th>
						<th width="10%">客户名称</th>
						<th width="10%">证件类型</th>
						<th width="10%">证件号</th>
						<th width="15%">申请编号</th>
						<th width="10%">申请金额(元)</th>
						<th width="10%">登记门店</th>
						<th width="10%">登记时间</th>
						<th width="10%">创建时间</th>
						<th width="10%">完成时间</th>
					</tr>
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
							<input type="hidden" value="${process.ID }" name="pHiddenid">
							<input type="hidden" value="${process.APPLY_NO }" name="pHiddenApp">
							<input type="hidden" value="${process.TASKID }" name="pHiddenTaskId">
						</td>
						<td class="title">
							<a href="javascript:void(0)" id="c_${index.count}_alink" onclick="showInfo('${process.PROC_INS_ID}','${process.APPLY_NO}','${process.CUST_NAME}')">${process.CUST_NAME}</a>
							<a id="todoneBusinessSupervisorSkipId"></a>
						</td>
						<td class="title">
							<c:choose>
								<c:when test="${'1'==process.CUST_TYPE}">
									${fns:getDictLabel(process.ID_TYPE, 'CUSTOMER_P_ID_TYPE', '')}
								</c:when>
								<c:when test="${'2'==process.CUST_TYPE}">
									${fns:getDictLabel(process.ID_TYPE, 'CUSTOMER_C_ID_TYPE', '')}
								</c:when>
							</c:choose>
						</td>
						<td class="title">${process.ID_NUM}</td>
						<td class="title">${process.APPLY_NO}</td>
						<td class="title">
							<fmt:formatNumber value="${process.APPLY_AMOUNT}" pattern="###,##0.00"></fmt:formatNumber>
						</td>
						<td class="title">${process.ORG_ID}</td>
						<td class="title">${process.REGISTER_DATE}</td>
						<td class="title">
							<fmt:formatDate value="${process.STARTTIME}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td class="title">
							<fmt:formatDate value="${process.ENDTIME}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />
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
