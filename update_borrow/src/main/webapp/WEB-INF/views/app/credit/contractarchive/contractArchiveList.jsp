<!--
 * @reqno: H1601150180
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
 * @e-out-other	: orgNum.name -归属机构 : 归属机构
 * @e-out-other	: isSenderName -是否发送 : 是否发送
 * @e-out-other	: senderTime -发出日期 : 发出日期
 * @e-out-other	: senderName -发件人 : 发件人
 * @e-out-other	: isRecipitentName -是否签收 : 是否签收
 * @e-out-other	: recipientName -签收人 : 签收人
 * @e-out-other	: recipientTime -签收日期 : 签收日期
 * @e-ctrl :btnSubmit - 查询 ：查询
 * @e-ctrl :btnReset - 重置 ：重置
 * @e-ctrl :recipitent - 签收 ：签收
 * @e-ctrl :archive - 归档 ：归档
 * @date-author:2016年01月25日-lirongchao:合同归档信息管理jsp
 -->
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>合同归档信息管理</title>
<meta name="decorator" content="default" />
<script src="${ctxStatic}/jqGrid/4.6/i18n/grid.locale-cn.js" type="text/javascript"></script>
<script src="${ctxStatic}/jqGrid/4.6/js/jquery.jqGrid.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/jqGrid/4.6/css/ui.jqgrid.css">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/jqGrid/4.6/css/default/jquery-ui-1.8.2.custom.css">
<script type="text/javascript">
	$(document).ready(function() {

		closeTip();
		$("#contractGrid").jqGrid({
			url : "${ctx}/credit/contractArchive/getData",
			datatype : "json",
			postData : {
				flag : null
			},
			mtype : 'POST',
			colNames : ["快递单号", "快递公司", "发件人"],
			colModel : [ {
				name : "id",
				index : "id",
				sortable : false,
				align : 'center'
				}, {
				name : "expressCompany",
				index : "expressCompany",
				sortable : false,
				align : 'center'
				}, {
				name : "senderName",
				index : "senderName",
				sortable : false,
				align : 'center'
			} ],
			viewrecords : true,
			subGrid : true,
			subGridUrl : "${ctx}/credit/contractArchive/getSubData",
			subGridModel : [ {
				name : [ "客户名称", "合同编号", "归属机构", "是否发出", "发出日期", "发件人", "是否签收", "签收人", "签收日期", "操作"],
				width : [ '100', '100', '100', '20', '100', '100' ,'20', '100', '100', '80'],
				mapping : [ 'custName', 'contractNo', 'orgNum', 'isSender', 'senderTime', 'senderName', 'isRecipitent', 'recipientName', 'recipientTime', 'operation' ]
			} ],
			pager : "gqgridPagerId",
			pagination : true,
			page : 1,
			rowNum : 20,
			rowList : [ 10, 20, 30 ],
			jsonReader : {
				root : "dataList",
				page : "currPage",
				total : "totalPages",
				records : "totalRecount",
				repeatitems : false,
				subgrid : {
					root : "subDataList",
					repeatitems : false
				}
			},
			height : "auto",
			autowidth : true,
			cellsubmit : 'clientArray'
		});

		$("#btnSubmit").on('click', function() {
			var formJson = $("#searchForm").serializeJson();
			//console.info(JSON.stringify(formJson));
			$("#contractGrid").jqGrid('setGridParam', {
			datatype : 'json',
			postData : formJson,
			page : 1
			}).trigger("reloadGrid");
		});


	});
	function fromReset() {
		$("#custName").val('');
		$("#contractNo").val('');
		$("#senderName").val('');
	}

	function recipitent(ids) {

		var url = "${ctx}/credit/contractArchive/recipitent?ids=" + ids;
		$.post(url, null, function(data) {
			if (data) {
				if (data == 'success') {
					alertx("签收成功！", function() {
						$("#contractGrid").jqGrid().trigger("reloadGrid");
					});
				} else {
					alertx("签收失败，请联系管理员！");
				}
			} else {
				alertx("签收失败，请联系管理员！");
			}
		});

	}
	function archive(id, isRecipitent) {
		if (isRecipitent == "0") {
			alertx('请选择以已签收状态的记录！');
			return;
		} else {
			var url = "${ctx}/credit/contractArchive/archive?id=" + id;
			openJBox("contractSendForm", url, "合同发送信息", 1000, 400);
		}
	}
</script>
</head>
<body>
	<div class="searchInfo">
		<h3 class="searchTitle">查询条件</h3>
		<div class="searchCon">
			<form:form id="searchForm" modelAttribute="contractArchive" action="${ctx}/credit/contractArchive/" method="post" class=" form-search ">
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
							<td class="ft_label">发件人：</td>
							<td class="ft_content">
								<input id="senderName" name="senderName" type="text" maxlength="50" class="input-medium noLegalInput" value="${contractArchive.senderName}" />
							</td>
						</tr>
					</table>
					<div class="searchButton">
						<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" />
						&nbsp;
						<input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="fromReset();" />
					</div>
				</div>
			</form:form>
		</div>
	</div>
	<sys:message content="${message}" />

	<div class="tableList;overflow-x:hidden;overflow-y:auto"" >
		<h3 class="tableTitle">数据列表</h3>
		<div id="tableDataId" style="max-height:400px;overflow:auto;">
			<table id="contractGrid"></table>
		</div>
		<div id="gqgridPagerId"></div>
	</div>
</body>
</html>