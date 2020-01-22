package org.third.chapter.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

/** @author wujunshen */
@Slf4j
public class TestCompletableFutureSet {
  /** 0自定义线程池 */
  private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

  private static final ThreadPoolExecutor POOL_EXECUTOR =
      new ThreadPoolExecutor(
          AVAILABLE_PROCESSORS,
          AVAILABLE_PROCESSORS * 2,
          1,
          TimeUnit.MINUTES,
          new LinkedBlockingQueue<>(5),
          new ThreadPoolExecutor.CallerRunsPolicy());

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    // 1.创建一个CompletableFuture对象
    CompletableFuture<String> future = new CompletableFuture<>();

    // 2.开启线程计算任务结果，并设置
    POOL_EXECUTOR.execute(
        () -> {
          // 2.1休眠3s，模拟任务计算
          try {
            Thread.sleep(3000);
          } catch (InterruptedException e) {
            log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
          }
          // 2.2设置计算结果到future
          log.info("{} set future result", Thread.currentThread().getName());
          future.complete("hello,jiaduo");
        });

    // 3.等待计算结果
    log.info("main thread wait future result");
    log.info(future.get());
    log.info("main thread got future result");

    future.whenComplete(
        (t, u) -> {
          if (null == u) {
            log.info(t);
          } else {
            log.info(u.getLocalizedMessage());
          }
        });
  }
}
