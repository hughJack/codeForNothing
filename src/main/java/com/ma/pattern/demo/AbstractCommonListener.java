// package com.ma.pattern.demo;
// // 1.抽象公共监听器 ，主要用到了单例模式获取常量
// public abstract class AbstractCommonListener {
//
//   private ParametersDO parametersDO;
//
//   protected AbstractCommonListener() {
//     //获取单例对象
//     this.parametersDO = ParametersDO.getInstance();
//   }
//
//   public final String getAccessKey() {
//     return parametersDO.getAccessKey();
//   }
//
//   public final String getSecretKey() {
//     return parametersDO.getSecretKey();
//   }
//
//   public final String getConsumerId() {
//     return parametersDO.getConsumerId();
//   }
//
//   public final String getONSAddr() {
//     return parametersDO.getONSAddr();
//   }
//
//   public final String getTopic() {
//     return parametersDO.getTopic();
//   }
//
//
// }