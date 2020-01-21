package com.books.dubbo.demo.provider;

import com.books.dubbo.demo.api.GrettingServiceRpcContext;
import java.io.IOException;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;

@Slf4j
public class ApiProviderForAsyncContext {

  public static void main(String[] args) throws IOException {
    ServiceConfig<GrettingServiceRpcContext> serviceConfig = new ServiceConfig<>();
    serviceConfig.setApplication(new ApplicationConfig("first-dubbo-provider"));
    serviceConfig.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181"));
    serviceConfig.setInterface(GrettingServiceRpcContext.class);
    serviceConfig.setRef(new GrettingServiceAsyncContextImpl());

    serviceConfig.setVersion("1.0.0");
    serviceConfig.setGroup("dubbo");

    // 设置线程池策略
    HashMap<String, String> parameters = new HashMap<>();
    parameters.put("threadpool", "mythreadpool");
    serviceConfig.setParameters(parameters);

    serviceConfig.export();

    log.info("server is started");
    System.in.read();
  }
}
