/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.juc.example.ThreadPoll;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import lombok.extern.slf4j.Slf4j;

/*
6种线程池 :
 * 描述 : 最难的一种线程池,不好理解!!!适合特别难的而服务做分拆, 执行的结果合并  就是任务的结果
 *
 * fork 分支
 * join 合并
 *
 * 一般应用于 ---- 大规模的数据计算
 *
 * 递归的归并排序 ?? 多线程的归并排序
 */
@Slf4j
public class Demo11_ForkJoinPool {

  // one million
  static int[] nums = new int[1000000];
  // 5 thousand
  static final int MAX_NUM = 50000;
  static Random random = new Random();

  static {
    for (int i = 0; i < nums.length; i++) {
      nums[i] = random.nextInt(100);
    }
    System.out.println(Arrays.stream(nums).sum());// stream API
  }

  // 没有返回值的,怎么求和的
  // RecursiveAction 是没有返回值的,所以只能Fork,然后打印, 没有使用到Join
  /*static class MyTask extends RecursiveAction {
    int start, end;
    public MyTask(int start, int end) {
      this.start = start;
      this.end = end;
    }
    @Override
    protected void compute() {
      if (end - start <= MAX_NUM) {
        long sum = 0L;
        for (int i = start; i < end; i++) {
          sum += nums[i];
        }
        log.info("from {} to {} = {}", start, end, sum);
      } else {
        int mid = start + (end - start) / 2;
        MyTask task1 = new MyTask(start, mid);
        MyTask task2 = new MyTask(mid, end);
        task1.fork();
        task2.fork();
      }
    }
  }*/
  // 有返回值的
  static class MyTask extends RecursiveTask<Long> {

    int start, end;

    public MyTask(int start, int end) {
      this.start = start;
      this.end = end;
    }

    @Override
    //  # # # 实现任务的切分 # # #
    protected Long compute() {
      if (end - start <= MAX_NUM) {
        long sum = 0L;
        for (int i = start; i < end; i++) {
          sum += nums[i];
        }
        log.info("from {} to {} = {}", start, end, sum);
        return sum;
      } else {
        int mid = start + (end - start) / 2;
        MyTask task1 = new MyTask(start, mid);
        MyTask task2 = new MyTask(mid, end);
        task1.fork();
        task2.fork();
        return task1.join() + task2.join();
      }
    }
  }

  public static void main(String[] args) throws IOException {
    ForkJoinPool pool = new ForkJoinPool();
    MyTask myTask = new MyTask(0, nums.length);
    pool.execute(myTask);
    // 如果使用有返回值的  RecursiveTask
    Long result = myTask.join(); //  本身就是阻塞的,所以不需要其他的
    System.out.println(result);
    // 由于ForkJoin是守护线程, 所以必须阻塞  看到执行的结果
  }

}
