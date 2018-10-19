<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<!-- 
  @reqno:H1601220021
  @date-designer:2016年1月28日-gaofeng
  @date-author:2016年1月28日-gaofeng:客户基本信息列表信息
 -->
<head>
	<title>客户基本信息管理</title>
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
         //重置 方法
        function fromReset(){
			$("#custName").val('');
			$("#mobileNum").val('');
			$("#idType").val('');
			$("#s2id_cardType>.select2-choice>.select2-chosen").html('--全部--');
			$("#idNum").val('');
		}
		//查询  方法
		function searchData(){
			loading();
			$("#searchForm").submit();		
		}
		//详情 方法
		function detail(id){
			var windowWidth = window.parent.window.document.body.offsetWidth -200;
		   // var windowHeight = window.parent.window.document.body.offsetHeight - 50;
		   var windowHeight =  $(document).height()-50;
			window.open("${ctx}/custinfo/custInfo/form?id="+id, "客户详情", "height="+windowHeight+",width="+windowWidth+", scrollbars=yes , top=250,resizable=yes,left=150");
		}
	</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="custInfo" action="${ctx}/custinfo/custInfo/" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="filter">
						<table class="searchTable">
							<tr>
								<td class="ft_label">名称：</td>
								<td class="ft_content"><input id="custName" name="custName" type="text" maxlength="50" class="input-medium" value="${custInfo.custName }" /></td>
								<td class="ft_label">手机号：</td>
								<td class="ft_content"><input id="mobileNum" name="mobileNum" type="text" maxlength="15" class="input-medium" value="${custInfo.mobileNum }" /></td>
							</tr>
							<tr>
								<td class="ft_label">证件类型：</td>
								<td class="ft_content">
									<form:select path="idType" id="idType" class="input-medium">
										<form:option value="" label="--全部--"/>
										<form:options items="${fns:getDictList('CUSTOMER_P_ID_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
									</form:select>
								</td>
								<td class="ft_label">证件号：</td>
								<td class="ft_content"><input id="idNum" name="idNum" type="text" maxlength="50" class="input-medium" value="${custInfo.idNum }" /></td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="searchData();" />&nbsp; 
							<input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="return fromReset();" />
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<sys:message content="${message}"/>
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div id="tableDataId" style="max-height:400px;overflow:auto;">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th>名称</th>
							<th>证件类型</th>
							<th>证件号</th>
							<th>手机号</th>
							<th>创建人</th>
							<th>创建时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${page.list}" var="custInfo">
						<tr>
							<td><a href="javascript:void(0)" onclick="detail('${custInfo.id}')">
								${custInfo.custName}
							</a></td>
							<td>
								${fns:getDictLabel(custInfo.idType, 'CUSTOMER_P_ID_TYPE', '')}
							</td>
							<td>
								${custInfo.idNum}
							</td>
							<td>
								${custInfo.mobileNum}
							</td>
							<td>
								${custInfo.loginName}
							</td>
							<td>
								<fmt:formatDate value="${custInfo.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>
			    				<a href="javascript:void(0)" onclick="detail('${custInfo.id}')">详情</a>
							</td>
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