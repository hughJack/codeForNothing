/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.juc.example.ThreadPoll;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import lombok.extern.slf4j.Slf4j;

/*
6种线程池 :
 * 描述 : 一般用于 定时执行的任务
 *
 */
@Slf4j
public class Demo09_SchedulePool {

  public static void main(String[] args) {
    ScheduledExecutorService service = Executors.newScheduledThreadPool(4);
    // // 以下任务最好单独执行,不要互相干扰
    // // 1.存放任务的时候  可以指定任务执行的延迟
    // service.schedule(() -> {
    //   System.out.println(Thread.currentThread().getName());
    // }, 2, TimeUnit.SECONDS);
    // // 2.优先保障任务的完成, 如果线程池满了的话 ,那么延迟执行下面的任务
    // service.scheduleAtFixedRate(() -> {
    //   System.out.println(Thread.currentThread().getName());
    // }, 1, 500, TimeUnit.MILLISECONDS);
    // 3. Timer定时器的使用
    Timer timer = new Timer();
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        log.info("shchedule running");
      }
    }, new Date(),5 * 1000);
  }

  private static void run() {
    System.out.println(Thread.currentThread().getName());
  }
}
