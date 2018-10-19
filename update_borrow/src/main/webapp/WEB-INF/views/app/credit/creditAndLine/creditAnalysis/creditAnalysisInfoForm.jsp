<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>征信与流水分析管理</title>
<script type="text/javascript">
	$(document).ready(function() {
		//$("#name").focus();
			adjustTextareaLength("lineDesc","pre0");
			adjustTextareaLength("creditDesc","1pre1");
		$("#creditAnalysisInfoForm").validate({
		submitHandler : function(form) {
			saveCreditAnalysis();
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});
	});
	function addRow(list, idx, tpl, row) {
		$(list).append(Mustache.render(tpl, {
		idx : idx,
		delBtn : true,
		row : row
		}));
		$(list + idx).find("select").each(function() {
			$(this).val($(this).attr("data-value"));
		});
		$(list + idx).find("input[type='checkbox'], input[type='radio']").each(function() {
			var ss = $(this).attr("data-value").split(',');
			for (var i = 0; i < ss.length; i++) {
				if ($(this).val() == ss[i]) {
					$(this).attr("checked", "checked");
				}
			}
		});
	}
	function delRow(obj, prefix) {
		var id = $(prefix + "_id");
		var delFlag = $(prefix + "_delFlag");
		if (id.val() == "") {
			$(obj).parent().parent().remove();
		} else if (delFlag.val() == "0") {
			delFlag.val("1");
			$(obj).html("&divide;").attr("title", "撤销删除");
			$(obj).parent().parent().addClass("error");
		} else if (delFlag.val() == "1") {
			delFlag.val("0");
			$(obj).html("&times;").attr("title", "删除");
			$(obj).parent().parent().removeClass("error");
		}
	}

	//保存分析信息
	function saveCreditAnalysis() {
		$.post("${ctx}/credit/creditAndLine/creditAnalysis/validateMandatoryMess", {
			applyNo : '${applyNo}'
		}, function(data) {
			if (data) {
				if (data.status == 1) {//保存成功
					loading();
					top.$.jBox.tip.mess = null;
					var creditAnalysis = $("#creditAnalysisInfoForm").serializeJson();
					$.post("${ctx}/credit/creditAndLine/creditAnalysis/save", creditAnalysis, function(data) {
						if (data) {
							closeTip();
							if (data.status == 1) {//保存成功
								var id = data.id;
								$("#creditAnalysisInfoForm input[id=id]").val(id);
								alertx(data.message, function() {
									parent.$.loadDiv("creditAnalysisDiv", "${ctx}/credit/creditAndLine/creditAnalysis/form", {
										applyNo : '${applyNo}'
									}, "post");
								});
							} else {
								alertx(data.message);
							}
						}
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
		<sys:message content="${message}" />
		<div class="tableList">
			<form:form id="creditAnalysisInfoForm" action="" modelAttribute="creditAnalysis" method="post" class="form-horizontal">
				<div class="searchInfo">
					<form:hidden path="id" />
					<input type="hidden" name="applyNo" value="${applyNo}" />
					<h3 class="tableTitle">分析信息</h3>
					<table class="fromTable filter">
						<tr>
							<td class="ft_label" style="width: 15%;">
								流水分析：
							</td>
							<td class="ft_content" style="width: 85%;">
								<pre class="input-xxlarge pre-style" style="width: 720px;" id="1pre1"></pre> 
								<pre class="input-xxlarge pre-style" style="width: 720px;" id="pre0"></pre> 
								<pre class="input-xxlarge pre-style" style="width: 720px;" id="1pre2"></pre> 
								<textarea id="lineDesc" name="lineDesc" maxlength="1000"  class="input-xxlarge textarea-style required"  onkeyup="adjustTextareaLength('lineDesc','pre0')" rows="4" cols="3" style="width: 720px;" placeholder="流水分析">${creditAnalysis.lineDesc}</textarea>
								<font color="red">*</font>
								<br />
							</td>
						</tr>
						
						<tr>
							<td class="ft_label" style="width: 15%;">
								担保人征信分析：
							</td>
							<td class="ft_content" style="width: 85%;">
								<textarea id="creditDesc" name="creditDesc"  class="input-xxlarge textarea-style required"  onkeyup="adjustTextareaLength('creditDesc','1pre1')" maxlength="1000" rows="4" cols="3" style="width: 720px;" placeholder="征信分析">${creditAnalysis.creditDesc}</textarea>
								<font color="red">*</font>
								<br />
							</td>
						</tr>
						
						<tr>
							<td class="ft_label" style="width: 15%;">
								法定代表人信用信息：
							</td>
							<td class="ft_content" style="width: 85%;">
								<textarea id="creditCompany" name="creditCompany"  class="input-xxlarge textarea-style required"  onkeyup="adjustTextareaLength('creditCompany','1pre2')" maxlength="1000" rows="4" cols="3" style="width: 720px;" placeholder="本栏内容需脱敏处理，禁止录入企业和自然人全称">${creditAnalysis.creditCompany}</textarea>
								<font color="red">*</font>
								<br />
							</td>
						</tr>
					</table>
					<div class="searchButton" id="buttonDiv">
						<input id="rsBtnSubmit" class="btn btn-primary" type="submit" value="保存" />
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>