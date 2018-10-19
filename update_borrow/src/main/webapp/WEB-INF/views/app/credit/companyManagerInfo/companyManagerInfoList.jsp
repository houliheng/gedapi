<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<script type="text/javascript">
	function addManager(){
		if(checkIsNull($("#companyId").val())){
			alertx("请先保存借款企业信息！");
		}else{
			openJBox('addManagerBox',"${ctx}/credit/companyManagerInfo/form","新增企业高管信息",700,350,{companyId:$("#companyId").val(),applyNo:$("#applyNoForCoCust").val()});
		}
	}
	function editManager(){
		var id = getCheckedIds('managerCheck');
    	if(id.length != 1){
    		alertx("请选择一条数据");
    	}else{
			openJBox('editManagerBox',"${ctx}/credit/companyManagerInfo/form","编辑企业高管信息",700,350,{id:id,applyNo:'${applyNo}'});
    	}
	}
	function deleteManager(){
		var ids = getCheckedIds('managerCheck');
    	if(0 == ids.length){
    		alertx("请选择需要删除的数据！");
    	}else{
    		confirmx("是否删除该高管?",function(){
    			$.post("${ctx}/credit/companyManagerInfo/delete?ids="+ids,null,function(data){
   		    		if(data.status == '1'){
   		    			alertx(data.message,function(){
	   		    			$.loadDiv('companyManagerInfoList', '${ctx}/credit/companyManagerInfo/list', {companyId:$("#companyId").val(),applyNo:$("#applyNoForCoCust").val()}, 'post');
   		    			});
   		    		}else{
   		    			alertx(data.message);
   		    		}
   		    	});
    		});
    	}
	}
	function comMana(){
	$("#comManaId").toggle(600);
	}
	</script>
</head>
<body>
	<div class="tableList">
		<h3 onclick="comMana()"  class="tableTitle">借款人企业高管信息列表</h3>
		<div class="ribbon filter">
			<div id="comManaId">
	    	<ul class="layout">
	    		<li class="add"><a href="#" onclick="addManager()"><span><b></b>新增</span></a></li>
	        	<li class="edit"><a href="#" onclick="editManager();" ><span><b></b>编辑</span></a></li>
	        	<li class="delete"><a href="#" onclick="deleteManager();"><span><b></b>删除</span></a></li>
	        </ul>
				<table id="contentTable"  width="100%" class="table table-striped table-bordered table-condensed table-hover">
					<thead>
						<tr>
							<th width="2%">
								<input type="checkbox" onclick="allCheck('allManager','managerCheck');" name="allManager">
							</th>
							<th width="8%">序号</th>
							<th width="10%">姓名</th>
							<th width="10%">手机号</th>
							<th width="20%">职位名称</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${companyManagerInfoList}" var="companyManagerInfo" varStatus="i">
							<tr>
								<td>
									<input type="checkbox" value="${companyManagerInfo.id}" name="managerCheck">
								</td>
								<td class="title" title="${i.index+1}">${i.index+1}</td>
								<td id="managerName" class="title" title="${companyManagerInfo.managerName}">${companyManagerInfo.managerName}</td>
								<td id="mobileNum" class="title" title="${companyManagerInfo.mobileNum}">${companyManagerInfo.mobileNum}</td>
								<td id="postName" class="title" title="${companyManagerInfo.postName}">${fns:getDictLabel(companyManagerInfo.postName, 'POST_LEVEL', '')}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>