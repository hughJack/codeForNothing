package com.ma.concurrency.example.singleton;

import com.ma.concurrency.annoations.NotThreadSafe;

/**
 * 懒汉模式 (线程不安全)
 * 单例的实例在第一次使用的时候 初始化
 */
@NotThreadSafe
public class SingletonExample1 {
    //单例对象
    private static SingletonExample1 instance = null;
    //私有的构造函数
    private SingletonExample1(){

    }
    public static SingletonExample1 getInstance(){
        if(instance == null){
            instance = new SingletonExample1();
        }
        return instance;
    }

}
