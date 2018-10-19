<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!-- 
 * @reqno:H1601220071
 * @date-designer:2016年1月27日-songmin
 * @date-author:2016年1月27日-songmin:CRE_信贷审批_客户信息管理_抵押权人信息管理_新增
 * 新增抵押权人信息
 * @e-in-text:mortgageeName-姓名:姓名
 * @e-in-text:mobileNum-移动电话:移动电话
 * @e-in-list:sexNo-性别:性别
 * @e-in-text:cardId-身份证:身份证
 * @e-in-text:birthDay-出生日期:出生日期
 * @e-in-text:bankcardNo-银行卡号:银行卡号
 * @e-in-list:bankNo-开户行:开户行
 * @e-in-text:bankDetailName-开户行名称:开户行名称
 * @e-in-list:contProvince-开户行地址（省）:开户行地址（省）
 * @e-in-list:contCity-开户行地址（市）:开户行地址（市）
 * @e-in-list:contDistinct-开户行地址（区）:开户行地址（区）
 * @e-in-text:contDetails-地址明细:地址明细
 * @e-in-text:capitalTerraceNo-资金平台账号:资金平台账号
 * @e-in-text:openAccountStatus-开户状态:开户状态
 * @e-ctrl:btnSubmit-保存:保存
 * @e-ctrl:btnClose-关闭:关闭
 -->
<!-- 
* @reqno:H1601250005
* @date-designer:2016年1月28日-songmin
* @date-author:2016年1月28日-songmin:数据库字段变更
 -->
<html>
<head>
<title>${not empty mortgagedPerson.id?'修改':'新增'}新增抵押权人信息</title>
<meta name="decorator" content="default1" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#inputForm").validate({
		submitHandler : function(form) {
			loading('正在提交，请稍等...');
			form.submit();
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});

		new CitySelect({
		data : data,
		provId : '#contProvince',
		cityId : '#contCity',
		areaId : '#contDistinct',
		isSelect : false
		});
		if (!checkIsNull('${mortgagedPerson.contDistinct}')) {
			setName(data, "cont", '${mortgagedPerson.contProvince}', '${mortgagedPerson.contCity}', '${mortgagedPerson.contDistinct}');
		}

	});
