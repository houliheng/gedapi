package com.gq.ged.user.service.impl;

import com.gq.ged.user.service.AsyncTaskService;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wyq_tomorrow on 2018/2/2.
 */
@Service
public class AsyncTaskServiceImpl implements AsyncTaskService {
    static Logger logger = LoggerFactory.getLogger(AsyncTaskServiceImpl.class);
    @Resource
    RedissonClient redissonClient;
    @Override
    @Async
    public void executeAsyncTask(Integer i) {
        logger.info("Task"+i+" started.");
    }
}
