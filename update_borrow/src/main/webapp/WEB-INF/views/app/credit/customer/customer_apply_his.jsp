<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!-- 
 * @reqno:AH1509140009
 * @date-designer:2015年9月28日-songmin
 * @date-author:2015年9月28日-songmin:CRE_信贷审批_贷款初审_内匹配信息
 * 						1 面初审环节，点击“内匹配信息”，进入内匹配信息页面
 * 						2 页面显示“客户历史申请信息列表”
 * 						3 列表数据项：申请编号、客户名称、证件类型、证件号、申请金额(元)、登记日期、登记门店、状态；
 * 						4 列表排序：登记日期升序
 * @e-out-table:-申请编号:申请编号
 * @e-out-table:-客户名称:客户名称
 * @e-out-table:-申请编号:申请编号
 * @e-out-table:-证件类型:证件类型-关联字典表
 * @e-out-table:-证件号:证件号
 * @e-out-table:-申请金额(元):申请金额(元)
 * @e-out-table:-登记日期:登记日期-格式：yyyy-MM-dd
 * @e-out-table:-登记门店:登记门店
 * @e-out-table:-状态:状态
 -->
 <!--
@reqno:H1510210081
@date-designer:2015年11月06日-songmin
@date-author:2015年11月06日-songmin:增量代码1106
-->
<!-- 
* @reqno:H1510290044
* @date-designer:2015年10月29日-songmin
* @date-author:2015年10月29日-songmin:去掉内匹配信息的表格高度控制
-->

<!-- 
* @reqno:H1511190029
* @date-designer:2015年11月06日-songmin
* @date-author:2015年11月06日-songmin:调整页面表格的宽度
 -->
 <!--  
@reqno:H1511230004
@date-designer:20151125-songmin
@date-author:20151125-songmin:低分辨率下页面样式更改
-->
<html>
  <head>
	<title>CRE_信贷审批_贷款初审_内匹配信息</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
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
		function check(applyId) {
			var windowWidth = window.parent.window.document.body.offsetWidth -50;
			window.showModalDialog("${ctx}/credit/loanApply/checkExistsradoy?applyId="+applyId+"&title=khxq&status=1", window, "dialogWidth:" + windowWidth + "px" + ";dialogHeight:" + 700 + "px" + ";status:no;help:no;resizable:yes;");
		}
		//页面数据列表高度设置
	     $(document).ready(function() {
	    	/* var windowHeight = window.document.body.offsetHeight;
	    	var x = (windowHeight - 360) + "px"; */
			
	    	 var tds = $(".title").filter("td");
			$.each(tds,function(){
				$(this).attr("title",$(this).html());
			});
	    }); 
	    
		</script>
  </head>

<body>
 	<div class="wrapper" style="padding: 0px;">
		<div class="tableList">
	    	<h3 class="tableTitle">客户历史申请信息列表</h3>
	    	<!-- <table cellpadding="0" cellspacing="0" border="0" width="100%">
	        	<tr>
	        		<th width="130px;">申请编号</th>
	        		<th width="130px;">客户名称</th>
	        		<th width="150px;">证件类型</th>
	        		<th width="150px;">证件号</th>
	        		<th width="150px;">申请金额(元)</th>
	        		<th width="100px;">登记日期</th>
	        		<th width="150px;">登记门店</th>
	        		<th width="100px;">状态</th>
	        		<th >操作</th>
	        	</tr>
	        </table> -->
	        <div id="tableDataId" style="overflow:auto;">
		    	<table cellpadding="0" cellspacing="0" border="0" width="100%" >
		        	<tr>
		        		<th width="10%">申请编号</th>
		        		<th width="10%">客户名称</th>
		        		<th width="10%">证件类型</th>
		        		<th width="10%">证件号</th>
		        		<th width="10%">申请金额(元)</th>
		        		<th width="10%">登记日期</th>
		        		<th width="10%">登记门店</th>
		        		<th width="10%">状态</th>
		        		<th >操作</th>
		        	</tr>
		        	
		        	<c:forEach items="${customerlist}" var="customer" varStatus="index">
						<c:if test="${0 == index.count%2}"><tr class="doubleRow"></c:if> 
						<c:if test="${1 == index.count%2}"><tr></c:if> 
							<td class="title" >${customer.applyNo}</td>
							<td class="title" >${customer.cusName}</td>
							<td class="title" >${customer.idType}</td>
							<td class="title" >${customer.idNum}</td>
							<td class="title"  style="text-align: right;">
								<fmt:formatNumber value="${customer.applyAmount}" pattern="###,##0.00"></fmt:formatNumber>
							</td>
							<td class="title">
								<fmt:formatDate value="${customer.bizDate}" pattern="yyyy-MM-dd"/>
							</td>
							<td class="title">${customer.orgName}</td>
							<td class="title">${customer.status}</td>
<!--
	**
	 * @reqno: H1511100159
	 * @date-designer:20151113-lirongchao
	 * @e-ctrl : checkkh-客户详情
	 * @e-in-other	: id
	 * @date-author:20151113-lirongchao: 1.CRE_信贷审批_初审_内匹配信息列表中，在“状态”列后面，添加操作列，内容为“客户详情”链接，点击时，弹出窗口，查看客户贷款申请信息、客户信息；
2.点击列表操作中的“客户详情”，弹出窗口,窗口名称“客户详情”；
3.页面布局：以tab页签形式显示，包括：贷款申请信息、客户信息；默认只加载“贷款申请信息”页签，其它页签只有在点击时才做加载；
两页面显示的元素与信贷审批系统中申请录入保持不变；页面所有的表单均为只读，保存、新增、删除、修改按钮隐藏不显示；
	 当前环节-初审内匹配信息添加客户详情连接
-->							
							<td><a href="#" id="checkkh" onclick="check('${customer.id}')">客户详情</a></td>
						</tr>
					</c:forEach>
		        </table>
	        </div>
			<div class="pagination">${page}</div>
		</div>
	</div>
</body>
</html>

 