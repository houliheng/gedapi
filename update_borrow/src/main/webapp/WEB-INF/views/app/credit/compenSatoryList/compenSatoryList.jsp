<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>还款管理查询</title>
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

		function del() {
			$("#contractNo").val('');
			$("#status").select2("val", "");
			$("#type").select2("val", "");
		}
		function compenSatoryDetail(contractNo, periodNum,compensatoryAmount) {
			if(compensatoryAmount<=0){
				alertx("不能代偿");
				return ;
			}else{
				loading("正在加载...");
				url = "${ctx}/credit/compenSatoryList/form";
				openJBox("compenSatoryListBox", url, "代偿确认", 800, 300,{
					contractNo:contractNo,
					periodNum:periodNum,
					compensatoryAmount:compensatoryAmount
				});
			}
		}
	</script>

</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="compenSatoryList" action="${ctx}/credit/compenSatoryList/list" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="filter">
						<ul class="ul-form">
							<li><label>合同编号：</label>
								<form:input path="contractNo" htmlEscape="false" maxlength="32" class="input-medium"/>
							</li>
							<li><label>     </label>
							</li>
							<li><label>期数：</label>
								<form:input path="periodNum" htmlEscape="false" maxlength="32" class="input-medium"/>
							</li>
						</ul>
						<ul class="ul-form">
							<li><label>代偿状态：</label>
								<select name="compenSatoryFlag" id="compenSatoryFlag" class="input-medium" style="width:176px;">
									<option></option>
									<option value="已代偿">已代偿</option>
									<option value="未代偿">未代偿</option>
									<option value="部分代偿">部分代偿</option>
								</select>
							</li>
						</ul>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />&nbsp;
							<input id="btnReset"class="btn btn-primary" type="button" value="重置" onclick="del()"/>
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<sys:message content="${message}"/>
		<div class="tableList">
			<h3 class="tableTitle">数据列表 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<c:if test="${not empty compenSatory}" >
						代偿余额总额(元):&nbsp; ${compenSatory.sumRemainAmount}
				</c:if>


			</h3>
			<div id="tableDataId">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th style="width:5%">操作</th>
							<th style="width:3%">序号</th>
							<th style="width:7%">代偿状态</th>
							<th style="width:16%">合同编号</th>
							<th style="width:3%">期数</th>
							<th style="width:8%">应还金额</th>
							<th style="width:8%">应还本息</th>
<%--
							<th style="width:8%">应还罚息</th>
--%>
							<th style="width:8%">应还时间</th>
							<th style="width:8%">实还金额</th>
							<th style="width:8%">实还本息</th>
<%--
							<th style="width:8%">实还罚息</th>
--%>
							<th style="width:8%">代偿余额</th>
					<%--		<th style="width:10%">代偿时间</th>--%>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${page.list}" var="compenSatoryList" varStatus="status">
						<tr>
							<td width="20px">
								<input type="hidden" value="${compenSatoryList.contractNo }" name="pHiddenid">
								<input type="hidden" value="${compenSatoryList.periodNum }" name="pHiddenApp">
								<a href="javaScript:void(0)" onclick="compenSatoryDetail('${compenSatoryList.contractNo}','${compenSatoryList.periodNum }','${compenSatoryList.remainCompensatoryAmount}')">代偿</a>
							</td>
							<td class="title" title="${status.index + 1}">${status.index + 1}</td>
							<td class="title" title="${compenSatoryList.compenSatoryFlag}">${compenSatoryList.compenSatoryFlag}</td>
							<td id="type" class="title" title="${compenSatoryList.contractNo}">
								${compenSatoryList.contractNo}
							</td>
							<td id="type" class="title" title="${compenSatoryList.periodNum}">
								${compenSatoryList.periodNum}
							</td>

							<td class="title" title="${compenSatoryList.repayAmount}">${compenSatoryList.repayAmount}</td>
							<td class="title" title="${compenSatoryList.capitalInterestAmount}">${compenSatoryList.capitalInterestAmount}</td>

							<td id="title" class="title" title="${compenSatoryList.fineDate}">
								<fmt:formatDate value="${compenSatoryList.fineDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>

							<td class="title" title="${compenSatoryList.allAmount}">${compenSatoryList.allAmount}</td>
							<td class="title" title="${compenSatoryList.factInterestCapital}">${compenSatoryList.factInterestCapital}</td>
<%--
							<td class="title" title="${compenSatoryList.factFineAmount}">${compenSatoryList.factFineAmount}</td>
--%>
							<td class="title" title="${compenSatoryList.remainCompensatoryAmount}">${compenSatoryList.remainCompensatoryAmount}</td>
						<%--	<td id="lastTime" class="title" title="${compenSatoryList.lastTime}">
								<fmt:formatDate value="${compenSatoryList.lastTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>--%>
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