// package com.ma.pattern.demo;
//
// class ParametersDO{
//
//     private static volatile boolean initialize = false;
//
//     private String accessKey;
//
//     private String secretKey;
//
//     private String consumerId;
//
//     private String ONSAddr;
//
//     private String topic;
//
//     private ParametersDO() {
//
//         synchronized (ParametersDO.class) {
//             if (!initialize) {
//                 this.accessKey = BundleUtil.getResult("mq.accesskey");
//                 this.consumerId = BundleUtil.getResult("mq.public.consumer.id");
//                 this.ONSAddr = BundleUtil.getResult("mq.ons.addr");
//                 this.topic = BundleUtil.getResult("mq.public.topic");
//                 this.secretKey = BundleUtil.getResult("mq.secretkey");
//                 initialize = !initialize;
//             } else {
//                 throw new RuntimeException("ParametersDO单例已被破坏");
//             }
//
//         }
//
//     }
//
//     static ParametersDO getInstance() {
//         return ListenerHolder.INSTANCE;
//     }
//
//     private static class ListenerHolder{
//         private static final ParametersDO INSTANCE = new ParametersDO();
//     }
//
//
//     final String getAccessKey() {
//         return accessKey;
//     }
//
//     final String getSecretKey() {
//         return secretKey;
//     }
//
//     final String getConsumerId() {
//         return consumerId;
//     }
//
//     final String getONSAddr() {
//         return ONSAddr;
//     }
//
//     final String getTopic() {
//         return topic;
//     }
//
// }