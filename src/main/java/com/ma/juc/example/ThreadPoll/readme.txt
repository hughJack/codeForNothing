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

#############################################
最佳实践
FixedThreadPool 和 CachedThreadPool 两者对高负载的应用都不是特别友好。

CachedThreadPool 要比 FixedThreadPool 危险很多。

如果应用要求高负载、低延迟，最好不要选择以上两种线程池：

任务队列的无边界：会导致内存溢出以及高延迟
长时间运行会导致 CachedThreadPool 在线程创建上失控
因为两者都不是特别友好，所以推荐使用 ThreadPoolExecutor ，它提供了很多参数可以进行细粒度的控制。

将任务队列设置成有边界的队列
使用合适的 RejectionHandler - 自定义的 RejectionHandler 或 JDK 提供的默认 handler 。
如果在任务完成前后需要执行某些操作，可以重载
 beforeExecute(Thread, Runnable)
 afterExecute(Runnable, Throwable)
重载 ThreadFactory ，如果有线程定制化的需求
在运行时动态控制线程池的大小（Dynamic Thread Pool）


######################