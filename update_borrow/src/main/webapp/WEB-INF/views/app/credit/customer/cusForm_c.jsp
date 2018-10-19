<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!-- 
 * @reqno:H1512160006
 * @date-designer:2015年12月16日-songmin
 * @date-author:2015年12月16日-songmin:CRE_信贷审批_进件管理_企业客户登记_新增、修改
 * 	新增、修改企业客户登记信息；
 *	 企业客户信息在提交前可以多次进行修改保存操作，提交后，将不能再进行保存、提交操作；
 * @e-in-text:cusName-客户名称:客户名称
 * @e-in-list:applyProductTypeCode-产品类型:产品类型 applyProductTypeName产品类型名称 备用字段 字典PRODUCT_TYPE
 * @e-in-list:applyProductId-产品ID:产品ID applyProductName产品名称  备用字段 
 * @e-in-text:linkManName-联系人名称:联系人名称
 * @e-in-text:linkManIndentityId-联系人身份证号:联系人身份证号
 * @e-in-text:linkManMobile-联系人手机号:联系人手机号
 * @e-in-list:idType-证件类型:证件类型，字典CUSTOMER_C_ID_TYPE
 * @e-in-text:idNum-证件号码:证件号码
 * @e-in-text:idNumConfirm-证件号码确认:证件号码确认
 * @e-in-list:channelSourceType-客户来源:客户来源，字典CHANNEL_SOURCE_TYPE
 * @e-in-list:channelType-渠道:渠道，字典CHANNEL_TYPE
 * @e-in-text:bizDate-登记日期:系统赋值，用户不允许进行修改操作
 * @e-in-text:otherChannelSource-其他渠道:其他渠道
 * @e-in-text:otherChannelTypeDesc-其他渠道来源说明:其他渠道来源说明
 * @e-in-text:custType-客户类型:使用“2”表示企业客户
 * @e-in-text:status-状态:由保存、提交按钮赋值，1保存，2提交
 * 
 * @e-ctrl:btnSave-保存:保存企业客户信息
 * @e-ctrl:btnSubmit-提交:提交企业客户信息
 * @e-ctrl:btnCancel-返回:返回上次历史界面
 -->
 <!-- 
 * @reqno:H1512160007
 * @date-designer:2015年12月23日-songmin
 * @date-author:2015年12月23日-songmin:代码格式微调
 *	html中部分路径修改为个人处理路径
 *	产品信修改为通过登记信息实体类进行关联获取
 -->
<!-- 
@reqno:H1512210023
@date-designer:20151230-songmin
@date-author:20151230-songmin:CRE_信贷审批_进件管理_个人客户登记_功能重构
修改页面弹框模式
-->
<html>
	<head>
		<title>${not empty customer.id?'修改':'新增'}企业客户信息</title>
		<meta name="decorator" content="default"/>
		<script type="text/javascript">
			//保存时，设置状态为“登记保存”，并保存至数据库
			function saveForm(){
				$("#status").val('1');
				$("#post").val('1');
				top.$.jBox.tip.mess = null;
				$("#inputForm").submit(); 
			}
			//提交时，设置状态为“提交审批中”，并保存至数据库
			function postForm(){
				$("#status").val('2');
				$("#post").val('2');
				top.$.jBox.tip.mess = null;
				$("#inputForm").submit(); 
			}
			//字段非空校验
			$(document).ready(function() {
				$.validator.addMethod("mobileValidator", function(value, element) {
				  var length = value.length;
				  return this.optional(element) || (length == 11 && /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(value));
				}, "请正确填写您的手机号码");
				
				$("#inputForm").validate({
					rules: {
						linkManMobile:{
								mobileValidator : true
							}
					},
					messages: {
						idNumConfirm : {equalTo : "2次证件号码不一致"}
					},
					submitHandler: function(form){
						var post = $("#post").val();
						var idType = $("#idType").val();
						var idNum = $("#idNum").val();
						$.ajax({
							url:"${ctx}/credit/customer/validate?idType="+idType+'&idNum='+idNum+'&post='+post+'&custType=2',
							type:"POST",
							datatype:"json",
							success:function(data){
								if("status" == data.st){
									alertx("本客户已在系统中登记申请，处于审批中，不能重复申请！");
								}else if("blackName" == data.st){
									alertx("此证件号已被列入黑名单，无法登记！");
								}else{
									form.submit();
								}
							}
						});		
					},
				});
			});
			
			function showChannelType(){
				if($("#channelType").val() == '0'){
					$("#other_params").show();
				}else{
					$("#other_params").hide();
				}
			}
			
			//修改 下拉框的“必填信息”提示，在选中后仍无法消失的问题
			function validateMsg(){
				var $applyProductTypeCode = $("#applyProductTypeCode").find("option:checked").val();
				if(""!=$applyProductTypeCode && null!=$applyProductTypeCode){
					$("label[for='applyProductTypeCode']").hide();
				}
				//这里由于加载产品名称存在延迟，所以这里会取到undifind的情况
				var $applyProductId= $("#applyProductId").find("option:checked").val();
				if(""!=$applyProductId && null!=$applyProductId){
					$("label[for='applyProductId']").hide();
				}
				if(""!=$("#idType").find("option:checked").val()){
					$("label[for='idType']").hide();
				}
				if(""!=$("#channelSourceType").find("option:checked").val()){
					$("label[for='channelSourceType']").hide();
				}
				if(""!=$("#channelType").find("option:checked").val()){
					$("label[for='channelType']").hide();
				}
			}
			
			//选择产品类型后，自动加载可用产品列表数据
			function loadProductList(){
				var applyProductTypeCode = $("#applyProductTypeCode").val();
				$("#applyProductTypeName").val($("#applyProductTypeCode").find("option:selected").text());
				$("#s2id_applyProductId>.select2-choice>.select2-chosen").html("");
				
				$("#applyProductId").empty();
				if(null!=applyProductTypeCode && ""!=applyProductTypeCode){
					$.post("${ctx}/credit/product/coProductList",
							 {"productType":applyProductTypeCode,
							  "orgId" : '${orgId}'
							 },
							 function(data){
								var opstr = "<option value=''></option>";
								$.each(data,function(i,val){
									opstr+="<option value='"+val.id+"'>"+val.productName+"</option>";
								});
								$("#applyProductId").append(opstr);
							 }
						 );
				}
			}
			
			//选择产品后，补填产品信息
			function loadProductPeriod(){
				$("#applyProductName").val($("#applyProductId").find("option:selected").text());
			}
		</script>		
