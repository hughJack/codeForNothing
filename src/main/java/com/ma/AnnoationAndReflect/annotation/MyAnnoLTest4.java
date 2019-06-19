package com.ma.AnnoationAndReflect.annotation;

@MyAnnol1
@MyAnnol2(username = "rose")
@MyAnnol3(
    age = 18,
    password = "1234",
    clazz = String.class,
    myAnno = @MyAnnol2,
    color = Color.RED,
    arrs = {"hello", "world"}
)
public class MyAnnoLTest4 {
// 	如果给类、方法等添加注解，如果需要获得注解上设置的数据，那么我们就必须对注解进行解析，
//    JDK提供java.lang.reflect.AnnotatedElement接口允许在运行时通过反射获得注解。
}

