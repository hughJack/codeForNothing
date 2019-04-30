/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.suanfa;

public class demo5_Recusion {

  public static void main(String[] args) {
    System.out.println(f(5));
  }

  // 递归实现   累加
  static long f(int n) {
    if (n < 1) {
      return -1;
    }
    if (n == 1) {
      return 1;
    }
    return n + f(n - 1);
  }
}
