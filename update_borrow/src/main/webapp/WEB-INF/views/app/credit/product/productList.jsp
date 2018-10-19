<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
		<title>产品管理</title>
		<meta name="decorator" content="default"/>
		<script type="text/javascript">
			function page(n,s){
				$("#pageNo").val(n);
				$("#pageSize").val(s);
				if($("#pageNo")[0].value.length>10){
					top.$.jBox.tip('当前页码大小长度不能大于10位！');
					return true;
				}
				if($("#pageSize")[0].value.length>10){
					top.$.jBox.tip('每页条数大小的长度不能大于10位！');
					return true;
				}
				$("#searchForm").submit();
		    	return false;
		    }
		    //重置
			function fromReset(){
				$("#productCode").val('');
				$("#productName").val('');
				$("#productTypeCode").select2("val","");
			//	page();//查询
			}
			function getCheckBoxVal(){
			 	var str = "";
				$("input[name=type]:checkbox").each(function(){
					if($(this).attr("checked")){
				    str += $(this).val() + ",";
					}
				});
				return str;
			}
	
			function editColumn(){
				var str = getCheckBoxVal();
				if(str!=""){	
					if(str.indexOf(",")!=str.length-1){
						alertx("请不要选择多条对应的产品信息");
					}else{
						str = str.substring(0,str.length-1);
					    var url = "${ctx}/credit/product/add?id=" + str;
					   openJBox(null, url, "更新产品信息", 1000, 400);
					}
				}else{
					alertx("请选择对应的产品信息");
				}
			}
		    //新增
	    function add(id){
	    	var url = "${ctx}/credit/product/add?id="+id;
	    	openJBox("productAdd-form", url, "新增产品信息", 1000, 400);
	    }
		</script>
	</head>
<body>
	<div class="wrapper" >
		<div class="searchInfo" >
	    	<h3 class="searchTitle">查询条件</h3>
	        <div class="searchCon">
	        	<form:form id="searchForm" modelAttribute="product"  action="${ctx}/credit/product/" method="post" >
	        		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="filter">
						<table class="searchTable">
							<tr>
								<td class="ft_label">产品编码：</td>
								<td class="ft_content">
									<input id="productCode" name="productCode" type="text" maxlength="15" class="input-medium" value="${product.productCode}"/>
								</td>
								<td class="ft_label">产品名称：</td>
								<td class="ft_content">
									<input id="productName" name="productName" type="text" maxlength="50" class="input-medium" value="${product.productName}"/>
								</td>
							</tr>
							<tr>
								<td class="ft_label">产品类型：</td>
								<td class="ft_content">
								<form:select id="productTypeCode" name="productTypeCode" path="productTypeCode" cssStyle="width:178px;" class="input-medium" value="${product.productTypeCode}">
									<form:option value="" label="" />
									<form:options items="${fns:getDictList('PRODUCT_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
								</form:select>
								</td>
								
								<td class="ft_label">所属机构：</td>
								<td class="ft_content" >
									<sys:usertreeselect id="company" name="company.id" value="${product.company.name }" labelName="company.name" labelValue="${product.company.name }" title="机构" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true" />
								</td>
							</tr>			
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />&nbsp;
							<input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="fromReset();"/>
						</div>
					</div>
				</form:form>
	        </div>
	    </div>
	    <sys:message content="${message}"/>
	    <div class="ribbon">
			<ul class="layout">
				<li class="add"><a href="#" id="addNewColumn" onclick="add('')" ><span><b></b>新增</span></a></li>
				<li class="edit"><a href="#" id="editColumn" onclick="editColumn()"><span><b></b>修改</span></a></li>
			</ul>
		</div>
		<div class="tableList"  id="tableListId">
	    	<h3 class="tableTitle">数据列表</h3>
	        <div id="tableDataId" style="max-height:400px;overflow:auto;">
			    <table cellpadding="0" cellspacing="0" border="0" width="100%">
		        <tr>
		        		<th width="3%;"><input type="checkbox" onclick="allCheck();" name="all" id="all"></th>
		        		<th width="12%;">产品编码</th>
		        		<th width="12%;">产品名称</th>
		        		<th width="12%;">产品类型</th>
		        		<th width="12%;">关联流程</th>
		        		<th width="12%;">所属机构</th>
		        		<th width="20%;">产品描述</th>
		        		<th width="12%;">操作</th>
		        	</tr>
		        	<c:forEach items="${page.list}" var="product" varStatus="index">
						<c:if test="${0 == index.count%2}"><tr class="doubleRow"></c:if> 
						<c:if test="${1 == index.count%2}"><tr></c:if> 
								<td><input type="checkbox" value="${product.id}" name="type"></td>
								<td class="title">${product.productCode}</td>
								<td title="${product.productName}"  class="title">${product.productName}</td>
								<td class="title">${product.productTypeDesc}</td>
								<td class="title">${product.procName}</td>
								<td class="title">${product.orgName}</td>
								<td title="${product.remarks}" class="title">${product.remarks}</td>
								<td>
									<a href="#" onclick="add('${product.id}')" >修改</a>
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