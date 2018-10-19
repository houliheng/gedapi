package com.gq.ged.config;

import com.xxl.job.core.executor.XxlJobExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by wyq_tomorrow on 2018/7/18.
 */
@Configuration
public class XxlJobConfig {

  @Value("${xxl.job.admin.addresses}")
  private String addresses;

  @Value("${xxl.job.executor.appname}")
  private String appname;

  @Value("${xxl.job.executor.ip}")
  private String ip;

  @Value("${xxl.job.executor.port}")
  private int port;

  @Value("${xxl.job.executor.logpath}")
  private String logpath;

  @Bean(initMethod = "start", destroyMethod = "destroy")
  public XxlJobExecutor xxlJobExecutor() {
    XxlJobExecutor xxlJobExecutor = new XxlJobExecutor();
    xxlJobExecutor.setIp(ip);
    xxlJobExecutor.setPort(port);
    xxlJobExecutor.setAppName(appname);
    xxlJobExecutor.setAdminAddresses(addresses);
    xxlJobExecutor.setLogPath(logpath);
    return xxlJobExecutor;
  }
}
