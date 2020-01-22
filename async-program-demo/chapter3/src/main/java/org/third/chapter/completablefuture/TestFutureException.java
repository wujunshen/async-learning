package org.third.chapter.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

/** @author wujunshen */
@Slf4j
public class TestFutureException {
  public static void one() throws InterruptedException, ExecutionException {
    /* 1.创建一个CompletableFuture对象 */
    CompletableFuture<String> future = new CompletableFuture<>();

    // 2.开启线程计算任务结果，并设置
    new Thread(
            () -> {
              // 2.1休眠3s，模拟任务计算
              try {
                Thread.sleep(3000);
              } catch (InterruptedException e) {
                log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
              }
              // 2.2设置计算结果到future
              log.info("{} set future result", Thread.currentThread().getName());
              future.completeExceptionally(new RuntimeException("error exception"));
            },
            "thread-1")
        .start();

    // 3.等待计算结果
    log.info("main thread wait future result");
    log.info(future.get());
    log.info("main thread got future result");
  }

  public static void two() throws InterruptedException, ExecutionException {
    /* 1.创建一个CompletableFuture对象 */
    CompletableFuture<String> future = new CompletableFuture<>();

    // 2.开启线程计算任务结果，并设置
    new Thread(
            () -> {
              // 2.1休眠3s，模拟任务计算
              try {
                Thread.sleep(3000);
              } catch (InterruptedException e) {
                log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
              }
              // 2.2设置计算结果到future
              log.info("{} set future result", Thread.currentThread().getName());
              future.completeExceptionally(new RuntimeException("error exception"));
            },
            "thread-1")
        .start();

    // 3.等待计算结果
    log.info("main thread wait future result");
    // 默认值
    log.info(future.exceptionally(t -> "default").get());
    log.info("main thread got future result");
  }

  public static void two1() throws InterruptedException, ExecutionException {
    // 1.创建一个CompletableFuture对象
    CompletableFuture<String> future = new CompletableFuture<>();

    // 2.开启线程计算任务结果，并设置
    new Thread(
            () -> {
              // 2.1休眠3s，模拟任务计算
              try {
                Thread.sleep(3000);
              } catch (InterruptedException e) {
                log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
              }
              // 2.2设置计算结果到future
              log.info("{} set future result", Thread.currentThread().getName());
              future.completeExceptionally(new RuntimeException("error exception"));
            },
            "thread-1")
        .start();

    // 3.等待计算结果
    log.info("main thread wait future result");
    // 默认值
    log.info(future.exceptionally(t -> "default").get());
    log.info("main thread got future result");
  }

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    two();
  }
}
