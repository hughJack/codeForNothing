/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.juc.example.ThreadPoll;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class Demo05_Future {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    // 放置的是一个Callable 匿名类
    // 包装了一个任务
    FutureTask<Integer> task = new FutureTask<>(() -> {
      TimeUnit.MILLISECONDS.sleep(500);
      return 1000;
    });
    //
    new Thread(task).start();
    System.out.println(task.get());// 阻塞式

    //**************************************
    ExecutorService service = Executors.newFixedThreadPool(5);
    Future<Integer> future = service.submit(() -> {
      TimeUnit.MILLISECONDS.sleep(500);
      return 1;
    });
    System.out.println(future.isDone());
    System.out.println(future.get());
    System.out.println(future.isDone());
  }
}
