<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>新增个人客户信息</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	var checkSubmit="1";
	//保存时，将状态置为“登记保存”状态  
	function saveForm(applyStatus) {
		if(checkSubmit=="1"){
			checkSubmit="2";
			loading();
			$("#applyStatus").val(applyStatus);
			top.$.jBox.tip.mess = null;
			var formJson = $("#detailForm").serializeJson();
			$.post("${ctx}/credit/applyRegister/save", formJson, function(data) {
				if (data) {
					closeTip();
					if (data.status == 1) {//保存成功
						alertx(data.message, function() {
							parent.$("#searchForm").submit();
							closeJBox();
						});
					} else {
						alertx(data.message);
					}
				}
			});
		}
	}

	//
	function setDisabledTime(id, time) {
        var btn = $("#" + id);
        var originBtnValue = btn.val();
        btn.fadeIn(1000);
        var hander = setInterval(function () {
            if (time <= 0) {
                clearInterval(hander);
                btn.removeAttr("disabled");
            } else {
            	time--;
                btn.attr({ "disabled": "disabled" });
            }
        }, 1000);
    };
	
	//提交时，首先进行验证
	function validate(applyStatus) {
		top.$.jBox.tip.mess = null;
		var formJson = $("#detailForm").serializeJson();
		$.post("${ctx}/credit/applyRegister/validate", formJson, function(data) {
			if (data) {
				if (data.status == '1') {//校验成功
					saveForm(applyStatus);
				} else if (data.status == 'confirmRejected') {//如果被拒件，校验是否在还款中，是否是担保人
					confirmx(data.message, function() {
						validateRepaymentAndGuarantor(applyStatus);
					});
				} else if (data.status == 'confirmRepayment') {//如果在还款中，校验是否是担保人
					confirmx(data.message, function() {
						validateGuarantor(applyStatus);
					});
				} else if (data.status == 'confirmGuarantor') {//如果是担保人，确认提交后，提交数据
					confirmx(data.message, function() {
						saveForm(applyStatus);
					});
				} else if (data.status == '0') {//校验失败
					alertx(data.message);
				} else {
					alertx(data.message);
				}
			}
		});
	}
	//字段非空校验
	$(document).ready(function() {
		/* if('${applyRegister.channelSourceType}'=='5'||'${applyRegister.channelSourceType}'=='8'){
			$("#isSameManager").addClass("required");
			if('${applyRegister.isSameManager}'=='0'){
				$("#managerDifferReason").addClass("required");
			}else{
				$("#managerDifferReason").removeClass("required");
			}
		}else{
			$("#isSameManager").removeClass("required");
		}
		if('${applyRegister.isSamePlace}'=='0'){
			$("#placeDifferReason").addClass("required");
		}else{
			$("#placeDifferReason").removeClass("required");
		}*/
		controlComRequired('${applyRegister.custType}');
		$("#IdNumCheck").hide();
		$("#IdNameCheck").hide();
		//初始化债股结合分类
		var isShowCategory='${applyRegister.applyProductTypeCode}';
		if(isShowCategory=='6'){
			$(".ZGCategory").show();
		}else{
			$("#productCategory").val("");
			$(".ZGCategory").hide();
		}
		adjustTextareaLength("channelOtherDesc", "channelOtherDescPre");
		$.validator.addMethod("mobileValidator", function(value, element) {
			var length = value.length;
			return this.optional(element) || (length == 11 && /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(166)|(17[0-9]{1})|(18[0-9]{1})|(19[8,9]{1}))+\d{8})$/.test(value));
		}, "请正确填写您的手机号码");
		$("#detailForm").validate({
		rules : {
			mobileNum : {
				mobileValidator : true
			}
		},
		messages : {
			idNumConfirm : {
				equalTo : "2次证件号码不一致"
			}
		},
		submitHandler : function() {
			var applyStatus = $("#applyStatus").val();
			//申请金额(元)
			var applyAmount = $("#applyAmount").val().replace(/,/g, "");
			$("#applyAmount").val(applyAmount);
			if (applyStatus == "1") {
				saveForm(applyStatus);
			} else if (applyStatus == "2") {
				validate(applyStatus);
			}
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});
	});

	function showChannelType() {
		if ($("#channelType").val() == '0') {
			$("#otherChannelSourceTr").show();
			$("#otherChannelTypeDescTr").show();
		} else {
			$("#otherChannelSourceTr").hide();
			$("#otherChannelTypeDescTr").hide();
			//隐藏时同时清空其他渠道表单数据
			$("#otherChannelSource").val("");
			$("#channelOtherDesc").val("");
		}
	}

	//修改 下拉框的“必填信息”提示，在选中后仍无法消失的问题
	function validateMsg() {
		if ("" != $("#idType").find("option:checked").val()) {
			$("label[for='idType']").hide();
		}
		if ("" != $("#channelSourceType").find("option:checked").val()) {
			$("label[for='channelSourceType']").hide();
		}
		if ("" != $("#channelType").find("option:checked").val()) {
			$("label[for='channelType']").hide();
		}
	}
	
	/*function showSameManager(val){
		if(val=='5'||val=='8'){//为循坏或者置换贷
			$("#isSameManager").addClass("required");
		}else{
			$("#isSameManager").removeClass("required");
		}
	}*/
	
	/*function showReason(val,flag){
		if(flag=='1'){
			if(val=='0'){
				$("#managerDifferReason").addClass("required");
			}else{
				$("#managerDifferReason").removeClass("required");
			}
		}
		if(flag=='2'){
			if(val=='0'){
				$("#placeDifferReason").addClass("required");
			}else{
				$("#placeDifferReason").removeClass("required");
			}
		}
	}*/

	//选择产品类型后，自动加载可用产品列表数据
	function loadProductList() {
		//初始化债股结合分类
		var applyProductTypeCode=$("#applyProductTypeCode").val();
		if(applyProductTypeCode=='6'){
			$(".ZGCategory").show();
		}else{
			$("#productCategory").val("");
			$(".ZGCategory").hide();
		}
		var applyProductTypeCode = $("#applyProductTypeCode").val();
		var custType = $("#custType").val();
		$("#applyProductTypeName").val($("#applyProductTypeCode").find("option:selected").text());
		$("#s2id_applyProductId>.select2-choice>.select2-chosen").html("");

		$("#applyProductId").empty();
		if (null != applyProductTypeCode && "" != applyProductTypeCode && null != custType && "" != custType) {
			$.post("${ctx}/credit/product/applyProductList", {
			"productType" : applyProductTypeCode,
			"orgId" : '${orgId}',
			"custType":custType
			}, function(data) {
				var opstr = "<option value=''></option>";
				$.each(data, function(i, val) {
					opstr += "<option value='"+val.id+"'>" + val.productName + "</option>";
				});
				$("#applyProductId").append(opstr);
			});
		}
	}
	//选择产品后，补填产品信息
	function loadProductPeriod() {
		$("#applyProductName").val($("#applyProductId").find("option:selected").text());
	}
	function onChangeIdType() {
		if ("1" == $("#idType").val()) {
			$("#idNum").addClass("card");
			$("#idNumConfirm").addClass("card");
		} else {
			$("#idNum").removeClass("card");
			$("#idNumConfirm").removeClass("card");
		}
	}
	function beforeSubmit(applyStatus) {
		if(applyStatus == '1'){
			setDisabledTime('btnSave',3);
		}else if(applyStatus == '2'){
			setDisabledTime('btnSubmit',3);
		}
		$("#applyStatus").val(applyStatus);
		$("#detailForm").submit();
	}
	function showIdNum() {
		$("#IdNumCheck").show(500);
		$("#IdNameCheck").show(500);
	}

	function controlComRequired(value) {
		if ("1" == value) {
			$("[name='fontName']").hide();
			$("#companyName").removeClass("required");
			$("#comIdType").removeClass("required");
			$("#comIdNum").removeClass("required");
			document.getElementById("companyName").style.backgroundColor = "";
			document.getElementById("comIdType").style.backgroundColor = "";
			document.getElementById("comIdNum").style.backgroundColor = "";
		} else {
			$("[name='fontName']").show();
			$("#companyName").addClass("required", "true");
			$("#comIdType").addClass("required", "true");
			$("#comIdNum").addClass("required", "true");
		}
	}

	function validateRepaymentAndGuarantor(applyStatus) {
		top.$.jBox.tip.mess = null;
		var formJson = $("#detailForm").serializeJson();
		$.post("${ctx}/credit/applyRegister/validateRepaymentAndGuarantor", formJson, function(data) {
			if (data) {
				if (data.status == '1') {//校验成功
					saveForm(applyStatus);
				} else if (data.status == 'confirmRepayment') {//此客户在还款中，还需校验是否是担保人
					confirmx(data.message, function() {
						validateGuarantor(applyStatus);
					});
				} else if (data.status == 'confirmGuarantor') {//如果是担保人，确认提交后，提交数据
					confirmx(data.message, function() {
						saveForm(applyStatus);
					});
				} else if (data.status == '0') {//校验失败
					alertx(data.message);
				} else {
					alertx(data.message);
				}
			}
		});
	}

	function validateGuarantor(applyStatus) {
		top.$.jBox.tip.mess = null;
		var formJson = $("#detailForm").serializeJson();
		$.post("${ctx}/credit/applyRegister/validateGuarantor", formJson, function(data) {
			if (data) {
				if (data.status == '1') {//校验成功
					saveForm(applyStatus);
				} else if (data.status == 'confirmGuarantor') {//如果是担保人，确认提交后，提交数据
					confirmx(data.message, function() {
						saveForm(applyStatus);
					});
				} else if (data.status == '0') {//校验失败
					alertx(data.message);
				} else {
					alertx(data.message);
				}
			}
		});
	}
	
	function validateChoose(){
		var applyAmount=$("#applyAmount").val();
		var productCategory=$("#productCategory").val();
		if(applyAmount==null&&applyAmount==''&&productCategory==null&&productCategory==''){
			return ;
		}
		applyAmount=applyAmount.replace(/,/g, "");
		applyAmount=parseFloat(applyAmount); 
		if(applyAmount>10000000){
			if(productCategory=='3'){
				alertx("申请金额一千万以上经营贷产品不能是A类！");
				$("#applyAmount").val("");
			}
		}
	}
