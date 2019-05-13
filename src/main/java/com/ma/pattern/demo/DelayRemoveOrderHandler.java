// package com.ma.pattern.demo;
//
// import com.google.common.base.Charsets;
// import java.util.Collections;
// import java.util.concurrent.TimeUnit;
// import java.util.concurrent.locks.Lock;
// import java.util.concurrent.locks.ReentrantLock;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.util.StringUtils;
//
// @Slf4j
// // 具体处理者 :延时订单处理Handler
// public class DelayRemoveOrderHandler extends AbstractMessageHandler {
//
//     private static Lock lock = new ReentrantLock(true);
//
//     @Override
//     public OrderAction handleMessage(Message message) {
//         //消费延时订单tag
//         if (message.getTopic().equals(getTopic()) && message.getTag().equals(CommonConstants.TAG)) {
//             log.info(" 监听订单删除消息,[topic:" + message.getTopic() + "], [tag:" + message.getTag() + "]。开始解析...");
//             //userId + UNDER_BAR + borrowOrderId
//             try {
//                 String content = new String(message.getBody(), Charsets.UTF_8);
//                 log.info("消费内容 userId_borrowOrderId :{}", content);
//                 if (StringUtils.isEmpty(content)) {
//                     return OrderAction.Success;
//                 }
//                 String[] info = content.split(CommonConstants.UNDER_BAR);
//                 String userId = info[0];
//                 String key = CommonConstants.CART_ID_LIST + userId;
//
//                 lock.tryLock(3, TimeUnit.SECONDS);
//                 //查询用户购物车列表
//                 String orders = RedisUtil.getString(key);
//                 if (StringUtils.isEmpty(orders)) {
//                     return OrderAction.Success;
//                 }
//                 List orderList = JSONObject.parseArray(orders, Integer.class);
//                 List delList;
//                 String idStr = info[1];
//                 //判断是否是批量加入
//                 if (idStr.startsWith(CommonConstants.LIST_MARK)) {
//                     String[] s = content.split(CommonConstants.LIST_MARK);
//                     delList = JSONObject.parseArray(s[1], Integer.class);
//                 } else {
//                     delList = Collections.singletonList(Integer.valueOf(info[1]));
//                 }
//                 orderList.removeAll(delList);
//                 RedisUtil.setString(key, GsonUtil.objectConvertJson(orderList));
//                 log.info("删除用户:{},延时订单:{},成功", userId, delList.toString());
//                 return OrderAction.Success;
//             } catch (Exception e) {
//                 //消费失败，挂起当前队列
//                 log.error("延时订单:{}消费异常", new String(message.getBody(), Charsets.UTF_8));
//                 return OrderAction.Suspend;
//             } finally {
//                 lock.unlock();
//             }
//
//         } else {
//             if (nextHandler == null) {
//                 log.info("未匹配到topic:{}, tag:{}跳过,",message.getTopic(), message.getTag());
//                 return OrderAction.Success;
//             }
//            return nextHandler.handleMessage(message);
//         }
//     }
// }