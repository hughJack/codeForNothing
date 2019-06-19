package com.ma.juc.example.atomic;

import com.ma.juc.annoations.ThreadSafe;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

@Slf4j
@ThreadSafe
public class Demo5_Atomic_FieldUpdater {

  // AtomicIntegerFieldUpdater 原子性的更新指定的类的字段
  // 目的是为了只执行一次(对字段进行原子性的修改)
  private static AtomicIntegerFieldUpdater<Demo5_Atomic_FieldUpdater> updater = AtomicIntegerFieldUpdater
      .newUpdater(Demo5_Atomic_FieldUpdater.class, "count");

  // 必须使用 volatile 修饰字段
  @Getter
  private volatile int count = 100;

  public static void main(String[] args) {
    Demo5_Atomic_FieldUpdater example5 = new Demo5_Atomic_FieldUpdater();
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
