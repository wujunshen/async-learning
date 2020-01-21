package com.books.dubbo.demo.api;

import java.io.Serializable;
import lombok.Data;

@Data
public class Result<T> implements Serializable {
  private static final long serialVersionUID = 1L;

  private T data;
  private boolean success;
  private String msg;
}
