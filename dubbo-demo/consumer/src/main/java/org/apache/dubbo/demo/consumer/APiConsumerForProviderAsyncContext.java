package org.apache.dubbo.demo.consumer;

import com.books.dubbo.demo.api.GrettingServiceRpcContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;

@Slf4j
public class APiConsumerForProviderAsyncContext {
  public static void main(String[] args) {
    ReferenceConfig<GrettingServiceRpcContext> referenceConfig = new ReferenceConfig<>();
    referenceConfig.setApplication(new ApplicationConfig("first-dubbo-consumer"));
    referenceConfig.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181"));
    referenceConfig.setInterface(GrettingServiceRpcContext.class);
    referenceConfig.setTimeout(5000);

    referenceConfig.setVersion("1.0.0");
    referenceConfig.setGroup("dubbo");

    GrettingServiceRpcContext greetingService = referenceConfig.get();

    // 设置隐士参数
    RpcContext.getContext().setAttachment("company", "alibaba");

    log.info("{}", greetingService.sayHello("world"));
  }
}
