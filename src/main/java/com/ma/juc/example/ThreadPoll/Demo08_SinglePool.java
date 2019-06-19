/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.juc.example.ThreadPoll;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;

/*
6种线程池 :
 * 描述 : 保证任务是按照前后顺序执行的(执行过程类似单线程)
 *
 */
@Slf4j
public class Demo08_SinglePool {

  public static void main(String[] args) {
    ExecutorService service = Executors.newSingleThreadExecutor();

    for (int i = 0; i < 5; i++) {
      final int finalI = i;
      service.execute(()->{
        log.info("value = {}",finalI);
      });
    }
    service.shutdown();
  }
}
