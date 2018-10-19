<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>流水信息管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(
			function() {
				//$("#name").focus();
				document.getElementById("curreValidLineAmount").value=outputmoney("${creditLineBankDetail.curreValidLineAmount}");
				document.getElementById("incomeAmount").value=outputmoney("${ creditLineBankDetail.incomeAmount}");
				document.getElementById("expenseAmount").value=outputmoney("${ creditLineBankDetail.expenseAmount }");
				document.getElementById("cycleEndAmount").value=outputmoney("${ creditLineBankDetail.cycleEndAmount }");
				
				$("#creditLineBankDetailForm")
						.validate(
								{
									submitHandler : saveCreditLineBankDetail,
									errorContainer : "#messageBox",
									errorPlacement : function(error, element) {
										$("#messageBox").text("输入有误，请先更正。");
										
									checkReq(error,element);
									}
								});
			});
	function addRow(list, idx, tpl, row) {
		$(list).append(Mustache.render(tpl, {
			idx : idx,
			delBtn : true,
			row : row
		}));
		$(list + idx).find("select").each(function() {
			$(this).val($(this).attr("data-value"));
		});
		$(list + idx).find("input[type='checkbox'], input[type='radio']").each(
				function() {
					var ss = $(this).attr("data-value").split(',');
					for (var i = 0; i < ss.length; i++) {
						if ($(this).val() == ss[i]) {
							$(this).attr("checked", "checked");
						}
					}
				});
	}
	function delRow(obj, prefix) {
		var id = $(prefix + "_id");
		var delFlag = $(prefix + "_delFlag");
		if (id.val() == "") {
			$(obj).parent().parent().remove();
		} else if (delFlag.val() == "0") {
			delFlag.val("1");
			$(obj).html("&divide;").attr("title", "撤销删除");
			$(obj).parent().parent().addClass("error");
		} else if (delFlag.val() == "1") {
			delFlag.val("0");
			$(obj).html("&times;").attr("title", "删除");
			$(obj).parent().parent().removeClass("error");
		}
	}
	function saveCreditLineBankDetail(){
	
 	var valueT1=$("#curreValidLineAmount").val().replace(/,/g,"");		
	var valueT2=$("#incomeAmount").val().replace(/,/g,"");		
	var valueT3=$("#expenseAmount").val().replace(/,/g,"");	
	var valueT4=$("#cycleEndAmount").val().replace(/,/g,"");	
	
	$("#curreValidLineAmount").val(valueT1);
	$("#incomeAmount").val(valueT2);
	$("#expenseAmount").val(valueT3);
	$("#cycleEndAmount").val(valueT4); 
				loading();
		top.$.jBox.tip.mess = null;
		var creditLineBankId = '${creditLineBankDetail.creditLineBank.id}';
		var creditLineBankDetail = $("#creditLineBankDetailForm").serializeJson();
		creditLineBankDetail.creditLineBank = {id:creditLineBankId};
		saveJson("${ctx}/credit/creditAndLine/creditLineBank/creditLineBankDetail/save",
				JSON.stringify(creditLineBankDetail),
				function(data) {
					if (data) {
						closeTip();
						if (data.status == 1) {//保存成功
							alertx(data.message,function() {
								parent.location.href="${ctx}/credit/creditAndLine/creditLineBank/creditLineBankDetail/list?creditLineBankId="+creditLineBankId+"&applyNo="+'${applyNo}'+"&readOnly="+'${readOnly}';
							    parent.parent.$.loadDiv("creditLineBankDiv","${ctx }/credit/creditAndLine/creditLineBank/list",{applyNo:'${applyNo}', readOnly:'${readOnly}'},"post");
							   	closeJBox();
							   	
							});
						} else {
							alertx(data.message);
						}
					}
				});
	}
	
	function checkForm() {
	   var curreValidLineAmount=$("#curreValidLineAmount").val().replace(/,/g,"");		
	   var incomeAmount=$("#incomeAmount").val().replace(/,/g,"");
		if (curreValidLineAmount*10/10 > incomeAmount*10/10) {
			alertx("当月有效流水应小于等于进项额");
			return false;
		} 
		return true;
	}
</script>
</head>
<body>
	<div class="searchInfo">
		<h3 class="searchTitle">流水信息</h3>
		<div class="searchCon">
			<form:form id="creditLineBankDetailForm" modelAttribute="creditLineBankDetail"
				action="${ctx}/credit/creditLineBank/creditLineBank/creditLineBankDetail/save"
				method="post" class="form-horizontal">
				<form:hidden path="id" />
				<sys:message content="${message}" />
				<table class="fromTable filter">
					<tr>
						<td class="ft_label">户名：</td>
						<td class="ft_content" colspan="3">
						<input type="text" id="custName" name="custName" class="input-medium" style="width: 50%;" readonly="readonly" value="${creditLineBankDetail.creditLineBank.custName }" /></td>
					</tr>
					<tr>
						<td class="ft_label">银行卡号：</td>
						<td class="ft_content" colspan="3">
						<input type="text" id="lineBankId" name="lineBankId" class="input-medium number" style="width: 50%;" readonly="readonly" value="${creditLineBankDetail.creditLineBank.bankcardNo }" /></td>
					</tr>
					<tr>
						<td class="ft_label">流水月份：</td>
						<td class="ft_content">
						<input type="text" readonly="readonly" id="lineMonth" name="lineMonth" class="input-medium required Wdate" value="${creditLineBankDetail.lineMonth }" onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyyMM',maxDate:new Date()});"/></td>
						<td class="ft_label">当月有效流水(元)：</td>
						<td class="ft_content">
						<input type="text" onkeyup="keyPress11(this);"
									onblur="this.value=outputmoney(this.value);" id="curreValidLineAmount" name="curreValidLineAmount" class="input-medium required" value="${creditLineBankDetail.curreValidLineAmount }" /></td>
					</tr>
					<tr>
						<td class="ft_label">进项额(元)：</td>
						<td class="ft_content">
						<input type="text" onkeyup="keyPress11(this);"
									onblur="this.value=outputmoney(this.value);" id="incomeAmount" name="incomeAmount" class="input-medium required" value="${creditLineBankDetail.incomeAmount }" /></td>
						<td class="ft_label">出项额(元)：</td>
						<td class="ft_content">
						<input type="text" onkeyup="keyPress11(this);"
									onblur="this.value=outputmoney(this.value);" id="expenseAmount" name="expenseAmount" class="input-medium required" value="${creditLineBankDetail.expenseAmount }" /></td>
					</tr>
					<tr>
						<td class="ft_label">期末余额：</td>
						<td class="ft_content">
						<input type="text" onkeyup="keyPress11(this);"
									onblur="this.value=outputmoney(this.value);" id="cycleEndAmount" name="cycleEndAmount" class="input-medium required" value="${creditLineBankDetail.cycleEndAmount }" /></td>
					</tr>
					<tr>
						<td colspan="4">
							<div class="form-actions">
								<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="return checkForm();"/>&nbsp; 
								<input id="btnCancel" class="btn" type="button" value="返 回" onclick="closeJBox();" />
							</div>
						</td>
					</tr>
				</table>

			</form:form>
		</div>
	</div>
</body>
</html>