/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.IO.NIO.demoNet.server;


public class Server {

  private static int DEFAULT_PORT = 12345;
  private static ServerHandle serverHandle;

  public static void start() {
    start(DEFAULT_PORT);
  }

  public static synchronized void start(int port) {
    if (serverHandle != null) {
      serverHandle.stop();
    }
    serverHandle = new ServerHandle(port);
    new Thread(serverHandle, "Server").start();
  }

  public static void main(String[] args) {
    start();
  }
}
