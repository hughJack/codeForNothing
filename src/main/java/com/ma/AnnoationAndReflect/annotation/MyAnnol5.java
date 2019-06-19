package com.ma.AnnoationAndReflect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class MyAnnol5 {
  public static void main(String[] args) {
    boolean b = MyAnnoLTest4.class.isAnnotationPresent(MyAnnol1.class);
    System.out.println(b);
    // false
    // TestAnno2类上有@MyAnno1注解，但运行后不能获得，
    // 因为每一个自定义注解，需要使用JDK提供的元注解进行修饰才可以真正的使用。
    // 	@Retention
    //    	RetentionPolicy.SOURCE   注解只能存在源码中，字节码class没有
    //    	RetentionPolicy.CLASS    注解只能存在源码和字节码中
    //    	RetentionPolicy.RUNTIME  注解存在源码、字节码、内存（运行时）
    // 	@Target 用于确定被修饰的自定义注解使用位置
    //    	ElementType.TYPE 修饰类、接口
    //    	ElementType.CONSTRUCTOR  修饰构造
    //    	ElementType.METHOD 修饰方法
    //    	ElementType.FIELD 修饰字段

  }

}
