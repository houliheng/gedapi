<%@ page contentType="text/html;charset=UTF-8"%>
<div class="ribbon">
	<ul class="layout">
		<c:if test="${0==actTaskParam.status}">
			<li class="mcp_upload"><a href="#" onclick="uploadImage();"><span><b></b>影像上传</span></a></li>
		</c:if>
		<c:if test="${actTaskParam.headUrl == '/credit/taskCenter/toDoneList/utask_cwfk'}">
			<li class="mcp_upload"><a href="#" onclick="uploadImage();"><span><b></b>影像上传</span></a></li>
		</c:if>
		<li class="mcp_pic"><a href="#" onclick="viewImage(${actTaskParam.status});"><span><b></b>影像查阅</span></a></li>
		<li class="mcp_pic"><a href="#"	onclick="tracePhoto('${actTaskParam.procDefId}','${actTaskParam.execId}')"><span><b></b>流程图</span></a></li>
		<li class="mcp_pic"><a href="#"	onclick="processTrack('${actTaskParam.taskDefKey}','${actTaskParam.procInstId}')"><span><b></b>流程轨迹</span></a></li>
		<c:if test="${0==actTaskParam.status}">
			<li class="mcp_change"><a href="#" onclick="change()" id="change"><span><b></b>转办</span></a></li>
		</c:if>
		 <c:if test="${1==actTaskParam.canRedo}"> 
			<c:if test="${actTaskParam.taskDefKey  eq 'utask_htsh'}">
			<shiro:hasPermission name="credit:htshReDo">
			<li class="mcp_change" id="reDo"><a href="#" onclick="reDo('${actTaskParam.taskId}','rs_msg','reDo','${ctx}/credit/taskCenter/reDo?taskId=${actTaskParam.taskId}&utask=${actTaskParam.taskDefKey}&applyNo=${actTaskParam.applyNo}')">
					<span><b></b>撤回</span>
			</a></li>
			</shiro:hasPermission>
			</c:if> 
			<c:if test="${actTaskParam.taskDefKey  ne 'utask_htsh'}">
			<li class="mcp_change" id="reDo"><a href="#" onclick="reDo('${actTaskParam.taskId}','rs_msg','reDo','${ctx}/credit/taskCenter/reDo?taskId=${actTaskParam.taskId}&utask=${actTaskParam.taskDefKey}&applyNo=${actTaskParam.applyNo}')">
					<span><b></b>撤回</span>
			</a></li>
			</c:if>
		 </c:if> 
		<li class="mcp_close"><a href="#" onclick="goToPage('${ctx}${actTaskParam.headUrl}','headUrlId');"><span><b></b>返回</span></a><!-- 跳转用a标签 --><a id="headUrlId" href=""></a></li>
	</ul>
</div>
<div id="rs_msg"></div>
