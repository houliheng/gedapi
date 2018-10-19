<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>借款审核表管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#creReviewedId").hide();
		$("textarea").css("width", $(window).width() * 0.22 - 30);
		if ("${checkReviewedId}" == "true" && "${readOnly}" == "false") {
			//document.getElementById("creReviewedId").style.visibility="visible";
			$("#creReviewedId").show();
			$("#passFlag3").removeAttr("disabled");
			$("#passFlag31").removeAttr("disabled");
			$("#passFlag4").removeAttr("disabled");
			$("#passFlag41").removeAttr("disabled");
			$("#passFlag5").removeAttr("disabled");
			$("#passFlag51").removeAttr("disabled");
			$("#remark3").removeAttr("readonly");
			$("#remark4").removeAttr("readonly");
			$("#remark5").removeAttr("readonly");
		}
		var sign = 0;
		<c:forEach items="${ruleLst}" var ="ruleFom">
		var passFlag = "'passFlag" + sign + "'";
		var remark = "remark" + sign;
		var value = "${ruleFom.passFlag}";
		$("input[name=" + passFlag + "][value=" + value + "]").attr("checked", true);
		sign = sign + 1
		$("#" + remark).val('${ruleFom.remark}');
		</c:forEach>
	});

	function submit() {
		if (checkIsNull($("input[name='passFlag3']:checked").val()) || checkIsNull($("input[name='passFlag4']:checked").val()) || checkIsNull($("input[name='passFlag5']:checked").val())) {
			alertx("请填写审批结果！");
		} else if ($("input[name='passFlag3']:checked").val() == "0" && checkIsNull($("#remark3").val().replace(/\s/g, ""))) {
			alertx("请填写备注信息！");
		} else if ($("input[name='passFlag4']:checked").val() == "0" && checkIsNull($("#remark4").val().replace(/\s/g, ""))) {
			alertx("请填写备注信息！");
		} else if ($("input[name='passFlag5']:checked").val() == "0" && checkIsNull($("#remark5").val().replace(/\s/g, ""))) {
			alertx("请填写备注信息！");
		} else {
			loading();
			top.$.jBox.tip.mess = null;
			var formJson = [ {
			"reviewedBook" : '${ruleLst0.id}',
			"passFlag" : $("input[name='passFlag3']:checked").val(),
			"remark" : $("#remark3").val()
			}, {
			"reviewedBook" : '${ruleLst1.id}',
			"passFlag" : $("input[name='passFlag4']:checked").val(),
			"remark" : $("#remark4").val()
			}, {
			"reviewedBook" : '${ruleLst2.id}',
			"passFlag" : $("input[name='passFlag5']:checked").val(),
			"remark" : $("#remark5").val()
			} ];
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

	function jcshsClick() {
		$("#jcsh").toggle(600);
	}
</script>
</head>
<body>
	<div class="wrapper">
		<sys:message content="${message}" />
		<div class="tab-content">
			<div class="tableList">
				<h3 onclick="jcshsClick()" class="tableTitle">基础审核</h3>
				<div id="jcsh">
					<table id="contentTable" class="table  table-bordered table-condensed">
						<thead>
							<tr>
								<th>审核科目</th>
								<th>审核对象</th>
								<th>审核工具</th>
								<th>材料要求</th>
								<th>禁入标准</th>
								<th>替换手段</th>
								<th style="width: 25%">审批结果</th>
								<th style="width: 20%">备注</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td rowspan="3">${ruleLst0.reviewedBook}</td>
								<td rowspan="9">${ruleLst0.reviewedTarget}</td>
								<td rowspan="9">${ruleLst0.reviewedTool}</td>
								<td rowspan="9">${ruleLst0.dataRequied}</td>
								<td rowspan="3">${ruleLst0.banRule}</td>
								<td rowspan="9">${ruleLst0.replaceMeans}</td>
								<td style="background-color: #F5F5F5">
									<label>分公司审核:</label>
									&nbsp;&nbsp;
									<input type="radio" disabled="true" name="passFlag0" value="0" id="radio_yes" class="required">
									<label for="radio_yes">碰触</label>
									&nbsp;&nbsp;
									<input type="radio" disabled="true" name="passFlag0" value="1" id="radio_no" class="required">
									<label for="radio_no">不碰触</label>
								</td>
								<td>
									<textarea id="remark0" style="height: 70px;" readonly="readonly"></textarea>
								</td>
							</tr>
							<tr>
								<td rowspan="1">
									<label>区域审核 :</label>
									&nbsp;&nbsp;
									<input type="radio" name="passFlag3" disabled="disabled" value="0" id="passFlag3" class="required">
									<label for="radio_yes">碰触</label>
									&nbsp;&nbsp;
									<input type="radio" name="passFlag3" disabled="disabled" value="1" id="passFlag31" class="required">
									<label for="radio_no">不碰触</label>
									<font style="color: red">*</font>
								</td>
								<td>
									<textarea id="remark3" readonly="readonly" maxlength="1000" style="height: 70px;"></textarea>
								</td>
							</tr>
							<tr>
							</tr>
							<tr>
								<td rowspan="3">${ruleLst1.reviewedBook}</td>
								<td rowspan="3">${ruleLst2.banRule}</td>
								<td rowspan="1" style="background-color: #F5F5F5">
									<label>分公司审核 :</label>
									&nbsp;&nbsp;
									<input type="radio" disabled="true" name="passFlag1" value="0" id="radio_yes" class="required">
									<label for="radio_yes">碰触</label>
									&nbsp;&nbsp;
									<input type="radio" disabled="true" name="passFlag1" value="1" id="radio_no" class="required">
									<label for="radio_no">不碰触</label>
								<td>
									<textarea id="remark1" style="height: 70px;" readonly="readonly"></textarea>
								</td>
							</tr>
							<tr>
								<td rowspan="1">
									<label>区域审核 :</label>
									&nbsp;&nbsp;
									<input type="radio" name="passFlag4" disabled="disabled" value="0" id="passFlag4" class="required">
									<label for="radio_yes">碰触</label>
									&nbsp;&nbsp;
									<input type="radio" name="passFlag4" disabled="disabled" value="1" id="passFlag41" class="required">
									<label for="radio_no">不碰触</label>
									<font style="color: red">*</font>
								</td>
								<td>
									<textarea id="remark4" readonly="readonly" maxlength="1000" style="height: 70px;"></textarea>
								</td>
							</tr>
							<tr>
							</tr>
							<tr>
								<td rowspan="3">${ruleLst2.reviewedBook}</td>
								<td rowspan="3">${ruleLst2.banRule}</td>
								<td rowspan="1">
									<label>分公司审核:</label>
									&nbsp;&nbsp;
									<input type="radio" disabled="true" name="passFlag2" value="0" id="radio_yes" class="required">
									<label for="radio_yes">碰触</label>
									&nbsp;&nbsp;
									<input type="radio" disabled="true" name="passFlag2" value="1" id="radio_no" class="required">
									<label for="radio_no">不碰触</label>
								<td>
									<textarea id="remark2" style="height: 70px;" readonly="readonly"></textarea>
								</td>
							</tr>
							<tr>
								<td rowspan="1">
									<label>区域审核 :</label>
									&nbsp;&nbsp;
									<input type="radio" name="passFlag5" disabled="disabled" value="0" id="passFlag5" class="required">
									<label for="radio_yes">碰触</label>
									&nbsp;&nbsp;
									<input type="radio" name="passFlag5" disabled="disabled" value="1" id="passFlag51" class="required">
									<label for="radio_no">不碰触</label>
									<font style="color: red">*</font>
								</td>
								<td>
									<textarea id="remark5" readonly="readonly" maxlength="1000" style="height: 70px;"></textarea>
								</td>
							</tr>
							<tr>
							</tr>
						</tbody>
					</table>
					<div id="creReviewedId" class="form-actions">
						<shiro:hasPermission name="credit:reviewedRule:edit">
							<input id="btnSubmit" class="btn btn-primary" onclick="submit()" type="button" value="保 存" />&nbsp;</shiro:hasPermission>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div>
		<%@ include file="/WEB-INF/views/app/credit/reviewedRule/tsbstksh/s2ruleBList.jsp"%>
	</div>
	<c:if test="${productId eq '1' || productId eq '7' }">
		<div>
			<%@ include file="/WEB-INF/views/app/credit/reviewedRule/xyjksh/s3ruleBList.jsp"%>
		</div>
		<div>
			<%@ include file="/WEB-INF/views/app/credit/reviewedRule/xyjkshS/s3ruleBListS.jsp"%>
		</div>
	</c:if>
	<c:if test="${productId eq '4'}">
		<div>
			<%@ include file="/WEB-INF/views/app/credit/reviewedRule/hhjksh/s4_1ruleBList.jsp"%>
		</div>
		<div>
			<%@ include file="/WEB-INF/views/app/credit/reviewedRule/hhjksh/s4_2ruleBList.jsp"%>
		</div>
	</c:if>
	<c:if test="${productId eq '2'}">
		<div>
			<%@ include file="/WEB-INF/views/app/credit/reviewedRule/fcdyjksh/s5ruleBList.jsp"%>
		</div>
		<div>
			<%@ include file="/WEB-INF/views/app/credit/reviewedRule/cldzyjksh/s6ruleBList.jsp"%>
		</div>
	</c:if>
	<div>
		<input id="btnPrint" class="btn btn-primary noprint" type="button" value="打印本页面" onclick="window.print()" />
	</div>
</body>
</html>