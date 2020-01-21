package org.chapter8.metaq;

import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

@Slf4j
public class Consumer {

  public static void main(String[] args) throws MQClientException {
    // 1. 创建消费实例 和 配置ns地址
    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("my-consumer-group");
    consumer.setNamesrvAddr("127.0.0.1:9876");

    // 2. 消费属性配置
    consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

    // 3. 订阅TopicTest topic下所有tag
    consumer.subscribe("TopicTest", "*");
    // 4. 注册回调
    consumer.registerMessageListener(
        (MessageListenerConcurrently)
            (msgs, context) -> {
              for (MessageExt msg : msgs) {
                String body = new String(msg.getBody(), StandardCharsets.UTF_8);
                log.info(
                    "{} Receive New Messages: {} {} \n",
                    Thread.currentThread().getName(),
                    msg.getMsgId(),
                    body);
              }
              return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });

    // 5.启动消费实例
    consumer.start();

    log.info("Consumer Started.\n");
  }
}
