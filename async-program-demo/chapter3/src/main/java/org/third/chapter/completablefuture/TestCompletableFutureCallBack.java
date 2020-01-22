package org.third.chapter.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

/** @author wujunshen */
@Slf4j
public class TestCompletableFutureCallBack {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    // 1.创建异步任务，并返回future
    CompletableFuture<String> oneFuture =
        CompletableFuture.supplyAsync(
            () -> {
              // 1.1休眠2s，模拟任务计算
              try {
                Thread.sleep(2000);
              } catch (InterruptedException e) {
                log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
              }
              // 1.2返回计算结果
              return "hello";
            });

    // 2.在future上施加事件，当future计算完成后回调该事件，并返回新future
    // 2.1对步骤1计算结果基础上进行计算，这里t为步骤1返回的hello
    CompletableFuture<String> twoFuture =
        oneFuture.thenApply(
            t -> {
              // 2.1.1对oneFuture返回的结果进行加工
              try {
                Thread.sleep(1000);
              } catch (Exception e) {
                log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
              }
              // 2.1.2返回加工后结果
              return t + " jiduo";
            });

    // 3.同步等待twoFuture对应的任务完成，并获取结果
    log.info(twoFuture.get());
  }
}
