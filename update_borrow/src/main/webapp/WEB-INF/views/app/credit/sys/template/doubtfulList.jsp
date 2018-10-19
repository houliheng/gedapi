<!--
* @reqno:H1509230028
* @date-designer:2015年9月24日-songmin
* @date-author:2015年9月24日-songmin:添加最新统一样式
-->
<!--  
@reqno:H1511230004
@date-designer:20151125-songmin
@date-author:20151125-songmin:低分辨率下页面样式更改
-->
<!--  
@reqno:H1512140102
@date-designer:20151215-songmin
@date-author:20151215-songmin:系统页面布局分辨率优化_在低分辨率下，部分表格、表单显示错位，统一调整优化
-->
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>存疑调查模板管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/js/checkbox.js"></script>
	<script type="text/javascript">
		function page(n, s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			if($("#pageNo")[0].value.length>10){
				top.$.jBox.tip('当前页码大小长度不能大于10位！');
				return true;
			}
			if($("#pageSize")[0].value.length>10){
				top.$.jBox.tip('每页条数大小的长度不能大于10位！');
				return true
			}
			$("#searchForm").submit();
	    	return false;
	    }
		
		//重置
		function fromReset(){
			$("#templateName").val('');
			$("#productType").val('');
			page();//查询
		}
		
		//模态模式上传文件
		function uploadFile(id){
			var url = "${ctx}/credit/sys/template/uploadView?fileType=1&id="+id;
			window.showModalDialog(url,null,"dialogWidth:550px;dialogHeight:300px;status:no;help:no;resizable:yes;");
			//window.open(url,"上传文件","width= 500px" + ",height=250px");
		}
	/**
	 * @reqno:AH1509130057
	 * @date-designer:20151020-huangxuecheng
	 * @date-author:20151020-huangxuecheng:系统管理-存疑调查模板配置信息页面，修改新增js方法，全部使用ajax提交然后，返回一个值判断是新增或者修改或者删除
	 * 原生的js校验
	 */
		//新增
		function addView(productType){
			var url = "${ctx}/credit/sys/template/addDoubtfulView?fileType=1&productType="+productType;
			var returnVal = window.showModalDialog(url,null,"dialogWidth:500px;dialogHeight:300px;status:no;help:no;resizable:yes;");
			if(productType!=''){
				if(returnVal==1){
					returnVal = 2;
				}else{
					returnVal = 0;
				}
			}
			var finalUrl = "${ctx}/credit/sys/template/templateList?fileType=1" + "&success_flag=" + returnVal;
	   		 $("#searchForm").attr("action",finalUrl).submit();
			
			//location.reload();
			//alert(backUrl);
		}
		
		//删除
		function deleteData(){
			var str = getCheckedValue();
			if(str!=""){
				var url = "${ctx}/credit/sys/template/deleteAll?fileType=1&idStr="+str;
				return confirmx('是否删除?', url);
			}else{
				alert("请选择产品类型!");
			}
		}

	    $(document).ready(function() {
	    	/* var windowHeight = window.parent.window.document.body.offsetHeight;
	    	var x = (windowHeight - 458) + "px";
			document.getElementById("tableDataId").style.height = x; */
		    resetTip();
	    });
	</script>
