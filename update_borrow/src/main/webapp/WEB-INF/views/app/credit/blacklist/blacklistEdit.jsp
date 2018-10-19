<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title><c:if test="${blacklist.listStatus eq '1'}">加黑设置</c:if><c:if test="${blacklist.listStatus eq '2'}">刷白设置</c:if></title>
<meta name="decorator" content="default" />
<base target="_self"/>
<script type="text/javascript">
	var form = $("#detailForm");
	 $(document).ready(function() {
		$("#detailForm").validate({
				rules: {
				},
				messages: {
				},
				submitHandler: function(form){
					loading("正在提交，请稍等...");
					var blacklistId = $("#blacklistId").val();
					var listStatus = $("#listStatus").val();
					var remarks = $("#remarks").val();
					$.ajax({
						type:"post",
						data : {listStatus:listStatus,blacklistId:blacklistId,remarks:remarks},
						url:"${ctx}/credit/blacklist/editSave",
						dataType:"text",
						success:function(data){
							if(data=="success"){
						 		window.parent.returnValue = 1;
						 		window.close();
						 	}else{
						 		alertx("保存失败，请查看后台信息");
						 	}	 
						},
						error:function(msg){
							alertx("未能保存，请查看后台信息");
						}						 
					});
				}
			});
			adjustTextareaLength("remarks","preRemarks");
		});
</script>
</head>
<body>
	<!-- 
	 * @reqno: H1512210031
	 * @date-designer:2015年12月24日-lirongchao
	 * @e-out-other	: custName -客户名称 : 客户名称
	 * @e-out-other	: mobile -移动电话 : 移动电话
	 * @e-out-other	: idTypeLabel -证件类型 : 证件类型
	 * @e-out-other	: idNum -证件号 : 证件号	 	 	 	 
	 * @e-in-other	: remarks -设置说明 : 设置说明	 	 	 	 
	 * @e-ctrl : btnSubmit -保存: 保存
	 * @e-ctrl : btnClose -关闭 : 关闭
	 * @date-author:2015年12月24日-lirongchao:   在“个人黑名单管理”页面，选择1条上记录，点击加黑(刷白)，判断所选择记录是否是白名单(白名单)，如果不是，给出提示；如果是，则进行加黑操作，弹出加黑窗体，窗体名称“加黑设置”，窗口数据项：【客户名称、移动电话】、【证件类型、证件号】、【设置说明】；
												  窗体按钮：保存、关闭；
												  【客户名称、移动电话】、【证件类型、证件号】为只读；【设置说明】必填项，大文本显示，最大输入500汉字；能输入普通字符，如句号、逗号等；
												  点击保存按钮，更新黑名单表状态为黑名单；同时在加黑详情表添加一条记录明细；保存成功后，提示“保存成功！”、关闭窗体、刷新列表数据；
												  点击关闭按钮，关闭窗体；
	 -->
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle"><c:if test="${blacklist.listStatus eq '1'}">加黑设置</c:if><c:if test="${blacklist.listStatus eq '2'}">刷白设置</c:if></h3>
			<div class="searchCon">
				<form id="detailForm" action="${ctx}/credit/blacklist/editSave" method="post"> 
					<input  type="hidden" id="blacklistId" name="blacklistId"  class="btn btn-primary" value="${blacklist.id}" /> 
					<input  type="hidden" id="listStatus" name="listStatus"  class="btn btn-primary" value="${blacklist.listStatus}" /> 
					<div class="filter">
						<table class="fromTable" width="650">
							<tr>
								<td class="ft_label">客户名称：</td>
								<td class="ft_content">
								<input id="custName" name="custName" type="text" value="${blacklist.custName }" readonly="readonly" class="input-medium"/>
								</td>
	<!-- 
	 * @reqno: H1512210034
	 * @date-designer:2015年12月24日-lirongchao
	 * @e-out-other	: custName -客户名称 : 客户名称
	 * @e-out-other	: idTypeLabel -证件类型 : 证件类型
	 * @e-out-other	: idNum -证件号 : 证件号	 	 	 	 
	 * @e-in-other	: remarks -设置说明 : 设置说明	 	 	 	 
	 * @e-ctrl : btnSubmit -保存: 保存
	 * @e-ctrl : btnClose -关闭 : 关闭
	 * @date-author:2015年12月24日-lirongchao:   在“个人黑名单管理”页面，选择1条上记录，点击加黑(刷白)，判断所选择记录是否是白名单(白名单)，如果不是，给出提示；如果是，则进行加黑操作，弹出加黑窗体，窗体名称“加黑设置”，窗口数据项：【客户名称、移动电话】、【证件类型、证件号】、【设置说明】；
												  窗体按钮：保存、关闭；
												  【客户名称、移动电话】、【证件类型、证件号】为只读；【设置说明】必填项，大文本显示，最大输入500汉字；能输入普通字符，如句号、逗号等；
												  点击保存按钮，更新黑名单表状态为黑名单；同时在加黑详情表添加一条记录明细；保存成功后，提示“保存成功！”、关闭窗体、刷新列表数据；
												  点击关闭按钮，关闭窗体；
	 -->								
								<c:choose>
									<c:when test="${blacklist.custType eq '1'}">
										<td class="ft_label">移动电话：</td>
										<td class="ft_content">
										<input id="mobile" name="mobile" type="text" value="${blacklist.mobile }" readonly="readonly" class="input-medium"/>
										</td>									
									</c:when>
									<c:otherwise><td></td></c:otherwise>
								</c:choose>
							</tr>
							<tr>
								<td class="ft_label">证件类型：</td>
								<td class="ft_content">
								<input id="idTypeLabel" name="idTypeLabel" type="text" value="${blacklist.idTypeLabel }" readonly="readonly" class="input-medium"/>
								</td>
								<td class="ft_label">证件号：</td>
								<td class="ft_content">
								<input id="idNum" name="idNum" type="text" value="${blacklist.idNum }" readonly="readonly" class="input-medium"/>
								</td>
							</tr>
							<tr>
								<td class="ft_label">设置说明：</td>
								<td class="ft_content" colspan="3">
								<pre class="input-xlarge pre-style"  style="width:500px" id="preRemarks"></pre>
								<textarea id="remarks" name="remarks" maxlength="1000" class="input-xlarge textarea-style required" rows="3" style="width:500px" onkeyup="adjustTextareaLength('remarks','preRemarks')"></textarea>
								<font color="red">*</font>
								</td>
							</tr>
						</table>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
