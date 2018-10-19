<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>车辆抵质押物信息管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$("div[id='company']").hide();
		$("div[id='personal']").hide();
		//$("#name").focus();
		$("#carForm").validate({
		submitHandler : function(form) {
			loading('正在提交，请稍等...');
			form.submit();
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
				checkReq(error,element);
		}
		});
		propertySelect();
		
		adjustTextareaLength("otherInformation","preCar");
		
		new CitySelect({
		data : data,
		provId : '#operateProvince',
		cityId : '#operateCity',
		areaId : '#operateDistinct',
		isSelect : false
		});
		if (!checkIsNull('${mortgageCarInfo.operateDistinct}')) {
				setName(data, "operate", '${mortgageCarInfo.operateProvince}', '${mortgageCarInfo.operateCity}', '${mortgageCarInfo.operateDistinct}');
			}
		
	});

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
				$("#btnClose").show();
				$("#btnClose").attr("value", "关闭");
			});
		});
	</script>
</c:if>
</head>
<body onload="propertyShow();">
	<form:form id="carForm" modelAttribute="mortgageCarInfo" action="${ctx}/credit/mortgageCarInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="applyNo"/>
		<sys:message content="${message}" />
		<h3 class="searchTitle">车辆抵质押物信息</h3>
		<div>
			<div class="filter">
			 <pre class="input-xxlarge pre-style textarea-width"  id="preCar"></pre>
				<table class="fromtable">
					<tr>
						<td class="ft_label">产权属性：</td>
						<td class="ft_content">
							<form:select path="propertyRight" id="propertyRight" class="input-medium required" onclick="propertySelect();">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('PROPERTY_RIGHT')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
						</td>
						<td class="ft_label">产权人姓名：</td>
						<td class="ft_content" colspan="3">
							<form:input path="propertyName" style="width:70%;"  maxlength="20" class="input-medium required" />
							<font color="red">*</font>
						</td>
						
					</tr>
					<tr>
						<td class="ft_label">车辆品牌：</td>
						<td class="ft_content">
							<form:input path="vehicleBrand" htmlEscape="false" maxlength="10" class="input-medium required" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">车辆类型：</td>
						<td class="ft_content">
							<form:input path="vehicleType" htmlEscape="false" maxlength="10" class="input-medium required" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">车辆型号：</td>
						<td class="ft_content">
							<form:input path="vehicleSeries" htmlEscape="false" maxlength="50" class="input-medium required" />
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">抵押质押类型：</td>
						<td class="ft_content">
							<form:select path="mortgageType" class="input-medium required">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('MORTGAGE_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<font color="red">*</font>
						</td>
						<td class="ft_label">购置日期：</td>
						<td class="ft_content">
							<input name="buyDate" id="buyDate" type="text"  maxlength="20" class="input-medium Wdate required" value="<fmt:formatDate value="${mortgageCarInfo.buyDate}" pattern="yyyy-MM-dd"/>"
								onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd',maxDate:new Date(),isShowClear:false});" />
							<font color="red">*</font>
						</td>
					   <td class="ft_label">是否推送外访系统：</td>
						<td class="ft_content">
							<form:select path="svFlag" id="svFlag" class="input-small required" onchange="valueIsRquired(this.value)">
								<option value="">请选择</option>
								<form:options items="${fns:getDictList('SV_FLAG')}" itemLabel="label" itemValue="value" htmlEscape="false" />
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
						<td class="ft_label">产权共有人姓名：</td>
						<td class="ft_content">
							<form:input path="propertyOfComOwners" htmlEscape="false" maxlength="20" class="input-medium required" />
						</td>
						<td colspan="2"></td>
					</tr>
					<tr>
						<td class="ft_label">其他信息：</td>
						<td class="ft_content" colspan="5">
							<form:textarea path="otherInformation" id="otherInformation" htmlEscape="false" rows="4" maxlength="1000" class="input-xxlarge textarea-style textarea-width required" onkeyup="adjustTextareaLength('otherInformation','preCar')"/>
						</td>
					</tr>
				</table>
			</div>
			<div class="filter" id="company">
				<h3 class="infoListTitle">企业车辆车主信息</h3>
				<table class="fromTable">
					<tr>
						<td class="ft_label">单位名称：</td>
						<td class="ft_content" colspan="2">
							<form:input path="busiRegName" style="width:90%" maxlength="300" class="input-medium required" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">营业执照号：</td>
						<td class="ft_content" colspan="5">
							<form:input path="busiLicRegNo" htmlEscape="false" maxlength="50" class="input-medium required" />
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">户口所在地址：</td>
						<td class="ft_content" colspan="4">
						<form:select path="operateProvince" class="input-small nuNullCheck" data-code="-1" ></form:select>
						&nbsp;省
						<form:select path="operateCity" class="input-small nuNullCheck" data-code="-1" ></form:select>
						&nbsp;市
						<form:select path="operateDistinct" class="input-small nuNullCheck" data-code="-1" ></form:select>
						&nbsp;区&nbsp;
							<span style="width: 60px; display: inline-block;">详细地址：</span>
							<input type="text" name="operateDetails" id="operateDetails" class="input-medium required" maxlength="200" value="${mortgageCarInfo.operateDetails}"
								style="margin: 0px; position: relative; display: inline-block; vertical-align: middle;" />
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">法定代表人：</td>
						<td class="ft_content">
							<form:input path="corporationName" htmlEscape="false" maxlength="300" class="input-medium required" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">法定代表人身份证号：</td>
						<td class="ft_content">
							<form:input path="corporationCardIdNo" htmlEscape="false" maxlength="18" class="input-medium card required" />
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">委托办理人：</td>
						<td class="ft_content">
							<form:input path="clientName" htmlEscape="false" maxlength="20" class="input-medium required" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">委托办理人身份证号：</td>
						<td class="ft_content">
							<form:input path="clientIdNo" htmlEscape="false" maxlength="18" class="input-medium card required" />
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="ft_label">委托办理人与公司的关系：</td>
						<td class="ft_content">
							<form:input path="clientCompanyRelation" htmlEscape="false" maxlength="4" class="input-medium required" />
							<font color="red">*</font>
						</td>
						<td class="ft_label">委托办理人联系方式：</td>
						<td class="ft_content">
							<form:input path="clientPhone" htmlEscape="false" maxlength="15" class="input-medium mobile required" />
							<font color="red">*</font>
						</td>
					</tr>
				</table>
			</div>
			<div class="filter">
				<table class="fromTable">
					<tr>
						<td colspan="5"></td>
						<td class="searchButton" style="text-align: right">
							<shiro:hasPermission name="credit:mortgageInfo:view">
								<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />&nbsp;
							</shiro:hasPermission>
							<input id="btnClose" class="btn btn-primary" type="button" value="取 消" onclick="closeJBox()" />
							&nbsp;
						</td>
						
					</tr>
				</table>
			</div>
		</div>
	</form:form>
	<c:if test="${not empty closeWindow}">
		<script type="text/javascript">
			alertx('${message}', function() {
				parent.$.loadDiv("car", "${ctx }/credit/mortgageCarInfo/list", {applyNo:'${mortgageCarInfo.applyNo}'}, "post");
				closeTip();
				closeJBox();
			});
		</script>
	</c:if>
</body>
</html>