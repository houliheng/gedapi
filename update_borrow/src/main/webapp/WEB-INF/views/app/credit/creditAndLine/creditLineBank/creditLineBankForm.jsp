<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>流水信息管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		//$("#name").focus();
		document.getElementById("balanceAmount").value=outputmoney("${ creditLineBank.balanceAmount}");
		document.getElementById("averageCycleEndAmount").value=outputmoney("${creditLineBank.averageCycleEndAmount}");
		$("#creditLineBankForm").validate({
		submitHandler : saveCreditLineBank,
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
		$(list + idx).find("input[type='checkbox'], input[type='radio']").each(function() {
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

	//根绝角色类型查询人员姓名List
	function findCustByRoleType() {
		$("#custId").empty();
		$("#custId").append("<option value=''>请选择</option>");
		var $custId = $("#s2id_custId>.select2-choice>.select2-chosen");
		$custId.html("请选择");
		$.post("${ctx}/credit/creditAndLine/creditLineBank/findCustNameByRoleType", {
		roleType : $("#roleType").val(),
		applyNo : '${creditLineBank.applyNo}'
		}, function(data) {
			$.each(data, function(i, val) {
				$("#custId").append("<option value='"+val["custId"]+"' label='"+val["custName"]+"'>" + val["custName"] + "</option>");
			});
		});
	}

	//保存银行卡流水信息信息
	function saveCreditLineBank() {
	
		var valueT=$("#balanceAmount").val().replace(/,/g,"");		
				$("#balanceAmount").val(valueT);
		var averageCycleEndAmount=$("#averageCycleEndAmount").val().replace(/,/g,"");		
				$("#averageCycleEndAmount").val(averageCycleEndAmount);
		top.$.jBox.tip.mess = null;
		var custName = $("#custId").find("option:selected").attr("label");
		$("#custName").val(custName);
		var creditLineBank = $("#creditLineBankForm").serializeJson();
		loading();
		saveJson("${ctx}/credit/creditAndLine/creditLineBank/save", JSON.stringify(creditLineBank), function(data) {
			if (data) {
				closeTip();
				if (data.status == 1) {//保存成功
					alertx(data.message, function() {
						parent.$.loadDiv("creditLineBankDiv", "${ctx }/credit/creditAndLine/creditLineBank/list", {
							applyNo : '${creditLineBank.applyNo }'
						}, "post");
						closeJBox();
					});
				} else {
					alertx(data.message);
				}
			}
		});

	}
</script>
</head>
<body>
	<div class="searchInfo">
		<h3 class="searchTitle">新增流水银行卡</h3>
		<div class="searchCon filter">
			<form:form id="creditLineBankForm" modelAttribute="creditLineBank" action="${ctx}/credit/creditLineBank/save" method="post" class="form-horizontal">
				<form:hidden path="id" />
				<form:hidden path="applyNo" />
				<form:hidden path="custName" />
				<sys:message content="${message}" />
				<table class="fromTable filter">
					<tr>
						<td class="ft_label">人员类型：</td>
						<td class="ft_content">
							<form:select id="roleType" path="roleType" onchange="findCustByRoleType();" class="input-medium required" cssStyle="width:177px;">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('ROLE_TYPE') }" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
						</td>
						<td class="ft_label">户名：</td>
						<td class="ft_content">
							<select id="custId" name="custId" class="input-medium required" style="width: 177px;">
								<option value="" label="" />
								<c:forEach items="${custNameMap }" var="cust">
									<c:if test="${cust.custId eq creditLineBank.custId }">
										<option value="${cust.custId }" label="${cust.custName}" selected="selected">${cust.custName}</option>
									</c:if>
									<c:if test="${cust.custId ne creditLineBank.custId }">
										<option value="${cust.custId }" label="${cust.custName}">${cust.custName}</option>
									</c:if>
								</c:forEach>
							</select>
							<font color="red">*</font>
						</td>
						<td class="ft_label">开户行：</td>
						<td class="ft_content">
							<input type="text" id="bankName" name="bankName" class="input-medium required" value="${creditLineBank.bankName}" />
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">银行卡号：</td>
						<td class="ft_content">
							<input type="text" id="bankcardNo" name="bankcardNo" maxlength="20" class="input-medium number required" value="${creditLineBank.bankcardNo}" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">打印日期：</td>
						<td class="ft_content">
							<input id="printingDate" name="printingDate" type="text"  maxlength="20" class="input-medium Wdate required" value="${creditLineBank.printingDate}"
								onclick="WdatePicker({onpicked:dateWhite(this),maxDate:new Date(),dateFmt:'yyyy-MM-dd'});" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">余额(元)：</td>
						<td class="ft_content">
							<input  onkeyup="keyPress11(this);"
									onblur="this.value=outputmoney(this.value);" type="text" id="balanceAmount" name="balanceAmount" class="input-medium required" value="${creditLineBank.balanceAmount}" />
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">平均期末余额：</td>
						<td class="ft_content">
							<input onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" 
								type="text" id="averageCycleEndAmount" name="averageCycleEndAmount" class="input-medium required" 
								value="${creditLineBank.averageCycleEndAmount}"/>
						</td>
					</tr>
					<tr>
						<td colspan="6">
							<div class="form-actions">
								<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />
								&nbsp;
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