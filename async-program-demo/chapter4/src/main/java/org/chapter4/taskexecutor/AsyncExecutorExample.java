package org.chapter4.taskexecutor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Slf4j
@Data
public class AsyncExecutorExample {
  /** 线程池执行器 */
  private TaskExecutor taskExecutor;

  public void shutdown() {
    if (taskExecutor instanceof ThreadPoolTaskExecutor) {
      ((ThreadPoolTaskExecutor) taskExecutor).shutdown();
    }
  }

  public void printMessages() {
    for (int i = 0; i < 6; i++) {
      taskExecutor.execute(new MessagePrinterTask("Message" + i));
    }
  }

  @AllArgsConstructor
  private static class MessagePrinterTask implements Runnable {
    private String message;

    @Override
    public void run() {
      try {
        Thread.sleep(1000);
        log.info("{} {}", Thread.currentThread().getName(), message);
      } catch (Exception e) {
        log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
      }
    }
  }
}
