<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="true" description="编号"%>
<%@ attribute name="name" type="java.lang.String" required="true" description="输入框名称"%>
<%@ attribute name="value" type="java.lang.String" required="true" description="输入框值"%>
<%@ attribute name="imgFunc" type="java.lang.String" description="图片点击事件方法名"%>
<img id="${id}Icon" style="width:30px;height:30px" class="${not empty value?'':'hide'}" src="${not empty value?ctxImgSource:''}${not empty value?value:''}"></img>&nbsp;<label id="${id}IconLabel">${not empty value?'':'无'}</label>&nbsp;
<input id="${id}" name="${name}" type="hidden" value="${value}"/><a id="${id}Button" href="javascript:" class="btn">选择</a>
<script type="text/javascript">
	$("#${id}Button").click(function(){
		top.$.jBox.open("iframe:${ctx}/img/imgSelect?value="+$("#${id}").val()+"&imgFunc="+"${imgFunc}", "选择图片", 700, $(top.document).height()-180, {
            buttons:{"确定":"ok", "清除":"clear", "关闭":true}, submit:function(v, h, f){
                if (v=="ok"){
                	var icon = h.find("iframe")[0].contentWindow.$("#img").val();
                	$("#${id}Icon").attr("src", "${ctxImgSource}"+icon);
                	$("#${id}Icon").show();
	                $("#${id}IconLabel").text("");
	                $("#${id}").val(icon);
                }else if (v=="clear"){
	                $("#${id}Icon").hide();
	                $("#${id}IconLabel").text("无");
	                $("#${id}").val("");
                }
            }, loaded:function(h){
                $(".jbox-content", top.document).css("overflow-y","hidden");
            },persistent: true
        });
	});
</script>