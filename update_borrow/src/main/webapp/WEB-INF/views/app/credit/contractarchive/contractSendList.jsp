<!-- 
 * @reqno: H1601150171
 * @date-designer:2016年01月25日-lirongchao
 * @e-in-other	: custName -客户名称 : 客户名称
 * @e-in-other	: contractNo -合同编号 : 合同编号
 * @e-in-other	: isSender -是否发送: 是否发送
 * @e-in-other	: isRecipitent -是否签收: 是否签收
 * @e-in-other	: startSenderTime -发出日期开始: 发出日期开始
 * @e-in-other	: endSenderTime -发出日期结束: 发出日期结束
 * @e-in-other	: startRecipientTime -签收日期开始: 签收日期开始
 * @e-in-other	: endRecipientTime -签收日期结束: 签收日期结束
 * @e-out-other	: custName -客户名称 : 客户名称
 * @e-out-other	: contractNo -合同编号 : 合同编号
 * @e-out-other	: isSenderName -是否发送 : 是否发送
 * @e-out-other	: senderTime -发出日期 : 发出日期
 * @e-out-other	: senderName -发件人 : 发件人
 * @e-out-other	: isRecipitentName -是否签收 : 是否签收
 * @e-out-other	: recipientName -签收人 : 签收人
 * @e-out-other	: recipientTime -签收日期 : 签收日期
 * @e-ctrl :btnSubmit - 查询 ：查询
 * @e-ctrl :btnReset - 重置 ：重置
 * @date-author:2016年01月25日-lirongchao:合同归档信息发送管理jsp
 -->
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>合同归档发送管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		resetTip();
		$("#searchForm").validate({
			submitHandler : function(form) {
				loading();
				form.submit();
			}
		});
	});
	function fromReset() {
		$("#pageNo").val('');
		$("#pageSize").val('');
		$("#custName").val('');
		$("#contractNo").val('');
		$("#isSender").val('0');
		$("#isRecipitent").val('');
		$("#startSenderTime").val('');
		$("#endSenderTime").val('');
		$("#startRecipientTime").val('');
		$("#endRecipientTime").val('');
		$("#orgNumId").val('');
		$("#orgNumName").val('');
		$("#s2id_isSender>.select2-choice>.select2-chosen").html('否');
		$("#s2id_isRecipitent>.select2-choice>.select2-chosen").html('--全部--');
	}
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
	function send(id) {
		var url = "${ctx}/credit/contractArchive/send?id=" + id;
		openJBox("contractArchiveForm", url, "合同发送信息", 800, 400);
	}
