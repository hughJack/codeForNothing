// 阻塞队列 , 一般用于生产者/消费者的场景
// 阻塞的场景
//    1.满队列,  执行put
//    2.空队列,  执行take


// 容器之类的  就是面试用的
// 读源码的方式 !!! -----> spring 那一块的
// 并发队列 和 阻塞队列的 区别 ?
//    并发队列 是 加锁不阻塞
//    阻塞队列 是 阻塞
/*
 * 总结 :
 * Map/Set 的选择和使用
 *    1. 不需要考虑并发的时候
 *       HashMap
 *       TreeMap : 红黑树,排号顺序的
 *       LinkedHashMap
 *
 *    2.少量并发的时候,使用同步容器
 *       HashTable
 *       Collections.synchronizedXXX
 *
 *    3.高并发的时候,使用并发容器,
 *       3.1 并发容器
 *        ConcurrentHashMap    :HashMap
 *        ConcurrentSkipListMap:TreeMap : 需要顺序的
 * 队列(复杂一点的)
 * 1. 不需要考虑并发的时候
 *    ArrayList
 *    LinkedList
 * 2.低并发的时候
 *    Collections.syncchronizedXXX
 *    CopyOnWriteList : 读的特别多,写的特别少
 *
 * 3.高并发: Queue
 *  3.1.并发容器(非阻塞式的,)
 *      ConCurrentLinkedQueue // 没有ConCurrentArrayQueue,很难写一个通用的
 *  3.2.阻塞式队列
 *     BlockingQueue( [add/offer,poll/peek] ,[transfer,take])
 *         LinkedBQ:链表实现的 [put,take] 实现生产者和消费者
 *         ArrayBQ :数组实现的 [put,take] 实现生产者和消费者
 *         TransferQueue: 接收转发消息时.可以使用,不需要加到队列里面,直接给消费者(首先要有消费者)
 *         SynchronizedQueue: extends TransferQueue , 队列长度为 0
 * 4.执行定时任务,延迟任务
 *    DelayQueue : 自定义 那个先执行
 */