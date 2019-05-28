/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.IO.NIO.demoNet.client;


public class Client {

  private static String DEFAULT_HOST = "127.0.0.1";
  private static int DEFAULT_PORT = 12345;
  private static ClientHandle clientHandle;

  public static void start() {
    start(DEFAULT_HOST, DEFAULT_PORT);
  }

  public static synchronized void start(String ip, int port) {
    if (clientHandle != null) {
      clientHandle.stop();
    }
    clientHandle = new ClientHandle(ip, port);
    new Thread(clientHandle, "AIOServer").start();
  }

  //向服务器发送消息
  public static boolean sendMsg(String msg) throws Exception {
    if (msg.equals("q")) {
      return false;
    }
    clientHandle.sendMsg(msg);
    return true;
  }

  public static void main(String[] args) {
    start();
  }
}
