<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>阶梯本金管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#searchForm").validate({
			submitHandler : function(form) {
				form.submit();
			},
			errorContainer : "#messageBox",
			errorPlacement : function(error, element) {
				checkReq(error, element);
			}
		});
	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
	//新增
	function addCapital() {
		var url = "${ctx}/credit/rateCapital/form";
		openJBox("rateCapital-form", url, "新增本金比例信息", 1000, 400);
	}
	//修改
	function editCapital() {
		var checkedId = $("input[name='ids']:checked");
		var len = checkedId.length;
		if (len != 1) {//需要勾选一条信息进行修改
			alertx("请选择一条本金信息");
		} else {
			var url = "${ctx}/credit/rateCapital/editform?id="+ checkedId.val();
			openJBox("rateCapital-editForm", url, "修改本金比例信息", 1000, 400);
		}
		
	}
	//删除
	function singleDel(id) {
		var checkedId = $("input[name='ids']:checked");
		var len = checkedId.length;
		if (len != 1) {//需要勾选一条信息进行修改
			alertx("请选择一条本金信息");
		} else {
			var url = "${ctx}/credit/rateCapital/delete?id=" + checkedId.val();
			confirmx('确认要删除本金比例信息吗？', url);
		}
	}
	//重置
	function resetForm() {
		$("#productTypeCode").select2("val", "");
		$("#loanRepayType").select2("val", "");
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="rateCapital"
					action="${ctx}/credit/rateCapital/" method="post"
					class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden"
						value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden"
						value="${page.pageSize}" />
					<div class="filter">
						<table class="searchTable">
							<tr>
								<td class="ft_label">产品类型：</td>
								<td class="ft_content"><form:select path="productTypeCode"
										class="input-medium required">
										<form:option value="" label="" />
										<form:options items="${fns:getDictList('PRODUCT_TYPE')}"
											itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select></td>
								<td class="ft_label">还款方式：</td>
								<td class="ft_content"><form:select path="loanRepayType"
										class="input-medium required">
										<form:option value="" label="" />
										<form:options items="${fns:getDictList('LOAN_REPAY_TYPE')}"
											itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select></td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />&nbsp; 
						    <input id="btnReset" class="btn btn-primary" type="button" onclick="resetForm();" value="重置" />
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div class="ribbon">
				<ul class="layout">
					<shiro:hasPermission name="credit:rateCapital:edit">
						<li class="add">
						<a id="add" href="javascript:void(0);" onclick="addCapital();"> 
							<span> 
								<b></b> 
								新增 
							</span>
						</a>
						</li>
					</shiro:hasPermission>
					<li class="edit">
						<a id="edit" href="javascript:void(0);" onclick="editCapital();">
							<span>
								<b></b>
								修改
							</span>
						</a>
					</li>
					<li class="delete">
					<a id="delete" href="javascript:void(0);" onclick="singleDel();"> 
					<span> <b></b> 删除</span>
					</a>
					</li>
				</ul>
			</div>
			<div id="tableDataId" style="overflow: auto;">
				<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
					<thead>
						<tr>
							<th style="width: 2%;"><input id="allCheck" name="allCheck" type="checkbox" onclick="allCheck();" /></th>
							<th>产品类型</th>
							<th style="width: 8%;">还款方式</th>
							<th>合同\当期</th>
							<c:forEach items="${periodValueList}" var="periVal"  varStatus="periValList">
								<th>${periVal}</th>
							</c:forEach>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${selectList}" var="rateCapital" varStatus="rateCapitalList">
							<tr>
								<td><input id="ids" name="ids" type="checkbox" value="${rateCapital.id }" /></td>
								<td class="title" title="${fns:getDictLabel(rateCapital.productTypeCode, 'PRODUCT_TYPE', '')}">
									${fns:getDictLabel(rateCapital.productTypeCode, 'PRODUCT_TYPE', '')}
								</td>
								<td class="title" title="${rateCapital.loanRepayDesc}">${rateCapital.loanRepayDesc}</td>
								<td id = "periodValueID" class="title" title="${rateCapital.periodValue}">${rateCapital.periodValue}</td>
								<c:forEach items="${resultList}" var="rateCurr" varStatus="rateCurrList">
									<c:if test="${rateCapital.periodValue == rateCurr.periodValue}">
										<td class="title" title="${rateCurr.rateCapitalCurr}">${rateCurr.rateCapitalCurr}</td>
									</c:if>
								</c:forEach>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>