<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="en">
<head>
	<meta charset="utf-8" />
	<title>${fns:getConfig('copyright.productName')}</title>
	<meta name="decorator" content="blank"/>
	<meta name="description" content="overview &amp; stats" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />

	<!--basic styles-->
	<%-- 	<link href="${themeStatic}/ace_admin/css/bootstrap-responsive.min.css" rel="stylesheet" /> --%>
	<link rel="stylesheet" href="${themeStatic}/ace_admin/css/font-awesome.min.css" />

	<!--ace styles-->
	<link rel="stylesheet" href="${themeStatic}/ace_admin/css/ace.min.css" />
	<%-- 		<link rel="stylesheet" href="${themeStatic}/ace_admin/css/ace-responsive.min.css" />
            <link rel="stylesheet" href="${themeStatic}/ace_admin/css/ace-skins.min.css" /> --%>

	<!--ace scripts-->
	<script src="${themeStatic}/ace_admin/js/ace-elements.min.js"></script>
	<script src="${themeStatic}/ace_admin/js/ace.min.js"></script>

	<!--easyui scripts 用窗口功能-->
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/jquery-easyui-1.4.2/themes/bootstrap/window.css">
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/jquery-easyui-1.4.2/themes/bootstrap/panel.css">
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/jquery-easyui-1.4.2/themes/icon.css">
	<script type="text/javascript" src="${ctxStatic}/jquery-easyui-1.4.2/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${ctxStatic}/jquery-easyui-1.4.2/locale/easyui-lang-zh_CN.js"></script>

	<!--拖拽功能-->
	<script src="${themeStatic}/ace_admin/js/jquery.dragsort-0.5.1.js"></script>
	<link rel="stylesheet" href="${themeStatic}/ace_admin/css/dragsort.css" />
	<link rel="Stylesheet" href="${ctxStatic}/jerichotab/css/jquery.jerichotab.css" />
	<script type="text/javascript" src="${ctxStatic}/jerichotab/js/jquery.jerichotab.js"/>


	<script type="text/javascript">
        var documentHeight = 0;
        var topPadding = 15;
        /* window.location.href = "${pageContext.request.contextPath}/changetheme/default?url=${pageContext.request.contextPath}"; */
        $(function(){
            if(window.navigator.userAgent.toUpperCase().indexOf("MSIE")>0){//验证是否符合该主题的浏览器版本
                var bowerType = window.navigator.userAgent.toUpperCase().match(/msie [\d.]+;/gi);
                if(parseInt(bowerType.toString().substring(5)) < 8 ){
                    alert("您当前的浏览器版本不支持此主题，点击确定后将切换到默认主题！");
                    window.location.href = "${pageContext.request.contextPath}/changetheme/default?url=${pageContext.request.contextPath}";
                }
            }
        });

        //text



	</script>

	<!--inline styles related to this page-->
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /></head>

