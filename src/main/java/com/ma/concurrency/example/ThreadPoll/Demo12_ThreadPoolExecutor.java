/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.concurrency.example.ThreadPoll;

import lombok.extern.slf4j.Slf4j;

/*
 * 描述 : 除FockJoinThreadPoll, 大多数线程池的实现,都是通过 ThreadPoolExecutor 实现的
 * 区别是: 传入的参数不同 (起始线程数量, 最大线程池数量, 存活时间, 存储队列)
 *
 * 使用的都是BockingQueue及其实现类
 */
@Slf4j
public class Demo12_ThreadPoolExecutor {

  public static void main(String[] args) {

  }

}
