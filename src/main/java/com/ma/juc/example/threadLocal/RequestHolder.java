package com.ma.juc.example.threadLocal;

// 希望绑定到线程中的信息
public class RequestHolder {
    private final static ThreadLocal<Long> requestHolder  = new ThreadLocal<>();

    public static void add(Long id){
        requestHolder.set(id);
    }
    public static Long getId(){
        return requestHolder.get();
    }
    public static void remove(){
        requestHolder.remove();
    }

}
