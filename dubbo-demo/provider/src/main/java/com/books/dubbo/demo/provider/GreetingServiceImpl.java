package com.books.dubbo.demo.provider;

import com.books.dubbo.demo.api.GreetingService;
import com.books.dubbo.demo.api.PoJo;
import com.books.dubbo.demo.api.Result;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.common.json.JSON;

@Slf4j
public class GreetingServiceImpl implements GreetingService {

  @Override
  public String sayHello(String name) {

    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
    }
    return name;
  }

  @Override
  public Result<String> testGeneric(PoJo poJo) {
    Result<String> result = new Result<>();
    result.setSuccess(true);
    try {
      result.setData(JSON.json(poJo));
    } catch (IOException e) {
      log.error("exception message is:{}", ExceptionUtils.getStackTrace(e));
    }

    return result;
  }
}
