package org.chapter4.async;

import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Slf4j
@EnableAsync // 开启异步执行
@Component // 注入该Bean到Spring容器
public class AsyncAnnotationExample {
  // @Scheduled(cron = "*/5 * * * * ?") // 每隔5秒执行一次
  public void asyncPrint1() {
    try {
      log.info("{}", Thread.currentThread().getName());
    } catch (Exception e) {
      log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
    }
  }

  @Async
  public void printMessages() {
    for (int i = 0; i < 6; i++) {
      try {
        Thread.sleep(5000);
        log.info("{} Message{}", Thread.currentThread().getName(), i);
      } catch (Exception e) {
        log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
      }
    }
  }

  @Async
  public void testException() throws Exception {
    throw new Exception("error");
  }

  @Async
  public CompletableFuture<String> doSomething() {
    // 1.创建future
    CompletableFuture<String> result = new CompletableFuture<>();
    // 2.模拟任务执行
    try {
      Thread.sleep(1000);
      log.info("{} doSomething", Thread.currentThread().getName());
    } catch (Exception e) {
      log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
    }
    result.complete("done");

    // 3.返回结果
    return result;
  }
}
