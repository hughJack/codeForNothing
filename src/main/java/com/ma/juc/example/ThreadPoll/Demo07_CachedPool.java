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
 * 描述 : 弹性的缓存池,线程空闲时间超过60s会被销毁
 *
 */
@Slf4j
public class Demo07_CachedPool {

  public static void main(String[] args) throws InterruptedException {
    ExecutorService service = Executors.newCachedThreadPool();
    System.out.println(service);

    for (int i = 0; i < 5; i++) {
        service.execute(()->{
          try {
            TimeUnit.MILLISECONDS.sleep(500);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          System.out.println(Thread.currentThread().getName());
        });
    }

    System.out.println(service);
    TimeUnit.SECONDS.sleep(65);// 线程池被清空
    System.out.println(service);

  }

}
