// package com.ma.pattern.demo;
//
// import org.springframework.beans.BeanUtils;
// import org.springframework.util.Assert;
//
// public class RoborderConsumerAdapter{
//
//     private OrderConsumer orderConsumer;
//
//     public RoborderConsumerAdapter(OrderConsumer orderConsumer) {
//         Assert.notNull(orderConsumer, "orderConsumer is null");
//         this.orderConsumer = orderConsumer;
//     }
//
//     /**
//      * 消费
//      */
//     public void consumerMessages(){
//         AbstractCommonListener listener = BeanUtils.instantiate(GlobalOrderListener.class);
//         this.orderConsumer.subscribe(listener.getTopic(), "*", (MessageOrderListener) listener);
//     }
//
// }