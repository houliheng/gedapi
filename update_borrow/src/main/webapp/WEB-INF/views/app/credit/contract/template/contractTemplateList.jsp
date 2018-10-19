<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>合同模板管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
	//打开新增合同模板配置页面
	function addnew() {
		$('#new').window('open');
	}

	//删除
	function del() {
		var hobb = document.getElementsByName("id");
		var bool = false;
		for (var i = 0; i < hobb.length; i++) {
			if (hobb[i].checked == true) {
				bool = true;
				location.href = "${ctx}/credit/contractTemplate";
				location.href = "${ctx}/credit/contractTemplate/delete?id=" + hobb[i].value;
				brack;
			}
		}
		if (!bool) {
			alert("请选择一条记录！");
		}
	}
	//上传模板
	function uploadTemplate(ctlid,templateTypeSearch,productTypeSearch,orgplatformSearch,templateNameSearch) {
		templateNameSearch = encodeX(templateNameSearch);
		url = "${ctx}/credit/contractTemplate/FormImport?id=" + ctlid + "&templateTypeSearch=" + templateTypeSearch + "&productTypeSearch=" + productTypeSearch +"&orgplatformSearch=" + orgplatformSearch + "&templateNameSearch=" + templateNameSearch;
		openJBox("uploadBox", url, "模板上传", 600, 350);
	}
	function resetForm() {
		$("#templateType").select2("val", "");
		$("#productType").select2("val", "");
		$("#orgplatform").select2("val", "");
		$('#templateType').val('');
		$('#productType').val('');
		$('#templateName').val('');
		$('#orgplatform').val('');
	}
	function print() {
		var productType = "${contractTemplate.productType}";
		url = "${ctx}/credit/contractTemplate/print?productType=" + productType;
		openJBox("printBox", url, 500, 300);
	}

	$(document).ready(function() {
		var tds = $(".title").filter("td");
		$.each(tds, function() {
			$(this).attr("title", $(this).html());
		});
	});
	function delet() {
		top.$.jBox.tip.mess = null;
		var idche = document.getElementsByName("type");
		var bool = false;
		var strid = "";
		for (var i = 0; i < idche.length; i++) {

			if (idche[i].checked) {
				strid += idche[i].value + "-";
				bool = true;
			}
		}
		if (bool) {
			confirmx("确认要删除所选择的模板吗？", "${ctx}/credit/contractTemplate/delete?strid=" + strid);
		} else {
			alertx("请选择一条记录");

		}
	}
	function print() {
		url = "${ctx}/credit/contractTemplate/print?productType=1&templatemap=2";
		openJBox("printBox", url, "模板打印", 500, 300);
	}
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active">
			<a href="${ctx}/credit/contractTemplate">合同模板管理列表</a>
		</li>
		<li>
			<a href="${ctx}/credit/contractTemplate/Form">合同模板添加</a>
		</li>
	</ul>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="contractTemplate" action="${ctx}/credit/contractTemplate" method="post">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<div class="filter">
						<table class="searchTable">
							<tr>
								<td class="ft_label">模板类型：</td>
								<td class="ft_content">
									<form:select id="templateType" name="templateType" path="templateType" class="input-medium">
										<form:option value="" label="--请选择--" />
										<form:options items="${fns:getDictList('CONTRACT_TEMPLATE_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
								</td>
<%-- 								<td class="ft_label">产品类型：</td>
								<td class="ft_content">
									<form:select id="productType" name="productType" path="productType" class="input-medium">
										<form:option value="" label="--请选择--" />
										<form:options items="${fns:getDictList('PRODUCT_TYPE_CONTRACT')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
								</td> --%>
								<td class="ft_label">归属地：</td>
								<td class="ft_content">
									<form:select path="orgplatform" class="input-medium">
										<form:option value="" label="--请选择--" />
										<form:options items="${fns:getDictList('ORG_PLATFORM')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
								</td>
								<td class="ft_label">模板名称：</td>
								<td class="ft_content">
									<input name="templateName" id="templateName" type="text" maxlength="50" class="input-medium" value="${contractTemplate.templateName }" />
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />
							&nbsp;
							<input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="resetForm();" />
							&nbsp;
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<sys:message content="${message}" />
		<div class="ribbon">
			<ul class="layout">
				<li class="delete">
					<a href="#" onclick="delet()" id="delet">
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
			<div id="tableDataId" style="max-height: 400px; overflow: auto;">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<th width="20px">
							<input id="all" name="all" type="checkbox" onclick="allCheck();" />
						</th>
						<th width="18%">模板类型</th>
						<!-- <th width="18%">产品类型</th> -->
						<th width="18%">模板名称</th>
						<th width="18%">是否已上传模板</th>
						<th>操作</th>
					</tr>
					<c:forEach items="${page.list}" var="ctl" varStatus="index">
						<c:if test="${0 == index.count%2}">
							<tr class="doubleRow">
						</c:if>
						<c:if test="${1 == index.count%2}">
							<tr>
						</c:if>
						<td id="id">
							<input name="type" type="checkbox" value="${ctl.id}" />
						</td>
						<td class="title" id="templateType">${ctl.templateType}</td>
						<%-- <td class="title" id="productType">${ctl.productType}</td> --%>
						<td class="title">${ctl.templateName}</td>
						<td class="title" id="isuploadfile">${ctl.isuploadfile}</td>
						<td id="delde">
							<a href="${ctx}/credit/contractTemplate/updateForm?id=${ctl.id}">修改</a>
							<a href="#" onclick="uploadTemplate('${ctl.id}','${contractTemplate.templateType}','${contractTemplate.productType}','${contractTemplate.orgplatform}','${contractTemplate.templateName}')">上传合同模板</a>
							<a href="${ctx}/credit/contractTemplate/download?id=${ctl.id}&isUnion=0" onclick="locaurl(this.href)">下载模板</a>
							<a href="${ctx}/credit/contractTemplate/download?id=${ctl.id}&isUnion=1" onclick="locaurl(this.href)">下载联合授信模板</a>
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