/**
 * 复选框全选事件
 *
 * @param allCheckName
 *            表头checkbox的name
 * @param checkName
 *            行内checkbox的name
 */
function allCheck(allCheckName, checkName) {
    if (allCheckName && checkName) {
        if ($('[name=' + allCheckName + ']:checkbox').attr("checked") == "checked") {
            $('[name=' + checkName + ']:checkbox').attr("checked", true);
        } else {
            $('[name=' + checkName + ']:checkbox').attr("checked", false);
        }
    } else {
        if ($('[name=all]:checkbox').attr("checked") == "checked") {
            $('[name=type]:checkbox').attr("checked", true);
        } else {
            $('[name=type]:checkbox').attr("checked", false);
        }
    }
}

/* 验证 */
var checkStr = false; /* 定义全局变量，判断输入框的颜色改变 */
function checkReq(error, element) {
    $("#messageBox").text("输入有误，请先更正。");
    var str2 = element[0].outerHTML;
    if (element.is(":checkbox") || element.parent().is(".input-append")) {
        error.appendTo(element.parent().parent());
    } else if (element.is(":radio")) {
        error.appendTo(element.parent().parent());
    } else {

        var str = element.prop("outerHTML").substring(0, 2);
        var div = $("[id='s2id_" + element.attr("id") + "']");

        var a = div.children("a.select2-choice");

        if (str == '<i') {
            element.get(0).style.backgroundColor = "pink";
            if (checkStr == true) {
                element.get(0).style.backgroundColor = "";
                checkStr = false;
            }
            element.live("input", function(event) {
                element.get(0).style.backgroundColor = "";
                // document.getElementById(IdCheck).style.backgroundColor = "";
            });
        }
        if (str == '<t') {
            element.get(0).style.backgroundColor = "pink";
            element.live("input", function(event) {
                element.get(0).style.backgroundColor = "";
            });
        }
        if (str == '<s') {
            a.css("background", "pink");
            a.css("backgroundColor", "pink");
            element.live("change", function(event) {
                event.stopPropagation();
                a.get(0).style.background = "";
            });
        }
    }
}
/* 实时输入 */
var valueTrue = "";
function keyPress11(s) {
    var idd = s.id;
    var number = document.getElementById(idd).value;
    var len = number.length;
    if (len == 12) {
        valueTrue = s.value;
    }
    if (len > 12) {
        s.value = valueTrue;
    }
    s.value = s.value.replace(/[^\d.]/g, "");
    s.value = s.value.replace(/^\./g, "");
    s.value = s.value.replace(/\.{2,}/g, ".");
    s.value = s.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
    var source = String(s.value).split(".");
    source[0] = source[0].replace(new RegExp('(\\d)(?=(\\d{3})+$)', 'ig'), "$1,");
    s.value = source.join(".");
}
/* 只能输入金额形式 */
function keyPressNumber(s) {
    s.value = s.value.replace(/[^\d.]/g, "");
    s.value = s.value.replace(/^\./g, "");
    s.value = s.value.replace(/\.{2,}/g, ".");
    s.value = s.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
}
/* 格式化金额 */
function outputmoney(s) {
    if (s == "" || s == null) {
        return "";
    } else {
        s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(2) + "";
        var a = s.split(".")[0].split("").reverse();
        r = s.split(".")[1];
        t = "";
        for (i = 0; i < a.length; i++) {
            t += a[i] + ((i + 1) % 3 == 0 && (i+1) != a.length ? "," : "");
        }
        return t.split("").reverse().join("") + "." + r;
    }
}
/* 去除空白格 */
function reSpaces(s){
    return s.replace(/\s/g,"");
}
/* 格式化利率 */
function rateMax(s) {
    s.value = s.value.replace(/[^\d.]/g, "");
    s.value = s.value.replace(/^\./g, "");
    if (s.value >= 10) {
        s.value = s.value.substring(0, 1);
    }
    if (s.value.length >= 4) {
        s.value = s.value.substring(0, 4);
    }
    s.value = s.value.replace(/\.{2,}/g, ".");
    s.value = s.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
}
/* 格式化占比 */
function gdpMax(s) {
    s.value = s.value.replace(/[^\d.]/g, "");
    s.value = s.value.replace(/^\./g, "");
    if (s.value >= 101) {
        s.value = s.value.substring(0, 2)
    }

    s.value = s.value.replace(/\.{2,}/g, ".");
    s.value = s.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
}
/* 利率格式化 */
function rateMoney(s) {
    if (isNaN(s)) {
        return "";
    }
    if (s == "" || s == null || s >= 10) {
        return "";
    }
    s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(2) + "";
    return s;
}
/* 格式化占比 */
function gdpMoney(s) {
    if (isNaN(s)) {
        return "";
    }
    if (s == "" || s == null || s >= 101) {
        return "";
    }
    s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(2) + "";
    return s;
}
/* 出资年限 保留一位小数点 */
function capMoney(s) {
    if (isNaN(s)) {
        return "";
    }
    if (s == "" || s == null || s >= 101) {
        return "";
    }
    s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(1) + "";
    return s;
}
/* 日期必填修改 */
function dateWhite(ss) {
    var idd = ss.id;
    $('#' + idd).css('backgroundColor', 'white');
    // $('#' + idd).css('background', 'white');
    checkStr = true;
    // $('#' + idd).css('class', 'wdate');
}
/**
 * 获取复选框选中的值
 *
 * @param checkbox的名字
 */
