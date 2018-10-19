<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>录入结论</title>
    <meta name="decorator" content="default" />
    <script type="text/javascript">
        $(document).ready(function() {
            adjustTextareaLength("suggestion","pre");
            $("#resultForm").validate({
                submitHandler : function(form) {
                    var radioVal = $("input[name='passFlag']:checked").val();
                    var message ;
                    if("yes" == radioVal){
                        message="流程提交后，各信息将不能再做修改，确认要提交吗？";
                    }else if("back" == radioVal){
                        message="确定需要打回吗？";
                    }else{
                        message="确信要废弃本申请吗？";
                    }
                    confirmx(message, function() {
                        loading();
                        var param = $("#resultForm").serialize();
                        $.post("${ctx}/credit/underConclusion/save", param, function(data) {
                            if(data){
                                closeTip();
                                if (data.status == '1') {
                                    $("#rs_msg").html("<div id='messageBox' class='alert alert-success'><button data-dismiss='alert' class='close'>×</button>提交成功！页面即将关闭...</div>");
                                    goToPage('${ctx}${actTaskParam.headUrl}','conclusionSkipId');
                                } else {
                                    alertx(data.message);
                                    $("#rs_msg").html("<div id='messageBox' class='alert alert-error'><button data-dismiss='alert' class='close'>×</button>" + data.message + "</div>");
                                }
                            }
                        });
                    });
                },
                errorContainer : "#messageBox",
                errorPlacement : function(error, element) {
                    checkReq(error,element);
                }
            });
        });
    </script>
</head>
<body>

<div class="searchInfo">
    <h3 class="searchTitle">审批结论</h3>
    <!-- 跳转用a标签 --><a id="conclusionSkipId" target="_parent" ></a>
    <div class="searchCon filter">
        <form:form id="resultForm" modelAttribute="processSuggestionInfo" action="${ctx}/credit/underConclusion/save" method="post" class="form-horizontal">
            <form:hidden path="id" />
                <%@ include file="/WEB-INF/views/app/credit/workflow/include/actTaskParams.jsp"%>
            <input type="hidden" id="sta" name="sta" value="" />
            <input type="hidden" id="deleteDataFlag" name="deleteDataFlag" value="" />

            <sys:message content="${approveMessage}" />
            <table class="fromTable">
                <tr>
                    <td class="ft_label" style="width: 10%;">审批结论：</td>
                        <td class="ft_content" style="width: 70%;">
                            <input type="radio" name="passFlag" value="yes" id="radio_yes" class="required" >
                            <label for="radio_yes">通过</label>
                            <c:if test="${actTaskParam.taskDefKey=='under_dqglr'}">
                                &nbsp;&nbsp;
                                <input type="radio" name="passFlag" value="no" id="radio_finish" class="required">
                                <label for="radio_finish">放弃</label>
                            </c:if>
                            <c:if test="${actTaskParam.taskDefKey=='under_tb'}">
                                &nbsp;&nbsp;
                                <input type="radio" name="passFlag" value="no" id="radio_finish" class="required">
                                <label for="radio_finish">流标</label>
                                <%--&nbsp;&nbsp;
                                <input type="radio" name="passFlag" value="back" id="radio_finish" class="required">
                                <label for="radio_finish">打回</label>--%>
                            </c:if>
                            <c:if test="${actTaskParam.taskDefKey=='under_htsh'}">
                                &nbsp;&nbsp;
                                <input type="radio" name="passFlag" value="back" id="radio_finish" class="required">
                                <label for="radio_finish">打回</label>
                            </c:if>
                        </td>

                </tr>

                <tr>
                    <td class="ft_label" style="width: 10%;">审批意见：</td>
                    <td class="ft_content" style="width: 70%;">
                        <pre class="textarea-width pre-style required" id="pre1"></pre>
                        <form:textarea path="suggestionDesc" htmlEscape="false" rows="4" maxlength="1000" class="textarea-width textarea-style required" onkeyup="adjustTextareaLength('suggestionDesc','pre1')" />
                        <font color="red">*</font>
                    </td>
                </tr>
                <tr>
                    <td style="text-align: right;" colspan="2">
                        &nbsp;
                        <input id="rsBtnSubmit" class="btn btn-primary" type="submit" value="提 交" />
                        &nbsp;
                    </td>
                </tr>
            </table>
        </form:form>
    </div>
</div>
<%--<div class="wrapper">
    <div id="rs_msg"></div>
    <!-- <div id="registerGED"></div> -->
    <div class="infoList">
        <form id="resultForm" method="post">
            <div class="infoList">
                <h3 class="infoListTitle">录入结论</h3>
                <!-- 跳转用a标签 --><a id="conclusionSkipId" target="_parent" ></a>
                <input type="hidden" id="applyNo" name="applyNo" value="${actTaskParam.applyNo}" />
                <input type="hidden" id="taskId" name="taskId" value="${actTaskParam.taskId}" />
                <input type="hidden" id="taskDefKey" name="taskDefKey" value="${actTaskParam.taskDefKey}" />
                <input type="hidden" id="procDefId" name="procDefId" value="${actTaskParam.procDefId}" />
                <input type="hidden" id="status" name="status" value="${actTaskParam.status}" />
                <input type="hidden" id="procInstId" name="procInstId" value="${actTaskParam.procInstId}" />
                <input type="hidden" id="headUrl" name="headUrl" value="${actTaskParam.headUrl}" />
                <div class="infoListCon">
                    <div class="filter">
                        <table class="fromTable">
                            <tr>
                                <td class="ft_label">录入结论：</td>
                                <td class="" colspan="5">
                                    <input type="radio" name="passFlag" value="yes" id="radio_yes" class="required">
                                    <label for="radio_yes">通过</label>
                                    &nbsp;&nbsp;
                                    <input type="radio" name="passFlag" value="no" id="radio_no" class="required">
                                    <label for="radio_no">放弃</label>
                                    <font style="color: red">*</font>
                                </td>
                            </tr>
                            <tr>
                                <td class="ft_label">录入意见：</td>
                                <td class="" colspan="5">
                                    <textarea rows="4" cols="100" maxlength="1000" id="suggestion" name="suggestion" class="input-xxlarge textarea-style required"  onkeyup="adjustTextareaLength('suggestion','pre')"></textarea>
                                    <font style="color: red">*</font>
                                </td>
                            </tr>
                        </table>
                        <div class="searchButton">
                            <input id="rsBtnSubmit" class="btn btn-primary" type="submit" value="提 交" />
                        </div>
                        <div>
                            <pre class="input-xxlarge pre-style"  id="pre"></pre>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>--%>
</body>
</html>

