package com.ma.concurrency.example.publlish;

import com.ma.concurrency.annoations.NotRecommend;
import com.ma.concurrency.annoations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NotThreadSafe
@NotRecommend
// 演示对象逸出(escape), 这和Java并发编程的线程安全性就很大的关系。
// 对象构建未完成之前不可以被发布  this  不能被看到
public class Escape {

  private int thisCanBeEscape = 0;

    public Escape(){
        new InnerClass();// 会启动一个新的线程?
    }

    // 解决方案:使用工厂方法和私有构造函数,完成对象的创建和监听器的注册
    private class InnerClass{
        public InnerClass(){
        // 怎么导致的逸出 ?
          log.info("{}",Escape.this.thisCanBeEscape);
        }
    }

    public static void main(String[] args) {
        new Escape();
    }
}
