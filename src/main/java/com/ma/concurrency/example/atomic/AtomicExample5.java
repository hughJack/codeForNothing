package com.ma.concurrency.example.atomic;

import com.ma.concurrency.annoations.ThreadSafe;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

@Slf4j
@ThreadSafe
public class AtomicExample5 {

  // AtomicIntegerFieldUpdater 更新指定的类的执行的字段的值
 //  目的是为了只执行一次(对字段进行原子性的修改)
  private static AtomicIntegerFieldUpdater<AtomicExample5> updater = AtomicIntegerFieldUpdater
      .newUpdater(AtomicExample5.class, "count");
  @Getter
  private volatile int count = 100;

  public static void main(String[] args) {
    AtomicExample5 example5 = new AtomicExample5();
    if (updater.compareAndSet(example5, 100, 120)) {
      log.info("parse sucess 1,{}", example5.getCount());
    }

    if (updater.compareAndSet(example5, 100, 120)) {
      log.info("parse sucess 2,{}", example5.getCount());
    } else {
      log.info("parse failed,{}", example5.getCount());

    }
  }
}
