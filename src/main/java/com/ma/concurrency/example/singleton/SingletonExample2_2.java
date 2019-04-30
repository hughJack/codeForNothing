/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.concurrency.example.singleton;

import lombok.extern.slf4j.Slf4j;

// 饿汉模式  -->  静态代码块
@Slf4j
public class SingletonExample2_2 {

  //私有的构造函数
  private SingletonExample2_2() {
  }

  // 单例对象
  // 注意 --> 静态域和静态代码块的顺序
 // 静态代码块和静态方法是按照顺序执行的
  private static SingletonExample2_2 instance = null;

  static {
    instance = new SingletonExample2_2();
  }


  public static SingletonExample2_2 getInstance() {
    return instance;
  }

  public static void main(String[] args) {
    System.out.println(getInstance().hashCode());
    System.out.println(getInstance().hashCode());
  }
}
