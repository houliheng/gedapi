<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<%--  <script src="${ctxStatic}/jqGrid/4.6/i18n/grid.locale-cn.js" type="text/javascript"></script>  --%>
<script src="${ctxStatic}/jqGrid/4.6/js/jquery.jqGrid.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/jqGrid/4.6/css/ui.jqgrid.css">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/jqGrid/4.6/css/default/jquery-ui-1.8.2.custom.css">
<script type="text/javascript">
	function getRepayPeriodStatus() {
		var data;
		$.ajax({
		url : "${ctx}/accounting/staRepayPlanStatus/getRepayPeriodStatus",
		type:"post",
		async : false,
		success : function(e) {
			if (e != null) {
				data = e;
			}
		}
		});
		return data;
	}
	$(document).ready(function() {
		$("#jglCkTzdDetailJglCkTzdMxGrid").jqGrid({
		url : "${ctx}/accounting/staRepayPlanStatus/getDataList",
		datatype : "json",
		mtype : 'POST',
		postData : {
			contractNo : "${contractNo}"
		},
		colNames : [ "期状态", "期数", "本息结清日期", "总结清日期", "本金", "利息", "违约金", "罚息", "服务费", "账户管理费", "营业外收入金额项", "还款日期", "应还罚息","退回" ],
		colModel : [ {
		name : "repayPeriodStatus",
		index : "repayPeriodStatus",
		editable : true,
		sortable : false,
		edittype : 'select',
		editrules : {
			required : true
		},
		editoptions : {
			value : getRepayPeriodStatus()
		}
		}, {
		name : "periodNum",
		index : "periodNum",
		sortable : false,
		align : 'center'
		}, {
		name : "capitalInterestRepayDate",
		index : "capitalInterestRepayDate",
		editable : true,
		sortable : false,
		search : false,
		editoptions : {
			dataInit : function(el) {
				$(el).click(function() {
					WdatePicker();
				})
			}
		}
		}, {
		name : "allRepayDate",
		index : "allRepayDate",
		editable : true,
		sortable : false,
		search : false,
		editoptions : {
			dataInit : function(el) {
				$(el).click(function() {
					WdatePicker();
				})
			}
		}
		}, {
		name : "factCapitalAmount",
		index : "factCapitalAmount",
		align : 'center',
		editable : true,
		sortable : false,
		editrules : {
		custom : true,
		custom_func : function(value, colname) {
			if ((!(/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(value))) || value == "")
				alertx(colname + "请输入数字");
			else
				return [ true, "" ];
		}
		}
		}, {
		name : "factInterestAmount",
		index : "factInterestAmount",
		align : 'center',
		editable : true,
		sortable : false,
		editrules : {
		custom : true,
		custom_func : function(value, colname) {
			if ((!(/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(value))) || value == "")
				alertx(colname + "请输入数字");
			else
				return [ true, "" ];
		}
		}
		}, {
		name : "factPenaltyAmount",
		index : "factPenaltyAmount",
		align : 'center',
		editable : true,
		sortable : false,
		editrules : {
		custom : true,
		custom_func : function(value, colname) {
			if ((!(/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(value))) || value == "")
				alertx(colname + "请输入数字");
			else
				return [ true, "" ];
		}
		}
		}, {
		name : "factFineAmount",
		index : "factFineAmount",
		align : 'center',
		editable : true,
		sortable : false,
		editrules : {
		custom : true,
		custom_func : function(value, colname) {
			if ((!(/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(value))) || value == "")
				alertx(colname + "请输入数字");
			else
				return [ true, "" ];
		}
		}
		}, {
		name : "factServiceFee",
		index : "factServiceFee",
		align : 'center',
		editable : true,
		sortable : false,
		editrules : {
		custom : true,
		custom_func : function(value, colname) {
			if ((!(/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(value))) || value == "")
				alertx(colname + "请输入数字");
			else
				return [ true, "" ];
		}
		}
		}, {
		name : "factMangementFee",
		index : "factMangementFee",
		align : 'center',
		editable : true,
		sortable : false,
		editrules : {
		custom : true,
		custom_func : function(value, colname) {
			if ((!(/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(value))) || value == "")
				alertx(colname + "请输入数字");
			else
				return [ true, "" ];
		}
		}
		}, {
		name : "factAddAmount",
		index : "factAddAmount",
		align : 'center',
		editable : true,
		sortable : false,
		editrules : {
		custom : true,
		custom_func : function(value, colname) {
			if ((!(/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(value))) || value == "")
				alertx(colname + "请输入数字");
			else
				return [ true, "" ];
		}
		}
		}, {
		name : "dataDt",
		index : "dataDt",
		sortable : false,
		align : 'center'
		}, {
		name : "fineAmount",
		index : "fineAmount",
		sortable : false,
		align : 'center'
		},{
		name : "backAmount",
		index : "backAmount",
		align : 'center',
		editable : true,
		sortable : false,
		editrules : {
		custom : true,
		custom_func : function(value, colname) {
			if ((!(/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(value))) || value == "")
				alertx(colname + "请输入数字");
			else
				return [ true, "" ];
		}
		}
		} ],
		viewrecords : true,
		height : "auto",
		autowidth : true,
		cellsubmit : 'clientArray',
		onSelectRow : editJglCkTzdMxRowData,
		beforeSelectRow : function(rowId, e) {
			if (rowId != $('#jglCkTzdDetailJglCkTzdMxGrid').jqGrid('getGridParam', 'selrow')) {
			} else {
				return false;
			}
			saveJglCkTzdMxRowData($('#jglCkTzdDetailJglCkTzdMxGrid').jqGrid('getGridParam', 'selrow'));
			return true;
		},
		caption : "列表信息"
		});
	});
	function editJglCkTzdMxRowData(rowId) {
		$('#jglCkTzdDetailJglCkTzdMxGrid').jqGrid("editRow", rowId, false);
	};

	function pickdates(id) {
		jQuery("#" + id + "_sdate", "#jglCkTzdDetailJglCkTzdMxGrid").datepicker({
			dateFormat : "yy-mm-dd"
		});
	}

	function saveJglCkTzdMxRowData(rowId) {
		if (rowId) {
			$('#jglCkTzdDetailJglCkTzdMxGrid').jqGrid("saveRow", rowId, false, 'clientArray');
		}
	};
	function saveJglCkTzd() {
		$("#btnButton").attr("disabled", true);
		var rowIds = $("#jglCkTzdDetailJglCkTzdMxGrid").jqGrid("getDataIDs");
		for (var i = 0; i < rowIds.length; i++) {
			saveJglCkTzdMxRowData(rowIds[i]);
		}
		var rowData = $("#jglCkTzdDetailJglCkTzdMxGrid").jqGrid("getRowData");
		saveJson("${ctx}/accounting/staRepayPlanStatus/save?contractNoTemp="+encodeURI(encodeURIComponent("${contractNo}")), JSON.stringify(removeDotProperty(rowData)), function(data) {
			if (data.status == 1) {
				alertx(data.message, function() {
					closeJBox();
				});
			} else {
				$("#btnButton").removeAttr("disabled");
				alertx(data.message);
			}
		});
	}

	function backOperate() {
		$.ajax({
		url : "${ctx}/accounting/staRepayPlanStatus/removeLock",
		type:"post",
		data : {
			contractNo : "${contractNo}"
		},
		async : false,
		dataType : "JSON",
		success : function(data) {
			if (data.status == 1) {
				closeJBox();
			} else {
				alertx(data.message);
			}
		}
		});
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="tableList" style="width:100%; overflow-x:auto;overflow-y:auto">
			<form:form id="jglCkTzdDetailForm" name="jglCkTzdDetailForm">
				<table id="jglCkTzdDetailJglCkTzdMxGrid"></table>
				<div style="text-align:center">
					<input id="btnButton" class="btn btn-primary" type="button" value="入账" onclick="saveJglCkTzd()" />
					<input id="btnBack" class="btn btn-primary" type="button" value="终止账务调整" onclick="backOperate()" />
				</div>
			</form:form>
		</div>
		<div id="repayPlanStatusDiv"  class="tableList" style="width:100%; overflow-x:auto;overflow-y:auto">
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>期状态</th>
						<th>期数</th>
						<th>本期本金</th>
						<th>本期利息</th>
						<th>违约金</th>
						<th>罚息</th>
						<th>服务费</th>
						<th>账户管理费</th>
						<th>营业外收入</th>
						<th>违约金减免</th>
						<th>罚息减免</th>
						<th>退回</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${repayPlanStatus}" var="repayPlanStatus">
						<tr>
							<td id="repayPeriodStatus" class="title" title="${fns:getDictLabel(repayPlanStatus.repayPeriodStatus, 'PERIOD_STATE', '')}">${fns:getDictLabel(repayPlanStatus.repayPeriodStatus, 'PERIOD_STATE', '')}</td>
							<td id="periodNum" class="title" title="${repayPlanStatus.periodNum}">${repayPlanStatus.periodNum}</td>
							<td id="factCapitalAmount" class="title" title="${repayPlanStatus.factCapitalAmount}">${repayPlanStatus.factCapitalAmount}</td>
							<td id="factInterestAmount" class="factInterestAmount" title="${repayPlanStatus.factInterestAmount}">${repayPlanStatus.factInterestAmount}</td>
							<td id="factPenaltyAmount" class="title" title="${repayPlanStatus.factPenaltyAmount}">${repayPlanStatus.factPenaltyAmount}</td>
							<td id="factFineAmount" class="title" title="${repayPlanStatus.factFineAmount}">${repayPlanStatus.factFineAmount}</td>
							<td id="factServiceFee" class="title" title="${repayPlanStatus.factServiceFee}">${repayPlanStatus.factServiceFee}</td>
							<td id="factMangementFee" class="title" title="${repayPlanStatus.factMangementFee}">${repayPlanStatus.factMangementFee}</td>
							<td id="factAddAmount" class="title" title="${repayPlanStatus.factAddAmount}">${repayPlanStatus.factAddAmount}</td>
							<td id="penaltyExemptAmount" class="title" title="${repayPlanStatus.penaltyExemptAmount}">${repayPlanStatus.penaltyExemptAmount}</td>
							<td id="fineEepmtAmount" class="title" title="${repayPlanStatus.fineEepmtAmount}">${repayPlanStatus.fineEepmtAmount}</td>
							<td id="backAmount" class="title" title="${repayPlanStatus.backAmount}">${repayPlanStatus.backAmount}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
</body>
</html>