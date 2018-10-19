<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<script type="text/javascript">
	function addContact(){
		if(checkIsNull($("#relationId").val())){
			alertx("请先保存借款人信息！");
		}else{
			openJBox('addContactBox',"${ctx}/credit/contactInfo/form","新增联系人",900,450,{relationId:$("#relationId").val(),applyNo:'${applyNo}'});
		}
	}
	function editContact(readOnly){
		var id = getCheckedIds('type');
    	if(id.length != 1){
    		alertx("请选择一条数据");
    	}else{
			openJBox('editContactBox',"${ctx}/credit/contactInfo/form","编辑联系人",900,450,{id:id,readOnly:readOnly,applyNo:'${applyNo}'});
    	}
	}
	function deleteContact(){
		var ids = getCheckedIds('type');
    	if(0 == ids.length){
    		alertx("请选择需要删除的数据！");
    	}else{
    		confirmx("是否删除该联系人?",function(){
    			$.post("${ctx}/credit/contactInfo/delete?ids="+ids,null,function(data){
   		    		if(data.status == '1'){
   		    			alertx(data.message,function(){
	   		    			$.loadDiv('contactCustInfo', '${ctx}/credit/contactInfo', {relationId:$("#relationId").val()}, 'post');
   		    			});
   		    		}else{
   		    			alertx(data.message);
   		    		}
   		    	});
    		});
    	}
	}
	function showDetail(id){
		openJBox('contactBox',"${ctx}/credit/contactInfo/form","查看联系人",900,450,{id:id,readOnly:true});
	}
	/* function showDetail(id){
	    var width = $(document).width() - 100;
		openJBox('contactBox',"${ctx}/credit/contactInfo/form","查看联系人",width,300,{id:id,readOnly:true});
	} */
	function contactClick(){
	$("#contactId").toggle(600);
	}
	</script>
</head>
<body>
	<div class="tableList">
		<sys:message content="${message}"/>
		<div class="searchInfo">
			<h3 onclick="contactClick()" class="searchTitle">联系人信息</h3>
			<input type="hidden" id="applyNo" value="${applyNo}"  />
			<div id="contactId"  class="ribbon filter">
		    	<ul class="layout">
		    		<li class="add"><a id="addContact" href="#" onclick="addContact()"><span><b></b>新增</span></a></li>
		        	<li class="edit"><a id="editContact" href="#" onclick="editContact(false);" ><span><b></b>编辑</span></a></li>
		        	<li class="delete"><a id="deleteContact" href="#" onclick="deleteContact();"><span><b></b>删除</span></a></li>
		        </ul>
			
			<div id="contactDiv" style="max-height:400px;overflow:auto;">
			<table id="contentTable" class="table table-striped table-bordered table-condensed table-hover">
				<thead>
					<tr>
						<th width="3%"><input type="checkbox" onclick="allCheck('all','type');" name="all" id="all"></th>
						<th width="3%">序号</th>
						<th width="10%">姓名</th>
						<th width="10%">与借款人关系</th>
						<th width="10%">移动电话</th>
						<th width="10%">是否知晓本次借款</th>
						<th width="10%">住宅电话</th>
						<th width="20%">单位名称</th>
						<th width="5%">操作</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${contactList}" var="contactInfo" varStatus="i">
					<tr>
						<td>
							<input type="checkbox" value="${contactInfo.id}" name="type">
						</td>
						<td class="title" title="${i.index+1}">
							${i.index+1}
						</td>
						<td id="contactName" class="title" title="${contactInfo.contactName}">
							${contactInfo.contactName}
						</td>
						<td id="contactRelations" class="title">
							${fns:getDictLabel(contactInfo.contactRelations, 'CONTACT_RELATIONS', '')}
						</td>
						<td id="contactMobile" class="title" title="${contactInfo.contactMobile}">
							${contactInfo.contactMobile}
						</td>
						<td id="isKnow" class="title">
							${fns:getDictLabel(contactInfo.isKnow, 'yes_no', '')}
						</td>
						<td id="housePhoneNo" class="title" title="${contactInfo.housePhoneNo}">
							${contactInfo.housePhoneNo}
						</td>
						<td id="deptName" class="title" title="${contactInfo.deptName}">
							${contactInfo.deptName}
						</td>
						<td>
		    				<a href="javascript:void(0)" onclick="showDetail('${contactInfo.id}')">详情</a>
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

