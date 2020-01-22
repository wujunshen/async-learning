package org.second.chapter.thread;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * Hello world!
 *
 * @author wujunshen
 */
@Slf4j
public class AsyncThreadExample2 {
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

    /* 1.开启异步单元执行任务A */
    Thread thread =
        new Thread("threadA") {
          @Override
          public void run() {
            doSomethingA();
          }
        };

    thread.setDaemon(true);
    thread.start();

    // 2.执行任务B
    doSomethingB();

    // 3.同步等待线程A运行结束
    thread.join();
    log.info("consume {}ms", System.currentTimeMillis() - start);
  }
}
