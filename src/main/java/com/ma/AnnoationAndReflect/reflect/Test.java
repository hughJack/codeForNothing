package com.ma.AnnoationAndReflect.reflect;

import org.springframework.scheduling.annotation.Scheduled;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.security.SecureRandom;
import java.util.Properties;
import java.util.Set;

public class Test {
  public static void main(String[] args) throws FileNotFoundException {


    //配置文件data.properties
    //uid=001
    //username=jack
    //password=123

  }

  /*
   * 读取配置文件(IO+集合),键值对存储到集合中
   * 键获取集合中的所有值
   * 反射,创建出User类的对象,调用方法setXX,数据存储
   */
  // 读取配置文件
  public void test() throws Exception {
    //
    Properties pro = new Properties();
    FileInputStream fis = new FileInputStream("data.properties");
    pro.load(fis);
    fis.close();
    // ==================
    // 读取 property 配置文件，对象赋值
    Class clazz = Class.forName("com.ma.AnnoationAndReflect.reflect.User");
    Object obj = clazz.newInstance();
    Set<String> keys = pro.stringPropertyNames();
    for (String key : keys) {
      String methodName = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
      Method method = clazz.getMethod(methodName, String.class);
      method.invoke(obj, pro.getProperty(key));
    }
    //
    System.out.println(obj);
  }

  //@Scheduled(cron = "0 0 0 1,16 * ?")
  //public void generateTokenSecret() {
  //  // 生成随机数
  //  SecureRandom secRandom = new SecureRandom();
  //  byte[] result = new byte[8];
  //  secRandom.nextBytes(result);
  //  String hexString = Hex.encodeHexString(result);
  //  // 更新prpdriskConfig表token_secret
  //  if (hexString != null && !"".equals(hexString) && hexString.length() == 16) {
  //    PrpDriskConfigKey prpDriskConfigKey = new PrpDriskConfigKey();
  //    PrpDriskConfig prpDriskConfig = new PrpDriskConfig();
  //    prpDriskConfigKey.setComCode("00000000");
  //    prpDriskConfigKey.setConfigCode("token_secret");
  //    prpDriskConfigKey.setRiskCode("PUB");
  //    prpDriskConfig.setPrpDriskConfigKey(prpDriskConfigKey);
  //    // 服务器秘钥需要15位
  //    String secret = hexString.substring(0, 15);
  //    prpDriskConfig.setConfigValue(secret);
  //    prpDriskConfigService.updateConfigValue(prpDriskConfig);
  //    redisCache.putCache("ZNCD.token_secret", secret);
  //  }
  //}
}
