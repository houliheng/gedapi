<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>结论信息</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		
		$("#overdueHandleConclusionForm").validate({
		submitHandler : saveForm,
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});
	});

	function saveForm() {
		loading();
		top.$.jBox.tip.mess = null;
		var formJson = $("#overdueHandleConclusionForm").serializeJson();
		$.post("${ctx}/postloan/overdueHandleConclusion/save", formJson, function(data) {
			if (data) {
				closeTip();
				if (data.status == 1) {
					alertx(data.message,function(){
						parent.page();
						closeJBox();
					});
				} else {
					alertx("提交失败");
				}
			}
		});

	}
	
	//影像上传
	function uploadImage() {
		var contractNo = $("#contractNo").val();
		var url = "${ctx}/postloan/overdueHandle/videoUpload?applyNo=${actTaskParam.applyNo}&taskDefKey=${actTaskParam.taskDefKey}&contractNo="+contractNo;
		window.open(url);
	}

	//查看影像
	function viewImage() {
		var contractNo = $("#contractNo").val();
		var windowWidth = document.body.offsetWidth - 50;
		var url = "${ctx}/postloan/overdueHandle/videoView?applyNo=${actTaskParam.applyNo}&taskDefKey=${actTaskParam.taskDefKey}&optype=1&&contractNo="+contractNo+"&status=" + "1";
		window.open(url, '${actTaskParam.applyNo}');
	}
</script>
</head>
<body>	
	<div class="wrapper">
		<div class="ribbon">
			<ul class="layout">
				<%-- <c:if test="${0==actTaskParam.status}"> --%>
				<li class="mcp_upload"><a href="#" onclick="uploadImage();"><span><b></b>影像上传</span></a></li>
				<%-- </c:if> --%>
				<%-- <li class="mcp_pic"><a href="#" onclick="viewImage(${actTaskParam.status});"><span><b></b>影像查阅</span></a></li> --%>
				<li class="mcp_pic"><a href="#" onclick="viewImage();"><span><b></b>影像查阅</span></a></li>
			</ul>
		</div>
		<div id="rs_msg"></div>
		<div class="filter">
			<table class="fromTable">
				<ul class="ul-form">
					<tr>
						<td class="ft_label" style="width:10%">
							<span>模板下载：</span>
						</td>
						<td class="ft_content" style="width:25%">
							<a href="${ctx }/postloan/overdueHandle/getTemplate">协议模板.docx</a>
						</td>
						<td>
							<span style="color: red;width: 20%">${downloadMassage} </span>
						</td>
					</tr>
				</ul>
			</table>
		</div>
	</div>
	<div class="searchInfo">
		<h3 class="searchTitle">录入结论</h3>
		<div class="searchCon">
			<form:form id="overdueHandleConclusionForm" modelAttribute="overdueHandleConclusion" action="${ctx}/postloan/overdueHandleConclusion/save" method="post" class="form-horizontal">
				<form:hidden path="id" />
				<form:hidden path="contractNo" />
				<form:hidden path="periodNum" />
				<input type="hidden" id="applyNo" name="applyNo" value="${actTaskParam.applyNo}"/>
				<input type="hidden" id="taskId" name="taskId" value="${actTaskParam.taskId}"/>
				<input type="hidden" id="taskDefKey" name="taskDefKey" value="${actTaskParam.taskDefKey}"/>
				<input type="hidden" id="procDefId" name="procDefId" value="${actTaskParam.procDefId}"/>
				<input type="hidden" id="status" name="status" value="${actTaskParam.status}"/>
				<input type="hidden" id="procInstId" name="procInstId" value="${actTaskParam.procInstId}"/>
				<pre class="textarea-width pre-style required" id="pre1"></pre>
				<pre class="textarea-width pre-style required" id="pre2"></pre>
				<pre class="textarea-width pre-style required" id="pre3"></pre>
				<sys:message content="${message}" />
				<table class="fromTable filter">
					<tr>
						<td class="ft_label" style="width: 10%;">处理结论：</td>
						<td class="ft_content" style="width: 70%;">
							<input type="radio" name="passFlag" value="yes" id="radio_yes" class="required" >
							<label for="radio_yes">通过</label>&nbsp;&nbsp;
							<c:if test="${actTaskParam.taskDefKey eq 'utask_dqcl'}">
								<input type="radio" name="passFlag" value="back" id="radio_back" class="required" >
								<label for="radio_back">打回</label>&nbsp;&nbsp;
								<input type="radio" name="passFlag" value="yesToFinish" id="radio_yesToFinish" class="required">
								<label for="radio_yesToFinish">结清结束流程</label>
							</c:if>
							<font color="red">*</font>
						</td>
					</tr>
					<c:if test="${actTaskParam.taskDefKey eq 'utask_dqcl' || actTaskParam.taskDefKey eq 'utask_zbcl'}">
						<tr>
							<td class="ft_label" style="width: 10%;">区域意见：</td>
							<td class="ft_content" style="width: 70%;">
								<textarea id="conclusionDescQY" htmlEscape="false" rows="4" maxlength="1000" class="textarea-width textarea-style" readonly onkeyup="adjustTextareaLength('conclusionDesc','pre1')">${conclusionDescQY}</textarea>
							</td>
						</tr>
					</c:if>
					<c:if test="${actTaskParam.taskDefKey eq 'utask_zbcl'}">
						<tr>
							<td class="ft_label" style="width: 10%;">大区意见：</td>
							<td class="ft_content" style="width: 70%;">
								<pre class="textarea-width pre-style required" id="pre1"></pre>
								<textarea id="conclusionDescDQ" htmlEscape="false" rows="4" maxlength="1000" class="textarea-width textarea-style" readonly onkeyup="adjustTextareaLength('conclusionDesc','pre2')">${conclusionDescDQ}</textarea>
							</td>
						</tr>
					</c:if>
					<tr>
						<td class="ft_label" style="width: 10%;">录入意见：</td>
						<td class="ft_content" style="width: 70%;">
							<form:textarea path="conclusionDesc" htmlEscape="false" rows="4" maxlength="1000" class="textarea-width textarea-style required" onkeyup="adjustTextareaLength('conclusionDesc','pre3')" />
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<td style="text-align: right;" colspan="2">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="提 交" />&nbsp;
							<!-- <a id="checkApproveSkipId" target="_parent"></a> -->
						</td>
					</tr>
				</table>
			</form:form>
		</div>
	</div>
</body>
</html>
