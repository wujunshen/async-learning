package org.third.chapter.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

@Slf4j
public class TestTwoCompletableFuture {

  /**
   * 1.异步任务，返回future
   *
   * @param encodedCompanyId
   * @return
   */
  public static CompletableFuture<String> doSomethingOne(String encodedCompanyId) {
    // 1.1创建异步任务
    return CompletableFuture.supplyAsync(
        () -> {
          // 1.1.1休眠1s，模拟任务计算
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
          }

          // 1.1.2 解密，并返回结果
          return encodedCompanyId;
        });
  }

  /**
   * 2.开启异步任务，返回future
   *
   * @param companyId
   * @return
   */
  public static CompletableFuture<String> doSomethingTwo(String companyId) {
    return CompletableFuture.supplyAsync(
        () -> {
          // 2.1,休眠3s，模拟计算
          try {
            Thread.sleep(3000);
          } catch (InterruptedException e) {
            log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
          }

          // 2.2 查询公司信息，转换为str，并返回
          return companyId + ":alibaba";
        });
  }

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    // I，等doSomethingOne执行完毕后，接着执行doSomethingTwo
    CompletableFuture<String> result =
        doSomethingOne("123").thenCompose(TestTwoCompletableFuture::doSomethingTwo);
    log.info("{}", result.get());

    // II,等doSomethingOne和doSomethingTwo都完成后，使用它们的结果做一件事
    result =
        doSomethingOne("123").thenCombine(doSomethingTwo("456"), (one, two) -> one + " " + two);
    log.info("{}", result.get());
  }
}
