/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.concurrency.example.lock.ReetrantDemo;
// 所谓不可重入锁，即若当前线程执行某个方法已经获取了该锁，
// 那么在方法中尝试再次获取锁时，就会获取不到被阻塞。我们尝试设计一个不可重入锁：
//

public class NotReentrant {

  private boolean isLocked = false;

  public synchronized void lock() throws InterruptedException {
    while (isLocked) {
      wait();
    }
    isLocked = true;
  }

  public synchronized void unlock() {
    isLocked = false;
    notify();
  }
}

// 当前线程执行print()方法首先获取lock，
// 接下来执行doAdd()方法就无法执行doAdd()中的逻辑，必须先释放锁。
//
// 这个例子很好的说明了不可重入锁。
//
class Count {

  NotReentrant lock = new NotReentrant();

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
