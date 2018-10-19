<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>注册冠E贷账号</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	$(document).ready(function() {
		var hasGEDAccount = '${isHasGEDAccount}';
		if(hasGEDAccount=='1'){//已经有了
			$("#hasRegisterAccount").hide();
			$("#registerFormDiv").hide();
			$("#hasAccount").show();
			$("#spanNum").show();
			var findGedNum = '${findGedNum}';
			$("#spanNum").text("账号为："+findGedNum);
		}else{
			$("#hasAccount").hide();
			$("#hasRegisterAccount").hide();
			$("#spanNum").hide();
			$("#registerFormDiv").show();
			var findException = '${findException}';
			if(findException!=null&&findException!=''){
				alertx("错误信息："+findException);
			}
		}
		
		$("#registerForm").validate({
			submitHandler : saveForm,
			errorContainer : "#messageBox",
			errorPlacement : function(error, element) {
				$("#messageBox").text("输入有误，请先更正。");
				if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
					error.appendTo(element.parent().parent());
				} else {
					error.insertAfter(element);
				}
			}
			});
	});
	function saveForm() {
		loading('正在注册，请稍等...');
		var formJson = $("#registerForm").serializeJson();
		$.post("${ctx}/credit/creGuaranteeCompany/registerGEDAccount",formJson,function(data){
			if(data){
				closeTip();
				if (data.status == '1') {//注册成功
					$("#hasAccount").hide();
					$("#registerFormDiv").hide();
					$("#hasRegisterAccount").show();
					$("#spanNum").show();
					$("#spanNum").text("账号为："+data.message);
					parent.location.reload();
				} else {
					alertx(data.message);
				}
			}
		});
	}
	
	function flag(flag){
		if(flag == "1"){
			$("#linkMobileFlag").val($("#linkMobile").val());
		}
		if(flag == "2"){
			$("#linkMobileFlag").val($("#unSocCreditNo").val());
		}
	}
	</script>
	<style type="text/css">
	.tableList table td, .tableList th {
    	border: 0px solid #d7e3f1;
    	text-align: ;
	}
	
	.ft_label {
    text-align: right;
	}
	</style>
</head>
<body>
	<div class="tableList">
		<sys:message content="${message}"/>
		<div class="searchInfo">
			<h3 class="searchTitle">开通冠易贷账号</h3>
			<div id="registerGED"  class="ribbon filter">
				<div >
					<span id="hasAccount" style="text-align: center;display:block;color: red">已注册冠易贷登陆账号！</span>
					<span id="hasRegisterAccount" style="text-align: center;display:block;color: red">成功注册冠易贷登陆账号！</span> <br>
					<span id="spanNum" style="text-align: center;display:block;color: red"></span>	
				</div>	
				<div id="registerFormDiv">
					<form id="registerForm" method="post">
						<input type="hidden" name="guaranteeLimit" id="guaranteeLimit" value="${creGuaranteeCompany.guaranteeLimit}">
						<input type="hidden"  name = "unSocCreditNo" id="unSocCreditNo"  value="${creGuaranteeCompany.unSocCreditNo}" readonly="readonly">
						<input type="hidden"  name = "linkMobile" id="linkMobile"  value="${creGuaranteeCompany.linkMobile}" readonly="readonly">
						<table class="fromTable filter">
							<tr>
								<td class="ft_label" style="text-align: right;">
									公司名称：
								</td>
								<td class="ft_content">
									<input type="text"  value="${creGuaranteeCompany.guaranteeCompanyName}" readonly="readonly">
								</td>
							</tr>
							<tr>
								<td class="ft_label" style="text-align: right;">
									选择标识：
								</td>
								<td class="ft_content">
									<input type="radio" name="registerFlag" value="1" id="radio_mo" class="required" onclick="flag(this.value);">手机号码
									<input type="radio" name="registerFlag" value="2" id="radio_mo" class="required" onclick="flag(this.value);">统一社会信用
								</td>
							</tr>
							<tr>
								<td class="ft_label" style="text-align: right;">
									冠E贷账号：
								</td>
								<td class="ft_content">
									<input type="text"   id="linkMobileFlag" value="" readonly="readonly">
								</td>
							</tr>
							<tr>
								<td colspan="2" style="text-align:center">
									<input id="btnSubmit"  class="btn btn-primary setwidths" type="submit" value="创建冠易贷账号"/>
									<input id="btnCancel" class="btn btn-primary" type="button" value="返 回" onclick="closeJBox();" />
								</td>
							</tr>
						</table>
					</form>
				</div>	
			</div>
		</div>
	</div>
</body>
</html>

