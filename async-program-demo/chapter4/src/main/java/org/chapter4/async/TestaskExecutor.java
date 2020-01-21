package org.chapter4.async;

import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Slf4j
public class TestaskExecutor {

  public static void main(String[] arg) {
    // 1.创建容器上下文
    ClassPathXmlApplicationContext applicationContext =
        new ClassPathXmlApplicationContext("beans-annotation.xml");

    // 2. 获取AsyncExecutorExample实例并调用打印方法
    log.info("{} begin ", Thread.currentThread().getName());
    AsyncAnnotationExample asyncCommentExample =
        applicationContext.getBean(AsyncAnnotationExample.class);

    // 3.获取异步future并设置回调
    CompletableFuture<String> resultFuture = asyncCommentExample.doSomething();
    resultFuture.whenComplete(
        (t, u) -> {
          if (null == u) {
            log.info("{} {}", Thread.currentThread().getName(), t);
          } else {
            log.info("error:{}", u.getLocalizedMessage());
          }
        });

    log.info("{} end ", Thread.currentThread().getName());
  }
}
