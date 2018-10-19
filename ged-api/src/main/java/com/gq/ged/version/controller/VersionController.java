package com.gq.ged.version.controller;


import com.gq.ged.common.resp.ResponseEntity;
import com.gq.ged.common.resp.ResponseEntityUtil;
import com.gq.ged.common.web.BaseController;
import com.gq.ged.version.controller.req.VersionReqForm;
import com.gq.ged.version.controller.res.VersionResForm;
import com.gq.ged.version.service.VersionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by wrh on 2017/9/18.
 */
@Api(value = "版本号", description = "版本号")
@RestController
@RequestMapping(value = "/version", produces = MediaType.APPLICATION_JSON_VALUE)
public class VersionController extends BaseController {
    static Logger logger = LoggerFactory.getLogger(VersionController.class);
    @Resource
    VersionService versionService;

    @ApiOperation(value = "查询版本", notes = "查询版本")
    @RequestMapping(value = "/selectVersion", method = RequestMethod.POST)
    public ResponseEntity<Object> selectVersion(@RequestBody VersionReqForm reqForm) throws Exception {
        String device = reqForm.getDevice();
        String version = reqForm.getVersion();
        logger.info(device);
        logger.info(version);
        VersionResForm versionResForm = versionService.selectVersionByDev(device, version);
        return ResponseEntityUtil.success(versionResForm);
    }

//    @ApiOperation(value = "查询版本状态", notes = "查询版本状态")
//    @RequestMapping(value = "/selectCheckStatus", method = RequestMethod.POST)
//    public ResponseEntity<CheckVersionResForm> selectCheckStatus(@RequestBody CheckVersionReqForm reqForm) {
//        String iosVersion = reqForm.getApp_version_header();
//        logger.info("iosVersion=" + iosVersion);
//       // CheckVersionResForm checkVersionResForm = versionService.selectCheckByVer(iosVersion);
//        //return ResponseEntityUtil.success(checkVersionResForm);
//        return null;
//    }
}
