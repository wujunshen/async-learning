package org.ChapterIV.TaskExecutor;

import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class AsyncExecutorExample {
  // 线程池执行器
  private TaskExecutor taskExecutor;

  public TaskExecutor getTaskExecutor() {
    return taskExecutor;
  }

  public void setTaskExecutor(TaskExecutor taskExecutor) {
    this.taskExecutor = taskExecutor;
  }

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

  private class MessagePrinterTask implements Runnable {

    private String message;

    public MessagePrinterTask(String message) {
      this.message = message;
    }

    public void run() {
      try {
        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName() + " " + message);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
