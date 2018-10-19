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
		url : "${ctx}/accounting/staRepayPlanStatus/getDataForm",
		datatype : "json",
		mtype : 'POST',
		postData : {
			contractNo :"${contractNo}"
		},
		colNames : [ "期状态", "期数", "本息结清日期", "总结清日期", "本金", "利息", "违约金", "罚息", "服务费", "账户管理费", "营业外收入金额项", "还款日期", "应还罚息", "退回" ],
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
		sortable : false,
		align : 'center'
		}, {
		name : "factInterestAmount",
		index : "factInterestAmount",
		sortable : false,
		align : 'center'
		}, {
		name : "factPenaltyAmount",
		index : "factPenaltyAmount",
		sortable : false,
		align : 'center'
		}, {
		name : "factFineAmount",
		index : "factFineAmount",
		sortable : false,
		align : 'center'
		}, {
		name : "factServiceFee",
		index : "factServiceFee",
		sortable : false,
		align : 'center'
		}, {
		name : "factMangementFee",
		index : "factMangementFee",
		sortable : false,
		align : 'center'
		}, {
		name : "factAddAmount",
		index : "factAddAmount",
		sortable : false,
		align : 'center'
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
		}, {
		name : "backAmount",
		index : "backAmount",
		sortable : false,
		align : 'center'
		} ],
		rownumbers : false,
		viewrecords : true,
		height : "auto",
		autowidth : true,
		shrinkToFit:true,
		gridview : true,
		autoencode : true,
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
		loading();
		$("#btnButton").attr("disabled", true);
		var rowIds = $("#jglCkTzdDetailJglCkTzdMxGrid").jqGrid("getDataIDs");
		for (var i = 0; i < rowIds.length; i++) {
			saveJglCkTzdMxRowData(rowIds[i]);
		}
		var rowData = $("#jglCkTzdDetailJglCkTzdMxGrid").jqGrid("getRowData");
		saveJson("${ctx}/accounting/staRepayPlanStatus/savePZ?contractNoTemp="+encodeURI(encodeURIComponent("${contractNo}")), JSON.stringify(removeDotProperty(rowData)), function(data) {
			closeTip();
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
		data:{
			contractNo:"${contractNo}"
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
</body>
</html>