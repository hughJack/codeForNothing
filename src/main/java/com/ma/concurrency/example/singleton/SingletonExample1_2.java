package com.ma.concurrency.example.singleton;

import com.ma.concurrency.annoations.NotRecommend;
import com.ma.concurrency.annoations.ThreadSafe;

/**
 * 懒汉模式（线程安全）
 */
@ThreadSafe
@NotRecommend
public class SingletonExample1_2 {
    //单例对象
    private static SingletonExample1_2 instance = null;
    //私有的构造函数
    private SingletonExample1_2(){

    }
    //  不推荐,同一时间内只允许单个线程,会带来性能上的开销
    public static synchronized SingletonExample1_2 getInstance(){
        if(instance == null){
            instance = new SingletonExample1_2();
        }
        return instance;
    }
}
