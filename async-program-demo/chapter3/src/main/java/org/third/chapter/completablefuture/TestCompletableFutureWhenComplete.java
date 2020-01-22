package org.third.chapter.completablefuture;

import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

/** @author wujunshen */
@Slf4j
public class TestCompletableFutureWhenComplete {
  public static void main(String[] args) throws InterruptedException {
    // 1.创建一个CompletableFuture对象
    CompletableFuture<String> future =
        CompletableFuture.supplyAsync(
            () -> {
              // 1.1模拟异步任务执行
              try {
                Thread.sleep(1000);
              } catch (InterruptedException e) {
                log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
              }
              // 1.2返回计算结果
              return "hello,jiaduo";
            });

    // 2.添加回调函数
    future.whenComplete(
        (t, u) -> {
          // 2.1如果没有异常，打印异步任务结果
          if (null == u) {
            log.info("1");
          } else {
            log.info(u.getLocalizedMessage());
          }
        });

    // 2.添加回调函数
    future.whenComplete(
        (t, u) -> {
          // 2.1如果没有异常，打印异步任务结果
          if (null == u) {
            log.info("2");
          } else {
            log.info(u.getLocalizedMessage());
          }
        });

    // 3.挂起当前线程，等待异步任务执行完毕
    Thread.currentThread().join();
  }
}
