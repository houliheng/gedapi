<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>产品添加</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		adjustTextareaLength('remarks', 'pre');
		$("#inputForm").validate({
		rules : {
			// 				自定义产品编码后台校验方法
			productCode : {
				remote : "${ctx}/credit/product/validate?json=" + $("#productCode").val()
			}
		},
		messages : {
			productCode : {
				remote : "产品编码已存在"
			}
		},
		submitHandler : saveForm,
		errorContainer : "#messageBox",
		//所属机构的必填信息提示要直接显示在查询按钮后，此处特进行修正
		errorPlacement : function(error, element) {
			checkReq(error, element);
		}
		});
	});

	//修改 下拉框的“必填信息”提示，在选中后仍无法消失的问题
	function validateProductTypeCode() {
		if ("" != $("#productTypeCode").find("option:checked").val()) {
			$("label[for='productTypeCode']").hide();
		}
		if ("" != $("#deductDateType").find("option:checked").val()) {
			$("label[for='deductDateType']").hide();
		}
	}

	function companyTreeselectCallBack(v) {
		if ("" != $("#companyId").val()) {
			$("label[for='companyproductName']").hide();
		}
	}

	function saveForm() {
		top.$.jBox.tip.mess = null;
		var formJson = $("#inputForm").serializeJson();
		$.post("${ctx}/credit/product/save", formJson, function(data) {
			if (data) {
				if (data.status == 1) {//保存成功
					alertx(data.message, function() {
					    parent.location.reload();
						closeJBox();
					});
				} else {
					alertx(data.message);
				}
			}
		});
	}
</script>
</head>
<body>
	<sys:message content="${message}" />
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">产品信息</h3>
			<div class="searchCon">
				<form:form id="inputForm" modelAttribute="product" action="${ctx}/credit/product/save" method="post">
					<input id="id" name="id" type="hidden" value="${product.id}" />
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<div class="filter">
						<table class="fromTable">
							<tr>
								<td class="ft_label">产品编码：</td>
								<td class="ft_content">
									<c:choose>
										<c:when test="${not empty product.id}">
											<input id="productCode1" name="productCode1" type="text" class="input-medium" value="${product.productCode}" readonly="readonly" />
										</c:when>
										<c:when test="${empty product.id}">
											<form:input path="productCode" name="productCode" type="text" maxlength="15" minlength="3" class="input-medium required" value="${product.productCode}" />
										</c:when>
									</c:choose>
									<font color="red">*</font>
								</td>
								<td class="ft_label">产品名称：</td>
								<td class="ft_content">
									<form:input path="productName" htmlEscape="false" maxlength="50" readonly="readonly" cssClass="input-medium required" />
									<font color="red">*</font>
								</td>
								<td class="ft_label">产品类型：</td>
								<td class="ft_content">
									<form:select id="productTypeCode" name="productTypeCode" path="productTypeCode" cssClass="required" value="${product.productTypeCode}" onchange="validateProductTypeCode();" cssStyle="width:176px;">
										<form:option value="" label="" />
										<form:options items="${fns:getDictList('PRODUCT_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
									<font color="red">*</font>
								</td>
							</tr>
							<tr>
								<td class="ft_label">扣款日类型：</td>
								<td class="ft_content">
									<form:select id="deductDateType" name="deductDateType" path="deductDateType" cssClass="required" value="${product.deductDateType}" style="width:176px" onchange="validateProductTypeCode();">
										<form:option value="" label="" />
										<form:options items="${fns:getDictList('DEDUCT_DATE_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
									<font color="red">*</font>
								</td>
								<td class="ft_label">所属机构：</td>
								<td class="ft_content">
									<input id="oldOrgId" type="hidden" value="${product.company.id}" onchange="companyTreeselectCallBack();" />
									<sys:usertreeselect id="company" name="company.id" value="${product.company.id}" labelName="company.name" labelValue="${product.orgName}" title="公司" url="/sys/office/treeData?type=1" cssClass="required" dataMsgRequired="必填信息" cssStyle="width:115px" />
									<font color="red">*</font>
								</td>
								<td class="ft_label">产品是否停用：</td>
								<td class="ft_content">
									<form:select id="delFlag" name="delFlag" path="delFlag" cssClass="required" value="${product.delFlag}" cssStyle="width:176px;">
										<form:option value="" label="" />
										<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
								</td>
							</tr>
							<tr>
								<td class="ft_label">关联流程：</td>
								<td class="ft_content" colspan="3">
									<form:select id="procDefKey" name="procDefKey" path="procDefKey" cssClass="required" value="" style="width:176px" onchange="synProName();">
										<form:option value="" label="" />
										<form:options items="${processList}" itemLabel="name" itemValue="key" htmlEscape="false" />
									</form:select>
									<input type="hidden" name="procName" id="procName" value="${product.procName}" />
									<font color="red">*</font>
									<script type="text/javascript">
										/**
										 * 选择关联的流程后，将流程的名称同步到流程名称隐藏域中
										 */
										function synProName() {
											$("#procName").val($("#procDefKey option:selected").html());
										}
									</script>
								</td>
							</tr>
							<tr>
								<td class="ft_label">产品描述：</td>
								<td class="ft_content" colspan="5">
									<pre class="textarea-width pre-style" id="pre"></pre>
									<form:textarea path="remarks"  htmlEscape="false"  rows="3" maxlength="1000"  class="textarea-width textarea-style" onkeyup="adjustTextareaLength('remarks', 'pre')" />
								</td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSave" class="btn btn-primary" type="submit" value="保存" />
							&nbsp;
							<input id="btnClose" class="btn btn-primary" type="button" value="关闭" onclick="closeJBox()" />
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<!--
		 @reqno:H1509130044
		 @date-designer:20150921-songmin
		 @date-author:20150921-songmin:ACC_系统设置_系统设置_产品管理_产品期限列表查询
		-->
		<!--
		 @reqno:H1509130045
		 @date-designer:20150921-songmin
		 @date-author:20150921-songmin:ACC_系统设置_系统设置_产品管理_产品期限_新增、修改
		-->
		<!--
		 @reqno:H1509130046
		 @date-designer:20150921-songmin
		 @date-author:20150921-songmin:ACC_系统设置_系统设置_产品管理_产品费用列表查询
		-->
		<!--
		 @reqno:H1509130047
		 @date-designer:20150921-songmin
		 @date-author:20150921-songmin:ACC_系统设置_系统设置_产品管理_产品费用_新增、修改
		-->
		<!--产品费用列表查询 开始 -->
		<%-- <div>
			<ul class="nav nav-tabs" id="p_maintab">
				<li class="active"><a href="#p_tab1" onclick="showTab('p_tab1','${ctx}/credit/product/period/list?productId=${product.id}');return false;">产品期限列表</a></li>