<body>
<div id="main">
	<div id="header" class="navbar navbar-fixed-top">
		<div  class="navbar-inner">

			<div style="margin-top: -20px; height: 45px;" class="brand" ><span id="productName"><img src="${ctxStatic}/images/logo_test.png"/></span></div>
			<ul class="nav ace-nav pull-right">
				<li>
					<div class="nav-search" id="nav-search">
						<form class="form-search" />
						<!-- <span class="input-icon">
                            <input type="text" placeholder="搜索 ..." class="input-small nav-search-input" id="nav-search-input" autocomplete="off" />
                            <i class="icon-search nav-search-icon"></i>
                        </span> -->
						</form>
					</div>
				</li>
				<!-- <li class="grey">
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#" title="公告">
                        <i class="icon-envelope icon-animated-vertical"></i>
                        <span class="badge badge-grey">1</span>
                    </a>
                    <ul class="pull-right dropdown-navbar dropdown-menu dropdown-caret dropdown-closer">
                        <li class="nav-header">
                            <i class="icon-ok"></i>
                            您有1条公告信息
                        </li>
                        <li>
                            <a href="#">
                                <div class="clearfix">
                                    <span class="pull-left">对于成都分行数据治理滞后通报批评</span>
                                </div>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                查看详情
                                <i class="icon-arrow-right"></i>
                            </a>
                        </li>
                    </ul>
                </li> -->


				<!-- 		<li class="purple">
							<a data-toggle="dropdown" class="dropdown-toggle" href="#" title="消息">
								<i class="icon-bell-alt icon-animated-bell"></i>
								<span class="badge badge-important">1</span>
							</a>

							<ul class="pull-right dropdown-navbar navbar-pink dropdown-menu dropdown-caret dropdown-closer">
								<li class="nav-header">
									<i class="icon-warning-sign"></i>
									您有1条新消息
								</li>

								<li>
									<a href="#">
										<div class="clearfix">
											<span class="pull-left">
												&nbsp;&nbsp;您有[24]笔数据审核任务今日到期 
											</span>
										</div>
									</a>
								</li>
								<li>
									<a href="#">
										See all notifications
										<i class="icon-arrow-right"></i>
									</a>
								</li>
							</ul>
						</li> -->

				<li class="green">
					<a data-toggle="dropdown" class="dropdown-toggle" href="#" title="待办任务">
						<i class="icon-tasks"></i>
						<span class="badge badge-success">3</span>
					</a>
					<ul class="pull-right dropdown-navbar dropdown-menu dropdown-caret dropdown-closer">
						<li class="nav-header">
							<i class="icon-ok"></i>
							您有3个待办任务
						</li>
						<li>
							<a href="#">
								<div class="clearfix">
									<span class="pull-left">苏州苏福市政工程公司</span>
								</div>
							</a>
						</li>
						<li>
							<a href="#">
								查看详情
								<i class="icon-arrow-right"></i>
							</a>
						</li>
					</ul>
				</li>
				<%-- 				<li id="themeSwitch" class="dropdown">
                                    <a class="dropdown-toggle" data-toggle="dropdown" href="#" title="主题切换"><i class="icon-th-large"></i></a>
                                    <ul class="dropdown-menu">
                                        <c:forEach items="${fns:getDictList('theme')}" var="dict">
        <!-- 									<li><a href="#" onclick="location='${pageContext.request.contextPath}/a?theme=${dict.value}'">${dict.label}</a></li> -->
                                            <li><a href="#" onclick="location='${pageContext.request.contextPath}/changetheme/${dict.value}?url=${pageContext.request.contextPath}'">${dict.label}</a></li>
                                        </c:forEach>
                                        <li class="divider"></li>
                                        <li><a id="editMTheme" href="#" onclick="editTheme();">开启编辑模式</a></li>
                                    </ul>
                                    <!--[if lte IE 6]><script type="text/javascript">$('#themeSwitch').hide();</script><![endif]-->
                                </li> --%>
				<li class="light-blue">
					<a data-toggle="dropdown" href="#" class="dropdown-toggle">
						<img class="nav-user-photo" src="${fns:getUser().photo}" alt="${fns:getUser().name} Photo" />
						<span class="user-info">
									<small>您好,</small>
									${fns:getUser().name}
								</span>
						<i class="icon-caret-down"></i>
					</a>
					<ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-closer">
						<li><a href="${ctx}/sys/user/info" target="mainFrame"><i class="icon-user"></i>&nbsp; 个人信息</a></li>
						<li><a href="${ctx}/sys/user/modifyPwd" target="mainFrame"><i class="icon-lock"></i>&nbsp;  修改密码</a></li>
						<li class="divider"></li>
						<li><a href="${ctx}/logout" title="退出登录"><i class="icon-off"></i>&nbsp; 退出 </a></li>
					</ul>
				</li>
			</ul><!--/.ace-nav-->
		</div><!--/.container-fluid-->


	</div><!--/.navbar-inner-->
</div>

