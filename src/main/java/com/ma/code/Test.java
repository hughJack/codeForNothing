package com.ma.code;

import com.google.common.base.Preconditions;

import java.io.PrintStream;
import java.util.Date;

public class Test extends Date {

  public static void main(String[] args) {
    new Test().test();
  }

  public void test() {
    System.out.println(super.getClass().getName());
    System.out.println(super.getClass().getSuperclass().getName());

    StringBuffer stringBuffer =  new StringBuffer("sss");
    stringBuffer.append("ss");
  }
  // 这属于脑筋急转弯的题目，在一个qq群有个网友正好问过这个问题，我觉得挺有趣，就研究了一下，没想到今天还被你面到了，哈哈。
  // 在test方法中，直接调用getClass().getName()方法，返回的是Test类名
  //
  // 由于getClass()在Object类中定义成了final，子类不能覆盖该方法，
  // 所以，在test方法中调用getClass().getName()方法，其实就是在调用从父类继承的getClass()方法，
  // 等效于调用super.getClass().getName()方法，所以，super.getClass().getName()方法返回的也应该是Test。
  //
  // 如果想得到父类的名称，应该用如下代码： getClass().getSuperClass().getName();
}
