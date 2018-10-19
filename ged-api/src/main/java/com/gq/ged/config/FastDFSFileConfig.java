package com.gq.ged.config;

import com.gqhmt.fs.fdfs.FastDFSFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by wyq_tomorrow on 2018/1/19.
 */
@Configuration
public class FastDFSFileConfig {

  @Value("${gq.ged.fast.trackerServers}")
  String trackerServers;

  @Bean("fileService")
  public FastDFSFileService getFileService() {
    FastDFSFileService fastDFSFileService = new FastDFSFileService();
    fastDFSFileService.setTrackerServers(trackerServers);
    fastDFSFileService.setMaxPoolSize(50);
    fastDFSFileService.setMinPoolSize(30);
    fastDFSFileService.setWaitTimes(200);
    fastDFSFileService.init();
    return fastDFSFileService;
  }
}
