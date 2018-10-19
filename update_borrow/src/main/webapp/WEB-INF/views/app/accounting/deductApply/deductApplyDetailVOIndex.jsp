<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>还款明细跳转页</title>
<meta name="decorator" content="default" />
<!-- <script src="${ctxStatic}/jqGrid/4.6/i18n/grid.locale-cn.js" type="text/javascript"></script> -->
<script src="${ctxStatic}/jqGrid/4.6/js/jquery.jqGrid.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/jqGrid/4.6/css/ui.jqgrid.css">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/jqGrid/4.6/css/default/jquery-ui-1.8.2.custom.css">
<style type="text/css">
.tableList table td ,.tableList th{
padding:0 2px;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
	});

	function loadJqgrid() {
		$("#qwertyu").hide();
		$("#deductApplyDetailForUpdateTempId").hide();
		$.loadDiv("deductApplyDetailForUpdateId", "${ctx}/accounting/deductApply/detailForm", {
			contractNo:"${contractNoClick}",
			streamNo:"${streamNoClick}",
		streamTimeStr : "${streamTimeStrClick}",
		deductApplyNo : "${deductApplyNoClick}",
		pageFlag : "update"
		}, "POST",null);

		$.loadDiv("deductApplyDetailReadOnlyId", "${ctx}/accounting/deductApply/detailForm", {
			contractNo:"${contractNoClick}",
			streamNo:"${streamNoClick}",
		streamTimeStr : "${streamTimeStrClick}",
		pageFlag : "readOnly"
		}, "POST",null);
	}

	function closePage() {
		$.ajax({
		url : "${ctx}/accounting/deductApply/deleteLock",
		type:"post",
		data:{
			contractNo:'${contractNoClick}'
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
	<a id="qwertyu" href="javaScript:void(0)" class="btn btn-primary" onclick="loadJqgrid()">开始拆分</a>
	<input type="hidden" id="streamTimeStrClick" value="${streamTimeStrClick}" />
	<input type="hidden" id="streamNoClick" value="${streamNoClick}" />
	<input type="hidden" id="deductApplyNoClick" value="${deductApplyNoClick}" />
	<input type="hidden" id="contractNoClick" value="${contractNoClick}" />
	<input type="hidden" id="amountSumStr" value="${amountSumStr}" />
	<div id="deductApplyDetailReadOnlyId"></div>
	<div id="deductApplyDetailForUpdateTempId"></div>
	<div id="deductApplyDetailForUpdateId"></div>
</body>
</html>