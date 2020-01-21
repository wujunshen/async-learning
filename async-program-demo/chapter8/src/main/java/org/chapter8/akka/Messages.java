package org.chapter8.akka;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class Messages {
  /** 加法域对象 */
  @Getter
  @AllArgsConstructor
  public static class Sum implements Serializable {
    private int first;
    private int second;
  }

  /** 存放计算结果的域对象 */
  @Getter
  @AllArgsConstructor
  public static class Result implements Serializable {
    private int result;
  }
}
