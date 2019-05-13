/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.AnnoationAndReflect;

public class Person {

  public static void main(String[] args)
      throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    // 方式一：主要的使用方法
    Class<?> clazz = Class.forName("com.ma.AnnoationAndReflect.Person");
    // 方式二：使用“类.class”取得，在日后学习Hibernate开发的时候使用
    Class<Person> personClass = Person.class;
    System.out.println(clazz.getName());  //
    Object instance = clazz.newInstance();// 实例化一个对象
    System.out.println(instance); //

  }

  private String name;
  private int age;

  @Override
  public String toString() {
    return "Person{" +
        "name='" + name + '\'' +
        ", age=" + age +
        '}';
  }
}

