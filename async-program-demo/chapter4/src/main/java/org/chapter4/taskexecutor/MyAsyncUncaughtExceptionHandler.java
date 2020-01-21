package org.chapter4.taskexecutor;

import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;

@Slf4j
public class MyAsyncUncaughtExceptionHandler extends SimpleAsyncUncaughtExceptionHandler
    implements AsyncUncaughtExceptionHandler {

  @Override
  public void handleUncaughtException(Throwable ex, Method method, Object... params) {
    // handle exception
    log.info("{}", ex.getLocalizedMessage());
  }
}
