<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="acc" tagdir="/WEB-INF/tags/accounting" %>
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
            $("#proDefKey").val('');
            $("#custName").val('');
            $("#officeId").val("");
            $("#officeLevel").val("");
            $("#officeName").val("");
            $("#createStartTime").val('');
            $("#createEndTime").val('');
        }
        function timeJudge() {
            if ($("#createStartTime").val() != '' & $("#createEndTime").val() != '') {
                if ($("#createStartTime").val() > $("#createEndTime").val()) {
                    alertx("开始时间应小于结束时间！");
                } else {
                    $("#searchForm").submit();
                }
            }else{
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

        function doSignBusiness(procDefId, execId, taskDefKey, id, title, taskId, procInstId,periodNum) {
            var url = "${ctx}/accounting/taskCenter/doBusiness/" + procDefId + "/" + execId + "/" + taskDefKey + "?taskId=" + taskId;
            $.ajax({
                type : "post",
                url : url,
                dataType : "json",
                success : function(data) {
                    if (data.flag == "fail") {
                        alertx("您选择的任务已被其他人抢办，无法再办理");
                        page();//查询
                    } else {
                        //url
                        var location = data.location;
                        //flag表示未办理0已办理1
                        var flag = "0";
                        var newUrl = "${ctx}" + location + "?procDefId=" + procDefId + "&taskDefKey=" + taskDefKey + "&applyNo=" + id + "&execId=" + execId + "&status=" + flag + "&title=" + title + "&taskId=" + taskId + "&procInstId=" + procInstId + "&periodNum=" + periodNum;
                        openJBox("box_" + execId, newUrl, title, width, height);
                    }
                },
                error : function(msg) {
                    alertx("未能办理，请查看后台信息");
                }
            });
        }
        $(document).ready(function() {
            if ($("#taskDefKey").val() != "") {
                var action = "${ctx}/accounting/taskCenter/list/" + $("#taskDefKey").val();
                $("#searchForm").attr("action", action);
            }
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
										<form:option value=""  label="" />
										<form:options items="${processList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
									</form:select>
								</td>
								<td class="ft_label">客户名称：</td>
								<td class="ft_content">
									<form:input path="custName" maxlength="50" class="input-medium"/>
								</td>
							</tr>
						</c:if>
						<tr>
							<c:if test="${noProcessName eq 'true' }">
								<td class="ft_label">客户名称：</td>
								<td class="ft_content">
									<form:hidden path="proDefKey"/>
									<form:input path="custName" maxlength="50" class="input-medium"/>
								</td>
							</c:if>
							<td class="ft_label">数据范围：</td>
							<td class="ft_content">
								<acc:officeselect url="/sys/customOffice/treeData" id="office" name="office.id" value="${taskVO.office.id}" labelName="office.name" labelValue="${taskVO.office.name}" orgLevel="office.levelnumString" title="机构" allowClear="true" cssClass="input-medium"></acc:officeselect>
							</td>
							<td class="ft_label">创建时间：</td>
							<td class="ft_content" style="width: 240px;">
								<input id="createStartTime" name="createStartTime" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate" value="<fmt:formatDate value="${taskVO.createStartTime }" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});" />
								<label>&nbsp;--&nbsp;</label>
								<input id="createEndTime" name="createEndTime" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate" value="<fmt:formatDate value="${taskVO.createEndTime }" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});" />
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
			<li class="mcp_commit">
				<a href="javascript:void(0)" id="processDo" onclick="doBusiness()">
						<span>
							<b></b>
							办理
						</span>
				</a>
			</li>
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
					<th width="10%">任务名称</th>
					<c:if test="${noProcessName ne 'true' }">
						<th width="10%">流程名称</th>
					</c:if>
					<th width="10%">合同编号</th>
					<th width="10%">客户名称</th>
					<th width="10%">登记门店</th>
					<th width="10%">区域</th>
					<th width="13%">大区</th>
					<th width="17%">创建时间</th>
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
						<input type="hidden" value="${process.CONTRACT_NO }" name="pHiddenid">
						<input type="hidden" value="${process.ID }" name="pHiddenid">
						<input type="hidden" value="${process.PERIOD_NUM }" name="pHiddenPeriodNum">
					</td>
					<td class="title" title="${process.NAME}">
						<a href="javascript:void(0)" id="c_${index.count}_alink"
						   onclick="doSignBusiness('${process.PROCESSDEFINITIONID }','${process.EXECUTIONID }','${process.TASKDEFINITIONKEY }','${process.CONTRACT_NO }','${process.NAME}','${process.ID }','${process.PROC_INS_ID}','${process.PERIOD_NUM}')">${process.NAME}</a>
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
						<fmt:formatDate value="${process.CREATETIME}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />
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