package com.ma.cache;

import java.util.LinkedHashMap;
import java.util.Map;

//  原文：https://blog.csdn.net/foye12/article/details/78647647
public class LRU<k, v> extends LinkedHashMap<k, v> {

  private final int MAX_SIZE;

  public LRU(int capcity) {
    super(8, 0.75f, true);
    this.MAX_SIZE = capcity;
  }

  // LinkedHashMap有一个accessOrder的参数正好和LRU的思路相契合，
  // 这里使用0.75的默认加载因子（加载因子过小空间利用率低，冲突减少，访问速度快，加载因子过大，反之。
  // 加载因子过大，当执行put操作，两个对象的hashcode相同时要操作链表，相应的get操作是也要操作链表，这样就使得访问变慢。）
  // removeEldestEntry方法当结果返回为true时，它会清除map中的最老元素。以实现LRU的算法。
  @Override
  public boolean removeEldestEntry(Map.Entry<k, v> eldest) {
    if (size() > MAX_SIZE) {
      System.out.println("移除的元素为：" + eldest.getValue());
    }
    return size() > MAX_SIZE;
  }

  public static void main(String[] args) {
    Map<Integer, Integer> map = new LRU<>(5);
    for (int i = 1; i <= 11; i++) {
      map.put(i, i);
      System.out.println("cache的容量为：" + map.size());
      if (i == 4) {
        map.get(1);
      }
    }

    System.out.println("=-=-=-=-=-=-=-map元素:");
    map.forEach((key, value) -> System.out.println(value));

  }

}
//
