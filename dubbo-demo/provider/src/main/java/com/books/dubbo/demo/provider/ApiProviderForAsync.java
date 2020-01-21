package com.books.dubbo.demo.provider;

import com.books.dubbo.demo.api.GrettingServiceAsync;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;

@Slf4j
public class ApiProviderForAsync {

  public static void main(String[] args) throws IOException {
    // 1.创建服务发布实例，并设置
    ServiceConfig<GrettingServiceAsync> serviceConfig = new ServiceConfig<>();
    serviceConfig.setApplication(new ApplicationConfig("first-dubbo-provider"));
    serviceConfig.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181"));
    serviceConfig.setInterface(GrettingServiceAsync.class);
    serviceConfig.setRef(new GrettingServiceAsyncImpl());
    serviceConfig.setVersion("1.0.0");
    serviceConfig.setGroup("dubbo");

    // 2.设置线程池策略

    // 3.导出服务
    serviceConfig.export();

    // 4.阻塞线程
    log.info("server is started");
    System.in.read();
  }
}
