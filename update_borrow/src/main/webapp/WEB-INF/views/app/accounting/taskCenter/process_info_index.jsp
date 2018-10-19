<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>${actTaskParam.title}</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		//关闭整个页面
		function closeTab(){
			if(null==top.$.fn.jerichoTab || typeof(top.$.fn.jerichoTab)=="undefined"){
				window.close();
			}else{
				top.$.fn.jerichoTab.closeCurrentTab({});
			}
		}
		//获取iframe的高度
		function calHeight(doc){
			var ch = Math.max(doc.body.clientHeight,doc.documentElement.clientHeight);
			var sh = Math.max(doc.body.scrollHeight,doc.documentElement.scrollHeight);
			return Math.max(ch,sh);
		}
		//iframe加载时初始化高度
		function reSize(frameid){
			var doc = window.frames[frameid].document
			var h = calHeight(doc);
			$("#"+frameid).height(h+50);
		}
		//查看影像
		function viewImage() {
			if("0"=="${actTaskParam.status}" && "${productType}".length<1){//status==0  在办  ==1办结
				alert("产品尚未登记，暂时无法查看影像信息！");
				return;
			}
			var windowWidth = window.parent.window.document.body.offsetWidth -50;
			var url ="${ctx}/credit/contractVisa//luru?applyId=${actTaskParam.applyId}&optype=1&productTypeId=${productType}";
			//alert(url);
			window.showModalDialog(url, null, "dialogWidth:" + windowWidth + "px" + ";dialogHeight:700px" + ";status:no;help:no;resizable:yes;");
		}
		
		//流程轨迹-参照代办、已办结流程中的流程轨迹
		function processTrack(){
		    //var url = "${ctx}/credit/taskCenter/processTrack?procInstId=" + procInstId + " &taskDefKey= " + taskDefKey;
		    var url = "${ctx}/credit/taskCenter/processTrack?procInstId=${actTaskParam.procInstId}";
		    window.showModalDialog(url,null,"dialogWidth:1000px;dialogHeight:600px;status:no;help:no;resizable:yes;");
		}
		//流程图-参照代办、已办结流程中的流程图
		function tracePhoto(){
	    	//var url = "${ctx}/credit/taskCenter/trace/photo/" + procDefId + "/" + execId;
	    	var url = "${ctx}/credit/taskCenter/trace/photo/customer?procInstId=${actTaskParam.procInstId}";
	    	var windowWidth = window.parent.window.document.body.offsetWidth -50;
			//var windowHeight = window.parent.window.document.body.offsetHeight - 50;
			window.showModalDialog(url, null, "dialogWidth:" + windowWidth + "px" + ";dialogHeight:700px" + ";status:no;help:no;resizable:yes;");
		}
		
		function print() {
			var url="${ctx}/credit/contractVisa/print?applyId=${actTaskParam.applyId}&productType=${productType}";
			window.open(url, "newwindow", "height=250,width=500, top=500,resizable=yes,left=500");
			//var windowWidth = window.parent.window.document.body.offsetWidth -50;
			//window.showModalDialog(url,"newwindow","dialogWidth:500px;dialogHeight:250px;status:no;help:no;resizable:yes;");
		}
	</script>
