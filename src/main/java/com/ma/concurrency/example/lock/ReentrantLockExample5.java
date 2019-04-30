/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.concurrency.example.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import lombok.extern.slf4j.Slf4j;

@Slf4j
// 公平锁 ---- 非竞争锁,等的最长的先执行
// 等待时间长的线程获得执行的权利
public class ReentrantLockExample5 {

  public static void main(String[] args) {
    // 使用公平锁
    ReentrantLock reentrantLock = new ReentrantLock(true);
    // 使用比较复杂,很少使用
    new Thread(() -> {
      try {
        reentrantLock.lock();
        for (int i = 0; i < 1000; i++) {
          System.out.println(Thread.currentThread().getName() + "获得锁");
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        log.info("getted signal");// 4
        reentrantLock.unlock();
      }
    }).start();

    new Thread(() -> {
      try {
        reentrantLock.lock();
        for (int i = 0; i < 1000; i++) {
          System.out.println(Thread.currentThread().getName() + "获得锁");
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        log.info("getted signal");// 4
        reentrantLock.unlock();
      }
    }).start();

  }

  public void run (){

  }

}
