<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript">
	$(document).ready(function() {
		$("#jglCkTzdDetailJglCkTzdMxGrid").jqGrid({
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
		sortable : false
		}, {
		name : "repayCapitalAmount",
		editable : true,
		sortable : false,
		defaultValue : 0,
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
		sortable : false,
		align : 'center'
		} ],
		rownumbers : true,
		rowNum:-1,
		viewrecords : true,
		height : "auto",
		gridview : true,
		autowidth : true,
		autoencode : true,
		cellsubmit : 'clientArray',
		caption : "列表信息"
		});

	});

	function editJglCkTzdMxRowData(rowId) {
		$('#jglCkTzdDetailJglCkTzdMxGrid').jqGrid("editRow", rowId, false);
	};

	function saveJglCkTzdMxRowData(rowId) {
		if (rowId) {
			$('#jglCkTzdDetailJglCkTzdMxGrid').jqGrid("saveRow", rowId, false, 'clientArray');
		}
	};
	function saveJglCkTzd() {
		confirmx("是否确定重新入账", function() {
			$("#btnButton").attr("disabled", true);
			loading();
			var rowIds = $("#jglCkTzdDetailJglCkTzdMxGrid").jqGrid("getDataIDs");
			for (var i = 0; i < rowIds.length; i++) {
				saveJglCkTzdMxRowData(rowIds[i]);
			}
			var contractNoClick = $("#contractNoClick").val();
			var deductApplyNoClick = $("#deductApplyNoClick").val();
			var streamTimeStrClick = $("#streamTimeStrClick").val();
			var amountSumStr = $("#amountSumStr").val();
			var rowData = $("#jglCkTzdDetailJglCkTzdMxGrid").jqGrid("getRowData");
			saveJson("${ctx}/accounting/deductApply/matchAgain?amountSumStr=" + amountSumStr + "&contractNoClick=" +  encodeURI(encodeURIComponent(contractNoClick)) + "&deductApplyNoClick=" + deductApplyNoClick + "&streamTimeStrClick=" + streamTimeStrClick, JSON.stringify(removeDotProperty(rowData)), function(data) {
				if (data) {
					closeTip();
					if (data.status == 1) {
						alertx(data.message, function() {
							closePage();
						});
					} else {
						$("#btnButton").removeAttr("disabled");
						alertx(data.message);
					}
				}
			});
		});
	}

	function addBox() {
		var readOnlyId = $('#readOnlyFrid').jqGrid('getGridParam', 'selrow');
		if (readOnlyId != null) {
			var readOnlyRowData = $('#readOnlyFrid').jqGrid('getRowData', readOnlyId);
			var deductApplyNo = readOnlyRowData.deductApplyNo;
			$.loadDiv("deductApplyDetailForUpdateTempId", "${ctx}/accounting/deductApply/addBox", {
			deductApplyNo : deductApplyNo
			}, "post");
			$("#deductApplyDetailForUpdateTempId").show();
		} else {
			alertx("请选择要操作的数据!");
		}
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="tableList">
			<table id="jglCkTzdDetailJglCkTzdMxGrid"></table>
			<div style="text-align:center">
				<input id="btnButton" class="btn btn-primary" type="button" value="匹配确认" onclick="saveJglCkTzd()" />
				<input id="btnBack" class="btn btn-primary" type="button" value="关闭" onclick="closePage()" />
				<input type="button" class="btn btn-primary" value="流水拆分" onclick="addBox();" />
			</div>
		</div>
	</div>
</body>
</html>