package org.chapter8.netty;

import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestModelAsyncRpc2 {
  private static final RpcClient RPC_CLIENT = new RpcClient();

  public static void main(String[] args) {
    // 1.发起远程调用异步，并注册回调，马上返回
    CompletableFuture<String> future1 = RPC_CLIENT.rpcAsyncCall("who are you");
    // 2.发起远程调用异步，并注册回调，马上返回
    CompletableFuture<String> future2 = RPC_CLIENT.rpcAsyncCall("who are you");

    // 3.等两个请求都返回结果时候，使用结果做些事情
    CompletableFuture<String> future = future1.thenCombine(future2, (u, v) -> u + v);

    // 4.等待最终结果
    future.whenComplete(
        (v, t) -> {
          if (t != null) {
            t.printStackTrace();
          } else {
            log.info("{}", v);
          }
        });
    log.info("---async rpc call over---");
  }
}
