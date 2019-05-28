package com.ma.cache;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

//---------------------
//    作者：Stormbreaker1995
//    来源：CSDN
//    原文：https://blog.csdn.net/foye12/article/details/78647647
//    版权声明：本文为博主原创文章，转载请附上博文链接！
@Getter
public class LFU2<k, v> {
  private final int capcity;

  private Map<k, Entry> cache = new HashMap<>();

  public LFU2(int capcity) {
    this.capcity = capcity;
  }

  public void put(k key, v value) {
    this.removeElement();
    Entry entry = this.cache.get(key);
    if (entry == null) {
      cache.put(key, new Entry(key, value, 1, System.nanoTime()));
      return;
    } else {
      entry.hitCount++;
    }
  }


  public v get(k key) {
    Entry entry = this.cache.get(key);
    if (entry != null) {
      entry.hitCount++;
      return entry.value;
    }
    return null;
  }

  //移除元素
  private void removeElement() {
    if (cache.size() >= capcity) {
      Collection<Entry> values = cache.values();
      Entry min = Collections.min(values);
      cache.remove(min.key);
    }
  }

  //内部类
  class Entry implements Comparable<Entry> {
    private k key;
    private v value;
    private int hitCount;
    private long lastTime;

    private Entry(k key, int hitCount, long lastTime) {
      this.key = key;
      this.hitCount = hitCount;
      this.lastTime = lastTime;
    }

    public Entry(k key, v value, int hitCount, long lastTime) {
      this.key = key;
      this.value = value;
      this.hitCount = hitCount;
      this.lastTime = lastTime;
    }

    @Override
    public int compareTo(Entry o) {
      int compare = Integer.compare(this.hitCount, o.hitCount);
      return compare == 0 ? Long.compare(this.lastTime, o.lastTime) : compare;
    }
  }


  public static void main(String[] args) {
    LFU2<Integer, Integer> cache = new LFU2<>(3);
    cache.put(2, 2);
    cache.put(1, 1);

    System.out.println(cache.get(2));
    System.out.println(cache.get(1));
    System.out.println(cache.get(2));

    cache.put(3, 3);
    cache.put(4, 4);

    //1、2元素都有访问次数，放入3后缓存满，加入4时淘汰3
    System.out.println(cache.get(3));
    System.out.println(cache.get(2));
    //System.out.println(cache.get(1));
    System.out.println(cache.get(4));

    cache.put(5, 5);
    //目前2访问2次，1访问一次，4访问一次，由于4的时间比较新，放入5的时候移除1元素。
    System.out.println("-=-=-=-");
    cache.cache.forEach((key, value) -> System.out.println(value.value));

  }

}
