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
 * 6种线程池 :
 * 描述 : 工作窃取的线程池
 *
 * 自动去拿任务执行
 *
 * #####!!!每个线程维护自己的队列!!!#####
 *
 * 线程会主动的去别的线程的队列里面那任务执行!!
 *
 */
@Slf4j
public class Demo10_WorkStealingPool {

  public static void main(String[] args) throws InterruptedException {
    ExecutorService service = Executors.newWorkStealingPool(4);
    int cores = Runtime.getRuntime().availableProcessors();
    System.out.println(cores);
    // 线程池里的所有线程都是守护线程
    service.execute(new R(1000));
    service.execute(new R(2000));
    service.execute(new R(3000));
    service.execute(new R(4000));
    service.execute(new R(5000));

    // 该线程池 产生的是精灵线程(守护线程,后台线程)
    // 主线程不阻塞, 看不到线程的输出结果
    TimeUnit.SECONDS.sleep(6);
  }

  // 初始化的时候指定睡眠的时间,
  // 该线程
  static  class R implements Runnable {
    int sleepTime ;

    public R(int sleepTime) {
      this.sleepTime = sleepTime;
    }

    @Override
    public void run() {
      try {
        TimeUnit.MILLISECONDS.sleep(sleepTime);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println(Thread.currentThread().getName());
    }

  }

}
