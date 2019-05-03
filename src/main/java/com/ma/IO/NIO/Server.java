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
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

// NIO 的的单线程模型,,  基本不用
// Non-Blocking IO
// 主线程有一个 Selector 死循环轮询遍历管理事件状态: accept read write
// 可以用于用于写类似Tomcat的服务器
// Server 特别难写,
public class Server {

  public static void main(String[] args) throws IOException {
    ServerSocketChannel ssc = ServerSocketChannel.open(); // 双向  可读可写
    ssc.socket().bind(new InetSocketAddress("127.0.0.1", 8888));
    ssc.configureBlocking(false); // 使用非阻塞

    System.out.println("server started, listening on :" + ssc.getLocalAddress());
    Selector selector = Selector.open(); // 等待事件发生(accept read 等)
    ssc.register(selector, SelectionKey.OP_ACCEPT);// 注册对什么事件感兴趣, 此处为accept事件

    while (true) {
      selector.select();// 阻塞方法, 有accept事件的时候处理
      Set<SelectionKey> keys = selector.selectedKeys();
      Iterator<SelectionKey> it = keys.iterator();
      while (it.hasNext()) {
        SelectionKey key = it.next();
        it.remove();
        handle(key);
      }
    }

  }

  private static void handle(SelectionKey key) {
    if (key.isAcceptable()) {
      try {
        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
        SocketChannel sc = ssc.accept();// 建立channel, 在channel 上面注册 read 事件
        sc.configureBlocking(false); // non-blocking
        //new Client
        //
        //String hostIP = ((InetSocketAddress)sc.getRemoteAddress()).getHostString();

			/*
			log.info("client " + hostIP + " trying  to connect");
			for(int i=0; i<clients.size(); i++) {
				String clientHostIP = clients.get(i).clientAddress.getHostString();
				if(hostIP.equals(clientHostIP)) {
					log.info("this client has already connected! is he alvie " + clients.get(i).live);
					sc.close();
					return;
				}
			}*/

        sc.register(key.selector(), SelectionKey.OP_READ);// 指定read事件
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
      }
    } else if (key.isReadable()) { //flip 复位的操作
      SocketChannel sc = null;
      try {
        sc = (SocketChannel) key.channel();
        // ByteBuffer 不好用
        ByteBuffer buffer = ByteBuffer.allocate(512);
        buffer.clear();
        int len = sc.read(buffer);

        if (len != -1) {
          System.out.println(new String(buffer.array(), 0, len));
        }

        ByteBuffer bufferToWrite = ByteBuffer.wrap("HelloClient".getBytes());
        sc.write(bufferToWrite);
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        if (sc != null) {
          try {
            sc.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }
}
