package com.gq.ged.file.service.impl;

import com.gq.ged.file.controller.req.FileReqForm;
import com.gq.ged.file.dao.mapper.SystemFileMapper;
import com.gq.ged.file.dao.model.SystemFile;
import com.gq.ged.file.service.SystemFileService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

/**
 * Created by wyq_tomorrow on 2018/1/19.
 */
@Service
public class SystemFileServiceImpl implements SystemFileService {
  @Resource
  SystemFileMapper systemFileMapper;

  @Override
  public void uploadFileService(List<FileReqForm> list, Long userId) {
    list.forEach(n -> {
      SystemFile systemFile = new SystemFile();
      try {
        BeanUtils.copyProperties(systemFile, n);
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      }
      systemFile.setCreateId(userId);
      systemFile.setCreateTime(new Date());
      systemFile.setModifyId(userId);
      systemFileMapper.insertSelective(systemFile);
    });

  }
}
