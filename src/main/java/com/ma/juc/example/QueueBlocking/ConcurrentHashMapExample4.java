/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.juc.example.QueueBlocking;

import com.ma.juc.annoations.ThreadSafe;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import lombok.extern.slf4j.Slf4j;

  @Slf4j
  @ThreadSafe
  public class ConcurrentHashMapExample4 {

    //请求总数
    public static int clientTotal = 5000;
    //同时并发执行的线程数
    public static int threadTotal = 200;

    public static Map<Integer,Integer> map = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {
      ExecutorService executorService = Executors.newCachedThreadPool();
      final Semaphore semaphore = new Semaphore(threadTotal);
      final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
      for(int i = 0;i < clientTotal ; i++){
        final int count = i;
        executorService.execute(() -> {
          try {
            semaphore.acquire();
            add(count);
            semaphore.release();
          } catch (Exception e) {
            log.error("exception",e);
          }
          countDownLatch.countDown();
        });
      }
      countDownLatch.await();
      executorService.shutdown();
      log.info("Size:{}",map.size());
    }
    public static void add(int i){
      map.put(i,i);
    }
  }
