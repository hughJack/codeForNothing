/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.juc.example.ForkJoin;

import java.util.concurrent.RecursiveTask;
import lombok.extern.slf4j.Slf4j;

/*
 * 描述 : 很少使用 : : 使用双端队列 和 工作窃取算法
 * 局限 : 1.只能使用Fork和Join来做同步机制,可能会有竞争(任务列表只有一个任务的时候)
 *        2.不能用于IO操作
 *        3.任务不能抛出检查异常,必须通过必要的代码处理异常
 *  核心方法:
 *  1.ForkJoin pull: 管理工作线程
 *  2.ForkJoin task: 管理fork() join() 的机制
 *
 *  实现类:
 *  1. RecursiveTask : 有返回值
 *  2.RecursiveAction: 无返回值
 */
// 递归拆分任务

@Slf4j
public class ForkJoinTaskExample1 extends RecursiveTask<Integer>{

  @Override
  protected Integer compute() {
    return null;
  }
}