</head>

<body>
  <ul class="nav nav-tabs">
	 	<li><a href="${ctx}/credit/customer/list_c">企业客户登记</a></li>
	 	<li class="line"></li>
	 	<li class="active"><a href="${ctx}/credit/customer/edit_c?id=${customer.id}">企业客户登记${not empty customer.id?'修改':'新增'}</a></li>
 	</ul>
 	
	<div class="infoList">
	  <h3 class="searchTitle">客户信息</h3>
	    <div class="infoListCon">
				<form:form id="inputForm" modelAttribute="customer" action="${ctx}/credit/customer/save?id=${customer.id}" method="post" >
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					
					<input id="custType" name="custType" type="hidden" value="2"/><%--企业客户 --%>
					<input id="status" name="status" type="hidden" value=""/>
					<input type="hidden" name="post" id="post" /><%--这个是用于后台标识是“提交”操作 --%>
					<input id="applyInfo_id" name="applyInfo_id" type="hidden" value="${customer.applyInfo.id}"/>
					
					<div class="filter">
						<table class="fromTable">
							<tr>
								<td class="ft_label">客户名称：</td>
								<td class="ft_content">
									<form:input path="cusName" htmlEscape="false" maxlength="50" cssClass="required input-medium"/>
									<font color="red">*</font>
								</td>
								
								<td class="ft_label">产品类型：</td>
								<td class="ft_content">
									<form:select id="applyProductTypeCode" path="applyInfo.applyProductTypeCode" name="applyProductTypeCode" 
										class="input-medium required" onchange="loadProductList();validateMsg();" cssStyle="width:176px;">
										<form:option value="" label="" />
										<form:options items="${fns:getDictList('PRODUCT_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
									</form:select>
									<font color="red">*</font>
									<input type="hidden" name="applyProductTypeName" id="applyProductTypeName" value="${customer.applyInfo.applyProductTypeName}"/>
								</td>
								
								<td class="ft_label">产品名称：</td>
								<td class="ft_content">
									<select id="applyProductId" name="applyInfo.applyProductId" class="input-medium required" 
										onchange="loadProductPeriod();validateMsg();" style="width:176px;">
										<option value=""></option>
										<c:if test="${not empty productList}">
											<c:forEach items="${productList}" var="product">
												<option value='${product.id}' <c:if test="${product.id == customer.applyInfo.applyProductId}">selected="selected"</c:if>>
													${product.productName}
												</option>
											</c:forEach>
										</c:if>
									</select>
									<font color="red">*</font>
									<input type="hidden" id="applyProductName" name="applyProductName" value="${customer.applyInfo.applyProductName}"/>
								</td>
							</tr>
							
							<tr>
								<td class="ft_label">联系人名称：</td>
								<td class="ft_content">
									<form:input path="linkManName" htmlEscape="false" maxlength="50" cssClass="required input-medium"/>
									<font color="red">*</font>
								</td>
								
								<td class="ft_label">联系人身份证号：</td>
								<td class="ft_content">
									<form:input path="linkManIndentityId" htmlEscape="false" maxlength="18" cssClass="required input-medium card"/>
									<font color="red">*</font>
								</td>
								
								<td class="ft_label">联系人手机号：</td>
								<td class="ft_content">
									<form:input path="linkManMobile" htmlEscape="false" maxlength="11" cssClass="required digits input-medium"/>
									<font color="red">*</font>
								</td>
							</tr>
							
							<tr>
								<td class="ft_label">证件类型：</td>
								<td class="ft_content">
									<form:select id="idType" name="idType" path="idType" 
										cssClass="required input-medium" cssStyle="width:176px;">
										<form:option value="" label="" />
										<form:options items="${fns:getDictList('CUSTOMER_C_ID_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
									</form:select>
									<font color="red">*</font>
								</td>
								
								<td class="ft_label">证件号码：</td>
								<td class="ft_content">
									<form:input path="idNum" htmlEscape="false" maxlength="50" cssClass="required input-medium"/>
									<font color="red">*</font>
								</td>
								
								<td class="ft_label">证件号码确认：</td>
								<td class="ft_content">
									<form:input path="idNumConfirm" htmlEscape="false" maxlength="50" value="" equalTo="#idNum" cssClass="required input-medium"/>
									<font color="red">*</font>
								</td>
							</tr>
							
							<tr>
								<td class="ft_label">客户来源：</td>
								<td class="ft_content">
									<form:select id="channelSourceType" name="channelSourceType" path="channelSourceType" 
										cssClass="required input-medium" value="${customer.channelSourceType}" 
										cssStyle="width:176px;" onchange="validateMsg();">
										<form:option value="" label="" />
										<form:options items="${fns:getDictList('CHANNEL_SOURCE_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
									</form:select>
									<font color="red">*</font>
								</td>
								
								<td class="ft_label">渠道：</td>
								<td class="ft_content">
									<form:select id="channelType" name="channelType" cssClass="required input-medium" path="channelType" 
										onclick="showChannelType()" value="${customer.channelType}" cssStyle="width:176px;" onchange="validateMsg();">
										<form:option value="" label="" />
										<form:options items="${fns:getDictList('CHANNEL_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
									</form:select>
									<font color="red">*</font>
								</td>
								
								<td class="ft_label">登记日期：</td>
								<td class="ft_content">
									<input id="bizDate" name="bizDate" type="text"  maxlength="20" class="input-medium "
										value="<fmt:formatDate value='${customer.bizDate}' pattern='yyyy-MM-dd'"/>
								</td>
							</tr>
							<c:choose>
								<c:when test="${0 == customer.channelType}">
									<tr id="other_params" style="display: block;">
								</c:when>
								<c:otherwise>
									<tr id="other_params" style="display: none;">
								</c:otherwise>
							</c:choose>
								<td class="ft_label">其他渠道：</td>
								<td class="ft_content">
									<input id="otherChannelSource" name="otherChannelSource" type="text" maxlength="50" 
										class="required input-medium" value="${customer.otherChannelSource}"/>
									<font color="red">*</font>
								</td>
								
								<td class="ft_label">渠道来源说明：</td>
								<td class="ft_content">
									<input id="otherChannelTypeDesc" name="otherChannelTypeDesc" type="text" maxlength="50"
										 class="required input-medium" value="${customer.otherChannelTypeDesc}"/>
									<font color="red">*</font>
								</td>
								
								<td class="ft_label"></td>
								<td class="ft_content">
									
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<c:choose>
								<c:when test="${'1' == customer.status || empty customer.status}">
									<input id="btnSave" class="btn btn-primary" type="button" onclick="saveForm()" value="保存"/>&nbsp;
									<input id="btnSubmit" class="btn btn-primary" type="button" onclick="postForm()" value="提交"/>&nbsp;
								</c:when>
								<c:when test="${'1' ne customer.status}">
									<input id="btnSave" class="btn btn-primary" type="button" value="保存" disabled/>&nbsp;
									<input id="btnSubmit" class="btn btn-primary" type="button" value="提交" disabled/>&nbsp;
								</c:when>
							</c:choose>
							<input id="btnCancel" class="btn btn-primary" type="button" value="返回" onclick="window.location='${ctx}/credit/customer/list_c'"/>
						</div>
					</div>
				</form:form>
			</div>
		</div>
   </body>
</html>
