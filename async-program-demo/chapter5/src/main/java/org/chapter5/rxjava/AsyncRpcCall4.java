package org.chapter5.rxjava;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

@Slf4j
public class AsyncRpcCall4 {
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
    // 1.生成ip列表
    List<String> ipList = new ArrayList<>();
    for (int i = 1; i <= 10; ++i) {
      ipList.add("192.168.0." + i);
    }

    // 2.顺序调用
    Flowable.fromArray(ipList.toArray(new String[0]))
        // 2.1切换到IO线程执行
        .observeOn(Schedulers.io())
        // 2.2映射结果
        .map(v -> rpcCall(v, v))
        // 2.3订阅
        .subscribe(ip -> log.info("{}", ip));

    // 3.
    log.info("main execute over and wait");
    Thread.currentThread().join(); // 挂起main函数所在线程
  }
}
