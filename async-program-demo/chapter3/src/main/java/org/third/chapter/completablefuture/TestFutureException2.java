package org.third.chapter.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import lombok.extern.slf4j.Slf4j;

/** @author wujunshen */
@Slf4j
public class TestFutureException2 {
  public static void main(String[] args) throws InterruptedException, ExecutionException {
    // 1.创建一个CompletableFuture对象
    CompletableFuture<String> future = new CompletableFuture<>();

    // 2.开启线程计算任务结果，并设置
    new Thread(
            () -> {
              // 2.1休眠3s，模拟任务计算
              try {
                // 2.1.1 抛出异常
                throw new RuntimeException("exception test");
                // 2.1.2设置正常结果
              } catch (Exception e) {
                // 2.1.3 设置异常结果
                future.completeExceptionally(e);
              }
              // 2.2设置计算结果到future
              log.info("{} set future result", Thread.currentThread().getName());
            },
            "thread-1")
        .start();

    // 3.等待计算结果
    // 默认值
    log.info(future.exceptionally(t -> "default").get());
  }
}
