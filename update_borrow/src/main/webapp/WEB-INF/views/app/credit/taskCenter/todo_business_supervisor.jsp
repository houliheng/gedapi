<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>在办流程监控</title>
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
			return true
		}
		$("#searchForm").submit();
	    return false;
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
	//重置
	function fromReset(){
		$("#cusName").val('');
		$("#idNum").val('');
		$("#applyNo").val('');
		page();//查询
	}
	//查询（时间条件）
	function timeJudge(){
		$("#searchForm").submit();
	}
	//获取procInstId
	function getProcHiddenVal(){
	 	var str = "";
		$("input[name=pcheck]:checkbox").each(function(){
			if($(this).attr("checked")){
		    str = $(this).val();
			}
		});
		return str;
	}
	function getCheckBoxVal(){
	 	var str = "";
		$("input[name=pcheck]:checkbox").each(function(){
			if($(this).attr("checked")){
		    str += $(this).val() + ",";
			}
		});
		return str;
	}
	function searchForm(){
		$("#searchForm").submit();
	}
	var width = $(window).width()-100;
	var height = $(window).height()-50;
	//流程轨迹
	function processTrack(){
		var str = getCheckBoxVal();
		if(str!=""){	
			if(str.indexOf(",")!=str.length-1){
				alertx("请不要选择多条对应的流程信息");
			}else{
			    var proId = getProcHiddenVal();
				var url = "${ctx}/credit/taskCenter/processTrack?procInstId=" + proId;
				openJBox("box_processTrack", url, "流程轨迹", width, height);
			}
		}else{
			alertx("请选择对应的流程信息");
		}
	}
	//流程图
	function tracePhoto(){
		var str = getCheckBoxVal();
		if(str!=""){	
			if(str.indexOf(",")!=str.length-1){
				alertx("请不要选择多条对应的流程信息");
			}else{
				var proId = getProcHiddenVal();
				var url = "${ctx}/credit/taskCenter/trace/photo/customer?procInstId=" + proId;
				openJBox("box_tracePhoto", url, "流程图", width, height);
			}
	    }else{
			alertx("请选择对应的流程信息");
		}
	}
	
	//详情功能
	function showInfoBtn(){
		var str = getCheckBoxVal();
		if(str!=""){	
			if(str.indexOf(",")!=str.length-1){
				alertx("请不要选择多条对应的流程信息");
			}else{
				$("input[name=pcheck]:checkbox").each(function(){
					if($(this).attr("checked")){
						var checkboxId = $(this).attr("id");
						$("#"+checkboxId+"_alink").click();
					}
				});
			}
	    }else{
			alertx("请选择对应的流程信息");
		}
	}
	function showInfo(procInstId,applyNo,title){
		var url = "${ctx}/credit/taskCenter/infoView?status=0&procInstId=" + procInstId+"&applyNo="+applyNo+"&title="+"详情查看---"+title;
		openJBox("box_"+applyNo, url, "详情查看--"+title, width, height);
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="searchInfo">
			<h3 class="searchTitle">查询条件</h3>
			<div class="searchCon">
				<form:form id="searchForm" modelAttribute="customer" action="${ctx}/credit/taskCenter/todoSupervisor" method="post">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
					<input id="taskDefKey" name="taskDefKey" type="hidden" value="${taskDefKey}" />
					<div class="filter">
						<table class="searchTable">
							<tr>
								<td class="ft_label">客户名称：</td>
								<td class="ft_content"><input id="cusName" name="cusName" type="text" maxlength="50" class="input-medium" value="${customer.cusName }" /></td>
								<td class="ft_label">证件号码：</td>
								<td class="ft_content"><input id="idNum" name="idNum" type="text" maxlength="50" class="input-medium" value="${customer.idNum }" /></td>
							</tr>
							<tr>
								<td class="ft_label">申请编号：</td>
								<td class="ft_content"><input id="applyNo" name="applyNo" type="text" maxlength="50" class="input-medium" value="${customer.applyNo }" /></td>
							</tr>
						</table>
						<div class="searchButton">
							<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="timeJudge();" />&nbsp; 
							<input id="btnReset" class="btn btn-primary" type="button" value="重置" onclick="return fromReset();" />
						</div>
					</div>
				</form:form>
			</div>
		</div>
		<div class="ribbon">
			<ul class="layout">
				<li class="mcp_pic"><a href="#" id="processPicture" onclick="tracePhoto()"><span><b></b>流程图</span></a></li>
				<li class="mcp_change"><a href="#" id="processTrack" onclick="processTrack()"><span><b></b>流程轨迹</span></a></li>
				<li class="mcp_info"><a href="#" id="processInfoView" onclick="showInfoBtn()"><span><b></b>详情</span></a></li>
			</ul>
		</div>
		<div class="tableList">
			<h3 class="tableTitle">数据列表</h3>
			<div id="tableDataId" style="max-height:400px;overflow:auto;">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<th width="20"></th>
						<th width="10%">客户名称</th>
						<th width="10%">证件类型</th>
						<th width="10%">证件号</th>
						<th width="10%">申请编号</th>
						<th width="13%">申请金额(元)</th>
						<th>登记门店</th>
						<th width="17%">登记时间</th>
						<th width="17%">开始时间</th>
					</tr>
					<c:forEach items="${page.list}" var="process" varStatus="index">
						<c:if test="${0 == index.count%2}">
							<tr class="doubleRow">
						</c:if>
						<c:if test="${1 == index.count%2}">
							<tr>
						</c:if>
						<td>
						<input type="checkbox" value="${process.PROC_INS_ID}" name="pcheck"  onclick="selectSingle()" id="c_${index.count}">
						</td>
						<td class="title">
							<a href="javascript:void(0)" id="c_${index.count}_alink" onclick="showInfo('${process.PROC_INS_ID}','${process.APPLY_NO}','${process.CUST_NAME}')">${process.CUST_NAME}</a>
						</td>
						<td class="title">${fns:getDictLabel(process.ID_TYPE, 'CUSTOMER_P_ID_TYPE', '')}</td>
						<td class="title">${process.ID_NUM}</td>
						<td class="title">${process.APPLY_NO}</td>
						<td class="title"><fmt:formatNumber value="${process.APPLY_AMOUNT}" pattern="###,##0.00"></fmt:formatNumber></td>
						<td class="title">${process.ORG_ID}</td>
						<td class="title">${process.REGISTER_DATE}</td>
						<td class="title"><fmt:formatDate value="${process.STARTTIME}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="pagination">${page}</div>
		</div>
	</div>
</body>
</html>
