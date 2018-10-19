<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!--
/**
	 * @reqno:H1512210075
	 * @date-designer:2015年12月25日-zhangzhida
	 * @e-out-tree : holidayTree - 假期类别树 : 假期类别树
	 * @date-author:2015年12月25日-zhangzhida:新增假期管理功能页面,页面布局左侧假期类别树.右侧为日历控件信息
	 */
  -->
<html>
<head>
<title>假期管理</title>
<meta name="decorator" content="default" />
<%@include file="/WEB-INF/views/include/treeview.jsp"%>
<style type="text/css">
.ztree {
	overflow: auto;
	margin: 0;
	_margin-top: 10px;
	padding: 10px 0 0 10px;
}
.right {
	margin-right: 8px;
}
.control-label{
	width : 80px;
	margin-right: 10px;
	margin-bottom: 10px;
}
.control-text {
	width : 200px;
	margin-right: 10px;
	margin-bottom: 10px;
}
</style>
<LINK rel=stylesheet type=text/css
	href="${ctxStatic}/common/css/style_homecalendar.css" rev=stylesheet
	media=screen>
<LINK rel=stylesheet type=text/css
	href="${ctxStatic}/common/css/rili_index_homecalendar.css"
	rev=stylesheet media=screen>
<script src="${ctxStatic}/common/rili.js" type="text/javascript"></script>
</head>

