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
import lombok.extern.slf4j.Slf4j;

/*
 * 描述 : BIO 的实现方式
 *
 * @param
 * @return
 * @author: sunshumeng @ 2019/5/2 22:34
 */
@Slf4j
public class ServerBIO {

  public static void main(String[] args) throws IOException {
    ServerSocket ss = new ServerSocket();
    ss.bind(new InetSocketAddress("127.0.0.1", 8888));
    Socket accept = ss.accept();
    new Thread(() -> {
      handle(accept);
    }).start();
  }

  static void handle(Socket s) {
    try {
      byte[] bytes = new byte[1024];
      int len = s.getInputStream().read(bytes);
      System.out.println(new String(bytes, 0, len));

      s.getOutputStream().write(bytes, 0, len);
      s.getOutputStream().flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
