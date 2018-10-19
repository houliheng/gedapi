<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">
	$(function() {
		$("#dq_s4_1saveButton").hide();
		$("textarea").css("width", $(window).width() * 0.22 - 30);
		if ("${checkReviewedId}" == "true" && "${readOnly}" == "false") {
			//document.getElementById("dq_s4_1saveButton").style.visibility="visible";
			$("#dq_s4_1saveButton").show();
			/* <c:forEach items="${fg_1hhjkshList}" var ="rule" varStatus="i">
				$("#fg_s4_1passFlag${i.index}").removeAttr("disabled");
				$("#fg_s4_1passFlag${i.index}1").removeAttr("disabled");
				$("#fg_s4_1remark${i.index}").removeAttr("readonly");
			</c:forEach> */
		}

		var sign = 0;
		<c:forEach items="${fg_1hhjkshList}" var ="ruleFom" varStatus="i">
		var fg_s4_1passFlag = "'fg_s4_1passFlag" + sign + "'";
		var value = "${ruleFom.passFlag}";
		$("input[name=" + fg_s4_1passFlag + "][value=" + value + "]").attr("checked", true);
		sign = sign + 1;
		</c:forEach>

		<c:forEach items="${qy_1hhjkshList}" var ="ruleFom" varStatus="i">
		var value = "${ruleFom.passFlag}";
		$("input[name= qy_s4_1passFlag${i.index}][value=" + value + "]").attr("checked", true);
		$("#qy_s4_1remark${i.index}").val('${ruleFom.remark}');
		</c:forEach>

		<c:forEach items="${dq_1hhjkshList}" var ="ruleFom" varStatus="i">
		var value = "${ruleFom.passFlag}";
		$("input[name= dq_s4_1passFlag${i.index}][value=" + value + "]").attr("checked", true);
		$("#dq_s4_1remark${i.index}").val('${ruleFom.remark}');
		</c:forEach>
	});

	function dq_s4_1submit() {
		var passIdent = false, remarkIdent = false;
		<c:forEach items="${dq_1hhjkshList}" var ="ruleFom" varStatus="i">
		if (checkIsNull($("[name=dq_s4_1passFlag${i.index}]:checked").val())) {
			passIdent = true;
		}
		if ($("[name=dq_s4_1passFlag${i.index}]:checked").val() == "0") {
			if (checkIsNull($("#dq_s4_1remark${i.index}").val())) {
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
			<c:forEach items="${dq_1hhjkshList}" var ="ruleFom" varStatus="i">
			formJson += "{'reviewedBook':'${ruleFom.id}','passFlag':" + $("input[name='dq_s4_1passFlag${i.index}']:checked").val() + ",'remark':'" + $("#dq_s4_1remark${i.index}").val() + "'},";
			</c:forEach>
			formJson = formJson.substring(0, formJson.length - 1) + "]";
			$.ajax({
			type : "post",
			url : "${ctx}/credit/reviewedRule/saveRule?taskDefKey=" + '${taskDefKey}' + '&applyNo=' + '${applyNo}' + '&sheetId=' + '4-1',
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

	function dq_1hhjkshClick() {
		$("#dq_1hhjksh").toggle(600);
	}
</script>
<div class="wrapper">
	<div class="tab-content">
		<div class="tableList">
			<h3 onclick="dq_1hhjkshClick()" class="tableTitle">混合贷借款额在抵质押物市值的70%到100%之间的</h3>
			<div id="dq_1hhjksh">
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
						<c:forEach items="${fg_1hhjkshList}" var="rule" varStatus="i">
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
									<input type="radio" name="fg_s4_1passFlag${i.index}" disabled="disabled" id="fg_s4_1passFlag${i.index}" value="0" class="required">
									<label for="radio_yes">触碰</label>
									&nbsp;&nbsp;
									<input type="radio" name="fg_s4_1passFlag${i.index}" disabled="disabled" id="fg_s4_1passFlag${i.index}1" value="1" class="required">
									<label for="radio_no">不触碰</label>
									<font style="color: red">*</font>
								</td>
								<td>
									<textarea id="fg_s4_1remark${i.index}" readonly="readonly" style="height: 70px;" maxlength="1000">${rule.remark}</textarea>
								</td>
							</tr>
							<tr>
								<td rowspan="1">
									<label>区域审核 :</label>
									&nbsp;&nbsp;
									<input type="radio" name="qy_s4_1passFlag${i.index}" disabled="disabled" value="0" id="qy_s4_1passFlag${i.index}" class="required">
									<label for="radio_yes">碰触</label>
									&nbsp;&nbsp;
									<input type="radio" name="qy_s4_1passFlag${i.index}" disabled="disabled" value="1" id="qy_s4_1passFlag${i.index}1" class="required">
									<label for="radio_no">不碰触</label>
									<font style="color: red">*</font>
								</td>
								<td>
									<textarea id="qy_s4_1remark${i.index}" readonly="readonly" maxlength="1000" style="height: 70px;"></textarea>
								</td>
							</tr>
							<tr>
								<td rowspan="1">
									<label>大区审核 :</label>
									&nbsp;&nbsp;
									<input type="radio" name="dq_s4_1passFlag${i.index}" value="0" id="dq_s4_1passFlag${i.index}" class="required">
									<label for="radio_yes">碰触</label>
									&nbsp;&nbsp;
									<input type="radio" name="dq_s4_1passFlag${i.index}" value="1" id="dq_s4_1passFlag${i.index}1" class="required">
									<label for="radio_no">不碰触</label>
									<font style="color: red">*</font>
								</td>
								<td>
									<textarea id="dq_s4_1remark${i.index}" maxlength="1000" style="height: 70px;"></textarea>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div id="dq_s4_1saveButton" class="form-actions">
					<shiro:hasPermission name="credit:reviewedRule:edit">
						<input id="btnSubmit" class="btn btn-primary" onclick="dq_s4_1submit()" type="button" value="保 存" />&nbsp;</shiro:hasPermission>
				</div>
			</div>
		</div>
	</div>
</div>
