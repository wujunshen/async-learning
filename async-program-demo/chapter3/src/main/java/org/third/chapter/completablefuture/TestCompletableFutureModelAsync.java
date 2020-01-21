package org.third.chapter.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

@Slf4j
public class TestCompletableFutureModelAsync {
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

  public static CompletableFuture<String> runAsync(Runnable runnable) {
    CompletableFuture<String> future = new CompletableFuture<>();

    // 2.开启线程计算任务结果，并设置
    POOL_EXECUTOR.execute(
        () -> {
          // 2.1休眠3s，模拟任务计算
          try {
            runnable.run();
            future.complete(null);
          } catch (Exception e) {
            future.completeExceptionally(e);
          }
        });

    return future;
  }

  public static void main(String[] args) {
    // 1.创建一个CompletableFuture对象
    CompletableFuture<String> future =
        runAsync(
            () -> {
              try {
                Thread.sleep(3000);
              } catch (InterruptedException e) {
                log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
              }

              log.info("hello");
            });

    future.whenComplete(
        (t, u) -> {
          if (null == u) {
            log.info("{}", t);
          } else {
            log.info("{}", u.getLocalizedMessage());
          }
        });
  }
}
