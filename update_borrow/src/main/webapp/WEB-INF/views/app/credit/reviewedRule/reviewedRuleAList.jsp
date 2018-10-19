<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>借款审核表管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(function() {
		$("#creReviewedId").hide();
		$("textarea").css("width", $(window).width() * 0.22 - 30);
		if ("${checkReviewedId}" == "true" && "${readOnly}" == "false") {

			$("#creReviewedId").show();
			//document.getElementById("creReviewedId").style.visibility = "visible";
			$("#passFlag0").removeAttr("disabled");
			$("#passFlag01").removeAttr("disabled");
			$("#passFlag1").removeAttr("disabled");
			$("#passFlag11").removeAttr("disabled");
			$("#passFlag2").removeAttr("disabled");
			$("#passFlag21").removeAttr("disabled");
			$("#remark0").removeAttr("readonly");
			$("#remark1").removeAttr("readonly");
			$("#remark2").removeAttr("readonly");
		}

		var sign = 0
		<c:forEach items="${ruleLst}" var ="ruleFom">
		var passFlag = "'passFlag" + sign + "'";
		var value = "${ruleFom.passFlag}";
		$("input[name=" + passFlag + "][value=" + value + "]").attr("checked", true);
		sign = sign + 1
		</c:forEach>
		/* var passFlag0= '\'passFlag0\'';
		var passFlag1= '\'passFlag1\'';
		var passFlag2= '\'passFlag2\'';
		var value0= "${ruleLst0.passFlag}";
		var value1= "${ruleLst1.passFlag}";
		var value2= "${ruleLst2.passFlag}";
			$("input[name="+passFlag0+"][value="+value0+"]").attr("checked",true);
			$("input[name="+passFlag1+"][value="+value1+"]").attr("checked",true);
			$("input[name="+passFlag2+"][value="+value2+"]").attr("checked",true); */
	});

	function submit() {
		if (checkIsNull($("input[name='passFlag0']:checked").val()) || checkIsNull($("input[name='passFlag1']:checked").val()) || checkIsNull($("input[name='passFlag2']:checked").val())) {
			alertx("请填写审批结果！");
		} else if ($("input[name='passFlag0']:checked").val() == "0" && checkIsNull($("#remark0").val().replace(/\s/g, ""))) {
			alertx("请填写备注信息！");
		} else if ($("input[name='passFlag1']:checked").val() == "0" && checkIsNull($("#remark1").val().replace(/\s/g, ""))) {
			alertx("请填写备注信息！");
		} else if ($("input[name='passFlag2']:checked").val() == "0" && checkIsNull($("#remark2").val().replace(/\s/g, ""))) {
			alertx("请填写备注信息！");
		} else {
			loading();
			top.$.jBox.tip.mess = null;
			var formJson = [ {
			"reviewedBook" : '${ruleLst0.id}',
			"passFlag" : $("input[name='passFlag0']:checked").val(),
			"remark" : $("#remark0").val()
			}, {
			"reviewedBook" : '${ruleLst1.id}',
			"passFlag" : $("input[name='passFlag1']:checked").val(),
			"remark" : $("#remark1").val()
			}, {
			"reviewedBook" : '${ruleLst2.id}',
			"passFlag" : $("input[name='passFlag2']:checked").val(),
			"remark" : $("#remark2").val()
			} ];
			console.log(JSON.stringify(formJson));
			$.ajax({
			type : "post",
			url : "${ctx}/credit/reviewedRule/saveRule?taskDefKey=" + '${taskDefKey}' + '&applyNo=' + '${applyNo}' + '&sheetId=' + '1',
			data : JSON.stringify(formJson),
			contentType : "application/json",
			dataType : "json",
			success : function(data) {
				if (data.status == "1") {
					alertx(data.message, function() {
						closeTip();
					});
				} else {
					alertx(data.message, function() {
						closeTip();
					});
				}
			}
			});
		}
	}

	function jcshClick() {
		$("#jcsh").toggle(600);
	}
