/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.juc.example.QueueBlocking;

import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConcurrentSkipListSetExample {

  private static Integer threadTotal = 200;

  private static Integer clientTotal = 5000;

  private static Set<Integer> list = new ConcurrentSkipListSet<>();
  private static Map<Integer,Integer> map = Collections.synchronizedSortedMap(Maps.newTreeMap());

  public static void main(String[] args) throws InterruptedException {
    ExecutorService service = Executors.newCachedThreadPool();
    Semaphore semaphore = new Semaphore(threadTotal);
    CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
    for (int i = 0; i < clientTotal ; i++) {
      final  int count = i;
      service.execute(() -> {
            try {
              semaphore.acquire();
              update(count);
              semaphore.release();
            } catch (Exception e) {
              log.info("exception",e);
            }
            countDownLatch.countDown();
          }
      );
    }
    countDownLatch.await();
    service.shutdown();
    log.info("size = {}",list.size());
  }

  public static void update(Integer i){
    list.add(i);
  }
}
