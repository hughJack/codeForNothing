/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.pattern.Observer;

import java.util.Random;

public class IPUtil {

  static String generateIpAddr() {
    Random random = new Random();
    StringBuilder sb = new StringBuilder();
    sb.append(random.nextInt(255) + ".").append(random.nextInt(255) + ".")
        .append(random.nextInt(255) + ".").append(random.nextInt(255));
    return sb.toString();
  }
}
