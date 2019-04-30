/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.concurrency.example.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.StampedLock;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StampLockExample4 {

  public static int threadTotal = 200;
  public static int clientTotal = 5000;
  public static int count = 0;
//  乐观锁,适合读线程特别多的时候使用
//  如果只有少量的竞争者,那么synchronized,不会造成死锁,其他锁如果不配对使用unlock()会造成死锁;
//  如果线程的增长的数量是可以预估的? 那么ReentrantLock
//
  private final static StampedLock lock = new StampedLock();

  public static void main(String[] args) throws InterruptedException {
    ExecutorService executor = Executors.newCachedThreadPool();
    final Semaphore semaphore = new Semaphore(threadTotal);
    final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
    for (int i = 0; i < clientTotal; i++) {
      executor.execute(() -> {
        try {
          semaphore.acquire();
          add();
          semaphore.release();
        } catch (InterruptedException e) {
          log.info("exception",e);
        }
        countDownLatch.countDown();
      });
    }
    countDownLatch.await();//
    executor.shutdown();//
    log.info("count:{}",count);
  }

  private static void add (){
    long l = lock.writeLock();
    try {
      count++;
    } finally {
      lock.unlock(l);
    }
  }

}
