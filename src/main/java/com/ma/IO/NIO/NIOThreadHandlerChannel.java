/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.IO.NIO;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * NIO-Reactor 模式, 响应式编程,  --- 观察者模式
 * 消息队列 : :
 *
 * @throws IOException
 */
class NIOThreadHandlerChannel extends Thread {

  private SelectionKey key;

  NIOThreadHandlerChannel(SelectionKey key) {
    this.key = key;
  }

  @Override
  public void run() {
    //
    SocketChannel channel = (SocketChannel) key.channel();
    //
    ByteBuffer buffer = ByteBuffer.allocate(1024);
    //
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try {
      int size = 0;
      while ((size = channel.read(buffer)) > 0) {
        buffer.flip();
        baos.write(buffer.array(), 0, size);
        buffer.clear();
      }
      baos.close();
      //
      byte[] content = baos.toByteArray();
      ByteBuffer writeBuf = ByteBuffer.allocate(content.length);
      writeBuf.put(content);
      writeBuf.flip();
      channel.write(writeBuf);//
      if (size == -1) {

        channel.close();
      } else {
        //
        key.interestOps(key.interestOps() | SelectionKey.OP_READ);
        key.selector().wakeup();
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