<div   class="main-container container-fluid">
	<a class="menu-toggler" id="menu-toggler" href="#">
		<span class="menu-text"></span>
	</a>

	<div id="textDiv"></div>
	<div class="sidebar" id="sidebar">
		<!--菜单导航-->
		<ul class="nav nav-list" id="menuList">
		</ul>
		<!--/.nav-list-->

		<div class="sidebar-collapse" id="sidebar-collapse">
			<i class="icon-double-angle-left"></i>
		</div>
	</div>


	<div class="main-content">

		<!--
         * @reqno: H1507020024
         * @date-designer:20150707-zhunan
         * @e-out-other : UserModule - 列表 : 展示自定义首页内容列表
         * @e-out-other : ifram - 展示页面 : 展示同的插件用
         * @e-out-other : figcaption - 标题 : 用来拖拽的标题
         * @date-author:20150707-zhunan: 用此列表对其动态添加，添加不同的插件，展示自定义首页内容
        -->
		<!--自定义首页-->
		<div>
			<ul id="UserModule" class="list listUl">
			</ul>
		</div>


		<!-- save sort order here which can be retrieved on server on postback -->
		<div>
			<input name="list1SortOrder" type="hidden" />
		</div>

		<!-- 	<div class="ace-settings-container" id="ace-settings-container">
                <div class="btn btn-app btn-mini btn-warning ace-settings-btn" id="ace-settings-btn">
                    <i class="icon-cog bigger-150"></i>
                </div>

                <div class="ace-settings-box" id="ace-settings-box">
                    <div> -->
		<!-- 							<div id="themeSwitch" class="dropdown"> -->
		<!-- 								<a class="dropdown-toggle" data-toggle="dropdown" href="#" title="主题切换"> -->
		<!-- 								<i class="icon-th-large"></i><label class="lbl" for="ace-settings-header">&nbsp; 切换主题</label></a> -->
		<!-- 								<ul class="dropdown-menu" style="margin:2px -10px 0px"> -->
		<!-- 									<c:forEach items="${fns:getDictList('theme')}" var="dict"> -->
		<!-- 										<li><a href="#" onclick="location='${pageContext.request.contextPath}/a?theme=${dict.value}'">${dict.label}</a></li> -->
		<!-- 									</c:forEach> -->
		<!-- 								</ul> -->

		<!-- 							</div> -->
		<!-- 							<label class="lbl" for="ace-settings-header"></label> -->
		<!-- 						</div> -->
		<!-- <div>
            <input type="checkbox" class="ace-checkbox-2" id="ace-settings-rtl" />
            <label class="lbl" for="ace-settings-rtl"> 菜单左右切换 </label>
        </div> -->
		<!-- <div>
            <div>
                <a href="#" onclick="openWindow()" title="主题切换">
                    <label class="lbl" for="ace-settings-header">&nbsp; 公用组件库</label>
                </a>

                 * @reqno: H1507020035
                 * @date-designer:20150707-zhunan
                 * @e-out-other : commonWindo - 窗口 : 打开的公共组建窗口
                 * @e-out-other : FreeCommonModule - 列表 : 存储公共组建的ul
                 * @e-out-other : ifram - 展示页面 : 展示同的插件用
                 * @e-out-other : figcaption - 标题 : 用来拖拽的标题
                 * @date-author:20150707-zhunan: 打开公用组建的窗口div

                没有用到的公共插件
                <div id="commonWin" class="easyui-window"
                    data-options="title:'公用组件库' ,modal:true, minimizable:false, maximizable:false, collapsible:false, closed:true"
                    style="width: 800px; height:500px; padding:2px;">
                    <div>
                        <ul id="FreeCommonModule" class="list2 listUl"></ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>--><!--/#ace-settings-container-->
	</div><!--/.main-content-->
</div><!--/.main-container-->

<div style="color:#333">
	<div id="openClose" class="close">&nbsp;</div>
	<div id="right" class="rightclose" style="">
		<!--  
			<iframe id="mainFrame" name="mainFrame" width="1875" height="820"></iframe>
			-->
	</div>
</div>

<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-small btn-inverse">
	<i class="icon-double-angle-up icon-only bigger-110"></i>
</a>

<!--inline scripts related to this page-->

