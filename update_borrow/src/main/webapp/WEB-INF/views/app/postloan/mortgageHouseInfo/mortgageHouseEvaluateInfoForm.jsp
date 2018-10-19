<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<title>房产抵质押物管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		if ('${readonly}' != "") {
			$("#btnSubmit").hide();
			$("#btnCancel").hide();
		}
		$("#houseForm").validate({
		submitHandler : function(form) {
			loading('正在提交，请稍等...');
			form.submit();
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});

		/* $("#readonly").attr("disabled","disabled"); */
		document.getElementById("housePurchasePrice").value = outputmoney("${mortgageHouseInfo.housePurchasePrice}");
		document.getElementById("marketPrice").value = outputmoney("${mortgageHouseInfo.marketPrice}");
		document.getElementById("evaluatePrice").value = outputmoney("${mortgageHouseInfo.evaluatePrice}");
	});

	function loadCity() {
		//重置市、县下拉框
		$("#contCity").empty();
		$("#contCity").append("<option value=''>请选择</option>");
		var $contCity = $("#s2id_contCity>.select2-choice>.select2-chosen");
		$contCity.html("请选择");
		$("#contDistinct").empty();
		$("#contDistinct").append("<option value=''>请选择</option>");
		var $contDistinct = $("#s2id_contDistinct>.select2-choice>.select2-chosen");
		$contDistinct.html("请选择");
		$.post("${ctx}/sys/area/treeNode", {
			parentId : $("#contProvince").val()
		}, function(data) {
			$.each(data, function(i, val) {
				$("#contCity").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
			});
		});
	}

	function loadDistinct(val) {
		$("#contDistinct").empty();
		$("#contDistinct").append("<option value=''>请选择</option>");
		var $contDistinct = $("#s2id_regDistinct>.select2-choice>.select2-chosen");
		$contDistinct.html("请选择");

		$.post("${ctx}/sys/area/treeNode", {
			parentId : $("#contCity").val()
		}, function(data) {
			$.each(data, function(i, val) {
				$("#contDistinct").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
			});
		});
	}

	function dealWithMoney() {
		var valueT = $("#housePurchasePrice").val().replace(/,/g, "");
		$("#housePurchasePrice").val(valueT);
		var valueT = $("#marketPrice").val().replace(/,/g, "");
		$("#marketPrice").val(valueT);
		var valueT = $("#evaluatePrice").val().replace(/,/g, "");
		$("#evaluatePrice").val(valueT);
	}
	function showEdit(id) {
		var width = $(window).width() - 100;
		var height = $(window).height() - 100;
		var heightTrue = Math.min(height, 320);
		openJBox("checkForm", "${ctx}/postloan/mortgageEvaluateInfo/showEdit?id=" + id, "编辑检查项目", width, heightTrue);
	}
</script>
<c:if test="${true == readOnly}">
	<script type="text/javascript">
		$(document).ready(function() {
			$(document).ready(function() {
				$("input[type!='button']").attr("readOnly", "readOnly");
				$("input[type='radio']").attr("disabled", "disabled");
				$("input[type='checkbox']").attr("disabled", "disabled");
				$("#readonly").attr("disabled", "disabled");
				disableSelect2();
				$("div[class='ribbon']").remove();
				$("div[class='searchButton']").remove();
				$("font[color='red']").remove();
				$(".Wdate").attr("onclick", "");
				$(".Wdate").removeClass("Wdate");
				$(".qdelete").remove();
				$("#btnSubmit").remove();
				$("#btnCancel").attr("value", "关闭");
			});
		});
	</script>
