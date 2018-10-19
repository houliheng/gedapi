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
		url : "${ctx}/credit/report/listSecondReport",
		datatype : "json",
		mtype : 'POST',
		colNames : [ "客户姓名", "申请编号", "大区", "区域", "分公司", "大区批复时间", "批复结果", "总部批复时间", "批复结果" ],
		colModel : [ {
		name : "custName",
		hidden : false,
		sortable : false,
		align : 'center'
		}, {
		name : "applyNo",
		hidden : false,
		sortable : false,
		align : 'center'
		}, {
		name : "orgLevel2",
		hidden : false,
		sortable : false,
		align : 'center'
		}, {
		name : "orgLevel3",
		hidden : false,
		sortable : false,
		align : 'center'
		}, {
		name : "orgLevel4",
		hidden : false,
		sortable : false,
		align : 'center'
		}, {
		name : "appTimeDq",
		hidden : false,
		sortable : false,
		align : 'center'
		}, {
		name : "appStatusDq",
		hidden : false,
		sortable : false,
		align : 'center'
		}, {
		name : "appTimeZb",
		hidden : false,
		sortable : false,
		align : 'center'
		}, {
		name : "appStatusZb",
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
		userDataOnFooter:true,
		gridComplete:function(){
			var a = $(this).footerData("get",{"appStatusDq":"aaa"});
			if(a != null){
			var aa = "通过率："+a.appStatusDq;
			$(this).footerData("set",{"appStatusDq":aa});
			}
			var b = $(this).footerData("get",{"appStatusZb":"bbb"});
			if(b != null){
			var bb = "通过率："+b.appStatusZb;
			$(this).footerData("set",{"appStatusZb":bb});
			}
			var c = $(this).footerData("get",{"appTimeDq":"ccc"});
			if(c != null){
			var cc = "平均耗时："+c.appTimeDq;
			$(this).footerData("set",{"appTimeDq":cc});
			}
			var d = $(this).footerData("get",{"appTimeZb":"ddd"});
			if(d != null){
			var dd = "平均耗时："+d.appTimeZb;
			$(this).footerData("set",{"appTimeZb":dd});
			}
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
		//$("#taskDefKey").select2("val", "");
		$("#companyId").val('');
		$("#companyName").val('');
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
									<%-- <input id="orgName" name="orgName" type="text" maxlength="50" class="input-medium" value="${staReportSecond.orgName }" /> --%>
									<sys:usertreeselect id="company" name="companyName" value="${staReportSecond.company.id}" labelName="company.name" labelValue="${staReportSecond.company.name}" title="所属机构" url="/sys/office/treeData?type=1" cssClass="input-medium" allowClear="true" />
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" />
							&nbsp;
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