package com.ma.juc.example.atomic;
import com.ma.juc.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@ThreadSafe
public class Demo1_Atomic_Integer {
    //请求总数
    private static int clientTotal = 5000;
    //同时并发执行的线程数
    private static int threadTotal = 200;

    private static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for(int i = 0;i < clientTotal ; i++)
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                } catch (Exception e) {
                   log.error("exception",e);
                }
                countDownLatch.countDown();
            });
        countDownLatch.await();
        executorService.shutdown();
        log.info("count:{}",count.get());
    }
    public static void add(){
        count.incrementAndGet();//先自增再获取值
        // count.compareAndSet(curentt,next)
        // count.getAndIncrement();//先获取值再自增
// 内部使用的方法
//        do {
//            var5 = this.getIntVolatile(var1, var2);
//        } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));
// 深度调用的方法
// public final native boolean compareAndSwapInt(Object var1, long var2, int var4, int var5);
// 执行过程
// 拿当前对象的值(工作内存)和底层(主内存)的值进行对比, 如果一样那么执行+1的操作,
    }
}
