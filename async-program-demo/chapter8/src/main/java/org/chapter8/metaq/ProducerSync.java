package org.chapter8.metaq;

import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

@Slf4j
public class ProducerSync {
  public static void main(String[] args) throws MQClientException {
    // 1. 创建生产者实例
    DefaultMQProducer producer = new DefaultMQProducer("my-producer-group");
    // 2. 设置nameserver地址，多个可以使用;分隔
    producer.setNamesrvAddr("127.0.0.1:9876");
    // 3. 启动生产者
    producer.start();

    // 4. 发送消息
    for (int i = 0; i < 3; i++) {
      try {

        // 4.1 创建消息体,topic为TopicTest，tag为TagA
        Message msg =
            new Message(
                /* Topic */
                "TopicTest",
                /* Tag */
                "TagA",
                ("Hello RocketMQ " + i)
                    /* Message body */
                    .getBytes(StandardCharsets.UTF_8));

        // 4.2 发送消息
        SendResult sendResult = producer.send(msg);
        // 4.3
        log.info("{}\n", sendResult);
      } catch (Exception e) {
        log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
      }
    }

    // 5. 关闭
    producer.shutdown();
  }
}
