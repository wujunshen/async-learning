package org.second.chapter.threadpool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {
  private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);

  private final AtomicInteger threadNumber = new AtomicInteger(1);
  private final ThreadGroup group;
  private final String namePrefix;
  private final boolean isDaemon;

  public NamedThreadFactory() {
    this("pool");
  }

  public NamedThreadFactory(String name) {
    this(name, true);
  }

  public NamedThreadFactory(String prefix, boolean daemon) {
    SecurityManager s = System.getSecurityManager();
    group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
    namePrefix = prefix + "-" + POOL_NUMBER.getAndIncrement() + "-thread-";
    isDaemon = daemon;
  }

  @Override
  public Thread newThread(Runnable runnable) {
    Thread t = new Thread(group, runnable, namePrefix + threadNumber.getAndIncrement(), 0);
    t.setDaemon(isDaemon);
    if (t.getPriority() != Thread.NORM_PRIORITY) {
      t.setPriority(Thread.NORM_PRIORITY);
    }
    return t;
  }
}