</script>
<style type="text/css">
.ft_label {
	text-align: left;
	width: 90px;
	float: right;
	margin-top: 10px;
}
</style>
</head>
<body>
	<div class="wrapper">
		<div class="infoList">
			<div class="infoListCon">
				<div class="filter">
					<sys:message content="${message}" />
					<form:form id="inputForm" modelAttribute="mortgagedPerson" action="${ctx}/credit/mortgagedperson/save" method="post">
						<form:hidden path="id" />
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
						<input id="applyId" name="applyId" type="hidden" value="-1" />
						<table class="fromTable">
							<tr>
								<td class="ft_label">姓名：</td>
								<td class="ft_content">
									<form:input path="mortgageeName" htmlEscape="false" maxlength="15" class="input-medium required" />
									<span class="help-inline">
										<font color="red">*</font>
									</span>
								</td>
								<td class="ft_label">移动电话：</td>
								<td class="ft_content">
									<form:input path="mobileNum" htmlEscape="false" maxlength="11" class="input-medium mobile required" />
									<span class="help-inline">
										<font color="red">*</font>
									</span>
								</td>
								<td class="ft_label">性别：</td>
								<td class="ft_content">
									<form:select path="sexNo" class="input-medium required" cssStyle="width:176px;">
										<form:option value="" label="" />
										<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
									<span class="help-inline">
										<font color="red">*</font>
									</span>
								</td>
							</tr>
							<tr>
								<td class="ft_label">身份证：</td>
								<td class="ft_content">
									<form:input path="idNum" htmlEscape="false" maxlength="18" class="input-medium card required" />
									<span class="help-inline">
										<font color="red">*</font>
									</span>
								</td>
								<td class="ft_label">出生日期：</td>
								<td class="ft_content" colspan="3">
									<input id="birthDay" name="birthDay" type="text" maxlength="20" class="input-medium Wdate required" value="<fmt:formatDate value="${mortgagedPerson.birthDay}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd'});" />
									<span class="help-inline">
										<font color="red">*</font>
									</span>
								</td>
							</tr>
							<tr>
								<td class="ft_label">银行卡号：</td>
								<td class="ft_content">
									<form:input path="bankcardNo" htmlEscape="false" maxlength="32" class="input-medium digits required" />
									<span class="help-inline">
										<font color="red">*</font>
									</span>
								</td>
								<td class="ft_label">开户行：</td>
								<td class="ft_content">
									<form:select path="bankNo" class="input-medium required" cssStyle="width:176px;">
										<form:option value="" label="" />
										<form:options items="${fns:getDictList('BANKS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
									<span class="help-inline">
										<font color="red">*</font>
									</span>
								</td>
								<td class="ft_label">开户行名称：</td>
								<td class="ft_content">
									<form:input path="bankDetailName" htmlEscape="false" maxlength="100" class="input-medium required" />
									<span class="help-inline">
										<font color="red">*</font>
									</span>
								</td>
							</tr>
							<tr>
								<td class="ft_label">开户行地址：</td>
								<td class="ft_content" colspan="5">
									<form:select path="contProvince" class="input-small nuNullCheck" data-code="-1"></form:select>
									&nbsp;省
									<form:select path="contCity" class="input-small nuNullCheck" data-code="-1"></form:select>
									&nbsp;市
									<form:select path="contDistinct" class="input-small nuNullCheck" data-code="-1"></form:select>
									&nbsp;区&nbsp;
									<span class="help-inline">
										<font color="red">*</font>
									</span>
									<span style="width: 15px;display:inline-block;"></span>
									地址明细：
									<input type="text" name="contDetails" id="contDetails" class="input-medium required" maxlength="100" value="${mortgagedPerson.contDetails}" style="margin: 0px;position: relative;display: inline-block;vertical-align: middle;width:450px" />
									<span class="help-inline">
										<font color="red">*</font>
									</span>
									<script type="text/javascript">
										function loadContProvince() {
											var $contProvince = $("#contProvince").select2("val");
											//重置市
											$("#contCity").empty();
											$("#contCity").append("<option value=''>请选择</option>");
											$.post("${ctx}/sys/area/treeNode", {
												parentId : $contProvince
											}, function(data) {
												$.each(data, function(i, val) {
													$("#contCity").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
												});
											});
											$("#contCity").select2("val", "");
											//重置区县
											$("#contDistinct").empty();
											$("#contDistinct").append("<option value=''>请选择</option>");
											$("#contDistinct").select2("val", "");
										}
										function loadContCity() {
											var $contCity = $("#contCity").select2("val");
											//重置市
											$("#contDistinct").empty();
											$("#contDistinct").append("<option value=''>请选择</option>");
											$.post("${ctx}/sys/area/treeNode", {
												parentId : $contCity
											}, function(data) {
												$.each(data, function(i, val) {
													$("#contDistinct").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
												});
											});
											$("#contDistinct").select2("val", "");
										}
										function finishContDistinct() {

										}
									</script>
								</td>
							</tr>
							<tr>
								<td class="ft_label">资金平台账号：</td>
								<td class="ft_content">
									<form:input path="capitalTerraceNo" htmlEscape="false" maxlength="32" class="input-medium " />
								</td>
								<td class="ft_label">开户状态：</td>
								<td class="ft_content" colspan="3">
									<form:select path="openAccountStatus" class="input-medium" cssStyle="width:176px;">
										<form:options items="${fns:getDictList('OPEN_ACCOUNT_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
									<script type="text/javascript">
										$("#openAccountStatus").prop("readonly", true).select2();
									</script>
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />
							&nbsp;
							<input id="btnClose" class="btn btn-primary" type="button" value="关 闭" onclick="closeJBox('mortgagedperson-form');" />
							&nbsp;
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
	<!-- 保存成功后，接口调用 -->
<!-- 	<c:if test="${closeWindow eq 'true'}"> -->
<!-- 		<script type="text/javascript"> -->
<!-- 			$.ajax({ -->
<!-- 			url : "${ctx}/outinterface/callInterface/mortgageeCreateAccount", -->
<!-- 			data : { -->
<!-- 				idNum : '${mortgagedPerson.idNum}' -->
<!-- 			}, -->
<!-- 			type : "post" -->
<!-- 			}); -->
<!-- 			alertx('${message}', function() { -->
<!-- 				parent.$("#searchForm").submit(); -->
<!-- 				closeJBox("mortgagedperson-form"); -->
<!-- 			}); -->
<!-- 		</script> -->
<!-- 	</c:if> -->
	<!-- 保存失败 -->
	<c:if test="${closeWindow ne 'true' && not empty message}">
		<script type="text/javascript">
			alertx('${message}');
		</script>
	</c:if>
</body>
</html>
