<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>阶梯利率管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	
		function saveForm() {
			loading();
			top.$.jBox.tip.mess = null;
			var formJson = $("#inputForm").serializeJson();
			$.post("${ctx}/credit/rateInterest/editSave", formJson, function(data) {
			if (data) {
	        	if (data.status == 1) {//保存成功
		      		alertx(data.message, function() {
		      		parent.$("#searchForm").submit();
					closeJBox();
					});
		      } else {
					$("#btnSubmit").removeAttr("disabled");
					alertx(data.message);
		      }
		   }
		   closeTip();
	      });
	}
	
		$(document).ready(function() {
			
			$("#inputForm").validate({
				submitHandler: function(){	
					saveForm();	
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	
	<br/>
	<form:form id="inputForm" modelAttribute="rateInterest" action="${ctx}/credit/rateInterest/editSave" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
			
		<div class="control-group">
			<label class="control-label">产品类型：</label>
			<div class="controls">
				<form:select id="productTypeCode" name="productTypeCode" path="productTypeCode" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('PRODUCT_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">还款方式：</label>
			<div class="controls">
				<form:select id="loanRepayType" name="loanRepayType" path="loanRepayType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('LOAN_REPAY_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">期限值：</label>
			<div class="controls">
				<form:select path="periodValue" class="input-medium">
				<form:option value="" label=""/>
				<form:options items="${fns:getDictList('PRODUCT_PERIOD_VALUE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">月应还利息的利率（%）：</label>
			<div class="controls">
				<form:input path="rateInterest" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">起始日期：</label>
			<div class="controls">
				<input id="startTime" name="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${rateInterest.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}',isShowClear:true});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">截止日期：</label>
			<div class="controls">
				<input id="endTime" name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${rateInterest.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}',isShowClear:true});"/>
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