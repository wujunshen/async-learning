package org.chapter5.rxjava;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

@Slf4j
public class AsyncRpcCall3 {

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
        .parallel()
        .runOn(Schedulers.io())
        .map(v -> rpcCall(v, v))
        .sequential()
        // 2.5
        .blockingSubscribe(ip -> log.info("{}", ip));

    // 3.打印耗时
    log.info("cost:{}ms", System.currentTimeMillis() - start);
  }
}