function getCheckedValue(checkName) {
    var str = "";
    if (checkName) {
        $("input:checkbox[name='" + checkName + "']:checked").each(function() {
            if ($(this).prop("checked")) {
                if (str == "") {
                    str += $(this).val();
                } else {
                    str += "," + $(this).val();
                }
            }
        });
    } else {
        $("input[name=type]:checkbox").each(function() {
            if ($(this).prop("checked")) {
                if (str == "") {
                    str += $(this).val();
                } else {
                    str += "," + $(this).val();
                }
            }
        });
    }
    if (str == "") {
        alertx('没有选中的记录，请选择!');
    }
    return str;
}

function getCheckedIds(checkName) {
    var ids = new Array();
    if (checkName) {
        $("input:checkbox[name='" + checkName + "']:checked").each(function() {
            if ($(this).attr("checked")) {
                ids.push($(this).val());
            }
        });
    }
    return ids;
}
// 单选框
function selectSingle(name) {
    $("[name=" + name + "]:checkbox").each(function() {
        $(this).click(function() {
            if ($(this).attr("checked")) {
                $("[name=" + name + "]:checkbox").removeAttr("checked");
                $(this).attr("checked", "checked");
            }
        });
    });
}
// 禁用所有下拉选项，该功能仅针对select2插件的下拉选项禁用
function disableSelect2() {
    $("select").prop("disabled", true).select2();
}

// 时间插件只读处理
function disableWdate() {
    $("input[class='input-medium Wdate required']").removeAttr("onclick");
    $("input[class='input-medium Wdate required valid']").removeAttr("onclick");
    $("input[class='input-mini Wdate required']").removeAttr("onclick");
    $("input[class='input-mini Wdate required valid']").removeAttr("onclick");
    $("input[class='input-medium Wdate']").removeAttr("onclick");
    $("input[class='input-medium Wdate valid']").removeAttr("onclick");
    $("input[class='input-mini Wdate']").removeAttr("onclick");
    $("input[class='input-mini Wdate valid']").removeAttr("onclick");
}

// 文本框、大文本框、单选框、复选框只读处理
function disableBaseElement() {
    // 文本框只读处理
    $("input").attr("readOnly", "readOnly");
    // 大文本只读处理
    $("textarea").attr("readOnly", "readOnly");
    // 单选框、复选框只读处理
    // $("input[type='checkbox']").onclick=function(){return false;};
    // $("input[type='radio']").onclick=function(){return false;};
    // return false在个别情况下不起作用，还是更改为disabled
    $("input[type='radio']").attr("disabled", "true");
    $("input[type='checkbox']").attr("disabled", "true");
}

