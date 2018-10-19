<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {

            $(".unSocCreditNoShow").hide();
            $(".corporationMobileShow").hide();
            $(".mobileNumShow").hide();
            if('${roleType}'!='5'){
                $("#registerChoose").hide();
            }else{
                $(".mobileNumShow").show();
            }
            //$("#name").focus();
            $("#inputForm").validate({
                submitHandler: function(form){
                    loading('正在注册，请稍等...');
                    var param = $("#inputForm").serialize();

                    $.post("${ctx}/credit/mortgagedperson/saveCustGedApi", param, function(data) {

                        if (data) {
                            closeTip();
                            if (data.status == '1') {
                                alertx(data.message, function() {
                                    parent.location.reload();
                                    closeJBox();
                                });
                            } else {
                                alertx(data.message);
                            }
                        }
                    });
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
        });


    </script>

</head>
<body>
<form id="inputForm" action="#" method="post" class="form-horizontal">
    <table class="fromTable filter">
        <td class="ft_content">
            <input type="hidden"  id="applyNo" name="applyNo" value="${applyNo}"/>
            <input type="hidden"  id="custId" name="custId" value="${custId}"/>
            <input type="hidden"  id="mobileNum1" name="mobileNum" value="${mobileNum}"/>
            <input type="hidden"  id="recBankcareNo" name="recBankcareNo" value="${recBankcareNo}"/>

        </td>

        <tr>
            <td class="ft_label">借款人姓名：</td>
            <td class="ft_content">
                <input type="text" readonly="readonly" id="borrowName" name="borrowName" value="${custName}"  class="required" />
                <font color="red">*</font>
            </td>
        </tr>
        <tr>
            <td class="ft_label">借款人手机号：</td>
            <td class="ft_content">
                <input type="text" readonly="readonly" id="mobileNum"   value="${mobileNum}"  class="required" />
                <font color="red">*</font>
            </td>
        </tr>

    </table>
    <div class="searchButton" id="buttonDiv">
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />
    </div>
</form>
</body>
</html>