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
                    loading('正在添加社会统一信用代码，请稍等...');
                    var param = $("#inputForm").serialize();

                    $.post("${ctx}/credit/mortgagedperson/updateCompanyCreditNo", param, function(data) {

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
            <input type="hidden"  id="custId" name="id" value="${custId}"/>
        </td>

        <tr>
            <td class="ft_label">企业名称：</td>
            <td class="ft_content">
                <input type="text" readonly="readonly" id="companyinfoName" name="busiRegName" value="${companyinfoName}"  class="required" />
                <font color="red">*</font>
            </td>
        </tr>
        <tr>
            <td class="ft_label">社会统一信用代码：</td>
            <td class="ft_content">
                <input type="text"  id="unSocCreditNo"  name="unSocCreditNo"   class="required" />
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