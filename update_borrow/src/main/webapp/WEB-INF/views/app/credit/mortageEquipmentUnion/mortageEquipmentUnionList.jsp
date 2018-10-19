<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript">
	$(document).ready(function() {
	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
	
	function addEquip(){
		openJBox('addEquipBox',"${ctx}/credit/mortageEquipmentUnion/form","新增抵押设备信息",700,350,{applyNo:$("#applyNo").val(),approveId:'${approveId}'});
		
	}
	
	function editEquip(){
		var id = getCheckedIds('equipCheck');
		alert(id);
    	if(id.length != 1){
    		alertx("请选择一条数据");
    	}else{
			openJBox('editEquiprBox',"${ctx}/credit/mortageEquipmentUnion/form","编辑抵押设备信息",700,350,{id:id,applyNo:$("#applyNo").val()});
    	}
	}
	function deleteEquip(){
		var ids = getCheckedIds('equipCheck');
    	if(0 == ids.length){
    		alertx("请选择需要删除的数据！");
    	}else{
    		confirmx("是否删除该设备?",function(){
    			$.post("${ctx}/credit/mortageEquipmentUnion/delete?id="+ids,null,function(data){
    				console.log(data);
   		    		if(data.status == '1'){
   		    			alertx(data.message,function(){
	   		    			$.loadDiv('equip', '${ctx}/credit/gqgetComInfoUnion/mortEquip', {applyNo:$("#applyNo").val(),approveId:'${approveId}'},'post');
   		    			});
   		    		}else{
   		    			alertx(data.message);
   		    		}
   		    	});
    		});
    	}
	}
	function guaranC(){
		$("#equipTable").toggle(600);
	}
	
</script>
</head>
<body>
	<div class="wrapper">
		<sys:message content="${message}" />
		<div class="tableList">
			<h3 onclick="guaranC()" class="tableTitle">设备抵押</h3>
			<div id="equipTable"class="searchCon" >
				<div class="ribbon">
					<ul class="layout">
			    		<li class="add"><a href="#" onclick="addEquip()"><span><b></b>新增</span></a></li>
			        	<li class="edit"><a href="#" onclick="editEquip();" ><span><b></b>编辑</span></a></li>
			        	<li class="delete"><a href="#" onclick="deleteEquip();"><span><b></b>删除</span></a></li>
			        </ul>
				</div>
				<div id="tableDataId" style="max-height:300px;overflow:auto;">
					<table id="fromTable filter" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th width="20px;">
									<!-- <input type="checkbox" onclick="allCheck('equip','equipCheck');" name="house" id="house"> -->
								</th>
								<th width="20px">序号</th>
								<th>型号</th>
								<th>购买价格</th>
								<th>估值</th>
								<th>市值</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${mortEquipInfoList}" var="mortEquip" varStatus="i">
								<tr>
									<td width="20px">
										<input type="checkbox" value="${mortEquip.id}" name="equipCheck" onclick="selectSingle('equipCheck');">
									</td>
									<td id="num" class="title" title="序号">${i.index+1}</td>
									<td id="model" class="title" title="型号">${mortEquip.model}</td>
									<td id="buyingPrice" class="title" title="购买价格">${mortEquip.buyingPrice}</td>
									<td id="valueOfAssessment" class="title" title="估值">${mortEquip.valueOfAssessment}</td>
									<td id="maketValue" class="title" title="市值">${mortEquip.maketValue}</td>
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
