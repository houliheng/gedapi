<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!-- 
 * @reqno:H1601220056
 * @date-designer:2016年1月25日-songmin
 * @date-author:2016年1月25日-songmin:CRE_信贷审批_客户信息管理_抵押权人信息管理
 * @e-in-text:mortgageeName-姓名:抵押权人姓名
 * @e-in-text:mobileNum-mobileNum:抵押权人联系电话
 * @e-in-text:sexNo-sexNo:性别
 * @e-in-text:cardId-cardId:身份证号 
 * @e-ctrl:btnSubmit-提交:查询抵押权人数据列表
 * @e-ctrl:btnReset-重置:重置抵押权人查询表单
 * @e-out-table:tableDataId-抵押权人数据列表:复选框、姓名、移动电话、性别、出生日期、身份证、资金平台账号
 -->
 <!-- 
 * @reqno:H1601220071
 * @date-designer:2016年1月27日-songmin
 * @date-author:2016年1月27日-songmin:CRE_信贷审批_客户信息管理_抵押权人信息管理_新增
 * 补全抵押权人新增功能链接
 -->
<!-- 
* @reqno:H1601250005
* @date-designer:2016年1月28日-songmin
* @date-author:2016年1月28日-songmin:CRE_信贷审批_客户信息管理_抵押权人信息管理7788_修改、删除
* @e-ctrl:edit-修改:修改抵押权人信息
* @e-ctrl:delete-删除:删除（批量）抵押权人信息
 -->
<html>
  <head>
	<title>抵押权人列表</title>
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
		//提交
	    function formSubmit(){
	    	loading();
	    	$("#searchForm").submit();
	    }
	    //重置
	    function formReset(){
	    	//$("#searchForm").get(0).reset();
	    	$("#mortgageeName").val("");
	    	$("#mobileNum").val("");
	    	$("#idNum").val("");
	    	$("#sexNo").select2("val","");
	    }
	    //新增
	    function add(){
	    	var width = $(top.document).width()-300;
	    	width = Math.max(width,1000);
	    	var url = "${ctx}/credit/mortgagedperson/form";
	    	openJBox("mortgagedperson-form",url,"新增抵押权人信息", width,$(top.document).height()-200);
	    }
	    //修改
	    function edit(){
	    	var width = $(top.document).width()-300;
	    	width = Math.max(width,1000);
	    	var $checkLine =  $("input[name='type']:checked");
	    	var $len = $checkLine.length;
	    	if($len!=1){
	    		alertx("请选择一条数据");
	    	}else{
	    		var url = "${ctx}/credit/mortgagedperson/form?id="+$checkLine.val();
				$.jBox.open("iframe:"+url,"修改抵押权人信息",width,$(top.document).height()-200,{
					id:"mortgagedperson-form",
					persistent:true,
					buttons:{},
					loaded:function(h){
						$(".jbox-content", top.document).css("overflow-y","hidden");
					}
				});
	    	}
	    }
	    //删除
	    function del(){
	    	var $checkLine =  $("input[name='type']:checked");
	    	if(0==$checkLine.length){
	    		alertx("请选择需要删除的数据！");
	    	}else{
	    		confirmx("是否删除?",delOper);
	    	}
	    }
	    
	    function delOper(){
	    	var $checkLine =  $("input[name='type']:checked");
	    	if(null!=$checkLine && $checkLine.length>0){
	    		var ids = "";
	    		$checkLine.each(function(v){
	    			ids+=(this.value+",");
	    		});
	    		$.post("${ctx}/credit/mortgagedperson/delete",{"ids":ids},function(data){
		    		if("success"==data){
		    			alertx("删除成功！");
		    			setTimeout(function(){
		    				formSubmit();
		    			}, 1200);
		    		}
		    	});
	    	}
	    }

	    $(document).ready(function() {
	    	var tds = $(".title").filter("td");
			$.each(tds,function(){
				$(this).attr("title",$.trim($(this).text()));
			});
	    }); 
	</script>
 </head>

<body>
 	<div class="wrapper">
		<div class="searchInfo">
		    <h3 class="searchTitle">查询条件</h3>
		        <div class="searchCon">
				   	<form:form id="searchForm" modelAttribute="mortgagedPerson" 
				   		action="${ctx}/credit/mortgagedperson/" method="post" class="breadcrumb form-search">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<div class="filter">
							<table class="searchTable">
								<tr>
									<td class="ft_label">姓名：</td>
									<td class="ft_content">
										<form:input path="mortgageeName" htmlEscape="false" maxlength="15" class="input-medium"/>
									</td>
									<td class="ft_label">移动电话：</td>
									<td class="ft_content">
										<form:input path="mobileNum" htmlEscape="false" maxlength="11" class="input-medium"/>
									</td>
								</tr>
								<tr>
									<td class="ft_label">性别：</td>
									<td class="ft_content">
										<form:select path="sexNo" class="input-medium" cssStyle="width:176px;">
											<form:option value="" label="全部"/>
											<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
										</form:select>
									</td>
									<td class="ft_label">身份证：</td>
									<td class="ft_content">
										<form:input path="idNum" htmlEscape="false" maxlength="18" class="input-medium"/>
									</td>
								</tr>
						</table>
							<div class="searchButton">
								<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="formSubmit()"/>
								<input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="formReset()"/>
							</div>
					</div>				
				</form:form>
			</div>
		</div>
		<div class="ribbon">
	    	<ul class="layout">
	    		<li class="add">
	    			<a href="#" onclick="add();"><span><b></b>新增</span></a>
	    		</li>
	        	<li class="edit"><a id="edit" href="javascript:void(0)" onclick="edit();" ><span><b></b>修改</span></a></li>
	        	<li class="delete"><a id="delete" href="javascript:void(0)" onclick="del();"><span><b></b>删除</span></a></li>
	        </ul>
		</div>
		<sys:message content="${message}"/>
		<div class="tableList">
	    	<h3 class="tableTitle">数据列表</h3>
			 <div id="tableDataId" style="max-height:400px;overflow:auto;">
			    <table cellpadding="0" cellspacing="0" border="0" width="100%">
			        <tr>
						<th width="20px"><input type="checkbox" onclick="allCheck();" name="all" id="all"></th>
						<th width="15%">姓名</th>
						<th width="15%">移动电话</th>
						<th width="15%">性别</th>
						<th width="15%">出生日期</th>
						<th width="15%">身份证</th>
						<th width="">资金平台账号</th>
					</tr>
		        	<c:forEach items="${page.list}" var="vars" varStatus="index">
					<c:if test="${0 == index.count%2}"><tr class="doubleRow"></c:if> 
					<c:if test="${1 == index.count%2}"><tr></c:if> 
						<td width="20px">
							<input type="checkbox" value="${vars.id}" name="type">
						</td>
						<td class="title">${vars.mortgageeName}</td>
						<td class="title">${vars.mobileNum}</td>
						<td class="title">${vars.sexNoLabel}</td>
						<td class="title">
							<fmt:formatDate value="${vars.birthDay}" pattern="yyyy-MM-dd"/>
						</td>
						<td class="title">${vars.idNum}</td>
						<td class="title">${vars.capitalTerraceNo}</td>
					</tr>
					</c:forEach>
				</table>
			</div>
		<div class="pagination">${page}</div>
	</body>
</html>

 