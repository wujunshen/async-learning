package org.chapter8.netty;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Sharable
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
  @Override
  public void channelActive(ChannelHandlerContext ctx) {
    log.info("--- client already connected----");
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) {
    // 提交给线程池异步执行， 释放IO线程
    AllChannelHandler.channelRead(
        () -> {
          // 1.根据请求id，获取对应future
          CompletableFuture future = FutureMapUtil.remove(((String) msg).split(":")[1]);
          // 2.如果存在，则设置future结果
          if (null != future) {
            future.complete(((String) msg).split(":")[0]);
          }
        });
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) {}

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    cause.printStackTrace();
    ctx.close();
  }
}
