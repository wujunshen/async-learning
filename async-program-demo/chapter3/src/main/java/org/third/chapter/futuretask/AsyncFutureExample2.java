package org.third.chapter.futuretask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
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
public class AsyncFutureExample2 {
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
  public static String doSomethingA() {
    Thread.sleep(2000);

    log.info("doSomethingA");

    return "TaskAResult";
  }

  @SneakyThrows
  public static String doSomethingB() {
    Thread.sleep(2000);

    log.info("doSomethingB");

    return "TaskBResult";
  }

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    long start = System.currentTimeMillis();

    // 1.创建future任务
    FutureTask<String> futureTask = new FutureTask<>(AsyncFutureExample2::doSomethingA);

    // 2.开启异步单元执行任务A
    POOL_EXECUTOR.execute(futureTask);

    // 3.执行任务B
    String doSomethingB = doSomethingB();

    // 4.同步等待线程A运行结束
    String doSomethingA = futureTask.get();
    // 5.打印两个任务执行结果
    log.info("{} {}", doSomethingA, doSomethingB);
    log.info("cost {}ms", System.currentTimeMillis() - start);
  }
}
