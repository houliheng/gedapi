<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!-- 
 * @reqno:H1511130067
 * @date-designer:20151116-chenshaojia
 * @date-author:20151116-chenshaojia:CRE_系统管理_产品管理_从业务使用方便考虑，需把【产品管理】功能，从账务系统移动到信贷审批系统，需求不做变动
 * 从jee_acc合并代码至jee_cre
-->
<!--
 * @reqno:H1509130047
 * @date-designer:2015年9月26日-songmin
 * @date-author:2015年9月26日-songmin:ACC_系统设置_系统设置_产品管理_产品费用_新增、修改
 *									新增、修改产品费用信息
 * @e-in-text:feeName-费用名称:费用名称
 * @e-in-text:feeTag-费用类型，参见字典表：FEE_TAG：1=费用；2=费率；
 * @e-in-text:feeRate-费用or费率:该列的值根据上面的费用类型feeTag而来
 * @e-in-text:delFlag-是否启用:参见字典表：yes_no
 * @e-ctrl:btnSubmit-保存:新增（修改）产品费用信息
 * @e-ctrl:btnCancel-返回:取消当前操作
-->
<!--
 * @reqno:H1509130045
 * @date-designer : 2015年9月26日 - songmin
 * @date-author : 2015年9月26日 - songmin:新加了必填*号标志，下拉列表取消默认值
 *	 			  2015年9月28日：限制feeRate长度最长为12位
-->
<html>
<head>
	<title>
		${not empty productFee.id?'修改':'新增'}费用信息
	</title>
	<meta name="decorator" content="default1"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$.validator.addMethod("feeRateValidator",function(){
				var reg = /^([1-9][\d]{0,14}|0)(\.[\d]{1,2})?$/g;
				if(reg.test($("#feeRate").val())){
					return true;
				} 
				return false;
			},"数据格式：整数位最多15位，小数位精确到小数点后2位");
			
			$("#inputForm").validate({
				rules:{
					feeRate:{
						feeRateValidator:true
					}
				},
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					//form.submit();
					var f = $("#inputForm").serialize();
					$.post("${ctx}/credit/product/fee/save",f,function(data){
						if("succ"==data){
							showTip("${not empty productFee.id?'修改':'新增'}费用信息成功！","success");
							setTimeout(function(){
								window.returnValue="succ";
								window.close();
							}, 1000);
						}else{
							alert("${not empty productFee.id?'修改':'新增'}费用信息时发生错误！")
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
	</script>
	<script type="text/javascript">
		function switchSpan(){
			var t = $("#fee_feeTag").val();
			if(t==2){//当选择的费用类型为费率时显示
				$("#fee_feeRate_opx").show();
			}else{
				$("#fee_feeRate_opx").hide();
			}
			if(""!=t){
				$("label[for='fee_feeTag']").hide();
			}
		}
	</script>
	<style type="text/css">
	</style>
</head>
<body>
	<div class="wrapper">
		<div class="infoList">
			<h3 class="infoListTitle">${not empty ProductFee.id?'修改':'新增'}费用信息</h3>
	        <div class="infoListCon">
	        	<div class="filter">
		        	<form:form id="inputForm" modelAttribute="ProductFee" action="${ctx}/credit/product/fee/save" 
						method="post">
						<form:hidden path="productId"/>
						<form:hidden path="id"/>
						<table class="fromTable">
							<tr>
								<td width="" class="ft_label">费用名称：</td>
								<td width="" class="ft_content">
									<form:input path="feeName" htmlEscape="false" maxlength="255" class="input-medium required"/>
									<font color="red">*</font>
								</td>
								
								<td width="" class="ft_label">费用类型名称：</td>
								<td width="" class="ft_content">
									<form:select path="feeTag" class="input-medium required"  id="fee_feeTag" onchange="switchSpan()">
										<form:option value=""  label="" />
										<form:options items="${fns:getDictList('FEE_TAG')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
									</form:select>
									<font color="red">*</font>
								</td>
							</tr>
							<tr>
								<td class="ft_label">费率or费：</td>
								<td class="ft_content">
									<%-- <form:input path="feeRate" htmlEscape="false" maxlength="12" class="input-medium required number"/> --%> 
									<input type="text" name="feeRate" id="feeRate" value="${ProductFee.feeRate}" class="input-medium required number" maxlength="18"/>
										<c:if test="${2==ProductFee.feeTag}">
											<b><span id="fee_feeRate_opx">%</span></b>
										</c:if>
										<c:if test="${2!=ProductFee.feeTag}">
											<b><span id="fee_feeRate_opx" style="display: none;">%</span></b>
										</c:if>
									<font color="red">*</font>
								</td>
								
								<td class="ft_label">是否启用：</td>
								<td class="ft_content">
									<form:select path="delFlag" class="input-medium required" >
										<form:options items="${fns:getDictList('yes_no')}"  itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
									<font color="red">*</font>
								</td>
							</tr>
						</table>
						<div class="searchButton" style="margin-top: 10px;">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
							<input id="btnCancel" class="btn" type="button" value="返 回" onclick="window.close();"/>
						</div>
					</form:form>
				</div>
	        </div>
	    </div>
	 </div>
</body>
</html>