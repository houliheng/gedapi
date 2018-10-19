package com.resoft.outinterface.rest.SVFinancialToThemis.interfacesImpl;

import com.resoft.common.utils.JsonTransferUtils;
import com.resoft.credit.interfaceinfo.entity.InterfaceInfo;
import com.resoft.credit.interfaceinfo.service.InterfaceInfoService;
import com.resoft.outinterface.SV.client2.WorkOrderNewField;
import com.resoft.outinterface.rest.GQget.client.entry.response.GetClientResponse;
import com.resoft.outinterface.rest.SVFinancialToThemis.interfaces.SVClient;
import com.resoft.outinterface.utils.OutInterfaceUtils;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.HashMap;


@Controller
public class SVClientImpl implements SVClient {

    private static final Logger logger = LoggerFactory.getLogger(SVClientImpl.class);
    private InterfaceInfoService interfaceInfoService = SpringContextHolder.getBean("interfaceInfoService");


    @Override
    public String SVAddFieldInterface(WorkOrderNewField workOrderNewField) {

        String json = null;
        String response = null;
        String code = null;
        try {
            json = JsonTransferUtils.bean2Json(workOrderNewField);
            logger.debug(json);
            response = OutInterfaceUtils.load(Global.getConfig("svAddField"), json);
            HashMap<String,String> map = JsonTransferUtils.json2Bean(response, HashMap.class);
            code = map.get("code");
            logger.debug(map.get("msg"));
            try {
                interfaceInfoService.save(new InterfaceInfo(workOrderNewField.getApp_no(), "外访新增字段接口", response, new Date(),json));
            } catch (Exception e) {
                logger.error("接口信息记录失败！");
            }
        } catch (Exception e) {
            logger.error("外访新增字段接口", e);
            try {
                interfaceInfoService.save(new InterfaceInfo(workOrderNewField.getApp_no(), "外访新增字段接口", "接口访问失败:" + response +e.toString(), new Date(),json));
            } catch (Exception e2) {
                logger.error("接口信息记录失败！", e2);
            }
        }
        return code;
    }
}
