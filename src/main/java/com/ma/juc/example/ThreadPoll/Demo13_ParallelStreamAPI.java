/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.juc.example.ThreadPoll;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;

/*
 * 描述 : 补充知识点, 和线程池没关系
 *
 *
 */
@Slf4j
public class Demo13_ParallelStreamAPI {

  public static void main(String[] args) {
    List<Integer> list = new ArrayList<>();
    Random random = new Random();

    for (int i = 0; i < 10000; i++) {
      list.add(100000 + random.nextInt(100000));
    }
    long start = System.currentTimeMillis();
    list.stream().forEach(v -> isPrime(v));
    long end = System.currentTimeMillis();
    System.out.println((end - start));

    long start2 = System.currentTimeMillis();
    list.parallelStream().forEach(v -> isPrime(v));
    long end2 = System.currentTimeMillis();
    System.out.println((end2 - start2));
  }

  static boolean isPrime(int num) {
    for (int i = 2; i < num / 2; i++) {
      if (num % i == 0) {
        return false;
      }
    }
    return true;
  }

}
