// package com.ma.pattern.demo;
//
// import java.util.Arrays;
// import java.util.stream.Collectors;
// import org.springframework.beans.BeanUtils;
//
// public final class HandlerFactory {
//
//     private static TypeConverterManager typeConverterManager = JoddBean.get().typeConverterManager();
//
//
//     public static  AbstractMessageHandler newJpushOrderHandler(){
//         return new JpushOrderHandler();
//     };
//
//     public static AbstractMessageHandler newDelayRemoveOrderHandler(){
//         return new DelayRemoveOrderHandler();
//     }
//
//     /**
//      * 责任链模式
//      */
//     @SafeVarargs
//     public static AbstractMessageHandler getHandlerResponsibilityChain(Class< ? extends AbstractMessageHandler> ... handlers ) {
//
//         Preconditions.checkNotNull(handlers, "handler列表不能为空");
//         if (handlers.length == CommonConstants.TRUE) {
//             return BeanUtils.instantiate(handlers[CommonConstants.FIRST_ELEMENT]);
//         }
//         List list = Arrays.stream(handlers).map(BeanUtils::instantiate).collect(Collectors.toList());
//         AbstractMessageHandler result = null;
//         for (int i = 1; i < list.size(); i++) {
//             AbstractMessageHandler pre = typeConverterManager.convertType(list.get(i - 1), handlers[i - 1]);
//             AbstractMessageHandler cur = typeConverterManager.convertType(list.get(i), handlers[i]);
//             cur.setNextHandler(pre);
//             result = cur;
//         }
//         return result;
//     }
// }