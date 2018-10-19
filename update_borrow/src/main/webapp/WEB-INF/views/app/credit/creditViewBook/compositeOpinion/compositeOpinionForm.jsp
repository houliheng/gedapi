<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">

	$(document).ready(function() {
	adjustTextareaLength("sugession","pre");
	adjustTextareaLength("suggestionDesc","pre1");
	adjustTextareaLength("composeId","pre2");
	adjustTextareaLength("positeId","pre3");
	});
function zhxxClick(){
$("#zhxxId").toggle(600);
}
</script>
<div class="searchInfo">
	<h3 onclick="zhxxClick()" class="searchTitle">综合信息</h3>
	<div id="zhxxId" class="searchCon">
		<sys:message content="${message}" />
		<table class="filter" style="width: 100%;">
			<tr>
				<td class="ft_label" style="width: 11%;">外访人员1：</td>
				<td class="ft_content" style="width: 80%;">
					<input type="text" id="taskId" name="taskId" readonly="readonly" value="${comprehensiveOpinionMap.visitor1}" />
				</td>
			</tr>
			<tr>
				<td class="ft_label" style="width: 11%;">外访综合意见1：</td>
				<td class="ft_content" style="width: 80%;">
					<textarea maxlength="1000" id="sugession" name="sugession" class="input-xxlarge textarea-style "  onkeyup="adjustTextareaLength('sugession','pre')" readonly="true">${comprehensiveOpinionMap.suggession1}</textarea>
					<pre class="input-xxlarge pre-style" id="pre"></pre>
					<pre class="input-xxlarge pre-style" id="pre1"></pre>
					<pre class="input-xxlarge pre-style" id="pre2"></pre>
					<pre hidden="hidden" class="input-xxlarge pre-style" id="pre3"></pre>
					<font color="red"></font>
				</td>
			</tr>
			<tr>
				<td class="ft_label" style="width: 11%;">外访人员2：</td>
				<td class="ft_content" style="width: 80%;">
					<input type="text" htmlEscape="false" maxlength="20" class="input-medium" value="${comprehensiveOpinionMap.visitor2}" readonly="true" />
				</td>
			</tr>
			<tr>
				<td class="ft_label" style="width: 11%;">外访综合意见2：</td>
				<td class="ft_content" style="width: 80%;">
					<textarea maxlength="1000" id="suggestionDesc" name="suggestionDesc"  class="input-xxlarge textarea-style "  onkeyup="adjustTextareaLength('suggestionDesc','pre1')" readonly="true">${comprehensiveOpinionMap.suggession2}</textarea>
					<font color="red"></font>
				</td>
			</tr>
			<tr>
				<td class="ft_label" style="width: 11%;">网查结果有无异常：</td>
				<td class="ft_content" style="width: 80%;">
					<input type="text" htmlEscape="false" maxlength="20" class="input-medium" value="${(comprehensiveOpinionMap.webIsAbnormal == '1')?'异常':'正常'}" readonly="true" />
				</td>
			</tr>
			<tr>
				<td class="ft_label" style="width: 11%;">网查备注：</td>
				<td class="ft_content" style="width: 80%;">
					<textarea htmlEscape="false" id="composeId" maxlength="1000" class="input-xxlarge textarea-style "  onkeyup="adjustTextareaLength('composeId','pre2')" readonly="true" >${comprehensiveOpinionMap.webSuggession}</textarea>
				</td>
			</tr>
			<tr>
				<td class="ft_label" style="width: 11%;">电核结果有无异常：</td>
				<td class="ft_content" style="width: 80%;">
					<input type="text" htmlEscape="false" maxlength="20" class="input-medium" value="${(comprehensiveOpinionMap.phoneIsAbnormal == '1')?'异常':'正常'}" readonly="true" />
				</td>
			</tr>
			 <tr>
				<td class="ft_label" style="width: 11%;">电核备注：</td>
				<td class="ft_content" style="width: 80%;">
					<textarea htmlEscape="false" id="positeId" maxlength="20" class="input-xxlarge textarea-style "  onkeyup="adjustTextareaLength('positeId','pre3')"  readonly="true" >${comprehensiveOpinionMap.phoneSuggession}</textarea>
				</td>
			</tr> 
			
		</table>
	
			
			
			
		
	</div>
</div>