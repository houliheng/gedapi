<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>阶梯利率管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	$(document).ready(function() {
			
		});
		
	function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
        return false;
       }
        //新增
	function add() {
		var url = "${ctx}/credit/rateInterest/form";
		openJBox("rateInterest-form", url, "新增利率信息", 1000, 400);
	   }

	//修改
	function edit(rateInterestId) {
		if (rateInterestId != null && "" != rateInterestId) {
			var url = "${ctx}/credit/rateInterest/editform?id=" + rateInterestId;
			openJBox("rateInterest-editform", url, "修改利率信息", 1000, 400);
		} else {
			var $checkLine = $("input[name='ids']:checked");
			var $len = $checkLine.length;
			if ($len != 1) {//需要勾选一条信息进行修改
				alertx("请选择一条利率信息");
			} else {
				var url = "${ctx}/credit/rateInterest/editform?id=" + $checkLine.val();
				openJBox("rateInterest-editform", url, "修改利率信息", 1000, 400);
			}
		}
	}
	//删除
	function del() {
	     var $checkLine = $("input[name='ids']:checked");
		 var $len = $checkLine.length;
		 if ($len < 1) {
			alertx("请选择要删除的利率信息");
		 } else {
			var checkedValue = getCheckedValue("ids");
			var url = "${ctx}/credit/rateInterest/delete?ids=" + checkedValue;
		 	confirmx('确认要删除勾选的利率信息吗？', url);
		 }
	}
	//单个删除
	function singleDel(id) {
			var url = "${ctx}/credit/rateInterest/delete?id="+id;
			confirmx('确认要删除利率信息吗？', url);
	}
	
	//重置
	function resetForm() {
		$("#productTypeCode").select2("val", "");
		$("#loanRepayType").select2("val", "");
		$("#periodValue").select2("val", "");
		$("input[name='startTime']").val('');
		$("input[name='endTime']").val('');
	}
	</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="rateInterest" action="${ctx}/credit/rateInterest/" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="filter">
						<table class="searchTable">
							<tr>
								<td class="ft_label">产品类型：</td>
								<td class="ft_content">
									<form:select path="productTypeCode" class="input-medium" >
									<form:option value="" label=""/>
									<form:options items="${fns:getDictList('PRODUCT_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
							       </form:select>
								</td>
								<td class="ft_label">还款方式：</td>
								<td class="ft_content">
									<form:select path="loanRepayType" class="input-medium">
									<form:option value="" label=""/>
									<form:options items="${fns:getDictList('LOAN_REPAY_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
							       </form:select>
								</td>
								<td class="ft_label">期限值：</td>
								<td class="ft_content">
									<form:select path="periodValue" class="input-medium">
									<form:option value="" label=""/>
									<form:options items="${fns:getDictList('PRODUCT_PERIOD_VALUE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
							       </form:select>
								</td>
							</tr>
							<tr>
								<td class="ft_label">起始日期：</td>
								<td class="ft_content">
									<input id="startTime" name="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
									value="<fmt:formatDate value="${rateInterest.startTime}" pattern="yyyy-MM-dd"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}',isShowClear:true});"/>
								</td>
								<td class="ft_label">截止日期：</td>
								<td class="ft_content" >
									<input id="endTime" name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
									value="<fmt:formatDate value="${rateInterest.endTime}" pattern="yyyy-MM-dd"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}',isShowClear:true});"/>
								</td>
							</tr>
						</table> 
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />&nbsp; 
							<input id="btnReset"class="btn btn-primary" type="button" onclick="resetForm();" value="重置"/>
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<sys:message content="${message}"/>
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div class="ribbon">
				<ul class="layout">
					<shiro:hasPermission name="credit:rateInterest:edit">
						<li class="add">
							<a id="add" href="javascript:void(0);" onclick="add();">
								<span>
									<b></b>
									新增
								</span>
							</a>
						</li>
					</shiro:hasPermission>
					<li class="delete">
						<a id="delete" href="javascript:void(0);" onclick="del();">
							<span>
								<b></b>
								删除
							</span>
						</a>
					</li>
				</ul>
			</div>
			<div id="tableDataId" style="max-height: 400px; overflow: auto;">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th style="width: 4%;">
								<input id="allCheck" name="allCheck" type="checkbox" onclick="allCheck();" />
							</th>
							<th style="width: 4%;">序号</th>
							<th >产品类型</th>
							<th >还款方式</th>
							<th >期限值</th>
							<th >月利率 （%）</th>
							<th >起始日期</th>
							<th >截止日期</th>
							<shiro:hasPermission name="credit:rateInterest:edit">
							<th >操作</th>
							</shiro:hasPermission>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${page.list}" var="rateInterest" varStatus="rateInterestList">
						<tr>
							<td>
							<input id="ids" name="ids" type="checkbox" value="${rateInterest.id }" />
							</td>
							<td>${rateInterestList.count }
							</td>
							<td class="title" title="${fns:getDictLabel(rateInterest.productTypeCode, 'PRODUCT_TYPE', '')}">
								${fns:getDictLabel(rateInterest.productTypeCode, 'PRODUCT_TYPE', '')}
							</td>
							<td class="title" title="${rateInterest.loanRepayDesc}">
								${rateInterest.loanRepayDesc}
							</td>
							<td class="title" title="${rateInterest.periodValue}">
								${rateInterest.periodValue}
							</td>
							<td class="title" title="${rateInterest.rateInterest}">
								${rateInterest.rateInterest}
							</td>
							<td class="title" title="${rateInterest.startTime}">
								<fmt:formatDate value="${rateInterest.startTime}" pattern="yyyy-MM-dd "/>
							</td>
							<td class="title" title="${rateInterest.endTime}">
								<fmt:formatDate value="${rateInterest.endTime}" pattern="yyyy-MM-dd "/>
							</td>
							<shiro:hasPermission name="credit:rateInterest:edit">
							<td>
								<a href="javascript:void(0);" onclick="edit('${rateInterest.id}');">修改</a>
								<a href="javascript:void(0);" onclick="singleDel('${rateInterest.id}');">删除</a>
			    			<!--<a href="${ctx}/credit/rateInterest/form?id=${rateInterest.id}">修改</a>
								<a href="${ctx}/credit/rateInterest/delete?id=${rateInterest.id}" onclick="return confirmx('确认要删除该阶梯利率吗？', this.href)">删除</a>
							-->	
							</td>
							</shiro:hasPermission>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="pagination">${page}</div>
		</div>
	</div>
</body>
</html>