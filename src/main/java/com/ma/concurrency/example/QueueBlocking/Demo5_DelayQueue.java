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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Demo5_DelayQueue {
  // 定时执行任务
  // 使用该对象的时候, 放入其中的对象必须实现 Delayed 接口
  static BlockingQueue<MyTask> tasks = new DelayQueue<>();
  static Random random = new Random();
  static class MyTask implements Delayed {

    long runtime;

    public MyTask(long rt) {
      this.runtime = rt;
    }

    @Override
    public long getDelay(TimeUnit unit) {
      return unit.convert(runtime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
      if (this.getDelay(TimeUnit.MILLISECONDS) < o.getDelay(TimeUnit.MILLISECONDS)) {
        return -1;
      } else if (this.getDelay(TimeUnit.MILLISECONDS) > o.getDelay(TimeUnit.MILLISECONDS)) {
        return 1;
      }
      return 0;
    }

    @Override
    public String toString() {
      return "MyTask{" +
          "runtime=" + runtime +
          '}';
    }
  }

  public static void main(String[] args) throws InterruptedException {
    long now = System.currentTimeMillis();
    MyTask task0 = new MyTask(now + 1000);
    MyTask task1 = new MyTask(now + 2000);
    MyTask task2 = new MyTask(now + 500);
    MyTask task3 = new MyTask(now + 1500);
    MyTask task4 = new MyTask(now + 1200);

    tasks.put(task0);
    tasks.put(task1);
    tasks.put(task2);
    tasks.put(task3);
    tasks.put(task4);

    System.out.println(tasks);

    System.out.println(tasks.size());
    for (int i = 0; i < tasks.size(); i++) {
      //  这么写是由问题的  ,会有一半的数据不能打印出来
      // 理由: tasks.size() 是变动的, 当 i == tasks.size() 的时候  循环不在执行
      System.out.println(tasks.take());
    }
    System.out.println(tasks.size());

  }
}
