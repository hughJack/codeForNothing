package com.ma.juc.example.singleton;

import com.ma.juc.annoations.ThreadSafe;

/**
 * 饿汉模式
 * 1.实例化的时候没有包含过多的处理(不会导致性能问题)
 * 2.实例化之后肯定会被使用(不会造成资源的浪费)
 */
@ThreadSafe
public class SingletonExample2 {
    //单例对象
    private static SingletonExample2 instance = new SingletonExample2();
    //私有的构造函数
    private SingletonExample2(){
    }
    public static SingletonExample2 getInstance(){
        return instance;
    }
}
