package org.second.chapter.thread;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

/** Hello world! */
@Slf4j
public class AsyncThreadExample2 {
  public static void doSomethingA() {
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
    }
    log.info("{}", "--- doSomethingA---");
  }

  public static void doSomethingB() {
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
    }
    log.info("{}", "--- doSomethingB---");
  }

  public static void main(String[] args) throws InterruptedException {
    long start = System.currentTimeMillis();

    // 1.开启异步单元执行任务A
    Thread thread =
        new Thread("threadA") {
          @Override
          public void run() {
            try {
              doSomethingA();
            } catch (Exception e) {
              log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
            }
          }
        };

    //    thread.setDaemon(true);
    thread.start();

    // 2.执行任务B
    doSomethingB();

    // 3.同步等待线程A运行结束
    thread.join();
    log.info("consume {}ms", System.currentTimeMillis() - start);
  }
}
