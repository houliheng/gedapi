<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>车辆抵质押物信息管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {

		new CitySelect({
		data : data,
		provId : '#contProvince',
		cityId : '#contCity',
		areaId : '#contDistinct',
		isSelect : false
		});

		if (!checkIsNull('${taskTempEntity.contDistinct}')) {
			setName(data, "cont", '${taskTempEntity.contProvince}', '${taskTempEntity.contCity}', '${taskTempEntity.contDistinct}');
		}

		getDealAllAmountFuntion();
		$("#dealSuggestionId").validate({
		submitHandler : function(form) {
			var dealStatus = $("#dealStatus").val();
			if ("0" == dealStatus) {
				confirmx("尚有抵押物未处置，确定进行提交吗？", function() {
					saveSuggestion();
				});
			} else {
				saveSuggestion();
			}
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});

		/* 引入车辆、房产、其他抵质押物的div */
		$.loadDiv("car", "${ctx }/postloan/mortgageCarInfo/dealList", {
		applyNo : '${actTaskParam.applyNo}',
		readOnly : '${readOnly}'
		}, "post");
		$.loadDiv("house", "${ctx }/postloan/mortgageHouseInfo/dealList", {
		applyNo : '${actTaskParam.applyNo}',
		readOnly : '${readOnly}'
		}, "post");
		$.loadDiv("other", "${ctx }/postloan/mortgageOtherInfo/dealList", {
		applyNo : '${actTaskParam.applyNo}',
		readOnly : '${readOnly}'
		}, "post");
	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}

	function getCheckBoxVal() {
		var str = "";
		$("input[name=type]:checkbox").each(function() {
			if ($(this).attr("checked")) {
				str += $(this).val() + ",";
			}
		});
		return str;
	}

	//处置
	function dealTask(url, message) {
		var contractNo = $("#contractNo").val();
		var width = $(window).width() - 100;
		var height = $(window).height() - 50;
		openJBox('', url, message, width, height, {
		contractNo : contractNo,
		taskDefKey : '${actTaskParam.taskDefKey}'
		});
	}

	//查看详情
	function details(url, message) {
		var width = $(window).width() - 100;
		var height = $(window).height() - 50;
		openJBox('', url, message, width, height);
	}

	function getDealAllAmountFuntion() {
		var applyNo = $("#applyNo").val();
		$.ajax({
		type : "post",
		url : "${ctx}/postloan/mortgage/getDealAllAmountFuntion",
		data : {
			applyNo : applyNo
		},
		dataType : "json",
		success : function(data) {
			$("#dealAllAmount").val(data.dealAllAmount);
			$("#dealStatus").val(data.dealStatus);
		},
		error : function(msg) {
			alertx("操作失败，请查看后台信息");
		}
		});
	}

	function saveSuggestion() {
		top.$.jBox.tip.mess = null;
		loading();
		var formJson = $("#dealSuggestionId").serializeJson();
		formJson['contractNo'] = $("#contractNo").val();
		formJson['periodNum'] = $("#periodNum").val();
		$.post("${ctx}/postloan/collateralDisposalConclusion/save", formJson, function(data) {
			if (data) {
				closeTip();
				if (data.status == 1) {
					alertx(data.message, function() {
						parent.page();
						closeJBox();
					});
				} else {
					alertx(data.message);
				}
			}
		});
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">合同基本信息</h3>
			<div class="searchCon">
				<form:form modelAttribute="taskTempEntity" action="#" method="post" class="form-horizontal">
					<form:hidden path="periodNum" />
					<input type="hidden" id="applyNo" name="applyNo" value="${actTaskParam.applyNo}" />
					<input type="hidden" id="taskId" name="taskId" value="${actTaskParam.taskId}" />
					<input type="hidden" id="taskDefKey" name="taskDefKey" value="${actTaskParam.taskDefKey}" />
					<input type="hidden" id="procDefId" name="procDefId" value="${actTaskParam.procDefId}" />
					<input type="hidden" id="status" name="status" value="${actTaskParam.status}" />
					<input type="hidden" id="procInstId" name="procInstId" value="${actTaskParam.procInstId}" />
					<!-- 是否全部处置标记  -->
					<input type="hidden" id="dealStatus" name="dealStatus" />
					<table class="fromTable filter">
						<tr>
							<td class="ft_label">合同号：</td>
							<td class="ft_content">
								<form:input path="contractNo" htmlEscape="false" class="input-medium " readOnly="true" />
							</td>
							<td class="ft_label">借款人姓名：</td>
							<td class="ft_content">
								<form:input path="custName" htmlEscape="false" class=" input-medium" readOnly="true" />
							</td>
							<td class="ft_label">手机号：</td>
							<td class="ft_content">
								<form:input path="mobileNum" htmlEscape="false" class="input-medium " readOnly="true" />
							</td>
						</tr>
						<tr>
							<td class="ft_label">合同金额：</td>
							<td class="ft_content">
								<form:input path="contractAmount" htmlEscape="false" class="input-medium " readOnly="true" />
							</td>
							<td class="ft_label">逾期天数：</td>
							<td class="ft_content">
								<form:input path="overdueDays" htmlEscape="false" class="input-medium " readOnly="true" />
							</td>
							<td class="ft_label">逾期金额：</td>
							<td class="ft_content">
								<form:input path="overdueAmount" htmlEscape="false" class="input-medium " readOnly="true" />
							</td>
						</tr>
						<tr>
							<td class="ft_label">现居地址：</td>
							<td class="ft_content" colspan="5">
								<form:select class="input-small nuNullCheck" path="contProvince" disabled="true" data-code="-1" />
								&nbsp;省
								<form:select class="input-small nuNullCheck" path="contCity" disabled="true" data-code="-1" />
								&nbsp;市
								<form:select class="input-small nuNullCheck" path="contDistinct" disabled="true" data-code="-1" />
								&nbsp;区&nbsp;
								<span style="width: 15px; display: inline-block;"></span>
								地址明细：
								<input type="text" name="contDetails" id="contDetails" readonly="true" value="${taskTempEntity.contDetails}" class="input-medium" maxlength="200" style="margin: 0px; position: relative; vertical-align: middle; width: 285px" />
							</td>
						</tr>
					</table>
				</form:form>
			</div>
		</div>
	</div>
	<div id="car"></div>
	<div id="house"></div>
	<div id="other"></div>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">处置信息</h3>
			<div class="searchCon">
				<form:form id="dealSuggestionId" modelAttribute="collateralDisposalConclusion" action="#" method="post" class="form-horizontal">
					<input type="hidden" id="applyNo" name="applyNo" value="${actTaskParam.applyNo}" />
					<input type="hidden" id="taskId" name="taskId" value="${actTaskParam.taskId}" />
					<input type="hidden" id="taskDefKey" name="taskDefKey" value="${actTaskParam.taskDefKey}" />
					<input type="hidden" id="procDefId" name="procDefId" value="${actTaskParam.procDefId}" />
					<input type="hidden" id="status" name="status" value="${actTaskParam.status}" />
					<input type="hidden" id="procInstId" name="procInstId" value="${actTaskParam.procInstId}" />
					<table id="tableIdQY" class="fromTable filter">
						<c:if test="${actTaskParam.taskDefKey == 'utask_zbrwcz' || actTaskParam.taskDefKey == 'utask_dqrwcz'}">
							<tr>
								<td class="ft_label">处置总金额：</td>
								<td class="ft_content">
									<form:input path="" htmlEscape="false" readonly="true"  value="${collateralDisposalConclusionQY.dealAllAmount}" />
								</td>
							</tr>
							<tr>
								<td class="ft_label">意见说明：</td>
								<td class="ft_content" colspan="5">
									<textarea rows="5" class="textarea-width textarea-style " disabled="disabled" htmlEscape="false">${collateralDisposalConclusionQY.conclusionDesc}</textarea>
								</td>
							</tr>
						</c:if>
						<c:if test="${actTaskParam.taskDefKey == 'utask_zbrwcz'}">
							<tr>
								<td class="ft_label">处置总金额：</td>
								<td class="ft_content">
									<form:input path="" htmlEscape="false" readonly="true" value="${collateralDisposalConclusionDQ.dealAllAmount}" />
								</td>
							</tr>
							<tr>
								<td class="ft_label">意见说明：</td>
								<td class="ft_content" colspan="5">
									<textarea rows="5" class="textarea-width textarea-style" disabled="disabled" htmlEscape="false">${collateralDisposalConclusionDQ.conclusionDesc}</textarea>
								</td>
							</tr>
						</c:if>
						<tr>
							<td class="ft_label">处置总金额：</td>
							<td class="ft_content">
								<form:input path="dealAllAmount" htmlEscape="false" readonly="true" class="required" />
								<font color="red">*</font>
							</td>
						</tr>
						<c:if test="${readOnly ne true }">
							<tr>
								<td class="ft_label" style="width: 10%;">审批结论：</td>
								<td class="ft_content" style="width: 70%;">
									<input type="radio" name="passFlag" value="yes" id="radio_yes" class="required">
									<label for="radio_yes">通过</label>
									<c:if test="${actTaskParam.taskDefKey == 'utask_dqrwcz'}">
									&nbsp;&nbsp;
									<input type="radio" name="passFlag" value="back" id="radio_back" class="required">
										<label for="radio_back">打回</label>
									</c:if>
									<c:if test="${actTaskParam.taskDefKey == 'utask_dqrwcz'}">
									&nbsp;&nbsp;
									<input type="radio" name="passFlag" value="yesToFinish" id="radio_yesToFinish" class="required">
										<label for="radio_yesToFinish">通过并结束流程</label>
									</c:if>
									<font color="red">*</font>
								</td>
							</tr>
						</c:if>
						<tr>
							<td class="ft_label">意见说明：</td>
							<td class="ft_content" colspan="5">
								<form:textarea path="conclusionDesc" rows="5" class="textarea-width textarea-style required" maxlength="1000" htmlEscape="false" />
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td class="searchButton" colspan="6" style="text-align: right;">
								<input id="btnSubmitt" type="submit" class="btn btn-primary" value="提 交" />
								&nbsp;
								<input id="btnButtonn" type="button" class="btn btn-primary" value="关 闭" onclick="closeJBox()" />
							</td>
						</tr>
					</table>
				</form:form>
			</div>
		</div>
</body>
</html>