package org.chapter8.netty;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class FutureMapUtil {
  /** <请求id，对应的future> */
  private static final ConcurrentHashMap<String, CompletableFuture<Void>> futureMap =
      new ConcurrentHashMap<>();

  public static void put(String id, CompletableFuture<Void> future) {
    futureMap.put(id, future);
  }

  public static CompletableFuture<Void> remove(String id) {
    return futureMap.remove(id);
  }
}