</head>
<body>
	<!-- 
		/**
		 * @reqno: H1509130056
		 * @date-designer:2015年9月20日-wujing01
		 * @date-author:2015年9月20日-wujing01:查询产品资料模板列表（存疑调查，资料核查）
		 */
	 -->
	  <!-- 
	 @reqno:AH1509130057
	 @date-designer:20151020-huangxuecheng
	 @date-author:20151020-huangxuecheng:系统管理-存疑调查模板配置信息页面，修改以前留下的问题，增加productType隐藏回显，改变以前的submit按钮改成button后使用js的ajax提交而不是form提交
	 @e-in-text:templateName-模板名称:模板名称
	 @e-in-list:productType-产品类型:产品类型
	 @e-ctrl:btnSubmit-提交按钮:提交按钮
	 @e-ctrl:btnReset-返回按钮:返回按钮	 
	 @e-out-table:tableDataId-数据列表:数据列表
	-->
 	<div class="wrapper">
		<div class="searchInfo">
	    	<h3 class="searchTitle">查询条件</h3>
	        <div class="searchCon">
	        	<form:form id="searchForm" modelAttribute="template"  action="${ctx}/credit/sys/template/templateList?fileType=1" method="post" >
	        		<!-- <input id="success_flag" name="success_flag" type="hidden" value="0"/> -->
	        		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize"  type="hidden" value="${page.pageSize}"/>
					<div class="filter">
						<table class="searchTable">
							<tr>
								<td class="ft_label">模板名称：</td>
								<td class="ft_content">
									<form:input path="templateName" htmlEscape="false" maxlength="50" class="input-medium"/>
								</td>
								
								<td class="ft_label">产品类型：</td>
								<td class="ft_content">
									<form:select id="productType" name="productType" path="productType" class="input-medium" value="${product.productType}">
										<form:option value="" label="" />
										<form:options items="${fns:getDictList('PRODUCT_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
									</form:select>
								</td>
							</tr>
						</table>
						<div class="searchButton">
					        <!-- 
								 * @reqno: 	H1512250042
								 * @date-designer:2015年12月28日-lirongchao
								 * @e-ctrl : btnSubmit -查询: 查询
								 * @date-author:2015年12月28日-lirongchao:1）当上传超大文件时，给出友好提示,不让上传；
																2）修改分页并查询时，按当前设置的分页进行刷新，不要恢复初始状态，重置时才恢复初始状态，与系统其他界面的分页功能保持一致；;
																        
					         -->						
							<input id="btnSubmit"  class="btn btn-primary" type="submit" value="查询" onclick="return page('${page.pageNo}','${page.pageSize}');"/>
							<input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="return fromReset();"/>
						</div>
					</div>
				</form:form>
	        </div>
	    </div>
	    
		<sys:message content="${message}"/>
		
		<!--
			 /**
	 		 * @reqno: AH1509130057
			 * @date-designer:2015年9月20日-wujing01
	 	 	* @date-author:2015年9月20日-wujing01:新增某一产品类型的模板信息(断定产品类型的唯一性) 
	 	 	*/
	 	  -->
	    <div class="ribbon">
	    	<ul class="layout">
	        	<li class="add"><a href="#" onclick="addView('');"><span><b></b>新增</span></a></li>
	        	<li class="delete"><a href="#" onclick="deleteData();"><span><b></b>删除</span></a></li>
	        </ul>
	    </div>
	    
		<div class="tableList">
	    	<h3 class="tableTitle">数据列表</h3>
	    	<!-- <table cellpadding="0" cellspacing="0" border="0" width="100%">
	        	<tr>
					<th width="20px;"><input type="checkbox" onclick="allCheck();" name="all" id="all"></th>
					<th width="150px;">产品类型</th>
					<th>模板名称</th>
					<th width="160px;">更新时间</th>
					<th width="150px;">操作</th>
	        	</tr>
	        </table> -->
	        <div id="tableDataId" style="max-height:400px;overflow:auto;">
		    	<table cellpadding="0" cellspacing="0" border="0" width="100%">
		        	<tr>
						<th width="20px;"><input type="checkbox" onclick="allCheck();" name="all" id="all"></th>
						<th width="20%">产品类型</th>
						<th width="20%">模板名称</th>
						<th width="20%">更新时间</th>
						<th>操作</th>
		        	</tr>
		        	<c:forEach items="${page.list}" var="template"  varStatus="index">
						<c:if test="${0 == index.count%2}"><tr class="doubleRow"></c:if> 
						<c:if test="${1 == index.count%2}"><tr></c:if>
							<td width="20px;"><input type="checkbox" value="${template.id}" name="type"></td>
							<td><c:out value="${template.productTypeName}"></c:out></td>
							<td><c:out value="${template.templateName}"></c:out></td>
							<td><fmt:formatDate value="${template.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
							<td>
			    				<a href="#" id="upd" onclick="addView('${template.productType}')">修改</a>
								<!-- 	
								/** 
			 					 * @reqno: H1509130058
								 * @date-designer:2015年9月20日-wujing01
			 					 * @date-author:2015年9月20日-wujing01:根据模块ID产品资料模板文件上传发布,下载
			 					 */ 
			 					 -->
			    				<a href="#" id="uploadF" onclick="uploadFile('${template.id}')">模板上传</a>
								<a href="${ctx}/credit/sys/template/download?id=${template.id}&fileType=1" onclick="locaurl(this.href)">模板下载</a>
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