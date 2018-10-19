<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
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
<html>
  <head>
	<title>客户登记列表</title>
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
				return true
			}
			$("#searchForm").submit();
	    	return false;
	    }
	    function formSubmit(){
	    	$("#searchForm").submit();
	    }
	    //重置
		function fromReset(){
			$("#cusName").val('');
			$("#idType").val('');
			$("#idNum").val('');
			$("#applyStatus").val('');
			page();//查询
		}

		//页面数据列表高度设置
	    $(document).ready(function() {
	    	/* var windowHeight = window.parent.window.document.body.offsetHeight;
	    	var x = (windowHeight - 555) + "px";
			document.getElementById("tableDataId").style.height = x; */
			
	    	var tds = $(".title").filter("td");
			$.each(tds,function(){
				$(this).attr("title",$(this).html());
			});
	    }); 
	    
		//删除
		function deleteData(){
			var str = getCheckedValue();
			if(str!=""){
				var url = "${ctx}/credit/customer/delete?id="+str;
				return confirmx('是否删除?',url);
			}
		}
		
		 /**
			 * @reqno: H1510210067
			 * @date-designer:20151028-gengchang
			 * @date-author:20151028-gengchang: CRE_信贷审批_进件管理_客户登记列表_新增、修改_提交_做客户唯一性校验
			 *				editData():增加修改按钮，只能选中一条记录进行修改
		  */
		//修改
		function editData(){
			var str = getCheckedValue();		//复选框选中的值
			var s = document.getElementsByName("type");		
			var total = 0;		//复选框选中的个数
			if(str!=""){
					 for(var i=0;i<s.length;i++){
						if(s[i].checked)
						{
							total++;
						}
					}
					if(total>1){
						alert("只能选择一条进行修改！");
					}else{
						var url = "${ctx}/credit/customer/add?id="+str;
						location.href=url;
					}
			}
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
	//获取procInstId
	function getProcHiddenVal(){
	 	var str = "";
		$("input[name=type]:checkbox").each(function(){
			if($(this).attr("checked")){
		    str = $(this).next("input[name=cusProcInsId]:hidden").val();
			}
		});
		return str;
	}
   /**
    * @reqno:H1511100047
    * @date-designer:20151111-huangxuecheng
    * @date-author:20151111-huangxuecheng: CRE_信贷审批_进件管理_个人客户登记列表_流程图、流程轨迹,描述：流程轨迹的js方法
	*/
	//流程轨迹
	function processTrack(){
		var str = getCheckBoxVal();
		if(str!=""){	
			if(str.indexOf(",")!=str.length-1){
				alertx("请不要选择多条对应的客户信息");
			}else{
			    var proId = getProcHiddenVal();
			    if(proId == ""){
			    	alertx("当前客户信息的流程未启动，请先提交");
			    }else{
			    var url = "${ctx}/credit/taskCenter/processTrack?procInstId=" + proId;
				window.showModalDialog(url,null,"dialogWidth:1000px;dialogHeight:600px;applyStatus:no;help:no;resizable:yes;");
				}
			}
		}else{
			alertx("请选择对应的客户信息");
		}
	}
   /**
    * @reqno:H1511100047
    * @date-designer:20151111-huangxuecheng
    * @date-author:20151111-huangxuecheng: CRE_信贷审批_进件管理_个人客户登记列表_流程图、流程轨迹,描述：流程图的js方法
	*/
	//流程图
	function tracePhoto(){
		var str = getCheckBoxVal();
		if(str!=""){	
			if(str.indexOf(",")!=str.length-1){
				alertx("请不要选择多条对应的客户信息");
			}else{
				var proId = getProcHiddenVal();
				if(proId == ""){
			    	alertx("当前客户信息的流程未启动，请先提交");
			    }else{
		    		var url = "${ctx}/credit/taskCenter/trace/photo/customer?procInstId=" + proId;
		    		var windowWidth = window.parent.window.document.body.offsetWidth -50;
					var windowHeight = window.parent.window.document.body.offsetHeight - 50;
					window.showModalDialog(url, null, "dialogWidth:" + windowWidth + "px" + ";dialogHeight:" + windowHeight + "px" + ";applyStatus:no;help:no;resizable:yes;");	
				}
			}
	    }else{
			alertx("请选择对应的客户信息");
		}
	}
	</script>
 </head>

<body>
	<!-- **
		 * @reqno: H1509110039
		 * @date-designer:20150918-gengchang
		 * @date-author:20150918-gengchang: CRE_信贷审批_进件管理_客户登记列表_查询
		 *			根据以下输入项进行查询，客户名称支持模糊查询;
	-->
	 <ul class="nav nav-tabs">
	 	<li class="active"><a href="${ctx}/credit/customer">个人客户登记</a></li>
	 	<li class="line"></li>
	 	<li><a href="${ctx}/credit/customer/add">个人客户登记新增</a></li>
	 </ul>
 	<div class="wrapper">
		<div class="searchInfo">
		    <h3 class="searchTitle">查询条件</h3>
		        <div class="searchCon">
				   	 <form:form id="searchForm" modelAttribute="customer"  action="${ctx}/credit/customer" method="post" >
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<div class="filter">
							<table class="searchTable">
								<tr>
									<td class="ft_label">客户名称：</td>
									<td class="ft_content" >
										<input id="cusName" name="cusName" type="text" maxlength="50" class="input-medium" value="${customer.cusName}"/>
									</td>
									<td class="ft_label">证件类型：</td>
									<td class="ft_content">
										<form:select  id="idType"  name="idType"  path="idType"  class="input-medium"  value="${customer.idType}">
												<form:option value="" label = "  --全部--"/>
												<form:options items="${fns:getDictList('CUSTOMER_P_ID_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
										</form:select>
									</td>
								</tr>
								<tr>
									<td class="ft_label">证件号码：</td>
									<td class="ft_content">
										<input id="idNum" name="idNum" type="text" maxlength="50" class="input-medium" value="${customer.idNum}"/>
									</td>
									<td class="ft_label">状态：</td>
									<td class="ft_content">
										<form:select  id="applyStatus"  name="applyStatus"  path="applyStatus"  class="input-medium"  value="${customer.applyStatus}">
												<form:option value="" label = "  --全部--"></form:option>
												<form:options items="${fns:getDictList('APPLY_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
										</form:select>
									</td>
								</tr>
						</table>
							<div class="searchButton">
								<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="formSubmit();"/>
								<input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="return fromReset();"/>
							</div>
					</div>				
				</form:form>
			</div>
		</div>
		<div class="ribbon">
	    	<ul class="layout">
	    		<li class="add"><a id="add" href="${ctx}/credit/customer/add" ><span><b></b>新增</span></a></li>
	        	<li class="edit"><a id="edit" href="#" onclick="editData();" ><span><b></b>修改</span></a></li>
	        	<li class="delete"><a id="delete" href="#" onclick="deleteData();"><span><b></b>删除</span></a></li>
	        	<li class="mcp_pic"><a href="#" id="processPicture" onclick="tracePhoto()"><span><b></b>流程图</span></a></li>
				<li class="mcp_change"><a href="#" id="processTrack" onclick="processTrack()"><span><b></b>流程轨迹</span></a></li>
	        </ul>
		</div>
		<sys:message content="${message}"/>
		<div class="tableList">
	    	<h3 class="tableTitle">数据列表</h3>
	    	<!-- <table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<th width="20px;"><input type="checkbox" onclick="allCheck();" name="all" id="all"></th>
					<th width="160px;">客户名称</th>
					<th width="160px;">申请编号</th>
					<th width="160px;">证件类型</th>
					<th width="160px;">证件号</th>
					<th width="160px;">移动电话</th>
					<th width="160px;">渠道</th>
					<th width="110px;">客户来源</th>
					<th width="110px;">登记日期</th>
					<th width="110px;">状态</th>
					<th>操作</th>
				</tr>
			</table> -->
			 <div id="tableDataId" style="max-height:400px;overflow:auto;">
			    <table cellpadding="0" cellspacing="0" border="0" width="100%">
			        <tr>
						<th width="20px;"><input type="checkbox" onclick="allCheck();" name="all" id="all"></th>
						<th width="10%">客户名称</th>
						<th width="10%">申请编号</th>
						<th width="10%">证件类型</th>
						<th width="10%">证件号</th>
						<th width="10%">移动电话</th>
						<th width="8%">客户来源</th>
						<th width="10%">登记日期</th>
						<th width="10%">状态</th>
						<th>操作</th>
					</tr>
		        	<c:forEach items="${page.list}" var="cus" varStatus="index">
						<c:if test="${0 == index.count%2}"><tr class="doubleRow"></c:if> 
						<c:if test="${1 == index.count%2}"><tr></c:if> 
						<!-- 
							@reqno:H1511100047
							@date-designer:20151111-huangxuecheng
							@date-author:20151111-huangxuecheng:CRE_信贷审批_进件管理_个人客户登记列表_流程图、流程轨迹，隐藏域查出proInsId
						-->
						<!-- 
							 * @reqno:H1601280119
							 * @date-designer:2016年1月28日-lirongchao
							 * @e-out-other : applyStatus - 状态 :状态
							 * @e-out-other : mobileNum - 移动电话 :移动电话
							 * @e-out-other : applyCode - 申请编号 :申请编号
							 * @e-out-other : registerDate - 登记日期:登记日期
							 * @date-author:2016年1月28日-lirongchao:CRE_信贷审批_进件管理_客户登记列表_个人客户登记_改造
							 										因新表和旧表差异，所以需要修改表结构,相对应的也需要修改实体类
						 --> 						
						<td width="20px">
						<input type="checkbox" value="${cus.id}" name="type">
						<input type="hidden" value="${cus.procInsId }" name="cusProcInsId">
						</td>
						<td class="title">${cus.cusName}</td>
						<td class="title">${cus.applyCode}</td>
						<td class="title">${cus.idType}</td>
						<td class="title">${cus.idNum}</td>
						<td class="title">${cus.mobileNum}</td>
						<td class="title">${cus.channelSourceType}</td>
						<td class="title"><fmt:formatDate value="${cus.registerDate}" type="both" pattern="yyyy-MM-dd"/></td>
						<td class="title">${cus.applyStatus}</td>
						<!-- 
							 * @reqno: H1509110042
							 * @date-designer:20150918-gengchang
							 * @date-author:20150918-gengchang: CRE_信贷审批_进件管理_客户登记列表_修改、删除
							 *			1.修改：
							 *			   当状态为已提交时，保存、提交按钮为灰色不可用状态；其它需求同新增功能
							 *			2.删除：
							 *			  在查询页面表格，选择1个及1个以上记录，点击删除，进行删除操作；
							 *			3.其它：删除只能删除未提交的记录;
						-->
						<td>
	    					<a href="${ctx}/credit/customer/add?id=${cus.id}">修改</a>
							<a href="${ctx}/credit/customer/delete?id=${cus.id}" onclick="return confirmx('确认要删除该客户信息吗？', this.href)">删除</a>
						</td>
					</tr>
					</c:forEach>
				</table>
			</div>
		<div class="pagination">${page}</div>
	</body>
</html>

 