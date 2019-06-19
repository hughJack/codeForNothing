package com.ma.juc.example.ThreadPoll.cancel;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NameThreadFactory implements ThreadFactory {
  private final AtomicInteger threadNumber = new AtomicInteger(1);
  private final String threadName;

  public NameThreadFactory(String threadName) {
    this.threadName = threadName;
  }

  public Thread newThread(Runnable r) {
    Thread t = new Thread(r, threadName + threadNumber.getAndIncrement());
    return t;
  }
}
//---------------------
//作者：网内网外
//来源：CSDN
//原文：https://blog.csdn.net/hjhandxjl/article/details/80491931
//版权声明：本文为博主原创文章，转载请附上博文链接！
