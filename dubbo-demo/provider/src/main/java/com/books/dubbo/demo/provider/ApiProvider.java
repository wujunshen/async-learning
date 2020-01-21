package com.books.dubbo.demo.provider;

import com.books.dubbo.demo.api.GreetingService;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;

@Slf4j
public class ApiProvider {

  public static void main(String[] args) throws IOException {
    // 1.创建ServiceConfig实例
    ServiceConfig<GreetingService> serviceConfig = new ServiceConfig<>();
    // 2.设置应用程序配置
    serviceConfig.setApplication(new ApplicationConfig("first-dubbo-provider"));

    // 3.设置服务注册中心信息
    RegistryConfig registryConfig = new RegistryConfig("zookeeper://127.0.0.1:2181");
    serviceConfig.setRegistry(registryConfig);
    // 4.设置接口与实现类
    serviceConfig.setInterface(GreetingService.class);
    serviceConfig.setRef(new GreetingServiceImpl());

    // 5.设置服务分组与版本
    serviceConfig.setVersion("1.0.0");
    serviceConfig.setGroup("dubbo");

    // 6.设置线程池策略

    // 7.导出服务
    serviceConfig.export();

    // 8.挂起线程，避免服务停止
    log.info("server is started");
    System.in.read();
  }
}
