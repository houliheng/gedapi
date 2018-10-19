package com.gq.ged.user.job;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.stereotype.Component;

/**
 * Created by wyq_tomorrow on 2018/7/18.
 */
@Component
@JobHandler(value="helloWorld")
public class HelloWorld extends IJobHandler {
    @Override
    public ReturnT<String> execute(String s) throws Exception {
        System.out.println("你好呀");
        return ReturnT.SUCCESS;
    }
}
