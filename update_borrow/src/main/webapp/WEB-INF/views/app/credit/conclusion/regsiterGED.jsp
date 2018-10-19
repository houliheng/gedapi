<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<script type="text/javascript">
	$(document).ready(function() {
		$(".setwidths").width(80);
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
		var hasNoCompany = '${hasNoCompany}';
		if(hasNoCompany=='1'){
			alertx("请先填写主借人企业和主借人信息！");
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
		var dataOne=$("#dataOne").val();
		var dataTwo=$("#dataTwo").val();
		if(dataOne==''||dataOne==null||dataTwo==''||dataTwo==null){
			alertx("请完善主借人企业或者主借人信息！");
			return;
		}
		var formJson = $("#registerForm").serializeJson();
		$.post("${ctx}/credit/conclusion/registerGEDAccount",formJson,function(data){
			if(data){
				closeTip();
				if (data.status == '1') {//注册成功
					$("#hasAccount").hide();
					$("#registerFormDiv").hide();
					$("#hasRegisterAccount").show();
					$("#spanNum").show();
					$("#spanNum").text("账号为："+data.message);
				} else {
					alertx(data.message);
				}
			}
		});
	}
	

	
	</script>
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
						<input type="hidden" name="unSocCreditNo" id="unSocCreditNo" value="${unSocCreditNo }">
						<input type="hidden" name="applyNo" id="applyNo" value="${applyNo }">
						<input type="hidden" name="comId" id="comId" value="${comId }">
						<input type="hidden" name="custId" id="custId" value="${custId }">
						<table class="fromTable filter">
							<tr>
								<td class="ft_label">
									<input type="radio" checked="checked" name="registerPhone" value="2" id="radio_co" class="required">
									<input type="hidden"  name="corporationMobile" value="${corporationMobile }" >
									法定代表人姓名：
								</td>
								<td class="ft_content">
									<input type="text" id="dataOne" value="${corporationName }" readonly="readonly">
								</td>
								<td class="ft_label">
									法定代表人电话：
								</td>
								<td class="ft_content">
									<input type="text" id="dataOne1" value="${corporationMobile }" readonly="readonly">
								</td>
							</tr>
							<tr>
								<td class="ft_label">
									<input type="radio" name="registerPhone" value="1" id="radio_mo" class="required">
									<input type="hidden"  name="mobileNum" value="${mobileNum }" >
									主借人姓名：
								</td>
								<td class="ft_content">
									<input type="text" id="dataTwo"  value="${custName }" readonly="readonly">
								</td>
								<td class="ft_label">
									主借人电话：
								</td>
								<td class="ft_content">
									<input type="text" id="dataTwo1"  value="${mobileNum }" readonly="readonly">
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit"  class="btn btn-primary setwidths" type="submit" value="创建冠易贷账号"/>
						</div>
					</form>
				</div>	
			</div>
		</div>
	</div>
</body>
</html>

