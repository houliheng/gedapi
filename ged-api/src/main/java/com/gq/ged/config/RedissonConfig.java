package com.gq.ged.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by wyq_tomorrow on 2017/12/4.
 */
@Configuration
public class RedissonConfig {
  @Value("${redisson.nodes}")
  private String nodes;

  @Value("${redisson.master}")
  private String master;

  @Value("${redisson.database}")
  private int database;

  @Value("${redisson.connect.type}")
  private String type;

  @Bean
  public RedissonClient redissonClient() {
    String[] urls = nodes.split(",");
    Config config = new Config();
    if("singleton".equals(type)){
      config.useSingleServer().setAddress("redis://10.100.200.61:6379")
              .setSubscriptionConnectionPoolSize(25).setConnectionPoolSize(100).setDatabase(database);
    }else{
      config.useSentinelServers().setMasterName(master).setDatabase(database)
              .addSentinelAddress(urls).setSubscriptionConnectionPoolSize(25);
    }

    RedissonClient client = Redisson.create(config);
    return client;
  }
}
