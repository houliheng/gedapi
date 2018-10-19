<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>机构管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            /**
             * @reqno        :H1509070147
             * @date-designer:20150910-jiangbing
             * @date-author  :20150910-jiangbing:查看的情况下不设置焦点
             **/
             /**
 			 * 
 			 * @reqno:H1512180077
 			 * @date-designer:2015年12月16日-zhangzhida
 			 * @e-in-text    :area:归属区域
 			 * @date-author:2015年12月16日-zhangzhida:修改机构类型字段的选择列表信息,当isCheck属性为1时.获取机构的类型数据.
 			 										如果为1(部门)或2(机构)的时候,将机构字段的readOnly属性设为true.不能进行修改.
 			 */
            if (${isCheck} != 1) {
				$("#name").focus();
				var jgType = '${office.type}';
				if(jgType == '1' || jgType == '2'){
					$("#type").attr("readOnly","true");
				}
			}
			$("#inputForm").validate({
				/**
		         * @reqno:  H1508310080
		         * @date-designer:20150907-jiangbing
		         * @date-author:20150907-jiangbing:添加检查机构编码是否已存在
		         */
				rules: {
					code: {remote: "${ctx}/sys/office/checkCode?oldCode=" + encodeURIComponent('${office.code}')}
                },
                messages: {
                	code: {remote: "机构编码已存在"}
                },
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		/**
		 * 
		 * @reqno:H1506290069
		 * @date-designer:20150630-zhangxingyu
		 * @e-ctrl : ID编号 - if : 判断isCheck是否是”1“ 是的话执行禁用页面元素的方法 
		 * @date-author:20150630-zhangxingyu:禁用页面元素
		 */
			var isCheck=${isCheck};
			if (isCheck=="1") {
				disabledAll("btnCancel");
			}
			
			/**
 			 * 
 			 * @reqno:H1512180077
 			 * @date-designer:2015年12月16日-zhangzhida
 			 * @date-author:2015年12月16日-zhangzhida:将机构类型,是否可用.机构级别的下拉选择中的搜索框隐藏掉
 			 */
			$("#type").select2({
				minimumResultsForSearch: -1
			 });
			 $("#grade").select2({
					minimumResultsForSearch: -1
			 });
			 $("#useable").select2({
					minimumResultsForSearch: -1
			 });
		});
		/**
		 * 
		 * @reqno: 	H1506290069
		 * @date-designer:20150630-zhangxingyu
		 * @e-in-other : ID编号 - disabledAll(cancel) : 禁用除id=cancel外的所有input,select,textarea标签和class='select2-choice'的a标签  移除掉  除id=cancel以外的所有按钮
		 * @date-author:20150630-zhangxingyu:定义禁用页面元素方法
		 */
		//禁用页面元素
		function disabledAll(cancel){
			$("input,select,textarea,a[class='select2-choice']").prop("readonly",true);//禁用所有input,select,textarea标签和class='select2-choice'的a标签  移除掉  除id=cancel以外的所有按钮
			$("#"+cancel).prop("disabled",false);//恢复id=cancel的标签
			$(".btn:not(#"+cancel+")").remove(); //移除id=cancel以外的所有样式为btn的标签
		}
	</script>
