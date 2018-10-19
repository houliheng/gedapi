<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">
	$(function() {
		$("#fq_s4_1saveButton").hide();
		$("textarea").css("width", $(window).width() * 0.22 - 30);
		if ("${checkReviewedId}" == "true" && "${readOnly}" == "false") {
			//document.getElementById("fq_s4_1saveButton").style.visibility="visible";
			$("#fq_s4_1saveButton").show();
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
	});

	function fq_s4_1submit() {
		var passIdent = false, remarkIdent = false;
		<c:forEach items="${fg_1hhjkshList}" var ="ruleFom" varStatus="i">
		if (checkIsNull($("[name=fg_s4_1passFlag${i.index}]:checked").val())) {
			passIdent = true;
		}
		if ($("[name=fg_s4_1passFlag${i.index}]:checked").val() == "0") {
			if (checkIsNull($("#fg_s4_1remark${i.index}").val())) {
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
			<c:forEach items="${fg_1hhjkshList}" var ="ruleFom" varStatus="i">
			formJson += "{'reviewedBook':'${ruleFom.id}','passFlag':" + $("input[name='fg_s4_1passFlag${i.index}']:checked").val() + ",'remark':'" + $("#fg_s4_1remark${i.index}").val() + "'},";
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

	function fg_1hhjkshClick() {
		$("#fg_1hhjksh").toggle(600);
	}
</script>
<div class="wrapper">
	<div class="tab-content">
		<div class="tableList">
			<h3 onclick="fg_1hhjkshClick()" class="tableTitle">混合贷借款额在抵质押物市值的70%到100%之间的</h3>
			<div id="fg_1hhjksh">
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
								<td>${rule.reviewedBook}</td>
								<td>${rule.reviewedTarget}</td>
								<td>${rule.reviewedTool}</td>
								<td>${rule.dataRequied}</td>
								<td>${rule.banRule}</td>
								<td>${rule.replaceMeans}</td>
								<td class="">
									<label>分公司审核:</label>
									&nbsp;&nbsp;
									<input type="radio" name="fg_s4_1passFlag${i.index}" id="fg_s4_1passFlag${i.index}" value="0" class="required">
									<label for="radio_yes">触碰</label>
									&nbsp;&nbsp;
									<input type="radio" name="fg_s4_1passFlag${i.index}" id="fg_s4_1passFlag${i.index}1" value="1" class="required">
									<label for="radio_no">不触碰</label>
									<font style="color: red">*</font>
								</td>
								<td>
									<textarea id="fg_s4_1remark${i.index}" style="height: 70px;" maxlength="1000">${rule.remark}</textarea>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div id="fq_s4_1saveButton" class="form-actions">
					<shiro:hasPermission name="credit:reviewedRule:edit">
						<input id="btnSubmit" class="btn btn-primary" onclick="fq_s4_1submit()" type="button" value="保 存" />&nbsp;</shiro:hasPermission>
				</div>
			</div>
		</div>
	</div>
</div>
