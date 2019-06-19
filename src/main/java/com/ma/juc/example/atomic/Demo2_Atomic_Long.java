package com.ma.juc.example.atomic;
import com.ma.juc.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@ThreadSafe
public class Demo2_Atomic_Long {
    //请求总数
    private static int clientTotal = 5000;
    //同时并发执行的线程数
    private static int threadTotal = 200;

    private static AtomicLong count = new AtomicLong(0);

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
        // count.getAndIncrement();//先获取值再自增
// 和jdk8 里面新添加的类LongAdder功能相同
// 分散热点数据?  不适合全局唯一的那种统计
// 高并发技术的时候可以有限使用LongAdder

//  AtomicBoolean可以保证只有一个线程可以执行

    }
}
