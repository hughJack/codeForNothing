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
public class ReentrantLockExample6 {

  public static void main(String[] args) {
    ReentrantLock reentrantLock = new ReentrantLock();
    // condition 维护了一个等待队列,用于线程之间的通信?
    // 使用比较复杂,很少使用
    Condition condition = reentrantLock.newCondition();

    new Thread(() -> {
      try {
        reentrantLock.lock();
        log.info("wait for signal");// 1.
        condition.await(); // 对应的操作是所得释放,从AQS队列中移除,进入condition等待队列
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      log.info("getted signal");// 4
      reentrantLock.unlock();
    }).start();

    new Thread(() -> {
      reentrantLock.lock();
      log.info("get lock");// 2.获取锁,加入到AQS的等待队列
      try {
        TimeUnit.SECONDS.sleep(1);
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      condition.signalAll();// 把唤醒的线程放入AQS队列中
      log.info("send signal");// 3.
      reentrantLock.unlock();
    }).start();

  }

}
