<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">
	$(document).ready(function() {
		document.getElementById("monthIncomeId").value = outputmoney("${custWorkInfo.monthIncome}");
		document.getElementById("otherMonthIncomeId").value = outputmoney("${custWorkInfo.otherMonthIncome}");

		$("#custWorkInfoForm").validate({
		submitHandler : saveCustWorkInfo,
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});

		new CitySelect({
		data : data,
		provId : '#companyProvince',
		cityId : '#companyCity',
		areaId : '#companyDistinct',
		isSelect : false
		});

		if (!checkIsNull('${custWorkInfo.companyDistinct}')) {
			setName(data, "company", '${custWorkInfo.companyProvince}', '${custWorkInfo.companyCity}', '${custWorkInfo.companyDistinct}');
		}

	});

	function beforeSubmit() {
		if (!checkIsNull($("#custIdForCustWork").val())) {
			$("#custWorkInfoForm").submit();
		} else {
			alertx("请先保存借款人信息！");
		}
	}

	function workLoadCity() {
		$("#companyCity").empty();
		$("#companyCity").append("<option value=''>请选择</option>");
		var $companyCity = $("#s2id_companyCity>.select2-choice>.select2-chosen");
		$companyCity.html("请选择");
		$("#companyDistinct").empty();
		$("#companyDistinct").append("<option value=''>请选择</option>");
		var $companyDistinct = $("#s2id_companyDistinct>.select2-choice>.select2-chosen");
		$companyDistinct.html("请选择");
		$.post("${ctx}/sys/area/treeNode", {
			parentId : $("#companyProvince").val()
		}, function(data) {
			$.each(data, function(i, val) {
				$("#companyCity").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
			});
		});
	}
	function workLoadDistinct() {
		$("#companyDistinct").empty();
		$("#companyDistinct").append("<option value=''>请选择</option>");
		var $companyDistinct = $("#s2id_companyDistinct>.select2-choice>.select2-chosen");
		$companyDistinct.html("请选择");

		$.post("${ctx}/sys/area/treeNode", {
			parentId : $("#companyCity").val()
		}, function(data) {
			$.each(data, function(i, val) {
				$("#companyDistinct").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
			});
		});
	}

	function zjrgzxxClick() {
		$("#zjrgzxx").toggle(600);
	}
</script>
<div class="searchInfo">
	<h3 onclick="zjrgzxxClick()" class="searchTitle">借款人工作信息</h3>
	<div id="zjrgzxx" class="searchCon">
		<form:form id="custWorkInfoForm" modelAttribute="custWorkInfo" action="#" method="post" class="form-horizontal">
			<form:hidden id="custWorkInfoId" path="id" />
			<form:hidden path="custInfo.id" />
			<input type="hidden" id="custIdForCustWork" name="custInfo.id" value="${custInfo.id }" />
			<sys:message content="${message}" />
			<table class="fromTable filter">
				<tr>
					<td class="ft_label">单位名称：</td>
					<td class="ft_content">
						<form:input path="companyName" onblur="this.value=reSpaces(this.value);"  htmlEscape="false" maxlength="300" class="input-medium required" />
						<font color="red">*</font>
					</td>
					<td class="ft_label">单位性质：</td>
					<td class="ft_content">
						<form:select path="comNature" class="input-medium required">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('COMPANY_PROPERTY')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">职位类别：</td>
					<td class="ft_content">
						<form:select path="postType" class="input-medium">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('POST_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
					</td>
					<td class="ft_label">职位级别：</td>
					<td class="ft_content">
						<form:select path="postLevel" class="input-medium">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('POST_LEVEL')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
					</td>
					<td class="ft_label">职位名称：</td>
					<td class="ft_content">
						<form:input path="postName" htmlEscape="false" maxlength="100" class="input-medium " />
					</td>
				</tr>
				<tr>
					<td class="ft_label">部门名称：</td>
					<td class="ft_content">
						<form:input path="department" htmlEscape="false"  onblur="this.value=reSpaces(this.value);" maxlength="100" class="input-medium" />
					</td>
					<td class="ft_label">单位电话：</td>
					<td class="ft_content" colspan="2" nowrap>
						<form:input path="comPhoneAr" htmlEscape="false" maxlength="4" class="input-mini number" />
						（区号）
						<form:input path="comPhoneSb" htmlEscape="false" style="width:40%" maxlength="8" class="input-mini number" />
						（总机）
						<form:input path="comPhoneEx" htmlEscape="false" style="width:20%" maxlength="8" class="input-mini number" />
					</td>
				</tr>
				<tr>
					<td class="ft_label">本单位入职年限(年)：</td>
					<td class="ft_content">
						<input id="workInDate" name="workInDate" type="text" maxlength="3" class="input-medium number" value="${custWorkInfo.workInDate}" />
					</td>
					<td class="ft_label">工资发放方式：</td>
					<td class="ft_content">
						<form:select path="salaryMode" class="input-medium ">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('SALARY_MODE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
					</td>
					<td class="ft_label">每月基本薪金(元)：</td>
					<td class="ft_content">
						<form:input onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" id="monthIncomeId" path="monthIncome" htmlEscape="false" class="input-medium required " />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="ft_label">其他月收入(元)：</td>
					<td class="ft_content">
						<form:input onkeyup="keyPress11(this);" onblur="this.value=outputmoney(this.value);" id="otherMonthIncomeId" path="otherMonthIncome" htmlEscape="false" class="input-medium " />
					</td>
					<td class="ft_label">工资发放日期 ：</td>
					<td class="ft_content">
						<input name="salartDay" type="text" maxlength="20" class="input-medium Wdate" value="${custWorkInfo.salartDay}" onclick="WdatePicker({dateFmt:'dd'});" />
					</td>
				</tr>
				<tr>
					<td class="ft_label">单位地址：</td>
					<td class="ft_content" colspan="5">
						<form:select path="companyProvince" class="input-small nuNullCheck" data-code="-1" ></form:select>
						&nbsp;省
						<form:select path="companyCity" class="input-small nuNullCheck" data-code="-1" ></form:select>
						&nbsp;市
						<form:select path="companyDistinct" class="input-small nuNullCheck" data-code="-1" ></form:select>
						&nbsp;区&nbsp;
						<span style="width: 15px; display: inline-block;"></span>
						地址明细：
						<input type="text" name="companyDetails" id="companyDetails" onblur="this.value=reSpaces(this.value);" value="${custWorkInfo.companyDetails}" class="input-medium" maxlength="300" style="margin: 0px; position: relative; vertical-align: middle;width:325px" />
						<font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td class="searchButton" colspan="6" style="text-align: right;">
						<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="beforeSubmit();" />
						&nbsp;
					</td>
				</tr>
			</table>
		</form:form>
	</div>
</div>
