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
import java.util.concurrent.locks.ReentrantLock;
import lombok.extern.slf4j.Slf4j;

@Slf4j
// 中断锁
public class ReentrantLockExample4 {

  public static void main(String[] args) {
    ReentrantLock reentrantLock = new ReentrantLock();

    Thread t1 = new Thread(() -> {
      try {
        // 执行不会被打断, 不会响应中断
        // 当线程已经被打扰了（isInterrupted()返回true）。则线程使用lock.lockInterruptibly()，直接会被要求处理InterruptedException。
        reentrantLock.lock();
        log.info("t1.start");
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        log.info("t1.end");// 1.
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        // 如果锁定的话,解锁
        if (reentrantLock.isHeldByCurrentThread()) {
          log.info("t1.unlock");// 4
          reentrantLock.unlock();
        }
      }
    });

    Thread t2 = new Thread(() -> {
      try {
        // reentrantLock.lock();  这个方法是一直等待获取锁
        // 线程执行的过程中,优先响应中断, 会响应 Interrupt 方法,  主线程调用interrupt()来打断!!!
        // ,  让线程不再等待, 因为他得不到锁
        reentrantLock.lockInterruptibly();
        log.info("t2.start");
        TimeUnit.SECONDS.sleep(5);
        log.info("t2.end");// 1.
      } catch (Exception e) {
        log.info("t2. interrupted.");
      } finally {
        // 如果锁定的话,解锁
        if (reentrantLock.isHeldByCurrentThread()) {
          log.info("t2.unlock");// 4
          reentrantLock.unlock();
        }
      }
    });
    t1.start();
    t2.start();
    t1.interrupt();
    t2.interrupt();
  }

}
