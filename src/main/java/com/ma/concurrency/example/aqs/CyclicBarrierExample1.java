/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.concurrency.example.aqs;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CyclicBarrierExample1 {

  // 告知需要多少个线程同步等待
  private static CyclicBarrier barrier = new CyclicBarrier(5);

  public static void main(String[] args) throws InterruptedException {
    ExecutorService executor = Executors.newCachedThreadPool();
    for (int i = 0; i < 10; i++) {
      final int threadNum = i;
      Thread.sleep(1000);
      executor.execute(() -> {
        try {
          race(threadNum);
        } catch (Exception e) {
          log.info("Exception", e);
        }
      });
    }
    executor.shutdown();
  }
  // 功能演示
  public static void race(int threadNum) throws BrokenBarrierException, InterruptedException {
    Thread.sleep(1000);
    log.info("Thread Num is {}", threadNum);
    // 核心就是await()方法
    // 每个线程调用await()方法告诉CyclicBarrier我已经到达了屏障，然后当前线程被阻塞。
    log.info("{} is ready", threadNum);
    barrier.await();
    log.info("{} continue", threadNum);
  }
}