</script>
</head>
<body>
	<div class="wrapper">
		<sys:message content="${message}" />
		<div class="tab-content">
			<div class="tableList">
				<h3 onclick="jcshClick()" class="tableTitle">基础审核</h3>
				<div id="jcsh">
					<table id="contentTable" class="table  table-bordered table-condensed">
						<thead>
							<tr>
								<th>审核科目</th>
								<th>审核对象</th>
								<th>审核工具</th>
								<th>材料要求</th>
								<th style="width: 20%">禁入标准</th>
								<th>替换手段</th>
								<th style="width: 25%">审批结果</th>
								<th style="width: 22%">备注</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>${ruleLst0.reviewedBook}</td>
								<td rowspan="3">${ruleLst0.reviewedTarget}</td>
								<td rowspan="3">${ruleLst0.reviewedTool}</td>
								<td rowspan="3">${ruleLst0.dataRequied}</td>
								<td>${ruleLst0.banRule}</td>
								<td rowspan="3">${ruleLst0.replaceMeans}</td>
								<td class="">
									<label>分公司审核:</label>
									&nbsp;&nbsp;
									<input type="radio" name="passFlag0" disabled="disabled" id="passFlag0" value="0" class="required">
									<label for="radio_yes">触碰</label>
									&nbsp;&nbsp;
									<input type="radio" name="passFlag0" disabled="disabled" id="passFlag01" value="1" class="required">
									<label for="radio_no">不触碰</label>
									<font style="color: red">*</font>
								</td>
								<td>
									<textarea id="remark0" style="height: 70px;" maxlength="1000" readonly="readonly"" >${ruleLst0.remark}</textarea>
								</td>
							</tr>
							<tr>
								<td>${ruleLst1.reviewedBook}</td>
								<td>${ruleLst1.banRule}</td>
								<td class="">
									<label>分公司审核:</label>
									&nbsp;&nbsp;
									<input type="radio" name="passFlag1" disabled="disabled" id="passFlag1" value="0" class="required">
									<label for="radio_yes">触碰</label>
									&nbsp;&nbsp;
									<input type="radio" name="passFlag1" disabled="disabled" id="passFlag11" value="1" class="required">
									<label for="radio_no">不触碰</label>
									<font style="color: red">*</font>
								</td>
								<td>
									<textarea id="remark1" readonly="readonly" maxlength="1000" style="height: 70px;">${ruleLst1.remark}</textarea>
								</td>
							</tr>
							<tr>
								<td>${ruleLst2.reviewedBook}</td>
								<td>${ruleLst2.banRule}</td>
								<td class="">
									<label>分公司审核:</label>
									&nbsp;&nbsp;
									<input type="radio" name="passFlag2" disabled="disabled" id="passFlag2" value="0" class="required">
									<label for="radio_yes">触碰</label>
									&nbsp;&nbsp;
									<input type="radio" name="passFlag2" disabled="disabled" id="passFlag21" value="1" class="required">
									<label for="radio_no">不触碰</label>
									<font style="color: red">*</font>
								</td>
								<td>
									<textarea id="remark2" readonly="readonly" maxlength="1000" style="height: 70px;">${ruleLst2.remark}</textarea>
								</td>
							</tr>
						</tbody>
					</table>
					<div id="creReviewedId" class="form-actions">
						<shiro:hasPermission name="credit:reviewedRule:edit">
							<input id="btnSubmit" class="btn btn-primary" onclick="submit()" type="button" value="保 存" />&nbsp;
						</shiro:hasPermission>
					</div>
				</div>
			</div>
		</div>
	</div>
	<c:if test="${productId eq '1' || productId eq '7' }">
		<div>
			<%@ include file="/WEB-INF/views/app/credit/reviewedRule/xyjksh/s3ruleAList.jsp"%>
		</div>
		<div>
			<%@ include file="/WEB-INF/views/app/credit/reviewedRule/xyjkshS/s3ruleAListS.jsp"%>
		</div>
	</c:if>
	<c:if test="${productId eq '4'}">
		<div>
			<%@ include file="/WEB-INF/views/app/credit/reviewedRule/hhjksh/s4_1ruleAList.jsp"%>
		</div>
		<div>
			<%@ include file="/WEB-INF/views/app/credit/reviewedRule/hhjksh/s4_2ruleAList.jsp"%>
		</div>
	</c:if>
	<c:if test="${productId eq '2'}">
		<div>
			<%@ include file="/WEB-INF/views/app/credit/reviewedRule/fcdyjksh/s5ruleAList.jsp"%>
		</div>
		<div>
			<%@ include file="/WEB-INF/views/app/credit/reviewedRule/cldzyjksh/s6ruleAList.jsp"%>
		</div>
	</c:if>
	<div>
		<input id="btnPrint" class="btn btn-primary noprint" type="button" value="打印本页面" onclick="window.print()" />
	</div>
</body>
</html>