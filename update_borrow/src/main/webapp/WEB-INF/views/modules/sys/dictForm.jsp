<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>字典管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			/**
			 * @reqno        :H1509070147
			 * @date-designer:20150910-jiangbing
			 * @date-author  :20150910-jiangbing:查看的情况下不设置焦点
			 **/
			if (${isCheck} != 1) {
			$("#value").focus();
	        }
			$("#inputForm").validate({
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
		 	* @reqno: 	H1512180091
		 	* @date-designer:20151223-sunna
		 	* @date-author:20151223-sunna:页面加载完成后，ajax发送请求获取系统编号和系统名称。替换展示列表中的系统编号为对应的系统名称
		 	*/
			$.ajax({
	 					type: "GET",
 					   	url: "${ctx}/sys/sysManage/getName",
 					   	//data:{parentId: id},
 					   	datatype: 'text',
 					   	success: function(result){
 					   		for (var i = 0; i < result.length; i++) {
 								var row = result[i];
 								$("#sysNo").append("<option value='"+row.no+"'>"+row.name+"</option>");
 					   		}
 					   	}	
	 				});
		/**
		 * 
		 * @reqno: 	H1506290083
		 * @date-designer:20150630-zhangxingyu
		 * @e-ctrl : ID编号 - if : 判断isCheck是否是”1“ 是的话执行禁用页面元素的方法 
		 * @date-author:20150630-zhangxingyu:禁用页面元素
		 */
			var isCheck=${isCheck};
			if (isCheck=="1") {
				disabledAll("btnCancel");
			}
		});
		/**
		 * 
		 * @reqno: 	H1506290083
		 * @date-designer:20150630-zhangxingyu
		 * @e-in-other : ID编号 - disabledAll(cancel) : 禁用除id=cancel外的所有input,select,textarea标签和class='select2-choice'的a标签  移除掉  除id=cancel以外的所有按钮
		 * @date-author:20150630-zhangxingyu:定义禁用页面元素方法
		 */
		//禁用页面元素
		function disabledAll(cancel){
			$("input,select,textarea,a[class='select2-choice']").prop("disabled",true);//禁用所有input,select,textarea标签和class='select2-choice'的a标签  移除掉  除id=cancel以外的所有按钮
			$("#"+cancel).prop("disabled",false);//恢复id=cancel的标签
			$(".btn:not(#"+cancel+")").remove(); //移除id=cancel以外的所有样式为btn的标签
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/dict/">字典列表</a></li>
		<!-- /**
			 * 
			 * @reqno: 	H1506290083
			 * @date-designer:20150630-zhangxingyu
			 * @date-author:20150630-zhangxingyu:判断是否是查看 如果是  将页签明写为字典查看
			 */ -->
		<li class="active"><a href="${ctx}/sys/dict/form?id=${dict.id}">字典<shiro:hasPermission name="sys:dict:edit">${not empty dict.id?(isCheck=='1'?'查看':'修改'):'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:dict:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="dict" action="${ctx}/sys/dict/save" method="post" class="form-horizontal">
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
			<!-- /**
	         * 
	         * @reqno:  H1507070037
	         * @date-designer:20150709-zhangxingyu
	         * @e-in-text : form:input : 输入框:键值
	         * @db-j : sys_dict : value
	         * @date-author:20150709-zhangxingyu:此项为必输项，但是没有加上必输标示，需要添加上
	         */ -->
            <!--
            @reqno        :H1509060061
            @date-designer:20150909-jiangbing
            @e-in-text    :value:键值
            @date-author  :20150909-jiangbing:将必填标志"*"挪到文字前面，避免报错信息显示不一致
            -->
			<label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;键值:</label>
			<div class="controls">
				<!--
				@reqno        :H1508310085
				@date-designer:20150907-jiangbing
				@date-author  :20150907-jiangbing:保存修改时的原键值
				-->
				<input id="oldValue" name="oldValue" type="hidden" value="${dict.oldValue}">
				<form:input path="value" htmlEscape="false" maxlength="50" class="required"/>
	
				
			</div>
		</div>
		<div class="control-group">
			<!-- /**
             * 
             * @reqno:  H1507070037
             * @date-designer:20150709-zhangxingyu
             * @e-in-text : form:input : 输入框:标签
             * @db-j : sys_dict : label
             * @date-author:20150709-zhangxingyu:此项为必输项，但是没有加上必输标示，需要添加上
             */ -->
            <!--
            @reqno        :H1509060061
            @date-designer:20150909-jiangbing
            @e-in-text    :label:标签
            @date-author  :20150909-jiangbing:将必填标志"*"挪到文字前面，避免报错信息显示不一致
            -->
			<label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;标签:</label>
			<div class="controls">
				<form:input path="label" htmlEscape="false" maxlength="50" class="required"/>
				
				
			</div>
		</div>
		<div class="control-group">
                <!-- /**
                 * 
                 * @reqno:  H1507070037
                 * @date-designer:20150709-zhangxingyu
                 * @e-in-text : form:input : 输入框:类型
                 * @db-j : sys_dict : type
                 * @date-author:20150709-zhangxingyu:此项为必输项，但是没有加上必输标示，需要添加上
                 */ -->
            <!--
            @reqno        :H1509060061
            @date-designer:20150909-jiangbing
            @e-in-text    :type:类型
            @date-author  :20150909-jiangbing:将必填标志"*"挪到文字前面，避免报错信息显示不一致
            -->
			<label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;类型:</label>
			<div class="controls">
				<!--
                @reqno        :H1508310085
                @date-designer:20150907-jiangbing
                @date-author  :20150907-jiangbing:保存修改时的原类型
                -->
				<input id="oldType" name="oldType" type="hidden" value="${dict.oldType}">
				<form:input path="type" htmlEscape="false" maxlength="50" class="required abc"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">描述:</label>
			<div class="controls">
			<!-- /**
		 * 
		 * @reqno: 	H1507070037
		 * @date-designer:20150709-zhangxingyu
		 * @e-in-text : form:input : 输入框:描述
		 * @db-j : sys_dict : description
		 * @date-author:20150709-zhangxingyu:此项不为必输项，去掉标签的class="required"属性使该标签可以不填
		 */ -->
				<form:input path="description" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序:</label>
			<div class="controls">
				<!-- /**
				 * 
				 * @reqno: 	H1507070037
				 * @date-designer:20150709-zhangxingyu
				 * @e-in-text : form:input : 输入框:排序
				 * @db-j : sys_dict : sort
				 * @date-author:20150709-zhangxingyu:此项不为必输项，将标签的class="required digits"属性改为class="digits"
				 													使该输入框可以不填，但填的值只能是整数，将输入框的最大长度改为10
				 */ -->
				<form:input path="sort" htmlEscape="false" maxlength="10" class="digits"/>
			</div>
		</div>
		<!-- 
			/**
		 	* @reqno: 	H1512180091
		 	* @date-designer:20151223-sunna
		 	* @date-author:20151223-sunna:在字典列表增加所属系统列，展示该数据对应的所属系统。
		 	*/
		 -->
		 
		 <!--  
		 		* @reqno:  	H1601080089
		 		* @date-designer:20160122-zhangjiyuan
		 		* @date-author:20160122-zhangjiyuan:在字典修改页面，“所属系统”没有把值传递过来，文本框为空。
		 		*/
		 		-->
		<%-- <div class="control-group">
			<label class="control-label">所属系统:</label>
				<div class="controls">
				<!-- 
				/**
		 		* @reqno: 	H1512180091
		 		* @date-designer:20151223-sunna
		 		* @date-author:20151223-sunna:修改所属系统的样式，保证所属系统的下拉框和其他框长度一致。并且可以多选。
		 		*/
		 		-->
					<form:select path="sysNo" id="sysNo" multiple="multiple" class="span3" >
					</form:select>
				</div>
				
		</div> --%>
		
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
			
			<!-- 
			/**
		 	* @reqno: 	H1512180091
		 	* @date-designer:20151223-sunna
		 	* @date-author:20151223-sunna:修改备注的样式，使备注框和其他框长度一致。
		 	*/
		 -->
				<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-large"/>
			</div>
		</div>
		</c:if>
		
		<!--
        @reqno        :H1509070147
        @date-designer:20150910-jiangbing
        @e-out-other  :value:键值
        @e-out-other  :label:标签
        @e-out-other  :type:类型
        @e-out-other  :description:描述
        @e-out-other  :sort:排序
        @e-out-other  :remarks:备注
        @e-ctrl       :c if:isCheck=='1' 查看的情况
        @date-author  :20150910-jiangbing:添加专门用于查看的HTML
        -->
		<c:if test="${isCheck=='1'}">
		<div class="control-group">
            <label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;键值:</label>
            <div class="controls">
                <label class="lbl area">${dict.value}</label>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;标签:</label>
            <div class="controls">
                <label class="lbl area">${dict.label}</label>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label"><span class="help-inline"><font color="red">*</font></span>&nbsp;类型:</label>
            <div class="controls">
                <label class="lbl">${dict.type}</label>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">描述:</label>
            <div class="controls">
                <label class="lbl area">${dict.description}</label>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">排序:</label>
            <div class="controls">
                <label class="lbl">${dict.sort}</label>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">备注:</label>
            <div class="controls">
                <label class="lbl">${dict.remarks}</label>
            </div>
        </div>
        </c:if>
		<div class="form-actions">
			<shiro:hasPermission name="sys:dict:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<!-- 
			 * @reqno: 	H1507080059
			 * @date-designer:20150810-zhunan
			 * @e-out-other : btn - 按钮 : 返回按钮
			 * @date-author:20150810-zhunan: 返回时候直接返回列表，不要用history.go(-1)，在ie情况下会出现验证未通过的情况
			 -->
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="location='${ctx}/sys/dict/list'"/>
		</div>
	</form:form>
</body>
</html>