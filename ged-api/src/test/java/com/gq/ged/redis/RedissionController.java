package com.gq.ged.redis;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gq.ged.account.dao.mapper.AccountCompanyMapper;
import com.gq.ged.account.dao.model.AccountCompany;
import com.gq.ged.common.http.HttpUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created by wyq_tomorrow on 2017/12/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedissionController {
  protected final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Resource
  RedissonClient redissonClient;

  @Resource
  AccountCompanyMapper companyMapper;

  @Test
  public void redissonBucketTest() throws Exception {
    AccountCompany accountCompany = companyMapper.selectByPrimaryKey(260l);
    JSONObject jsonObject = HttpUtils.doPost("http://10.100.161.40:8082/gqilms/f/rest/api/companyAccount",
            JSONObject.toJSONString(accountCompany, SerializerFeature.WriteMapNullValue));
  }
}