</head>
<body>
	<div id="rs_msg">
	
	</div>	
	
	<div class="wrapper">
	 	<div class="ribbon">
	    	<ul class="layout">
	        	<li class="mcp_pic"><a href="#" onclick="viewImage();"><span><b></b>影像查阅</span></a></li>
	        	<li class="mcp_pic"><a href="#" onclick="tracePhoto()"><span><b></b>流程图</span></a></li>
	        	<li class="mcp_pic"><a href="#" onclick="processTrack()"><span><b></b>流程轨迹</span></a></li>
	        	<c:if test="${1==actTaskParam.status}"><!-- 已办时显示 -->
	        		<li class="mcp_print"><a href="#" onclick="print()" id="print"><span><b></b>合同查看</span></a></li>
	        	</c:if>
	        	<li class="mcp_close"><a href="#" onclick="closeTab()"><span><b></b>关闭</span></a></li>
	        </ul>
	    </div>
		<div style="margin-top: 5px;">
			<script type="text/javascript">
				//初始化最初展示的Tab页面
				$(document).ready(function(){
					var loc = window.location.toString();
					//url到时候补上申请信息的url
					showTab("tab_sqdk_info","${ctx}/credit/loanApply/loanApplyInfoForm");
				});
				//加载Tab标签页
				function showTab(tabId,url,force){//force=true时，强制重新加载页面
					$('#p_maintab a[href="#'+tabId+'"]').tab('show');
					if($("#"+tabId).html().length<20 || force){
						if(null==url || ""==url){
							return false;
						}
						
						var urlsuffix = "?readOnly=true&noInsert=true&applyId=${actTaskParam.applyId}&taskId=${actTaskParam.taskId}&taskDefKey=${actTaskParam.taskDefKey}&procDefId=${actTaskParam.procDefId}&status=${actTaskParam.status}&procInstId=${actTaskParam.procInstId}";
						url = url+urlsuffix;
						$("#"+tabId).html("<iframe style=\"min-height: 100px;\" id='frame_"+tabId+"' name='frame_"+tabId+"' src='"+url+"' width='100%' height='100%' frameborder='0' onload='reSize(\"frame_"+tabId+"\")'></iframe>");
					}
					return false;
				}
			</script>
			<ul class="nav nav-tabs" id="p_maintab">
				<li class="">
					<a href="#tab_sqdk_info" onclick="showTab('tab_sqdk_info','${ctx}/credit/loanApply/loanApplyInfoForm');return false;">贷款申请信息</a>
				</li>
				<li class="line"></li>
				<li class="active">
					<a href="#tab_customer_info" onclick="showTab('tab_customer_info','${ctx}/credit/custInfo/info');return false;" >客户信息</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_app_info" onclick="showTab('tab_app_info', '${ctx}/credit/appoint/view'); return false;">预约信息</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_mq_info" id="tab_mq_info_link" onclick="showTab('tab_mq_info','${ctx}/credit/contractVisa/form');return false;">面签信息</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_npp_info" onclick="showTab('tab_npp_info','${ctx}/credit/customer/apply_his');return false;">内匹配信息</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_dhhc_info" 
					onclick="showTab('tab_dhhc_info','${ctx}/credit/firstTrial/phone_check/input');return false;">电话核查</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_zxxx_insert" onclick="showTab('tab_zxxx_insert','${ctx}/credit/info/infoView');return false;">征信信息</a>
				</li>
				<li class="line"></li>
				<li class="">
					<a href="#tab_zl_hc" onclick="showTab('tab_zl_hc','${ctx}/credit/check/checkView');return false;">资料核查</a>
				</li>
				<li class="line"></li>
				<li class="">
					<!-- 
					 * @reqno:HHHHZZBB
					 * @date-designer:2015年11月18日-songmin
					 * @date-author:2015年11月18日-songmin:实地调查页面变更
					-->
					<a href="#tab_sddc" onclick="showTab('tab_sddc','${ctx}/credit/doubtful/infoView');return false;">实地调查</a>
				</li>
			</ul>
		</div>
		<div class="tab-content">
			<div class="tab-pane active" id="tab_customer_info">功能开发中...</div>
			<div class="tab-pane" id="tab_sqdk_info">功能开发中...</div>
			<div class="tab-pane" id="tab_app_info">功能开发中...</div>
			<div class="tab-pane" id="tab_mq_info">功能开发中...</div>
			<div class="tab-pane" id="tab_npp_info">功能开发中...</div>
			<div class="tab-pane" id="tab_dhhc_info">功能开发中...</div>
			<div class="tab-pane" id="tab_zxxx_insert">功能开发中...</div>
			<div class="tab-pane" id="tab_zl_hc">功能开发中...</div>
			<div class="tab-pane" id="tab_sddc">功能开发中...</div>
		</div>
	</div>
</body>
</html>