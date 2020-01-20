package org.second.chapter.thread;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

@Slf4j
public class SyncExample {
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

  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    // 1.执行任务A
    doSomethingA();

    // 2.执行任务B
    doSomethingB();

    log.info("consume {}ms", System.currentTimeMillis() - start);
  }
}
