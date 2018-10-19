<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<script type="text/javascript">
	function addGuarantorCompanyRelated(applyNo){
		openJBox('addGuarantorCompanyRelatedBox',"${ctx}/credit/guarantorCompanyRelated/form?applyNo="+applyNo,"新增企业关联企业信息",700,350);
	}
	function editGuarantorCompanyRelated(applyNo){
		var id = getCheckedIds('guarantorCompanyRelatedCheck');
    	if(id.length != 1){
    		alertx("请选择一条数据");
    	}else{
			openJBox('editGuarantorCompanyRelatedBox',"${ctx}/credit/guarantorCompanyRelated/form","编辑企业关联企业信息",700,350,{id:id,applyNo:applyNo});
    	}
	}
	function deleteGuarantorCompanyRelated(){
		var ids = getCheckedIds('guarantorCompanyRelatedCheck');
    	if(0 == ids.length){
    		alertx("请选择需要删除的数据！");
    	}else{
    		confirmx("是否删除该关联企业?",function(){
    			$.post("${ctx}/credit/guarantorCompanyRelated/delete?ids="+ids,null,function(data){
   		    		if(data.status == '1'){
   		    			alertx(data.message,function(){
	   		    			$.loadDiv('guarantorCompanyRelatedListDiv', '${ctx}/credit/guarantorCompanyRelated/list', {applyNo:$("#applyNo").val()}, 'post');
   		    			});
   		    		}else{
   		    			alertx(data.message);
   		    		}
   		    	});
    		});
    	}
	}
	function guaranD(){
	$("#guaraDId").toggle(600);
	}
	</script>
</head>
<body>
<div class="tableList">
	<div id="tableDataId">
		<sys:message content="${message}"/>
		<div class="searchInfo">
			<h3 onclick="guaranD()" class="searchTitle">担保企业关联企业信息列表</h3>
			<div id="guaraDId" class="ribbon filter">
		    	<ul class="layout">
		    		<li class="add"><a href="javaScript:void(0)" onclick="addGuarantorCompanyRelated('${applyNo}')"><span><b></b>新增</span></a></li>
		        	<li class="edit"><a href="javaScript:void(0)" onclick="editGuarantorCompanyRelated('${applyNo}')" ><span><b></b>编辑</span></a></li>
		        	<li class="delete"><a href="javaScript:void(0)" onclick="deleteGuarantorCompanyRelated();"><span><b></b>删除</span></a></li>
		        </ul>
				<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
					<thead>
						<tr>
							<th width="2%"><input type="checkbox" onclick="allCheck('allGuarantorCompanyRelated','guarantorCompanyRelatedCheck');" name="allGuarantorCompanyRelated"></th>
							<th width="2%">序号</th>
							<th width="20%">担保业名称</th>
							<th width="10%">关联企业名称</th>
							<th width="20%">组织机构代码</th>
							<th width="20%">与担保企业关系</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${companyRelatedList}" var="companyRelated" varStatus="i">
						<tr>
							<td><input type="checkbox" value="${companyRelated.id}" name="guarantorCompanyRelatedCheck"></td>
							<td class="title" title="${i.index+1}">
								${i.index+1}
							</td>
							<td id="relatedCompanyName" class="title" title="${companyRelated.companyInfo.busiRegName}">
								${companyRelated.companyInfo.busiRegName}
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
</div>
</body>
</html>