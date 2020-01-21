package org.third.chapter.completablefuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

@Slf4j
public class TestMoreCompletableFuture {

  // 1.异步任务，返回future
  public static CompletableFuture<String> doSomethingOne(String id) {
    // 1.1创建异步任务
    return CompletableFuture.supplyAsync(
        () -> {
          // 1.1.1休眠1s，模拟任务计算
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
          }
          log.info("compute {}", id);

          return id;
        });
  }

  // 2.开启异步任务，返回future
  public static CompletableFuture<String> doSomethingTwo(String id) {
    return CompletableFuture.supplyAsync(
        () -> {
          // 2.1,休眠3s，模拟计算
          try {
            Thread.sleep(5000);
          } catch (InterruptedException e) {
            log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
          }
          log.info("compute {}", id);

          return id;
        });
  }

  public static void allOf() throws InterruptedException, ExecutionException {
    // 1.创建future列表
    List<CompletableFuture<String>> futureList = new ArrayList<>();
    futureList.add(doSomethingOne("1"));
    futureList.add(doSomethingOne("2"));
    futureList.add(doSomethingOne("3"));
    futureList.add(doSomethingOne("4"));

    // 2.转换多个future为一个
    CompletableFuture<Void> result =
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0]));

    // 3.等待所有future都完成
    log.info("{}", result.get());

    // 4.等所有future执行完毕后，获取所有future的计算结果
    CompletableFuture<List<String>> finallyResult =
        result.thenApply(
            t -> futureList.stream().map(CompletableFuture::join).collect(Collectors.toList()));

    // 5.打印所有future的结果
    for (String str : finallyResult.get()) {
      log.info("{}", str);
    }
  }

  public static void anyOf() throws InterruptedException, ExecutionException {
    // 1.创建future列表
    List<CompletableFuture<String>> futureList = new ArrayList<>();
    futureList.add(doSomethingOne("1"));
    futureList.add(doSomethingOne("2"));
    futureList.add(doSomethingTwo("3"));

    // 2.转换多个future为一个
    CompletableFuture<Object> result =
        CompletableFuture.anyOf(futureList.toArray(new CompletableFuture[0]));

    // 3.等待某一个future完成
    log.info("{}", result.get());
  }

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    // 1.allOf
    allOf();

    // 2.anyOf
    anyOf();

    Thread.sleep(10000);
  }
}
