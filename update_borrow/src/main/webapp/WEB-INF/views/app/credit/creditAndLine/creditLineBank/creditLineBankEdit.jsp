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
				$("#inputForm")
						.validate(
								{
									submitHandler : function(form) {
										loading('正在提交，请稍等...');
										form.submit();
									},
									errorContainer : "#messageBox",
									errorPlacement : function(error, element) {
										$("#messageBox").text("输入有误，请先更正。");
										if (element.is(":checkbox")
												|| element.is(":radio")
												|| element.parent().is(
														".input-append")) {
											error.appendTo(element.parent()
													.parent());
										} else {
											error.insertAfter(element);
										}
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
</script>
</head>
<body>
	<div class="searchInfo">
		<h3 class="searchTitle">编辑流水银行卡</h3>
		<div class="searchCon">
			<form:form id="inputForm" modelAttribute="creditLineBank"
				action="${ctx}/credit/creditLineBank/save" method="post"
				class="form-horizontal">
				<form:hidden path="id" />
				<sys:message content="${message}" />
				<table class="fromTable filter">
					<tr>
						<td class="ft_label">户名：</td>
						<td class="ft_content"><input type="text" id="custName"
							name="custName" class="input-medium"
							value="${creditLineBank.custName}" /></td>
						<td class="ft_label">开户行：</td>
						<td class="ft_content"><input type="text" id="bankName"
							name="bankName" class="input-medium"
							value="${creditLineBank.bankName}" /></td>
						<td class="ft_label">银行卡号：</td>
						<td class="ft_content"><input type="text" id="bankcardNo"
							name="bankcardNo" class="input-medium"
							value="${creditLineBank.bankcardNo}" /></td>
					</tr>
					<tr>
						<td class="ft_label">打印日期：</td>
						<td class="ft_content"><input id="printingDate" name="printingDate" type="text"
							maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${creditLineBank.printingDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:new Date(),isShowClear:false});" />
						</td>
						<td class="ft_label">余额(元)：</td>
						<td class="ft_content"><input type="text" id="balanceAmount"
							name="balanceAmount" class="input-medium"
							value="${creditLineBank.balanceAmount}" /></td>
					</tr>
					<tr>
						<td colspan="6">
							<div class="form-actions">
								<input id="btnSubmit" class="btn btn-primary" type="submit"
									value="保 存" />&nbsp; <input id="btnCancel" class="btn"
									type="button" value="返 回" onclick="history.go(-1)" />
							</div>
						</td>
					</tr>
				</table>
			</form:form>
		</div>
	</div>
</body>
</html>