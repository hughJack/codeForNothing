/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.concurrency.example.immutable;

import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.notification.RunListener.ThreadSafe;

@Slf4j
@ThreadSafe
public class ImmutableExample2 {

  private  static Map<Integer,Integer> map = Maps.newHashMap();

  static {
    map.put(1,1);
    map.put(2,2);
    map.put(3,3);
    map = Collections.unmodifiableMap(map);// 处理过之后不帅瞥视再进行操作
  }

  public static void main(String[] args) {
    map.put(1,2);
    log.info("{}", map.get(1));
  }

}
