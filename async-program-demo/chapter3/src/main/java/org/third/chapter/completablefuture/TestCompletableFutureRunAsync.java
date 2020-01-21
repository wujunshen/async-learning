package org.third.chapter.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

@Slf4j
public class TestCompletableFutureRunAsync {
  /** 0.创建线程池 */
  private static final ThreadPoolExecutor BIZ_POOL_EXECUTOR =
      new ThreadPoolExecutor(8, 8, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(10));

  /**
   * 没有返回值的异步执行
   *
   * @throws InterruptedException
   * @throws ExecutionException
   */
  public static void runAsync() throws InterruptedException, ExecutionException {
    // 1.1创建异步任务，并返回future
    CompletableFuture<Void> future =
        CompletableFuture.runAsync(
            () -> {
              // 1.1.1休眠2s模拟任务计算
              try {
                Thread.sleep(2000);
              } catch (InterruptedException e) {
                log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
              }
              log.info("over");
            });

    // 1.2 同步等待异步任务执行结束
    log.info("{}", future.get());
  }

  /**
   * 2. 有返回值的异步执行
   *
   * @throws InterruptedException
   * @throws ExecutionException
   */
  public static void supplyAsync() throws InterruptedException, ExecutionException {
    // 2.1创建异步任务，并返回future
    CompletableFuture<String> future =
        CompletableFuture.supplyAsync(
            () -> {
              // 2.1.1休眠2s模拟任务计算
              try {
                Thread.sleep(2000);
              } catch (InterruptedException e) {
                log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
              }
              // 2.1.2 返回异步计算结果
              return "hello,jiaduo";
            });

    // 2.2 同步等待异步任务执行结束
    log.info("{}", future.get());
  }

  /**
   * 3. 没有返回值的异步执行，异步任务有业务自己线程池执行
   *
   * @throws InterruptedException
   * @throws ExecutionException
   */
  public static void runAsyncWithBizExecutor() throws InterruptedException, ExecutionException {
    // 1.1创建异步任务，并返回future
    CompletableFuture<Void> future =
        CompletableFuture.runAsync(
            () -> {
              // 1.1.1休眠2s模拟任务计算
              try {
                Thread.sleep(2000);
              } catch (InterruptedException e) {
                log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
              }
              log.info("over");
            },
            BIZ_POOL_EXECUTOR);

    // 1.2 同步等待异步任务执行结束
    log.info("{}", future.get());
  }

  /**
   * 4. 有返回值的异步执行
   *
   * @throws InterruptedException
   * @throws ExecutionException
   */
  public static void supplyAsyncWithBizExecutor() throws InterruptedException, ExecutionException {
    // 2.1创建异步任务，并返回future
    CompletableFuture<String> future =
        CompletableFuture.supplyAsync(
            () -> {
              // 2.1.1休眠2s模拟任务计算
              try {
                Thread.sleep(2000);
              } catch (InterruptedException e) {
                log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
              }
              // 2.1.2 返回异步计算结果
              return "hello,jiaduo";
            },
            BIZ_POOL_EXECUTOR);

    // 2.2 同步等待异步任务执行结束
    log.info("{}", future.get());
  }

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    // 1 runAsync
    runAsync();

    // 2. supplyAsync
    supplyAsync();

    // 3.runAsyncWithBizExecutor
    runAsyncWithBizExecutor();

    // 4. supplyAsyncWithBizExecutor
    supplyAsyncWithBizExecutor();
  }
}
