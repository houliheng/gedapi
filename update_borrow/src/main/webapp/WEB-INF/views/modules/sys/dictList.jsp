<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>字典管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	/*
	* @reqno: H1507030028
	* @date-designer:2015年7月2日-daijun
	* @date-author:2015年7月2日-daijun:分页添加提示窗口，如果输入数据长度超过10位需要提示
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
			 * @reqno:H1506250096
			 * @date-designer:20150626-zhangxingyu
			 * @e-ctrl : ID编号 - ready : 定义在页面加载完成之后执行的方法
			 * @e-ctrl : ID编号 - select2 : 调用jQuery_select2的方法
			 * @date-author:20150626-zhangxingyu:1.当页面加载完成，调用jQuery_select2的方法为下拉框加上清除按钮
			 *                                  
			 */
	    $(document).ready(function(){
	    	$("#type").select2({
			    placeholder:" ",
			    allowClear: true
			});
	    	/**
		 	* @reqno: 	H1512180091
		 	* @date-designer:20151223-sunna
		 	* @date-author:20151223-sunna:替换列表中展示的系统编号，替换成对应的系统名称
		 	*/
	    	$.ajax({
	 					type: "GET",
 					   	url: "${ctx}/sys/sysManage/getName",
 					   	//data:{parentId: id},
 					   	datatype: 'text',
 					   	success: function(result){
 					   		for (var j = 0; j < result.length; j++) {
 								var row = result[j];
 								//sunna根据no获取name
 						    	$("table tr:gt(0)").each(function(i){
 						    		//alert("这是第"+i+"行的内容");
 						    		$(this).children("td").each(function(i){
 						    			if(i==5){
 						    				if($(this).text()==result[j].no){
 						    					$(this).html(result[j].name);
 						    				}
 						    				
 						    			}
 						    			
 						    		})
 						    	})
 					   		}
 					   	}	
	 				});
	    });
	    /**end*/
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/dict/">字典列表</a></li>
		<shiro:hasPermission name="sys:dict:edit"><li><a href="${ctx}/sys/dict/form?sort=10">字典添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="dict" action="${ctx}/sys/dict/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>类型：</label>
		<form:select id="type" path="type" class="input-medium">
			<form:option value="" label="" />
			<form:options items="${typeList}" htmlEscape="false"/>
		</form:select>
		&nbsp;&nbsp;<label>描述 ：</label><form:input path="description" htmlEscape="false" maxlength="50" class="input-medium"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<!--/**
		 	* @reqno: 	H1512180091
		 	* @date-designer:20151223-sunna
		 	* @date-author:20151223-sunna:增加所属系统列
		 	*/-->
		<thead><tr><th>键值</th><th>标签</th><th>类型</th><th>描述</th><th>排序</th><th>所属系统</th><shiro:hasPermission name="sys:dict:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="dict">
			<tr>
				<td>${dict.value}</td>
				<!-- /**
					 * 
					 * @reqno: 	H1506290083
					 * @date-designer:20150630-zhangxingyu
					 * 
					 * @date-author:20150630-zhangxingyu:加一个请求参数isCheck=1用来标记该请求是查看
					 */ -->
				<td><a href="${ctx}/sys/dict/form?id=${dict.id}&isCheck=1">${dict.label}</a></td>
				<!--
				 * @reqno:H1507080059
				 * @date-designer:2015年7月8日-zhunan
				 * @e-out-other : contentTable - 列表 : 展示字典项列表
				 * @e-out-other : type - 列内容: 类型
				 * @date-author:2015年7月8日-zhunan: 类型处不需要链接，查找本类型的东西直接在查询条件中选择即可，此处赘余
				-->
<!-- 				<td><a href="javascript:" onclick="$('#type').val('${dict.type}');$('#searchForm').submit();return false;">${dict.type}</a></td> -->
				<td>${dict.type}</td>
				<td>${dict.description}</td>
				<td>${dict.sort}</td>
				<!--/**
		 			* @reqno: 	H1512180091
		 			* @date-designer:20151223-sunna
		 			* @date-author:20151223-sunna:增加所属系统列的展示
		 			*/-->
				<td>${dict.sysNo}</td>
				<shiro:hasPermission name="sys:dict:edit"><td>
    				<a href="${ctx}/sys/dict/form?id=${dict.id}">修改</a>
					<a href="${ctx}/sys/dict/delete?id=${dict.id}&type=${dict.type}" onclick="return confirmx('确认要删除该字典吗？', this.href)">删除</a>
    				<!-- /**
	 * 
	 * @reqno:H1505270024
	 * @date-designer:20150618-zhangxingyu
	 * 
	 * 
	 * @date-author:20150618-zhangxingyu:此汉字参数无用   去掉此参数
	 */ -->
    				<a href="${ctx}/sys/dict/form?type=${dict.type}&sort=${dict.sort+10}">添加键值</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<div class="pagination">${page}</div>
</body>
</html>