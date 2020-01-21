package org.third.chapter.completablefuture;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

@Slf4j
public class StreamTestFuture {
  public static String rpcCall(String ip, String param) {
    log.info("{} rpcCall:{}", ip, param);
    try {
      Thread.sleep(1000);
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

    // 2.发起广播调用
    long start = System.currentTimeMillis();
    List<String> result = new ArrayList<>();
    for (String ip : ipList) {
      result.add(rpcCall(ip, ip));
    }

    // 3.输出
    result.forEach(r -> log.info("{}", r));
    log.info("cost:{}ms", System.currentTimeMillis() - start);
  }
}
