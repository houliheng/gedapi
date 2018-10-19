<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript">
	$(document).ready(function() {
		$("#tempGridId").jqGrid({
		datatype : "local",
		colNames : [ "计划扣款日", "大区", "区域", "分公司", "划扣申请序列号", "合同号", "期数", "流水号", "入账本金", "入账利息", "入账账户管理费", "入账服务费", "罚息", "违约金", "营业外收入", "提前还款费用", "退回" ],
		colModel : [ {
		name : "repayDate",
		hidden : true,
		sortable : false,
		align : 'center'
		}, {
		name : "orgLevel2Id",
		hidden : true,
		sortable : false,
		defaultValue : "",
		align : 'center'
		}, {
		name : "orgLevel3Id",
		hidden : true,
		sortable : false,
		align : 'center'
		}, {
		name : "orgLevel4Id",
		hidden : true,
		sortable : false,
		align : 'center'
		}, {
		name : "deductApplyNo",
		hidden : true,
		sortable : false,
		align : 'center'
		}, {
		name : "contractNo",
		hidden : true,
		sortable : false,
		align : 'center'
		}, {
		name : "periodNum",
		width : 40,
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
		name : "streamNo",
		width : 90,
		sortable : false
		}, {
		name : "repayCapitalAmount",
		width : 90,
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
		name : "repayInterestAmount",
		width : 90,
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
		name : "repayManagementFee",
		width : 90,
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
		name : "repayServiceFee",
		width : 90,
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
		name : "repayFineAmount",
		width : 90,
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
		name : "repayPenaltyAmount",
		width : 90,
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
		name : "repayAddAmount",
		width : 90,
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
		name : "repayBreakAmount",
		width : 90,
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
		name : "backAmount",
		width : 90,
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
		rowNum:-1,
		height : "auto",
		gridview : true,
		autoScroll : false,
		shrinkToFit : false,
		autoencode : true,
		cellsubmit : 'clientArray',
		onSelectRow : editTempGridRowData,
		caption : "列表信息"
		});

		$("#dedata").click(function() {
			var gr = $("#tempGridId").jqGrid('getGridParam', 'selrow');
			if (gr != null)
				jQuery("#tempGridId").jqGrid('delRowData', gr, {
					reloadAfterSubmit : false
				});
			else
				alertx("请选择要删除的数据!");
		});

	});

	function add(tableId) {
		var readOnlyId = $('#readOnlyFrid').jqGrid('getGridParam', 'selrow');
		var readOnlyRowData = $('#readOnlyFrid').jqGrid('getRowData', readOnlyId);
		var newRow = {
		repayDate : readOnlyRowData.repayDate,
		orgLevel2Id : readOnlyRowData.orgLevel2Id,
		orgLevel3Id : readOnlyRowData.orgLevel3Id,
		orgLevel4Id : readOnlyRowData.orgLevel4Id,
		deductApplyNo : readOnlyRowData.deductApplyNo,
		contractNo : readOnlyRowData.contractNo,
		periodNum : readOnlyRowData.periodNum,
		streamNo : readOnlyRowData.streamNo,
		repayCapitalAmount : 0,
		repayInterestAmount : 0,
		repayManagementFee : 0,
		repayServiceFee : 0,
		repayFineAmount : 0,
		repayPenaltyAmount : 0,
		repayAddAmount : 0,
		repayBreakAmount : 0,
		backAmount : 0
		};
		var ids = $('#tempGridId').jqGrid("getDataIDs");
		if (ids == null || ids == '') {
			rowid = 0;
		} else {
			var rowid = Math.max.apply(Math, ids);
		}
		var newrowid = (rowid + 1);
		$('#tempGridId').setGridParam({
			cellEidt : false
		});
		$('#tempGridId').jqGrid("addRowData", newrowid, newRow, "last");
		editTempGridRowData(newrowid);
	};
	function editTempGridRowData(rowId) {
		$('#tempGridId').jqGrid("editRow", rowId, false);
	};

	function saveTempGridRowData(rowId) {
		if (rowId) {
			$('#tempGridId').jqGrid("saveRow", rowId, false, 'clientArray');
		}
	};

	function saveAllTempGridRowData() {
		confirmx("是否确定提交", function() {
			var partAmount = 0;
			var streamNoAmount = '${streamNoAmount}';
			var rowIds = $("#tempGridId").jqGrid("getDataIDs");
			for (var i = 0; i < rowIds.length; i++) {
				saveTempGridRowData(rowIds[i]);
				var rowData = $('#tempGridId').jqGrid('getRowData', rowIds[i]);
				partAmount = Number(partAmount) + Number(rowData.repayCapitalAmount) + Number(rowData.repayInterestAmount) + Number(rowData.repayManagementFee) + Number(rowData.repayServiceFee) + Number(rowData.repayFineAmount) + Number(rowData.repayPenaltyAmount) + Number(rowData.repayAddAmount) + Number(rowData.repayBreakAmount) + Number(rowData.backAmount);
			}
			if (Number(streamNoAmount).toFixed(2) == Number(partAmount).toFixed(2)) {
				var rowIds = $("#tempGridId").jqGrid("getDataIDs");
				var ids = $("#jglCkTzdDetailJglCkTzdMxGrid").jqGrid("getDataIDs");
				if (ids == null || ids == '') {
					rowid = 0;
				} else {
					var rowid = Math.max.apply(Math, ids);
				}
				var newrowid = (rowid + 1);
				for (var i = 0; i < rowIds.length; i++) {
					newRow = $('#tempGridId').jqGrid('getRowData', rowIds[i]);
					$('#jglCkTzdDetailJglCkTzdMxGrid').jqGrid("addRowData", newrowid, newRow, "last");
				}
				$("#deductApplyDetailForUpdateTempId").hide();
			} else {
				alertx("金额不符:总金额为:" + Number(streamNoAmount).toFixed(2) + ",已拆分金额为:" + Number(partAmount).toFixed(2));
			}

		});
	}

	function hideDiv() {
		$("#deductApplyDetailForUpdateTempId").hide();
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="tableList">
			<table id="tempGridId"></table>
			<div style="text-align:center">
				<input class="btn btn-primary" type="button" value="拆分确认" onclick="saveAllTempGridRowData()" />
				<input class="btn btn-primary" type="button" value="拆分中止" onclick="hideDiv();" />
				<input class="btn btn-primary" type="button" value="增加行" onclick="add();" />
				<input class="btn btn-primary" type="button" value="删除行" id="dedata" />
			</div>
		</div>
	</div>
</body>
</html>