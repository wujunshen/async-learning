package org.chapter8.netty;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestModelAsyncRpc {
  private static final RpcClient RPC_CLIENT = new RpcClient();

  public static void main(String[] args) throws InterruptedException, ExecutionException {

    // 1.同步调用
    log.info("{}", RPC_CLIENT.rpcSyncCall("who are you"));

    // 2.发起远程调用异步，并注册回调，马上返回
    CompletableFuture<String> future = RPC_CLIENT.rpcAsyncCall("who are you");

    future.whenComplete(
        (v, t) -> {
          if (t != null) {
            t.printStackTrace();
          } else {
            log.info("{}", v);
          }
        });

    log.info("---async rpc call over");
  }
}
