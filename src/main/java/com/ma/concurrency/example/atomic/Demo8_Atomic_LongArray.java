/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.concurrency.example.atomic;

import java.util.concurrent.atomic.AtomicLongArray;

public class Demo8_Atomic_LongArray {

  // 更新数组
  // 使用索引进行更新
  public static void main(String[] args) {
    AtomicLongArray array = new AtomicLongArray(8);
    System.out.println(array.toString());
    array.compareAndSet(1,0,2);
    System.out.println(array.toString());
  }
}
