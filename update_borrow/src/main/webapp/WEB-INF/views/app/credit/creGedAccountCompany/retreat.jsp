<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">

        //提交
        function retreat(id){
            loading();
            var applyDate = $("#applyDate").val();
            var repeatReason=$("#repeatReason").val();
            var status=10;
            $.ajax({
                url:"${ctx}/credit/creGedAccountCompany/updateSaveAccount",
                data:{
                    applyDate:applyDate,
                    repeatReason:repeatReason,
                    status:status,
                    id:id
                },
                type:"post",
                dataType:"json",
                success:function (data) {
                    if(data.status == '1'){
                        parent.location.reload();
                        closeJBox();
                        alertx(data.message)
                    }else{
                        closeJBox();
                        alertx(data.message)
                        closeTip();
                    }
                },
                error:function (data) {
                    parent.location.reload();
                    closeJBox();
                    alertx(data.message)
                }
            })
        }

    </script>
</head>

<body>
    <div class="tableList">
        <div id="tableDataId" style="max-height:400px;overflow:auto;">
            <form id="inputForm" action="#" method="post" class="form-horizontal">
                <input type="hidden" value="${id}">
                <table class="fromTable filter">
                    <tr>
                        <td class="ft_label">退回日期:</td>
                        <td class="ft_content">
                            <input type="text" readonly="readonly" id="applyDate" value="${value}" />
                        </td>
                    </tr>
                    <tr>
                    <td class="ft_label">退回原因:</td>
                        <td class="ft_content">
                            <textarea  rows="3" cols="20" id="repeatReason"></textarea>
                        </td>
                    </tr>
                </table>
                <div class="searchButton" id="buttonDiv">
                    <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"  onclick="retreat('${id}')"/>
                </div>
            </form>
        </div>
    </div>
</body>
</html>

