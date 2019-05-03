/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.IO.NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PoolServer {

  ExecutorService pool = Executors.newFixedThreadPool(50);

  private Selector selector;
  //中文测试

  /**
   *
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {
    PoolServer server = new PoolServer();
    server.initServer(8000);
    server.listen();
  }

  /**
   *
   * @param port
   * @throws IOException
   */
  public void initServer(int port) throws IOException {
    //
    ServerSocketChannel serverChannel = ServerSocketChannel.open();
    //
    serverChannel.configureBlocking(false);
    //
    serverChannel.socket().bind(new InetSocketAddress(port));
    //
    this.selector = Selector.open();

    serverChannel.register(selector, SelectionKey.OP_ACCEPT);
    System.out.println("服务端启动成功！");
  }

  /**
   *
   * @throws IOException
   */
  @SuppressWarnings("unchecked")
  public void listen() throws IOException {
    // 轮询访问selector
    while (true) {
      //
      selector.select();
      //
      Iterator ite = this.selector.selectedKeys().iterator();
      while (ite.hasNext()) {
        SelectionKey key = (SelectionKey) ite.next();
        //
        ite.remove();
        //
        if (key.isAcceptable()) {
          ServerSocketChannel server = (ServerSocketChannel) key.channel();
          //
          SocketChannel channel = server.accept();
          //
          channel.configureBlocking(false);
          //
          channel.register(this.selector, SelectionKey.OP_READ);
          //
        } else if (key.isReadable()) {
          //
          key.interestOps(key.interestOps() & (~SelectionKey.OP_READ));
          //
          pool.execute(new ThreadHandlerChannel(key));
        }
      }
    }
  }
}
