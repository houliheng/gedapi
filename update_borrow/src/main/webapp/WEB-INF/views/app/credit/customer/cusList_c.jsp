<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!-- 
 * @reqno:H1512160005
 * @date-designer:2015年12月16日-songmin
 * @date-author:2015年12月16日-songmin:CRE_信贷审批_进件管理_企业客户登记_查询
 * 实现营业员查询自己所登记过的企业客户信息列表功能及其他附属功能
 * 查询条件
 * @e-in-text:cusName-客户名称:（企业）客户名称
 * @e-in-text:status-状态:信贷审批流程状态
 * @e-in-text:idType-证件类型:（企业）证件类型
 * @e-in-text:idNum-证件号码:（企业）证件号码
 * 查询结果
 * @e-out-table:cusName-cusName:客户名称
 * @e-out-table:applyNo-applyNo:申请编号
 * @e-out-table:idType-idTypeLabel:证件类型
 * @e-out-table:idNum-idNum:证件号
 * @e-out-table:mobile-mobile:移动电话
 * @e-out-table:channelType-channelType:渠道
 * @e-out-table:channelSourceType-channelSourceTypeLabel:客户来源
 * @e-out-table:bizDate-bizDate:登记日期
 * @e-out-table:status-statusLabel:状态
 * 功能点分布
 * @e-ctrl:add-新增:新增企业客户信息
 * @e-ctrl:edit-修改:修改企业客户信息
 * @e-ctrl:delete-删除:（批量）删除企业客户信息
 * @e-ctrl:mcp_pic-流程图:查看当前流程流程图展示
 * @e-ctrl:mcp_change-流程轨迹:查看当前流程流程轨迹
 * @e-ctrl:btnSubmit-查询:分页查询当前用户所登记的企业客户信息
 * @e-ctrl:btnReset-重置:重置分页查询当前用户登记的企业客户信息查询条件
 -->
 <!-- 
 * @reqno:H1512160007
 * @date-designer:2015年12月23日-songmin
 * @date-author:2015年12月23日-songmin:titile的使用进行trim操作，IE9的bug
 *		JS中调用路径修复为调用企业处理路径
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
			$("#status").val('');
			page();//查询
		}

	    $(document).ready(function() {
	    	var tds = $(".title").filter("td");
			$.each(tds,function(){
				$(this).attr("title",$.trim($(this).text()));
			});
	    }); 
	    
		//删除
		function deleteData(){
			var str = getCheckedValue();
			if(str!=""){
				var url = "${ctx}/credit/customer/delete_c?id="+str;
				return confirmx('是否删除?',url);
			}
		}
		
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
						var url = "${ctx}/credit/customer/edit_c?id="+str;
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
					window.showModalDialog(url,null,"dialogWidth:1000px;dialogHeight:600px;status:no;help:no;resizable:yes;");
					}
				}
			}else{
				alertx("请选择对应的客户信息");
			}
		}
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
						window.showModalDialog(url, null, "dialogWidth:" + windowWidth + "px" + ";dialogHeight:" + windowHeight + "px" + ";status:no;help:no;resizable:yes;");	
					}
				}
		    }else{
				alertx("请选择对应的客户信息");
			}
		}
	</script>
 </head>

