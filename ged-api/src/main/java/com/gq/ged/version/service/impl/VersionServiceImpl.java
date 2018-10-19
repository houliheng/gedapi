package com.gq.ged.version.service.impl;


import com.gq.ged.common.utils.copy.BeanCopyTools;
import com.gq.ged.version.controller.res.VersionResForm;
import com.gq.ged.version.dao.mapper.SystemVersionMapper;
import com.gq.ged.version.dao.model.SystemVersion;
import com.gq.ged.version.dao.model.SystemVersionExample;
import com.gq.ged.version.service.VersionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wrh on 2017/9/18.
 */
@Service
public class VersionServiceImpl implements VersionService {
  static Logger logger = LoggerFactory.getLogger(VersionServiceImpl.class);

  @Resource
  private SystemVersionMapper systemVersionMapper;

  @Override
  public VersionResForm selectVersionByDev(String devicePlatform, String version) {
    if (devicePlatform == null) {
      devicePlatform = "android";
    }
    int deviceCode = 1;
    if ("android".equals(devicePlatform)) {
      deviceCode = 1;
    } else if ("ios".equals(devicePlatform)) {
      deviceCode = 2;
    }
    // 判断首付需要强制更新
    int versionCount = versionCount(deviceCode, version);
    logger.info("versionCount========"+versionCount);
    if (versionCount > 0) {
      // 强制更新
      VersionResForm versionResForm = new VersionResForm();
      SystemVersion systemVersion = systemVersion(deviceCode);
      copyVersion(systemVersion, versionResForm);
      // 同时更新状态为强制
      if (systemVersion.getStatus() != 1) {
        versionResForm.setStatus(1);
      }
      StringBuffer sb=new StringBuffer();
      versionResForm.setTitle("最新版本"+systemVersion.getVersionCode());
      return versionResForm;
    } else {
      VersionResForm versionResForm = new VersionResForm();
      SystemVersion systemVersion = systemVersion(deviceCode);
      if (systemVersion != null) {
        copyVersion(systemVersion, versionResForm);
        String currentVersion = versionResForm.getVersionCode();
        String iversion = version.replaceAll("\\.", "");//传过来的
        String sversion = currentVersion.replaceAll("\\.", "");//查出来的当前版本
        if (Integer.parseInt(iversion) >= Integer.parseInt(sversion)) {
              versionResForm.setStatus(0);
              versionResForm.setTitle("");
        }else{
          versionResForm.setTitle("最新版本(V"+systemVersion.getVersionCode()+")");
        }
      } else {
        // 不需要更新
        versionResForm.setStatus(0);
      }
      return versionResForm;
    }
  }


  private void copyVersion(SystemVersion systemVersion, VersionResForm versionResForm) {
    String[] excludeAtts = new String[]{"id", "createTime", "modifyTime", "createId", "modifyId"};
    try {
      BeanCopyTools.copyPropertiesExclude(systemVersion, versionResForm, excludeAtts);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 查询
   *
   * @return
   */
  public int versionCount(int deviceCode, String version) {
    SystemVersionExample example = new SystemVersionExample();
    SystemVersionExample.Criteria criteria = example.createCriteria();
    criteria.andDevicePlatformEqualTo(deviceCode);
    criteria.andVersionCodeGreaterThan(version);
    criteria.andStatusEqualTo(1);
    criteria.andIsEnabledEqualTo((byte) 1);
    return systemVersionMapper.countByExample(example);
  }

  /**
   * 根据类型查询最新的一条记录
   *
   * @param deviceCode
   * @return
   */
  public SystemVersion systemVersion(int deviceCode) {
    SystemVersionExample example = new SystemVersionExample();
    example.setOrderByClause("create_time desc");
    SystemVersionExample.Criteria criteria = example.createCriteria();
    criteria.andDevicePlatformEqualTo(deviceCode);
    criteria.andIsEnabledEqualTo((byte) 1);
    List<SystemVersion> list = systemVersionMapper.selectByExample(example);
    if (list == null || list.size() == 0) {
      return null;
    }
    return list.get(0);
  }
}


