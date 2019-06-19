/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.juc.example.immutable;

import com.google.common.collect.Maps;
import com.ma.juc.annoations.NotThreadSafe;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NotThreadSafe
// 不可变的对象
public class ImmutableExample1 {
// final 修饰基本类型的变量
  private final static Integer a = 1;
  private final static String b = "2";
  // 使用final修饰引用变量,但是不是线程安全的
  private final static Map<Integer,Integer> map = Maps.newHashMap();

  static {
    map.put(1,1);
    map.put(2,2);
    map.put(3,3);
  }

  public static void main(String[] args) {
    map.put(1,2);
    log.info("map.get(1)", map.get(1));
  }

  private void testChange(int a){
    log.info(String.valueOf(a));
  }
}
