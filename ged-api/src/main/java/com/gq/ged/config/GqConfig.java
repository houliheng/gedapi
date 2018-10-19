package com.gq.ged.config;

import com.gq.base.configuration.ConfigManager;
import com.gq.base.properties.ext.ConfigurationPropertyInjectSupport;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.io.IOException;

/**
 * Created by gickeuy on 2018/1/10.
 */
@Configuration
public class GqConfig {

    @Bean(initMethod = "init" )
    public ConfigManager configManager()
    {
        ConfigManager cfg = new ConfigManager();
        cfg.setConfigServer("http://config-api.dev.gqihome.com");
        return cfg;
    }

    @Bean
    @DependsOn(value = "configManager")
    public ConfigurationPropertyInjectSupport configurationPropertyInjectSupport()
    {
        ConfigurationPropertyInjectSupport cpis = new ConfigurationPropertyInjectSupport();
        cpis.setConfigKey("ged-api");
        cpis.setConfigManager(configManager());
        return cpis;
    }

    @Bean
    @DependsOn(value = "configurationPropertyInjectSupport")
    public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer()
    {
        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ppc.setSystemPropertiesMode(2);
        ppc.setFileEncoding("UTF-8");
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = null;
        try {
            resources = resolver.getResources("classpath*:*.properties");
            ppc.setLocations(resources);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ppc;

    }

    @Bean
    public MultipartResolver multipartResolver(){
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        resolver.setResolveLazily(true);//resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常
        resolver.setMaxUploadSize(10*1024*1024);
        return resolver;
    }
}
