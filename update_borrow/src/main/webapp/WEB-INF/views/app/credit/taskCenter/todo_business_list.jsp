<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>待办任务列表</title>
<meta name="decorator" content="default" />
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
			return true;
		}
		$("#searchForm").submit();
	    return false;
	}
	//复选框选中
	function selectAll() {
		if ($('[name=all]:checkbox').attr("checked") == "checked") {
			$('[name=pcheck]:checkbox').attr("checked", true);
		} else {
			$('[name=pcheck]:checkbox').attr("checked", false);
		}
	}
	//单选框
	function selectSingle(){
		$("[name=pcheck]:checkbox").each(function(){
			$(this).click(function(){
				if($(this).attr("checked")){
					$("[name=pcheck]:checkbox").removeAttr("checked");
					$(this).attr("checked","checked");
				}
			});
		});
	} 
	function fromReset(){
		$("#cusName").val('');
		$("#idNum").val('');
		$("#applyNo").val('');
		$("#createStartTime").val('');
		$("#createEndTime").val('');
		$("#applyProductTypeCode").select2("val", "");
		$("#companyId").val('');
		$("#companyName").val('');
		$("#contractNo").val('');
		$("#loanModel").select2("val", "");
		}
	function timeJudge(){
		if($("#createStartTime").val()!=''&$("#createEndTime").val()!=''){
			if($("#createStartTime").val()>$("#createEndTime").val()){
				alertx("开始时间应小于结束时间！");
				return;
			}else{
				loading("正在加载，请稍等");
				$("#searchForm").submit();
				return;
				}
			}
			loading("正在加载，请稍等");
		$("#searchForm").submit();
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
	//获取PROCDEF
	function getProcHiddenVal(){
	 	var str = "";
		$("input[name=pcheck]:checkbox").each(function(){
			if($(this).attr("checked")){
		    str = $(this).next("input[name=pHiddenPro]:hidden").val();
			}
		});
		return str;
	}
	//获取EXEC
	function getExecHiddenVal(){
	 	var str = "";
		$("input[name=pcheck]:checkbox").each(function(){
			if($(this).attr("checked")){
		    str = $(this).next("input[name=pHiddenPro]:hidden").next("input[name=pHiddenExe]:hidden").val();
			}
		});
		return str;
	}
	/**
	 * @reqno:H1509230062
	 * @date-designer:20151016-huangxuecheng
	 * @date-author:20151016-huangxuecheng:循环获取taskId
	 */
	//获取taskId
	function getTaskHiddenVal(){
	 	var str = "";
		$("input[name=pcheck]:checkbox").each(function(){
			if($(this).attr("checked")){
		    str = $(this).next("input[name=pHiddenPro]:hidden").next("input[name=pHiddenExe]:hidden").next("input[name=pHiddenTak]:hidden").val();
			}
		});
		return str;
	}
	//获取Id
	function getIdHiddenVal(){
	 	var str = "";
		$("input[name=pcheck]:checkbox").each(function(){
			if($(this).attr("checked")){
		    str = $(this).next("input[name=pHiddenPro]:hidden").next("input[name=pHiddenExe]:hidden").next("input[name=pHiddenTak]:hidden").next("input[name=pHiddenid]:hidden").val();
			}
		});
		return str;
	}
	/**
	 * @reqno:H1509230062
	 * @date-designer:20151016-huangxuecheng
	 * @date-author:20151016-huangxuecheng:循环获取applyCode
	 */
	//获取applyCode
	function getApplyCodeVal(){
	 	var str = "";
		$("input[name=pcheck]:checkbox").each(function(){
			if($(this).attr("checked")){
		    str = $(this).parent().next().next().next().next().next().text();
			}
		});
		return str;
	}
	var width = $(window).width()-100;
	var height = $(window).height()-50;
	//流程轨迹
	function processTrack(){
		var str = getCheckBoxVal();
		if(str!=""){	
			if(str.indexOf(",")!=str.length-1){
				alertx("请不要选择多条对应的流程任务");
			}else{
				var taskDefKey = $("#taskDefKey").val();
			    str = str.substring(0,str.length-1);
			    var url = "${ctx}/credit/taskCenter/processTrack?procInstId=" + str + " &taskDefKey= " + taskDefKey;
				openJBox("box_"+taskDefKey, url, "流程轨迹", width, height);
			}
		}else{
			alertx("请选择对应的流程任务");
		}
	}
	/**
	 * @reqno:H1510130137
	 * @date-designer:20151020-huangxuecheng
	 * @date-author:20151020-huangxuecheng:开发内容：灵活查询列表使用后流程图的大小需要修改，修改流程图的大小
	 */
	//流程图
	function tracePhoto(){
		var str = getCheckBoxVal();
		if(str!=""){	
			if(str.indexOf(",")!=str.length-1){
				alertx("请不要选择多条对应的流程任务");
			}else{
		    	str = str.substring(0,str.length-1);
		    	var procDefId = getProcHiddenVal();
		    	var execId = getExecHiddenVal();
		    	var url = "${ctx}/credit/taskCenter/trace/photo/" + procDefId + "/" + execId;
				openJBox("box_"+execId, url, "流程图", width, height);
			}
	    }else{
			alertx("请选择对应的流程任务");
		}
	}
	/**
	 * @reqno:H1509230062
	 * @date-designer:20151016-huangxuecheng
	 * @date-author:20151016-huangxuecheng:办理功能js，通过已经写好的js方法获取procDefId流程定义id，execId执行id， taskDefKey任务定义id，
	 *									        并通过ajax提交，返回值进行处理跳转到处理页面
	 */
	//办理按钮
	function doBusiness(){
		var str = getCheckBoxVal();
		if(str != ""){	
			if(str.indexOf(",") != str.length-1){
				alertx("请不要选择多条对应的办理任务");
			}else{
				$("input[name=pcheck]:checkbox").each(function(){
					if($(this).attr("checked")){
				    	//str = $(this).next("input[name=pHiddenPro]:hidden").next("input[name=pHiddenExe]:hidden").next("input[name=pHiddenTak]:hidden").val();
						var checkboxId = $(this).attr("id");
						$("#"+checkboxId+"_alink").click();
					}
				});
				
			}
	    }else{
			alertx("请选择对应的流程任务");
		}
	}
	/**
	 * @reqno:H1510290039
	 * @date-designer:2015年11月7日-songmin
	 * @date-author:2015年11月7日-songmin:已办、未办的标记位，修改为status
	 */
	function doSignBusiness(procDefId, execId, taskDefKey, id, title,taskId,procInstId){
 		var url = "${ctx}/credit/taskCenter/doBusiness/" + procDefId + "/" + execId + "/" + taskDefKey + "?taskId=" + taskId;
		$.ajax({
			type:"post",
			url:url,
			dataType:"json",
			success:function(data){
			    if(data.flag == "fail"){
			        alertx("您选择的任务已被其他人抢办，无法再办理");
			        page();//查询
			    }else{
					var location =  data.location;

					//flag表示未办理0已办理1
				    var flag = "0"; 
					var newUrl = "${ctx}" + location + "?procDefId=" + procDefId + "&taskDefKey=" + taskDefKey + "&applyNo=" + id + "&execId=" + execId + "&status=" + flag + "&title=" + title + "&taskId=" + taskId+"&procInstId="+procInstId+"&headUrl=${headUrl}";
					goToPage(newUrl,'todoBusinessListSkipId');
			    }
			},
			error:function(msg){
				alertx("未能办理，请查看后台信息");
			}
		});
	}
	
	//拒绝签收
	function refuseToSignTask(){
	var str = getCheckBoxVal();
		if(str != ""){	
			if(str.indexOf(",") != str.length-1){
				alertx("请不要选择多条对应的办理任务");
			}else{
				str = str.substring(0, str.length-1);
		    	var taskId = getIdHiddenVal();
		    	var url = "${ctx}/credit/taskCenter/refuseToSignTask?taskId=" + taskId;
		    	$.ajax({
					type:"post",
					url:url,
					dataType:"json",
					success:function(data){
						alertx(data.message,function(){
	   		    			page();
   		    			});
					},
					error:function(msg){
						alertx("未能办理，请查看后台信息");
					}
				});
			}
	    }else{
			alertx("请选择对应的流程任务");
		}
	}
	//页面数据列表高度设置
	$(document).ready(function() {
		
		/* var isShowGZ='${isShowGZ}';
		if(isShowGZ!='1'){
			$(".showGZ").hide();
		}else{
			$(".showGZ").show();
		} */
		if($("#taskDefKey").val()!=""){
			var action = "${ctx}/credit/taskCenter/list/"+$("#taskDefKey").val();
			$("#searchForm").attr("action",action);
		}
		var tds = $(".title");
		$.each(tds,function(){
			$(this).attr("title",$.trim($(this).text()));
		});
	});
	
	//重新办理
	function deAllocate() {
	   var str = getCheckBoxVal();
		if(str!=""){	
			if(str.indexOf(",")!=str.length-1){
				alertx("请不要选择多条对应的流程任务");
			}else{
				var taskId = getIdHiddenVal();
				var applyNo = getApplyNoVal();
				var procDefId = getProcHiddenVal();
		    	var execId = getExecHiddenVal();
		    	var taskDefKey = getTaskHiddenVal();
			    str = str.substring(0,str.length-1);
			    
			    //flag表示未办理0已办理1
			    var flag = "0";
				var urlsuffix = "?applyNo= " + applyNo + "&taskId= " + taskId + "&taskDefKey= " + taskDefKey + "&procDefId=" + procDefId + "&status=" + flag + "&procInstId= " + str;
				var url = "${ctx}/credit/taskCenter/adminChange" + urlsuffix;
				var width = $(document).width() - 100;
				width = Math.max($(document).width() - 100, 800);
				var height = $(document).height() - 100;
				height = Math.min($(document).height() - 100, 400);
				openJBox("viewImageBox", url, "重新分配", width, height);
			}
		}else{
			alertx("请选择对应的流程任务");
		}
	}
	
	//获取applyNo
	function getApplyNoVal(){
	 	var str = "";
		$("input[name=pcheck]:checkbox").each(function(){
			if($(this).attr("checked")){
    		    str = $(this).next("input[name=pHiddenPro]:hidden").next("input[name=pHiddenExe]:hidden").next("input[name=pHiddenTak]:hidden").next("input[name=pHiddenid]:hidden").next("input[name=pHiddenApp]:hidden").val();
			}
		});
		return str;
	}
	
	
	function downloadGZ(){
		var str = getCheckBoxVal();
		if(str != ""){	
			if(str.indexOf(",") != str.length-1){
				alertx("请不要选择多条对应的任务");
			}else{
				var taskDefKey=getTaskHiddenVal();
				
				var applyNo=getApplyNoVal();
				$.post("${ctx}/credit/videoUpload/checkHasFile?downType=1&&applyNo="+applyNo+"&&taskDefKey="+taskDefKey,null, function(data) {
					if (data) {
						if (data.status == 1) {
							var url = "${ctx}/credit/videoUpload/download?downType=1&&applyNo="+applyNo+"&&taskDefKey="+taskDefKey;
							window.location.href=url;
						} else {
							alertx("检查文件是否上传！");
						}
					}
				});
			}
	    }else{
			alertx("请选择对应的任务");
		}
	}
	
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="customer" action="${ctx}/credit/taskCenter" method="post">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<input id="taskDefKey" name="taskDefKey" type="hidden" value="${taskDefKey}" />
					<div class="filter">
						<table class="searchTable">
							<tr>
								<td class="ft_label">客户名称：</td>
								<td class="ft_content">
									<input id="cusName" name="cusName" type="text" maxlength="50" class="input-medium" value="${customer.cusName }" />
								</td>
								<c:if test="${taskDefKey ne 'utask_cwfk' && taskDefKey ne 'utask_htsh' }">
									<td class="ft_label">证件号码：</td>
									<td class="ft_content">
										<input id="idNum" name="idNum" type="text" maxlength="50" class="input-medium" value="${customer.idNum }" />
									</td>
								</c:if>
							</tr>
							<tr>
								<c:if test="${taskDefKey eq 'utask_cwfk' || taskDefKey eq 'utask_htsh'}">
									<td class="ft_label">渠道：</td>
									<td class="ft_content">
										<form:select path="loanModel" class="input-medium ">
											<form:option value="" label="" />
											<form:options items="${fns:getDictList('LOAN_MODEL')}" itemLabel="label" itemValue="value" htmlEscape="false" />
										</form:select>
									</td>
								</c:if>
								<c:if test="${taskDefKey ne 'utask_cwfk' && taskDefKey ne 'utask_htsh' }">
									<td class="ft_label">申请编号：</td>
									<td class="ft_content">
										<input id="applyNo" name="applyNo" type="text" maxlength="50" class="input-medium" value="${customer.applyNo }" />
									</td>
								</c:if>
								<td class="ft_label">创建时间：</td>
								<td class="ft_content" style="width: 240px;">
									<input id="createStartTime" name="createStartTime" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate" value="<fmt:formatDate value="${customer.createStartTime }" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd',isShowClear:true});" />
									<label>&nbsp;--&nbsp;</label>
									<input id="createEndTime" name="createEndTime" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate" value="<fmt:formatDate value="${customer.createEndTime }" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({onpicked:dateWhite(this),dateFmt:'yyyy-MM-dd',isShowClear:true});" />
								</td>
							</tr>
							<c:if test="${taskDefKey eq 'utask_htsh' || taskDefKey eq 'utask_cwfk' }">
								<tr>
									<td class="ft_label">产品类型：</td>
									<td class="ft_content">
										<form:select path="applyProductTypeCode" cssClass="input-medium">
											<form:option value="" label="" />
											<form:options items="${fns:getDictList('PRODUCT_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
										</form:select>
									</td>
									<td class="ft_label">所属机构：</td>
									<td class="ft_content">
										<sys:usertreeselect id="company" name="company.id" value="${customer.company.id}" labelName="company.name" labelValue="${customer.company.name}" title="所属机构" url="/sys/office/treeData?type=2&companyId=${loginOfficeId }" cssClass="input-medium" allowClear="true" />
									</td>
								</tr>
							</c:if>
							<c:if test="${taskDefKey eq 'utask_htmq' || taskDefKey eq 'utask_fwsh' || taskDefKey eq 'utask_htsh' || taskDefKey eq 'utask_cwfk'}">
								<tr>
									<td class="ft_label">合同编号：</td>
									<td class="ft_content">
										<input id="contractNo" name="contractNo" type="text" maxlength="50" class="input-medium" value="${customer.contractNo }" />
									</td>
								</tr>
							</c:if>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="timeJudge();" />
							&nbsp;
							<input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="return fromReset();" />
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<sys:message content="${message}" />
		<div class="ribbon">
			<ul class="layout">
				<li class="mcp_commit">
					<a href="javascript:void(0)" id="processDo" onclick="doBusiness()">
						<span>
							<b></b>
							办理
						</span>
					</a>
				</li>
				<li class="mcp_pic">
					<a href="javascript:void(0)" id="processPicture" onclick="tracePhoto()">
						<span>
							<b></b>
							流程图
						</span>
					</a>
				</li>
				<li class="mcp_change">
					<a href="javascript:void(0)" id="processTrack" onclick="processTrack()">
						<span>
							<b></b>
							流程轨迹
						</span>
					</a>
				</li>
				<c:if test="${taskDefKey ne 'utask_cwfk' && taskDefKey ne 'utask_htsh' }">
					<shiro:hasRole name="admin">
						<li class="mcp_change">
							<a href="javascript:void(0)" id="processDeAllocate" onclick="deAllocate()">
								<span>
									<b></b>
									重新分配
								</span>
							</a>
						</li>
						<li class="delete">
							<a href="javascript:void(0)" id="resufeToSign" onclick="refuseToSignTask()">
								<span>
									<b></b>
									拒签
								</span>
							</a>
						</li>
					</shiro:hasRole>
				</c:if>
				<!-- <li class="delete showGZ">
					<a href="javascript:void(0)" id="downloadGZ" onclick="downloadGZ()">
						<span>
							<b></b>
							岗责下载
						</span>
					</a>
				</li> -->
			</ul>
		</div>
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div id="tableDataId" style="max-height:400px;overflow:auto;">
				<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered table-condensed table-hover">
					<thead>
						<tr>
							<th width="20px"></th>
							<th width="10%">任务名称</th>
							<th width="10%">客户名称</th>
							<c:if test="${taskDefKey ne 'utask_cwfk' && taskDefKey ne 'utask_htsh' }">
								<th width="8%">证件类型</th>
								<th width="15%">证件号</th>
								<th width="15%">申请编号</th>
								<!-- 合同预约环节之后显示合同金额 -->
								<c:if test="${taskDefKey ne 'utask_htyy' 
								&& taskDefKey ne 'utask_htmq' 
								&& taskDefKey ne 'utask_fwsh'
								&& taskDefKey ne 'utask_htsh'
								&& taskDefKey ne 'utask_cwfk'
								&& taskDefKey ne 'utask_qxsp' }">
									<th width="13%">申请金额(元)</th>
								</c:if>
							</c:if>
							<c:if test="${taskDefKey eq 'utask_cwfk' || taskDefKey eq 'utask_htsh'}">
								<th width="15%">申请编号</th>
							</c:if>
							<c:if test="${empty taskDefKey 
							|| taskDefKey eq 'utask_htyy' 
							|| taskDefKey eq 'utask_htmq' 
							|| taskDefKey eq 'utask_fwsh'
							|| taskDefKey eq 'utask_htsh'
							|| taskDefKey eq 'utask_cwfk'
							|| taskDefKey eq 'utask_qxsp' }">
								<th width="13%">合同金额(元)</th>
							</c:if>
							<c:if test="${taskDefKey eq 'utask_cwfk' || taskDefKey eq 'utask_htsh'}">
								<th width="7%">产品类型</th>
								<th width="7%">渠道</th>
								<th width="7%">大区</th>
								<th width="7%">区域</th>
								<th width="7%">分公司</th>
							</c:if>
							<c:if test="${taskDefKey ne 'utask_cwfk' && taskDefKey ne 'utask_htsh' }">
								<th width="10%">登记门店</th>
							</c:if>
							<th width="15%">创建时间</th>
							<c:if test="${taskDefKey eq 'utask_cwfk'}">
								<th width="7%">操作状态</th>
							</c:if>
						</tr>
					</thead>
					<c:forEach items="${page.list}" var="process" varStatus="index">
						<c:if test="${0 == index.count%2}">
							<tr class="doubleRow">
						</c:if>
						<c:if test="${1 == index.count%2}">
							<tr>
						</c:if>
						<td width="20px">
							<input type="checkbox" value="${process.PROC_INS_ID}" name="pcheck" onclick="selectSingle()" id="c_${index.count}">
							<input type="hidden" value="${process.PROCESSDEFINITIONID }" name="pHiddenPro">
							<input type="hidden" value="${process.EXECUTIONID }" name="pHiddenExe">
							<input type="hidden" value="${process.TASKDEFINITIONKEY }" name="pHiddenTak">
							<input type="hidden" value="${process.ID }" name="pHiddenid">
							<input type="hidden" value="${process.APPLY_NO }" name="pHiddenApp">
						</td>
						<!-- procDefId, execId, taskDefKey, applyCode -->
						<td class="title" title="${process.NAME}">
							<a href="javascript:void(0)" id="c_${index.count}_alink" onclick="doSignBusiness('${process.PROCESSDEFINITIONID }','${process.EXECUTIONID }','${process.TASKDEFINITIONKEY }','${process.APPLY_NO }','${process.NAME}','${process.ID }','${process.PROC_INS_ID}')">${process.NAME}</a>
							<a id="todoBusinessListSkipId" href="javascript:void(0)"></a>
						</td>
						<td class="title" style="word-break:break-all;word-wrap:break-word;">${process.CUST_NAME}</td>
						<c:if test="${taskDefKey ne 'utask_cwfk' && taskDefKey ne 'utask_htsh' }">
							<td class="title" title="${process.ID_TYPE}">${fns:getDictLabel(process.ID_TYPE, 'CUSTOMER_P_ID_TYPE', '')}</td>
							<td class="title" title="${process.ID_NUM}">${process.ID_NUM}</td>
							<td class="title" title="${process.APPLY_NO}">${process.APPLY_NO}</td>
							<c:if test="${taskDefKey ne 'utask_htyy' 
								&& taskDefKey ne 'utask_htmq' 
								&& taskDefKey ne 'utask_fwsh'
								&& taskDefKey ne 'utask_htsh'
								&& taskDefKey ne 'utask_cwfk'
								&& taskDefKey ne 'utask_qxsp' }">
								<td class="title" title="${process.APPLY_AMOUNT}">
									<fmt:formatNumber value="${process.APPLY_AMOUNT}" pattern="###,##0.00"></fmt:formatNumber>
								</td>
							</c:if>
						</c:if>
						<c:if test="${taskDefKey eq 'utask_cwfk' || taskDefKey eq 'utask_htsh'}">
							<td class="title">${process.APPLY_NO}</td>
						</c:if>
						<c:if test="${empty taskDefKey 
							|| taskDefKey eq 'utask_htyy' 
							|| taskDefKey eq 'utask_htmq' 
							|| taskDefKey eq 'utask_fwsh'
							|| taskDefKey eq 'utask_htsh'
							|| taskDefKey eq 'utask_cwfk'
							|| taskDefKey eq 'utask_qxsp' }">
							<td class="title" title="${process.CONTRACT_AMOUNT}">
								<fmt:formatNumber value="${process.CONTRACT_AMOUNT}" pattern="###,##0.00"></fmt:formatNumber>
							</td>
						</c:if>
						<c:if test="${taskDefKey eq 'utask_cwfk' || taskDefKey eq 'utask_htsh'}">
							<td class="title">${fns:getDictLabel(process.APPLY_PRODUCT_TYPE_CODE, 'PRODUCT_TYPE', '')}</td>
							<td class="title">${fns:getDictLabel(process.LOAN_MODEL, 'LOAN_MODEL', '')}</td>
							<td class="title">${process.ORG_LEVEL2}</td>
							<td class="title">${process.ORG_LEVEL3}</td>
							<td class="title">${process.ORG_LEVEL4}</td>
						</c:if>
						<c:if test="${taskDefKey ne 'utask_cwfk' && taskDefKey ne 'utask_htsh' }">
							<td class="title" title="${process.ORG_ID}">${process.ORG_ID}</td>
						</c:if>
						<td class="title" title="">
							<fmt:formatDate value="${process.CREATETIME }" type="both" pattern="yyyy-MM-dd HH:mm:ss" />
						</td>
						<c:if test="${taskDefKey eq 'utask_cwfk'}">
							<td class="title">${fns:getDictLabel(process.LOAN_STATUS, 'LOAN_STATUS', '')}</td> 
						</c:if>
						</tr>
					</c:forEach>
				</table>
			</div>
			<!--  
  	@reqno:H1511040003
  	@date-designer:20151106-huangxuecheng
  	@date-author:20151106-huangxuecheng:CRE_信贷审批_任务中心_待办理任务列表_添加分页功能
  	@e-out-table:tableDataId-输出代办分页列表:输出代办分页列表
     -->
			<div class="pagination">${page}</div>
		</div>
	</div>
</body>
</html>