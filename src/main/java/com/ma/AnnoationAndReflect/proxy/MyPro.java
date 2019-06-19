package com.ma.AnnoationAndReflect.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

public class MyPro implements InvocationHandler {
  private List<String> list;

  public MyPro(List<String> list) {
    this.list = list;
  }

  public Object invoke(Object porxy, Method method, Object[] arge) throws Exception {
    if ("add".equals(method.getName()))
      throw new UnsupportedOperationException("add NO");
    if ("set".equals(method.getName()))
      throw new UnsupportedOperationException("set NO");
    if ("remove".equals(method.getName()))
      throw new UnsupportedOperationException("remove NO");
    return method.invoke(list, arge);
  }
}
