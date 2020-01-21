package org.third.chapter.completablefuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

@Slf4j
public class StreamTestFuture2 {

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

    // 2.并发调用
    long start = System.currentTimeMillis();
    List<CompletableFuture<String>> futureList =
        ipList.stream()
            .map(ip -> CompletableFuture.supplyAsync(() -> rpcCall(ip, ip)))
            .collect(Collectors.toList());

    List<String> resultList =
        futureList.stream().map(CompletableFuture::join).collect(Collectors.toList());

    // 3.输出
    resultList.forEach(r -> log.info("{}", r));

    log.info("cost:{}ms", System.currentTimeMillis() - start);
  }
}
