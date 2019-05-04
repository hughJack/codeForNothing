/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.IO.AIO.demo.client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

public class WriteHandler implements CompletionHandler<Integer, ByteBuffer> {

  private AsynchronousSocketChannel clientChannel;
  private CountDownLatch latch;

  public WriteHandler(AsynchronousSocketChannel clientChannel, CountDownLatch latch) {
    this.clientChannel = clientChannel;
    this.latch = latch;
  }

  @Override
  public void completed(Integer result, ByteBuffer buffer) {
    //完成全部数据的写入
    if (buffer.hasRemaining()) {
      clientChannel.write(buffer, buffer, this);
    } else {
      //读取数据
      ByteBuffer readBuffer = ByteBuffer.allocate(1024);
      clientChannel.read(readBuffer, readBuffer, new ReadHandler(clientChannel, latch));
    }
  }

  @Override
  public void failed(Throwable exc, ByteBuffer attachment) {
    System.err.println("数据发送失败...");
    try {
      clientChannel.close();
      latch.countDown();
    } catch (IOException e) {
    }
  }
}