</script>
</head>
<body>
	<div class="searchInfo">
		<h3 class="searchTitle">查询条件</h3>
		<div class="searchCon">
			<form:form id="searchForm" modelAttribute="contractArchive" action="${ctx}/credit/contractArchive/sendList" method="post" class=" form-search ">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
				<div class="filter">
					<table class="fromTable">
						<tr>
							<td class="ft_label">客户名称：</td>
							<td class="ft_content">
								<input id="custName" name="custName" type="text" maxlength="50" class="input-medium noLegalInput" value="${contractArchive.custName}" />
							</td>
							<td class="ft_label">合同编号：</td>
							<td class="ft_content">
								<input id="contractNo" name="contractNo" type="text" maxlength="50" class="input-medium noLegalInput" value="${contractArchive.contractNo}" />
							</td>
						</tr>
						<tr>
							<td class="ft_label">是否发送：</td>
							<td class="ft_content">
								<form:select path="isSender" class="input-medium" value="${contractArchive.isSender}">
									<form:option value="" label="  --全部--" />
									<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
								</form:select>
							</td>
							<td class="ft_label">是否签收：</td>
							<td class="ft_content">
								<form:select path="isRecipitent" class="input-medium" value="${contractArchive.isRecipitent}">
									<form:option value="" label="  --全部--" />
									<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
								</form:select>
							</td>
						</tr>
						<tr>
							<td class="ft_label">发出日期：</td>
							<td class="ft_content" style="width: 240px;">
								<input id="startSenderTime" name="startSenderTime" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate" value="<fmt:formatDate value="${contractArchive.startSenderTime }" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd',isShowClear:true});" />
								<label>&nbsp;--&nbsp;</label>
								<input id="endSenderTime" name="endSenderTime" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate" value="<fmt:formatDate value="${contractArchive.endSenderTime }" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd',isShowClear:true});" />
							</td>
							<td class="ft_label">签收日期：</td>
							<td class="ft_content" style="width: 240px;">
								<input id="startRecipientTime" name="startRecipientTime" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate" value="<fmt:formatDate value="${contractArchive.startRecipientTime }" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd',isShowClear:true});" />
								<label>&nbsp;--&nbsp;</label>
								<input id="endRecipientTime" name="endRecipientTime" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate" value="<fmt:formatDate value="${contractArchive.endRecipientTime }" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd',isShowClear:true});" />
							</td>
						</tr>
					</table>
					<div class="searchButton">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />
						&nbsp;
						<input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="fromReset();" />
					</div>
				</div>
			</form:form>
		</div>
	</div>
	<sys:message content="${message}" />
	<div class="tableList">
		<h3 class="tableTitle">数据列表</h3>
		<div id="tableDataId" style="max-height:400px;overflow:auto;">
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<th width="20px">
						<input type="checkbox" onclick="allCheck()" name="all" id="all" />
					</th>
					<th width="10%">客户名称</th>
					<th width="10%">合同编号</th>
					<th width="10%">是否发出</th>
					<th width="10%">发出日期</th>
					<th width="10%">发件人</th>
					<th width="10%">是否签收</th>
					<th width="10%">签收人</th>
					<th width="10%">签收日期</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${page.list}" var="contractArchiveInfo" varStatus="index">
					<c:if test="${0 == index.count%2}">
						<tr class="doubleRow">
					</c:if>
					<c:if test="${1 == index.count%2}">
						<tr>
					</c:if>
					<td id="id">
						<input type="checkbox" value="${contractArchiveInfo.id}" name="type" id="c_${index.count}">
						<input id="isRecipitent" value="${contractArchiveInfo.isRecipitent}" type="hidden">
					</td>
					<td id="custName" class="title" title="${contractArchiveInfo.custName}">${contractArchiveInfo.custName}</td>
					<td id="contractNo" class="title" title="${contractArchiveInfo.contractNo}">${contractArchiveInfo.contractNo}</td>
					<td id="isSenderName" class="title" title="${contractArchiveInfo.isSenderName}">${contractArchiveInfo.isSenderName}</td>
					<td id="senderTime" class="title" title="<fmt:formatDate value="${contractArchiveInfo.senderTime}" pattern="yyyy-MM-dd"/>">
						<fmt:formatDate value="${contractArchiveInfo.senderTime}" pattern="yyyy-MM-dd" />
					</td>
					<td id="senderName" class="title" title="${contractArchiveInfo.senderName}">${contractArchiveInfo.senderName}</td>
					<td id="isRecipitentName" class="title" title="${contractArchiveInfo.isRecipitentName}">${contractArchiveInfo.isRecipitentName}</td>
					<td id="recipientName" class="title" title="${contractArchiveInfo.recipientName}">${contractArchiveInfo.recipientName}</td>
					<td id="recipientTime" class="title" title="<fmt:formatDate value="${contractArchiveInfo.recipientTime}" pattern="yyyy-MM-dd"/>">
						<fmt:formatDate value="${contractArchiveInfo.recipientTime}" pattern="yyyy-MM-dd" />
					</td>
					<td id="archive">
						<c:if test="${contractArchiveInfo.isSender eq '0'}">
							<a href="#" onclick="send('${contractArchiveInfo.id}')">发送</a>
						</c:if>
					</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div class="pagination">${page}</div>
	</div>
</body>
</html>