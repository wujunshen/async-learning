package org.chapter5.rxjava;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

@Slf4j
public class AsyncRpcCall5 {
  public static String rpcCall(String ip, String param) {
    log.info("{} {} rpcCall:{}", Thread.currentThread().getName(), ip, param);
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
    }

    return param;
  }

  public static void main(String[] args) throws InterruptedException {
    // 1.
    long start = System.currentTimeMillis();
    Flowable.fromCallable(
            // 1.1
            () -> {
              Thread.sleep(1000); // 1.2模拟耗时的操作
              return "Done" + Thread.currentThread().getName();
            })
        // 1.3
        .subscribeOn(Schedulers.io())
        // 1.4
        .observeOn(Schedulers.single())
        .subscribe(
            t -> log.info("{} {}", Thread.currentThread().getName(), t),
            // 1.5
            Throwable::printStackTrace);

    // 2.
    log.info("cost:{}ms", System.currentTimeMillis() - start);

    // 3. 等待流结束
    Thread.sleep(2000000);
  }
}
