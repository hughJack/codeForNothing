/**
 * 描述.
 *
 * @author sunshumeng
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 * @deprecated （可选）
 */
package com.ma.IO.NIO.bytebuffer;

import java.nio.ByteBuffer;

public class ByteBufferDemo {

  public static void main(String[] args) {
    // 创建一个缓冲区
    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

    // 看一下初始时4个核心变量的值
    System.out.println("初始时-->limit--->" + byteBuffer.limit());
    System.out.println("初始时-->position--->" + byteBuffer.position());
    System.out.println("初始时-->capacity--->" + byteBuffer.capacity());
    System.out.println("初始时-->mark--->" + byteBuffer.mark());

    System.out.println("--------------------------------------");

    // 添加一些数据到缓冲区中
    String s = "Java3y";
    byteBuffer.put(s.getBytes());
    ByteBufferDemo.checkPosParam(byteBuffer);
    readPattern(byteBuffer);
    // 重复写入
    byteBuffer.put(s.getBytes());
    ByteBufferDemo.checkPosParam(byteBuffer);
    readPattern(byteBuffer);

  }

  private static void readPattern(ByteBuffer byteBuffer) {
    // 创建一个limit()大小的字节数组(因为就只有limit这么多个数据可读)
    byte[] bytes = new byte[byteBuffer.limit()];
    // 将读取的数据装进我们的字节数组中
    byteBuffer.get(bytes);
    // 输出数据
    System.out.println(new String(bytes, 0, bytes.length));
    byteBuffer.clear();// 读完我们还想写数据到缓冲区，那就使用clear()函数，这个函数会“清空”缓冲区：
  }

  private static void checkPosParam(ByteBuffer byteBuffer) {
    // 看一下初始时4个核心变量的值
    System.out.println("put完之后-->limit--->" + byteBuffer.limit());
    System.out.println("put完之后-->position--->" + byteBuffer.position());
    System.out.println("put完之后-->capacity--->" + byteBuffer.capacity());
    System.out.println("put完之后-->mark--->" + byteBuffer.mark());

    System.out.println("--------------------------------------");

    byteBuffer.flip();
// 看一下初始时4个核心变量的值
    System.out.println("flip完之后-->limit--->" + byteBuffer.limit());
    System.out.println("flip完之后-->position--->" + byteBuffer.position());
    System.out.println("flip完之后-->capacity--->" + byteBuffer.capacity());
    System.out.println("flip完之后-->mark--->" + byteBuffer.mark());
  }
}
