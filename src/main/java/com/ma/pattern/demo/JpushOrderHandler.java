// package com.ma.pattern.demo;
//
// import java.util.Arrays;
// import lombok.extern.slf4j.Slf4j;
// // 具体处理者 :推送消息Handler
// @Slf4j
// public class JpushOrderHandler extends AbstractMessageHandler {
//
//     @Override
//     public OrderAction handleMessage(Message message) {
//         String tagList = BundleUtil.getResult("mq.tag");
//         String[] tags = tagList.split(",");
//         if (message.getTopic().equals(getTopic()) && Arrays.asList(tags).contains(message.getTag())) {  //避免消费到其他消息 json转换报错
//             log.info(" 监听到推送消息,[topic:" + message.getTopic() + "], [tag:" + message.getTag() + "]。开始解析...");
//             try {
//                 // res 是生产者传过来的消息内容
//                 byte[] body = message.getBody();
//                 String res = new String(body);
//                 String substring = res.substring(res.length() -1, res.length());
//                 PushInfo info = JSON.parseObject(res.substring(0, res.length() - 1), PushInfo.class);
//
//                 if ("1".equals(substring)){
//                     // 分组推送
//                     CommonUtil.Jpush2SingleUserMq(info,true);
//                  }else {
//                  //  多个用户推送
//                     CommonUtil.Jpush2SingleUserMq(info,false);
//                 }
//                 return OrderAction.Success;
//             }catch (Exception e) {
//                 log.error("MessageListener.consume error:" + e.getMessage(), e);
//                 return OrderAction.Suspend;
//             }
//         } else {
//            if (nextHandler == null) {
//                log.info("未匹配到topic:{}, tag:{}跳过,",message.getTopic(), message.getTag());
//                return OrderAction.Success;
//            }
//            return nextHandler.handleMessage(message);
//        }
//     }
// }