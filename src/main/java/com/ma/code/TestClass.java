package com.ma.code;

public class TestClass {
  private static void testMethod() {
    System.out.println("testMethod");
  }

  public static void main(String[] args) {
    //null可以被强制类型转换成任意类型（不是任意类型对象），于是可以通过它来执行静态方法。
    ((TestClass) null).testMethod();
  }
}
