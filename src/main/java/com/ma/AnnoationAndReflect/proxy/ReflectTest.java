package com.ma.AnnoationAndReflect.proxy;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class ReflectTest {

  //@Test
  //public void test_2() {
  //  List<String> list = new ArrayList<>();
  //  list.add("123");
  //  System.out.println(list.size());
  //  list = myProxy(list);
  //  list.set(0, "1");
  //  System.out.println(list.size());
  //}
  //
  //public static List<String> myProxy(List<String> list) {
  //
  //  List<String> listProxy = (List) Proxy.newProxyInstance(
  //      ReflectTest.class.getClassLoader(), list.getClass()
  //          .getInterfaces(), new MyPro(list));
  //  return listProxy;
  //}


  @Test
  public void test_2() {
    List<String> list = new ArrayList<String>();
    list.add("123");
    System.out.println(list.size());
    list = myProxy(list);
    list.set(0, "1");
    System.out.println(list.size());
  }

  public static List<String> myProxy(final List<String> list) {

    InvocationHandler handler = new InvocationHandler() {
      @Override
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("add".equals(method.getName()))
          throw new UnsupportedOperationException("add NO");
        if ("set".equals(method.getName()))
          throw new UnsupportedOperationException("set NO");
        if ("remove".equals(method.getName()))
          throw new UnsupportedOperationException("r NO");
        return method.invoke(list, args);
      }
    };
    List<String> list2 = (List) Proxy.newProxyInstance(
        ReflectTest.class.getClassLoader(),
        list.getClass().getInterfaces(),
        handler);
    return list2;
  }

}
