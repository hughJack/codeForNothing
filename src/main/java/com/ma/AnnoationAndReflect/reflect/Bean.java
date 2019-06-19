package com.ma.AnnoationAndReflect.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Bean {
  private String name;
  private String id;
  private String description;

  public Bean() {
    System.out.println("午餐构造函数");
  }

  public Bean(String id, String classname) {
    System.out.println("大餐构造函数 : " + id + " , " + classname);
    this.id = id;
    this.name = classname;
  }

  private Bean(String name) {
    this.name = name;
  }

  public static void main(String[] args) throws Exception {
    // 1. 构造函数，构建对象
    ////无参构造 , 并实例化
    //noArgConstruct();
    ////有参构造 , 并实例化
    //withArgConstruct();
    ////  public Method
    //simpleConstructor();
    //私有构造, 暴力访问
    //forcePrivateConcrutor();

    // 2.方法的访问
    //withArgMethod();
    //forcePrivateMethod();

    // 3.字段的复制
    forcePrivateFiled();
  }

  private static void forcePrivateFiled() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException {
    Class<?> clazz = Class.forName("com.ma.AnnoationAndReflect.reflect.Bean");
    Object bean = clazz.newInstance();
    Field field = clazz.getDeclaredField("description");
    // 1.暴力访问
    field.setAccessible(true);
    // 2.
    field.set(bean,"Bean的类的名称");
    // 3.
    Object o = field.get(bean);
    System.out.println(o);
  }

  public static void forcePrivateMethod() throws Exception {
    //1 获得Class
    Class clazz = Class.forName("com.ma.AnnoationAndReflect.reflect.Bean");
    //2 获得构造 -- 两个字符串形参 -- Bean(String id, String className)
    Constructor constructor = clazz.getConstructor(String.class, String.class);
    //3 实例对象，两个字符串实参
    Object bean = constructor.newInstance("ArrayListId", "java.util.ArrayList");
    //3 获得方法 -- 私有方法 -- private String show()
    // * getMethod(方法名,形成列表) 将抛异常java.lang.NoSuchMethodException
    // * getDeclaredMethod(方法名,形成列表) 可以获得私有构造
    Method showMethod = clazz.getDeclaredMethod("show");
    //暴力访问
    showMethod.setAccessible(true);
    //4 执行方法，没有实参
    Object getReturnObj = showMethod.invoke(bean);
    System.out.println("show方法返回值：" + getReturnObj);
    /* 运行结果：
     *	有参构造：ArrayListId，java.util.ArrayList
     *	私有方法执行
     *	show方法返回值：Bean[ArrayListId, java.util.ArrayList]
     */
  }

  private static void withArgMethod() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
    Class<Bean> clazz = Bean.class;
    Bean bean = clazz.newInstance();
    Method method = clazz.getMethod("setId", String.class);
    Object setReturnObj = method.invoke(bean, "我是参数");
    System.out.println(" set方法没有返回值，  set方法返回值：" + setReturnObj);
    System.out.println(bean);
    /* 运行结果：
     * 	无参构造
     * 	setId方法执行：我是参数
     * 	set方法返回值：null
     */
    System.out.println("---------------");
    // 4 操作getId方法 (巩固)
    // 3.1 获得方法，没有形参
    Method getMethod = clazz.getMethod("getId");
    // 3.2 执行方法，没有实参
    Object getReturnObj = getMethod.invoke(bean);
    System.out.println("get方法返回值：" + getReturnObj);
    /* 运行结果：
     *	getId方法执行
     *	get方法返回值：我是参数
     */
  }

  private static void forcePrivateConcrutor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
    //1获得Class
    Class beanClass = Bean.class;
    //2获得构造 -- 两个字符串形参 -- Bean(String id, String className)
    // * getConstructor() 将抛异常java.lang.NoSuchMethodException
    // * getDeclaredConstructor可以获得私有构造
    Constructor constructor = beanClass.getDeclaredConstructor(String.class);
    //暴力访问
    constructor.setAccessible(true);
    //3 实例对象，两个字符串实参
    Object bean = constructor.newInstance("userId");
    System.out.println(bean);
    /* 结果：
     * 有参构造：userId
     * com.itheima_00_Bean.Bean@8c5488
     */
  }

  private static void simpleConstructor() throws IllegalAccessException, InstantiationException {
    //无参构造 , 简化版
    //1获得Class
    Class beanClass = Bean.class;
    //2 直接获得实例对象，两个字符串实参
    Object bean = beanClass.newInstance();
    System.out.println(bean);
    /* 结果：
     * 无参构造
     * com.itheima_00_Bean.Bean@101acff
     */
  }

  private static void noArgConstruct() throws NoSuchMethodException, InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
    //1 获得Class
    Class clazz = Bean.class;
    //2 获得构造 -- 没有形参
    // 	getConstructor() 使用该方法将无法获得私有方法，程序运行抛异常
    Constructor constructor = clazz.getConstructor();
    //3 实例对象，没有实参
    Object bean = constructor.newInstance();
    System.out.println(bean);
  }

  public static void withArgConstruct() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
    //1获得Class
    Class clazz = Bean.class;
    //2获得构造 -- 两个字符串形参 -- Bean(String id, String className)
    // 	getConstructor() 使用该方法将无法获得私有方法，程序运行抛异常
    Constructor constructor = clazz.getConstructor(String.class, String.class);
    //3 实例对象，两个字符串实参
    Object bean = constructor.newInstance("ArrayListId", "java.util.ArrayList");
    System.out.println(bean);
  }
}
