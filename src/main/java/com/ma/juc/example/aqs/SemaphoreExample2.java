/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.juc.example.aqs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * 信号量
 * 适用于只能提供 "有限访问" 的情况,例如数据库的线程池
 */
@Slf4j
public class SemaphoreExample2 {

  //请求总数
  public static int clientTotal = 20;
  //同时并发执行的线程数
  public static int threadTotal = 3;

  public static int count = 0;

  public static void main(String[] args) throws InterruptedException {
    ExecutorService executorService = Executors.newCachedThreadPool();
    final Semaphore semaphore = new Semaphore(threadTotal);// 标识一次拿几个许可,指定并发数
    final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
    for (int i = 0; i < clientTotal; i++) {
      final  int finalI = i;
      executorService.execute(() -> {
        try {
          //  尝试获取锁: 获取不到,丢弃线程, 剩余的线程不在执行
          // if (semaphore.tryAcquire()) {
          // 执行5s以内获取到锁的线程
          // 尝试等待一段时间: 实时获取许可可能太匆忙了,如果超过时间,那么丢弃
          if (semaphore.tryAcquire(5, TimeUnit.SECONDS)) {
            add(finalI);
            semaphore.release();// 是放一个许可
          }
        } catch (Exception e) {
          log.error("exception", e);
        }
        countDownLatch.countDown();
      });
    }
    countDownLatch.await();
    executorService.shutdown();
  }

  public static void add(int threadNum) throws InterruptedException {
    log.info("{}",threadNum);
    Thread.sleep(1000);
  }
}
