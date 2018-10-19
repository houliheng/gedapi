package com.gq.ged.log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by wyq_tomorrow on 2017/12/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LogController {
  protected final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Test
  public void contextLoads() {
    logger.trace("I am trace log.");
    logger.debug("I am debug log.");
    logger.warn("I am warn log.");
    logger.error("I am error log.");
  }

}
