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
 * 描述 : 演示 线程局部变量,实现线程封闭de一种方式
 * 使用不当,会导致内存泄漏!!!
 * @param   
 * @return    
 * @author: sunshumeng @ 2019/4/22 23:23
 */
@Slf4j
public class ThreadLocalExample2 {
  // 线程自产自销,其他线程获取不到
  //
  // ThreadLocal: 使用空间换取时间
  // 上锁的: 时间换取空间
  static ThreadLocal<Person> local = new ThreadLocal<>();

  public static void main(String[] args) {
    new Thread(()->{
      try {
        TimeUnit.SECONDS.sleep(2);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println(local.get());
      // log.info(local.get().getName());
    }).start();

    new Thread(()->{
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      local.set(new Person());
    }).start();
  }

  @Data
  static class Person{
    String name = "lisi";
  }
}
