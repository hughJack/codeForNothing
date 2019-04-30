/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.concurrency.example.Future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FutureTaskExample2 {

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>() {
      @Override
      public String call() throws Exception {
        log.info("do something in Callable ");
        Thread.sleep(5000);
        return "Done";
      }
    });

    new Thread(futureTask).start();
    log.info("in main");
    Thread.sleep(1000);
    String result = futureTask.get();
    log.info("result:{}",result);
  }
}
