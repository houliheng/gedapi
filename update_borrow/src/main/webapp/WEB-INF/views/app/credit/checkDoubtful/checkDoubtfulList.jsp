<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>借前外访管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {

		if ('${actTaskParam.status}' == '1') {
			$("#addCheckDoubtDiv").hide();
		}

		$("#resultForm").validate({
		submitHandler : function(form) {
			var flag = $("input[name='passFlag']:checked").val();
			if (flag == "yes") {
				if ('${visitCountFlag}' == "true") {
					loading('正在提交，请稍等...');
					saveResultFun();
				} else {
					alertx("必须录入至少一条外访信息！");
				}
			} else {
				loading('正在提交，请稍等...');
				saveResultFun();
			}
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});

		$("#checkDoubtfulForm").validate({
		submitHandler : function(form) {
			loading('正在提交，请稍等...');
			form.submit();
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});

		adjustTextareaLength("description", "preDescription");

		adjustTextareaLength("suggestionDesc", "preSuggestionDesc");
	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
	function saveResultFun() {
		var flag = $("input[name='passFlag']:checked").val();
		if (checkIsNull(flag)) {
			alertx("请选择外访结论！");
		} else {
			top.$.jBox.tip.mess = null;
			var formJson = $("#resultForm").serializeJson();
			$.post("${ctx}/credit/checkDoubtful/saveResult", formJson, function(data) {
				if (data) {
					closeTip();
					if (data.status == 1) {//保存成功
						alertx(data.message, function() {
							goToPage('${ctx}${actTaskParam.headUrl}', 'checkDoubtfulSkipId');
						});
					} else {
						alertx(data.message);
					}
				}
			});
		}
	}
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
		<div id="addCheckDoubtDiv" class="filter">
			<h3 class="infoListTitle">新增外访信息</h3>
			<form:form id="checkDoubtfulForm" modelAttribute="checkDoubtful" action="${ctx}/credit/checkDoubtful/save" method="post" class="form-horizontal">
				<form:hidden path="applyNo" value="${actTaskParam.applyNo}" />
				<input type="hidden" id="taskId" name="taskId" value="${actTaskParam.taskId}" />
				<input type="hidden" id="taskDefKey" name="taskDefKey" value="${actTaskParam.taskDefKey}" />
				<input type="hidden" id="procDefId" name="procDefId" value="${actTaskParam.procDefId}" />
				<input type="hidden" id="status" name="status" value="${actTaskParam.status}" />
				<input type="hidden" id="procInstId" name="procInstId" value="${actTaskParam.procInstId}" />
				<input type="hidden" id="headUrl" name="headUrl" value="${actTaskParam.headUrl}" />
				<sys:message content="${message}" />
				<table class="fromTable" width="100%">
					<tr>
						<td class="" style="text-align: right; width:10% ">外访日期：</td>
						<td class="" width="40%">
							<input name="checkDate" id="checkDate" type="text" maxlength="20" class="required input-medium Wdate" value="${checkDoubtful.checkDate }" onclick="WdatePicker({onpicked:dateWhite(this),maxDate:new Date()})" />
							<font color="red">*</font>
						</td>
						<td class="" style="text-align: right; width: 10% ">外访人：</td>
						<td class="" width="40%">
							<input name="checkUserName" readonly="readonly" id="checkUserName" type="text" maxlength="100" class="input-medium required" value="${checkDoubtful.checkUserName }" />
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="" style="text-align: right; width: 10% ">外访地点：</td>
						<td colspan="3" class="">
							<input name="checkAddress" id="checkAddress" style="width: 75%" type="text" maxlength="300" class="required" />
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="" style="text-align: right; width: 10% ">异常风险点：</td>
						<td class="" colspan="3">
							<form:checkboxes path="riskPoint" class="required" itemLabel="label" itemValue="value" htmlEscape="false" items="${fns:getDictList('CUST_RISK_POINT')}" />
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td class="" style="text-align: right; width: 10% ">外访详情：</td>
						<td class="" colspan="5">
							<pre class="input-medium pre-style" style="width:825px" id="preDescription"></pre>
							<textarea rows="5" cols="300" class="input-medium textarea-style required" style="width:825px" maxlength="1000" name="description" id="description" onkeyup="adjustTextareaLength('description','preDescription')"></textarea>
							<font color="red">*</font>
						</td>
					</tr>
				</table>
				<div class="searchButton">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />
					&nbsp;
				</div>
			</form:form>
			<c:if test="${not empty saveTip}">
				<script type="text/javascript">
					alertx('${saveMessage}', function() {
						closeJBox();
					});
				</script>
			</c:if>
		</div>
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 class="tableTitle">外访信息列表</h3>
			<div id="tableDataId" style="max-height: 400px; overflow: auto;">
				<table id="contentTable" cellpadding="0" cellspacing="0" border="1" width="100%">
					<thead>
						<tr>
							<th width="30px">序号</th>
							<th>外访日期</th>
							<th>外访人</th>
							<th>外访地点</th>
							<th>异常风险点</th>
							<th>外访详情</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${checkDoubtfullList}" var="checkDoubtful" varStatus="index">
							<c:if test="${0 == index.count%2}">
								<tr class="doubleRow">
							</c:if>
							<c:if test="${1 == index.count%2}">
								<tr>
							</c:if>
							<td id="num" class="title" title="序号">${index.index+1}</td>
							<td id="checkDate" class="title" title="${checkDoubtful.checkDate}">
								<fmt:formatDate value="${checkDoubtful.checkDate}" pattern="yyyy-MM-dd" />
							</td>
							<td id="checkUserName" class="title" title="${checkDoubtful.checkUserName}">${checkDoubtful.checkUserName}</td>
							<td id="checkAddress" class="title" title="${checkDoubtful.checkAddress}">${checkDoubtful.checkAddress}</td>
							<td id="riskPoint" class="title" title="${fns:getDictLabels(checkDoubtful.riskPoint, 'CUST_RISK_POINT', '')}">${fns:getDictLabels(checkDoubtful.riskPoint, 'CUST_RISK_POINT', '')}</td>
							<td id="description" class="title" title="${checkDoubtful.description}">${checkDoubtful.description}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<c:if test="${empty noInsert}">
			<div id="isHideSuggestionDiv">
				<div id="rs_msg"></div>
				<div class="infoList">
					<form id="resultForm" action="#" method="post">
						<div class="infoList">
							<h3 class="infoListTitle">外访结论</h3>
							<!-- 跳转用a标签 -->
							<a id="checkDoubtfulSkipId" target="_parent"></a>
							<input type="hidden" name="applyNo" value="${actTaskParam.applyNo}" />
							<input type="hidden" name="taskId" value="${actTaskParam.taskId}" />
							<input type="hidden" name="taskDefKey" value="${actTaskParam.taskDefKey}" />
							<input type="hidden" name="procDefId" value="${actTaskParam.procDefId}" />
							<input type="hidden" name="status" value="${actTaskParam.status}" />
							<input type="hidden" name="procInstId" value="${actTaskParam.procInstId}" />
							<input type="hidden" name="headUrl" value="${actTaskParam.headUrl}" />
							<div class="infoListCon">
								<div class="filter">
									<table class="fromTable">
										<tr>
											<td class="ft_label">外访结论：</td>
											<td class="" colspan="5">
												<input type="radio" name="passFlag" value="yes" id="radio_yes" class="required">
												<label for="radio_yes">提交</label>
												&nbsp;&nbsp;
												<input type="radio" name="passFlag" value="no" id="radio_no" class="required">
												<label for="radio_no">打回</label>
												<font color="red">*</font>
											</td>
										</tr>
										<tr>
											<td class="ft_label">外访意见：</td>
											<td class="" colspan="5">
												<pre class="input-xxlarge pre-style" style="width:825px" id="preSuggestionDesc"></pre>
												<textarea rows="5" cols="300" maxlength="1000" id="suggestionDesc" name="suggestionDesc" class="input-xxlarge textarea-style required" style="width:825px" onkeyup="adjustTextareaLength('suggestionDesc','preSuggestionDesc')"></textarea>
												<font color="red">*</font>
											</td>
										</tr>
									</table>
									<div class="searchButton">
										<input id="rsBtnSubmit" class="btn btn-primary" type="submit" value="提 交" />
										&nbsp;
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</c:if>
	</div>
</body>
</html>
