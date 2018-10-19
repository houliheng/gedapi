package com.gq.ged.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import com.gq.ged.common.interceptor.CustomInterceptor;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;


@Configuration
public class AppWebMvcConfigurer extends WebMvcConfigurerAdapter {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new CustomInterceptor()).excludePathPatterns("/error/**")
        .excludePathPatterns("/swagger-resources/**").excludePathPatterns("/v2/**");
    super.addInterceptors(registry);
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // 将所有/static/** 访问都映射到classpath:/static/ 目录下
    registry.addResourceHandler("swagger-ui.html")
        .addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
    registry.addResourceHandler("/webjars/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
  }

  @Bean(name = "multipartResolver")
  public MultipartResolver multipartResolver()
  {
    CommonsMultipartResolver resolver = new CommonsMultipartResolver();
    resolver.setDefaultEncoding("UTF-8");
    // resolver.setResolveLazily(true);// resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常
    // resolver.setMaxInMemorySize(40960);
    resolver.setMaxUploadSize(10 * 1024 * 1024);// 上传文件大小 5M 5*1024*1024
    return resolver;
  }


  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/signin").setViewName("signin");
    registry.addViewController("/error/404.html").setViewName("404");
    registry.addViewController("/error/505.html").setViewName("505");
  }

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    super.configureMessageConverters(converters);
    FastJsonHttpMessageConverter4 converter = new FastJsonHttpMessageConverter4();
    FastJsonConfig fastJsonConfig = new FastJsonConfig();
    fastJsonConfig.setSerializerFeatures(SerializerFeature.QuoteFieldNames,
        SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullNumberAsZero,
        SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullStringAsEmpty,
        SerializerFeature.WriteNullBooleanAsFalse, SerializerFeature.WriteDateUseDateFormat,
        SerializerFeature.BrowserCompatible);
    converter.setFastJsonConfig(fastJsonConfig);
    converters.add(converter);
  }
}
