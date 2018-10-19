<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>阶梯利率管理</title>
<script src="${ctxStatic}/jqGrid/4.6/js/jquery.jqGrid.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/jqGrid/4.6/css/ui.jqgrid.css">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/jqGrid/4.6/css/default/jquery-ui-1.8.2.custom.css">
<meta name="decorator" content="default"/>
<script type="text/javascript">
	$(document).ready(function() {
		var ratebase = "${ratebase}";
		$("#rateContent").jqGrid({
		url : "${ctx}/credit/rateInterest/getData",
		datatype : "json",
		mtype : 'POST',
		colNames : [ "期限值", "月利率（%）"],
		colModel : [
				   {
					 name : "periodValue",
					 align : 'center'
					},{
					 name : "rateInterest",
					 align : 'center',
					 editable : true,
					 editrules : 
					{
					 custom : true,
					 custom_func : function(value, colname) {
					 if ((value !="")&& (!(/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(value)))){
						alertx(colname + "请输入数字");
						}
						else if((value < 0)||(value > (ratebase * 4))){ 	
					      	alertx(colname + "不正确");
						  }
							   else{
							   
									return [ true, "" ];
							   }
							}
				          }
					}
			        ],
		 cellsubmit : 'clientArray',
		 rowNum:-1 ,
		 onSelectRow : editRowData,
		 beforeSelectRow : function(rowId, e) {
				if (rowId == $('#rateContent').jqGrid('getGridParam', 'selrow')) {
					return false;
				}
				saveRowData($('#rateContent').jqGrid('getGridParam', 'selrow'));
					return true;
				}	   
			});
		
		
		$("#inputForm").validate({
			submitHandler: function(){	
			saveForm();	
		  },
			errorContainer: "#messageBox",
			errorPlacement: function(error, element) {
			checkReq(error, element);
			 }
		});
	});
		
	function saveForm() {
		loading();
		var rowIds = $("#rateContent").jqGrid("getDataIDs");
		for (var i = 0; i < rowIds.length; i++) {
			saveRowData(rowIds[i]);
		}
		var rowData = $("#rateContent").jqGrid("getRowData");
		var productTypeCode = $("#productTypeCode").val();
		var loanRepayType = $("#loanRepayType").val();
		var startTime=$("#startTime").val();
		var endTime=$("#endTime").val();
		
		saveJson("${ctx}/credit/rateInterest/save?productTypeCode="+productTypeCode+"&loanRepayType="+loanRepayType+"&startTime="+startTime+"&endTime="+endTime, JSON.stringify(removeDotProperty(rowData)), function(data) {
			if (data.status == 1) {
				alertx(data.message, function() {
					closeJBox();
				});
			} else {
// 				$("#btnSubmit").removeAttr("disabled");
				alertx(data.message);
			}
			closeTip();
		});
	}
	
	function editRowData(rowId) {
		$('#rateContent').jqGrid("editRow", rowId, false);
	};
	
	function saveRowData(rowId) {
		if (rowId) {
			$('#rateContent').jqGrid("saveRow", rowId, false, 'clientArray');
		}
	};
	
		
</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="rateInterest" action="${ctx}/credit/rateInterest/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>

		<table class="searchTable">
			<tr>
				<td class="ft_label">产品类型：</td>
				<td class="ft_content">
					<form:select id="productTypeCode" path="productTypeCode" class="input-medium required" >
					<form:option value="" label="" />
					<form:options items="${fns:getDictList('PRODUCT_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
				</td>
				<td class="ft_label">还款方式：</td>
				<td class="ft_content">
				<form:select id="loanRepayType" path="loanRepayType" class="input-medium required">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('LOAN_REPAY_TYPE')}"
							itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select></td>
			</tr>
			<tr>
				<td class="ft_label">起始日期：</td>
				<td class="ft_content">
				<input id="startTime" name="startTime" type="text"
					readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${rateInterest.startTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}',isShowClear:true});" />
				</td>
				<td class="ft_label">截止日期：</td>
				<td class="ft_content">
				<input id="endTime" name="endTime" type="text"
					readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${rateInterest.endTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}',isShowClear:true});" />
				</td>
			</tr>
		</table> 
	<div>
		<div  style="margin:auto;width:30%">
			<table id="rateContent" class="control-label" ></table>
		</div>
	</div>
		
		<div class="form-actions">
			<shiro:hasPermission name="credit:rateInterest:edit">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" style="margin-left: 250px;" />&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn btn-primary" type="button" value="返回" onclick="closeJBox();" />
		</div>
	</form:form>
</body>
</html>