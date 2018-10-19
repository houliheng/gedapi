<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>节假日</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		var tds = $(".title").filter("td");
		$.each(tds, function() {
			$(this).attr("title", $(this).html());
		});
	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		if ($("#pageNo")[0].value.length > 10) {
			top.$.jBox.tip('当前页码大小长度不能大于10位！');
			return true;
		}
		if ($("#pageSize")[0].value.length > 10) {
			top.$.jBox.tip('每页条数大小的长度不能大于10位！');
			return true
		}
		$("#searchForm").submit();
		return false;
	}
	//重置
	function formReset() {
		$("#hldName").val('');
		$("#hldStartDate").val('');
		$("#hldEndDate").val('');
		page();//查询
	}

	//时间范围判断，考虑到只输入一端时间的情况，用到以下判断		
	function timeJudge() {
		if ($("#hldEndDate").val()) {
			if ($("#hldStartDate").val() > $("#hldEndDate").val()) {
				alert("开始时间应小于截止时间！");
			} else {
				$("#searchForm").submit();
			}
		} else {
			$("#searchForm").submit();
		}
	}

	/**
	 * 复选框全选事件
	 */
	function allCheck() {
		if ($('[name=all]:checkbox').attr("checked") == "checked") {
			$('[name=type]:checkbox').attr("checked", true);
		} else {
			$('[name=type]:checkbox').attr("checked", false);
		}
	}
	/**
	 * 获取复选框选中的值
	 * @returns
	 */
	function getCheckedValue() {
		var str = "";
		$("input[name=type]:checkbox").each(function() {
			if ($(this).attr("checked")) {
				str += $(this).val() + ",";
			}
		});
		return str;
	}
	//删除
	function deleteData() {
		var str = getCheckedValue();
		if (str != "") {
			var url = "${ctx}/sys/holiday/delete?id=" + str;
			return confirmx('是否删除?', url);
		} else {
			alert("请选择节假日信息!");
		}
	}
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active">
			<a href="${ctx}/sys/holiday/">节假日</a>
		</li>
		<li>
			<a href="${ctx}/sys/holiday/add">节假日添加</a>
		</li>
	</ul>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="holiday" action="${ctx}/sys/holiday/" method="post">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<div class="filter">
						<table class="searchTable">
							<tr>
								<td class="ft_label">节假日名称：</td>
								<td class="ft_content">
									<input id="hldName" name="hldName" type="text" maxlength="50" class="input-medium" value="${holiday.hldName}" />
								</td>
								<td class="ft_label">时间范围：</td>
								<td class="tm_ft_content">
									<input id="hldStartDate" name="hldStartDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate" value="<fmt:formatDate value="${holiday.hldStartDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});" />
									&nbsp;--&nbsp;
									<input id="hldEndDate" name="hldEndDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate" value="<fmt:formatDate value="${holiday.hldEndDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});" />
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="timeJudge()" />
							&nbsp;
							<input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="return formReset();" />
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<sys:message content="${message}" />
		<div class="ribbon">
			<ul class="layout">
				<li class="delete">
					<a href="#" onclick="deleteData();">
						<span>
							<b></b>
							删除
						</span>
					</a>
				</li>
			</ul>
		</div>
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div id="tableDataId" style="max-height:400px;overflow:auto;">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<th width="20px">
							<input type="checkbox" onclick="allCheck();" name="all" id="all">
						</th>
						<th width="20%">名称</th>
						<th width="20%">日期</th>
						<th>操作</th>
					</tr>
					<c:forEach items="${page.list}" var="holiday" varStatus="index">
						<c:if test="${0 == index.count%2}">
							<tr class="doubleRow">
						</c:if>
						<c:if test="${1 == index.count%2}">
							<tr>
						</c:if>
						<td width="20px;">
							<input type="checkbox" value="${holiday.id}" name="type">
						</td>
						<td class="title">${holiday.hldName}</td>
						<td class="title">
							<fmt:formatDate value="${holiday.hldDate}" type="both" pattern="yyyy-MM-dd" />
						</td>
						<td>
							<a href="${ctx}/sys/holiday/add?id=${holiday.id}">修改</a>
							<a href="${ctx}/sys/holiday/delete?id=${holiday.id}" onclick="return confirmx('确认要删除该节假日吗？', this.href)">删除</a>
						</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="pagination">${page}</div>
		</div>
	</div>
</body>
</html>