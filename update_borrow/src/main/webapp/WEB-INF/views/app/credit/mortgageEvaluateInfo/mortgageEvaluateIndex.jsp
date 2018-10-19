<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>分公司风控审核-抵质押物信息管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		adjustTextareaLength("analysisDesc","preanaly");
		$.loadDiv("mortgageCarEvaluate", "${ctx }/credit/mortgageCarEvaluateInfo/toCarEvaluateIndex", {
			applyNo : '${actTaskParam.applyNo}'
		}, "post");
		$.loadDiv("mortgageHouseEvaluate", "${ctx }/credit/mortgageHouseInfo/toHouseEvaluateIndex", {
			applyNo : '${actTaskParam.applyNo}'
		}, "post");
		$.loadDiv("mortgageOtherEvaluate", "${ctx }/credit/mortgageOtherInfo/toOtherEvaluateIndex", {
			applyNo : '${actTaskParam.applyNo}'
		}, "post");

		//抵质押物分析信息必填验证
		$("#suggForm").validate({
			submitHandler : function(form) {
				saveForm();
			},
			errorContainer : "#messageBox",
			errorPlacement : function(error, element) {
				checkReq(error,element);
			}
		});
	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}

	//修改
	function edit(type, urlSingle, title) {
		var width = 1000;
		var height = 600;
		var $checkLine = $("input[name='" + type + "']:checked");
		var $len = $checkLine.length;
		if ($len != 1) {
			alertx("请选择一条数据");
		} else {
			var url = urlSingle + $checkLine.val() + "&applyNo=" + '${actTaskParam.applyNo}' + "&flag=0";
			openJBox('formBox', url, title, width, height);
		}
	}

	//查询
	function query(id, urlSingle, title) {
		/* width = Math.max(width, 1000); */
		var width = 1000;
		var height = 600;
		var url = urlSingle + id + "&applyNo=" + '${actTaskParam.applyNo}' + "&flag=1";
		openJBox('formBox', url, title, width, $(top.document).height() - 200);
	}
	function saveForm() {
		var sug = $("#analysisDesc").val();
		if (sug == '') {
			alertx("请填写分析信息");
		} else {
			loading();
			top.$.jBox.tip.mess = null;
			var formJson = $("#suggForm").serializeJson();
			$.post("${ctx}/credit/processSuggestionInfo/saveEvaluate", formJson, function(data) {
				if (data) {
					closeTip();
					if (data.status == 1) {//保存成功
						alertx(data.message, function() {
							//closeJBox();

						});
					} else {
						alertx(data.message);
					}
				}
			});
		}
	}
</script>
</head>
<body>
	<div id="mortgageCarEvaluate"></div>
	<div id="mortgageHouseEvaluate"></div>
	<div id="mortgageOtherEvaluate"></div>
	<div>
		<form:form id="suggForm" modelAttribute="processSuggestionInfo" action="#" method="post" class="form-horizontal">
			<input type="hidden" id="applyNo" name="applyNo" class="btn btn-primary" value="${actTaskParam.applyNo}" />
			<input type="hidden" id="taskDefKey" name="taskDefKey" class="btn btn-primary" value="${actTaskParam.taskDefKey}" />
			<h3 class="infoListTitle">分析信息</h3>
			<sys:message content="${suggMessage}" />
			<table class="filter" style="width: 99%">
				<tr>
					<td class="ft_label">抵质押物分析：</td>
					<td class="" colspan="10" style="padding-bottom: 10px;">
						<pre class="textarea-width pre-style required" style="width: 720px"  id="preanaly"></pre>
						<textarea name="analysisDesc" id="analysisDesc" htmlEscape="false" rows="4" maxlength="1000" class="textarea-width textarea-style required" onkeyup="adjustTextareaLength('analysisDesc','preanaly')" style="width: 720px">${processSuggestionInfo.analysisDesc}</textarea>
						<font color="red">*</font>
					</td>
				</tr>
			</table>
			<div class="searchButton">
				<shiro:hasPermission name="credit:mortgageInfo:edit">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />&nbsp;</shiro:hasPermission>
			</div>
		</form:form>
	</div>
</body>
</html>