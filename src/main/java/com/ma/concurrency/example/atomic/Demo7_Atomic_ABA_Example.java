/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.concurrency.example.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

// 部分乐观锁的实现是通过版本号（version）的方式来解决 ABA 问题，乐观锁每次在执行数据的修
// 改操作时，都会带上一个版本号，一旦版本号和数据的版本号一致就可以执行修改操作并对版本
// 号执行+1 操作，否则就执行失败。因为每次操作的版本号都会随之增加，所以不会出现 ABA 问
// 题，因为版本号只会增加不会减少。

// 在运用CAS做Lock-Free操作中有一个经典的ABA问题：
//
// 线程1准备用CAS将变量的值由A替换为B，在此之前，线程2将变量的值由A替换为C，又由C替换为A，然后线程1执行CAS时发现变量的值仍然为A，所以CAS成功。
//
// 但实际上这时的现场已经和最初不同了，尽管CAS成功，但可能存在潜藏的问题，例如下面的例子：
//
// 现有一个用单向链表实现的堆栈，栈顶为A，这时线程T1已经知道A.next为B，然后希望用CAS将栈顶替换为B：
//
// head.compareAndSet(A,B);
//
// 在T1执行上面这条指令之前，线程T2介入，将A、B出栈，再pushD、C、A，此时堆栈结构如下图，而对象B此时处于游离状态：
//
//
//
// 此时轮到线程T1执行CAS操作，检测发现栈顶仍为A，所以CAS成功，栈顶变为B，但实际上B.next为null，所以此时的情况变为：
//
// 其中堆栈中只有B一个元素，C和D组成的链表不再存在于堆栈中，平白无故就把C、D丢掉了。
//
// 以上就是由于ABA问题带来的隐患，各种乐观锁的实现中通常都会用版本戳version来对记录或对象标记，避免并发操作带来的问题，
// 在Java中，AtomicStampedReference<E>也实现了这个作用，它通过包装[E,Integer]的元组来对对象标记版本戳stamp，从而避免ABA问题，
// 例如下面的代码分别用AtomicInteger和AtomicStampedReference来对初始值为100的原子整型变量进行更新，AtomicInteger会成功执行CAS操作，
// 而加上版本戳的AtomicStampedReference对于ABA问题会执行CAS失败：
public class Demo7_Atomic_ABA_Example {

  // 解决线程的ABA问题
  // 每次变量更新的时候, 变量的版本号 +1,  版本号 只增不减
  //
  //
  private static AtomicInteger count = new AtomicInteger(100);
  private static AtomicStampedReference ref = new AtomicStampedReference(100, 0);

  public static void main(String[] args) throws InterruptedException {
    Thread intT1 = new Thread(new Runnable() {
      @Override
      public void run() {
        count.compareAndSet(100, 101);
        count.compareAndSet(101, 100);
      }
    });

    Thread intT2 = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
        }
        boolean c3 = count.compareAndSet(100, 101);
        System.out.println(c3); // true
      }
    });

    intT1.start();
    intT2.start();
    intT1.join();
    intT2.join();

    Thread refT1 = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
        }
        ref
            .compareAndSet(100, 101, ref.getStamp(), ref.getStamp() + 1);
        ref
            .compareAndSet(101, 100, ref.getStamp(), ref.getStamp() + 1);
      }
    });

    Thread refT2 = new Thread(new Runnable() {
      @Override
      public void run() {
        int stamp = ref.getStamp();
        try {
          TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
        }
        boolean c3 = ref.compareAndSet(100, 101, stamp, stamp + 1);
        System.out.println(c3); // false
      }
    });

    refT1.start();
    refT2.start();
  }

}