</c:if>
<form:form id="houseForm" modelAttribute="mortgageHouseInfo" action="${ctx}/postloan/mortgageHouseInfo/saveEvaluateHouse" method="post" class="form-horizontal">
	<form:hidden path="id" />
	<form:hidden path="applyNo" />
	<sys:message content="${message}" />
	<table class="filter" style="width: 100%" id="readonly">
		<h3 class="infoListTitle">房产抵质押物信息</h3>
		<tr>
			<td class="ft_label">产权属性：</td>
			<td class="ft_content">
				<form:select path="propertyRight" disabled="${readonly}" class="input-medium  required">
					<form:option value="" label="" />
					<form:options items="${fns:getDictList('PROPERTY_RIGHT')}" disabled="${readonly}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
				<font color="red">*</font>
			</td>
			<td class="ft_label">产权人姓名：</td>
			<td class="ft_content">
				<form:input path="propertyName" htmlEscape="false" readonly="${readonly}" maxlength="20" class="input-medium required" />
				<font color="red">*</font>
			</td>
			<td class="ft_label">当前状态：</td>
			<td class="ft_content">
				<form:select path="currentUseStatus" disabled="${readonly}" class="input-medium required">
					<form:option value="" label="" />
					<form:options items="${fns:getDictList('CURRENT_USE_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
				<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td class="ft_label">房产使用性质：</td>
			<td class="ft_content">
				<form:select path="houseUseProperty" disabled="${readonly}" class="input-medium required">
					<form:option value="" label="" />
					<form:options items="${fns:getDictList('HOUSE_USE_PROPERTY')}" disabled="${readonly}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
				<font color="red">*</font>
			</td>
			<td class="ft_label">规划用途：</td>
			<td class="ft_content">
				<form:select path="landUseProperty" disabled="${readonly}" class="input-medium required">
					<form:option value="" label="" />
					<form:options items="${fns:getDictList('LAND_USE_PROPERTY')}" disabled="${readonly}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
				<font color="red">*</font>
			</td>
			<td class="ft_label">房产种类：</td>
			<td class="ft_content">
				<form:select path="houseType" disabled="${readonly}" class="input-medium required">
					<form:option value="" label="" />
					<form:options items="${fns:getDictList('HOUSE_TYPE')}" disabled="${readonly}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
				<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td class="ft_label">房产地址：</td>
			<td class="ft_content" colspan="5">
				<form:select path="contProvince" id="contProvince" disabled="${readonly}" class="input-small required" onchange="loadCity('reg')">
					<option value="">请选择</option>
					<form:options items="${contProvinceMap}" htmlEscape="false" />
				</form:select>
				&nbsp;省
				<font color="red">*</font>
				<form:select path="contCity" id="contCity" disabled="${readonly}" class="input-small required" onchange="loadDistinct()">
					<option value="">请选择</option>
					<form:options items="${contCityMap}" htmlEscape="false" />
				</form:select>
				&nbsp;市
				<font color="red">*</font>
				<form:select path="contDistinct" id="contDistinct" disabled="${readonly}" class="input-small required">
					<option value="">请选择</option>
					<form:options items="${contDistinctMap}" htmlEscape="false" />
				</form:select>
				&nbsp;区&nbsp;
				<font color="red">*</font>
				<span style="width: 15px; display: inline-block;"></span>
				<input type="text" name="contDetails" id="contDetails" class="input-medium required" maxlength="200" value="${mortgageHouseInfo.contDetails}" style="margin: 0px; position: relative; display: inline-block; vertical-align: middle;" />
				<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td class="ft_label">建筑面积(㎡)：</td>
			<td class="ft_content">
				<form:input path="buildingArea" readonly="${readonly}" htmlEscape="false" class="input-medium required" />
				<font color="red">*</font>
			</td>
			<td class="ft_label">使用面积(㎡)：</td>
			<td class="ft_content">
				<form:input path="usingArea" readonly="${readonly}" htmlEscape="false" class="input-medium required" />
				<font color="red">*</font>
			</td>
			<td class="ft_label">所在层数：</td>
			<td class="ft_content">
				<form:input path="floorNum" readonly="${readonly}" htmlEscape="false" maxlength="30" class="input-medium required" />
				<font color="red">*</font>
			</td>
		</tr>
		<tr>
	</table>
	<table class="filter">
		<h3 class="infoListTitle">房产抵质押物评估信息</h3>
		<tr>
			<td class="ft_label">房龄：</td>
			<td class="ft_content">
				<form:input path="houseYears" htmlEscape="false" maxlength="3" class="input-medium number required" readonly="${readonly}" />
				<font color="red">*</font>
			</td>
			<td class="ft_label">使用年限：</td>
			<td class="ft_content">
				<form:input path="serviceLife" htmlEscape="false" maxlength="3" class="input-medium number required" readonly="${readonly}" />
				<font color="red">*</font>
			</td>
			<td class="ft_label">房产取得价格(元)：</td>
			<td class="ft_content">
				<form:input path="housePurchasePrice" htmlEscape="false" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" class="input-medium required" readonly="${readonly}" />
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
			<td class="ft_label">有无产权纠纷：</td>
			<td class="ft_content">
				<form:select path="isPropertyLine" disabled="${readonly}" class="input-medium required">
					<form:option value="" label="" />
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
				<font color="red">*</font>
			</td>
			<td class="ft_label">有无调档记录：</td>
			<td class="ft_content">
				<form:select path="isShiftRecord" disabled="${readonly}" class="input-medium required">
					<form:option value="" label="" />
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
				<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td class="ft_label">产权目前状态：</td>
			<td class="ft_content">
				<form:select path="propertyStatus" disabled="${readonly}" class="input-medium required">
					<form:option value="" label="" />
					<form:options items="${fns:getDictList('PROPERTY_HOUSE_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
				<font color="red">*</font>
			</td>
			<td class="ft_label">是否唯一住房：</td>
			<td class="ft_content">
				<form:select path="isUniqueHouse" disabled="${readonly}" class="input-medium required">
					<form:option value="" label="" />
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
				<font color="red">*</font>
			</td>
			<td class="ft_label">评估方式价格取得途径：</td>
			<td class="ft_content">
				<form:input path="evaluateWay" readonly="${readonly}" htmlEscape="false" class="input-medium required" />
				<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td class="ft_label">当前市场价格(元)：</td>
			<td class="ft_content">
				<form:input path="marketPrice" readonly="${readonly}" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" htmlEscape="false" class="input-medium required" />
				<font color="red">*</font>
			</td>
			<td class="ft_label">评估价格(元)：</td>
			<td class="ft_content">
				<form:input path="evaluatePrice" readonly="${readonly}" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" htmlEscape="false" class="input-medium required" />
				<font color="red">*</font>
			</td>
			<td class="ft_label">抵押率(%)：</td>
			<td class="ft_content">
				<form:input path="mortgageRate" readonly="${readonly}" htmlEscape="false" onblur="this.value=gdpMoney(this.value);" onkeyup="gdpMax(this)" maxlength="6" class="input-medium required" />
				<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td class="ft_label">抵押权人姓名：</td>
			<td class="ft_content">
				<form:input path="mortgageeName" readonly="${readonly}" htmlEscape="false" class="input-medium required" />
				<font color="red">*</font>
			</td>
			<td class="ft_label">抵押权人身份证号：</td>
			<td class="ft_content">
				<form:input path="mortgageeIdNum" readonly="${readonly}" htmlEscape="false" maxlength="18" class="required input-medium card" />
				<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td class="searchButton" colspan="6" style="text-align: right">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" onclick="dealWithMoney()" />
				&nbsp;
				<input id="btnCancel" class="btn" type="button" value="取消" onclick="closeJBox();" />
			</td>
		</tr>
	</table>
</form:form>
</html>