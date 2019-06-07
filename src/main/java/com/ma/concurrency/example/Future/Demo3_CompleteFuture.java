package com.ma.concurrency.example.Future;

import org.apache.commons.lang3.RandomUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
// 并行操作 Streams 和CompletableFutures 比较 
// 1. 如果有大量计算的操作而没有I/O 操作（包括连接互联网），那么使用异步的 Streams 可以得到最好的性能。
// 2. 相反如果有很多IO操作， 使用CompletableFutures可以得到更好的编弹性。

public class Demo3_CompleteFuture {
  private static List<Shop> shops = Arrays.asList(new Shop("shop1"),
      new Shop("shop2"),
      new Shop("shop3"),
      new Shop("shop4"),
      new Shop("shop5"),
      new Shop("shop6"),
      new Shop("shop7"),
      new Shop("shop8")
  );

  // http://www.jcodecraeer.com/a/chengxusheji/java/2017/1226/9011.html
  // 分步迭代
  // 在map方法中，要完类通过supplyAsync方法，以异步方式提交了一个将整数转化为字符串的任务，
  // 并返回相应的要完对象，然后由collect方法收集成一个List<CompletableFuture<String>>
  // ============================
  // 没有考虑计算出错的问题，一旦在Lambda表达式中抛出了异常，后果便不堪设想
  // 如果任务执行过程中产生了异常会怎样呢？
  //非常不幸，这种情况下你会得到一个相当糟糕的结果：异常会被限制在执行任务的线程的范围内，最终会杀死该线程，而这会导致等待 get 方法返回结果的线程永久地被阻塞。
  public static void main(String[] args) {
    //findPrices("hello");

    first();

    second();

  }

  public static List<String> findPrices(String product) {
    // 多线程不也是浪费啊，具体开多少才合适，就要涉及到任务密集类型与CPU线程相关的内容
    // 1. IO密集计算 CPU 核数 * [ 1 +（I/O 耗时 / Cpu 耗时）
    // 2. Cpu密集型 这个就很简单了 Cpu的核数 = 线程数就行
    Executor executor = Executors.newCachedThreadPool();
    // Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(),100));
    List<CompletableFuture<String>> priceFuture =
        shops.stream().map(
            shop -> CompletableFuture.supplyAsync(
                () -> String.format("%s price is %.2f ", shop.getName(), shop.getPice(product)), executor
            )
        ).collect(Collectors.toList());
    return priceFuture.stream().map(CompletableFuture::join).collect(Collectors.toList());
  }

  // 可级联的方法皆以then开头，很符合要完用于提交异步任务的本质
  // 1. thenApply方法会在任务执行完毕后再继续执行其他操作，消费之前的结果并产生新的结果，有一点像map类的方法
  // 2. thenCompose<意味着级联>方法跟thenApply功能类似，只是传入的表达式需要返回CompletionStage类型的值。
  //
  //    可以通过thenCompose方法将两个CompletableFuture结合在一起
  //    CompletionStage前面说过，是CompletableFuture除了Future外所实现的另一个接口，可以看做是它的养父。
  //    尽管在表达式的返回值上有区别，thenCompose方法的返回值却仍然和thenApply一样是单层泛型参数的CompletableFuture，而不会发生嵌套，
  //    返回一个CompletableFuture<CompletableFuture<U>>。这就很像flatMap类的方法。
  //    这两个比喻，已经被写在Javadoc中，这就说明，要完的设计思想与Stream、Optional、Collector等接口是很类似的，都体现了级联式函数式编程的思想。
  // 3.thenAccept，<响应 CompletableFuture 的 completion 事件>,
  //               <thenAccept是只会对计算结果进行"消费"而不会返回任何结果的方法>
  //
  //    该方法的参数与前两者不同，前两者都是传入的Function可以产生新的运算结果，
  //    而它则传入一个Consumer直接把之前算出来的结果给干掉了。尽管如此，为了和其他then族方法保持一致，免得级联在它这里掉队，
  //    它还是很诚实的返回一个CompletableFuture，
  //    注意这里的Void是开头大写的包装类，我们如果对它返回的空要完再进行join、get之类的求值操作就只能得到空空如也的null。
  public static List<String> findPrice4(String product) {
    Executor executor = Executors.newCachedThreadPool();
    List<CompletableFuture<String>> priceFuture =
        shops.stream()
            .map(shop -> CompletableFuture.supplyAsync( // 异步获取价格
                () -> shop.getPrice(product), executor))
            .map(future -> future.thenApply(Quote::parse)) // 获取到价格后对价格解析
            .map(future -> future.thenCompose(quote ->
                CompletableFuture.supplyAsync( // 另一个异步任务构造异步应用报价
                    () -> Discount.applyDiscount(quote),
                    executor)))
            .collect(toList());
    return priceFuture.stream()
        .map(CompletableFuture::join) //join 操作和get操作有相同的含义，等待所有异步操作的结果。
        .collect(toList());
  }

