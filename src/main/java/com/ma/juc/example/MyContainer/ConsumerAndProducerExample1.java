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
import lombok.extern.slf4j.Slf4j;

/*
 * 描述 : 写一个固定容量的同步容器,拥有put get count 方法
 * 支持2个生产者,10个消费者线程的阻塞调用!!!
 * 要点:::
 * 1.固定容量
 * 2.同步容器
 * 3.阻塞调用
 * !!!!!非常原始的做法!!!!!
 */
@Slf4j
public class ConsumerAndProducerExample1<T> extends Thread {

  private final int MAX = 10;
  private final LinkedList<T> list = new LinkedList<>();
  private int count = 0;

  // 生产者
  public synchronized void add(T t) {
    while (list.size() == MAX) {
      try {
        this.wait();// 释放锁
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    list.add(t);
    ++count;
    this.notifyAll();
  }

  // 消费者
  public synchronized T get() {
    while (list.size() == 0) {
      try {
        this.wait();// 释放锁
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    T t = list.removeFirst();
    --count;
    this.notifyAll();
    return t;
  }

  public int getCount() {
    return count;
  }

  public static void main(String[] args) {
    ConsumerAndProducerExample1<String> example = new ConsumerAndProducerExample1();
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
