package com.gq.ged.version.service;


import com.gq.ged.version.controller.res.VersionResForm;

/**
 * Created by wrh on 2017/9/18.
 */
public interface VersionService {
  /**
   * 根据设备查询信息
   * 
   * @param devicePlatform
   * @return
   */
  VersionResForm selectVersionByDev(String devicePlatform, String version);

  /**
   * 根据版本号查询状态
   * @param iosVersion
   * @return
   */
//  CheckVersionResForm selectCheckByVer(String iosVersion);
}