  // thenCombine：而有些情况下，两个要完并不存在依赖关系，二者可以同时进行，只需要在最后将他们的结果合并到一起，
//                这就需要用到thenCombine方法，传入一个BiFunction < 二元函数 >
// thenAccept很像，thenAccept方法吃掉了前面计算出来的值后就不再往外吐东西，
// thenAcceptBoth比它多了个Both就变本加厉，可以吃掉两个要完计算出来的值。
// 当然这话说得对于他们两个有失公正，既然设计了他们，就必然会有用处，
// Consumer和BiConsumer没有返回值，所以就只能通过在代码中产生副作用来对外部造成影响。
//  public static void paral(String[] args) {
//    List<CompletableFuture<Double>> priceFuture = shops.stream()
//        .map(shop -> CompletableFuture.supplyAsync( // 异步获取价格
//            () -> shop.getPrice(product), executor))
//        .map((Function<CompletableFuture<String>, Object>) future -> future.thenCombine(CompletableFuture.supplyAsync( // 异步获取折扣率
//            () -> Discount.getRate(), executor)
//            , (price, rate) -> price * rate)) // 将两个异步任务的结果合并
//        .collect(toList());
//  }
//---------------------

  private static void first() {
    CompletableFuture<String> futureA = CompletableFuture.
        supplyAsync(() -> "执行结果:" + (100 / 0))
        .thenApply(s -> "apply result:" + s)
        .whenComplete((s, e) -> {
          if (s != null) {
            System.out.println(s);//未执行
          }
          if (e == null) {
            System.out.println(s);//未执行
          } else {
            System.out.println(e.getMessage());//java.lang.ArithmeticException: / by zero
          }
        })
        .exceptionally(e -> {
          System.out.println("ex" + e.getMessage()); //ex:java.lang.ArithmeticException: / by zero
          return "futureA result: 100";
        });
    System.out.println(futureA.join());//futureA result: 100

// 代码先执行了exceptionally后执行whenComplete,可以发现,由于在exceptionally中对异常进行了处理,
// 并返回了默认值,
// whenComplete中接收到的结果是一个正常的结果,被exceptionally美化过的结果,这一点需要留意一下.
    CompletableFuture<String> futureB = CompletableFuture.
        supplyAsync(() -> "执行结果:" + (100 / 0))
        .thenApply(s -> "apply result:" + s)
        .exceptionally(e -> {
          System.out.println("ex:" + e.getMessage()); //ex:java.lang.ArithmeticException: / by zero
          return "futureB result: 100";
        })
        .whenComplete((s, e) -> {
          if (e == null) {
            System.out.println(s);//futureB result: 100
          } else {
            System.out.println(e.getMessage());//未执行
          }
        });
    System.out.println(futureB.join());//futureB result: 100

    CompletableFuture<String> futureC = CompletableFuture.
        supplyAsync(() -> "执行结果:" + (100 / 0))
        .thenApply(s -> "apply result:" + s)
        .exceptionally(e -> {
          System.out.println("ex:" + e.getMessage()); //java.lang.ArithmeticException: / by zero
          return "futureC result: 100";
        })
        .handle((s, e) -> {
          if (e == null) {
            System.out.println(s);//futureC result: 100
          } else {
            System.out.println(e.getMessage());//未执行
          }
          return "handle result:" + (s == null ? "500" : s);
        });
    System.out.println(futureC.join());//handle result:futureC result: 100
  }


  public static void second() {
    ExecutorService executorService = Executors.newFixedThreadPool(4);

    long start = System.currentTimeMillis();
    CompletableFuture<String> futureA = CompletableFuture.supplyAsync(() -> {
      try {
        Thread.sleep(1000 + RandomUtils.nextInt(0, 1000));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return "商品详情";
    }, executorService);

    CompletableFuture<String> futureB = CompletableFuture.supplyAsync(() -> {
      try {
        Thread.sleep(RandomUtils.nextInt(0, 1000));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return "卖家信息";
    }, executorService);

    CompletableFuture<String> futureC = CompletableFuture.supplyAsync(() -> {
      try {
        Thread.sleep(RandomUtils.nextInt(0, 1000));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return "库存信息";
    }, executorService);

    CompletableFuture<String> futureD = CompletableFuture.supplyAsync(() -> {
      try {
        Thread.sleep(RandomUtils.nextInt(0, 1000));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return "订单信息";
    }, executorService);

    CompletableFuture<Void> allFuture = CompletableFuture.allOf(futureA, futureB, futureC, futureD);
    allFuture.join();

    System.out.println(futureA.join() + futureB.join() + futureC.join() + futureD.join());
    System.out.println("总耗时:" + (System.currentTimeMillis() - start));
  }
}
