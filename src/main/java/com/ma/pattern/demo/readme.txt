业务场景

我们在项目中使用了阿里的MQ消息中间件，来加快请求的响应时间和异步解耦处理。
RocktMQ主要可以按Topic来分区，然后按Tag分组，不同的业务区分不同的 tag

比如：
短信类的消息 messageTag
手机推送消息 pushTag
延时任务消息 delayTag
等等。。。

常规写法

if(message.getTag() == messageTag){
 //doSomething
}else if(message.getTag() == pushTag){
 //doSomething
}else if (message.getTag() == delayTag){
 //doSomething
}

....

要是 ifelse 多了，最后会形成箭头代码，最后连自己都不知道逻辑了。
所以我就想到了责任链模式，刚好符合我们的实际场景。