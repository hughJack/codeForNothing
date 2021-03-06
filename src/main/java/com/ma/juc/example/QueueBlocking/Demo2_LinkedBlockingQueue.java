/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.juc.example.QueueBlocking;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/*
 * 描述 : 队列在并发容器里面是最重要的,也是应用最多的容器
 * 高并发的情况下:  可以使用的队列分两种
 * 1.ConcurrentLinkedQueue // 内部加锁的
 * 2.BlockingQueue         // 阻塞式队列?? 例如:满了生产者等待, 空了消费者等待
 *    LinkedBlockingQueue
 *    ArrayBlockingQueue
 * 3. DelayQueue           //执行定时任务的
 */
@Slf4j
public class Demo2_LinkedBlockingQueue {

  static BlockingQueue<String> queue = new LinkedBlockingQueue<>();
  static Random random = new Random();

  public static void main(String[] args) {
    // 1个生产者
    new Thread(() -> {
      for (int i = 0; i < 100; i++) {
        try {
          queue.put("q" + i); // 如果满了就会等待
          TimeUnit.MILLISECONDS.sleep(random.nextInt(1000));
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    },"p1").start();

    // 消费者 5个
    for (int i = 0; i < 5; i++) {
      new Thread(() -> {
        for (; ; ) {
          try {
            String take = queue.take();// 如果空了就会等待
            log.info("Thread = {} , value = {}", Thread.currentThread().getName(), take);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }, "c" + i).start();
    }
  }
}
