<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<script type="text/javascript">
	$(document).ready(function(){
		var isReady='${hideInHtmq}';
		if(isReady=='utask_htmq'||isReady=='utask_htyy'||isReady=='utask_cwfk'){
			$("#hideInHTMQ").hide();
		}
	});
	
	function addPurchaseInfo(){
		openJBox('addCompanyRelatedBox',"${ctx}/credit/purchaseInfo/form","新增采购商品信息",1000,550,{applyNo:'${applyNo}'});
	}
	
	function editPurchaseInfo(){
		var id = getCheckedIds('purchaseInfoCheck');
    	if(id.length != 1){
    		alertx("请选择一条数据");
    	}else{
			openJBox('editCompanyRelatedBox',"${ctx}/credit/purchaseInfo/form","编辑采购商品信息",1000,550,{id:id,applyNo:'${applyNo}'});
    	}
	}
	
	function deletePurchaseInfo(){
		var ids = getCheckedIds('purchaseInfoCheck');
    	if(0 == ids.length){
    		alertx("请选择需要删除的数据！");
    	}else{
    		confirmx("是否删除该采购商品信息?",function(){
    			$.post("${ctx}/credit/purchaseInfo/delete?ids="+ids,null,function(data){
   		    		if(data.status == '1'){
   		    			alertx(data.message,function(){
	   		    			$.loadDiv('purchaseInfoListDiv', '${ctx}/credit/purchaseInfo/list', {applyNo:'${applyNo}'}, 'post');
   		    			});
   		    		}else{
   		    			alertx(data.message);
   		    		}
   		    	});
    		});
    	}
	}
	</script>
</head>
<body>
	<div class="tableList">
		<div id="comRelateId" class="ribbon filter">
			<div id="tableDataId1">
				<ul class="layout" id="hideInHTMQ">
		    		<li class="add"><a href="#" onclick="addPurchaseInfo()"><span><b></b>新增</span></a></li>
		        	<li class="edit"><a href="#" onclick="editPurchaseInfo();" ><span><b></b>编辑</span></a></li>
		        	<li class="delete"><a href="#" onclick="deletePurchaseInfo();"><span><b></b>删除</span></a></li>
		        </ul>
				<table id="purchaseInfoTable" width="100%" class="table table-striped table-bordered table-condensed table-hover">
					<thead>
						<tr>
							<th width="2%"><input type="checkbox" onclick="allCheck('allPurchaseInfo','purchaseInfoCheck');" name="allPurchaseInfo"></th>
							<th width="10%">采购商品名称</th>
							<th width="10%">商品规格</th>
							<th width="20%">采购商品数量</th>
							<th width="20%">采购商品单位</th>
							<th width="20%">采购商品单位原价格(元)</th>
							<th width="20%">采购商品优惠后价格(元)</th>
							<th width="20%">货物利差(元)</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${purchaseInfoList}" var="purchaseInfo" varStatus="i">
						<tr>
							<td><input type="checkbox" value="${purchaseInfo.id}" name="purchaseInfoCheck"></td>
							<td id="commodityName" class="title" title="${purchaseInfo.commodityName}">
								${purchaseInfo.commodityName}
							</td>
							<td id="commodityFormat" class="title" title="${purchaseInfo.commodityFormat}">
								${purchaseInfo.commodityFormat}
							</td>
							<td id="commodityNum" class="title" title="${purchaseInfo.commodityNum}">
								${purchaseInfo.commodityNum}
							</td>
							
							<td id="commodityCompany" class="title" title="${purchaseInfo.commodityCompany}">
								${purchaseInfo.commodityCompany}
							</td>
							<td id="commodityPrePrice" class="title" title="${purchaseInfo.commodityPrePrice}">
								${purchaseInfo.commodityPrePrice}
							</td>
							<td id="commodityDiscountPrice" class="title" title="${purchaseInfo.commodityDiscountPrice}">
								${purchaseInfo.commodityDiscountPrice}
							</td>
							<td id="commodityMargin" class="title" title="${purchaseInfo.commodityMargin}">
								${purchaseInfo.commodityMargin}
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