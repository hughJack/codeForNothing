// package com.ma.pattern.demo;
//
// /**
//  * @author nicky_chin [shuilianpiying@163.com]
//  * @since --created on 2018/6/26 at 14:42
//  * 责任链抽象类
//  */
// // 抽象处理者
// public abstract class AbstractMessageHandler extends AbstractCommonListener {
//
//     /**
//      * 下一个责任链成员
//      */
//     protected AbstractMessageHandler nextHandler;
//
//     public AbstractMessageHandler getNextHandler() {
//         return nextHandler;
//     }
//
//     public void setNextHandler(AbstractMessageHandler nextHandler) {
//         this.nextHandler = nextHandler;
//     }
//
//     /**
//      * 处理传递过来的tag
//      * @param message 表达式
//      * @return T
//      */
//     public abstract T handleMessage(R message);
//
// }