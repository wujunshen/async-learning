package com.books.dubbo.demo.provider;

import com.books.dubbo.demo.api.GreetingService;
import com.books.dubbo.demo.api.PoJo;
import com.books.dubbo.demo.api.Result;
import java.io.IOException;
import org.apache.dubbo.common.json.JSON;

public class GreetingServiceImpl implements GreetingService {

  @Override
  public String sayHello(String name) {

    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return name;
  }

  @Override
  public Result<String> testGeneric(PoJo poJo) {
    Result<String> result = new Result<String>();
    result.setSucess(true);
    try {
      result.setData(JSON.json(poJo));
    } catch (IOException e) {
      e.printStackTrace();
    }

    return result;
  }
}
