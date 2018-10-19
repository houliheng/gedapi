<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">
	$(function() {
		$("#dqs_s3saveButton").hide();
		$("textarea").css("width", $(window).width() * 0.22 - 30);
		if ("${checkReviewedId}" == "true" && "${readOnly}" == "false") {
			//document.getElementById("dqs_s3saveButton").style.visibility="visible";
			$("#dqs_s3saveButton").show();
			/* <c:forEach items="${fgs_xyjkshList}" var ="rule" varStatus="i">
				$("#fgs_s3passFlag${i.index}").removeAttr("disabled");
				$("#fgs_s3passFlag${i.index}1").removeAttr("disabled");
				$("#fgs_s3remark${i.index}").removeAttr("readonly");
			</c:forEach> */
		}

		var sign = 0;
		<c:forEach items="${fgs_xyjkshList}" var ="ruleFom" varStatus="i">
		var fgs_s3passFlag = "'fgs_s3passFlag" + sign + "'";
		var value = "${ruleFom.passFlag}";
		$("input[name=" + fgs_s3passFlag + "][value=" + value + "]").attr("checked", true);
		sign = sign + 1;
		</c:forEach>

		<c:forEach items="${qys_xyjkshList}" var ="ruleFom" varStatus="i">
		var value = "${ruleFom.passFlag}";
		$("input[name= qys_s3passFlag${i.index}][value=" + value + "]").attr("checked", true);
		$("#qys_s3remark${i.index}").val('${ruleFom.remark}');
		</c:forEach>

		<c:forEach items="${dqs_xyjkshList}" var ="ruleFom" varStatus="i">
		var value = "${ruleFom.passFlag}";
		$("input[name= dqs_s3passFlag${i.index}][value=" + value + "]").attr("checked", true);
		$("#dqs_s3remark${i.index}").val('${ruleFom.remark}');
		</c:forEach>
	});

	function dqs_s3submit() {
		var passIdent = false, remarkIdent = false;
		<c:forEach items="${dqs_xyjkshList}" var ="ruleFom" varStatus="i">
		if (checkIsNull($("[name=dqs_s3passFlag${i.index}]:checked").val())) {
			passIdent = true;
		}
		if ($("[name=dqs_s3passFlag${i.index}]:checked").val() == "0") {
			if (checkIsNull($("#dqs_s3remark${i.index}").val())) {
				remarkIdent = true;
			}
		}
		</c:forEach>
		if (remarkIdent) {
			alertx("请填写备注信息！");
		} else if (passIdent) {
			alertx("请填写审批结果！");
		} else {
			loading();
			top.$.jBox.tip.mess = null;
			var formJson = "[";
			<c:forEach items="${dqs_xyjkshList}" var ="ruleFom" varStatus="i">
			formJson += "{'reviewedBook':'${ruleFom.id}','passFlag':" + $("input[name='dqs_s3passFlag${i.index}']:checked").val() + ",'remark':'" + $("#dqs_s3remark${i.index}").val() + "'},";
			</c:forEach>
			formJson = formJson.substring(0, formJson.length - 1) + "]";
			$.ajax({
			type : "post",
			url : "${ctx}/credit/reviewedRule/saveRule?taskDefKey=" + '${taskDefKey}' + '&applyNo=' + '${applyNo}' + '&sheetId=' + '3',
			data : formJson,
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

	function dqs_xyjkshClick() {
		$("#dqs_xyjksh").toggle(600);
	}
</script>
<div class="wrapper">
	<div class="tab-content">
		<div class="tableList">
			<h3 onclick="dqs_xyjkshClick()" class="tableTitle">信用借款审核(申请额度相关)</h3>
			<div id="dqs_xyjksh">
				<table id="contentTable" class="table table-bordered table-condensed">
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
						<c:forEach items="${fgs_xyjkshList}" var="rule" varStatus="i">
							<tr>
								<td rowspan="3">${rule.reviewedBook}</td>
								<td rowspan="3">${rule.reviewedTarget}</td>
								<td rowspan="3">${rule.reviewedTool}</td>
								<td rowspan="3">${rule.dataRequied}</td>
								<td rowspan="3">${rule.banRule}</td>
								<td rowspan="3">${rule.replaceMeans}</td>
								<td class="">
									<label>分公司审核:</label>
									&nbsp;&nbsp;
									<input type="radio" name="fgs_s3passFlag${i.index}" disabled="disabled" id="fgs_s3passFlag${i.index}" value="0" class="required">
									<label for="radio_yes">触碰</label>
									&nbsp;&nbsp;
									<input type="radio" name="fgs_s3passFlag${i.index}" disabled="disabled" id="fgs_s3passFlag${i.index}1" value="1" class="required">
									<label for="radio_no">不触碰</label>
									<font style="color: red">*</font>
								</td>
								<td>
									<textarea id="fgs_s3remark${i.index}" readonly="readonly" style="height: 70px;" maxlength="1000">${rule.remark}</textarea>
								</td>
							</tr>
							<tr>
								<td rowspan="1">
									<label>区域审核 :</label>
									&nbsp;&nbsp;
									<input type="radio" name="qys_s3passFlag${i.index}" disabled="disabled" value="0" id="qys_s3passFlag${i.index}" class="required">
									<label for="radio_yes">碰触</label>
									&nbsp;&nbsp;
									<input type="radio" name="qys_s3passFlag${i.index}" disabled="disabled" value="1" id="qys_s3passFlag${i.index}1" class="required">
									<label for="radio_no">不碰触</label>
									<font style="color: red">*</font>
								</td>
								<td>
									<textarea id="qys_s3remark${i.index}" readonly="readonly" maxlength="1000" style="height: 70px;"></textarea>
								</td>
							</tr>
							<tr>
								<td rowspan="1">
									<label>大区审核 :</label>
									&nbsp;&nbsp;
									<input type="radio" name="dqs_s3passFlag${i.index}" value="0" id="dqs_s3passFlag${i.index}" class="required">
									<label for="radio_yes">碰触</label>
									&nbsp;&nbsp;
									<input type="radio" name="dqs_s3passFlag${i.index}" value="1" id="dqs_s3passFlag${i.index}1" class="required">
									<label for="radio_no">不碰触</label>
									<font style="color: red">*</font>
								</td>
								<td>
									<textarea id="dqs_s3remark${i.index}" maxlength="1000" style="height: 70px;"></textarea>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div id="dqs_s3saveButton" class="form-actions">
					<shiro:hasPermission name="credit:reviewedRule:edit">
						<input id="btnSubmit" class="btn btn-primary" onclick="dqs_s3submit()" type="button" value="保 存" />&nbsp;</shiro:hasPermission>
				</div>
			</div>
		</div>
	</div>
</div>
