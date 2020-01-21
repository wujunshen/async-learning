package org.chapter8.disruptor;

import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReplicationConsumer implements EventHandler<LongEvent> {
  @Override
  public void onEvent(LongEvent event, long sequence, boolean endOfBatch) {
    log.info("{} Replication Event: {}", Thread.currentThread().getName(), event.getValue());
  }
}
