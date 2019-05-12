/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.concurrency.example.lock.ReetrantDemo;

// 所谓可重入，意味着线程可以进入它已经拥有的锁的同步代码块儿。
//
// 我们设计两个线程调用print()方法，第一个线程调用print()方法获取锁，进入lock()方法，由于初始lockedBy是null，所以不会进入while而挂起当前线程，而是是增量lockedCount并记录lockBy为第一个线程。接着第一个线程进入doAdd()方法，由于同一进程，所以不会进入while而挂起，接着增量lockedCount，当第二个线程尝试lock，由于isLocked=true,所以他不会获取该锁，直到第一个线程调用两次unlock()将lockCount递减为0，才将标记为isLocked设置为false。
//
// 可重入锁的概念和设计思想大体如此，Java中的可重入锁ReentrantLock设计思路也是这样
// ---------------------
// 作者：Androider_Zxg
// 来源：CSDN
// 原文：https://blog.csdn.net/u012545728/article/details/80843595
// 版权声明：本文为博主原创文章，转载请附上博文链接！
public class Reentrant {

  boolean isLocked = false;
  Thread lockedBy = null;
  int lockedCount = 0;

  public synchronized void lock()
      throws InterruptedException {
    Thread thread = Thread.currentThread();
    while (isLocked && lockedBy != thread) {
      wait();
    }
    isLocked = true;
    lockedCount++;
    lockedBy = thread;
  }

  public synchronized void unlock() {
    if (Thread.currentThread() == this.lockedBy) {
      lockedCount--;
      if (lockedCount == 0) {
        isLocked = false;
        notify();
      }
    }
  }
}

class Count2 {

  Reentrant lock = new Reentrant();

  public void print() throws InterruptedException {
    lock.lock();
    doAdd();
    lock.unlock();
  }

  public void doAdd() throws InterruptedException {
    lock.lock();
    //do something
    lock.unlock();
  }
}