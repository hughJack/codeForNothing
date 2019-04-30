/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.concurrency.example.QueueBlocking;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.extern.slf4j.Slf4j;

/*
 * 描述 : 队列在并发容器里面是最重要的,也是应用最多的容器
 * 高并发的情况下:  可以使用的队列分两种
 * 高并发的情况下:  可以使用的队列分两种
 * 1.ConcurrentLinkedQueue // 内部加锁的
 * 2.BlockingQueue         // 阻塞式队列?? 例如:满了生产者等待, 空了消费者等待
 *    LinkedBlockingQueue
 *    ArrayBlockingQueue
 * 3. DelayQueue           //执行定时任务的
 */
@Slf4j
public class Demo01_LinkedBlockingQueue {

  public static void main(String[] args) {
    // 并发 单向 无界 队列, 队列永远不会满, 除非内存满了
    Queue<Object> queue = new ConcurrentLinkedQueue<>();
    for (int i = 0; i < 10; i++) {
      boolean offer = queue.offer("o-" + i);// add方法
    }
    log.info("value{}", queue);
    log.info("size {}", queue.size());
    log.info("poll {}", queue.poll());// 区别? 拿出来,删除
    log.info("size {}", queue.size());
    log.info("peek {}", queue.peek());// 区别? 拿出来,不删
    log.info("size {}", queue.size());
  }
}
