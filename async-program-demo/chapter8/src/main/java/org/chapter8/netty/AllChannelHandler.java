package org.chapter8.netty;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AllChannelHandler {
  /** 使用JDK的ThreadPoolExecutor线程池创建一个线程池 */
  private static ThreadPoolExecutor executor =
      new ThreadPoolExecutor(
          8,
          8,
          1,
          TimeUnit.MINUTES,
          new SynchronousQueue<>(),
          new ThreadPoolExecutor.CallerRunsPolicy());

  /**
   * 异步执行任务
   *
   * @param runnable
   */
  public static void channelRead(Runnable runnable) {
    executor.execute(runnable);
  }

  /** 关闭线程池 */
  public static void shutdown() {
    executor.shutdown();
  }
}
