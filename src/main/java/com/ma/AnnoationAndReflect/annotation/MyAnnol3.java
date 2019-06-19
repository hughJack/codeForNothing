package com.ma.AnnoationAndReflect.annotation;

public @interface MyAnnol3 {
  int age() default 1;

  String password();

  Class clazz();

  MyAnnol2 myAnno(); // 注解

  Color color(); // 枚举

  String[] arrs();
}

enum Color {
  BLUE, RED;
}
