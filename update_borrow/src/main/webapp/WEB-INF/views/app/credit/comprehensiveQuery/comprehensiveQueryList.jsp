<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>综合查询</title>
<meta name="decorator" content="default" />
<script src="${ctxStatic}/jqGrid/4.6/i18n/grid.locale-cn.js" type="text/javascript"></script>
<script src="${ctxStatic}/jqGrid/4.6/js/jquery.jqGrid.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/jqGrid/4.6/css/ui.jqgrid.css">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/jqGrid/4.6/css/default/jquery-ui-1.8.2.custom.css">
<script type="text/javascript">
	$(document).ready(function() {
		closeTip();
		$("#jglCkTzdDetailJglCkTzdMxGrid").jqGrid({
		url : "${ctx}/credit/comprehensiveQuery/getData",
		postData : {
			flag : "${flag}"
		},
		datatype : "json",
		mtype : 'POST',
		colNames : [ "流程ID", "编号", "客户名称", "申请编号", "证件类型", "证件号", "申请金额（元）", "登记日期", "登机门店" ],
		colModel : [ {
			name : "procInsId",
			hidden : true,
			sortable : false,
			align : 'center'
			}, {
			name : "id",
			hidden : true,
			sortable : false,
			align : 'center'
			}, {
			name : "custName",
			index : "custName",
			sortable : false,
			align : 'center'
			}, {
			name : "applyNo",
			index : "applyNo",
			sortable : false,
			align : 'center'
			}, {
			name : "idType",
			index : "idType",
			sortable : false,
			align : 'center'
			}, {
			name : "idNum",
			index : "idNum",
			sortable : false,
			align : 'center'
			}, {
			name : "applyAmount",
			index : "applyAmount",
			sortable : false,
			align : 'center'
			}, {
			name : "registerDate",
			index : "registerDate",
			sortable : false,
			align : 'center'
			}, {
			name : "company.name",
			index : "company.name",
			sortable : false,
			align : 'center'
		} ],
		viewrecords : true,
		subGrid : true,
		subGridUrl : "${ctx}/credit/comprehensiveQuery/getSubData",
		subGridModel : [ {
			name : [ "客户名称", "申请编号", "证件类型", "证件号", "合同金额（元）", "登记日期", "登机门店" ],
			width : [ '100', '100', '100', '100', '100', '100' ,'100'],
			mapping : [ 'subCustName', 'subApplyNo', 'subIdType', 'subIdNum', 'subContractAmount', 'subRegisterDate', 'companyName' ]
		} ],
		pager : "gqgridPagerId",
		pagination : true,
		page : 1,
		rowNum : 20,
		rowList : [ 10, 20, 30 ],
		jsonReader : {
			root : "dataList",
			page : "currPage",
			total : "totalPages",
			records : "totalRecount",
			repeatitems : false,
			subgrid : {
				root : "subDataList",
				repeatitems : false
			}
		},
		height : "auto",
		autowidth : true,
		cellsubmit : 'clientArray'
		});

		$("#btnSubmit").on('click', function() {
			var formJson = $("#searchForm").serializeJson();
			console.info(formJson);
			$("#jglCkTzdDetailJglCkTzdMxGrid").jqGrid('setGridParam', {
			datatype : 'json',
			postData : formJson,
			page : 1,
			expandOnLoad : true
			}).trigger("reloadGrid");
		});

	});
	//重置
	function resetForm() {
		$("#custName").val('');
		$("#applyStatus").select2("val", "");
		$("#idType").select2("val", "");
		$("#idNum").val('');
		$("#registerName").val('');
		//重置归属结构
		$("#companyId").val('');
		$("#companyName").val('');
		$("#applyNo").val('');
	}

	//详细
	function detail() {
		var opreateId = $('#jglCkTzdDetailJglCkTzdMxGrid').jqGrid('getGridParam', 'selrow');
		if (opreateId == null) {//需要勾选一条信息进行修改
			alertx("请选择一条综合查询信息");
		} else {
			var rowData = $('#jglCkTzdDetailJglCkTzdMxGrid').jqGrid('getRowData', opreateId);
			var url1 = "${ctx}/credit/comprehensiveQuery/form?applyRegisterVOId=" + rowData.id;
			$.post(url1, null, function(data1) {
				if (data1) {
					if (data1.flag == 'success') {
						var url2 = "${ctx}/credit/comprehensiveQuery/doBusiness/" + data1.procDefId + "/" + data1.execId + "/" + data1.taskDefKey + "?taskId=" + data1.taskId;
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
								var title = "综合查询详细信息";
								var newUrl = "${ctx}" + location + "?procDefId=" + data1.procDefId + "&taskDefKey=" + data1.taskDefKey + "&applyNo=" + data1.applyNo + "&execId=" + data1.execId + "&status=" + flag + "&title=" + title + "&taskId=" + data1.taskId + "&procInstId=" + data1.procInstId + "&headUrl=/credit/comprehensiveQuery/list";
								var windowWidth = window.document.body.offsetWidth - 50;
								var windowHeight = window.document.body.offsetHeight - 50;
								goToPage(newUrl, 'queryListSkipId');
							}
						},
						error : function(msg) {
							alertx("未能查看，请查看后台信息");
						}
						});
					} else if (data1.flag == 'notask') {
						$.post("${ctx}/credit/comprehensiveQuery/showDown?applyRegisterVOId=" + rowData.id, function(data) {
							if (data) {
								var procDefId = data.map.PROCESSDEFINITIONID;
								var execId = data.map.EXECUTIONID;
								var taskDefKey = data.map.TASKDEFINITIONKEY;
								var id = data.map.applyNo;
								var title = data.map.custName;
								var taskId = data.map.id;
								var procInstId = data.map.procInsId;
								showInfoDown(procDefId, execId, taskDefKey, id, title, taskId, procInstId);
							} else {
								alertx("未能查看已办流程信息，请查看后台信息");
							}
						});
					} else {
						alertx("获取流程参数失败，请联系管理员！");
					}
				} else {
					alertx("获取流程参数失败，请联系管理员！");
				}
			});
		}
	}
	function showInfoDown(procDefId, execId, taskDefKey, id, title, taskId, procInstId) {
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

	//流程轨迹
	function processTrack() {
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

	//流程图
	function tracePhoto() {
		var opreateId = $('#jglCkTzdDetailJglCkTzdMxGrid').jqGrid('getGridParam', 'selrow');
		if (opreateId == null) {//需要勾选一条信息进行修改
			alertx("请选择一条要操作的信息");
		} else {
			var rowData = $('#jglCkTzdDetailJglCkTzdMxGrid').jqGrid('getRowData', opreateId);
			if (rowData.procInsId == null || "" == rowData.procInsId) {
				alertx("暂无流程信息！");
			} else {
				var url = "${ctx}/act/model/tracePhoto?procInsId=" + rowData.procInsId;
				openJBox("tracePhotoBox", url, "流程图", $(window).width() - 100, $(window).height() - 100);
			}
		}

	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon filter">
				<form:form id="searchForm" modelAttribute="applyRegisterVO" action="${ctx}/credit/comprehensiveQuery/list" method="post" class="breadcrumb form-search">
					<a id="queryListSkipId" href="javascript:void(0)"></a>
					<div class="filter">
						<table class="searchTable" width="100%">
							<tr>
								<td align="right">
									<label>客户名称：</label>
								</td>
								<td>
									<form:input path="custName" htmlEscape="false" maxlength="20" class="input-medium" />
								</td>
								<td align="right">
									<label>证件类型：</label>
								</td>
								<td>
									<form:select id="idType" name="idType" path="idType" class="input-medium" value="${applyRegister.idType}" cssStyle="width:164px;">
										<form:option value="" label="" />
										<form:options items="${fns:getDictList('CUSTOMER_P_ID_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
								</td>
								<td align="right">
									<label>证件号：</label>
								</td>
								<td>
									<form:input path="idNum" htmlEscape="false" maxlength="18" class="input-medium" />
								</td>
							</tr>
							<tr>
								<td align="right">
									<label>登记人：</label>
								</td>
								<td>
									<form:input path="registerName" htmlEscape="false" maxlength="20" class="input-medium" />
								</td>
								<td align="right">
									<label>状态：</label>
								</td>
								<td>
									<form:select path="applyStatus" class="input-medium">
										<form:option value="" label="" />
										<form:options items="${fns:getDictList('APPLY_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
								</td>
								<td align="right">
									<label>登记门店：</label>
								</td>
								<td>
									<sys:usertreeselect id="company" name="orgId" value="" labelName="company.name" labelValue="" title="机构" url="/sys/office/treeData?type=2&companyId=${companyId }" cssClass="input-small" notAllowSelectParent="false" allowClear="true" />
								</td>
							</tr>
							<tr>
								<td align="right">
									<label>申请编号：</label>
								</td>
								<td>
									<form:input path="applyNo" htmlEscape="false" class="input-medium" />
								</td>
								<td align="right">
									<label>合同编号：</label>
								</td>
								<td>
									<form:input path="contractNo" htmlEscape="false" class="input-medium" />
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" />
							&nbsp;
							<input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="resetForm();" />
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div class="ribbon">
				<ul class="layout">
					<li class="edit">
						<a id="detail" href="javascript:void(0);" onclick="detail();">
							<span>
								<b></b>
								详情
							</span>
						</a>
						<a id="skipId"></a>
						<a id="todoBusinessListSkipId"></a>
					</li>
					<li class="mcp_pic">
						<a id="" href="javascript:void(0);" onclick="tracePhoto();">
							<span>
								<b></b>
								流程图
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
				</ul>
			</div>
		</div>
		<div style="height:500px; width:100%; overflow-x:hidden;overflow-y:auto">
			<table id="jglCkTzdDetailJglCkTzdMxGrid"></table>
			<div id="gqgridPagerId"></div>
		</div>
	</div>
</body>
</html>