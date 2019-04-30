/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.concurrency;

import com.ma.concurrency.annoations.NotThreadSafe;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NotThreadSafe
public class ConcurrencyTest {

  public static int clientTotal = 5000;

  public static int threadTotal = 200;

  public static int count = 0;

  public static void main(String[] args) throws InterruptedException {
    ExecutorService service = Executors.newCachedThreadPool();
    // 线程调度和闭锁
    final Semaphore semaphore = new Semaphore(threadTotal);
    final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
    for (int i = 0; i < clientTotal; i++) {
      service.execute(() -> {
        try {
          semaphore.acquire();
          add();
          semaphore.release();
        } catch (Exception e) {
          log.info("exceptino", e);
        }
        countDownLatch.countDown();
      });
    }
    countDownLatch.await();
    service.shutdown();
    log.info("count:{}", count);
  }

  private static void add() {
    count++;
  }
}