<body>
	 <ul class="nav nav-tabs">
	 	<li class="active"><a href="${ctx}/credit/customer/list_c">企业客户登记</a></li>
	 	<li class="line"></li>
	 	<li><a href="${ctx}/credit/customer/edit_c">企业客户登记新增</a></li>
	 </ul>
 	<div class="wrapper">
		<div class="searchInfo">
		    <h3 class="searchTitle">查询条件</h3>
		        <div class="searchCon">
				   	 <form:form id="searchForm" modelAttribute="customer"  action="${ctx}/credit/customer/list_c" method="post" >
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<div class="filter">
							<table class="searchTable">
								<tr>
									<td class="ft_label">客户名称：</td>
									<td class="ft_content" >
										<input id="cusName" name="cusName" type="text" maxlength="50" class="input-medium" value="${customer.cusName}"/>
									</td>
									<td class="ft_label">状态：</td>
									<td class="ft_content">
										<form:select  id="status" name="status"  path="status"  class="input-medium"  value="${customer.status}" cssStyle="width:176px;">
											<form:option value="" label = "  --全部--"></form:option>
											<form:options items="${fns:getDictList('APPLY_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
										</form:select>
									</td>
								</tr>
								<tr>
									<td class="ft_label">证件类型：</td>
									<td class="ft_content">
										<form:select  id="idType"  name="idType"  path="idType"  class="input-medium" cssStyle="width:176px;">
											<form:option value="" label = "  --全部--"/>
											<form:options items="${fns:getDictList('CUSTOMER_C_ID_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
										</form:select>
									</td>
									<td class="ft_label">证件号码：</td>
									<td class="ft_content">
										<input id="idNum" name="idNum" type="text" maxlength="50" class="input-medium" value="${customer.idNum}"/>
									</td>
								</tr>
						</table>
							<div class="searchButton">
								<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="formSubmit()"/>
								<input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="fromReset()"/>
							</div>
					</div>				
				</form:form>
			</div>
		</div>
		<div class="ribbon">
	    	<ul class="layout">
	    		<li class="add"><a id="add" href="${ctx}/credit/customer/edit_c" ><span><b></b>新增</span></a></li>
	        	<li class="edit"><a id="edit" href="#" onclick="editData();" ><span><b></b>修改</span></a></li>
	        	<li class="delete"><a id="delete" href="#" onclick="deleteData();"><span><b></b>删除</span></a></li>
	        	<li class="mcp_pic"><a href="#" id="processPicture" onclick="tracePhoto()"><span><b></b>流程图</span></a></li>
				<li class="mcp_change"><a href="#" id="processTrack" onclick="processTrack()"><span><b></b>流程轨迹</span></a></li>
	        </ul>
		</div>
		<sys:message content="${message}"/>
		<div class="tableList">
	    	<h3 class="tableTitle">数据列表</h3>
			 <div id="tableDataId" style="max-height:400px;overflow:auto;">
			    <table cellpadding="0" cellspacing="0" border="0" width="100%">
			        <tr>
						<th width="20px;"><input type="checkbox" onclick="allCheck();" name="all" id="all"></th>
						<th width="10%">客户名称</th>
						<th width="10%">申请编号</th>
						<th width="10%">证件类型</th>
						<th width="10%">证件号</th>
						<th width="10%">移动电话</th>
						<th width="7%">渠道</th>
						<th width="8%">客户来源</th>
						<th width="10%">登记日期</th>
						<th width="10%">状态</th>
						<th>操作</th>
					</tr>
		        	<c:forEach items="${page.list}" var="cus" varStatus="index">
					<c:if test="${0 == index.count%2}"><tr class="doubleRow"></c:if> 
					<c:if test="${1 == index.count%2}"><tr></c:if> 
						<td width="20px">
							<input type="checkbox" value="${cus.id}" name="type">
							<input type="hidden" value="${cus.procInsId }" name="cusProcInsId">
						</td>
						<td class="title">${cus.cusName}</td>
						<td class="title">${cus.applyNo}</td>
						<td class="title">${cus.idTypeLabel}</td>
						<td class="title">${cus.idNum}</td>
						<td class="title">${cus.linkManMobile}</td>
						<td class="title">${cus.channelTypeLabel}</td>
						<td class="title">${cus.channelSourceTypeLabel}</td>
						<td class="title"><fmt:formatDate value="${cus.bizDate}" type="both" pattern="yyyy-MM-dd"/></td>
						<td class="title">${cus.statusLabel}</td>
						<td>
	    					<a href="${ctx}/credit/customer/edit_c?id=${cus.id}">修改</a>
							<a href="${ctx}/credit/customer/delete_c?id=${cus.id}" onclick="return confirmx('确认要删除该客户信息吗？', this.href)">删除</a>
						</td>
					</tr>
					</c:forEach>
				</table>
			</div>
		<div class="pagination">${page}</div>
	</body>
</html>

 