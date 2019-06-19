package com.ma.juc.example.singleton;

import com.ma.juc.annoations.Recommend;
import com.ma.juc.annoations.ThreadSafe;

/**
 * 懒汉模式 --->  双重同步锁安全机制
 * , 指令重排导致线程安全问题
 * 双重检查机制(使用volatile保证不被指令重排序),那么就变成线程安全的了
 */
@ThreadSafe
@Recommend
public class SingletonExample1_3 {
    //单例对象
    private volatile static SingletonExample1_3 instance = null;
    //私有的构造函数
    private SingletonExample1_3(){
    }
//    new Instance() 的执行过程
    //1、memory = allocate() 分配对象内存空间
    //2、ctoInstance() 初始化对象
    //3、instance = memory 设置instance指向刚分配的内存

    //JVM和CPU优化，发生了指令重排序  1 -> 3 -> 2
    //1、memory = allocate() 分配对象内存空间
    //3、instance = memory 设置instance指向刚分配的内存
    //2、ctoInstance() 初始化对象

// 单例对象 volatile + 双重检测机制  ->  禁止指令重排
    public static SingletonExample1_3 getInstance(){
        //使用双重检查机制
        if(instance == null){
            // 锁类,双重检测机制
            synchronized (SingletonExample1_3.class){
                if(instance == null){
                    instance = new SingletonExample1_3();
                }
            }
        }
        return instance;
    }
}