// 审批意见只读处理
function hideSuggDiv() {
    $("div[id='isHideSuggestionDiv']").hide();
}

// 新增、修改、删除、保存按钮只读处理
function hideButtons() {
    $("div[class='searchButton']").hide();
    $("input[class='btn']").hide();
    $("input[id='btnSubmit']").hide();
    $("input[class='btn btn-primary']").hide();
    $("div[id='saveBtnDiv']").hide();
    $("ul[class='layout']").hide();
}

// 将指定DIV下的所有元素只读
function setDivReadOnly(divId) {
    $("div[id='" + divId + "'] input").attr("readOnly", "readOnly");
    $("div[id='" + divId + "'] textarea").attr("readOnly", "readOnly");
    $("div[id='" + divId + "'] input[type='checkbox']").attr("disabled", "true");
    $("div[id='" + divId + "'] select").prop("disabled", true).select2();
    $("div[id='" + divId + "'] input[type='radio']").attr("disabled", "true");
    $("div[id='" + divId + "'] input[class='input-medium Wdate required']").removeAttr("onclick");
    $("div[id='" + divId + "'] input[class='input-medium Wdate required valid']").removeAttr("onclick");
    $("div[id='" + divId + "'] input[class='input-mini Wdate required']").removeAttr("onclick");
    $("div[id='" + divId + "'] input[class='input-mini Wdate required valid']").removeAttr("onclick");
    $("div[id='" + divId + "'] div[class='searchButton']").hide();
    $("div[id='" + divId + "'] input[class='btn']").hide();
    $("div[id='" + divId + "'] input[id='btnSubmit']").hide();
    $("div[id='" + divId + "'] input[class='btn btn-primary']").hide();
    $("div[id='" + divId + "'] div[id='saveBtnDiv']").hide();
    $("div[id='" + divId + "'] ul[class='layout']").hide();
}

// 将指定DIV下的所有元素设置可编辑
function removeDivReadOnly(divId) {
    $("div[id='" + divId + "'] input").removeAttr("readOnly");
    $("div[id='" + divId + "'] textarea").removeAttr("readOnly");
    $("div[id='" + divId + "'] select").prop("disabled", false).select2();
    $("div[id='" + divId + "'] input[type='radio']").attr("disabled", "false");
    $("div[id='" + divId + "'] input[type='checkbox']").attr("disabled", "false");
    $("div[id='" + divId + "'] div[class='searchButton']").show();
    $("div[id='" + divId + "'] input[class='btn']").show();
    $("div[id='" + divId + "'] input[id='btnSubmit']").show();
    $("div[id='" + divId + "'] input[class='btn btn-primary']").show();
    $("div[id='" + divId + "'] div[id='saveBtnDiv']").show();
    $("div[id='" + divId + "'] ul[class='layout']").show();
}

// 将指定DIV下的所有元素值清空
function clearDivVal(divId) {
    $("div[id='" + divId + "'] input").val("");
    $("div[id='" + divId + "'] textarea").val("");
    $("div[id='" + divId + "'] input[type='radio']").removeAttr("checked");
    $("div[id='" + divId + "'] input[type='checkbox']").removeAttr("checked");
    $("div[id='" + divId + "'] select:selected").removeAttr("selected");
}

