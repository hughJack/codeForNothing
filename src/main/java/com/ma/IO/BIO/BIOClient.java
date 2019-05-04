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
import java.io.OutputStream;
import java.net.Socket;

public class BIOClient {

  public static void main(String[] args) {
    try {
      String serverName = "127.0.0.1";
      int port = 8888;
      System.out.println("连接到主机：" + serverName + " ，端口号：" + port);
      Socket client = new Socket(serverName, port);
      System.out.println("远程主机地址：" + client.getRemoteSocketAddress());
      // 发送消息
      OutputStream outToServer = client.getOutputStream();
      DataOutputStream out = new DataOutputStream(outToServer);
      out.writeUTF("Hello Server from " + client.getLocalSocketAddress());
      out.flush();
      System.out.println("write over, waiting for msg back...");
      // 接收消息
      InputStream inFromServer = client.getInputStream();
      DataInputStream in = new DataInputStream(inFromServer);
      System.out.println("服务器响应" + in.readUTF());
      client.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
