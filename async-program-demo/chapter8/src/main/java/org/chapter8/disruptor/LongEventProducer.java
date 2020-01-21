package org.chapter8.disruptor;

import com.lmax.disruptor.RingBuffer;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LongEventProducer {
  private final RingBuffer<LongEvent> ringBuffer;

  public void onData(long bb) {
    // 8.1 第一阶段，获取序列号
    long sequence = ringBuffer.next();
    try {
      // 8.2获取序列号对应的实体元素
      LongEvent event = ringBuffer.get(sequence);
      // 8.3修改元素的值
      event.setValue(bb);
    } finally {
      // 8.4发布元素
      ringBuffer.publish(sequence);
    }
  }
}