<script type="text/javascript">
    document.cokkie="tabmode=1";
    var Module = null;
    var userModule = null;//存储用户主题插件内容
    var commonModule = null;//存储公共主题插件内容
    var isEdit = false;//编辑模式开关，默认是关闭状态

    //判断是否是编辑模式，如果保存编辑结果，则关闭编辑模式
    function editTheme(){
        if(isEdit==false){//开启编辑模式
            top.$.jBox.tip('开启编辑模式！');
            $('#editMTheme').text('保存并关闭编辑模式');
            isEdit = true;
            $("figcaption").removeClass("titleCss");
// 					Module.startDrag();
        }else{

            /**
             * @reqno: H1507020033
             * @date-designer:20150707-zhunan
             * @e-out-other : UserModule - 列表 : 展示自定义首页内容列表
             * @e-out-other : ifram - 展示页面 : 展示同的插件用
             * @e-out-other : figcaption - 标题 : 用来拖拽的标题
             * @db-j : sys_config : USER_ID,CONFIG_ID,CONFIG_TYPE，CONFIG_VALUE，CONFIG_NAME，REMARKS
             * @date-author:20150707-zhunan: 把用户配置好的页面参数保存到sys_config表中，每个用户对应一条config_type='CommonModule'的数据，此数据存的值为公共插件的ID
             */

            {//保存用户的偏好
                var list = $('[id=UserModule]').find('li');

                if(list==null || list.length == 0){
                    alert("您必须选择一个组建才能保存！");
                    return;
                }

                loading('正在保存，请稍等...');

                var ids = "";
                for(var i=0;i<list.length;i++){
                    if(i!=0){
                        ids += ",";
                    }
                    ids += list[i].id;
                }
                $.ajax({
                    type: "POST",
                    url: "${ctx}/theme/theme/saveModel",
                    data:{ids: ids},
                    datatype: 'json',
                    success: function(result){
                        $.jBox.tip('保存成功！', 'success');

                        $('#editMTheme').text('开启编辑模式');
                        isEdit = false;
                        $("figcaption").addClass("titleCss");
                    }
                });
            }
        }

    }

    /**
     * @reqno: H1507020035
     * @date-designer:20150707-zhunan
     * @e-out-other : commonWindo - 窗口 : 打开的公共组建窗口
     * @e-out-other : FreeCommonModule - 列表 : 存储公共组建的ul
     * @e-out-other : ifram - 展示页面 : 展示同的插件用
     * @e-out-other : figcaption - 标题 : 用来拖拽的标题
     * @date-author:20150707-zhunan: 打开公用组建的窗口调用函数
     */
    function openWindow(){//打开公共组建窗口
        if(isEdit==true){
            $('#commonWin').window('open');
        }else{
            alert('请先开启编辑模式！');
        }
    }

    $("ul:first").dragsort();

    /**
     * @reqno: H1507020024
     * @date-designer:20150707-zhunan
     * @e-out-other : UserModule - 列表 : 展示自定义首页内容列表
     * @e-out-other : ifram - 展示页面 : 展示同的插件用
     * @e-out-other : figcaption - 标题 : 用来拖拽的标题
     * @date-author:20150707-zhunan: 用此列表对其动态添加，添加不同的插件，展示自定义首页内容
     */
    /**
     * 初始化主题
     */
    function innerTheme() {

        //window.location.href="${ctx}/credit/appoint/tasktodo";
        /* 	loading('正在初始化布局页面，请稍等...'); */
        $.ajax({
            type: "POST",
            url: "${ctx}/theme/theme/getModel",
            datatype: 'json',
            success: function(result){

                if(result==null || result.userModule == null || result.commonModule == null){
                    top.$.jBox.messager('初始化失败！请联系管理员','失败！');
                    return;
                }

                userModule = result.userModule;//用户所选的插件
                commonModule = result.commonModule;//其余公共插件

                /**
                 * @reqno: H1507020035
                 * @date-designer:20150707-zhunan
                 * @e-out-other : commonWindo - 窗口 : 打开的公共组建窗口
                 * @e-out-other : FreeCommonModule - 列表 : 存储公共组建的ul
                 * @e-out-other : ifram - 展示页面 : 展示同的插件用
                 * @e-out-other : figcaption - 标题 : 用来拖拽的标题
                 * @db-j : sys_config : USER_ID,CONFIG_ID,CONFIG_TYPE，CONFIG_VALUE，CONFIG_NAME，REMARKS 取config_type='CommonModule'对应客户的数据
                 * @db-j : sys_common_module : MODULE_ID，MODULE_NAME，MODULE_SYSTEM....公共组建存储表
                 * @date-author:20150707-zhunan: 根据sys_config表config_type='CommonModule',user_id=当前用户和sys_common_module表的组建信息生成客户上次保存过的首页页面插件信息，渲染到首页中
                 */
                    //初始化用户主题插件
                var UserModuleHtml = '';
                for(var key in userModule){
                    UserModuleHtml += createNode(userModule[key],"iframe");
                }
                $('#UserModule').append(UserModuleHtml);

                /**
                 * @reqno: H1507020035
                 * @date-designer:20150707-zhunan
                 * @e-out-other : commonWindo - 窗口 : 打开的公共组建窗口
                 * @e-out-other : FreeCommonModule - 列表 : 存储公共组建的ul
                 * @e-out-other : ifram - 展示页面 : 展示同的插件用
                 * @e-out-other : figcaption - 标题 : 用来拖拽的标题
                 * @db-j : sys_common_module : MODULE_ID，MODULE_NAME，MODULE_SYSTEM....公共组建存储表
                 * @date-author:20150707-zhunan: 渲染客户没有选取的公共插件内容通过
                 */
                    //初始化公共插件
                var FreeCommonModuleHtml = '';
                for (var key in commonModule) {
                    FreeCommonModuleHtml += createNode(commonModule[key],"img");
                }
                $('#FreeCommonModule').append(FreeCommonModuleHtml);
                $("figcaption").addClass("titleCss");//去掉标题

                //初始化拖拽模块
                Module = $("#UserModule, #FreeCommonModule").dragsort({
                    dragSelector : "figcaption",
                    dragBetween : true,
                    firstDragBetween : true,
                    dragEnd : moveModuleNode,
                    dragStart: dragStart,
                    placeHolderTemplate : "<li class='placeHolder'><div class=\"Listdiv\"></div></li>",
                    scrollSpeed: 0
                });

                top.$.jBox.tip('初始化成功！', 'success');
            }
        });
    }


    /**
     * @reqno: H1507020024
     * @date-designer:20150707-zhunan
     * @e-out-other : commonWindo - 窗口 : 打开的公共组建窗口
     * @e-out-other : FreeCommonModule - 列表 : 存储公共组建的ul
     * @e-out-other : ifram - 展示页面 : 展示同的插件用
     * @e-out-other : figcaption - 标题 : 用来拖拽的标题
     * @date-author:20150707-zhunan: 生成节点
     @param module 节点数据
     @param type 插入个性化类型
     */
    function createNode(module,type){
        if(module==null){
            return "";
        }
        var html = "<li id=\"" + module.MODULE_ID + "\" data-colspan=\"" + module.MODULE_WIDTH + "\" >" /*data-rowspan=\"" + module.MODULE_HEIGHT + "\"*/
            + "<div class=\"grid\"><div class=\"effect-zoe\"><div class=\"Listdiv\" ><figcaption>" /*data-rowspan=\"" + module.MODULE_HEIGHT + "\"*/
            + module.MODULE_NAME;
        if(type!=null && type == "iframe"){
            html += "<a href=\"javascript:removeUserModuleNode('"+ module.MODULE_ID + "')\"></a>"
                + "</figcaption>"
                + createIframe(module);
        }else if(type!=null && type == "img"){
            html += "</figcaption><img src=\"${themeStatic}/" + module.MODULE_IMG + "\" />";
        }else{
            html += "</figcaption>";
        }
        html += "</div></div></li>";
        return html;
    }
    /**
     * @reqno: H1507020024
     * @date-designer:20150707-zhunan
     * @e-out-other : commonWindo - 窗口 : 打开的公共组建窗口
     * @e-out-other : FreeCommonModule - 列表 : 存储公共组建的ul
     * @e-out-other : ifram - 展示页面 : 展示同的插件用
     * @e-out-other : figcaption - 标题 : 用来拖拽的标题
     * @date-author:20150707-zhunan: 生成iframe
     @param module 节点数据
     */
    function createIframe(module){
        return 	"<iframe id=\"iframe_" + module.MODULE_ID + "\" src=\"${ctx}/" + module.MODULE_HREF
            + "\" frameborder=\"0\" scrolling=\"auto\" width=\"100%\" height=\"100%\"/>";
    }

    /**
     移动点击移动时候执行的函数，返回true为继续执行移动
     */
    function dragStart(data,thisData){
        if(isEdit==false){//如果不是编辑模式，不能进行移动
            return false;
        }

        //移动节点时候删除iframe，否则会很慢
        var iframe = thisData.draggedItem.find('iframe');
        if(iframe.length > 0){
            iframe.remove();
        }
        return true;
    }

    /**
     * 移动节点
     */
    function moveModuleNode(data, thisData) {
        var from = $(data).index(thisData);
        var to = thisData.getItems().index(thisData.draggedItem);
        var thisNode = thisData.draggedItem;

        var id = thisNode.attr('id');
        if (from == '1' && to == '-1') {
            thisNode.find('img').remove();//删除对应的图片
            //添加关闭标签
            thisNode.find('figcaption').append(
                "<a href=\"javascript:removeUserModuleNode('" + id + "')\"></a>");
            //添加iframe
            thisNode.find('.Listdiv').append(createIframe(commonModule[id]));
            userModule[id] = commonModule[id];//公共主题插件转到用户主题下
            delete commonModule[id];//从公用主题中删除用户拖拽走的插件
        }else if(from == '0' && to == '-1'){
            thisNode.find('iframe').remove();//删除对应的iframe
            thisNode.find('a').remove();//删除对应的a标签
            thisNode.find('.Listdiv').append("<img src=\"${themeStatic}/" + userModule[id].MODULE_IMG + "\" />");//添加缩略图
            commonModule[id] = userModule[id];//用户主题插件转到公共主题下
            delete userModule[id];//从公共主题中删除公共拖拽走的插件
        }else if(from == '0'){
            thisNode.find('.Listdiv').append(createIframe(userModule[id]));
        }
    };

    /**
     * 删除用户节点
     * @param id 要被删除的节点ID
     */
    function removeUserModuleNode(id) {
        if(isEdit==false){
            alert('请先开启编辑模式！');
            return;
        }

        var text = $('#' + id).text();
        $('#' + id).remove();
        $('#FreeCommonModule').append(createNode(userModule[id],"img"));
        commonModule[id] = userModule[id];//用户主题插件转到公共主题下
        delete userModule[id];//删除用户主题对应的ID
    }

    $(function() {
        /**
         * @reqno: H1507020024
         * @date-designer:20150707-zhunan
         * @date-author:20150707-zhunan: 初始化主题调用sql
         */
        innerTheme();//初始化主题控件

        initMenu();//初始化菜单

    });

    /**
     * 初始化菜单
     */
    function initMenu(){
        $.getJSON("${ctx}/theme/theme/treeData?nodeId=1",function(data) {
            var menus = data;
            $('#menuList').append(creatMenu(menus,0));
            $("#sidebar-collapse").click();//默认菜单是收缩状态
        });
    }
    /**
     * 创建菜单
     * @param menus 菜单内容
     * @param down 深度
     */
    function creatMenu(menus,down){
        var html = "";
        if(down!=0){ html += "<ul class=\"submenu\">"; }//只有子菜单才有此节点，子菜单深度大于等于1
        for (var i = 0; i < menus.length; i++) {

            if (menus[i].show) {
                var isChild = "";
                if (menus[i].children != null
                    && menus[i].children.length != 0) {
                    isChild = " class=\"dropdown-toggle\" ";
                }
                html += "<li>";
                if (menus[i].attributes.menu.href == null
                    || menus[i].attributes.menu.href.length == 0) {
                    html += "<a href=\"#\" data-id=\""+ menus[i].id + "\" " + isChild +"  >";
                } else {
                    html += "<a href=\"${ctx}" + menus[i].attributes.menu.href + "\" data-id=\""+ menus[i].id + "\" target=\"mainFrame\"" + isChild + "  >";
                }

                if (menus[i].iconCls == null
                    || menus[i].iconCls.length == 0) {
                    if (menus[i].text=="系统设置"){
                        html += "<i class=\"icon-" + "xtsz" + "\" ></i>";
                    }
                    /* html += "<i class=\"icon-double-angle-right\" ></i>"; */
                } else {/* html += "<i class=\"icon-" + menus[i].iconCls + "\"></i>"; */

                    if (menus[i].text=="信贷审批"){
                        html += "<i class=\"icon-" + "xdsp" + "\"></i>";
                    }
                    if (menus[i].text=="催收管理"){
                        html += "<i class=\"icon-" + "csgl" + "\"></i>";
                    }
                    if (menus[i].text=="影像管理"){
                        html += "<i class=\"icon-" + "yxgl" + "\"></i>";
                    }
                    if (menus[i].text=="账务管理"){
                        html += "<i class=\"icon-" + "cwgl" + "\"></i>";
                    }
                    if (menus[i].text=="合同管理"){
                        html += "<i class=\"icon-" + "htgl" + "\"></i>";
                    }
                    if (menus[i].text=="报表管理"){
                        html += "<i class=\"icon-" + "bbgl" + "\"></i>";
                    }
                }
                //	alert(menus[i].text+menus[i].iconCls);
                html += "<span class=\"menu-text\">"
                    + menus[i].text + "</span>";

                if (isChild.length != 0) {
                    html += "<b class=\"arrow icon-angle-down\"></b>";
                }

                html += "</a>";
                if (isChild.length != 0) {
                    html += creatMenu(menus[i].children,(down+1));
                } else {
                    isChild = "";
                }
                html += "</li>";
            }
        }
        if(down!=0){ html += "</ul>";}//只有子菜单才有此节点，子菜单深度大于等于1
        return html;
    }
    var tabTitleHeight = 33; // 页签的高度
    $(function(){
        resizeRight(false);
        $.fn.initJerichoTab({
            renderTo: '#right', uniqueId: 'jerichotab',
            contentCss: { 'height': $('#right').height() - tabTitleHeight },
            tabs: [], loadOnce: true, tabWidth: 110, titleHeight: tabTitleHeight
        });

        $("[target='mainFrame']").live("click",function(e){
            e.preventDefault();
            console.info(arguments);
            addTab($(this),true);
            return true;
        });

    });

    function resizeRight(){
        var w=$(window).width();
        var sw =0;
        var bopen =$("#right").hasClass("rightopen");
        if(bopen){
            sw =209;
        }else{
            sw =65;
        }
        $("#right").width(w-sw);
        console.info($("#right").width()+"--"+bopen+"---w"+w+"--sw"+sw);

    }

    function addTab($this, refresh){
        console.info({
            tabFirer: $this,
            title: $this.text(),
            closeable: true,
            data: {
                dataType: 'iframe',
                dataLink: $this.attr('href')
            }
        });
        $.fn.jerichoTab.addTab({
            tabFirer: $this,
            title: $this.text(),
            closeable: true,
            data: {
                dataType: 'iframe',
                dataLink: $this.attr('href')
            }
        }).loadData(refresh);
        return false;
    }
</script>
<style>
	.rightclose{
		padding-left:46px;padding-top:40px;height:820px
	}
	.rightopen{
		padding-left:190px;padding-top:40px;height:820px
	}
</style>
</body>
</html>
