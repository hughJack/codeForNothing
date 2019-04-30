/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.concurrency.example.ThreadPoll;

import java.util.concurrent.Executor;
import lombok.extern.slf4j.Slf4j;

/*  
 * 描述 : 稍微了解一下 Executor
 *
 */
@Slf4j
public class Demo00_Executor implements Executor{

  @Override
  public void execute(Runnable command) {
    command.run();
  }

  public static void main(String[] args) {
    new Demo00_Executor().execute(()->{
      System.out.println("hello from executor");
    });
  }
}
