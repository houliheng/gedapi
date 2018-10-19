<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
				$(".unSocCreditNoShow").hide();
				$(".corporationMobileShow").hide();
				$(".mobileNumShow").hide();
				if('${roleType}'!='5'){
					$("#registerChoose").hide();
				}else{
					$(".mobileNumShow").show();
				}
				//$("#name").focus();
				$("#inputForm").validate({
					submitHandler: function(form){
						loading('正在注册，请稍等...');
						var param = $("#inputForm").serialize();
						$.post("${ctx}/credit/createGedAccount/createAccountNew", param, function(data) {
							if (data) {
								closeTip();
								if (data.status == '1') {
									alertx(data.message, function() {
										parent.location.reload();
										//parent.$.loadDiv("guarantorInfoMainList", "${ctx }/credit/createGedAccount/mainList", {applyNo : '${applyNo}'}, "post");
										closeJBox();
									});
								} else {
									alertx(data.message);
								}
							}
						});
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
		
		function chooseAccountList(status){
			if(status=='1'){
				$(".unSocCreditNoShow").hide();
				$(".corporationMobileShow").hide();
				$(".mobileNumShow").show();
			}
			if(status=='2'){
				$(".unSocCreditNoShow").hide();
				$(".corporationMobileShow").show();
				$(".mobileNumShow").hide();			
			}
			if(status=='3'){
				$(".unSocCreditNoShow").show();
				$(".corporationMobileShow").hide();
				$(".mobileNumShow").hide();
			}
		}
	</script>

</head>
<body>
	<form id="inputForm" action="#" method="post" class="form-horizontal">
		<table class="fromTable filter">
			<tr>
				<td class="ft_label">企业名称：</td>
				<td class="ft_content">
					<input type="text" readonly="readonly" id="busiRegName" name="busiRegName" value="${busiRegName}"  class="required" />
					<input type="hidden"  id="unSocCreditNo" name="unSocCreditNo" value="${unSocCreditNo}"/>
					<input type="hidden"  id="applyNo" name="applyNo" value="${applyNo}"/>
					<input type="hidden"  id="corporationMobile" name="corporationMobile" value="${corporationMobile}"/>
					<input type="hidden"  id="flag" name="flag" value="${flag}"/>
					<input type="hidden"  id="roleType" name="roleType" value="${roleType}"/>
					<input type="hidden"  id="id" name="id" value="${id}"/>
					<input type="hidden"  id="mobileNum" name="mobileNum" value="${mobileNum}"/>
					<font color="red">*</font>
				</td>
			</tr>
			<tr id="registerChoose">
				<td class="ft_label">选择标识：</td>
				<td id="redioId" class="ft_content"   colspan="7">
					<input type="radio" name="accountType" checked="checked" value="1" id="radio_1" class="required" onclick="chooseAccountList(1)">
					<label for="radio_yes">主借人手机号</label>&nbsp;&nbsp;
				</td>	
			</tr>
			<tr>
				<td class="ft_label"></td>
				<td id="redioId" class="ft_content"   colspan="7">
					<input type="radio" name="accountType" value="2" id="radio_2" class="required" onclick="chooseAccountList(2)">
					<label for="radio_no">法人代表手机号</label>&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<td class="ft_label"></td>
				<td id="redioId" class="ft_content"   colspan="7">
					<input type="radio" name="accountType" value="3" id="radio_3" class="required" onclick="chooseAccountList(3)">
					<label for="radio_no">统一社会信用代码</label>&nbsp;&nbsp;
				</td>
			</tr>	
			<tr>
				<td class="ft_label">冠易贷账号 ：</td>
				<td class="ft_content">
					<input type="text" readonly="readonly"   value="${unSocCreditNo}" class="unSocCreditNoShow" />
					<input type="text" readonly="readonly"   value="${corporationMobile}" class="corporationMobileShow"/>
					<input type="text" readonly="readonly"   value="${mobileNum}" class="mobileNumShow"/>
				</td>
			</tr>
		</table>
		<div class="searchButton" id="buttonDiv">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />
		</div>
	</form>
</body>
</html>