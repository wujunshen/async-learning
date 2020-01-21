package org.chapter4.taskexecutor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Slf4j
public class TestaskExecutor {

  public static void main(String[] arg) {
    // 1.创建容器上下文
    ClassPathXmlApplicationContext applicationContext =
        new ClassPathXmlApplicationContext("beans.xml");

    // 2. 获取AsyncExecutorExample实例并调用打印方法
    log.info("{} begin ", Thread.currentThread().getName());
    AsyncExecutorExample asyncExecutorExample =
        applicationContext.getBean(AsyncExecutorExample.class);
    asyncExecutorExample.printMessages();
    log.info("{} end ", Thread.currentThread().getName());

    // 3.关闭执行器，释放线程
    asyncExecutorExample.shutdown();
  }
}
