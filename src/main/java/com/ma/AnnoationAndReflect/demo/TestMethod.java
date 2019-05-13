package com.ma.AnnoationAndReflect.demo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

class Person {

  private String name;
  int age = 99;

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

public class TestMethod {

  public static <Mehtod> void main(String[] args) throws Exception {
    Class<?> clazz = Class.forName("com.ma.AnnoationAndReflect.demo.Person"); // 取得Class对象
    Object obj = clazz.newInstance(); // 实例化对象，没有向Person转型
    String name = "name"; // 要调用类之中的属性
    Method setName = clazz.getMethod("set" + initcap(name), String.class);// setName()
    Method getName = clazz.getMethod("get" + initcap(name));// getName()
    setName.invoke(obj, "张三"); // 等价于：Person对象.setName("张三")
    System.out.println(getName.invoke(obj));// 等价于：Person对象.getName() 输出张三
    /*自己测试-------------------------------------------------*/
    Person p = (Person) obj;
    p.say();//输出123456789
    Method m = clazz.getDeclaredMethod("say");
    m.invoke(obj);//输出123456789
    /*自己测试-------------------------------------------------*/
    Method m2 = clazz.getDeclaredMethod("setName", String.class);//获得了set方法
    Method m3 = clazz.getDeclaredMethod("getName"); //get方法
    Object invoke = m2.invoke(obj, "张三");//如何用
    System.out.println(m3.invoke(obj)); // 张三
    //
    Field nameField = clazz.getDeclaredField("name"); // 获得name属性
    nameField.setAccessible(true); // 如何用解除封装了
    nameField.set(obj, "张三"); // Person对象.name = "张三"
    System.out.println(nameField.get(obj)); // Person对象.name
    /*如果里面有get方法，可以通过obj.getName（）获得这个属性*/
    /*证明通过反射获得类的对象和以前new出来的是一样的*/
    Person pp = (Person) obj;
    pp.age = 99;
    System.out.println(pp.age);
    pp.say();
  }

  public static String initcap(String str) {
    return str.substring(0, 1).toUpperCase().concat(str.substring(1));
  }
}