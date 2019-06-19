package com.ma.pattern;

public class Main {

  // 使用函数式接口的另一个好处就是可以用lambda表达式赋给一个接口声明的变量.
  public static void main(String[] args) {
//创建线程
    Runnable run = () -> {
      System.out.println("BlahBlahBlahBlah");
    };
    Thread thread = new Thread(run);
    thread.start();
//我自己写的函数式接口
    FunctionalInterfaceDemo fi = (sth) -> System.out.println("BlahBlahBlahBlah");
    fi.doSomething("测试");

// 向函数式变量  传递一个   方法的引用
// 依旧执行  原先的类的方法
// 实际执行的是哪一个???    新传递的
    new Main().change();
  }

  public void change() {
    FunctionalInterfaceDemo fi = this::test;
    fi.doSomething("测试");
    System.out.println(this.getClass().getName());
  }

  public void test(String sth) {
    System.out.println("hello test2 from main" + sth);
  }
}

@FunctionalInterface
interface FunctionalInterfaceDemo {

  public void doSomething(String sth);

  @Override
  String toString();
}
