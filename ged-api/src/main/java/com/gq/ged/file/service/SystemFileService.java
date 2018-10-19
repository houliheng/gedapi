package com.gq.ged.file.service;

import com.gq.ged.file.controller.req.FileReqForm;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by wyq_tomorrow on 2018/1/19.
 */
public interface SystemFileService {
  /**
   * 上传文件
   * 
   * @param fileReqForm
   */
  void uploadFileService(List<FileReqForm> list, Long userId);
}
