<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
  <head>
    <title>阶梯本金管理</title>
    <script src="${ctxStatic}/jqGrid/4.6/js/jquery.jqGrid.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/jqGrid/4.6/css/ui.jqgrid.css">
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/jqGrid/4.6/css/default/jquery-ui-1.8.2.custom.css">
	<meta name="decorator" content="default"/>
    <script type="text/javascript">
    function updateGqgrid(){
    		$("#capitalContent").jqGrid('setGridParam',{
				url : "${ctx}/credit/rateCapital/getData",
				postData:{"periodValue":$("#periodValue").val(),"productTypeCode":$("#productTypeCode").val(),"loanRepayType":$("#loanRepayType").val(),"judgeFlag":1},
				mtype : 'POST',
				datatype : "json"  
				}).trigger('reloadGrid');
			$("#productTypeCode").attr("disabled","true");		
			$("#loanRepayType").attr("disabled","true");		
			$("#periodValue").attr("disabled","true");			
		}
    
    	$(document).ready(function(){
    		var gridUrl = '';
    		$("#capitalContent").jqGrid({
				url : gridUrl,
				datatype : "json",
				scrollOffset : 0 ,
				colNames : [ "Id","产品类型","还款方式","还款方式描述","合同期限","当期限值", "当期应还本金比例"],
				colModel : [
					{
					 name : "id",
					 hidden : true
					},
					{
					 name : "productTypeCode",
					 hidden : true
					},
					{
					 name : "loanRepayType",
					 hidden : true
					},
					{
					 name : "loanRepayDesc",
					 hidden : true
					},
					{
					 name : "periodValue",
					 hidden : true
					},
				   {
					 name : "periodNum",
					 align : 'center'
					},
					{
					 name : "rateCapitalCurr",
					 align : 'center',
					 editable : true,
					 editrules : 
					{
					 	custom : true,
					 	custom_func : function(value, colname) {
						 if ((value !="")&& (!(/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(value)))){
							alertx(colname + "请输入数字");
							}
							else if((value < 0)||(value > 100)){ 	
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
					if (rowId == $('#capitalContent').jqGrid('getGridParam', 'selrow')) {
						return false;
					}
					saveRowData($('#capitalContent').jqGrid('getGridParam', 'selrow'));
						return true;
					}	   
				});
			
			if ('${judgeFlag}'=='1'){
				setTimeout(function(){updateGqgrid();},300);
			}
			
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
    	function editRowData(rowId) {
			$('#capitalContent').jqGrid("editRow", rowId, false);
		};
	
		function saveRowData(rowId) {
			if (rowId) {
				$('#capitalContent').jqGrid("saveRow", rowId, false, 'clientArray');
			}
		};
		
		function createGqgrid(value){
    		$("#capitalContent").jqGrid('setGridParam',{
				url : "${ctx}/credit/rateCapital/getData",
				postData:{"periodValue":value,"productTypeCode":$("#productTypeCode").val(),"loanRepayType":$("#loanRepayType").val(),"judgeFlag":2},
				mtype : 'POST',
				datatype : "json"  
				}).trigger('reloadGrid');			
		}
		
		function saveForm() {
			loading();
			var rowIds = $("#capitalContent").jqGrid("getDataIDs");
			for (var i = 0; i < rowIds.length; i++) {
				saveRowData(rowIds[i]);
			}
			var rowData = $("#capitalContent").jqGrid("getRowData");
			if ('${judgeFlag}'=='1'){
				var URL = "${ctx}/credit/rateCapital/save?judgeFlag="+1;
				saveJson(URL, JSON.stringify(removeDotProperty(rowData)), function(data) {
				if (data.status == 1) {
					alertx(data.message, function() {
					parent.$("#searchForm").submit();
					closeJBox();
					});
				 } else {
					alertx(data.message);
				  }
					closeTip();
				});
			}else{
				var URL = "${ctx}/credit/rateCapital/save?judgeFlag="+2;
				saveJson(URL, JSON.stringify(removeDotProperty(rowData)), function(data) {
				if (data.status == 1) {
					alertx(data.message, function() {
					closeJBox();
					});
				} else {
					alertx(data.message);
				 }
				closeTip();
				});
			}
	}
		 
    </script>
  </head> 
  <body>
  	<form:form id="inputForm" modelAttribute="rateCapital" action="${ctx}/credit/rateCapital/save" method="post" class="form-horizontal">
  		<form:hidden path="id"/>
  		<sys:message content="${message}"/>
  		<table class="searchTable">
  			<tr>
  				<td class="ft_label">产品类型：</td>
  				<td class="ft_content">
					<form:select id="productTypeCode" path="productTypeCode" class="input-medium required" >
					<form:option value="" label="" />
					<form:options items="${fns:getDictList('PRODUCT_TYPE')}" 
						itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
				</td>
				<td class="ft_label">还款方式：</td>
				<td class="ft_content">
					<form:select id="loanRepayType" path="loanRepayType" class="input-medium required">
					<form:option value="" label="" />
					<form:options items="${fns:getDictList('LOAN_REPAY_TYPE')}"
						itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
				</td>
				<td class="ft_label">总期限值：</td>
				<td class="ft_content" >
					<form:select id="periodValue" path="periodValue" class="input-medium required" onchange="createGqgrid(this.value);">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('PRODUCT_PERIOD_VALUE')}" 
						itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</td>
  			</tr>
  		</table>
  	<div>
		<div  style="margin:auto;width:50%">
			<table id="capitalContent" class="control-label" ></table>
		</div>
	</div>
	<div class="form-actions">
		<shiro:hasPermission name="credit:rateCapital:edit">
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" style="margin-left: 250px;" />&nbsp;
		</shiro:hasPermission>
		<input id="btnCancel" class="btn btn-primary" type="button" value="返回" onclick="closeJBox();" />
	</div>
  	</form:form>
  </body>
</html>
