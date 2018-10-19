<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript">
	$(document).ready(function() {
		$("#readOnlyFrid").jqGrid({
		url : "${ctx}/accounting/deductApply/getData",
		datatype : "json",
		mtype : 'POST',
		postData : {
		contractNo : "${contractNoClick}",
		streamTimeStr : "${streamTimeStrClick}"
		},
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
		sortable : false,
		align : 'center'
		}, {
		name : "streamNo",
		sortable : false,
		align : 'center'
		}, {
		name : "repayCapitalAmount",
		sortable : false,
		align : 'center'
		}, {
		name : "repayInterestAmount",
		sortable : false,
		align : 'center'
		}, {
		name : "repayManagementFee",
		sortable : false,
		align : 'center'
		}, {
		name : "repayServiceFee",
		sortable : false,
		align : 'center'
		}, {
		name : "repayFineAmount",
		sortable : false,
		align : 'center'
		}, {
		name : "repayPenaltyAmount",
		sortable : false,
		align : 'center'
		}, {
		name : "repayAddAmount",
		sortable : false,
		align : 'center'
		}, {
		name : "repayBreakAmount",
		sortable : false,
		align : 'center'
		}, {
		name : "backAmount",
		sortable : false,
		align : 'center'
		} ],
		rownumbers : true,
		viewrecords : true,
		rowNum:-1,
		height : "auto",
		gridview : true,
		autowidth : true,
		autoScroll : false,
		autoencode : true,
		caption : "列表信息"
		});
	});
</script>
</head>
<body>
	<div class="wrapper">
		<div class="tableList">
			<table id="readOnlyFrid"></table>
		</div>
	</div>
</body>
</html>