<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!-- 
 * @reqno:H1511130067
 * @date-designer:20151116-chenshaojia
 * @date-author:20151116-chenshaojia:CRE_系统管理_产品管理_从业务使用方便考虑，需把【产品管理】功能，从账务系统移动到信贷审批系统，需求不做变动
 * 从jee_acc合并代码至jee_cre
-->
<!--
 * @reqno:H1509130045
 * @date-designer : 2015年9月26日 - songmin
 * @e-in-text : periodValue - 期限值 : 期限值
 * @e-in-list : periodType - 期限值类型 : 期限值类型
 * @e-in-text : yearRate - 年利率 : 年利率
 * @e-in-list : delFlag - 是否启用: 期限值类型
 * @e-in-text : remarks - 备注 : 备注
 * @e-ctrl : btnSubmit - 保 存 : 保存、修改产品期限信息
 * @e-ctrl : btnCancel - 返回 : 关闭产品期限信息新增或修改页面
 * @date-author : 2015年9月26日 - songmin:ACC_系统设置_系统设置_产品管理_产品期限_新增、修改
 * 				  2015年9月28日：添加利率长度和利率最大值的限制
-->
<html>
<head>
	<title>
		${not empty productPeriod.id?'修改':'新增'}期限信息
	</title>
	<meta name="decorator" content="default1"/>
	<c:if test="${empty productPeriod.id}">
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				rules:{
					yearRate:{
						max:999.999
					}
				},
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					//form.submit();
					var f = $("#inputForm").serialize();
					$.post("${ctx}/credit/product/period/save",f,function(data){
						if("succ"==data){
							showTip("新增期限信息成功！","success");
							setTimeout(function(){
								window.returnValue="succ";
								window.close();
							}, 1000);
						}else{
							alert("新增期限信息时发生错误！")
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
	</c:if>
	<c:if test="${not empty productPeriod.id}">
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					//form.submit();
					var f = $("#inputForm").serialize();
					$.post("${ctx}/credit/product/period/save",f,function(data){
						if("succ"==data){
							showTip("修改期限信息成功！","success");
							setTimeout(function(){
								window.returnValue="succ";
								window.close();
							}, 1000);
						}else{
							alert("修改期限信息时发生错误！")
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
	</c:if>
	<script type="text/javascript">
		//修改 下拉框的“必填信息”提示，在选中后仍无法消失的问题
		function validatePeriodType(){
			if(""!=$("#periodType").find("option:checked").val()){
				$("label[for='periodType']").hide();
			}
			if(""!=$("#delFlag").find("option:checked").val()){
				$("label[for='delFlag']").hide();
			}
		}
	</script>
</head>
<!-- 
 * @reqno:H1511130067
 * @date-designer:20151118-chenshaojia
 * @date-author:20151118-chenshaojia: 修复ACC系统中controller 命名错误导致的 产品期限列表加载不出问题 将peroid 改为 period
-->
<body>
	<div class="wrapper">
		<sys:message content="${message}"/>
		<div class="infoList">
	    	<h3 class="infoListTitle">${not empty productPeriod.id?'修改':'新增'}期限信息</h3>
	        <div class="infoListCon">
	        	<div class="filter">
		        	<form:form id="inputForm" modelAttribute="productPeriod" action="${ctx}/credit/product/period/save" 
						method="post">
						<form:hidden path="productId"/>
						<form:hidden path="id"/>
						<table class="fromTable">
							<tr>
								<td  class="ft_label">期限值：</td>
								<td  class="ft_content">
									<form:input path="periodValue" htmlEscape="false" maxlength="10" class="input-medium required digits"/>
									<font color="red">*</font>
								</td>
								
								<td  class="ft_label">期限值类型：</td>
								<td  class="ft_content">
									<form:select path="periodType" class="input-medium required" onchange="validatePeriodType();">
										<form:option value=""></form:option>
										<form:options items="${fns:getDictList('PRODUCT_PERIOD_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
									<font color="red">*</font>
								</td>
								
							</tr>
							<tr>
								<td class="ft_label">年利率：</td>
								<td class="ft_content">
									<form:input path="yearRate" htmlEscape="false" maxlength="7" class="input-medium required number"/> <span s><b>（%）</b></span>
									<font color="red">*</font>
								</td>
								<td class="ft_label">是否启用：</td>
								<td class="ft_content" colspan="">
									<form:select path="delFlag" class="input-medium required" onchange="validatePeriodType();">
										<form:option value=""></form:option>
										<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
									</form:select>
									<font color="red">*</font>
								</td>
							</tr>
							<tr>
								<td class="ft_label">备注：</td>
								<td class="ft_content" colspan="3">
									<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="1000" class="input-xxlarge "/>
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