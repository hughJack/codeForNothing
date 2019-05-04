/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.IO.BIO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Handler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
// blocking IO    很少使用
// 大量连接的时候 不建议使用
public class BIOServer {

  public static void main(String[] args) throws IOException {
    ServerSocket serverSocket = new ServerSocket();
    serverSocket.bind(new InetSocketAddress("127.0.0.1", 8888));
    // client 和 server 建立连接
    while (true) {
      Socket s = serverSocket.accept(); // 阻塞  等待  客户端连接

      new Thread(() -> {
        handle(s);
      }).start();
    }
  }

  static void handle(Socket server) {
    log.info("远程主机的地址:端口号 = {} " , server.getRemoteSocketAddress());
    // #################################################
    // try {
    //   byte[] bytes = new byte[1024];
    //   int len = server.getInputStream().read(bytes);
    //   System.out.println(new String(bytes, 0, len));
    //
    //   server.getOutputStream().write(bytes, 0, len);
    //   server.getOutputStream().flush();
    // } catch (IOException e) {
    //   e.printStackTrace();
    // }
    // #################################################
    DataInputStream in = null;
    DataOutputStream out = null;
    try {
      InputStream is = server.getInputStream();
      in = new DataInputStream(is);
      String s = in.readUTF();// 当前线程等待结果到达直至错误
      System.out.println(s);
      out = new DataOutputStream(server.getOutputStream());
      out.writeUTF("谢谢连接我：" + server.getLocalSocketAddress() + "\nGoodbye!");
      server.close();
    } catch (SocketTimeoutException s) {
      System.out.println("Socket timed out!");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
