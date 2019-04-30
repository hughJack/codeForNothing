/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.concurrency.example.commonUnsafe;

import com.ma.concurrency.annoations.NotRecommend;
import com.ma.concurrency.annoations.NotThreadSafe;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NotThreadSafe
@NotRecommend
public class DateFormatExample1 {

  //请求总数
  public static int clientTotal = 5000;
  //同时并发执行的线程数
  public static int threadTotal = 200;

  private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

  public static void main(String[] args) throws InterruptedException {
    ExecutorService executorService = Executors.newCachedThreadPool();
    final Semaphore semaphore = new Semaphore(threadTotal);
    final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
    for (int i = 0; i < clientTotal; i++) {
      executorService.execute(() -> {
        try {
          semaphore.acquire();
          parse();
          semaphore.release();
        } catch (Exception e) {
          log.error("exception", e);
        }
        countDownLatch.countDown();
      });
    }
    countDownLatch.await();
    executorService.shutdown();
  }

  public static void parse() {
    try {
      simpleDateFormat.parse("20180208");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
