package com.resoft.credit.applyRegister.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.resoft.common.utils.AjaxView;
import com.resoft.common.utils.Constants;
import com.resoft.credit.applyRegister.entity.ActTaskParam;
import com.resoft.credit.applyRegister.entity.ApplyRegisterVO;
import com.resoft.credit.applyRegister.service.ComprehensiveQueryService;
import com.resoft.credit.taskCenter.service.TaskCenterService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.act.entity.MyMap;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 综合查询Controller
 * 
 * @author wuxi01
 * @version 2016-03-22
 */
@Controller
@RequestMapping(value = "${adminPath}/credit/comprehensiveQuery")
public class ComprehensiveQueryController extends BaseController {

	@Autowired
	private ComprehensiveQueryService comprehensiveQueryService;

	@Autowired
	private ActTaskService actTaskService;

	@Autowired
	private TaskCenterService taskCenterService;


	@ModelAttribute
	public ApplyRegisterVO get(@RequestParam(required = false) String id) {
		ApplyRegisterVO entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = comprehensiveQueryService.get(id);
		}
		if (entity == null) {
			entity = new ApplyRegisterVO();
		}
		return entity;
	}

	@RequiresPermissions("credit:applyRegisterVO:view")
	@RequestMapping(value = { "list", "" })
	public String list(ApplyRegisterVO applyRegisterVO, Model model, String flag) {
		model.addAttribute("companyId", UserUtils.getUser().getCompany().getId());
		model.addAttribute("applyRegisterVO", applyRegisterVO);
		model.addAttribute("flag", flag);
		return "app/credit/comprehensiveQuery/comprehensiveQueryList";
	}

	// 查询
	@ResponseBody
	@RequestMapping(value = "/getData")
	public Map<String, Object> getData(String flag, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = Maps.newHashMap();
		Map<String, Object> parMap = Maps.newHashMap();
		List<Map<String, Object>> plans = Lists.newArrayList();
		Long totalRecountLong = 0L;
		try {
			// 当前页
			String page = request.getParameter("page");
			// 每页显示的行数
			String rows = request.getParameter("rows");
			String custName = request.getParameter("custName");
			String idType = request.getParameter("idType");
			String idNum = request.getParameter("idNum");
			String registerName = request.getParameter("registerName");
			String applyStatus = request.getParameter("applyStatus");
			String applyNo = request.getParameter("applyNo");
			String orgId = request.getParameter("orgId");
			String contractNo = request.getParameter("contractNo");
			parMap.put("custName", custName);
			parMap.put("idType", idType);
			parMap.put("idNum", idNum);
			parMap.put("registerName", registerName);
			parMap.put("applyStatus", applyStatus);
			parMap.put("applyNo", applyNo);
			parMap.put("contractNo", contractNo);
			if (StringUtils.isNull(orgId)) {
				parMap.put("orgId", UserUtils.getUser().getCompany().getId());
			} else {
				parMap.put("orgId", orgId);
			}
			if (Constants.COMPREHENSIVE_QUERY_FLAG.equals(flag)) {
				parMap.put("officeId", UserUtils.getUser().getOffice().getId());
				totalRecountLong = comprehensiveQueryService.findHQListCount(parMap);
			} else {
				totalRecountLong = comprehensiveQueryService.findComprehensiveQueryListCount(parMap);
			}
			// 数据总数
			int totalRecount = totalRecountLong.intValue();
			// 总页数
			int totalPage = totalRecount % Integer.parseInt(rows) == 0 ? totalRecount / Integer.parseInt(rows) : totalRecount / Integer.parseInt(rows) + 1;
			parMap.put("pageStart", Integer.parseInt(rows) * (Integer.parseInt(page) - 1));
			parMap.put("pageEnd", Integer.parseInt(rows));
			if(Constants.COMPREHENSIVE_QUERY_FLAG.equals(flag)){
				plans = comprehensiveQueryService.findHQList(parMap);
			}else{
				plans = comprehensiveQueryService.findComprehensiveQueryList(parMap);
			}
			if (plans != null && plans.size() != 0) {
				for (Map<String, Object> plan : plans) {
					String idTypeName = DictUtils.getDictLabel(plan.get("idType").toString(), "CUSTOMER_P_ID_TYPE", null);
					plan.put("idType", idTypeName);
				}
			}
			params.put("totalPages", totalPage);
			params.put("totalRecount", totalRecount);
			params.put("dataList", plans);
		} catch (Exception e) {
			logger.error("综合查询列表信息加载失败！", e);
		}
		return params;
	}

	// 查询子表格
	@ResponseBody
	@RequestMapping(value = "/getSubData")
	public Map<String, Object> getSubData(ApplyRegisterVO applyRegisterVO, String flag, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = Maps.newHashMap();
		Map<String, Object> parMap = Maps.newHashMap();
		List<Map<String, Object>> plans = Lists.newArrayList();
		try {
			parMap.put("applyNo", applyRegisterVO.getApplyNo());
			plans = comprehensiveQueryService.findComprehensiveQuerySubList(parMap);
			if (plans != null && plans.size() != 0) {
				for (Map<String, Object> plan : plans) {
					String idTypeName = DictUtils.getDictLabel(plan.get("subIdType").toString(), "CUSTOMER_P_ID_TYPE", null);
					plan.put("subIdType", idTypeName);
				}
				params.put("subDataList", plans);
			} else {
				Map<String, Object> param = Maps.newHashMap();
				param.put("subCustName", applyRegisterVO.getCustName());
				param.put("subApplyNo", applyRegisterVO.getApplyNo());
				param.put("subIdType", DictUtils.getDictLabel(applyRegisterVO.getIdType(), "CUSTOMER_P_ID_TYPE", null));
				param.put("subIdNum", applyRegisterVO.getIdNum());
				param.put("subApplyAmount", applyRegisterVO.getApplyAmount());
				param.put("subContractAmount", applyRegisterVO.getContractAmount());
				param.put("subRegisterDate", applyRegisterVO.getRegisterDate());
				param.put("companyName", applyRegisterVO.getCompany().getName());
				plans.add(param);
				params.put("subDataList", plans);
			}
		} catch (Exception e) {
			logger.error("综合查询列表信息加载失败！", e);
		}
		return params;
	}

	/**
	 * 查询流程参数
	 * 
	 * @param applyRegisterVOId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "form")
	public Map<String, Object> form(String applyRegisterVOId) {
		Map<String, Object> resultMap = Maps.newHashMap();
		try {
			ApplyRegisterVO applyRegisterVO = comprehensiveQueryService.get(applyRegisterVOId);
			String applyNo = applyRegisterVO.getApplyNo();
			ActTaskParam actTaskParam = comprehensiveQueryService.getActTaskParamByApplyNo(applyNo);
			if (actTaskParam == null) {
				resultMap.put("flag", "notask");// 没有任务信息
				return resultMap;
			} else {
				resultMap.put("applyNo", applyNo);
				resultMap.put("taskId", actTaskParam.getTaskId());
				resultMap.put("taskDefKey", actTaskParam.getTaskDefKey());
				resultMap.put("execId", actTaskParam.getExecId());
				resultMap.put("procDefId", actTaskParam.getProcDefId());
				resultMap.put("procInstId", actTaskParam.getProcInstId());
				resultMap.put("headUrl","/credit/comprehensiveQuery/list" );
				resultMap.put("status", Constants.ACTTASKPARAM_STATUS_Y);// 综合查询时，按照已办处理，即全部已读
				resultMap.put("flag", "success");// 综合查询时，按照已办处理，即全部已读
				return resultMap;
			}
		} catch (Exception e) {
			logger.error("查询任务详情发生异常，可能是由于并行任务原因，导致由applyNo查询taskDefKey过程中查到多条记录造成！", e);
			resultMap.put("flag", "notask");// 没有任务信息
			return resultMap;
		}
	}

	// 流程轨迹
	@RequestMapping(value = "/processTrack")
	public String processTrack(HttpServletRequest request, HttpServletResponse response, Model model, String applyRegisterVOId) {
		ApplyRegisterVO applyRegisterVO = comprehensiveQueryService.get(applyRegisterVOId);
		String procInstId = applyRegisterVO.getProcInsId();
		List<MyMap> proList = actTaskService.findProcessTask(procInstId);
		proList = taskCenterService.sortList(proList);
		model.addAttribute("proList", proList);
		return "app/credit/taskCenter/processTrack";
	}

	// 详情
	@RequestMapping(value = "doBusiness/{procDefId}/{execId}/{taskDefKey}")
	public @ResponseBody
	Map<String, String> doBusiness(@PathVariable("procDefId") String procDefId, @PathVariable("taskDefKey") String taskDefKey, HttpServletResponse response, String taskId) throws Exception {
		// 判定是否能够签收
		Map<String, String> msgMap = new HashMap<String, String>();
		String formKey = actTaskService.getFormKey(procDefId, taskDefKey);
		msgMap.put("location", formKey);
		msgMap.put("flag", "success");
		return msgMap;
	}

	// 查询
	@ResponseBody
	@RequestMapping(value = "/showDown")
	public AjaxView showDown(String applyRegisterVOId) {
		AjaxView ajaxView = new AjaxView();
		try {
			// 验证条件
			HashMap<String, String> map = new HashMap<String, String>();
			map = comprehensiveQueryService.showDown(applyRegisterVOId);
			map.put("TASKDEFINITIONKEY", Constants.UTASK_ZGSJLSH);
			ajaxView.put("map", map);
			return ajaxView;
		} catch (Exception e) {
			logger.error("综合查询已办流程错误", e);
			return ajaxView;
		}
	}

}