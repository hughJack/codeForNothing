package com.ma.concurrency.example.lock.ReetrantDemo;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

class CachedData {

  Object data;
  volatile boolean cacheValid;
  final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

  void processCachedData() {
    ReadLock readLock = rwl.readLock();
    WriteLock writeLock = rwl.writeLock();
    readLock.lock();
    // 锁降级 ???
    if (!cacheValid) {
      // Must release read lock before acquiring write lock
      readLock.unlock();
      writeLock.lock(); // 1. 获取写入锁
      try {
        // Recheck state
        // because another thread might have
        // acquired write lock and changed state before we did.
        if (!cacheValid) {
          // data = ...
          cacheValid = true;
        }
        // 通过在释放写锁之前获取读锁来降级
        // Downgrade by acquiring read lock before releasing write lock
        readLock.lock(); // 2. 获取读锁
      } finally {
        // 释放写入锁
        // Unlock write, still hold read
        writeLock.unlock();
      }
    }
    try {
      // use(data);
    } finally {
      readLock.unlock();
    }
  }
}