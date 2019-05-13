package com.ma.AnnoationAndReflect.demo;

import java.lang.reflect.Method;

class Person {

  private String name;

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void say() {
    System.out.println(123456789);
  }
}

public class TestDemo {

  public static <Mehtod> void main(String[] args) throws Exception {
    Class<?> cls = Class.forName("com.ma.AnnoationAndReflect.demo.Person"); // 取得Class对象
    Object obj = cls.newInstance(); // 实例化对象，没有向Person转型
    String attribute = "name"; // 要调用类之中的属性
    Method setMet = cls.getMethod("set" + initcap(attribute), String.class);// setName()
    Method getMet = cls.getMethod("get" + initcap(attribute));// getName()
    setMet.invoke(obj, "张三"); // 等价于：Person对象.setName("张三")
    System.out.println(getMet.invoke(obj));// 等价于：Person对象.getName() 输出张三
    /*自己测试-------------------------------------------------*/
    Person p = (Person) obj;
    p.say();//输出123456789
    Method m = cls.getDeclaredMethod("say");
    m.invoke(obj);//输出123456789
    /*自己测试-------------------------------------------------*/
    Method m2 = cls.getDeclaredMethod("setName", String.class);//获得了set方法
    Method m3 = cls.getDeclaredMethod("getName"); //get方法
    m2.invoke(obj, "张三");//如何用
    System.out.println(m3.invoke(obj)); // 张三
  }

  public static String initcap(String str) {
    return str.substring(0, 1).toUpperCase().concat(str.substring(1));
  }
}