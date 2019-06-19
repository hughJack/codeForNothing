package com.ma.juc.example.Future;

public class Discount {
  public enum Code {
    NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);

    private final int percentage;

    Code(int percentage) {
      this.percentage = percentage;
    }
  }

  public static String applyDiscount(Quote quote) {
    return quote.getShopName() + "  price  is  " + Discount.apply(quote.getPrice(), quote.getCode());
  }


  public static double apply(double price, Code code) {
    delay();
    return price * (100 - code.percentage) / 100;
  }

  public static void delay() {
    try {
      Thread.sleep(1000L);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

//  public static List<String> findPrice3(String product) {
//    return shops.stream()
//        .map(shop -> shop.getPrice2(product))  //获取原始报价
//        .map(Quote::parse)  //解析报价字符串
//        .map(Discount::applyDiscount)  //调用折扣服务应用报价折扣
//        .collect(toList());
//  }
