/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.concurrency.example.aqs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CountdownLatchExample1 {

  //请求总数
  public static int clientTotal = 20;
  //同时并发执行的线程数
  public static int threadTotal = 3;

  public static int count = 0;

  public static void main(String[] args) throws InterruptedException {
    ExecutorService executorService = Executors.newCachedThreadPool();
    final Semaphore semaphore = new Semaphore(threadTotal);
    final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);// 标识一次拿几个许可
    System.out.println(countDownLatch.getCount());
    for (int i = 0; i < clientTotal; i++) {
      final  int finalI = i;
      executorService.execute(() -> {
        try {
          semaphore.acquire();// 获取一个许可
          add(finalI);
          semaphore.release();// 是放一个许可
        } catch (Exception e) {
          log.error("exception", e);
        }
        countDownLatch.countDown();
      });
    }
    System.out.println(countDownLatch.getCount());
    countDownLatch.await();
    System.out.println(countDownLatch.getCount());

    executorService.shutdown();
  }

  public static void add(int threadNum) throws InterruptedException {
    log.info("{}",threadNum);
    Thread.sleep(1000);
  }
}
