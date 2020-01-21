package org.chapter8.netty;

import io.reactivex.Flowable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestModelAsyncRpc4Reactive {
  /** 1.创建rpc客户端 */
  private static final RpcClient RPC_CLIENT = new RpcClient();

  public static void main(String[] args) {
    // 2.发起远程调用异步，并注册回调，马上返回
    Flowable<String> result = RPC_CLIENT.rpcAsyncCallFlowable4("who are you");

    // 3.订阅流对象
    result.subscribe(
        /* onNext */
        r -> log.info("{}:{}", Thread.currentThread().getName(), r),
        /* onError */
        error ->
            log.info("{}error:{}", Thread.currentThread().getName(), error.getLocalizedMessage()));

    log.info("---async rpc call over");
  }
}
