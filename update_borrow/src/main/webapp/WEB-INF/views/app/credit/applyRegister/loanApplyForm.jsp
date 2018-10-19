<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page import="com.resoft.common.utils.Constants"%>
<html>
<head>
<title>借款申请信息</title>
<meta name="decorator" content="default" />
<c:if test="${true != readOnly}">
	<script type="text/javascript">
		$(document).ready(function() {
			var productCategory='${productCategory}';
			if(productCategory==null||productCategory==''){
				$(".ZGCategory").hide();
			}else{
				$(".ZGCategory").show();
			}
			adjustTextareaLength("channelOtherDesc","channelOtherDescPre");
			var applyNo=$("#applyNo").val().length;
			if(applyNo>32){
				alertx("申请编号过长，请重新进件或者联系管理员！");
			}
			if ('${applyInfo.isHaveAssure}' == '1') {
				parent.showPartGuarantorInfoTab();
			}else{
				parent.hideGuarantorInfoTab();
			}
			adjustTextareaLength("description", "pre");
			//"${applyInfo.applyAmount}"
			document.getElementById("applyAmount").value = outputmoney("${applyInfo.applyAmount}");
			//$("#applyAmount").value = outputmoney("${applyInfo.applyAmount}");
			$("#applyInfoForm").validate({
			rules : {},
			submitHandler : function(form) {
				var valueT = $("#applyAmount").val().replace(/,/g, "");
				$("#applyAmount").val(valueT);
				if(applyNo>32){
					alertx("申请编号过长，请重新进件或者联系管理员！");
					return;
				}
				loading();
				form.submit();
			},
			errorContainer : "#messageBox",
			errorPlacement : function(error, element) {
				checkReq(error, element);
			}
			});
		});
		function setPeriodValue() {
			var periodVal = $("#applyPeriodId").find("option:selected").text();
			$("#applyPeriodValue").val(periodVal);
		}
		function formate(o, blur) {
			o.value = o.value.replace(/[^\d]/g, '');
			if (!blur) {
				o.value = o.value.replace(/(\d{3}(?!,))/g, '$1,').replace(/,$/, '');
			}
		}
		
		
		function validateChoose(){
			var applyProductTypeCode=$("#applyProductTypeCode").val();
			if(applyProductTypeCode=="3"){
				var applyAmount=$("#applyAmount").val();
				var productCategoryValue=$("#productCategoryValue").val();
				applyAmount=applyAmount.replace(/,/g, "");
				applyAmount=parseFloat(applyAmount); 
				if(applyAmount>10000000){
					if(productCategoryValue=='6'){
						alertx("申请金额一千万以上经营贷产品不能是A类！");
						$("#applyAmount").val("");
					}
				}
			}
		}
	</script>
</c:if>
<c:if test="${true == readOnly}">
	<!-- 查看详细信息时生效 -->
	<script type="text/javascript">
		$(document).ready(function() {
			var productCategory='${productCategory}';
			if(productCategory==null||productCategory==''){
				$(".ZGCategory").hide();
			}else{
				$(".ZGCategory").show();
			}
			document.getElementById("applyAmount").value = outputmoney("${applyInfo.applyAmount}");
			$("input").attr("readOnly", "readOnly");
			$("textarea").attr("readOnly", "readOnly");
			disableSelect2();
			$("div[class='searchButton']").remove();
			$("font").remove();//由于页面的特殊性，所以这里直接将所有的fongt节点删除
		});
	</script>
</c:if>
<script type="text/javascript">
	function khdjxxClick() {
		$("#khdjxx").toggle(600);
	}

	function jksqxxClick() {
		$("#jksqxx").toggle(600);
	}
