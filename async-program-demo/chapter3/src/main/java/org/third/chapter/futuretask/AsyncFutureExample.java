package org.third.chapter.futuretask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/** @author wujunshen */
@Slf4j
public class AsyncFutureExample {

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
    FutureTask<String> futureTask = new FutureTask<>(AsyncFutureExample::doSomethingA);

    // 2.开启异步单元执行任务A
    Thread thread = new Thread(futureTask, "threadA");
    thread.start();

    // 3.执行任务B
    String doSomethingB = doSomethingB();

    // 4.同步等待线程A运行结束
    String doSomethingA = futureTask.get();
    // 5.打印两个任务执行结果
    log.info("{} {}", doSomethingA, doSomethingB);
    log.info("cost {}ms", System.currentTimeMillis() - start);
  }
}
