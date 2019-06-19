/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.juc.example.Future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FutureExample1 {

  // 需指定泛型
  static class MmCallable implements Callable<String>{

    @Override
    public String call() throws Exception {
      log.info("do something in Callable ");
      Thread.sleep(5000);
      return "Done";
    }
  }

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    ExecutorService executor = Executors.newCachedThreadPool();
    Future<String> future = executor.submit(new MmCallable());
    log.info("do sth in main");
    Thread.sleep(1000);
    String s = future.get();
    log.info("result:{}",s);
  }
}
