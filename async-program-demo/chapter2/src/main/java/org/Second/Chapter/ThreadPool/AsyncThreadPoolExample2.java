package org.Second.Chapter.ThreadPool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/** Hello world! */
public class AsyncThreadPoolExample2 {

  // 0自定义线程池
  private static final int AVALIABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
  private static final ThreadPoolExecutor POOL_EXECUTOR =
      new ThreadPoolExecutor(
          AVALIABLE_PROCESSORS,
          AVALIABLE_PROCESSORS * 2,
          1,
          TimeUnit.MINUTES,
          new LinkedBlockingQueue<>(5),
          new ThreadPoolExecutor.CallerRunsPolicy());

  public static void doSomethingA() {

    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("--- doSomethingA---");
  }

  public static void doSomethingB() {
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("--- doSomethingB---");
  }

  public static void main(String[] args) throws InterruptedException, ExecutionException {

    long start = System.currentTimeMillis();

    // 1.开启异步单元执行任务A
    POOL_EXECUTOR.execute(
        () -> {
          try {
            doSomethingA();

          } catch (Exception e) {
            e.printStackTrace();
          }
        });

    // 2.执行任务B
    POOL_EXECUTOR.execute(
        () -> {
          try {
            doSomethingB();

          } catch (Exception e) {
            e.printStackTrace();
          }
        });

    // 3.同步等待线程A运行结束
    System.out.println(System.currentTimeMillis() - start);

    // 4.挂起当前线程
    Thread.currentThread().join();
  }
}
