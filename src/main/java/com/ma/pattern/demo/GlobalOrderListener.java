// package com.ma.pattern.demo;
//
// // 具体监听器 ，监听器主要用于MQ监听消费Topic
// public class GlobalOrderListener extends AbstractCommonListener implements MessageOrderListener {
//
//   @Override
//   // 正常情况下，我们会在 consume() 方法中区分tag来做不同业务的数据处理
//   public OrderAction consume(Message message, ConsumeOrderContext context) {
//
//     //新增处理消费tag 只需添加Handler
//     AbstractMessageHandler handler = HandlerFactory.getHandlerResponsibilityChain(
//         JpushOrderHandler.class,
//         DelayRemoveOrderHandler.class);
//     return handler.handleMessage(message);
//   }
// }