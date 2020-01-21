package com.books.dubbo.demo.provider;

import com.books.dubbo.demo.api.GrettingServiceAsync;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.common.utils.NamedThreadFactory;
import org.apache.dubbo.rpc.RpcContext;

@Slf4j
public class GrettingServiceAsyncImpl implements GrettingServiceAsync {
  /** 1.创建业务自定义线程池 */
  private final ThreadPoolExecutor bizThreadpool =
      new ThreadPoolExecutor(
          8,
          16,
          1,
          TimeUnit.MINUTES,
          new SynchronousQueue<>(),
          new NamedThreadFactory("biz-thread-pool"),
          new ThreadPoolExecutor.CallerRunsPolicy());

  /**
   * 2.创建服务处理接口，返回值为CompletableFuture
   *
   * @param name
   * @return
   */
  @Override
  public CompletableFuture<String> sayHello(String name) {
    // 2.1 为supplyAsync提供自定义线程池bizThreadpool，避免使用JDK公用线程池(ForkJoinPool.commonPool())
    // 使用CompletableFuture.supplyAsync让服务处理异步化进行处理
    // 保存当前线程的上下文
    RpcContext context = RpcContext.getContext();

    return CompletableFuture.supplyAsync(
        () -> {
          try {
            Thread.sleep(2000);
          } catch (InterruptedException e) {
            log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
          }
          log.info("async return ");
          return "Hello " + name + " " + context.getAttachment("company");
        },
        bizThreadpool);
  }
}
