<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<title>车辆抵质押物信息管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
	 	$("div[id='personal']").hide();
		$("div[id='company']").hide(); 
		if ('${readonly}' != "") {
			$("#btnSubmit").hide();
		}
		//$("#name").focus();
		$("#inputCarForm").validate({
		submitHandler : function(form) {
			saveForm();
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});
		propertySelect();
		document.getElementById("travelKms").value = outputmoney("${mortgageCarEvaluateInfo.travelKms}");
		document.getElementById("netPurchasePrice").value = outputmoney("${mortgageCarEvaluateInfo.netPurchasePrice}");
		document.getElementById("marketPrice1").value = outputmoney("${mortgageCarEvaluateInfo.marketPrice1}");
		document.getElementById("marketPrice2").value = outputmoney("${mortgageCarEvaluateInfo.marketPrice2}");
		document.getElementById("marketPrice3").value = outputmoney("${mortgageCarEvaluateInfo.marketPrice3}");
		document.getElementById("carEvaluatePrice").value = outputmoney("${mortgageCarEvaluateInfo.carEvaluatePrice}");

        adjustTextareaLength("otherInformation","preOtherInformation");
	});

	function loadCity() {
		//重置市、县下拉框
		$("#operateCity").empty();
		$("#operateCity").append("<option value=''>请选择</option>");
		var $operateCity = $("#s2id_operateCity>.select2-choice>.select2-chosen");
		$operateCity.html("请选择");
		$("#operateDistinct").empty();
		$("#operateDistinct").append("<option value=''>请选择</option>");
		var $operateDistinct = $("#s2id_operateDistinct>.select2-choice>.select2-chosen");
		$operateDistinct.html("请选择");
		$.post("${ctx}/sys/area/treeNode", {
			parentId : $("#operateProvince").val()
		}, function(data) {
			$.each(data, function(i, val) {
				$("#operateCity").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
			});
		});
	}

	function loadDistinct(val) {
		$("#operateDistinct").empty();
		$("#operateDistinct").append("<option value=''>请选择</option>");
		var $operateDistinct = $("#s2id_regDistinct>.select2-choice>.select2-chosen");
		$operateDistinct.html("请选择");
		$.post("${ctx}/sys/area/treeNode", {
			parentId : $("#operateCity").val()
		}, function(data) {
			$.each(data, function(i, val) {
				$("#operateDistinct").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
			});
		});
	}
	//根据产权属性的不同，分别显示个人车辆信息和企业车辆信息
	function propertySelect() {
		var value = $('#propertyRight').val();
		if (value == '1') {
			$("div[id='company']").hide();
			$("div[id='personal']").show();
		} else if (value == '2') {
			$("div[id='personal']").hide();
			$("div[id='company']").show();
		}
	}

	$(document).ready(function() {
	});

	function saveForm() {
		var valueT1 = $("#netPurchasePrice").val().replace(/,/g, "");
		$("#netPurchasePrice").val(valueT1);
		var valueT2 = $("#marketPrice1").val().replace(/,/g, "");
		$("#marketPrice1").val(valueT2);
		var valueT3 = $("#marketPrice2").val().replace(/,/g, "");
		$("#marketPrice2").val(valueT3);
		var valueT4 = $("#marketPrice3").val().replace(/,/g, "");
		$("#marketPrice3").val(valueT4);
		var valueT5 = $("#carEvaluatePrice").val().replace(/,/g, "");
		$("#carEvaluatePrice").val(valueT5);
		var valueT6 = $("#travelKms").val().replace(/,/g, "");
		$("#travelKms").val(valueT6);
		loading();
		top.$.jBox.tip.mess = null;
		var formJson = $("#inputCarForm").serializeJson();
		$.post("${ctx}/postloan/mortgageCarEvaluateInfo/save", formJson, function(data) {
			if (data) {
			closeTip();
				if (data.status == 1) {//保存成功
					alertx(data.message, function() {
						parent.$.loadDiv("mortgageCarEvaluate", "${ctx}/postloan/mortgageCarEvaluateInfo/toCarEvaluateIndex?applyNo="+data.data,"post");
						closeJBox();
					});
				} else {
					alertx(data.message);
				}
			}
		});
	}
	//编辑
     function showEdit(id){
        var width = $(window).width() - 100;
	   	var height = $(window).height()-100;
	    var heightTrue=Math.min(height,320);
		openJBox(null,"${ctx}/postloan/mortgageEvaluateInfo/showEdit?id="+id,"编辑检查项目",width,heightTrue);
	}
	
