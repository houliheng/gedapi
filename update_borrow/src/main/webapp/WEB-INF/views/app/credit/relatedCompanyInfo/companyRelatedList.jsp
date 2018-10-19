<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<script type="text/javascript">
	function addCompanyRelated(){
		if(checkIsNull($("#companyId").val())){
			alertx("请先保存借款企业信息！");
		}else{
			openJBox('addCompanyRelatedBox',"${ctx}/credit/companyRelated/form","新增企业关联企业信息",700,350,{companyId:$("#companyId").val(),applyNo:$("#applyNoForCoCust").val()});
		}
	}
	function editCompanyRelated(){
		var id = getCheckedIds('companyRelatedCheck');
    	if(id.length != 1){
    		alertx("请选择一条数据");
    	}else{
			openJBox('editCompanyRelatedBox',"${ctx}/credit/companyRelated/form","编辑企业关联企业信息",700,350,{id:id,applyNo:'${applyNo}'});
    	}
	}
	function deleteCompanyRelated(){
		var ids = getCheckedIds('companyRelatedCheck');
    	if(0 == ids.length){
    		alertx("请选择需要删除的数据！");
    	}else{
    		confirmx("是否删除该关联企业?",function(){
    			$.post("${ctx}/credit/companyRelated/delete?ids="+ids,null,function(data){
   		    		if(data.status == '1'){
   		    			alertx(data.message,function(){
	   		    			$.loadDiv('companyRelatedListDiv', '${ctx}/credit/companyRelated/list', {companyId:$("#companyId").val(),applyNo:$("#applyNoForCoCust").val()}, 'post');
   		    			});
   		    		}else{
   		    			alertx(data.message);
   		    		}
   		    	});
    		});
    	}
	}
	function comRelateClick(){
	$("#comRelateId").toggle(600);
	}
	</script>
</head>
<body>
	<div class="tableList">
		<h3 onclick="comRelateClick()" class="tableTitle">借款人企业关联企业信息列表</h3>
		<div id="comRelateId" class="ribbon filter">
			<div id="tableDataId">
	    	<ul class="layout">
	    		<li class="add"><a href="#" onclick="addCompanyRelated()"><span><b></b>新增</span></a></li>
	        	<li class="edit"><a href="#" onclick="editCompanyRelated();" ><span><b></b>编辑</span></a></li>
	        	<li class="delete"><a href="#" onclick="deleteCompanyRelated();"><span><b></b>删除</span></a></li>
	        </ul>
			<table id="contentTable" width="100%" class="table table-striped table-bordered table-condensed table-hover">
				<thead>
					<tr>
						<th width="2%"><input type="checkbox" onclick="allCheck('allCompanyRelated','companyRelatedCheck');" name="allCompanyRelated"></th>
						<th width="8%">序号</th>
						<th width="10%">工商登记名称</th>
						<th width="10%">组织机构代码</th>
						<th width="20%">与借款人企业关系</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${companyRelatedList}" var="companyRelated" varStatus="i">
					<tr>
						<td><input type="checkbox" value="${companyRelated.id}" name="companyRelatedCheck"></td>
						<td class="title" title="${i.index+1}">
							${i.index+1}
						</td>
						<td id="relatedCompanyName" class="title" title="${companyRelated.relatedCompanyName}">
							${companyRelated.relatedCompanyName}
						</td>
						<td id="relatedCompanyOrg" class="title" title="${companyRelated.relatedCompanyOrg}">
							${companyRelated.relatedCompanyOrg}
						</td>
						<td id="relatedCompanyType" class="title" title="${companyRelated.relatedCompanyType}">
							${companyRelated.relatedCompanyType}
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			</div>
		</div>
	</div>
</body>
</html>