// 使页面的ipnut textaera select button 必填项为只读
function setPageReadOnly(removeBtn) {
    $("input").attr("readOnly", "readOnly");
    $("textarea").attr("readOnly", "readOnly");
    disableSelect2();
    $("font").remove();
    if (removeBtn) {
        $("div[class='searchButton']").remove();
    }
}
// 各个详情页面的撤回操作
function reDo(taskId, msgId, redoId, url) {
    $("#" + msgId).html("");
    $.post(url, function(data) {
        resetTip();
        if ("success" == data) {
            $("#" + redoId).hide();
            showTip("撤回成功，请及时办理。", "success", 1000, 0);
            $("#" + msgId).html("<div id='messageBox' class='alert alert-success'><button data-dismiss='alert' class='close'>×</button>撤回成功，请及时办理。</div>");
        } else {
            alertx("系统异常！");
        }
    });
};
/* 判断是否为空 */
function checkIsNull(value) {
    if (typeof value == "undefined")
        return true;
    if (value == 0 && (value + "").lenth > 0)
        return false;
    if (value != null && value != "" && value != "null" && value != "NULL" && value != "\"\"" && value != undefined) {
        return false;
    } else {
        return true;
    }
}
/* 加载页面到指定的div中 */
$.loadDiv = function(_divId, _url, _param, _type, _successCallBack) {
    var type = "GET";
    if (!checkIsNull(_type)) {
        type = _type;
    }
    $.ajax({
        type : type,
        url : _url,
        data : _param,
        dataType : "html",
        async : false,
        success : function(data) {
            $("#" + _divId).html(data);
            if ($.isFunction(_successCallBack)) {
                _successCallBack(data.data);
            }
        },
        error : function() {
            alertx("加载失败！");
        }
    });
};
/* 序列化表单 */
$.fn.serializeJson = function() {
    var serializeObj = {};
    $(this.serializeArray()).each(function() {
        serializeObj[this.name] = this.value;
    });
    return serializeObj;
};

function removeDotProperty(obj) {
    if (typeof obj == "string" || obj == null) {
        return obj;
    } else {
        if (!obj.sort) {
            var newObj = {};
            for ( var i in obj) {
                if (i.indexOf(".") < 0) {
                    newObj[i] = removeDotProperty(obj[i]);
                }
            }
            return newObj;
        } else {
            var array = new Array();
            for (var i = 0; i < obj.length; i++) {
                array.push(removeDotProperty(obj[i]));
            }
            return array;
        }
    }
}

/* jBox方式弹出页面 */

function openJBox(_jboxId, _url, _title, _width, _height, _params) {
    $.jBox.open("iframe:" + _url, _title, _width, _height, {
        id : _jboxId,
        ajaxData : _params,
        persistent : true,
        dragLimit : true,
        buttons : {},
        loaded : function(h) {
            $(".jbox-content", document).css("overflow-y", "hidden");
        }
    });
}

function closeJBox(_jboxId) {
    if (_jboxId) {
        parent.$.jBox.close(_jboxId);
    } else {
        parent.$.jBox.close(true);
    }
}

var jobxID = null;
function closeDialog() {
    alert(jobxID);
    $("#" + jobxID).dialog('close');
}
/* 保存表单 */
function saveJson(_url, _json, _callback) {
    $.ajax({
        type : "post",
        url : _url,
        contentType : "application/json",
        data : _json,
        dataType : "json",
        errors : function(data) {
            alertx("保存失败，请查看后台信息");
        }
    }).done(function(data) {
        if ($.isFunction(_callback)) {
            _callback(data);
        } else {
            alertx(data.message);
        }
    }).fail(function(data) {
        alertx("保存失败，请查看后台信息");
    });
}

