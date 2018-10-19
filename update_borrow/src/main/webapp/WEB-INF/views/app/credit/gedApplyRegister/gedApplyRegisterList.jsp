<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>个人客户登记管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {

	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		loading();
		$("#searchForm").submit();
		return false;
	}

	//重置
	function resetForm() {
		$("#applyStatus").select2("val", "");
		$("#idType").select2("val", "");
		$("#idNum").val('');
		$("#custName").val('');
	}

	//提交
	function submitForm() {
		loading();
		$("#searchForm").submit();
	}
	//分配
	function allot() {
		var registerId = $("input[name='ids']:checked").val();
		if (registerId != null) {
			var allot = $("input[name='ids']:checked").attr("allotStatus");
			if (allot == "1") {
				var url = "${ctx}/credit/gedApplyRegister/toAllot?id=" + registerId;
				openJBox("gedApplyRegister-allot", url, "分配任务", 1000, 400);
			} else {
				alertx("此任务已分配");
			}
		} else {
			alertx("请选择需要操作的客户信息");
		}
	}
	//拒绝
	function refuseTask() {
		var registerId = $("input[name='ids']:checked").val();
		if (registerId != null) {
			var allot = $("input[name='ids']:checked").attr("allotStatus");
			if (allot == "1") {
				var ged = $("input[name='ids']:checked").attr("gedApplyStatus");
				if (ged == "04") {
					alertx("此申请已被拒绝");
				} else {
					$.ajax({
					type : "post",
					url : "${ctx}/credit/gedApplyRegister/refuseTask",
					data : {
						id : registerId
					},
					dataType : "json",
					success : function(data) {
						alertx(data.message, function() {
							page();
						});
					},
					error : function(msg) {
						alertx("系统出现问题，请查看后台信息");
					}
					});
				}
			} else {
				alertx("此任务已分配");
			}
		} else {
			alertx("请选择需要操作的客户信息");
		}
	}
	//流程轨迹
	function processTrack() {
		var registerId = $("input[name='ids']:checked").val();
		if (registerId == null) {
			alertx("请选择需要操作的客户信息");
		} else {
			var allot = $("input[name='ids']:checked").attr("allotStatus");
			if (allot == "1") {
				alertx("暂无流程信息！");
			} else {
				$.ajax({
				url : "${ctx}/credit/gedApplyRegister/toProcessTrack",
				data : {
					id : registerId
				},
				type : "post",
				dataType : "json",
				success : function(data) {
					if (data.status == "1") {
						var url = "${ctx}/credit/comprehensiveQuery/processTrack?applyRegisterVOId=" + data.registerVOId;
						openJBox("processTrackBox", url, "流程轨迹", $(window).width() - 100, $(window).height() - 100);
					} else {
						alertx("暂无流程信息！");
					}
				},
				error : function(msg) {
					alertx("系统出现问题，请查看后台信息");
				}
				});

			}
		}
		var opreateId = $('#jglCkTzdDetailJglCkTzdMxGrid').jqGrid('getGridParam', 'selrow');
		if (opreateId == null) {//需要勾选一条信息进行修改
			alertx("请选择一条要操作的信息");
		} else {
			var rowData = $('#jglCkTzdDetailJglCkTzdMxGrid').jqGrid('getRowData', opreateId);
			console.info(rowData);
			if (rowData.procInsId == null || "" == rowData.procInsId) {
				alertx("暂无流程信息！");
			} else {
				var url = "${ctx}/credit/comprehensiveQuery/processTrack?applyRegisterVOId=" + rowData.id;
				openJBox("processTrackBox", url, "流程轨迹", $(window).width() - 100, $(window).height() - 100);
			}
		}
	}

	function excelExport(){
		top.$.jBox.confirm("确认要导出申请数据吗？", "系统提示", function(v, h, f) {
			if (v == "allpage") {//导出当前查询结果的全部数据
				loading('正在导出，请稍等...');
				$("#searchForm").attr("action", "${ctx}/credit/gedApplyRegister/excelExport?exportFlag=allpage");
				$("#searchForm").submit();
				setTimeout("closeTip();", 4000);//关闭loading
			} else if (v == "onepage") {//导出当前查询结果当前页数据
				loading('正在导出，请稍等...');
				$("#searchForm").attr("action", "${ctx}/credit/gedApplyRegister/excelExport?exportFlag=onepage");
				$("#searchForm").submit();
				setTimeout("closeTip();", 3000);//关闭loading
			}
		}, {
			buttons : {
			"导出全部" : "allpage",
			"导出当页" : "onepage",
			"取消" : "cancel"
			}
		}, {
			buttonsFocus : 1
		});
		top.$('.jbox-body .jbox-icon').css('top', '55px');
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="gedApplyRegister" action="${ctx}/credit/gedApplyRegister/list" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<div class="filter">
						<table class="searchTable">
							<tr>
								<td class="ft_label">客户名称：</td>
								<td class="ft_content">
									<form:input path="custName" class="input-medium" />
								</td>
								<td class="ft_label">证件号：</td>
								<td class="ft_content" style="width: 240px;">
									<form:input path="idNum" htmlEscape="false" maxlength="18" class="input-medium" />
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="submitForm();" />
							&nbsp;
							<input id="btnReset" class="btn btn-primary" type="button" onclick="resetForm();" value="重置" />
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 class="tableTitle">列表信息</h3>
			<div class="ribbon">
				<ul class="layout">
					<li class="mcp_commit">
						<a id="" href="javascript:void(0);" onclick="allot();">
							<span>
								<b></b>
								分配
							</span>
						</a>
					</li>
					<li class="delete">
						<a id="" href="javascript:void(0);" onclick="refuseTask();">
							<span>
								<b></b>
								拒绝
							</span>
						</a>
					</li>
					<li class="mcp_change">
						<a id="" href="javascript:void(0);" onclick="processTrack();">
							<span>
								<b></b>
								流程轨迹
							</span>
						</a>
					</li>
					<li class="mcp_produce">
						<a id="" href="javascript:void(0);" onclick="excelExport();">
							<span>
								<b></b>
								导出
							</span>
						</a>
					</li>
				</ul>
			</div>
			<div id="tableDataId" style="max-height: 400px; overflow: auto;">
				<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
					<thead>
						<tr>
							<th style="width: 3%;">
								<input id="allCheck" name="allCheck" type="checkbox" />
							</th>
							<th style="width: 3%;">序号</th>
							<th style="width: 5%;">客户名称</th>
							<th style="width: 8%;">申请流水号</th>
							<th style="width: 8%;">证件类型</th>
							<th style="width: 10%;">证件号码</th>
							<th style="width: 8%;">移动电话</th>
							<th style="width: 8%;">客户来源</th>
							<th style="width: 8%;">所在城市</th>
							<th style="width: 8%;">申请日期</th>
							<th style="width: 8%;">客户分配状态</th>
							<th style="width: 8%;">客户申请状态</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="gedApplyRegister" varStatus="gedApplyRegisterList">
							<tr>
								<td>
									<input id="ids" name="ids" type="checkbox" value="${gedApplyRegister.id}" gedApplyStatus="${gedApplyRegister.gedApplyStatus}" allotStatus="${gedApplyRegister.allotStatus}" onclick="selectSingle('ids')" />
								</td>
								<td>${gedApplyRegisterList.count }</td>
								<td id="custName" class="title" title="${gedApplyRegister.custName}">${gedApplyRegister.custName}</td>
								<td id="applyId" class="title" title="${gedApplyRegister.applyId}">${gedApplyRegister.applyId}</td>
								<td id="idType" class="title" title="${fns:getDictLabel(gedApplyRegister.idType, 'CUSTOMER_P_ID_TYPE', '')}">${fns:getDictLabel(gedApplyRegister.idType, 'CUSTOMER_P_ID_TYPE', '')}</td>
								<td id="idNum" class="title" title="${gedApplyRegister.idNum}">${gedApplyRegister.idNum}</td>
								<td id="mobileNum" class="title" title="${gedApplyRegister.mobileNum}">${gedApplyRegister.mobileNum}</td>
								<td id="channelSourceType" class="title" title="${fns:getDictLabel(gedApplyRegister.channelSourceType, 'CHANNEL_SOURCE_TYPE', '')}">${fns:getDictLabel(gedApplyRegister.channelSourceType, 'CHANNEL_SOURCE_TYPE', '')}</td>
								<td id="locationCity" class="title" title="${gedApplyRegister.locationCity}">${gedApplyRegister.locationCity}</td>
								<td id="registerDate" class="title" title="<fmt:formatDate value='${gedApplyRegister.registerDate}' pattern='yyyy-MM-dd'/>">
									<fmt:formatDate value='${gedApplyRegister.registerDate}' pattern='yyyy-MM-dd' />
								</td>
								<td id="allotStatus" class="title" title="${fns:getDictLabel(gedApplyRegister.allotStatus, 'ALLOT_STATUS', '')}">${fns:getDictLabel(gedApplyRegister.allotStatus, 'ALLOT_STATUS', '')}</td>
								<td id="gedApplyStatus" class="title" title="${fns:getDictLabel(gedApplyRegister.gedApplyStatus, 'GED_APPLY_STATUS', '')}">${fns:getDictLabel(gedApplyRegister.gedApplyStatus, 'GED_APPLY_STATUS', '')}</td>
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