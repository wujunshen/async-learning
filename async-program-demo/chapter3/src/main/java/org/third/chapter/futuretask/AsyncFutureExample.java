package org.third.chapter.futuretask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

@Slf4j
public class AsyncFutureExample {
  public static String doSomethingA() {
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
    }
    log.info("--- doSomethingA---");

    return "TaskAResult";
  }

  public static String doSomethingB() {
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
    }
    log.info("--- doSomethingB---");
    return "TaskBResult";
  }

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    long start = System.currentTimeMillis();

    // 1.创建future任务
    FutureTask<String> futureTask =
        new FutureTask<>(
            () -> {
              String result = null;
              try {
                result = doSomethingA();

              } catch (Exception e) {
                log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
              }
              return result;
            });

    // 2.开启异步单元执行任务A
    Thread thread = new Thread(futureTask, "threadA");
    thread.start();

    // 3.执行任务B
    String taskbResult = doSomethingB();

    // 4.同步等待线程A运行结束
    String taskaResult = futureTask.get();
    // 5.打印两个任务执行结果
    log.info("{} {}", taskaResult, taskbResult);
    log.info("{}", System.currentTimeMillis() - start);
  }
}
