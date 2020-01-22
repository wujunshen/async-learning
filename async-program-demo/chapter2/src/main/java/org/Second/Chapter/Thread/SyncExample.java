package org.second.chapter.thread;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/** @author wujunshen */
@Slf4j
public class SyncExample {
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

  public static void main(String[] args) {
    long start = System.currentTimeMillis();

    // 1.执行任务A
    doSomethingA();

    // 2.执行任务B
    doSomethingB();

    log.info("consume {}ms", System.currentTimeMillis() - start);
  }
}
