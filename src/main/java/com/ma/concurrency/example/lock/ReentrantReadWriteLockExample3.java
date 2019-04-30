/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.concurrency.example.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockExample3 {

  private final Map<String, Data> map = new HashMap<>();
  private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
  private final Lock readlock = lock.readLock();
  private final Lock writelock = lock.writeLock();

  public Data get( String key){
    readlock.lock();
    try {
      return map.get(key);
    } finally {
      readlock.unlock();
    }
  }

  public Set<String> getAllKeys(){
    return  map.keySet();
  }

  public Data put(String key,Data value){
    writelock.lock();// 想执行写锁,如果一直有读锁,那么写操作一直都不能执行
    try {
      return map.put(key, value);
    } finally {
      writelock.unlock();
    }
  }
  class Data{}
}
