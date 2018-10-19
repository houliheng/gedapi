<%-- <%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>新增个人客户信息</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {

		//初始化身份证终止日期
		if ('${coCustWorkInfo.personIdEndDate}' == null || '${coCustWorkInfo.personIdEndDate}' == '') {
			$("input[id='yes']").attr("checked", "true");
			$("#personIdEndDate").hide();
			$("#cardEndText").hide();
		} else {
			$("input[id='no']").attr("checked", "true");
			$("#personIdEndDate").show();
			$("#cardEndText").show();
		}

		$("#coCustInfoForm").validate({
		submitHandler : function() {
			saveCoCustInfo();
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});
		if ('${readOnly}' == '0') {
			setPageReadOnly();
		}
	});

	//身份证终止日期显示与否
	function changeShowOrHide() {
		var $cardFlag = $("input[name='cardFlag']:checked").val();
		if ("yes" == $cardFlag) {
			$("input[id='yes']").attr("checked", "true");
			$("#personIdEndDate").hide();
			$("#cardEndText").hide();
		} else {
			$("input[id='no']").attr("checked", "true");
			$("#personIdEndDate").show();
			$("#cardEndText").show();
		}
	}

	function loadCity(val) {
		//重置市、县下拉框
		if ("reg" == val) {
			$("#regCity").empty();
			$("#regCity").append("<option value=''>请选择</option>");
			var $regCity = $("#s2id_regCity>.select2-choice>.select2-chosen");
			$regCity.html("请选择");
			$("#regDistinct").empty();
			$("#regDistinct").append("<option value=''>请选择</option>");
			var $regDistinct = $("#s2id_regDistinct>.select2-choice>.select2-chosen");
			$regDistinct.html("请选择");
			$.post("${ctx}/sys/area/treeNode", {
				parentId : $("#regProvince").val()
			}, function(data) {
				$.each(data, function(i, val) {
					$("#regCity").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
				});
			});
		} else {
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
	}

	function loadDistinct(val) {
		if ("reg" == val) {
			$("#regDistinct").empty();
			$("#regDistinct").append("<option value=''>请选择</option>");
			var $regDistinct = $("#s2id_regDistinct>.select2-choice>.select2-chosen");
			$regDistinct.html("请选择");

			$.post("${ctx}/sys/area/treeNode", {
				parentId : $("#regCity").val()
			}, function(data) {
				$.each(data, function(i, val) {
					$("#regDistinct").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
				});
			});
		} else {
			$("#contDistinct").empty();
			$("#contDistinct").append("<option value=''>请选择</option>");
			var $contDistinct = $("#s2id_contDistinct>.select2-choice>.select2-chosen");
			$contDistinct.html("请选择");

			$.post("${ctx}/sys/area/treeNode", {
				parentId : $("#contCity").val()
			}, function(data) {
				$.each(data, function(i, val) {
					$("#contDistinct").append("<option value='"+val["id"]+"'>" + val["name"] + "</option>");
				});
			});
		}
	}
	var treeCategory1 = [ "categoryMain1", "categoryLarge1", "categoryMedium1", "categorySmall1" ];
	function mainLoadCategory(root, curr) {
		var parentInduCode = $("#" + root).select2("val");
		var emptyFlag = false;
		for (var i = 0; i < treeCategory1.length; i++) {
			if (curr == treeCategory1[i]) {
				emptyFlag = true;
			}
			if (emptyFlag) {
				$("#" + treeCategory1[i]).select2("val", "");
				$("#" + treeCategory1[i]).empty();
			}
		}
		if ("" != parentInduCode && parentInduCode.length > 0) {
			$.post("${ctx}/credit/industry/loadIndustry", {
				parentInduCode : parentInduCode
			}, function(data) {
				$("#" + curr).append("<option value=''></option>");
				$.each(data, function(i, val) {
					$("#" + curr).append("<option value='"+val.induCode+"'>" + val.induName + "</option>");
				});
			}, "json");
		}
	}

	function finishDistinct() {
		//现居地等于户籍地
		var $addrResi = $("input[name='addrResi']:checked").val();
		if (1 == $addrResi) {//是
			$("#contDistinct").val($("#regDistinct").val());
			$("#s2id_contDistinct>.select2-choice>.select2-chosen").html($("#s2id_regDistinct>.select2-choice>.select2-chosen").html());
			//房屋所在地等于现居地
			var $addrSync = $("input[name='addrSync']:checked").val();
			if (1 == $addrSync) {//是
				$("#hsDistinct").val($("#contDistinct").val());
				$("#s2id_hsDistinct>.select2-choice>.select2-chosen").html($("#s2id_contDistinct>.select2-choice>.select2-chosen").html());
			}
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

	//保存共借人信息
	function saveCoCustInfo() {
		var perAnnualIncome = $("input[id='custInfo.perAnnualIncome']").val().replace(/,/g, "");
		$("input[id='custInfo.perAnnualIncome']").val(perAnnualIncome);
		top.$.jBox.tip.mess = null;
		var cocust = $("#coCustInfoForm").serializeJson();
		$.post("${ctx}/credit/coCustInfo/save", cocust, function(data) {
			if (data) {
				if (data.status == 1) {//保存成功
					alertx(data.message, function() {
						parent.$.loadDiv("coCustInfo", "${ctx }/credit/coCustInfo/list", {
							applyNo : '${coCustWorkInfo.currApplyNo}'
						}, "post");
						closeJBox();
					});
				} else {
					alertx(data.message);
				}
			}
		});
	}
	//根据证件号生成年龄
	function ensureAgeCo(card) {
		$.ajax({
		type : "post",
		data : {
			card : card
		},
		url : "${ctx}/credit/coCustInfo/ensureAge",
		dataType : "json",
		success : function(data) {
			if (data.age != 0) {
				$("#ageNo").val(data.age);
			}
		},
		error : function(msg) {
			alertx("未能保存，请查看后台信息");
		}
		});
	}
</script>
</head>
<body>
	<div class="searchInfo">
		<h3 class="searchTitle">共借人信息</h3>
		<div class="searchCon">
			<form:form id="coCustInfoForm" modelAttribute="coCustWorkInfo" action="${ctx}/credit/coCustInfo/save" method="post" class="form-horizontal">
				<form:hidden path="custId" />
				<form:hidden path="id" />
				<form:hidden path="custInfo.id" />
				<input type="hidden" name="custInfo.currApplyNo" value="${coCustWorkInfo.custInfo.currApplyNo }" />
				<sys:message content="${message}" />
				<table class="fromTable filter">
					<tr>
						<td class="ft_label">姓名：</td>
						<td class="ft_content">
							<form:input type="text" path="custInfo.custName" maxlength="20" htmlEscape="false" class="input-medium required" />
						</td>
						<td class="ft_label">性别：</td>
						<td class="ft_content">
							<form:select id="sexNo" path="custInfo.sexNo" class="input-medium required" cssStyle="width:177px;">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
						</td>
						<td class="ft_label">移动电话：</td>
						<td class="ft_content">
							<form:input type="text" path="custInfo.mobileNum" maxlength="11" htmlEscape="false" class="mobile input-medium required" />
						</td>
					</tr>
					<tr>
						<td class="ft_label">证件类型：</td>
						<td class="ft_content">
							<form:select id="idType" path="custInfo.idType" class="input-medium required" cssStyle="width:177px;">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('CUSTOMER_P_ID_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
						</td>
						<td class="ft_label">证件号：</td>
						<td class="ft_content">
							<form:input type="text" path="custInfo.idNum" maxlength="18" htmlEscape="false" onblur="ensureAgeCo(this.value)" class="input-medium required card" />
						</td>
						<td class="ft_label">年龄：</td>
						<td class="ft_content">
							<input type="text" id="ageNo" maxlength="2" name="custInfo.ageNo" readonly="true" class="input-medium" value="${coCustWorkInfo.custInfo.ageNo}" />
						</td>
					</tr>
					<tr>
						<td class="ft_label">身份证起始日期：</td>
						<td class="ft_content">
							<input id="personIdStartDate" name="custInfo.personIdStartDate" type="text" maxlength="20" class="input-medium required Wdate " value="${coCustWorkInfo.custInfo.personIdStartDate}" onclick="WdatePicker({onpicked:dateWhite(this),maxDate:'#F{$dp.$D(\'personIdEndDate\')}'})" />
						</td>
						<td class="ft_label">无限期：</td>
						<td class="ft_label" style="text-align: left;">
							<input type="radio" name="cardFlag" id="yes" value="yes" onchange="changeShowOrHide()" />
							是
							<input type="radio" id="no" name="cardFlag" value="no" onchange="changeShowOrHide()" />
							否
						</td>
						<td class="ft_label"><span id="cardEndText">身份证终止日期：</span></td>
						<td class="ft_content">
							<input id="personIdEndDate" name="custInfo.personIdEndDate" type="text" maxlength="20" class="input-medium required Wdate " value="${coCustWorkInfo.custInfo.personIdEndDate}" onclick="WdatePicker({onpicked:dateWhite(this),minDate:'#F{$dp.$D(\'personIdStartDate\')}'})" />
						</td>
					</tr>
					<tr>
						<td class="ft_label">最高学历：</td>
						<td class="ft_content">
							<form:select id="topEducationNo" path="custInfo.topEducationNo" class="input-medium required" cssStyle="width:177px;">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('EDUCATION')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
						</td>
						<td class="ft_label">婚姻状况：</td>
						<td class="ft_content">
							<form:select id="wedStatus" path="custInfo.wedStatus" class="input-medium required" cssStyle="width:177px;">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('WED_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
						</td>
						<td class="ft_label">子女数量：</td>
						<nobr>
							<td class="ft_content">
								<form:select id="childrenSon" path="custInfo.childrenSon" class="input-medium required" cssStyle="width:33%">
									<form:option value="" label="" />
									<form:options items="${fns:getDictList('CHILDREN_NUM')}" itemLabel="label" itemValue="value" htmlEscape="false" />
								</form:select>
								男
								<form:select id="childrenGirl" path="custInfo.childrenGirl" class="input-medium required" cssStyle="width:33%">
									<form:option value="" label="" />
									<form:options items="${fns:getDictList('CHILDREN_NUM')}" itemLabel="label" itemValue="value" htmlEscape="false" />
								</form:select>
								女
							</td>
						</nobr>
					</tr>
					<tr>
						<td class="ft_label">户籍地址：</td>
						<td class="ft_content" colspan="5">
							<form:select path="custInfo.regProvince" id="regProvince" class="input-small required" onchange="loadCity('reg')">
								<option value="">请选择</option>
								<form:options items="${regProvinceMap}" htmlEscape="false" />
							</form:select>
							&nbsp;省
							<font color="red">*</font>
							<form:select path="custInfo.regCity" id="regCity" class="input-small required" onchange="loadDistinct('reg')">
								<option value="">请选择</option>
								<form:options items="${regCityMap}" htmlEscape="false" />
							</form:select>
							&nbsp;市
							<font color="red">*</font>
							<form:select path="custInfo.regDistinct" id="regDistinct" class="input-small required" onchange="finishDistinct()">
								<option value="">请选择</option>
								<form:options items="${regDistinctMap}" htmlEscape="false" />
							</form:select>
							&nbsp;区&nbsp;
							<font color="red">*</font>
							<span style="width: 15px; display: inline-block;"></span>
							地址明细：
							<input type="text" name="custInfo.regDetails" id="regDetails" class="input-medium required" maxlength="200" value="${coCustWorkInfo.custInfo.regDetails}" style="margin: 0px; position: relative; display: inline-block; vertical-align: middle;" />
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">现居住地：</td>
						<td class="ft_content" colspan="5">
							<form:select path="custInfo.contProvince" id="contProvince" class="input-small required" onchange="loadCity()" d="true">
								<option value="">请选择</option>
								<form:options items="${contProvinceMap}" htmlEscape="false" />
							</form:select>
							&nbsp;省
							<font color="red">*</font>
							<form:select path="custInfo.contCity" id="contCity" class="input-small required" onchange="loadDistinct()">
								<option value="">请选择</option>
								<form:options items="${conCityMap}" htmlEscape="false" />
							</form:select>
							&nbsp;市
							<font color="red">*</font>
							<form:select path="custInfo.contDistinct" id="contDistinct" class="input-small required">
								<option value="">请选择</option>
								<form:options items="${contDistinctMap}" htmlEscape="false" />
							</form:select>
							&nbsp;区&nbsp;
							<font color="red">*</font>
							<span style="width: 15px; display: inline-block;"></span>
							地址明细：
							<input type="text" name="custInfo.contDetails" id="contDetails" value="${coCustWorkInfo.custInfo.contDetails}" class="input-medium required" maxlength="200" style="margin: 0px; position: relative; vertical-align: middle;" />
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">与借款人关系：</td>
						<td class="ft_content">
							<form:select path="custInfo.relationForApply" id="relationForApply" class="input-small required">
								<option value="">请选择</option>
								<form:options items="${fns:getDictList('CONTACT_RELATIONS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
						</td>
						<td class="ft_label">居住状况：</td>
						<td class="ft_content">
							<form:select path="custInfo.livingStatus" id="livingStatus" class="input-small required">
								<option value="">请选择</option>
								<form:options items="${fns:getDictList('LIVING_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
						</td>
						<td class="ft_label">名下是否有房产：</td>
						<td class="ft_content">
							<form:select path="custInfo.isFixedHouse" id="isFixedHouse" class="input-small required">
								<option value="">请选择</option>
								<form:options items="${fns:getDictList('IS_HAVE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
						</td>
					</tr>
					<tr>
						<td class="ft_label">居住状况说明：</td>
						<td class="ft_content" colspan="5">
							<form:textarea path="custInfo.livingStatusDesc" maxlength="300" rows="5" class="area-length required" htmlEscape="false" />
						</td>
					</tr>
					<tr>
						<td class="ft_label">个人年收入：</td>
						<td class="ft_content">
							<input id="custInfo.perAnnualIncome" name="custInfo.perAnnualIncome" type="text" value="<fmt:formatNumber value="${coCustWorkInfo.custInfo.perAnnualIncome}" pattern="###,##0.00"/>" onkeyup="keyPress11(this)" onblur="this.value=outputmoney(this.value);" maxlength="18" class="input-medium required " />
						</td>
						<td class="ft_label">收入来源：</td>
						<td class="ft_content" colspan="3">
							<form:input path="custInfo.sourceOfIncome" class="input-large required" style="width:80%;" />
						</td>
					</tr>
					<tr>
						<td class="ft_label">紧急联系人：</td>
						<td class="ft_content">
							<form:input path="custInfo.energentName" class="input-medium required" />
						</td>
						<td class="ft_label">紧急联系电话：</td>
						<td class="ft_content">
							<form:input path="custInfo.energentMobileNum" class=" mobile input-medium required" />
						</td>
					</tr>
					<tr>
						<td class="ft_label">备注：</td>
						<td class="ft_content" colspan="5">
							<form:textarea path="custInfo.remarks" maxlength="1000" rows="5" class="area-length required" htmlEscape="false" />
						</td>
					</tr>
				</table>
				<h3 class="searchTitle">共借人工作信息</h3>
				<div class="searchCon">
					<input type="hidden" id="custIdForCustWorkId" name="id" />
					<input type="hidden" id="custIdForCustWork" name="custInfo.id" />
					<table class="fromTable filter">
						<tr>
							<td class="ft_label">单位名称：</td>
							<td class="ft_content">
								<form:input path="companyName" htmlEscape="false" maxlength="300" class="input-medium required" />
							</td>
							<td class="ft_label">单位性质：</td>
							<td class="ft_content">
								<form:select path="comNature" class="input-medium required">
									<form:option value="" label="" />
									<form:options items="${fns:getDictList('COMPANY_PROPERTY')}" itemLabel="label" itemValue="value" htmlEscape="false" />
								</form:select>
							</td>
						</tr>
						<tr>
							<td class="ft_label">单位地址：</td>
							<td class="ft_content" colspan="5">
								<form:select path="companyProvince" id="companyProvince" class="input-small required" onchange="workLoadCity()" d="true">
									<option value="">请选择</option>
									<form:options items="${companyProvinceMap}" htmlEscape="false" />
								</form:select>
								&nbsp;省
								<font color="red">*</font>
								<form:select path="companyCity" id="companyCity" class="input-small required" onchange="workLoadDistinct()">
									<option value="">请选择</option>
									<form:options items="${companyCityMap}" htmlEscape="false" />
								</form:select>
								&nbsp;市
								<font color="red">*</font>
								<form:select path="companyDistinct" id="companyDistinct" class="input-small required">
									<option value="">请选择</option>
									<form:options items="${companyDistinctMap}" htmlEscape="false" />
								</form:select>
								&nbsp;区&nbsp;
								<font color="red">*</font>
								<span style="width: 15px; display: inline-block;"></span>
								地址明细：
								<input type="text" name="companyDetails" id="companyDetails" value="${coCustWorkInfo.companyDetails}" class="input-medium required" maxlength="200" style="margin: 0px; position: relative; vertical-align: middle;" />
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td class="ft_label">单位电话：</td>
							<td class="ft_content">
								<form:input path="comPhoneAr" htmlEscape="false" maxlength="4" class="input-mini number required " />
								（区号）
							</td>
							<td class="ft_content">
								<form:input path="comPhoneSb" htmlEscape="false" style="width:65%" maxlength="8" class="input-mini number required" />
								（总机）
							</td>
							<td class="ft_content">
								<form:input path="comPhoneEx" htmlEscape="false" style="width:65%" maxlength="8" class="input-mini number required" />
								（分机）
							</td>
						</tr>
						<tr>
							<td class="ft_label">职位类别：</td>
							<td class="ft_content">
								<form:select path="postType" class="input-medium required">
									<form:option value="" label="" />
									<form:options items="${fns:getDictList('POST_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
								</form:select>
							</td>
							<td class="ft_label">职位级别：</td>
							<td class="ft_content">
								<form:select path="postLevel" class="input-medium required">
									<form:option value="" label="" />
									<form:options items="${fns:getDictList('POST_LEVEL')}" itemLabel="label" itemValue="value" htmlEscape="false" />
								</form:select>
							</td>
							<td class="ft_label">职位名称：</td>
							<td class="ft_content">
								<form:input path="postName" htmlEscape="false" maxlength="100" class="input-medium required" />
							</td>
						</tr>
						<tr>
							<td class="ft_label">本单位入职日期(年)：</td>
							<td class="ft_content">
								<input id="workInDate" name="workInDate" type="text" maxlength="3" class="input-medium number" value="${coCustWorkInfo.workInDate}" />
							</td>
						</tr>
						<tr>
							<td class="searchButton" colspan="6" style="text-align: right;">
								<c:if test="${readOnly ne '0' }">
									<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />
								</c:if>
								<input id="btnCancel" class="btn btn-primary" type="button" value="返 回" onclick="closeJBox();" />
							</td>
						</tr>
					</table>
			</form:form>
		</div>
	</div>
	</div>
</body> --%>