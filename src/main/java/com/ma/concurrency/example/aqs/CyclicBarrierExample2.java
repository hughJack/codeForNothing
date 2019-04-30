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
public class CyclicBarrierExample2 {

  // 告知需要多少个线程同步等待
  private static CyclicBarrier barrier = new CyclicBarrier(5, () -> {
    log.info("call back is running");
  });

  public static void main(String[] args) throws Exception {
    ExecutorService executor = Executors.newCachedThreadPool();
    for (int i = 0; i < 10; i++) {
      final int threadNum = i;
      Thread.sleep(1000);
      executor.execute(() -> {
        try {
          race(threadNum);
        } catch (Exception e) {
          e.printStackTrace();
        }
      });
    }
    executor.shutdown();
  }

  public static void race(Integer threadNum) throws Exception{
    Thread.sleep(1000);
    log.info("{} is ready", threadNum);
    // 等待2秒,如果超过这个时间,那么不再继续等待,继续往下执行
    try {
      // 告知CyclicBarrier当前线程已经在等待执行了
      barrier.await(2000, TimeUnit.MILLISECONDS);
    } catch (BrokenBarrierException  | TimeoutException e) {
      log.warn("exception is {}",e);
    }
    log.info("{} continue", threadNum);
  }
}
