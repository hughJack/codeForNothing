package com.ma.concurrency.example.singleton;

import com.ma.concurrency.annoations.Recommend;
import com.ma.concurrency.annoations.ThreadSafe;

/**
 * 枚举(最安全的) !!!!
 * 使用的时候才会初始化一个对象
 */
@ThreadSafe
@Recommend
public class SingletonExample6 {
    //私有的构造函数
    private SingletonExample6(){
    }
    public static SingletonExample6 getInstance(){
        return Single.INSTANCE.getInstance();
    }
    private  enum Single{
        INSTANCE;
        private SingletonExample6 singleton;
        // jvm 保证这个方法只调用一次
        Single(){
            singleton = new SingletonExample6();
        }
        public SingletonExample6 getInstance(){
            return singleton;
        }
    }
}
