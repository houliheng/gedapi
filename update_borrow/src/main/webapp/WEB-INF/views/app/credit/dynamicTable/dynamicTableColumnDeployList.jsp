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
   <title>数据项配置查询</title>
    <meta name="decorator" content="default"/>
    <base target="_self"/>
    <!--  
	@reqno:H1510170010
	@date-designer:20151022-huangxuecheng
	@date-author:20151022-huangxuecheng:CRE_信贷审批_系统管理_表单数据项配置_数据项配置查询
	@e-ctrl:addNewColumn-新增:新增
	@e-ctrl:editColumn-修改:修改
	@e-ctrl:deleteColumn-删除:删除
	@e-ctrl:btnReset-关闭:关闭
	@e-out-table:tableDataId-数据表id:数据表id
    -->
	<script type="text/javascript">
		//页面自适应 
		$(document).ready(function(){
	    	/* var windowHeight = window.parent.window.document.body.offsetHeight;
	    	var x = (windowHeight - 400) + "px";
			document.getElementById("tableDataId").style.height = x; */
			var tds = $(".title").filter("td");
			$.each(tds,function(){
				$(this).attr("title",$(this).html());
			});
			resetTip();
	    });
	    function selectAll() {
			if ($('[name=all]:checkbox').attr("checked") == "checked") {
				$('[name=pcheck]:checkbox').attr("checked", true);
			} else {
				$('[name=pcheck]:checkbox').attr("checked", false);
			}
		}
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			if($("#pageNo")[0].value.length>10){
				top.$.jBox.tip('当前页码大小长度不能大于10位！');
				return true;
			}
			if($("#pageSize")[0].value.length>10){
				top.$.jBox.tip('每页条数大小的长度不能大于10位！');
				return true;
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
		//获取复选框的val
		function getCheckBoxVal(){
		 	var str = "";
			$("input[name=pcheck]:checkbox").each(function(){
			if($(this).attr("checked")){
			   str += $(this).val() + ",";
			}
		});
			return str;
		}
		/**
		 * @reqno:H1510170012
		 * @date-designer:20151022-huangxuecheng
		 * @date-author:20151022-huangxuecheng:点击修改出现的js方法
	 	 */		
		//修改
		function editDynamicTable(creDataGroupTableId,productTypeValue,showPositionValue,dataGroupValue){
			var url = "${ctx}/sys/dynamicTableColumn/dynamicTableColumnDeploy?creDataGroupTableId=" + creDataGroupTableId + "&productTypeValue=" + productTypeValue + "&showPositionValue=" + showPositionValue + "&dataGroupValue=" + dataGroupValue;
			var windowWidth = window.parent.window.document.body.offsetWidth -50;
		    var windowHeight = window.parent.window.document.body.offsetHeight - 50;
			//window.showModalDialog(url, null, "dialogWidth:" + windowWidth + "px" + ";dialogHeight:" + windowHeight + "px" + ";status:no;help:no;resizable:yes;");
			window.open(url,"数据项配置窗口","width=" + windowWidth + ",height=" + windowHeight);
		}
		/**
		 * @reqno:H1510170011
		 * @date-designer:20151022-huangxuecheng
		 * @date-author:20151022-huangxuecheng:点击新增出现的js方法
	 	 */
		//点击新增
		function addNewColumn(){
			var creDataGroupTableId = $("#creDataGroupTableId").val();  
			var productType = $("#productType").val();  
			var url = "${ctx}/sys/dynamicTableColumn/addTableColumn?creDataGroupTableId=" + creDataGroupTableId + "&productType=" + productType;
			var returnVal = window.showModalDialog(url,null,"dialogWidth:1000px;dialogHeight:400px;status:no;help:no;resizable:yes;");
			var finalUrl = "${ctx}/sys/dynamicTableColumn/dynamicTableColumnDeploy?productTypeValuePage=${productTypeValuePage }&showPositionValuePage=${showPositionValuePage }&dataGroupValuePage=${dataGroupValuePage }&returnVal=" + returnVal;
			$("#searchForm").attr("action",finalUrl).submit();
		}
		//重写confirmx方法，使其满足业务需求
		function confirmxx(mess, id, closed){
			top.$.jBox.confirm(mess,'系统提示',function(v,h,f){
				if(v=='ok'){
					$.ajax({
						type:"post",
						url:"${ctx}/sys/dynamicTableColumn/deleteTableColumn?ids=" + id,
						dataType:"json",
						success:function(data){
						var returnVal = "2";
						var finalUrl = "${ctx}/sys/dynamicTableColumn/dynamicTableColumnDeploy?productTypeValuePage=${productTypeValuePage }&showPositionValuePage=${showPositionValuePage }&dataGroupValuePage=${dataGroupValuePage }&returnVal=" + returnVal;
						$("#searchForm").attr("action",finalUrl).submit();
						},
						error:function(msg){
							alertx("未能删除，请查看后台信息");
						}
					});
				}
			},{buttonsFocus:1, closed:function(){
				if (typeof closed == 'function') {
					closed();
				}
			}});
			top.$('.jbox-body .jbox-icon').css('top','55px');
			return false;
		}
	    /**
		 * @reqno:H1510170012
		 * @date-designer:20151022-huangxuecheng
		 * @date-author:20151022-huangxuecheng:点击删除出现的js方法
	 	 */	
		//点击删除
/* 		function deleteColumn(id){
			var str = getCheckBoxVal();
			if(id!=""){
				if(window.confirm("确定要删除吗？")){
					$.ajax({
						type:"post",
						url:"${ctx}/sys/dynamicTableColumn/deleteTableColumn?ids=" + id,
						dataType:"json",
						success:function(data){
						var returnVal = "2";
						var finalUrl = "${ctx}/sys/dynamicTableColumn/dynamicTableColumnDeploy?productTypeValuePage=${productTypeValuePage }&showPositionValuePage=${showPositionValuePage }&dataGroupValuePage=${dataGroupValuePage }&returnVal=" + returnVal;
						$("#searchForm").attr("action",finalUrl).submit();
						},
						error:function(msg){
							alertx("未能删除，请查看后台信息");
						}
					});
					}
				}
				else if(str!=""){
					if(window.confirm("确定要删除吗？")){
					str = str.substring(0,str.length-1);
					$.ajax({
						type:"post",
						url:"${ctx}/sys/dynamicTableColumn/deleteTableColumn?ids=" + str,
						dataType:"json",
						success:function(data){
						var returnVal = "3";
						var finalUrl = "${ctx}/sys/dynamicTableColumn/dynamicTableColumnDeploy?productTypeValuePage=${productTypeValuePage }&showPositionValuePage=${showPositionValuePage }&dataGroupValuePage=${dataGroupValuePage }&returnVal=" + returnVal;
						$("#searchForm").attr("action",finalUrl).submit();
						},
						error:function(msg){
							alertx("未能删除，请查看后台信息");
						}
					});
				}
			}else{
				alertx("请选择对应的数据信息名称");
			}	
		} */
		function deleteColumn(id){
			var str = getCheckBoxVal();
			if(id!=""){
				confirmxx("确定删除选定记录",id);
			}
			else if(str!=""){
				str = str.substring(0,str.length-1);
				confirmxx("确定删除选定记录",str);
			}else{
				alertx("请选择对应的数据信息名称");
			}	
		} 
	/**
	 * @reqno:H1510170012
	 * @date-designer:20151022-huangxuecheng
	 * @date-author:20151022-huangxuecheng:点击修改出现的js方法
	 */
	function editColumn(id){
		var str = getCheckBoxVal();
		if(id!=""){
			var creDataGroupTableId = $("#creDataGroupTableId").val();  
			var productType = $("#productType").val();  
			var url = "${ctx}/sys/dynamicTableColumn/addTableColumn?creDataGroupTableId=" + creDataGroupTableId + "&productType=" + productType + "&id=" +id;
			var returnVal = window.showModalDialog(url,null,"dialogWidth:1000px;dialogHeight:400px;status:no;help:no;resizable:yes;");
			var finalUrl = "${ctx}/sys/dynamicTableColumn/dynamicTableColumnDeploy?productTypeValuePage=${productTypeValuePage }&showPositionValuePage=${showPositionValuePage }&dataGroupValuePage=${dataGroupValuePage }&returnVal=" + returnVal;
			$("#searchForm").attr("action",finalUrl).submit();
		}else if(str!=""){
			if(str.indexOf(",") != str.length-1){
				alertx("请不要选择多条数据项信息名称");
			}else{
				str = str.substring(0, str.length-1);
		    	var creDataGroupTableId = $("#creDataGroupTableId").val();  
				var productType = $("#productType").val();  
				var url = "${ctx}/sys/dynamicTableColumn/addTableColumn?creDataGroupTableId=" + creDataGroupTableId + "&productType=" + productType +"&id=" +str;
				var returnVal = window.showModalDialog(url,null,"dialogWidth:1000px;dialogHeight:400px;status:no;help:no;resizable:yes;");
				var finalUrl = "${ctx}/sys/dynamicTableColumn/dynamicTableColumnDeploy?productTypeValuePage=${productTypeValuePage }&showPositionValuePage=${showPositionValuePage }&dataGroupValuePage=${dataGroupValuePage }&returnVal=" + returnVal;
				$("#searchForm").attr("action",finalUrl).submit();
			}
		}else{
			alertx("请选择对应的数据项信息名称");
		}	
	}
	function thisClose(){
		window.close();
		window.returnValue = "3";
	}
	</script>
 </head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
	    	<h3 class="searchTitle">栏目信息</h3>
	        <div class="searchCon">
				<form:form id="searchForm" modelAttribute="addOne" action="${ctx}/sys/dynamicTableColumn/dynamicTableColumnDeploy?productType=${productType }&creDataGroupTableId=${creDataGroupTableId }&productTypeValuePage=${productTypeValuePage }&showPositionValuePage=${showPositionValuePage }&dataGroupValuePage=${dataGroupValuePage } " method="post" >
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<input id="productTypeValuePage" name="productTypeValuePage" type="hidden" value="${productTypeValuePage }"/>
					<input id="showPositionValuePage" name="showPositionValuePage" type="hidden" value="${showPositionValuePage }	"/>
					<input id="dataGroupValuePage" name="dataGroupValuePage" type="hidden" value="${dataGroupValuePage }"/>
					<input id="productType" name="productType" type="hidden" value="${productType }"/>
					<input id="creDataGroupTableId" name="creDataGroupTableId" type="hidden" value="${creDataGroupTableId }"/>
					<div class="filter">
						<table class="searchTable">
							<tr>
								<td class="ft_label">产品类型：</td>
								<td class="ft_content">
								 <c:if test="${productTypeValuePage != null}">
									${productTypeValuePage } 
								</c:if>
								<c:if test="${productTypeValuePage == null}">  
									${addOne.productTypeValue }
							 	</c:if> 
								</td>
								<td class="ft_label">栏目类型：</td>
								<td class="ft_content">
								<c:if test="${showPositionValuePage != null}">
									${showPositionValuePage }
								</c:if>
								<c:if test="${showPositionValuePage == null}">
									${addOne.showPositionValue }
								</c:if>
								</td>
								<td class="ft_label">栏目名称：</td>
								<td class="ft_content">
								<c:if test="${dataGroupValuePage != null}">
									${dataGroupValuePage }
								</c:if>
								<c:if test="${dataGroupValuePage == null}">
									${addOne.dataGroupValue } 
								</c:if>
								</td>
							</tr>
						</table>
					</div>
				</form:form>
			</div>
		</div>
		<sys:message content="${message}"/>
		<div class="ribbon">
			<ul class="layout">
				<li class="add"><a href="#" id="addNewColumn" onclick="addNewColumn()"><span><b></b>新增</span></a></li>
				<li class="edit"><a href="#" id="editColumn" onclick="editColumn('')"><span><b></b>修改</span></a></li>
				<li class="delete"><a href="#" id="deleteColumn" onclick="deleteColumn('')"><span><b></b>删除</span></a></li>
			</ul>
		</div>
		<div class="tableList">
		    <h3 class="tableTitle">数据列表</h3>
		    	<!-- <table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>	
						<th width="20px">
						<input type="checkbox" onclick="selectAll()" name="all" id="all" />
						</th>
						<th width="300px;">数据项列名</th>
						<th width="300px;">数据项名称</th>
						<th width="300px;">最大输入长度</th>
						<th width="300px;">是否必填项</th>
						<th width="300px;">序号</th>
						<th >操作</th>
					</tr>
				 </table> -->
	        <div id="tableDataId" style="height:400px;overflow:auto;">
		    	<table cellpadding="0" cellspacing="0" border="0" width="100%">
		        	<tr>	
						<th width="20px">
							<input type="checkbox" onclick="selectAll()" name="all" id="all" />
						</th>
						<th width="15%">数据项列名</th>
						<th width="15%">数据项名称</th>
						<th width="15%">最大输入长度</th>
						<th width="15%">是否必填项</th>
						<th width="15%">序号</th>
						<th >操作</th>
					</tr>
		        	
		        	<c:forEach items="${page.list}" var="creProFromColumn" varStatus="index">
						<c:if test="${0 == index.count%2}">
							<tr class="doubleRow">
						</c:if>
						<c:if test="${1 == index.count%2}">
							<tr>
						</c:if>
						<td width="20px">
						<input type="checkbox" value="${creProFromColumn.id}" name="pcheck">
						</td>
					    <!--  
						@reqno:H1511050008
						@date-designer:20151117-huangxuecheng
						@date-author:20151117-huangxuecheng:CRE_信贷审批_系统管理_表单数据项配置_数据项配置查询,当配置保存后会出现如果太长会挤兑表单的情况，增加break-all防止问题
					    -->
						<td class="title">${creProFromColumn.columnCode }</td>
						<td class="title">${creProFromColumn.columnName }</td>
						<td class="title">${creProFromColumn.columnLength }</td>
						<td class="title">${creProFromColumn.isRequiredValue }</td>
						<td class="title">${creProFromColumn.sort }</td>
						<td ><a href="javascript:editColumn('${creProFromColumn.id}')">修改</a>&nbsp;&nbsp;<a href="javascript:deleteColumn('${creProFromColumn.id}')">删除</a></td>
						</tr>
				</c:forEach>
			</table>
		</div>
		<div class="pagination">${page}</div>
		<div class="searchButton">
			<input id="btnReset" class="btn btn-primary" type="button" value="关闭" onclick="javascript:thisClose();"/>
		</div>
	</div>
</div>
</body>
</html>