function setName(data, str, proCode, cityCode, areaCode) {

    var flag = 0;
    for (var i = 0; i < data.length; i++) {
        if (data[i][0] == proCode) {
            $("#s2id_" + str + "Province>.select2-choice>.select2-chosen").html(data[i][1]);
            flag = i;
            break;
        }
    }
    $("#" + str + "Province").attr("data-code", proCode);
    $($("#" + str + "Province option")[flag + 1]).prop("selected", true);

    var data1 = data[flag][2];
    for (var j = 0; j < data1.length; j++) {
        if (data1[j][0] == cityCode) {
            $("#s2id_" + str + "City>.select2-choice>.select2-chosen").html(data1[j][1]);
            flag = j;
            break;
        }
    }
    $("#" + str + "City").html('<option value="-1" data-name="请选择">请选择</option>');
    for (var m = 0; m < data1.length; m++) {
        $("#" + str + "City").append("<option value='" + data1[m][0] + "' data-name='" + data1[m][1] + "'>" + data1[m][1] + "</option>");
    }
    $("#" + str + "City").attr("data-code", cityCode);
    $($("#" + str + "City option")[flag + 1]).prop("selected", true);
    var data2 = data1[flag][2];
    for (var k = 0; k < data2.length; k++) {
        if (data2[k][0] == areaCode) {
            $("#s2id_" + str + "Distinct>.select2-choice>.select2-chosen").html(data2[k][1]);
            flag = k;
            break;
        }
    }
    $("#" + str + "Distinct").html('<option value="-1" data-name="请选择">请选择</option>');
    for (var m = 0; m < data2.length; m++) {
        $("#" + str + "Distinct").append("<option value='" + data2[m][0] + "' data-name='" + data2[m][1] + "'>" + data2[m][1] + "</option>");
    }
    $("#" + str + "Distinct").attr("data-code", areaCode);
    $($("#" + str + "Distinct option")[flag + 1]).prop("selected", true);
}
function closeAndReload() {
    parent.parent.page();
    closeJBox(true);
}
function closeAndReloadACC() {
    parent.page();
    closeJBox(true);
}
function closeAndReloadPostLoan() {
    parent.page();
    closeJBox(true);
}
/* pre会占空间，所以尽量将pre放到页面前面，否则当输入的内容过多时，会将页面撑大 */
function adjustTextareaLength(textareaId, preId) {
    var textarea = document.getElementById(textareaId);
    var pre = document.getElementById(preId);
    if(textarea != null && pre != null){
        pre.textContent = textarea.value;
        textarea.style.height = pre.offsetHeight + 'px';
    }
}
/**
 * 将页面弹框改成直接覆盖当前页面
 */
function goToPage(headUrl, id) {
    var headUr = document.getElementById(id);
    headUr.href = headUrl;
    headUr.click();
    loading("正在加载，请稍等");
}

// json转为form表单
var JSONToform = function(form, oJson) {
    var max = form.elements.length;
    var i = 0;
    var chkRadioName = ",";
    var e, ename, eValue = "";
    for (i = 0; i < max; i++) {
        e = form.elements[i];
        ename = e.name;
        switch (e.type) {
            case 'checkbox':
            case 'radio':
                if (oJson[ename])
                    handchkradio(form, ename, oJson[ename]);
                break;
            case 'hidden':
            case 'text':
            case 'select-one':
            case 'textarea':
                if (oJson[ename]){
                    e.value = oJson[ename];
                }
                break;
            default:
        }
    }
}

var handchkradio = function(form, ename, strValue) {
    strValue = "," + strValue + ",";
    var max = form.elements.length;
    var i = 0;
    var oE;
    var strTemp = "";
    for (i = 0; i < max; i++) {
        oE = form.elements[i];
        if (oE.name != ename)
            continue; // 元素名称不同，就跳过
        oE.checked = false;
        if (strValue.indexOf("," + escape(oE.value) + ",") >= 0)
            oE.checked = true;
    }
}
function selectCheckBoxes(checkName,checkedValId){
    var checkedVal = $("#"+checkedValId).val();
    if(checkedVal){
        var checkedArray = checkedVal.split(",");
        if(checkName){
            for(var i = 0;i<checkedArray.length;i++){
                $("input:checkbox[name="+checkName+"]").each(function(){
                    if($(this).val()==checkedArray[i]){
                        $(this).attr("checked","checked");
                    }
                });
            }
        }
    }
}

function getCheckBoxesValue(value,checkBoxes){
    var valueArray = value.split(",");
    for(var i = 0;i<valueArray.length;i++){
        $.each(checkBoxes,function(j,checkBox){
            var checkValue = $(checkBox).val();
            if(valueArray[i] == checkValue){
                $(checkBox).attr("checked",true);
            }
        });
    }
}
