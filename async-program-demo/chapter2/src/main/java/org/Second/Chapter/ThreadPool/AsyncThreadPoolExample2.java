package org.second.chapter.threadpool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * Hello world!
 *
 * @author wujunshen
 */
@Slf4j
public class AsyncThreadPoolExample2 {
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

  @SneakyThrows
  public static void doSomethingA() {
    Thread.sleep(2000);

    log.info("doSomethingA");
  }

  @SneakyThrows
  public static void doSomethingB() {
    Thread.sleep(2000);

    log.info("doSomethingB");
  }

  public static void main(String[] args) throws InterruptedException {
    long start = System.currentTimeMillis();

    // 1.开启异步单元执行任务A
    POOL_EXECUTOR.execute(AsyncThreadPoolExample2::doSomethingA);

    // 2.执行任务B
    POOL_EXECUTOR.execute(AsyncThreadPoolExample2::doSomethingB);

    // 3.同步等待线程A运行结束
    log.info("consume {}ms", System.currentTimeMillis() - start);

    // 4.挂起当前线程
    Thread.currentThread().join();
  }
}
