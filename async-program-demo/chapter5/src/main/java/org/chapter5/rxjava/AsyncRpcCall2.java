package org.chapter5.rxjava;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

@Slf4j
public class AsyncRpcCall2 {
  /** 0自定义线程池 */
  private static final ThreadPoolExecutor POOL_EXECUTOR =
      new ThreadPoolExecutor(
          10,
          10,
          1,
          TimeUnit.MINUTES,
          new LinkedBlockingQueue<>(5),
          new ThreadPoolExecutor.CallerRunsPolicy());

  public static String rpcCall(String ip, String param) {
    log.info("{} rpcCall:{}", ip, param);
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
    }

    return param;
  }

  public static void main(String[] args) {
    // 1.生成ip列表
    List<String> ipList = new ArrayList<>();
    for (int i = 1; i <= 10; ++i) {
      ipList.add("192.168.0." + i);
    }

    // 2.并发调用
    long start = System.currentTimeMillis();

    Flowable.fromArray(ipList.toArray(new String[0]))
        .flatMap(
            // 2.1
            ip ->
                Flowable.just(ip) // 2.2
                    .subscribeOn(Schedulers.from(POOL_EXECUTOR)) // 2.3
                    .map(v -> rpcCall(v, v))) // 2.4
        // 2.5
        .blockingSubscribe(ip -> log.info("{}", ip));

    // 3.打印耗时
    log.info("cost:{}ms", System.currentTimeMillis() - start);

    // 4.关闭线程池
    POOL_EXECUTOR.shutdown();
  }
}
