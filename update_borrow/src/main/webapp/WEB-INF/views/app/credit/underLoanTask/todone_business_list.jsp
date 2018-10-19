<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
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
		loading();
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
		$("#cusName").val('');
		$("#idNum").val('');
		$("#applyNo").val('');
		$("#doneStartTime").val('');
		$("#doneEndTime").val('');
		$("#applyProductTypeCode").select2("val", "");
		$("#companyId").val('');
		$("#companyName").val('');
		$("#loanModel").select2("val", "");
	}
	function timeJudge() {
		if ($("#doneStartTime").val() != '' & $("#doneEndTime").val() != '') {
			if ($("#doneStartTime").val() > $("#doneEndTime").val()) {
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
	var width = $(window).width() - 100;
	var height = $(window).height() - 50;
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
				var url = "${ctx}/credit/taskCenter/trace/photo/" + procDefId + "/" + execId;
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
	function showSignBusiness(procDefId, execId, taskDefKey, id, title, taskId, procInstId) {
		var url = "${ctx}/credit/taskCenter/loadTaskAddr/" + procDefId + "/" + execId + "/" + taskDefKey + "?taskId=" + taskId;
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
				var newUrl = "${ctx}" + location + "?procDefId=" + procDefId + "&taskDefKey=" + taskDefKey + "&applyNo=" + id + "&execId=" + execId + "&status=" + flag + "&title=" + title + "&taskId=" + taskId + "&procInstId=" + procInstId + "&canRedo=" + canRedo + "&headUrl=${headUrl}";
				//openJBox("box_" + execId, newUrl, title, width, height);
				var skip = document.getElementById("skipId");
				skip.href = newUrl;
				skip.click();
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
			var action = "${ctx}/credit/taskCenter/underLoanDone/" + $("#taskDefKey").val();
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
				<form:form id="searchForm" modelAttribute="customer" action="${ctx}/credit/taskCenter/underLoanDone" method="post">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<input id="taskDefKey" name="taskDefKey" type="hidden" value="${taskDefKey}" />
					<input id="cwfkFlag" name="cwfkFlag" type="hidden" value="${cwfkFlag}" />
					<div class="filter">
						<table class="searchTable">
							<tr>
								<td class="ft_label">借款客户：</td>
								<td class="ft_content">
									<input id="cusName" name="cusName" type="text" maxlength="50" class="input-medium" value="${customer.cusName }" />
								</td>
								<td class="ft_label">申请编号：</td>
								<td class="ft_content">
									<input id="applyNo" name="applyNo" type="text" maxlength="50" class="input-medium" value="${customer.applyNo }" />
								</td>
							</tr>
							<%--<tr>
								<td class="ft_label">产品类型：</td>
								<td class="ft_content">
									<form:select path="applyProductTypeCode" cssClass="input-medium">
										<form:option value="" label="" />
										<form:options items="${fns:getDictList('PRODUCT_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
								</td>
							</tr>--%>
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
				<table cellpadding="0" cellspacing="0" border="0" width="100%" class="table table-striped table-bordered table-condensed table-hover">
					<tr>
						<th width="20px"></th>
						<th width="20px">序号</th>
						<th width="10%">申请编号</th>
						<th width="10%">借款客户</th>
						<th width="10%">借款产品</th>
						<th width="10%">借款金额（元）</th>
						<th width="10%">期数</th>
						<th width="10%">合同还款方式</th>
						<th width="10%">冠易贷账号</th>
                        <c:if test="${taskDefKey=='under_tb'}">
                            <th width="15%">放款状态</th>
                        </c:if>
						<th width="15%">创建时间</th>
					</tr>
					<c:forEach items="${page.list}" var="process" varStatus="index">
						<c:if test="${0 == index.count%2}">
							<tr class="doubleRow">
						</c:if>
						<c:if test="${1 == index.count%2}">
							<tr>
						</c:if>
						<td>
							<input type="checkbox" value="${process.PROCESSINSTANCEID}" name="pcheck" onclick="selectSingle()" id="c_${index.count}">
							<input type="hidden" value="${process.PROCESSDEFINITIONID }" name="pHiddenPro">
							<input type="hidden" value="${process.EXECUTIONID }" name="pHiddenExe">
							<input type="hidden" value="${process.TASKDEFINITIONKEY }" name="pHiddenTak">
							<input type="hidden" value="${process.applyNo }" name="pHiddenAPP">
							<input type="hidden" value="${process.ID }" name="pHiddenid">
						</td>
						<td>${index.count}</td>
						<td class="title">
							<a href="javascript:void(0)" id="c_${index.count}_alink" onclick="showSignBusiness('${process.PROCESSDEFINITIONID }','${process.EXECUTIONID }','${process.TASKDEFINITIONKEY }','${process.applyNo }','${process.NAME}','${process.ID }','${process.PROCESSINSTANCEID}')">${process.applyNo}</a>
							<a id="skipId" href="javascript:void(0)" ></a>
						</td>
						<td class="title" style="word-break:break-all;word-wrap:break-word;">${process.busiRegName}</td>
						<td class="title" style="word-break:break-all;word-wrap:break-word;">${process.approProductName}</td>
						<td class="title" style="word-break:break-all;word-wrap:break-word;">${process.contractAmount}</td>
						<td class="title" style="word-break:break-all;word-wrap:break-word;">${process.approPeriodValue}</td>
						<td class="title" style="word-break:break-all;word-wrap:break-word;">${fns:getDictLabel(process.approLoanRepayType, 'LOAN_REPAY_TYPE', '')}</td>
						<td class="title" style="word-break:break-all;word-wrap:break-word;">${process.gedNumber}</td>
                        <c:if test="${taskDefKey=='under_tb'}">
                            <td>
                                <c:if test="${process.loanStatus=='2'}">
                                    待推送
                                </c:if>
                                <c:if test="${process.loanStatus=='3'}">
                                    已推送待放款
                                </c:if>
                                <c:if test="${process.loanStatus=='4'}">
                                    放款成功
                                </c:if>
                                <c:if test="${process.loanStatus=='5'}">
                                    流标
                                </c:if>
                                <c:if test="${process.loanStatus=='26'}">
                                    首次已提现
                                </c:if>
                                <c:if test="${process.loanStatus=='22'}">
                                    全部已提现
                                </c:if>
                            </td>
                        </c:if>
						<td class="title" title="">
							<fmt:formatDate value="${process.ENDTIME }" type="both" pattern="yyyy-MM-dd HH:mm:ss" />
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