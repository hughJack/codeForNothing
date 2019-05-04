package com.ma.IO.AIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

// asynchronous 异步IO
// 单线程的模型
// 模型: 操作系统(OS)  通知 Selector 有连接过来了 , 只处理accept 的事件
// 需要调用系统的底层的代码
// 每一步使用钩子函数 (观察者模式)
public class AIOServer {

  public static void main(String[] args) throws Exception {
    final AsynchronousServerSocketChannel serverChannel = AsynchronousServerSocketChannel.open()
        .bind(new InetSocketAddress(8888));
    // 把 CompletionHandler 交给操作系统处理, 操作系统持有这个处理的引用?  占用内存 ? ?
    // 由于是异步的 所以
    serverChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
      @Override
      public void completed(AsynchronousSocketChannel client, Object attachment) {
        // 如果不写 ,那么下一个客户端连不上来了
        serverChannel.accept(null, this);
        try {
          System.out.println(client.getRemoteAddress());
          ByteBuffer buffer = ByteBuffer.allocate(1024);
          // 也是使用的 -- 回调函数 , 由操作系统持有这个引用
          // 读完了之后的操作
          client.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
              attachment.flip();
              System.out.println(new String(attachment.array(), 0, result));
              client.write(ByteBuffer.wrap("HelloClient".getBytes()));
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
              exc.printStackTrace();
            }
          });


        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      @Override
      public void failed(Throwable exc, Object attachment) {
        exc.printStackTrace();
      }
    });
    // 不那啥的话 线程就自己结束了, 所以不萌让主线程退出
    while (true) {
      Thread.sleep(1000);
    }

  }
}
