<!--
 * @reqno: H1601150180
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
 * @date-author:2016年01月25日-lirongchao:合同归档信息归档jsp
 -->
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>合同归档信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			adjustTextareaLength("borrowingReason","pre");
			var isBorrowing = $("#isBorrowing").val();
			isBorrowingChage(isBorrowing);
			$("#inputForm").validate({
				submitHandler: function(form){
					loading("正在提交，请稍等...");
					var contractLocation = $("#contractLocation").val();
					var isBorrowing = $("#isBorrowing").val();
					var borrowingName = $("#borrowingName").val();
					var borrowingTime = $("#borrowingTime").val();
					var borrowingReason = $("#borrowingReason").val();
					var id = $("#id").val();
					$.ajax({
						type:"post",
						data : {contractLocation:contractLocation,isBorrowing:isBorrowing,borrowingName:borrowingName,borrowingTime:borrowingTime,borrowingReason:borrowingReason,id:id},
						url:"${ctx}/credit/contractArchive/archiveSave",
						dataType:"text",
						success:function(data){
							if(data=="success"){
						 		alertx("归档成功！", function(){
						 			parent.$("#contractGrid").jqGrid().trigger("reloadGrid");
							 		closeJBox();
						 		});
						 		closeTip();
						 	}else{
						 		alertx("归档失败，请查看后台信息！");
						 	}
						},
						error:function(msg){
							alertx("归档失败，请查看后台信息！");
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
				$("#borrowingNameTD1").show();
				$("#borrowingNameTD2").show();
				$("#borrowingTimeTD1").show();
				$("#borrowingTimeTD2").show();
				$("#borrowingReasonTD1").show();
				$("#borrowingReasonTD2").show();
			}
			else {
				$("#borrowingNameTD1").hide();
				$("#borrowingNameTD2").hide();
				$("#borrowingTimeTD1").hide();
				$("#borrowingTimeTD2").hide();
				$("#borrowingReasonTD1").hide();
				$("#borrowingReasonTD2").hide();
				$("#borrowingName").val("");
				$("#borrowingTime").val("");
				$("#borrowingReason").val("");
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
								<td class="ft_label">归属机构：</td>
								<td class="ft_content">
								<input id="orgNum.name" name="orgNum.name" type="text" value="${contractArchive.orgNum.name}" readonly="readonly" class="input-medium"/>
								</td>
							</tr>
							<tr>
								<td class="ft_label">发件人：</td>
								<td class="ft_content">
								<input id="senderName" name="senderName" type="text" value="${contractArchive.senderName }" readonly="readonly" class="input-medium"/>
								</td>
								<td class="ft_label">发出日期：</td>
								<td class="ft_content">
								<input id="senderTime" name="senderTime" type="text" value='<fmt:formatDate  value="${contractArchive.senderTime}" pattern="yyyy-MM-dd"/>'  class="input-medium Wdate"/>
								</td>
								<td class="ft_label">是否签收：</td>
								<td class="ft_content">
								<input id="isRecipitentName" name="isRecipitentName" type="text" value="${contractArchive.isRecipitentName }" readonly="readonly" class="input-medium"/>
								</td>
							</tr>
							<tr>
								<td class="ft_label">合同资料存放位置：</td>
								<td class="ft_content">
								<input id="contractLocation" name="contractLocation" type="text" maxlength="200" value="${contractArchive.contractLocation }"  class="input-medium "/>
								</td>
								<td class="ft_label">快递公司：</td>
								<td class="ft_content">
								<input id="expressCompany" name="expressCompany" type="text" value="${contractArchive.expressCompany }" readonly="readonly" class="input-medium required"/>
								</td>
								<td class="ft_label">快递单号：</td>
								<td class="ft_content">
								<input id="expressNo" name="expressNo" type="text" value="${contractArchive.expressNo }" readonly="readonly" class="input-medium required"/>
								</td>
							</tr>
							<tr>
								<td class="ft_label">是否被借出：</td>
								<td class="ft_content">
								<form:select path="isBorrowing" class="input-medium" onchange="isBorrowingChage(this.value)">
								<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
								</form:select>
								</td>
								<td id="borrowingNameTD1" class="ft_label">借阅人：</td>
								<td id="borrowingNameTD2" class="ft_content">
								<input id="borrowingName" name="borrowingName" type="text" maxlength="15" value="${contractArchive.borrowingName }"  class="input-medium required"/>
								<font color="red" id = "borrowingNamefont">*</font>
								</td>
								<td id="borrowingTimeTD1" class="ft_label">借阅日期：</td>
								<td id="borrowingTimeTD2" class="ft_content">
								<input id="borrowingTime" name="borrowingTime" type="text"  maxlength="20" class="input-medium Wdate required"
								value="<fmt:formatDate value="${contractArchive.borrowingTime }" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd ',isShowClear:true});" />
								<font color="red" id = "borrowingTimefont">*</font>
								</td>
							</tr>
							<tr>
								<td id="borrowingReasonTD1" class="ft_label">借阅原因：</td>
								<td id="borrowingReasonTD2" class="ft_content" colspan="3">
								<textarea id="borrowingReason" name="borrowingReason" maxlength="1000"  class="input-xxlarge textarea-style required"  onkeyup="adjustTextareaLength('borrowingReason','pre')" rows="3">${contractArchive.borrowingReason}</textarea>
								<font color="red" id = "borrowingReasonfont">*</font>
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="保存"/>&nbsp;
							<input id="btnClose" class="btn btn-primary" type="button" value="关闭" onclick="closeJBox();" />
						</div>
					</div>
				</form:form>
				<div>
					<pre class="input-xxlarge pre-style" id="pre"></pre>
				</div>
			</div>
		</div>
	</div>
</body>
</html>