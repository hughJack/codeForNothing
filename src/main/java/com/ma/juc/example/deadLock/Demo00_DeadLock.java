/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.juc.example.deadLock;

import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/*
 * 描述 : 出现死锁的条件 ? ? ?
 * 死锁的示例 :
 * 死锁的处理 :
 *
 * 记录日志 : !!!! java项目中使用log记录日志的一些总结 !!!!
 */

@Slf4j
public class Demo00_DeadLock implements Runnable {

  public int flag = 1;
  // 静态对象, 是类的所有对象共享的
  private static Object o1 = new Object(), o2 = new Object();

  @Override
  public void run() {
    log.info("flag = {}" , flag);
    // 保证两个互相竞争的线程,都进入  锁定资源(o1 或者 o2)的状态
    if (flag == 1) {
      synchronized (o1) {
        try {
          TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        synchronized (o2) {
          log.info("flag = {}", flag);
        }
      }
    }
    if (flag == 0) {
      synchronized (o2) {
        try {
          TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        synchronized (o1) {
          log.info("flag = {}", flag);
        }
      }
    }
  }

  public static void main(String[] args) {
    Demo00_DeadLock lock1 = new Demo00_DeadLock();
    Demo00_DeadLock lock2 = new Demo00_DeadLock();
    lock1.flag = 1;
    lock2.flag = 0;
    new Thread(lock1).start();
    new Thread(lock2).start();
  }
}
