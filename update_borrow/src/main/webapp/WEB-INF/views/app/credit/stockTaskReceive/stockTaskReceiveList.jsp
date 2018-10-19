<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>股权任务接收管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
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
		var width = $(window).width()-100;
		var height = $(window).height()-50;
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
			    	if(procDefId==''||procDefId==null){
			    		alertx("债权流程已经结束流程，没有流程图，请查看流程轨迹");
			    	}else{
			    		var url = "${ctx}/credit/taskCenter/trace/photo/" + procDefId + "/" + execId;
						openJBox("box_"+execId, url, "流程图", width, height);
			    	}
				}
		    }else{
				alertx("请选择对应的流程任务");
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
		 * @reqno:H1510290039
		 * @date-designer:2015年11月7日-songmin
		 * @date-author:2015年11月7日-songmin:已办、未办的标记位，修改为status
		 */
		function doSignBusiness(procDefId, execId, taskDefKey, id, title,taskId,stockInfoId,procInstId,reviceStatus,stockTaskReceiveId){
			var isDone = $("#isDone").val();
			var applyNo=id;
	 		var url = "${ctx}/credit/stockTaskReceive/doBusiness/" + procDefId + "/" + execId + "/" + taskDefKey + "/" + applyNo + "/"+ isDone + "?taskId=" + taskId + "&procInstId=" + procInstId  + "&stockTaskReceiveId=" + stockTaskReceiveId + "&applyNo=" + applyNo +"&isDone="+isDone;
			$.ajax({
				type:"post",
				url:url,
				dataType:"json",
				success:function(data){
				    if(data.flag == "fail"){
				        alertx("您选择的任务已被其他人抢办，无法再办理");
				        page();//查询
				    }else{
				    	var open = data.open;
				    	if(open=="1"){
				    		alertx("该任务已经提交，不可编辑！");
				    		goToPage("${ctx}/credit/stockTaskReceive/list",'todoBusinessListSkipId');
				    		return;
				    	}
						var location =  data.location;
						//flag表示未办理0已办理1
					    var flag = "0"; 
						var newUrl = "${ctx}" + location + "?procDefId=" + procDefId + "&taskDefKey=" + taskDefKey + "&applyNo=" + id + "&execId=" + execId + "&status=" + flag + "&title=" + title + "&taskId=" + taskId+"&procInstId="+procInstId+"&stockInfoId="+stockInfoId+"&reviceStatus="+reviceStatus+"&isDone="+isDone+"&headUrl=${headUrl}"+ "&stockTaskReceiveId=" + stockTaskReceiveId;
						goToPage(newUrl,'todoBusinessListSkipId');
				    }
				},
				error:function(msg){
					alertx("未能办理，请查看后台信息");
				}
			});
		}
		
		//重置按钮
		function del() {
			$("#applyNo").val('');
			$("#status").select2("val", "");
			$("#type").select2("val", "");
		}
		
		function getApplyNoVal(){
		 	var str = "";
			$("input[name=pcheck]:checkbox").each(function(){
				if($(this).attr("checked")){
	    		    str = $(this).next("input[name=pHiddenPro]:hidden").next("input[name=pHiddenExe]:hidden").next("input[name=pHiddenTak]:hidden").next("input[name=pHiddenid]:hidden").next("input[name=pHiddenApp]:hidden").val();
				}
			});
			return str;
		}
		
		
		function getTaskVal(){
		 	var str = "";
			$("input[name=pcheck]:checkbox").each(function(){
				if($(this).attr("checked")){
			    str = $(this).next("input[name=pHiddenPro]:hidden").next("input[name=pHiddenExe]:hidden").next("input[name=pHiddenTak]:hidden").val();
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
					var taskDefKey=getTaskVal();
					var applyNo=getApplyNoVal();
					$.post("${ctx}/credit/videoUpload/checkHasFile?downType=1&&applyNo="+applyNo+"&&taskDefKey"+taskDefKey, {taskDefKey:taskDefKey}, function(data) {
						if (data) {
							if (data.status == 1) {
								var url = "${ctx}/credit/videoUpload/download?downType=1&&applyNo="+applyNo+"&&taskDefKey"+taskDefKey;
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
	<c:if test="${true == readOnly}">
		<!-- 查看详细信息时生效 -->
		<script type="text/javascript">
			$(document).ready(function() {
				$("input").attr("readOnly", "readOnly");
				$("textarea").attr("readOnly", "readOnly");
				disableSelect2();
				$("div[class='searchButton']").remove();
				$("font").remove();//由于页面的特殊性，所以这里直接将所有的fongt节点删除
			});
		</script>
	</c:if>
</head>
<body>
	<div class="wrapper">
<%-- 		<ul class="nav nav-tabs">
			<li class="active"><a href="${ctx}/credit/stockTaskReceive/">股权任务接收列表</a></li>
			<shiro:hasPermission name="credit:stockTaskReceive:edit"><li><a href="${ctx}/credit/stockTaskReceive/form">股权任务接收添加</a></li></shiro:hasPermission>
		</ul> --%>
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="stockTaskReceive" action="${ctx}/credit/stockTaskReceive/" method="post" class="breadcrumb form-search">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<input id="isDone" name="isDone" type="hidden" value="${isDone}"/>
					<div class="filter">
						<ul class="ul-form">
							<li><label>申请编号：</label>
								<form:input path="applyNo" htmlEscape="false" maxlength="32" class="input-medium"/>
							</li>
							<%-- 
							<li><label>接单人：</label>
								<form:input path="receiver" htmlEscape="false" maxlength="32" class="input-medium"/>
							</li>
							--%>
						</ul>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />&nbsp; 
							<input id="btnReset"class="btn btn-primary" type="button" value="重置" onclick="del()"/>
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<sys:message content="${message}"/>
		<div class="tableList">
			<div class="ribbon">
				<ul class="layout">
					<li class="mcp_pic">
						<a href="javascript:void(0)" id="processPicture" onclick="tracePhoto()" ><!--  -->
							<span>
								<b></b>
								流程图
							</span>
						</a>
					</li>
					<li class="mcp_change">
						<a href="javascript:void(0)" id="processTrack" onclick="processTrack()" ><!--  -->
							<span>
								<b></b>
								流程轨迹
							</span>
						</a>
					</li>
					<!-- <li class="delete">
						<a href="javascript:void(0)" id="downloadGZ" onclick="downloadGZ()">
							<span>
								<b></b>
								岗责下载
							</span>
						</a>
					</li> -->
				</ul>
			</div>
			<h3 class="tableTitle">数据列表</h3>
			<div id="tableDataId">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th width="20px"></th>
							<th style="width:17%">申请编号</th>
							<th>客户名称</th>
							<th>证件类型</th>
							<th>证件号</th>	
							<!-- <th>签收时间</th> -->
							<!-- <th>估值状态</th> -->
							<th>申请金额(元)</th>
							<th>登记门店</th>
							<!-- <th>类型</th> -->
							<th>创建时间</th>
							<th>估值信息</th>
							<th>债权状态</th>
							<%-- <shiro:hasPermission name="credit:stockTaskReceive:edit"><th>操作</th></shiro:hasPermission> --%>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${page.list}" var="stockTaskReceive">
						<tr>
							<td width="20px">
								<input type="checkbox" value="${stockTaskReceive.procInsId}" name="pcheck" onclick="selectSingle()" id="c_${index.count}">
								<input type="hidden" value="${stockTaskReceive.procDefId }" name="pHiddenPro">
								<input type="hidden" value="${stockTaskReceive.executionId }" name="pHiddenExe">
								<input type="hidden" value="${stockTaskReceive.taskDefKey }" name="pHiddenTak">
								<input type="hidden" value="${stockTaskReceive.id }" name="pHiddenid">
								<input type="hidden" value="${stockTaskReceive.applyNo }" name="pHiddenApp">
							</td>
							<%-- <td id="applyNo" class="title" title="${stockTaskReceive.applyNo}"><a href="${ctx}/credit/stockTaskReceive/form?id=${stockTaskReceive.id}">
								${stockTaskReceive.applyNo}</a>
							</td> --%>
							<td class="title" title="${stockTaskReceive.applyNo}">
								<a href="javascript:void(0)" onclick="doSignBusiness('${stockTaskReceive.procDefId }','${stockTaskReceive.executionId }','${stockTaskReceive.taskDefKey }','${stockTaskReceive.applyNo }','股权尽调','${stockTaskReceive.id }','${stockTaskReceive.stockInfoId}','${stockTaskReceive.procInsId}','${stockTaskReceive.status}','${stockTaskReceive.id}')">${stockTaskReceive.gradeDesc}${stockTaskReceive.applyNo}</a>
								<a id="todoBusinessListSkipId" href="javascript:void(0)"></a>
							</td>
							<td class="title" title="${stockTaskReceive.custName}">${stockTaskReceive.custName}</td>
							<td class="title" title="${stockTaskReceive.idType}">${fns:getDictLabel(stockTaskReceive.idType, 'CUSTOMER_P_ID_TYPE', '')}</td>
							<td class="title" title="${stockTaskReceive.idNum}">${stockTaskReceive.idNum}</td>
 							<%--
 							<td id="receiver" class="title" title="${stockTaskReceive.receiver}">
								${stockTaskReceive.receiver}
							</td> --%>
							<%-- <td id="receiveDate" class="title" title="${stockTaskReceive.receiveDate}">
								<fmt:formatDate value="${stockTaskReceive.receiveDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td> --%>
							<%-- <td id="status" class="title" title="${stockTaskReceive.status}">
								${fns:getDictLabel(stockTaskReceive.status, 'stock_assess_status', '')}
							</td>--%>
							<td id="type" class="title" title="${stockTaskReceive.applyAmount}">
								${stockTaskReceive.applyAmount}
							</td> 
							<td id="type" class="title" title="${stockTaskReceive.orgName}">
								${stockTaskReceive.orgName}
							</td>
							<%-- <td id="type" class="title" title="${stockTaskReceive.type}">
								${fns:getDictLabel(stockTaskReceive.type, 'stock_assess_type', '')}
							</td> --%>
							<td id="createDate" class="title" title="${stockTaskReceive.createDate}">
								<fmt:formatDate value="${stockTaskReceive.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>
								<c:if test="${stockTaskReceive.status == '0'}">
									<span>已保存</span>
								</c:if>
								<c:if test="${stockTaskReceive.status == '1'}">
									<span>已提交</span>
								</c:if>
								<c:if test="${stockTaskReceive.status == ''||stockTaskReceive.status ==null}">
									<span>未操作</span>
								</c:if>
							</td>
							<td>
								<span>${stockTaskReceive.processStatus}</span>
							</td>
							<%-- <shiro:hasPermission name="credit:stockTaskReceive:edit"><td>
			    				<a href="${ctx}/credit/stockTaskReceive/form?id=${stockTaskReceive.id}">修改</a>
								<a href="${ctx}/credit/stockTaskReceive/delete?id=${stockTaskReceive.id}" onclick="return confirmx('确认要删除该股权任务接收吗？', this.href)">删除</a>
							</td></shiro:hasPermission> --%>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="pagination">${page}</div>
		</div>
	</div>
</body>
</html>