/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.juc.example.MyContainer;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import lombok.extern.slf4j.Slf4j;

/*
 * 描述 : 写一个固定容量的同步容器,拥有put get count 方法
 * 支持2个生产者,10个消费者线程的阻塞调用!!!
 * 要点:::
 * 1.固定容量
 * 2.同步容器
 * 3.阻塞调用
 */
@Slf4j
public class ConsumerAndProducerExample2<T> extends Thread {

  private final int MAX = 10;
  private final LinkedList<T> list = new LinkedList<>();
  private int count = 0;

  private ReentrantLock lock = new ReentrantLock();
  private Condition producer = lock.newCondition();
  private Condition consumer = lock.newCondition();

  // 生产者
  public void add(T t) {
    try {
      lock.lock();
      while (list.size() == MAX) {
        producer.await();// 释放锁
      }
      list.add(t);
      ++count;
      consumer.signalAll();// 通知消费者消费
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      if (lock.isHeldByCurrentThread()) {
        lock.unlock();
      }
    }
  }

  // 消费者
  public T get() {
    T t = null;
    try {
      lock.lock();
      while (list.size() == 0) {
        consumer.await();// 释放锁
      }
      t = list.removeFirst();
      --count;
      producer.signalAll();// 通知生产者进行生产
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      if (lock.isHeldByCurrentThread()) {
        lock.unlock();
      }
    }
    return t;
  }

  public static void main(String[] args) {
    ConsumerAndProducerExample2<String> example = new ConsumerAndProducerExample2();
    // 启用消费者
    for (int i = 0; i < 10; i++) {
      new Thread(() -> {
        for (int j = 0; j < 5; j++) {
          log.info(example.get());
        }
      }, "c" + i).start();
    }
    // 启用生产者
    for (int i = 0; i < 2; i++) {
      new Thread(() -> {
        for (int j = 0; j < 25; j++) {
          example.add(Thread.currentThread().getName() + " " + j);
        }
      }, "p" + i).start();
    }
  }
}