</script>
</head>
<body>
	<sys:message content="${message}" />
	<div class="">
		<div class="infoList">
			<h3 onclick="khdjxxClick()" class="infoListTitle">客户登记信息</h3>
			<div class="infoListCon">
				<div id="khdjxx" class="filter">
					<table class="fromTable">
						<tr>
							<td class="ft_label">客户名称：</td>
							<td class="ft_content">
								<input type="text" id="custName" class="input-medium" readonly="true" value="${applyRegister.custName}" />
							</td>
							<td class="ft_label">证件类型：</td>
							<td class="ft_content">
								<input type="text" id="idType" class="input-medium" readonly="true" value="${applyRegister.idTypeLabel}" />
							</td>
							<td class="ft_label">证件号：</td>
							<td class="ft_content">
								<input type="text" id="idNum" class="input-medium card" readonly="true" value="${applyRegister.idNum}" />
							</td>
						</tr>
						<tr>
							<td class="ft_label">企业名称：</td>
							<td class="ft_content">
								<input type="text" id="companyName" class="input-medium" readonly="true" value="${applyRegister.companyName}" />
							</td>
							<td class="ft_label">企业证件类型：</td>
							<td class="ft_content">
								<input type="text" id="comIdType" class="input-medium" readonly="true" value="${applyRegister.comIdTypeLabel}" />
							</td>
							<td class="ft_label">企业证件号：</td>
							<td class="ft_content">
								<input type="text" id="comIdNum" class="input-medium" readonly="true" value="${applyRegister.comIdNum}" />
							</td>
						</tr>
						<tr>
							<td class="ft_label">移动电话：</td>
							<td class="ft_content">
								<input type="text" id="mobile" class="input-medium mobile" readonly="true" value="${applyRegister.mobileNum}" />
							</td>
							<td class="ft_label">申请编号：</td>
							<td class="ft_content">
								<input type="text" id="applyNo" class="input-medium" readonly="true" value="${applyRegister.applyNo}" />
							</td>
							<td class="ft_label"></td>
							<td class="ft_content"></td>
						</tr>
						<tr>
							<td class="ft_label">登记员：</td>
							<td class="ft_content">
								<input type="text" id="registerName" class="input-medium" readonly="true" value="${applyRegister.registerName}" />
							</td>
							<td class="ft_label">登记门店：</td>
							<td class="ft_content">
								<input type="text" id="orgName" class="input-medium" readonly="true" value="${applyRegister.company.name}" />
							</td>
							<td class="ft_label">登记日期：</td>
							<td class="ft_content">
								<input type="text" id="registerDate" class="input-medium" readonly="true" value="<fmt:formatDate value='${applyRegister.registerDate}'  pattern='yyyy-MM-dd' />" />
							</td>
						</tr>
						<tr>
							<td class="ft_label">客户来源：</td>
							<td class="ft_content" colspan="5">
								<input type="text" id="channelSourceType" class="input-medium" readonly="true" value="${applyRegister.channelSourceTypeLabel}" />
							</td>
						</tr>
						
						<%-- <tr id="sameManager">
							<td width="13%" class="ft_label" >上笔与本笔业务经理是否一致：</td>
							<td class="ft_content">
								<input type="text" id="isSameManager" class="input-medium" readonly="true" value="${fns:getDictLabel(applyRegister.isSameManager, 'yes_no', '')}" />
							</td>
							<td width="13%" class="ft_label" id="reasonName1">不一致原因：</td>
							<td class="ft_content" colspan="3" id="reasonValue1">
								<input type="text" name="managerDifferReason" id="managerDifferReason" readonly="true"  class="input-medium required" maxlength="50" value="${applyRegister.managerDifferReason}" style="margin: 0px; position: relative; display: inline-block; vertical-align: middle; width: 420px"  />
							</td>
						</tr>
						<tr>
							<td width="13%" class="ft_label">登记门店与企业经营地在同一地级市：</td>
							<td class="ft_content">
								<input type="text" id="isSamePlace" class="input-medium" readonly="true" value="${fns:getDictLabel(applyRegister.isSamePlace, 'yes_no', '')}" />
							</td>
							<td width="13%" class="ft_label" id="reasonName2">不一致原因：</td>
							<td class="ft_content" colspan="3" id="reasonValue2">
								<input type="text" name="placeDifferReason" id="placeDifferReason"  readonly="true" class="input-medium required" maxlength="50" value="${applyRegister.placeDifferReason}" style="margin: 0px; position: relative; display: inline-block; vertical-align: middle; width: 420px"  />
							</td>
						</tr> --%>
						
						
						<tr>
							<td class="ft_label">渠道说明：</td>
							<td class="ft_content" colspan="5">
								<pre class="area-xxlarge pre-style"  id="channelOtherDescPre"></pre>
								<textarea id="channelOtherDesc" name="channelOtherDesc" readonly="readonly" rows="4" cols="3" maxlength="1000" class="area-xxlarge textarea-style required" >${applyRegister.channelOtherDesc}</textarea>
								<font color="red">*</font>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<form:form id="applyInfoForm" modelAttribute="applyInfo" action="${ctx}/credit/applyInfo/save" method="post">
			<div class="infoList">
				<h3 onclick="jksqxxClick()" class="infoListTitle">借款申请信息</h3>
				<%@ include file="/WEB-INF/views/app/credit/workflow/include/actTaskParams.jsp"%>
				<input type="hidden" name="id" value="${applyInfo.id}" />
				<div id="jksqxx" class="infoListCon">
					<div class="filter">
						<table class="fromTable">
							<tr>
								<td class="ft_label">产品类型：</td>
								<td class="ft_content">
									<input type="text" class="input-medium" readonly="true" value="${applyRegister.applyProductTypeLabel}" />
									<input type="hidden" id="applyProductTypeCode" name="applyProductTypeCode" value="${applyRegister.applyProductTypeCode }"/>
								</td>
								<td class="ft_label">产品名称：</td>
								<td class="ft_content">
									<input type="text" class="input-medium" readonly="true" value="${applyRegister.applyProductName}" class="input-medium" />
								</td>
								<td class="ft_label">产品期限(月)：</td>
								<td class="ft_content">
									<form:select path="applyPeriodId" class="input-medium required" onchange="setPeriodValue()">
										<form:option value="" label=""></form:option>
										<form:options items="${fns:getDictList('PRODUCT_PERIOD_VALUE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
									<form:hidden path="applyPeriodValue" />
									<font style="color: red">*</font>
								</td>
							</tr>
							<tr>
								<td class="ft_label">申请利率(%)：</td>
								<td class="ft_content">
									<form:input path="applyYearRate" onblur="this.value=rateMoney(this.value);" onkeyup="rateMax(this)" maxlength="5" cssClass="input-medium required" />
									<font style="color: red">*</font>
								</td>
								<td class="ft_label">申请服务费率(%)：</td>
								<td class="ft_content">
									<form:input path="applyServiceRate" onblur="this.value=rateMoney(this.value);" onkeyup="rateMax(this)" maxlength="5" cssClass="input-medium required" />
									<font style="color: red">*</font>
								</td>
								<td class="ft_label">客户类型：</td>
								<td class="ft_content">
									<form:select path="applyCustType" class="input-medium required error">
										<!-- <option value="">请选择</option>  -->
										<option value=""></option>
										<form:options items="${fns:getDictList('APPLY_CUST_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
									<font style="color: red">*</font>
								</td>
							</tr>
							<tr>
								<td class="ft_label">申请额度(元)：</td>
								<td class="ft_content">
									<input type="text" onkeyup="keyPress11(this);" maxlength="18" onblur="this.value=outputmoney(this.value);validateChoose();" class="input-medium required" id="applyAmount" name="applyAmount" value="${not empty applyInfo.applyAmount ? applyInfo.applyAmount : applyRegister.applyAmount}" />
									<font style="color: red">*</font>
								</td>
								<td class="ft_label">还款方式：</td>
								<td class="ft_content">
									<form:select path="loanRepayType" class="input-medium required">
										<form:option value=""></form:option>
										<form:options items="${loanRepayTypeList}" itemLabel="loanRepayDesc" itemValue="loanRepayType" htmlEscape="false" />
									</form:select>
									<font style="color: red">*</font>
								</td>
								<td class="ft_label">是否加急：</td>
								<td class="ft_content">
									<form:select path="isUrgent" class="input-medium required">
										<form:option value=""></form:option>
										<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
									<font style="color: red">*</font>
								</td>
							</tr>
							<tr>
								<td class="ft_label">借款用途：</td>
								<td class="ft_content">
									<form:select path="loanPurpost" class="input-medium required">
										<form:option value=""></form:option>
										<form:options items="${fns:getDictList('LOAN_PURPOST')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
									<font style="color: red">*</font>
								</td>
								<td class="ft_label">SV委托平台：</td>
								<td class="ft_content">
									<form:select path="svPlatform" class="input-medium required">
										<form:option value="" label=""></form:option>
										<form:options items="${fns:getDictList('SV_PLATFORM')}" itemLabel="label" itemValue="value" cssClass="" htmlEscape="false"/>
									</form:select>
									<font color="red">*</font>
								</td>
								<td class="ft_label">是否有担保人：</td>
								<td class="ft_content">
									<form:select path="isHaveAssure" class="input-medium required" >
										<form:option value="" label="请选择" />
										<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
									<font style="color: red">*</font>
								</td>
							</tr>
							<tr class="ZGCategory">
								<td width="13%" class="ft_label ZGCategory">产品分类：</td>
								<td class="ft_content ZGCategory">
									<input type="text" id="productCategory" class="input-medium" readonly="true" value="${productCategory}" />
									<input type="hidden" id="productCategoryValue" name="productCategoryValue" value="${productCategoryValue}">
									<font color="red">*</font>
								</td>
							</tr>
							<tr>
								<td class="ft_label">补充说明：</td>
								<td class="ft_content" colspan="5"  style="padding-bottom: 10px;">
									<pre class="area-xxlarge pre-style" id="pre"></pre>
									<form:textarea path="description" htmlEscape= "false" rows="4" maxlength="1000" class="area-xxlarge textarea-style required" onkeyup="adjustTextareaLength('description','pre')" />
									<font style="color: red">*</font>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div class="searchButton">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />
			</div>
		</form:form>
	</div>
</body>
</html>