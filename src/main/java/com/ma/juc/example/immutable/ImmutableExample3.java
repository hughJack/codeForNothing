/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.juc.example.immutable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.notification.RunListener.ThreadSafe;

@Slf4j
@ThreadSafe
// 谁会这么声明 Set Map List 集合 ? ? ? ?
// 如果可以做成不可变的对象,尽量这么做!!  可以保证线程安全
public class ImmutableExample3 {

  private static final ImmutableList list = ImmutableList.of(1, 2, 3);

  private static final ImmutableSet set = ImmutableSet.copyOf(list);

  private static final ImmutableMap<Integer, Integer> map2 = ImmutableMap.<Integer, Integer>builder()
      .put(1, 1).put(2, 2).build();

  public static void main(String[] args) {
    map2.put(3,3);
    list.add(4);
  }
}
