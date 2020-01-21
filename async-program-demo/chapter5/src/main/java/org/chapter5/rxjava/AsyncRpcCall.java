package org.chapter5.rxjava;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

@Slf4j
public class AsyncRpcCall {
  public static String rpcCall(String ip, String param) {
    log.info("{} rpcCall:{}", ip, param);
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
    }

    return param;
  }

  public static void main(String[] args) throws InterruptedException {
    CompletableFuture<String> future =
        CompletableFuture.supplyAsync(
            () -> {
              try {
                Thread.sleep(3000);
              } catch (InterruptedException e) {
                log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
              }
              return "test";
            });

    Flowable.fromCallable(future::get)
        .subscribeOn(Schedulers.io())
        .subscribe(name -> log.info("{}", name));
    log.info("{}", 111);

    Thread.sleep(3000);
  }
}