<body>
	<div id="content" class="row-fluid">
		<div id="left" class="accordion-group">
			<div class="accordion-heading">
				<a class="accordion-toggle">假期类别
				
					<!--
					/**
		 			 * @reqno:H1512210075
		 			 * @date-designer:2015年12月25日-zhangzhida
		 			 * @e-ctrl : refreshButton - 刷新假期类别: 刷新假期类别
		 			 * @date-author:2015年12月25日-zhangzhida:在假期管理信息界面增加刷新假期类别按钮.点击按钮刷新假期信息树
		 			 */
					  -->
					<i class="icon-refresh pull-right" id="refreshButton" onclick="refreshTree();" data-toggle="tooltip" data-placement="bottom" title="刷新假期类别"></i>
					
					<!--
					/**
		 			 * @reqno:H1512210074
		 			 * @date-designer:2015年12月25日-zhangzhida
		 			 * @e-ctrl : deleteButton - 删除假期类别: 删除假期类别
		 			 * @date-author:2015年12月25日-zhangzhida:在假期管理信息界面增加删除假期类别按钮.点击按钮弹出确认信息框,如果确认删除则直接删除假期信息
		 			 */
					  -->
					<i class="icon-remove pull-right right" id="deleteButton" onclick="confirmx('是否要删除该假期？',removeHoliday);" data-toggle="tooltip" data-placement="bottom" title="删除假期类别"></i>
					
					<!--
					/**
		 			 * @reqno:H1512210072
		 			 * @date-designer:2015年12月25日-zhangzhida
		 			 * @e-ctrl : insertButton - 修改假期类别: 修改假期类别
		 			 * @date-author:2015年12月25日-zhangzhida:在假期管理信息界面增加修改假期类别按钮.点击按钮弹出修改假期页面
		 			 */
					  -->
					<i class="icon-edit pull-right right" id="updateButton" onclick="editHoliday();" data-toggle="tooltip" data-placement="bottom" title="修改假期类别"></i>
					
					<!--
					/**
		 			 * @reqno:H1512210070
		 			 * @date-designer:2015年12月25日-zhangzhida
		 			 * @e-ctrl : insertButton - 新增假期类别: 新增假期类别
		 			 * @date-author:2015年12月25日-zhangzhida:在假期管理信息界面增加新增假期类别按钮.点击按钮弹出新增假期页面
		 			 */
					  -->
					<i class="icon-plus pull-right right" id="insertButton" onclick="addHoliday();" data-toggle="tooltip" data-placement="bottom" title="新增假期类别" ></i>
				</a>
			</div>
			<div id="holidayTree" class="ztree"></div>
		</div>
		<div id="openClose" class="close">&nbsp;</div>
		<div id="right"  class="RiliIndex">
			<DIV id=page>
				<DIV id=bodyPart>
					<DIV style="MARGIN: 20px 50px; WIDTH: 700px" class=mt15>
						<DIV style="BACKGROUND: none transparent scroll repeat 0% 0%"
							id=calendar_container class="container clearfix">
							<DIV class=cldContentWrapper>
								<DIV class=cldContent>
									<!-- 日历月视图 -->
									<DIV id=cldGridWrapper class=cldGridWrapper>
										<DIV id=mainWrapper class=mainWrapper>
											<DIV id=mainBody>
											<!--
											/**
								 			 * @reqno:H1512210075
								 			 * @date-designer:2015年12月25日-zhangzhida
								 			 * @e-ctrl : prevMonthBtn - 上一月: 上一月
								 			 * @e-ctrl : nextMonthBtn - 下一月: 下一月
								 			 * @e-ctrl : prevYearBtn - 上一年: 上一年
								 			 * @e-ctrl : nextYearBtn - 下一年: 下一年
								 			 * @e-ctrl : dateSelectionBtn - 日历月视图选择:日历月视图选择
								 			 * @e-out-other : yearValue - 年 : 年
								 			 * @e-out-other : monthValue - 月 : 月
								 			 * @e-out-other : lunarValue - 农历年 : 农历年
								 			 * @date-author:2015年12月25日-zhangzhida:日历月视图的头部信息区.显示年月以及农历年信息
								 			 */
											  -->
												<DIV id=mainNav class=mainNavRili>
													<DIV id=prevMonthBtn class="dateNavBtnWrapperRili month"
														title=上一月 onclick="dateSelection.goPrevMonth()">
														<DIV class="t2 themeBgColorRili1">&nbsp;</DIV>
														<DIV class="getBtn dateNavBtnRili themeBgColorRili1">◄</DIV>
														<DIV class="t2 themeBgColorRili1">&nbsp;</DIV>
													</DIV>
													<DIV id=nextMonthBtn class="dateNavBtnWrapperRili month"
														title=下一月 onclick="dateSelection.goNextMonth()">
														<DIV class="t2 themeBgColorRili1">&nbsp;</DIV>
														<DIV class="getBtn dateNavBtnRili themeBgColorRili1">►</DIV>
														<DIV class="t2 themeBgColorRili1">&nbsp;</DIV>
													</DIV>
													<DIV id=prevYearBtn class="dateNavBtnWrapperRili year"
														title=上一年 onclick="dateSelection.goPrevYear()">
														<DIV class="t2 themeBgColorRili1">&nbsp;</DIV>
														<DIV class="getBtn dateNavBtnRili themeBgColorRili1">◄◄</DIV>
														<DIV class="t2 themeBgColorRili1">&nbsp;</DIV>
													</DIV>
													<DIV id=nextYearBtn class="dateNavBtnWrapperRili year"
														title=下一年 onclick="dateSelection.goNextYear()">
														<DIV class="t2 themeBgColorRili1">&nbsp;</DIV>
														<DIV class="getBtn dateNavBtnRili themeBgColorRili1">►►</DIV>
														<DIV class="t2 themeBgColorRili1">&nbsp;</DIV>
													</DIV>
													<DIV style="WIDTH: 300px; FONT-SIZE: 16px"
														class=dateNavInfoRili>
														<SPAN style="BORDER-BOTTOM: red 1px solid; COLOR: red"
															id=dateSelectionRili class=dateSelectionRili
															onclick="dateSelection.show()"> <SPAN id=yearValue></SPAN>年<SPAN
															id=monthValue></SPAN>月
														</SPAN><SPAN class=dateSelectionBtn
															onclick="dateSelection.show()">▼</SPAN> <SPAN
															id=lunarValue></SPAN>
													</DIV>
												</DIV>
												<!--
												/**
									 			 * @reqno:H1512210075
									 			 * @date-designer:2015年12月25日-zhangzhida
									 			 * @e-ctrl : yearListPrev - 上: 上
									 			 * @e-ctrl : yearListNext - 下: 下
									 			 * @e-ctrl : dateSelectionTodayBtn - 今天: 今天
									 			 * @e-ctrl : dateSelectionOkBtn - 确定: 确定
									 			 * @e-ctrl : dateSelectionCancelBtn - 取消:取消
									 			 * @e-out-other : yearListContent - 年份 : 年份
									 			 * @e-out-other : monthListContent - 月份 : 月份
									 			 * @date-author:2015年12月25日-zhangzhida:日历月视图的选择弹出框信息.用于选择年份月份.点击确定替换当前日历信息
									 			 */
												  -->
												<DIV id=dateSelectionDiv>
													<DIV id=dateSelectionHeader></DIV>
													<DIV id=dateSelectionBody>
														<DIV id=yearList>
															<DIV id=yearListPrev
																onclick="dateSelection.prevYearPage()">&lt;</DIV>
															<DIV id=yearListContent></DIV>
															<DIV id=yearListNext
																onclick="dateSelection.nextYearPage()">&gt;</DIV>
														</DIV>
														<DIV id=dateSeparator></DIV>
														<DIV id=monthList>
															<DIV id=monthListContent></DIV>
															<DIV style="CLEAR: both"></DIV>
														</DIV>
														<DIV id=dateSelectionBtn>
															<DIV id=dateSelectionTodayBtn
																onclick="dateSelection.goToday()">今天</DIV>
															<DIV id=dateSelectionOkBtn onclick="dateSelection.go()">确定</DIV>
															<DIV id=dateSelectionCancelBtn
																onclick="dateSelection.hide()">取消</DIV>
														</DIV>
													</DIV>
													<DIV id=dateSelectionFooter></DIV>
												</DIV>
												
												<!--
												/**
									 			 * @reqno:H1512210075
									 			 * @date-designer:2015年12月25日-zhangzhida
									 			 * @date-author:2015年12月25日-zhangzhida:日历信息显示区域
									 			 */
												  -->
												<DIV class="t1 themeBgColorRili"></DIV>
												<DIV class="t2 themeBgColorRili">&nbsp;</DIV>
												<DIV id=mainGrid class="mainGrid themeBgColorRili">
													<DIV id=colheadersRili></DIV>
													<DIV id=gridcontainerRili>
														<DIV id=calowner>
															<DIV id=grid class=grid>
																<DIV id=decowner></DIV>
																<DIV id=eventowner></DIV>
																<DIV id=clickowner></DIV>
																<DIV id=colowner></DIV>
																<DIV id=rowowner></DIV>
															</DIV>
														</DIV>
													</DIV>
												</DIV>
											</DIV>
										</DIV>
									</DIV>
								</DIV>
								<DIV class="t2 ">&nbsp;</DIV>
								<DIV class="t1 ">&nbsp;</DIV>
								<DIV style="CLEAR: both"></DIV>
							</DIV>
						</DIV>
						<DIV
							style="DISPLAY: none; BACKGROUND: none transparent scroll repeat 0% 0%; HEIGHT: 396px"
							id=iframeContainer class="container left">
							<DIV class="cldContentWrapper left">
								<DIV class="t1 ">&nbsp;</DIV>
								<DIV class="t2 ">&nbsp;</DIV>
								<DIV class="cldContent ">
									<DIV style="HEIGHT: 370px" class=cldGridWrapper>
										<DIV
											style="BACKGROUND-COLOR: #ffffff; MARGIN-TOP: 10px; HEIGHT: 360px"
											class=mainWrapper>
											<DIV id=iframeContent></DIV>
										</DIV>
									</DIV>
								</DIV>
								<DIV class="t2 ">&nbsp;</DIV>
								<DIV class="t1 ">&nbsp;</DIV>
								<DIV style="CLEAR: both"></DIV>
							</DIV>
						</DIV>
						<DIV style="CLEAR: both"></DIV>
					</DIV>
					<DIV id=detailDialog class="dialogRili ts328">
						<DIV class="titleWrapperRili clearfix">
							<DIV class="title right"></DIV>
						</DIV>
						<DIV class=contentWrapper>
							<DIV class=content>
								<DIV id=detail class=detailRili></DIV>
							</DIV>
						</DIV>
					</DIV>
				</DIV>
				<!--
					/**
		 			 * @reqno:H1512210075
		 			 * @date-designer:2015年12月25日-zhangzhida
		 			 * @e-in-other : detail_default_tpl - 展示信息 :展示信息
		 			 * @e-in-other : detail_iframe_tpl -  展示信息:展示信息
		 			 * @e-in-other : detail_click_tpl -  展示信息:展示信息
		 			 * @e-in-other : iframe_tpl -  展示信息:展示信息
		 			 * @e-in-other : calendar_cell_in_tpl -  展示信息:展示信息
		 			 * @e-in-other : calendar_cell_out_tpl -  展示信息:展示信息
		 			 * @date-author:2015年12月25日-zhangzhida:日历信息显示区域
		 			 */
					  -->
					<TEXTAREA id=detail_default_tpl class=tplbox>    	&lt;!---
				    	var info = arguments[0];
				    	if(!info.huangliY)
				    	info.huangliY='无';
				    	if(!info.huangliJ)
				    	info.huangliJ='无';
				    	--&gt;
				    	&lt;div class=""&gt;
				    	&lt;div class="left dateNumClass"&gt;+-info.dateNum-+&lt;/div&gt;	
				    	&lt;div class="lunar left"&gt;
				    		&lt;div style="font-size:14px;font-weight:bold;margin-top:5px;"&gt;+-info.dateDetail-+&lt;/div&gt;
				    		&lt;div style="margin-top:-10px;"&gt;+-info.lunar-+&lt;/div&gt;
				    	&lt;/div&gt;
				    	&lt;/div&gt;
				    	&lt;div style="clear:both;padding:0px 10px;"&gt;&lt;HR class="hrClass"&gt;&lt;/HR&gt;&lt;/div&gt;
				    	&lt;div class="huangli"&gt;&lt;span class="yj y"&gt;&lt;/span&gt;&lt;span class="huangliSpan"&gt;+-info.huangliY-+&lt;/span&gt;&lt;/div&gt;
				    	&lt;div style="clear:both;"&gt;&lt;/div&gt;
				    	&lt;div class="huangli"&gt;&lt;span class="yj j"&gt;&lt;/span&gt;&lt;span class="huangliSpan"&gt;+-info.huangliJ-+&lt;/span&gt;&lt;/div&gt;
				    	&lt;!---
				    	if(info.schedule){
				    	--&gt;
				    	&lt;div style="clear:both;padding:0px 10px;"&gt;&lt;HR class="hrClass"&gt;&lt;/HR&gt;&lt;/div&gt;
				    	&lt;!---
				    	for(var k=0;k&lt;(info.schedule.length&lt;2?info.schedule.length:2);k++)
						{
				    	 --&gt;
				    	 &lt;div style="margin-left:17px;color:#003399;line-height:20px;"&gt;+-info.schedule[k].notes-+&lt;/div&gt;
				    	 &lt;!---
				    	 }
				    	 }
				    	   --&gt;
				    	&lt;div style="clear:both;margin-bottom:5px;"&gt;&lt;/div&gt;
				    </TEXTAREA> 
				    <TEXTAREA id=detail_iframe_tpl class=tplbox>	&lt;!---
				    	var info = arguments[0];
				    	if(!info.huangliY)
				    	info.huangliY='无';
				    	if(!info.huangliJ)
				    	info.huangliJ='无';
				    	--&gt;
					&lt;div id="dateInfo" class="left"&gt;
				    &lt;div class="left dateNumClass1" id="dateNumIframe"&gt;+-info.dateNum-+&lt;/div&gt;
				    &lt;div class="lunar left" style="margin-left:5px;"&gt;
				    	&lt;div id="detailInfo" style="font-size:14px;font-weight:bold;margin-top:13px;"&gt;+-info.dateDetail-+&lt;/div&gt;
				    	&lt;div id="lunar" style="margin-top:10px;"&gt;+-info.lunar-+&lt;/div&gt;
				    &lt;/div&gt;
				    &lt;/div&gt;
				 
				    &lt;div style="clear:both;padding:0px 10px;"&gt;&lt;HR class="hrClass"&gt;&lt;/HR&gt;&lt;/div&gt;
					</TEXTAREA> 
					<TEXTAREA id=detail_click_tpl class=tplbox>		&lt;!--- 
						var data = arguments[0]; 
						var key = 'd'+global.currYear+'-'+(global.currMonth+1)+'-'+data.date;
						var title = cellImage[key]?cellImage[key].imageDesc:'设置节假日';
						--&gt;
						&lt;div title="+-title-+" onmouseover="indexMgr.dateover(+-data.index-+,+-data.date-+,event)" 
						onmouseout="indexMgr.dateout(+-data.index-+,event)" 
						onclick="showIframeRili(+-data.date-+)" 
						class="click" style="left: +-data.left-+px;top: +-data.top-+px;width: +-data.width-+px;height: +-data.height-+px;"&gt;&lt;/div&gt;
				 
					</TEXTAREA> 
					<TEXTAREA id=iframe_tpl class=tplbox>	&lt;!---
					 var iframe_params = arguments[0];
					 var dateInfo = arguments[1];
					 var dateStr = escape(JSON.stringify(dateInfo));
					 var keySrc="";
					 if(Number(iframe_params.sYear)&gt;2012||Number(iframe_params.sYear)&lt;2010){
						keySrc="/dayList/rili.html?dateInfo="+dateStr;
					 }
					 else keySrc="/dayList/"+iframe_params.key+".html?dateInfo="+dateStr;           //keySrc="/dayList/"+iframe_params.key+".html";
					 --&gt;
					&lt;iframe style="width:565px;height:420px;background-color:#ffffff;" src="+-keySrc-+" scrolling="no" FrameBorder="0"&gt;
					&lt;/iframe&gt;
					</TEXTAREA> 
					<TEXTAREA id=calendar_cell_in_tpl class=tplbox>		&lt;!---
						var data = arguments[0];
						//alert('d'+global.currYear+'-'+(global.currMonth+1)+'-'+data.dateNum);
						var key = 'd'+global.currYear+'-'+(global.currMonth+1)+'-'+data.dateNum;
						if(cellImage[key])
						{
						--&gt;
						&lt;div title="+-cellImage[key].imageDesc-+"  onclick="showIframeRili(+-data.dateNum-+)" onmouseover="indexMgr.dateover(+-data.index-+,+-data.dateNum-+,event)" onmouseout="indexMgr.dateout(+-data.index-+,event)" style="background:url(+-cellImage[key].imageURL-+);background-repeat:no-repeat;background-position:center center;position: absolute; left: +-data.left-+%; top: +-data.top-+%; width: +-data.width-+%; height: +-data.height-+%; z-index: 3;"&gt;
							&lt;div id="rg_rowy_h+-data.index-+" style="color:#ffffff;font-size:12px;font-weight:bold;overflow:hidden;cursor:pointer;" class="dayOfMonthRili dayOfMonthWithoutBg dayInMonth+-data.isToday ? '  currentDayRili' : ''-+" &gt;
								&nbsp;+-cellImage[key].imageDesc-+
							&lt;/div&gt;
							&lt;div class="dateNum" style="color:#ffffff;font-size:20px;"&gt;+-data.dateNum-+&lt;/div&gt;
						&lt;/div&gt;
						&lt;!---
						}
						else
						{
						--&gt;
						&lt;div title="设置节假日" style="position: absolute; left: +-data.left-+%; top: +-data.top-+%; width: +-data.width-+%; height: +-data.height-+%;cursor:pointer; z-index:1;" onclick="showIframeRili(+-data.dateNum-+)" &gt;
				 
							&lt;div id="rg_rowy_h+-data.index-+"   class="dayOfMonthRili dayInMonth+-data.isToday ? '  currentDayRili' : ''-+" style="color:+-data.lunarColor-+;cursor:pointer;"&gt;
								&nbsp;+-data.lunar-+
							&lt;/div&gt;
							&lt;div id="rg_cell_h+-data.dateNum-+"   class="dateNum"&gt;+-data.dateNum-+&lt;/div&gt;
						&lt;/div&gt;
						&lt;!---
						}
						--&gt;
					</TEXTAREA> 
					<TEXTAREA id=calendar_cell_out_tpl class=tplbox>		&lt;!---
						var data = arguments[0];
						--&gt;
						&lt;div class="dayNotInMonthWrapper" style="position: absolute; left: +-data.left-+%; top: +-data.top-+%; width: +-data.width-+%; height: +-data.height-+%; z-index: 1;"&gt;&lt;/div&gt;
					</TEXTAREA> 
					<TEXTAREA id=calendar_cell_today_tpl class=tplbox>		&lt;!---
						var data = arguments[0];
						--&gt;
						&lt;div style="position: absolute; left: +-data.left-+%; top: +-data.top-+%; width: +-data.width-+%; height: +-data.height-+%; z-index: 0;"&gt;
							&lt;div class="currentDayDec"&gt;&lt;/div&gt;
						&lt;/div&gt;
					</TEXTAREA> 
				<DIV id=feedbackDialog class="dialog ts428">
					<DIV class=top></DIV>
					<DIV class="titleWrapper clearfix">
						<DIV class="title left"></DIV>
						<DIV class="close right" title=关闭 onclick="dialogMgr.hide()"></DIV>
					</DIV>
					<DIV class=contentWrapper>
						<DIV class=content>
							<DIV id=feedbackDialogContent class=loginDialogContent></DIV>
						</DIV>
					</DIV>
					<DIV class=bottom></DIV>
				</DIV>
			</DIV>
		</div>
	</div>
	
	<!--
	/**
		 * @reqno:H1512210070
		 * @date-designer:2015年12月25日-zhangzhida
		 * @e-in-text : holidayName - 假期名称: 假期名称
		 * @e-in-list : enable - 是否启用: 是否启用
		 * @e-in-text : remark - 备注: 备注
		 * @e-ctrl : saveButton - 确认按钮: 确认按钮
		 * @e-ctrl : backButton - 返回按钮: 返回按钮
		 * @date-author:2015年12月25日-zhangzhida:点击新增假期类别按钮的响应的弹出窗体.输入假期名称,选择是否可用,备注信息.保存信息
		 */
	  -->
	<div class="modal fade" id="holidayDialog" tabindex="-1" role="dialog" aria-labelledby="123" aria-hidden="true" style="display:none">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title" id="holidayTitle">新增假期类别</h4>
				</div>
				<div class="modal-body">
					<form  role="form">	
					<!--
					/**
						 * @reqno:H1512210070
						 * @date-designer:2015年12月25日-zhangzhida
						 * @e-out-other : holidayId - 假期编号: 假期编号
						 * @e-out-other : holidayName - 假期名称: 假期名称
						 * @e-in-list : enable - 是否启用: 是否启用
						 * @e-in-text : remark - 备注: 备注
						 * @e-ctrl : saveButton - 确认按钮: 确认按钮
						 * @e-ctrl : backButton - 返回按钮: 返回按钮
						 * @date-author:2015年12月25日-zhangzhida:点击修改假期类别按钮的响应的弹出窗体.修改是否可用,备注信息.保存信息
						 */
					  -->
						<input type="hidden" id="holidayId" name="holidayId">
						<div class="form-group">
							<label for="holidayName" class="control-label">假期名称:</label>
							<input type="text"  class="form-control control-text span4" id="holidayName" name="holidayName" >&nbsp;<font color="red">*</font>
						</div>
						<div class="form-group">
							<label for="enable" class="control-label">是否启用:</label>
							<select class="form-control required control-text " id="enable" name="enable" style="width: 300px" >
								<option value="0">启用</option>
								<option value="1">不启用</option>
							</select>
						</div>
						<div class="form-group">
							<label for="remark" class="control-label">备注:</label>
							<textarea rows="4"  class="form-control control-text" id="remark" name="remark" style="width: 300px; height: 80px;"></textarea>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" id="saveButton" onclick="saveOrUpdateHoliday();">确定</button>
					<button type="button" class="btn btn-default" id="backButton" data-dismiss="modal">返回</button>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var holidayType = '';
		var holidayArray = '';
		var dateObj = new Date();
		var currYear = dateObj.getFullYear();
		var currMonth = dateObj.getMonth();
		var selectNode;
		/**
		 * @reqno:H1512210075
		 * @date-designer:2015年12月25日-zhangzhida
		 * @date-author:2015年12月25日-zhangzhida:设置假期树的配置项,设置click事件.数据获取地址等信息
		 */
		var treeOptions = {
			onClick : function(event, treeId, treeNode) {//定义单机事件的方法
				var id = treeNode.id == '0' ? '' : treeNode.id;
				if(id != 'u_root'){
					selectNode = treeNode;
					holidayType = id;
					refreshRiliIndex(currYear,currMonth);
				}else{
					selectNode = null;
					holidayType = null;
					refreshRiliIndex(currYear,currMonth);
				}
			},
			dataUrl : "${ctx}/sys/holiday/buildTree",//获取数据的url
			isOpen : false,//是否展开全部
			openLevel : 1,//展开一级
			openNumber : 1,//每层展开第一个节点
			div : $("#holidayTree"),//生成结构树所在的div
			addflag : false,
			addTreeNode : 0,
			selectFirst : true
		};
		/**
		 * @reqno:H1512210075
		 * @date-designer:2015年12月25日-zhangzhida
		 * @date-author:2015年12月25日-zhangzhida:创建假期类别树
		 */
		$.fn.mmInfoZtree(treeOptions);//调用ztree组件方法生成结构树
		
		
		var leftWidth = 200; // 左侧窗口大小
		var htmlObj = $("html"), mainObj = $("#main");
		var frameObj = $("#left, #openClose, #right, #right iframe");
		/**
		 * @reqno:H1512210075
		 * @date-designer:2015年12月25日-zhangzhida
		 * @date-author:2015年12月25日-zhangzhida:设置页面布局.调整左边树与中间日历组件的显示位置
		 */
		function wSize() {
			var strs = getWindowSize().toString().split(",");
			htmlObj.css({
				"overflow-x" : "hidden",
				"overflow-y" : "hidden"
			});
			mainObj.css("width", "auto");
			frameObj.height(strs[0] - 5);
			var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
			$("#right").width(
					$("#content").width() - leftWidth - $("#openClose").width()
							- 5);
			$(".holidayTree").width(leftWidth - 10).height(
					frameObj.height() - 46);
		}
		
		/**
		 * @reqno:H1512210075
		 * @date-designer:2015年12月25日-zhangzhida
		 * @date-author:2015年12月25日-zhangzhida:初始化日历控件的常量信息
		 */
		var FIX_COLOR = '#8bcf1c';
		var FIX_COLORADD = '#00c';
		var FIX_COLORREMORE = '#f00';
		var SHOW_HOLIDAY_MONTH = true;

		var cellImage = {};
		initRiliIndex();
		
		/**
		 * @reqno:H1512210075
		 * @date-designer:2015年12月25日-zhangzhida
		 * @date-author:2015年12月25日-zhangzhida:当点击刷新假期类别按钮的时候.调用refreshTree方法.重新实例化假期信息树
		 */
		function refreshTree(){
			$.fn.mmInfoZtree(treeOptions);
		}
		
		/**
		 * @reqno:H1512210075
		 * @date-designer:2015年12月25日-zhangzhida
		 * @date-author:2015年12月25日-zhangzhida:页面初始加载时.检索所有组件中包含data-toggle='tooltip'属性的组件,初始化提示信息
		 										将是否可用的下拉框选择框隐藏掉不显示
		 */
		$(function (){
			$("[data-toggle='tooltip']").tooltip();
			 $("#enable").select2({
					minimumResultsForSearch: -1
			 });
		});
		
		/**
		 * @reqno:H1512210070
		 * @date-designer:2015年12月25日-zhangzhida
		 * @date-author:2015年12月25日-zhangzhida:当点击新增假期类别按钮的时候.调用addHoliday方法
		 										设置假期编号,假期名称,是否可用,备注的值为空.调用show方法弹出新增窗体
		 */
		function addHoliday(){
			$("#holidayId").val("");
			$("#holidayName").val("");
			$("#enable").val("");
			$("#remark").val("");
			$(".select2-chosen").html("启用");
			$("#holidayDialog").modal("show");
		}
		
		/**
		 * @reqno:H1512210072
		 * @date-designer:2015年12月25日-zhangzhida
		 * @e-in-other : selectNode - 选中假期节点:选中假期节点
		 * @date-author:2015年12月25日-zhangzhida:当点击修改假期类别按钮的时候.调用editHoliday方法
		 										验证是否selectNode是否为空.为空提示"请选择一个假期类型!"
		 										不为空则设置假期编号,假期名称,是否可用,备注的值.调用show方法弹出修改窗体
		 */
		function editHoliday(){
			if(selectNode != null && selectNode != undefined){
				$("#holidayId").val(selectNode.holidayId);
				$("#holidayName").val(selectNode.name);
				var typeText = $("#enable option[value='"+selectNode.enable+"']").text();
				$(".select2-chosen").html(typeText);
				$("#remark").val(selectNode.remark);
				$("#holidayDialog").modal("show");
			}else{
				alertx("请选择一个假期类型!");
			}
			
		}
		
		/**
		 * @reqno:H1512210074
		 * @date-designer:2015年12月25日-zhangzhida
		 * @e-in-other : selectNode - 选中假期节点:选中假期节点
		 * @date-author:2015年12月25日-zhangzhida:当点击删除假期类别按钮的时候.确认删除后,调用removeHoliday方法
		 										验证是否selectNode是否为空.为空提示"请选择一个假期类型!"
		 										不为空将选中的假期节点作为参数调用删除action.删除成功刷新假期树.失败弹出失败信息
		 */
		function removeHoliday(){
			if(selectNode != null && selectNode != undefined){
				$.post("${ctx}/sys/holiday/delete", {holidayId:selectNode.holidayId}, function (data) {
					var result = data.result;
					var message = data.message;
					if(result == "ok"){
						selectNode = null;
						refreshTree();
					}else if(result == "error"){
						alertx(message);
					}
				});
			}else{
				alertx("请选择一个假期类型!");
			}
		}
		
		/**
		 * @reqno:H1512210070
		 * @date-designer:2015年12月25日-zhangzhida
		 * @date-author:2015年12月25日-zhangzhida:获取假期编号(新增没有),假期名称,是否可用.备注信息的值作为参数调用编辑action.
		 */
		function saveOrUpdateHoliday(){
			var holidayId = $("#holidayId").val();
			var holidayName = $("#holidayName").val();
			var enable = $("#enable").val();
			var remark = $("#remark").val();
			
			if(holidayName == undefined || holidayName == null || holidayName == ""){
		  		alertx("请填写假期名称");
				return;
			}
			
			if(holidayName.length > 10){
		  		alertx("假期名称的长度应介于1-10");
				return;
			}
			if(remark.length > 250){
				alertx("备注信息的长度应介于1-250");
				return;
			}
			/**
			 * @reqno:H1512210072
			 * @date-designer:2015年12月25日-zhangzhida
			 * @date-author:2015年12月25日-zhangzhida:获取假期编号,假期名称,是否可用.备注信息的值作为参数调用编辑action.
			 */
			$.post("${ctx}/sys/holiday/edit", {holidayId:holidayId,holidayName : holidayName,enable : enable,remark : remark}, function (data) {
				var result = data.result;
				var message = data.message;
				/**
				 * @reqno:H1512210070
				 * @date-designer:2015年12月25日-zhangzhida
				 * @date-author:2015年12月25日-zhangzhida:隐藏弹出窗体信息.保存成功刷新假期树.失败弹出失败信息
				 */
				if(result == "ok"){
					$("#holidayDialog").modal("hide");
					refreshTree();
					selectNode = null;
				}else if(result == "error"){
					alertx(message);
				}
			});
		}
	</script>
	<input type="hidden" id="unAudit" name="unAudit" value="" />
	<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>
