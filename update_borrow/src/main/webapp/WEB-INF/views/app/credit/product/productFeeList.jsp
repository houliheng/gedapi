<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!-- 
 * @reqno:H1511130067
 * @date-designer:20151116-chenshaojia
 * @date-author:20151116-chenshaojia:CRE_系统管理_产品管理_从业务使用方便考虑，需把【产品管理】功能，从账务系统移动到信贷审批系统，需求不做变动
 * 从jee_acc合并代码至jee_cre
-->
<!--
 @reqno:H1509130046
 @date-designer:20150921-songmin
 @date-author:20150921-songmin:ACC_系统设置_系统设置_产品管理_产品费用列表查询
			1.页面位置：在产品新增或修改页面，上面是产品信息维护，下面有2个TAB面签，分别显示产品期限列表和产品费用列表
			2.列表数据项：费用名称、费用类型名称、费率or费、是否启用
			3.列表排序：费用类型升序、费用名称升序
			4.操作按钮：新增、修改
 @e-out-table:ID编号-费用名称:费用名称
 @e-out-table:ID编号-费用类型名称:费用类型名称
 @e-out-table:ID编号-费率or费:费率or费
 @e-out-table:ID编号-是否启用:是否启用
 @e-ctrl:-新增:弹出产品费用“新增”窗口
 @e-ctrl:-修改:弹出产品费用“修改”窗口
-->
<!--
 @reqno:H1509130047
 @date-designer:20150921-songmin
 @date-author:20150921-songmin:为“添加”、“修改”功能附加URL地址信息
-->
<div class="ribbon">
  	<ul class="layout">
      <li class="add"><a href="#p_tab2" onclick="showFeeAddWindow('${productFee.productId}')"><span><b></b>新增</span></a></li>
    </ul>
</div>

<div class="tableList">
   	 <table cellpadding="0" cellspacing="0" border="0" width="100%">
       		<tr>
	       		<th width="150px;">费用名称</th>
	       		<th width="150px;">费用类型名称</th>
	       		<th width="150px;">费率or费</th>
	       		<th width="150px;">是否启用</th>
	       		<th width="">操作</th>
       		</tr>
       </table>
       <div id="tableDataId_2" style="height:300px;overflow:auto;">
    		<table cellpadding="0" cellspacing="0" border="0" width="100%" style="word-break:break-all;word-wrap:break-word;">
        		<c:forEach items="${page.list}" var="pfee" varStatus="index">
					<c:if test="${0 == index.count%2}"><tr class="doubleRow"></c:if> 
					<c:if test="${1 == index.count%2}"><tr></c:if> 
						<td width="150px;">${pfee.feeName}</td>
						<td width="150px;">${fns:getDictLabel(pfee.feeTag, 'FEE_TAG', '')}</td>
						<td width="150px;" style="text-align: right;">
							<c:if test="${1==pfee.feeTag}">
								<fmt:formatNumber value="${pfee.feeRate}" pattern="#,##0.00"></fmt:formatNumber>
							</c:if>
							<c:if test="${2==pfee.feeTag}">
								${pfee.feeRate} %
							</c:if>
						</td>
						<td width="150px;">${fns:getDictLabel(pfee.delFlag, 'yes_no', '')}</td>
		            	<td width="">
		                	<a href="#p_tab2" onclick="showFeeEditWindow('${productFee.productId}','${pfee.id}')">修改</a>
		                </td>
					</tr>
				</c:forEach>
        	</table>
        </div>
         <script type="text/javascript">
       $(document).ready(function() {
	    	var windowHeight = top.window.document.body.offsetHeight;
	    	var x = (windowHeight - 635) + "px";
			document.getElementById("tableDataId_2").style.height = x;
	    });
       </script>
	<div class="pagination">${page}</div>
</div>

