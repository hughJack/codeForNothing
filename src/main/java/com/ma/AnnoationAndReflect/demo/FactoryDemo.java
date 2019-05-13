package com.ma.AnnoationAndReflect.demo;

public class FactoryDemo {

  public static void main(String[] args) {

    Fruit f = Factory.getInstance("apple");  // 静态方法
    // 只要传入字符串进入就可以实例化对象--深度解耦了
    // Fruit f = Factory.getInstance("cn.mldn.demo.Orange");
    f.eat();
  }
}


class Factory {

// 这个时候即使增加了接口的子类，工厂类照样可以完成对象的实例化操作，
// 这个才是真正的工厂类，可以应对于所有的变化。
//
// 如果单独从开发角度而言，与开发者关系不大，但是对于日后学习的一些框架技术这个就是它实现的命脉，
// 在日后的程序开发上，如果发现操作的过程之中需要参数传递了一个完整的“包.类”名称的时候几乎都是反射机制作用。
  public static Fruit getInstance(String className) {
    // if ("apple".equals(className)) {
    //   return new Apple();
    // }
    // return null;

    Fruit f = null;
    try {
      //获得Class对象 ---- 实例化一个类的对象

      f = (Fruit) Class.forName(className).newInstance();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return f;
  }

}

interface Fruit {

  public void eat();
}

class Apple implements Fruit {

  public void eat() {
    System.out.println("吃苹果。");
  }

}

