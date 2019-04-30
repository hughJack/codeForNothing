/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.concurrency.example.threadLocal;

import java.util.concurrent.TimeUnit;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/*  
 * 描述 : 演示线程间的相互影响   简单
 * 一个线程改变,另外一个线程知道,那么实现了线程通信??
 * @param   
 * @return    
 * @author: sunshumeng @ 2019/4/22 23:23
 */
@Slf4j
public class ThreadLocalExample1 {

  volatile static Person p = new Person();

  public static void main(String[] args) {
    new Thread(()->{
      try {
        TimeUnit.SECONDS.sleep(2);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      log.info(p.getName());
    }).start();

    new Thread(()->{
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      p.setName("zhangsan");
    }).start();
  }

  @Data
  static class Person{
    String name = "lisi";
  }
}
