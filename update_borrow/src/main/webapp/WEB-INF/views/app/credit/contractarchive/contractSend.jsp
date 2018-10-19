<!--
 * @reqno: H1601150171
 * @date-designer:2016年01月25日-lirongchao
 * @e-in-other	: isRecipitent -是否签收: 是否签收
 * @e-in-other	: recipientName -签收人 : 签收人
 * @e-in-other	: recipientTime -签收日期 : 签收日期
 * @e-out-other	: custName -客户名称 : 客户名称
 * @e-out-other	: contractNo -合同编号 : 合同编号
 * @e-out-other	: orgNum.name -归属机构 : 归属机构
 * @e-out-other	: senderTime -发出日期 : 发出日期
 * @e-out-other	: senderName -发件人 : 发件人
 * @e-out-other	: isRecipitentName -是否签收 : 是否签收
 * @e-out-other	: recipientName -签收人 : 签收人
 * @e-out-other	: recipientTime -签收日期 : 签收日期
 * @e-ctrl :btnSubmit - 保存 ：保存
 * @e-ctrl :btnClose - 关闭：关闭
 * @date-author:2016年01月25日-lirongchao:合同归档信息发送jsp
 -->
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>合同归档信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					var senderName = $("#senderName").val();
					var senderTime = $("#senderTime").val();
					var expressCompany = $("#expressCompany").val();
					var expressNo = $("#expressNo").val();
					var id = $("#id").val();
					var senderTimeDate = new Date(senderTime);
					var newDate = new Date();
					if(senderTimeDate > newDate){
						alertx("发出日期不能大于当前日期!");
						return;
					}
					$.ajax({
						type:"post",
						data : {id:id,senderTime:senderTime,senderName:senderName,expressCompany:expressCompany,expressNo:expressNo},
						url:"${ctx}/credit/contractArchive/sendSave",
						dataType:"text",
						success:function(data){
							if(data=="success"){
								alertx("发送成功！",function(){
									parent.page();
									closeJBox('contractSendForm');
								});
						 	}else{
						 		alertx("发送失败，请查看后台信息");
						 	}
						},
						error:function(msg){
							alertx("未能保存，请查看后台信息");
						}
					});
				},
				errorContainer : "#messageBox",
				errorPlacement : function(error, element) {
					checkReq(error, element);
				}
			});
		});
		function isBorrowingChage(isBorrowing) {
			if("1" == isBorrowing){
				$("#borrowingNamefont").html('*');
				$("#borrowingTimefont").html('*');
				$("#borrowingReasonfont").html('*');
				$("#borrowingName").addClass("required");
				$("#borrowingTime").addClass("required");
				$("#borrowingReason").addClass("required");
			}
			else {
				$("#borrowingNamefont").html('');
				$("#borrowingTimefont").html('');
				$("#borrowingReasonfont").html('');
				$("#borrowingName").removeClass("required");
				$("#borrowingTime").removeClass("required");
				$("#borrowingReason").removeClass("required");
			}
		}
	</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">合同归档信息</h3>
			<div class="searchCon">
				<form:form id="inputForm"  modelAttribute="contractArchive" action="${ctx}/credit/contractArchive/save" method="post">
					<input  type="hidden" id="id" name="id"  class="btn btn-primary" value="${contractArchive.id}" />
					<div class="filter">
						<table class="fromTable" width="650">
							<tr>
								<td class="ft_label">客户名称：</td>
								<td class="ft_content">
								<input id="custName" name="custName" type="text" value="${contractArchive.custName }" readonly="readonly" class="input-medium"/>
								</td>
								<td class="ft_label">合同编号：</td>
								<td class="ft_content">
								<input id="contractNo" name="contractNo" type="text" value="${contractArchive.contractNo }" readonly="readonly" class="input-medium"/>
								</td>
							</tr>
							<tr>
								<td class="ft_label">快递公司：</td>
								<td class="ft_content">
								<input id="expressCompany" name="expressCompany" type="text" value="${contractArchive.expressCompany }"  class="input-medium required"/>
								</td>
								<td class="ft_label">快递单号：</td>
								<td class="ft_content">
								<input id="expressNo" name="expressNo" type="text" value="${contractArchive.expressNo }"  class="input-medium required"/>
								</td>
							</tr>
							<tr>
								<td class="ft_label">发件人：</td>
								<td class="ft_content">
								<input id="senderName" name="senderName" maxlength="15" type="text" value="${contractArchive.senderName }" class="input-medium required"/>
								<font color="red" >*</font>
								</td>
								<td class="ft_label">发出日期：</td>
								<td class="ft_content">
								<input id="senderTime" name="senderTime" type="text"  maxlength="20" class="input-medium Wdate required"
								value="<fmt:formatDate value="${contractArchive.senderTime }" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd ',isShowClear:true});" />
								<font color="red" >*</font>
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="提交"/>&nbsp;
							<input id="btnClose" class="btn btn-primary" type="button" value="关闭" onclick="closeJBox();" />
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>