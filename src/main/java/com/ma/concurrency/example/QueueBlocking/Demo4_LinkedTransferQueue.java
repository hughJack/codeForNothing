/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.concurrency.example.QueueBlocking;

import java.util.Random;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;
import lombok.extern.slf4j.Slf4j;

/*
 * 描述 : 队列在并发容器里面是最重要的,也是应用最多的容器
 * 高并发的情况下:  可以使用的队列分两种
 * 高并发的情况下:  可以使用的队列分两种
 * 1.ConcurrentLinkedQueue // 内部加锁的
 * 2.BlockingQueue         // 阻塞式队列?? 例如:满了生产者等待, 空了消费者等待
 *    LinkedBQ
 *    ArrayBQ
 *    TransferQueue : 有容量,还可以通过 add put 放
 *    SynchronizeQueue: 继承自TransferQueue,容量为0
 * 3. DelayQueue           //执行定时任务的
 */
@Slf4j
public class Demo4_LinkedTransferQueue {

  static TransferQueue<String> queue = new LinkedTransferQueue<>() ;
  static Random random = new Random();

  public static void main(String[] args) throws InterruptedException {
    // 1个生产者
    // new Thread(()->{
    //   try {
    //     System.out.println(QueueBlocking.take());
    //   } catch (InterruptedException e) {
    //     e.printStackTrace();
    //   }
    // }).start();

    // 生产者
    // 直接交给消费者处理
     queue.transfer("aaa"); // 找不到消费的情况下,会被阻塞,how to identify
     // QueueBlocking.put("aaa");       // 只在容器满的时候,出现阻塞
    // 消费者 5个
    System.out.println("执行了吗");
    new Thread(()->{
      try {
        System.out.println(queue.take());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }).start();
  }
}
