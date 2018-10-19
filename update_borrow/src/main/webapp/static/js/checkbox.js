
	/**
	 * @reqno: H1509130057
	 * @date-designer:2015年9月20日-wujing01
	 * @date-author:2015年9月20日-wujing01:  复选框全选事件, 获取复选框选中的值,批量删除
	 */
/**
	 * @reqno: AH1509130057
	 * @date-designer:20151008-huangxuecheng
	 * @date-author:20151008-huangxuecheng:  复选框全选事件, 获取复选框选中的值,批量删除
	 */
/**
 * 复选框全选事件
 */
function allCheck(){
	if($('[name=all]:checkbox').attr("checked")=="checked"){
		$('[name=type]:checkbox').attr("checked",true);
	}else{
		$('[name=type]:checkbox').attr("checked",false);
	}
}
/**
 * 获取复选框选中的值
 * @returns
 */
function getCheckedValue(){
	var str = "";
	$("input[name=type]:checkbox").each(function(){
		if($(this).attr("checked")){
			 str += $(this).val() + ",";
		}
	});
	return str;
}