</script>
</head>
<body>
	<div class="searchInfo">
		<h3 class="searchTitle">客户信息</h3>
		<div class="searchCon">
			<form:form id="detailForm" modelAttribute="applyRegister" action="#" method="post">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
				<input type="hidden" id="applyNo" name="applyNo" value="${applyRegister.applyNo}" />
				<input type="hidden" name="applyStatus" id="applyStatus" />
				<div class="filter">
					<sys:message content="${message}" />
					<form:hidden path="id" />
					<table class="fromTable">
						<tr>
							<td width="13%" class="ft_label">客户名称：</td>
							<td class="ft_content" align="left">
								<form:input path="custName" htmlEscape="false" maxlength="20" onblur="this.value=reSpaces(this.value);" style="width:150px;" cssClass="input-medium required" />
								<font color="red">*</font>
							</td>
							<td width="13%" class="ft_label">产品类型：</td>
							<td class="ft_content">
								<form:select id="applyProductTypeCode" path="applyProductTypeCode" name="applyProductTypeCode" class="input-medium required" onchange="loadProductList();validateMsg();" cssStyle="width:164px;">
									<form:option value="" label="" />
									<form:options items="${fns:getDictList('PRODUCT_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
								</form:select>
								<font color="red">*</font>
								<input type="hidden" name="applyProductTypeName" id="applyProductTypeName" />
							</td>
							<td width="13%" class="ft_label">产品名称：</td>
							<td class="ft_content">
								<form:select id="applyProductId" path="applyProductId" class="input-medium required" cssStyle="width:164px;" onchange="loadProductPeriod();validateMsg();">
									<form:option value=""></form:option>
									<form:options items="${productList }" htmlEscape="false" itemLabel="productName" itemValue="id" />
								</form:select>
								<font color="red">*</font>
								<input type="hidden" id="applyProductName" name="applyProductName" value="${applyRegister.applyProductName}" />
							</td>
						</tr>
						<tr>
							<td width="13%" class="ft_label">移动电话：</td>
							<td class="ft_content" align="left">
								<form:input path="mobileNum" htmlEscape="false" maxlength="11" style="width:150px;" cssClass="mobile input-medium required" />
								<font color="red">*</font>
							</td>
							<td width="13%" class="ft_label">申请金额(元)：</td>
							<td class="ft_content">
								<input id="applyAmount" name="applyAmount" type="text" onkeyup="keyPress11(this);" style="width:150px;" onblur="this.value=outputmoney(this.value);validateChoose();" value="<fmt:formatNumber value="${applyRegister.applyAmount}" pattern="###,##0.00"/>" htmlEscape="false" maxlength="18" class="required input-medium" />
								<font color="red">*</font>
							</td>
							<td width="13%" class="ft_label">客户类型</td>
							<td class="ft_content">
								<form:select path="custType" class="input-medium required" cssStyle="width:164px;" onchange="loadProductList();controlComRequired(this.value);">
									<form:option value=""></form:option>
									<form:options items="${fns:getDictList('CUST_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
								</form:select>
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td width="13%" class="ft_label">企业名称：</td>
							<td class="ft_content" align="left">
								<form:input path="companyName" htmlEscape="false" maxlength="20" onblur="this.value=reSpaces(this.value);" style="width:150px;" cssClass="input-medium required" />
								<font name="fontName" color="red">*</font>
							</td>
							<td width="13%" class="ft_label">企业证件类型：</td>
							<td class="ft_content">
								<input type="text" value="统一社会信用代码" readonly="readonly" htmlEscape="false" maxlength="20" style="width:150px;" cssClass="input-medium" />
								<input type="hidden" id="comIdType" name="comIdType" value="unSocCreditNo">
								<%-- <form:select path="comIdType" class="input-medium required" cssStyle="width:164px;">
									<form:option value="" label="" />
									<form:options items="${fns:getDictList('COM_ID_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
								</form:select> --%>
								<font name="fontName" color="red">*</font>
							</td>
							<td width="13%" class="ft_label">企业证件号：</td>
							<td class="ft_content">
								<form:input path="comIdNum" htmlEscape="false" maxlength="50" style="width:150px;" class="required input-medium" />
								<font name="fontName" color="red">*</font>
							</td>
						</tr>
						<tr>
							<td width="13%" class="ft_label">证件类型：</td>
							<td class="ft_content">
								<form:select id="idType" name="idType" path="idType" class="input-medium required" value="${applyRegister.idType}" cssClass="required input-medium" cssStyle="width:164px;" onchange="validateMsg();onChangeIdType();">
									<form:option value="" label="" />
									<form:options items="${fns:getDictList('CUSTOMER_P_ID_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
								</form:select>
								<font color="red">*</font>
							</td>
							<td width="13%" class="ft_label">证件号码：</td>
							<td class="ft_content">
								<form:input path="idNum" htmlEscape="false" onchange="showIdNum()" maxlength="50" style="width:150px;" class="required input-medium card" />
								<font color="red">*</font>
							</td>
							<td width="13%" class="ft_label" id="IdNameCheck">证件号码确认：</td>
							<td class="ft_content" id="IdNumCheck">
								<input type="text" style="width: 150px;" value="${idNumConfirm}" equalTo="#idNum" class="required input-medium card" />
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td width="13%" class="ft_label">客户来源：</td>
							<td class="ft_content">
								<form:select id="channelSourceType" name="channelSourceType" path="channelSourceType" cssClass="required input-medium" value="${applyRegister.channelSourceType}" cssStyle="width:164px;" onchange="validateMsg();">
									<form:option value="" label="" />
									<form:options items="${fns:getDictList('CHANNEL_SOURCE_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
								</form:select>
								<font color="red">*</font>
							</td>
							<td width="13%" class="ft_label">登记日期：</td>
							<td class="ft_content">
								<input id="registerDate" name="registerDate" type="text" maxlength="20" readonly="true" class="input-medium" style="width: 150px;" value="<fmt:formatDate value='${applyRegister.registerDate}' pattern='yyyy-MM-dd'/>" />
							</td>
							
							<td width="13%" class="ft_label ZGCategory">产品分类：</td>
							<td class="ft_content ZGCategory">
								<form:select id="productCategory" path="productCategory" name="productCategory" onchange="validateChoose()" class="input-medium required" cssStyle="width:164px;">
									<form:option value="" label="" />
									<form:options items="${fns:getDictList('product_category')}" itemLabel="label" itemValue="value" htmlEscape="false" />
								</form:select>
								<font color="red">*</font>
							</td>
						</tr>
						
						<!-- 在合规改造时注释下面四个字段 -->
						<%-- <tr>
							<td width="13%" class="ft_label">上笔与本笔业务经理是否一致：</td>
							<td class="ft_content">
								<form:select id="isSameManager" name="isSameManager" path="isSameManager" class="input-xlarge" value="${applyRegister.isSameManager}" cssStyle="width:164px;" onchange="showReason(this.value,1);">
									<form:option value="" label="" />
									<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
								</form:select>
							</td>
							<td width="13%" class="ft_label">不一致原因：</td>
							<td class="ft_content" colspan="3">
								<input type="text" name="managerDifferReason" id="managerDifferReason"  class="input-medium" maxlength="50" value="${applyRegister.managerDifferReason}" style="margin: 0px; position: relative; display: inline-block; vertical-align: middle; width: 420px"  />
							</td>
						</tr>
						<tr>
							<td width="13%" class="ft_label">登记门店与企业经营地在同一地级市：</td>
							<td class="ft_content">
								<form:select id="isSamePlace" name="isSamePlace" path="isSamePlace" class="required input-xlarge" value="${applyRegister.isSamePlace}" cssStyle="width:164px;" onchange="showReason(this.value,2);">
									<form:option value="" label="" />
									<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
								</form:select>
								<font color="red">*</font>
							</td>
							<td width="13%" class="ft_label">不一致原因：</td>
							<td class="ft_content" colspan="3">
								<input type="text" name="placeDifferReason" id="placeDifferReason"  class="input-medium" maxlength="50" value="${applyRegister.placeDifferReason}" style="margin: 0px; position: relative; display: inline-block; vertical-align: middle; width: 420px"  />
							</td>
						</tr> --%>
						<tr>
							<td class="ft_label">渠道说明：</td>
							<td class="ft_content" colspan="5">
								<pre class="area-xxlarge pre-style" id="channelOtherDescPre"></pre>
								<textarea id="channelOtherDesc" name="channelOtherDesc" rows="4" cols="3" maxlength="1000" class="area-xxlarge textarea-style required" onkeyup="adjustTextareaLength('channelOtherDesc','channelOtherDescPre')">${applyRegister.channelOtherDesc}</textarea>
								<font color="red">*</font>
							</td>
						</tr>
					</table>
					<div class="searchButton">
						<tr>
							<td>
								<c:choose>
									<c:when test="${'1'==applyRegister.applyStatus || empty applyRegister.applyStatus}">
										<input id="btnSave" class="btn btn-primary" type="button" onclick="beforeSubmit('1')" value="保存" />&nbsp;
											<input id="btnSubmit" class="btn btn-primary" type="button" onclick="beforeSubmit('2');" value="提交" />&nbsp;
										</c:when>
									<c:when test="${'1' ne applyRegister.applyStatus}">
										<input id="btnSave" class="btn btn-primary" type="button" value="保存" disabled />&nbsp;
											<input id="btnSubmit" class="btn btn-primary" type="button" value="提交" disabled />&nbsp;
										</c:when>
								</c:choose>
								<input id="btnCancel" class="btn btn-primary" type="button" value="返回" onclick="closeJBox();" />
							</td>
						</tr>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>
