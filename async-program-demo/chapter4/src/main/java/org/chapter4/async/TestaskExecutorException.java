package org.chapter4.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Slf4j
public class TestaskExecutorException {

  public static void main(String[] arg) throws Exception {
    // 1.创建容器上下文
    ClassPathXmlApplicationContext applicationContext =
        new ClassPathXmlApplicationContext("beans-annotation.xml");

    // 2. 获取AsyncExecutorExample实例并调用打印方法
    log.info("{} begin ", Thread.currentThread().getName());
    AsyncAnnotationExample asyncCommentExample =
        applicationContext.getBean(AsyncAnnotationExample.class);
    asyncCommentExample.testException();
    log.info("{} end ", Thread.currentThread().getName());
  }
}
