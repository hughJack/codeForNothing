/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.IO.BIO.demo.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * BIO服务端源码---------网络编程的基本模型是C/S模型，即两个进程间的通信。
 *
 * @author 从以上代码，很容易看出，BIO主要的问题在于每当有一个新的客户端请求接入时，
 * @version 服务端必须创建一个新的线程来处理这条链路，在需要满足高性能、高并发的场景是没法应用的
 * @desc 服务端提供IP和监听端口，客户端通过连接操作想服务端监听的地址发起连接请求，通过三次握手连接， 如果连接成功建立，双方就可以通过套接字进行通信。    
 * 传统的同步阻塞模型开发中，ServerSocket负责绑定IP地址，启动监听端口；Socket负责发起连接操作。连接成功后，双方通过输入和输出流进行同步阻塞式通信。 
 *
 * 简单的描述一下BIO的服务端通信模型：采用BIO通信模型的服务端，通常由一个独立的Acceptor线程负责监听客户端的连接，它接收到客户端连接请求之后为每个客户端创建一个新的线程进行链路处理没处理完成后，通过输出流返回应答给客户端，线程销毁。即典型的一请求一应答通宵模型。
 * @time （大量创建新的线程会严重影响服务器性能，甚至罢工）。
 */
public final class Server {


  //默认的端口号
  private static int DEFAULT_PORT = 12345;
  //单例的ServerSocket
  private static ServerSocket server;

  //根据传入参数设置监听端口，如果没有参数调用以下方法并使用默认值
  public static void start() throws IOException {
    //使用默认值
    start(DEFAULT_PORT);
  }

  //这个方法不会被大量并发访问，不太需要考虑效率，直接进行方法同步就行了
  public synchronized static void start(int port) throws IOException {
    if (server != null) {
      return;
    }
    try {
      //通过构造函数创建ServerSocket
      //如果端口合法且空闲，服务端就监听成功
      server = new ServerSocket(port);
      System.out.println("服务器已启动，端口号：" + port);
      //通过无线循环监听客户端连接
      //如果没有客户端接入，将阻塞在accept操作上。
      while (true) {
        Socket socket = server.accept();
        //当有新的客户端接入时，会执行下面的代码
        //然后创建一个新的线程处理这条Socket链路
        new Thread(new ServerHandler(socket)).start();
      }
    } finally {
      //一些必要的清理工作
      if (server != null) {
        System.out.println("服务器已关闭。");
        server.close();
        server = null;
      }
    }
  }
}
