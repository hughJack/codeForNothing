/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.concurrency.example.unsafe;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

@Slf4j
public class DateFormatExample3 {

  private  static DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyyMMdd");

  //请求总数
  public static int clientTotal = 5000;
  //同时并发执行的线程数
  public static int threadTotal = 200;

  public static void main(String[] args) throws InterruptedException {
    ExecutorService executorService = Executors.newCachedThreadPool();
    final Semaphore semaphore = new Semaphore(threadTotal);
    final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
    for (int i = 0; i < clientTotal; i++) {
      // 传递到线程池的需要是final类型的变量
      final int count = i;
      executorService.execute(() -> {
        try {
          semaphore.acquire();
          parse(count);
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

  public static void parse( int i) {
    // 使用堆栈封闭保证线程安全
    try {
      log.info("{},{}",i,DateTime.parse("20180208",dateTimeFormatter).toDate());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
