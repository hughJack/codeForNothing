package com.ma.concurrency.example.lock;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author yzhang
 * @date 2018/5/25 21:43
 * @desc 写一个多线程情况下缓存小例子
 */
public class CacheLock {

  private Map<String, Object> cacheMap = new ConcurrentHashMap<String, Object>();
  /**
   * 根据key获取value
   *
   * @param key
   * @return
   */
  private ReadWriteLock lock = new ReentrantReadWriteLock();

  public Object getCache(String key) {
    //1.先获取读锁
    Lock readLock = this.lock.readLock();
    Lock writeLock = this.lock.writeLock();
    readLock.lock();
    Object result = cacheMap.get(key);
    try {
      //2.判断是否存在数据
      if (result == null) {
        //3.如果数据不存在，那么就去数据库获取数据,此时应该先释放读锁，并开启写锁
        readLock.unlock();
        //4.为了防止所线程，这里还需要再次判断是否存在数据
        if (cacheMap.get(key) == null) {
          try {
            writeLock.lock();
            //5.从数据库里面获取数据
            //...........
          } finally {
            //6.释放写锁，这里是为了防止，当前线程操作异常，所以必须要释放写锁
            writeLock.unlock();
          }
        }
        //7.再一次开启读锁
        readLock.lock();
      }
    } finally {
      //8.防止程序失败，强制释放读锁
      readLock.unlock();
    }
    //9.返回结果
    return result;

  }
}