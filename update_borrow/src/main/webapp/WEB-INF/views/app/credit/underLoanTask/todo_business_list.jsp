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
	function createUnderLoan(){
            window.location.href="${ctx}/credit/workflow/areaRegister";
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


		if($("#taskDefKey").val()!=""){
			var action = "${ctx}/credit/taskCenter/underLoanList/"+$("#taskDefKey").val();
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


    function createGedAccount(applyNo,unSocCreditNo,corporationMobile,busiRegName,id){
        if(unSocCreditNo!=null&&unSocCreditNo!=''&&corporationMobile!=null&&corporationMobile!=''){
            loading("正在注册，请稍等");
            var url = "${ctx}/credit/underConclusion/registerGed?unSocCreditNo=" + unSocCreditNo+"&&corporationMobile="+corporationMobile+"&&roleType=0&&busiRegName="+busiRegName+"&&comId="+id+"&&applyNo="+applyNo;
            $.post(url, null, function(data) {
				if (data.status == '1') {
					alertx(data.message, function() {
						location.reload();
					});
				} else {
					alertx(data.message);
				}
            });
		}else {
            alertx("请先完善信息！");
		}
    }


    function pushTarget(applyNo,unSocCreditNo){
		loading("正在推送，请稍等");
		var url = "${ctx}/credit/underConclusion/pushTarget?applyNo=" + applyNo+"&&unSocCreditNo="+unSocCreditNo;
		$.post(url, null, function(data) {
			if (data.status == '1') {
				alertx(data.message, function() {
					location.reload();
				});
			} else {
				alertx(data.message);
			}
		});
    }

    function targetLoan(applyNo,contractNo){
        var url = "${ctx}/credit/applyLoanStatus/sureGuaranteeForm?applyNo="+applyNo+"&&contractNo="+contractNo+"&&flag=1";
        openJBox("applyLoanStatus-form", url, "确认缴费", 800, 400,null);
    }

</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="customer" action="${ctx}/credit/taskCenter/underLoanList" method="post">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<input id="taskDefKey" name="taskDefKey" type="hidden" value="${taskDefKey}" />
					<div class="filter">
						<table class="searchTable">
							<tr>
								<td class="ft_label">借款客户：</td>
								<td class="ft_content">
									<input id="cusName" name="cusName" type="text" maxlength="50" class="input-medium" value="${customer.cusName }" />
								</td>
								<td class="ft_label">申请编号：</td>
								<td class="ft_content">
									<input id="applyNo" name="applyNo" type="text" maxlength="50" class="input-medium" value="${customer.applyNo }" />
								</td>
							</tr>
							<%--<tr>
								<td class="ft_label">产品类型：</td>
								<td class="ft_content">
									<input id="applyProductTypeCode" name="applyProductTypeCode" type="text" maxlength="50" class="input-medium" value="${customer.applyProductTypeCode }" />
								</td>
							</tr>--%>
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
                <c:if test="${taskDefKey=='under_dqglr'}">
                    <li class="mcp_commit">
                        <a href="javascript:void(0)" id="processDo" onclick="createUnderLoan()">
                            <span>
                                <b></b>
                                新增借款
                            </span>
                        </a>
                    </li>
                </c:if>
				<li class="mcp_change">
					<a href="javascript:void(0)" id="processTrack" onclick="processTrack()">
						<span>
							<b></b>
							流程轨迹
						</span>
					</a>
				</li>
			</ul>
		</div>
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div id="tableDataId" style="max-height:400px;overflow:auto;">
				<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered table-condensed table-hover">
					<thead>
						<tr>
							<th width="20px"></th>
							<th width="20px">序号</th>
							<th width="15%">申请编号</th>
							<th width="10%">借款客户</th>
							<th width="10%">借款产品</th>
							<th width="10%">借款金额（元）</th>
							<th width="5%">期数</th>
							<c:if test="${taskDefKey!='under_tb'}">
								<th width="10%">合同还款方式</th>
								<th width="10%">冠易贷账号</th>
								<th width="15%">创建时间</th>
							</c:if>
							<c:if test="${taskDefKey=='under_tb'}">
								<th width="15%">放款状态</th>
							</c:if>

							<th width="7%">操作状态</th>
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
							<input type="checkbox" value="${process.PROCESSINSTANCEID}" name="pcheck" onclick="selectSingle()" id="c_${index.count}">
							<input type="hidden" value="${process.PROCESSDEFINITIONID }" name="pHiddenPro">
							<input type="hidden" value="${process.EXECUTIONID }" name="pHiddenExe">
							<input type="hidden" value="${process.TASKDEFINITIONKEY }" name="pHiddenTak">
							<input type="hidden" value="${process.ID }" name="pHiddenid">
							<input type="hidden" value="${process.applyNo }" name="pHiddenApp">
						</td>
						<td>${index.count}</td>
						<!-- procDefId, execId, taskDefKey, applyCode -->
						<td class="title" title="${process.NAME}">
							<a href="javascript:void(0)" id="c_${index.count}_alink" onclick="doSignBusiness('${process.PROCESSDEFINITIONID }','${process.EXECUTIONID }','${process.TASKDEFINITIONKEY }','${process.applyNo }','${process.NAME}','${process.ID }','${process.PROCESSINSTANCEID}')">${process.applyNo}</a>
							<a id="todoBusinessListSkipId" href="javascript:void(0)"></a>
						</td>
						<td class="title" style="word-break:break-all;word-wrap:break-word;">${process.busiRegName}</td>
						<td class="title" style="word-break:break-all;word-wrap:break-word;">${process.approProductTypeName}</td>
						<td class="title" style="word-break:break-all;word-wrap:break-word;">${process.contractAmount}</td>
						<td class="title" style="word-break:break-all;word-wrap:break-word;">${process.approPeriodValue}</td>
						<c:if test="${taskDefKey!='under_tb'}">
							<td class="title">${fns:getDictLabel(process.approLoanRepayType, 'LOAN_REPAY_TYPE', '')}</td>
							<td class="title" style="word-break:break-all;word-wrap:break-word;">${process.gedNumber}</td>
							<td class="title" title="">
								<fmt:formatDate value="${process.CREATETIME }" type="both" pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
						</c:if>
						<c:if test="${taskDefKey=='under_tb'}">
							<td>
								<c:if test="${process.loanStatus=='2'}">
									待推送
								</c:if>
								<c:if test="${process.loanStatus=='3'}">
									已推送待放款
								</c:if>
								<c:if test="${process.loanStatus=='4'}">
									放款成功
								</c:if>
                                <c:if test="${process.loanStatus=='5'}">
                                    流标
                                </c:if>
								<c:if test="${process.loanStatus=='26'}">
									首次已提现
								</c:if>
								<c:if test="${process.loanStatus=='22'}">
									全部已提现
								</c:if>
							</td>
						</c:if>
                        <td>
                            <c:if test="${empty process.gedNumber &&taskDefKey!='under_tb'}">
                                <a href="javascript:void(0)" onclick="createGedAccount('${process.applyNo }','${process.unSocCreditNo}','${process.corporationMobile}','0','${process.busiRegName}','${process.comId}')">创建冠易贷账号</a>
                            </c:if>
							<c:if test="${taskDefKey=='under_tb'&&process.loanStatus=='2'}">
								<a href="javascript:void(0)" onclick="pushTarget('${process.applyNo }','${process.unSocCreditNo}')">推标</a>
							</c:if>
							<c:if test="${taskDefKey=='under_tb'&&(process.loanStatus=='4'||process.loanStatus=='26'||process.loanStatus=='22')}">
								<c:if test="${empty process.factGuaranteeFee}">
									<a href="javascript:void(0)" onclick="targetLoan('${process.applyNo }','${process.contractNo }')">缴费</a>
								</c:if>
							</c:if>
                        </td>
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