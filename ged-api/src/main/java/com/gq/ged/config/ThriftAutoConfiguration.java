package com.gq.ged.config;


import com.gq.ged.annotation.THttpService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnClass(THttpService.class)
@ConditionalOnWebApplication
public class ThriftAutoConfiguration {

  public interface ThriftConfigurer {
    void configureProxyFactory(ProxyFactory proxyFactory, String path, String type);
  }

  @Bean
  @ConditionalOnMissingBean(ThriftConfigurer.class)
  ThriftConfigurer thriftConfigurer() {
    return new DefaultThriftConfigurer();
  }

  @Bean
  @ConditionalOnMissingBean(TProtocolFactory.class)
  TProtocolFactory thriftProtocolFactory() {
    return new TBinaryProtocol.Factory();
  }

  public static class DefaultThriftConfigurer implements ThriftConfigurer {
    @Override
    public void configureProxyFactory(ProxyFactory proxyFactory, String path, String type) {
      proxyFactory.setOptimize(true);
    }
  }
}
