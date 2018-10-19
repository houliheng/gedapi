<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">
	$(function() {
		$("#saveButton").hide();
		$("textarea").css("width", $(window).width() * 0.22 - 30);
		if ("${checkReviewedId}" == "true" && "${readOnly}" == "false") {
			//document.getElementById("saveButton").style.visibility="visible";
			$("#saveButton").show();
			<c:forEach items="${tsbstkshList}" var ="rule" varStatus="i">
			$("#s2passFlag${i.index}").removeAttr("disabled");
			$("#s2passFlag${i.index}1").removeAttr("disabled");
			$("#s2remark${i.index}").removeAttr("readonly");
			</c:forEach>
		}

		var sign = 0;
		<c:forEach items="${tsbstkshList}" var ="ruleFom" varStatus="i">
		var s2passFlag = "'s2passFlag" + sign + "'";
		var value = "${ruleFom.passFlag}";
		$("input[name=" + s2passFlag + "][value=" + value + "]").attr("checked", true);
		sign = sign + 1;
		</c:forEach>
	});

	function submits2() {
		var passIdent = false, remarkIdent = false;
		for (var i = 0; i < 4; i++) {
			if (checkIsNull($("[name=s2passFlag" + i + "]:checked").val())) {
				passIdent = true;
			}
			if ($("[name=s2passFlag" + i + "]:checked").val() == "0") {
				if (checkIsNull($("#s2remark" + i).val())) {
					remarkIdent = true;
				}
			}
		}
		if (remarkIdent) {
			alertx("请填写备注信息！");
		} else if (passIdent) {
			alertx("请填写审批结果！");
		} else {
			loading();
			top.$.jBox.tip.mess = null;
			var formJson = "[";
			<c:forEach items="${tsbstkshList}" var ="ruleFom" varStatus="i">
			formJson += "{'reviewedBook':'${ruleFom.id}','passFlag':" + $("input[name='s2passFlag${i.index}']:checked").val() + ",'remark':'" + $("#s2remark${i.index}").val() + "'},";
			</c:forEach>
			formJson = formJson.substring(0, formJson.length - 1) + "]";

			$.ajax({
			type : "post",
			url : "${ctx}/credit/reviewedRule/saveRule?taskDefKey=" + '${taskDefKey}' + '&applyNo=' + '${applyNo}' + '&sheetId=' + '2',
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

	function tsbstkshClick() {
		$("#tsbstksh").toggle(600);
	}
</script>
<div class="wrapper">
	<div class="tab-content">
		<div class="tableList">
			<h3 onclick="tsbstkshClick()" class="tableTitle">特殊必诉条款审核</h3>
			<div id="tsbstksh">
				<table id="contentTable" class="table table-bordered table-condensed">
					<thead>
						<tr>
							<th>审核科目</th>
							<th>审核工具</th>
							<th>材料要求</th>
							<th style="width: 25%">审批结果</th>
							<th style="width: 22%">备注</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${tsbstkshList}" var="rule" varStatus="i">
							<tr>
								<td>${rule.reviewedBook}</td>
								<td>${rule.reviewedTool}</td>
								<td>${rule.dataRequied}</td>
								<td class="">
									<label>区域审核:</label>
									&nbsp;&nbsp;
									<input type="radio" name="s2passFlag${i.index}" id="s2passFlag${i.index}" value="0" class="required">
									<label for="radio_yes">触碰</label>
									&nbsp;&nbsp;
									<input type="radio" name="s2passFlag${i.index}" id="s2passFlag${i.index}1" value="1" class="required">
									<label for="radio_no">不触碰</label>
									<font style="color: red">*</font>
								</td>
								<td>
									<textarea id="s2remark${i.index}" style="height: 70px;" maxlength="1000">${rule.remark}</textarea>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div id="saveButton" class="form-actions">
					<shiro:hasPermission name="credit:reviewedRule:edit">
						<input id="btnSubmit" class="btn btn-primary" onclick="submits2()" type="button" value="保 存" />&nbsp;</shiro:hasPermission>
				</div>
			</div>
		</div>
	</div>
</div>
