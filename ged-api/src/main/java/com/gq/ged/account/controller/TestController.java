package com.gq.ged.account.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wyq_tomorrow on 2018/3/24.
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/page")
    public String test(){
        return "index";
    }
}
