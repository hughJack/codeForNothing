package com.ma.concurrency.example.singleton;

import com.ma.concurrency.annoations.ThreadSafe;

/**
 * 静态内部类
 */
@ThreadSafe
public class SingletonExample5 {
    //单例对象
    private static SingletonExample5 instance = new SingletonExample5();
    //私有的构造函数
    private SingletonExample5(){
    }
    public static class SingletonHolder{
        private final static SingletonExample5 instance = new SingletonExample5();
    }
    public static SingletonExample5 getInstance(){
        // 只有当我们  执行到这里的时候,才会加载Class 对象
        return SingletonHolder.instance;
    }
}
