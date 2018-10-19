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
   <title>动态数据项</title>
    <meta name="decorator" content="default"/>
    <!--  
	@reqno:H1510170009
	@date-designer:20151022-huangxuecheng
	@date-author:20151022-huangxuecheng:CRE_信贷审批_系统管理_表单数据项配置_查询
	@e-in-list:productType-产品名称:产品名称
	@e-in-list:showPosition-栏目类型:栏目类型
	@e-in-list:dataGroup-栏目名称:栏目名称
	@e-ctrl:btnSubmit-查询:查询
	@e-ctrl:btnReset-重置:重置
	@e-out-table:tableDataId-数据表id:数据表id
    -->
	<script type="text/javascript">
		//页面自适应 
		$(document).ready(function(){
	    	/* var windowHeight = window.parent.window.document.body.offsetHeight;
	    	var x = (windowHeight - 524) + "px";
			document.getElementById("tableDataId").style.height = x; */
			resetTip();
	    });
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
		//重置
		function formReset(){
				$("#productType").val('');
				$("#dataGroup").val('');
				$("#showPosition").val('');
				page();//查询
		}
		/**
		 * 复选框全选事件
		 */
		function allCheck(){
			if($('[name=all]:checkbox').attr("checked")=="checked"){
				$('[name=type]:checkbox').attr("checked",true);
			}else{
				$('[name=type]:checkbox').attr("checked",false);
			}
		}
		/**
		 * 获取复选框选中的值
		 * @returns
		 */
		function getCheckedValue(){
			var str = "";
			$("input[name=type]:checkbox").each(function(){
				if($(this).attr("checked")){
					 str += $(this).val() + ",";
				}
			});
			return str;
		}
		//提交
		function searchForm(){
			$("#searchForm").submit();
		}
		/**
	 	 * @reqno:H1510170010
	 	 * @date-designer:20151022-huangxuecheng
	     * @date-author:20151022-huangxuecheng:CRE_信贷审批_系统管理_表单数据项配置_数据项配置查询，点击配置执行的方法。
	     */
		//配置
		function editDynamicTable(creDataGroupTableId,productTypeValue,showPositionValue,dataGroupValue,productType){
			productTypeValue = encodeX(productTypeValue);
			showPositionValue = encodeX(showPositionValue);
			dataGroupValue = encodeX(dataGroupValue);
			var url = "${ctx}/sys/dynamicTableColumn/dynamicTableColumnDeploy?creDataGroupTableId=" + creDataGroupTableId + "&productTypeValue=" + productTypeValue + "&showPositionValue=" + showPositionValue + "&dataGroupValue=" + dataGroupValue + "&productType=" + productType;
			var windowWidth = window.parent.window.document.body.offsetWidth -150;
		    var windowHeight = window.parent.window.document.body.offsetHeight - 150;
			var resultVal = window.showModalDialog(url, null, "dialogWidth:" + windowWidth + "px" + ";dialogHeight:" + windowHeight + "px" + ";status:no;help:no;resizable:yes;");
		    //var resultVal = window.open(url,"数据项配置窗口","width=" + windowWidth + ",height=" + windowHeight);
		    if(resultVal == 3){
		    	searchForm();
		    }else if(typeof resultVal == 'undefined'){
		    	searchForm();
			}
		}
	</script>
 </head>

<body>
	<div class="wrapper">
		<div class="searchInfo">
	    	<h3 class="searchTitle">查询条件</h3>
	        <div class="searchCon">
				<form:form id="searchForm" modelAttribute="dynamicTableColumnVo" action="${ctx}/sys/dynamicTableColumn" method="post" >
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class="filter">
						<table class="searchTable">
							<tr>
								<td class="ft_label">产品类型：</td>
								<td class="">
									<form:select id="productType" name="productType" path="productType" class="input-medium" value="${dynamicTableColumnVo.productType }">
										<form:option value="" label="--请选择--" />
										<form:options items="${fns:getDictList('PRODUCT_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
								</td>
								<td class="ft_label">栏目名称：</td>
								<td class="">
									<form:select id="dataGroup" name="dataGroup" path="dataGroup" class="input-medium" value="${dynamicTableColumnVo.dataGroup }">
										<form:option value="" label="--请选择--" />
										<form:options items="${fns:getDictList('DATA_GROUP')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
								</td>
								<td class="ft_label">栏目类型：</td>
								<td class="">
									<form:select id="showPosition" name="showPosition" path="showPosition" class="input-medium" value="${dynamicTableColumnVo.showPosition }">
										<form:option value="" label="--请选择--" />
										<form:options items="${fns:getDictList('SHOW_POSITION')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
								</td>
							</tr>
						</table>
						<div class="searchButton"  style="margin-top: 2px;">
							<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="searchForm()"/>&nbsp;
							<input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="return formReset();"/>
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<sys:message content="${message}"/>
		<div class="tableList">
		    <h3 class="tableTitle">数据列表</h3>
		    	<!-- <table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<th width="300px;">产品类型</th>
						<th width="300px">栏目类型</th>
						<th width="300px;">栏目名称</th>
						<th width="300px;">数据项个数</th>
						<th >操作</th>
					</tr>
				 </table> -->
	        <div id="tableDataId" style="max-height:400px;overflow:auto;">
		    	<table cellpadding="0" cellspacing="0" border="0" width="100%">
		        	<tr>
						<th width="20%">产品类型</th>
						<th width="20%">栏目类型</th>
						<th width="20%">栏目名称</th>
						<th width="20%">数据项个数</th>
						<th >操作</th>
					</tr>
		        	<c:forEach items="${page.list}" var="dynamicTableColumnVo" varStatus="index">
						<c:if test="${0 == index.count%2}">
							<tr class="doubleRow">
						</c:if>
						<c:if test="${1 == index.count%2}">
							<tr>
						</c:if>
						<input type="hidden" id="id" name="id" value="${dynamicTableColumnVo.id }"/>
						<input type="hidden" id="id" name="id" value="${dynamicTableColumnVo.productType }"/>
						<td>${dynamicTableColumnVo.productTypeValue }</td>
						<td>${dynamicTableColumnVo.showPositionValue }</td>
						<td>${dynamicTableColumnVo.dataGroupValue }</td>
						<td>${dynamicTableColumnVo.columnCount }</td>
						<td><a href="javascript:editDynamicTable('${dynamicTableColumnVo.creDataGroupTableId }','${dynamicTableColumnVo.productTypeValue }','${dynamicTableColumnVo.showPositionValue }','${dynamicTableColumnVo.dataGroupValue }','${dynamicTableColumnVo.productType }')">配置</td>
						</tr>
				</c:forEach>
			</table>
		</div>
		<div class="pagination">${page}</div>
	</div>
</div>
</body>
</html>