/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.IO.BIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Handler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
// blocking IO    很少使用
// 大量连接的时候 不建议使用
public class Server2 {

  public static void main(String[] args) throws IOException {
    ServerSocket serverSocket = new ServerSocket();
    serverSocket.bind(new InetSocketAddress("127.0.0.1", 8888));
    // client 和 server 建立连接
    while (true) {
      Socket s = serverSocket.accept(); // 阻塞  等待  客户端连接

      new Thread(() -> {
        handle(serverSocket);
      }).start();
    }
  }

  static void handle(ServerSocket s) {
    byte[] bytes = new byte[1024];

  }
}
