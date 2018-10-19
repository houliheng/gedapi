<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!-- 
 * @reqno:H1511130067
 * @date-designer:20151116-chenshaojia
 * @date-author:20151116-chenshaojia:CRE_系统管理_产品管理_从业务使用方便考虑，需把【产品管理】功能，从账务系统移动到信贷审批系统，需求不做变动
 * 从jee_acc合并代码至jee_cre
-->
<!--
 @reqno:H1509130044
 @date-designer:20150921-songmin
 @date-author:20150921-songmin:ACC_系统设置_系统设置_产品管理_产品期限列表查询
-->
<div class="ribbon">
  	<ul class="layout">
  		<!--
		 @reqno:H1509130045
		 @date-designer:20150921-songmin
		 @date-author:20150921-songmin:产品ID需要加上单引号，否则火狐会出异常
		-->
      <li class="add"><a href="#" onclick="showPeriodAddWindow('${productPeriod.productId}')"><span><b></b>新增</span></a></li>
    </ul>
</div>
<div class="tableList">
   	<table cellpadding="0" cellspacing="0" border="0" width="100%">
       	<tr>
       		<th width="100px;">期限值</th>
       		<th width="100px;">期限值类型</th>
       		<th width="100px;">年利率（%）</th>
       		<th width="100px;">是否启用</th>
       		<th>备注</th>
       		<th width="70px;">操作</th>
       	</tr>
       </table>
       <div id="tableDataId" style="height:300px;">
    	<table cellpadding="0" cellspacing="0" border="0" width="100%">
        	<c:forEach items="${page.list}" var="proper" varStatus="index">
				<c:if test="${0 == index.count%2}"><tr class="doubleRow"></c:if> 
				<c:if test="${1 == index.count%2}"><tr></c:if> 
					<td width="100px;">${proper.periodValue}</td>
					<td width="100px;">${fns:getDictLabel(proper.periodType, 'PRODUCT_PERIOD_TYPE', '')}</td>
					<td width="100px;">${proper.yearRate}</td>
					<td width="100px;">${fns:getDictLabel(proper.delFlag, 'yes_no', '')}</td>
					<td title="${proper.remarks}">${proper.remarks}</td>
	            	<td width="70px;">
	                	<a href="#p_tab1" onclick="showPeriodEditWindow('${productPeriod.productId}','${proper.id}')">修改</a>
	                </td>
				</tr>
			</c:forEach>
        </table>
       </div>
       <script type="text/javascript">
       $(document).ready(function() {
	    	var windowHeight = top.window.document.body.offsetHeight;
	    	var x = (windowHeight - 635) + "px";
			document.getElementById("tableDataId").style.height = x;
	    });
       </script>
	<div class="pagination">${page}</div>
</div>


