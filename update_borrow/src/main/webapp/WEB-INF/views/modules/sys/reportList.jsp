<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!-- 
	/**
 	* @reqno: 	H1512300129
 	* @date-designer:20160112-sunna
 	* @date-author:20160112-sunna:增加报表管理功能:包括报表的增加、删除、修改、展示、查询功能。
 	*/
 -->
<html>
<head>
	<title>报表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	/*
	* @reqno:  	H1512300129
	* @date-designer:2016年1月12日-sunna
	* @date-author:2016年1月12日-sunna:分页添加提示窗口，如果输入数据长度超过10位需要提示
	*/
	
	/*
	* @reqno: H1601180023
	* @date-designer:2016年1月21日-sunna
	* @date-author:2016年1月21日-sunna:可以切换至下一页面、上一页面，或任意指定页面。点击下一页、上一页或指定页面的时候调用这个方法。
	*/
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			if($("#pageNo")[0].value.length>10){
				top.$.jBox.tip('当前页码大小长度不能大于10位！');
				return true;
			}
			else if($("#pageSize")[0].value.length>10){
				top.$.jBox.tip('每页条数大小的长度不能大于10位！');
				return true
			}else{
				$("#searchForm").submit();
		    	return false;
			}
	    }
	    /**
			 * 
			 * @reqno: 	H1512300129
			 * @date-designer:20160112-sunna
			 * @e-ctrl : ID编号 - ready : 定义在页面加载完成之后执行的方法
			 * @e-ctrl : ID编号 - select2 : 调用jQuery_select2的方法
			 * @date-author:20160112-sunna:1.当页面加载完成，调用jQuery_select2的方法为下拉框加上清除按钮
			 *                                  
			 */
	    $(document).ready(function(){
	    	$("#type").select2({
			    placeholder:" ",
			    allowClear: true
			})
	    });

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/report/">报表列表</a></li>
		<shiro:hasPermission name="sys:report:edit"><li><a href="${ctx}/sys/report/form?sort=10">报表添加</a></li></shiro:hasPermission>
	</ul>
	<!-- 
	/**
 	* @reqno: 	H1601050065
 	* @date-designer:20160112-sunna
 	* @date-author:20160112-sunna:报表管理增加报表查询功能。可以根据报表编号和报表名称进行查询。
 	*/
	 -->
	<form:form id="searchForm" modelAttribute="report" action="${ctx}/sys/report/" method="post" class="breadcrumb form-search">
	<!-- 
		/*
		* @reqno: H1601180023
		* @date-designer:2016年1月21日-sunna
		* @date-author:2016年1月21日-sunna:可以切换至下一页面、上一页面，或任意指定页面。翻页参数。
		*/
	 -->
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>报表编号：</label>
		<!-- 
			/**
 			* @reqno: 	H1601050065
 			* @date-designer:20160112-sunna
 			* @date-author:20160112-sunna:报表管理增加报表查询功能。可以根据报表编号和报表名称进行查询。输入框不应设置成下拉框，将原来设计的下拉框改为输入框。
 			*/
		 -->
		<form:input path="no" htmlEscape="false" maxlength="50" class="input-medium"/>
		<%-- <form:select path="no" class="input-medium">
			<form:option value="" label="" />
			<form:options items="${noList}" htmlEscape="false"/>
		</form:select> --%>
		&nbsp;&nbsp;<label>报表名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		
		<thead><tr><th>报表编号</th><th>报表名称</th><shiro:hasPermission name="sys:report:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="report">
			<tr>
				<td>${report.no}</td>
				<td>${report.name}</td>
				<shiro:hasPermission name="sys:report:edit"><td>
				<!-- 
				/**
 				* @reqno: 	H1601050060
 				* @date-designer:20160112-sunna
 				* @date-author:20160112-sunna:报表管理增加报表修改功能。可以修改报表编号和报表名称。
 				*/
				 -->
				 <!-- 
				 /**
	 			  * @reqno: 	H1601050060
	 			  * @date-designer:20160118-sunna
	 			  * @date-author:20160118-sunna：修改报表页面正文乱码问题。页面的name字段传给后台，会报错。改为只将id传给后台，后台去数据库获取数据再在页面展示。
	 			 */
				  -->
				
    				<a href="${ctx}/sys/report/form?id=${report.id}">修改</a>
    				<!-- 
    				/**
 					* @reqno: H1601050066
 					* @date-designer:20160112-sunna
 					* @date-author:20160112-sunna:报表管理增加报表查询功能。在列表中点击删除按钮，删除对应的报表数据。
 					*/
    				 -->
					<a href="${ctx}/sys/report/delete?id=${report.id}" onclick="return confirmx('确认要删除该报表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<!-- 
		/*
		* @reqno: H1601180023
		* @date-designer:2016年1月21日-sunna
		* @date-author:2016年1月21日-sunna:可以切换至下一页面、上一页面，或任意指定页面。调用翻页方法
		*/
	 -->
	<div class="pagination">${page}</div>
</body>
</html>