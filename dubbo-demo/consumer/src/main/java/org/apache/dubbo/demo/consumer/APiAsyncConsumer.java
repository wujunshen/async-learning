package org.apache.dubbo.demo.consumer;

import com.books.dubbo.demo.api.GreetingService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.RpcContext;

@Slf4j
public class APiAsyncConsumer {
  public static void main(String[] args) throws InterruptedException, ExecutionException {
    // 1.创建引用实例，并设置属性
    ReferenceConfig<GreetingService> referenceConfig = new ReferenceConfig<>();
    referenceConfig.setApplication(new ApplicationConfig("first-dubbo-consumer"));
    referenceConfig.setRegistry(new RegistryConfig("zookeeper://127.0.0.1:2181"));
    referenceConfig.setInterface(GreetingService.class);
    referenceConfig.setVersion("1.0.0");
    referenceConfig.setGroup("dubbo");

    // 2. 设置为异步
    referenceConfig.setAsync(true);

    // 3.引用服务
    GreetingService greetingService = referenceConfig.get();

    log.info("{}", greetingService.sayHello("world"));

    // 4.等待结果
    Future<String> future = RpcContext.getContext().getFuture();
    log.info("{}", future.get());
  }
}
