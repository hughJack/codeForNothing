package com.ma.juc.example.ThreadPoll.cancel;

import java.lang.Thread.State;
import java.util.UUID;

import lombok.Data;

/**
 * 任务类，实现了Runnable接口
 * <p>
 * Create Time: 2018年5月25日
 * </p>
 *
 * @version 1.0
 */
@Data
public class Task implements Runnable {

  private String taskName;// 任务名称
  private volatile long start = 0L; // 任务开始时间
  private State state; // 线程状态

  private Thread taskInThread; // 当前任务所处的线程

  public Task(String taskName) {
    this.taskName = taskName;
  }

  @Override
  public void run() {
    // 将当前任务与当前线程相关连，为的是后续设置中断状态做准备
    this.setTaskInThread(Thread.currentThread());
    // 记录任务执行开始时间
    start = System.currentTimeMillis();
    long num = 0;
    try {
      String uuid = "";
      ////////// 模拟任务耗时开始 //////////
      uuid = UUID.randomUUID().toString();
      while (!Thread.currentThread().isInterrupted()) {
        if (uuid.startsWith("0")) {
          break;
        }
      }
      long now = System.currentTimeMillis();
      num = now - start;
      System.out.println(taskName + "=>开始  start==" + start + "  模拟耗时" + num / 1000);

      ////////// 模拟任务耗时结束 //////////

      // 任务执行结束，在存储任务容器中删除该任务
      if (Main.map.get(this.getTaskName()) != null) {
        Main.map.remove(this.getTaskName());
        if (!Thread.currentThread().isInterrupted()) {
          System.out.println(taskName + "==>正常结束，模拟清除 耗时：" + (num / 1000) + "  实际耗时=="
              + ((System.currentTimeMillis() - start) / 1000) + "  start=" + start + "  end=" + now);
        } else {
          System.out.println(taskName + "==>中断结束，模拟清除 耗时：" + (num / 1000) + "  实际耗时=="
              + ((System.currentTimeMillis() - start) / 1000) + "  start=" + start + "  end=" + now);
        }
      }
    } catch (Exception e) {
      long end = System.currentTimeMillis();
      System.out.println(taskName + "===>被中断了   模拟耗时：" + (num / 1000) + "  start：" + start + "  end：" + end
          + " 实际耗时==" + ((System.currentTimeMillis() - start) / 1000));

      // 任务中断，在存储任务容器中删除该任务
      if (Main.map.get(this.getTaskName()) != null) {
        Main.map.remove(this.getTaskName());
      }
    }

  }

  // 对外提供设置任务中断的方法
  public void setInterrupte(Thread thread) {
    try {
      thread.interrupt();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  public State getThreadStatus() {
    this.state = Thread.currentThread().getState();
    return this.state;
  }

}
//
//---------------------
//作者：网内网外
//来源：CSDN
//原文：https://blog.csdn.net/hjhandxjl/article/details/80491931
//版权声明：本文为博主原创文章，转载请附上博文链接！
