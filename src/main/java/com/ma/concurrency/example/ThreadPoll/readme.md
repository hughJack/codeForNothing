 # 线程池的好处
 - 重复使用存在的线程,减少线程对象的创建/消亡的开销,性能更好
 - 有效的控制**最大并发**的线程数, 提高系统资源的利用率, 同时可以避免过多的资源竞争,避免阻塞
 - 提供定时执行? / 定期执行? / 单线程 / 并发数控制等高级功能
# 线程池创建的实现类 `ThreadPoolExecutor`
## `ThreadPoolExecutor`创建线程池的核心参数
 - corePoolSize   : 
 - maximumPoolSize: 
 - keepAliveTime + TimeUnit.xxxxx : 线程存活时间
 - workQueue : 阻塞队列,存储等待执行的任务,很重要,会对线程池运行过程产生重大影响
               如果该队列满了,那么根据其他参数处理新的请求
 - ThreadFactory: 线程工厂
 - RejectHandle : 拒绝处理任务时的策略,(抛弃 / 等待)
## 线程池的状态
 Running Stop 
## 线程提交和线程池关闭的方法
- execute()    submit()    
- shutdown()   shutdownNow()
## 线程池的监控方法(图表展示?)
- getTaskCount()
- getCompletedTaskCount()
- getPoolSize()
- getActiveCount()
- 也可以直接打印线程池 的对象 获取上述几个参数
 
# 线程池基础
 * 主要分析 ExecutorService 的实现类,  的使用
 * 线程池的基础类:
 *   00 Executor : 执行任务的
 *   01 ExecutorService : 提交任务的
 *   02 Runnable == Callable
 *   03 Executors : 操作上述的工具类 , 工厂类
 *   04 ThreadPool
 *   05 Future

# 4种线程池
 * fixed :
 * cached : 
 * scheduled : 指定执行的顺序
 * single : 指定执行的顺序
# 2种线程池()
 * WorkStealing 
 * ForkJoin
 *    底层实现 : ThreadPoolExecutor

 * 扩展流操作:
 *   ParallelStream
