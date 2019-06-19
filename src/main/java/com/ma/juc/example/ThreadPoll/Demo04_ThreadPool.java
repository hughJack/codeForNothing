/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.juc.example.ThreadPoll;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/*
 * 描述 : 初步认识线程池
 *
 */
@Slf4j
public class Demo04_ThreadPool {

  public static void main(String[] args) throws InterruptedException {
    // 启动了5个线程,需要的时候启动
    // 所有的线程池,都实现了 ExecutorService 接口
    ExecutorService service = Executors.newFixedThreadPool(5);
    System.out.println(service);
    // 使用线程池,执行操作
    // 放置了6个任务,那么有线程会被重用
    for (int i = 0; i < 6; i++) {
      // service.submit  执行的是 runnale/callable 方法
      // service.execute 执行的是 runnale() 方法
      service.execute(() -> {
        try {
          TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());
      });
    }
    // 关闭线程池,查看线程池的状态
    System.out.println(service);
    service.shutdown();
    System.out.println(service.isShutdown());  // 线程池关闭,代表正在关闭线程池,
    System.out.println(service.isTerminated());// 代表所有的任务,都执行完了
    System.out.println(service);

    // 等待线程池执行完毕,查看线程池的状态
    TimeUnit.SECONDS.sleep(5); // 主线程,睡5s
    System.out.println(service.isShutdown());
    System.out.println(service.isTerminated());
    System.out.println(service);

  }

}
