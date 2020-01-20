package org.Chapter8.disruptor;

import com.lmax.disruptor.EventHandler;

public class ReplicationConsumer implements EventHandler<LongEvent> {
  public void onEvent(LongEvent event, long sequence, boolean endOfBatch) {
    System.out.println(Thread.currentThread().getName() + "Replication Event: " + event.get());
  }
}
