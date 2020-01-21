package org.third.chapter.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

// 又返回结果的回调
@Slf4j
public class TestCompletableFutureCallBack1 {
  /** 0.创建线程池 */
  private static final ThreadPoolExecutor BIZ_POOL_EXECUTOR =
      new ThreadPoolExecutor(8, 8, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(10));

  /**
   * I thenRun不能访问oneFuture的结果
   *
   * @throws InterruptedException
   * @throws ExecutionException
   */
  public static void thenRun() throws InterruptedException, ExecutionException {
    // 1.创建异步任务，并返回future
    CompletableFuture<String> oneFuture =
        CompletableFuture.supplyAsync(
            () -> {
              // 1.1休眠2s，模拟任务计算
              try {
                Thread.sleep(2000);
              } catch (InterruptedException e) {
                log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
              }
              // 1.2返回计算结果
              return "hello";
            });

    // 2.在future上施加事件，当future计算完成后回调该事件，并返回新future
    CompletableFuture<Void> twoFuture =
        oneFuture.thenRun(
            () -> {
              // 2.1.1当oneFuture任务计算完成后做一件事情
              try {
                Thread.sleep(1000);
              } catch (Exception e) {
                log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
              }
              log.info("{}", Thread.currentThread().getName());
              log.info("---after oneFuture over doSomething---");
            });

    // 3.同步等待twoFuture对应的任务完成，返回结果固定为null
    log.info("{}", twoFuture.get());
  }

  /**
   * II thenRun不能访问oneFuture的结果
   *
   * @throws InterruptedException
   * @throws ExecutionException
   */
  public static void thenAccept() throws InterruptedException, ExecutionException {
    // 1.创建异步任务，并返回future
    CompletableFuture<String> oneFuture =
        CompletableFuture.supplyAsync(
            () -> {
              // 1.1休眠2s，模拟任务计算
              try {
                Thread.sleep(2000);
              } catch (InterruptedException e) {
                log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
              }

              // 1.2返回计算结果
              return "hello";
            });

    // 2.在future上施加事件，当future计算完成后回调该事件，并返回新future
    CompletableFuture<Void> twoFuture =
        oneFuture.thenAccept(
            t -> {
              // 2.1.1对oneFuture返回的结果进行加工
              try {
                Thread.sleep(1000);
              } catch (Exception e) {
                log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
              }

              log.info("---after oneFuture over doSomething---" + t);
            });

    // 3.在future上施加事件，当future计算完成后回调该事件，并返回新future
    CompletableFuture<Void> threeFuture =
        oneFuture.thenAccept(
            t -> {
              // 2.1.1对oneFuture返回的结果进行加工
              try {
                Thread.sleep(1000);
              } catch (Exception e) {
                log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
              }

              log.info("---after oneFuture over doSomething---" + t);
            });

    // 3.同步等待twoFuture对应的任务完成，返回结果固定为null
    log.info("{}", twoFuture.get());
  }

  /**
   * III thenRun不能访问oneFuture的结果
   *
   * @throws InterruptedException
   * @throws ExecutionException
   */
  public static void thenRunAsync() throws InterruptedException, ExecutionException {
    // 1.创建异步任务，并返回future
    CompletableFuture<String> oneFuture =
        CompletableFuture.supplyAsync(
            () -> {
              // 1.1休眠2s，模拟任务计算
              try {
                Thread.sleep(2000);
              } catch (InterruptedException e) {
                log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
              }

              // 1.2返回计算结果
              return "hello";
            });
    // 2.在future上施加事件，当future计算完成后回调该事件，并返回新future
    CompletableFuture<Void> twoFuture =
        oneFuture.thenRunAsync(
            () -> {
              // 2.1.1当oneFuture任务计算完成后做一件事情
              try {
                Thread.sleep(1000);
              } catch (Exception e) {
                log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
              }

              log.info("---after oneFuture over doSomething---");
            });

    // 3.同步等待twoFuture对应的任务完成，返回结果固定为null
    log.info("{}", twoFuture.get());
  }

  /**
   * IV thenRun不能访问oneFuture的结果
   *
   * @throws InterruptedException
   * @throws ExecutionException
   */
  public static void thenAcceptAsync() throws InterruptedException, ExecutionException {
    // 1.创建异步任务，并返回future
    CompletableFuture<String> oneFuture =
        CompletableFuture.supplyAsync(
            () -> {
              // 1.1休眠2s，模拟任务计算
              try {
                Thread.sleep(2000);
              } catch (InterruptedException e) {
                log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
              }

              // 1.2返回计算结果
              return "hello";
            });
    // 2.在future上施加事件，当future计算完成后回调该事件，并返回新future
    CompletableFuture<Void> twoFuture =
        oneFuture.thenAcceptAsync(
            t -> {
              // 2.1.1对oneFuture返回的结果进行加工
              try {
                Thread.sleep(1000);
              } catch (Exception e) {
                log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
              }

              log.info("---after oneFuture over doSomething---{}", t);
            });

    // 3.同步等待twoFuture对应的任务完成，返回结果固定为null
    log.info("{}", twoFuture.get());
  }

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    // 1.thenRun test
    thenRun();

    // 2.thenAccept test
    thenAccept();

    // 3.thenRunAsync test
    thenRunAsync();

    // 4.thenAcceptAsync test
    thenAcceptAsync();
  }
}