<style type="text/css">
.span4Tree {
	width : 240px;
}
</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<!-- 	 /**
	 * 
	 * @reqno:H1505280043
	 * @date-designer:20150601-zhangxingyu
	 * 
	 * 
	 * @date-author:20150601-zhangxingyu:原来是点过添加下级之后就再点机构列表，就会只查询点击的那个机构的下级机构 ，去掉参数，再点机构列表就会查询所有
	 */ -->
	 	<!--
	   	 * @reqno: H1507080024
	   	 * @date-designer:20150713-zhunan
	   	 * @e-out-other : treeTableList - 树表 : 展示区域树的grid
		 * @db-j : sys_office : id,parent_id,name,sort,code,type
	   	 * @date-author:20150713-zhunan: 只显示根节点为0的数据
		-->
		<li><a href="${ctx}/sys/office/list?id=&&parent.id=0">机构列表</a></li>
		<!-- /**
			 * 
			 * @reqno: 	H1506290069
			 * @date-designer:20150630-zhangxingyu
			 * @date-author:20150630-zhangxingyu:判断是否是查看 如果是  将页签明写为机构查看
			 */ -->
		<li class="active"><a href="${ctx}/sys/office/form?id=${office.id}&parent.id=${office.parent.id}">机构<shiro:hasPermission name="sys:office:edit">${not empty office.id?(isCheck=='1'?'查看':'修改'):'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:office:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	
	<form:form id="inputForm" modelAttribute="office" action="${ctx}/sys/office/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<!--
        @reqno        :H1509070147
        @date-designer:20150910-jiangbing
        @e-ctrl       :c if:isCheck!='1' 修改或是添加的情况
        @date-author  :20150910-jiangbing:在修改或是添加的情况下显示输入框选择框等控件
        -->
		<c:if test="${isCheck!='1'}">
		<div class="control-group">
			<label class="control-label">上级机构:</label>
			<div class="controls">
                <sys:treeselect id="office" name="parent.id" value="${office.parent.id}" labelName="parent.name" labelValue="${office.parent.name}"
					title="机构" url="/sys/office/treeData" extId="${office.id}"  allowClear="${office.currentUser.admin}"  cssClass="span4Tree" />
			</div>
		</div>

		<div class="control-group">
            <!--
            @reqno        :H1509060061
            @date-designer:20150909-jiangbing
            @e-in-text    :area:归属区域
            @date-author  :20150909-jiangbing:将必填标志"*"挪到文字前面，避免报错信息显示不一致
            -->
            <!--
            /**
			 * 
			 * @reqno:H1512180077
			 * @date-designer:2015年12月16日-zhangzhida
			 * @e-in-text    :area:归属区域
			 * @date-author:2015年12月16日-zhangzhida:去掉归属区域的必填代码以及必填验证
			 */
              -->
			<label class="control-label"><span class="help-inline"></span>&nbsp;归属区域:</label>
			<div class="controls">
	            <!--
	            @reqno        :H1509060061
	            @date-designer:20150909-jiangbing
	            @e-in-text    :area:归属区域
	            @date-author  :20150909-jiangbing:为归属区域添加必须输入报错信息
	            -->
                <sys:treeselect id="area" name="area.id" value="${office.area.id}" labelName="area.name" labelValue="${office.area.name}"
					title="区域" url="/sys/area/treeData"  cssClass="span4Tree"/>
			</div>
		</div>
		
		<!--
            /**
			 * 
			 * @reqno:H1512180077
			 * @date-designer:2015年12月16日-zhangzhida
			 * @e-in-text    :caode:机构编码
             * @e-in-text    :name:机构名称
			 * @date-author:2015年12月16日-zhangzhida:机构新增或修改时将机构编码与机构名称字段在页面中的显示顺序互换
			 */
              -->
		<div class="control-group">
            <!--
            @reqno        :H1509060061
            @date-designer:20150909-jiangbing
            @e-in-text    :caode:机构编码
            @date-author  :20150909-jiangbing:将必填标志"*"挪到文字前面，避免报错信息显示不一致
            -->
			<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>&nbsp;机构编码:</label>
			<div class="controls">
				<!--
				@reqno        :H1508310080
				@date-designer:20150905-jiangbing
				@e-in-text    :code:机构编码
				@date-author  :20150905-jiangbing:将机构编码设为必须输入 保存修改前机构编码
				-->
				<input id="oldCode" name="oldCode" type="hidden" value="${office.code}">
				<form:input path="code" htmlEscape="false" maxlength="50" class="required span4"/>
			</div>
		</div>
		<div class="control-group">
            <!--
            @reqno        :H1509060061
            @date-designer:20150909-jiangbing
            @e-in-text    :name:机构名称
            @date-author  :20150909-jiangbing:将必填标志"*"挪到文字前面，避免报错信息显示不一致
            -->
			<label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;机构名称:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required span4"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机构类型:</label>
			<div class="controls">
				<form:select path="type" class="span4">
					<form:options items="${fns:getDictList('sys_office_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机构级别:</label>
			<div class="controls">
				<form:select path="grade" class="span4">
					<form:options items="${fns:getDictList('sys_office_grade')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否可用:</label>
			<div class="controls">
				<form:select path="useable" class="span4">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline">“是”代表此账号允许登陆，“否”则表示此账号不允许登陆</span>
			</div>
		</div>
		<!-- /**
			 * @reqno: 	H1507130003
			 * @date-designer:20150722-chenshaojia
			 * @date-author:20150722-chenshaojia:优化新增、修改机构时的信息项,主负责人与副负责人暂时无用，需去掉
			 */ -->
			 <!--
            /**
			 * 
			 * @reqno:H1512180076
			 * @date-designer:2015年12月16日-zhangzhida
			 * @e-in-text    :caode:机构编码
             * @e-in-text    :name:机构名称
			 * @date-author:2015年12月16日-zhangzhida:机构新增或修改时.将联系地址,邮件编码,电话,传真,邮箱,备注等字段隐藏
			 */
              -->
		<%-- <div class="control-group">
			<label class="control-label">主负责人:</label>
			<div class="controls">
				 <sys:treeselect id="primaryPerson" name="primaryPerson.id" value="${office.primaryPerson.id}" labelName="office.primaryPerson.name" labelValue="${office.primaryPerson.name}"
					title="用户" url="/sys/office/treeData?type=3" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">副负责人:</label>
			<div class="controls">
				 <sys:treeselect id="deputyPerson" name="deputyPerson.id" value="${office.deputyPerson.id}" labelName="office.deputyPerson.name" labelValue="${office.deputyPerson.name}"
					title="用户" url="/sys/office/treeData?type=3" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div> --%>
		<%--<div class="control-group">
			<label class="control-label">联系地址:</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮政编码:</label>
			<div class="controls">
				<form:input path="zipCode" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<!-- /**
			 * @reqno: 	H1507130003
			 * @date-designer:20150722-chenshaojia
			 * @date-author:20150722-chenshaojia:优化新增、修改机构时的信息项,负责人暂时无用，需去掉
			 */ -->
		<%-- <div class="control-group">
			<label class="control-label">负责人:</label>
			<div class="controls">
				<form:input path="master" htmlEscape="false" maxlength="50"/>
			</div>
		</div> 
		<div class="control-group">
			<label class="control-label">电话:</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">传真:</label>
			<div class="controls">
				<form:input path="fax" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮箱:</label>
			<div class="controls">
				<form:input path="email" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
			</div>
		</div>
		--%>
		</c:if>
		<!--
        @reqno        :H1509070147
        @date-designer:20150910-jiangbing
        @e-out-other  :parent.name:上级机构
        @e-out-other  :area.name:归属区域
        @e-out-other  :name:机构名称
        @e-out-other  :code:机构编码
        @e-out-other  :typeLabel:机构类型
        @e-out-other  :gradeLabel:机构级别
        @e-out-other  :useableLabel:是否可用
        @e-out-other  :address:联系地址
        @e-out-other  :zipCode:邮政编码
        @e-out-other  :phone:电话
        @e-out-other  :fax:传真
        @e-out-other  :email:邮箱
        @e-out-other  :remarks:备注
        @e-ctrl       :c if:isCheck=='1' 查看的情况
        @date-author  :20150910-jiangbing:添加专门用于查看的HTML
        -->
		<c:if test="${isCheck=='1'}">
		<div class="control-group">
            <label class="control-label">上级机构:</label>
            <div class="controls">
                <label class="lbl area">${office.parent.name}</label>
            </div>
        </div>
        
        <!--
            /**
			 * 
			 * @reqno:H1512180077
			 * @date-designer:2015年12月16日-zhangzhida
			 * @e-in-text    :caode:机构编码
             * @e-in-text    :name:机构名称
			 * @date-author:2015年12月16日-zhangzhida:机构信息产看时,将机构编码与机构名称字段在页面中的显示顺序互换
			 */
              -->
        <div class="control-group">
            <label class="control-label"><span class="help-inline"><font color="red">*</font> </span>&nbsp;机构编码:</label>
            <div class="controls">
                <label class="lbl">${office.code}</label>
            </div>
        </div>
         <div class="control-group">
            <label class="control-label"><span class="help-inline"><font color="red">*</font> </span>&nbsp;归属区域:</label>
            <div class="controls">
                <label class="lbl area">${office.area.name}</label>
            </div>
        </div>
        <!--
        @reqno        :H1509070148
        @date-designer:20150910-jiangbing
        @e-out-other  :typeLabel:机构类型
        @e-out-other  :gradeLabel:机构级别
        @e-out-other  :useableLabel:是否可用
        @date-author  :20150910-jiangbing:将选择的内容在查看页面表示出来
        -->
        <div class="control-group">
            <label class="control-label">机构类型:</label>
            <div class="controls">
                <label class="lbl">${office.typeLabel}</label>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">机构级别:</label>
            <div class="controls">
                <label class="lbl">${office.gradeLabel}</label>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">是否可用:</label>
            <div class="controls">
                <label class="lbl">${office.useableLabel}</label>
                <span class="help-inline lbl">&nbsp;&nbsp;“是”代表此账号允许登陆，“否”则表示此账号不允许登陆</span>
            </div>
        </div>
        <!--
            /**
			 * 
			 * @reqno:H1512180076
			 * @date-designer:2015年12月16日-zhangzhida
			 * @date-author:2015年12月16日-zhangzhida:机构查看时.将联系地址,邮件编码,电话,传真,邮箱,备注等字段隐藏
			 */
              -->
        <!--  
        <div class="control-group">
            <label class="control-label">联系地址:</label>
            <div class="controls">
                <label class="lbl area">${office.address}</label>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">邮政编码:</label>
            <div class="controls">
                <label class="lbl">${office.zipCode}</label>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">电话:</label>
            <div class="controls">
                <label class="lbl">${office.phone}</label>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">传真:</label>
            <div class="controls">
                <label class="lbl">${office.fax}</label>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">邮箱:</label>
            <div class="controls">
                <label class="lbl">${office.email}</label>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">备注:</label>
            <div class="controls">
                <label class="lbl area">${office.remarks}</label>
            </div>
        </div>
        -->
        </c:if>
		<c:if test="${empty office.id}">
			<div class="control-group">
				<label class="control-label">快速添加下级部门:</label>
				<div class="controls">
					<form:checkboxes path="childDeptList" items="${fns:getDictList('sys_office_common')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</div>
			</div>
		</c:if>
		<div class="form-actions">
			<shiro:hasPermission name="sys:office:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<!--
            /**
			 * 
			 * @reqno:H1512180076
			 * @date-designer:2015年12月16日-zhangzhida
			 * @e-ctrl    :btnCancel:返回按钮
			 * @date-author:2015年12月16日-zhangzhida:修改点击返回按钮时的onclick时间.返回历史前一页会导致返回主页面.修改为加载机构信息列表的action
			 */
              -->
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="location='${ctx}/sys/office/list'"/>
		</div>
	</form:form>
</body>
</html>