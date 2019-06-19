/**
 * 描述. 线程池的概念 例子: 汇总结果
 */
package com.ma.juc.example.ThreadPoll;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;

/*
 * 6种线程池 :
 * 描述 : 线程池实现并行计算
 *
 */
@Slf4j
public class Demo06_FixedPool {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    ExecutorService service = Executors.newFixedThreadPool(2);
    MyTask t1 = new MyTask(1,80000);
    MyTask t2 = new MyTask(80001,130000);
    MyTask t3 = new MyTask(130001,150000);
    MyTask t4 = new MyTask(150001,200000);
    Future<List<Integer>> submit0 = service.submit(t1);
    Future<List<Integer>> submit1 = service.submit(t2);
    Future<List<Integer>> submit2 = service.submit(t3);
    Future<List<Integer>> submit3 = service.submit(t4);

    long head = System.currentTimeMillis();
    List<Integer> integers = submit0.get();
    List<Integer> integers1 = submit1.get();
    List<Integer> integers2 = submit2.get();
    List<Integer> integers3 = submit3.get();
    long tail = System.currentTimeMillis();
    System.out.println(tail - head);

    service.shutdown();
    System.out.println("done");
  }
  /*
   * 描述 : 获取一段的质数的集合
   *
   */
  static class MyTask implements Callable<List<Integer>> {

    int startPos, endPos;

    public MyTask(int startPos, int endPos) {
      this.startPos = startPos;
      this.endPos = endPos;
    }

    @Override
    public List<Integer> call() throws Exception {
      return null;
    }

    // 判断质数
    static boolean isPrime(int num) {
      for (int j = 2; j <= num / 2; j++) {
        if (num % j == 0) {
          return false;
        }
      }
      return true;
    }

    static List<Integer> getPrime(int start, int end) {
      ArrayList<Integer> list = new ArrayList<>();
      // 感觉需要去重
      for (int i = start; i <= end; i++) {
        if (isPrime(i)) {
          list.add(i);
        }
      }
      return list;
    }
  }
}
