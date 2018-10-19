<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<!--
@reqno:H1601150074
@date-designer:2016年1月22日-lihao02
@e-out-table : 人员列表 - 人员列表: 人员列表
@e-out-other : id - 用户ID : 用户ID
@e-out-other : count - 序号 : 序号
@e-out-other : name - 姓名 : 姓名
@e-out-other : officeId - 归属部门 : 归属部门
@e-out-other : companyId - 归属机构 : 归属机构 
@e-ctrl : bind - 绑定 : 绑定用户
@e-ctrl : btnclose - 关闭 : 关闭当前页面 
@date-author:2016年1月22日-lihao02:增加【信贷审批|系统管理|客户解绑定管理】
绑定客户选择客户页面
-->
  
<html>
<head>
	<title>绑定客户经理</title>
	<meta name="decorator" content="default1"/>
	<base target="_self">
	<script type="text/javascript">
	window.name = "self";
	$(document).ready(function() {
    	var windowHeight = top.window.document.body.offsetHeight;
    	var x = (windowHeight - 348) + "px";
    	var of = $("#tableDataId").offset();
		
    });
	
	function formSubmit(){
    	$("#searchForm").submit();
    	return false;
    }
	
	//重置
	function fromReset(){
		$("#name").val('');
		$("#companyId").val("");
		$("#companyName").val("");
    }
	
	function bind() {
		var che=$("[id='id']:checked");
		if(che.length!=1){
			alertx("请选择一个绑定人员!");
		}else {
			var sysuserid=che[0].value;	
			var urlsuffix = "?id=${id}&sysuserid="+sysuserid;
	 		$.ajax({
				url:"${ctx}/credit/custRemoveBind/bind"+urlsuffix,
				type:"POST",		
				success:function(data){ 
					resetTip();
					if("success"==data){
						showTip("绑定成功","success",1000,0);
						$("#rs_msg").html("<div id='messageBox' class='alert alert-success'><button data-dismiss='alert' class='close'>×</button>绑定成功！页面即将关闭...</div>");
						setTimeout(function(){
							 resetTip();
							 if(null==top.$.fn.jerichoTab || typeof(top.$.fn.jerichoTab)=="undefined"){
								window.dialogArguments.page();
								window.close();
							}else{
								top.$.fn.jerichoTab.closeCurrentTab({});
							} 
						},1500);
					}else{
						alertx("系统异常！");
					}
				}
			}); 
		}
	}
	</script>
</head>
<body>
	<div class="wrapper">
		<div class="ribbon">
	    	<ul class="layout">
	        	<li class="mcp_close"><a href="#" onclick="closeJBox()" id="btnclose"><span><b></b>关闭</span></a></li>
	        </ul>
	    </div>	
	    <div class="searchInfo">
		    <h3 class="searchTitle">查询条件</h3>
		        <div class="searchCon">
					<form:form id="searchForm" modelAttribute="cresysuser" action="${ctx}/credit/custRemoveBind/bindUser?userType=${userType}&id=${id}" method="post" class="breadcrumb form-search">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<div calss="filter">
							<table class="searchTable">
								<ul class="ul-form">
									<tr>
										<td class="ft_label">用户名：</td>
										<td class="ft_content">
											<input id="name" name="name" type="text" maxlength="50" class="input-medium required" value="${cresysuser.name}"/>
										</td>
										<c:if test="${'manage' == userType}">
											<td class="ft_label">所属机构：</td>
											<td class="ft_content">
												<sys:usertreeselect id="company" name="company.id" value="${cresysuser.company.id}" labelName="company.name" labelValue="${cresysuser.company.name}" 
													title="公司" url="/sys/office/treeData?type=1" cssClass="input-small required" allowClear="true"/>
											</td>
										</c:if>
									</tr>
								</ul>
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
	        	<li class="mcp_change"><a href="#" onclick="bind()" id="bind"><span><b></b>绑定</span></a></li>
	        </ul>
	    </div>
	    <div id="rs_msg"></div>		
		<div class="tableList">
		<!--
		@reqno:H1601150074
		@date-designer:2016年2月19日-yanwanmei
		@date-author:2016年2月19日-yanwanmei:将“人员列表”改为“待人员列表”
		-->
	    	<h3 class="infoListTitle">待选人员列表</h3>
	    	<div id="tableDataId" style="max-height:400px;overflow:auto;">
		    	<table cellpadding="0" cellspacing="0" border="0" width="100%">
		        	<tr>
		        		<th width="30px;"></th>
		        		<th width="50px;">序号</th>
		        		<th width="100px;">用户名</th>
		        		<th width="100px;">姓名</th>
		        		<th width="100px;">所属机构</th>
		        		<th width="100px;">所属部门</th>
		        	</tr>
			        <c:forEach items="${page.list}" var="bindUser" varStatus="index">
						<c:if test="${0 == index.count%2}"><tr class="doubleRow"></c:if> 
						<c:if test="${1 == index.count%2}"><tr></c:if> 
							<td width="30px;"><input type="radio" id="id" name="id" value="${bindUser.id}"/> </td>
							<td width="50px;" id="count">${index.count}</td>
							<td width="100px;" id="loginName">${bindUser.loginName}</td>
							<td width="100px;" id="name">${bindUser.name}</td>
							<td width="100px;" id="companyId">${bindUser.company.name}</td>
							<td width="100px;" id="officeId">${bindUser.office.name}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
	    </div>
		</div>
		<div class="pagination">${page}</div>
	</div>
</body>
</html>