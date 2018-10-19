<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>房产抵质押物管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		document.getElementById("buildingArea").value = outputmoney("${mortgageHouseInfo.buildingArea}");
		document.getElementById("usingArea").value = outputmoney("${mortgageHouseInfo.usingArea}");
		$("#houseForm").validate({
		submitHandler : function(form) {
			var valueT2 = $("#buildingArea").val().replace(/,/g, "");
			var valueT3 = $("#usingArea").val().replace(/,/g, "");
			$("#buildingArea").val(valueT2);
			$("#usingArea").val(valueT3);
			loading('正在提交，请稍等...');
			form.submit();
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});
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
</script>
<c:if test="${true == readOnly}">
	<script type="text/javascript">
		$(document).ready(function() {
			$(document).ready(function() {
				$("input[type!='button']").attr("readOnly", "readOnly");
				$("input[type='radio']").attr("disabled", "disabled");
				$("input[type='checkbox']").attr("disabled", "disabled");
				$("#otherInformation").attr("disabled", "disabled");
				disableSelect2();
				$("div[class='ribbon']").remove();
				$("div[class='searchButton']").remove();
				$("font[color='red']").remove();
				$(".Wdate").attr("onclick", "");
				$(".Wdate").removeClass("Wdate");
				$(".qdelete").remove();
				$("#btnSubmit").remove();
				$("#btnCancel").show();
				$("#btnCancel").attr("value", "关闭");
			});
		});
	</script>
</c:if>
</head>
<body>
	<form:form id="houseForm" modelAttribute="mortgageHouseInfo" action="${ctx}/postloan/mortgageHouseInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<form:hidden path="applyNo" />
		<sys:message content="${message}" />
		<table class="filter" style="width: 100%" >
			<h3 class="infoListTitle">房产抵质押物信息</h3>
			<tr>
				<td class="ft_label">产权人姓名：</td>
				<td class="ft_content" colspan="3">
					<form:input path="propertyName"  style="width:70%;" maxlength="20" class="input-medium required" />
					<font color="red">*</font>
				</td>
			</tr>
			<tr>
			<td class="ft_label">产权属性：</td>
				<td class="ft_content">
					<form:select path="propertyRight" class="input-medium required">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('PROPERTY_RIGHT')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
					<font color="red">*</font>
				</td>
			<td class="ft_label">当前状态：</td>
				<td class="ft_content">
					<form:select path="currentUseStatus" class="input-medium required">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('CURRENT_USE_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
					<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td class="ft_label">房产使用性质：</td>
				<td class="ft_content">
					<form:select path="houseUseProperty" class="input-medium required">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('HOUSE_USE_PROPERTY')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
					<font color="red">*</font>
				</td>
				<td class="ft_label">规划用途：</td>
				<td class="ft_content">
					<form:select path="landUseProperty" class="input-medium required">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('LAND_USE_PROPERTY')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
					<font color="red">*</font>
				</td>
				<td class="ft_label">房产种类：</td>
				<td class="ft_content">
					<form:select path="houseType" class="input-medium required">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('HOUSE_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
					<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td class="ft_label">房产地址：</td>
				<td class="ft_content" colspan="5">
					<form:select path="contProvince" id="contProvince" class="input-small required" onchange="loadCity('reg')">
						<option value="">请选择</option>
						<form:options items="${contProvinceMap}" htmlEscape="false" />
					</form:select>
					&nbsp;省
					<font color="red">*</font>
					<form:select path="contCity" id="contCity" class="input-small required" onchange="loadDistinct()">
						<option value="">请选择</option>
						<form:options items="${contCityMap}" htmlEscape="false" />
					</form:select>
					&nbsp;市
					<font color="red">*</font>
					<form:select path="contDistinct" id="contDistinct" class="input-small required">
						<option value="">请选择</option>
						<form:options items="${contDistinctMap}" htmlEscape="false" />
					</form:select>
					&nbsp;区&nbsp;
					<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td class="ft_label">详细地址：</td>
				<td class="ft_content" colspan="5">
					<input type="text" name="contDetails" id="contDetails" class="input-medium required" maxlength="200" value="${mortgageHouseInfo.contDetails}" style="margin: 0px; position: relative; display: inline-block; vertical-align: middle; width: 70%;" />
					<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td class="ft_label">建筑面积(㎡)：</td>
				<td class="ft_content">
					<form:input path="buildingArea" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" htmlEscape="false" class="input-medium  required" />
					<font color="red">*</font>
				</td>
				<td class="ft_label">使用面积(㎡)：</td>
				<td class="ft_content">
					<form:input path="usingArea" onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" htmlEscape="false" class="input-medium  required" />
					<font color="red">*</font>
				</td>
				<td class="ft_label">所在层数：</td>
				<td class="ft_content">
					<form:input path="floorNum" htmlEscape="false" maxlength="30" class="input-medium required" />
					<font color="red">*</font>
				</td>
			</tr>
			<tr>
				<td class="ft_label">房产证登记号：</td>
				<td class="ft_content">
					<form:input path="houseCardNumber" htmlEscape="false" maxlength="30" class="input-medium" />
				</td>
				<td class="ft_label">土地证登记号：</td>
				<td class="ft_content">
					<form:input path="landCardNumber" htmlEscape="false" maxlength="30" class="input-medium" />
				</td>
				<td class="ft_label">土地性质：</td>
				<td class="ft_content">
					<form:select path="landProperty" class="input-medium">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('land_property')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
				</td>
			</tr>
			<%-- <tr>
				<td class="ft_label">抵押率(%)：</td>
				<td class="ft_content">
					<form:input path="mortgageRate" htmlEscape="false" onblur="this.value=gdpMoney(this.value);" onkeyup="gdpMax(this)" maxlength="6" class="input-medium required" />
					<font color="red">*</font>
				</td>
			</tr> --%>
			<tr>
				<td class="searchButton" colspan="6">
					<shiro:hasPermission name="credit:mortgageInfo:edit">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />&nbsp;</shiro:hasPermission>
					<input id="btnCancel" class="btn btn-primary" type="button" value="取消" onclick="parent.$.jBox.close()" />
				</td>
			</tr>
		</table>
	</form:form>
	<c:if test="${not empty closeWindow}">
		<script type="text/javascript">
			alertx('${message}', function() {
				parent.$.loadDiv("mortgageHouseEvaluate", "${ctx}/postloan/mortgageHouseInfo", {
					applyNo : '${mortgageHouseInfo.applyNo}'
				}, "post");
				closeJBox();
			});
		</script>
	</c:if>
</body>
</html>