<!-- 				<li class="line"></li>  -->
				<li><a href="#p_tab2"  onclick="showTab('p_tab2','${ctx}/credit/product/fee/list?productId=${product.id}');return false;">产品费用列表</a></li>
			</ul>
			<script type="text/javascript">
				$(document).ready(function(){
					//初始化最初展示的Tab页面
					var loc = window.location.toString();
					if(-1!=loc.indexOf("p_tab2")){
						showTab("p_tab2","${ctx}/credit/product/fee/list?productId=${product.id}");
					}else{
						showTab("p_tab1","${ctx}/credit/product/period/list?productId=${product.id}");
					}
				});
				function showTab(tabId,url,force){
					$('#p_maintab a[href="#'+tabId+'"]').tab('show');
					if($("#"+tabId).html().length<10 || force){
						//iframe还需要做加载时重置边框高度操作，所以直接用ajax的方式进行load,将页面直接追加到该页面
						//$("#"+tabId).html("<iframe id='frame_"+url+"' src='"+url+"' width='100%' productName=''height='91%' frameborder='0'></iframe>");
						$("#"+tabId).load(url);
					}
					return false;
				}
			</script>
			<div class="tab-content">
				<div class="tab-pane active" id="p_tab1"></div>
				<div class="tab-pane" id="p_tab2"></div>
			</div>
		</div> --%>
		<!-- 
		 * @reqno:H1511130067
		 * @date-designer:20151118-chenshaojia
		 * @date-author:20151118-chenshaojia: 修复ACC系统中controller 命名错误导致的 产品期限列表加载不出问题 将peroid 改为 period
		-->
		<!-- <script type="text/javascript">
			//产品期限列表分页				
			function pagePeroid(n,s){
				showTab("p_tab1","${ctx}/credit/product/period/list?productId=${product.id}&pageNo="+n+"&pageSize="+s,true);
	        	return false;
	        }
			//产品费用列表分页
			function pageFee(n,s){
				showTab("p_tab2","${ctx}/credit/product/fee/list?productId=${product.id}&pageNo="+n+"&pageSize="+s,true);
	        	return false;
	        }
			//弹出产品期限添加窗口
			function showPeriodAddWindow(productId){
			    if(null==productId||''==productId){
					alertx("产品信息尚未保存，请先保存产品信息");
					return;
				} 
			    var url = "${ctx}/credit/product/period/form?productId="+productId;
				var rs = window.showModalDialog(url,window,"dialogWidth:850px;dialogHeight:500px;status:no;help:no;resizable:yes;");
				if("succ"==rs){
					//window.location.reload();
					showTab('p_tab1','${ctx}/credit/product/period/list?productId=${product.id}',true)
				} 
			}
			//弹出产品期限修改窗口
			function showPeriodEditWindow(productId,id){
			   if(null==productId||''==productId){
					alert("产品信息缺失，请先补全产品信息");
					return;
				}
			    var url = "${ctx}/credit/product/period/form?productId="+productId+"&id="+id;
				var rs = window.showModalDialog(url,window,"dialogWidth:850px;dialogHeight:500px;status:no;help:no;resizable:yes;");
				if("succ"==rs){
					//window.location.reload();
					showTab('p_tab1','${ctx}/credit/product/period/list?productId=${product.id}',true)
				} 
			}
			//弹出产品费用添加窗口
			function showFeeAddWindow(productId){
				if(null==productId||''==productId){
					alert("产品信息尚未保存，请先保存产品信息");
					return;
				} 
			    var url = "${ctx}/credit/product/fee/form?productId="+productId;
				var rs = window.showModalDialog(url,window,"dialogWidth:850px;dialogHeight:500px;status:no;help:no;resizable:yes;");
				if("succ"==rs){
					//window.location.reload();
					showTab('p_tab2','${ctx}/credit/product/fee/list?productId=${product.id}',true);
				} 
			}
			//弹出产品费用修改窗口
			function showFeeEditWindow(productId,id){
			    if(null==productId||''==productId){
					alert("产品信息缺失，请先补全产品信息");
					return;
				} 
			    var url = "${ctx}/credit/product/fee/form?productId="+productId+"&id="+id;
				var rs = window.showModalDialog(url,window,"dialogWidth:850px;dialogHeight:500px;status:no;help:no;resizable:yes;");
				if("succ"==rs){
					//window.location.reload();
					showTab('p_tab2','${ctx}/credit/product/fee/list?productId=${product.id}',true);
				} 
			}
		</script> -->
		<!-- 产品费用列表查询  结束 -->
	</div>
</body>
</html>