</script>

	<form:form id="inputCarForm" modelAttribute="mortgageCarEvaluateInfo"  method="post" class="form-horizontal">
		<sys:message content="${message}" />
		 <input type="hidden" id="mortgageCarInfo.applyNo" name="mortgageCarInfo.applyNo" value="${mortgageCarInfo.applyNo}" />
		<h3 class="searchTitle">车辆抵质押物信息</h3>
		<div>
			<div class="filter">
			<pre class="input-xxlarge pre-style textareaWidth"  id="preOtherInformation"></pre>
				<table class="fromtable">
					<tr>
						<td class="ft_label">产权属性：</td>
						<td class="ft_content">
							<form:select path="mortgageCarInfo.propertyRight" id="propertyRight" class="input-medium required" disabled="${readonly}" onclick="propertySelect();">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('PROPERTY_RIGHT')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
						</td>
						<td class="ft_label">产权人姓名：</td>
						<td class="ft_content">
							<form:input path="mortgageCarInfo.propertyName" name="mortgageCarInfo.propertyName" htmlEscape="false" maxlength="20" class="input-medium required" readonly="${readonly}" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">购置日期：</td>
						<td class="ft_content">
							<input id="mortgageCarInfo.buyDate" name="mortgageCarInfo.buyDate" type="text" name="mortgageCarInfo.buyDate" maxlength="20" class="input-medium Wdate required"  value="<fmt:formatDate value="${mortgageCarInfo.buyDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd',maxDate:new Date(),isShowClear:false});" />
						</td>
					</tr>
					<tr>
						<td class="ft_label">车辆品牌：</td>
						<td class="ft_content">
							<form:input path="mortgageCarInfo.vehicleBrand" name="mortgageCarInfo.vehicleBrand" htmlEscape="false" maxlength="10" class="input-medium required" readonly="${readonly}" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">车辆类型：</td>
						<td class="ft_content">
							<form:input path="mortgageCarInfo.vehicleType" name="mortgageCarInfo.vehicleType" htmlEscape="false" maxlength="10" class="input-medium required" readonly="${readonly}" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">车辆系列：</td>
						<td class="ft_content">
							<form:input path="mortgageCarInfo.vehicleSeries" name="mortgageCarInfo.vehicleSeries" htmlEscape="false" maxlength="50" class="input-medium required" readonly="${readonly}" />
							<font color="red">*</font>
						</td>
					</tr>
					 <tr>
						<td class="ft_label">抵押质押类型：</td>
						<td class="ft_content">
							 <form:select path="mortgageCarInfo.mortgageType" id = "mortgageType" class="input-medium required" disabled="${readonly}">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('MORTGAGE_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
						</td>
					</tr>
				</table>
			</div>
			<div class="filter" id="personal">
				<h3 class="infoListTitle">个人车辆车主信息</h3>
				<table class="fromTable">
					<tr>
						<td class="ft_label">财产共有人姓名：</td>
						<td class="ft_content">
							<form:input path="mortgageCarInfo.propertyOfComOwners" name="mortgageCarInfo.propertyOfComOwners" htmlEscape="false" maxlength="20" class="input-medium required" readonly="${readonly}" />
						</td>
						<td colspan="2"></td>
					</tr>
					<tr>
						<td class="ft_label">其他信息：</td>
						<td class="ft_content" colspan="5">
							<form:textarea path="mortgageCarInfo.otherInformation" name="mortgageCarInfo.otherInformation" id="otherInformation" htmlEscape="false" rows="4" maxlength="1000" class="input-xxlarge textarea-style textareaWidth required" readonly="${readonly}" />
						</td>
					</tr>
				</table>
			</div>
			<div class="filter" id="company">
				<h3 class="infoListTitle">企业车辆车主信息</h3>
				<table class="fromTable">
					<tr>
						<td class="ft_label">单位名称：</td>
						<td class="ft_content">
							<form:input path="mortgageCarInfo.busiRegName" htmlEscape="false" maxlength="300" class="input-medium required" readonly="${readonly}" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">营业执照号：</td>
						<td class="ft_content" colspan="5">
							<form:input path="mortgageCarInfo.busiLicRegNo" htmlEscape="false" maxlength="50" class="input-medium required" readonly="${readonly}" />
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">户口所在地址：</td>
						<td class="ft_content" colspan="4">
							<form:select path="mortgageCarInfo.operateProvince" disabled="${readonly}" id="operateProvince" class="input-small required" onchange="loadCity('reg')">
								<option value="">请选择</option>
								<form:options items="${regProvinceMap}" htmlEscape="false" />
							</form:select>
							&nbsp;省
							<font color="red">*</font>
							<form:select path="mortgageCarInfo.operateCity" id="operateCity" disabled="${readonly}" class="input-small required" onchange="loadDistinct()">
								<option value="">请选择</option>
								<form:options items="${regCityMap}" htmlEscape="false" />
							</form:select>
							&nbsp;市
							<font color="red">*</font>
							<form:select path="mortgageCarInfo.operateDistinct" id="operateDistinct" disabled="${readonly}" class="input-small required">
								<option value="">请选择</option>
								<form:options items="${regDistinctMap}" htmlEscape="false" />
							</form:select>
							&nbsp;区&nbsp;
							<font color="red">*</font>
							<span style="width:13px;display: inline-block;"></span>
							<input type="text" name="mortgageCarInfo.operateDetails" id="operateDetails"  class="input-medium required" maxlength="200" value="${mortgageCarInfo.operateDetails}" style="margin: 0px;position: relative;display: inline-block;vertical-align: middle;" />
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">法定代表人：</td>
						<td class="ft_content">
							<form:input path="mortgageCarInfo.corporationName" htmlEscape="false" maxlength="300" class="input-medium required" readonly="${readonly}" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">法定代表人身份证号：</td>
						<td class="ft_content">
							<form:input path="mortgageCarInfo.corporationCardIdNo" htmlEscape="false" maxlength="18" class="input-medium required" readonly="${readonly}" />
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">委托办理人：</td>
						<td class="ft_content">
							<form:input path="mortgageCarInfo.clientName" htmlEscape="false" maxlength="20" class="input-medium required" readonly="${readonly}" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">委托办理人身份证号：</td>
						<td class="ft_content">
							<form:input path="mortgageCarInfo.clientIdNo" htmlEscape="false" maxlength="18" class="input-medium required" readonly="${readonly}" />
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">委托办理人与公司的关系：</td>
						<td class="ft_content">
							<form:input path="mortgageCarInfo.clientCompanyRelation" htmlEscape="false" maxlength="4" class="input-medium required" readonly="${readonly}" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">委托办理人联系方式：</td>
						<td class="ft_content">
							<form:input path="mortgageCarInfo.clientPhone" htmlEscape="false" maxlength="15" class="input-medium required" readonly="${readonly}" />
							<font color="red">*</font>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="filter">
			<input type="hidden" id="carId" name="carId" value="${mortgageCarInfo.id}" />
			<input type="hidden" id="applyNo" name="applyNo" value="${applyNo}" />
			<sys:message content="${message}" />
			<h3 class="infoListTitle">车辆评估信息</h3>
			<table class="filter">
				<tr>
					<td class="ft_label">机动车辆登记证号：</td>
					<td class="ft_content">
						<form:input path="motorVehiRegiCertiNo" htmlEscape="false" maxlength="15" class="input-medium required" readonly="${readonly}" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">车牌号码：</td>
					<td class="ft_content">
						<form:input path="vehicleNo" htmlEscape="false" maxlength="7" class="input-medium required" readonly="${readonly}" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">发动机号：</td>
					<td class="ft_content">
						<form:input path="engineNum" htmlEscape="false" maxlength="50" class="input-medium required" readonly="${readonly}" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">车架号：</td>
					<td class="ft_content">
						<form:input path="vehicleShelfNo" htmlEscape="false" maxlength="17" class="input-medium required" readonly="${readonly}" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">净车购买价格(元)：</td>
					<td class="ft_content">
						<form:input path="netPurchasePrice" htmlEscape="false" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" class="input-medium required" readonly="${readonly}" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">行驶公里数：</td>
					<td class="ft_content">
						<form:input path="travelKms" htmlEscape="false" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" class="input-medium required" readonly="${readonly}" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">年检及违规情况：</td>
					<td class="ft_content">
						<form:select path="annualInspectionStatus" class="input-medium required" disabled="${readonly}">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('ANNUAL_INSPECTION_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
					<td class="ft_label">使用性质：</td>
					<td class="ft_content">
						<form:select path="useProperty" class="input-medium required" disabled="${readonly}">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('CAR_USE_PROPERTY')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
					<td class="ft_label">车辆状态：</td>
					<td class="ft_content">
						<form:select path="carStatus" disabled="${readonly}" class="input-medium required">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('CAR_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">是否留存相关证件：</td>
					<td class="ft_content">
						<form:select path="isKeepPapers" disabled="${readonly}" class="input-medium required">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
					<td class="ft_label">有无处置能力：</td>
					<td class="ft_content">
						<form:select path="isHandle" disabled="${readonly}" class="input-medium required">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
					<td class="ft_label">已过户次数(次)：</td>
					<td class="ft_content">
						<form:input path="usedChanges" htmlEscape="false" maxlength="10" class="input-medium number required" readonly="${readonly}" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">已使用年限(年)：</td>
					<td class="ft_content">
						<form:input path="usedYears" htmlEscape="false" maxlength="3" class="input-medium number required" readonly="${readonly}" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">是否常用流通性高：</td>
					<td class="ft_content">
						<form:select path="isCommon" disabled="${readonly}" class="input-medium required">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">抵押率：(%)</td>
					<td class="ft_content">
						<form:input path="mortgageRate" htmlEscape="false" onblur="this.value=gdpMoney(this.value);" onkeyup="gdpMax(this)" maxlength="6" class="input-medium required" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">市场参考价1(元)：</td>
					<td class="ft_content">
						<form:input path="marketPrice1" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" htmlEscape="false" class="input-medium  required" readonly="${readonly}" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">二手市场电话1：</td>
					<td class="ft_content">
						<form:input path="marketPhone1" htmlEscape="false" class="input-medium phone required" readonly="${readonly}" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">车辆评估价格(元)：</td>
					<td class="ft_content">
						<form:input path="carEvaluatePrice" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" htmlEscape="false" class="input-medium required" readonly="${readonly}" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">市场参考价2(元)：</td>
					<td class="ft_content">
						<form:input path="marketPrice2" htmlEscape="false" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" class="input-medium required" readonly="${readonly}" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">二手市场电话2：</td>
					<td class="ft_content">
						<form:input path="marketPhone2" htmlEscape="false" class="input-medium  phone required" readonly="${readonly}" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">抵押车辆手续是否齐全：</td>
					<td class="ft_content">
						<form:select path="isProcedureComplete" disabled="${readonly}" class="input-medium required">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
					<td class="ft_label">市场参考价3(元)：</td>
					<td class="ft_content">
						<form:input path="marketPrice3" htmlEscape="false" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" class="input-medium required" readonly="${readonly}" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">二手市场电话3：</td>
					<td class="ft_content">
						<form:input path="marketPhone3" htmlEscape="false" class="input-medium phone required" readonly="${readonly}" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="searchButton" colspan="6" style="text-align:right">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />
						&nbsp;
						<input id="btnCancel" class="btn" type="button" value="取 消" onclick="closeJBox()" />
						&nbsp;
					</td>
				</tr>
			</table>
	</form:form>
	</div>
</html>