package com.gq.ged.common.filter;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * Created by wyq_tomorrow on 2017/12/1.
 */
@WebFilter(filterName = "druidStatFilter", urlPatterns = "/*", initParams = {
    @WebInitParam(name = "exclusions", value = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid*//*")// 忽略资源
})
public class DruidStatFilter extends WebStatFilter {
}
