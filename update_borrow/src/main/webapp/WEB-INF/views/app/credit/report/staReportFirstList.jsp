<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>统计报表2管理</title>
<meta name="decorator" content="default" />
<script src="${ctxStatic}/jqGrid/4.6/i18n/grid.locale-cn.js" type="text/javascript"></script>
<script src="${ctxStatic}/jqGrid/4.6/js/jquery.jqGrid.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/jqGrid/4.6/css/ui.jqgrid.css">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/jqGrid/4.6/css/default/jquery-ui-1.8.2.custom.css">
<style type="text/css"></style>
<script type="text/javascript">
	$(document).ready(function() {
		$("#reportSecondTable").jqGrid({
		url : "${ctx}/credit/report/listfirstReport",
		datatype : "json",
		mtype : 'POST',
		colNames : [ "机构名称", "月份", "平均借款额度", "申请额度", "放款总金额", "服务费总额", "企业个数", "进件量" ],
		colModel : [ {
		name : "orgName",
		hidden : false,
		sortable : false,
		align : 'center'
		}, {
		name : "dataMonth",
		hidden : false,
		sortable : false,
		align : 'center'
		}, {
		name : "aveContractAmount",
		hidden : false,
		sortable : false,
		align : 'center'
		}, {
		name : "applyAmount",
		hidden : false,
		sortable : false,
		align : 'center'
		}, {
		name : "loanAmount",
		hidden : false,
		sortable : false,
		align : 'center'
		}, {
		name : "serviceFee",
		hidden : false,
		sortable : false,
		align : 'center'
		}, {
		name : "companyAmount",
		hidden : false,
		sortable : false,
		align : 'center'
		}, {
		name : "registerAmount",
		hidden : false,
		sortable : false,
		align : 'center'
		} ],
		rownumbers : true,
		viewrecords : true,
		height : "auto",
		gridview : true,
		autowidth : true,
		autoScroll : true,
		footerrow : true,
		autoencode : true,
		rowNum : -1,
		caption : "列表信息",
		userDataOnFooter : true,
		gridComplete : function() {
			var aveContractAmountSum = $(this).getCol("aveContractAmount", false, "sum");
			var applyAmountSum = $(this).getCol("applyAmount", false, "sum");
			var loanAmountSum = $(this).getCol("loanAmount", false, "sum");
			var serviceFeeSum = $(this).getCol("serviceFee", false, "sum");
			var companyAmountSum = $(this).getCol("companyAmount", false, "sum");
			var registerAmountSum = $(this).getCol("registerAmount", false, "sum");
			$(this).footerData("set", {
			"orgName" : "总计",
			"aveContractAmount" : aveContractAmountSum,
			"applyAmount" : applyAmountSum,
			"loanAmount" : loanAmountSum,
			"serviceFee" : serviceFeeSum,
			"companyAmount" : companyAmountSum,
			"registerAmount" : registerAmountSum
			});
		}
		});
		$("#btnSubmit").on('click', function() {
			var formJson = $("#searchForm").serializeJson();
			$("#reportSecondTable").jqGrid('setGridParam', {
			datatype : 'json',
			postData : formJson,
			}).trigger("reloadGrid");
		});
	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
	function fromReset() {
		$("#dataMonthStart").val('');
		$("#dataMonthEnd").val('');
		$("#company").select2("val", "");
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="staReportSecond" action="${ctx}/credit/report/" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<div class="filter">
						<table class="searchTable">
							<tr>
								<td class="ft_label">月份：</td>
								<td class="ft_content">
									<nobr>
										<form:input path="dataMonthStart" type="text" maxlength="50" class="input-small Wdate" value="${staReportSecond.dataMonthStart}" onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy-MM',maxDate:new Date()});" />
										-
										<form:input path="dataMonthEnd" type="text" maxlength="50" class="input-small Wdate" value="${staReportSecond.dataMonthEnd}" onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy-MM',maxDate:new Date()});" />
									</nobr>
								</td>
								<td class="ft_label">机构：</td>
								<td class="ft_content">
									<form:select path="company" class="input-medium">
										<form:option value="" class="class1" label="请选择" />
										<form:options items="${list}" itemLabel="orgName" itemValue="orgId" htmlEscape="false" />
									</form:select>
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" />
							<input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="return fromReset();" />
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<div>
			<table id="reportSecondTable"></table>
		</div>
	</div>
</body>
</html>