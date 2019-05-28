package com.ma.cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> extends LinkedHashMap<K, V> {
  private final int CACHE_SIZE;

  /*
    @param  cacheSize      缓存大小
  */
  // true 表示让 linkedHashMap 按照访问顺序来进行排序，最近访问的放在头部，最老访问的放在尾部。
  public LRUCache(int cacheSize) {
    super((int) Math.ceil(cacheSize / 0.7) + 1, 0.75f
        , true);
    CACHE_SIZE = cacheSize;
  }

  @Override
// 当 map中的数据量大于指定的缓存个数的时候，就自动删除最老的数据。
  protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
    return super.size() > CACHE_SIZE;
  }
}
