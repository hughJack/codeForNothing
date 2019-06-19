/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.juc.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/*
 * 描述 : 用户在支付宝拥有多种支付方式("余额", "红包", "余额宝", "银行卡")
 *   每种支付方式通过调用远程服务获取可用性,
 *   在外部资源环境不变的情况下,请设计程序以最短的相应时间获取尽可能多的可用支付方式列表
 */
public class PayDemo {

  // 测试代码
  public static void main(String[] args) {
    List<String> allPaymentList = Arrays.asList("余额", "红包", "余额宝", "银行卡");
    long start = System.currentTimeMillis();
    List<ConsultResult> list = filterDisablePayment(allPaymentList);
    double seconds = System.currentTimeMillis();
    System.out.println("总共耗时:" + seconds + "s");
    for (ConsultResult paymentType : list) {
      System.out.println(paymentType);
    }
  }

  // 类似验活
  public static List<ConsultResult> filterDisablePayment(List<String> allPaymentList) {
    // 建立线程池
    List<ConsultResult> results = new ArrayList<>();
    ExecutorService executorService = Executors.newFixedThreadPool(allPaymentList.size());
    // 添加任务,任务实现callable接口
    List<Future<ConsultResult>> tasks = new ArrayList<>();
    for (String paymentType : allPaymentList) {
      tasks.add(executorService.submit(new RemoteCallable(paymentType)));
    }
    // 关闭线程池 ? ?
    executorService.shutdown();
    for (Future<ConsultResult> future : tasks) {
      try {
        //超时异常处理机制
        ConsultResult result = future.get(2, TimeUnit.SECONDS);
        if (result != null) {
          results.add(result);
        }
      } catch (Exception e) {
        // 异常处理
        e.printStackTrace();
      }
    }
    return results;
  }

  static class Payment {

    public static Boolean PaymentIsEnabled(String paymentType) {
      //模拟远程服务调用所用时间
      Random random = new Random();
      try {
        // 睡眠一定的时间
        Thread.sleep(random.nextInt(5));
        return random.nextBoolean();
      } catch (Exception e) {
        e.printStackTrace();
      }
      return false;
    }
  }

  static class RemoteCallable implements Callable<ConsultResult> {

    private String paymentType;

    public RemoteCallable(String paymentType) {
      this.paymentType = paymentType;
    }

    @Override
    public ConsultResult call() {
      return new ConsultResult(Payment.PaymentIsEnabled(paymentType), "0000");
    }

    // 多余的方法
    public String getPaymentType() {
      return paymentType;
    }

    public void setPaymentType(String paymentType) {
      this.paymentType = paymentType;
    }

  }

  static class ConsultResult {

    // 咨询结果是否可用
    private boolean isEnabled;
    // 错误码
    private String errorCode;

    public ConsultResult(boolean isEnabled, String errorCode) {
      this.isEnabled = isEnabled;
      this.errorCode = errorCode;
    }

    public boolean isEnabled() {
      return isEnabled;
    }
  }
}




