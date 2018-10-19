package com.gq.ged.async;

import com.gq.ged.activemq.service.JmsProvider;
import com.gq.ged.user.service.AsyncTaskService;
import com.gq.ged.user.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created by wyq_tomorrow on 2018/2/2.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AsyncTaskTest {
  Logger logger = LoggerFactory.getLogger(this.getClass());
  @Resource
  AsyncTaskService asyncTaskService;
  @Resource
  RedissonClient redissonClient;
  @Resource
  JmsProvider jmsProvider;

  @Test
  public void testNoAsync() {
    Long stime = System.currentTimeMillis();
    for (int i = 0; i < 100; i++) {
      RLock rlock = redissonClient.getLock(i + "");
      rlock.lock();
      asyncTaskService.executeAsyncTask(i);
      rlock.unlock();
    }
    Long etime = System.currentTimeMillis();
    logger.info("All tasks finished.===" + (etime - stime));
  }

  @Test
  public void testAsync() {
    Long stime = System.currentTimeMillis();
    int sum = 0;
    for (int i = 0; i < 100; i++) {
      asyncTaskService.executeAsyncTask(i);
      sum += i;
    }
    if (sum == 4950) {
      logger.info("All tasks finished.===" + (sum));
      Long etime = System.currentTimeMillis();
      logger.info("All tasks finished.===" + (etime - stime));
    }
  }
}
