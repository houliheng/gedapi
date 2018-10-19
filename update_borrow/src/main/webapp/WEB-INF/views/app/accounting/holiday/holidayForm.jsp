<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!--  
@reqno:H1512140102
@date-designer:20151215-songmin
@date-author:20151215-songmin:系统页面布局分辨率优化_在低分辨率下，部分表格、表单显示错位，统一调整优化
-->
<html>
<head>
<title>节假日</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#inputForm").validate({
		rules : {},
		submitHandler : function(form) {
			var ss = "${holiday.id}";
			if ("" !== ss) {
				form.submit();
			} else {
				$.ajax({
				url : "${ctx}/sys/holiday/validate?hldDate=" + $("#hldDate").val(),
				type : "POST",
				datetype : "josn",
				success : function(data) {
					if ("cz" == data.jg) {
						alert("节假日已存在");
					} else {
						form.submit();
					}
				}
				});
			}
		}
		});
	});
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li>
			<a href="${ctx}/sys/holiday/">节假日</a>
		</li>
		<li class="active">
			<a href="${ctx}/sys/holiday/add?id=${holiday.id}">节假日${not empty holiday.id?'修改':'添加'}</a>
		</li>
	</ul>
	<sys:message content="${message}" />
	<div class="searchInfo">
		<h3 class="searchTitle">查询条件</h3>
		<div class="searchCon">
			<form:form id="inputForm" modelAttribute="holiday" action="${ctx}/sys/holiday/save?id=${holiday.id}" method="post">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
				<div class="filter">
					<table class="fromTable">
						<tr>
							<td class="ft_label">节假日名称：</td>
							<td class="ft_content">
								<form:input path="hldName" name="hldName" type="text" maxlength="50" class="input-medium" cssClass="required" style="width:160px" value="${holiday.hldName}" />
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td class="ft_label">节假日日期：</td>
							<td class="ft_content">
								<c:choose>
									<c:when test="${not empty holiday.id }">
										<input id="hldDate" name="hldDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required" disabled value="<fmt:formatDate value="${holiday.hldDate}" pattern="yyyy-MM-dd"/>"
											onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});" />
									</c:when>
									<c:otherwise>
										<input id="hldDate" name="hldDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate hldDate required" value="<fmt:formatDate value="${holiday.hldDate}" pattern="yyyy-MM-dd"/>"
											onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});" />
									</c:otherwise>
								</c:choose>
								<font color="red">*</font>
							</td>
						</tr>
					</table>
					<div class="searchButton">
						<input id="btnSave" class="btn btn-primary" type="submit" value="保存" />
						&nbsp;
						<input id="btnClose" class="btn btn-primary" type="button" value="取消" onclick="location='${ctx}/sys/holiday/'" />
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>
